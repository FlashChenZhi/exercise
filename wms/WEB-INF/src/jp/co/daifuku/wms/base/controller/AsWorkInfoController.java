// $Id: AsWorkInfoController.java 5924 2009-11-16 10:00:19Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.JOB_TYPE_RETRIEVAL;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_NOWWORKING;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_UNSTART;

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.asrs.schedule.AsrsOutParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.db.SQLAlterKey;
import jp.co.daifuku.wms.handler.db.SQLSearchKey;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS用の入出庫作業情報を操作するためのコントローラクラスです。
 *
 * @version $Revision: 5924 $, $Date: 2009-11-16 19:00:19 +0900 (月, 16 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class AsWorkInfoController
        extends WorkInfoController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 予定データを持つ作業区分 */
    private enum PLAN_JOB_TYPE
    {
        /** 出庫 */
        RETRIEVAL(SystemDefine.JOB_TYPE_RETRIEVAL, new RetrievalPlanAlterKey()), ;

        PLAN_JOB_TYPE(String value, SQLAlterKey ent)
        {
            _value = value;
            _ent = ent;
        }

        private String _value;

        private SQLAlterKey _ent;

        String getValue()
        {
            return _value;
        }

        SQLAlterKey getAlterKey()
        {
            return _ent;
        }
    }

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)<br>
     * 
     * このコンストラクタでは、WMSSequenceHandlerを初期化します。
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public AsWorkInfoController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

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
     * 出庫開始用に入出庫作業情報の検索およびロックを行います。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>作業区分
     * <li>荷主コード
     * <li>予定エリア
     * <li>バッチNo.
     * </ol>
     * @param dest 出庫先ステーションNo. または作業場<br>
     * 指定されたときは搬送情報の絞込条件として使用します。
     * @param orderNos オーダーNo. 一覧
     * 
     * @return 対象の入出庫作業情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException 対象ステーション定義が複数存在する
     */
    public WorkInfo[] lockRetrievalStart(WorkInfo tgt, String dest, String[] orderNos)
            throws ReadWriteException,
                LockTimeOutException,
                ScheduleException
    {
        try
        {
            AbstractDBHandler wih = new WorkInfoHandler(getConnection());

            SQLSearchKey key = setupRetrievalKeys(tgt, dest);

            // additional key (order list)
            key.setKey(WorkInfo.ORDER_NO, orderNos, true);
            key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);

            WorkInfo[] records = (WorkInfo[])wih.findForUpdate(key);
            return records;
        }
        catch (NoPrimaryException e)
        {
            throw new ScheduleException("Station define error, Too many stations found for " + dest);
        }
    }
    
    /**
     * 出庫キャンセル用に入出庫作業情報と搬送情報の検索およびロックを行います。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>作業区分
     * <li>荷主コード
     * <li>予定エリア
     * <li>バッチNo.
     * </ol>
     * @param dest 出庫先ステーションNo. または作業場<br>
     * 指定されたときは搬送情報の絞込条件として使用します。
     * @param fromOrder 範囲開始オーダーNo.
     * @param toOrder 範囲終了オーダーNo.(単一オーダーのときは fromOrder と同じものをセットします)
     * 
     * @return 対象の入出庫作業情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException 対象ステーション定義が複数存在する
     */
    public WorkInfo[] lockRetrievalCancel(WorkInfo tgt, String dest, String fromOrder, String toOrder)
            throws ReadWriteException,
                LockTimeOutException,
                ScheduleException
    {
        try
        {
            AbstractDBHandler wih = new WorkInfoHandler(getConnection());
            SQLSearchKey key = setupRetrievalKeys(tgt, dest);
            key.setKey(WorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_NOWWORKING);

            // additional key (FROM/TO order)
            key.setRangeKey(WorkInfo.ORDER_NO, fromOrder, toOrder, true);
            key.setKey(WorkInfo.HARDWARE_TYPE, SystemDefine.HARDWARE_TYPE_ASRS);

            WorkInfo[] records = (WorkInfo[])wih.findForUpdate(key);
            return records;
        }
        catch (NoPrimaryException e)
        {
            throw new ScheduleException("Station define error, Too many stations found for " + dest);
        }
    }

    /**
     * 出庫用に絞り込み用検索キーを生成して返します。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定エリア
     * <li>バッチNo.
     * </ol>
     * @param dest 出庫先ステーションNo. または作業場<br>
     * 指定されたときは搬送情報の絞込条件として使用します。
     * @return 絞り込み用検索キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象作業場/ステーションが一意でない
     */
    protected SQLSearchKey setupRetrievalKeys(WorkInfo tgt, String dest)
            throws ReadWriteException,
                NoPrimaryException
    {
        SQLSearchKey key = createRetrievalKey(tgt, dest);

        // sort order
        key.setOrder(WorkInfo.PLAN_LOCATION_NO, true);
        key.setOrder(WorkInfo.ITEM_CODE, true);
        key.setOrder(WorkInfo.PLAN_LOT_NO, true);
        key.setOrder(WorkInfo.ORDER_NO, true);
        key.setOrder(WorkInfo.PLAN_UKEY, true);
        return key;
    }

    /**
     * 出庫開始用に入出庫作業情報の検索を行います。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定エリア
     * <li>バッチNo.
     * </ol>
     * @param dest 出庫先ステーションNo. または作業場
     * @param fromOrder 範囲指定の From または 単一オーダーの場合にセットします。<br>
     * fromなしの場合は、null または 空文字 を指定します。
     * @param toOrder 範囲指定の To オーダーを指定します。<br>
     * To なしの場合は、null または 空文字 を指定します。
     * @param rangeOrder 範囲指定のとき true を指定します。単一オーダーの場合は false を指定します。
     * 
     * @return 入出庫作業情報を検索するための検索キー
     * <ol>
     * 取得項目
     * <li>バッチNo.
     * <li>オーダーNo.
     * <li>出荷先コード
     * <li>出荷先名称
     * <li>対象行数 (作業情報の行数, PLAN_QTYにセットされています)
     * </ol>
     * <ol>
     * グループ項目
     * <li>バッチNo.
     * <li>オーダーNo.
     * <li>出荷先コード
     * </ol>
     * <ol>
     * ソート順
     * <li>バッチNo. 昇順
     * <li>オーダーNo. 昇順
     * <li>出荷先コード 昇順
     * </ol>
     * 
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException 対象ステーション定義が複数存在する
     * 
     * @deprecated rangeOrderパラメータなしのメソッドを使用してください。
     */
    @Deprecated
    public SQLSearchKey getRetrievalWorkKey(WorkInfo tgt, String dest, String fromOrder, String toOrder,
            boolean rangeOrder)
            throws ReadWriteException,
                ScheduleException
    {
        // FIXME 3.0リリース前にメソッドごと削除
        try
        {
            SQLSearchKey key = createRetrievalKey(tgt, dest);
            key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);

            // condition of order number

            fixOrderRange(key, fromOrder, toOrder, rangeOrder);

            key.setCollect(WorkInfo.BATCH_NO);
            key.setCollect(WorkInfo.ORDER_NO);
            key.setCollect(WorkInfo.CUSTOMER_CODE);

            key.setCollect(Customer.CUSTOMER_NAME);

            // get number of record at group
            key.setCollect(WorkInfo.ITEM_CODE, "COUNT", WorkInfo.PLAN_QTY);

            // Group by
            key.setGroup(WorkInfo.BATCH_NO);
            key.setGroup(WorkInfo.ORDER_NO);
            key.setGroup(WorkInfo.CUSTOMER_CODE);
            key.setGroup(Customer.CUSTOMER_NAME);

            // Join
            key.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
            key.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

            // Sort order
            key.setOrder(WorkInfo.BATCH_NO, true);
            key.setOrder(WorkInfo.ORDER_NO, true);
            key.setOrder(WorkInfo.CUSTOMER_CODE, true);

            return key;
        }
        catch (NoPrimaryException e)
        {
            throw new ScheduleException("Station define error, Too many stations found for " + dest);
        }
    }

    /**
     * 出庫開始用に入出庫作業情報の検索を行います。<br>
     * 単一キー指定の場合は、fromOrder, toOrderともに同じ値をセットしてください。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定エリア
     * <li>バッチNo.
     * </ol>
     * @param dest 出庫先ステーションNo. または作業場
     * @param fromOrder 範囲指定の From または 単一オーダーの場合にセットします。<br>
     * fromなしの場合は、null または 空文字 を指定します。
     * @param toOrder 範囲指定の To オーダーを指定します。<br>
     * To なしの場合は、null または 空文字 を指定します。
     * 
     * @return 入出庫作業情報を検索するための検索キー
     * <ol>
     * 取得項目
     * <li>作業区分
     * <li>予定エリア
     * <li>バッチNo.
     * <li>オーダーNo.
     * <li>出荷先コード
     * <li>出荷先名称
     * <li>対象行数 (作業情報の行数, PLAN_QTYにセットされています)
     * </ol>
     * <ol>
     * グループ項目
     * <li>予定エリア
     * <li>バッチNo.
     * <li>オーダーNo.
     * <li>出荷先コード
     * </ol>
     * <ol>
     * ソート順
     * <li>バッチNo. 昇順
     * <li>オーダーNo. 昇順
     * <li>出荷先コード 昇順
     * </ol>
     * 
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException 対象ステーション定義が複数存在する
     */
    public SQLSearchKey getRetrievalWorkKey(WorkInfo tgt, String dest, String fromOrder, String toOrder)
            throws ReadWriteException,
                ScheduleException
    {
        try
        {
            SQLSearchKey key = createRetrievalKey(tgt, dest);
            key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);

            // DFKLOOK start 出庫開始時に即出庫搬送できるように対応
            // エリアNo.が指定されていなかった場合は設定しない
            if (!StringUtil.isBlank(tgt.getPlanAreaNo()))
            {
                // searchkey add areno
                key.setKey(WorkInfo.PLAN_AREA_NO, tgt.getPlanAreaNo());
            }
            // DFKLOOK end 出庫開始時に即出庫搬送できるように対応
            
            // condition of order number
            key.setRangeKey(WorkInfo.ORDER_NO, fromOrder, toOrder, true);

            // DFKLOOK start 出庫開始時に即出庫搬送できるように対応
            // 取得項目として予定エリアNo.を追加
            key.setCollect(WorkInfo.PLAN_AREA_NO);
            // DFKLOOK end 出庫開始時に即出庫搬送できるように対応
            
            key.setCollect(WorkInfo.BATCH_NO);
            key.setCollect(WorkInfo.ORDER_NO);
            key.setCollect(WorkInfo.CUSTOMER_CODE);

            key.setCollect(Customer.CUSTOMER_NAME);
            key.setCollect(CarryInfo.DEST_STATION_NO);
            key.setCollect(Station.STATION_NAME);

            // get number of record at group
            key.setCollect(WorkInfo.ITEM_CODE, "COUNT", WorkInfo.PLAN_QTY);

            // Group by            
            key.setGroup(WorkInfo.BATCH_NO);
            key.setGroup(WorkInfo.ORDER_NO);
            key.setGroup(WorkInfo.CUSTOMER_CODE);
            key.setGroup(Customer.CUSTOMER_NAME);
            key.setGroup(CarryInfo.DEST_STATION_NO);
            key.setGroup(Station.STATION_NAME);

            // DFKLOOK start 出庫開始時に即出庫搬送できるように対応
            // 集約条件として予定エリアNo.を追加
            key.setGroup(WorkInfo.PLAN_AREA_NO);
            // DFKLOOK end 出庫開始時に即出庫搬送できるように対応
            
            // Join
            key.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
            key.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");
            key.setJoin(CarryInfo.DEST_STATION_NO, "", Station.STATION_NO, "");

            // Sort order
            key.setOrder(WorkInfo.BATCH_NO, true);
            key.setOrder(WorkInfo.ORDER_NO, true);
            key.setOrder(WorkInfo.CUSTOMER_CODE, true);
            key.setOrder(CarryInfo.DEST_STATION_NO, true);

            return key;
        }
        catch (NoPrimaryException e)
        {
            throw new ScheduleException("Station define error, Too many stations found for " + dest);
        }
    }

    /**
     * @param key
     * @param fromOrder
     * @param toOrder
     * @param rangeOrder
     */
    @Deprecated
    private void fixOrderRange(SQLSearchKey key, String fromOrder, String toOrder, boolean rangeOrder)
    {
        boolean isEmpFrom = StringUtil.isBlank(fromOrder);
        boolean isEmpTo = StringUtil.isBlank(toOrder);

        if ((isEmpFrom && isEmpTo) || (!rangeOrder && isEmpFrom))
        {
            // no condition for order OR
            // single select and no condition for order, do nothing
            return;
        }

        if (rangeOrder)
        {
            if (!isEmpFrom)
            {
                key.setKey(WorkInfo.ORDER_NO, fromOrder, ">=", "", "", true);
            }
            if (!isEmpTo)
            {
                key.setKey(WorkInfo.ORDER_NO, toOrder, "<=", "", "", true);
            }
        }
        else
        {
            // single select
            key.setKey(WorkInfo.ORDER_NO, fromOrder);
        }
    }

    /**
     * 出庫開始用に入出庫作業情報の検索を行います。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定エリア
     * <li>バッチNo.
     * </ol>
     * @param dest 出庫先ステーションNo. または作業場
     * @param limit 検索最大件数, この件数+1の件数をリミットに検索を行います。<br>
     * 返値のパラメータの件数は、最大このパラメータの件数+1となります。(検索対象件数オーバー)
     * 
     * @param orderNos from/to のオーダーNo.をセットしてください。<br>
     * nullを指定: 絞込みなし
     * 1件だけ指定: 対象オーダーのみ
     * 2件指定(配列OK): from, to 範囲指定
     * 
     * @return 対象の入出庫作業情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException 対象ステーション定義が複数存在する
     */
    @Deprecated
    public AsrsOutParameter[] getRetrievalWorks(WorkInfo tgt, String dest, int limit, String... orderNos)
            throws ReadWriteException,
                ScheduleException
    {
        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        SQLSearchKey key = null;
        try
        {
            key = createRetrievalKey(tgt, dest);
            key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);

            // condition of order number
            if (null != orderNos)
            {
                if (2 > orderNos.length)
                {
                    // single
                    key.setKey(WorkInfo.ORDER_NO, orderNos[0]);
                }
                else
                {
                    // range
                    key.setKey(WorkInfo.ORDER_NO, orderNos[0], ">=", "", "", true);
                    key.setKey(WorkInfo.ORDER_NO, orderNos[1], "<=", "", "", true);
                }
            }

            key.setCollect(WorkInfo.BATCH_NO);
            key.setCollect(WorkInfo.ORDER_NO);
            key.setCollect(WorkInfo.CUSTOMER_CODE);

            key.setCollect(Customer.CUSTOMER_NAME);

            // get number of record at group
            key.setCollect(WorkInfo.ITEM_CODE, "COUNT", WorkInfo.PLAN_QTY);

            // Group by
            key.setGroup(WorkInfo.BATCH_NO);
            key.setGroup(WorkInfo.ORDER_NO);
            key.setGroup(WorkInfo.CUSTOMER_CODE);
            key.setGroup(Customer.CUSTOMER_NAME);

            // Join
            key.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
            key.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

            // Sort order
            key.setOrder(WorkInfo.BATCH_NO, true);
            key.setOrder(WorkInfo.ORDER_NO, true);
            key.setOrder(WorkInfo.CUSTOMER_CODE, true);

            WorkInfo[] records;
            records = (WorkInfo[])wih.find(key, limit + 1);

            int numrec = records.length;
            AsrsOutParameter[] rparams = new AsrsOutParameter[numrec];
            for (int i = 0; i < numrec; i++)
            {
                WorkInfo rec = records[i];
                AsrsOutParameter param = new AsrsOutParameter();

                param.setBatchNo(rec.getBatchNo());
                param.setOrderNo(rec.getOrderNo());
                param.setCustomerCode(rec.getCustomerCode());

                Object cuName = rec.getValue(Customer.CUSTOMER_NAME, "");
                param.setCustomerName(String.valueOf(cuName));

                param.setDetailCnt(rec.getPlanQty()); // 明細行

                rparams[i] = param;
            }
            return rparams;
        }
        catch (NoPrimaryException e)
        {
            throw new ScheduleException("Station define error, Too many stations found for " + dest);
        }
    }

    /**
     * 出庫作業データの検索用キーを生成して返します。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>作業区分
     * <li>予定日
     * <li>荷主コード
     * <li>予定エリア
     * <li>バッチNo.
     * </ol>
     * @param dest 出庫先ステーションNo. または作業場<br>
     * 指定されたときは搬送情報の絞込条件として使用します。
     * @return 検索キー
     * @throws NoPrimaryException 対象作業場/ステーションが一意でない
     * @throws ReadWriteException データベースエラー
     */
    protected SQLSearchKey createRetrievalKey(WorkInfo tgt, String dest)
            throws ReadWriteException,
                NoPrimaryException
    {
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        FieldName[] keyFields = {
            WorkInfo.JOB_TYPE,
            WorkInfo.PLAN_DAY,
            WorkInfo.CONSIGNOR_CODE,
            WorkInfo.BATCH_NO,
            WorkInfo.PLAN_AREA_NO,
        };
        key = (WorkInfoSearchKey)createKey(tgt, key, keyFields);
        
        // CarryInfo keys
        if (!StringUtil.isBlank(dest))
        {
            String[] dests = fixWorkPlace(dest);
            key.setKey(CarryInfo.DEST_STATION_NO, dests, true);
        }

        // Joins
        key.setJoin(CarryInfo.CARRY_KEY, WorkInfo.SYSTEM_CONN_KEY);
        return key;
    }

    /**
     * 出庫作業データの検索用キーを生成して返します。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>出庫予定日
     * <li>荷主コード
     * <li>バッチNo.
     * <li>予定エリア
     * <li>状態フラグ
     * </ol>
     * @param dest 出庫先ステーションNo. または作業場
     * @param cmdstatus 搬送状態フラグ
     * @return 検索キー
     * @throws NoPrimaryException 対象作業場/ステーションが一意でない
     * @throws ReadWriteException データベースエラー
     */
    public SQLSearchKey createRetrievalStatusKey(WorkInfo tgt, String dest, String cmdstatus)
            throws ReadWriteException,
                NoPrimaryException
    {
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        FieldName[] keyFields = {
            WorkInfo.PLAN_DAY,
            WorkInfo.CONSIGNOR_CODE,
            WorkInfo.BATCH_NO,
            WorkInfo.PLAN_AREA_NO,
            WorkInfo.STATUS_FLAG,
        };
        key = (WorkInfoSearchKey)createKey(tgt, key, keyFields);

        key.setKey(WorkInfo.JOB_TYPE, JOB_TYPE_RETRIEVAL);

        // CarryInfo keys
        if (!StringUtil.isBlank(dest))
        {
            String[] dests = fixWorkPlace(dest);
            key.setKey(CarryInfo.DEST_STATION_NO, dests, true);
        }

        if (!StringUtil.isBlank(cmdstatus))
        {
            key.setKey(CarryInfo.CMD_STATUS, cmdstatus);
        }

        // Joins
        key.setJoin(CarryInfo.CARRY_KEY, WorkInfo.SYSTEM_CONN_KEY);
        return key;
    }

    /**
     * ステーションまたは作業場をステーションに展開します。
     * 
     * @param wp ステーションまたは作業場<br>
     * ステーションのときは、要素数1でステーションがそのままセットされます。
     * @return 展開済みステーション
     * @throws NoPrimaryException 対象作業場/ステーションが一意でない
     * @throws ReadWriteException データベースエラー
     */
    protected String[] fixWorkPlace(String wp)
            throws ReadWriteException,
                NoPrimaryException
    {
        try
        {
            Station tstat = StationFactory.makeStation(getConnection(), wp);
            if (tstat instanceof WorkPlace)
            {
                WorkPlace twp = (WorkPlace)tstat;
                return twp.getWPStations();
            }
            else
            {
                String[] stations = {
                    tstat.getStationNo(),
                };
                return stations;
            }
        }
        catch (InvalidDefineException e)
        {
            // do nothing.
        }
        catch (NotFoundException e)
        {
            // do nothing.
        }
        return new String[0];
    }

    /**
     * ステーションまたは作業場をステーションに展開します。
     * 
     * @param stat ステーションまたは作業場<br>
     * ステーションのときは、要素数1でステーションがそのままセットされます。
     * @return 展開済みステーション
     * @throws NoPrimaryException 対象作業場/ステーションが一意でない
     * @throws ReadWriteException データベースエラー
     */
    protected String[] fixWorkPlace(Station stat)
            throws ReadWriteException,
                NoPrimaryException
    {
        if (stat instanceof WorkPlace)
        {
            WorkPlace wpc = (WorkPlace)stat;
            return wpc.getWPStations();
        }
        else
        {
            String[] stations = {
                stat.getStationNo(),
            };
            return stations;
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * AS/RS作業情報開始<BR>
     * パラメータの対象データキー項目の作業No.に該当する未開始データを作業中に更新します。<BR>
     * 入出庫作業更新内容で設定単位キー、集約作業No.、システム接続キー、予定エリア、予定棚、予定ロットNo.、
     * ハードウェア区分、ユーザID、端末No.を更新します。<BR>
     * 予定数 &gt; 実績数の作業情報は分割として、予定数から不足数を減算し、不足分の作業情報を未開始で新規登録します。<BR>
     * <BR>
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 以下の項目が参照されます
     * <li>作業No.
     * <li>予定数
     * </ol>
     * 
     * @param newWork 入出庫作業更新内容
     * <ol>
     * 以下の項目が参照されます
     * <li>設定単位キー
     * <li>集約作業No.
     * <li>システム接続キー
     * <li>予定エリア
     * <li>予定棚
     * <li>予定ロットNo.
     * <li>予定数
     * </ol>
     * 
     * @param ui WMSユーザ情報
     * <ol>
     * 以下の項目が参照されます
     * <li>ユーザID
     * <li>端末No.
     * </ol>
     * @throws NotFoundException データが存在しない場合にスローされます。
     * @throws ReadWriteException データベース処理でエラーが発生した場合にスローされます。
     * @throws DataExistsException 既にデータが登録済みであった場合にスローされます。
     */
    public void startAsrsWorkInfo(WorkInfo tgt, WorkInfo newWork, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException
    {
        WorkInfoAlterKey workInfoKey = new WorkInfoAlterKey();

        FieldName[] updateFields = {
            WorkInfo.STOCK_ID,
            WorkInfo.SETTING_UNIT_KEY,
            WorkInfo.COLLECT_JOB_NO,
            WorkInfo.SYSTEM_CONN_KEY,
            WorkInfo.PLAN_AREA_NO,
            WorkInfo.PLAN_LOT_NO,
        };
        workInfoKey = (WorkInfoAlterKey)createAlterKey(newWork, workInfoKey, updateFields);

        // 分割かどうか(対象データキー.予定数 > 更新内容.予定数の場合)
        // 更新内容.予定数がセットされていなければ分割判定は不要
        Object planQty = newWork.getValue(WorkInfo.PLAN_QTY);
        boolean isPlanSet = (null != planQty);

        int newPlanQty = newWork.getPlanQty();
        int tgtPlanQty = tgt.getPlanQty();
        boolean isDivide = isPlanSet && (tgtPlanQty > newPlanQty);

        // 更新内容の設定
        workInfoKey.updateStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING); // 状態フラグ(作業中)
        if (isDivide)
        {
            workInfoKey.updatePlanQty(newPlanQty); // 予定数
        }

        // 棚はnullに更新する場合あり
        workInfoKey.updatePlanLocationNo(newWork.getPlanLocationNo());

        workInfoKey.updateHardwareType(SystemDefine.HARDWARE_TYPE_ASRS); // ハードウェア区分
        workInfoKey.updateUserId(ui.getUserId()); // ユーザID
        workInfoKey.updateTerminalNo(ui.getTerminalNo()); // 端末No.
        workInfoKey.updateLastUpdatePname(getCallerName()); // 最終更新処理名

        // 更新条件の設定
        workInfoKey.setJobNo(tgt.getJobNo()); // 作業No.

        // 更新処理実行
        WorkInfoHandler handler = new WorkInfoHandler(getConnection());
        handler.modify(workInfoKey);

        if (isDivide)
        {
            // 作業No.採番
            String jobNo = getSeqHandler().nextWorkInfoJobNo();

            WorkInfo divWorkInfo = (WorkInfo)tgt.clone();
            divWorkInfo.setJobNo(jobNo); // 作業No.
            divWorkInfo.setSettingUnitKey(""); // 設定単位キー
            divWorkInfo.setCollectJobNo(""); // 集約作業No.
            divWorkInfo.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART); // 状態フラグ(未作業)
            divWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART); // ハードウェア区分(未作業)
            divWorkInfo.setPlanQty(tgtPlanQty - newPlanQty); // 予定数
            divWorkInfo.setResultQty(0); // 実績数
            divWorkInfo.setShortageQty(0); // 欠品数
            divWorkInfo.setUserId(""); // ユーザID
            divWorkInfo.setTerminalNo(""); // 端末No.
            divWorkInfo.setRegistPname(getCallerName()); // 登録処理名
            divWorkInfo.setRegistDate(new SysDate());
            divWorkInfo.setLastUpdatePname(getCallerName()); // 最終更新処理名

            // FIXME 入庫予定日、商品コード、作業区分はいらない？？

            // 分割用作業情報の登録
            handler.create(divWorkInfo);
        }
    }

    /**
     * 作業のJOB_NOをキーとして、実績数と欠品数を入れ替えます。<br>
     * トラッキング削除によって、完了済み作業を欠品に変更する場合に
     * 使用します。
     * 
     * @param works 対象作業
     * @return 更新対象となったJOB_NO
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象作業が見つからない
     */
    public String[] updateToShortage(WorkInfo[] works)
            throws NotFoundException,
                ReadWriteException
    {
        String[] jobNos = getUniqueJobNos(works);

        WorkInfoAlterKey akey = new WorkInfoAlterKey();

        // 更新内容のセット
        akey.setAdhocUpdateValue(WorkInfo.RESULT_QTY, WorkInfo.SHORTAGE_QTY);
        akey.setAdhocUpdateValue(WorkInfo.SHORTAGE_QTY, WorkInfo.RESULT_QTY);
        akey.updateLastUpdatePname(getCallerName());

        // 条件のセット
        akey.setJobNo(jobNos);
        akey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        WorkInfoHandler handler = new WorkInfoHandler(getConnection());
        handler.modify(akey);

        return jobNos;
    }

    /**
     * 作業の実績棚No.を変更します。
     * 
     * @param works 対象作業
     * @param newLocation 新しい実績棚No. (ParamLocationフォーマット)
     * @return 更新対象となったJOB_NO
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 該当作業なし
     */
    public String[] updateResultLocation(WorkInfo[] works, String newLocation)
            throws NotFoundException,
                ReadWriteException
    {
        // 重複する作業No.を集約
        String[] jobNos = getUniqueJobNos(works);

        WorkInfoAlterKey akey = new WorkInfoAlterKey();

        // 更新内容のセット
        akey.updateResultLocationNo(newLocation);
        akey.updateLastUpdatePname(getCallerName());

        // 条件のセット
        akey.setJobNo(jobNos);
        akey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        WorkInfoHandler handler = new WorkInfoHandler(getConnection());
        handler.modify(akey);

        return jobNos;
    }

    /**
     * 作業の実績棚No.を変更します。
     * 
     * @param works 対象作業
     * @param newStockId 新しいStockId
     * @param newArea 新しい実績エリア
     * @param newLocation 新しい実績棚No. (ParamLocationフォーマット)
     * @return 更新対象となったJOB_NO
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 該当作業なし
     */
    public String[] updateResultLocation(WorkInfo[] works, String newStockId, String newArea, String newLocation)
            throws NotFoundException,
                ReadWriteException
    {
        // 重複する作業No.を集約
        String[] jobNos = getUniqueJobNos(works);

        WorkInfoAlterKey akey = new WorkInfoAlterKey();

        // 更新内容のセット
        akey.updateStockId(newStockId);
        akey.updateResultAreaNo(newArea);
        akey.updateResultLocationNo(newLocation);
        akey.updateLastUpdatePname(getCallerName());

        // 条件のセット
        akey.setJobNo(jobNos);
        akey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        WorkInfoHandler handler = new WorkInfoHandler(getConnection());
        handler.modify(akey);

        return jobNos;
    }

    /**
     * 作業情報を未スケジュール状態に戻します。<br>
     * 該当作業を削除し、予定データが存在するときはその予定データを
     * 未スケジュール状態に変更します。
     * 
     * @param work 対象の作業
     * @throws NotFoundException 対象作業なし
     * @throws ReadWriteException データベースアクセスエラー
     */
    public void deAllocate(WorkInfo work)
            throws ReadWriteException,
                NotFoundException
    {
        // 作業情報の削除
        String jobNo = work.getJobNo();

        WorkInfoHandler wiH = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setJobNo(jobNo);
        // int numWork = wiH.drop(wiKey);

        wiH.drop(wiKey);

        /* 作業区分
         
         02:入庫
         03:出庫 <-- 対象
         22:予定外入庫
         23:予定外出庫
         40:在庫確認（AS/RS）
         55:計画補充（AS/RS）
         56:緊急補充（AS/RS）　
         */

        // 予定データキャンセル対象の作業区分であるかチェック
        String jobType = work.getJobType();
        for (PLAN_JOB_TYPE planJobType : PLAN_JOB_TYPE.values())
        {
            if (planJobType.getValue().equals(jobType))
            {
                // 予定データの更新
                deAllocRetrievalPlan(planJobType.getAlterKey(), work.getPlanUkey());
                break;
            }
        }
    }

    /**
     * 予定データを「未スケジュール」に更新します。
     * 
     * @param akey 対象予定データ用の更新キー
     * @param planUkey 対象予定情報の設定単位キー
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 予定データが見つからない
     */
    private void deAllocRetrievalPlan(SQLAlterKey akey, String planUkey)
            throws NotFoundException,
                ReadWriteException
    {
        akey.clear();

        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        try
        {
            if (akey instanceof RetrievalPlanAlterKey)
            {
                RetrievalPlanAlterKey rpakey = (RetrievalPlanAlterKey)akey;
                rpakey.setPlanUkey(planUkey);

                rpakey.updateSchFlag(SystemDefine.SCH_FLAG_NOT_SCHEDULE);
                rpakey.updateLastUpdatePname(getCallerName());

                handler.modify(akey);
            }
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * ASRSキャンセル用の更新キーを生成して返します。
     * 
     * @return 更新内容がセットされた更新キー
     */
    protected WorkInfoAlterKey asCreateCancelKey()
    {
        WorkInfoAlterKey akey = new WorkInfoAlterKey();

        akey.updateSettingUnitKey("");
        akey.updateCollectJobNo("");
        akey.updateStatusFlag(STATUS_FLAG_UNSTART);
        akey.updateUserId("");
        akey.updateTerminalNo("");
        akey.updateLastUpdatePname(getCallerName());

        return akey;
    }

    /**
     * ASRS作業情報をキャンセルします。
     * 
     * @param workInfos 対象作業
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void asCancelWorkInfo(WorkInfo workInfos)
            throws ReadWriteException,
                NotFoundException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoAlterKey akey = asCreateCancelKey();

        akey.setJobNo(workInfos.getJobNo());
        akey.setStatusFlag(STATUS_FLAG_NOWWORKING);

        wih.modify(akey);
    }
    
    /**
     * AS/RS補充開始用に設定単位キーに紐づく移動作業情報、作業情報、搬送情報をロックします。
     * 
     * @param settingUnitKey 対象の設定単位キー
     * 
     * @return 対象の入出庫作業情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException 対象ステーション定義が複数存在する
     */
    public WorkInfo[] lockReplenishStart(String settingUnitKey)
            throws ReadWriteException,
            LockTimeOutException,
            ScheduleException
    {
        WorkInfoHandler mwih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wKey = new WorkInfoSearchKey();

        // select
        // join
        wKey.setJoin(WorkInfo.JOB_NO, MoveWorkInfo.WORK_CONN_KEY);
        wKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);

        // where
        wKey.setKey(MoveWorkInfo.SETTING_UNIT_KEY, settingUnitKey);
        wKey.setKey(CarryInfo.CMD_STATUS, CarryInfo.CMD_STATUS_ALLOCATION);

        WorkInfo[] works = (WorkInfo[])retryLock(wKey, mwih, 1);
        
        return works;
        
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
        return "$Id: AsWorkInfoController.java 5924 2009-11-16 10:00:19Z kishimoto $";
    }
}
