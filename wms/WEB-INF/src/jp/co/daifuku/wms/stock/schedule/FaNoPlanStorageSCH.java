// $Id: FaNoPlanStorageSCH.java 88 2009-11-25 03:14:15Z s.yoshida@technogear.co.jp $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.FUNCTION_ID;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.PRINT_FLAG;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.AREA_NO;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.CONSIGNOR_CODE;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.ITEM_CODE;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.ITEM_NAME;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.LOCATION_NO;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.LOT_NO;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.LST_ITEM_CODE;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.LST_LOCATION_NO;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.LST_LOT_NO;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.SETTING_UKEYS;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.STOCK_QTY;
import static jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams.STORAGE_QTY;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.stock.operator.NoPlanWorkOperator;

/**
 * 平置予定外入庫作業のスケジュール処理を行います。
 *
 * @version $Revision: 88 $, $Date:: 2009-11-25 12:14:15 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: s.yoshida@technogear.co.jp $
 */
public class FaNoPlanStorageSCH
        extends AbstractSCH
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
    public FaNoPlanStorageSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
    	
    	Stock[] stocks = (Stock[])handler.find(createSearchKey(p));
    	
    	List<Params> ret = new ArrayList<Params>();
    	Params outParam = new Params();

    	if(stocks.length == 0)
    	{
        	outParam.set(STOCK_QTY, 0);
    	}
    	else
    	{
    		outParam.set(STOCK_QTY, stocks[0].getStockQty());
    	}
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

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param startParams 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
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
            StockInParameter[] inParams = new StockInParameter[ps.length];
            for (int i = 0; i < inParams.length; i++)
            {
                inParams[i] = new StockInParameter(getWmsUserInfo());

                // 荷主コード
                inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
                // リストセル行No.
                inParams[i].setRowNo(ps[i].getRowIndex());
                // エリアNo.
                inParams[i].setAreaNo(ps[i].getString(AREA_NO));
                // 棚No.
                inParams[i].setLocation(ps[i].getString(LOCATION_NO));
                // 商品コード
                inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
                // 商品名称
                inParams[i].setItemName(ps[i].getString(ITEM_NAME));
                // ロットNo.
                inParams[i].setLotNo(ps[i].getString(LOT_NO));
                inParams[i].setResultLotNo(ps[i].getString(LOT_NO));
                // 入庫数
                inParams[i].setResultQty(ps[i].getInt(STORAGE_QTY));
            }

            try
            {
                // オペレータ生成
                NoPlanWorkOperator operator = new NoPlanWorkOperator(getConnection(), getClass());
                // オペレータ呼び出し
                StockOutParameter outParam = operator.completeStorage(inParams);

                ps[0].set(SETTING_UKEYS, outParam.getSettingUnitKey());

                // 6001006=設定しました。
                setMessage(WmsMessageFormatter.format(6001006));

                // 画面定義情報を更新
                String value = ps[0].getBoolean(PRINT_FLAG) ? WebSetting.KEYDATA_ON
                                                            : WebSetting.KEYDATA_OFF;
                
                updateWebSetting(inParams[0].getTerminalNo(), ps[0].getString(FUNCTION_ID), value);

                return true;

            }
            catch (LockTimeOutException e)
            {
                // 6027008=このデータは他の端末で更新中のため、処理できません。
                setMessage(WmsMessageFormatter.format(6027008));
                return false;
            }
            catch (OperatorException e)
            {
                // Operatorからの例外をcatchし、該当するエラーメッセージを表示する

                // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
                if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                        || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                        || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
                {
                    // No. {0} 他端末で処理されたため、処理を中断しました。
                    setMessage(WmsMessageFormatter.format(6023015, inParams[e.getErrorLineNo() - 1].getRowNo()));
                    setErrorRowIndex(inParams[e.getErrorLineNo() - 1].getRowNo());
                    return false;
                }
                else if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
                {
                	long qty = Long.valueOf(e.getDetailString());
                    if(qty <= 0)
                    {
                    	// 6023291=No.{0} 入庫先の在庫数が在庫上限数を超えるため、入庫できません。
                    	setMessage(WmsMessageFormatter.format(6023291, inParams[e.getErrorLineNo() - 1].getRowNo()));
                    }
                    else
                    {
	                	// 6023183=No.{0} 入庫先の在庫数が在庫上限数を超えるため、入庫できません。{1}以下の値を入力してください。
	                    setMessage(WmsMessageFormatter.format(6023183, inParams[e.getErrorLineNo() - 1].getRowNo(),WmsFormatter.getNumFormat(qty)));
                    }
                    setErrorRowIndex(inParams[e.getErrorLineNo() - 1].getRowNo());
                    return false;
                }
                // 上記以外は例外をそのまま投げる
                throw e;
            }
        }

    /**
     * パラメータの内容を元に、登録を行う棚をチェックします。<BR>
     * 棚マスタ管理で棚マスタに登録されていない棚に作成する場合または
     * 固定棚管理で商品・棚が一致しない場合にExceptionをthrowします。<BR>
     * <BR>
     * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力内容が正常な場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        // 棚マスタコントローラで棚チェック
        LocateController locateCtrl = new LocateController(getConnection(), getClass());

        Stock stock = new Stock();

        // エリアNo.
        stock.setAreaNo(p.getString(AREA_NO));
        // 棚No.
        stock.setLocationNo(p.getString(LOCATION_NO));
        // 荷主コード
        stock.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        stock.setItemCode(p.getString(ITEM_CODE));

        try
        {

            // 6001019=入力を受け付けました。
            setMessage("6001019");

            locateCtrl.checkStorageLocate(stock);

            return true;
        }
        catch (OperatorException e)
        {
            // MSG-W0016=登録棚ではありません。よろしいですか？
            setDispMessage("MSG-W0016");
            return false;
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
        // 日次更新チェック
        if (!canStart())
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

        // 数量チェックを行う
        // 作業数のチェックを行う
        if (p.getInt(STORAGE_QTY) > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023188=入庫数には作業上限数{0}以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023188, MAX_TOTAL_QTY_DISP));
            return false;
        }

        // 在庫数チェック
        if (p.getInt(STORAGE_QTY) > WmsParam.MAX_STOCK_QTY)
        {
            // 6023217=入庫数には在庫上限数{0}以下の値を入力してしてください。
            setMessage(WmsMessageFormatter.format(6023217, MAX_STOCK_QTY_DISP));
            return false;
        }

        // 入力エリアのデータに関してDBを検索してチェックするべき内容をチェックする
        List<Params> list = query(p);
        if(list.size() != 0)
        {
        	Params param = list.get(0);
        	if(WmsParam.MAX_STOCK_QTY <= param.getInt(STOCK_QTY))
        	{
        		// 6023290=入庫先の在庫数が在庫上限数を超えるため、入庫できません。
        		setMessage("6023290");
        		return false;
        	}
        }

        // リストセルエリアとのチェックを行う

        // ためうちエリアとの重複チェック
        if (ps != null)
        {
            for (ScheduleParams schParam : ps)
            {
                if (p.getString(ITEM_CODE).equals(schParam.getString(LST_ITEM_CODE))
                        && p.getString(LOCATION_NO).equals(schParam.getString(LST_LOCATION_NO))
                        && p.getString(LOT_NO).equals(schParam.getString(LST_LOT_NO)))
                {
                    // 6023020 = 既に同一データが存在するため、入力できません。
                    setMessage("6023020");
                    return false;
                }
            }
        }

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
     * @return SearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
    	StockSearchKey searchKey = new StockSearchKey();

        // 検索条件をセットする。
        // 荷主コード
    	searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
    	searchKey.setItemCode(p.getString(ITEM_CODE));
    	// ロットNo.
    	searchKey.setLotNo(p.getString(LOT_NO));
    	// エリア
    	searchKey.setAreaNo(p.getString(AREA_NO));
    	// 棚
    	searchKey.setLocationNo(p.getString(LOCATION_NO));
    	
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
        Stock[] entities = (Stock[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (Stock ent : entities)
        {
            Params param = new Params();

            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            // 在庫数
            param.set(STOCK_QTY, ent.getStockQty());
            
            result.add(param);
        }

        return result;
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
