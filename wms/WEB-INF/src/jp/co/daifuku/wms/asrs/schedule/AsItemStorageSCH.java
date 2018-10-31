package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsItemStorageSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;

/**
 * AS/RS 入庫開始のスケジュール処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:17:00 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class AsItemStorageSCH
        extends AbstractAsrsSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public AsItemStorageSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期データ検索を行います。
     * 
     * @param p 検索条件を指定したパラメータ
     * @return 初期表示用データ
     * @throws CommonException アクセスエラーなどの例外発生時にスローされます。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        Params outParam = new Params();
       
        try
        {
            WebSettingHandler webhandler = new WebSettingHandler(getConnection());
            WebSettingSearchKey key = new WebSettingSearchKey();

            // 端末No.
            key.setTerminalNo(getWmsUserInfo().getTerminalNo());
            // 画面ID
            key.setFunctionid(p.getString(FUNCTION_ID));
            // キーデータ
            key.setKeydata(WebSetting.KEY_LIST_CHECK);

            WebSetting[] webset = (WebSetting[])webhandler.find(key);

            if (webset != null && webset.length > 0)
            {
                outParam.set(PRINT_FLAG, webset[0].getValue());
            }
        }
        catch (Exception e)
        {
            // 6006042=画面定義情報の参照に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006042, e), getClass().getName());
        }
        
        outParam.set(NEED_PUL_CHANGE, WmsParam.SOFTZONE_SELECT_ITEM);

        return outParam;
    }
        
    /**
     * 画面から入力された内容をパラメータとして受け取り、データをデータベースから取得して返します。<BR>
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     * <BR>
     *   <I>queryメソッドは以下のチェック・処理を行います。<BR></I>
     *   引き当て可能かをチェックし、引き当て可能ならば荷主名称・商品名称を取得します。<BR>
     *   なお、在庫パッケージなしの場合に本メソッドが呼ばれた場合、<CODE>ScheduleException</CODE>を投げます。<BR>
     *   以下の処理を行います。<BR>
     * <DIR>
     *   ・対象在庫が存在していること<BR>
     *   ・引当て可能数が入力移動数以上であること<BR>
     *   ・上記条件を満たし、荷主名称・商品名称が入力されていない場合、在庫情報から名称を取得します。<BR>
     * </DIR>
     * <BR>
     * ＜返却データ＞
     * <DIR>
     *   表示情報を含む<CODE>Parameter</CODE>インターフェースを実装したクラス<BR>
     * </DIR>
     * <BR>
     * @param p 表示データ取得条件を持つ<CODE>AsrsInParameter</CODE>クラスのインスタンス。<BR>
     *         <CODE>AsrsInParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
     * @return 検索結果を持つ<CODE>StockOutParameter</CODE>インスタンスの配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0の配列を返します。入力条件にエラーが発生した場合はnullを返します。<BR>
     *          要素数0の配列またはnullが返された場合は、<CODE>getMessage</CODE>メソッドでエラー内容をメッセージとして取得できます。
     * @throws CommonException DAIFUKU例外が発生した場合に通知されます。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        WorkInfoFinder finder = null;

        List<Params> params = null;
        Params outParam = null;

        try
        {
            finder = new WorkInfoFinder(getConnection());

            finder.open(true);

            // 検索処理実行
            int count = finder.search(createSearchKey(p));
            
            // データが無い場合は処理終了
            if (count == 0)
            {
                // 対象データはありませんでした。
                setMessage("6003011");
                return params;
            }
            // 複数存在した場合は処理終了
            else if (count > 1)
            {
                // 対象となる予定情報が複数件あります。入庫予定検索ボタンにて選択して下さい。
                setMessage("6023068");
                return params;
            }

            // 作業情報取得結果
            WorkInfo[] workInfos = (WorkInfo[])finder.getEntities(1);

            // return用のパラメータ
            outParam = new Params();
            params = new ArrayList<Params>();

            // 荷主コード
            outParam.set(CONSIGNOR_CODE, workInfos[0].getConsignorCode());
            // 入庫予定日
            outParam.set(STORAGE_PLAN_DAY, WmsFormatter.toDate(workInfos[0].getPlanDay()));
            // 商品コード
            outParam.set(ITEM_CODE, workInfos[0].getItemCode());
            // 商品名称
            outParam.set(ITEM_NAME, (String)workInfos[0].getValue(Item.ITEM_NAME, ""));
            // ケース入数
            outParam.set(ENTERING_QTY, workInfos[0].getBigDecimal(Item.ENTERING_QTY).intValue());
            // JANコード
            outParam.set(JAN_CODE, (String)workInfos[0].getValue(Item.JAN, ""));
            // ケースITF
            outParam.set(ITF, (String)workInfos[0].getValue(Item.ITF, ""));
            // 予定エリアNo.
            outParam.set(PLAN_AREA_NO, workInfos[0].getPlanAreaNo());
            // 棚No.
            outParam.set(LOCATION, workInfos[0].getPlanLocationNo());
            // 予定数
            outParam.set(PLAN_QTY, workInfos[0].getPlanQty());
            // 予定ロットNo.
            outParam.set(PLAN_LOT_NO, workInfos[0].getPlanLotNo());
            // ソフトゾーン
            outParam.set(SOFT_ZONE_NAME, (String)workInfos[0].getValue(SoftZone.SOFT_ZONE_NAME, ""));
            
            params.add(outParam);


            return params;
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        AsrsInParameter[] params = new AsrsInParameter[ps.length];

        for (int i = 0; i < ps.length; i++)
        {
            AsItemStorageSCHParams startParams = (AsItemStorageSCHParams)ps[i];
            params[i] = new AsrsInParameter(getWmsUserInfo());

            // 帳票発行フラグ
            params[i].setPrintFlag(startParams.getBoolean(PRINT_FLAG));
            // リストセル行No.
            params[i].setRowNo(startParams.getRowIndex());
            // 荷主コード
            params[i].setConsignorCode(startParams.getString(CONSIGNOR_CODE));
            // 作業区分
            if (StringUtil.isBlank(startParams.getString(JOB_TYPE)))
            {
                params[i].setJobType("");
            }
            else
            {
                params[i].setJobType(startParams.getString(JOB_TYPE));
            }
            // 入庫予定日
            params[i].setStorageDay(startParams.getString(STORAGE_PLAN_DAY));
            // 商品コード
            params[i].setItemCode(startParams.getString(ITEM_CODE));
            // 予定エリアNo.
            params[i].setPlanAreaNo(startParams.getString(PLAN_AREA_NO));
            // 棚No.
            params[i].setLocation(startParams.getString(LOCATION));
            // 予定ロットNo.
            params[i].setPlanLotNo(startParams.getString(PLAN_LOT_NO));
            // エリアNo.
            params[i].setAreaNo(startParams.getString(AREA_NO));
            // ゾーンNo.
            params[i].setZoneNo(startParams.getString(ZONE_NO));
            // ステーションNo.
            params[i].setStationNo(startParams.getString(STATION_NO));
            // ロットNo.
            params[i].setLotNo(startParams.getString(LOT_NO));
            // ケース入数
            params[i].setEnteringQty(startParams.getInt(ENTERING_QTY));
            // 入庫ケース数
            params[i].setStorageCaseQty(startParams.getInt(STORAGE_CASE_QTY));
            // 入庫ピース数
            params[i].setStoragePieceQty(startParams.getInt(STORAGE_PIECE_QTY));
            // 入庫数
            params[i].setStorageQty(params[i].getEnteringQty() * params[i].getStorageCaseQty()
                    + params[i].getStoragePieceQty());
            // ソフトゾーンID
            params[i].setSoftZoneNo(startParams.getString(SOFT_ZONE_ID));

        }

        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }
        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        try
        {
            AreaController areacon = new AreaController(getConnection(), getClass());
            
            // ソフトゾーンチェック
            if (params != null && params.length > 1 && WmsParam.SOFTZONE_SELECT_ITEM && WmsParam.MIXED_SOFTZONE)
            {
                if (!checkSoftZonePriority(params, areacon.getWhStationNo(params[0].getAreaNo()), params[0].getSoftZoneNo()))
                {
                    // 6023262=共通ソフトゾーンが存在しないため、混載できません。
                    setMessage("6023262");
                    return false;
                }
            }
            
            // 最大混載数チェック
            // 入力ボタン押下時と、設定ボタン押下時で、異なる倉庫が選択されている場合があるため、ここでもチェックを行う
            if (params.length > areacon.getNumMixedOfWarehouse(params[0].getAreaNo()))
            {
                // 6023095=混載数が最大混載数を超えるため、設定できません。
                setMessage("6023095");
                return false;
            }

            // ステーションなどが、入庫できる状態かをチェックします。
            if (!storageStationCheck(params[0].getStationNo()))
            {
                return false;
            }

            AsrsOperator ope = new AsrsOperator(getConnection(), getClass());

            //オペレータ呼び出し
            AsrsOutParameter outparam = ope.webStartStorage(params);

            ps[0].set(SETTING_UKEYS, outparam.getSettingUnitKey());

            // 6001006=設定しました。
            setMessage("6001006");
            
            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(PRINT_FLAG) ? WebSetting.KEYDATA_ON
                                                                : WebSetting.KEYDATA_OFF;
            updateWebSetting(getUserInfo().getTerminalNumber(), ps[0].getString(FUNCTION_ID), value);
      
            return true;
        }
        catch (LockTimeOutException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
        }
        catch (DataExistsException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        catch (RouteException e)
        {
            // 搬送ルート異常
            setMessage(getRouteErrorMessage(e.getRouteStatus()));
            return false;
        }
        catch (OperatorException e)
        {
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, params[e.getErrorLineNo() - 1].getRowNo()));
                setErrorRowIndex(params[e.getErrorLineNo() - 1].getRowNo());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }

    /**
     * 画面から入力された内容とリストセルエリアのデータをパラメータとして受け取り、
     * チェックを行います。<BR>
     *
     * @param p 入力パラメータ
     * @param ps リストセルエリアのパラメータ
     * @return 入力チェック、オーバーフロー、重複、商品マスタ・入庫棚エラーでない場合はtrueを返す。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean check(ScheduleParams p, ScheduleParams... ps)
            throws CommonException
    {
        // 入力エリアの内容
        AsItemStorageSCHParams checkParam = (AsItemStorageSCHParams)p;
        AsrsInParameter param = new AsrsInParameter(getWmsUserInfo());

        // 荷主コード
        param.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
        // 入庫予定日
        param.setStoragePlanDay(checkParam.getString(STORAGE_PLAN_DAY));
        // 商品コード
        param.setItemCode(checkParam.getString(ITEM_CODE));
        // 予定エリアNo.
        param.setPlanAreaNo(checkParam.getString(PLAN_AREA_NO));
        // 棚No.
        param.setLocation(checkParam.getString(LOCATION));
        // 予定ロットNo.
        param.setPlanLotNo(checkParam.getString(PLAN_LOT_NO));
        // エリアNo.
        param.setAreaNo(checkParam.getString(AREA_NO));
        // ゾーンNo.
        param.setZoneNo(checkParam.getString(ZONE_NO));
        // ステーションNo.
        param.setStationNo(checkParam.getString(STATION_NO));
        // ロットNo.
        param.setResultLotNo(checkParam.getString(LOT_NO));
        // 入庫ケース数
        param.setStorageCaseQty(checkParam.getInt(STORAGE_CASE_QTY));
        // 入庫ピース数
        param.setStoragePieceQty(checkParam.getInt(STORAGE_PIECE_QTY));
        // ケース入数
        param.setEnteringQty(checkParam.getInt(ENTERING_QTY));
        // 予定数
        param.setPlanQty(checkParam.getInt(PLAN_QTY));

        // ためうちエリアの内容
        AsrsInParameter[] paramlist = new AsrsInParameter[ps.length];

        for (int i = 0; i < ps.length; i++)
        {
            AsItemStorageSCHParams startParams = (AsItemStorageSCHParams)ps[i];
            paramlist[i] = new AsrsInParameter(getWmsUserInfo());

            // 帳票発行フラグ
            paramlist[i].setPrintFlag(startParams.getBoolean(PRINT_FLAG));
            // リストセル行No.
            paramlist[i].setRowNo(startParams.getRowIndex());
            // 荷主コード
            paramlist[i].setConsignorCode(startParams.getString(CONSIGNOR_CODE));
            // 作業区分
            paramlist[i].setJobType(startParams.getString(JOB_TYPE));
            // 入庫予定日
            paramlist[i].setStoragePlanDay(startParams.getString(STORAGE_PLAN_DAY));
            // 商品コード
            paramlist[i].setItemCode(startParams.getString(ITEM_CODE));
            // 予定エリアNo.
            paramlist[i].setPlanAreaNo(startParams.getString(PLAN_AREA_NO));
            // 棚No.
            paramlist[i].setLocation(startParams.getString(LOCATION));
            // 予定ロットNo.
            paramlist[i].setPlanLotNo(startParams.getString(PLAN_LOT_NO));
            // エリアNo.
            paramlist[i].setAreaNo(startParams.getString(AREA_NO));
            // ゾーンNo.
            paramlist[i].setZoneNo(startParams.getString(ZONE_NO));
            // ステーションNo.
            paramlist[i].setStationNo(startParams.getString(STATION_NO));
            // ロットNo.
            paramlist[i].setLotNo(startParams.getString(LOT_NO));
            // 入庫数
            paramlist[i].setStorageQty(startParams.getInt(STORAGE_QTY));
        }

        // 空パレット入力チェック
        if (!checkCorrectEmptyPB(param.getItemCode(), param.getResultLotNo(), param.getEnteringQty(),
                param.getStorageCaseQty(), param.getStoragePieceQty()))
        {
            return false;
        }

        // 空パレット、通常在庫混載チェック
        if (!checkAddListEmptyPB(param.getItemCode(), paramlist))
        {
            return false;
        }

        // 荷主コード、入庫予定日、商品コード、予定エリア、予定棚、予定ロットNo.、実績ロットNo.を
        // キーにして重複チェックを行います。
        if (paramlist != null)
        {
            for (int i = 0; i < paramlist.length; i++)
            {
                if (paramlist[i].getConsignorCode().equals(param.getConsignorCode())
                        && paramlist[i].getStoragePlanDay().equals(param.getStoragePlanDay())
                        && paramlist[i].getItemCode().equals(param.getItemCode())
                        && paramlist[i].getPlanAreaNo().equals(param.getPlanAreaNo())
                        && paramlist[i].getLocation().equals(param.getLocation())
                        && paramlist[i].getPlanLotNo().equals(param.getPlanLotNo())
                        && paramlist[i].getLotNo().equals(param.getResultLotNo()))
                {
                    // 6023020 = 既に同一データが存在するため、入力できません。
                    setMessage("6023020");
                    return false;
                }
            }
        }

        // 入庫ケース数と入庫ピース数が0の場合
        if (param.getStorageCaseQty() == 0 && param.getStoragePieceQty() == 0)
        {
            // 6023035 = ケース数またはピース数には1以上の値を入力してください。
            setMessage("6023035");
            return false;
        }

        // ケース入数が0、入庫ケース数が0以外の場合
        if (param.getEnteringQty() == 0 && param.getStorageCaseQty() != 0)
        {
            // 6023506 = ケース入数が0の場合、ケース数は入力できません。
            setMessage("6023036");
            return false;
        }

        // 残予定数チェック
        if (param.getPlanQty() == 0)
        {
            // 6023106 = すでに予定数量分の設定をしているため、入力できません。
            setMessage("6023106");
            return false;
        }

        long inputqty = (long)param.getStorageCaseQty() * (long)param.getEnteringQty() + param.getStoragePieceQty();
        // 残予定数オーバーチェック
        if (inputqty > param.getPlanQty())
        {
            // 6023197 = 入庫数には入庫予定数以下の値を入力してください。
            setMessage("6023197");
            return false;
        }

        // 作業上限数チェック
        if (inputqty > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023188=入庫数には作業上限数{0}以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023188, MAX_TOTAL_QTY_DISP));
            return false;
        }
        
        // 在庫上限数チェック
        if (inputqty > WmsParam.MAX_STOCK_QTY)
        {
            // 6023217=入庫数には在庫上限数{0}以下の値を入力してしてください。
            setMessage(WmsMessageFormatter.format(6023217, MAX_STOCK_QTY_DISP));
            return false;
        }

        // 表示件数チェック
        if (paramlist != null && paramlist.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
        {
            // 6023019 = 件数が{0}件を超えるため、入力できません。
            setMessage(WmsMessageFormatter.format(6023019, MAX_NUMBER_OF_DISP_DISP));
            return false;
        }

        // 最大混載数チェック
        AreaController areacon = new AreaController(getConnection(), getClass());
        if (paramlist.length + 1 > areacon.getNumMixedOfWarehouse(param.getAreaNo()))
        {
            // 6023095=混載数が最大混載数を超えるため、設定できません。
            setMessage("6023095");
            return false;
        }

        if (paramlist != null && paramlist.length != 0 && !WmsParam.MIXED_SOFTZONE)
        {
            // ソフトゾーン違いの混載チェック
            if (!checkSoftZoneMixed(param, paramlist))
            {
                // 6023260=ソフトゾーンの違う商品は混載できません。
                setMessage("6023260");
                return false;
            }
        }

        // 6001019 = 入力を受け付けました。
        setMessage("6001019");

        return true;
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
     * 入庫リスト作業情報を取得するための検索キークラスのインスタンスを生成します。<BR>
     * <BR>
     * @param inParam AS/RS入力パラメータ
     * @return 入出庫作業情報を取得するための検索キークラスのインスタンス
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        WorkInfoSearchKey searchKey = new WorkInfoSearchKey();

        /* 検索条件の指定 */
        // 入庫予定日
        if (!StringUtil.isBlank(p.getDate(STORAGE_PLAN_DAY)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(STORAGE_PLAN_DAY)));
        }
        // 商品コード
        if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
        {
            searchKey.setItemCode(p.getString(ITEM_CODE));
        }
        // 予定エリアNo.
        // NULLの場合は検索対象外、それ以外は検索条件とする（ブランクの場合はIS NULL検索となる）
        if (null != p.get(PLAN_AREA_NO))
        {
            searchKey.setPlanAreaNo(p.getString(PLAN_AREA_NO));
        }
        // 予定棚No.
        // NULLの場合は検索対象外、それ以外は検索条件とする（ブランクの場合はIS NULL検索となる）
        if (null != p.get(LOCATION))
        {
            searchKey.setPlanLocationNo(p.getString(LOCATION));
        }
        // 予定ロットNo.
        // NULLの場合は検索対象外、それ以外は検索条件とする（ブランクの場合はIS NULL検索となる）
        if (null != p.get(PLAN_LOT_NO))
        {
            searchKey.setPlanLotNo(p.getString(PLAN_LOT_NO));
        }
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));

        // 状態フラグ(未作業)
        searchKey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
        // 作業区分(入庫)
        searchKey.setJobType(WorkInfo.JOB_TYPE_STORAGE);

        /* 結合条件の指定 */
        // 荷主コード
        searchKey.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        searchKey.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
        // ソフトゾーン
        searchKey.setJoin(Item.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");
        
        /* 取得項目と集約条件の指定 */
        // 入庫予定日
        searchKey.setPlanDayCollect();
        searchKey.setPlanDayGroup();
        // 荷主コード
        searchKey.setConsignorCodeCollect();
        searchKey.setConsignorCodeGroup();
        // 商品コード
        searchKey.setItemCodeCollect();
        searchKey.setItemCodeGroup();
        // 商品名称
        searchKey.setCollect(Item.ITEM_NAME, "MAX", Item.ITEM_NAME);
        // ケース入数
        searchKey.setCollect(Item.ENTERING_QTY, "MAX", Item.ENTERING_QTY);
        // JANコード
        searchKey.setCollect(Item.JAN, "MAX", Item.JAN);
        // ケースITF
        searchKey.setCollect(Item.ITF, "MAX", Item.ITF);
        // ソフトゾーン名称
        searchKey.setCollect(SoftZone.SOFT_ZONE_NAME, "MAX", SoftZone.SOFT_ZONE_NAME);
        // 予定エリアNo.
        searchKey.setPlanAreaNoCollect();
        searchKey.setPlanAreaNoGroup();
        // 予定棚No.
        searchKey.setPlanLocationNoCollect();
        searchKey.setPlanLocationNoGroup();
        // 予定ロットNo.
        searchKey.setPlanLotNoCollect();
        searchKey.setPlanLotNoGroup();
        // 予定数
        searchKey.setPlanQtyCollect("SUM");

        return searchKey;
    }

    /**
     * 画面用の作業区分をDB用に変換します。
     * @param params 入力パラメータ
     * @throws CommonException 全ての例外をスローします。
     */
    protected void changeJobType(AsrsInParameter[] params)
            throws CommonException
    {
        for (AsrsInParameter param : params)
        {
            if (StringUtil.isBlank(param.getJobType()))
            {
                param.setJobType("");
            }
            else
            {
                param.setJobType(param.getJobType());
            }
        }
    }

    /**
     * 混載される商品のソフトゾーンとソフトゾーン優先情報のチェックを行います。
     * 
     * @param params 入庫設定パラメータの配列
     * @param whstation 倉庫ステーションNo.
     * @param softzone 入庫ソフトゾーン
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkSoftZonePriority(AsrsInParameter[] params , String whstation, String softzone)
            throws CommonException
    {
        if (SoftZone.SOFT_ZONE_ALL.equals(softzone))
        {
            // フリーソフトゾーンの場合、チェックなし
            return true;
        }
        
        SoftZonePriorityHandler softhandler = new SoftZonePriorityHandler(getConnection());
        SoftZonePrioritySearchKey softkey = new SoftZonePrioritySearchKey();
        ItemSearchKey itemkey = new ItemSearchKey();

        // 1件目商品の入庫可能ソフトゾーンを取得
        List<String> sz_lst = new ArrayList<String>();
        if (isFreeSoftZoneItem(params[0]))
        {
            // 商品がフリーソフトゾーン商品の場合
            // 全ソフトゾーンを取得
            SoftZoneHandler szhandler = new SoftZoneHandler(getConnection());
            SoftZoneSearchKey szkey = new SoftZoneSearchKey();

            // フリーソフトゾーン以外
            szkey.setSoftZoneId(SoftZone.SOFT_ZONE_ALL, "!=");
            szkey.setSoftZoneIdCollect();
            szkey.setSoftZoneIdOrder(true);
            
            SoftZone[] soft = (SoftZone[])szhandler.find(szkey);
            
            for (SoftZone sz : soft)
            {
                sz_lst.add(sz.getSoftZoneId());
            }
        }
        else
        {
            // 商品がフリーソフトゾーン商品でない場合
            softkey.setPrioritySoftZoneCollect();
    
            itemkey.setItemCode(params[0].getItemCode());
            itemkey.setSoftZoneIdCollect("DISTINCT");
            softkey.setKey(SoftZonePriority.SOFT_ZONE_ID, itemkey);
            softkey.setWhStationNo(whstation);
            softkey.setPrioritySoftZoneOrder(true);
    
            SoftZonePriority[] softzone_priority = (SoftZonePriority[])softhandler.find(softkey);
    
            for (SoftZonePriority sz : softzone_priority)
            {
                sz_lst.add(sz.getPrioritySoftZone());
            }
        }

        boolean find_flg;
        for (int i = sz_lst.size() - 1; i >= 0; i--)
        {
            // 2件目以降の商品とソフトゾーンをチェック
            for (int j = 1; j < params.length; j++)
            {
                if (isFreeSoftZoneItem(params[j]))
                {
                    // フリーソフトゾーン商品の場合はチェックしない
                    continue;
                }
                
                softkey.clear();
                itemkey.clear();

                softkey.setPrioritySoftZoneCollect();

                itemkey.setItemCode(params[j].getItemCode());
                itemkey.setSoftZoneIdCollect("DISTINCT");
                softkey.setKey(SoftZonePriority.SOFT_ZONE_ID, itemkey);
                softkey.setWhStationNo(whstation);
                softkey.setPrioritySoftZoneOrder(true);

                SoftZonePriority[] chk_softzone_priority = (SoftZonePriority[])softhandler.find(softkey);

                find_flg = false;
                for (SoftZonePriority chk_sz : chk_softzone_priority)
                {
                    if (sz_lst.get(i).equals(chk_sz.getPrioritySoftZone()))
                    {
                        find_flg = true;
                        break;
                    }
                }

                if (!find_flg)
                {
                    // 見つからない場合、削除
                    sz_lst.remove(i);
                    break;
                }
            }
        }

        // 入庫ソフトゾーン優先の取得
        softkey.clear();
        softkey.setPrioritySoftZoneCollect();
        softkey.setSoftZoneId(softzone);
        softkey.setWhStationNo(whstation);
        softkey.setPrioritySoftZoneOrder(true);
        
        SoftZonePriority[] storage_sz_priority = (SoftZonePriority[])softhandler.find(softkey);
        
        for (SoftZonePriority sz : storage_sz_priority)
        {
            if (sz_lst.contains(sz.getPrioritySoftZone()))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 入力商品がフリーソフトゾーン商品かどうかを返します。
     * 
     * @param item ためうち商品
     * @return フリーソフトゾーン商品の場合、true
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean isFreeSoftZoneItem(AsrsInParameter param)
            throws CommonException
    {
        ItemHandler ihandler = new ItemHandler(getConnection());
        ItemSearchKey ikey = new ItemSearchKey();
        
        ikey.setConsignorCode(param.getConsignorCode());
        ikey.setItemCode(param.getItemCode());
        ikey.setSoftZoneIdCollect();
        
        Item item = (Item)ihandler.findPrimary(ikey);
        
        if (item != null && Item.SOFT_ZONE_ALL.equals(item.getSoftZoneId()))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 混載される商品がソフトゾーン違いでないかをチェックします。
     * 
     * @param input 入力データ
     * @param params リストセルエリアのパラメータ
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkSoftZoneMixed(AsrsInParameter input, AsrsInParameter[] params)
            throws CommonException
    {
        ItemHandler ihandler = new ItemHandler(getConnection());
        ItemSearchKey ikey = new ItemSearchKey();
        
        // 入力データのソフトゾーンを取得
        ikey.setSoftZoneIdCollect();
        ikey.setConsignorCode(input.getConsignorCode());
        ikey.setItemCode(input.getItemCode());
        
        Item input_sz = (Item)ihandler.findPrimary(ikey);

        // リストセルエリアの1件目のみチェックする
        ikey.clear();
        ikey.setSoftZoneIdCollect();
        ikey.setConsignorCode(params[0].getConsignorCode());
        ikey.setItemCode(params[0].getItemCode());
        
        Item chk_sz = (Item)ihandler.findPrimary(ikey);
        
        if (input_sz.getSoftZoneId().equals(chk_sz.getSoftZoneId()))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 画面定義情報を更新します。<br>
     * 更新処理で異常が発生した場合は、Exceptionをスローせず、
     * ロギングのみ行います。
     * 
     * @param term 端末No.
     * @param funcid 画面ID
     * @param value 更新値
     */
    protected void updateWebSetting(String term, String funcid, String value)
    {
        try
        {
            WebSettingHandler webhandler = new WebSettingHandler(getConnection());
            WebSettingAlterKey akey = new WebSettingAlterKey();

            akey.setTerminalNo(term);
            akey.setFunctionid(funcid);
            akey.setKeydata(WebSetting.KEY_LIST_CHECK);

            akey.updateValue(value);
            akey.updateLastUpdatePname(getClass().getSimpleName());

            try
            {
                webhandler.modify(akey);
            }
            catch (NotFoundException e)
            {
                // 更新対象がない場合は新規作成
                WebSetting newdata = new WebSetting();

                newdata.setTerminalNo(term);
                newdata.setFunctionid(funcid);
                newdata.setKeydata(WebSetting.KEY_LIST_CHECK);
                newdata.setValue(value);
                newdata.setRegistPname(getClass().getSimpleName());
                newdata.setLastUpdatePname(getClass().getSimpleName());

                webhandler.create(newdata);
            }
        }
        catch (Exception e)
        {
            // 6006043=画面定義情報の更新に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006043, e), getClass().getName());
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
