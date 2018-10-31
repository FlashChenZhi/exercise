//$Id: ReceivingDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $
package jp.co.daifuku.wms.receiving.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.schedule.HostDBDataLoadSCHParams.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.AbstractDBDataLoader;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorFinder;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierFinder;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.HostToEWN;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.IllegalDataException;
import jp.co.daifuku.wms.base.fileentity.Receiving;
import jp.co.daifuku.wms.base.util.DataLoadLogFileWriter;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.HostIOUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.RecordFormatException;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;
import jp.co.daifuku.wms.receiving.operator.ReceivingPlanOperator;


/**
 * <CODE>ReceivingDBDataLoader</CODE>クラスは、入荷データ取込み処理を行うクラスです。<BR>
 * <CODE>AbstractDBDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * Designer : Gary Muhlestein <BR>
 * Maker    : Gary Muhlestein <BR>
 * <BR>
 * @version $Revision: 7650 $, $Date: 2010-03-17 18:31:17 +0900 (水, 17 3 2010) $
 * @author  wms
 * @author  Last commit: $Author: okayama $
 */
public class ReceivingDBDataLoader
        extends AbstractDBDataLoader
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /** Data Loading Key **/
    private static final String LOAD_KEY = "RECEIVE_SUPPORT";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    // Consignor Master Information
    private ConsignorFinder _consignorFinder = null;

    private ConsignorSearchKey _consignorSearchKey = null;

    private Consignor[] _consignorEntity = null;

    private ConsignorHandler _consignorHandler = null;

    // Item Master Information
    private ItemFinder _itemFinder = null;

    private ItemSearchKey _itemSearchKey = null;

    private Item[] _itemEntity = null;

    private ItemHandler _itemHandler = null;

    // Supplier
    private SupplierFinder _supplierFinder = null;

    private SupplierSearchKey _supplierSearchKey = null;

    private Supplier[] _supplierEntity = null;

    private SupplierHandler _supplierHandler = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * An instance called by an auto process.
     */
    public ReceivingDBDataLoader()
    {
        setUserInfo(getAutoUserInfo());
        setDataType(ReceivingInParameter.DATA_TYPE_RECEIVE);
    }

    /**
     * Generates an instance by specifing a DB connection and a caller class.
     *
     * @param conn DB connection
     * @param errorInfoConn DB connection for loading  error information
     */
    public ReceivingDBDataLoader(Connection conn, Connection errorInfoConn)
    {
        super(conn, errorInfoConn);
        setDataType(ReceivingInParameter.DATA_TYPE_RECEIVE);
    }

    /**
     * データベースコネクションと呼び出し元クラスとユーザ情報を指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param userInfo ユーザ情報
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public ReceivingDBDataLoader(Connection conn, Connection errorInfoConn, DfkUserInfo userInfo)
    {
        super(conn, errorInfoConn, userInfo);
        setDataType(ReceivingInParameter.DATA_TYPE_RECEIVE);
    }

    /**
     * Gets SystemInParameter Object and WareNaviSystem Object as parameters, 
     * loads Receiving Plan data.<BR>
     * 
     * @param sysController  WarenaviSystemController Object
     * @param inEntity HostToEWN Entity
     * @param outParameter Parameter
     * @return True: normal process, False: otherwise
     * @throws CommonException Thrown when an exception occurs
     * @throws NumberFormatException Thrown when an exception occurs
     */
    public boolean loadEntity(WarenaviSystemController sysController, HostToEWN inEntity, Params outParameter)
            throws CommonException
    {
        // Generates an instance
        prepare();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        setEntryTimestamp(df.format(inEntity.getMessageDate()));

        setLineData(inEntity.getEntireRecord());
        setSequenceno(inEntity.getSequenceNo());
        setFileName(existFiles() + outParameter.getString(REGIST_TIME) + ReceivingInParameter.EXTENSION);

        // Copy flag
        boolean isFileCopy = false;
        getErrlist().setFileName(getFileName());

        //even though we're not reading a file, we need the handler to tell us the offsets of the fields we are reading
        Receiving entity = new Receiving();
        FileHandler handler = AbstractFileHandler.getInstance(entity);

        try
        {
            // Sets loading start date
            setStartDate(DbDateUtil.getTimeStamp());

            // Loads data
            try
            {
                setAllItemCount(outParameter.getInt(ATTEMPTED_LOADLINES_COUNT));
                // データ取込処理
                loadData(sysController, handler, inEntity);
            }

            catch (ValidateException e)
            {
                // When contents of data are incorrect, Skips
                setSkipFlag(true);
                if (!isErrorFlag())
                {
                    setErrorFlag(true);
                    // 6023438=Invalid format data found. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                    createErrorLog(WmsMessageFormatter.format(6023438, "HostToEWN", getEntryTimestamp(), "DATA",
                            getLineData()), getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR, "", getLineData(), this.getClass());

                    ValidateError[] errors = e.getValidateErrors();
                    for (ValidateError error : errors)
                    {
                        // When there is no required heading, does not set All NG.
                        if (ValidateError.RETURN_REQUIRE_ERROR == error.getErrorCode())
                        {
                            setErrorFlag(false);
                        }
                    }
                }
            }
            catch (NumberFormatException e)
            {
                setSkipFlag(true);
                setErrorFlag(true);
                // 6023438=Invalid format data found. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023438, "HostToEWN", getEntryTimestamp(), "DATA",
                        getLineData()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR, "", getLineData(), this.getClass());
            }
            catch (LockTimeOutException e)
            {
                setSkipFlag(true);
                // 6023419="Skipped. Target data being processed by another terminal.  Table:{0} Timestamp:{1}"
                createErrorLog(WmsMessageFormatter.format(6023419, "HostToEWN", getEntryTimestamp()), getLineData(),
                        this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_CANCELLATION_DATA_LOCK, "", getLineData(), this.getClass());
            }
            if (isErrorFlag())
            {
                setRegistFlag(false);
                // 6023027 = "Error in Load Plan Data. The process is aborted. See log."
                setMessage(WmsMessageFormatter.format(6023027));
                return false;
            }
            else
            {
                if (isRegistFlag())
                {
                    // if all data is skipped, give up the process
                    log_write(getConnection(), EmConstants.OPELOG_CLASS_SETTING);
                }
                return true;
            }
        }
        catch (IOException e)
        {
            setErrorFlag(true);
            // 6007031 = "File I/O error occurred. See log."
            setMessage("6007031");
            return false;
        }
        catch (ReadWriteException e)
        {
            setErrorFlag(true);
            // 6007031 = "File I/O error occurred. See log."
            setMessage("6007031");
            return false;
        }
        catch (Exception e)
        {
            setErrorFlag(true);
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        finally
        {
            // loadメソッドのfinally処理
            // エラーファイルの書き込み、取込ファイルの削除、BSRの書き込みを行なう
            if (!loadFinallyProc(handler, isFileCopy))
            {
                return false;
            }
        }
    }

    /**
     * Gets a line one by one from a loading file, and loads it to BD.
     * @param sysController  WarenaviSystemController Object
     * @param handler  Filehandler
     * @param inEntity HostToEWN Entity
     * @return True when normal, otherwise fasle.
     * @throws CommonException Thrown when an exception occurs
     * @throws IOException thrown when a file I/O error occurs.
     * @throws SQLException SQLでエラーが発生した場合に通知されます。
     */
    protected boolean loadData(WarenaviSystemController sysController, FileHandler handler, HostToEWN inEntity)
            throws CommonException,
                IOException,
                SQLException
    {
        ReceivingPlanOperator planOperator = new ReceivingPlanOperator(getConnection(), this.getClass());
        ReceivingInParameter inParam = null;
        // counter for loading data
        int dataCount = 0;
        // Laoding data end flag


        // iterates until no more data

        try
        {
            // Counting the number ofloading
            dataCount++;
            setAllItemCount(dataCount);
            // Gets a line from a loading file
            inParam = convertToParam(sysController, inEntity);
            if (null == inParam)
            {
                // Exits when END of file is reached.
                return false;
            }
            // Exits when the number of loading exceeds the maximum loading.
            int maxCount = getMaxRecord(ExchangeEnvironment.DATA_TYPE_RECEVING);
            if (getAllItemCount() > maxCount && maxCount >= 0)
            {
                // All NG
                setErrorFlag(true);
                // 6023041 = "Processing was interrupted because the records count had exceeded {0}. File name:{1}"
                setMessage(WmsMessageFormatter.format(6023041, WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_RECEIVE),
                        "HostToEWN"));
                createErrorLog(WmsMessageFormatter.format(6023041,
                        WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_RECEIVE), "HostToEWN"), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_OVER_LINES, "", "", this.getClass());
                return false;
            }

            // Checks values.  Skips a line if they are incorrect.
            if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getCancelFlag())
                    && !check(inParam, handler, sysController))
            {
                setSkipFlag(true);
                return false;
            }

            ReceivingPlan[] entitys = (ReceivingPlan[])planOperator.findPlan(inParam);
            // Checks if target data exist in DB.
            if (entitys != null && entitys.length > 0)
            {
                if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getCancelFlag()))
                {
                    // Duplicated loading (The same data already exits.)
                    setSkipFlag(true);
                    // 6023441=Skipped. Same data already exists. Table: {0} Timestamp: {1}
                    createErrorLog(WmsMessageFormatter.format(6023441, "HostToEWN", getEntryTimestamp()),
                            getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_REPETITION_DATA, "", getLineData(), this.getClass());
                    return false;
                }
                else
                {
                    // Checks if all searched jobs are "unstarted" or "deleted".
                    boolean isStart = false;
                    for (ReceivingPlan planEntity : entitys)
                    {
                        if (!SystemDefine.STATUS_FLAG_UNSTART.equals(planEntity.getStatusFlag())
                                && !SystemDefine.STATUS_FLAG_DELETE.equals(planEntity.getStatusFlag()))
                        {
                            isStart = true;
                        }
                    }

                    if (isStart)
                    {
                        // In case that data are "delete data and  unstarted" or "delete data" or "other data except deletion" 
                        setSkipFlag(true);
                        // 6023416="Skipped. Target data being processed.  Table:{0} Timestamp:{1}"
                        createErrorLog(WmsMessageFormatter.format(6023416, "HostToEWN", getEntryTimestamp()),
                                getLineData(), this.getClass());
                        createLoadErrorInfo(SystemDefine.ERROR_FLAG_CANCELLATION_DATA_STARTED, "", getLineData(),
                                this.getClass());
                        return false;
                    }

                    if (!isErrorFlag())
                    {
                        // Locks plan/work data to delete
                        if (!lockReceivingPlan(inParam) || !lockWorkInfo(inParam))
                        {
                            // When no target exists
                            setSkipFlag(true);

                            // 6023418=Skipped. There is no target data to delete. Table:{0} Timestamp:{1}
                            createErrorLog(WmsMessageFormatter.format(6023418, "HostToEWN", getEntryTimestamp()),
                                    getLineData(), this.getClass());
                            createLoadErrorInfo(SystemDefine.ERROR_FLAG_NO_CANCELLATION_DATA, "", getLineData(),
                                    this.getClass());
                            return false;
                        }
                        setRegistFlag(true);
                        // Updates it to  delete data
                        planOperator.deletePlan(SystemDefine.REGIST_KIND_DATALOADER, inParam);
                    }
                }
            }
            else
            {
                if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getCancelFlag()))
                {
                    if (!isErrorFlag())
                    {
                        setRegistFlag(true);
                        // Registers receiving information
                        planOperator.createPlan(SystemDefine.REGIST_KIND_DATALOADER, inParam);
                    }
                }
                else
                {
                    // When no target exists
                    setSkipFlag(true);
                    // 6023418="Skipped. There is no target data to delete. Table:{0} Timestamp:{1}"
                    createErrorLog(WmsMessageFormatter.format(6023418, "HostToEWN", getEntryTimestamp()),
                            getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_NO_CANCELLATION_DATA, "", getLineData(),
                            this.getClass());
                    return false;
                }
            }
            // Adds noraml data to a class that creates a loading error file.
            getErrlist().addStatusFile(DataLoadLogFileWriter.STATUS_NORMAL, getLineData(), "");
        }
        catch (ParseException e)
        {
            setErrorFlag(true);
            setSkipFlag(true);
            return false;
        }
        catch (ValidateException e)
        {
            // When contents ofdata are incorrect, Skips
            setSkipFlag(true);
            if (!isErrorFlag())
            {
                setErrorFlag(true);
                ValidateError[] errors = e.getValidateErrors();
                for (ValidateError error : errors)
                {
                    // When there is no required heading, does not set All NG.
                    if (ValidateError.RETURN_REQUIRE_ERROR == error.getErrorCode())
                    {
                        setErrorFlag(false);
                    }
                }
            }
            return false;

        }
        catch (IllegalDataException e)
        {
            // 項目内容異常の場合(スキップ)
            setSkipFlag(true);
            if (!isErrorFlag())
            {
                // 警告あれベルの場合は全NGにしない
                if (IllegalDataException.ERROR_LEVEL_WARN == e.getErrorLevel())
                {
                    setErrorFlag(false);
                }
                else
                {
                    setErrorFlag(true);
                }
            }
            return false;
        }
        catch (LockTimeOutException e)
        {
            setSkipFlag(true);
            // 6023419="Skipped. Target data being processed by another terminal.  Table:{0} Timestamp:{1}"
            createErrorLog(WmsMessageFormatter.format(6023419, "HostToEWN", getEntryTimestamp()), getLineData(),
                    this.getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_CANCELLATION_DATA_LOCK, "", getLineData(), this.getClass());
            return false;
        }


        return true;
    }


    /**
     * Make BSR logging accessible to the SCH
     * @param skip スキップフラグ
     * @param regist 登録フラグ
     */
    public void doBSRLog(boolean skip, boolean regist)
    {
        setSkipFlag(skip);
        setRegistFlag(regist);
        createBSRLog(this.getClass(), true);

    }

    /**
     * Get my loading limit from WMSParam.properties
     * 
     * @return My limit
     */
    public int getMyLoadingLimit()
    {
        return WmsParam.MAX_RECORD_RECEIVE;
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
     * EnvironmentInformation.propertiesに定義してある各データ取込キー名を取得します。
     * 
     * @return データ取込キー
     */
    @Override
    protected String getDataLoadKey()
    {
        return LOAD_KEY;
    }

    /**
     * Sets file records to parameters.
     * @param inEntity HostToEWN Entity
     * @param sysController WarenaviSystemController class
     * @return File records and set parameters
     * @throws ReadWriteException Reports when something abnormal occurs in accessing to DB
     * @throws RecordFormatException Reports when a value of heading specified in a file is incorrect.
     * @throws IOException Reports in case of a failure of file i/O
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     * @throws IllegalDataException 指定された値が不正だった場合に通知します（ファイルハンドラでのチェック以外の項目）。 
     */
    protected ReceivingInParameter convertToParam(WarenaviSystemController sysController, HostToEWN inEntity)
            throws ReadWriteException,
                RecordFormatException,
                IOException,
                ValidateException,
                IllegalDataException
    {
        try
        {
            Receiving entity = new Receiving();
            try
            {

                HostIOUtil handler = new HostIOUtil();

                handler.open(entity, null);

                // ファイルハンドラより一行取得
                entity = (Receiving)handler.next(inEntity.getData());

                if (entity == null)
                {
                    // 取得できなかったらnullを返す
                    return null;
                }

                // Stores data of a loaded line
                setLineData(cutCRLF(String.valueOf(entity.getValue(FileHandler.FIELD_ORIGINAL_RECORD))));
            }
            catch (ValidateException e)
            {
                if (!StringUtil.isBlank(e.getRecordString()))
                {
                    setLineData(cutCRLF(e.getRecordString()));
                }
                validateExceptionMsg(e, this.getClass());
                throw e;
            }

            // Reset a heading counter
            setItemcount(0);

            ReceivingInParameter inParam = new ReceivingInParameter(null);

            // Loading Unit Key(File Name without an extension)
            String filePrefix =
                    getFileName().substring(0, (getFileName().length() - ReceivingInParameter.EXTENSION.length()));
            inParam.setLoadUnitKey(filePrefix);
            // File Line No.
            inParam.setRowNo(getAllItemCount());
            // Cancel Type
            inParam.setCancelFlag(getValue(entity.getCancelFlag().trim(), Receiving.CANCEL_FLAG, true));
            // Planned Date
            inParam.setReceivingPlanDay(getDateValue(entity.getPlanDay().trim(), Receiving.PLAN_DAY, true));
            // Consignor Code
            inParam.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            // Supplier Code
            inParam.setSupplierCode(getAsciiValue(entity.getSupplierCode().trim(), Receiving.SUPPLIER_CODE, true));
            // Supplier Name
            inParam.setSupplierName(getValue(StringUtil.rtrim(entity.getSupplierName()), Receiving.SUPPLIER_NAME));
            // Slip No.
            inParam.setTicketNo(getAsciiValue(entity.getReceiveTicketNo().trim(), Receiving.RECEIVE_TICKET_NO, true));
            // Slip Row No.
            inParam.setTicketLineNo(getIntValue(entity.getReceiveLineNo(), Receiving.RECEIVE_LINE_NO));
            // Item Code
            inParam.setItemCode(getAsciiValue(entity.getItemCode().trim(), Receiving.ITEM_CODE, true));
            // Item Name
            inParam.setItemName(getValue(StringUtil.rtrim(entity.getItemName()), Receiving.ITEM_NAME));
            // JAN Code
            inParam.setJanCode(getAsciiValue(entity.getJan().trim(), Receiving.JAN));
            // Case ITF
            inParam.setItf(getAsciiValue(entity.getItf().trim(), Receiving.ITF));
            // Quantity of pieces in one case
            inParam.setEnteringQty(getIntValue(entity.getEnteringQty(), Receiving.ENTERING_QTY));
            // Lot No.
            inParam.setLotNo(getAsciiValue(entity.getLotNo().trim(), Receiving.LOT_NO));
            // Planned quantity
            inParam.setPlanQty(getIntValue(entity.getPlanQty(), Receiving.PLAN_QTY));

            // When the data is normal and there is Master Control, Complements  headings from the Master.
            if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getCancelFlag()) && sysController.hasMasterPack())
            {
                // Gets Consignor Name from Consignor Master Information 
                if (!StringUtil.isBlank(inParam.getConsignorCode()))
                {
                    // Sets search conditions
                    _consignorSearchKey.clear();
                    _consignorSearchKey.setConsignorCode(inParam.getConsignorCode());

                    // Consignor Master Information search
                    _consignorEntity = (Consignor[])_consignorHandler.find(_consignorSearchKey);

                    if (_consignorEntity.length >= 1 && !StringUtil.isBlank(_consignorEntity[0].getConsignorName()))
                    {
                        // Sets an acquired name from Consignor Master Information
                        inParam.setConsignorName(_consignorEntity[0].getConsignorName().trim());
                    }
                }

                // Gets Supplier Name from Supplier Master Information 
                if (!StringUtil.isBlank(inParam.getSupplierCode()))
                {
                    // Sets search conditions
                    _supplierSearchKey.clear();
                    _supplierSearchKey.setConsignorCode(inParam.getConsignorCode());
                    _supplierSearchKey.setSupplierCode(inParam.getSupplierCode());

                    // Supplier Master Information search
                    _supplierEntity = (Supplier[])_supplierHandler.find(_supplierSearchKey);

                    if (_supplierEntity.length >= 1 && !StringUtil.isBlank(_supplierEntity[0].getSupplierName()))
                    {
                        // Sets an acquired name from Supplier Master Information
                        inParam.setSupplierName(_supplierEntity[0].getSupplierName().trim());
                    }
                }

                // Item Master Informationより項目を取得
                if (!StringUtil.isBlank(inParam.getItemCode()))
                {
                    // Sets search conditions
                    _itemSearchKey.clear();
                    _itemSearchKey.setConsignorCode(inParam.getConsignorCode());
                    _itemSearchKey.setItemCode(inParam.getItemCode());

                    // Item Master Information search
                    _itemEntity = (Item[])_itemHandler.find(_itemSearchKey);

                    if (_itemEntity.length >= 1)
                    {
                        // Item Name
                        if (!StringUtil.isBlank(_itemEntity[0].getItemName()))
                        {
                            inParam.setItemName(_itemEntity[0].getItemName().trim());
                        }

                        // JAN Code
                        if (!StringUtil.isBlank(_itemEntity[0].getJan()))
                        {
                            inParam.setJanCode(_itemEntity[0].getJan().trim());
                        }

                        // Case ITF
                        if (!StringUtil.isBlank(_itemEntity[0].getItf()))
                        {
                            inParam.setItf((_itemEntity[0].getItf().trim()));
                        }

                        // Quantity of pieces in one case
                        inParam.setEnteringQty(_itemEntity[0].getEnteringQty());
                    }
                }
            }
            return inParam;
        }
        catch (RecordFormatException e)
        {
            // Outputs no empty row in STATUS.txt
            if (!StringUtil.isBlank(e.getRecordString()))
            {
                // 6023438=Invalid format data found. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023438, "HostToEWN", getEntryTimestamp(), "DATA",
                        getLineData()), getLineData(), this.getClass());
            }
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_ITEM_NUMBER_ERROR, "", cutCRLF(e.getRecordString()),
                    this.getClass());
            throw e;
        }
    }

    /**
     * Checks parameters values<BR>
     * Skips a row when there is NG in this method.<BR>
     * @param inParam ReceivingInParameter Class
     * @param handler FileHandler Class
     * @param sysController WarenaviSystemController Class
     * @return True when normal, otherwise false.
     * @throws ReadWriteException Reports when something abnormal occurs in accessing to DB
     * @throws IOException Reports in case of a failure of file i/O
     * @throws ValidateException Thrown when specified values are incorrect.
     */
    protected boolean check(ReceivingInParameter inParam, FileHandler handler, WarenaviSystemController sysController)
            throws ReadWriteException,
                IOException,
                ValidateException
    {
        // Checks when data is normal. 
        if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(inParam.getCancelFlag()))
        {
            return true;
        }

        // MASTER Information check
        if (sysController.hasMasterPack())
        {
            boolean ret = true;
            // checks if Consignor Code is registered to Consignor Master Information.
            if (_consignorEntity.length <= 0)
            {
                // 6023428 = "Consignor Code does not exist in Consignor Master. Table:{0} Timestamp:{1} Field: {2} Value: {3}"
                createErrorLog(WmsMessageFormatter.format(6023428, "HostToEWN", getEntryTimestamp(), "CONSIGNOR_CODE",
                        inParam.getConsignorCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_MASTER_UNREGIST, "荷主コード", getLineData(), this.getClass());
                ret = false;
            }

            // checks if Supplier Code is registered to Supplier Master Information.
            if (_supplierEntity.length <= 0)
            {
                // 6023429 = "Supplier Code does not exist in Supplier Master. Table:{0} TImestamp:{1} Field: {2} Value: {3}"
                createErrorLog(WmsMessageFormatter.format(6023429, "HostToEWN", getEntryTimestamp(),
                        Receiving.SUPPLIER_CODE, inParam.getSupplierCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_MASTER_UNREGIST,
                        getEntity().getStoreMetaData().getFieldMetaData(Receiving.SUPPLIER_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                ret = false;
            }

            // checks if Item Code is registered to Item Master Information.
            if (_itemEntity.length <= 0)
            {
                // 6023430 = "Item Code does not exist in Item Master. Table:{0} TImestamp:{1} Field: {2} Value: {3}"
                createErrorLog(WmsMessageFormatter.format(6023430, "HostToEWN", getEntryTimestamp(),
                        Receiving.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_MASTER_UNREGIST,
                        getEntity().getStoreMetaData().getFieldMetaData(Receiving.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                ret = false;
            }
            // Abnormal location Item Code input check・簡易直行用商品コード入力チェック
            else if (WmsParam.IRREGULAR_ITEMCODE.equals(inParam.getItemCode())
                    || WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(inParam.getItemCode()))
            {
                // 6023431 = "Unable to add. The specified Item Code is already used by the system. Table: {0} Timestamp: {1}"
                createErrorLog(WmsMessageFormatter.format(6023431, "HostToEWN", getEntryTimestamp()), getLineData(),
                        this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                        getEntity().getStoreMetaData().getFieldMetaData(Receiving.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                ret = false;
            }

            if (!ret)
            {
                return ret;
            }
        }

        // Quantity check
        if (inParam.getPlanQty() <= 0)
        {
            // 6023432 = "Data with Plan Qty 0 or less found. Table: {0} Timestamp: {1}"
            createErrorLog(WmsMessageFormatter.format(6023432, "HostToEWN", getEntryTimestamp()), getLineData(),
                    this.getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                    getEntity().getStoreMetaData().getFieldMetaData(Receiving.PLAN_QTY.getName()).getDescription(),
                    getLineData(), this.getClass());
            return false;
        }
        else if (inParam.getPlanQty() > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023433 = "Plan Qty exceeds the Max Work Qty {0}. Table:{1} Timestamp:{2}"
            createErrorLog(WmsMessageFormatter.format(6023433, WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY),
                    "HostToEWN", getEntryTimestamp()), getLineData(), this.getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                    getEntity().getStoreMetaData().getFieldMetaData(Receiving.PLAN_QTY.getName()).getDescription(),
                    getLineData(), this.getClass());
            return false;
        }
        return true;
    }

    /**
     * Locks undone receiving plan information for loading plans.<BR>
     * Returns false when there is no lock target.<BR>
     *
     * @param inParam Lock target
     * @return True when lock process is done, otherwise fasle.
     * @throws ReadWriteException Thorwn when DB access error occurs.
     * @throws LockTimeOutException Thrown in case that the given lock target has already been  locked.
     * @throws NoPrimaryException Thrown in case that Until Key is duplicated.
     */
    protected boolean lockReceivingPlan(ReceivingInParameter inParam)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException
    {
        AbstractDBHandler sph = new ReceivingPlanHandler(getConnection());
        ReceivingPlanSearchKey key = new ReceivingPlanSearchKey();

        key.setConsignorCode(inParam.getConsignorCode());
        key.setPlanDay(inParam.getReceivingPlanDay());
        key.setReceiveTicketNo(inParam.getTicketNo());
        key.setReceiveLineNo(inParam.getTicketLineNo());
        key.setStatusFlag(ReceivingPlan.STATUS_FLAG_UNSTART);

        if (sph.findPrimaryForUpdate(key, ReceivingPlanHandler.WAIT_SEC_NOWAIT) == null)
        {
            // No Lock target
            return false;
        }

        return true;
    }

    /**
     * Locks undone receiving work information for loading plans.<BR>
     * Returns false when there is no lock tartegt.<BR>
     *
     * @param inParam Lock target
     * @return True when lock process is done, otherwise fasle.
     * @throws ReadWriteException Thorwn when DB access error occurs.
     * @throws LockTimeOutException Thrown in case that the given lock target has already been  locked.
     * @throws NoPrimaryException Thrown in case that Until Key is duplicated.
     */
    protected boolean lockWorkInfo(ReceivingInParameter inParam)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException
    {
        try
        {
            // Generates an instance of System Definition Information Access Controller.
            WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), this.getClass());
            // Process only when stock packages are not introduced
            if (!sysController.hasStockPack())
            {
                AbstractDBHandler wih = new ReceivingWorkInfoHandler(getConnection());
                ReceivingWorkInfoSearchKey key = new ReceivingWorkInfoSearchKey();

                key.setConsignorCode(inParam.getConsignorCode());
                key.setPlanDay(inParam.getReceivingPlanDay());
                key.setReceiveTicketNo(inParam.getTicketNo());
                key.setReceiveLineNo(inParam.getTicketLineNo());
                key.setKey(ReceivingWorkInfo.JOB_TYPE, SystemDefine.JOB_TYPE_RECEIVING);
                key.setKey(ReceivingWorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_UNSTART);

                if (wih.findPrimaryForUpdate(key, ReceivingWorkInfoHandler.WAIT_SEC_NOWAIT) == null)
                {
                    // No Lock target
                    return false;
                }
            }
            return true;
        }
        catch (ScheduleException e)
        {
            e.printStackTrace();
            throw new ReadWriteException(e);
        }
    }

    /**
     * Initializes
     * 
     */
    protected void prepare()
    {
        // Consignor Master Information
        _consignorHandler = new ConsignorHandler(getConnection());
        _consignorFinder = new ConsignorFinder(getConnection());
        _consignorSearchKey = new ConsignorSearchKey();
        // Item Master Information
        _itemFinder = new ItemFinder(getConnection());
        _itemSearchKey = new ItemSearchKey();
        _itemHandler = new ItemHandler(getConnection());
        // Supplier Master Information
        _supplierFinder = new SupplierFinder(getConnection());
        _supplierSearchKey = new SupplierSearchKey();
        _supplierHandler = new SupplierHandler(getConnection());

        _itemEntity = null;
        _supplierEntity = null;
    }

    /**
     * 自動処理時にuserInfoをセットするメソッドです。
     * @return ユーザ情報
     */
    protected DfkUserInfo getAutoUserInfo()
    {
        DfkUserInfo userInfo = new DfkUserInfo();
        //DS番号
        userInfo.setDsNumber(DsNumberDefine.DS_AUTOLOAD);
        // ユーザID
        userInfo.setUserId(WmsParam.SYS_USER_ID);
        // ユーザ名称
        userInfo.setUserName(WmsParam.SYS_USER_NAME);
        // 端末No
        userInfo.setTerminalNumber(SERVER_TERMINAL_NO);
        // 端末名称
        userInfo.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
        // 端末IPアドレス
        userInfo.setTerminalAddress(WmsParam.SYS_IP_ADDRESS);
        // リソース番号
        userInfo.setPageNameResourceKey(DsNumberDefine.PAGERESOUCE_AUTOLOAD);

        return userInfo;
    }

    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   conn            データベースコネクション
     * @param   operationKind  操作区分
     * @throws SQLException SQLでエラーが発生した場合に通知されます。
     * @throws ReadWriteException データベースエラーが発生した場合にスローされます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void log_write(Connection conn, int operationKind)
            throws SQLException,
                ReadWriteException,
                ScheduleException
    {
        // オペレーションログ出力
        List<String> itemLog = new ArrayList<String>();

        // データ区分
        itemLog.add(ReceivingInParameter.DATA_TYPE_RECEIVE);
        // ファイル名称
        itemLog.add(getFileName());

        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(conn);

        // 常駐処理からの自動起動時、ユーザ情報がセットされないのでWareNavi固定値にて再編集を行います。
        DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();

        opeLogWriter.createOperationLog(userInfo, operationKind, itemLog);
    }

    /**
     * Find some supplier params
     *
     * @param sKey The pre-configured search key.
     * @return Supplier[]
     * @throws ReadWriteException Thrown when a database error occurs.
     */
    protected Supplier[] findSupplier(SupplierSearchKey sKey)
            throws ReadWriteException
    {
        Supplier[] entities = null;
        try
        {
            _supplierFinder.open(true);

            // 検索処理実行
            _supplierFinder.search(sKey);
            entities = (Supplier[])_supplierFinder.getEntities(0, 1); //just need one
        }
        finally
        {
            // now that the search is done, close the finder
            _supplierFinder.close();
        }
        return entities;
    }

    /**
     * Find some consignor params
     *
     * @param sKey The pre-configured search key.
     * @return Consignor[]
     * @throws ReadWriteException Thrown when a database error occurs.
     */
    protected Consignor[] findConsignor(ConsignorSearchKey sKey)
            throws ReadWriteException
    {
        Consignor[] entities = null;
        try
        {
            _consignorFinder.open(true);

            _consignorFinder.search(sKey);

            entities = (Consignor[])_consignorFinder.getEntities(0, 1); //just need one
        }
        finally
        {
            // now that the search is done, close the finder
            _consignorFinder.close();
        }
        return entities;
    }

    /**
     * Find some item params
     *
     * @param sKey The pre-configured search key.
     * @return Item[]
     * @throws ReadWriteException Thrown when a database error occurs.
     */
    protected Item[] findItem(ItemSearchKey sKey)
            throws ReadWriteException
    {
        Item[] entities = null;
        try
        {
            _itemFinder.open(true);

            // Execute search process
            _itemFinder.search(sKey);

            entities = (Item[])_itemFinder.getEntities(0, 1); //just need one

        }
        finally
        {
            // now that the search is done, close the finder
            _itemFinder.close();
        }
        return entities;
    }

    /**
     * 取込データのファイル名を取得
     * @return String ファイル名を返します。
     * @exception ReadWriteException 例外が発生した場合に通知されます。
     */
    public String existFiles()
            throws ReadWriteException
    {
        // 環境設定ファイルから格納フォルダ、ファイル名を取得
        IniFileOperator iO = new IniFileOperator(WmsParam.ENVIRONMENT);
        // ファイル名
        String file_name = iO.get(DATALOAD_FILENAME, LOAD_KEY);

        return file_name;
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new Receiving();
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns a revision of this class.
     * @return String of a revision.
     */
    public static String getVersion()
    {
        return "$Id: ReceivingDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $";
    }
}
