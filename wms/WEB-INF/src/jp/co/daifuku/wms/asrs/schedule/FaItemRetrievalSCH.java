// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.schedule.FaItemRetrievalSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsWorkInfoController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkListHandler;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.entity.WorkList;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.retrieval.allocate.RetrievalAllocateOperator;
import jp.co.daifuku.wms.retrieval.allocate.ShortageOperator;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListDASCH;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListDASCHParams;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalShortageInquiryDASCH;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalShortageInquiryDASCHParams;
import jp.co.daifuku.wms.retrieval.exporter.AsRetrievalWorkListParams;
import jp.co.daifuku.wms.retrieval.exporter.RetrievalWorkListParams;
import jp.co.daifuku.wms.retrieval.exporter.ShortageListParams;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;

/**
 * 商品コード指定出庫のスケジュール処理を行います。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaItemRetrievalSCH
        extends AbstractAsrsSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 出庫フリーステーションクラス名
     */
    private static final String FREE_RETRIEVAL_STATION_CLASSNAME = FreeRetrievalStationOperator.class.getName();

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * 確認ダイアログを表示するかどうか
     */
    private boolean _confirmFlag = false;

    /**
     * 作業があったかどうか
     */
    private boolean _hasWork = false;

    /**
     * AS/RSの出庫開始処理を行ったかどうか
     */
    private boolean _startAsrsWork = false;

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
    public FaItemRetrievalSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
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
    	StockHandler handler = new StockHandler(getConnection());
    	int totalQty = 0;

    	String stationNo = p.getString(STATION_NO);
    	if (WmsParam.ALL_STATION.equals(stationNo))
    	{
    		Stock floorStocks = (Stock)handler.findPrimary(createFloorSearchKey(p));
    		totalQty += floorStocks.getAllocationQty();

    		Stock asrsStocks = (Stock)handler.findPrimary(createAsrsSearchKey(p));
    		totalQty += asrsStocks.getAllocationQty();
    	}
		else if (stationNo.startsWith(AsrsInParameter.SELECT_STATION_NONE))
		{
    		Stock floorStocks = (Stock)handler.findPrimary(createFloorSearchKey(p));
    		totalQty += floorStocks.getAllocationQty();
		}
		else
		{
    		Stock asrsStocks = (Stock)handler.findPrimary(createAsrsSearchKey(p));
    		totalQty += asrsStocks.getAllocationQty();
		}

    	List<Params> ret = new ArrayList<Params>();
    	Params outParam = new Params();

    	// 総在庫数(総引当可能数)
		outParam.set(TOTAL_STOCK_QTY, totalQty);
    	ret.add(outParam);

        return ret;

    }

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
                outParam.set(WORK_LIST_PRINT, webset[0].getValue());
            }
        }
        catch (Exception e)
        {
            // 6006042=画面定義情報の参照に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006042, e), getClass().getName());
        }

        return outParam;
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
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // オペレータパラメータ生成
        AsrsInParameter[] inParams = collectListData(ps);

        // 出庫引当て中フラグの更新
    	if (!doRetrievalAllocateStart())
        {
            return false;
        }
        doCommit(getClass());

        try
        {
            RetrievalAllocateOperator allocOpe = new RetrievalAllocateOperator(getConnection(), inParams[0], true, getClass());
            // 出庫開始日時
            Date retrievalStartDate = WmsFormatter.toDateTime(DbDateUtil.getSystemDateTime());

            // 引当処理を行う
            boolean noShortage = allocate(allocOpe, inParams, retrievalStartDate);

            // 6001006=設定しました。
            setMessage("6001006");

            if(_hasWork)
            {
	            // AS/RS作業の開始
	            if (!startAsRetrieval(ps[0].getBoolean(WORK_LIST_PRINT), ps))
	            {
	                // 搬送が開始できなかったらロールバック
	                doRollBack(getClass());
	                return false;
	            }
	            // 開始処理まで完了したところでコミット
	            doCommit(getClass());

                try
                {
                    // 出庫搬送情報とパレット情報の状態の不整合を補正します。トランザクションの切れ目を明確にするためスリープします。
                    Thread.sleep(100);
                    allocOpe.updatePalletState();
                    doCommit(getClass());
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

	            // 平置の作業リスト発行
	            if (ps[0].getBoolean(WORK_LIST_PRINT))
	            {
	            	if(!printFloorRetrievalWorkList(allocOpe.getSettingUkey(false)))
	            	{
	                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
	                    setMessage("6007042");
	            	}
	            }
            }
            // 欠品リスト発行はこのタイミングで行う
            if (!noShortage)
            {
                if (!_hasWork)
                {
                    // 6021037=全欠品のため、引当処理を行えませんでした。
                    setMessage("6021037");
                }
                else if (!noShortage)
                {
                    // 6021036=開始しました。（欠品があるため欠品情報照会を参照してください。）
                    setMessage("6021036");
                }

                if (ps[0].getBoolean(SHORTAGE_LIST_PRINT))
		        {
		            printShortageList(retrievalStartDate);
		        }
            }

            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(WORK_LIST_PRINT) ? WebSetting.KEYDATA_ON
                                                                 : WebSetting.KEYDATA_OFF;

            updateWebSetting(inParams[0].getTerminalNo(), ps[0].getString(FUNCTION_ID), value);
            doCommit(getClass());
            return true;
        }
        catch (RouteException e)
        {
            // rollback.
            doRollBack(getClass());
            // 搬送ルート異常
            setMessage(getRouteErrorMessage(e.getRouteStatus()));
            return false;
        }
        finally
        {
        	doRollBack(getClass());

            // 出庫引当て中フラグの更新
        	doRetrievalAllocateEnd();
            doCommit(getClass());
        }
    }

    /**
     * リストセルエリアのデータをパラメータとして受け取り、
     * チェックを行います。<BR>
     *
     * @param ps リストセルエリアのパラメータ
     * @return オンラインチェックがエラーでない場合はtrueを返す。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean check(ScheduleParams... ps)
            throws CommonException
    {
        // オペレータパラメータ生成
        AsrsInParameter[] inParams = new AsrsInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
        	inParams[i] = new AsrsInParameter(getWmsUserInfo());

            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            inParams[i].setAreaNo(ps[i].getString(AREA_NO));
            inParams[i].setLotNo(ps[i].getString(LOT_NO));
            inParams[i].setStationNo(ps[i].getString(STATION_NO));
            inParams[i].setRetrievalQty(ps[i].getInt(RETRIEVAL_RESULT_QTY));
        }

        RetrievalAllocateOperator allocOpe = new RetrievalAllocateOperator(getConnection(), inParams[0], true, getClass());

        // ASRSエリアが存在しないならチェックしない
        if (!allocOpe.hasAsrsArea())
        {
            return true;
        }

    	// 全ステーションのときはスルー
    	if (!WmsParam.ALL_STATION.equals(ps[0].getString(STATION_NO)))
    	{
    		// ステーションの出庫作業可能判定
            if (!retrievalStationCheck(ps[0].getString(STATION_NO), 0))
            {
                return false;
            }
    	}

        // 全てASRSエリアの場合
        if (allocOpe.isAllAsrsArea())
        {
            // オンラインの出庫可能な設備が存在しない場合
            if (!allocOpe.hasOnlineNormalStatusEquipment())
            {
                // 6023698=オンラインの出庫可能な設備が存在しません。
                setMessage("6023698");
                _confirmFlag = false;
                return false;
            }
            // オンラインの異常、切り離し、中断中の設備が存在する場合
            if (allocOpe.hasOnlineStatusErrorEquipment())
            {
                // MSG-W9111=出庫作業を行えない設備が存在します。よろしいですか？
                setDispMessage("MSG-W9111");
                _confirmFlag = true;
                return false;
            }
        }
        // 平置きとASRSが混合している場合
        else
        {
            if (allocOpe.hasOffLineAGC())
            {
                // オフラインのAGCが存在します。よろしいですか？
            	setDispMessage("MSG-W0037");
                _confirmFlag = true;
                return false;
            }
            // オンラインの異常、切り離し、中断中の設備が存在する場合
            if (allocOpe.hasOnlineStatusErrorEquipment())
            {
                // MSG-W9111=出庫作業を行えない設備が存在します。よろしいですか？
                setDispMessage("MSG-W9111");
                _confirmFlag = true;
                return false;
            }
        }
        return true;
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
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }
        // 引当中チェック
        if (isRetrievalAllocate())
        {
        	return false;
        }

        // 最大表示件数を超えないかのチェックを行う
        // 表示件数チェック
        if (ps != null && ps.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
        {
            // 6023019 = 件数が{0}件を超えるため、入力できません。
            setMessage(WmsMessageFormatter.format(6023019, MAX_NUMBER_OF_DISP_DISP));
            return false;
        }

        // マスタパッケージありの場合、入力された
        // 商品や出荷先がマスタに登録されているものかチェックする
        // システムコントローラよりマスタパッケージの有無を取得
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());
        if (systemController.hasMasterPack())
        {
            // 荷主コードが存在するかチェック
            ConsignorHandler consignorHandler = new ConsignorHandler(getConnection());
            ConsignorSearchKey consignorKey = new ConsignorSearchKey();

            // 荷主コード
            consignorKey.setConsignorCode(p.getString(CONSIGNOR_CODE));

            if (consignorHandler.count(consignorKey) <= 0)
            {
                // 6023040=荷主コードがマスタに登録されていません。
                setMessage("6023040");
                return false;
            }

            // 商品コードが存在するかチェック
            ItemHandler itemHandler = new ItemHandler(getConnection());
            ItemSearchKey itemKey = new ItemSearchKey();

            itemKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
            itemKey.setItemCode(p.getString(ITEM_CODE));

            if (itemHandler.count(itemKey) <= 0)
            {
                // 6023021=商品コードがマスタに登録されていません。
                setMessage("6023021");
                return false;
            }
        }

        //エリアにAS/RSが含まれる場合は、異常商品は入力できない。
        // オペレータパラメータ生成
        AsrsInParameter inParam = new AsrsInParameter(getWmsUserInfo());

        inParam.setConsignorCode(p.getString(CONSIGNOR_CODE));
        inParam.setItemCode(p.getString(ITEM_CODE));
        inParam.setAreaNo(p.getString(AREA_NO));
        inParam.setLotNo(p.getString(LOT_NO));
        inParam.setStationNo(p.getString(STATION_NO));
        inParam.setRetrievalQty(p.getInt(RETRIEVAL_RESULT_QTY));

        RetrievalAllocateOperator allocOpe = new RetrievalAllocateOperator(getConnection(), inParam, true, getClass());

        // ASRSエリアが存在しないならチェックしない
        if (allocOpe.hasAsrsArea())
        {
            //異常棚の入力チェック
            if (!checkIrregularItem(p.getString(ITEM_CODE)))
            {
                return false;
            }
        }

        // リストのデータを纏める
        AsrsInParameter[] inParams = collectListData(ps);
        int listRetQty = 0;

        for (AsrsInParameter param:inParams)
        {
        	if(!(param.getItemCode().equals(p.getString(ITEM_CODE))&&
        			param.getLotNo().equals(p.getString(LOT_NO))))
        	{
        		continue;
        	}
	        // 纏めてるので1回しかこないはずなので1回きたら終わり。
	        listRetQty = param.getRetrievalQty();
	        break;
        }

        // 作業数のチェックを行う
        if (listRetQty + p.getInt(RETRIEVAL_QTY) > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023285=出庫数には作業上限数{0}以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023285, MAX_TOTAL_QTY_DISP));
            return false;
        }

        // 入力エリアのデータに関してDBを検索してチェックするべき内容をチェックする
        List<Params> list = query(p);
        Params params = list.get(0);

        if(listRetQty + p.getInt(RETRIEVAL_QTY) > params.getInt(TOTAL_STOCK_QTY))
        {
        	// 6001029 =入力を受け付けました。(引当可能数を超えています。)
            setMessage(WmsMessageFormatter.format(6001029));
        }
        else
        {
	        // 6001019 =入力を受け付けました。
	        setMessage("6001019");
        }
        return true;
    }

    /**
     * ダイアログ表示するかを返します。
     *
     * @return ダイアログ表示を行うときtrue
     */
    public boolean isConfirm()
    {
    	return _confirmFlag;
    }

    /**
     * AS/RSの出庫開始を行ったかを返します。
     *
     * @return AS/RS出庫開始を行った場合、true
     */
    public boolean isStartAsrsWork()
    {
        return _startAsrsWork;
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
     * 平置きの検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected SearchKey createFloorSearchKey(ScheduleParams p)
    		throws ReadWriteException
    {
    	StockSearchKey searchKey = new StockSearchKey();

        // 検索条件をセットする。
        // 荷主コード
    	searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
    	searchKey.setItemCode(p.getString(ITEM_CODE));
    	// ロットNo.
    	if(!StringUtil.isBlank(p.getString(LOT_NO)))
    	{
    		searchKey.setLotNo(p.getString(LOT_NO));
    	}
    	// エリア
    	// 全エリアのときはセットしない。
    	if(!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
    	{
    		searchKey.setAreaNo(p.getString(AREA_NO));
    	}
    	searchKey.setKey(Stock.AREA_NO, Area.AREA_NO);
        searchKey.setKey(Area.AREA_TYPE, Area.AREA_TYPE_ASRS, "!=", "", "", true);

    	// パレットID 平置のときは空
    	searchKey.setPalletId("");

    	// 取得項目をセットする
    	// 総在庫数(総引当可能数)
    	searchKey.setAllocationQtyCollect("SUM");

        return searchKey;
    }

    /**
     * AS/RSの検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     * @throws InvalidDefineException
     */
    protected SearchKey createAsrsSearchKey(ScheduleParams p)
    		throws ReadWriteException, InvalidDefineException
    {
    	StockSearchKey searchKey = new StockSearchKey();

        // 検索条件をセットする。
        // 荷主コード
    	searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
    	searchKey.setItemCode(p.getString(ITEM_CODE));
    	// ロットNo.
    	if(!StringUtil.isBlank(p.getString(LOT_NO)))
    	{
    		searchKey.setLotNo(p.getString(LOT_NO));
    	}
    	// エリア
    	// 全エリアのときはセットしない。
    	if(!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
    	{
    		searchKey.setAreaNo(p.getString(AREA_NO));
    	}
    	searchKey.setKey(Stock.AREA_NO, Area.AREA_NO);
    	searchKey.setKey(Area.AREA_TYPE, Area.AREA_TYPE_ASRS);

    	// ステーション
    	// 全ステーションのときはセットしない。
    	if(!WmsParam.ALL_STATION.equals(p.getString(STATION_NO)))
    	{
            try
            {
                DfkUserInfo ui = getUserInfo();
                String term = (null == ui) ? ""
                                          : ui.getTerminalNumber();

                // 副問い合わせ用TerminalAreaの検索条件のセットをする。
                TerminalAreaSearchKey tKey = new TerminalAreaSearchKey();
                tKey.setStationNoCollect();
                tKey.setTerminalNo(term);

                Station st = StationFactory.makeStation(getConnection(), p.getString(STATION_NO));

                // 作業場の場合
                if (st instanceof WorkPlace)
                {
                    WorkPlace wp = (WorkPlace)st;
                    String[] stations = wp.getWPStations();
                    StationSearchKey stkey = new StationSearchKey();

                    // 作業場にあるステーションをセット
                    stkey.setKey(Station.STATION_NO, stations, true);

                    // ステーション種別は、子の字出庫か入出庫兼用
                    stkey.setClassName(FREE_RETRIEVAL_STATION_CLASSNAME, "=", "(", "", false);
                    stkey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);

                    // 副問い合わせ用Stationの検索条件をセットする。
                    stkey.setKey(Station.STATION_NO, tKey);

                    if(hasAisleCombined(stations))
                    {
                    	// アイル結合がある場合
                    	stkey.setWhStationNoCollect();
                    	searchKey.setKey(Shelf.WH_STATION_NO, stkey);
                    }
                    else
                    {
                    	// アイル独立しかない場合
                    	stkey.setAisleStationNoCollect();
                    	searchKey.setKey(Shelf.PARENT_STATION_NO, stkey);
                    }
                }
                // ステーションの場合
                else
                {
                	searchKey.setKey(Station.STATION_NO, p.getString(STATION_NO));
            		if(StringUtil.isBlank(st.getAisleStationNo()))
            		{
                		// アイル独立の場合
            			searchKey.setJoin(Shelf.WH_STATION_NO, Station.WH_STATION_NO);
            		}
            		else
            		{
                		// アイル結合の場合
            			searchKey.setJoin(Shelf.PARENT_STATION_NO, Station.AISLE_STATION_NO);
            		}
                }
            }
            catch (NotFoundException e)
            {
                // ステーションが見つからなかった場合
                throw new InvalidDefineException();
            }
    	}
    	// 棚状態が使用不可以外、アクセス不可以外
    	searchKey.setKey(Shelf.PROHIBITION_FLAG, Shelf.PROHIBITION_FLAG_NG, "!=", "", "", true);
    	searchKey.setKey(Shelf.ACCESS_NG_FLAG, Shelf.ACCESS_NG_FLAG_NG, "!=", "", "", true);

    	searchKey.setJoin(Shelf.STATION_NO, Pallet.CURRENT_STATION_NO);
		searchKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);

        if (!WmsParam.MULTI_ALLOCATE_FLAG)
        {
            searchKey.setKey(Pallet.STATUS_FLAG, Pallet.PALLET_STATUS_REGULAR);
        }


    	// 取得項目をセットする
    	// 総在庫数(総引当可能数)
    	searchKey.setAllocationQtyCollect("SUM");

        return searchKey;
    }

    /**
     * 引当処理を行います。<br>
     * 引当可能な分だけ引当を行います。<br>
     *
     * @param allocOpe 引当オペレータ
     * @param inParams リストセルエリアの行単位でのデータ
     * @param retrievalStartDate 出庫開始日時
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected boolean allocate(RetrievalAllocateOperator allocOpe, AsrsInParameter[] inParams, Date settingDate)
            throws CommonException
    {
        // 戻り値の初期化
        boolean retShortage = true;

    	WMSSequenceHandler seq = new WMSSequenceHandler(getConnection());

        String startUKey = seq.nextStartUnitKey();

        ShortageOperator shortOpe = new ShortageOperator(getConnection(), startUKey, settingDate, getClass());

        // ためうちの分だけループ
        for (int i = 0; i < inParams.length; i++)
		{
            int shortageQty = allocOpe.allocateStock(inParams[i]);

	        // 欠品ありの場合
	        if (shortageQty > 0)
	        {
                // 欠品情報を作成
	        	shortOpe.createItemRetrievalShortage(shortageQty, inParams[i]);
	        	// 戻り値の更新
	        	retShortage = false;
	        }
	        if (inParams[i].getRetrievalQty() != shortageQty)
	        {
	        	// 作業ありフラグON
	        	_hasWork = true;
	        }
		}

        // 搬送情報決定
        allocOpe.decideCarryInfo();

        // 平置きの作業をしていないときは空
        if (!allocOpe.getSettingUkey(false).equals(""))
        {
	        // WorkList作成
	        createWorkListByHostSend(allocOpe.getSettingUkey(false));
        }
        return retShortage;
    }

    /**
     * 指定されたステータスがオンラインかどうかを判定します。
     *
     * @param status 判定するステータス
     * @return オンラインの場合ture、それ以外false
     */
    protected boolean isOnLine(Object status)
    {
        if (GroupController.GC_STATUS_ONLINE.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたエリアがASRSエリアかどうかを判定します。
     *
     * @param areaNo 判定するエリアNo.
     * @return ASRSエリアの場合ture、それ以外false
     * @throws CommonException
     */
    protected boolean isAsrs(String areaNo) throws CommonException
    {
    	AreaController acon = new AreaController(getConnection(),getClass());

        if (SystemDefine.AREA_TYPE_ASRS.equals(acon.getAreaType(areaNo)))
        {
            return true;
        }
        return false;
    }

    /**
     * AS/RSの出庫開始処理とリスト発行を行います<br>
     * 設備状態により搬送を開始できなかった場合にfalseを返します。<br>
     *
     * @param isPrint 作業リストを発行の有無
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列
     * @throws CommonException 全ての例外を報告します
     * @return 搬送開始に失敗した場合false、それ以外はtrue。
     */
    protected boolean startAsRetrieval(boolean isPrint, ScheduleParams... ps)
            throws CommonException
    {
        WorkInfoFinder workfinder = null;

        try
        {
            workfinder = new WorkInfoFinder(getConnection());

            // ASRSの出庫情報を取得
            if (searchAsrsWorkInfo(workfinder, ps) == 0)
            {
                // AS/RS出庫開始作業が無い場合
                _startAsrsWork = false;

                // trueで処理を抜ける
                return true;
            }

            List<AsrsInParameter> paramlist = new ArrayList<AsrsInParameter>();
            while (workfinder.hasNext())
            {
                WorkInfo[] asworks = (WorkInfo[]) workfinder.getEntities(100);
                for (WorkInfo aswork : asworks)
                {
                    AsrsInParameter param = new AsrsInParameter(getWmsUserInfo());

                    // 荷主コード
                    param.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
                    // エリアNo.
                    param.setAreaNo(aswork.getPlanAreaNo());
                    // バッチNo.
                    param.setBatchNo(aswork.getBatchNo());
                    // オーダーNo.
                    param.setOrderNo(aswork.getOrderNo());
                    // 出庫予定日
                    param.setRetrievalDay(aswork.getPlanDay());
                    // ステーションNo.
                    param.setStationNo((String)aswork.getValue(CarryInfo.DEST_STATION_NO, ""));

                    param.setJobType(WorkInfo.JOB_TYPE_NOPLAN_RETRIEVAL);

                    // ステーションの出庫作業可能判定
                    if (!retrievalStationCheck(param.getStationNo(), 0))
                    {
                        return false;
                    }

                    paramlist.add(param);
                }
            }

            // オペレータパラメータ生成
            AsrsInParameter[] inParams = new AsrsInParameter[paramlist.size()];
            paramlist.toArray(inParams);

            // 印刷用の設定単位キーを保持するリスト
            List<AsrsOutParameter> outparamlist = new ArrayList<AsrsOutParameter>();

            // オペレータ生成
            AsrsOperator operator = new AsrsOperator(getConnection(), getClass());

            for (AsrsInParameter inparam : inParams)
            {
                // オペレータ呼び出し
                AsrsOutParameter outParam = operator.webStartRetrieval(inparam);
            	outparamlist.add(outParam);
            }

            if (isPrint && !outparamlist.isEmpty())
            {
                // 作業リスト印刷処理
                if (!printAsRetrievalWorkList(outparamlist))
                {
                    // 印刷に失敗した場合
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }
            }
            // AS/RS出庫開始を行ったことを記憶する。
            _startAsrsWork = true;

            return true;
        }
        finally
        {
            // finder close
            closeFinder(workfinder);
        }
    }

    /**
     * 作業情報からAS/RSの出庫作業を検索します。<br>
     *
     * @param finder <CODE>WorkInfoFinder</CODE>
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列
     * @throws CommonException
     */
    protected int searchAsrsWorkInfo(WorkInfoFinder finder, ScheduleParams... ps)
            throws CommonException
    {
        finder.open(true);

        // AsWorkInfoControllerのインスタンスを生成する。
        AsWorkInfoController asWorkInfo = new AsWorkInfoController(getConnection(), this.getClass());

        WorkInfo work = new WorkInfo();

        // 必須項目セット
        work.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
        // 作業区分に23:予定外出庫を追加
        work.setJobType(WorkInfo.JOB_TYPE_NOPLAN_RETRIEVAL);

        // AsWorkInfoControllerにて検索条件をセットしてもらう。
        WorkInfoSearchKey key = (WorkInfoSearchKey)asWorkInfo.getRetrievalWorkKey(work, null, null, null);
        // ハードウェア区分にAS/RSを追加
        key.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS);

        // 検索実行
        return finder.search(key);
    }

    /**
     * 平置出庫作業リストを発行します。<br>
     *
     * @param paramlist 出庫出力パラメータのリスト
     * @return 印刷成功:true 印刷失敗:false
     */
    protected boolean printFloorRetrievalWorkList(String ukey)
    {
        // get locale.
        DfkUserInfo ui = getUserInfo();
        Locale locale = getLocale();

        FaRetrievalListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            dasch = new FaRetrievalListDASCH(getConnection(), getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaRetrievalListDASCHParams inparam = new FaRetrievalListDASCHParams();

            // 発行条件をセット
            List<String> ukeys = new ArrayList<String>();
            ukeys.add(ukey);
            inparam.set(FaRetrievalListDASCHParams.SETTING_UNIT_KEY, ukeys);
            inparam.set(FaRetrievalListDASCHParams.WORK_TYPE, RetrievalInParameter.SEARCH_FLOOR_RETRIEVAL_LIST);

            // check count.
            if(dasch.count(inparam) == 0)
            {
            	return true;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("RetrievalWorkList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                RetrievalWorkListParams expparam = new RetrievalWorkListParams();
                expparam.set(RetrievalWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListDASCHParams.DFK_DS_NO));
                expparam.set(RetrievalWorkListParams.DFK_USER_ID, outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                expparam.set(RetrievalWorkListParams.DFK_USER_NAME, outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                expparam.set(RetrievalWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                expparam.set(RetrievalWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                expparam.set(RetrievalWorkListParams.LIST_NO, outparam.get(FaRetrievalListDASCHParams.SETTING_UNIT_KEY));
                expparam.set(RetrievalWorkListParams.AREA_NO, outparam.get(FaRetrievalListDASCHParams.AREA_NO));
                expparam.set(RetrievalWorkListParams.LOCATION_NO, outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                expparam.set(RetrievalWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                expparam.set(RetrievalWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                expparam.set(RetrievalWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                expparam.set(RetrievalWorkListParams.WORK_QTY, outparam.get(FaRetrievalListDASCHParams.WORK_QTY));
                expparam.set(RetrievalWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
                expparam.set(RetrievalWorkListParams.BATCH_NO, outparam.get(FaRetrievalListDASCHParams.BATCH_NO));
                expparam.set(RetrievalWorkListParams.TICKET_NO, outparam.get(FaRetrievalListDASCHParams.TICKET_NO));
                // 予定外では行No.0になってしまうためセットしない。
                //expparam.set(RetrievalWorkListParams.LINE_NO, outparam.get(FaRetrievalListDASCHParams.LINE_NO));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            if (dasch != null && !StringUtil.isBlank(dasch.getMessage()))
            {
                setMessage(dasch.getMessage());
            }
            return false;
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
        }

        return true;
    }

    /**
     * 出庫作業リストを発行します。<br>
     *
     * @param paramlist 出庫出力パラメータのリスト
     * @return 印刷成功:true 印刷失敗:false
     */
    protected boolean printAsRetrievalWorkList(List<AsrsOutParameter> paramlist)
    {
        // get locale.
        DfkUserInfo ui = getUserInfo();
        Locale locale = getLocale();

        FaRetrievalListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            AsrsOutParameter[] params = new AsrsOutParameter[paramlist.size()];
            paramlist.toArray(params);

            dasch = new FaRetrievalListDASCH(getConnection(), getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaRetrievalListDASCHParams inparam = new FaRetrievalListDASCHParams();

            // 発行条件をセット
            List<String> ukeys = new ArrayList<String>();
            for (AsrsOutParameter param : params)
            {
                ukeys.add(param.getSettingUnitKey());
            }
            inparam.set(FaRetrievalListDASCHParams.SETTING_UNIT_KEY, ukeys);
            inparam.set(FaRetrievalListDASCHParams.WORK_TYPE, RetrievalInParameter.SEARCH_ASRS_RETRIEVAL_LIST);

            // check count.
            if(dasch.count(inparam) == 0)
            {
            	return true;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("AsRetrievalWorkList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                AsRetrievalWorkListParams expparam = new AsRetrievalWorkListParams();
                expparam.set(AsRetrievalWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListDASCHParams.DFK_DS_NO));
                expparam.set(AsRetrievalWorkListParams.DFK_USER_ID, outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                expparam.set(AsRetrievalWorkListParams.DFK_USER_NAME, outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                expparam.set(AsRetrievalWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                expparam.set(AsRetrievalWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                expparam.set(AsRetrievalWorkListParams.STATION_NO, outparam.get(FaRetrievalListDASCHParams.STATION_NO));
                expparam.set(AsRetrievalWorkListParams.STATION_NAME, outparam.get(FaRetrievalListDASCHParams.STATION_NAME));
                expparam.set(AsRetrievalWorkListParams.WORK_NO, outparam.get(FaRetrievalListDASCHParams.WORK_NO));
                expparam.set(AsRetrievalWorkListParams.LOCATION_NO, outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                expparam.set(AsRetrievalWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                expparam.set(AsRetrievalWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                expparam.set(AsRetrievalWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                expparam.set(AsRetrievalWorkListParams.RETRIEVAL_COMMAND_DETAIL, outparam.get(FaRetrievalListDASCHParams.RETRIEVAL_COMMAND_DETAIL));
                expparam.set(AsRetrievalWorkListParams.PRIORITY_FLAG, outparam.get(FaRetrievalListDASCHParams.PRIORITY_FLAG));
                expparam.set(AsRetrievalWorkListParams.WORK_QTY, outparam.get(FaRetrievalListDASCHParams.WORK_QTY));
                expparam.set(AsRetrievalWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
                expparam.set(AsRetrievalWorkListParams.BATCH_NO, outparam.get(FaRetrievalListDASCHParams.BATCH_NO));
                expparam.set(AsRetrievalWorkListParams.TICKET_NO, outparam.get(FaRetrievalListDASCHParams.TICKET_NO));
                expparam.set(AsRetrievalWorkListParams.AREA_NO, outparam.get(FaRetrievalListDASCHParams.AREA_NO));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return false;
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            if (dasch != null && !StringUtil.isBlank(dasch.getMessage()))
            {
                setMessage(dasch.getMessage());
            }
            return false;
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
        }

        return true;
    }

    /**
     * 欠品リストを発行します。<br>
     *
     * @param settingDate 出庫開始日時
     * @return 印刷成功:true 印刷失敗:false
     */
    protected boolean printShortageList(Date settingDate)
    {
        // get locale.
        DfkUserInfo ui = getUserInfo();
        Locale locale = getLocale();

        FaRetrievalShortageInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            dasch = new FaRetrievalShortageInquiryDASCH(getConnection(), getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaRetrievalListDASCHParams inparam = new FaRetrievalListDASCHParams();
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_DAY, settingDate);
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_TIME, settingDate);

            // check count.
            if(dasch.count(inparam) == 0)
            {
            	return true;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("ShortageList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ShortageListParams expparam = new ShortageListParams();
                expparam.set(ShortageListParams.DFK_DS_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ShortageListParams.DFK_USER_ID, outparam.get(FaRetrievalShortageInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ShortageListParams.DFK_USER_NAME, outparam.get(FaRetrievalShortageInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ShortageListParams.SYS_DAY, outparam.get(FaRetrievalShortageInquiryDASCHParams.SYS_DAY));
                expparam.set(ShortageListParams.SYS_TIME, outparam.get(FaRetrievalShortageInquiryDASCHParams.SYS_TIME));
                expparam.set(ShortageListParams.SETTING_DAY, outparam.get(FaRetrievalShortageInquiryDASCHParams.SETTING_DAY));
                expparam.set(ShortageListParams.SETTING_TIME, outparam.get(FaRetrievalShortageInquiryDASCHParams.SETTING_TIME));
                expparam.set(ShortageListParams.ITEM_CODE, outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_CODE));
                expparam.set(ShortageListParams.ITEM_NAME, outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_NAME));
                expparam.set(ShortageListParams.LOT_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.LOT_NO));
                expparam.set(ShortageListParams.PLAN_QTY, outparam.get(FaRetrievalShortageInquiryDASCHParams.PLAN_QTY));
                expparam.set(ShortageListParams.SHORTAGE_QTY, outparam.get(FaRetrievalShortageInquiryDASCHParams.SHORTAGE_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            if (dasch != null && !StringUtil.isBlank(dasch.getMessage()))
            {
                setMessage(dasch.getMessage());
            }
            return false;
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
        }

        return true;
    }

    /**
     * 設定単位キーを元に、入出庫実績送信情報の検索を行い、作業リスト情報を作成します。<br>
     * <ul>
     * 以下のDB情報を検索します。
     * <li>入出庫実績送信情報
     * </ul>
     *
     * @param key 設定単位キー
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void createWorkListByHostSend(String key)
            throws CommonException
    {
        HostSendFinder hsf = null;
        HostSendSearchKey hkey = new HostSendSearchKey();

        // 取得項目一覧の作成
        FieldName[] collects = {
            HostSend.JOB_NO,
            HostSend.SETTING_UNIT_KEY,
            HostSend.COLLECT_JOB_NO,
            HostSend.JOB_TYPE,
            HostSend.PLAN_UKEY,
            HostSend.STOCK_ID,
            HostSend.PLAN_DAY,
            HostSend.CONSIGNOR_CODE,
            HostSend.CONSIGNOR_NAME,
            HostSend.CUSTOMER_CODE,
            HostSend.CUSTOMER_NAME,
            HostSend.SHIP_TICKET_NO,
            HostSend.SHIP_LINE_NO,
            HostSend.SHIP_BRANCH_NO,
            HostSend.BATCH_NO,
            HostSend.ORDER_NO,
            HostSend.PLAN_AREA_NO,
            HostSend.PLAN_LOCATION_NO,
            HostSend.ITEM_CODE,
            HostSend.ITEM_NAME,
            HostSend.JAN,
            HostSend.ITF,
            HostSend.BUNDLE_ITF,
            HostSend.ENTERING_QTY,
            HostSend.BUNDLE_ENTERING_QTY,
            HostSend.PLAN_LOT_NO,
            HostSend.PLAN_QTY,
            HostSend.REASON_TYPE,
            HostSend.REASON_NAME,
            HostSend.USER_ID,
            HostSend.USER_NAME,
            HostSend.TERMINAL_NO,
        };

        // 取得フィールドのセット
        for (FieldName fld : collects)
        {
        	hkey.setCollect(fld);
        }

        // 検索条件のセット
        hkey.setSettingUnitKey(key);

        // ソート
        hkey.setStockIdOrder(true);
        hkey.setJobNoOrder(true);

        try
        {
	        // 検索実行
	        hsf = new HostSendFinder(getConnection());
	        hsf.open(true);
	        hsf.search(hkey);

	        while(hsf.hasNext())
	        {
		        Entity[] readEnts = hsf.getEntities(100);
		        if (null == readEnts || readEnts.length == 0)
		        {
		            // 見つからなかった場合は、エラー
		            throw new ScheduleException();
		        }

		        WorkListHandler wlh = new WorkListHandler(getConnection());
		        String cname = getClass().getSimpleName();

		        String stock_id = "";
		        int stock_qty = 0;
		        int alloc_qty = 0;
		        int work_qty = 0;
		        for (Entity readEnt : readEnts)
		        {
		            WorkList wlEnt = new WorkList();
		            wlEnt.setValueMap(readEnt.getValueMap());

		            // 出庫、予定外出庫の場合
		            if (StringUtil.isBlank(stock_id) || !stock_id.equals(wlEnt.getStockId()))
		            {
		                stock_id = wlEnt.getStockId();

		                Stock stk = getStock(stock_id);
		                int total_qty = getTotalResultQty(key, wlEnt.getStockId());
		                if (stk == null)
		                {
		                    // 平置き作業の場合、在庫がなくなっていたら全数出庫
		                    stock_qty = total_qty;
		                    // 作業前の引当可能数
		                    alloc_qty = total_qty;
		                }
		                else
		                {
		                    stock_qty = stk.getStockQty() + total_qty;
		                    // 作業前の引当可能数
		                    alloc_qty = total_qty + stk.getAllocationQty();
		                }
		            }
		            else
		            {
		                // 引当可能数の減算
		                alloc_qty -= work_qty;
		            }

		            work_qty = wlEnt.getPlanQty();

		            // 在庫数、引当可能数のセット
		            wlEnt.setAllocationQty(alloc_qty);
		            wlEnt.setStockQty(stock_qty);

		            wlEnt.setRegistDate(new SysDate());
		            wlEnt.setRegistPname(cname);

		            // insert
		            wlh.create(wlEnt);
		        }
	        }
        }
        finally
        {
        	closeFinder(hsf);
        }
    }

    /**
     * 在庫IDから在庫情報を取得します。<br>
     * 対象データが無い場合はnullを返します。
     *
     * @param stock_id
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected Stock getStock(String stock_id)
            throws ReadWriteException
    {
        StockSearchKey skey = new StockSearchKey();

        skey.setStockId(stock_id);

        Stock[] stk = (Stock[])(new StockHandler(getConnection())).find(skey);

        if (stk == null || stk.length == 0)
        {
            return null;
        }

        return stk[0];
    }

    /**
     * 同一在庫を対象とした作業の合計実績数を取得します。
     *
     * @param setting_ukey
     * @param stock_id
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected int getTotalResultQty(String setting_ukey, String stock_id)
            throws ReadWriteException
    {
        HostSendSearchKey hkey = new HostSendSearchKey();

        hkey.setSettingUnitKey(setting_ukey);
        hkey.setStockId(stock_id);
        hkey.setResultQtyCollect("SUM");

        HostSend[] hostsend = (HostSend[])(new HostSendHandler(getConnection())).find(hkey);

        return hostsend[0].getResultQty();
    }

    /**
     * 画面定義情報を更新します。<br>
     * 更新処理で異常が発生した場合は、Exceptionをスローせず、ロギングのみ行います。
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


    /**
     * リストセルのデータを商品コード＋ロットNo.で一意に纏めます。
     *
     * @param ps リストセルデータ
     */
    protected AsrsInParameter[] collectListData(ScheduleParams... ps)
    {
        // 同じ商品コード、ロットのものをまとめる。
        List<AsrsInParameter> list = new ArrayList<AsrsInParameter>();

        for (int i = 0; i < ps.length; i++)
        {
        	AsrsInParameter param = new AsrsInParameter(getWmsUserInfo());
        	boolean add = true;

        	param.setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            param.setItemCode(ps[i].getString(ITEM_CODE));
            param.setAreaNo(ps[i].getString(AREA_NO));
            param.setLotNo(ps[i].getString(LOT_NO));
            param.setStationNo(ps[i].getString(STATION_NO));
            param.setRetrievalQty(ps[i].getInt(RETRIEVAL_RESULT_QTY));
            param.setPriorityType(ps[i].getString(TYPE));

            for (int j = 0; j < list.size(); j++)
            {
            	if(list.get(j).getItemCode().equals(param.getItemCode()) &&
            			list.get(j).getLotNo().equals(param.getLotNo()))
            	{
            		param.setRetrievalQty(list.get(j).getRetrievalQty() + param.getRetrievalQty());
            		list.set(j, param);
            		add = false;
            		break;
            	}
            }
            if (add)
            {
            	list.add(param);
            }
        }

        AsrsInParameter[] inParams = new AsrsInParameter[list.size()];

        list.toArray(inParams);

        return inParams;
    }

    /**
     * 指定されたステーションに結合倉庫が存在するかを返します。
     *
     * @param wp ステーション
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected boolean hasAisleCombined(String[] stations)
    		throws ReadWriteException
    {
    	StationHandler sthandler = new StationHandler(getConnection());
    	StationSearchKey stkey = new StationSearchKey();

    	stkey.setStationNo(stations, true);
    	stkey.setAisleStationNo("");

    	if (sthandler.count(stkey) > 0)
    	{
    		return true;
    	}
    	return false;
    }

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
