// $Id: XDPlanOperator.java 5021 2009-09-17 10:27:10Z shibamoto $
package jp.co.daifuku.wms.crossdock.operator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.ShipPlan;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.crossdock.schedule.XDInParameter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DatabaseFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * TC予定操作用のオペレータクラスです<BR>
 *
 *
 * @version $Revision: 5021 $, $Date: 2009-09-17 19:27:10 +0900 (木, 17 9 2009) $
 * @author  020246
 * @author  Last commit: $Author: shibamoto $
 */
public class XDPlanOperator
        extends AbstractTcOperator
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
     * WareNaviSystemコントローラ
     */
    private WarenaviSystemController _WNSysCtrl;

    /**
     * シーケンスハンドラ
     */
    private WMSSequenceHandler _SeqHndl;

    /**
     * TC予定ハンドラ
     */
    private CrossDockPlanHandler _XDHndl;

    /**
     * TC予定検索キー
     */
    private CrossDockPlanSearchKey _XDSKey;

    /**
     * TC予定更新キー
     */
    private CrossDockPlanAlterKey _XDAKey;

    /**
     * 入荷作業情報ハンドラ
     */
    private ReceivingWorkInfoHandler _RwiHndl;

    /**
     * 入荷作業情報更新キー
     */
    private ReceivingWorkInfoAlterKey _RwiAKey;

    /**
     * 出荷予定情報ハンドラ
     */
    private ShipPlanHandler _SpHndl;

    /**
     * 出荷予定情報更新キー
     */
    private ShipPlanAlterKey _SpAKey;

    /**
     * 商品マスタコントローラ
     */
    private ItemController _ItemCtrl;

    /**
     * 仕入先マスタコントローラ
     */
    private SupplierController _SupCtrl;

    /**
     * 出荷先マスタコントローラ
     */
    private CustomerController _CusCtrl;


    private CrossDockPlan xdPlan = null;

    private ReceivingWorkInfo rwiInfo = null;

    private ShipPlan plan = null;

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
    public XDPlanOperator(Connection conn, Class caller)
            throws ReadWriteException,
                ScheduleException
    {
        super(conn, caller);

        // DBハンドラ、コントローラ生成
        _WNSysCtrl = new WarenaviSystemController(conn, caller);
        _XDHndl = new CrossDockPlanHandler(conn);
        _XDSKey = new CrossDockPlanSearchKey();
        _XDAKey = new CrossDockPlanAlterKey();
        _SeqHndl = new WMSSequenceHandler(conn);
        _RwiHndl = new ReceivingWorkInfoHandler(conn);
        _RwiAKey = new ReceivingWorkInfoAlterKey();
        _SpHndl = new ShipPlanHandler(conn);
        _SpAKey = new ShipPlanAlterKey();
        _ItemCtrl = new ItemController(conn, caller);
        _SupCtrl = new SupplierController(conn, caller);
        _CusCtrl = new CustomerController(conn, caller);

        xdPlan = new CrossDockPlan();
        rwiInfo = new ReceivingWorkInfo();
        plan = new ShipPlan();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * TC予定検索<BR>
     * XDInParameterの検索条件に一致するTC予定情報を検索し、取得件数を返します。<BR>
     *
     * @param param 入力パラメータ
     * @return 取得件数
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    public CrossDockPlan[] findPlan(Parameter param)
            throws CommonException
    {
        // キャスト
        XDInParameter xdParam = (XDInParameter)param;

        // 出荷先一意チェックフラグ
        boolean isCheckCustomer = WmsParam.IS_UNIQUE_CHECK_CUSTOMER;
        // 仕入先一意チェックフラグ
        boolean isCheckSupplier = WmsParam.IS_UNIQUE_CHECK_SUPPLIER;

        try
        {
            // 検索条件セット
            _XDSKey.clear();
            // TC予定情報．予定日
            _XDSKey.setPlanDay(xdParam.getPlanDay());
            if (isCheckCustomer)
            {
                // TC予定情報．出荷先コード
                _XDSKey.setCustomerCode(xdParam.getCustomerCode());
            }
            // TC予定情報．出荷伝票No.
            _XDSKey.setShipTicketNo(xdParam.getShipTicketNo());
            // TC予定情報．出荷伝票行No.
            _XDSKey.setShipLineNo(xdParam.getShipLineNo());
            // TC予定情報．入荷伝票No.
            _XDSKey.setReceiveTicketNo(xdParam.getReceiveTicketNo());
            // TC予定情報．入荷伝票行No.
            _XDSKey.setReceiveLineNo(xdParam.getReceiveLineNo());
            if (isCheckSupplier)
            {
                // TC予定情報．仕入先コード
                _XDSKey.setSupplierCode(xdParam.getSupplierCode());
            }
            // TC予定情報．状態フラグ
            _XDSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            // TC予定情報．荷主コード
            _XDSKey.setConsignorCode(xdParam.getConsignorCode());

            // 取得情報
            _XDSKey.setPlanUkeyCollect();
            _XDSKey.setStatusFlagCollect();
            _XDSKey.setLastUpdateDateCollect();

            CrossDockPlan[] entities = (CrossDockPlan[])_XDHndl.find(_XDSKey);

            return entities;
        }
        finally
        {
        }
    }

    /**
     * TC予定検索<BR>
     * XDInParameterの検索条件に一致するTC予定情報を検索し、取得件数を返します。<BR>
     *
     * @param param 入力パラメータ
     * @return 取得件数
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    public int countPlan(Parameter param)
            throws CommonException
    {
        // キャスト
        XDInParameter xdParam = (XDInParameter)param;

        // 出荷先一意チェックフラグ
        boolean isCheckCustomer = WmsParam.IS_UNIQUE_CHECK_CUSTOMER;
        // 仕入先一意チェックフラグ
        boolean isCheckSupplier = WmsParam.IS_UNIQUE_CHECK_SUPPLIER;

        try
        {
            // 検索条件セット
            _XDSKey.clear();
            // TC予定情報．予定日
            _XDSKey.setPlanDay(xdParam.getPlanDay());
            if (isCheckCustomer)
            {
                // TC予定情報．出荷先コード
                _XDSKey.setCustomerCode(xdParam.getCustomerCode());
            }
            // TC予定情報．出荷伝票No.
            _XDSKey.setShipTicketNo(xdParam.getShipTicketNo());
            // TC予定情報．出荷伝票行No.
            _XDSKey.setShipLineNo(xdParam.getShipLineNo());
            // TC予定情報．入荷伝票No.
            _XDSKey.setReceiveTicketNo(xdParam.getReceiveTicketNo());
            // TC予定情報．入荷伝票行No.
            _XDSKey.setReceiveLineNo(xdParam.getReceiveLineNo());
            if (isCheckSupplier)
            {
                // TC予定情報．仕入先コード
                _XDSKey.setSupplierCode(xdParam.getSupplierCode());
            }
            // TC予定情報．状態フラグ
            _XDSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            // TC予定情報．荷主コード
            _XDSKey.setConsignorCode(xdParam.getConsignorCode());

            return _XDHndl.count(_XDSKey);

        }
        finally
        {
        }
    }


    /**
     * TC予定登録<BR>
     * paramで指定されたパラメータの内容をもとに、TC予定データ登録処理を行います。<BR>
     * 登録失敗など異常が発生した場合は、例外を通知します。<BR>
     *
     * @param loadUnitKey 取込単位キー
     * @param shipLoadUnitKey 出荷取込キー
     * @param registKind 登録区分
     * @param param 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     * @throws NoPrimaryException 一意項目が複数存在するときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    public void createPlan(String loadUnitKey, String shipLoadUnitKey, String registKind, Parameter param)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                OperatorException
    {

        // 予定一意キーを採番
        String planUkey = _SeqHndl.nextCrossDockPlanUkey();
        // TC連携キーを採番
        String xdUkey = _SeqHndl.nextCrossDockUkey();

        // TC予定情報を登録する。
        createCrossDockPlan(planUkey, xdUkey, loadUnitKey, registKind, param);

        // 入荷作業情報を登録する。
        createRecevingWorkInfo(planUkey, xdUkey, param);

        // 出荷予定情報を登録する。
        createShipPlan(xdUkey, shipLoadUnitKey, registKind, param);

        // 商品マスタ情報を登録する。
        createItemMaster(param);

        // 仕入先マスタ情報を登録する。
        createSupplierMaster(param);

        // 出荷先マスタ情報を登録する。
        createCustomerMaster(param);

    }

    /**
     * TC予定情報更新<BR> 
     * paramsで指定されたパラメータの内容をもとに、TC予定データ更新処理を行います。<BR>
     * 更新失敗など異常が発生した場合は、例外を通知します。<BR>
     * 
     * @param params 入力パラメータ配列
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws InvalidDefineException パラメータがStorageInParameter[]でないときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     * @throws DataExistsException 該当データが既に存在するときにスローされます。
     * @throws NoPrimaryException 該当データが一意でないときにスローされます。
     * @throws NotFoundException 該当データが見つからないときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    public void modifyPlan(Parameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException,
                OperatorException,
                CommonException

    {
        // キャスト
        XDInParameter[] xdParams = (XDInParameter[])params;

        CrossDockPlanFinder finder = null;
        try
        {
            // TC予定情報、出荷予定情報、入荷作業情報のロック（searchForUpdate）を行う。
            // 検索結果はfinderに保持される
            lockXdPlan(xdParams, finder);

            // 1件ずつ情報を更新する
            for (XDInParameter param : xdParams)
            {
                // 行NoをOperatorに保持する
                setRowNo(param.getRowNo());

                // 入荷作業情報を更新する
                _RwiAKey.clear();
                // 条件
                // 入荷作業情報．作業No. = finderで取得した入荷作業情報の作業No.
                _RwiAKey.setJobNo((String)(param.getLockEntity().getValue(ReceivingWorkInfo.JOB_NO)));

                // 更新内容
                // 入荷作業情報．予定数 = パラメータ値の入力パラメータクラスの予定数
                _RwiAKey.updatePlanQty(param.getPlanQty());
                // 入荷作業情報．最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
                _RwiAKey.updateLastUpdatePname(getCallerName());

                // 入荷作業情報更新
                _RwiHndl.modify(_RwiAKey);

                // TC予定情報を更新する
                _XDAKey.clear();
                // 条件
                // TC予定情報．予定一意キー = パラメータ値の入力パラメータクラスの予定一意キー
                _XDAKey.setPlanUkey(param.getPlanUkey());

                // 更新内容
                // TC予定情報．仕分場所 = パラメータ値の入力パラメータクラスの仕分場所
                _XDAKey.updateSortingPlace(param.getSortingPlace());
                // TC予定情報．予定数 = パラメータ値の入力パラメータクラスの予定数
                _XDAKey.updatePlanQty(param.getPlanQty());
                // TC予定情報．最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
                _XDAKey.updateLastUpdatePname(getCallerName());

                // TC予定情報更新
                _XDHndl.modify(_XDAKey);


                // 出荷予定情報を更新する
                _SpAKey.clear();
                // 条件
                // 出荷予定情報．予定一意キー = finderで取得した出荷予定情報の予定一意キー
                _SpAKey.setPlanUkey((String)(param.getLockEntity().getValue(ShipPlan.PLAN_UKEY)));

                // 更新内容
                // 出荷予定情報．予定数 = パラメータ値の入力パラメータクラスの予定数
                _SpAKey.updatePlanQty(param.getPlanQty());
                // 出荷予定情報．最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
                _SpAKey.updateLastUpdatePname(getCallerName());

                // 出荷予定情報更新
                _SpHndl.modify(_SpAKey);
            }
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * TC予定情報削除<BR> 
     * paramsで指定されたパラメータの内容をもとに、TC予定データ更新(論理削除)処理を行います。<BR>
     * 更新失敗など異常が発生した場合は、例外を通知します。<BR>
     * 
     * @param params 入力パラメータ配列
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws InvalidDefineException パラメータがStorageInParameter[]でないときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     * @throws DataExistsException 該当データが既に存在するときにスローされます。
     * @throws NoPrimaryException 該当データが一意でないときにスローされます。
     * @throws NotFoundException 該当データが見つからないときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    public void deletePlan(Parameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException,
                OperatorException,
                CommonException
    {
        deletePlan(params, false);
    }

    /**
     * TC予定情報削除<BR> 
     * paramsで指定されたパラメータの内容をもとに、TC予定データ更新(論理削除)処理を行います。<BR>
     * 更新失敗など異常が発生した場合は、例外を通知します。<BR>
     * 
     * @param params 入力パラメータ配列
     * @param isHostCancel 取消区分がホストかどうか(ホストのときtrue、通常のときfalse)
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws InvalidDefineException パラメータがStorageInParameter[]でないときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     * @throws DataExistsException 該当データが既に存在するときにスローされます。
     * @throws NoPrimaryException 該当データが一意でないときにスローされます。
     * @throws NotFoundException 該当データが見つからないときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    public void deletePlan(Parameter[] params, boolean isHostCancel)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException,
                OperatorException,
                CommonException
    {
        // キャスト
        XDInParameter[] xdParams = (XDInParameter[])params;

        CrossDockPlanFinder finder = null;
        try
        {
            // TC予定情報、出荷予定情報、入荷作業情報のロック（searchForUpdate）を行う。
            // 検索結果はfinderに保持される
            lockXdPlan(xdParams, finder);

            // InParameterより、削除で使用するEntityの配列を生成する。
            Entity[] entities = new Entity[xdParams.length];
            for (int i = 0; i < xdParams.length; i++)
            {
                entities[i] = xdParams[i].getLockEntity();
            }

            // 対象のTC予定情報、出荷予定情報、入荷作業情報をdeleteを使用して更新する。
            delete(entities, isHostCancel);
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * TC予定データ一括削除<BR> 
     * paramで指定されたパラメータの内容をもとに、TC予定データ更新(論理削除)処理を行います。<BR>
     * 更新失敗など異常が発生した場合は、例外を通知します。<BR>
     * 
     * @param params 入力パラメータ配列
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当データが見つからないときスローされます。
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    public void massDeletePlan(Parameter[] params)
            throws LockTimeOutException,
                ReadWriteException,
                NotFoundException,
                CommonException
    {
        final int LIMIT = 100;

        XDInParameter[] inParams = (XDInParameter[])params;

        CrossDockPlanFinder finder = null;
        try
        {
            // ロック用Finderの構築
            finder = new CrossDockPlanFinder(getConnection());

            // エンティティを保持する連想配列
            LinkedHashMap<Integer, List<CrossDockPlan[]>> entities =
                    new LinkedHashMap<Integer, List<CrossDockPlan[]>>();

            // ロック用検索キーの構築
            CrossDockPlanSearchKey lockKey = new CrossDockPlanSearchKey();

            // select
            lockKey.setCollect(new FieldName(CrossDockPlan.STORE_NAME, FieldName.ALL_FIELDS));
            lockKey.setCollect(ReceivingWorkInfo.JOB_NO);
            lockKey.setCollect(ShipPlan.PLAN_UKEY);

            // join
            lockKey.setJoin(CrossDockPlan.CROSS_DOCK_UKEY, "", ShipPlan.CROSS_DOCK_UKEY, "");
            lockKey.setJoin(CrossDockPlan.PLAN_UKEY, "", ReceivingWorkInfo.PLAN_UKEY, "");

            // ロックしたデータを保持する可変長配列
            List<CrossDockPlan[]> detas;

            // データのロッック処理の実行
            for (XDInParameter inParam : inParams)
            {
                // 操作中のリストセルの行No.をセットする
                setRowNo(inParam.getRowNo());

                // Finderの初期化
                finder.open(true);

                // where
                lockKey.clearKeys();
                lockKey.setPlanDay(inParam.getPlanDay());
                lockKey.setLoadUnitKey(inParam.getLoadUnitKey());
                lockKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                lockKey.setConsignorCode(inParam.getConsignorCode());

                // ロック用の検索を実行
                int count = finder.searchForUpdate(lockKey, CrossDockPlanFinder.WAIT_SEC_NOWAIT);

                // データを取得出来なかった場合
                if (count <= 0)
                {
                    throw new NotFoundException();
                }

                // データは100件程度の単位で処理するので100件を超える場合は分割して保持する為、
                // エンティティを可変長配列に保持する
                detas = new ArrayList<CrossDockPlan[]>();

                // ロックしたデータを展開
                while (finder.hasNext())
                {
                    // 取得位置の確定
                    int sPos = detas.size() * LIMIT;
                    int ePos = sPos + LIMIT;

                    // エンティティを可変長配列に保持する
                    detas.add((CrossDockPlan[])finder.getEntities(sPos, ePos));
                }

                // 対象のリストセルの行番号をキーにロックしたエンティティの可変長配列を保持
                entities.put(inParam.getRowNo(), detas);
            }

            // リストセルの行番号に紐付くデータ単位に処理を行う
            for (XDInParameter inParam : inParams)
            {
                // 操作中のリストセルの行No.をセットする
                setRowNo(inParam.getRowNo());

                // 処理単位数毎に処理を繰り返す
                detas = entities.get(inParam.getRowNo());
                for (CrossDockPlan[] entity : detas)
                {
                    // 論理削除の実行
                    delete(entity);
                }
            }
        }
        finally
        {
            closeFinder(finder);
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
     * TC予定データを作成します。
     *
     * @param planUKey 予定一意キー
     * @param xdUKey TC連携キー
     * @param loadUKey 取込単位キー
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @return TC予定データ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     * @throws NoPrimaryException 同一のデータが複数存在したときにスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    protected CrossDockPlan createCrossDockPlan(String planUKey, String xdUKey, String loadUKey, String registKind,
            Parameter startParam)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                OperatorException

    {
        XDInParameter param = (XDInParameter)startParam;

        // 値のセット
        // 予定一意キー
        xdPlan.setPlanUkey(planUKey);
        // TC連携キー
        xdPlan.setCrossDockUkey(xdUKey);
        // 取込単位キー
        xdPlan.setLoadUnitKey(loadUKey);
        // ファイル行No.
        xdPlan.setFileLineNo(param.getRowNo());
        // 状態フラグ
        xdPlan.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        // ホスト取消区分
        xdPlan.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        // 予定日
        xdPlan.setPlanDay(param.getPlanDay());
        // バッチNo.
        xdPlan.setBatchNo(param.getBatchNo());
        // 荷主コード
        xdPlan.setConsignorCode(param.getConsignorCode());
        // 仕入先コード
        xdPlan.setSupplierCode(param.getSupplierCode());
        // 入荷伝票No.
        xdPlan.setReceiveTicketNo(param.getReceiveTicketNo());
        // 入荷伝票行No.
        xdPlan.setReceiveLineNo(param.getReceiveLineNo());
        // 出荷先コード
        xdPlan.setCustomerCode(param.getCustomerCode());
        // 出荷伝票No.
        xdPlan.setShipTicketNo(param.getShipTicketNo());
        // 出荷伝票行No.
        xdPlan.setShipLineNo(param.getShipLineNo());
        // 仕分場所
        xdPlan.setSortingPlace(param.getSortingPlace());
        // 商品コード
        xdPlan.setItemCode(param.getItemCode());
        // 予定ロットNo.
        xdPlan.setPlanLotNo(param.getPlanLotNo());
        // 予定数
        xdPlan.setPlanQty(param.getPlanQty());
        // 入荷実績数
        xdPlan.setReceiveResultQty(0);
        // 入荷欠品数
        xdPlan.setReceiveShortageQty(0);
        // 仕分実績数
        xdPlan.setSortResultQty(0);
        // 仕分欠品数
        xdPlan.setSortShortageQty(0);
        // 実績数
        xdPlan.setResultQty(0);
        // 欠品数
        xdPlan.setShortageQty(0);
        // 実績報告区分
        xdPlan.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        // 作業日
        xdPlan.setWorkDay("");
        // 登録区分
        xdPlan.setRegistKind(registKind);
        // 登録処理名
        xdPlan.setRegistPname(getCallerName());
        // 最終更新処理名
        xdPlan.setLastUpdatePname(getCallerName());

        // TC予定データ作成
        _XDHndl.create(xdPlan);

        return xdPlan;
    }


    /**
     * 入荷作業情報データを作成します。
     *
     * @param planUKey 予定一意キー
     * @param xdUKey TC連携キー
     * @param startParam 入力パラメータ
     * @return 入荷作業情報データ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     */
    protected ReceivingWorkInfo createRecevingWorkInfo(String planUKey, String xdUKey, Parameter startParam)
            throws ReadWriteException,
                DataExistsException
    {
        XDInParameter param = (XDInParameter)startParam;

        // 作成内容をセット(作業情報)
        // 作業No.    = 1で採番した作業No.
        rwiInfo.setJobNo(_SeqHndl.nextReceivingJobNo());
        // 設定単位キー    = 空白
        rwiInfo.setSettingUnitKey("");
        // 集約作業No.    = 空白
        rwiInfo.setCollectJobNo("");
        // TC連携キー    = パラメータ値のTC連係キー
        rwiInfo.setCrossDockUkey(xdUKey);
        // 作業区分    = ”01”（入荷）
        rwiInfo.setJobType(SystemDefine.JOB_TYPE_RECEIVING);
        // 状態フラグ    = ”0”（未作業）
        rwiInfo.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        // ハードウェア区分    = ”0”（未作業）
        rwiInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
        // 予定一意キー    = パラメータ値の予定一意キー
        rwiInfo.setPlanUkey(planUKey);
        // TC/DC区分    = ”1”（TC）
        rwiInfo.setTcdcFlag(SystemDefine.TCDC_FLAG_TC);
        // 予定日    = パラメータ値の入力パラメータクラスの予定日
        rwiInfo.setPlanDay(param.getPlanDay());
        // 荷主コード    = パラメータ値の入力パラメータクラスの荷主コード
        rwiInfo.setConsignorCode(param.getConsignorCode());
        // 仕入先コード    = パラメータ値の入力パラメータクラスの仕入先コード
        rwiInfo.setSupplierCode(param.getSupplierCode());
        // 入荷伝票No.    = パラメータ値の入力パラメータクラスの入荷伝票No.
        rwiInfo.setReceiveTicketNo(param.getReceiveTicketNo());
        // 入荷伝票行No.    = パラメータ値の入力パラメータクラスの入荷伝票行No.
        rwiInfo.setReceiveLineNo(param.getReceiveLineNo());
        // 商品コード    = パラメータ値の入力パラメータクラスの商品コード
        rwiInfo.setItemCode(param.getItemCode());
        // 予定ロットNo.    = パラメータ値の入力パラメータクラスのロットNo.
        rwiInfo.setPlanLotNo(param.getPlanLotNo());
        // 実績ロットNo.    = 空白
        rwiInfo.setResultLotNo("");
        // 予定数    = パラメータ値の入力パラメータクラスの予定数
        rwiInfo.setPlanQty(param.getPlanQty());
        // 実績数    = 0
        rwiInfo.setResultQty(0);
        // 欠品数    = 0
        rwiInfo.setShortageQty(0);
        // 作業日    = 空白
        rwiInfo.setWorkDay("");
        // ユーザID    = 空白
        rwiInfo.setUserId("");
        // 端末No.、RFTNo.    = 空白
        rwiInfo.setTerminalNo("");
        // 作業秒数    = 0
        rwiInfo.setWorkSecond(0);
        // 登録処理名    = インスタンス変数のクラスよりプログラム名を取得
        rwiInfo.setRegistPname(getCallerName());
        // 最終更新処理名    = インスタンス変数のクラスよりプログラム名を取得
        rwiInfo.setLastUpdatePname(getCallerName());

        // 作業情報作成
        _RwiHndl.create(rwiInfo);

        return rwiInfo;
    }

    /**
     * 出荷予定情報データを作成します。
     *
     * @param xdUKey TC連携キー
     * @param loadUKey 出荷取込単位キー
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @return 出荷予定情報データ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     * @throws NoPrimaryException 同一のデータが複数存在したときにスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    protected ShipPlan createShipPlan(String xdUKey, String loadUKey, String registKind, Parameter startParam)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                OperatorException

    {
        XDInParameter param = (XDInParameter)startParam;

        // 予定一意キー     = 1で採番した予定一意キー
        plan.setPlanUkey(_SeqHndl.nextShipPlanUkey());
        // TC連携キー     = パラメータ値のTC連係キー
        plan.setCrossDockUkey(xdUKey);
        // 取込単位キー     = パラメータ値の取込単位キー
        plan.setLoadUnitKey(loadUKey);
        // ファイル行No.     = パラメータ値の入力パラメータクラスのリストセルの行No.
        plan.setFileLineNo(param.getRowNo());
        // 出荷検品状態フラグ     = ”0”（未作業）
        plan.setWorkStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        // バース登録状態フラグ     = ”0”（未作業）
        plan.setBerthStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        // ホスト取消区分     = ”0”（通常データ）
        plan.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        // TC/DC区分     = ”1”（TC）
        plan.setTcdcFlag(SystemDefine.TCDC_FLAG_TC);
        // 予定日     = パラメータ値の入力パラメータクラスの予定日
        plan.setPlanDay(param.getPlanDay());
        // バッチNo.     = パラメータ値の入力パラメータクラスのバッチNo.
        plan.setBatchNo(param.getBatchNo());
        // 荷主コード     = パラメータ値の入力パラメータクラスの荷主コード
        plan.setConsignorCode(param.getConsignorCode());
        // 出荷先コード     = パラメータ値の入力パラメータクラスの仕入先コード
        plan.setCustomerCode(param.getCustomerCode());
        // 出荷伝票No.     = パラメータ値の入力パラメータクラスの入荷伝票No.
        plan.setShipTicketNo(param.getShipTicketNo());
        // 出荷伝票行No.     = パラメータ値の入力パラメータクラスの入荷伝票行No.
        plan.setShipLineNo(param.getShipLineNo());
        // 商品コード     = パラメータ値の入力パラメータクラスの出荷先コード
        plan.setItemCode(param.getItemCode());
        // 予定ロットNo.     = パラメータ値の入力パラメータクラスの出荷伝票No.
        plan.setPlanLotNo(param.getPlanLotNo());
        // 配分済数     = 0
        plan.setDistributedQty(0);
        // 予定数     = パラメータ値の入力パラメータクラスの予定数
        plan.setPlanQty(param.getPlanQty());
        // 実績数     = 0
        plan.setResultQty(0);
        // 欠品数     = 0
        plan.setShortageQty(0);
        // 実績報告区分     = ”0”（未報告）
        plan.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        // 作業日     = 空白
        plan.setWorkDay("");
        // 登録区分     = パラメータ値の登録区分
        plan.setRegistKind(registKind);
        // 登録処理名     = インスタンス変数のクラスよりプログラム名を取得
        plan.setRegistPname(getCallerName());
        // 最終更新処理名     = インスタンス変数のクラスよりプログラム名を取得
        plan.setLastUpdatePname(getCallerName());

        // 出荷予定データ作成
        _SpHndl.create(plan);

        return plan;
    }


    /**
     * 商品マスタの登録を行ないます。<BR>
     * マスタパッケージが導入されている場合は何もしません。<BR>
     *
     * @param startParam 入力パラメータ
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     * @throws NoPrimaryException 同一の商品コードが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected void createItemMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        if (_WNSysCtrl.hasMasterPack())
        {
            return;
        }

        XDInParameter param = (XDInParameter)startParam;

        Item item = new Item();
        // システム管理区分 = ”0”（ユーザ） 
        item.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        // 荷主コード = パラメータ値の入力パラメータクラスの荷主コード 
        item.setConsignorCode(param.getConsignorCode());
        // 商品コード = パラメータ値の入力パラメータクラスの商品コード 
        item.setItemCode(param.getItemCode());
        // 商品名称 = パラメータ値の入力パラメータクラスの商品名称 ※　値が定義されている場合
        if (!StringUtil.isBlank(param.getItemName()))
        {
            item.setItemName(param.getItemName());
        }
        // JANコード = パラメータ値の入力パラメータクラスのJANコード ※　値が定義されている場合
        if (!StringUtil.isBlank(param.getJan()))
        {
            item.setJan(param.getJan());
        }
        // ケースITF = パラメータ値の入力パラメータクラスのケースITF ※　値が定義されている場合
        if (!StringUtil.isBlank(param.getItf()))
        {
            item.setItf(param.getItf());
        }
        // ケース入数 = パラメータ値の入力パラメータクラスのケース入数 ※　値が0以上の場合
        if (param.getEnteringQty() >= 0)
        {
            item.setEnteringQty(param.getEnteringQty());
        }
        // 登録処理名 = インスタンス変数のクラスよりプログラム名を取得 
        item.setRegistPname(getCallerName());
        // 最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得 
        item.setLastUpdatePname(getCallerName());

        try
        {
            _ItemCtrl.autoCreate(item, param.getWmsUserInfo());
        }
        catch (LockTimeOutException e)
        {
            // 他端末で作業中
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }
        catch (NotFoundException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 仕入先マスタの登録を行ないます。<BR>
     * マスタパッケージが導入されている場合は何もしません。<BR>
     *
     * @param startParam 入力パラメータ
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     * @throws NoPrimaryException 同一の仕入先コードが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected void createSupplierMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        if (_WNSysCtrl.hasMasterPack())
        {
            return;
        }

        XDInParameter param = (XDInParameter)startParam;

        Supplier supplier = new Supplier();

        // 荷主コード = パラメータ値の入力パラメータクラスの荷主コード 
        supplier.setConsignorCode(param.getConsignorCode());
        // 仕入先コード = パラメータ値の入力パラメータクラスの仕入先コード ※　値が定義されている場合
        if (!StringUtil.isBlank(param.getSupplierCode()))
        {
            supplier.setSupplierCode(param.getSupplierCode());
        }
        // 仕入先名称 = パラメータ値の入力パラメータクラスの仕入先名称 ※　値が定義されている場合
        if (!StringUtil.isBlank(param.getSupplierName()))
        {
            supplier.setSupplierName(param.getSupplierName());
        }
        // 登録処理名 = インスタンス変数のクラスよりプログラム名を取得 
        supplier.setRegistPname(getCallerName());
        // 最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得 
        supplier.setLastUpdatePname(getCallerName());

        try
        {
            _SupCtrl.autoCreate(supplier, param.getWmsUserInfo());
        }
        catch (LockTimeOutException e)
        {
            // 他端末で作業中
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }
        catch (NotFoundException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }


    /**
     * 出荷先マスタの登録を行ないます。<BR>
     * マスタパッケージが導入されている場合は何もしません。<BR>
     *
     * @param startParam 入力パラメータ
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     * @throws NoPrimaryException 同一の出荷先コードが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected void createCustomerMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        if (_WNSysCtrl.hasMasterPack())
        {
            return;
        }

        XDInParameter param = (XDInParameter)startParam;

        Customer customer = new Customer();

        // 荷主コード = パラメータ値の入力パラメータクラスの荷主コード 
        customer.setConsignorCode(param.getConsignorCode());
        // 出荷先コード = パラメータ値の入力パラメータクラスの仕入先コード
        if (!StringUtil.isBlank(param.getCustomerCode()))
        {
            customer.setCustomerCode(param.getCustomerCode());
        }
        // 出荷先名称 = パラメータ値の入力パラメータクラスの出荷先名称
        if (!StringUtil.isBlank(param.getCustomerName()))
        {
            customer.setCustomerName(param.getCustomerName());
        }
        // 仕分場所 = パラメータ値の入力パラメータクラスの仕分場所
        if (!StringUtil.isBlank(param.getSortingPlace()))
        {
            customer.setSortingPlace(param.getSortingPlace());
        }
        // 登録処理名 = インスタンス変数のクラスよりプログラム名を取得 
        customer.setRegistPname(getCallerName());
        // 最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得 
        customer.setLastUpdatePname(getCallerName());

        try
        {
            _CusCtrl.autoCreate(customer, param.getWmsUserInfo());
        }
        catch (LockTimeOutException e)
        {
            // 他端末で作業中
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }
        catch (NotFoundException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 引数<code>entities</code>に対応する
     * TC予定情報、出荷予定情報、入荷作業情報を論理削除します。
     * 
     * @param entities CrossDockPlanエンティティ
     * @throws NotFoundException 該当データが見つからないときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected void delete(Entity[] entities)
            throws NotFoundException,
                ReadWriteException
    {
        delete(entities, false);
    }

    /**
     * 引数<code>entities</code>に対応する
     * TC予定情報、出荷予定情報、入荷作業情報を論理削除します。
     * 
     * @param entities CrossDockPlanエンティティ
     * @param isHostCancel 取消区分がホストかどうか(ホストのときtrue、通常のときfalse)
     * @throws NotFoundException 該当データが見つからないときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected void delete(Entity[] entities, boolean isHostCancel)
            throws NotFoundException,
                ReadWriteException
    {
        // 入荷作業情報更新キー
        // 状態フラグ
        _RwiAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        // 最終更新処理名
        _RwiAKey.updateLastUpdatePname(getCallerName());

        // TC予定情報更新キー
        // 状態フラグ
        _XDAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        // 最終更新処理名
        _XDAKey.updateLastUpdatePname(getCallerName());

        // 出荷予定情報更新キー
        // 出荷検品状態フラグ
        _SpAKey.updateWorkStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        // バース登録状態フラグ
        _SpAKey.updateBerthStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        // 最終更新処理名
        _SpAKey.updateLastUpdatePname(getCallerName());

        // 取消区分がホストのとき
        if (isHostCancel)
        {
            _XDAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
            _SpAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
        }

        // エンティティの件数分処理を繰り返す
        for (Entity entity : entities)
        {
            // 入荷作業情報の更新
            _RwiAKey.clearKeys();
            _RwiAKey.setJobNo((String)entity.getValue(ReceivingWorkInfo.JOB_NO));
            _RwiHndl.modify(_RwiAKey);

            // TC予定情報の更新
            _XDAKey.clearKeys();
            _XDAKey.setPlanUkey((String)entity.getValue(CrossDockPlan.PLAN_UKEY));
            _XDHndl.modify(_XDAKey);

            // 出荷予定情報の更新
            _SpAKey.clearKeys();
            _SpAKey.setPlanUkey((String)entity.getValue(ShipPlan.PLAN_UKEY));
            _SpHndl.modify(_SpAKey);
        }
    }

    /**
     * TC予定情報、出荷予定情報、入荷作業情報のロック（searchForUpdate）を行う。<BR>
     * ロック時に取得された項目はXDInParameter[]に保持されます。
     * 
     * @param params 入力パラメータ配列
     * @param finder DatabaseFinder
     * @throws CommonException データベースエラーが発生した場合に通知されます。
     */
    protected void lockXdPlan(XDInParameter[] params, DatabaseFinder finder)
            throws CommonException
    {
        finder = new CrossDockPlanFinder(getConnection());
        finder.open(true);

        for (XDInParameter param : params)
        {
            // 行NoをOperatorに保持する
            setRowNo(param.getRowNo());

            // TC予定情報、出荷予定情報、入荷作業情報のロック（searchForUpdate）を行う。
            _XDSKey.clear();

            // 取得情報設定
            // TC予定情報(全て)
            _XDSKey.setCollect(new FieldName(CrossDockPlan.STORE_NAME, FieldName.ALL_FIELDS));
            // 出荷予定情報(主キー)
            _XDSKey.setCollect(ShipPlan.PLAN_UKEY);
            // 入荷作業情報(主キー)
            _XDSKey.setCollect(ReceivingWorkInfo.JOB_NO);

            // 検索条件設定
            // TC予定情報．予定一意キー = パラメータ値の入力パラメータクラスの予定一意キー
            _XDSKey.setPlanUkey(param.getPlanUkey());
            // TC予定情報．最終更新日時 = パラメータ値の入力パラメータクラスの最終更新日時
            _XDSKey.setLastUpdateDate(param.getLastUpdateDate());

            // 結合条件
            // TC予定情報．TC連係キー = 出荷予定情報．TC連係キー
            _XDSKey.setJoin(CrossDockPlan.CROSS_DOCK_UKEY, ShipPlan.CROSS_DOCK_UKEY);
            // TC予定情報．予定一意キー = 入荷作業情報．予定一意キー
            _XDSKey.setJoin(CrossDockPlan.PLAN_UKEY, ReceivingWorkInfo.PLAN_UKEY);

            // ロック処理の実行
            int lockCount = finder.searchForUpdate(_XDSKey, CrossDockPlanFinder.WAIT_SEC_DEFAULT);

            // データが存在しなかった場合
            if (lockCount < 1)
            {
                throw new NotFoundException();
            }
            // データが2件以上存在した場合
            if (lockCount > 1)
            {
                throw new NoPrimaryException();
            }

            // 取得したEntityをパラメータに保持する
            finder.hasNext();
            Entity[] entity = finder.getEntities(0, 1);
            param.setLockEntity(entity[0]);
        }

        // 行NoをOperatorに保持する
        setRowNo(-1);
    }

}
