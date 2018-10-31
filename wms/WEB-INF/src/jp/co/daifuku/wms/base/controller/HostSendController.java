// $Id: HostSendController.java 7748 2010-03-29 09:13:01Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 入出庫実績送信情報を操作するためのコントローラクラスです。
 *
 *
 * @version $Revision: 7748 $, $Date: 2010-03-29 18:13:01 +0900 (月, 29 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class HostSendController
        extends AbstractController
        implements SystemDefine
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public HostSendController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入出庫作業情報から入出庫実績送信情報を作成します。<br>
     * 作業No.を元に入出庫作業情報およびマスタ情報を検索して、
     * 入出庫実績送信情報を登録します。
     * <ul>
     * 以下のマスタ情報を検索します。
     * <li>荷主マスタ
     * <li>仕入先マスタ
     * <li>出荷先マスタ
     * <li>商品マスタ
     * <li>ログインユーザ
     * </ul>
     * 
     * @param jobNo 作業No. (入出庫作業情報の検索キー)
     * @param ui 設定ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    @SuppressWarnings("unused")
    public void insertByWorkInfo(String jobNo, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        // set collect
        FieldName[] collects = {
            WorkInfo.WORK_DAY,
            WorkInfo.JOB_NO,
            WorkInfo.COLLECT_JOB_NO,
            WorkInfo.SETTING_UNIT_KEY,
            WorkInfo.JOB_TYPE,
            WorkInfo.STATUS_FLAG,
            WorkInfo.HARDWARE_TYPE,
            WorkInfo.PLAN_UKEY,
            WorkInfo.STOCK_ID,
            WorkInfo.SYSTEM_CONN_KEY,
            WorkInfo.PLAN_DAY,

            WorkInfo.CONSIGNOR_CODE,
            Consignor.CONSIGNOR_NAME,

            WorkInfo.SUPPLIER_CODE,
            Supplier.SUPPLIER_NAME,

            WorkInfo.RECEIVE_TICKET_NO,
            WorkInfo.RECEIVE_LINE_NO,
            WorkInfo.RECEIVE_BRANCH_NO,

            WorkInfo.CUSTOMER_CODE,
            Customer.CUSTOMER_NAME,

            WorkInfo.SHIP_TICKET_NO,
            WorkInfo.SHIP_LINE_NO,
            WorkInfo.SHIP_BRANCH_NO,

            WorkInfo.BATCH_NO,
            WorkInfo.ORDER_NO,
            WorkInfo.PLAN_AREA_NO,
            WorkInfo.PLAN_LOCATION_NO,

            WorkInfo.ITEM_CODE,
            Item.ITEM_NAME,
            Item.JAN,
            Item.ITF,
            Item.BUNDLE_ITF,
            Item.ENTERING_QTY,
            Item.BUNDLE_ENTERING_QTY,

            WorkInfo.PLAN_LOT_NO,
            WorkInfo.PLAN_QTY,
            WorkInfo.RESULT_QTY,
            WorkInfo.SHORTAGE_QTY,
            WorkInfo.RESULT_AREA_NO,
            WorkInfo.RESULT_LOCATION_NO,
            WorkInfo.RESULT_LOT_NO,

            WorkInfo.REASON_TYPE,

            WorkInfo.USER_ID,
            WorkInfo.TERMINAL_NO,
            WorkInfo.WORK_SECOND,
        };
        setCollectFields(key, collects);

        // collect field (not same field name with HostSend)
        key.setCollect(Com_loginuser.USERNAME, "", HostSend.USER_NAME);

        // set key job number
        key.setJobNo(jobNo);

        // set join ----
        key.setJoin(WorkInfo.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);
        key.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);

        key.setJoin(WorkInfo.CONSIGNOR_CODE, "", Supplier.CONSIGNOR_CODE, "(+)");
        key.setJoin(WorkInfo.SUPPLIER_CODE, "", Supplier.SUPPLIER_CODE, "(+)");
        key.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        key.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        // join with eManager tables
        key.setJoin(WorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        // FIXME getting terminal info with RFT
        //  rft_no = TerminalNo.,"" = Terninal Name, ip_address = IP address

        // find ----
        Entity readEnt = wih.findPrimary(key);
        if (null == readEnt)
        {
            // not found.
            throw new ScheduleException();
        }
        // set values
        HostSend hsEnt = new HostSend();
        hsEnt.setValueMap(readEnt.getValueMap());

        // "REPORTED" if AS/RS inv. check
        String jobType = (String)readEnt.getValue(WorkInfo.JOB_TYPE);
        String hardwareType = (String)readEnt.getValue(WorkInfo.HARDWARE_TYPE);
        String repFlag = "";
        int resultQty = readEnt.getBigDecimal(WorkInfo.RESULT_QTY).intValue();
        int shortageQty = readEnt.getBigDecimal(WorkInfo.SHORTAGE_QTY).intValue();
        if (JOB_TYPE_ASRS_INVENTORYCHECK.equals(jobType) || JOB_TYPE_ASRS_RACK_TO_RACK.equals(jobType)
                || JOB_TYPE_ASRS_REARRANGE.equals(jobType) || JOB_TYPE_DIRECT_TRAVEL.equals(jobType)
                || (resultQty == 0 && shortageQty == 0))
        {
            repFlag = SystemDefine.REPORT_FLAG_REPORT;
        }
        else if (HARDWARE_TYPE_ASRS.equals(hardwareType)
                && (JOB_TYPE_STORAGE.equals(jobType) || JOB_TYPE_NOPLAN_STORAGE.equals(jobType) || JOB_TYPE_RESTORING.equals(jobType)
                        || JOB_TYPE_RETRIEVAL.equals(jobType) || JOB_TYPE_NOPLAN_RETRIEVAL.equals(jobType)))
        {
            repFlag = SystemDefine.REPORT_FLAG_CARRY;
        }
        else
        {
            repFlag = SystemDefine.REPORT_FLAG_NOT_REPORT;
        }

        //  理由区分名称
        int reasonType = readEnt.getBigDecimal(WorkInfo.REASON_TYPE).intValue();
        ReasonController reason = new ReasonController(getConnection(), getClass());
        String reasonName = reason.getReasonName(reasonType);
        hsEnt.setReasonName(reasonName);

        hsEnt.setReportFlag(repFlag);

        String cname = getCallerName();
        hsEnt.setRegistPname(cname);
        hsEnt.setRegistDate(new SysDate());
        hsEnt.setLastUpdatePname(cname);

        // insert to hostsend table
        HostSendHandler hsh = new HostSendHandler(getConnection());
        hsh.create(hsEnt);
    }

    /**
     * 入出庫作業情報から入出庫実績送信情報を作成します。<br>
     * 搬送データ削除、強制払出しの場合にしようします。
     * 入出庫実績送信情報を登録します。
     * 
     * @param workInfo 入出庫作業情報エンティティ
     * @param workType 作業種別
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     */
    @SuppressWarnings("unused")
    public void insertByWorkInfo(WorkInfo workinfo, String workType)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        // set collect
        FieldName[] collects = {
            WorkInfo.WORK_DAY,
            WorkInfo.JOB_NO,
            WorkInfo.COLLECT_JOB_NO,
            WorkInfo.SETTING_UNIT_KEY,
            WorkInfo.JOB_TYPE,
            WorkInfo.STATUS_FLAG,
            WorkInfo.HARDWARE_TYPE,
            WorkInfo.PLAN_UKEY,
            WorkInfo.STOCK_ID,
            WorkInfo.SYSTEM_CONN_KEY,
            WorkInfo.PLAN_DAY,

            WorkInfo.CONSIGNOR_CODE,
            Consignor.CONSIGNOR_NAME,

            WorkInfo.SUPPLIER_CODE,
            Supplier.SUPPLIER_NAME,

            WorkInfo.RECEIVE_TICKET_NO,
            WorkInfo.RECEIVE_LINE_NO,
            WorkInfo.RECEIVE_BRANCH_NO,

            WorkInfo.CUSTOMER_CODE,
            Customer.CUSTOMER_NAME,

            WorkInfo.SHIP_TICKET_NO,
            WorkInfo.SHIP_LINE_NO,
            WorkInfo.SHIP_BRANCH_NO,

            WorkInfo.BATCH_NO,
            WorkInfo.ORDER_NO,
            WorkInfo.PLAN_AREA_NO,
            WorkInfo.PLAN_LOCATION_NO,

            WorkInfo.ITEM_CODE,
            Item.ITEM_NAME,
            Item.JAN,
            Item.ITF,
            Item.BUNDLE_ITF,
            Item.ENTERING_QTY,
            Item.BUNDLE_ENTERING_QTY,

            WorkInfo.PLAN_LOT_NO,
            WorkInfo.RESULT_AREA_NO,
            WorkInfo.RESULT_LOCATION_NO,
            WorkInfo.RESULT_LOT_NO,

            WorkInfo.REASON_TYPE,

            WorkInfo.USER_ID,
            WorkInfo.TERMINAL_NO,
            WorkInfo.WORK_SECOND,
        };
        setCollectFields(key, collects);

        // collect field (not same field name with HostSend)
        key.setCollect(Com_loginuser.USERNAME, "", HostSend.USER_NAME);

        // set key job number
        key.setJobNo(workinfo.getJobNo());

        // set join ----
        key.setJoin(WorkInfo.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);
        key.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);

        key.setJoin(WorkInfo.CONSIGNOR_CODE, "", Supplier.CONSIGNOR_CODE, "(+)");
        key.setJoin(WorkInfo.SUPPLIER_CODE, "", Supplier.SUPPLIER_CODE, "(+)");
        key.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        key.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        // join with eManager tables
        key.setJoin(WorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        // 作業情報検索
        Entity readEnt = wih.findPrimary(key);
        if (null == readEnt)
        {
            // not found.
            throw new NotFoundException();
        }
        // 在庫情報検索
        StockHandler stHan = new StockHandler(getConnection());
        StockSearchKey stKey = new StockSearchKey();
        stKey.setStockId(workinfo.getStockId());
        Stock stock = (Stock)stHan.findPrimary(stKey);
        int stockQty;
        int workQty = workinfo.getResultQty();
        
        if (StringUtil.isBlank(stock))
        {
            stockQty = 0;
        }
        else 
        {
            stockQty = stock.getStockQty();
        }

        HostSend hsEnt = new HostSend();

        if (StringUtil.isBlank(readEnt.getValue(WorkInfo.WORK_DAY)))
        {
            WarenaviSystemController wsc;
            try
            {
                wsc = new WarenaviSystemController(getConnection(), getCaller());
            }
            catch (ScheduleException e)
            {
                throw new ReadWriteException();
            }
            readEnt.setValue(WorkInfo.WORK_DAY, wsc.getWorkDay());
        }
        if (!SystemDefine.STATUS_FLAG_COMPLETION.equals(readEnt.getValue(WorkInfo.STATUS_FLAG)))
        {
            readEnt.setValue(WorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_COMPLETION);
            workQty = workinfo.getPlanQty();
        }
        readEnt.setValue(WorkInfo.JOB_TYPE, workType);

        hsEnt.setValueMap(readEnt.getValueMap());

        int shortageQty = 0;
        int planQty = 0;

        hsEnt.setResultQty(stockQty);
        hsEnt.setShortageQty(shortageQty);
        hsEnt.setPlanQty(planQty);


        String repFlag = "";
        repFlag = SystemDefine.REPORT_FLAG_NOT_REPORT;

        //  理由区分名称
        int reasonType = readEnt.getBigDecimal(WorkInfo.REASON_TYPE).intValue();
        ReasonController reason = new ReasonController(getConnection(), getClass());
        String reasonName = reason.getReasonName(reasonType);
        hsEnt.setReasonName(reasonName);

        hsEnt.setReportFlag(repFlag);

        String cname = getCallerName();
        hsEnt.setRegistPname(cname);
        hsEnt.setRegistDate(new SysDate());
        hsEnt.setLastUpdatePname(cname);

        // insert to hostsend table
        HostSendHandler hsh = new HostSendHandler(getConnection());
        hsh.create(hsEnt);
    }

    /**
     * 在庫情報から入出庫実績送信情報を作成します。<br>
     * メンテナンス増減の入出庫実績送信情報を登録します。
     * 
     * @param stock 在庫情報エンティティ
     * @param jobType メンテナンス区分(増減)
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     */
    @SuppressWarnings("unused")
    public void insertByStock(Stock stock, String jobType, int resultQty, WmsUserInfo ui)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException
    {

        StockHandler stHan = new StockHandler(getConnection());
        StockSearchKey stKey = new StockSearchKey();

        // se collect
        // 必須項目
        stKey.setCollect(Item.ITEM_NAME);
        stKey.setItemCodeCollect();
        stKey.setLotNoCollect();
        stKey.setAreaNoCollect();
        stKey.setLocationNoCollect();
        // 付加項目
        stKey.setStockIdCollect();
        stKey.setConsignorCodeCollect();
        stKey.setCollect(Consignor.CONSIGNOR_CODE);
        stKey.setCollect(Consignor.CONSIGNOR_NAME);
        
        // set join 
        stKey.setJoin(Stock.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);
        stKey.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        stKey.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        // set key
        stKey.setStockId(stock.getStockId());
        Stock st = (Stock)stHan.findPrimary(stKey);
        if (null == st)
        {
            // not found.
            throw new NotFoundException();
        }

        WarenaviSystemController wsc;
        try
        {
            wsc = new WarenaviSystemController(getConnection(), getCaller());
        }
        catch (ScheduleException e)
        {
            throw new ReadWriteException();
        }
        
        // 実績送信情報作成
        HostSend hsEnt = new HostSend();

        // 作業No.の採番
        WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
        String jobNo = seqHandler.nextWorkInfoJobNo();
        
        // 必須項目
        // 作業NO.
        hsEnt.setJobNo(jobNo);
        // 作業区分
        hsEnt.setJobType(jobType);
        // 商品コード
        hsEnt.setItemCode(st.getItemCode());
        // 商品名称
        hsEnt.setItemName(String.valueOf(st.getValue(Item.ITEM_NAME)));
        // ロットNO.
        hsEnt.setResultLotNo(st.getLotNo());
        // エリア
        hsEnt.setResultAreaNo(st.getAreaNo());
        // 棚No.
        hsEnt.setResultLocationNo(st.getLocationNo());
        // 実績数
        hsEnt.setResultQty(resultQty);
        // 実績日
        hsEnt.setWorkDay(wsc.getWorkDay());
        // ユーザID
        hsEnt.setUserId(ui.getUserId());
        // ユーザ名称
        hsEnt.setUserName(ui.getUserName());
        // 端末No.
        hsEnt.setTerminalNo(ui.getTerminalNo());
        
        // 付加項目
        // 状態
        hsEnt.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
        // 在庫ID
        hsEnt.setStockId(st.getStockId());
        // 荷主コード
        hsEnt.setConsignorCode(String.valueOf(st.getValue(Consignor.CONSIGNOR_CODE)));
        // 荷主名称
        hsEnt.setConsignorName(String.valueOf(st.getValue(Consignor.CONSIGNOR_NAME)));
        // 予定エリア
        hsEnt.setPlanAreaNo(st.getAreaNo());
        // 予定棚No.
        hsEnt.setPlanLocationNo(st.getLocationNo());
        // 理由区分
        hsEnt.setReasonName("");
        // 報告フラグ
        hsEnt.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);

        hsEnt.setRegistPname(getCallerName());
        hsEnt.setRegistDate(new SysDate());
        hsEnt.setLastUpdatePname(getCallerName());

        // insert to hostsend table
        HostSendHandler hsh = new HostSendHandler(getConnection());
        hsh.create(hsEnt);
    }

    /**
     * 入出庫実績送信情報を登録します。
     * 
     * @param src 入出庫実績送信情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     */
    public void insert(HostSend src)
            throws ReadWriteException,
                DataExistsException
    {
        HostSendHandler hsh = new HostSendHandler(getConnection());
        hsh.create(src);
    }

    /**
     * JOB_NOをキーとして、実績数と欠品数を入れ替えます。<br>
     * トラッキング削除によって、完了済み作業を欠品に変更する場合に
     * 使用します。
     * 
     * @param works 対象作業
     * @return 更新対象となったJOB_NO
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象実績が見つからない
     */
    public String[] updateToShortage(WorkInfo[] works)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績を登録しないパターンであれば更新を行なわない
        if (works.length == 1 && !checkHostSendType(works[0].getConsignorCode(), works[0].getItemCode()))
        {
            return null;
        }

        // 重複する作業No.を集約
        String[] jobNos = WorkInfoController.getUniqueJobNos(works);

        HostSendAlterKey akey = new HostSendAlterKey();

        // 更新内容のセット
        akey.setAdhocUpdateValue(HostSend.RESULT_QTY, HostSend.SHORTAGE_QTY);
        akey.setAdhocUpdateValue(HostSend.SHORTAGE_QTY, HostSend.RESULT_QTY);
        akey.updateLastUpdatePname(getCallerName());

        // 条件のセット
        akey.setJobNo(jobNos, true);

        HostSendHandler handler = new HostSendHandler(getConnection());
        handler.modify(akey);

        return jobNos;
    }

    /**
     * JOB_NOをキーにして該当する実績の実績棚No.を変更します。
     * 
     * @param works 対象作業
     * @param newLocation 新しい実績棚No.
     * @return 更新対象となったJOB_NO
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象実績が見つからない
     */
    public String[] updateResultLocation(WorkInfo[] works, String newLocation)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績を登録しないパターンであれば更新を行なわない
        if (works.length == 1 && !checkHostSendType(works[0].getConsignorCode(), works[0].getItemCode()))
        {
            return null;
        }

        // 重複する作業No.を集約
        String[] jobNos = WorkInfoController.getUniqueJobNos(works);

        HostSendAlterKey akey = new HostSendAlterKey();

        // 更新内容のセット
        akey.updateResultLocationNo(newLocation);
        akey.updateLastUpdatePname(getCallerName());

        // 条件のセット
        akey.setJobNo(jobNos, true);

        HostSendHandler handler = new HostSendHandler(getConnection());
        handler.modify(akey);

        return jobNos;
    }

    /**
     * JOB_NOをキーにして該当する実績の実績棚No.を変更します。
     * 
     * @param works 対象作業
     * @param newStockId 新しいStockId
     * @param newArea 新しい実績エリア
     * @param newLocation 新しい実績棚No.
     * @return 更新対象となったJOB_NO
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象実績が見つからない
     */
    public String[] updateResultLocation(WorkInfo[] works, String newStockId, String newArea, String newLocation)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績を登録しないパターンであれば更新を行なわない
        if (works.length == 1 && !checkHostSendType(works[0].getConsignorCode(), works[0].getItemCode()))
        {
            return null;
        }

        // 重複する作業No.を集約
        String[] jobNos = WorkInfoController.getUniqueJobNos(works);

        HostSendAlterKey akey = new HostSendAlterKey();

        // 更新内容のセット
        akey.updateStockId(newStockId);
        akey.updateResultAreaNo(newArea);
        akey.updateResultLocationNo(newLocation);
        akey.updateLastUpdatePname(getCallerName());

        // 条件のセット
        akey.setJobNo(jobNos, true);

        HostSendHandler handler = new HostSendHandler(getConnection());
        handler.modify(akey);

        return jobNos;
    }

    /**
     * 荷主または商品コードと、内部管理用コードの比較を行います。<br>
     * 通常利用されるコードであれば true が返されます。
     * 
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @return 通常コードの時 true, システム管理コードの時 false
     */
    public boolean checkHostSendType(String consignorCode, String itemCode)
    {
        // FIXME どこかに共通化しておくべきメソッド
        // 空パレットチェック
        if ((WmsParam.EMPTYPB_CONSIGNORCODE.equals(consignorCode)) && (WmsParam.EMPTYPB_ITEMCODE.equals(itemCode)))
        {
            return false;
        }

        // 異常棚商品チェック
        if ((WmsParam.IRREGULAR_CONSIGNORCODE.equals(consignorCode)) && (WmsParam.IRREGULAR_ITEMCODE.equals(itemCode)))
        {
            return false;
        }

        return true;
    }

    /**
     * 搬送が完了した実績送信情報の報告フラグの更新を行います。
     * 
     * @param carryInfo 対象搬送情報
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象実績が見つからない
     */
    public void updateReportFlag(CarryInfo carryInfo) throws ReadWriteException, NotFoundException
    {
        HostSendHandler handler = new HostSendHandler(getConnection());
        HostSendAlterKey akey = new HostSendAlterKey();
        HostSendSearchKey skey = new HostSendSearchKey();

        // 検索条件
        skey.setSystemConnKey(carryInfo.getCarryKey());
        skey.setReportFlag(SystemDefine.REPORT_FLAG_CARRY);

        // 更新内容のセット
        akey.updateReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        akey.updateLastUpdatePname(getCallerName());

        // 条件のセット
        akey.setReportFlag(SystemDefine.REPORT_FLAG_CARRY);
        akey.setSystemConnKey(carryInfo.getCarryKey());

        if (handler.count(skey) > 0)
        {
            handler.modify(akey);
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * 取得フィールドを検索キーにセットします。
     * 
     * @param key セット先の検索キー
     * @param collects 取得フィールド一覧
     */
    private void setCollectFields(WorkInfoSearchKey key, FieldName[] collects)
    {
        for (FieldName fld : collects)
        {
            key.setCollect(fld);
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
        return "$Id: HostSendController.java 7748 2010-03-29 09:13:01Z kishimoto $";
    }
}
