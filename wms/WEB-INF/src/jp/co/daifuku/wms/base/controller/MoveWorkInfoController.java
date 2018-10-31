// $Id: MoveWorkInfoController.java 5020 2009-09-17 10:25:05Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.db.SysDate;

/**
 * 移動作業情報を操作するためのコントローラクラスです。
 * 
 * @version $Revision: 5020 $, $Date: 2009-09-17 19:25:05 +0900 (木, 17 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class MoveWorkInfoController
        extends AbstractController
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
    private WMSSequenceHandler _seqHandler = null;

    private WarenaviSystemController _wmsSysCtlr = null;

    private MoveResultController _moveResultCtlr = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public MoveWorkInfoController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 移動入庫開始ロック処理を行います。<br>
     * 移動入庫開始のための作業情報を検索してロックし、内容を返します。<br>
     * 対象データが0件の場合は、要素数0の配列を返します。
     * 
     * @param consignor 荷主コード
     * @param item 商品コード
     * @param jobNo 作業No.
     * @param jobType 作業区分
     * @return 対象作業一覧
     * @throws LockTimeOutException ロックタイムアウト
     * @throws ReadWriteException データベースエラー
     */
    public MoveWorkInfo[] lockStorageStart(String consignor, String item, String jobNo, String jobType)
            throws ReadWriteException,
                LockTimeOutException
    {
        MoveWorkInfoHandler mwih = new MoveWorkInfoHandler(getConnection());

        String targetStatus = MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_WAITING;
        String[] items = {
            item,
        };
        MoveWorkInfoSearchKey key = createStorageKey(consignor, items, jobNo, jobType, targetStatus);

        key.setJobNoOrder(true);

        MoveWorkInfo[] mwis = (MoveWorkInfo[])retryLock(key, mwih, 1);
        if (ArrayUtil.isEmpty(mwis))
        {
            return new MoveWorkInfo[0];
        }
        return mwis;
    }

    /**
     * 移動出庫開始ロック処理を行います。<br>
     * 移動出庫開始のための作業情報を検索してロックし、内容を返します。
     * 
     * @param consignor 荷主コード
     * @param area エリアNo.
     * @param orders オーダーNo.
     * @return 対象作業一覧
     * @throws LockTimeOutException ロックタイムアウト
     * @throws ReadWriteException データベースエラー
     */
    @Deprecated
    public MoveWorkInfo[] lockRetrievalStart(String consignor, String area, String... orders)
            throws ReadWriteException,
                LockTimeOutException
    {
        // FIXME DESIGN PENDING. 

        MoveWorkInfoHandler mwih = new MoveWorkInfoHandler(getConnection());

        String targetStatus = MoveWorkInfo.STATUS_FLAG_UNSTART;
        MoveWorkInfoSearchKey key = createRetrievalKey(consignor, area, targetStatus, orders);

        key.setRetrievalLocationNoOrder(true);
        key.setItemCodeOrder(true);
        key.setLotNoOrder(true);

        MoveWorkInfo[] mwis = (MoveWorkInfo[])retryLock(key, mwih, 1);
        if (ArrayUtil.isEmpty(mwis))
        {
            return new MoveWorkInfo[0];
        }
        return mwis;
    }

    /**
     * 完了時のロック処理(設定単位キー単位)を行います。<br>
     * 設定単位キー、状態フラグ=作業中に該当するデータをロックします。
     * 
     * @param settingUkey 設定単位キー 
     * @param jobType 作業区分
     * @throws LockTimeOutException ロックタイムアウト
     * @throws ReadWriteException データベースエラー
     */
    @Deprecated
    public void lockComplete(String settingUkey, String jobType)
            throws ReadWriteException,
                LockTimeOutException
    {
        // FIXME DESIGN PENDING.

        MoveWorkInfoHandler mwih = new MoveWorkInfoHandler(getConnection());

        String targetStatus = MoveWorkInfo.STATUS_FLAG_NOWWORKING;

        MoveWorkInfoSearchKey key = new MoveWorkInfoSearchKey();
        key.setSettingUnitKey(settingUkey);
        key.setStatusFlag(targetStatus);

        retryLock(key, mwih, 1);
    }

    /**
     * 完了ロック (作業No.単位)を行います。<br>
     * 作業No.、状態フラグに該当するデータをロックして返します。
     * 
     * @param jobNo 作業No.
     * @param statusFlag 状態フラグ
     * @return ロックした作業情報
     * @throws LockTimeOutException ロックタイムアウト
     * @throws ReadWriteException データベースエラー
     * @throws NoPrimaryException 対象データが複数存在した場合
     * @throws NotFoundException 対象データなし
     */
    public MoveWorkInfo lockCompleteJob(String jobNo, String statusFlag)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException
    {
        MoveWorkInfoHandler mwih = new MoveWorkInfoHandler(getConnection());

        String targetStatus = statusFlag;

        MoveWorkInfoSearchKey key = new MoveWorkInfoSearchKey();
        key.setJobNo(jobNo);
        key.setStatusFlag(targetStatus);

        MoveWorkInfo[] mwis = (MoveWorkInfo[])retryLock(key, mwih, 1);
        if (ArrayUtil.isEmpty(mwis))
        {
            throw new NotFoundException();
        }
        if (1 != mwis.length)
        {
            throw new NoPrimaryException();
        }
        return mwis[0];
    }

    /**
     * 作業開始処理を行います。<br>
     * 該当する移動作業情報を作業開始状態に更新します。
     * 
     * @param jobNo 作業No.
     * @param settingUKey 設定単位キー
     * @param status 状態フラグ
     * @param ui ユーザ情報
     * @throws InvalidDefineException 状態フラグが移動出庫中,移動入庫中のいずれでもない
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 対象作業データなし
     */
    public void start(String jobNo, String settingUKey, String status, WmsUserInfo ui)
            throws InvalidDefineException,
                NotFoundException,
                ReadWriteException
    {
        MoveWorkInfoAlterKey akey = createStartAlterKey(settingUKey, status, ui);

        // set key for update
        akey.setJobNo(jobNo);

        // update a record
        MoveWorkInfoHandler mwih = new MoveWorkInfoHandler(getConnection());
        mwih.modify(akey);
    }

    /**
     * AS/RS用 作業開始処理を行います。<br>
     * 該当する移動作業情報を作業開始状態に更新します。
     * 
     * @param jobNo 作業No.(作業接続キーと比較,絞込みを行います)
     * @param settingUKey 設定単位キー
     * @param status 状態フラグ
     * @param ui ユーザ情報
     * @throws InvalidDefineException 状態フラグが移動出庫中,移動入庫中のいずれでもない
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 対象作業データなし
     */
    public void startAsWork(String jobNo, String settingUKey, String status, WmsUserInfo ui)
            throws InvalidDefineException,
                NotFoundException,
                ReadWriteException
    {
        MoveWorkInfoAlterKey akey = createStartAlterKey(settingUKey, status, ui);

        // set key for update
        akey.setWorkConnKey(jobNo);

        // update a record
        MoveWorkInfoHandler mwih = new MoveWorkInfoHandler(getConnection());
        mwih.modify(akey);
    }

    /**
     * 移動作業完了処理を行います。<br>
     * 移動作業情報を完了状態で作成します。
     * 
     * @param retrieval 移動元在庫
     * @param storage 移動先在庫
     * @param settingUkey 設定単位キー
     * @param jobType 作業区分
     * @param qty 完了数
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースエラー
     * @throws ScheduleException WarenaviSystemテーブルに整合性がない
     * @throws DataExistsException 移動作業情報登録済み
     * @throws NoPrimaryException 移動実績作成に失敗
     * @throws NotFoundException 移動実績作成に失敗
     */
    public void complete(Stock retrieval, Stock storage, String settingUkey, String jobType, int qty, WmsUserInfo ui)
            throws ReadWriteException,
                ScheduleException,
                DataExistsException,
                NotFoundException,
                NoPrimaryException
    {
        MoveWorkInfo mwi = new MoveWorkInfo();

        String jobNo = getSeqHandler().nextMoveJobNo();
        mwi.setJobNo(jobNo);

        mwi.setSettingUnitKey(settingUkey);
        mwi.setJobType(jobType);
        mwi.setStatusFlag(MoveWorkInfo.STATUS_FLAG_COMPLETION);
        mwi.setHardwareType(ui.getHardwareType());
        mwi.setReceivingFlag(MoveWorkInfo.RECEIVING_FLAG_EXCEPT_RECEIVEING_STORAGE);

        mwi.setStockId(storage.getStockId()); // NEW STOCK ID

        mwi.setRetrievalAreaNo(retrieval.getAreaNo());
        mwi.setRetrievalLocationNo(retrieval.getLocationNo());
        mwi.setConsignorCode(retrieval.getConsignorCode());
        mwi.setItemCode(retrieval.getItemCode());
        mwi.setLotNo(retrieval.getLotNo());
        mwi.setStorageDay(retrieval.getStorageDay());
        mwi.setStorageDate(retrieval.getStorageDate());
        mwi.setRetrievalDay(retrieval.getRetrievalDay());
        mwi.setRetrievalUserId(ui.getUserId());
        mwi.setRetrievalTerminalNo(ui.getTerminalNo());
        mwi.setRetrievalWorkDate(new SysDate());

        mwi.setPlanQty(qty);
        mwi.setRetrievalResultQty(qty);
        mwi.setStorageResultQty(qty);

        mwi.setStorageAreaNo(storage.getAreaNo()); // NEW AREA
        mwi.setStorageLocationNo(storage.getLocationNo()); // NEW LOCATION

        mwi.setStorageUserId(ui.getUserId());
        mwi.setStorageTerminalNo(ui.getTerminalNo());
        mwi.setStorageWorkDate(new SysDate());

        String workDay = getWmsSysCtlr().getWorkDay();
        mwi.setStorageWorkDay(workDay);
        mwi.setStorageWorkSecond(0);

        mwi.setRegistPname(getCallerName());
        mwi.setRegistDate(new SysDate());
        mwi.setLastUpdatePname(getCallerName());

        new MoveWorkInfoHandler(getConnection()).create(mwi);

        // Result regist
        MoveResultController mrctlr = getMoveResultCtlr();
        mrctlr.insert(jobNo);
    }

    /**
     * 移動作業完了処理を行います。<br>
     * 移動作業情報を完了状態で作成します。
     * 
     * @param retrieval 移動元在庫
     * @param storage 移動先在庫
     * @param settingUkey 設定単位キー
     * @param jobNo 作業No.
     * @param jobType 作業区分
     * @param qty 完了数
     * @param ui ユーザ情報
     */
    @Deprecated
    public void completeJob(Stock retrieval, Stock storage, String settingUkey, String jobNo, String jobType, int qty,
            WmsUserInfo ui)
    {
        // FIXME DESIGN PENDING
    }

    /**
     * 移動出庫作業完了処理を行います。<br>
     * 移動作業情報を出庫済み入庫待ち状態で作成します。
     * 
     * @param retrieval 移動元在庫
     * @param jobType 作業区分
     * @param stockId 在庫ID
     * @param qty 出庫数
     * @param second 作業時間(秒数)
     * @param ui ユーザ情報
     * @param receivingFlg 入荷作業
     * @return jobno
     * @throws ReadWriteException データベースエラー
     * @throws ScheduleException WarenaviSystemテーブルに整合性がない
     * @throws DataExistsException 移動作業情報登録済み
     * @throws NoPrimaryException 移動実績作成に失敗
     * @throws NotFoundException 移動実績作成に失敗
     */
    public String completeRetrieval(Stock retrieval, String jobType, String stockId, int qty, int second,
            WmsUserInfo ui, String receivingFlg)
            throws ReadWriteException,
                ScheduleException,
                DataExistsException,
                NotFoundException,
                NoPrimaryException
    {
        MoveWorkInfo mwi = new MoveWorkInfo();

        String jobNo = getSeqHandler().nextMoveJobNo();
        mwi.setJobNo(jobNo);

        mwi.setJobType(jobType);
        mwi.setStatusFlag(SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING);
        mwi.setHardwareType(ui.getHardwareType());

        mwi.setReceivingFlag(receivingFlg);

        mwi.setStockId(stockId);

        mwi.setRetrievalAreaNo(retrieval.getAreaNo());
        mwi.setRetrievalLocationNo(retrieval.getLocationNo());
        mwi.setConsignorCode(retrieval.getConsignorCode());
        mwi.setItemCode(retrieval.getItemCode());
        mwi.setLotNo(retrieval.getLotNo());
        mwi.setStorageDay(retrieval.getStorageDay());
        mwi.setStorageDate(retrieval.getStorageDate());
        mwi.setRetrievalDay(retrieval.getRetrievalDay());
        mwi.setRetrievalUserId(ui.getUserId());
        mwi.setRetrievalTerminalNo(ui.getTerminalNo());
        mwi.setRetrievalWorkDate(new SysDate());

        mwi.setPlanQty(qty);
        mwi.setRetrievalResultQty(qty);
        mwi.setRetrievalWorkSecond(second);

        mwi.setStorageResultQty(0);

        mwi.setRegistPname(getCallerName());
        //        mwi.setRegistDate(new SysDate());
        mwi.setLastUpdatePname(getCallerName());

        new MoveWorkInfoHandler(getConnection()).create(mwi);

        return jobNo;
    }

    /**
     * 移動出庫作業完了処理を行います。<br>
     * 移動作業情報を出庫済み入庫待ち状態で作成します。
     * 
     * @param retrieval 移動元在庫
     * @param jobNo 作業No.
     * @param jobType 作業区分
     * @param stockId 在庫ID
     * @param qty 出庫数
     * @param ui ユーザ情報
     */
    @Deprecated
    public void completeRestrievalJob(Stock retrieval, String jobNo, String jobType, String stockId, int qty,
            WmsUserInfo ui)
    {
        // FIXME DESIGN PENDING
    }

    /**
     * 移動入庫作業完了処理を行います。<br>
     * 実績数により、確定,分割,キャンセルのいずれかを行います。<br>
     * キャンセル時に設定単位キー、ハードウェア区分はクリアしません。<br>
     * (出庫作業内容が不明になってしまうため)
     * 
     * @param work 対象作業情報
     * @param stockId 在庫ID
     * @param area エリア
     * @param location ロケーション
     * @param qty 完了数
     * @param workday 作業日
     * @param worksec 作業秒数
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 該当作業データなし
     * @throws DataExistsException 分割作業情報登録済み
     * @throws NoPrimaryException FROM MoveResultController INSERT
     * @see MoveResultController#insert(String)
     */
    public void completeStorage(MoveWorkInfo work, String stockId, String area, String location, int qty,
            String workday, int worksec, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException,
                NoPrimaryException
    {
        completeStorage(work, stockId, area, location, qty, workday, worksec, ui, false);
    }

    /**
     * 移動入庫作業完了処理を行います。<br>
     * 実績数により、確定,分割,キャンセルのいずれかを行います。<br>
     * キャンセル時に設定単位キー、ハードウェア区分はクリアしません。<br>
     * (出庫作業内容が不明になってしまうため)
     * 
     * @param work 対象作業情報
     * @param stockId 在庫ID
     * @param area エリア
     * @param location ロケーション
     * @param qty 完了数
     * @param workday 作業日
     * @param worksec 作業秒数
     * @param ui ユーザ情報
     * @param isUpdateRetrievalResultQty 出庫数出庫実績数・・・入庫予定数を完了数で更新するか判定します
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 該当作業データなし
     * @throws DataExistsException 分割作業情報登録済み
     * @throws NoPrimaryException FROM MoveResultController INSERT
     * @see MoveResultController#insert(String)
     */
    public void completeStorage(MoveWorkInfo work, String stockId, String area, String location, int qty,
            String workday, int worksec, WmsUserInfo ui, boolean isUpdateRetrievalResultQty)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException,
                NoPrimaryException
    {
        MoveWorkInfoHandler mwih = new MoveWorkInfoHandler(getConnection());
        boolean lessPlan = (qty < work.getPlanQty());
        boolean isComplete = (0 < qty);
        String jobNo = work.getJobNo();

        if (isComplete)
        {
            // do complete
            MoveWorkInfoAlterKey akey = new MoveWorkInfoAlterKey();

            // set updates
            akey.updateStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);

            akey.updateStockId(work.getStockId());
            akey.updateStorageResultQty(qty);
            akey.updateStorageAreaNo(area);
            akey.updateStorageLocationNo(location);
            akey.updateStorageWorkDate(new SysDate());
            akey.updateStorageWorkDay(workday);
            akey.updateStorageWorkSecond(worksec);

            akey.updateStorageUserId(ui.getUserId());
            akey.updateStorageTerminalNo(ui.getTerminalNo());

            if (isUpdateRetrievalResultQty)
            {
                akey.updateRetrievalResultQty(qty);
            }

            // check plan qty
            if (lessPlan)
            {
                akey.updatePlanQty(qty);
                akey.updateRetrievalResultQty(qty);
            }

            akey.updateLastUpdatePname(getCallerName());

            // set key value
            akey.setJobNo(jobNo);

            // update 移動作業
            mwih.modify(akey);

            // 履歴の登録
            getMoveResultCtlr().insert(jobNo);

            // check divided
            if (lessPlan)
            {
                // 残作業の登録
                MoveWorkInfo divwork = (MoveWorkInfo)work.clone();

                String dJobNo = getSeqHandler().nextMoveJobNo();
                int resQty = work.getPlanQty() - qty;

                divwork.setJobNo(dJobNo);
                divwork.setStatusFlag(SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING);
                divwork.setPlanQty(resQty);
                divwork.setRetrievalResultQty(resQty);
                divwork.setStorageResultQty(0);
                divwork.setStorageUserId("");
                divwork.setStorageTerminalNo("");
                divwork.setRegistPname(getCallerName());
                divwork.setRegistDate(new SysDate());
                divwork.setLastUpdatePname(getCallerName());

                mwih.create(divwork);
            }
        }
        else
        {
            // do cancel
            MoveWorkInfoAlterKey akey = createCancelKey();
            akey.setJobNo(jobNo);

            mwih.modify(akey);
        }
    }

    /**
     * 入庫キャンセル処理を行います。<br>
     * 作業No. に該当する移動作業の入庫キャンセル処理を行います。
     * 
     * @param jobNo 対象作業No.
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 更新対象作業データなし
     * @throws ScheduleException 移動作業実績作成に失敗した
     * @throws DataExistsException 移動作業実績作成に失敗した
     * @throws NoPrimaryException 移動作業実績作成に失敗した
     */
    public void cancelStorage(String jobNo)
            throws NotFoundException,
                ReadWriteException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        MoveWorkInfoAlterKey akey = new MoveWorkInfoAlterKey();

        akey.updateStatusFlag(SystemDefine.STATUS_FLAG_MOVE_STORAGE_CANCEL);
        akey.updateLastUpdatePname(getCallerName());

        akey.setJobNo(jobNo);

        new MoveWorkInfoHandler(getConnection()).modify(akey);
    }

    /**
     * 補充完了処理を行います。<br>
     * 出庫済み入庫待ちでない作業の場合は、出庫完了処理を行ったあと、入庫完了メソッドを呼び出します。<br>
     * 入庫完了処理では、実績数により、確定,分割,キャンセルのいずれかを行います。<br>
     * キャンセル時に設定単位キー、ハードウェア区分はクリアしません。<br>
     * (出庫作業内容が不明になってしまうため)
     * 
     * @param work 対象作業情報
     * @param stockId 在庫ID
     * @param area エリア
     * @param location ロケーション
     * @param qty 完了数
     * @param workday 作業日
     * @param worksec 作業秒数
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 該当作業データなし
     * @throws DataExistsException 分割作業情報登録済み
     * @throws NoPrimaryException FROM MoveResultController INSERT
     * @see MoveResultController#insert(String)
     */
    public void completeReplenish(MoveWorkInfo work, String stockId, String area, String location, int qty,
            String workday, int worksec, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException,
                NoPrimaryException
    {
        // 出庫済み入庫待ちでない場合は、出庫完了処理を行う
        if (!work.getStatusFlag().equals(SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING))
        {
            MoveWorkInfoHandler mwih = new MoveWorkInfoHandler(getConnection());
            String jobNo = work.getJobNo();

            // do complete
            MoveWorkInfoAlterKey akey = new MoveWorkInfoAlterKey();

            // set updates
            akey.updateStatusFlag(SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING);
            akey.updateStockId(stockId);

            akey.updateRetrievalResultQty(qty);
            akey.updateRetrievalWorkDate(new SysDate());
            akey.updateRetrievalWorkSecond(worksec);

            akey.updateRetrievalUserId(ui.getUserId());
            akey.updateRetrievalTerminalNo(ui.getTerminalNo());

            akey.updateLastUpdatePname(getCallerName());

            // set key value
            akey.setJobNo(jobNo);

            // update 移動作業
            mwih.modify(akey);

        }

        // 入庫完了処理を行う
        completeStorage(work, stockId, area, location, qty, workday, worksec, ui, false);

    }

    /**
     * 作業キャンセル処理を行います。<br>
     * 設定単位キーに該当する移動作業のキャンセルを行います。
     * 
     * @param settingUKey 対象設定単位キー
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 対象データなし
     */
    @Deprecated
    public void cancelWork(String settingUKey)
            throws NotFoundException,
                ReadWriteException
    {
        // FIXME DESIGN PENDING.

        MoveWorkInfoAlterKey akey = new MoveWorkInfoAlterKey();

        // set update values
        akey.updateStatusFlag(SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING);
        akey.updateStorageUserId("");
        akey.updateStorageTerminalNo("");
        akey.updateLastUpdatePname(getCallerName());

        // set keys
        akey.setSettingUnitKey(settingUKey);
        akey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);

        // update a record
        new MoveWorkInfoHandler(getConnection()).modify(akey);
    }

    /**
     * 移動入庫開始可能チェック処理を行います。<br>
     * 移動作業を検索し、入庫開始データの状態をチェックします。<br>
     * なんらかの問題がある場合には例外がスローされます。
     * @param consignor 荷主コード
     * @param item 商品コード
     * @param jobNo 作業No.
     * @param jobType 作業タイプ
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 対象移動作業なし
     * @throws OperatorException チェックの結果開始できないとき理由が通知されます
     */
    @Deprecated
    public void checkStorageStart(String consignor, String item, String jobNo, String jobType)
            throws ReadWriteException,
                NotFoundException,
                OperatorException
    {
        String[] items = {
            item,
        };
        checkStorageStart(consignor, items, jobNo, jobType);
    }

    /**
     * 移動入庫開始可能チェック処理を行います<br>
     * 移動作業を検索し、入庫開始データの状態をチェックします。<br>
     * なんらかの問題がある場合には例外がスローされます。
     * 
     * @param consignor 荷主コード
     * @param items 商品コード
     * @param jobNo 作業No.
     * @param jobType 作業タイプ
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 対象移動作業なし
     * @throws OperatorException チェックの結果開始できないとき理由が通知されます
     */
    public void checkStorageStart(String consignor, String[] items, String jobNo, String jobType)
            throws ReadWriteException,
                NotFoundException,
                OperatorException
    {
        MoveWorkInfoSearchKey key = createStorageKey(consignor, items, jobNo, jobType, null);

        key.setStatusFlagCollect("MIN");

        MoveWorkInfo[] mwis = (MoveWorkInfo[])new MoveWorkInfoHandler(getConnection()).find(key);

        if (ArrayUtil.isEmpty(mwis))
        {
            throw new NotFoundException();
        }

        String stat = mwis[0].getStatusFlag();
        // STATUS_FLAG_UNSTART
        // STATUS_FLAG_MOVE_RETRIEVAL_WORKING
        // STATUS_FLAG_MOVE_STORAGE_WAITING
        if (SystemDefine.STATUS_FLAG_UNSTART.equals(stat)
                || SystemDefine.STATUS_FLAG_MOVE_RETRIEVAL_WORKING.equals(stat)
                || SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING.equals(stat))
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }

        // STATUS_FLAG_MOVE_STORAGE_WORKING
        if (SystemDefine.STATUS_FLAG_MOVE_STORAGE_WORKING.equals(stat))
        {
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }

        // STATUS_FLAG_COMPLETION
        // STATUS_FLAG_MOVE_STORAGE_CANCEL
        if (SystemDefine.STATUS_FLAG_COMPLETION.equals(stat)
                || SystemDefine.STATUS_FLAG_MOVE_STORAGE_CANCEL.equals(stat))
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_COMPLETED);
        }
        throw new NotFoundException();
    }

    /**
     * 移動出庫開始チェック処理を行います<br>
     * 移動作業を検索し、出庫開始データの状態をチェックします。<br>
     * なんらかの問題がある場合には例外がスローされます。
     * 
     * @param consignor 荷主コード
     * @param area エリア
     * @param order オーダーNo.
     */
    @Deprecated
    public void checkRetrievalStart(String consignor, String area, String order)
    {
        // FIXME DESIGN PENDING
    }

    /**
     * 補充開始ロック処理を行います。<br>
     * 作業情報がなくても該当作業単位キーの移動作業情報はロックします。
     * 
     * @param settingUnitKey 作業単位キー
     * @return ロックした作業情報
     * @throws ReadWriteException データベースエラー
     * @throws LockTimeOutException ロックタイムアウト時に通知
     */
    public WorkInfo[] lockReplenishStart(String settingUnitKey)
            throws ReadWriteException,
                LockTimeOutException
    {
        WorkInfoHandler mwih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wKey = new WorkInfoSearchKey();

        // select
        // 全ての項目を取得する為、項目の指定は行わない

        // join
        wKey.setJoin(WorkInfo.JOB_NO, "(+)", MoveWorkInfo.WORK_CONN_KEY, "");

        // where
        wKey.setKey(MoveWorkInfo.SETTING_UNIT_KEY, settingUnitKey);

        WorkInfo[] mwis = (WorkInfo[])retryLock(wKey, mwih, 1);

        return mwis;
    }

    /**
     * 補充開始作業のエラー理由をチェックします。<br>
     * 対象データに含まれる作業情報のもっとも小さいものをチェックし、
     * チェック結果をOperatorExceptionにセットしthrowします。<br>
     * このメソッドは、エラー時に呼ばれるため正常終了はありません。
     *
     * @param settingUnitKey 作業単位キー
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 対象移動作業なし
     * @throws OperatorException チェックの結果開始できないとき理由が通知されます
     * @throws NoPrimaryException 対象データが複数存在した場合
     */
    public void checkReplenishStart(String settingUnitKey)
            throws OperatorException,
                NotFoundException,
                ReadWriteException,
                NoPrimaryException
    {
        WorkInfoSearchKey wKey = new WorkInfoSearchKey();

        // select
        wKey.setCollect(WorkInfo.STATUS_FLAG, "MIN", WorkInfo.STATUS_FLAG);

        // join
        wKey.setJoin(WorkInfo.JOB_NO, MoveWorkInfo.WORK_CONN_KEY);

        // where
        wKey.setKey(MoveWorkInfo.SETTING_UNIT_KEY, settingUnitKey);

        // 作業情報取得結果
        WorkInfoHandler handler = new WorkInfoHandler(getConnection());
        WorkInfo workInfo = (WorkInfo)handler.findPrimary(wKey);

        if (workInfo == null)
        {
            throw new NotFoundException();
        }

        String stat = workInfo.getStatusFlag();

        // 未作業の場合
        if (SystemDefine.STATUS_FLAG_UNSTART.equals(stat))
        {
            throw new OperatorException(OperatorException.ERR_NOT_START_EXISTS);
        }

        // 作業中の場合
        if (SystemDefine.STATUS_FLAG_NOWWORKING.equals(stat))
        {
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }

        // 完了の場合
        if (SystemDefine.STATUS_FLAG_COMPLETION.equals(stat))
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_COMPLETED);
        }
        throw new NotFoundException();
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
     * 開始用の更新キーを生成して返します。<br>
     * 更新内容の設定のみを行い、絞り込みキーの指定は行っていません。
     * 
     * @param settingUKey 設定単位キー
     * @param status 移動作業ステータス
     * @param ui ユーザ情報
     * @return MoveWorkInfoAlterKey
     * @throws InvalidDefineException statusが移動作業関連でない
     */
    protected MoveWorkInfoAlterKey createStartAlterKey(String settingUKey, String status, WmsUserInfo ui)
            throws InvalidDefineException
    {
        // check status flag of parameter
        boolean correctStat =
                SystemDefine.STATUS_FLAG_MOVE_STORAGE_WORKING.equals(status)
                        || SystemDefine.STATUS_FLAG_MOVE_RETRIEVAL_WORKING.equals(status);
        if (!correctStat)
        {
            throw new InvalidDefineException();
        }

        // set update values
        MoveWorkInfoAlterKey akey = new MoveWorkInfoAlterKey();
        if (!StringUtil.isBlank(settingUKey))
        {
            akey.updateSettingUnitKey(settingUKey);
        }
        akey.updateStatusFlag(status);
        akey.updateHardwareType(ui.getHardwareType());

        boolean storage = SystemDefine.STATUS_FLAG_MOVE_STORAGE_WORKING.equals(status);
        if (storage)
        {
            // Storage
            akey.updateStorageUserId(ui.getUserId());
            akey.updateStorageTerminalNo(ui.getTerminalNo());
        }
        else
        {
            // Retrieval
            akey.updateRetrievalUserId(ui.getUserId());
            akey.updateRetrievalTerminalNo(ui.getTerminalNo());
        }

        akey.updateLastUpdatePname(getCallerName());

        return akey;
    }

    /**
     * 移動入庫検索キーを生成して返します。
     * 
     * @param consig 荷主コード
     * @param items 商品コード
     * @param jobNo 作業No.
     * @param jobType 作業区分
     * @param status 状態フラグ nullのときは "削除済み以外" として生成します。
     * @return 検索キー
     */
    protected MoveWorkInfoSearchKey createStorageKey(String consig, String[] items, String jobNo, String jobType,
            String status)
    {
        MoveWorkInfoSearchKey key = new MoveWorkInfoSearchKey();

        key.setConsignorCode(consig);
        key.setItemCode(items, true);

        if (!StringUtil.isBlank(jobNo))
        {
            key.setJobNo(jobNo);
        }
        key.setJobType(jobType);
        if (status == null)
        {
            key.setKey(MoveWorkInfo.STATUS_FLAG, MoveWorkInfo.STATUS_FLAG_DELETE, "!=", "", "", true);
        }
        else
        {
            key.setKey(MoveWorkInfo.STATUS_FLAG, status);
        }
        return key;
    }

    /**
     * 移動出庫検索キーを生成して返します。
     * 
     * @param consignor 荷主コード
     * @param area エリア
     * @param orders オーダーNo.
     * @param status 状態フラグ nullのときは "削除済み以外" として生成します。
     * @return 検索キー
     */
    @Deprecated
    protected MoveWorkInfoSearchKey createRetrievalKey(String consignor, String area, String status, String[] orders)
    {
        // FIXME DESIGN PENDING.

        MoveWorkInfoSearchKey key = new MoveWorkInfoSearchKey();

        key.setConsignorCode(consignor);
        key.setRetrievalAreaNo(area);

        if (status == null)
        {
            key.setStatusFlag(MoveWorkInfo.STATUS_FLAG_DELETE, "!=", "", "", true);
        }
        else
        {
            key.setStatusFlag(status);
        }

        key.setJobType(MoveWorkInfo.JOB_TYPE_MOVEMENT);
        return key;
    }

    /**
     * WMSSequenceHandlerを返します。生成されていないときは生成します。
     * @return WMSSequenceHandler
     */
    protected WMSSequenceHandler getSeqHandler()
    {
        if (null == _seqHandler)
        {
            _seqHandler = new WMSSequenceHandler(getConnection());
        }
        return _seqHandler;
    }

    /**
     * WarenaviSystemControllerを返します。生成されていないときは生成します。
     * @return WarenaviSystemController
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException システム定義異常
     */
    protected WarenaviSystemController getWmsSysCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _wmsSysCtlr)
        {
            _wmsSysCtlr = new WarenaviSystemController(getConnection(), getCaller());
        }
        return _wmsSysCtlr;
    }

    /**
     * MoveResultControllerを返します。生成されていないときは生成します。
     * @return MoveResultController
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected MoveResultController getMoveResultCtlr()
            throws ReadWriteException
    {
        if (null == _moveResultCtlr)
        {
            _moveResultCtlr = new MoveResultController(getConnection(), getCaller());
        }
        return _moveResultCtlr;
    }

    /**
     * キャンセル処理に使用されるAlterkeyを生成して返します。
     * 
     * @return akey alterkey for cancel.
     */
    protected MoveWorkInfoAlterKey createCancelKey()
    {
        MoveWorkInfoAlterKey akey = new MoveWorkInfoAlterKey();
        akey.updateStatusFlag(SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING);
        akey.updateLastUpdatePname(getCallerName());
        akey.updateStorageUserId("");
        akey.updateStorageTerminalNo("");
        akey.updateLastUpdatePname(getCallerName());

        return akey;
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
        return "$Id: MoveWorkInfoController.java 5020 2009-09-17 10:25:05Z kishimoto $";
    }
}
