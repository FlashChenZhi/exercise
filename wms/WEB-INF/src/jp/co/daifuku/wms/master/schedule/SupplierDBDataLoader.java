//$Id: SupplierDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.AbstractDBDataLoader;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierFinder;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.HostToEWN;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.IllegalDataException;
import jp.co.daifuku.wms.base.fileentity.HostSupplier;
import jp.co.daifuku.wms.base.util.DataLoadLogFileWriter;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.HostIOUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.RecordFormatException;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;


/**
 * <CODE>SupplierDBDataLoader</CODE>クラスは、仕入先マスタ取込み処理を行うクラスです。<BR>
 * <CODE>AbstractDBDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * @version $Revision: 7650 $, $Date: 2010-03-17 18:31:17 +0900 (水, 17 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class SupplierDBDataLoader
        extends AbstractDBDataLoader
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /** データ報告キー **/
    private static final String LOAD_KEY = "MASTER_SUPPLIER";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    // 仕入先マスタ情報
    private SupplierHandler _supplierHandler = null;

    private SupplierSearchKey _supplierSearchKey = null;

    private Supplier[] _supplierEntity = null;

    // 入荷予定情報
    private ReceivingPlanFinder _receivePlanFinder = null;

    private ReceivingPlanSearchKey _receivePlanSearchKey = null;

    // TC予定情報
    private CrossDockPlanFinder _xdPlanFinder = null;

    private CrossDockPlanSearchKey _xdPlanSearchKey = null;

    // 入庫予定情報
    private StoragePlanFinder _storagePlanFinder = null;

    private StoragePlanSearchKey _storagePlanSearchKey = null;

    // 入出庫作業情報
    private WorkInfoFinder _workinfoFinder = null;

    private WorkInfoSearchKey _workinfoSearchKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 自動処理より呼ばれるインスタンスです。
     */
    public SupplierDBDataLoader()
    {
        setDataType(SystemDefine.LOAD_DATA_TYPE_MASTER_SUPPLIER);
    }

    /**
     * データベースコネクションと呼び出し元クラスを指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public SupplierDBDataLoader(Connection conn, Connection errorInfoConn)
    {
        super(conn, errorInfoConn);
        setDataType(SystemDefine.LOAD_DATA_TYPE_MASTER_SUPPLIER);
    }

    /**
     * データベースコネクションと呼び出し元クラスとユーザ情報を指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param userInfo ユーザ情報
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public SupplierDBDataLoader(Connection conn, Connection errorInfoConn, DfkUserInfo userInfo)
    {
        super(conn, errorInfoConn, userInfo);
        setDataType(SystemDefine.LOAD_DATA_TYPE_MASTER_SUPPLIER);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * Get my loading limit from WMSParam.properties
     * 
     * @return My limit
     */
    public int getMyLoadingLimit()
    {
        return WmsParam.MAX_RECORD_SUPPLIER;
    }

    /**
     * A single incoming DB line has already been cast to a host supplier entity, let's see if we can send it to the DB process
     * WareNaviSystem cast an incoming entity as a parameter<BR>
     * 仕入先マスタデータを取込みます。<BR>
     * @param sysController  WarenaviSystemControllerオブジェクト
     * @param inEntity HostToEWN Entity
     * @param outParameter パラメータ
     * @return 実行結果（正常：true 異常：false）
     * @throws CommonException 例外が発生した場合に通知されます。
     * @throws IOException ファイル入出力エラーが発生した場合に通知されます。
     */
    public boolean loadEntity(WarenaviSystemController sysController, HostToEWN inEntity, Params outParameter)
            throws CommonException,
                IOException
    {
        // インスタンスの生成
        prepare(sysController);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        setEntryTimestamp(df.format(inEntity.getMessageDate()));

        setLineData(inEntity.getEntireRecord());
        setSequenceno(inEntity.getSequenceNo());
        setFileName(existFilesCount() + outParameter.getString(REGIST_TIME) + MasterInParameter.EXTENSION);

        HostSupplier entity = new HostSupplier();
        FileHandler handler = AbstractFileHandler.getInstance(entity); //we'll need this when it comes time to create the log
        boolean isFileCopy = false;
        getErrlist().setFileName(getFileName());

        try
        {
            // 取込開始日時をセット
            setStartDate(DbDateUtil.getTimeStamp());

            // データ取込処理
            try
            {
                // 取込件数を加算
                setAllItemCount(outParameter.getInt(ATTEMPTED_LOADLINES_COUNT));

                // データ取込処理
                loadData(sysController, handler, inEntity);

            }
            catch (ValidateException e)
            {
                // 項目内容異常の場合(スキップ)
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
                        // 必須項目が無い場合の異常については全NGにしない
                        if (ValidateError.RETURN_REQUIRE_ERROR == error.getErrorCode())
                        {
                            setErrorFlag(false);
                        }
                    }
                }
            }
            catch (DataExistsException e)
            {
                // 6023419=Skipped. Target data being processed by another terminal.  Table:{0} Timestamp:{1}
                createErrorLog(WmsMessageFormatter.format(6023419, "HostToEWN", getEntryTimestamp()), getLineData(),
                        this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_CANCELLATION_DATA_LOCK, "", getLineData(), this.getClass());

                setSkipFlag(true);
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

            if (isErrorFlag())
            {
                setRegistFlag(false);
                // 6023027=異常なデータがあったため、処理を中止しました。ログを参照してください。
                setMessage(WmsMessageFormatter.format(6023027));
                return false;
            }
            else
            {
                if (isRegistFlag())
                {
                    // 全データがスキップされた場合は処理を飛ばす
                    log_write(getConnection(), EmConstants.OPELOG_CLASS_SETTING);
                }
                return true;
            }
        }
        catch (IOException e)
        {
            setErrorFlag(true);
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage("6007031");
            return false;
        }
        catch (ReadWriteException e)
        {
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage("6007031");
            return false;
        }
        catch (Exception e)
        {
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
     * 取込ファイルから一行ずつ取得し、DBへの取込処理を行ないます。
     * @param sysController  WarenaviSystemControllerオブジェクト
     * @param handler  ファイルハンドラ
     * @param inEntity HostToEWN Entity
     * @return 実行結果（正常：true 異常：false）
     * @throws CommonException 例外が発生した場合に通知されます。
     * @throws IOException ファイル入出力エラーが発生した場合に通知されます。
     */
    protected boolean loadData(WarenaviSystemController sysController, FileHandler handler, HostToEWN inEntity)
            throws CommonException,
                IOException
    {
        MasterInParameter inParam = null;
        // 取込データ件数
        int dataCount = 0;
        // 取込データ終了フラグ


        try
        {
            // 取込件数を加算
            dataCount++;
            setAllItemCount(dataCount);
            // 取込ファイルから一行取得
            inParam = convertToParam(sysController, inEntity);
            if (null == inParam)
            {
                return false;
            }
            // If the loading count exceeds the upper limit then terminate the loading process
            int maxCount = getMaxRecord(ExchangeEnvironment.DATA_TYPE_MASTER_SUPPLIER);
            if (getAllItemCount() > maxCount && maxCount >= 0)
            {
                // 全NG
                setErrorFlag(true);
                // 6023041=Suspended the process. Number of records exceeds {0}. File name:{1}
                setMessage(WmsMessageFormatter.format(6023041, WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_SUPPLIER),
                        "HostToEWN"));
                createErrorLog(WmsMessageFormatter.format(6023041,
                        WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_SUPPLIER), "HostToEWN"), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_OVER_LINES, "", "", this.getClass());
                return false;
            }

            // 仕入先マスタ検索処理
            _supplierSearchKey.clear();
            _supplierSearchKey.setConsignorCode(inParam.getConsignorCode());
            _supplierSearchKey.setSupplierCode(inParam.getSupplierCode());

            _supplierEntity = (Supplier[])_supplierHandler.find(_supplierSearchKey);

            // 対象データがDBに存在するかチェック
            if (_supplierEntity != null && _supplierEntity.length > 0)
            {
                if (SystemDefine.MASTERDATA_LOAD_FLAG_NORMAL.equals(inParam.getLoadFlag()))
                {
                    // 通常データで対象がすでに存在した場合(二重取込)
                    setSkipFlag(true);
                    // 6020040=すでに同一データが存在するため、スキップしました。ファイル:{0} 行:{1}
                    createErrorLog(WmsMessageFormatter.format(6023417, "HostToEWN", getEntryTimestamp()),
                            getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_REPETITION_DATA, "", getLineData(), this.getClass());
                    return false;
                }
                else
                {
                    // 他テーブルで使用していないかチェックを行なう
                    if (!checkRelation(inParam, sysController))
                    {
                        setSkipFlag(true);
                        return false;
                    }

                    // データ更新処理(削除処理)
                    update(inParam);
                    setRegistFlag(true);
                }
            }
            else
            {
                if (SystemDefine.MASTERDATA_LOAD_FLAG_NORMAL.equals(inParam.getLoadFlag()))
                {
                    // データ更新処理(登録処理)
                    update(inParam);
                    setRegistFlag(true);
                }
                else
                {
                    // 削除データで対象がなかった場合
                    setSkipFlag(true);
                    // 6020046=削除対象が存在しなかったため、スキップしました。ファイル:{0} 行:{1}
                    createErrorLog(WmsMessageFormatter.format(6023438, "HostToEWN", getEntryTimestamp(), "DATA",
                            getLineData()), getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_NO_DELETE_DATA, "", getLineData(), this.getClass());
                    return false;
                }
            }

            // 取込みエラーファイル作成クラスに正常データも追記する。
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
            // 項目内容異常の場合(スキップ)
            setSkipFlag(true);
            if (!isErrorFlag())
            {
                setErrorFlag(true);
                ValidateError[] errors = e.getValidateErrors();
                for (ValidateError error : errors)
                {
                    // 必須項目が無い場合の異常については全NGにしない
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
        catch (DataExistsException e)
        {
            // 6020043=他端末で処理中のため、スキップしました。ファイル:{0} 行:{1}
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
     * {@inheritDoc}
     */
    protected String getDataLoadKey()
    {
        return LOAD_KEY;
    }

    /**
     * ファイルのレコードをパラメータにセットします。
     * @param inEntity HostToEWN Entity
     * @param sysController WarenaviSystemControllerクラス
     * @return ファイルレコードをセットしたパラメータ
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws RecordFormatException ファイル上の指定された項目の値が不正だった場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     * @throws IllegalDataException 指定された値が不正だった場合に通知します（ファイルハンドラでのチェック以外の項目）。 
     * @throws NumberFormatException 指定された値が文字列が数字ではなかった場合に通知します。
     */
    protected MasterInParameter convertToParam(WarenaviSystemController sysController, HostToEWN inEntity)
            throws ReadWriteException,
                RecordFormatException,
                IOException,
                ValidateException,
                IllegalDataException
    {
        try
        {
            HostSupplier entity = new HostSupplier();
            try
            {
                HostIOUtil handler = new HostIOUtil();

                handler.open(entity, null);

                // ファイルハンドラより一行取得
                entity = (HostSupplier)handler.next(inEntity.getData());

                if (entity == null)
                {
                    // 取得できなかったらnullを返す
                    return null;
                }

                // 取り込んだ行のデータを保持
                setLineData(String.valueOf(entity.getValue(FileHandler.FIELD_ORIGINAL_RECORD)));
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

            // 項目数カウントをリセット
            setItemcount(0);

            MasterInParameter inParam = new MasterInParameter(new WmsUserInfo(getUserInfo()));

            // ファイル行No.
            inParam.setRowNo(getAllItemCount());
            // 取込区分
            inParam.setLoadFlag(getAsciiValue(entity.getLoadFlag().trim(), HostSupplier.LOAD_FLAG, true));
            // 荷主コード
            inParam.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            // 仕入先コード
            inParam.setSupplierCode(getAsciiValue(entity.getSupplierCode().trim(), HostSupplier.SUPPLIER_CODE, true));
            // 仕入先名称
            inParam.setSupplierName(getValue(StringUtil.rtrim(entity.getSupplierName()), HostSupplier.SUPPLIER_NAME));

            return inParam;

        }
        catch (RecordFormatException e)
        {
            // STATUS.txtには、空行を出力しない
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
     * 他テーブルで使用されていないかチェックします。<BR>
     * @param inParam MasterInParameterクラス
     * @param sysController WarenaviSystemControllerクラス
     * @return 実行結果（正常：true 異常：false）
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean checkRelation(MasterInParameter inParam, WarenaviSystemController sysController)
            throws ReadWriteException
    {
        // 入荷Package Add-On時
        if (sysController.hasReceivingPack())
        {
            // 入荷予定情報にて使用されているかチェック
            _receivePlanSearchKey.clear();
            _receivePlanSearchKey.setConsignorCode(inParam.getConsignorCode());
            _receivePlanSearchKey.setSupplierCode(inParam.getSupplierCode());
            _receivePlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existReceivePlan(_receivePlanSearchKey))
            {
                // 6023423=Unable to delete. The specified Supplier Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023423, "HostToEWN", getEntryTimestamp(),
                        HostSupplier.SUPPLIER_CODE, inParam.getSupplierCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }

        // クロスドックPackage Add-On時
        if (sysController.hasCrossdockPack())
        {
            // TC予定情報にて使用されているかチェック
            _xdPlanSearchKey.clear();
            _xdPlanSearchKey.setConsignorCode(inParam.getConsignorCode());
            _xdPlanSearchKey.setSupplierCode(inParam.getSupplierCode());
            _xdPlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existXDPlan(_xdPlanSearchKey))
            {
                // 6023423=Unable to delete. The specified Supplier Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023423, "HostToEWN", getEntryTimestamp(),
                        HostSupplier.SUPPLIER_CODE, inParam.getSupplierCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }

        // 入庫Package Add-On時
        if (sysController.hasStoragePack())
        {
            // 入庫予定情報にて使用されているかチェック
            _storagePlanSearchKey.clear();
            _storagePlanSearchKey.setConsignorCode(inParam.getConsignorCode());
            _storagePlanSearchKey.setSupplierCode(inParam.getSupplierCode());
            _storagePlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existStoragePlan(_storagePlanSearchKey))
            {
                // 6023423=Unable to delete. The specified Supplier Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023423, "HostToEWN", getEntryTimestamp(),
                        HostSupplier.SUPPLIER_CODE, inParam.getSupplierCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }

        // AS/RSPackage Add-On時
        if (sysController.hasAsrsPack())
        {
            // 入出庫作業情報にて使用されているかチェック
            _workinfoSearchKey.clear();
            _workinfoSearchKey.setConsignorCode(inParam.getConsignorCode());
            _workinfoSearchKey.setSupplierCode(inParam.getSupplierCode());
            _workinfoSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existWorkInfo(_workinfoSearchKey))
            {
                //                position = getPosition(handler, HostSupplier.SUPPLIER_CODE);
                // 6023423=Unable to delete. The specified Supplier Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023423, "HostToEWN", getEntryTimestamp(),
                        HostSupplier.SUPPLIER_CODE, inParam.getSupplierCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }
        return true;
    }

    /**
     * 取込情報にてマスタ情報の更新処理を行います。
     *
     * @param inParam 更新対象
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 対象データが見つからない場合にスローされます。
     * @throws NoPrimaryException 定義情報が異常な場合にスローされます。
     * @throws DataExistsException 対象データが重複した場合にスローされます。
     */
    protected void update(MasterInParameter inParam)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getLoadFlag()))
        {
            // 登録処理
            Supplier entity = new Supplier();

            // 荷主コード
            entity.setConsignorCode(inParam.getConsignorCode());
            // 仕入先コード
            entity.setSupplierCode(inParam.getSupplierCode());
            // 仕入先名称
            entity.setSupplierName(inParam.getSupplierName());
            // 登録処理名
            entity.setRegistPname(this.getClass().getSimpleName());
            // 最終更新処理名
            entity.setLastUpdatePname(this.getClass().getSimpleName());

            _supplierHandler.create(entity);

            SupplierController sCtl = new SupplierController(getConnection(), this.getClass());

            // 仕入先マスタ改廃履歴登録
            sCtl.insertHistory(entity, SystemDefine.UPDATE_KIND_REGIST, inParam.getWmsUserInfo().getDfkUserInfo());
        }
        else
        {
            // 削除処理
            _supplierSearchKey.clear();
            _supplierSearchKey.setConsignorCode(inParam.getConsignorCode());
            _supplierSearchKey.setSupplierCode(inParam.getSupplierCode());

            // 既存情報取得
            Supplier entity = (Supplier)_supplierHandler.findPrimary(_supplierSearchKey);

            _supplierHandler.drop(_supplierSearchKey);

            SupplierController sCtl = new SupplierController(getConnection(), this.getClass());

            // 仕入先マスタ改廃履歴登録
            sCtl.insertHistory(entity, SystemDefine.UPDATE_KIND_DELETE, inParam.getWmsUserInfo().getDfkUserInfo());
        }
    }

    /**
     * 初期化を行います。
     * @param sysController  WarenaviSystemControllerオブジェクト
     */
    protected void prepare(WarenaviSystemController sysController)
    {
        // 仕入先マスタ情報
        _supplierHandler = new SupplierHandler(getConnection());
        _supplierSearchKey = new SupplierSearchKey();

        // 入荷Package Add-On時
        if (sysController.hasReceivingPack())
        {
            // 入荷予定情報
            _receivePlanFinder = new ReceivingPlanFinder(getConnection());
            _receivePlanSearchKey = new ReceivingPlanSearchKey();
        }

        // クロスドックPackage Add-On時
        if (sysController.hasCrossdockPack())
        {
            // TC予定情報
            _xdPlanFinder = new CrossDockPlanFinder(getConnection());
            _xdPlanSearchKey = new CrossDockPlanSearchKey();
        }

        // 入庫Package Add-On時
        if (sysController.hasStoragePack())
        {
            // 入庫予定情報
            _storagePlanFinder = new StoragePlanFinder(getConnection());
            _storagePlanSearchKey = new StoragePlanSearchKey();
        }

        // AS/RSPackage Add-On時
        if (sysController.hasAsrsPack())
        {
            // 入出庫作業情報
            _workinfoFinder = new WorkInfoFinder(getConnection());
            _workinfoSearchKey = new WorkInfoSearchKey();
        }
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
        itemLog.add(MasterInParameter.DATA_TYPE_SUPPLIER_MASTER);
        // ファイル名称
        itemLog.add(getFileName());

        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(conn);

        // 常駐処理からの自動起動時、ユーザ情報がセットされないのでWareNavi固定値にて再編集を行います。
        DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();

        opeLogWriter.createOperationLog(userInfo, operationKind, itemLog);
    }

    /**
     * Check to see if a supplier is already recorded in the master.<BR>
     * If the master package is not installed, then unconditionally return true.
     * 
     * @param sKey The pre-configured search key.
     * @param sysController システムコントローラー
     * @return If the supplier code is found: true、otherwise: false
     * @throws CommonException Throw when a DB error occurs.
     */
    protected boolean existSupplierCode(SupplierSearchKey sKey, WarenaviSystemController sysController)
            throws CommonException
    {
        if (sysController.hasMasterPack())
        {
            SupplierFinder finder = null;
            try
            {
                finder = new SupplierFinder(this.getConnection());
                finder.open(true);

                int searchCount = finder.search(sKey);

                if (searchCount < 1)
                {
                    return false;
                }
            }
            finally
            {
                if (finder != null)
                {
                    finder.close();
                }
            }
        }
        return true;
    }

    /**
     * Check to see if this supplier code is being used in an existing receiving plan.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the supplier code is involved in a plan: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existReceivePlan(ReceivingPlanSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _receivePlanFinder.open(true);

            int searchCount = _receivePlanFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_receivePlanFinder != null)
            {
                _receivePlanFinder.close();
            }
        }
        return true;
    }

    /**
     * Check to see if this supplier code is being used in an existing XD plan.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the supplier code is involved in a plan: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existXDPlan(CrossDockPlanSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _xdPlanFinder.open(true);

            int searchCount = _xdPlanFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_xdPlanFinder != null)
            {
                _xdPlanFinder.close();
            }
        }
        return true;
    }

    /**
     * Check to see if this supplier code is being used in an existing storage plan.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the supplier code is involved in a plan: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existStoragePlan(StoragePlanSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _storagePlanFinder.open(true);

            int searchCount = _storagePlanFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_storagePlanFinder != null)
            {
                _storagePlanFinder.close();
            }
        }
        return true;
    }

    /**
     * Check to see if this supplier code is being used in existing work info.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the supplier code is involved in work info: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existWorkInfo(WorkInfoSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _workinfoFinder.open(true);

            int searchCount = _workinfoFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_workinfoFinder != null)
            {
                _workinfoFinder.close();
            }
        }
        return true;
    }

    /**
     * 取込データのファイル名を取得
     * @return String ファイル名を返します。
     * @exception ReadWriteException 例外が発生した場合に通知されます。
     */
    public String existFilesCount()
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
        return new HostSupplier();
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
        return "$Id: SupplierDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $";
    }
}
