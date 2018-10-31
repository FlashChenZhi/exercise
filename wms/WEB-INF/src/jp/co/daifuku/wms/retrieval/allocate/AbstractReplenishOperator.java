// $Id: AbstractReplenishOperator.java 7734 2010-03-26 06:22:45Z ota $
package jp.co.daifuku.wms.retrieval.allocate;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AllocatePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SysDate;


/**
 * 補充引当を行うクラスです。<br>
 * 在庫作成処理を呼び出し、
 * 移動作業情報、作業情報、搬送情報の作成処理を行います。
 *
 *
 * @version $Revision: 7734 $, $Date: 2010-03-26 15:22:45 +0900 (金, 26 3 2010) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: ota $
 */
public abstract class AbstractReplenishOperator
        extends AbstractAllocateOperator
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
     * 設定単位キー(帳票発行用)
     */
    private List<String> _tempSettingUnitKey = new ArrayList<String>();

    /**
     * 設定単位キー(ASRS用)(帳票発行用)
     */
    private List<String> _tempAsrsSettingUnitKey = new ArrayList<String>();

    /**
     * KEY：移動作業情報設定単位キー VALUE：そのキーで作成した作業件数
     */
    private Map<String, Integer> _moveKeyMap = new HashMap<String, Integer>();

    /**
     * KEY：エリアNo. VALUE：そのエリアで現在使われている移動作業情報設定単位キー
     */
    private Map<String, String> _areaKeyMap = new HashMap<String, String>();

    /**
     * KEY：移動作業情報設定単位キー VALUE：作業情報の設定単位キー
     */
    private Map<String, String> _workKeyMap = new HashMap<String, String>();

    /**
     * KEY：移動作業情報設定単位キー VALUE：そのキーに紐付くスケジュールNo.
     */
    private Map<String, String> _schNoMap = new HashMap<String, String>();

    /**
     * 作業種別
     */
    private String _jobType;

    /**
     * 出庫開始日時
     */
    private Date _retStartDate;

    /**
     * 在庫移動ハンドラ
     */
    private MoveWorkInfoHandler _mwih = null;

    private MoveWorkInfoSearchKey _mwiKey = null;

    /**
     * 作業情報ハンドラ
     */
    private WorkInfoHandler _wih = null;

    /**
     * 引当パタンマスタハンドラ
     */
    private AllocatePrioritySearchKey _allocKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param pattern 引当パタンNo.
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public AbstractReplenishOperator(Connection conn, String pattern, Class caller)
            throws CommonException
    {
        super(conn, pattern, caller);

        setJobType();

        _mwih = new MoveWorkInfoHandler(conn);
        _wih = new WorkInfoHandler(conn);
        _allocKey = new AllocatePrioritySearchKey();
        _mwiKey = new MoveWorkInfoSearchKey();

        _retStartDate = new SysDate();

    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 設定単位キーを返します。
     * @return 設定単位キーを返します。
     */
    public String[] getSettingUnitKeys()
    {
        if (_tempSettingUnitKey.size() > 0)
        {
            return _tempSettingUnitKey.toArray(new String[_tempSettingUnitKey.size()]);
        }
        else
        {
            return null;
        }
    }

    /**
     * 設定単位キー(ASRS用)を返します。
     * @return 設定単位キー(ASRS用)を返します。
     */
    public String[] getAsrsSettingUnitKeys()
    {
        if (_tempAsrsSettingUnitKey.size() > 0)
        {
            return _tempAsrsSettingUnitKey.toArray(new String[_tempAsrsSettingUnitKey.size()]);
        }
        else
        {
            return null;
        }
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 作業種別のセットを行います。
     * 親クラス(<code>AbstractReplenishOperator</code>)のsetJobType(String)を呼びます。
     */
    protected abstract void setJobType();

    /**
     * 作業種別をセットします。
     * @param jobType 作業種別
     */
    protected void setJobType(String jobType)
    {
        _jobType = jobType;
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.retrieval.allocate.AbstractAllocateOperator#createWork(jp.co.daifuku.wms.handler.Entity, jp.co.daifuku.wms.base.entity.Stock, int)
     */
    /**
     * {@inheritDoc}
     */
    @Override
    protected void createWork(Entity ent, Stock stk, int allocateQty)
            throws CommonException
    {
        // 設定単位キーを取得する ------------------
        String areano = stk.getAreaNo();
        String moveSettingUKey = null;
        String workSettingUKey = null;
        // 設定単位キーに紐づく処理件数
        int cnt = 0;
        // すでに引当処理をしたことがあるエリアの場合
        if (_areaKeyMap.containsKey(areano))
        {
            // 前に取得した設定単位キーを使用する
            moveSettingUKey = _areaKeyMap.get(areano);
            cnt = _moveKeyMap.get(moveSettingUKey);
            cnt++;
            _moveKeyMap.put(moveSettingUKey, cnt);

            // スケジュールNo.は前回使用したものをセットする
            if (isAsrsArea(areano))
            {
                workSettingUKey = _workKeyMap.get(moveSettingUKey);
                super.setScheduleNo(_schNoMap.get(moveSettingUKey));
            }

        }
        // このエリアから初めて引当を行う場合
        else
        {
            cnt = WmsParam.REPLENISH_MAX_PER_AREA + 1;
        }

        if (cnt > WmsParam.REPLENISH_MAX_PER_AREA)
        {
            // 新規に設定単位キーを採番し、処理件数を１件に初期化する
            cnt = 1;
            moveSettingUKey = getSequence().nextMoveSetUkey();

            // 帳票発行用に、設定単位キーを保持する
            if (isAsrsArea(areano))
            {
                _tempAsrsSettingUnitKey.add(moveSettingUKey);
                // 新規に搬送情報のスケジュールNo.を採番するため、リセットする
                super.resetScheduleNo();
                // 新規に設定単位キーを採番
                workSettingUKey = getSequence().nextWorkInfoSetUkey();
                _workKeyMap.put(moveSettingUKey, workSettingUKey);
            }
            else
            {
                _tempSettingUnitKey.add(moveSettingUKey);
            }
            _areaKeyMap.put(areano, moveSettingUKey);
            _moveKeyMap.put(moveSettingUKey, cnt);

        }
        // 設定単位キー取得ここまで -----------------

        MoveWorkInfo mwi = new MoveWorkInfo();

        String moveJobNo = getSequence().nextMoveJobNo();

        // 作業No.
        mwi.setJobNo(moveJobNo);
        // 設定単位キー
        mwi.setSettingUnitKey(moveSettingUKey);
        // 作業区分
        mwi.setJobType(_jobType);
        // 状態フラグ(出庫作業中)
        mwi.setStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_RETRIEVAL_WORKING);
        // ハードウェア区分(リスト作業中)
        mwi.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_LIST);
        // 入荷フラグ(入荷入庫以外)
        mwi.setReceivingFlag(MoveWorkInfo.RECEIVING_FLAG_EXCEPT_RECEIVEING_STORAGE);
        // 在庫ID
        mwi.setStockId(stk.getStockId());
        // 移動出庫エリア
        mwi.setRetrievalAreaNo(stk.getAreaNo());
        // 移動出庫棚
        mwi.setRetrievalLocationNo(stk.getLocationNo());
        // 荷主コード
        mwi.setConsignorCode(stk.getConsignorCode());
        // 商品コード
        mwi.setItemCode(stk.getItemCode());
        // ロットNo.
        mwi.setLotNo(stk.getLotNo());
        // 入庫日
        mwi.setStorageDay(stk.getStorageDay());
        // 入庫日時
        mwi.setStorageDate(stk.getStorageDate());
        // 最終出庫日
        mwi.setRetrievalDay(stk.getRetrievalDay());
        // 予定数
        mwi.setPlanQty(allocateQty);
        // 出庫開始日時(緊急出庫の場合のみセットする)
        if (_jobType.equals(SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT))
        {
            mwi.setRetrievalStartDate(_retStartDate);
        }

        // 補充先を決定する
        Locate toLoc = getDestLocate(ent, stk);
        // 移動入庫エリア
        mwi.setStorageAreaNo(toLoc.getAreaNo());
        // 移動入庫予定棚
        mwi.setStorageLocationNo(toLoc.getLocationNo());
        // 登録処理名
        mwi.setRegistPname(getCallerName());
        // 最終更新処理名
        mwi.setLastUpdatePname(getCallerName());

        // ASRSパッケージあり
        if (hasAsrsPack())
        {
            // 引当エリアがASRS
            if (isAsrsArea(areano))
            {
                // 作業No.の採番
                String jobNo = getSequence().nextWorkInfoJobNo();

                // 入出庫作業情報接続キー
                mwi.setWorkConnKey(jobNo);
                // 状態フラグ(未作業)
                mwi.setStatusFlag(MoveWorkInfo.STATUS_FLAG_UNSTART);
                // ハードウェア区分(ASRS)
                mwi.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_ASRS);

                // 入出庫作業情報を編集する
                WorkInfo wInfo = editWorkInfo(stk, allocateQty);
                wInfo.setSettingUnitKey(workSettingUKey);
                wInfo.setJobNo(jobNo);

                String carryKey = "";
                // 搬送情報が存在しない場合
                if (!hasCarryInfo(stk.getPalletId()))
                {
                    // スケジュールNo.を保持する
                    _schNoMap.put(moveSettingUKey, super.getScheduleNo());

                    CarryInfo ci = editCarryInfo(stk ,null);
                    if (_jobType.equals(SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT))
                    {
                        ci.setWorkType(CarryInfo.WORK_TYPE_NORMAL_REPLENISHMENT);
                    }
                    else
                    {
                        ci.setWorkType(CarryInfo.WORK_TYPE_EMERGENCY_REPLENISHMENT);
                    }
                    new CarryInfoHandler(getConnection()).create(ci);

                    carryKey = ci.getCarryKey();
                }
                else
                {
                    carryKey = getCarryKey(stk.getPalletId());
                }
                wInfo.setSystemConnKey(carryKey);// 搬送キー

                _wih.create(wInfo);
            }
        }

        // 移動作業情報の作成
        _mwih.create(mwi);
    }

    /**
     * 在庫の補充先のエリアを決定します。<BR>
     * 在庫の荷主コード、商品コードをもとに、該当する固定棚情報を取得し
     * 今回の引当パターンマスタの優先順位に従い固定棚情報を決定します。
     * 該当する固定棚情報がない場合や、今回の引当パターンに含まれていない場合はnullを返します。
     *
     * @param ent 引当クラスから指定されたエンティティ
     * @param stk 引当てたStockエンティティ
     * @return 補充先の棚NoとエリアNoをセットしたLocateインスタンス
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException 商品固定棚情報に不整合データが存在した場合にスローされます。
     * @throws 引数のエンティティにFixedLocateInfo、Locate以外がセットされていた場合にスローされます。
     */
    private Locate getDestLocate(Entity ent, Stock stk)
            throws ReadWriteException,
                InvalidDefineException,
                ScheduleException
    {
        Locate ret = new Locate();

        // 補充先を決定する
        FixedLocateInfo toLoc = null;
        if (ent instanceof FixedLocateInfo)
        {
            toLoc = (FixedLocateInfo)ent;
        }
        else if (ent instanceof Locate)
        {
            return (Locate)ent;
        }
        else
        {
            throw new ScheduleException();
        }

        ret.setAreaNo(toLoc.getAreaNo());
        ret.setLocationNo(toLoc.getLocationNo());

        return ret;
    }

    /**
     * 指定されたパラメータからASRS緊急補充用の入出庫作業情報を編集して返します。
     * 
     * @param stk 在庫情報
     * @param allocateQty 引当数
     * @return 入出庫作業情報
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    private WorkInfo editWorkInfo(Stock stk, int allocateQty)
            throws ReadWriteException
    {
        WorkInfo wInfo = new WorkInfo();

        // 作業区分
        wInfo.setJobType(_jobType);
        // 状態フラグ(未開始)
        wInfo.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
        // ハードウェア区分(ASRS)
        wInfo.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS);
        // 在庫ID
        wInfo.setStockId(stk.getStockId());
        // 予定日(WareNaviシステムの作業日)
        wInfo.setPlanDay(getWnSysCon().getWorkDay());
        // 荷主コード
        wInfo.setConsignorCode(stk.getConsignorCode());
        // 商品コード
        wInfo.setItemCode(stk.getItemCode());
        // エリア
        wInfo.setPlanAreaNo(stk.getAreaNo());
        // 棚
        wInfo.setPlanLocationNo(stk.getLocationNo());
        // 予定数
        wInfo.setPlanQty(allocateQty);
        // 登録処理名
        wInfo.setRegistPname(getCallerName());
        // 最終更新処理名
        wInfo.setLastUpdatePname(getCallerName());

        return wInfo;
    }

    /**
     * 移動作業情報に指定されたパラメータの未完了の補充作業情報が存在するかどうかを判定する。<BR>
     * 
     * @param areaNo 計画補充の場合に指定します
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @return データが存在する : true データが存在しない : false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected boolean hasMoveWorkInfo(String areaNo, String consignorCode, String itemCode)
            throws ReadWriteException
    {
        ArrayList<String> ukey = new ArrayList<String>();
        if (getSettingUnitKeys() != null)
        {
            for (String key : getSettingUnitKeys())
            {
                ukey.add(key);
            }
        }
        if (getAsrsSettingUnitKeys() != null)
        {
            for (String key : getAsrsSettingUnitKeys())
            {
                ukey.add(key);
            }
        }

        _mwiKey.clear();
        _mwiKey.setConsignorCode(consignorCode);
        _mwiKey.setItemCode(itemCode);
        // 完了以外
        _mwiKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_COMPLETION, "!=");
        // 削除以外
        _mwiKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_DELETE, "!=");
        // 入庫キャンセル以外
        _mwiKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_CANCEL, "!=");
        // 今回自分が作成した移動情報以外
        for (String key : ukey.toArray(new String[ukey.size()]))
        {
            _mwiKey.setSettingUnitKey(key, "!=");
        }

        // 緊急補充の場合、引当パターンに含まれる出庫エリアへの補充作業を対象とする
        if (_jobType.equals(SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT))
        {
            _allocKey.clear();
            _allocKey.setAllocateNo(getPattern());
            _allocKey.setReplenishmentAreaType(AllocatePriority.REPLENISHMENT_AREA_TYPE_NORMAL_AREA);
            _mwiKey.setKey(_allocKey);

            _mwiKey.setJoin(MoveWorkInfo.STORAGE_AREA_NO, AllocatePriority.ALLOCATE_AREA);
        }
        // 計画補充の場合、画面から指定されたエリアに対する補充作業を対象とする
        else
        {
            _mwiKey.setStorageAreaNo(areaNo);
        }

        if (_mwih.count(_mwiKey) != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

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
        return "$Id: AbstractReplenishOperator.java 7734 2010-03-26 06:22:45Z ota $";
    }


}
