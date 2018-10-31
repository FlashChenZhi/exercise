// $Id: StockModifySCH.java,v 1.2 2009/02/24 02:45:45 ose Exp $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.stock.schedule.StockModifySCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 在庫情報修正・削除のスケジュール処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:45:45 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class StockModifySCH
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
    public StockModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // 入力チェック
        if (StringUtil.isBlank(p.getString(LOCATION_NO)) && StringUtil.isBlank(p.getString(ITEM_CODE)))
        {
            //棚か商品コードのどちらかを入力してください。
            setMessage("6023174");
            return new ArrayList<Params>();
        }

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
        String day = DbDateUtil.getSystemDate();
        String time = DbDateUtil.getSystemDateTime();
        for (int i = 0; i < ps.length; i++)
        {
            // 入庫日時入力チェック
            if (StringUtil.isBlank(ps[i].getDate(STORAGE_DAY)) ^ StringUtil.isBlank(ps[i].getDate(STORAGE_TIME)))
            {
                // 6022058=入庫日時は日付と時間の両方を入力してください。
                setMessage("6022058");
                return false;
            }
            // 現在はBusinessで行っているためありえないがねんのため
            else if (StringUtil.isBlank(ps[i].getDate(STORAGE_DAY)) && StringUtil.isBlank(ps[i].getDate(STORAGE_TIME)))
            {
                // 入庫日時
                ps[i].set(STORAGE_DAY, WmsFormatter.toDate(day));
                ps[i].set(STORAGE_TIME, WmsFormatter.toDateTime(time));
            }
        }

        ScheduleParams[] inputParam = (ScheduleParams[])ps;

        // 入力チェック
        if (!inputCheck(inputParam))
        {
            return false;
        }

        // 日次更新中チェック
        if (isDailyUpdate())
        {
            return false;
        }
        // 取込中チェック
        else if (isLoadData())
        {
            return false;
        }

        StockHandler stockHandler = null;
        StockSearchKey stockSearchKey = null;

        int rowNum = 0;

        try
        {
            // 在庫コントローラ
            StockController stockcontrol = new StockController(getConnection(), this.getClass());
            //入出庫作業情報Handler類のインスタンス生成
            stockHandler = new StockHandler(getConnection());
            stockSearchKey = new StockSearchKey();

            for (rowNum = 0; rowNum < inputParam.length; rowNum++)
            {
                // サーチキーをクリア
                stockSearchKey.clear();

                // 在庫IDをセット
                stockSearchKey.setStockId(inputParam[rowNum].getString(StockModifySCHParams.STOCKID));
                // 最終更新日時をセット
                stockSearchKey.setLastUpdateDate(inputParam[rowNum].getDate(StockModifySCHParams.LASTUPDATEDATE));
                // ロック処理
                if (stockHandler.findPrimaryForUpdate(stockSearchKey, StockHandler.WAIT_SEC_NOWAIT) == null)
                {
                    // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                    setMessage(WmsMessageFormatter.format(6023015, inputParam[rowNum].getRowIndex()));
                    setErrorRowIndex(inputParam[rowNum].getRowIndex());
                    return false;
                }
            }

            for (rowNum = 0; rowNum < inputParam.length; rowNum++)
            {
                // サーチキーをクリア
                stockSearchKey.clear();

                // 在庫IDをセット
                stockSearchKey.setStockId(inputParam[rowNum].getString(StockModifySCHParams.STOCKID));
                // 最終更新日時をセット
                stockSearchKey.setLastUpdateDate(inputParam[rowNum].getDate(StockModifySCHParams.LASTUPDATEDATE));

                // 検索結果を取得
                Stock oldStock = (Stock)stockHandler.findPrimary(stockSearchKey);

                // 修正登録
                if ((inputParam[0].getProcessFlag()).equals(StockModifySCHParams.ProcessFlag.UPDATE))
                {
                    Stock newStock = new Stock();

                    // 修正項目をセット
                    // 在庫数,引当可能数
                    int newTotalStock =
                            inputParam[rowNum].getInt(StockModifySCHParams.MODIFY_CASE_QTY)
                                    * inputParam[rowNum].getInt(StockModifySCHParams.ENTERING_QTY)
                                    + inputParam[rowNum].getInt(StockModifySCHParams.MODIFY_PIECE_QTY);

                    if (newTotalStock != 0)
                    {
                        newStock.setStockQty(newTotalStock);
                        newStock.setAllocationQty(oldStock.getAllocationQty() + newTotalStock - oldStock.getStockQty());
                    }
                    else
                    {
                        newStock.setStockQty(oldStock.getStockQty());
                        newStock.setAllocationQty(oldStock.getAllocationQty());
                    }

                    // 入庫日時
                    Date storageDate =
                            WmsFormatter.toDate(inputParam[rowNum].getDate(StockModifySCHParams.STORAGE_DAY),
                                    inputParam[rowNum].getDate(StockModifySCHParams.STORAGE_TIME));
                    newStock.setStorageDate(storageDate);
                    // 最終出庫日
                    newStock.setRetrievalDay(WmsFormatter.toParamDate(inputParam[rowNum].getDate(StockModifySCHParams.RETRIEVAL_DAY)));

                    // 在庫増 & 日時修正
                    if (newTotalStock - oldStock.getStockQty() >= 0)
                    {
                        stockcontrol.update(oldStock, newStock, SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT,
                                SystemDefine.JOB_TYPE_MAINTENANCE_PLUS, getWmsUserInfo(), 0);
                    }
                    // 在庫減
                    else if (newTotalStock - oldStock.getStockQty() < 0)
                    {
                        stockcontrol.update(oldStock, newStock, SystemDefine.INC_DEC_TYPE_STOCK_DECREMENT,
                                SystemDefine.JOB_TYPE_MAINTENANCE_MINUS, getWmsUserInfo(), 0);
                    }

                    // 修正しました。
                    setMessage("6001004");
                }
                // 削除
                else if ((inputParam[0].getProcessFlag()).equals(StockModifySCHParams.ProcessFlag.DELETE))
                {
                    // 引当済在庫は削除不可
                    if (oldStock.getStockQty() - oldStock.getAllocationQty() > 0)
                    {
                        // 6023237=No.{0} 引当済み在庫のため削除できません。
                        setMessage(WmsMessageFormatter.format(6023237, inputParam[rowNum].getRowIndex()));
                        setErrorRowIndex(inputParam[rowNum].getRowIndex());
                        return false;
                    }

                    // 在庫コントローラ　在庫削除処理
                    stockcontrol.delete(oldStock, SystemDefine.INC_DEC_TYPE_STOCK_DECREMENT,
                            SystemDefine.JOB_TYPE_MAINTENANCE_MINUS, getWmsUserInfo());

                    // 削除しました。
                    setMessage("6001005");
                }
            }

            return true;
        }
        catch (LockTimeOutException e)
        {
            // No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, inputParam[rowNum].getRowIndex()));
            setErrorRowIndex(inputParam[rowNum].getRowIndex());
            return false;
        }
        catch (NotFoundException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, inputParam[rowNum].getRowIndex()));
            setErrorRowIndex(inputParam[rowNum].getRowIndex());
            return false;
        }
        catch (DataExistsException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, inputParam[rowNum].getRowIndex()));
            setErrorRowIndex(inputParam[rowNum].getRowIndex());
            return false;
        }
        catch (NoPrimaryException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, inputParam[rowNum].getRowIndex()));
            setErrorRowIndex(inputParam[rowNum].getRowIndex());
            return false;
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

        // 在庫情報を検索
        searchKey.setConsignorCode(p.getString(StockModifySCHParams.CONSIGNOR_CODE));
        // エリアNo.
        if (WmsParam.ALL_AREA_NO.equals(p.getString(StockModifySCHParams.AREA_NO)))
        {
            // 全エリアを指定された場合、AR/RSエリア以外を指定
            searchKey.setKey(Area.AREA_TYPE, Area.AREA_TYPE_ASRS, "!=", "(", "", true);
            searchKey.setKey(Area.AREA_TYPE, Area.AREA_TYPE_MOVING, "!=", "", ")", true);
            searchKey.setJoin(Stock.AREA_NO, Area.AREA_NO);
        }
        else
        {
            // 入力データ
            searchKey.setAreaNo(p.getString(StockModifySCHParams.AREA_NO));
        }
        // 棚No.
        if (!StringUtil.isBlank(p.getString(StockModifySCHParams.LOCATION_NO)))
        {
            searchKey.setLocationNo(p.getString(StockModifySCHParams.LOCATION_NO));
        }
        // 商品コード
        if (!StringUtil.isBlank(p.getString(StockModifySCHParams.ITEM_CODE)))
        {
            searchKey.setItemCode(p.getString(StockModifySCHParams.ITEM_CODE));
        }
        //商品マスタテーブルを検索
        searchKey.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        searchKey.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        searchKey.setCollect(Item.ITEM_NAME);
        searchKey.setCollect(Item.ENTERING_QTY);
        searchKey.setCollect(Item.JAN);
        searchKey.setCollect(Item.ITF);
        searchKey.setCollect(new FieldName(Stock.STORE_NAME, FieldName.ALL_FIELDS));
        //ソート順をセットする
        searchKey.setAreaNoOrder(true);
        searchKey.setLocationNoOrder(true);
        searchKey.setItemCodeOrder(true);
        searchKey.setLotNoOrder(true);

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

            // 返却データをセットする
            // 在庫ID
            param.set(StockModifySCHParams.STOCKID, ent.getStockId());
            // エリア
            param.set(StockModifySCHParams.AREA_NO, ent.getAreaNo());
            // 棚
            param.set(StockModifySCHParams.LOCATION_NO, ent.getLocationNo());
            // 商品コード
            param.set(StockModifySCHParams.ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(StockModifySCHParams.ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            // ロットNo.
            param.set(StockModifySCHParams.LOT_NO, ent.getLotNo());
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            param.set(StockModifySCHParams.ENTERING_QTY, enteringQty);
            // 在庫ケース数
            param.set(StockModifySCHParams.STOCK_CASE_QTY, DisplayUtil.getCaseQty(ent.getStockQty(), enteringQty));
            // 在庫ピース数
            param.set(StockModifySCHParams.STOCK_PIECE_QTY, DisplayUtil.getPieceQty(ent.getStockQty(), enteringQty));
            // 引当可能ケース数
            param.set(StockModifySCHParams.ALLOC_CASE_QTY, DisplayUtil.getCaseQty(ent.getAllocationQty(), enteringQty));
            // 引当可能ピース数
            param.set(StockModifySCHParams.ALLOC_PIECE_QTY,
                    DisplayUtil.getPieceQty(ent.getAllocationQty(), enteringQty));
            // JANコード
            param.set(StockModifySCHParams.JAN, ent.getValue(Item.JAN));
            // ケースITF
            param.set(StockModifySCHParams.ITF, ent.getValue(Item.ITF));
            // 入庫日
            param.set(StockModifySCHParams.STORAGE_DAY, ent.getStorageDate());
            // 入庫時刻
            param.set(StockModifySCHParams.STORAGE_TIME, ent.getStorageDate());
            // 最終出庫日
            param.set(StockModifySCHParams.RETRIEVAL_DAY, WmsFormatter.toDate(ent.getRetrievalDay()));
            // 最終更新日時
            param.set(StockModifySCHParams.LASTUPDATEDATE, ent.getLastUpdateDate());

            result.add(param);
        }

        return result;
    }

    /**
     * リストセルエリアの入力チェックを行います。<BR>
     * <BR>
     * @param inParam 編集行を含むパラメータ
     * @return boolean
     */
    protected boolean inputCheck(ScheduleParams[] inParam)
    {
        for (int i = 0; i < inParam.length; i++)
        {
            // ケース入数
            int enteringQty = inParam[i].getInt(ENTERING_QTY);
            // 修正ケース数
            int modifyCase = inParam[i].getInt(MODIFY_CASE_QTY);
            // 修正ピース数
            int modifyPiece = inParam[i].getInt(MODIFY_PIECE_QTY);

            // 在庫数に変更があった場合、チェックを実施
            if (!StringUtil.isBlank(inParam[i].getString(MODIFY_CASE_QTY))
                    || !StringUtil.isBlank(inParam[i].getString(MODIFY_PIECE_QTY)))
            {
                // 在庫ケース数と在庫ピース数が0の場合
                if (modifyCase == 0 && modifyPiece == 0)
                {
                    // 6023046=No.{0} ケース数またはピース数には1以上の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023046, inParam[i].getRowIndex()));
                    return false;
                }
                // ケース入数が0かつ在庫ケース数が1以上の場合
                if (enteringQty == 0 && modifyCase >= 1)
                {
                    // 6023146=No.{0} ケース入数が0の場合、ケース数は入力できません。
                    setMessage(WmsMessageFormatter.format(6023146, inParam[i].getRowIndex()));
                    return false;
                }

                // オーバーフローチェック
                long maxQty = (long)enteringQty * (long)modifyCase + (long)modifyPiece;
                if (maxQty > WmsParam.MAX_STOCK_QTY)
                {
                    // 6023191=No.{0} 在庫数には在庫上限数{1}以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023191, inParam[i].getRowIndex(),
                            WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY)));
                    return false;
                }

                // 入力可能在庫数チェック
                // 在庫数
                long stockQty = enteringQty * inParam[i].getInt(STOCK_CASE_QTY) + inParam[i].getInt(STOCK_PIECE_QTY);
                // 引当可能数
                long allocateQty = enteringQty * inParam[i].getInt(ALLOC_CASE_QTY) + inParam[i].getInt(ALLOC_PIECE_QTY);

                // 修正在庫数は在庫数と引当可能数の差より少なくできない
                if (stockQty - allocateQty > maxQty)
                {
                    // 6023038=No.{0} 修正データは在庫数と引当可能数の差を超える値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023038, inParam[i].getRowIndex()));
                    return false;
                }
            }
        }
        return true;
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
