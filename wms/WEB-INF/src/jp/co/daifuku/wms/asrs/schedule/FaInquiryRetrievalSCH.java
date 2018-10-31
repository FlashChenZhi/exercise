// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z nagao $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import static jp.co.daifuku.wms.asrs.schedule.FaInquiryRetrievalSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
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
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListDASCH;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListDASCHParams;
import jp.co.daifuku.wms.retrieval.exporter.AsRetrievalWorkListParams;
import jp.co.daifuku.wms.retrieval.exporter.RetrievalWorkListParams;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;
import jp.co.daifuku.wms.stock.operator.NoPlanWorkOperator;
import jp.co.daifuku.wms.stock.schedule.StockInParameter;
import jp.co.daifuku.wms.stock.schedule.StockOutParameter;

/**
 * 問合せ出庫設定のスケジュール処理を行います。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: nagao $
 */
public class FaInquiryRetrievalSCH
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
    public FaInquiryRetrievalSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
                outParam.set(WORK_LIST_PRINT_FLAG, webset[0].getValue());
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
        // AS/RS用パラメータリストを生成
        List<ScheduleParams> inparamAsrs = new ArrayList<ScheduleParams>();
        // 平置用パラメータリストを生成
        List<ScheduleParams> inparamFloor = new ArrayList<ScheduleParams>();

        // 画面から渡されたパラメータ件数分繰り返し
        int i = 1;
        boolean initFlag = true;
        for (Params p : ps)
        {
            // パラメータに格納
            FaInquiryRetrievalSCHParams param = new FaInquiryRetrievalSCHParams();
            param.setProcessFlag(ProcessFlag.REGIST);
            param.setRowIndex(ps[i - 1].getRowIndex());
            param.set(FaInquiryRetrievalSCHParams.SELECT, p.getBoolean(SELECT));
            param.set(FaInquiryRetrievalSCHParams.ITEM_CODE, p.getString(ITEM_CODE));
            param.set(FaInquiryRetrievalSCHParams.ITEM_NAME, p.getString(ITEM_NAME));
            param.set(FaInquiryRetrievalSCHParams.LOT_NO, p.getString(LOT_NO));
            param.set(FaInquiryRetrievalSCHParams.LOCATION_NO, p.getString(LOCATION_NO));
            param.set(FaInquiryRetrievalSCHParams.MIXED_LOAD, p.getString(MIXED_LOAD));
            param.set(FaInquiryRetrievalSCHParams.STOCK_QTY, p.getLong(STOCK_QTY));
            param.set(FaInquiryRetrievalSCHParams.ALLOCATE_QTY, p.getLong(ALLOCATE_QTY));
            param.set(FaInquiryRetrievalSCHParams.PICKING_QTY, p.getInt(PICKING_QTY));
            param.set(FaInquiryRetrievalSCHParams.ALL_QTY, p.getBoolean(ALL_QTY));
            param.set(FaInquiryRetrievalSCHParams.AREA_NO, p.getString(AREA_NO));
            param.set(FaInquiryRetrievalSCHParams.STATION_NO, p.getString(STATION_NO));
            param.set(FaInquiryRetrievalSCHParams.FROM_RM_NO, String.valueOf(p.getInt(FROM_RM_NO)));
            param.set(FaInquiryRetrievalSCHParams.TO_RM_NO, String.valueOf(p.getInt(TO_RM_NO)));
            param.set(FaInquiryRetrievalSCHParams.WORK_PLACE, p.getString(WORK_PLACE));
            param.set(FaInquiryRetrievalSCHParams.CONSIGNOR_CODE, p.getString(CONSIGNOR_CODE));
            param.set(FaInquiryRetrievalSCHParams.PALLET_ID, p.getString(PALLET_ID));
            param.set(FaInquiryRetrievalSCHParams.STATUS_FLAG, p.getString(STATUS_FLAG));
            param.set(FaInquiryRetrievalSCHParams.WORK_LIST_PRINT_FLAG, p.getString(WORK_LIST_PRINT_FLAG));
            param.set(FaInquiryRetrievalSCHParams.FUNCTION_ID, p.getString(FUNCTION_ID));
            param.set(FaInquiryRetrievalSCHParams.LAST_UPDATE_DATE, p.getDate(LAST_UPDATE_DATE));
            param.set(FaInquiryRetrievalSCHParams.STOCK_ID, p.getString(STOCK_ID));
            param.set(FaInquiryRetrievalSCHParams.AREA_TYPE, p.getString(AREA_TYPE));

            // 入力チェックを行う
            if (!check(param, initFlag))
            {
                return false;
            }
            else
            {
                initFlag = false;
            }

            // AS/RSの場合
            if (SystemDefine.AREA_TYPE_ASRS.equals(p.getString(AREA_TYPE)))
            {
                // AS/RS用パラメータ配列に格納
                inparamAsrs.add(param);
            }
            // 平置の場合
            else
            {
                // 平置用パラメータ配列に格納
                inparamFloor.add(param);
            }
            i++;
        }

        // 全エリアが選択されている場合
        if (WmsParam.ALL_AREA_NO.equals(ps[0].getString(PUL_AREA_NO)))
        {
            // 平置用パラメータ配列にデータが格納されている場合
            String unitKey = "";
            if (!inparamFloor.isEmpty())
            {
                ScheduleParams[] inparams = new ScheduleParams[inparamFloor.size()];
                inparamFloor.toArray(inparams);

                // start sch Floor
                unitKey = startSCH_FLOOR(inparams);
                if (StringUtil.isBlank(unitKey))
                {
                    return false;
                }
            }

            // AS/RS用パラメータ配列にデータが格納されている場合
            List<AsrsOutParameter> asrslist = new ArrayList<AsrsOutParameter>();
            if (!inparamAsrs.isEmpty())
            {
                ScheduleParams[] inparams = new ScheduleParams[inparamAsrs.size()];
                inparamAsrs.toArray(inparams);

                // start sch ASRS
                asrslist = startSCH_ASRS(inparams);
                if (asrslist.size() <= 0)
                {
                    return false;
                }
            }

            // 印刷の実行
            if (ps[0].getBoolean(WORK_LIST_PRINT_FLAG))
            {
                if (!inparamFloor.isEmpty() && !printFloorRetrievalWorkList(unitKey))
                {
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }

                if (!inparamAsrs.isEmpty() && !printAsRetrievalWorkList(asrslist))
                {
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }
            }
        }
        else
        {
            if (SystemDefine.AREA_TYPE_ASRS.equals(ps[0].getString(AREA_TYPE)))
            {
                // start sch ASRS
                ScheduleParams[] inparams = new ScheduleParams[inparamAsrs.size()];
                inparamAsrs.toArray(inparams);

                List<AsrsOutParameter> asrslist = new ArrayList<AsrsOutParameter>();
                asrslist = startSCH_ASRS(inparams);
                if (asrslist.size() <= 0)
                {
                    return false;
                }
                // 印刷の実行
                if (ps[0].getBoolean(WORK_LIST_PRINT_FLAG))
                {
                    if (!printAsRetrievalWorkList(asrslist))
                    {
                        // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                        setMessage("6007042");
                    }
                }
            }
            else
            {
                // start sch Floor
                ScheduleParams[] inparams = new ScheduleParams[inparamFloor.size()];
                inparamFloor.toArray(inparams);

                String unitKey = "";
                unitKey = startSCH_FLOOR(inparams);
                if (StringUtil.isBlank(unitKey))
                {
                    return false;
                }
                // 印刷の実行
                if (ps[0].getBoolean(WORK_LIST_PRINT_FLAG))
                {
                    if (!printFloorRetrievalWorkList(unitKey))
                    {
                        // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                        setMessage("6007042");
                    }
                }
            }
        }

        return true;
    }

    /**
     * エリア：自動倉庫のためうち入力チェックを行います。<BR>
     * @param  checkParams ASRS入力パラメータ
     * @return チェックOK : true チェックNG : false
     */
    protected boolean checkList_ASRS(AsrsInParameter[] checkParams)
            throws CommonException
    {
        long stockQty = 0;
        long retrievalQty = 0;
        long allocateQty = 0;
        for (int i = 0; i < checkParams.length; i++)
        {
            AsrsInParameter param = (AsrsInParameter)checkParams[i];

            if (param.isAllQtyFlag())
            {
                // 全数にチェックがあって かつ
                // 出庫数に入力がある場合エラー
                if (param.getRetrievalQty() != 0)
                {
                    // No.{0} 全数を選択する場合は、出庫数は入力できません。
                    setMessage(WmsMessageFormatter.format(6023145, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
            }

            // 出庫数、全数チェックはあるが入力されていない場合
            if (!param.isSelected())
            {
                setMessage(WmsMessageFormatter.format(6023148, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }

            // 全数チェック
            if (param.isAllQtyFlag())
            {
                param.setRetrievalQty((int)param.getStockQty());
            }

            // 出庫数
            retrievalQty = (long)param.getRetrievalQty();
            // 在庫数
            stockQty = (long)param.getStockQty();
            // 引当可能数
            allocateQty = (long)param.getAllocateQty();

            if (stockQty == 0)
            {
                // 6023297=No.{0} 在庫数が0の場合は、作業できません。
                setMessage(WmsMessageFormatter.format(6023297, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }

            if (retrievalQty < 0)
            {
                // 6023312=No.{0} 出庫数には0以上の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023312, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }
            else
            {
                // 出庫数が作業数の最大値より大きい場合
                if (retrievalQty > (long)WmsParam.MAX_TOTAL_QTY)
                {
                    // 6023207=No.{0} 出庫数には作業上限数{1}以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023207, param.getRowNo(),
                            WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }

                // 出庫数（入力データ）が引当可能数より大きい
                if (retrievalQty > allocateQty)
                {
                    // 6023296=No.{0} 出庫数には在庫数以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023296, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
                else
                {
                    param.setAllocateQty(stockQty);
                    param.setRetrievalQty((int)retrievalQty);
                }
            }

            StockHandler stockHandler = new StockHandler(getConnection());
            StockSearchKey stockSearchKey = new StockSearchKey();
            // 在庫IDをセット
            stockSearchKey.setStockId(param.getStockId());
            // 最終更新日時をセット
            stockSearchKey.setLastUpdateDate(param.getLastUpdateDate());
            // ロック処理
            if (stockHandler.findPrimaryForUpdate(stockSearchKey, StockHandler.WAIT_SEC_NOWAIT) == null)
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }

        }
        return true;
    }

    /**
     * エリア：平置倉庫時のためうち入力チェックを行います。<BR>
     * @param  checkParams ASRS入力パラメータ
     * @return チェックOK : true チェックNG : false
     * @throws CommonException
     */
    protected boolean checkList_Floor(StockInParameter[] checkParams)
            throws CommonException
    {
        long stockQty = 0;
        long retrievalQty = 0;
        long allocateQty = 0;
        for (int i = 0; i < checkParams.length; i++)
        {
            StockInParameter param = (StockInParameter)checkParams[i];

            if (param.isAllQtyFlag())
            {
                // 全数にチェックがあって かつ
                // 出庫数に入力がある場合エラー
                if (param.getResultQty() != 0)
                {
                    // No.{0} 全数を選択する場合は、出庫数は入力できません。
                    setMessage(WmsMessageFormatter.format(6023145, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
            }

            // 出庫数、全数チェックはあるが入力されていない場合
            if (!param.isSelected())
            {
                setMessage(WmsMessageFormatter.format(6023148, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }

            // 全数チェック
            if (param.isAllQtyFlag())
            {
                param.setResultQty((int)param.getStockQty());
            }

            // 出庫数
            retrievalQty = param.getResultQty();
            // 引当可能数
            allocateQty = param.getAllocateQty();
            // 在庫数
            stockQty = (long)param.getStockQty();

            if (stockQty == 0)
            {
                // 6023297=No.{0} 在庫数が0の場合は、作業できません。
                setMessage(WmsMessageFormatter.format(6023297, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }

            if (retrievalQty == 0)
            {
                // 6023289=平置きエリアからは0数出庫は出来ません。
                setMessage(WmsMessageFormatter.format(6023289));
                setErrorRowIndex(param.getRowNo());
                return false;
            }

            if (retrievalQty < 0)
            {
                // 6023281=No.{0} 出庫数には1以上の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023281, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }
            else
            {
                // 出庫数が作業数の最大値より大きい場合
                if (retrievalQty > (long)WmsParam.MAX_TOTAL_QTY)
                {
                    // 6023207=No.{0} 出庫数には作業上限数{1}以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023207, param.getRowNo(),
                            WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }

                // 出庫数（入力データ）が引当可能数より大きい
                if (retrievalQty > allocateQty)
                {
                    // 6023296=No.{0} 出庫数には在庫数以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023296, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
                else
                {
                    param.setAllocateQty(stockQty);
                    param.setResultQty((int)retrievalQty);
                }
            }

            StockHandler stockHandler = new StockHandler(getConnection());
            StockSearchKey stockSearchKey = new StockSearchKey();
            // 在庫IDをセット
            stockSearchKey.setStockId(param.getStockID());
            // 最終更新日時をセット
            stockSearchKey.setLastUpdateDate(param.getLastUpdateDate());
            // ロック処理
            if (stockHandler.findPrimaryForUpdate(stockSearchKey, StockHandler.WAIT_SEC_NOWAIT) == null)
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
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
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        return null;
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
        //        // 最大表示件数分検索結果を取得する
        //    	Stock[] stock = (Stock[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        //        for (Stock ent : stock)
        //        {
        //            Params param = new Params();
        //
        //            // 返却データをセットする
        //             * 1.Entity#getValueを使用して、商品名称などnullがセットされている可能性がある値を
        //             *   取得する場合は、引数２つのgetValueメソッドを使用すること。第二引数は""。
        //             * 2.Entity#getBigDecimalを使用して、nullがセットされている可能性がある数値を
        //             *   取得する場合は、引数２つのgetValueメソッドを使用すること。
        //             *   第二引数はnew BigDecimal(0)。
        //             * 3.ケース・ピースの変換処理は、DisplayUtil#getPieceQtyを使用すること。
        //             */
        //
        //            // 荷主コード
        //            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
        //            // 商品コード
        //            param.set(ITEM_CODE, ent.getItemCode());
        //            // 商品名称
        //            param.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
        //            // エリアNo.
        //            param.set(AREA, ent.getAreaNo());
        //
        //            result.add(param);
        //        }

        return result;
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

            // 商品コード
            stkKey.setItemCode(param.getItemCode());
            if (!StringUtil.isBlank(param.getStockId()))
            {
                // 在庫ID
                stkKey.setStockId(param.getStockId());
            }
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
            if (dasch.count(inparam) == 0)
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
                expparam.set(RetrievalWorkListParams.DFK_USER_NAME,
                        outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
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
            if (dasch.count(inparam) == 0)
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
                expparam.set(AsRetrievalWorkListParams.DFK_USER_ID,
                        outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                expparam.set(AsRetrievalWorkListParams.DFK_USER_NAME,
                        outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                expparam.set(AsRetrievalWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                expparam.set(AsRetrievalWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                expparam.set(AsRetrievalWorkListParams.STATION_NO, outparam.get(FaRetrievalListDASCHParams.STATION_NO));
                expparam.set(AsRetrievalWorkListParams.STATION_NAME,
                        outparam.get(FaRetrievalListDASCHParams.STATION_NAME));
                expparam.set(AsRetrievalWorkListParams.WORK_NO, outparam.get(FaRetrievalListDASCHParams.WORK_NO));
                expparam.set(AsRetrievalWorkListParams.LOCATION_NO,
                        outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                expparam.set(AsRetrievalWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                expparam.set(AsRetrievalWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                expparam.set(AsRetrievalWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                expparam.set(AsRetrievalWorkListParams.RETRIEVAL_COMMAND_DETAIL,
                        outparam.get(FaRetrievalListDASCHParams.RETRIEVAL_COMMAND_DETAIL));
                expparam.set(AsRetrievalWorkListParams.PRIORITY_FLAG,
                        outparam.get(FaRetrievalListDASCHParams.PRIORITY_FLAG));
                expparam.set(AsRetrievalWorkListParams.WORK_QTY, outparam.get(FaRetrievalListDASCHParams.WORK_QTY));
                expparam.set(AsRetrievalWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
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
     * AS/RSエリア、平置エリアのチェックを行う
     *
     * @param p スケジュールパラメータ
     * @param basicCheck 基本チェック必要可否フラグ
     * @return 結果(問題なし:true、問題あり:false)
     * @throws CommonException
     */
    protected boolean check(FaInquiryRetrievalSCHParams p, boolean basicCheck)
            throws CommonException
    {
        if (SystemDefine.AREA_TYPE_ASRS.equals(p.getString(AREA_TYPE)))
        {
            // AS/RSエリア用のチェック
            AsrsInParameter[] inParams = new AsrsInParameter[1];
            inParams[0] = new AsrsInParameter(getWmsUserInfo());
            inParams[0].setStationNo(p.getString(STATION_NO));
            inParams[0].setConsignorCode(p.getString(CONSIGNOR_CODE));
            inParams[0].setAreaNo(p.getString(AREA_NO));
            inParams[0].setLocation(p.getString(LOCATION_NO));
            inParams[0].setPlanLotNo(p.getString(LOT_NO));
            inParams[0].setLotNo(p.getString(LOT_NO));
            inParams[0].setItemCode(p.getString(ITEM_CODE));
            inParams[0].setRowNo(p.getRowIndex());
            inParams[0].setAllQtyFlag(p.getBoolean(ALL_QTY));
            inParams[0].setStockId(p.getString(STOCK_ID));
            inParams[0].setPalletId(p.getString(PALLET_ID));
            inParams[0].setStockQty(p.getLong(STOCK_QTY));
            inParams[0].setRetrievalQty(p.getInt(PICKING_QTY));
            inParams[0].setAllocateQty(p.getLong(ALLOCATE_QTY));
            inParams[0].setLastUpdateDate(p.getDate(LAST_UPDATE_DATE));
            inParams[0].setPriorityType(p.getString(STATUS_FLAG));
            inParams[0].setPalletId(p.getString(PALLET_ID));
            inParams[0].setSelected(p.getBoolean(SELECT));

            // 基本チェック必要時
            if (basicCheck)
            {
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
            }

            // ためうちの入力チェック
            if (!checkList_ASRS(inParams))
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
                if (!inParam.getStationNo().equals(WmsParam.ALL_STATION))
                {
                    // ステーションの出庫作業可能判定
                    if (!retrievalStationCheck(inParam.getStationNo(), inParam.getRowNo()))
                    {
                        setErrorRowIndex(inParam.getRowNo());
                        return false;
                    }
                }
            }
        }
        else
        {
            // 平置エリア用のチェック
            StockInParameter[] inParams = new StockInParameter[1];
            inParams[0] = new StockInParameter(getWmsUserInfo());
            inParams[0].setRowNo(p.getRowIndex());
            inParams[0].setAreaNo(p.getString(AREA_NO));
            inParams[0].setConsignorCode(p.getString(CONSIGNOR_CODE));
            inParams[0].setLocation(p.getString(LOCATION_NO));
            inParams[0].setPlanLotNo(p.getString(LOT_NO));
            inParams[0].setLotNo(p.getString(LOT_NO));
            inParams[0].setItemCode(p.getString(ITEM_CODE));
            inParams[0].setStockID(p.getString(STOCK_ID));
            inParams[0].setStockQty(p.getLong(STOCK_QTY));
            inParams[0].setAllocateQty(p.getLong(ALLOCATE_QTY));
            inParams[0].setLastUpdateDate(p.getDate(LAST_UPDATE_DATE));
            inParams[0].setResultQty(p.getInt(PICKING_QTY));
            inParams[0].setResultLotNo(p.getString(LOT_NO));
            inParams[0].setAllQtyFlag(p.getBoolean(ALL_QTY));
            inParams[0].setSelected(p.getBoolean(SELECT));
            inParams[0].setLastUpdateDate(p.getDate(LAST_UPDATE_DATE));

            // ためうちの入力チェック
            if (!checkList_Floor(inParams))
            {
                return false;
            }
        }
        return true;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 自動倉庫のスケジュール作成を行うクラスです。
     * @params ps
     */
    private List<AsrsOutParameter> startSCH_ASRS(ScheduleParams... ps)
            throws CommonException
    {
        // オペレータパラメータ生成
        AsrsInParameter[] inParams = new AsrsInParameter[ps.length];

        List<AsrsInParameter> paramlist = new ArrayList<AsrsInParameter>();
        paramlist.toArray(inParams);

        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new AsrsInParameter(getWmsUserInfo());

            inParams[i].setStationNo(ps[i].getString(STATION_NO));
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setAreaNo(ps[i].getString(AREA_NO));
            inParams[i].setLocation(ps[i].getString(LOCATION_NO));
            inParams[i].setPlanLotNo(ps[i].getString(LOT_NO));
            inParams[i].setLotNo(ps[i].getString(LOT_NO));
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            inParams[i].setRowNo(ps[i].getRowIndex());
            inParams[i].setAllQtyFlag(ps[i].getBoolean(ALL_QTY));
            inParams[i].setStockId(ps[i].getString(STOCK_ID));
            inParams[i].setPalletId(ps[i].getString(PALLET_ID));
            inParams[i].setStockQty(ps[i].getLong(STOCK_QTY));
            inParams[i].setRetrievalQty(ps[i].getInt(PICKING_QTY));
            inParams[i].setAllocateQty(ps[i].getLong(ALLOCATE_QTY));
            inParams[i].setLastUpdateDate(ps[i].getDate(LAST_UPDATE_DATE));
            inParams[i].setPriorityType(ps[i].getString(STATUS_FLAG));
            inParams[i].setPalletId(ps[i].getString(PALLET_ID));
            inParams[i].setSelected(ps[i].getBoolean(SELECT));

            // 全数チェックされている場合は、設定値を変更
            if (ps[i].getBoolean(ALL_QTY))
            {
                inParams[i].setRetrievalQty(ps[i].getInt(STOCK_QTY));

                if (ps[i].getInt(STOCK_QTY) > ps[i].getLong(ALLOCATE_QTY))
                {
                    // 処理なし
                }
                else
                {
                    inParams[i].setAllocateQty(ps[i].getInt(STOCK_QTY));
                }
            }
        }

        // 印刷用の設定単位キーを保持するリスト
        List<AsrsOutParameter> outparamlist = new ArrayList<AsrsOutParameter>();
        AsrsOperator operator = new AsrsOperator(getConnection(), getClass());

        try
        {
            // ASRS予定外出庫設定
            AsrsOutParameter outParam = operator.webStartNoPlanRetrieval(inParams);
            outparamlist.add(outParam);

            // 6001006=設定しました。
            setMessage("6001006");

            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(WORK_LIST_PRINT_FLAG) ? WebSetting.KEYDATA_ON
                                                                 : WebSetting.KEYDATA_OFF;

            updateWebSetting(inParams[0].getTerminalNo(), ps[0].getString(FUNCTION_ID), value);

            return outparamlist;
        }
        catch (LockTimeOutException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return new ArrayList<AsrsOutParameter>();
        }
        catch (DataExistsException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return new ArrayList<AsrsOutParameter>();
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
                return new ArrayList<AsrsOutParameter>();
            }
            // 搬送ルートエラー
            else if (OperatorException.ERR_ROUTE.equals(e.getErrorCode()))
            {
                // 6023119=No.{0} {1}
                setMessage(WmsMessageFormatter.format(6023119, inParams[index].getRowNo(),
                        MessageResource.getMessage(getRouteErrorMessage(e.getRouteStatus()))));
                setErrorRowIndex(inParams[index].getRowNo());
                return new ArrayList<AsrsOutParameter>();
            }
            // 出庫可能数不足エラー
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023296=No.{0} 出庫数には在庫数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023296, inParams[index].getRowNo()));
                setErrorRowIndex(inParams[index].getRowNo());
                return new ArrayList<AsrsOutParameter>();
            }
            // すでにパレットがほかで引当済
            else if (OperatorException.ERR_ALREADY_ALLOCATED.equals(e.getErrorCode()))
            {
                // 6023119=No.{0} {1}
                // 6023070=指定された棚は現在引当中です。
                setMessage(WmsMessageFormatter.format(6023119, inParams[index].getRowNo(),
                        MessageResource.getMessage("6023070")));
                setErrorRowIndex(inParams[index].getRowNo());
                return new ArrayList<AsrsOutParameter>();
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }

    /**
     * 平置倉庫のスケジュールを行うクラスです。
     *
     */
    private String startSCH_FLOOR(ScheduleParams... ps)
            throws CommonException
    {
        // オペレータパラメータ生成
        StockInParameter[] inParams = new StockInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new StockInParameter(getWmsUserInfo());
            inParams[i].setRowNo(ps[i].getRowIndex());
            inParams[i].setAreaNo(ps[i].getString(AREA_NO));
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setLocation(ps[i].getString(LOCATION_NO));
            inParams[i].setPlanLotNo(ps[i].getString(LOT_NO));
            inParams[i].setLotNo(ps[i].getString(LOT_NO));
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            inParams[i].setStockID(ps[i].getString(STOCK_ID));
            inParams[i].setStockQty(ps[i].getLong(STOCK_QTY));
            inParams[i].setAllocateQty(ps[i].getLong(ALLOCATE_QTY));
            inParams[i].setLastUpdateDate(ps[i].getDate(LAST_UPDATE_DATE));
            inParams[i].setResultQty(ps[i].getInt(PICKING_QTY));
            inParams[i].setResultLotNo(ps[i].getString(LOT_NO));
            inParams[i].setAllQtyFlag(ps[i].getBoolean(ALL_QTY));
            inParams[i].setSelected(ps[i].getBoolean(SELECT));

            // 全数チェックされている場合は、設定値を変更
            if (ps[i].getBoolean(ALL_QTY))
            {
                inParams[i].setResultQty(ps[i].getInt(STOCK_QTY));

                if (ps[i].getInt(STOCK_QTY) > ps[i].getLong(ALLOCATE_QTY))
                {
                    // 処理なし
                }
                else
                {
                    inParams[i].setAllocateQty(ps[i].getInt(STOCK_QTY));
                }
            }
        }

        NoPlanWorkOperator operator = new NoPlanWorkOperator(getConnection(), getClass());

        try
        {
            // 平置出庫パラメータ
            StockOutParameter outParam = operator.completeRetrieval(inParams);

            // 6001006=設定しました。
            setMessage("6001006");

            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(WORK_LIST_PRINT_FLAG) ? WebSetting.KEYDATA_ON
                                                                 : WebSetting.KEYDATA_OFF;

            updateWebSetting(inParams[0].getTerminalNo(), ps[0].getString(FUNCTION_ID), value);

            return outParam.getSettingUnitKey();
        }
        catch (LockTimeOutException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return null;
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
                return null;
            }
            // 出庫可能数不足エラー
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023296=No.{0} 出庫数には在庫数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023296, inParams[index].getRowNo()));
                setErrorRowIndex(inParams[index].getRowNo());
                return null;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
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
