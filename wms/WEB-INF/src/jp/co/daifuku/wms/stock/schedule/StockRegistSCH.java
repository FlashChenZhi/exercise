package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.stock.schedule.StockRegistSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 在庫登録のスケジュール処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:45:46 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class StockRegistSCH
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
    public StockRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * マスタパッケージが導入フラグを取得します。<BR>
     * @param searchParam 検索条件をもつ<CODE>StockInParameter</CODE>クラスを継承したクラス
     * @return 検索結果が含まれた<CODE>StockOutParameter</CODE>クラスを実装したインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public Params initFind(ScheduleParams startParam)
            throws CommonException
    {
        // システムコントローラよりマスタパッケージの有無を取得します。
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        Params outParam = new Params();
        outParam.set(MASTER_FLAG, systemController.hasMasterPack());

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
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        int rowNum = 0;

        try
        {
            // 在庫情報と棚マスタをロック
            StockController stockCtrl = new StockController(getConnection(), this.getClass());

            // ロックキーを格納するEntity
            Stock lockStock = new Stock();

            for (int i = 0; i < ps.length; i++)
            {
                lockStock.clear();

                // エリア
                lockStock.setAreaNo(ps[i].getString(AREA_NO));
                // 棚
                lockStock.setLocationNo(ps[i].getString(LOCATION_NO));
                // 荷主コード
                lockStock.setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
                // 商品コード
                lockStock.setItemCode(ps[i].getString(ITEM_CODE));
                // ロットNo.
                lockStock.setLotNo(ps[i].getString(LOT_NO));

                stockCtrl.lock(lockStock);
            }

            // 在庫登録情報を格納するEntity
            Stock insStock = new Stock();

            for (rowNum = 0; rowNum < ps.length; rowNum++)
            {
                // 既に同一在庫が存在する場合
                if (!duplicateCheck(ps[rowNum]))
                {
                    // 6023029=No.{0} すでに同一データが存在するため、登録できません。
                    setMessage(WmsMessageFormatter.format(6023029, ps[rowNum].getRowIndex()));
                    return false;
                }

                // マスタチェック
                masterUpdateCheck(ps[rowNum]);

                // 在庫数
                int inputQty =
                        ps[rowNum].getInt(ENTERING_QTY) * ps[rowNum].getInt(STOCK_CASE_QTY)
                                + ps[rowNum].getInt(STOCK_PIECE_QTY);

                // 登録情報をセット
                insStock.clear();
                // エリア
                insStock.setAreaNo(ps[rowNum].getString(AREA_NO));
                // 棚
                insStock.setLocationNo(ps[rowNum].getString(LOCATION_NO));
                // 荷主コード
                insStock.setConsignorCode(ps[rowNum].getString(CONSIGNOR_CODE));
                // 商品コード
                insStock.setItemCode(ps[rowNum].getString(ITEM_CODE));
                // ロットNo.
                insStock.setLotNo(ps[rowNum].getString(LOT_NO));
                // 在庫数
                insStock.setStockQty(inputQty);
                // 引当可能数
                insStock.setAllocationQty(inputQty);
                // 入庫日時
                insStock.setStorageDate(WmsFormatter.toDate(ps[rowNum].getDate(STORAGE_DAY),
                        ps[rowNum].getDate(STORAGE_TIME)));

                // 在庫情報の登録
                stockCtrl.insert(insStock, SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT,
                        SystemDefine.JOB_TYPE_MAINTENANCE_PLUS, getWmsUserInfo(), SystemDefine.DEFAULT_REASON_TYPE);
            }

            // 登録しました。
            setMessage(WmsMessageFormatter.format(6001003));
            return true;

        }
        catch (LockTimeOutException e)
        {
            // 6027008=このデータは他の端末で更新中のため、処理できません。
            setMessage(WmsMessageFormatter.format(6027008));
            return false;
        }
        catch (DataExistsException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
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
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, ps[e.getErrorLineNo() - 1].getRowIndex()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
                return false;
            }
            // 在庫の引当で不足数が発生した場合
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023189=No.{0} 出庫数には引当可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023189, ps[e.getErrorLineNo() - 1].getRowIndex()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }

    /**
     * パラメータの内容を元に、登録を行う棚をチェックします。<BR>
     * 棚マスタ管理で棚マスタに登録されていない棚に作成する場合または
     * 固定棚管理で商品・棚が一致しない場合にExceptionをthrowします。
     * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力内容が正常な場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean check(ScheduleParams checkParam)
            throws CommonException
    {

        // 棚マスタコントローラで棚チェック
        LocateController locateCtrl = new LocateController(getConnection(), getClass());

        Stock stock = new Stock();

        // エリアNo.
        stock.setAreaNo(checkParam.getString(AREA_NO));
        // 棚No.
        stock.setLocationNo(checkParam.getString(LOCATION_NO));
        // 荷主コード
        stock.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
        // 商品コード
        stock.setItemCode(checkParam.getString(ITEM_CODE));

        try
        {
            locateCtrl.checkStorageLocate(stock);

            // 6001019=入力を受け付けました。
            setMessage("6001019");

            return true;
        }
        catch (OperatorException e)
        {
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

        WmsChecker chk = new WmsChecker();

        if (!chk.checkDate(p.getDate(STORAGE_DAY), p.getDate(STORAGE_TIME)))
        {
            setMessage(chk.getMessage());
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
        // ケース入数が0かつ入庫ケース数が1以上の場合
        if (p.getInt(ENTERING_QTY) == 0 && p.getInt(STOCK_CASE_QTY) >= 1)
        {
            // 6023036=ケース入数が0の場合、ケース数は入力できません。
            setMessage("6023036");
            return false;
        }

        // 数量入力チェック
        if (p.getInt(STOCK_CASE_QTY) == 0 && p.getInt(STOCK_PIECE_QTY) == 0)
        {
            // 6023035=ケース数またはピース数には1以上の値を入力してください。
            setMessage("6023035");
            return false;
        }

        // オーバーフローチェック
        if ((long)((long)p.getInt(ENTERING_QTY) * (long)p.getInt(STOCK_CASE_QTY) + (long)p.getInt(STOCK_PIECE_QTY)) > (long)WmsParam.MAX_STOCK_QTY)
        {
            // 6023217=在庫数には在庫上限数{0}以下の値を入力してしてください。
            setMessage(WmsMessageFormatter.format(6023190, MAX_STOCK_QTY_DISP));
            return false;
        }

        // 入力エリアのデータに関してDBを検索してチェックするべき内容をチェックする
        // リストセルエリアとのチェックを行う

        // ためうちエリアとの重複チェック
        if (ps != null)
        {
            for (ScheduleParams schParam : ps)
            {
                if (p.getString(ITEM_CODE).equals(schParam.getString(ITEM_CODE))
                        && p.getString(AREA_NO).equals(schParam.getString(AREA_NO))
                        && p.getString(LOCATION_NO).equals(schParam.getString(LOCATION_NO))
                        && p.getString(LOT_NO).equals(schParam.getString(LOT_NO)))
                {
                    // 6023020 = 既に同一データが存在するため、入力できません。
                    setMessage("6023020");
                    return false;
                }
            }
        }

        // 既に同一在庫が存在する場合
        if (!duplicateCheck(p))
        {
            // 6023037=既に在庫データが存在するため、入力できません。
            setMessage("6023037");
            return false;
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
     * @return ItemSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        ItemSearchKey searchKey = new ItemSearchKey();

        // 検索条件をセットする。
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
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

            // 返却データをセットする            
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
     * <CODE>checkParam</CODE>の内容を元に、在庫情報との重複チェックを行います。<BR>
     * @param p 入力チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true : 正常 false : 異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean duplicateCheck(ScheduleParams p)
            throws CommonException
    {
        StockHandler stockHandler = new StockHandler(getConnection());

        // 検索条件のセット
        StockSearchKey stockKey = createStockSearchKey(p);

        // 在庫データが存在する場合
        if (stockHandler.count(stockKey) > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * <CODE>checkParam</CODE>の内容を元に、マスタ情報のチェックを行います。<BR>
     * マスタなしの場合にマスタ情報の更新(マスタが存在しなければ登録)を行います。<BR>
     * @param p 入力チェックを行う内容が含まれたパラメータクラス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void masterUpdateCheck(ScheduleParams p)
            throws CommonException
    {
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        // マスタの更新
        if (!systemController.hasMasterPack())
        {
            ConsignorController consignorCtrl = new ConsignorController(getConnection(), this.getClass());
            ItemController itemCtrl = new ItemController(getConnection(), this.getClass());

            // マスタ情報を格納するEntity
            Consignor consignor = new Consignor();
            Item item = new Item();

            // マスタパッケージなしの場合は登録または更新
            // 荷主マスタ情報
            consignor.clear();
            consignor.setConsignorCode(p.getString(CONSIGNOR_CODE));
            consignorCtrl.autoCreate(consignor, getWmsUserInfo());

            // 商品マスタ情報
            item.clear();
            item.setConsignorCode(p.getString(CONSIGNOR_CODE));
            item.setItemCode(p.getString(ITEM_CODE));
            item.setItemName(p.getString(ITEM_NAME));
            item.setEnteringQty(p.getInt(ENTERING_QTY));
            item.setJan(p.getString(JAN));
            item.setItf(p.getString(ITF));
            itemCtrl.autoCreate(item, getWmsUserInfo());
        }
    }

    /**
     * 在庫情報を検索するための検索キーを作成し返します。<BR>
     * @param p 検索情報を含むパラメータ
     * @return 在庫情報の検索キー
     */
    protected StockSearchKey createStockSearchKey(ScheduleParams p)
    {
        StockSearchKey stockKey = new StockSearchKey();

        // 検索条件のセット
        // エリア
        stockKey.setAreaNo(p.getString(AREA_NO));
        // 棚
        stockKey.setLocationNo(p.getString(LOCATION_NO));
        // 荷主コード
        stockKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        stockKey.setItemCode(p.getString(ITEM_CODE));
        // ロットNo.
        stockKey.setLotNo(p.getString(LOT_NO));
        // 在庫数>0
        stockKey.setStockQty(0, ">");

        return stockKey;
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
