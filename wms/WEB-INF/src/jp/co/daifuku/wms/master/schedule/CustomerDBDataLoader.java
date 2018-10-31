//$Id: CustomerDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $
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
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CustomerFinder;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.HostToEWN;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.IllegalDataException;
import jp.co.daifuku.wms.base.fileentity.HostCustomer;
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
 * <CODE>CustomerDataLoader</CODE>クラスは、出荷先マスタ取込み処理を行うクラスです。<BR>
 * <CODE>AbstractDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * @version $Revision: 7650 $, $Date: 2010-03-17 18:31:17 +0900 (水, 17 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class CustomerDBDataLoader
        extends AbstractDBDataLoader
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /** データ報告キー **/
    private static final String LOAD_KEY = "MASTER_CUSTOMER";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    // 出荷先マスタ情報
    private CustomerHandler _customerHandler = null;

    private CustomerSearchKey _customerSearchKey = null;

    // 出庫予定情報
    private RetrievalPlanFinder _retPlanFinder = null;

    private RetrievalPlanSearchKey _retPlanSearchKey = null;

    // 入出庫作業情報
    private WorkInfoFinder _workinfoFinder = null;

    private WorkInfoSearchKey _workinfoSearchKey = null;

    // 出荷予定情報
    private ShipPlanFinder _shipPlanFinder = null;

    private ShipPlanSearchKey _shipPlanSearchKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 自動処理より呼ばれるインスタンスです。
     */
    public CustomerDBDataLoader()
    {
        setDataType(SystemDefine.LOAD_DATA_TYPE_MASTER_CUSTOMER);
    }

    /**
     * データベースコネクションと呼び出し元クラスを指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public CustomerDBDataLoader(Connection conn, Connection errorInfoConn)
    {
        super(conn, errorInfoConn);
        setDataType(SystemDefine.LOAD_DATA_TYPE_MASTER_CUSTOMER);
    }

    /**
     * データベースコネクションと呼び出し元クラスとユーザ情報を指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param userInfo ユーザ情報
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public CustomerDBDataLoader(Connection conn, Connection errorInfoConn, DfkUserInfo userInfo)
    {
        super(conn, errorInfoConn, userInfo);
        setDataType(SystemDefine.LOAD_DATA_TYPE_MASTER_CUSTOMER);
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
        return WmsParam.MAX_RECORD_CUSTOMER;
    }

    /**
     * WareNaviSystemオブジェクトをパラメータとして受け取り、<BR>
     * 出荷先マスタデータを取込みます。<BR>
     * @param sysController  WarenaviSystemControllerオブジェクト
     * @param inEntity HostToEWN Entity
     * @param outParameter Params
     * @return 実行結果（正常：true 異常：false）
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean loadEntity(WarenaviSystemController sysController, HostToEWN inEntity, Params outParameter)
            throws CommonException
    {
        // インスタンスの生成
        prepare(sysController);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        setEntryTimestamp(df.format(inEntity.getMessageDate()));

        setLineData(inEntity.getEntireRecord());
        setSequenceno(inEntity.getSequenceNo());
        setFileName(existFiles() + outParameter.getString(REGIST_TIME) + MasterInParameter.EXTENSION);
        //setFileName("Customer"+outParameter.getRegistTime()+".txt");
        boolean isFileCopy = false;
        getErrlist().setFileName(getFileName());

        //        MasterInParameter inParam = null;

        //even though we're not reading a file, we need the handler to tell us the offsets of the fields we are reading
        HostCustomer entity = new HostCustomer();
        FileHandler handler = AbstractFileHandler.getInstance(entity);

        try
        {
            // 取込開始日時をセット
            setStartDate(DbDateUtil.getTimeStamp());

            try
            {
                // 取込件数を加算
                setAllItemCount(outParameter.getInt(ATTEMPTED_LOADLINES_COUNT));

                loadData(sysController, handler, inEntity);

            }
            catch (ValidateException e)
            {
                // 項目内容異常の場合(スキップ)
                setSkipFlag(true);
                // 6023438=Invalid format data found. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023438, "HostToEWN", getEntryTimestamp(), "DATA",
                        getLineData()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR, "", getLineData(), this.getClass());

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
            setErrorFlag(true);
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
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
     * 取込ファイルから一行ずつ取得し、DBへの取込処理を行ないます。
     * @param sysController  WarenaviSystemControllerオブジェクト
     * @param inEntity  HostToEWN Entity
     * @param handler FileHandler
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
                // ファイルの終端に達したら終了する
                return false;
            }
            // 取込件数が取込最大数を超えていたら取込処理を終了する
            int maxCount = getMaxRecord(ExchangeEnvironment.DATA_TYPE_MASTER_CUSTOMER);
            if (getAllItemCount() > maxCount && maxCount >= 0)
            {
                // 全NG
                setErrorFlag(true);
                // 6023041=レコード数が{0}件を超えたため、処理を中断しました。ファイル名:{1}
                setMessage(WmsMessageFormatter.format(6023041, WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_CUSTOMER),
                        "HostToEWN"));
                createErrorLog(WmsMessageFormatter.format(6023041,
                        WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_CUSTOMER), "HostToEWN"), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_OVER_LINES, "", "", this.getClass());
                return false;
            }

            // 出荷先マスタ検索処理
            _customerSearchKey.clear();
            _customerSearchKey.setConsignorCode(inParam.getConsignorCode());
            _customerSearchKey.setCustomerCode(inParam.getCustomerCode());

            // 対象データがDBに存在するかチェック
            if (existCustomerCode(_customerSearchKey, sysController)) //uses finder instead of handler
            {
                if (SystemDefine.MASTERDATA_LOAD_FLAG_NORMAL.equals(inParam.getLoadFlag()))
                {
                    // 通常データで対象がすでに存在した場合(二重取込)
                    setSkipFlag(true);
                    // 6023417=Skipped. Same data already exists. Table: {0} Timestamp: {1}
                    createErrorLog(WmsMessageFormatter.format(6023417, "HostToEWN", getEntryTimestamp()),
                            getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_REPETITION_DATA, "", getLineData(), this.getClass());
                }
                else if (SystemDefine.MASTERDATA_LOAD_FLAG_DELETE.equals(inParam.getLoadFlag()))
                {
                    // 他テーブルで使用していないかチェックを行なう
                    if (!checkRelation(inParam, handler, sysController))
                    {
                        setSkipFlag(true);
                    }
                    else
                    {
                        // データ更新処理(削除処理)
                        update(inParam);
                        setRegistFlag(true);
                    }
                }
                else
                //unrecognized char in Load Flag field.
                {
                    setErrorFlag(true);
                    setSkipFlag(true);
                    // 6023438=Invalid format data found. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                    createErrorLog(WmsMessageFormatter.format(6023438, "HostToEWN", getEntryTimestamp(), "DATA",
                            getLineData()), getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR, "", getLineData(), this.getClass());
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
                else if (SystemDefine.MASTERDATA_LOAD_FLAG_DELETE.equals(inParam.getLoadFlag()))
                {
                    // 削除データで対象がなかった場合
                    setSkipFlag(true);
                    // 6023422=Skipped. Target data not found. Table:{0} Timestamp:{1} Data:{2}
                    createErrorLog(WmsMessageFormatter.format(6023422, "HostToEWN", getEntryTimestamp()),
                            getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_NO_DELETE_DATA, "", getLineData(), this.getClass());
                }
                else
                //unrecognized char in Load Flag field.
                {
                    setErrorFlag(true);
                    setSkipFlag(true);
                    // 6023438=Invalid format data found. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                    createErrorLog(WmsMessageFormatter.format(6023438, "HostToEWN", getEntryTimestamp(), "DATA",
                            getLineData()), getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR, "", getLineData(), this.getClass());
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
            // 6023416=Skipped. Target data being processed.  Table:{0} Timestamp:{1}
            createErrorLog(WmsMessageFormatter.format(6023416, "HostToEWN", getEntryTimestamp()), getLineData(),
                    this.getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_CANCELLATION_DATA_LOCK, "", getLineData(), this.getClass());

            setSkipFlag(true);
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
            HostCustomer entity = new HostCustomer();
            try
            {
                // ファイルハンドラより一行取得
                HostIOUtil handler = new HostIOUtil();

                handler.open(entity, null);

                // ファイルハンドラより一行取得
                entity = (HostCustomer)handler.next(inEntity.getData());

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
            inParam.setLoadFlag(getAsciiValue(entity.getLoadFlag().trim(), HostCustomer.LOAD_FLAG, true));
            // 荷主コード
            inParam.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            // 出荷先コード
            inParam.setCustomerCode(getAsciiValue(entity.getCustomerCode().trim(), HostCustomer.CUSTOMER_CODE, true));
            // 出荷先名称
            inParam.setCustomerName(getValue(StringUtil.rtrim(entity.getCustomerName()), HostCustomer.CUSTOMER_NAME));
            // ルート
            inParam.setRoute(getValue(StringUtil.rtrim(entity.getRoute()), HostCustomer.ROUTE));
            // 郵便番号
            inParam.setPostalCode(getValue(StringUtil.rtrim(entity.getPostalCode()), HostCustomer.POSTAL_CODE));
            // 都道府県名
            inParam.setPrefecture(getValue(StringUtil.rtrim(entity.getPrefecture()), HostCustomer.PREFECTURE));
            // 住所1
            inParam.setAddress1(getValue(StringUtil.rtrim(entity.getAddress1()), HostCustomer.ADDRESS1));
            // 住所2
            inParam.setAddress2(getValue(StringUtil.rtrim(entity.getAddress2()), HostCustomer.ADDRESS2));
            // TEL
            inParam.setTelephone(getValue(StringUtil.rtrim(entity.getTelephone()), HostCustomer.TELEPHONE));
            // 連絡先1
            inParam.setContact1(getValue(StringUtil.rtrim(entity.getContact1()), HostCustomer.CONTACT1));
            // 連絡先2
            inParam.setContact2(getValue(StringUtil.rtrim(entity.getContact2()), HostCustomer.CONTACT2));
            // 仕分場所
            inParam.setSortingPlace(getValue(entity.getSortingPlace().trim(), HostCustomer.SORTING_PLACE));

            return inParam;

        }
        catch (RecordFormatException e)
        {
            // STATUS.txtには、空行を出力しない
            if (!StringUtil.isBlank(e.getRecordString()))
            {
                // 6026006=データフォーマットが不正なデータがありました。ファイル:{0} 行:{1}
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
     * @param handler FileHandlerクラス
     * @param sysController WarenaviSystemControllerクラス
     * @return 実行結果（正常：true 異常：false）
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean checkRelation(MasterInParameter inParam, FileHandler handler,
            WarenaviSystemController sysController)
            throws ReadWriteException
    {
        // 出庫Package Add-On時
        if (sysController.hasRetrievalPack())
        {
            // 出庫予定情報にて使用されているかチェック
            _retPlanSearchKey.clear();
            _retPlanSearchKey.setConsignorCode(inParam.getConsignorCode());
            _retPlanSearchKey.setCustomerCode(inParam.getCustomerCode());
            _retPlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existRetrievePlan(_retPlanSearchKey))
            {
                //position = getPosition(handler, HostCustomer.CUSTOMER_CODE);
                // 6023427=Unable to delete. The specified Customer Code in use. Table:{0} Line:{1} Timestamp: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023427, "HostToEWN", getEntryTimestamp(),
                        HostCustomer.CUSTOMER_CODE, inParam.getCustomerCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription(),
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
            _workinfoSearchKey.setCustomerCode(inParam.getCustomerCode());
            _workinfoSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existWorkInfo(_workinfoSearchKey))
            {
                //position = getPosition(handler, HostCustomer.CUSTOMER_CODE);
                // 6023427=Unable to delete. The specified Customer Code in use. Table:{0} Line:{1} Timestamp: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023427, "HostToEWN", getEntryTimestamp(),
                        HostCustomer.CUSTOMER_CODE, inParam.getCustomerCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }

        // クロスドックPackage, 出荷Package Add-On時
        if (sysController.hasCrossdockPack() || sysController.hasShippingPack())
        {
            // クロスドックパッケージ導入時、
            // CrossDockPlanがShipPlanよりも先に削除される可能性があるため
            // CrossDockPlanではなくShipPlanでチェックする
            // 出荷予定情報にて使用されているかチェック
            _shipPlanSearchKey.clear();
            _shipPlanSearchKey.setConsignorCode(inParam.getConsignorCode());
            _shipPlanSearchKey.setCustomerCode(inParam.getCustomerCode());
            _shipPlanSearchKey.setWorkStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
            _shipPlanSearchKey.setBerthStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "", ")", true);
            if (existShipPlan(_shipPlanSearchKey))
            {
                //position = getPosition(handler, HostCustomer.CUSTOMER_CODE);
                // 6023427=Unable to delete. The specified Customer Code in use. Table:{0} Line:{1} Timestamp: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023427, "HostToEWN", getEntryTimestamp(),
                        HostCustomer.CUSTOMER_CODE, inParam.getCustomerCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription(),
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
     * @throws NotFoundException すでにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 定義情報が異常な場合にスローされます。
     * @throws DataExistsException 重複するデータが存在する場合にスローされます。
     */
    protected void update(MasterInParameter inParam)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        if (SystemDefine.MASTERDATA_LOAD_FLAG_NORMAL.equals(inParam.getLoadFlag()))
        {
            // 登録処理
            Customer entity = new Customer();

            // 荷主コード
            entity.setConsignorCode(inParam.getConsignorCode());
            // 出荷先コード
            entity.setCustomerCode(inParam.getCustomerCode());
            // 出荷先名称
            entity.setCustomerName(inParam.getCustomerName());
            // ルート
            entity.setRoute(inParam.getRoute());
            // 郵便番号
            entity.setPostalCode(inParam.getPostalCode());
            // 都道府県名
            entity.setPrefecture(inParam.getPrefecture());
            // 住所1
            entity.setAddress1(inParam.getAddress1());
            // 住所2
            entity.setAddress2(inParam.getAddress2());
            // TEL
            entity.setTelephone(inParam.getTelephone());
            // 連絡先1
            entity.setContact1(inParam.getContact1());
            // 連絡先2
            entity.setContact2(inParam.getContact2());
            // 仕分場所
            entity.setSortingPlace(inParam.getSortingPlace());
            // 登録処理名
            entity.setRegistPname(this.getClass().getSimpleName());
            // 最終更新処理名
            entity.setLastUpdatePname(this.getClass().getSimpleName());

            _customerHandler.create(entity);

            CustomerController sCtl = new CustomerController(getConnection(), this.getClass());

            // 出荷先マスタ改廃履歴登録
            sCtl.insertHistory(entity, SystemDefine.UPDATE_KIND_REGIST, this.getUserInfo());
        }
        else
        {
            // 削除処理
            _customerSearchKey.clear();
            _customerSearchKey.setConsignorCode(inParam.getConsignorCode());
            _customerSearchKey.setCustomerCode(inParam.getCustomerCode());

            // 既存情報取得
            Customer entity = (Customer)_customerHandler.findPrimary(_customerSearchKey);

            _customerHandler.drop(_customerSearchKey);

            CustomerController sCtl = new CustomerController(getConnection(), this.getClass());

            // 出荷先マスタ改廃履歴登録
            sCtl.insertHistory(entity, SystemDefine.UPDATE_KIND_DELETE, this.getUserInfo());
        }
    }

    /**
     * 初期化を行います。
     * @param sysController  WarenaviSystemControllerオブジェクト
     */
    protected void prepare(WarenaviSystemController sysController)
    {
        // 出荷先マスタ情報
        _customerHandler = new CustomerHandler(getConnection());
        _customerSearchKey = new CustomerSearchKey();

        // 出庫Package Add-On時
        if (sysController.hasRetrievalPack())
        {
            // 出庫予定情報
            _retPlanFinder = new RetrievalPlanFinder(getConnection());
            _retPlanSearchKey = new RetrievalPlanSearchKey();
        }

        // AS/RSPackage Add-On時
        if (sysController.hasAsrsPack())
        {
            // 入出庫作業情報
            _workinfoFinder = new WorkInfoFinder(getConnection());
            _workinfoSearchKey = new WorkInfoSearchKey();
        }

        // クロスドックPackage, 出荷Package Add-On時
        if (sysController.hasShippingPack() || sysController.hasCrossdockPack())
        {
            // 出荷予定情報
            _shipPlanFinder = new ShipPlanFinder(getConnection());
            _shipPlanSearchKey = new ShipPlanSearchKey();
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
        itemLog.add(MasterInParameter.DATA_TYPE_CUSTOMER_MASTER);
        // ファイル名称
        itemLog.add(getFileName());

        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(conn);

        // 常駐処理からの自動起動時、ユーザ情報がセットされないのでWareNavi固定値にて再編集を行います。
        DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();

        opeLogWriter.createOperationLog(userInfo, operationKind, itemLog);
    }

    /**
     * Check to see if a customer is already recorded in the master.<BR>
     * If the master package is not installed, then unconditionally return true.
     * 
     * @param sKey The pre-configured search key.
     * @param sysController WarenaviSystemController
     * @return If the customer code is found: true、otherwise: false
     * @throws CommonException Throw when a DB error occurs.
     */
    protected boolean existCustomerCode(CustomerSearchKey sKey, WarenaviSystemController sysController)
            throws CommonException
    {
        if (sysController.hasMasterPack())
        {
            CustomerFinder finder = null;
            try
            {
                finder = new CustomerFinder(this.getConnection());
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
     * Check to see if this item code is being used with a retrieval plan.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in a retrieval plan: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existRetrievePlan(RetrievalPlanSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _retPlanFinder.open(true);

            int searchCount = _retPlanFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_retPlanFinder != null)
            {
                _retPlanFinder.close();
            }
        }
        return true;
    }

    /**
     * Check to see if this item code is being used with work info.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in work info: true、otherwise: false
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
     * Check to see if this item code is being used with a shipping plan.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in a shipping plan: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existShipPlan(ShipPlanSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _shipPlanFinder.open(true);

            int searchCount = _shipPlanFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_shipPlanFinder != null)
            {
                _shipPlanFinder.close();
            }
        }
        return true;
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
        return new HostCustomer();
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
        return "$Id: CustomerDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $";
    }
}
