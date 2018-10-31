//$Id: ItemDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $
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
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.HostToEWN;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.IllegalDataException;
import jp.co.daifuku.wms.base.fileentity.HostItem;
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
 * <CODE>ItemDBDataLoader</CODE>クラスは、商品マスタ取込み処理を行うクラスです。<BR>
 * <CODE>AbstractDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * @version $Revision: 7650 $, $Date: 2010-03-17 18:31:17 +0900 (水, 17 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class ItemDBDataLoader
        extends AbstractDBDataLoader
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /** データ取込キー **/
    private static final String LOAD_KEY = "MASTER_ITEM";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    // 商品マスタ情報
    private ItemHandler _ItemHandler = null;

    private ItemSearchKey _ItemSearchKey = null;

    private Item[] _ItemEntity = null;

    // 商品固定棚情報
    private FixedLocateInfoFinder _fixedLocFinder = null;

    private FixedLocateInfoSearchKey _fixedLocSearchKey = null;

    // 入荷予定情報
    private ReceivingPlanFinder _receivePlanFinder = null;

    private ReceivingPlanSearchKey _receivePlanSearchKey = null;

    // 入庫予定情報
    private StoragePlanFinder _storagePlanFinder = null;

    private StoragePlanSearchKey _storagePlanSearchKey = null;

    // 出庫予定情報
    private RetrievalPlanFinder _retPlanFinder = null;

    private RetrievalPlanSearchKey _retPlanSearchKey = null;

    // 入出庫作業情報
    private WorkInfoFinder _workinfoFinder = null;

    private WorkInfoSearchKey _workinfoSearchKey = null;

    // 出荷予定情報
    private ShipPlanFinder _shipPlanFinder = null;

    private ShipPlanSearchKey _shipPlanSearchKey = null;

    // 移動作業情報
    private MoveWorkInfoFinder _moveWIFinder = null;

    private MoveWorkInfoSearchKey _moveWISearchKey = null;

    // 棚卸作業情報
    private InventWorkInfoFinder _inventWIFinder = null;

    private InventWorkInfoSearchKey _inventWISearchKey = null;

    // 在庫情報
    private StockFinder _stockFinder = null;

    private StockSearchKey _stockSearchKey = null;

    // 作業単位数マスタ情報
    private WorkingUnitFinder _workingUnitFinder = null;

    private WorkingUnitSearchKey _workingUnitSearchKey = null;

    // AS/RSソフトゾーン情報
    private SoftZoneHandler _softZoneHandler = null;

    private SoftZoneSearchKey _softZoneSearchKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 自動処理より呼ばれるインスタンスです。
     */
    public ItemDBDataLoader()
    {
        setDataType(SystemDefine.LOAD_DATA_TYPE_MASTER_ITEM);
    }

    /**
     * データベースコネクションと呼び出し元クラスを指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public ItemDBDataLoader(Connection conn, Connection errorInfoConn)
    {
        super(conn, errorInfoConn);
        setDataType(SystemDefine.LOAD_DATA_TYPE_MASTER_ITEM);
    }

    /**
     * データベースコネクションと呼び出し元クラスとユーザ情報を指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param userInfo ユーザ情報
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public ItemDBDataLoader(Connection conn, Connection errorInfoConn, DfkUserInfo userInfo)
    {
        super(conn, errorInfoConn, userInfo);
        setDataType(SystemDefine.LOAD_DATA_TYPE_MASTER_ITEM);
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
        return WmsParam.MAX_RECORD_ITEM;
    }

    /**
     * WareNaviSystemオブジェクトをパラメータとして受け取り、<BR>
     * 仕入先マスタデータを取込みます。<BR>
     * @param sysController  WarenaviSystemControllerオブジェクト
     * @param inEntity HostToEWN inEntity
     * @param outParameter パラメータ
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

        //even though we're not reading a file, we need the handler to tell us the offsets of the fields we are reading
        HostItem entity = new HostItem();
        FileHandler handler = AbstractFileHandler.getInstance(entity);//we'll need this when it comes time to create the log
        boolean isFileCopy = false;
        getErrlist().setFileName(getFileName());

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
                            return false;
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
                return false;
            }
            catch (NumberFormatException e)
            {
                setSkipFlag(true);
                setErrorFlag(true);
                // 6023438=Invalid format data found. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023438, "HostToEWN", getEntryTimestamp(), "DATA",
                        getLineData()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR, "", getLineData(), this.getClass());
                return false;
            }

            if (isErrorFlag())
            {
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

            // If the loading count exceeds the upper limit then terminate the loading process
            int maxCount = getMaxRecord(ExchangeEnvironment.DATA_TYPE_MASTER_ITEM);
            if (getAllItemCount() > maxCount && maxCount >= 0)
            {
                // 全NG
                setErrorFlag(true);
                // 6023041=レコード数が{0}件を超えたため、処理を中断しました。ファイル名:{1}
                setMessage(WmsMessageFormatter.format(6023041, WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_ITEM),
                        "HostToEWN"));//getFilePath() + getFileName()));
                createErrorLog(WmsMessageFormatter.format(6023041, WmsFormatter.getNumFormat(WmsParam.MAX_RECORD_ITEM),
                        "HostToEWN"), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_OVER_LINES, "", "", this.getClass());
                return false;
            }

            // 値のチェックを行う、不正だった場合一行をスキップする
            if (SystemDefine.MASTERDATA_LOAD_FLAG_NORMAL.equals(inParam.getLoadFlag())
                    && !check(inParam, handler, sysController))
            {
                setSkipFlag(true);
            }

            // 商品マスタ検索処理
            _ItemSearchKey.clear();
            _ItemSearchKey.setConsignorCode(inParam.getConsignorCode());
            _ItemSearchKey.setItemCode(inParam.getItemCode());

            _ItemEntity = (Item[])_ItemHandler.find(_ItemSearchKey);

            // 対象データがDBに存在するかチェック
            if (_ItemEntity != null && _ItemEntity.length > 0)
            {
                if (SystemDefine.MASTERDATA_LOAD_FLAG_NORMAL.equals(inParam.getLoadFlag()))
                {
                    // 通常データで対象がすでに存在した場合(二重取込)
                    setSkipFlag(true);
                    // 6023417=Skipped. Same data already exists. Table: {0} Timestamp: {1}
                    createErrorLog(WmsMessageFormatter.format(6023417, "HostToEWN", getEntryTimestamp()),
                            getLineData(), this.getClass());
                    createLoadErrorInfo(SystemDefine.ERROR_FLAG_REPETITION_DATA, "", getLineData(), this.getClass());
                    return false;
                }
                else if (SystemDefine.MASTERDATA_LOAD_FLAG_DELETE.equals(inParam.getLoadFlag()))
                {
                    // 他テーブルで使用していないかチェックを行なう
                    if (!checkRelation(inParam, handler, sysController))
                    {
                        setSkipFlag(true);
                        return false;
                    }

                    // システム管理区分がシステム管理（削除NGデータ）の場合は削除不可
                    if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(_ItemEntity[0].getManagementType()))
                    {
                        // 6023424=Unable to delete. The specified Item Code reserved for the System. Table :{0} Timestamp:{1}
                        createErrorLog(WmsMessageFormatter.format(6023424, "HostToEWN", getEntryTimestamp()),
                                getLineData(), this.getClass());
                        createLoadErrorInfo(
                                SystemDefine.ERROR_FLAG_DELETE_NG_DATA,
                                getEntity().getStoreMetaData().getFieldMetaData(HostItem.LOAD_FLAG.getName()).getDescription(),
                                getLineData(), this.getClass());
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
                    // AS/RSパッケージが導入されている場合のみ
                    if (sysController.hasAsrsPack())
                    {
                        // 指定されたソフトゾーンIDが存在するかチェック
                        _softZoneSearchKey.clear();
                        _softZoneSearchKey.setSoftZoneId(inParam.getSoftZoneId());
                        if (_softZoneHandler.count(_softZoneSearchKey) == 0)
                        {
                            // 6023442=ソフトゾーンが未登録のため、登録/修正できません。テーブル:{0} 時間:{1} 項目:{2} 値:{3}
                            createErrorLog(WmsMessageFormatter.format(6023442, "HostToEWN", getEntryTimestamp(),
                                    HostItem.SOFT_ZONE_ID, inParam.getSoftZoneId()), getLineData(), this.getClass());
                            createLoadErrorInfo(
                                    SystemDefine.ERROR_FLAG_MASTER_UNREGIST,
                                    getEntity().getStoreMetaData().getFieldMetaData(HostItem.SOFT_ZONE_ID.getName()).getDescription(),
                                    getLineData(), this.getClass());
                            setSkipFlag(true);
                            return false;
                        }
                    }
                    // データ更新処理(登録処理)
                    update(inParam);
                    setRegistFlag(true);
                }
                else if (SystemDefine.MASTERDATA_LOAD_FLAG_DELETE.equals(inParam.getLoadFlag()))
                {
                    // 削除データで対象がなかった場合
                    setSkipFlag(true);
                    // 6020046=削除対象が存在しなかったため、スキップしました。ファイル:{0} 行:{1}
                    createErrorLog(WmsMessageFormatter.format(6023422, "HostToEWN", getEntryTimestamp()),
                            getLineData(), this.getClass());
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
            // 6023419=Skipped. Target data being processed by another terminal.  Table:{0} Timestamp:{1}
            createErrorLog(WmsMessageFormatter.format(6023419, "HostToEWN", getEntryTimestamp()), getLineData(),
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
            HostItem entity = new HostItem();
            try
            {
                HostIOUtil handler = new HostIOUtil();

                handler.open(entity, null);

                // ファイルハンドラより一行取得
                entity = (HostItem)handler.next(inEntity.getData());

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
            inParam.setLoadFlag(getAsciiValue(entity.getLoadFlag().trim(), HostItem.LOAD_FLAG, true));
            // 荷主コード
            inParam.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            // 商品コード
            inParam.setItemCode(getAsciiValue(entity.getItemCode().trim(), HostItem.ITEM_CODE, true));
            // 商品名称
            inParam.setItemName(getValue(StringUtil.rtrim(entity.getItemName()), HostItem.ITEM_NAME));
            // ソフトゾーンID
            inParam.setSoftZoneId(getValue(entity.getSoftZoneId(), HostItem.SOFT_ZONE_ID));
            // 入数
            inParam.setEnteringQty(getIntValue(entity.getEnteringQty(), HostItem.ENTERING_QTY));
            // JANコード
            inParam.setJanCode(getValue(entity.getJan().trim(), HostItem.JAN));
            // ケースITF
            inParam.setItf(getValue(entity.getItf().trim(), HostItem.ITF));
            // 上限在庫数
            inParam.setUpperQty(getIntValue(entity.getUpperQty(), HostItem.UPPER_QTY));
            // 下限在庫数
            inParam.setLowerQty(getIntValue(entity.getLowerQty(), HostItem.LOWER_QTY));

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
     * パラメータの値をチェックします。<BR>
     * このメソッドでNGだった場合は一行をスキップします。<BR>
     * @param inParam MasterInParameterクラス
     * @param handler FileHandlerクラス
     * @param sysController WarenaviSystemControllerクラス
     * @return 実行結果（正常：true 異常：false）
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     */
    protected boolean check(MasterInParameter inParam, FileHandler handler, WarenaviSystemController sysController)
            throws ReadWriteException,
                IOException,
                ValidateException
    {
        // 下限在庫数が上限在庫数を上回る値
        if (inParam.getUpperQty() < inParam.getLowerQty())
        {
            // 6023425=Min Stock Qty exceeds Max Stock Qty. Table :{0} Timestamp:{1}
            createErrorLog(WmsMessageFormatter.format(6023425, "HostToEWN", getEntryTimestamp()), getLineData(),
                    this.getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                    getEntity().getStoreMetaData().getFieldMetaData(HostItem.LOWER_QTY.getName()).getDescription(),
                    getLineData(), this.getClass());
            return false;
        }

        return true;
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
        // マスタPackage Add-On時
        if (sysController.hasMasterPack())
        {
            // 商品固定棚情報にて使用されているかチェック
            _fixedLocSearchKey.clear();
            _fixedLocSearchKey.setConsignorCode(inParam.getConsignorCode());
            _fixedLocSearchKey.setItemCode(inParam.getItemCode());
            if (existFixedLocate(_fixedLocSearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }
        // 入荷Package Add-On時
        if (sysController.hasReceivingPack())
        {
            // 入荷予定情報にて使用されているかチェック
            _receivePlanSearchKey.clear();
            _receivePlanSearchKey.setConsignorCode(inParam.getConsignorCode());
            _receivePlanSearchKey.setItemCode(inParam.getItemCode());
            _receivePlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existReceivePlan(_receivePlanSearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
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
            _storagePlanSearchKey.setItemCode(inParam.getItemCode());
            _storagePlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existStoragePlan(_storagePlanSearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
            // 棚卸作業情報にて使用されているかチェック
            _inventWISearchKey.clear();
            _inventWISearchKey.setConsignorCode(inParam.getConsignorCode());
            _inventWISearchKey.setItemCode(inParam.getItemCode());
            _inventWISearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existInventWorkInfo(_inventWISearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }
        // 出庫Package Add-On時
        if (sysController.hasRetrievalPack())
        {
            // 出庫予定情報にて使用されているかチェック
            _retPlanSearchKey.clear();
            _retPlanSearchKey.setConsignorCode(inParam.getConsignorCode());
            _retPlanSearchKey.setItemCode(inParam.getItemCode());
            _retPlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existRetrievePlan(_retPlanSearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
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
            _workinfoSearchKey.setItemCode(inParam.getItemCode());
            _workinfoSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existWorkInfo(_workinfoSearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }
        // クロスドックPackage, 出荷Package Add-On時
        if (sysController.hasCrossdockPack() || sysController.hasShippingPack())
        {
            // クロスドックパッケージ導入時、
            // CrossDockPlanのほうがShipPlanよりも先に削除される可能性があるため
            // CrossDockPlanではなくShipPlanでチェックする
            // 出荷予定情報にて使用されているかチェック
            _shipPlanSearchKey.clear();
            _shipPlanSearchKey.setConsignorCode(inParam.getConsignorCode());
            _shipPlanSearchKey.setItemCode(inParam.getItemCode());
            _shipPlanSearchKey.setWorkStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
            _shipPlanSearchKey.setBerthStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "", ")", true);
            if (existShipPlan(_shipPlanSearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }
        // 在庫Package Add-On時
        if (sysController.hasStockPack())
        {
            // 在庫情報にて使用されているかチェック
            _stockSearchKey.clear();
            _stockSearchKey.setConsignorCode(inParam.getConsignorCode());
            _stockSearchKey.setItemCode(inParam.getItemCode());
            if (existStock(_stockSearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
            // 移動作業情報にて使用されているかチェック
            _moveWISearchKey.clear();
            _moveWISearchKey.setConsignorCode(inParam.getConsignorCode());
            _moveWISearchKey.setItemCode(inParam.getItemCode());
            _moveWISearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (existMoveWorkInfo(_moveWISearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
                        getLineData(), this.getClass());
                return false;
            }
        }
        // 分析Package Add-On時
        if (sysController.hasAnalysisPack())
        {
            // 作業単位数マスタ情報にて使用されているかチェック
            _workingUnitSearchKey.clear();
            _workingUnitSearchKey.setConsignorCode(inParam.getConsignorCode());
            _workingUnitSearchKey.setItemCode(inParam.getItemCode());
            if (existWorkingUnit(_workingUnitSearchKey))
            {
                // 6023426=Unable to delete. The specified Item Code in use. Table:{0} Timestamp:{1} Field: {2} Value: {3}
                createErrorLog(WmsMessageFormatter.format(6023426, "HostToEWN", getEntryTimestamp(),
                        HostItem.ITEM_CODE, inParam.getItemCode()), getLineData(), this.getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_USEED_MASTER_CODE,
                        getEntity().getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription(),
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
        // 通常データだった場合のみチェックを不要
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(inParam.getLoadFlag()))
        {
            // 登録処理
            Item entity = new Item();

            // システム管理区分（通常）
            entity.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
            // 荷主コード
            entity.setConsignorCode(inParam.getConsignorCode());
            // 商品コード
            entity.setItemCode(inParam.getItemCode());
            // 商品名称
            entity.setItemName(inParam.getItemName());
            // ソフトゾーンID
            entity.setSoftZoneId(inParam.getSoftZoneId());
            // JANコード
            entity.setJan(inParam.getJanCode());
            // ケースITF
            entity.setItf(inParam.getItf());
            // ボールITF
            entity.setBundleItf("");
            // ケース入数
            entity.setEnteringQty(inParam.getEnteringQty());
            // ボール入数
            entity.setBundleEnteringQty(0);
            // 上限在庫数
            entity.setUpperQty(inParam.getUpperQty());
            // 商品名称
            entity.setLowerQty(inParam.getLowerQty());
            // 登録処理名
            entity.setRegistPname(this.getClass().getSimpleName());
            // 最終更新処理名
            entity.setLastUpdatePname(this.getClass().getSimpleName());

            _ItemHandler.create(entity);

            ItemController sCtl = new ItemController(getConnection(), this.getClass());

            // 商品マスタ改廃履歴登録
            sCtl.insertHistory(entity, SystemDefine.UPDATE_KIND_REGIST, inParam.getWmsUserInfo().getDfkUserInfo());
        }
        else
        {
            // 削除処理
            _ItemSearchKey.clear();
            _ItemSearchKey.setConsignorCode(inParam.getConsignorCode());
            _ItemSearchKey.setItemCode(inParam.getItemCode());

            // 既存情報取得
            Item entity = (Item)_ItemHandler.findPrimary(_ItemSearchKey);

            _ItemHandler.drop(_ItemSearchKey);

            ItemController sCtl = new ItemController(getConnection(), this.getClass());

            // 商品マスタ改廃履歴登録
            sCtl.insertHistory(entity, SystemDefine.UPDATE_KIND_DELETE, inParam.getWmsUserInfo().getDfkUserInfo());
        }
    }

    /**
     * 初期化を行います。
     * @param sysController  WarenaviSystemControllerオブジェクト
     */
    protected void prepare(WarenaviSystemController sysController)
    {
        // 商品マスタ情報
        _ItemHandler = new ItemHandler(getConnection());
        _ItemSearchKey = new ItemSearchKey();

        // マスタPackage Add-On時
        if (sysController.hasMasterPack())
        {
            // 商品固定棚情報
            _fixedLocFinder = new FixedLocateInfoFinder(getConnection());
            _fixedLocSearchKey = new FixedLocateInfoSearchKey();
        }

        // 入荷Package Add-On時
        if (sysController.hasReceivingPack())
        {
            // 入荷予定情報
            _receivePlanFinder = new ReceivingPlanFinder(getConnection());
            _receivePlanSearchKey = new ReceivingPlanSearchKey();
        }

        // 入庫Package Add-On時
        if (sysController.hasStoragePack())
        {
            // 入庫予定情報
            _storagePlanFinder = new StoragePlanFinder(getConnection());
            _storagePlanSearchKey = new StoragePlanSearchKey();

            // 棚卸作業情報
            _inventWIFinder = new InventWorkInfoFinder(getConnection());
            _inventWISearchKey = new InventWorkInfoSearchKey();
        }

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

            // AS/RSソフトゾーン情報
            _softZoneHandler = new SoftZoneHandler(getConnection());
            _softZoneSearchKey = new SoftZoneSearchKey();
        }

        // クロスドックPackage, 出荷Package Add-On時
        if (sysController.hasCrossdockPack() || sysController.hasShippingPack())
        {
            // 出荷予定情報
            _shipPlanFinder = new ShipPlanFinder(getConnection());
            _shipPlanSearchKey = new ShipPlanSearchKey();
        }

        // 在庫Package Add-On時
        if (sysController.hasStockPack())
        {
            // 在庫情報
            _stockFinder = new StockFinder(getConnection());
            _stockSearchKey = new StockSearchKey();

            // 移動作業情報
            _moveWIFinder = new MoveWorkInfoFinder(getConnection());
            _moveWISearchKey = new MoveWorkInfoSearchKey();
        }
        // 分析Package Add-On時
        if (sysController.hasAnalysisPack())
        {
            // 在庫情報
            _workingUnitFinder = new WorkingUnitFinder(getConnection());
            _workingUnitSearchKey = new WorkingUnitSearchKey();
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
        itemLog.add(MasterInParameter.DATA_TYPE_ITEM_MASTER);
        // ファイル名称
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
     * @param sysController システムコントロール
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
     * Check to see if this item code is being used with a fixed location.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in fixed location: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existFixedLocate(FixedLocateInfoSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _fixedLocFinder.open(true);

            int searchCount = _fixedLocFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_fixedLocFinder != null)
            {
                _fixedLocFinder.close();
            }
        }
        return true;
    }

    /**
     * Check to see if this item code is being used with a regceivin plan.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in a receiving plan: true、otherwise: false
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
     * Check to see if this item code is being used with a storage plan.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in a storage plan: true、otherwise: false
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
     * Check to see if this item code is being used with invent work info.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in invent work info: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existInventWorkInfo(InventWorkInfoSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _inventWIFinder.open(true);

            int searchCount = _inventWIFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_inventWIFinder != null)
            {
                _inventWIFinder.close();
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
     * Check to see if this item code is being used with stock.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in stock: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existStock(StockSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _stockFinder.open(true);

            int searchCount = _stockFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_stockFinder != null)
            {
                _stockFinder.close();
            }
        }
        return true;
    }

    /**
     * Check to see if this item code is being used with move work info.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in move work info: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existMoveWorkInfo(MoveWorkInfoSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _moveWIFinder.open(true);

            int searchCount = _moveWIFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_moveWIFinder != null)
            {
                _moveWIFinder.close();
            }
        }
        return true;
    }

    /**
     * Check to see if this item code is being used with working unit.<BR>
     * 
     * @param sKey The pre-configured search key.
     * @return If the item code is involved in working unit: true、otherwise: false
     * @throws ReadWriteException Throw when a DB error occurs.
     */
    protected boolean existWorkingUnit(WorkingUnitSearchKey sKey)
            throws ReadWriteException
    {
        try
        {
            _workingUnitFinder.open(true);

            int searchCount = _workingUnitFinder.search(sKey);

            if (searchCount < 1)
            {
                return false;
            }
        }
        finally
        {
            if (_workingUnitFinder != null)
            {
                _workingUnitFinder.close();
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
        return new HostItem();
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
        return "$Id: ItemDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $";
    }
}
