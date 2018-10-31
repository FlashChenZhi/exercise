package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsNoPlanAddStorageSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 予定外入庫設定(積増)のスケジュール処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:17:00 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class AsNoPlanAddStorageSCH
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
    public AsNoPlanAddStorageSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面遷移のための処理を行います。<BR>
     * 入力された棚に在庫が存在するかをチェックします。<BR>
     * エリアマスタ情報を検索し、エリア名称を取得します。<BR>
     * @param p 入力パラメータ
     * @return Parameter エリア名称が格納されたパラメータ
     * @throws CommonException 全ての例外をスローします。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        AsrsInParameter inParam = new AsrsInParameter(getWmsUserInfo());

        Params outParam = null;

        // チェックボックスの状態
        if(!StringUtil.isBlank(p.getString(FUNCTION_ID)))
        {
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
	            	outParam = new Params();
	                outParam.set(PRINT_FLAG, webset[0].getValue());
	            }
	        }
	        catch (Exception e)
	        {
	            // 6006042=画面定義情報の参照に失敗しました。{0}
	            RmiMsgLogClient.write(new TraceHandler(6006042, e), getClass().getName());
	        }
	        return outParam;
        }
        // エリアNo.
        inParam.setAreaNo(p.getString(AREA_NO));
        // 棚No.
        inParam.setLocation(p.getString(LOCATION_NO));

        // 棚状態チェック
        if (!checkShelf(inParam.getAreaNo(), inParam.getLocation()))
        {
            return outParam;
        }

        // パレット情報を取得する
        Pallet pallet = getPallet(inParam.getAreaNo(), inParam.getLocation(), null);
        if (pallet == null)
        {
            // 該当データが無ければ、メッセージをセットし、falseを返します。
            // 6023002=指定された棚には在庫が存在しません。
            setMessage("6023002");
            return outParam;
        }
        inParam.setPalletId(pallet.getPalletId());

        // 在庫有無チェック
        if (!checkStockData(pallet))
        {
            return outParam;
        }

        // エリアマスタ情報ハンドラ
        AreaHandler areaHndl = new AreaHandler(getConnection());
        // エリアマスタ情報検索キー
        AreaSearchKey areaShKy = new AreaSearchKey();

        // 検索条件をセット
        areaShKy.setAreaNo(inParam.getAreaNo());

        // 検索結果を取得
        Area area = (Area)areaHndl.findPrimary(areaShKy);

        outParam = new Params();

        // エリア名称
        outParam.set(AREA_NAME, area.getAreaName());
        // パレットID
        outParam.set(PALLET_ID, pallet.getPalletId());

        return outParam;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new ItemFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
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
        // リストセルエリアデータ
        AsrsInParameter[] inParam = new AsrsInParameter[ps.length];

        for (int i = 0; i < ps.length; i++)
        {
            AsNoPlanAddStorageSCHParams asParam = (AsNoPlanAddStorageSCHParams)ps[i];
            inParam[i] = new AsrsInParameter(getWmsUserInfo());

            // 荷主コード
            inParam[i].setConsignorCode(asParam.getString(CONSIGNOR_CODE));
            // リストセル行No.
            inParam[i].setRowNo(asParam.getRowIndex());
            // エリアNo.
            inParam[i].setAreaNo(asParam.getString(AREA_NO));
            // 棚No.
            inParam[i].setLocation(asParam.getString(LOCATION_NO));
            // 商品コード
            inParam[i].setItemCode(asParam.getString(ITEM_CODE));
            // ロットNo.
            inParam[i].setLotNo(asParam.getString(LOT_NO));
            // 予定ロットNo.
            inParam[i].setPlanLotNo(asParam.getString(LOT_NO));
            // ケース入数
            inParam[i].setEnteringQty(asParam.getInt(ENTERING_QTY));
            // 予定ケース数
            inParam[i].setStorageCaseQty(asParam.getInt(PLAN_CASE_QTY));
            // 予定ピース数
            inParam[i].setStoragePieceQty(asParam.getInt(PLAN_PIECE_QTY));
            // 予定数
            inParam[i].setStorageQty(inParam[i].getEnteringQty() * inParam[i].getStorageCaseQty()
                    + inParam[i].getStoragePieceQty());
            // 商品名称
            inParam[i].setItemName(asParam.getString(ITEM_NAME));
            // JANコード
            inParam[i].setJanCode(asParam.getString(JAN));
            // ケースITF
            inParam[i].setItf(asParam.getString(ITF));
            // 帳票発行フラグ
            inParam[i].setPrintFlag(asParam.getBoolean(PRINT_FLAG));
            // パレットID
            inParam[i].setPalletId(asParam.getString(PALLET_ID));
            // ステーションNo.
            inParam[i].setStationNo(asParam.getString(STATION_NO));
            // 作業場
            inParam[i].setWorkplace(asParam.getString(WORK_PLACE));
            // 理由区分
            inParam[i].setReasonType(asParam.getInt(REASON_TYPE));
        }

        // 画面で指定されたステーション
        Station dispSt = null;
        // 画面で指定されたステーションのオペレータクラス
        StationOperator stOpe = null;

        String storageStNo = null;
        String retrievalStNo = null;

        if (!canStart())
        {
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        // 出庫作業可能チェック
        if (!retrievalStationCheck(inParam[0].getStationNo()))
        {
            return false;
        }

        // 棚状態チェック
        if (!checkShelf(inParam[0].getAreaNo(), inParam[0].getLocation()))
        {
            return false;
        }

        // エリア+棚No+パレットID 指定でパレット情報を取得する
        Pallet pallet = getPallet(inParam[0].getAreaNo(), inParam[0].getLocation(), inParam[0].getPalletId());
        if (pallet == null)
        {
            // 6023004=他端末で変更が行われました。前画面に戻り再度検索を行ってください。
            setMessage("6023004");
            return false;
        }

        // 在庫有無チェック
        if (!checkStockData(pallet))
        {
            return false;
        }

        // エリアコントローラ
        AreaController areaControl = new AreaController(getConnection(), getClass());
        // 在庫情報ハンドラ
        StockHandler stockHndl = new StockHandler(getConnection());
        // 在庫情報検索キー
        StockSearchKey stockShKy = new StockSearchKey();

        // 検索条件をセット
        // 配列内のエリアと棚は全て同一データのはずなので、inParam[0]
        stockShKy.setAreaNo(inParam[0].getAreaNo());
        stockShKy.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
        String asrsLocation = areaControl.toAsrsLocation(inParam[0].getAreaNo(), inParam[0].getLocation());
        stockShKy.setKey(Pallet.CURRENT_STATION_NO, asrsLocation);
        stockShKy.setPalletId(inParam[0].getPalletId());

        // 取得項目をセット
        stockShKy.setCollect(new FieldName(Stock.STORE_NAME, FieldName.ALL_FIELDS));
        stockShKy.setCollect(Pallet.EMPTY_FLAG);

        // 検索結果を取得
        Stock[] stock = (Stock[])stockHndl.find(stockShKy);
        if (stock == null || stock.length == 0)
        {
            // 6023004=他端末で変更が行われました。前画面に戻り再度検索を行ってください。
            setMessage("6023004");
            return false;
        }

        // 在庫情報の空パレットチェック
        else if (!checkAddStockEnptyPB(inParam[0].getItemCode(), stock, true))
        {
            return false;
        }

        // 積増・混載数チェック
        if (!checkMixed(null, inParam, stock))
        {
            return false;
        }

        try
        {
            // AS/RSオペレータ
            AsrsOperator asOptr = new AsrsOperator(getConnection(), getClass());
            // Web画面積増入庫作業開始
            AsrsOutParameter settingUnit = asOptr.webStartAddStorage(inParam);

            // 帳票発行ON
            if (inParam[0].isPrintFlag())
            {

                retrievalStNo = settingUnit.getDestStationNo();

                // 画面入力よりステーションを作成する
                dispSt = StationFactory.makeStation(getConnection(), retrievalStNo);
                // オペレータクラスを作成する 
                stOpe =
                        StationOperatorFactory.makeOperator(getConnection(), dispSt.getStationNo(),
                                dispSt.getClassName());

                // コの字ステーションの場合、入庫側ステーションを取得する
                if (stOpe instanceof FreeRetrievalStationOperator)
                {
                    FreeRetrievalStationOperator castStOpe = (FreeRetrievalStationOperator)stOpe;
                    storageStNo = castStOpe.getFreeStorageStationNo();
                }
                // コの字ステーション以外の場合（入出庫兼用）、
                // 画面で指定されたステーションを入庫ステーションにセットする
                else
                {
                    storageStNo = stOpe.getStation().getStationNo();
                }

                // 設定単位キー
                ps[0].set(SETTING_UNITKEY, settingUnit.getSettingUnitKey());
                // 出庫ステーションNo.
                ps[0].set(RETRIEVAL_STATION_NO, retrievalStNo);
                // 入庫ステーションNo.
                ps[0].set(STORAGE_STATION_NO, storageStNo);
                // ステーションNo.
                ps[0].set(STATION_NO, retrievalStNo);
            }

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
            // 他端末で変更が行われました。前画面に戻り再度検索を行ってください。
            setMessage("6023004");
            return false;
        }
        catch (RouteException ex)
        {
            // 6022013=搬送可能なルートがありません。
            setMessage("6022013");
            return false;
        }
        catch (NotFoundException ex)
        {
            // 6023004=他端末で変更が行われました。前画面に戻り再度検索を行ってください。
            setMessage("6023004");
            return false;
        }
        catch (OperatorException ex)
        {
            if (OperatorException.ERR_ALREADY_UPDATED.equals(ex.getErrorCode()))
            {
                // 6023004=他端末で変更が行われました。前画面に戻り再度検索を行ってください。
                setMessage("6023004");
                return false;
            }
            if (OperatorException.ERR_OVERFLOW.equals(ex.getErrorCode()))
            {
                // 6023183=No.{0} 入庫先の在庫数が在庫上限数を超えるため、入庫できません。{1}以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023183, ex.getErrorLineNo(),
                        WmsFormatter.getNumFormat(Long.valueOf(ex.getDetailString()))));
                setErrorRowIndex(ex.getErrorLineNo());
                return false;
            }
            throw ex;
        }
    }

    /**
     * マスタ管理導入チェックを行います。<BR>
     * @param checkParam 入力パラメータ
     * @return boolean
     * @throws CommonException 全ての例外をスローします。
     */
    public boolean check(ScheduleParams checkParam)
            throws CommonException
    {
        // WareNaviシステムコントローラ
        WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), getClass());
        // マスタ管理チェックメソッド
        return wmsControl.hasMasterPack();
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
        // 入力エリアデータ
        AsNoPlanAddStorageSCHParams aParam = (AsNoPlanAddStorageSCHParams)p;
        AsrsInParameter inParam = new AsrsInParameter(getWmsUserInfo());

        // エリアNo.
        inParam.setAreaNo(aParam.getString(AREA_NO));
        // 棚No.
        inParam.setLocation(aParam.getString(LOCATION_NO));
        // 商品コード
        inParam.setItemCode(aParam.getString(ITEM_CODE));
        // ロットNo.
        inParam.setLotNo(aParam.getString(LOT_NO));
        // ケース入数
        inParam.setEnteringQty(aParam.getInt(ENTERING_QTY));
        // 予定ケース数
        inParam.setStorageCaseQty(aParam.getInt(PLAN_CASE_QTY));
        // 予定ピース数
        inParam.setStoragePieceQty(aParam.getInt(PLAN_PIECE_QTY));
        // パレットID
        inParam.setPalletId(aParam.getString(PALLET_ID));

        // リストセルエリアデータ
        AsrsInParameter[] listParam = new AsrsInParameter[ps.length];

        for (int i = 0; i < ps.length; i++)
        {
            AsNoPlanAddStorageSCHParams asParam = (AsNoPlanAddStorageSCHParams)ps[i];
            listParam[i] = new AsrsInParameter(getWmsUserInfo());

            // リストセル行No.
            listParam[i].setRowNo(asParam.getRowIndex());
            // エリアNo.
            listParam[i].setAreaNo(asParam.getString(AREA_NO));
            // 商品コード
            listParam[i].setItemCode(asParam.getString(ITEM_CODE));
            // ロットNo.
            listParam[i].setLotNo(asParam.getString(LOT_NO));
        }

        if (inParam != null)
        {
            // 棚状態チェック
            if (!checkShelf(inParam.getAreaNo(), inParam.getLocation()))
            {
                return false;
            }

            // エリア+棚No+パレットID 指定でパレット情報を取得する
            Pallet pallet = getPallet(inParam.getAreaNo(), inParam.getLocation(), inParam.getPalletId());
            if (pallet == null)
            {
                // 6023004=他端末で変更が行われました。前画面に戻り再度検索を行ってください。
                setMessage("6023004");
                return false;
            }

            // 在庫有無チェック
            if (!checkStockData(pallet))
            {
                return false;
            }

            // 空棚入力チェック
            if (!checkCorrectEmptyPB(inParam.getItemCode(), inParam.getLotNo(), inParam.getEnteringQty(),
                    inParam.getStorageCaseQty(), inParam.getStoragePieceQty()))
            {
                return false;
            }

            // 異常棚指定時の入力チェック
            if (!checkIrregularItem(inParam.getItemCode()))
            {
                return false;
            }

            //直行品番の入力チェック
            if (!checkSimpleDirectTransferItem(inParam.getItemCode()))
            {
                return false;
            }

            // 入力チェック
            if (!inputCheck(inParam))
            {
                return false;
            }

            // 空パレットチェック
            if (!checkAddListEmptyPB(inParam.getItemCode(), listParam))
            {
                return false;
            }
            // リストセル重複チェック
            if (!doubleInputCheck(inParam, listParam))
            {
                return false;
            }
            // ソフトゾーンチェック
            if (!softZoneCheck(pallet, inParam.getItemCode(), listParam))
            {
                return false;
            }

        }

        // エリアコントローラ
        AreaController areaControl = new AreaController(getConnection(), getClass());
        // 在庫情報ハンドラ
        StockHandler stockHndl = new StockHandler(getConnection());
        // 在庫情報検索キー
        StockSearchKey stockShKy = new StockSearchKey();

        // 検索条件をセット
        // 配列内のエリアと棚は全て同一データのはずなので、inParam[0]
        stockShKy.setAreaNo(inParam.getAreaNo());
        stockShKy.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
        String asrsLocation = areaControl.toAsrsLocation(inParam.getAreaNo(), inParam.getLocation());
        stockShKy.setKey(Pallet.CURRENT_STATION_NO, asrsLocation);
        stockShKy.setPalletId(inParam.getPalletId());

        // 取得項目をセット
        stockShKy.setCollect(new FieldName(Stock.STORE_NAME, FieldName.ALL_FIELDS));
        stockShKy.setCollect(Pallet.EMPTY_FLAG);

        // 検索結果を取得
        Stock[] stock = (Stock[])stockHndl.find(stockShKy);
        if (stock == null || stock.length == 0)
        {
            // 6023004=他端末で変更が行われました。前画面に戻り再度検索を行ってください。
            setMessage("6023004");
            return false;
        }

        // 在庫情報を入力データの混載数チェック
        if (!checkMixed(inParam, listParam, stock))
        {
            return false;
        }

        // リストセル最大表示件数オーバー
        if (WmsParam.MAX_NUMBER_OF_DISP < listParam.length + 1)
        {
            // 6023019=件数が{0}件を超えるため、入力できません。
            setMessage(WmsMessageFormatter.format(6023019, WmsParam.MAX_NUMBER_OF_DISP));
            return false;
        }
        // 6001019=入力を受け付けました。
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
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return ItemSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        ItemSearchKey searchKey = new ItemSearchKey();

        // 検索条件をセット
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        searchKey.setItemCode(p.getString(ITEM_CODE));

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        Item[] entities = (Item[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (Item ent : entities)
        {
            Params param = new Params();

            // 該当データは一件のはずなので、要素0のみを返す。
            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, ent.getItemName());
            // ケース入数
            param.set(ENTERING_QTY, ent.getEnteringQty());
            // JANコード
            param.set(JAN, ent.getJan());
            // ケースITF
            param.set(ITF, ent.getItf());

            result.add(param);
        }

        return result;
    }

    /**
     * 在庫有無チェックを行います。<BR>
     * <BR>
     * @param pallet パレット情報
     * @return boolean
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean checkStockData(Pallet pallet)
            throws CommonException
    {
        // パレット状態が正常の場合
        if (Pallet.PALLET_STATUS_REGULAR.equals(pallet.getStatusFlag()))
        {
            if (Pallet.ALLOCATION_FLAG_ALLOCATED.equals(pallet.getAllocationFlag()))
            {
                // 取得データが引当済みであれば、メッセージをセットし、falseを返します。
                // 6023070=指定された棚は現在引当中です。
                setMessage("6023070");
                return false;
            }
        }
        // パレット状態が異常の場合
        else if (Pallet.PALLET_STATUS_IRREGULAR.equals(pallet.getStatusFlag()))
        {
            // パレット状態が異常であれば、メッセージをセットし、falseを返します。
            // 6023071=指定された棚は異常棚のため設定できません。
            setMessage("6023071");
            return false;
        }
        else
        {
            // パレット状態が上記以外の場合
            // 6023070=指定された棚は現在引当中です。
            setMessage("6023070");
            return false;
        }

        return true;
    }

    /**
     * 指定されたエリア＋棚Noのパレット情報を取得します。<BR>
     * <BR>
     * @param area エリアNo
     * @param location 棚No
     * @param palletId パレットID
     * @return Pallet
     * @throws CommonException 全ての例外をスローします。
     */
    protected Pallet getPallet(String area, String location, String palletId)
            throws CommonException
    {
        AreaController areacon = new AreaController(getConnection(), getClass());
        String station = areacon.toAsrsLocation(area, location);

        // AS/RSパレット情報ハンドラ
        PalletHandler palletHndl = new PalletHandler(getConnection());
        // AS/RSパレット情報検索キー
        PalletSearchKey palletShKy = new PalletSearchKey();

        // 副問い合せキー
        CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
        CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();

        // 検索条件をセット
        palletShKy.setCurrentStationNo(station);
        if (!StringUtil.isBlank(palletId))
        {
            // パレットIDが指定されているならば、検索条件に追加する。
            palletShKy.setPalletId(palletId);
        }
        palletShKy.setKey(Pallet.PALLET_ID, carryKey, "!=", "", "", true);

        // 検索結果を取得
        Pallet[] pallet = (Pallet[])palletHndl.find(palletShKy);

        if (pallet.length == 0 || pallet == null)
        {
            return null;
        }
        return pallet[0];
    }

    /**
     * 指定されたエリア、ロケーションNo.に該当する棚の状態をチェックします。<BR>
     * <BR>
     * @param area エリアNo.
     * @param location ロケーションNo.
     * @return boolean
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean checkShelf(String area, String location)
            throws CommonException
    {
        ShelfHandler shelfHndl = null;
        ShelfSearchKey shelfShKy = null;

        // AS/RS棚情報ハンドラ
        shelfHndl = new ShelfHandler(getConnection());
        // AS/RS棚情報検索キー
        shelfShKy = new ShelfSearchKey();

        // 検索条件のセット
        AreaController areacon = new AreaController(getConnection(), getClass());
        String stationNo = areacon.toAsrsLocation(area, location);
        shelfShKy.setStationNo(stationNo);

        // 検索結果の取得
        Shelf shelf = (Shelf)shelfHndl.findPrimary(shelfShKy);
        if (shelf == null)
        {
            // 該当データが無ければ、メッセージをセットし、falseを返します。
            // 6023067=実在する棚No.を入力してください。
            setMessage("6023067");
            return false;
        }
        if (Shelf.ACCESS_NG_FLAG_NG.equals(shelf.getAccessNgFlag()))
        {
            // 取得データがアクセス禁止であれば、メッセージをセットし、falseを返します。
            // 6023069=棚がアクセス不可のため、設定できません。
            setMessage("6023069");
            return false;
        }
        if (Shelf.PROHIBITION_FLAG_NG.equals(shelf.getProhibitionFlag()))
        {
            // 取得データが使用禁止棚であれば、メッセージをセットし、falseを返します。
            // 6123274=指定された棚は使用不可棚に設定されているため、設定できません。
            setMessage("6123274");
            return false;
        }

        return true;
    }

    /**
     * 混載数チェックを行います。<BR>
     * <BR>
     * @param inParam 入力エリアデータ
     * @param listParam リストセルエリアのパラメータ
     * @param stock 在庫情報
     * @return boolean
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean checkMixed(AsrsInParameter inParam, AsrsInParameter[] listParam, Stock[] stock)
            throws CommonException
    {
        int count = 0;
        for (int i = 0; i < stock.length; i++)
        {
            // 入力データと同一の在庫が存在するかチェック
            if (inParam != null && stock[i].getItemCode().equals(inParam.getItemCode())
                    && stock[i].getLotNo().equals(inParam.getLotNo()))
            {
                // 積増しされる商品数をカウントする
                count++;
            }

            // リストセルエリアデータと同一の在庫が存在するかチェック
            for (int j = 0; j < listParam.length; j++)
            {
                if (stock[i].getItemCode().equals(listParam[j].getItemCode())
                        && stock[i].getLotNo().equals(listParam[j].getLotNo()))
                {
                    // 積増しされる商品数をカウントする
                    count++;
                }
            }
        }

        int maxMixed = 0;
        int mixedQty = 0;
        AreaController areaControl = new AreaController(getConnection(), getClass());
        // 混載数 ＝ 現在の混載数 ＋ 新たに入庫する商品数(リストセルデータ数 － 積増し商品数)
        if (inParam == null)
        {
            mixedQty = stock.length + listParam.length - count;
            maxMixed = areaControl.getNumMixedOfWarehouse(listParam[0].getAreaNo());
        }
        else
        {
            mixedQty = stock.length + listParam.length + 1 - count;
            maxMixed = areaControl.getNumMixedOfWarehouse(inParam.getAreaNo());
        }

        // 最大混載数の取得
        if (maxMixed < mixedQty)
        {
            // 最大混載数を超えた場合、メッセージをセットし、falseを返す。
            // 6023095=混載数が最大混載数を超えるため、設定できません。
            setMessage("6023095");
            return false;
        }

        return true;
    }

    /**
     * 入力チェックを行います。<BR>
     * 入数とケース数、ケース数とピース数のチェックを行います。<BR>
     * @param inParam 入力データ
     * @return boolean
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean inputCheck(AsrsInParameter inParam)
            throws CommonException
    {
        // ケース入数が０で、ケース数の入力がある場合
        if (inParam.getEnteringQty() == 0 && inParam.getStorageCaseQty() > 0)
        {
            // 6023036=ケース入数が0の場合、ケース数は入力できません。
            setMessage("6023036");
            return false;
        }
        // ケース数、ピース数の入力が無い場合
        if (inParam.getStorageCaseQty() == 0 && inParam.getStoragePieceQty() == 0)
        {
            // 6023035=ケース数またはピース数には1以上の値を入力してください。
            setMessage("6023035");
            return false;
        }
        // 作業数チェック
        long inputQty =
                (long)inParam.getEnteringQty() * (long)inParam.getStorageCaseQty() + (long)inParam.getStoragePieceQty();
        if (inputQty > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023188=入庫数には作業上限数{0}以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023188, MAX_TOTAL_QTY_DISP));
            return false;
        }

        if (WmsParam.MAX_STOCK_QTY < inputQty)
        {
            // 6023217=入庫数には在庫上限数{0}以下の値を入力してしてください。
            setMessage(WmsMessageFormatter.format(6023217, MAX_STOCK_QTY_DISP));
            return false;
        }
        return true;
    }

    /**
     * リストセルエリアデータとの重複チェックを行います。<BR>
     * @param inParam 入力データ
     * @param listParam リストセルエリアデータ
     * @return boolean 正常な場合、true。異常があった場合、falseを返します。
     */
    protected boolean doubleInputCheck(AsrsInParameter inParam, AsrsInParameter[] listParam)
    {
        if (listParam == null || listParam.length == 0)
        {
            return true;
        }

        for (AsrsInParameter lParam : listParam)
        {
            // 同一商品、同一ロットを入力した場合、排他する。
            if (inParam.getItemCode().equals(lParam.getItemCode()) && inParam.getLotNo().equals(lParam.getLotNo()))
            {
                // 6023020=既に同一データが存在するため、入力できません。
                setMessage("6023020");
                return false;
            }
        }
        return true;
    }

    /**
     * リストセルエリアデータとの重複チェックを行います。<BR>
     * @param inParam 入力データ
     * @param listParam リストセルエリアデータ
     * @return boolean 正常な場合、true。異常があった場合、falseを返します。
     * @throws CommonException 
     */
    protected boolean softZoneCheck(Pallet pallet, String itemCode, AsrsInParameter[] params)
            throws CommonException
    {
        ItemHandler itemHand = new ItemHandler(getConnection());
        ItemSearchKey iKey = new ItemSearchKey();

        iKey.setItemCode(itemCode);
        Item ite = null;
        try
        {
            ite = (Item)itemHand.findPrimary(iKey);
        }
        catch (Exception e)
        {
            // 商品コードが存在しない場合
            // 6023021=商品コードがマスタに登録されていません。
            setMessage("6023021");
            return false;
        }

        SoftZonePriorityHandler softZoneHand = new SoftZonePriorityHandler(getConnection());
        SoftZonePrioritySearchKey softKey = new SoftZonePrioritySearchKey();


        StockHandler stHnad = new StockHandler(getConnection());
        StockSearchKey stSKey = new StockSearchKey();


        stSKey.clear();
        stSKey.setPalletId(pallet.getPalletId());
        // 元在庫を検索
        Stock[] stocks = (Stock[])stHnad.find(stSKey);

        // 混載なしの場合、パレットのソフトゾーンではなく在庫のソフトゾーンの混載とする
        if (!WmsParam.MIXED_SOFTZONE)
        {


            for (Stock stock : stocks)
            {
                // 在庫の商品のソフトゾーンを取得する
                iKey.clear();
                iKey.setItemCode(stock.getItemCode());
                Item item = (Item)itemHand.findPrimary(iKey);
                // 元在庫の商品のソフトゾーンと積み増し予定の商品を比較
                if (!ite.getSoftZoneId().equals(item.getSoftZoneId()))
                {
                    // 6023279=棚のソフトゾーンと指定商品のソフトゾーンが異なるため、混載できません。
                    setMessage("6023279");
                    return false;
                }
            }
        }
        // 商品がフリーで無い場合
        if (!SystemDefine.SOFT_ZONE_ALL.equals(ite.getSoftZoneId()))
        {
            boolean softZoneFlag = false;
            for (Stock stock : stocks)
            {

                // 在庫の商品のソフトゾーンを取得する
                iKey.clear();
                iKey.setItemCode(stock.getItemCode());
                Item item = (Item)itemHand.findPrimary(iKey);
                softKey.clear();
                softKey.setSoftZoneId(item.getSoftZoneId());
                softKey.setWhStationNo(pallet.getWhStationNo());

                SoftZonePriority[] softs = (SoftZonePriority[])softZoneHand.find(softKey);

                for (SoftZonePriority soft : softs)
                {
                    if (ite.getSoftZoneId().equals(soft.getPrioritySoftZone()))
                    {
                        // 商品のソフトゾーンがパレットのソフトゾーンと合う
                        softZoneFlag = true;
                    }
                }
                if (!softZoneFlag)
                {
                    // 6023279=棚のソフトゾーンと指定商品のソフトゾーンが異なるため、混載できません。
                    setMessage("6023279");
                }
            }


            return softZoneFlag;
        }

        return true;

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
