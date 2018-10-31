// $Id: StorageListCompleteSCH.java 7597 2010-03-16 01:16:06Z ota $
package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.storage.schedule.StorageListCompleteSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.storage.operator.StorageOperator;

/**
 * 入庫リスト作業結果入力のスケジュール処理を行います。
 * 
 * @version $Revision: 7597 $, $Date: 2010-03-16 10:16:06 +0900 (火, 16 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class StorageListCompleteSCH
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
    public StorageListCompleteSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new WorkInfoFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder, p);
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

        // エリアマスタコントローラー
        AreaController areaCtr = new AreaController(getConnection(), this.getClass());

        // 対象件数取得
        int dataCnt = 0;
        for (int i = 0; i < ps.length; i++)
        {
            // 対象となるデータの場合
            if (ps[i].getBoolean(SELECT))
            {
                // カウントを行う
                dataCnt++;
            }
        }

        // オペレータパラメータ生成
        StorageInParameter[] inParams = new StorageInParameter[dataCnt];
        int pLength = 0;
        for (int i = 0; i < ps.length; i++)
        {
            // 画面にて選択されたデータのみパラメータに追加
            if (ps[i].getBoolean(SELECT))
            {
                // パラメータクラス生成
                inParams[pLength] = new StorageInParameter(getWmsUserInfo());

                // 荷主コード
                inParams[pLength].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
                // リストセル行No.
                inParams[pLength].setRowNo(i);
                // 設定単位キー
                inParams[pLength].setSettingUnitKey(ps[i].getString(SETTING_UNIT_KEY));
                // 商品コード
                inParams[pLength].setItemCode(ps[i].getString(ITEM_CODE));
                // 入庫エリア
                inParams[pLength].setStorageAreaNo(ps[i].getString(PLAN_AREA_NO));
                // ロットNo.
                inParams[pLength].setLotNo(ps[i].getString(PLAN_LOT_NO));
                // 入庫棚
                inParams[pLength].setStorageLocation(ps[i].getString(PLAN_LOCATION_NO));
                // ケース入数
                inParams[pLength].setEnteringQty(ps[i].getInt(ENTERING_QTY));
                // 入庫予定ケース数
                inParams[pLength].setPlanCaseQty(ps[i].getInt(PLAN_CASE_QTY));
                // 入庫予定ピース数
                inParams[pLength].setPlanPieceQty(ps[i].getInt(PLAN_PIECE_QTY));
                // 入庫ケース数
                inParams[pLength].setResultCaseQty(ps[i].getInt(STORAGE_CASE_QTY));
                // 入庫ピース数
                inParams[pLength].setResultPieceQty(ps[i].getInt(STORAGE_PIECE_QTY));
                // 予定数
                int planQty = ps[i].getInt(ENTERING_QTY) * ps[i].getInt(PLAN_CASE_QTY) + ps[i].getInt(PLAN_PIECE_QTY);
                inParams[pLength].setPlanQty(planQty);
                // 実績数
                int resultQty =
                        ps[i].getInt(ENTERING_QTY) * ps[i].getInt(STORAGE_CASE_QTY) + ps[i].getInt(STORAGE_PIECE_QTY);
                inParams[pLength].setResultQty(resultQty);
                // 欠品
                inParams[pLength].setShortageFlag(ps[i].getBoolean(SHORTAGE));
                // 完了フラグ
                if (planQty == resultQty || ps[i].getBoolean(SHORTAGE))
                {
                    // 完了フラグ(1:確定(全数完了 or 欠品完了))
                    inParams[pLength].setCompletionFlag(StorageInParameter.COMPLETION_FLAG_DECISION);
                }
                else
                {
                    // 完了フラグ(2:分割)
                    inParams[pLength].setCompletionFlag(StorageInParameter.COMPLETION_FLAG_REMNANT);
                }
                // 集約作業No.
                inParams[pLength].setCollectJobNo(ps[i].getString(COLLECT_JOBNO));
                // ハードウェア区分
                inParams[pLength].setHardwareType(WorkInfo.HARDWARE_TYPE_LIST);
                // ユーザ情報
                inParams[pLength].setWmsUserInfo(getWmsUserInfo());

                // インクリメント
                pLength++;
            }
        }

        try
        {
            // オペレータ生成
            StorageOperator operator = new StorageOperator(getConnection(), getClass());
            // オペレータ呼び出し
            operator.complete(inParams);

            // (6001014)完了しました。
            setMessage("6001014");

            // 正常の場合はtrueを返却
            return true;
        }
        catch (LockTimeOutException e)
        {
            // (6023114)他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));

            // 異常の場合はfalseを返却
            return false;
        }
        catch (NotFoundException e)
        {
            // (6023115)他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015));

            // 異常の場合はfalseを返却
            return false;
        }
        catch (OperatorException e)
        {
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // (6023015)No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, ps[e.getErrorLineNo() - 1].getRowIndex()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());

                // 異常の場合はfalseを返却
                return false;
            }
            // 在庫の引当で不足数が発生した場合
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // (6023189)No.{0} 出庫数には引当可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023189, ps[e.getErrorLineNo() - 1].getRowIndex()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());

                // 異常の場合はfalseを返却
                return false;
            }
            // 在庫数が在庫上限数を超える場合
            else if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
            {
                // (6023291)No.{0} 入庫先の在庫数が在庫上限数を超えるため、入庫できません。
                setMessage(WmsMessageFormatter.format(6023291, ps[e.getErrorLineNo() - 1].getRowIndex()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());

                // 異常の場合はfalseを返却
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
     * @return WorkInfoSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        // 入出庫作業情報検索キー
        WorkInfoSearchKey sKey = new WorkInfoSearchKey();

        // 検索キーのセット
        // 入庫予定日
        sKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        // 設定単位キー
        sKey.setSettingUnitKey(p.getString(SETTING_UNIT_KEY));
        // 荷主コード
        sKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 状態フラグ(作業中)
        sKey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
        // ハードウェア区分
        sKey.setHardwareType(WorkInfo.HARDWARE_TYPE_LIST);
        // 作業区分：入庫
        sKey.setJobType(WorkInfo.JOB_TYPE_STORAGE);

        // 並び順の設定
        // 商品コード
        sKey.setItemCodeOrder(true);
        // ロットNo.
        sKey.setPlanLotNoOrder(true);
        // 入庫エリア
        sKey.setPlanAreaNoOrder(true);
        // 入庫棚
        sKey.setPlanLocationNoOrder(true);

        // 商品マスタ情報取得
        // 荷主コード
        sKey.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        sKey.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);

        // 取得キーの指定
        // 集約作業No.
        sKey.setCollectJobNoCollect();
        // 商品コード
        sKey.setItemCodeCollect();
        // 予定エリアNo.
        sKey.setPlanAreaNoCollect();
        // 予定棚
        sKey.setPlanLocationNoCollect();
        // 予定数
        sKey.setPlanQtyCollect("SUM");
        // 実績数
        sKey.setResultQtyCollect("SUM");
        // 予定ロットNo.
        sKey.setPlanLotNoCollect();
        // 商品名称
        sKey.setCollect(Item.ITEM_NAME);
        // ケース入数
        sKey.setCollect(Item.ENTERING_QTY);

        // 集約条件の指定
        // 集約作業No.
        sKey.setCollectJobNoGroup();
        // 商品コード
        sKey.setItemCodeGroup();
        // 予定エリアNo.
        sKey.setPlanAreaNoGroup();
        // 予定棚
        sKey.setPlanLocationNoGroup();
        // 予定ロットNo.
        sKey.setPlanLotNoGroup();
        // 商品名称
        sKey.setGroup(Item.ITEM_NAME);
        // ケース入数
        sKey.setGroup(Item.ENTERING_QTY);

        // 生成した検索キーを返却
        return sKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder, ScheduleParams p)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        WorkInfo[] entities = (WorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        // 返却用の配列を生成
        List<Params> result = new ArrayList<Params>();

        AreaController areaCont = new AreaController(getConnection(),this.getClass());
        
        // 取得データ件数分、繰り返す
        for (WorkInfo ent : entities)
        {
            // パラメータクラス生成
            Params param = new Params();

            // 返却データをセット
            // 入庫予定日
            param.set(PLAN_DAY, p.get(PLAN_DAY));
            // 集約作業No
            param.set(COLLECT_JOBNO, ent.getCollectJobNo());
            // 設定単位キー
            param.set(SETTING_UNIT_KEY, p.get(SETTING_UNIT_KEY));
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // ロットNO.
            param.set(PLAN_LOT_NO, ent.getPlanLotNo());
            // 入庫予定エリア
            param.set(PLAN_AREA_NO, ent.getPlanAreaNo());
            // 入庫予定棚
            String loc = null;
            try
            {
                loc = WmsFormatter.toDispLocation(ent.getPlanLocationNo(), areaCont.getLocationStyle(ent.getPlanAreaNo()));
            }
            catch (ScheduleException e)
            {
                throw new ReadWriteException();
            }
            
            param.set(PLAN_LOCATION_NO, loc);
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY, new BigDecimal(0)).intValue();
            param.set(ENTERING_QTY, enteringQty);
            // 予定ケース数
            param.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
            // 予定ピース数
            param.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
            // 入庫数の初期入力にチェックされている場合
            if (p.getBoolean(STORAGE_QTY_INPUT_NUMBER))
            {
                // 実績ケース数
                param.set(STORAGE_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
                // 実績ピース数
                param.set(STORAGE_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
            }
            // セットした返却データを配列に格納
            result.add(param);
        }
        // 設定されたリストを返却
        return result;
    }

    /**
     * リストセル入力値に対してチェックを行います。
     * エリア登録チェック、棚登録チェック、実績数登録チェック
     * @param searchParam 入庫変更パラメータ
     * @return boolean
     * @throws CommonException 変更パラメータチェック時エラー
     */
    public boolean check(ScheduleParams p, int rowNo)
            throws CommonException
    {
        // コネクションの取得
        Connection conn = getConnection();
        // eWareNaviシステム定義コントローラークラス
        WarenaviSystemController sysController = new WarenaviSystemController(conn, getClass());
        // チェッカークラス生成
        WmsChecker checker = new WmsChecker();

        // 欠品チェックが入っていない場合のみ処理を行う
        if (!p.getBoolean(SHORTAGE))
        {
            // ケース数、ピース数が入力されていない場合
            if (StringUtil.isBlank(p.getString(STORAGE_CASE_QTY)) && StringUtil.isBlank(p.getString(STORAGE_PIECE_QTY)))
            {
                // (6023205)No.{0} 入庫ケース数または入庫ピース数を入力してください。
                setMessage(WmsMessageFormatter.format(6023205, rowNo));

                // 異常の場合はfalseを返却
                return false;
            }
        }

        // エリアNo.
        if (!checker.checkContainNgText(p.getString(PLAN_AREA_NO), rowNo, "LBL-W0198"))
        {
            // 返却メッセージの設定
            setMessage(checker.getMessage());

            // 異常の場合はfalseを返却
            return false;
        }

        // 欠品チェックがなく(分納)、ケース数とピース数が0の場合
        if (!p.getBoolean(SHORTAGE) && p.getInt(STORAGE_CASE_QTY) == 0 && p.getInt(STORAGE_PIECE_QTY) == 0)
        {
            // (6023046)No.{0} ケース数またはピース数には1以上の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023046, rowNo));

            // 異常の場合はfalseを返却
            return false;
        }

        // パラメータのケース入数が0の場合、ケース数は0が登録されているか
        if (p.getInt(ENTERING_QTY) == 0 && p.getInt(STORAGE_CASE_QTY) != 0)
        {
            // (6023017)ケース入数が0の場合、予定ケース数は入力できません。
            setMessage(WmsMessageFormatter.format(6023017, rowNo));

            // 異常の場合はfalseを返却
            return false;
        }

        // 欠品チェックがあり・入庫数が0の場合、エリア、棚チェックは行われない
        boolean hasAreaLocCheck =
                !(p.getBoolean(SHORTAGE) && p.getInt(STORAGE_CASE_QTY) == 0 && p.getInt(STORAGE_PIECE_QTY) == 0);
        if (hasAreaLocCheck)
        {
            // 入庫エリアチェック
            if (StringUtil.isBlank(p.getString(PLAN_AREA_NO)))
            {
                // (6023127)No.{0}入庫エリアを入力してください。
                setMessage(WmsMessageFormatter.format(6023127, rowNo));

                // 異常の場合はfalseを返却
                return false;
            }

            // 入庫棚チェック
            if (StringUtil.isBlank(p.getString(PLAN_LOCATION_NO)))
            {
                // (6023128)No.{0}入庫棚を入力してください。
                setMessage(WmsMessageFormatter.format(6023128, rowNo));

                // 異常の場合はfalseを返却
                return false;
            }
        }

        // エリアチェック
        if (!StringUtil.isBlank(p.getString(PLAN_AREA_NO)))
        {
            AreaHandler vpAreaCtlr = new AreaHandler(conn);
            AreaSearchKey vpKey = new AreaSearchKey();

            vpKey.setAreaNo(p.getString(PLAN_AREA_NO));

            Area[] vapResults = (Area[])vpAreaCtlr.find(vpKey);
            if (vapResults.length == 0)
            {
                // 6023414=No.{0} 入力された入荷エリア、{1}は存在しません。
                setMessage(WmsMessageFormatter.format(6023414, rowNo, p.getString(PLAN_AREA_NO)));
                return false;
            }
            else if (vapResults[0].getAreaType().equals(Area.AREA_TYPE_ASRS))
            {
                // (6023032)No. {0} 指定されたエリアはAS/RSエリアです。
                setMessage(WmsMessageFormatter.format(6023032, rowNo));
                return false;
            }
        }

        // 予定数
        long planQty = (long)p.getInt(ENTERING_QTY) * (long)p.getInt(PLAN_CASE_QTY) + (long)p.getInt(PLAN_PIECE_QTY);
        // 実績数
        long resultQty =
                (long)p.getInt(ENTERING_QTY) * (long)p.getInt(STORAGE_CASE_QTY) + (long)p.getInt(STORAGE_PIECE_QTY);
        // 予定数より実績数が多い場合
        if (planQty < resultQty)
        {
            // (6023034)No.{0} 入庫数には入庫予定数以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023034, rowNo));

            // 異常の場合はfalseを返却
            return false;
        }

        // 完了数に作業予定数以上の値を入力した場合、欠品は指定できません。
        if (p.getBoolean(SHORTAGE) && (planQty <= resultQty))
        {
            // 6023250=No.{0} 完了数に作業予定数以上の値を入力した場合、欠品は指定できません。
            setMessage(WmsMessageFormatter.format(6023250, rowNo));
            return false;
        }

        // 在庫パッケージが導入されている場合
        boolean hasStockPack = sysController.hasStockPack();
        // 入庫先が在庫上限数を超えないか
        // 在庫パッケージが導入されていない場合、欠品チェックがあり・入庫数が0の場合はチェックは行われない
        if (hasStockPack && hasAreaLocCheck)
        {
            // 在庫数の取得
            StockHandler shandler = new StockHandler(conn);
            StockSearchKey sskey = new StockSearchKey();
            sskey.setAreaNo(p.getString(PLAN_AREA_NO));
            sskey.setLocationNo(p.getString(PLAN_LOCATION_NO));
            sskey.setConsignorCode(p.getString(CONSIGNOR_CODE));
            sskey.setItemCode(p.getString(ITEM_CODE));
            sskey.setLotNo(p.getString(PLAN_LOT_NO));
            Stock[] stock = (Stock[])shandler.find(sskey);

            // 在庫がある場合チェックを行う
            if (stock != null && stock.length != 0)
            {
                long stockQty = stock[0].getStockQty();
                long qty = resultQty + stockQty - ((long)WmsParam.MAX_STOCK_QTY);

                // 在庫上限数を超える場合
                if (qty > 0)
                {
                    if (resultQty - qty == 0)
                    {
                        // 6023291=No.{0} 入庫先の在庫数が在庫上限数を超えるため、入庫できません。
                        setMessage(WmsMessageFormatter.format(6023291, rowNo));
                    }
                    else
                    {
                        // 6023183=No.{0} 入庫先の在庫数が在庫上限数を超えるため、入庫できません。{1}以下の値を入力してください。
                        setMessage(WmsMessageFormatter.format(6023183, rowNo, resultQty - qty));
                    }
                    return false;
                }
            }
        }

        // 正常の場合はtrueを返却
        return true;
    }

    /**
     * リストセル入力値に対してチェックを行います。
     * 棚チェック
     * @param searchParam 入庫変更パラメータ
     * @return boolean
     * @throws CommonException 変更パラメータチェック時エラー
     */
    public boolean checkLocation(ScheduleParams p, int rowNo)
            throws CommonException
    {
        //棚整合性チェック
        //棚マスタコントローラ
        LocateController control = new LocateController(getConnection(), this.getClass());
        //在庫情報エンティティ
        Stock stock = new Stock();

        //荷主コードをセット
        stock.setConsignorCode(p.getString(CONSIGNOR_CODE));
        //商品コードをセット
        stock.setItemCode(p.getString(ITEM_CODE));
        //エリアNo.をセット
        stock.setAreaNo(p.getString(PLAN_AREA_NO));
        //棚No.をセット
        stock.setLocationNo(WmsFormatter.getString(p.getString(PLAN_LOCATION_NO), "-"));

        try
        {
            //入庫棚チェックメソッドを呼び出す。
            control.checkStorageLocate(stock);
        }
        catch (OperatorException ex)
        {
            // 指定したエリアが存在しない場合
            if (OperatorException.ERR_NO_AREA_FOUND.equals(ex.getErrorCode()))
            {
                // (6023030)No. {0} 指定されたエリアは、存在しません。
                setMessage(WmsMessageFormatter.format(6023030, rowNo));

                // 異常の場合はfalseを返却
                return false;
            }
            // 指定したエリアがAS/RSエリアの場合
            else if (OperatorException.ERR_ASRS_AREA_FOUND.equals(ex.getErrorCode()))
            {
                // (6023032)No. {0} 指定されたエリアはAS/RSエリアです。
                setMessage(WmsMessageFormatter.format(6023032, rowNo));

                // 異常の場合はfalseを返却
                return false;
            }
            // 指定したエリアが商品固定棚なし、棚マスタなしの場合
            else if (OperatorException.ERR_FIXED_ITEM_LOCATION_FOUND.equals(ex.getErrorCode())
                    || OperatorException.ERR_NO_LOCATION_FOUND.equals(ex.getErrorCode()))
            {
                // (MSG-W0017)登録棚ではないデータが存在します。よろしいですか？
                setDispMessage("MSG-W0017");

                // 異常の場合はfalseを返却
                return false;
            }
            // 上記以外は例外をそのままthrowする
            throw ex;
        }
        catch (NoPrimaryException ex)
        {
            // (6023015)No. {0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, rowNo));

            // 異常の場合はfalseを返却
            return false;
        }
        // 正常の場合はtrueを返却
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
