// $Id: AsNoPlanRetrievalSCH.java 7678 2010-03-18 02:30:14Z kishimoto $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsNoPlanRetrievalSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * AS/RS 予定外出庫設定のスケジュール処理を行います。
 *
 * @version $Revision: 7678 $, $Date: 2010-03-18 11:30:14 +0900 (木, 18 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class AsNoPlanRetrievalSCH
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
    public AsNoPlanRetrievalSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
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
                outParam.set(L_ISSUE_REPORT, webset[0].getValue());
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
            finder = new StockFinder(getConnection());
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
        // オペレータパラメータ生成
        AsrsInParameter[] inParams = new AsrsInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new AsrsInParameter(getWmsUserInfo());
            
            inParams[i].setStationNo(ps[i].getString(STATION));
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setAreaNo(ps[i].getString(AREA_NO));
            inParams[i].setLocation(ps[i].getString(LOCATION_NO));
            inParams[i].setPlanLotNo(ps[i].getString(LOT_NO));
            inParams[i].setLotNo(ps[i].getString(RESULT_LOT));
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            inParams[i].setEnteringQty(ps[i].getInt(ENTERING_QTY));
            inParams[i].setRetrievalCaseQty(ps[i].getInt(PLAN_CASE_QTY));
            inParams[i].setRetrievalPieceQty(ps[i].getInt(PLAN_PIECE_QTY));
            inParams[i].setAllocCaseQty(ps[i].getInt(ALLOC_CASE_QTY));
            inParams[i].setAllocPieceQty(ps[i].getInt(ALLOC_PIECE_QTY));
            inParams[i].setRowNo(ps[i].getRowIndex());
            inParams[i].setAllQtyFlag(ps[i].getBoolean(ALL));
            inParams[i].setStockId(ps[i].getString(STOCK_ID));
            inParams[i].setPalletId(ps[i].getString(PALLET_ID));
            inParams[i].setReasonType(ps[i].getInt(REASON));
            
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

        // ためうちの入力チェック
        if (!checkList(inParams))
        {
            return false;
        }

        // 棚のチェック
        if (!checkShelf(inParams))
        {
            return false;
        }
        
        // ステーションの出庫作業可能判定
        for (AsrsInParameter inParam : inParams)
        {
            // ステーションの出庫作業可能判定
            if (!retrievalStationCheck(inParam.getStationNo(), inParam.getRowNo()))
            {
                setErrorRowIndex(inParam.getRowNo());
                return false;
            }
        }

        AsrsOperator operator = new AsrsOperator(getConnection(), getClass());

        try
        {
            // ASRS予定外出庫設定
            AsrsOutParameter outParam = operator.webStartNoPlanRetrieval(inParams);
            
            // Businessの印刷にて使用するために引数のパラメータオブジェクトに上書きする。
            ps[0].set(SETTING_UKEY, outParam.getSettingUnitKey());

            // 6001006=設定しました。
            setMessage("6001006");
            
            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(L_ISSUE_REPORT) ? WebSetting.KEYDATA_ON
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
        catch (OperatorException e)
        {
            // オペレータ内でループインデックス+1が設定されるため-1する
            int index = e.getErrorLineNo() - 1;

            // 「他端末で更新済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode()))
            {
                // No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParams[index].getRowNo()));
                setErrorRowIndex(inParams[index].getRowNo());
                return false;
            }
            // 搬送ルートエラー
            else if (OperatorException.ERR_ROUTE.equals(e.getErrorCode()))
            {
                // 6023119=No.{0} {1}
                setMessage(WmsMessageFormatter.format(6023119, inParams[index].getRowNo(),
                        MessageResource.getMessage(getRouteErrorMessage(e.getRouteStatus()))));
                setErrorRowIndex(inParams[index].getRowNo());
                return false;
            }
            // 出庫可能数不足エラー
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023189=No.{0} 出庫数には引当可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023189, inParams[index].getRowNo()));
                setErrorRowIndex(inParams[index].getRowNo());
                return false;
            }
            // すでにパレットがほかで引当済
            else if (OperatorException.ERR_ALREADY_ALLOCATED.equals(e.getErrorCode()))
            {
                // 6023119=No.{0} {1}
                // 6023070=指定された棚は現在引当中です。
                setMessage(WmsMessageFormatter.format(6023119, inParams[index].getRowNo(),
                        MessageResource.getMessage("6023070")));
                setErrorRowIndex(inParams[index].getRowNo());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
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
    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        StockSearchKey searchKey = new StockSearchKey();

        /* 検索条件の指定 */

        // 商品コード
        if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
        {
            searchKey.setItemCode(p.getString(ITEM_CODE));
        }
        // ロットNo.
        if (!StringUtil.isBlank(p.getString(LOT_NO)))
        {
            searchKey.setLotNo(p.getString(LOT_NO));
        }
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            searchKey.setAreaNo(p.getString(AREA_NO));
        }
        
        String[] loc = WmsFormatter.getFromTo(p.getString(FROM_LOCATION), p.getString(TO_LOCATION));
        
        // 棚(From)
        if (!StringUtil.isBlank(loc[0]))
        {
            searchKey.setLocationNo(loc[0], ">=");
        }
        // 棚(To)
        if (!StringUtil.isBlank(loc[1]))
        {
            searchKey.setLocationNo(loc[1], "<=");
        }
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 在庫数が0より大きい数量であること
        searchKey.setStockQty(0, ">");
        // 引当可能数が0より大きい数量であること
        searchKey.setAllocationQty(0, ">");

        ShelfSearchKey shelfKey = new ShelfSearchKey();

        // アクセス不可棚フラグ(アクセス可)
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        // 状態(使用可)
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);

        searchKey.setKey(shelfKey);

        /* 結合条件の指定 */

        // 荷主コード
        searchKey.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        searchKey.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        // パレットID
        searchKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
        // 棚No.
        searchKey.setJoin(Pallet.CURRENT_STATION_NO, Shelf.STATION_NO);

        /* 取得項目と集約条件の指定 */

        // ロットNo.
        searchKey.setLotNoCollect();
        // 入庫時日時
        searchKey.setStorageDateCollect();
        // エリア
        searchKey.setAreaNoCollect();
        // 棚
        searchKey.setLocationNoCollect();
        // ケース入数
        searchKey.setCollect(Item.ENTERING_QTY);
        // 出庫可能数
        searchKey.setAllocationQtyCollect();
        // JANコード
        searchKey.setCollect(Item.JAN);
        // ITF
        searchKey.setCollect(Item.ITF);
        // 商品コード
        searchKey.setItemCodeCollect();
        // 商品名称
        searchKey.setCollect(Item.ITEM_NAME);
        // 在庫ID
        searchKey.setStockIdCollect();
        // パレットID
        searchKey.setPalletIdCollect();

        /* ソート順の指定 */

        // ロットNo.
        searchKey.setLotNoOrder(true);
        // 入庫日時
        searchKey.setStorageDateOrder(true);
        // 棚
        searchKey.setLocationNoOrder(true);

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
            
            // ロットNo.
            param.set(LOT_NO, ent.getLotNo());
            // 入庫日時
            param.set(STORAGE_DATE, ent.getStorageDate());
            // 出庫エリア
            param.set(AREA_NO, ent.getAreaNo());
            // 出庫棚
            param.set(LOCATION_NO, ent.getLocationNo());
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            param.set(ENTERING_QTY, enteringQty);
            // 引当可能ケース数
            param.set(ALLOC_CASE_QTY, DisplayUtil.getCaseQty(ent.getAllocationQty(), enteringQty));
            // 引当可能ピース数
            param.set(ALLOC_PIECE_QTY, DisplayUtil.getPieceQty(ent.getAllocationQty(), enteringQty));
            // JANコード
            param.set(JAN, (String)ent.getValue(Item.JAN, ""));
            // ケースITF
            param.set(ITF, (String)ent.getValue(Item.ITF, ""));
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称(リストセルエリア表示用)
            param.set(ITEM_NAME, (String)ent.getValue(Item.ITEM_NAME, ""));
            // 在庫ID
            param.set(STOCK_ID, ent.getStockId());
            // パレットID
            param.set(PALLET_ID, ent.getPalletId());

            result.add(param);
            
        }

        return result;
    }
    
    /**
     * ためうち入力チェックを行います。<BR>
     * @param  checkParams ASRS入力パラメータ
     * @return チェックOK : true チェックNG : false
     */
    protected boolean checkList(AsrsInParameter[] checkParams)
    {
        long allocateQty = 0;
        long retrievalQty = 0;
        for (int i = 0; i < checkParams.length; i++)
        {
            AsrsInParameter param = (AsrsInParameter)checkParams[i];

            if (param.isAllQtyFlag())
            {
                long temp = ((long)param.getEnteringQty() * (long)param.getRetrievalCaseQty())
                + (long)param.getRetrievalPieceQty();
                // 全数にチェックがあって かつ
                // 出庫数に入力がある場合エラー
                if (temp != 0)
                {
                    // No.{0} 全数を選択する場合は、出庫数は入力できません。
                    setMessage(WmsMessageFormatter.format(6023145, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
            }

            if (param.isAllQtyFlag())
            {
                param.setRetrievalCaseQty(param.getAllocCaseQty());
                param.setRetrievalPieceQty(param.getAllocPieceQty());
            }

            // 引当可能数の算出
            // 引当可能ケース数 * ケース入数 + 引当可能ピース数
            allocateQty =
                    ((long)param.getEnteringQty() * (long)param.getAllocCaseQty())
                            + (long)param.getAllocPieceQty();
            // 出庫数の算出
            // 出庫ケース数 * ケース入数 + 出庫ピース数
            retrievalQty =
                    ((long)param.getEnteringQty() * (long)param.getRetrievalCaseQty())
                            + (long)param.getRetrievalPieceQty();

            // 引当可能数が0の場合
            if (allocateQty == 0)
            {
                // 6023061=No.{0} 引当可能数が0の場合は、作業できません。
                setMessage(WmsMessageFormatter.format(6023061, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }

            // 出庫数が0の場合
            if (retrievalQty <= 0)
            {
                // 6023046=No.{0} ケース数またはピース数には1以上の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023046, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }
            // 出庫数が1以上設定の場合
            else
            {
                // 出庫数が在庫数の最大値より大きい場合
                if (retrievalQty > (long)WmsParam.MAX_STOCK_QTY)
                {
                    // 6023207=No.{0} 出庫数には作業上限数{1}以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023207, param.getRowNo(),
                            WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY)));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }

                // 出庫数（入力データ）が引当可能数より大きい
                if (retrievalQty > allocateQty)
                {
                    // 6023189=No.{0} 出庫数には引当可能数以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023189, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
                else
                {
                    param.setAllocateQty(allocateQty);
                    param.setRetrievalQty((int)retrievalQty);
                }
            }
        }
        return true;
    }

    /**
     * 対象在庫の棚情報のチェックを行います。<BR>
     *
     * @param  checkParams ASRS入力パラメータ
     * @return チェックOK : true チェックNG : false
     * @throws CommonException DBエラーが発生した場合にスローされます。
     */
    protected boolean checkShelf(AsrsInParameter[] checkParams)
            throws CommonException
    {
        StockHandler handler = new StockHandler(getConnection());
        StockSearchKey stkKey = new StockSearchKey();
        ShelfSearchKey shelfKey = new ShelfSearchKey();

        for (int i = 0; i < checkParams.length; i++)
        {
            AsrsInParameter param = (AsrsInParameter)checkParams[i];

            stkKey.clear();
            shelfKey.clear();

            // 在庫ID
            stkKey.setStockId(param.getStockId());
            // 在庫数が0より大きい数量であること
            stkKey.setStockQty(0, ">");
            // 引当可能数が0より大きい数量であること
            stkKey.setAllocationQty(0, ">");
            // アクセス不可棚フラグ(アクセス可)
            shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
            // 状態(使用可)
            shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);

            stkKey.setKey(shelfKey);

            /* 結合条件の指定 */

            // パレットID
            stkKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
            // 現在ステーション
            stkKey.setJoin(Pallet.CURRENT_STATION_NO, Shelf.STATION_NO);

            if (handler.count(stkKey) == 0)
            {
                // No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }
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
