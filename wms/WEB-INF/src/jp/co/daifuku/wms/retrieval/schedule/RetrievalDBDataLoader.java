//$Id: RetrievalDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $
package jp.co.daifuku.wms.retrieval.schedule;

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
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaFinder;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ConsignorFinder;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerFinder;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.HostToEWN;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.IllegalDataException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.fileentity.Retrieval;
import jp.co.daifuku.wms.base.util.DataLoadLogFileWriter;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.HostIOUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.RecordFormatException;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;
import jp.co.daifuku.wms.retrieval.operator.RetrievalPlanOperator;

/**
 * <CODE>RetrievalDBDataLoader</CODE>クラスは、出庫データ取込み処理を行うクラスです。<BR>
 * <CODE>AbstractDBDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * Designer : Gary Muhlestein <BR>
 * Maker    : Gary Muhlestein <BR>
 * <BR>
 * @version $Revision: 7650 $, $Date: 2010-03-17 18:31:17 +0900 (水, 17 3 2010) $
 * @author  wms
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalDBDataLoader
        extends AbstractDBDataLoader
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /** データ取込キー **/
    private static final String LOAD_KEY = "RETRIEVAL_SUPPORT";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    // 荷主マスタ情報
    private ConsignorHandler _consignorHandler = null;

    private ConsignorFinder _consignorFinder = null;

    private ConsignorSearchKey _consignorSearchKey = null;

    private Consignor[] _consignorEntity = null;

    // 出荷先マスタ情報
    private CustomerHandler _customerHandler = null;

    private CustomerFinder _customerFinder = null;

    private CustomerSearchKey _customerSearchKey = null;

    private Customer[] _customerEntity = null;

    // 商品マスタ情報
    private ItemHandler _itemHandler = null;

    private ItemFinder _itemFinder = null;

    private ItemSearchKey _itemSearchKey = null;

    private Item[] _itemEntity = null;

    // エリアマスタ情報
    private AreaFinder _areaFinder = null;

    private AreaSearchKey _areaSearchKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 自動処理より呼ばれるインスタンスです。
     */
    public RetrievalDBDataLoader()
    {
        setUserInfo(getAutoUserInfo());
        setDataType(RetrievalInParameter.DATA_TYPE_RETRIEVAL);
    }

    /**
     * データベースコネクションと呼び出し元クラスを指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public RetrievalDBDataLoader(Connection conn, Connection errorInfoConn)
    {
        super(conn, errorInfoConn);
        setDataType(RetrievalInParameter.DATA_TYPE_RETRIEVAL);
    }

    /**
     * データベースコネクションと呼び出し元クラスとユーザ情報を指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param userInfo ユーザ情報
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public RetrievalDBDataLoader(Connection conn, Connection errorInfoConn, DfkUserInfo userInfo)
    {
        super(conn, errorInfoConn, userInfo);
        setDataType(RetrievalInParameter.DATA_TYPE_RETRIEVAL);
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
        return WmsParam.MAX_RECORD_RETRIEVAL;
    }

    /**
     * WarenaviSystemControllerオブジェクトをパラメータとして受け取り、出庫予定データを取込みます。<BR>
     * @param sysController  WarenaviSystemControllerオブジェクト
     * @param inEntity HostToEWNエンティティ
     * @param outParameter パラメータ
     * @return 実行結果（正常：true 異常：false）
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean loadEntity(WarenaviSystemController sysController, HostToEWN inEntity, Params outParameter)
            throws CommonException
    {
        // インスタンスの生成
        prepare();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        setEntryTimestamp(df.format(inEntity.getMessageDate()));

        setLineData(inEntity.getEntireRecord());
        setSequenceno(inEntity.getSequenceNo());
        setFileName(existFilesCount() + outParameter.getString(REGIST_TIME) + RetrievalInParameter.EXTENSION);

        boolean isFileCopy = false;
        getErrlist().setFileName(getFileName());

        //even though we're not reading a file, we need the handler to tell us the offsets of the fields we are reading
        Retrieval entity = new Retrieval();

        FileHandler handler = AbstractFileHandler.getInstance(entity);
        try
        {
            // 取込開始日時をセット
            setStartDate(DbDateUtil.getTimeStamp());

            try
            {
                setAllItemCount(outParameter.getInt(ATTEMPTED_LOADLINES_COUNT));

                // データ取込処理
                loadData(sysController, handler, inEntity);
            }
            catch (ValidateException e)
            {
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
                // 6023419=Skipped. Target data being processed by another terminal.  Table:{0} Timestamp:{1}
                createErrorLog(WmsMessageFormatter.format(6023419, "HostToEWN", getEntryTimestamp()), getLineData(),
                        this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_CANCELLATION_DATA_LOCK, "", getLineData(), this.getClass());
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
     * @param handler ファイルハンドラ
     * @param inEntity HostToEWN エンティティ
     * @return 実行結果（正常：true 異常：false）
     * @throws CommonException 例外が発生した場合に通知されます。
     * @throws IOException ファイル入出力エラーが発生した場合に通知されます。
     * @throws SQLException SQLでエラーが発生した場合に通知されます。
     */
    protected boolean loadData(WarenaviSystemController sysController, FileHandler handler, HostToEWN inEntity)
            throws CommonException,
                IOException,
                SQLException
    {
        RetrievalPlanOperator planOperator = new RetrievalPlanOperator(getConnection(), this.getClass());
        RetrievalInParameter inParam = null;

        WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), this.getClass());

        // 取込データ件数
        int dataCount = 0;

        // 取込データがなくなるまでループ

        try
        {
            // 取込件数を加算
            dataCount++;
            setAllItemCount(dataCount);
            // 取込ファイルから一行取得
            inParam = convertEntityToParam(inEntity, sysController);
            if (null == inParam)
            {
                // ファイルの終端に達したら終了する
                return false;
            }
            // 取込件数が取込最大数を超えていたら取込処理を終了する
            // If the loading count exceeds the upper limit then terminate the loading process
            int maxCount = getMaxRecord(ExchangeEnvironment.DATA_TYPE_RETRIEVAL);
            if (getAllItemCount() > maxCount && maxCount >= 0)
            {
                // 全NG
                setErrorFlag(true);
                // 6023041=レコード数が{0}件を超えたため、処理を中断しました。ファイル名:{1}
                setMessage(WmsMessageFormatter.format(6023041,
                        WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_RETRIEVAL), "HostToEWN"));
                createErrorLog(WmsMessageFormatter.format(6023041,
                        WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_RETRIEVAL), "HostToEWN"), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_OVER_LINES, "", "", this.getClass());
                return false;
            }
            // 値のチェックを行う、不正だった場合一行をスキップする
            if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getCancelFlag())
                    && !check(inParam, handler, sysController))
            {
                setSkipFlag(true);
                return false;
            }
            RetrievalPlan[] entitys = (RetrievalPlan[])planOperator.findPlan(inParam);
            // 対象データがDBに存在するかチェック
            if (entitys != null && entitys.length > 0)
            {
                if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getCancelFlag()))
                {
                    // 通常データで対象がすでに存在した場合(スキップ)
                    setSkipFlag(true);
                    // 6023417=Skipped. Same data already exists. Table: {0} Timestamp: {1}
                    createErrorLog(WmsMessageFormatter.format(6023417, "HostToEWN", getEntryTimestamp()),
                            getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_REPETITION_DATA, "", getLineData(), this.getClass());
                    return false;
                }
                else
                {
                    // 検索した作業が全て未開始か削除であるかチェック
                    boolean isStart = false;
                    for (RetrievalPlan planEntity : entitys)
                    {
                        if (!SystemDefine.STATUS_FLAG_UNSTART.equals(planEntity.getStatusFlag())
                                && !SystemDefine.STATUS_FLAG_DELETE.equals(planEntity.getStatusFlag()))
                        {
                            isStart = true;
                        }
                        // 在庫パッケージありの場合は引当て済みも取り込まない
                        if (wmsControl.hasStockPack() && SystemDefine.SCH_FLAG_SCHEDULE.equals(planEntity.getSchFlag()))
                        {
                            isStart = true;
                        }
                    }
                    if (isStart)
                    {
                        // 取消データで引当済みもしくは未開始か削除以外のデータがあった場合
                        setSkipFlag(true);
                        // 6023416=Skipped. Target data being processed.  Table:{0} Timestamp:{1}
                        createErrorLog(WmsMessageFormatter.format(6023416, "HostToEWN", getEntryTimestamp()),
                                getLineData(), this.getClass());
                        createLoadErrorInfo(SystemDefine.ERROR_FLAG_CANCELLATION_DATA_STARTED, "", getLineData(),
                                this.getClass());
                        return false;
                    }
                    if (!isErrorFlag())
                    {
                        // 取消対象予定･作業データをロック
                        if (!lockRetrievalPlan(inParam) || !lockWorkInfo(inParam))
                        {
                            // 取消データで対象がなかった場合
                            setSkipFlag(true);

                            // 6023418=Skipped. There is no target data to delete. Table:{0} Timestamp:{1}
                            createErrorLog(WmsMessageFormatter.format(6023418, "HostToEWN", getEntryTimestamp()),
                                    getLineData(), this.getClass());
                            createLoadErrorInfo(SystemDefine.ERROR_FLAG_NO_CANCELLATION_DATA, "", getLineData(),
                                    this.getClass());
                            return false;
                        }
                        setRegistFlag(true);
                        // 取消データに更新
                        planOperator.deletePlan(SystemDefine.REGIST_KIND_DATALOADER, inParam);
                    }
                }
            }
            else
            {
                if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getCancelFlag()))
                {
                    // １オーダー内に複数の予定日が無いかチェック
                    if (!planOperator.isOrderInPlanDay(inParam))
                    {
                        setSkipFlag(true);
                        // 6020044=同一オーダー内に複数の予定日が存在するため、スキップしました。ファイル:{0} 行:{1}
                        // 6023420=Skipped. Multiple Plan Date exist for an order. Table:{0} Timestamp:{1}
                        createErrorLog(WmsMessageFormatter.format(6023420, "HostToEWN", getEntryTimestamp()),
                                getLineData(), this.getClass());
                        createLoadErrorInfo(
                                SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                                getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_DAY.getName()).getDescription(),
                                getLineData(), this.getClass());
                        return false;
                    }
                    // １オーダー内に複数の出荷先コードが無いかチェック
                    if (!planOperator.isOrderInCustomer(inParam))
                    {
                        setSkipFlag(true);
                        // 6023421=Skipped. Multiple Customer Code exist for an order. Table:{0} Timestamp:{1}
                        createErrorLog(WmsMessageFormatter.format(6023421, "HostToEWN", getEntryTimestamp()),
                                getLineData(), this.getClass());
                        createLoadErrorInfo(
                                SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                                getEntity().getStoreMetaData().getFieldMetaData(Retrieval.CUSTOMER_CODE.getName()).getDescription(),
                                getLineData(), this.getClass());
                        return false;
                    }
                    if (!isErrorFlag())
                    {
                        setRegistFlag(true);
                        // 出庫情報を登録
                        planOperator.createPlan(SystemDefine.REGIST_KIND_DATALOADER, inParam);
                    }
                }
                else
                {
                    // 取消データで対象がなかった場合
                    setSkipFlag(true);
                    // 6020042=取消対象が存在しなかったため、スキップしました。ファイル:{0} 行:{1}
                    createErrorLog(WmsMessageFormatter.format(6020042, getFileName(),
                            WmsFormatter.getNumFormat(getAllItemCount())), getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_NO_CANCELLATION_DATA, "", getLineData(),
                            this.getClass());
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
        catch (LockTimeOutException e)
        {
            setSkipFlag(true);
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
     * @param inEntity HostToEWNエンティティ
     * @param sysController WarenaviSystemControllerクラス
     * @return ファイルレコードをセットしたパラメータ
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws RecordFormatException ファイル上の指定された項目の値が不正だった場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     * @throws IllegalDataException 指定された値が不正だった場合に通知します（ファイルハンドラでのチェック以外の項目）。 
     * @throws NumberFormatException 指定された値が文字列が数字ではなかった場合に通知します。
     */
    protected RetrievalInParameter convertEntityToParam(HostToEWN inEntity, WarenaviSystemController sysController)
            throws ReadWriteException,
                RecordFormatException,
                IOException,
                ValidateException,
                IllegalDataException,
                NumberFormatException
    {

        try
        {
            // 項目数カウントをリセット
            setItemcount(0);

            Retrieval entity = new Retrieval();

            HostIOUtil handler = new HostIOUtil();

            handler.open(entity, null);

            try
            {
                // ファイルハンドラより一行取得
                entity = (Retrieval)handler.next(inEntity.getData());

                if (entity == null)
                {
                    // 取得できなかったらnullを返す
                    return null;
                }

                // 取り込んだ行のデータを保持
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

            // 項目数カウントをリセット
            setItemcount(0);

            RetrievalInParameter inParam = new RetrievalInParameter(null);

            // 取込単位キー(拡張子を除いたファイル名)
            String filePrefix =
                    getFileName().substring(0, (getFileName().length() - RetrievalInParameter.EXTENSION.length()));
            inParam.setLoadUnitKey(filePrefix);
            // ファイル行No.
            inParam.setRowNo(getAllItemCount());
            // 取消区分
            inParam.setCancelFlag(getValue(entity.getCancelFlag().trim(), Retrieval.CANCEL_FLAG, true));
            // 予定日
            inParam.setRetrievalPlanDay(getDateValue(entity.getPlanDay().trim(), Retrieval.PLAN_DAY, true));
            // 荷主コード
            inParam.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            // 出荷先コード
            inParam.setCustomerCode(getAsciiValue(entity.getCustomerCode().trim(), Retrieval.CUSTOMER_CODE, false));
            // 出荷先名称
            inParam.setCustomerName(getValue(StringUtil.rtrim(entity.getCustomerName()), Retrieval.CUSTOMER_NAME));
            // 出荷伝票No.
            inParam.setTicketNo(getAsciiValue(entity.getShipTicketNo().trim(), Retrieval.SHIP_TICKET_NO, true));
            // 出荷伝票行No.
            inParam.setLineNo(getIntValue(entity.getShipLineNo(), Retrieval.SHIP_LINE_NO));
            // 作業枝番
            inParam.setBranchNo(getIntValue(entity.getBranchNo(), Retrieval.BRANCH_NO));
            // バッチNo.
            inParam.setBatchNo(getAsciiValue(entity.getBatchNo().trim(), Retrieval.BATCH_NO, true));
            // オーダーNo.
            inParam.setOrderNo(getAsciiValue(entity.getOrderNo().trim(), Retrieval.ORDER_NO, true));
            // 商品コード
            inParam.setItemCode(getAsciiValue(entity.getItemCode().trim(), Retrieval.ITEM_CODE, true));
            // 商品名称
            inParam.setItemName(getValue(StringUtil.rtrim(entity.getItemName()), Retrieval.ITEM_NAME));
            // JANコード
            inParam.setJanCode(getAsciiValue(entity.getJan().trim(), Retrieval.JAN));
            // ケースITF
            inParam.setItf(getAsciiValue(entity.getItf().trim(), Retrieval.ITF));
            // ケース入数
            inParam.setEnteringQty(getIntValue(entity.getEnteringQty(), Retrieval.ENTERING_QTY));
            // ロットNo.
            inParam.setLotNo(getAsciiValue(entity.getLotNo().trim(), Retrieval.LOT_NO));
            // 予定数
            inParam.setPlanQty(getIntValue(entity.getPlanQty(), Retrieval.PLAN_QTY));
            // エリアNo.
            inParam.setRetrievalAreaNo(getAsciiValue(entity.getPlanAreaNo().trim(), Retrieval.PLAN_AREA_NO));
            // 棚No.
            inParam.setRetrievalLocation(getAsciiValue(entity.getPlanLocationNo().trim(), Retrieval.PLAN_LOCATION_NO));

            // 通常データでマスタ管理あり時はマスタより項目の補完を行なう
            if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getCancelFlag()) && sysController.hasMasterPack())
            {
                // 荷主マスタ情報より名称を取得
                if (!StringUtil.isBlank(inParam.getConsignorCode()))
                {
                    // 検索条件のセット
                    _consignorSearchKey.clear();
                    _consignorSearchKey.setConsignorCode(inParam.getConsignorCode());

                    // 荷主マスタ情報の検索
                    _consignorEntity = (Consignor[])_consignorHandler.find(_consignorSearchKey);

                    if (_consignorEntity.length >= 1 && !StringUtil.isBlank(_consignorEntity[0].getConsignorName()))
                    {
                        // 荷主マスタ情報より取得した名称をセット
                        inParam.setConsignorName(_consignorEntity[0].getConsignorName());
                    }
                }

                // 出荷先マスタ情報より項目を取得
                if (!StringUtil.isBlank(inParam.getCustomerCode()))
                {
                    // 検索条件のセット
                    _customerSearchKey.clear();
                    _customerSearchKey.setConsignorCode(inParam.getConsignorCode());
                    _customerSearchKey.setCustomerCode(inParam.getCustomerCode());

                    // 出荷先マスタ情報の検索
                    _customerEntity = (Customer[])_customerHandler.find(_customerSearchKey);

                    if (_customerEntity.length >= 1 && !StringUtil.isBlank(_customerEntity[0].getCustomerName()))
                    {
                        // 出荷先マスタ情報より取得した名称をセット
                        inParam.setCustomerName(_customerEntity[0].getCustomerName());
                    }
                }

                // 商品マスタ情報より項目を取得
                if (!StringUtil.isBlank(inParam.getItemCode()))
                {
                    // 検索条件のセット
                    _itemSearchKey.clear();
                    _itemSearchKey.setConsignorCode(inParam.getConsignorCode());
                    _itemSearchKey.setItemCode(inParam.getItemCode());

                    // 商品マスタ情報の検索
                    _itemEntity = (Item[])_itemHandler.find(_itemSearchKey);

                    if (_itemEntity.length >= 1)
                    {
                        // 商品名称
                        if (!StringUtil.isBlank(_itemEntity[0].getItemName()))
                        {
                            inParam.setItemName(_itemEntity[0].getItemName());
                        }

                        // JANコード
                        if (!StringUtil.isBlank(_itemEntity[0].getJan()))
                        {
                            inParam.setJanCode(_itemEntity[0].getJan());
                        }

                        // ケースITF
                        if (!StringUtil.isBlank(_itemEntity[0].getItf()))
                        {
                            inParam.setItf((_itemEntity[0].getItf()));
                        }

                        // ケース入数
                        inParam.setEnteringQty(_itemEntity[0].getEnteringQty());
                    }
                }
            }
            return inParam;
        }
        catch (RecordFormatException e)
        {
            // STATUS.txtには、空行を出力しない
            if (!StringUtil.isBlank(e.getRecordString()))
            {
                // 6026006=データフォーマットが不正なデータがありました。ファイル:{0} 行:{1}
                createErrorLog(WmsMessageFormatter.format(6026006, getFileName(),
                        WmsFormatter.getNumFormat(getAllItemCount())), cutCRLF(e.getRecordString()), this.getClass());
            }
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_ITEM_NUMBER_ERROR, "", cutCRLF(e.getRecordString()),
                    this.getClass());
            throw e;
        }
    }

    /**
     * パラメータの値をチェックします。<BR>
     * このメソッドでNGだった場合は一行をスキップします。<BR>
     * @param inParam RetrievalInParameterクラス
     * @param handler FileHandlerクラス
     * @param sysController WarenaviSystemControllerクラス
     * @return 実行結果（正常：true 異常：false）
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     */
    protected boolean check(RetrievalInParameter inParam, FileHandler handler, WarenaviSystemController sysController)
            throws ReadWriteException,
                IOException,
                ValidateException
    {
        // 通常データだった場合のみチェックを行う
        if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(inParam.getCancelFlag()))
        {
            return true;
        }

        // マスタ情報のチェック
        if (sysController.hasMasterPack())
        {
            boolean ret = true;
            // 荷主コードが荷主マスタ情報に登録されていたかチェック
            if (_consignorEntity.length <= 0)
            {
                // 6023428=Consignor Code does not exist in Consignor Master. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023428, "HostToEWN", getEntryTimestamp(), "CONSIGNOR_CODE",
                        inParam.getConsignorCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_MASTER_UNREGIST, "荷主コード", getLineData(), this.getClass());
                ret = false;
            }

            // 出荷先コードが出荷先マスタ情報に登録されていたかチェック
            if (_customerEntity != null)
            {
                if (_customerEntity.length <= 0)
                {
                    // 6023434=Customer Code does not exist in Customer Master. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                    createErrorLog(WmsMessageFormatter.format(6023434, "HostToEWN", getEntryTimestamp(),
                            Retrieval.CUSTOMER_CODE, inParam.getCustomerCode()), getLineData(), this.getClass());
                    createLoadErrorInfo(
                            SystemDefine.ERROR_FLAG_MASTER_UNREGIST,
                            getEntity().getStoreMetaData().getFieldMetaData(Retrieval.CUSTOMER_CODE.getName()).getDescription(),
                            getLineData(), this.getClass());
                    ret = false;
                }
            }

            // 商品コードが商品マスタ情報に登録されていたかチェック
            if (_itemEntity.length <= 0)
            {
                // 6023430=Item Code does not exist in Item Master. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023430, "HostToEWN", getEntryTimestamp(),
                        Retrieval.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_MASTER_UNREGIST,
                        getEntity().getStoreMetaData().getFieldMetaData(Retrieval.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                ret = false;
            }
            // 異常棚商品コード入力チェック・簡易直行用商品コード入力チェック
            else if (WmsParam.IRREGULAR_ITEMCODE.equals(inParam.getItemCode())
                    || WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(inParam.getItemCode()))
            {
                // 6023431=Unable to add. The specified Item Code is already used by the system. Table: {0} Timestamp: {1}
                createErrorLog(WmsMessageFormatter.format(6023431, "HostToEWN", getEntryTimestamp()), getLineData(),
                        this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                        getEntity().getStoreMetaData().getFieldMetaData(Retrieval.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                ret = false;
            }

            if (!ret)
            {
                return ret;
            }
        }

        // 在庫管理ごとの、エリアと棚の入力チェック
        if (sysController.hasStockPack())
        {
            if (!StringUtil.isBlank(inParam.getRetrievalAreaNo()))
            {
                // 6023435=Unable to specify when Inventory package is installed. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023435, "HostToEWN", getEntryTimestamp(),
                        Retrieval.PLAN_AREA_NO, inParam.getRetrievalAreaNo()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_INPUT_PROHIBITION_ITEM,
                        getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_AREA_NO.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;

            }
            else if (!StringUtil.isBlank(inParam.getRetrievalLocation()))
            {
                // 6023435=Unable to specify when Inventory package is installed. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023435, "HostToEWN", getEntryTimestamp(),
                        Retrieval.PLAN_LOCATION_NO, inParam.getRetrievalLocation()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_INPUT_PROHIBITION_ITEM,
                        getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_LOCATION_NO.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }
        else
        {
            if (StringUtil.isBlank(inParam.getRetrievalAreaNo()))
            {
                // 6023436=Data with missing mandatory fields. Table:{0} Timestamp:{1} Item:{2}
                createErrorLog(WmsMessageFormatter.format(6023436, "HostToEWN", getEntryTimestamp(),
                        Retrieval.PLAN_AREA_NO), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_INDISPENSABLE_ITEM_BLANK,
                        getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_AREA_NO.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
            else if (StringUtil.isBlank(inParam.getRetrievalLocation()))
            {
                // 6023436=Data with missing mandatory fields. Table:{0} Timestamp:{1} Item:{2}
                createErrorLog(WmsMessageFormatter.format(6023436, "HostToEWN", getEntryTimestamp(),
                        Retrieval.PLAN_LOCATION_NO), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_INDISPENSABLE_ITEM_BLANK,
                        getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_LOCATION_NO.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }

        // エリアがエリアマスタ情報に登録されていたかチェック
        if (!StringUtil.isBlank(inParam.getRetrievalAreaNo()))
        {
            // 検索条件のセット
            _areaSearchKey.clear();
            _areaSearchKey.setAreaNo(inParam.getRetrievalAreaNo());

            if (!existArea(_areaSearchKey)) //uses finder instead of handler
            {
                // 6023437=Area does not exist in Area Master. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023437, "HostToEWN", getEntryTimestamp(),
                        Retrieval.PLAN_AREA_NO, inParam.getRetrievalAreaNo()), getLineData(), this.getClass());
                createLoadErrorInfo(
                        SystemDefine.ERROR_FLAG_MASTER_UNREGIST,
                        getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_AREA_NO.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }

            // 棚が入力されていた場合、棚の形式が正しいかチェック
            if (!StringUtil.isBlank(inParam.getRetrievalLocation()))
            {
                float length = 0;
                FieldMetaData[] fieldMetas = handler.getStoreMetaData().getFieldMetaDatas();
                for (FieldMetaData fieldMeta : fieldMetas)
                {
                    if (fieldMeta.getName().equals(Retrieval.PLAN_LOCATION_NO.getName()))
                    {
                        length = fieldMeta.getLength();
                    }
                }
                if ((int)length < inParam.getRetrievalLocation().length())
                {
                    // 6023438=Invalid format data found. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                    createErrorLog(WmsMessageFormatter.format(6023438, "HostToEWN", getEntryTimestamp(),
                            Retrieval.PLAN_LOCATION_NO, inParam.getRetrievalLocation()), getLineData(), this.getClass());
                    createLoadErrorInfo(
                            SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                            getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_LOCATION_NO.getName()).getDescription(),
                            getLineData(), this.getClass());
                    return false;
                }
                else
                {
                    // エリアコントローラ
                    AreaController areaContorl = new AreaController(getConnection(), getClass());
                    try
                    {
                        // 棚No.が、指定されたエリアの棚形式と合っているかチェック
                        areaContorl.checkLocateFormat(inParam.getRetrievalAreaNo(), inParam.getRetrievalLocation());
                    }
                    catch (OperatorException e)
                    {
                        // 6023439=Found illegal format data. Table:{0} TImestamp:{1} Item:{2} Value:{3}
                        createErrorLog(WmsMessageFormatter.format(6023439, "HostToEWN", getEntryTimestamp(),
                                Retrieval.PLAN_LOCATION_NO, inParam.getRetrievalLocation()), getLineData(),
                                this.getClass());
                        createLoadErrorInfo(
                                SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                                getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_LOCATION_NO.getName()).getDescription(),
                                getLineData(), this.getClass());
                        return false;
                    }
                    catch (ScheduleException e)
                    {
                        // 6023439=Found illegal format data. Table:{0} TImestamp:{1} Item:{2} Value:{3}
                        createErrorLog(WmsMessageFormatter.format(6023439, "HostToEWN", getEntryTimestamp(),
                                Retrieval.PLAN_LOCATION_NO, inParam.getRetrievalLocation()), getLineData(),
                                this.getClass());
                        createLoadErrorInfo(
                                SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                                getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_LOCATION_NO.getName()).getDescription(),
                                getLineData(), this.getClass());
                        return false;
                    }
                }
            }
        }

        // 数量のチェック
        if (inParam.getPlanQty() <= 0)
        {
            // 6023432=Data with Plan Qty 0 or less found. Table: {0} Timestamp: {1}
            createErrorLog(WmsMessageFormatter.format(6023432, "HostToEWN", getEntryTimestamp()), getLineData(),
                    this.getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                    getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_QTY.getName()).getDescription(),
                    getLineData(), this.getClass());
            return false;
        }
        else if (inParam.getPlanQty() > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023433=Plan Qty exceeds the Max Work Qty {0}. Table:{1} Timestamp:{2}
            createErrorLog(WmsMessageFormatter.format(6023433, WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY),
                    "HostToEWN", getEntryTimestamp()), getLineData(), this.getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                    getEntity().getStoreMetaData().getFieldMetaData(Retrieval.PLAN_QTY.getName()).getDescription(),
                    getLineData(), this.getClass());
            return false;
        }

        return true;
    }

    /**
     * 予定取り込み用に未作業の出庫予定情報をロックします。<BR>
     * ロック対象が無かった場合falseを返します。<BR>
     *
     * @param inParam ロック対象の作業区分
     * @return 実行結果（正常：true 異常：false
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 一意項目が重複している場合にスローされます。
     */
    protected boolean lockRetrievalPlan(RetrievalInParameter inParam)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException
    {
        AbstractDBHandler sph = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey key = new RetrievalPlanSearchKey();

        key.setConsignorCode(inParam.getConsignorCode());
        key.setPlanDay(inParam.getRetrievalPlanDay());
        key.setShipTicketNo(inParam.getTicketNo());
        key.setShipLineNo(inParam.getLineNo());
        key.setBranchNo(inParam.getBranchNo());
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);

        if (sph.findPrimaryForUpdate(key, RetrievalPlanHandler.WAIT_SEC_NOWAIT) == null)
        {
            // ロック対象が無い場合
            return false;
        }

        return true;
    }

    /**
     * 予定取り込み用に未作業の入出庫作業情報をロックします。<BR>
     * ロック対象が無かった場合falseを返します。<BR>
     *
     * @param inParam ロック対象の作業区分
     * @return 実行結果（正常：true 異常：false
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 一意項目が重複している場合にスローされます。
     */
    protected boolean lockWorkInfo(RetrievalInParameter inParam)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException
    {
        try
        {
            // システム定義情報アクセスコントローラのインスタンスを生成する
            WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), this.getClass());
            // 在庫パッケージが導入されていない場合のみ処理を実行
            if (!sysController.hasStockPack())
            {
                AbstractDBHandler wih = new WorkInfoHandler(getConnection());
                WorkInfoSearchKey key = new WorkInfoSearchKey();

                key.setConsignorCode(inParam.getConsignorCode());
                key.setPlanDay(inParam.getRetrievalPlanDay());
                key.setShipTicketNo(inParam.getTicketNo());
                key.setShipLineNo(inParam.getLineNo());
                key.setShipBranchNo(inParam.getBranchNo());
                key.setKey(WorkInfo.JOB_TYPE, SystemDefine.JOB_TYPE_RETRIEVAL);
                key.setKey(WorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_UNSTART);

                if (wih.findPrimaryForUpdate(key, WorkInfoHandler.WAIT_SEC_NOWAIT) == null)
                {
                    // ロック対象が無い場合
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
     * 初期化を行います。
     * 
     */
    protected void prepare()
    {
        // 荷主マスタ情報
        _consignorHandler = new ConsignorHandler(getConnection());
        _consignorFinder = new ConsignorFinder(getConnection());
        _consignorSearchKey = new ConsignorSearchKey();
        // 出荷先マスタ情報
        _customerHandler = new CustomerHandler(getConnection());
        _customerFinder = new CustomerFinder(getConnection());
        _customerSearchKey = new CustomerSearchKey();
        _customerEntity = null;
        // 商品マスタ情報
        _itemHandler = new ItemHandler(getConnection());
        _itemFinder = new ItemFinder(getConnection());
        _itemSearchKey = new ItemSearchKey();
        _itemEntity = null;
        // エリアマスタ情報
        _areaFinder = new AreaFinder(getConnection());
        _areaSearchKey = new AreaSearchKey();
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
        itemLog.add(RetrievalInParameter.DATA_TYPE_RETRIEVAL);
        // ファイル名
        itemLog.add(getFileName());

        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(conn);

        // 常駐処理からの自動起動時、ユーザ情報がセットされないのでWareNavi固定値にて再編集を行います。
        DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();

        opeLogWriter.createOperationLog(userInfo, operationKind, itemLog);
    }

    /**
     * Check to see if a item is already recorded in the master.<BR>
     * If the master package is not installed, then unconditionally return true.
     * 
     * @param sKey The pre-configured search key.
     * @param sysController システムコントローラー
     * @return If the item code is found: true、otherwise: false
     * @throws CommonException Throw when a DB error occurs.
     */
    protected boolean existItemCode(ItemSearchKey sKey, WarenaviSystemController sysController)
            throws CommonException
    {
        if (sysController.hasMasterPack())
        {
            ItemFinder finder = null;
            try
            {
                finder = new ItemFinder(this.getConnection());
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
     * Find some customer params
     *
     * @param sKey The pre-configured search key.
     * @return Customer[]
     * @throws ReadWriteException Thrown when a database error occurs.
     */
    protected Customer[] findCustomer(CustomerSearchKey sKey)
            throws ReadWriteException
    {
        Customer[] entities = null;
        try
        {
            _customerFinder.open(true);

            // 検索処理実行
            //if (_supplierFinder.search(sKey) > 0)
            _customerFinder.search(sKey);
            //{
            entities = (Customer[])_customerFinder.getEntities(0, 1); //just need one
            //}

        }
        finally
        {
            // now that the search is done, close the finder
            _customerFinder.close();
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
     * Check to see if an area is already recorded.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @param sysController 
     * @return If the supplier code is found: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existArea(AreaSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _areaFinder.open(true);

            int searchCount = _areaFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_areaFinder != null)
            {
                _areaFinder.close();
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
        return new Retrieval();
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
        return "$Id: RetrievalDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $";
    }
}
