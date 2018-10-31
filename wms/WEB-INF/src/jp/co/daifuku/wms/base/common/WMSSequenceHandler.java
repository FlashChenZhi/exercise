// $Id: WMSSequenceHandler.java 8071 2013-11-13 06:19:45Z fukuwa $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.InvalidClassException;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InventResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.InventSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkListHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkListSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.InventResult;
import jp.co.daifuku.wms.base.entity.InventSetting;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.ReStoringPlan;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.ShipPlan;
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.SortWorkInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.entity.WorkList;
import jp.co.daifuku.wms.handler.db.DatabaseHandler;
import jp.co.daifuku.wms.handler.db.sequence.AbstractSequenceHandler;
import jp.co.daifuku.wms.handler.db.sequence.SequenceInfo;

/**
 * eWareNaviで使用するシーケンスオブジェクトの取得などを行うための
 * クラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * @version $Revision: 8071 $, $Date: 2013-11-13 15:19:45 +0900 (水, 13 11 2013) $
 * @author  ss
 * @author  Last commit: $Author: fukuwa $
 */

public class WMSSequenceHandler
        extends AbstractSequenceHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 入荷予定一意キー(RECEIVINGPLANUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo RECEIVINGPLANUKEY = new SequenceInfo("RECEIVING_PLANUKEY_SEQ",
            SystemDefine.JOB_TYPE_RECEIVING + "{0}", "00000000", 1L, 99999999L, 20, true);

    /** 入庫予定一意キー(STORAGEPLANUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo STORAGEPLANUKEY = new SequenceInfo("STORAGE_PLANUKEY_SEQ",
            SystemDefine.JOB_TYPE_STORAGE + "{0}", "00000000", 1L, 99999999L, 20, true);

    /** 出庫予定一意キー(RETRIEVALPLANUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo RETRIEVALPLANUKEY = new SequenceInfo("RETRIEVAL_PLANUKEY_SEQ",
            SystemDefine.JOB_TYPE_RETRIEVAL + "{0}", "00000000", 1L, 99999999L, 20, true);

    /** クロスドック予定一意キー(CROSSDOCKPLANUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo CROSSDOCKPLANUKEY = new SequenceInfo("CROSS_DOCK_PLANUKEY_SEQ",
            SystemDefine.JOB_TYPE_SORTING + "{0}", "00000000", 1L, 99999999L, 20, true);

    /** クロスドック連係キー(CROSSDOCKUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo CROSSDOCKUKEY = new SequenceInfo("CROSS_DOCK_UKEY_SEQ", "", "00000000", 1L,
            99999999L, 20, true);

    /** 出荷予定一意キー(SHIPPLANUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo SHIPPLANUKEY = new SequenceInfo("SHIP_PLANUKEY_SEQ",
            SystemDefine.JOB_TYPE_SHIPPING + "{0}", "00000000", 1L, 99999999L, 20, true);

    /** 再入庫予定一意キー(RESTORINGPLANUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo RESTORINGPLANUKEY = new SequenceInfo("RESTORING_PLANUKEY_SEQ",
            SystemDefine.JOB_TYPE_RESTORING + "{0}", "00000000", 1L, 99999999L, 20, true);

    /** 在庫ID(STOCK_ID)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo STOCKID = new SequenceInfo("STOCK_ID_SEQ", "", "00000000", 1L, 999999L, 20, true);

    /** 入荷作業設定単位キー(RECEIVINGSETUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo RECEIVINGSETUKEY = new SequenceInfo("RECEIVING_SETUKEY_SEQ", "", "00000000", 1L,
            99999999L, 20, true);

    /** 入出庫作業設定単位キー(WORKINFOSETUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo WORKINFOSETUKEY = new SequenceInfo("WORKINFO_SETUKEY_SEQ", "", "00000000", 1L,
            99999999L, 20, true);

    /** 仕分作業設定単位キー(SORTSETUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo SORTSETUKEY = new SequenceInfo("SORT_SETUKEY_SEQ", "", "00000000", 1L, 99999999L,
            20, true);

    /** 出荷作業設定単位キー(SHIPSETUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo SHIPSETUKEY = new SequenceInfo("SHIP_SETUKEY_SEQ", "", "00000000", 1L, 99999999L,
            20, true);

    /** 移動作業設定単位キー(MOVESETUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo MOVESETUKEY = new SequenceInfo("MOVE_SETUKEY_SEQ", "", "00000000", 1L, 99999999L,
            20, true);

    /** 入荷作業作業No.(RECEIVINGJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo RECEIVINGJOBNO = new SequenceInfo("RECEIVING_JOBNO_SEQ", "", "00000000", 1L,
            99999999L, 20, true);

    /** 入出庫作業作業No.(WORKINFOJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo WORKINFOJOBNO = new SequenceInfo("WORKINFO_JOBNO_SEQ", "", "00000000", 1L,
            99999999L, 20, true);

    /** 仕分作業作業No.(SORTJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo SORTJOBNO = new SequenceInfo("SORT_JOBNO_SEQ", "", "00000000", 1L, 99999999L, 20,
            true);

    /** 出荷作業作業No.(SHIPJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo SHIPJOBNO = new SequenceInfo("SHIP_JOBNO_SEQ", "", "00000000", 1L, 99999999L, 20,
            true);

    /** 移動作業作業No.(MOVEJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo MOVEJOBNO = new SequenceInfo("MOVE_JOBNO_SEQ", "", "00000000", 1L, 99999999L, 20,
            true);

    /** 棚卸作業作業No.(INVENTJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo INVENTJOBNO = new SequenceInfo("INVENT_JOBNO_SEQ", "", "00000000", 1L, 99999999L,
            20, true);

    /** 入荷作業集約作業No.(RECEIVINGCOLLECTJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo RECEIVINGCOLLECTJOBNO = new SequenceInfo("RECEIVING_COLLECTJOBNO_SEQ", "",
            "00000000", 1L, 99999999L, 20, true);

    /** 入出庫作業集約作業No.(WORKINFOCOLLECTJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo WORKINFOCOLLECTJOBNO = new SequenceInfo("WORKINFO_COLLECTJOBNO_SEQ", "",
            "00000000", 1L, 99999999L, 20, true);

    /** 仕分作業集約作業No.(SORTCOLLECTJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo SORTCOLLECTJOBNO = new SequenceInfo("SORT_COLLECTJOBNO_SEQ", "", "00000000", 1L,
            99999999L, 20, true);

    /** 出荷作業集約作業No.(SHIPCOLLECTJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo SHIPCOLLECTJOBNO = new SequenceInfo("SHIP_COLLECTJOBNO_SEQ", "", "00000000", 1L,
            99999999L, 20, true);

    /** 出庫開始単位キー(STARTUNITKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo STARTUNITKEY = new SequenceInfo("START_UNIT_KEY_SEQ", "", "00000000", 1L,
            99999999L, 20, true);

    /** レポートNoの順序オブジェクトを表すフィールド */
    public static final SequenceInfo REPORTNO = new SequenceInfo("REPORTNO_SEQ", "", "00000000", 1L, 99999999L, 20,
            true);

    /* Pカート用Sequence */
    /** PCT出庫予定一意キー(PCTRETPLANUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo PCTRETPLANUKEY = new SequenceInfo("PCTRET_PLANUKEY_SEQ",
            SystemDefine.JOB_TYPE_RETRIEVAL + "{0}", "00000000", 1L, 99999999L, 20, true);

    /** PCT出庫作業作業No.(PCTRETWORKINFOJOBNO)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo PCTRETWORKINFOJOBNO = new SequenceInfo("PCTRETWORKINFO_JOBNO_SEQ", "", "00000000",
            1L, 99999999L, 20, true);

    /** PCT出庫作業設定単位キー(PCTRETWORKINFOSETUKEY)の順序オブジェクトを表すフィールド */
    public static final SequenceInfo PCTRETWORKINFOSETUKEY = new SequenceInfo("PCTRETWORKINFO_SETUKEY_SEQ", "",
            "00000000", 1L, 99999999L, 20, true);

    /**<jp>
     * 搬送Key(Mckey)の順序オブジェクトを表すフィールドです。
     </jp>*/
    /**<en>
     * Field: sequence object of CarryKey(Mckey)
     </en>*/
    public static final SequenceInfo CARRYKEY = new SequenceInfo("CARRYKEY_SEQ", "", "00000000", 1L, 90000000L, 10,
            true);

    /**<jp>
     * PalletIDの順序オブジェクトを表すフィールドです。
     </jp>*/
    /**<en>
     * Field: sequence object of PalletID
     </en>*/
    public static final SequenceInfo PALLETID = new SequenceInfo("PALLETID_SEQ", "", "00000000", 1L, 99999999L, 10,
            true);

    /**<jp>
     * 作業Noのフォーマット。
     </jp>*/
    /**<en>
     * Field: format of work no.
     </en>*/
    public static final String WORKNO_FORMAT = "00000000";

    /**<jp>
     * 作業Noの最小値。
     </jp>*/
    /**<en>
     * Field: MIN. number of work no.
     </en>*/
    public static final long WORKNO_MIN = 1L;

    /**<jp>
     * 作業Noの最大値。
     </jp>*/
    /**<en>
     * Field: MAX. number of work no.
     </en>*/
    public static final long WORKNO_MAX = 9999999L;

    /**<jp>
     * 作業Noの順序オブジェクトを表すフィールドです。
     </jp>*/
    /**<en>
     * Field: sequence object of work no.
     </en>*/
    public static final SequenceInfo WORKNO = new SequenceInfo("WORKNO_SEQ", "", WORKNO_FORMAT, WORKNO_MIN, WORKNO_MAX,
            10, true);

    /**<jp>
     * 入庫作業Noの最小値。
     </jp>*/
    /**<en>
     * Field: MIN. number of storage work no.
     </en>*/
    public static final long STORAGE_WORKNO_MIN = 10000000L;

    /**<jp>
     * 入庫作業Noの最大値。
     </jp>*/
    /**<en>
     * Field: MAX. number of storage work no.
     </en>*/
    public static final long STORAGE_WORKNO_MAX = 19999999L;

    /**<jp>
     * 入庫作業Noの順序オブジェクトを表すフィールドです。
     </jp>*/
    /**<en>
     * Field: sequence object of storage work no.
     </en>*/
    public static final SequenceInfo STORAGE_WORKNO = new SequenceInfo("STORAGE_WORKNO_SEQ", "", WORKNO_FORMAT,
            STORAGE_WORKNO_MIN, STORAGE_WORKNO_MAX, 10, true);

    /**<jp>
     * 出庫作業Noの最小値。
     </jp>*/
    /**<en>
     * Field: MIN. number of retrieval work no.
     </en>*/
    public static final long RETRIEVAL_WORKNO_MIN = 20000000L;

    /**<jp>
     * 出庫作業Noの最大値。
     </jp>*/
    /**<en>
     * Field: MAX. number of retrieval work no.
     </en>*/
    public static final long RETRIEVAL_WORKNO_MAX = 29999999L;

    /**<jp>
     * 出庫作業Noの順序オブジェクトを表すフィールドです。
     </jp>*/
    /**<en>
     * Field: sequence object of retrieval work no.
     </en>*/
    public static final SequenceInfo RETRIEVAL_WORKNO = new SequenceInfo("RETRIEVAL_WORKNO_SEQ", "", WORKNO_FORMAT,
            RETRIEVAL_WORKNO_MIN, RETRIEVAL_WORKNO_MAX, 10, true);

    /**<jp>
     * スケジュールNoの順序オブジェクトを表すフィールドです。
     </jp>*/
    /**<en>
     * Field: sequence object of schedule no.
     </en>*/
    public static final SequenceInfo SCHNO = new SequenceInfo("SCHNO_SEQ", "", "000000000", 1L, 999999999L, 10, true);

    /**<jp>
     * 棚卸スケジュールNoの順序オブジェクトを表すフィールドです。
     </jp>*/
    /**<en>
     * Field: sequence object of invent schedule no.
     </en>*/
    public static final SequenceInfo INVENT_SCHNO = new SequenceInfo("INVENT_SCHNO_SEQ", "", "000000000", 1L,
            999999999L, 10, true);

    /**<jp>
     * 棚卸設定単位キーの順序オブジェクトを表すフィールドです。
     </jp>*/
    /**<en>
     * Field: sequence object of invent setting unitkey.
     </en>*/
    public static final SequenceInfo INVENT_SETUKEY = new SequenceInfo("INVENT_SETUKEY_SEQ", "", "00000000", 1L,
            99999999L, 10, true);

    /**<jp>
     *パレットIDの採番でループする最大回数
     *AWCで考え得る最大の棚数を初期値に設定しました。
     </jp>*/
    /**<en>
     * MAX. number of looping time in getting number for pallet ID
     *The MAX. location number, that AWC could possibly consider, has been set as initial value.
     </en>*/
    public static final int PALLET_COUNT_MAX = 150000;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    // WareNavi3.5.4

    ///** Cache for Handlers */
    //private Map<Class, DatabaseHandler> _handlerCacheMap = null;

    // WareNavi3.5.4
    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * シーケンス用のハンドラを生成します。
     * 
     * @param conn データベースコネクション
     */
    public WMSSequenceHandler(Connection conn)
    {
        super(conn);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * 入荷予定一意キーを取得します。入荷予定一意キーは10桁(作業区分2桁+8桁)の文字列で表されます。（例:0100243070）
     * @return 入荷予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextReceivingPlanUkey()
            throws ReadWriteException
    {
        long seq = getNext(RECEIVINGPLANUKEY);
        return RECEIVINGPLANUKEY.format(seq);
    }

    /**
     * receiving_planukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean receiving_planukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得入荷予定一意キーが入荷予定ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        ReceivingPlanSearchKey ssKey = new ReceivingPlanSearchKey();
        ssKey.setPlanUkey(seqinf.format(next));
        ssKey.setPlanUkeyCollect();

        int cnt = getHandler(ReceivingPlanHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * receiving_planukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal receiving_planukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大入荷予定一意キーを取得します。
        ReceivingPlanSearchKey ssKey = new ReceivingPlanSearchKey();
        ssKey.setPlanUkeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ReceivingPlan[] plan = (ReceivingPlan[])getHandler(ReceivingPlanHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(plan))
        {
            String keyStr = trimPrefix(plan[0].getPlanUkey(), SystemDefine.JOB_TYPE_RECEIVING);
            maxSeq = toBigDecimal(keyStr);
        }
        return maxSeq;
    }

    /**
     * 入庫予定一意キーを取得します。入庫予定一意キーは10桁(作業区分2桁+8桁)の文字列で表されます。（例:0200243070）
     * @return 入庫予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextStoragePlanUkey()
            throws ReadWriteException
    {
        long seq = getNext(STORAGEPLANUKEY);
        return STORAGEPLANUKEY.format(seq);
    }

    /**
     * storage_planukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean storage_planukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得入庫予定一意キーが入庫予定ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        StoragePlanSearchKey ssKey = new StoragePlanSearchKey();
        ssKey.setPlanUkey(seqinf.format(next));
        ssKey.setPlanUkeyCollect();

        int cnt = getHandler(StoragePlanHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * storage_planukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal storage_planukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大入庫予定一意キーを取得します。
        StoragePlanSearchKey ssKey = new StoragePlanSearchKey();
        ssKey.setPlanUkeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        StoragePlan[] storage = (StoragePlan[])getHandler(StoragePlanHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(storage))
        {
            String keyStr = trimPrefix(storage[0].getPlanUkey(), SystemDefine.JOB_TYPE_STORAGE);
            maxSeq = toBigDecimal(keyStr);
        }
        return maxSeq;
    }

    /**
     * 出庫予定一意キーを取得します。出庫予定一意キーは10桁(作業区分2桁+8桁)の文字列で表されます。（例:0300243070）
     * @return 出庫予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextRetrievalPlanUkey()
            throws ReadWriteException
    {
        long seq = getNext(RETRIEVALPLANUKEY);
        return RETRIEVALPLANUKEY.format(seq);
    }

    /**
     * retrieval_planukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean retrieval_planukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得出庫予定一意キーが出庫予定ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        RetrievalPlanSearchKey ssKey = new RetrievalPlanSearchKey();
        ssKey.setPlanUkey(seqinf.format(next));
        ssKey.setPlanUkeyCollect();

        int cnt = getHandler(RetrievalPlanHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * retrieval_planukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal retrieval_planukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大出庫予定一意キーを取得します。
        RetrievalPlanSearchKey ssKey = new RetrievalPlanSearchKey();
        ssKey.setPlanUkeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        RetrievalPlan[] retrieval = (RetrievalPlan[])getHandler(RetrievalPlanHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(retrieval))
        {
            String keyStr = trimPrefix(retrieval[0].getPlanUkey(), SystemDefine.JOB_TYPE_RETRIEVAL);
            maxSeq = toBigDecimal(keyStr);
        }
        return maxSeq;
    }

    /**
     * クロスドック予定一意キーを取得します。クロスドック予定一意キーは10桁(作業区分2桁+8桁)の文字列で表されます。（例:0400243070）
     * @return クロスドック予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextCrossDockPlanUkey()
            throws ReadWriteException
    {
        long seq = getNext(CROSSDOCKPLANUKEY);
        return CROSSDOCKPLANUKEY.format(seq);
    }

    /**
     * cross_dock_planukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean cross_dock_planukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得クロスドック予定一意キーがTC予定情報に存在しないかチェックし
        // 存在すれば、再取得を行う。
        CrossDockPlanSearchKey ssKey = new CrossDockPlanSearchKey();
        ssKey.setPlanUkey(seqinf.format(next));
        ssKey.setPlanUkeyCollect();

        int cnt = getHandler(CrossDockPlanHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * cross_dock_planukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal cross_dock_planukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大クロスドック予定一意キーを取得します。
        CrossDockPlanSearchKey ssKey = new CrossDockPlanSearchKey();
        ssKey.setPlanUkeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        CrossDockPlan[] sorting = (CrossDockPlan[])getHandler(CrossDockPlanHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(sorting))
        {
            String keyStr = trimPrefix(sorting[0].getPlanUkey(), SystemDefine.JOB_TYPE_SORTING);
            maxSeq = toBigDecimal(keyStr);
        }
        return maxSeq;
    }

    /**
     * クロスドック連係キーを取得します。クロスドック連係キーは8桁の文字列で表されます。（例:00289070）
     * @return クロスドック連係キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextCrossDockUkey()
            throws ReadWriteException
    {
        long seq = getNext(CROSSDOCKUKEY);
        return CROSSDOCKUKEY.format(seq);
    }

    /**
     * cross_dock_ukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean cross_dock_ukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // クロスドック連携キーがTC予定情報に存在しないかチェックし
        // 存在すれば、再取得を行う。
        CrossDockPlanSearchKey ssKey = new CrossDockPlanSearchKey();
        ssKey.setCrossDockUkey(seqinf.format(next));
        ssKey.setCrossDockUkeyCollect();

        int cnt = getHandler(CrossDockPlanHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * cross_dock_ukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal cross_dock_ukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大仕分予定一意キーを取得します。
        CrossDockPlanSearchKey ssKey = new CrossDockPlanSearchKey();
        ssKey.setCrossDockUkeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        CrossDockPlan[] sorting = (CrossDockPlan[])getHandler(CrossDockPlanHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(sorting))
        {
            String keyStr = trimPrefix(sorting[0].getCrossDockUkey(), "");
            maxSeq = toBigDecimal(keyStr);
        }
        return maxSeq;
    }

    /**
     * 出荷予定一意キーを取得します。出荷予定一意キーは10桁(作業区分2桁+8桁)の文字列で表されます。（例:0500243070）
     * @return 出荷予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextShipPlanUkey()
            throws ReadWriteException
    {
        long seq = getNext(SHIPPLANUKEY);
        return SHIPPLANUKEY.format(seq);
    }

    /**
     * ship_planukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean ship_planukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得出荷予定一意キーが出荷予定ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        ShipPlanSearchKey ssKey = new ShipPlanSearchKey();
        ssKey.setPlanUkey(seqinf.format(next));
        ssKey.setPlanUkeyCollect();

        int cnt = getHandler(ShipPlanHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * ship_planukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal ship_planukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大出荷予定一意キーを取得します。
        ShipPlanSearchKey ssKey = new ShipPlanSearchKey();
        ssKey.setPlanUkeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ShipPlan[] shipping = (ShipPlan[])getHandler(ShipPlanHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(shipping))
        {
            String keyStr = trimPrefix(shipping[0].getPlanUkey(), SystemDefine.JOB_TYPE_SHIPPING);
            maxSeq = toBigDecimal(keyStr);
        }
        return maxSeq;
    }

    /**
     * 再入庫予定一意キーを取得します。再入庫予定一意キーは10桁(作業区分2桁+8桁)の文字列で表されます。（例:2700243070）
     * @return 再入庫予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextReStoringPlanUkey()
            throws ReadWriteException
    {
        long seq = getNext(RESTORINGPLANUKEY);
        return RESTORINGPLANUKEY.format(seq);
    }

    /**
     * restoring_planukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean restoring_planukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得再入庫予定一意キーが再入庫予定ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        ReStoringPlanSearchKey ssKey = new ReStoringPlanSearchKey();
        ssKey.setPlanUkey(seqinf.format(next));
        ssKey.setPlanUkeyCollect();

        int cnt = getHandler(ReStoringPlanHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * restoring_planukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal restoring_planukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大再入庫予定一意キーを取得します。
        ReStoringPlanSearchKey ssKey = new ReStoringPlanSearchKey();
        ssKey.setPlanUkeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ReStoringPlan[] restoring = (ReStoringPlan[])getHandler(ReStoringPlanHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(restoring))
        {
            String keyStr = trimPrefix(restoring[0].getPlanUkey(), SystemDefine.JOB_TYPE_RESTORING);
            maxSeq = toBigDecimal(keyStr);
        }
        return maxSeq;
    }

    /**
     * 在庫IDを取得します。在庫IDは8桁の文字列で表されます。（例:ST023070）
     * @return 在庫ID
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextStockId()
            throws ReadWriteException
    {
        long seq = getNext(STOCKID);
        return STOCKID.format(seq);
    }

    /**
     * stock_id_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean stock_id_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得在庫ＩＤが在庫ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        StockSearchKey ssKey = new StockSearchKey();
        ssKey.clear();
        ssKey.setStockId(seqinf.format(next));
        ssKey.setStockIdCollect();

        int cnt = getHandler(StockHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * stock_id_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal stock_id_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大在庫IDを取得します。
        StockSearchKey ssKey = new StockSearchKey();
        ssKey.setStockIdCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        Stock[] stock = (Stock[])getHandler(StockHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(stock))
        {
            maxSeq = toBigDecimal(stock[0].getStockId());
        }
        return maxSeq;
    }

    /**
     * 入荷作業設定単位キーを取得します。入荷作業設定単位キーは8桁の文字列で表されます。（例:00243070）
     * @return 入荷作業設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextReceivingSetUkey()
            throws ReadWriteException
    {
        long seq = getNext(RECEIVINGSETUKEY);
        return RECEIVINGSETUKEY.format(seq);
    }

    /**
     * receiving_setukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean receiving_setukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得入荷作業設定単位キーが入荷作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        ReceivingWorkInfoSearchKey ssKey = new ReceivingWorkInfoSearchKey();
        ssKey.setSettingUnitKey(seqinf.format(next));
        ssKey.setSettingUnitKeyCollect();

        int cnt = getHandler(ReceivingWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * receiving_setukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal receiving_setukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大入荷作業設定単位キーを取得します。
        ReceivingWorkInfoSearchKey ssKey = new ReceivingWorkInfoSearchKey();
        ssKey.setSettingUnitKeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ReceivingWorkInfo[] work = (ReceivingWorkInfo[])getHandler(ReceivingWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getSettingUnitKey());
        }
        return maxSeq;
    }

    /**
     * 入出庫作業設定単位キーを取得します。入庫作業設定単位キーは8桁の文字列で表されます。（例:00243070）
     * @return 入出庫作業設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextWorkInfoSetUkey()
            throws ReadWriteException
    {
        long seq = getNext(WORKINFOSETUKEY);
        return WORKINFOSETUKEY.format(seq);
    }

    /**
     * workinfo_setukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean workinfo_setukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得入出庫作業設定単位キーが入出庫作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        // 作業リストをチェック
        WorkListSearchKey sKey = new WorkListSearchKey();
        sKey.setSettingUnitKey(seqinf.format(next));
        sKey.setSettingUnitKeyCollect();

        int cnt = getHandler(WorkListHandler.class, conn).count(sKey);

        if (cnt != 0)
        {
            return false;
        }

        // 作業情報をチェック
        WorkInfoSearchKey ssKey = new WorkInfoSearchKey();
        ssKey.setSettingUnitKey(seqinf.format(next));
        ssKey.setSettingUnitKeyCollect();

        cnt = getHandler(WorkInfoHandler.class, conn).count(ssKey);

        return (cnt == 0);
    }

    /**
     * workinfo_setukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal workinfo_setukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 作業リストのチェック
        WorkListSearchKey sKey = new WorkListSearchKey();
        sKey.setSettingUnitKeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        WorkList[] worklist = (WorkList[])getHandler(WorkListHandler.class, conn).find(sKey);
        if (!ArrayUtil.isEmpty(worklist))
        {
            maxSeq = toBigDecimal(worklist[0].getSettingUnitKey());
        }

        // 最大入出庫作業設定単位キーを取得します。
        WorkInfoSearchKey ssKey = new WorkInfoSearchKey();
        ssKey.setSettingUnitKeyCollect("MAX");

        WorkInfo[] work = (WorkInfo[])getHandler(WorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            if (0 > maxSeq.compareTo(toBigDecimal(work[0].getSettingUnitKey())))
            {
                maxSeq = toBigDecimal(work[0].getSettingUnitKey());
            }
        }
        return maxSeq;
    }

    /**
     * 仕分作業設定単位キーを取得します。仕分作業設定単位キーは8桁の文字列で表されます。（例:00243070）
     * @return 仕分作業設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextSortSetUkey()
            throws ReadWriteException
    {
        long seq = getNext(SORTSETUKEY);
        return SORTSETUKEY.format(seq);
    }

    /**
     * sort_setukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean sort_setukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得仕分作業設定単位キーが仕分作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        SortWorkInfoSearchKey ssKey = new SortWorkInfoSearchKey();
        ssKey.setSettingUnitKey(seqinf.format(next));
        ssKey.setSettingUnitKeyCollect();

        int cnt = getHandler(SortWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * sort_setukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal sort_setukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大仕分作業設定単位キーを取得します。
        SortWorkInfoSearchKey ssKey = new SortWorkInfoSearchKey();
        ssKey.setSettingUnitKeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        SortWorkInfo[] work = (SortWorkInfo[])getHandler(SortWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getSettingUnitKey());
        }
        return maxSeq;
    }

    /**
     * 出荷作業設定単位キーを取得します。出荷作業設定単位キーは8桁の文字列で表されます。（例:00243070）
     * @return 出荷作業設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextShipSetUkey()
            throws ReadWriteException
    {
        long seq = getNext(SHIPSETUKEY);
        return SHIPSETUKEY.format(seq);
    }

    /**
     * ship_setukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean ship_setukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得出荷作業設定単位キーが出荷作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        ShipWorkInfoSearchKey ssKey = new ShipWorkInfoSearchKey();
        ssKey.setSettingUnitKey(seqinf.format(next));
        ssKey.setSettingUnitKeyCollect();

        int cnt = getHandler(ShipWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * ship_setukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal ship_setukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大出荷作業設定単位キーを取得します。
        ShipWorkInfoSearchKey ssKey = new ShipWorkInfoSearchKey();
        ssKey.setSettingUnitKeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ShipWorkInfo[] work = (ShipWorkInfo[])getHandler(ShipWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getSettingUnitKey());
        }
        return maxSeq;
    }

    /**
     * 移動作業設定単位キーを取得します。移動作業設定単位キーは8桁の文字列で表されます。（例:00243070）
     * @return 移動作業設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextMoveSetUkey()
            throws ReadWriteException
    {
        long seq = getNext(MOVESETUKEY);
        return MOVESETUKEY.format(seq);
    }

    /**
     * move_setukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean move_setukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得移動作業設定単位キーが移動作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        MoveWorkInfoSearchKey ssKey = new MoveWorkInfoSearchKey();
        ssKey.setSettingUnitKey(seqinf.format(next));
        ssKey.setSettingUnitKeyCollect();

        int cnt = getHandler(MoveWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * move_setukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal move_setukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大移動作業設定単位キーを取得します。
        MoveWorkInfoSearchKey ssKey = new MoveWorkInfoSearchKey();
        ssKey.setSettingUnitKeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        MoveWorkInfo[] work = (MoveWorkInfo[])getHandler(MoveWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getSettingUnitKey());
        }
        return maxSeq;
    }

    /**
     * 入荷作業作業No.を取得します。入荷作業作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 入荷作業作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextReceivingJobNo()
            throws ReadWriteException
    {
        long seq = getNext(RECEIVINGJOBNO);
        return RECEIVINGJOBNO.format(seq);
    }

    /**
     * receiving_jobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean receiving_jobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得入荷作業作業No.が入荷作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        ReceivingWorkInfoSearchKey ssKey = new ReceivingWorkInfoSearchKey();
        ssKey.setJobNo(seqinf.format(next));
        ssKey.setJobNoCollect();

        int cnt = getHandler(ReceivingWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * receiving_jobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal receiving_jobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大入荷作業作業No.を取得します。
        ReceivingWorkInfoSearchKey ssKey = new ReceivingWorkInfoSearchKey();
        ssKey.setJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ReceivingWorkInfo[] work = (ReceivingWorkInfo[])getHandler(ReceivingWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getJobNo());
        }
        return maxSeq;
    }

    /**
     * 入出庫作業作業No.を取得します。入庫作業作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 入出庫作業作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextWorkInfoJobNo()
            throws ReadWriteException
    {
        long seq = getNext(WORKINFOJOBNO);
        return WORKINFOJOBNO.format(seq);
    }

    /**
     * workinfo_jobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean workinfo_jobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得入出庫作業作業No.が入出庫作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        WorkInfoSearchKey ssKey = new WorkInfoSearchKey();
        ssKey.setJobNo(seqinf.format(next));
        ssKey.setJobNoCollect();

        int cnt = getHandler(WorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * workinfo_jobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal workinfo_jobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大入出庫作業作業No.を取得します。
        WorkInfoSearchKey ssKey = new WorkInfoSearchKey();
        ssKey.setJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        WorkInfo[] work = (WorkInfo[])getHandler(WorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getJobNo());
        }
        return maxSeq;
    }

    /**
     * 仕分作業作業No.を取得します。仕分作業作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 仕分作業作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextSortJobNo()
            throws ReadWriteException
    {
        long seq = getNext(SORTJOBNO);
        return SORTJOBNO.format(seq);
    }

    /**
     * sort_jobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean sort_jobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得仕分作業作業No.が仕分作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        SortWorkInfoSearchKey ssKey = new SortWorkInfoSearchKey();
        ssKey.setJobNo(seqinf.format(next));
        ssKey.setJobNoCollect();

        int cnt = getHandler(SortWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * sort_jobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal sort_jobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大仕分作業作業No.を取得します。
        SortWorkInfoSearchKey ssKey = new SortWorkInfoSearchKey();
        ssKey.setJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        SortWorkInfo[] work = (SortWorkInfo[])getHandler(SortWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getJobNo());
        }
        return maxSeq;
    }

    /**
     * 出荷作業作業No.を取得します。出荷作業作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 出荷作業作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextShipJobNo()
            throws ReadWriteException
    {
        long seq = getNext(SHIPJOBNO);
        return SHIPJOBNO.format(seq);
    }

    /**
     * ship_jobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean ship_jobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得出荷作業作業No.が出荷作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        ShipWorkInfoSearchKey ssKey = new ShipWorkInfoSearchKey();
        ssKey.setJobNo(seqinf.format(next));
        ssKey.setJobNoCollect();

        int cnt = getHandler(ShipWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * ship_jobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal ship_jobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大出荷作業作業No.を取得します。
        ShipWorkInfoSearchKey ssKey = new ShipWorkInfoSearchKey();
        ssKey.setJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ShipWorkInfo[] work = (ShipWorkInfo[])getHandler(ShipWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getJobNo());
        }
        return maxSeq;
    }

    /**
     * 移動作業作業No.を取得します。移動作業作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 移動作業作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextMoveJobNo()
            throws ReadWriteException
    {
        long seq = getNext(MOVEJOBNO);
        return MOVEJOBNO.format(seq);
    }

    /**
     * move_jobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean move_jobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得移動作業作業No.が移動作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        MoveWorkInfoSearchKey ssKey = new MoveWorkInfoSearchKey();
        ssKey.setJobNo(seqinf.format(next));
        ssKey.setJobNoCollect();

        int cnt = getHandler(MoveWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * move_jobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal move_jobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大移動作業作業No.を取得します。
        MoveWorkInfoSearchKey ssKey = new MoveWorkInfoSearchKey();
        ssKey.setJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        MoveWorkInfo[] work = (MoveWorkInfo[])getHandler(MoveWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getJobNo());
        }
        return maxSeq;
    }

    /**
     * 棚卸作業作業No.を取得します。棚卸作業作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 棚卸作業作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextInventJobNo()
            throws ReadWriteException
    {
        long seq = getNext(INVENTJOBNO);
        return INVENTJOBNO.format(seq);
    }

    /**
     * invent_jobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean invent_jobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得棚卸作業作業No.が棚卸作業ファイル、棚卸実績ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        InventWorkInfoSearchKey ssKey = new InventWorkInfoSearchKey();
        ssKey.setJobNo(seqinf.format(next));
        ssKey.setJobNoCollect();

        InventResultSearchKey srKey = new InventResultSearchKey();
        srKey.setJobNo(seqinf.format(next));
        srKey.setJobNoCollect();

        int cntWork = getHandler(InventWorkInfoHandler.class, conn).count(ssKey);
        int cntResult = getHandler(InventResultHandler.class, conn).count(srKey);

        return (cntWork == 0 && cntResult == 0);
    }

    /**
     * invent_jobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal invent_jobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大棚卸作業作業No.を取得します。
        InventWorkInfoSearchKey ssKey = new InventWorkInfoSearchKey();
        ssKey.setJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        InventWorkInfo[] work = (InventWorkInfo[])getHandler(InventWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getJobNo());
        }
        // 棚卸実績情報の最大値も確認する。
        InventResultSearchKey srKey = new InventResultSearchKey();
        srKey.setJobNoCollect("MAX");

        BigDecimal maxSeqResult = BigDecimal.ZERO;
        InventResult[] result = (InventResult[])getHandler(InventResultHandler.class, conn).find(srKey);
        if (!ArrayUtil.isEmpty(result))
        {
            maxSeqResult = toBigDecimal(result[0].getJobNo());
        }
        if (maxSeqResult.longValue() > maxSeq.longValue())
        {
            return maxSeqResult;
        }
        else
        {
            return maxSeq;
        }
    }

    /**
     * 入荷作業集約作業No.を取得します。入荷作業集約作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 入荷作業集約作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextReceivingCollectJobNo()
            throws ReadWriteException
    {
        long seq = getNext(RECEIVINGCOLLECTJOBNO);
        return RECEIVINGCOLLECTJOBNO.format(seq);
    }

    /**
     * receiving_collectjobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean receiving_collectjobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得入荷作業集約作業No.が入荷作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        ReceivingWorkInfoSearchKey ssKey = new ReceivingWorkInfoSearchKey();
        ssKey.setCollectJobNo(seqinf.format(next));
        ssKey.setCollectJobNoCollect();

        int cnt = getHandler(ReceivingWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * receiving_collectjobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal receiving_collectjobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大入荷作業集約作業No.を取得します。
        ReceivingWorkInfoSearchKey ssKey = new ReceivingWorkInfoSearchKey();
        ssKey.setCollectJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ReceivingWorkInfo[] work = (ReceivingWorkInfo[])getHandler(ReceivingWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getCollectJobNo());
        }
        return maxSeq;
    }

    /**
     * 入出庫作業集約作業No.を取得します。入庫作業集約作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 入出庫作業集約作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextWorkInfoCollectJobNo()
            throws ReadWriteException
    {
        long seq = getNext(WORKINFOCOLLECTJOBNO);
        return WORKINFOCOLLECTJOBNO.format(seq);
    }

    /**
     * workinfo_collectjobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean workinfo_collectjobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得入出庫作業集約作業No.が入出庫作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        WorkInfoSearchKey ssKey = new WorkInfoSearchKey();
        ssKey.setCollectJobNo(seqinf.format(next));
        ssKey.setCollectJobNoCollect();

        int cnt = getHandler(WorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * workinfo_collectjobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal workinfo_collectjobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大入出庫作業集約作業No.を取得します。
        WorkInfoSearchKey ssKey = new WorkInfoSearchKey();
        ssKey.setCollectJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        WorkInfo[] work = (WorkInfo[])getHandler(WorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getCollectJobNo());
        }
        return maxSeq;
    }

    /**
     * 仕分作業集約作業No.を取得します。仕分作業集約作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 仕分作業集約作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextSortCollectJobNo()
            throws ReadWriteException
    {
        long seq = getNext(SORTCOLLECTJOBNO);
        return SORTCOLLECTJOBNO.format(seq);
    }

    /**
     * sort_collectjobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean sort_collectjobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得仕分作業集約作業No.が仕分作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        SortWorkInfoSearchKey ssKey = new SortWorkInfoSearchKey();
        ssKey.setCollectJobNo(seqinf.format(next));
        ssKey.setCollectJobNoCollect();

        int cnt = getHandler(SortWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * sort_collectjobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal sort_collectjobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大仕分作業集約作業No.を取得します。
        SortWorkInfoSearchKey ssKey = new SortWorkInfoSearchKey();
        ssKey.setCollectJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        SortWorkInfo[] work = (SortWorkInfo[])getHandler(SortWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getCollectJobNo());
        }
        return maxSeq;
    }

    /**
     * 出荷作業集約作業No.を取得します。出荷作業集約作業No.は8桁の文字列で表されます。（例:00243070）
     * @return 出荷作業集約作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextShipCollectJobNo()
            throws ReadWriteException
    {
        long seq = getNext(SHIPCOLLECTJOBNO);
        return SHIPCOLLECTJOBNO.format(seq);
    }

    /**
     * ship_collectjobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean ship_collectjobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得出荷作業集約作業No.が出荷作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        ShipWorkInfoSearchKey ssKey = new ShipWorkInfoSearchKey();
        ssKey.setCollectJobNo(seqinf.format(next));
        ssKey.setCollectJobNoCollect();

        int cnt = getHandler(ShipWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * ship_collectjobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal ship_collectjobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大出荷作業集約作業No.を取得します。
        ShipWorkInfoSearchKey ssKey = new ShipWorkInfoSearchKey();
        ssKey.setCollectJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ShipWorkInfo[] work = (ShipWorkInfo[])getHandler(ShipWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getCollectJobNo());
        }
        return maxSeq;
    }

    /**
     * 出庫開始単位キーを取得します。出庫開始単位キーは8桁の文字列で表されます。（例:00243070）
     * @return 出庫開始単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextStartUnitKey()
            throws ReadWriteException
    {
        long seq = getNext(STARTUNITKEY);
        return STARTUNITKEY.format(seq);
    }

    /**
     * start_unit_key_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean start_unit_key_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 出庫開始単位キーが欠品情報に存在しないかチェックし
        // 存在すれば、再取得を行う。
        ShortageInfoSearchKey siKey = new ShortageInfoSearchKey();
        siKey.setStartUnitKey(seqinf.format(next));
        siKey.setStartUnitKeyCollect();

        int cnt = getHandler(ShortageInfoHandler.class, conn).count(siKey);
        return (cnt == 0);
    }

    /**
     * start_unit_key_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal start_unit_key_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 出庫開始単位キーーを取得します。
        ShortageInfoSearchKey siKey = new ShortageInfoSearchKey();
        siKey.setStartUnitKeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        ShortageInfo[] siInfos = (ShortageInfo[])getHandler(ShortageInfoHandler.class, conn).find(siKey);
        if (!ArrayUtil.isEmpty(siInfos))
        {
            maxSeq = toBigDecimal(siInfos[0].getStartUnitKey());
        }
        return maxSeq;
    }

    /**<jp>
     * 帳票用のレポートNoを取得します。
     * @return レポートNo
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Gets the report no. for document.
     * @return report no.
     * @throws ReadWriteException
     </en>*/
    public int nextReportNo()
            throws ReadWriteException
    {
        return (int)getNext(REPORTNO);
    }

    /**
     * reportno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean reportno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // チェックを行わないためtrueを返します。
        return true;
    }

    /**
     * reportno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal reportno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // チェックを行わないためSequenceの最小値を返します。
        return BigDecimal.ONE;
    }

    /**<jp>
     * 搬送Keyを取得します。搬送Keyは8桁の文字列で表されます。（例: "00243070"）
     * @return 搬送key
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Gets CarryKey. This Key is to be represented by a string of 8 digits. (e.g., 00243070)
     * @return Carry Key
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String nextCarryKey()
            throws ReadWriteException
    {
        long seq = getNext(CARRYKEY);
        return CARRYKEY.format(seq);
    }

    /**
     * carrykey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean carrykey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        //<jp>同一搬送キーが見つからない場合, OK</jp>
        //<en>If the same carry key cannot be found, it can use.</en>
        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        ssKey.setCarryKey(seqinf.format(next));
        ssKey.setCarryKeyCollect();

        WorkInfoSearchKey wsKey = new WorkInfoSearchKey();
        wsKey.setSystemConnKey(seqinf.format(next));
        wsKey.setSystemConnKeyCollect();

        int cntCarry = getHandler(CarryInfoHandler.class, conn).count(ssKey);
        int cntWork = getHandler(WorkInfoHandler.class, conn).count(wsKey);

        return (cntCarry == 0 && cntWork == 0);
    }

    /**
     * carrykey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal carrykey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大搬送キーを取得します。
        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        ssKey.setCarryKeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        CarryInfo[] carry = (CarryInfo[])getHandler(CarryInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(carry))
        {
            maxSeq = toBigDecimal(carry[0].getCarryKey());
        }

        // 最大搬送キーを取得します。
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setSystemConnKeyCollect("MAX");

        BigDecimal maxSeqWork = BigDecimal.ZERO;
        WorkInfo[] work = (WorkInfo[])getHandler(WorkInfoHandler.class, conn).find(wiKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeqWork = toBigDecimal(work[0].getSystemConnKey());
        }
        if (maxSeqWork.longValue() > maxSeq.longValue())
        {
            return maxSeqWork;
        }
        else
        {
            return maxSeq;
        }
    }

    /**<jp>
     * パレットIDを取得します。
     * @return パレットID
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Gets pallet ID.
     * @return Pallet ID
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String nextPalletId()
            throws ReadWriteException
    {
        long seq = getNext(PALLETID, PALLET_COUNT_MAX);
        return PALLETID.format(seq);
    }

    /**
     * palletid_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean palletid_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        //<jp>同一パレットIDが見つからない場合, OK</jp>
        //<en>If the same pallet ID cannot be found, it can use.</en>
        PalletSearchKey ssKey = new PalletSearchKey();
        ssKey.setPalletId(seqinf.format(next));
        ssKey.setPalletIdCollect();

        int cnt = getHandler(PalletHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * palletid_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal palletid_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大パレットIDを取得します。
        PalletSearchKey ssKey = new PalletSearchKey();
        ssKey.setPalletIdCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        Pallet[] pallet = (Pallet[])getHandler(PalletHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(pallet))
        {
            maxSeq = toBigDecimal(pallet[0].getPalletId());
        }
        return maxSeq;
    }

    /**<jp>
     * 入庫、出庫共通の作業Noを取得します。作業Noは8桁の文字列で表されます。（例: "00000001"）
     * @return 作業No
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Gets common work no. for storage and retrrieval. This work no. is to be represented 
     * as an 8 digit string. (e.g., 00000001)
     * @return work no.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String nextWorkNo()
            throws ReadWriteException
    {
        long seq = getNext(WORKNO);
        return WORKNO.format(seq);
    }

    /**
     * workno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean workno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        //<jp>同一作業No.が見つからない場合, OK</jp>
        //<en>If the same work no. cannot be found, it can use.</en>
        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        ssKey.setWorkNo(seqinf.format(next));
        ssKey.setWorkNoCollect();

        int cnt = getHandler(CarryInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * workno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal workno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大作業No.を取得します。
        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        DecimalFormat rangeFormat = new DecimalFormat(WORKNO_FORMAT);
        ssKey.setWorkNo(rangeFormat.format(WORKNO_MIN), ">=");
        ssKey.setWorkNo(rangeFormat.format(WORKNO_MAX), "<=");
        ssKey.setWorkNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        CarryInfo[] carry = (CarryInfo[])getHandler(CarryInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(carry))
        {
            maxSeq = toBigDecimal(carry[0].getWorkNo());
        }
        return maxSeq;
    }

    /**<jp>
     * 入庫作業Noを取得します。作業Noは8桁の文字列で表されます。（例: "10000001"）
     * @return 入庫作業No
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Gets storage work no. This no. is to be represented as an 8 digit string. (e.g., 10000001)
     * @return storage work no.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String nextStorageWorkNo()
            throws ReadWriteException
    {
        long seq = getNext(STORAGE_WORKNO);
        return STORAGE_WORKNO.format(seq);
    }

    /**
     * storage_workno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean storage_workno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        //<jp>同一作業No.が見つからない場合, OK</jp>
        //<en>If the same work no. cannot be found, it can use.</en>
        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        ssKey.setWorkNo(seqinf.format(next));
        ssKey.setWorkNoCollect();

        int cnt = getHandler(CarryInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * storage_workno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal storage_workno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大入庫作業No.を取得します。
        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        DecimalFormat rangeFormat = new DecimalFormat(WORKNO_FORMAT);
        ssKey.setWorkNo(rangeFormat.format(STORAGE_WORKNO_MIN), ">=");
        ssKey.setWorkNo(rangeFormat.format(STORAGE_WORKNO_MAX), "<=");
        ssKey.setWorkNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        CarryInfo[] carry = (CarryInfo[])getHandler(CarryInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(carry))
        {
            maxSeq = toBigDecimal(carry[0].getWorkNo());
        }
        return maxSeq;
    }

    /**<jp>
     * 出庫作業Noを取得します。作業Noは8桁の文字列で表されます。（例: "20000001"）
     * @return 出庫作業No
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Gets retrieval work no. This work no. is to be represented as a string of 8digits. (e.g., 20000001)
     * @return retrieval work no.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String nextRetrievalWorkNo()
            throws ReadWriteException
    {
        long seq = getNext(RETRIEVAL_WORKNO);
        return RETRIEVAL_WORKNO.format(seq);
    }

    /**
     * retrieval_workno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean retrieval_workno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        //<jp>同一作業No.が見つからない場合, OK</jp>
        //<en>If the same work no. cannot be found, it can use.</en>
        int cnt = 0;
        try
        {
            if (new WarenaviSystemController(conn, getClass()).isFaDaEnabled())
            {
                ReStoringPlanSearchKey rskey = new ReStoringPlanSearchKey();
                rskey.setWorkNo(seqinf.format(next));
                rskey.setWorkNoCollect();

                cnt = getHandler(ReStoringPlanHandler.class, conn).count(rskey);

                if (cnt != 0)
                {
                    return false;
                }
            }
        }
        catch (ScheduleException e)
        {
            throw new ReadWriteException(e);
        }

        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        ssKey.setWorkNo(seqinf.format(next));
        ssKey.setWorkNoCollect();

        cnt = getHandler(CarryInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * retrieval_workno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal retrieval_workno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大出庫作業No.を取得します。
        BigDecimal maxSeq = BigDecimal.ZERO;
        DecimalFormat rangeFormat = new DecimalFormat(WORKNO_FORMAT);
        try
        {
            if (new WarenaviSystemController(conn, getClass()).isFaDaEnabled())
            {
                ReStoringPlanSearchKey rskey = new ReStoringPlanSearchKey();
                rskey.setWorkNo(rangeFormat.format(RETRIEVAL_WORKNO_MIN), ">=");
                rskey.setWorkNo(rangeFormat.format(RETRIEVAL_WORKNO_MAX), "<=");
                rskey.setWorkNoCollect("MAX");

                ReStoringPlan[] restoring = (ReStoringPlan[])getHandler(ReStoringPlanHandler.class, conn).find(rskey);
                if (!ArrayUtil.isEmpty(restoring))
                {
                    maxSeq = toBigDecimal(restoring[0].getWorkNo());
                }
            }
        }
        catch (ScheduleException e)
        {
            throw new ReadWriteException(e);
        }

        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        ssKey.setWorkNo(rangeFormat.format(RETRIEVAL_WORKNO_MIN), ">=");
        ssKey.setWorkNo(rangeFormat.format(RETRIEVAL_WORKNO_MAX), "<=");
        ssKey.setWorkNoCollect("MAX");

        CarryInfo[] carry = (CarryInfo[])getHandler(CarryInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(carry))
        {
            if (0 > maxSeq.compareTo(toBigDecimal(carry[0].getWorkNo())))
            {
                maxSeq = toBigDecimal(carry[0].getWorkNo());
            }
        }
        return maxSeq;
    }

    /**<jp>
     * スケジュールNoを取得します。スケジュールNoは10桁の文字列で表されます。
     * @return スケジュールNo
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Gets schedule no. the schedule no. is to be represented as a string of 10 digits.
     * @return schedule no.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String nextScheduleNo()
            throws ReadWriteException
    {
        long seq = getNext(SCHNO);

        return SCHNO.format(seq);
    }

    /**
     * schno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean schno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        //<jp>同一スケジュールNo.が見つからない場合, OK</jp>
        //<en>If the same schedule no. cannot be found, it can use.</en>
        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        ssKey.setScheduleNo(seqinf.format(next));
        ssKey.setScheduleNoCollect();

        InventoryCheckSearchKey isKey = new InventoryCheckSearchKey();
        isKey.setScheduleNo(seqinf.format(next));
        isKey.setScheduleNoCollect();

        int cntCarry = getHandler(CarryInfoHandler.class, conn).count(ssKey);
        int cntInvent = getHandler(InventoryCheckHandler.class, conn).count(isKey);

        return (cntCarry == 0 && cntInvent == 0);
    }

    /**
     * schno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal schno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大スケジュールNo.を取得します。
        CarryInfoSearchKey ssKey = new CarryInfoSearchKey();
        ssKey.setScheduleNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        CarryInfo[] carry = (CarryInfo[])getHandler(CarryInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(carry))
        {
            maxSeq = toBigDecimal(carry[0].getScheduleNo());
        }
        InventoryCheckSearchKey isKey = new InventoryCheckSearchKey();
        isKey.setScheduleNoCollect("MAX");

        BigDecimal maxSeqCheck = BigDecimal.ZERO;
        InventoryCheck[] inventory = (InventoryCheck[])getHandler(InventoryCheckHandler.class, conn).find(isKey);
        if (!ArrayUtil.isEmpty(inventory))
        {
            maxSeqCheck = toBigDecimal(inventory[0].getScheduleNo());
        }
        if (maxSeqCheck.longValue() > maxSeq.longValue())
        {
            return maxSeqCheck;
        }
        else
        {
            return maxSeq;
        }
    }

    /**<jp>
     * 棚卸スケジュールNoを取得します。スケジュールNoは10桁の文字列で表されます。
     * @return スケジュールNo
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Gets schedule no. the schedule no. is to be represented as a string of 10 digits.
     * @return schedule no.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String nextInventScheduleNo()
            throws ReadWriteException
    {
        long seq = getNext(INVENT_SCHNO);
        return INVENT_SCHNO.format(seq);
    }

    /**
     * invent_schno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean invent_schno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        //<jp>同一スケジュールNo.が見つからない場合, OK</jp>
        //<en>If the same schedule no. cannot be found, it can use.</en>
        InventSettingSearchKey ssKey = new InventSettingSearchKey();
        ssKey.setScheduleNo(seqinf.format(next));
        ssKey.setScheduleNoCollect();

        int cnt = getHandler(InventSettingHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * invent_schno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal invent_schno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大スケジュールNo.を取得します。
        InventSettingSearchKey ssKey = new InventSettingSearchKey();
        ssKey.setScheduleNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        InventSetting[] invent = (InventSetting[])getHandler(InventSettingHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(invent))
        {
            maxSeq = toBigDecimal(invent[0].getScheduleNo());
        }
        return maxSeq;
    }

    /**
     * 棚卸作業設定単位キーを取得します。棚卸作業設定単位キーは8桁の文字列で表されます。（例:00243070）
     * @return 棚卸作業設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextInventSetUkey()
            throws ReadWriteException
    {
        long seq = getNext(INVENT_SETUKEY);
        return INVENT_SETUKEY.format(seq);
    }

    /**
     * invent_setukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean invent_setukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得棚卸作業設定単位キーが棚卸作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        InventWorkInfoSearchKey ssKey = new InventWorkInfoSearchKey();
        ssKey.setSettingUnitKey(seqinf.format(next));
        ssKey.setSettingUnitKeyCollect();

        int cnt = getHandler(InventWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * invent_setukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal invent_setukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大棚卸作業設定単位キーを取得します。
        InventWorkInfoSearchKey ssKey = new InventWorkInfoSearchKey();
        ssKey.setSettingUnitKeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        InventWorkInfo[] work = (InventWorkInfo[])getHandler(InventWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getSettingUnitKey());
        }
        return maxSeq;
    }

    /* Pカート用Sequence */
    /**
     * PCT出庫予定一意キーを取得します。PCT出庫予定一意キーは10桁(作業区分2桁+8桁)の文字列で表されます。（例:0300243070）
     * @return PCT出庫予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextPCTRetPlanUkey()
            throws ReadWriteException
    {
        long seq = getNext(PCTRETPLANUKEY);
        return PCTRETPLANUKEY.format(seq);
    }

    /**
     * pctret_planukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean pctret_planukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得PCT出庫予定一意キーが出庫予定ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        PCTRetPlanSearchKey ssKey = new PCTRetPlanSearchKey();
        ssKey.setPlanUkey(seqinf.format(next));
        ssKey.setPlanUkeyCollect();

        int cnt = getHandler(PCTRetPlanHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * pctret_planukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal pctret_planukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大PCT出庫予定一意キーを取得します。
        PCTRetPlanSearchKey ssKey = new PCTRetPlanSearchKey();
        ssKey.setPlanUkeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        PCTRetPlan[] retrieval = (PCTRetPlan[])getHandler(PCTRetPlanHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(retrieval))
        {
            String keyStr = trimPrefix(retrieval[0].getPlanUkey(), SystemDefine.JOB_TYPE_RETRIEVAL);
            maxSeq = toBigDecimal(keyStr);
        }
        return maxSeq;
    }

    /**
     * PCT出庫作業作業No.を取得します。PCT出庫作業作業No.は8桁の文字列で表されます。（例:00243070）
     * @return PCT出庫作業作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextPCTRetWorkInfoJobNo()
            throws ReadWriteException
    {
        long seq = getNext(PCTRETWORKINFOJOBNO);
        return PCTRETWORKINFOJOBNO.format(seq);
    }

    /**
     * pctretworkinfo_jobno_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean pctretworkinfo_jobno_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得PCT出庫作業作業No.が入出庫作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        PCTRetWorkInfoSearchKey ssKey = new PCTRetWorkInfoSearchKey();
        ssKey.setJobNo(seqinf.format(next));
        ssKey.setJobNoCollect();

        int cnt = getHandler(PCTRetWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * pctretworkinfo_jobno_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal pctretworkinfo_jobno_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大PCT出庫作業作業No.を取得します。
        PCTRetWorkInfoSearchKey ssKey = new PCTRetWorkInfoSearchKey();
        ssKey.setJobNoCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        PCTRetWorkInfo[] work = (PCTRetWorkInfo[])getHandler(PCTRetWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getJobNo());
        }
        return maxSeq;
    }

    /**
     * PCT出庫作業設定単位キーを取得します。PCT出庫作業設定単位キーは8桁の文字列で表されます。（例:00243070）
     * @return PCT出庫作業設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String nextPCTRetWorkInfoSetUkey()
            throws ReadWriteException
    {
        long seq = getNext(PCTRETWORKINFOSETUKEY);
        return PCTRETWORKINFOSETUKEY.format(seq);
    }

    /**
     * pctretworkinfo_setukey_seq用チェッカメソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @param next チェック対象のシーケンス番号
     * @return 使用可能なときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean pctretworkinfo_setukey_seq_check(Connection conn, SequenceInfo seqinf, long next)
            throws ReadWriteException
    {
        // 取得入出庫作業設定単位キーが入出庫作業ファイルに存在しないかチェックし
        // 存在すれば、再取得を行う。
        PCTRetWorkInfoSearchKey ssKey = new PCTRetWorkInfoSearchKey();
        ssKey.setSettingUnitKey(seqinf.format(next));
        ssKey.setSettingUnitKeyCollect();

        int cnt = getHandler(PCTRetWorkInfoHandler.class, conn).count(ssKey);
        return (cnt == 0);
    }

    /**
     * pctretworkinfo_setukey_seq用最大シーケンス取得メソッド。
     * 
     * @param conn データベースコネクション
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public BigDecimal pctretworkinfo_setukey_seq_max(Connection conn, SequenceInfo seqinf)
            throws ReadWriteException
    {
        // 最大入出庫作業設定単位キーを取得します。
        PCTRetWorkInfoSearchKey ssKey = new PCTRetWorkInfoSearchKey();
        ssKey.setSettingUnitKeyCollect("MAX");

        BigDecimal maxSeq = BigDecimal.ZERO;
        PCTRetWorkInfo[] work = (PCTRetWorkInfo[])getHandler(PCTRetWorkInfoHandler.class, conn).find(ssKey);
        if (!ArrayUtil.isEmpty(work))
        {
            maxSeq = toBigDecimal(work[0].getSettingUnitKey());
        }
        return maxSeq;
    }

    //------------------------------------------------------------
    // reset a sequence methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**<jp>
     * 入庫、出庫共通の作業No sequenceのリセットを行います。
     * 日締め処理で使用します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Resetting Sequence of common work no. for storage and retrrieval 
     * This is used in daily transactions.
     * @throws ReadWriteException
     </en>*/
    public void resetWorkNo()
            throws ReadWriteException
    {
        reset(WORKNO);
    }

    /**<jp>
     * 作業No（入庫用)sequenceのリセットを行います。
     * 日締め処理で使用します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Resetting of Storage work no Sequence
     * This is used in daily transactions.
     * @throws ReadWriteException
     </en>*/
    public void resetStorageWorkNo()
            throws ReadWriteException
    {
        reset(STORAGE_WORKNO);
    }

    /**<jp>
     * 作業No（出庫用)sequenceのリセットを行います。
     * 日締め処理で使用します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     </jp>*/
    /**<en>
     * Resetting of Retrieval work no Sequence
     * This is used in daily transactions.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public void resetRetrievalWorkNo()
            throws ReadWriteException
    {
        reset(RETRIEVAL_WORKNO);
    }

    //------------------------------------------------------------
    // checker methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * 文字列をBigDecimalに変換します。<br>
     * valueがnullまたは空文字または数値として判断できないときは 0 が返ります。
     * @param value 更新する値
     * @return bigdecimal
     */
    protected BigDecimal toBigDecimal(String value)
    {
        try
        {
            BigDecimal bd = (StringUtil.isBlank(value)) ? BigDecimal.ZERO
                                                       : new BigDecimal(value);
            return bd;
        }
        catch (Exception e)
        {
            return BigDecimal.ZERO;
        }
    }

    /**
     * キー項目からプレフィックスを取り除いて返します。<br>
     * プレフィックスよりもorgKeyが短い場合は、""を返します。
     * 
     * @param orgKey キー項目
     * @param prefix プレフィックス
     * @return プレフィックスを取り除いた文字列
     */
    protected String trimPrefix(String orgKey, String prefix)
    {
        if (StringUtil.isBlank(orgKey))
        {
            return "";
        }
        int prefixLength = prefix.length();
        if (orgKey.length() <= prefixLength)
        {
            return "";
        }
        return orgKey.substring(prefixLength);
    }

    /**
     * ハンドラを生成して返します。<br>
     * 生成済みハンドラはキャッシュされ、2度目の取得時にはキャッシュされた
     * ハンドラクラスが返されます。
     * 
     * @param handlerClass 取得したいハンドラクラス
     * @param conn データベースコネクション
     * @return データベースハンドラ
     * @throws ReadWriteException ハンドラが生成できなかったときスローされます。
     */
    protected DatabaseHandler getHandler(Class handlerClass, Connection conn)
            throws ReadWriteException
    {
        // WareNavi3.5.4

        //Map<Class, DatabaseHandler> hCacheMap = getHandlerCacheMap();

        // try to get handler from cache
        //DatabaseHandler handler = hCacheMap.get(handlerClass);
        //if (null != handler)
        //{
        //    return handler;
        //}

        // create new Handler instance

        DatabaseHandler handler = null;

        // WareNavi3.5.4

        try
        {
            Class[] cParamClasses = {
                Connection.class,
            };
            Constructor hConst = handlerClass.getConstructor(cParamClasses);

            Object[] cParams = {
                conn,
            };
            Object hInstance = hConst.newInstance(cParams);
            if (hInstance instanceof DatabaseHandler)
            {
                handler = (DatabaseHandler)hInstance;
                // WareNavi3.5.4
                // hCacheMap.put(handlerClass, handler);
                // WareNavi3.5.4

                return handler;
            }
            throw new InvalidClassException(handlerClass.getSimpleName() + " is not DatabaseHandler.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ReadWriteException(e);
        }
    }

    // WareNavi3.5.4

    //    /**
    //     * Handler用のキャッシュマップを返します。
    //     * @return handler cache map
    //     */
    //    protected Map<Class, DatabaseHandler> getHandlerCacheMap()
    //    {
    //        if (null == _handlerCacheMap)
    //        {
    //            _handlerCacheMap = new HashMap<Class, DatabaseHandler>();
    //        }
    //        return _handlerCacheMap;
    //    }

    // WareNavi3.5.4
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
        return "$Id: WMSSequenceHandler.java 8071 2013-11-13 06:19:45Z fukuwa $";
    }
}
