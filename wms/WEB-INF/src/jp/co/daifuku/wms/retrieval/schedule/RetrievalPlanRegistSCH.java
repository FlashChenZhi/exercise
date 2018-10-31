// $Id: RetrievalPlanRegistSCH.java 8096 2015-02-27 02:33:04Z okamura $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanRegistSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.retrieval.operator.RetrievalPlanOperator;

/**
 * 出庫予定データ登録のスケジュール処理を行います。
 * 
 * @version $Revision: 8096 $, $Date: 2015-02-27 11:33:04 +0900 (金, 27 2 2015) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class RetrievalPlanRegistSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 取込単位キーのプリフィックス
     */
    private static final String PREFIX_LOADUNITKEY = "Web-Rt-";

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
    public RetrievalPlanRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * マスタパッケージが導入フラグを取得します。<BR>
     * <BR>
     * @param p 検索条件をもつ<CODE>RetrievalInParameter</CODE>クラスを継承したクラス
     * @return 検索結果が含まれた<CODE>RetrievalOutParameter</CODE>クラスを実装したインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        Params out = new Params();
        out.set(HAS_MASTER, systemController.hasMasterPack());
        out.set(HAS_STOCK, systemController.hasStockPack());

        return out;
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
        // 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
        boolean isUpdateLoadDataFlag = false;
        // リストセル行No.
        int rowNum = 0;
        // 出庫パラメータ
        RetrievalInParameter[] listParam = null;
        // startSCH用パラメータの生成
        List<RetrievalInParameter> rpl = createStartSCHParams(ps);
        // セットするリストセルデータがあれば値をセットします
        listParam = rpl.toArray(new RetrievalInParameter[rpl.size()]);
        // 出庫予定オペレータ
        RetrievalPlanOperator retrievalPlanOprt = new RetrievalPlanOperator(getConnection(), this.getClass());
        // 取込単位キー
        String loadUnitKey = PREFIX_LOADUNITKEY.concat(DbDateUtil.getSystemDateTime());

        try
        {

            // 日次更新・取込中・取込中フラグ更新チェック
            if (!canStart() || isLoadData() || !doLoadStart())
            {
                // 異常の場合はfalseを返却
                return false;
            }
            // 搬送データクリアチェック
            if (isAllocationClear())
            {
                return false;
            }
            // トランザクションの確定
            doCommit(this.getClass());
            // 判定フラグの成立
            isUpdateLoadDataFlag = true;

            // 同一オーダーチェック
            if (!orderCheck(listParam[0], ps))
            {
                // 異常の場合はfalseを返却
                return false;
            }

            // DB重複チェック(件数分)
            for (RetrievalInParameter param : listParam)
            {
                if (!duplicateCheck(param))
                {
                    // 異常の場合はfalseを返却
                    return false;
                }
            }

            // 出庫予定データ登録処理(件数分)
            for (rowNum = 0; rowNum < listParam.length; rowNum++)
            {
                // 取込単位キーの設定
                listParam[rowNum].setLoadUnitKey(loadUnitKey);
                // 出庫予定データ作成
                retrievalPlanOprt.createPlan(SystemDefine.REGIST_KIND_TERMINAL_REGIST, listParam[rowNum]);
            }

            // (6001003)登録しました。
            setMessage("6001003");

            // 正常の場合はtrueを返却
            return true;
        }
        // データロックタイムエラーの場合
        catch (LockTimeOutException e)
        {
            doRollBack(getClass());
            // (6023114)他端末で処理中のため、処理を中断しました。
            setMessage("6023114");

            // 異常の場合はfalseを返却
            return false;
        }
        // データ重複エラーが発生した場合
        catch (DataExistsException e)
        {
            doRollBack(getClass());
            // (6023015)No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, listParam[rowNum].getRowNo()));

            // エラー行の設定
            setErrorRowIndex(listParam[rowNum].getRowNo());

            // 異常の場合はfalseを返却
            return false;
        }
        // オペレータエラーが発生した場合
        catch (OperatorException e)
        {
            doRollBack(getClass());
            // 他端末で作業中の場合
            if (OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode()))
            {
                // (6023014)No.{0} 他端末で処理中のため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023014, listParam[rowNum].getRowNo()));

                // エラー行の設定
                setErrorRowIndex(listParam[rowNum].getRowNo());

                // 異常の場合はfalseを返却
                return false;
            }
            // 他端末で更新済みの場合
            else if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode()))
            {
                // (6023015)No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, listParam[rowNum].getRowNo()));

                // エラー行の設定
                setErrorRowIndex(listParam[rowNum].getRowNo());

                // 異常の場合はfalseを返却
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
        catch (CommonException e)
        {
            doRollBack(getClass());
            throw e;
        }
        finally
        {
            // 取り込み中フラグが自クラスで更新されたものであるならば、
            // 取り込み中フラグを、0：停止中にする
            if (isUpdateLoadDataFlag)
            {
                doLoadEnd();
                doCommit(this.getClass());
            }
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
        // スケジュール用パラメータにデータをセット
        RetrievalInParameter inputparam = createCheckParams(p);
        // システムコントローラー
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        // 異常棚商品コード入力チェック
        if (WmsParam.IRREGULAR_ITEMCODE.equals(p.getString(ITEM_CODE)))
        {
            // (6023078){0}は異常棚用の商品コードのため、使用できません。
            setMessage(WmsMessageFormatter.format(6023078, WmsParam.IRREGULAR_ITEMCODE));

            // 異常の場合はfalseを返却
            return false;
        }

        // 簡易直行用商品コード入力チェック
        if (WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(p.getString(ITEM_CODE)))
        {
            // 6023259 = {0}は簡易直行用の商品コードのため、使用できません。
            setMessage(WmsMessageFormatter.format(6023259, WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE));

            // 異常の場合はfalseを返却
            return false;
        }

        // マスタパッケージを導入している場合
        if (systemController.hasMasterPack())
        {
            // 荷主マスタチェック
            // 荷主マスタデータベースハンドラ
            ConsignorHandler consignorHandler = new ConsignorHandler(getConnection());
            // 荷主マスタ検索キー
            ConsignorSearchKey consignorKey = new ConsignorSearchKey();

            // 検索キーのセット
            // 荷主コード
            consignorKey.setConsignorCode(p.getString(CONSIGNOR_CODE));

            // データが存在しない場合
            if (consignorHandler.count(consignorKey) <= 0)
            {
                // (6023021)荷主コードがマスタに登録されていません。
                setMessage("6023021");

                // 異常の場合はfalseを返却
                return false;
            }

            // 出荷先コードが入力されている場合
            if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE)))
            {
                // 出荷先マスタチェック
                // 出荷先マスタデータベースハンドラ
                CustomerHandler customerHandler = new CustomerHandler(getConnection());
                // 出荷先マスタ検索キー
                CustomerSearchKey customerKey = new CustomerSearchKey();

                // 検索キーのセット
                // 荷主コード
                customerKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
                // 出荷先コード
                customerKey.setCustomerCode(p.getString(CUSTOMER_CODE));

                // データが存在しない場合
                if (customerHandler.count(customerKey) <= 0)
                {
                    // (6023137)出荷先コードがマスタに登録されていません。
                    setMessage("6023138");

                    // 異常の場合はfalseを返却
                    return false;
                }
            }

            // 商品マスタチェック
            // 商品マスタデータベースハンドラ
            ItemHandler itemHandler = new ItemHandler(getConnection());
            // 商品マスタ検索キー
            ItemSearchKey itemKey = new ItemSearchKey();

            // 検索キーのセット
            // 荷主コード
            itemKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
            // 商品コード
            itemKey.setItemCode(p.getString(ITEM_CODE));

            // データが存在しない場合
            if (itemHandler.count(itemKey) <= 0)
            {
                // (6023021)商品コードがマスタに登録されていません。
                setMessage("6023021");

                // 異常の場合はfalseを返却
                return false;
            }
        }

        // 入力値チェック
        if (!inputCheck(p))
        {
            // 異常の場合はfalseを返却
            return false;
        }

        // オーバーフローチェック
        // 予定ケース入数
        long caseQty = (long)p.getInt(PLAN_CASE_QTY);
        // ケース入数
        long enteringQty = (long)p.getInt(ENTERING_QTY);
        // 予定ピース数
        long pieceQty = (long)p.getInt(PLAN_PIECE_QTY);
        // 入数(予定ケース入数 * ケース入数 + 予定ピース数)
        long inputqty = caseQty * enteringQty + pieceQty;

        // バラ総数の最大値を超えている場合
        if (inputqty > (long)WmsParam.MAX_TOTAL_QTY)
        {
            // (6023184)予定数には作業上限数{0}以下の値を入力してしてください。
            setMessage(WmsMessageFormatter.format(6023184, MAX_TOTAL_QTY_DISP));

            // 異常の場合はfalseを返却
            return false;
        }

        // 同一オーダーチェック
        if (!orderCheck(inputparam, ps))
        {
            // 異常の場合はfalseを返却
            return false;
        }

        // リスト重複チェック
        if (ps != null)
        {
            for (ScheduleParams listparam : ps)
            {
                // 行No.と作業枝番が同じ場合
                if (listparam.getInt(LINE_NO) == p.getInt(LINE_NO)
                        && listparam.getInt(BRANCH_NO) == p.getInt(BRANCH_NO))
                {
                    // (6023020)既に同一データが存在するため、入力できません。
                    setMessage("6023020");

                    // 異常の場合はfalseを返却
                    return false;
                }
            }
        }

        // 表示件数チェック
        if (ps != null && ps.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
        {
            // (6023019)件数が{0}件を超えるため、入力できません。
            setMessage(WmsMessageFormatter.format(6023019, MAX_NUMBER_OF_DISP_DISP));

            // 異常の場合はfalseを返却
            return false;
        }

        // 既に同一予定データが存在する場合
        if (!duplicateCheck(inputparam))
        {
            // (6023020)既に同一データが存在するため、入力できません。
            setMessage("6023020");

            // 異常の場合はfalseを返却
            return false;
        }

        // (6001019)入力を受付けました。
        setMessage("6001019");

        // 正常の場合はtrueを返却
        return true;
    }

    /**
     * searchParamで指定されたパラメータの内容を元に、出荷先･商品マスタデータを検索します。<BR>
     * データが見つからない場合は、要素数0のParameter配列を返します。<BR>
     * <BR>
     * @param searchParam 検索条件をもつ<CODE>StockInParameter</CODE>クラス
     * @return 検索結果が含まれた<CODE>StockOutParameter</CODE>インスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public List<Params> query(ScheduleParams searchParam)
            throws CommonException
    {
        // 出荷先マスタエンティティ
        Customer[] customer = new Customer[0];
        // 出荷先マスタデータベースハンドラ
        CustomerHandler customHandler = new CustomerHandler(getConnection());
        // 出荷先マスタ検索キー
        CustomerSearchKey customKey = new CustomerSearchKey();
        // 商品マスタエンティティ
        Item[] item = new Item[0];
        // 商品マスタデータベースハンドラ
        ItemHandler itemHandler = new ItemHandler(getConnection());
        // 商品マスタ検索キー
        ItemSearchKey itemKey = new ItemSearchKey();
        // パラメータクラス生成
        Params param = new Params();

        // return用のパラメータ
        List<Params> outParam = new ArrayList<Params>();

        // 出荷先マスタ検索
        if (!StringUtil.isBlank(searchParam.getString(CUSTOMER_CODE)))
        {
            // 検索条件をセットする
            // 荷主コード
            customKey.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
            // 出荷先コード
            customKey.setCustomerCode(searchParam.getString(CUSTOMER_CODE));

            // 検索する
            customer = (Customer[])customHandler.find(customKey);

            if (customer == null || customer.length <= 0)
            {
                return new ArrayList<Params>();
            }
            else
            {
                // 出荷先コード
                param.set(CUSTOMER_CODE, customer[0].getCustomerCode());
                // 出荷先名称
                param.set(CUSTOMER_NAME, customer[0].getCustomerName());
            }
        }

        // 商品マスタ検索
        // 検索条件をセットする。
        // 荷主コード
        itemKey.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
        // 商品コード
        itemKey.setItemCode(searchParam.getString(ITEM_CODE));

        // 検索する
        item = (Item[])itemHandler.find(itemKey);

        // 取得データのチェック
        if (item == null || item.length <= 0)
        {
            return new ArrayList<Params>();
        }

        // 該当データは一件のはずなので、要素0のみを返す。
        // 荷主コード
        param.set(CONSIGNOR_CODE, item[0].getConsignorCode());
        // 商品コード
        param.set(ITEM_CODE, item[0].getItemCode());
        // 商品名称
        param.set(ITEM_NAME, item[0].getItemName());
        // 入数
        param.set(ENTERING_QTY, item[0].getEnteringQty());
        // JANコード
        param.set(JAN, item[0].getJan());
        // ケースITF
        param.set(ITF, item[0].getItf());
        outParam.add(param);

        // 設定した値を返却
        return outParam;
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 入力値のチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力内容が正常な場合  false：入力内容が異常な場合
     */
    private boolean inputCheck(ScheduleParams checkParam)
    {
        // 予定ケース数と予定ピース数が0の場合
        if (checkParam.getInt(PLAN_CASE_QTY) == 0 && checkParam.getInt(PLAN_PIECE_QTY) == 0)
        {
            // (6023035)ケース数またはピース数には1以上の値を入力してください。
            setMessage("6023035");

            // 異常の場合はfalseを返却
            return false;
        }

        // ケース入数が0かつ予定ケース数が1以上の場合
        if (checkParam.getInt(ENTERING_QTY) == 0 && checkParam.getInt(PLAN_CASE_QTY) >= 1)
        {
            // (6023036)ケース入数が0の場合、ケース数は入力できません。
            setMessage("6023036");

            // 異常の場合はfalseを返却
            return false;
        }
        // 正常の場合はtrueを返却
        return true;
    }

    /**
     * １オーダー内に予定日・出荷先が複数存在しないかチェックを行います。<BR>
     * 正常な場合はtureを返します。重複する場合はメッセージをセットし、falseを返します。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @param listParams リストパラメータ
     * @return boolean
     * @throws CommonException 全ての例外をスローします。
     */
    private boolean orderCheck(RetrievalInParameter checkParam, ScheduleParams... listParams)
            throws CommonException
    {
        // 出庫予定オペレータ
        RetrievalPlanOperator retrievalPlanOprt = new RetrievalPlanOperator(getConnection(), this.getClass());

        // 予定日重複チェック
        if (!retrievalPlanOprt.isOrderInPlanDay(checkParam))
        {
            // (6023055)同一オーダー内に複数の予定日が存在するため、登録できません。
            setMessage("6023055");

            // エラー行の設定
            setErrorRowIndex(checkParam.getRowNo());

            // 異常の場合はfalseを返却
            return false;
        }

        // 出荷先重複チェック
        if (!retrievalPlanOprt.isOrderInCustomer(checkParam))
        {
            // (6023054)同一オーダー内に複数の出荷先コードが存在するため、登録できません。
            setMessage("6023054");

            // エラー行の設定
            setErrorRowIndex(checkParam.getRowNo());

            // 異常の場合はfalseを返却
            return false;
        }

        // 第2引数が存在しなかった場合
        if (listParams == null || listParams.length == 0)
        {
            // 正常の場合はtrueを返却
            return true;
        }

        // 第2引数の件数分、繰り返す
        for (ScheduleParams param : listParams)
        {
            // 同一オーダーNoの場合
            if (param.get(ORDER_NO).equals(checkParam.getOrderNo()))
            {
                // 入力データとリストセルエリアデータが異なる出荷先の場合
                if (!checkParam.getCustomerCode().equals(param.get(CUSTOMER_CODE)))
                {
                    // (6023054)同一オーダー内に複数の出荷先コードが存在するため、登録できません。
                    setMessage("6023054");

                    // エラー行の設定
                    setErrorRowIndex(checkParam.getRowNo());

                    // 異常の場合はfalseを返却
                    return false;
                }
            }
        }
        // 正常の場合はtrueを返却
        return true;
    }

    /**
     * <CODE>checkParam</CODE>の内容を元に、出庫予定情報との重複チェックを行います。<BR>
     * <BR>
     * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true : 正常 false : 異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private boolean duplicateCheck(Parameter checkParam)
            throws CommonException
    {
        // 出庫パラメータ
        RetrievalInParameter param = (RetrievalInParameter)checkParam;
        // 出庫予定オペレーター
        RetrievalPlanOperator rtOp = new RetrievalPlanOperator(getConnection(), this.getClass());

        // 予定データが存在する場合
        if (rtOp.findPlan(checkParam) != null)
        {
            // (6023029)No.{0} すでに同一データが存在するため、登録できません。
            setMessage(WmsMessageFormatter.format(6023029, param.getRowNo()));

            // エラー行の設定
            setErrorRowIndex(param.getRowNo());

            // 異常の場合はfalseを返却
            return false;
        }
        // 正常の場合はtrueを返却
        return true;
    }

    /**
     * startSCH()にて使用するパラメータ配列を生成します。<BR>
     * <BR>
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。
     * @return rpl 生成したパラメータ配列
     */
    private List<RetrievalInParameter> createStartSCHParams(ScheduleParams... ps)
    {
        // 出庫パラメータの生成
        RetrievalInParameter rp = null;
        // 出庫パラメータ配列の生成
        List<RetrievalInParameter> rpl = new ArrayList<RetrievalInParameter>();

        // 取得件数分、繰り返す
        for (int i = 0; i < ps.length; i++)
        {
            // 出庫予定データ登録パラメータ型に変換
            RetrievalPlanRegistSCHParams pp = (RetrievalPlanRegistSCHParams)ps[i];
            // 出庫パラメータの初期化
            rp = new RetrievalInParameter(getWmsUserInfo());

            // 取得データのセット
            // リストセル行No.
            rp.setRowNo(i + 1);
            // 荷主コード
            rp.setConsignorCode(pp.getString(CONSIGNOR_CODE));
            // 出庫予定日
            rp.setRetrievalPlanDay(pp.getString(PLAN_DAY));
            // 伝票No.
            rp.setTicketNo(pp.getString(SHIP_TICKET_NO));
            // 行No.
            rp.setLineNo(pp.getInt(LINE_NO));
            // 作業枝番
            rp.setBranchNo(pp.getInt(BRANCH_NO));
            // バッチNo.
            rp.setBatchNo(pp.getString(BATCH_NO));
            // オーダーNo.
            rp.setOrderNo(pp.getString(ORDER_NO));
            // 出荷先コード
            rp.setCustomerCode(pp.getString(CUSTOMER_CODE));
            // 出荷先名称
            rp.setCustomerName(pp.getString(CUSTOMER_NAME));
            // 商品コード
            rp.setItemCode(pp.getString(ITEM_CODE));
            // 商品名称
            rp.setItemName(pp.getString(ITEM_NAME));
            // ケース入数
            int enteringQty = pp.getInt(ENTERING_QTY);
            rp.setEnteringQty(enteringQty);
            // 予定ケース入数
            int caseQty = pp.getInt(PLAN_CASE_QTY);
            // 予定ピース数
            int pieceQty = pp.getInt(PLAN_PIECE_QTY);
            // 予定数(ケース入数 * 予定ケース数 + 予定ピース数)
            rp.setPlanQty(enteringQty * caseQty + pieceQty);
            // ロットNo.
            rp.setLotNo(pp.getString(PLAN_LOT_NO));
            // JANコード
            rp.setJanCode(pp.getString(JAN));
            // ケースITF
            rp.setItf(pp.getString(ITF));
            // 出庫エリアNo.
            rp.setRetrievalAreaNo(pp.getString(PLAN_AREA_NO));
            // 出庫棚
            rp.setRetrievalLocation(pp.getString(PLAN_LOCATION_NO));

            // 設定した値を配列に格納
            rpl.add(rp);
        }
        // 生成した配列を返却
        return rpl;
    }

    /**
     * check()にて使用するパラメータを生成します。<BR>
     * <BR>
     * @param p 出庫予定データ登録(詳細入力)画面情報
     * @return inputParam 生成したパラメータ
     */
    private RetrievalInParameter createCheckParams(ScheduleParams p)
    {
        // 出庫パラメータの生成
        RetrievalInParameter inputParam = new RetrievalInParameter(getWmsUserInfo());

        // 取得データのセット
        // 荷主コード
        inputParam.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 出庫予定日
        inputParam.setRetrievalPlanDay(p.getString(PLAN_DAY));
        // 伝票No.
        inputParam.setTicketNo(p.getString(SHIP_TICKET_NO));
        // 行No.
        inputParam.setLineNo(p.getInt(LINE_NO));
        // 作業枝番
        inputParam.setBranchNo(p.getInt(BRANCH_NO));
        // バッチNo.
        inputParam.setBatchNo(p.getString(BATCH_NO));
        // オーダーNo.
        inputParam.setOrderNo(p.getString(ORDER_NO));
        // 出荷先コード
        inputParam.setCustomerCode(p.getString(CUSTOMER_CODE));
        // 出荷先名称
        inputParam.setCustomerName(p.getString(CUSTOMER_NAME));
        // 商品コード
        inputParam.setItemCode(p.getString(ITEM_CODE));
        // 商品名称
        inputParam.setItemName(p.getString(ITEM_NAME));
        // ロットNo.
        inputParam.setLotNo(p.getString(PLAN_LOT_NO));
        // ケース入数
        inputParam.setEnteringQty(p.getInt(ENTERING_QTY));
        // 予定ケース数
        inputParam.setPlanCaseQty(p.getInt(PLAN_CASE_QTY));
        // 予定ピース数
        inputParam.setPlanPieceQty(p.getInt(PLAN_PIECE_QTY));
        // JANコード
        inputParam.setJanCode(p.getString(JAN));
        // ケースITF
        inputParam.setItf(p.getString(ITF));
        // 出庫エリアNo.
        inputParam.setRetrievalAreaNo(p.getString(PLAN_AREA_NO));
        // 出庫棚
        inputParam.setRetrievalLocation(p.getString(PLAN_LOCATION_NO));

        // 生成した出庫パラメータを返却
        return inputParam;
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
