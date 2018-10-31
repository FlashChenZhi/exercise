// $Id: AbstractTcOperator.java 1660 2008-12-02 04:43:21Z rnakai $
package jp.co.daifuku.wms.crossdock.operator;

import java.math.BigDecimal;
import java.sql.Connection;

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
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.ReceivingWorkInfoController;
import jp.co.daifuku.wms.base.controller.ShipWorkInfoController;
import jp.co.daifuku.wms.base.controller.SortWorkInfoController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.ShipPlan;
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;
import jp.co.daifuku.wms.base.entity.SortWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.db.BasicDatabaseFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * TC作業を行うための抽象オペレータクラスです<BR>
 *
 * @version $Revision: 1660 $, $Date: 2008-12-02 13:43:21 +0900 (火, 02 12 2008) $
 * @author  Last commit: $Author: rnakai $
 */
public abstract class AbstractTcOperator
        extends AbstractOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;


    private CrossDockPlanHandler _xdHandler = null;

    private CrossDockPlanAlterKey _xdAlterKey = null;

    private CrossDockPlanSearchKey _xdSKey = null;

    private SortWorkInfoHandler _sortWiHandler = null;

    private SortWorkInfoSearchKey _sortSkey = null;

    private ShipPlanHandler _shipPHandler = null;

    private ShipPlanAlterKey _shipPAlterKey = null;

    private ShipPlanSearchKey _shipPSKey = null;

    private ShipWorkInfoHandler _shipWiHandler = null;

    private ShipWorkInfoSearchKey _shipWiSKey = null;

    private ReceivingWorkInfoController _receiveController = null;

    private SortWorkInfoController _sortController = null;

    private ShipWorkInfoController _shipController = null;

    private WarenaviSystemController _wmsController = null;

    private WMSSequenceHandler _seqHandler = null;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    /**
     * 操作中のリストセル行No.
     */
    private int _rowNo;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して、インスタンスを生成します。<BR>
     *
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     */
    public AbstractTcOperator(Connection conn, Class caller) throws ReadWriteException,
            ScheduleException
    {
        super(conn, caller);

        _xdHandler = new CrossDockPlanHandler(getConnection());
        _xdAlterKey = new CrossDockPlanAlterKey();
        _xdSKey = new CrossDockPlanSearchKey();

        _sortWiHandler = new SortWorkInfoHandler(getConnection());
        _sortSkey = new SortWorkInfoSearchKey();

        _shipPHandler = new ShipPlanHandler(getConnection());
        _shipPAlterKey = new ShipPlanAlterKey();
        _shipPSKey = new ShipPlanSearchKey();
        _shipWiHandler = new ShipWorkInfoHandler(getConnection());
        _shipWiSKey = new ShipWorkInfoSearchKey();

        _receiveController = new ReceivingWorkInfoController(getConnection(), getCaller());
        _sortController = new SortWorkInfoController(getConnection(), getCaller());
        _shipController = new ShipWorkInfoController(getConnection(), getCaller());
        _wmsController = new WarenaviSystemController(getConnection(), getCaller());

        _seqHandler = new WMSSequenceHandler(getConnection());
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 操作中のリストセル行No.を返します。
     * @return 操作中のリストセル行No.を返します。
     */
    public int getRowNo()
    {
        return _rowNo;
    }

    /**
     * 操作中のリストセル行No.を設定します。
     * @param rowNo 操作中のリストセル行No.
     */
    public void setRowNo(int rowNo)
    {
        _rowNo = rowNo;
    }

    /**
     * TC予定情報状態取得<BR>
     * パラメータのクロスドック連携キーにて入荷作業情報、TC予定情報の検索を行い、セットすべき予定情報の状態を返します。
     * 
     * @param crossDockUkey クロスドック連携キー
     * @return 状態フラグ
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws NotFoundException 該当データなし
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在
     */
    public String getCrossDockPlanStatus(String crossDockUkey)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException
    {
        // 入荷作業情報、TC予定情報を検索する
        _xdSKey.clear();
        // 取得項目
        _xdSKey.setPlanQtyCollect("SUM");
        _xdSKey.setResultQtyCollect("SUM");
        _xdSKey.setShortageQtyCollect("SUM");
        _xdSKey.setCollect(ReceivingWorkInfo.STATUS_FLAG, "MAX", null);
        _xdSKey.setPlanUkeyCollect("MAX");

        // 結合
        _xdSKey.setJoin(CrossDockPlan.CROSS_DOCK_UKEY, ReceivingWorkInfo.CROSS_DOCK_UKEY);

        // 検索条件
        _xdSKey.setCrossDockUkey(crossDockUkey);
        _xdSKey.setKey(ReceivingWorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);

        // データ検索
        CrossDockPlan plan = (CrossDockPlan)_xdHandler.findPrimary(_xdSKey);

        // データが存在しなかった場合
        if (plan == null)
        {
            throw new NotFoundException();
        }
        else if (StringUtil.isBlank(plan.getPlanUkey()))
        {
            throw new NotFoundException();
        }

        // 仕分作業情報を検索する
        _sortSkey.clear();
        // 検索条件
        _sortSkey.setCrossDockUkey(crossDockUkey);

        // cross_dock_ukeyに紐尽く仕分作業情報の件数を取得
        int totalCount = _sortWiHandler.count(_sortSkey);

        // 検索条件の追加
        _sortSkey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);

        // cross_dock_ukeyに紐尽く完了済みの仕分作業情報の件数を取得
        int completeCount = _sortWiHandler.count(_sortSkey);

        // 仕分作業情報が全て完了になっているか判定
        boolean isComplete = ((totalCount > 0) && (totalCount == completeCount));

        // 戻り値(状態フラグ)
        String status = null;

        // MAX（入荷作業情報．状態フラグ）が”0”（未作業）の場合
        if (SystemDefine.STATUS_FLAG_UNSTART.equals(plan.getStatusFlag()))
        {
            status = SystemDefine.STATUS_FLAG_UNSTART;
        }
        // SUM（TC予定情報．予定数）=SUM（TC予定情報．実績数）+SUM（TC予定情報．欠品数）の場合かつ
        // 紐尽く仕分作業情報の状態フラグが”4”（完了）の場合
        else if (plan.getPlanQty() == plan.getResultQty() + plan.getShortageQty() && isComplete)
        {
            status = SystemDefine.STATUS_FLAG_COMPLETION;
        }
        // それ以外
        else
        {
            status = SystemDefine.STATUS_FLAG_NOWWORKING;
        }

        return status;
    }

    /**
     * 出荷予定情報状態取得<BR>
     * パラメータのクロスドック連携キーにて出荷作業情報の検索を行い、セットすべき予定情報の状態を返します。<BR>
     * 戻り値の要素0には出荷検品状態、要素1にはバース登録状態を返します。
     * 
     * @param crossDockUkey クロスドック連携キー
     * @return 出荷予定情報状態配列 要素0には出荷検品状態、要素1にはバース登録状態
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws NotFoundException 該当データなし
     */
    public String[] getShipPlanStatus(String crossDockUkey)
            throws ReadWriteException,
                NotFoundException
    {
        // 出荷予定情報、出荷作業情報を検索する。
        _shipWiSKey.clear();

        // 取得項目
        _shipWiSKey.setWorkStatusFlagCollect();
        _shipWiSKey.setBerthStatusFlagCollect();
        _shipWiSKey.setCollect(ShipPlan.PLAN_QTY, "SUM", null);
        _shipWiSKey.setCollect(ShipPlan.DISTRIBUTED_QTY, "SUM", null);
        _shipWiSKey.setJobNoCollect("MAX");

        // 結合
        _shipWiSKey.setJoin(ShipWorkInfo.CROSS_DOCK_UKEY, "", ShipPlan.CROSS_DOCK_UKEY, "(+)");

        // 検索条件
        _shipWiSKey.setCrossDockUkey(crossDockUkey);

        _shipWiSKey.setWorkStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
        _shipWiSKey.setWorkStatusFlag("", "=", "", ")", true);

        _shipWiSKey.setBerthStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
        _shipWiSKey.setBerthStatusFlag("", "=", "", ")", true);

        // 集約条件
        _shipWiSKey.setWorkStatusFlagGroup();
        _shipWiSKey.setBerthStatusFlagGroup();

        // 検索処理
        ShipWorkInfo[] infos = (ShipWorkInfo[])_shipWiHandler.find(_shipWiSKey);

        // データが存在しなかった場合
        if (ArrayUtil.isEmpty(infos))
        {
            throw new NotFoundException();
        }
        else if (StringUtil.isBlank(infos[0].getJobNo()))
        {
            // 主キーの値が取得できていない場合は、データ無しの可能性があるためエラーを通知
            throw new NotFoundException();
        }

        // 戻り値(状態フラグ)
        String[] status = new String[2];

        // フラグ
        boolean unstart = false;
        boolean complte = false;

        // ::: 出荷検品状態フラグのチェック
        // 出荷検品状態フラグが全て”0”（未作業）またはNULLの場合 → STATUS_FLAG_UNSTART(未作業)
        // 出荷検品状態フラグが全て”4”（完了）、かつ予定数=配分済数の場合 → STATUS_FLAG_COMPLETION(完了)
        // それ以外 → STATUS_FLAG_NOWWORKING(作業中)
        for (ShipWorkInfo info : infos)
        {
            // 出荷検品状態フラグが”0”（未作業）またはNULLの場合
            if (SystemDefine.STATUS_FLAG_UNSTART.equals(info.getWorkStatusFlag()) || info.getWorkStatusFlag() == null)
            {
                if (!complte)
                {
                    status[0] = SystemDefine.STATUS_FLAG_UNSTART;
                    unstart = true;
                    continue;
                }
            }

            // 出荷検品状態フラグが”4”（完了）、かつ予定数=配分済数の場合
            if (SystemDefine.STATUS_FLAG_COMPLETION.equals(info.getWorkStatusFlag())
                    && info.getPlanQty() == info.getBigDecimal(ShipPlan.DISTRIBUTED_QTY, new BigDecimal(0)).intValue())
            {
                if (!unstart)
                {
                    status[0] = SystemDefine.STATUS_FLAG_COMPLETION;
                    complte = true;
                    continue;
                }
            }

            // それ以外
            status[0] = SystemDefine.STATUS_FLAG_NOWWORKING;
            break;
        }

        // フラグを初期化
        unstart = false;
        complte = false;

        // ::: バース登録状態フラグのチェック
        // バース登録状態フラグが全て”0”（未作業）またはNULLの場合 → STATUS_FLAG_UNSTART(未作業)
        // バース登録状態フラグが全て”4”（完了）、かつ予定数=配分済数の場合 → STATUS_FLAG_COMPLETION(完了)
        // それ以外 → STATUS_FLAG_NOWWORKING(作業中)
        for (ShipWorkInfo info : infos)
        {
            // 出荷検品状態フラグが”0”（未作業）またはNULLの場合
            if (SystemDefine.STATUS_FLAG_UNSTART.equals(info.getBerthStatusFlag()) || info.getBerthStatusFlag() == null)
            {
                if (!complte)
                {
                    status[1] = SystemDefine.STATUS_FLAG_UNSTART;
                    unstart = true;
                    continue;
                }
            }

            // 出荷検品状態フラグが”4”（完了）、かつ予定数=配分済数の場合
            if (SystemDefine.STATUS_FLAG_COMPLETION.equals(info.getBerthStatusFlag())
                    && info.getPlanQty() == info.getBigDecimal(ShipPlan.DISTRIBUTED_QTY, new BigDecimal(0)).intValue())
            {
                if (!unstart)
                {
                    status[1] = SystemDefine.STATUS_FLAG_COMPLETION;
                    complte = true;
                    continue;
                }
            }

            // それ以外
            status[1] = SystemDefine.STATUS_FLAG_NOWWORKING;
            break;
        }

        return status;
    }

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**
     * seqHandlerを返します。
     * @return seqHandler
     */
    protected WMSSequenceHandler getSeqHandler()
    {
        return _seqHandler;
    }

    /**
     * 入荷完了<BR>
     * パラメータの作業No.に該当するデータに対して、入荷作業情報の完了処理を行い、入荷実績送信情報登録処理を行います。<BR>
     * 入荷完了分の仕分作業情報を登録し、予定情報の入荷実績数、入荷欠品数、欠品数を更新します。<BR>
     * 欠品がある場合は、欠品数分の次工程作業を作成・完了処理を行います。<BR>
     * ・入荷作業情報完了メソッド(ReceivingWorkInfoController)<BR>
     * ・仕分作業情報登録処理<BR>
     * ・予定情報更新処理<BR>
     * ・仕分完了メソッド（欠品分）<BR>
     * 
     * @param src 完了対象データ<BR>以下の項目を参照しています。<UL><LI>作業No</LI><LI>予定一意キー</LI><LI>クロスドック連携キー</LI></UL>
     * @param result 完了情報<BR>以下の項目を参照しています。セットされていない項目は更新されません。<UL><LI>予定数</LI><LI>実績数</LI><LI>欠品数</LI><LI>実績ロットNo.</LI><LI>作業日</LI><LI>作業秒数</LI><LI>ハードウェア区分</UL>
     * @param ui WMSユーザ情報<BR>以下の項目を参照しています。<UL><LI>ユーザID</LI><LI>端末No.</LI></UL>
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在していた場合にスローされます。
     * @throws OperatorException 予期しないエラーを検出した場合にスローされます。
     * @throws DataExistsException データが既に登録されていた場合にスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     */
    protected void completeReceiving(ReceivingWorkInfo src, ReceivingWorkInfo result, WmsUserInfo ui)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        try
        {
            // ::: 入荷作業情報完了処理
            _receiveController.completeWorkInfo(src, result, ui);

            // ::: 仕分作業情報登録処理
            // 実績数がない場合は、欠品数を予定数として作業情報を作成する。
            int planQty = result.getResultQty() > 0 ? result.getResultQty()
                                                   : result.getShortageQty();

            SortWorkInfo srcInfo = null;

            if ((result.getResultQty() != 0 && SystemDefine.HARDWARE_TYPE_RFT.equals(ui.getHardwareType()))
                    || StringUtil.isBlank(ui.getHardwareType()))
            {
                srcInfo = insertSortWorkInfo(src.getCrossDockUkey(), planQty, result);
            }


            // ::: TC予定情報を更新
            _xdAlterKey.clear();
            // 更新内容
            if (!StringUtil.isBlank(result.getStatusFlag()))
            {
                _xdAlterKey.updateStatusFlag(result.getStatusFlag());
            }
            _xdAlterKey.setUpdateWithColumn(CrossDockPlan.RECEIVE_RESULT_QTY, CrossDockPlan.RECEIVE_RESULT_QTY,
                    new BigDecimal(result.getResultQty()));
            _xdAlterKey.setUpdateWithColumn(CrossDockPlan.RECEIVE_SHORTAGE_QTY, CrossDockPlan.RECEIVE_SHORTAGE_QTY,
                    new BigDecimal(result.getShortageQty()));
            _xdAlterKey.setUpdateWithColumn(CrossDockPlan.SHORTAGE_QTY, CrossDockPlan.SHORTAGE_QTY, new BigDecimal(
                    result.getShortageQty()));
            _xdAlterKey.updateWorkDay(result.getWorkDay());
            _xdAlterKey.updateLastUpdatePname(getCallerName());

            // 更新条件
            _xdAlterKey.setPlanUkey(src.getPlanUkey());

            // 更新
            _xdHandler.modify(_xdAlterKey);

            // パラメータの完了情報の欠品数>0の場合 欠品数分の次工程作業を作成・完了処理を行う。
            if (result.getShortageQty() > 0)
            {
                // 仕分作業情報完了処理

                // 完了情報の作成
                SortWorkInfo completeWork = new SortWorkInfo();
                completeWork.setResultQty(0);
                completeWork.setShortageQty(result.getShortageQty());
                completeWork.setResultLotNo(src.getResultLotNo());
                completeWork.setWorkDay(result.getWorkDay());
                completeWork.setWorkSecond(0);
                completeWork.setHardwareType(result.getHardwareType());

                // 仕分完了処理
                // 欠品数は入荷完了処理時に計算済みなので、欠品処理なしの仕分完了処理を呼び出す。
                this.completeSort(srcInfo, completeWork, ui, false);
            }
        }
        catch (NotFoundException ne)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 仕分完了<BR>
     * パラメータの作業No.に該当するデータに対して、仕分作業情報の完了処理を行い、
     * 仕分実績送信情報登録処理を行います。<BR>
     * 仕分完了分の出荷作業情報を登録し、予定情報の仕分実績数、実績数、仕分欠品数、欠品数を更新します。
     * 
     * @param src 完了対象データ<BR>以下の項目を参照しています。<UL><LI>作業No</LI><LI>予定一意キー</LI><LI>クロスドック連携キー</LI></UL>
     * @param result 完了情報<BR>以下の項目を参照しています。セットされていない項目は更新されません。<UL><LI>実績数</LI><LI>欠品数</LI><LI>実績ロットNo.</LI><LI>作業日</LI><LI>作業秒数</LI></UL>
     * @param ui WMSユーザ情報<BR>以下の項目を参照しています。<UL><LI>ユーザID</LI><LI>端末No.</LI></UL>
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在していた場合にスローされます。
     * @throws OperatorException 予期しないエラーを検出した場合にスローされます。
     * @throws DataExistsException データが既に登録されていた場合にスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     */
    protected void completeSort(SortWorkInfo src, SortWorkInfo result, WmsUserInfo ui)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        completeSort(src, result, ui, true);
    }

    /**
     * 仕分完了(前工程からの完了の場合、こちらを使用します)。<BR>
     * パラメータの作業No.に該当するデータに対して、仕分作業情報の完了処理を行い、
     * 仕分実績送信情報登録処理を行います。<BR>
     * 仕分完了分の出荷作業情報を登録し、予定情報の仕分実績数、実績数、仕分欠品数、欠品数を更新します。
     * 欠品数＞０の場合は出荷作業情報、出荷実績送信情報を欠品完了で登録します。<BR>
     * isSortShortageがtrueの場合は、仕分欠品出荷予定情報、TC予定情報の状態フラグ、実績数、欠品数、配分済数を更新します。
     * 
     * @param src 完了対象データ<BR>以下の項目を参照しています。<UL><LI>作業No</LI><LI>予定一意キー</LI><LI>クロスドック連携キー</LI></UL>
     * @param result 完了情報<BR>以下の項目を参照しています。セットされていない項目は更新されません。<UL><LI>実績数</LI><LI>欠品数</LI><LI>実績ロットNo.</LI><LI>作業日</LI><LI>作業秒数</LI><LI>ハードウェア区分</UL>
     * @param ui WMSユーザ情報<BR>以下の項目を参照しています。<UL><LI>ユーザID</LI><LI>端末No.</LI></UL>
     * @param isSortShortage 欠品区分(前工程からの欠品完了の場合false、仕分作業での欠品完了の場合はtrue)
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在していた場合にスローされます。
     * @throws OperatorException 予期しないエラーを検出した場合にスローされます。
     * @throws DataExistsException データが既に登録されていた場合にスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     */
    protected void completeSort(SortWorkInfo src, SortWorkInfo result, WmsUserInfo ui, boolean isSortShortage)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        try
        {
            // SortWorkInfoController#completeWorkInfoを呼び出し、仕分作業情報完了処理を行なう
            _sortController.completeWorkInfo(src, result, ui);

            // TC予定情報を実績数を更新する(getCrossDockPlanStatusで数量を反映させておく必要があるため、事前にUPDATEを行う)
            _xdAlterKey.clear();

            // パラメータの実績数>0の場合
            if (result.getResultQty() > 0)
            {
                BigDecimal resultQty = new BigDecimal(result.getResultQty());

                // TC予定情報．仕分実績数 = パラメータの完了情報の実績数（setUpdateWithColumnを使用し加算）  
                _xdAlterKey.setUpdateWithColumn(CrossDockPlan.SORT_RESULT_QTY, CrossDockPlan.SORT_RESULT_QTY, resultQty);

                // TC予定情報．実績数 = パラメータの完了情報の実績数（setUpdateWithColumnを使用し加算）
                _xdAlterKey.setUpdateWithColumn(CrossDockPlan.RESULT_QTY, CrossDockPlan.RESULT_QTY, resultQty);
            }

            // パラメータの欠品数>0かつisSortShortage=trueの場合
            if (result.getShortageQty() > 0 && isSortShortage)
            {
                BigDecimal shortageQty = new BigDecimal(result.getShortageQty());

                // TC予定情報．仕分欠品数 = パラメータの完了情報の欠品数（setUpdateWithColumnを使用し、加算）
                _xdAlterKey.setUpdateWithColumn(CrossDockPlan.SORT_SHORTAGE_QTY, CrossDockPlan.SORT_SHORTAGE_QTY,
                        shortageQty);

                // TC予定情報．欠品数 = パラメータの完了情報の欠品数（setUpdateWithColumnを使用し、加算）
                _xdAlterKey.setUpdateWithColumn(CrossDockPlan.SHORTAGE_QTY, CrossDockPlan.SHORTAGE_QTY, shortageQty);
            }

            // TC予定情報．最終更新処理名
            _xdAlterKey.updateLastUpdatePname(getCallerName());

            // TC予定情報．作業日
            _xdAlterKey.updateWorkDay(_wmsController.getWorkDay());

            // 更新条件 TC予定情報．予定一意キー
            _xdAlterKey.setPlanUkey(src.getPlanUkey());

            // 更新
            _xdHandler.modify(_xdAlterKey);

            // TC予定情報の状態フラグを取得する
            String xdPlanStatus = getCrossDockPlanStatus(src.getCrossDockUkey());

            // 状態フラグが”4”（完了）の場合TC予定情報を更新する
            if (SystemDefine.STATUS_FLAG_COMPLETION.equals(xdPlanStatus))
            {
                // 更新項目の初期化
                _xdAlterKey.clear();

                // 状態フラグ”4”（完了）
                _xdAlterKey.updateStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);

                // TC予定情報．最終更新処理名
                _xdAlterKey.updateLastUpdatePname(getCallerName());

                // 更新条件 パラメータの完了対象データの予定一意キー
                _xdAlterKey.setPlanUkey(src.getPlanUkey());

                // 更新
                _xdHandler.modify(_xdAlterKey);
            }

            // insertShipWorkInfoメソッドを呼び出し、出荷作業情報登録処理を行なう
            // 実績数がない場合は、欠品数を予定数として作業情報を作成する。
            int planQty = result.getResultQty() > 0 ? result.getResultQty()
                                                   : result.getShortageQty();
            ShipWorkInfo insSwi = insertShipWorkInfo(src.getCrossDockUkey(), planQty, result);

            // 出荷予定情報の実績数を更新する(completeShipの完了処理で数量を反映させておく必要があるため、事前にUPDATEを行う)
            _shipPAlterKey.clear();

            // 出荷予定情報．配分済数 = パラメータの完了情報の実績数+欠品数（setUpdateWithColumnを使用して加算）
            BigDecimal distributedQty = new BigDecimal(result.getResultQty() + result.getShortageQty());
            _shipPAlterKey.setUpdateWithColumn(ShipPlan.DISTRIBUTED_QTY, ShipPlan.DISTRIBUTED_QTY, distributedQty);

            // 出荷予定情報．最終更新処理名 = インスタンス変数の呼出元クラスよりプログラム名を取得
            _shipPAlterKey.updateLastUpdatePname(getCallerName());

            // 更新条件 パラメータの完了対象データのクロスドック連携キー
            _shipPAlterKey.setCrossDockUkey(src.getCrossDockUkey());

            // 更新
            _shipPHandler.modify(_shipPAlterKey);

            // パラメータの完了情報の欠品数>0の場合、 欠品数分の次工程作業を作成・完了処理を行う
            if (result.getShortageQty() > 0)
            {

                if (result.getShortageQty() != src.getPlanQty())
                {
                    insSwi = insertShipWorkInfo(src.getCrossDockUkey(), result.getShortageQty(), result);
                }
                // 完了情報用出荷作業情報エンティティを新規に作成し、編集を行なう
                ShipWorkInfo swi = new ShipWorkInfo();

                // 予定数
                swi.setPlanQty(insSwi.getPlanQty());

                // 実績数 = 0
                swi.setResultQty(0);

                // 欠品数 = パラメータの完了情報の同項目
                swi.setShortageQty(result.getShortageQty());

                // 実績ロットNo. = 1の完了対象出荷作業情報の予定ロットNo.
                swi.setResultLotNo(insSwi.getPlanLotNo());

                // 作業日 = パラメータの完了情報の同項目
                swi.setWorkDay(result.getWorkDay());

                // 作業秒数 = 0
                swi.setShipWorkSecond(0);

                // ハードウェア区分
                swi.setHardwareType(result.getHardwareType());

                String userId = ui.getUserId();
                String termNo = ui.getTerminalNo();

                // completeShipを呼び出し、出荷作業情報完了処理を行なう
                completeShip(insSwi, swi, ui);

                // completeShipLoadを呼び出し、出荷作業情報完了処理を行なう
                insSwi.setResultBerth("");
                insSwi.setBerthStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
                insSwi.setWorkStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);

                completeShipLoad(insSwi, swi, ui);
                ui.setUserId(userId);
                ui.setTerminalNo(termNo);
            }

        }
        catch (NotFoundException ne)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 出荷積込作業完了<BR>
     * パラメータの設定単位キーに該当するデータに対して出荷積込完了処理を行います。<BR>
     * 出荷予定情報のバース状態フラグを更新します。
     *
     * @param src 完了対象データ<BR>以下の項目を参照しています。<UL><LI>作業No</LI><LI>予定一意キー</LI><LI>クロスドック連携キー</LI><LI>出荷検品状態フラグ</LI><LI>バース登録状態フラグ</LI></UL>
     * @param result 完了情報<BR>以下の項目を参照しています。セットされていない項目は更新されません。<UL><LI>結果バース</LI><LI>作業日</LI><LI>作業秒数</LI></UL>
     * @param ui WMSユーザ情報<BR>以下の項目を参照しています。<UL><LI>ユーザID</LI><LI>端末No.</LI></UL>
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在していた場合にスローされます。
     * @throws OperatorException 予期しないエラーを検出した場合にスローされます。
     * @throws DataExistsException データが既に登録されていた場合にスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     */
    protected void completeShipLoad(ShipWorkInfo src, ShipWorkInfo result, WmsUserInfo ui)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        try
        {
            // ShipWorkInfoController#completeLoadを呼び出し、出荷作業情報完了処理を行なう
            _shipController.completeLoad(src, result, ui);

            // 出荷予定情報を更新する
            _shipPAlterKey.clear();

            // 出荷予定情報．バース登録状態フラグ = getShipPlanStatusにて出荷予定情報を検索した戻り値の状態フラグ[1]
            String[] workStatusFlag = getShipPlanStatus(src.getCrossDockUkey());
            _shipPAlterKey.updateBerthStatusFlag(workStatusFlag[1]);

            // 出荷予定情報．作業日 ※Null以外の場合、更新対象
            if (!StringUtil.isBlank(result.getWorkDay()))
            {
                _shipPAlterKey.updateWorkDay(result.getWorkDay());
            }

            // 更新条件 パラメータの完了対象データの予定一意キー
            _shipPAlterKey.setPlanUkey(src.getPlanUkey());

            _shipPAlterKey.updateLastUpdatePname(getCallerName());

            // 更新
            _shipPHandler.modify(_shipPAlterKey);
        }
        catch (NotFoundException ne)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 出荷検品完了<BR>
     * パラメータの作業No.に該当するデータに対して、出荷作業情報の完了処理を行い、出荷実績送信情報登録処理を行います。<BR>
     * 出荷完了分の出荷作業情報を登録し、予定情報の状態フラグ、実績数、欠品数を更新します。<BR>
     * 
     * @param src 完了対象データ<BR>以下の項目を参照しています。<UL><LI>作業No</LI><LI>予定一意キー</LI><LI>クロスドック連携キー</LI><LI>バース登録状態フラグ</LI></UL>
     * @param result 完了情報<BR>以下の項目を参照しています。セットされていない項目は更新されません。<UL><LI>予定数</LI><LI>実績数</LI><LI>欠品数</LI><LI>実績ロットNo.</LI><LI>作業日</LI><LI>作業秒数</LI><LI>ハードウェア区分</LI></UL>
     * @param ui WMSユーザ情報<BR>以下の項目を参照しています。<UL><LI>ユーザID</LI><LI>端末No.</LI></UL>
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在していた場合にスローされます。
     * @throws OperatorException 予期しないエラーを検出した場合にスローされます。
     * @throws DataExistsException データが既に登録されていた場合にスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     */
    protected void completeShip(ShipWorkInfo src, ShipWorkInfo result, WmsUserInfo ui)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        try
        {
            // ShipWorkInfoController#completeWorkInfoを呼び出し、出荷作業情報完了処理を行なう
            _shipController.completeWorkInfo(src, result, ui);

            // 出荷予定情報を更新する
            _shipPAlterKey.clear();

            // 出荷予定情報．出荷検品状態フラグ = getShipPlanStatusにて出荷予定情報状態を検索した戻り値の状態フラグ[0]
            String[] workStatusFlag = getShipPlanStatus(src.getCrossDockUkey());
            _shipPAlterKey.updateWorkStatusFlag(workStatusFlag[0]);

            // 出荷予定情報．実績数 = パラメータの完了情報の実績数（setUpdateWithColumnを使用して加算）
            BigDecimal resultQty = new BigDecimal(result.getResultQty());
            _shipPAlterKey.setUpdateWithColumn(ShipPlan.RESULT_QTY, ShipPlan.RESULT_QTY, resultQty);

            // 出荷予定情報．欠品数 = パラメータの完了情報の欠品数（setUpdateWithColumnを使用して加算）
            BigDecimal shortageQty = new BigDecimal(result.getShortageQty());
            _shipPAlterKey.setUpdateWithColumn(ShipPlan.SHORTAGE_QTY, ShipPlan.SHORTAGE_QTY, shortageQty);

            // 更新条件 パラメータの完了対象データのクロスドック連携キー
            _shipPAlterKey.setCrossDockUkey(src.getCrossDockUkey());
            final String workDay = _wmsController.getWorkDay();
            _shipPAlterKey.updateWorkDay(workDay);

            _shipPAlterKey.updateLastUpdatePname(getCallerName());

            // 更新
            _shipPHandler.modify(_shipPAlterKey);
        }
        catch (NotFoundException ne)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 仕分作業情報登録<BR>
     * TC予定情報を検索し、仕分作業情報を未作業で登録します。<BR>
     * 登録した作業情報を戻り値として返します。
     * 
     * @param crossDockUkey クロスドック連携キー
     * @param planQty 予定数
     * @return 仕分作業情報エンティティ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在していた場合にスローされます。
     * @throws DataExistsException データが既に登録されていた場合にスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった場合にスローされます。
     */
    protected SortWorkInfo insertSortWorkInfo(String crossDockUkey, int planQty, ReceivingWorkInfo result)
            throws ReadWriteException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // TC予定情報を検索する（findPrimary）      
        _xdSKey.clear();

        // 全ての項目を取得する
        _xdSKey.setCollect(new FieldName(CrossDockPlan.STORE_NAME, FieldName.ALL_FIELDS));

        // 取得条件 パラメータのクロスドック連携キー
        _xdSKey.setCrossDockUkey(crossDockUkey);

        // TC予定情報を取得する
        CrossDockPlan xdPlan = (CrossDockPlan)_xdHandler.findPrimary(_xdSKey);

        // データが存在しなかった場合はScheduleExceptionを通知する
        if (xdPlan == null)
        {
            throw new ScheduleException();
        }

        // 仕分作業情報エンティティオブジェクトを作成し、編集を行う
        SortWorkInfo sort = new SortWorkInfo();

        // 作業No. = 採番
        String jobNoSequence = _seqHandler.nextSortJobNo();
        sort.setJobNo(jobNoSequence);

        // 設定単位キー = 空白
        sort.setSettingUnitKey("");

        // 集約作業No. = 空白
        sort.setCollectJobNo("");

        // クロスドック連携キー = 検索した同項目
        sort.setCrossDockUkey(xdPlan.getCrossDockUkey());

        // 作業区分 = ”04”（仕分）
        sort.setJobType(SystemDefine.JOB_TYPE_SORTING);

        // 状態フラグ = ”0”（未作業）
        sort.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // ハードウェア区分 = ”0”（未作業）
        sort.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);

        // 予定一意キー = 検索した同項目
        sort.setPlanUkey(xdPlan.getPlanUkey());

        // 予定日 = 検索した同項目
        sort.setPlanDay(xdPlan.getPlanDay());

        // バッチNo. = 検索した同項目
        sort.setBatchNo(xdPlan.getBatchNo());

        // 荷主コード = 検索した同項目
        sort.setConsignorCode(xdPlan.getConsignorCode());

        // 出荷先コード = 検索した同項目
        sort.setCustomerCode(xdPlan.getCustomerCode());

        // 出荷伝票No. = 検索した同項目
        sort.setShipTicketNo(xdPlan.getShipTicketNo());

        // 出荷伝票行 = 検索した同項目
        sort.setShipLineNo(xdPlan.getShipLineNo());

        // 仕分場所 = 検索した同項目
        sort.setSortingPlace(xdPlan.getSortingPlace());

        // 商品コード = 検索した同項目
        sort.setItemCode(xdPlan.getItemCode());

        // 予定ロットNo. = 検索した同項目
        sort.setPlanLotNo(result.getResultLotNo());

        // 予定数 = パラメータの予定数
        sort.setPlanQty(planQty);

        // 実績数 = 0
        sort.setResultQty(0);

        // 欠品数 = 0
        sort.setShortageQty(0);

        // 実績ロットNo = 空白
        sort.setResultLotNo("");

        // 作業日 = 空白
        sort.setWorkDay("");

        // ユーザID = 空白
        sort.setUserId("");

        // 端末No、RFTNo = 空白
        sort.setTerminalNo("");

        // 作業秒数 = 0
        sort.setWorkSecond(0);

        // 登録処理名 = インスタンス変数のクラスよりプログラム名を取得
        sort.setRegistPname(getCallerName());

        // 最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
        sort.setLastUpdatePname(getCallerName());

        // 仕分作業情報の登録を行う   
        _sortWiHandler.create(sort);

        return sort;
    }

    /**
     * 出荷作業情報登録<BR>
     * 出荷予定情報を検索し、出荷作業情報を未作業で登録します。<BR>
     * 登録した作業情報を戻り値として返します。
     * 
     * @param crossDockUkey クロスドック連携キー
     * @param planQty 予定数
     * @return 仕分作業情報エンティティ
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在していた場合にスローされます。
     * @throws DataExistsException データが既に登録されていた場合にスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった場合にスローされます。
     */
    protected ShipWorkInfo insertShipWorkInfo(String crossDockUkey, int planQty, SortWorkInfo result)
            throws ReadWriteException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // 出荷予定情報を検索する（findPrimary)
        _shipPSKey.clear();

        // 全ての項目を取得する
        _shipPSKey.setCollect(new FieldName(ShipPlan.STORE_NAME, FieldName.ALL_FIELDS));

        // 取得条件 パラメータのクロスドック連携キー
        _shipPSKey.setCrossDockUkey(crossDockUkey);

        // 出荷予定情報を取得する
        ShipPlan spPlan = (ShipPlan)_shipPHandler.findPrimary(_shipPSKey);

        // データが存在しなかった場合はScheduleExceptionを通知する
        if (spPlan == null)
        {
            throw new ScheduleException();
        }

        // 出荷作業情報エンティティオブジェクトを作成し、編集を行う
        ShipWorkInfo shipWi = new ShipWorkInfo();

        // 作業No. = 採番
        String jobNoSequence = _seqHandler.nextShipJobNo();
        shipWi.setJobNo(jobNoSequence);

        // 設定単位キー = 空白
        shipWi.setSettingUnitKey("");

        // 集約作業No. = 空白
        shipWi.setCollectJobNo("");

        // クロスドック連携キー = 検索した同項目
        shipWi.setCrossDockUkey(spPlan.getCrossDockUkey());

        // 作業区分 = ”05”（出荷）
        shipWi.setJobType(SystemDefine.JOB_TYPE_SHIPPING);

        // 出荷検品状態フラグ = ”0”（未作業）
        shipWi.setWorkStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // バース登録状態フラグ = ”0”（未作業）
        shipWi.setBerthStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // ハードウェア区分 = ”0”（未作業）
        shipWi.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);

        // 予定一意キー = 検索した同項目
        shipWi.setPlanUkey(spPlan.getPlanUkey());

        // TC/DC区分 = 検索した同項目
        shipWi.setTcdcFlag(spPlan.getTcdcFlag());

        // 予定日 = 検索した同項目
        shipWi.setPlanDay(spPlan.getPlanDay());

        // バッチNo. = 検索した同項目
        shipWi.setBatchNo(spPlan.getBatchNo());

        // 荷主コード = 検索した同項目
        shipWi.setConsignorCode(spPlan.getConsignorCode());

        // 出荷先コード = 検索した同項目
        shipWi.setCustomerCode(spPlan.getCustomerCode());

        // 出荷伝票No. = 検索した同項目
        shipWi.setShipTicketNo(spPlan.getShipTicketNo());

        // 出荷伝票行 = 検索した同項目
        shipWi.setShipLineNo(spPlan.getShipLineNo());

        // 商品コード = 検索した同項目
        shipWi.setItemCode(spPlan.getItemCode());

        // 予定ロットNo = 仕分実績ロット
        shipWi.setPlanLotNo(result.getResultLotNo());

        // 予定数 = パラメータの予定数
        shipWi.setPlanQty(planQty);

        // 実績数 = 0
        shipWi.setResultQty(0);

        // 欠品数 = 0
        shipWi.setShortageQty(0);

        // 実績ロットNo = 空白
        shipWi.setResultLotNo("");

        // 結果バース = 空白
        shipWi.setResultBerth("");

        // 作業日 = 空白
        shipWi.setWorkDay("");

        // 出荷検品ユーザID = 空白
        shipWi.setShipUserId("");

        // 出荷検品端末No、RFTNo = 空白
        shipWi.setShipTerminalNo("");

        // 出荷検品作業秒数 = 0
        shipWi.setShipWorkSecond(0);

        // バース登録ユーザID = 空白
        shipWi.setBerthUserId("");

        // バース登録端末No、RFTNo = 空白
        shipWi.setBerthTerminalNo("");

        // バース登録作業秒数 = 0
        shipWi.setBerthWorkSecond(0);

        // 登録処理名 = インスタンス変数のクラスよりプログラム名を取得
        shipWi.setRegistPname(getCallerName());

        // 最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
        shipWi.setLastUpdatePname(getCallerName());

        // 出荷作業情報の登録を行う   
        _shipWiHandler.create(shipWi);

        return shipWi;
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
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractTcOperator.java 1660 2008-12-02 04:43:21Z rnakai $";
    }
}
