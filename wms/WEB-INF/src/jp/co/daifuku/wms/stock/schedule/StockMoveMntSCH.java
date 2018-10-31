// $Id: StockMoveMntSCH.java 7390 2010-03-05 09:30:24Z okayama $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.stock.schedule.StockMoveMntSCHParams.*;

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
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.stock.operator.MoveOperator;

/**
 * 在庫移動RFTメンテナンスのスケジュール処理を行います。
 * 
 * @version $Revision: 7390 $, $Date: 2010-03-05 18:30:24 +0900 (金, 05 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class StockMoveMntSCH
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
    public StockMoveMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new MoveWorkInfoFinder(getConnection());
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

        //日次更新チェック
        if (!this.canStart())
        {
            return false;
        }
        StockInParameter param = null;

        List<StockInParameter> listParam = new ArrayList<StockInParameter>();
        for (ScheduleParams startParam : ps)
        {
            param = new StockInParameter(getWmsUserInfo());
            param.setCancelCheck(startParam.getBoolean(CANCEL));
            param.setJobNo(startParam.getString(JOB_NO));
            param.setMovementQty((startParam.getLong(MOVE_CASE_QTY) * startParam.getLong(ENTERING_QTY))
                    + startParam.getLong(MOVE_PIECE_QTY));
            param.setDestAreaNo(startParam.getString(MOVE_AREA_NO));
            param.setDestLocation(startParam.getString(MOVE_LOCATION_NO));
            param.setRowNo(startParam.getRowIndex());
            listParam.add(param);
        }

        StockInParameter[] inParams = new StockInParameter[listParam.size()];
        listParam.toArray(inParams);

        int rowNum = 0;

        //入庫完了処理
        try
        {
            MoveOperator moveOperator = new MoveOperator(this.getConnection(), this.getClass());


            for (rowNum = 0; rowNum < inParams.length; rowNum++)
            {

                // キャンセルチェックボックスにチェックが入っている場合
                if (inParams[rowNum].isCancelCheck() == true)
                {
                    //入庫キャンセル処理
                    moveOperator.cancelStorage(inParams[rowNum]);
                }
                else
                {
                    //在庫移動入庫完了処理
                    moveOperator.completeStorage(inParams[rowNum]);
                }
            }

            //修正登録しました。
            setMessage("6001011");

            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(L_ISSUE_REPORT) ? WebSetting.KEYDATA_ON
                                                           : WebSetting.KEYDATA_OFF;
            updateWebSetting(getUserInfo().getTerminalNumber(), ps[0].getString(FUNCTION_ID), value);

            return true;
        }
        catch (LockTimeOutException e)
        {
            // No. {0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, inParams[rowNum].getRowNo()));
            setErrorRowIndex(inParams[rowNum].getRowNo());
            return false;
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode()))
            {
                // No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParams[rowNum].getRowNo()));
                setErrorRowIndex(inParams[rowNum].getRowNo());
                return false;
            }
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023187=No.{0} 移動数には移動可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023187, inParams[rowNum].getRowNo()));
                setErrorRowIndex(inParams[rowNum].getRowNo());
                return false;
            }
            else if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
            {
                // 6023185=No.{0} 移送先の在庫数が在庫上限数を超えるため、移動できません。{1}以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023185, inParams[rowNum].getRowNo(),
                        WmsFormatter.getNumFormat(Long.valueOf(e.getDetailString()))));
                setErrorRowIndex(inParams[rowNum].getRowNo());
                return false;

            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }

    /**
     * リストセル入力値に対してチェックを行います。
     * エリア登録チェック、棚登録チェック、移動数登録チェック
     * @param p メンテナンス対象パラメータ
     * @return boolean型 
     * @throws CommonException メンテナンス対象データ変更可能チェックエラー
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        try
        {
            //キャンセルチェックがない場合
            if (!p.getBoolean(CANCEL))
            {
                //パラメータのケース入数が0の場合、ケース数は入力できません。
                if (p.getInt(ENTERING_QTY) == 0 && p.getInt(MOVE_CASE_QTY) != 0)
                {
                    //No.{0}ケース入数が0の場合、移動ケース数は入力できません。
                    setMessage(WmsMessageFormatter.format(6023125, p.getRowIndex()));
                    return false;
                }
                if (p.getInt(MOVE_PIECE_QTY) == 0 && p.getInt(MOVE_CASE_QTY) == 0)
                {
                    //No.{0} 移動ケース数または移動ピース数には1以上の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023170, p.getRowIndex()));
                    return false;
                }

                // オーバーフローチェック
                long allQty =
                        (long)p.getInt(ENTERING_QTY) * (long)p.getInt(MOVE_CASE_QTY) + (long)p.getInt(MOVE_PIECE_QTY);
                if (allQty > (long)WmsParam.MAX_STOCK_QTY)
                {
                    // 6023219=No.{0} 移動数には在庫上限数{1}以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023219, p.getRowIndex(),
                            WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY)));
                    return false;
                }

                // 入庫エリアチェック
                if (StringUtil.isBlank(p.getString(MOVE_AREA_NO)))
                {
                    //No.{0}入庫エリアを入力してください。
                    setMessage(WmsMessageFormatter.format(6023127, p.getRowIndex()));
                    return false;
                }

                //入庫棚チェック
                if (StringUtil.isBlank(p.getString(MOVE_LOCATION_NO)))
                {
                    //No.{0}入庫棚を入力してください。
                    setMessage(WmsMessageFormatter.format(6023128, p.getRowIndex()));
                    return false;
                }

                //パラメータの移動先エリアがエリアマスタに登録されてているか
                Stock stockParam = new Stock();
                stockParam.setConsignorCode(p.getString(CONSIGNOR_CODE));
                stockParam.setItemCode(p.getString(ITEM_CODE));
                stockParam.setAreaNo(p.getString(MOVE_AREA_NO));
                stockParam.setLocationNo(p.getString(MOVE_LOCATION_NO));
                LocateController locateCtr = new LocateController(this.getConnection(), this.getClass());
                locateCtr.checkStorageLocate(stockParam);
            }
            //パラメータの予定数より入力実績数が大きくないこと
            int planCaseQty = p.getInt(PLAN_CASE_QTY);
            int planPieceQty = p.getInt(PLAN_PIECE_QTY);
            int resultCaseQty = p.getInt(MOVE_CASE_QTY);
            int resultPieceQty = p.getInt(MOVE_PIECE_QTY);
            int enteringQty = p.getInt(ENTERING_QTY);

            long planQty = (long)(((long)planCaseQty * (long)enteringQty) + (long)planPieceQty);
            long resultQty = (long)(((long)resultCaseQty * (long)enteringQty) + (long)resultPieceQty);

            if (planQty < resultQty)
            {
                //6023187=No.{0} 移動数には移動可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023187, p.getRowIndex()));
                return false;
            }

            return true;
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_NO_AREA_FOUND.equals(e.getErrorCode()))
            {
                // No. {0} 指定されたエリアは、存在しません。
                setMessage(WmsMessageFormatter.format(6023030, p.getRowIndex()));
                return false;
            }
            else if (OperatorException.ERR_NO_LOCATION_FOUND.equals(e.getErrorCode()))
            {
                //No:{0}指定されたエリアに入力された棚は存在しません。
                setMessage(WmsMessageFormatter.format(6023031, p.getRowIndex()));
                return false;
            }
            else if (OperatorException.ERR_ASRS_AREA_FOUND.equals(e.getErrorCode()))
            {
                //No. {0} 指定されたエリアはAS/RSエリアです。
                setMessage(WmsMessageFormatter.format(6023032, p.getRowIndex()));
                return false;
            }
            else if (OperatorException.ERR_FIXED_ITEM_LOCATION_FOUND.equals(e.getErrorCode()))
            {
                //No. {0} 指定された棚は固定棚で商品が登録されていません。
                setMessage(WmsMessageFormatter.format(6023033, p.getRowIndex()));
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
        catch (NoPrimaryException e)
        {
            // No. {0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, p.getRowIndex()));
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
     * @return MoveWorkInfoSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        MoveWorkInfoSearchKey sKey = new MoveWorkInfoSearchKey();

        // 移動元エリア
        if (!WmsParam.ALL_AREA_NO.equals(p.getString(FROM_MOVE_AREA_NO)))
        {
            sKey.setRetrievalAreaNo(p.getString(FROM_MOVE_AREA_NO));
        }

        // 移動元棚
        if (!StringUtil.isBlank(p.getString(LOCATION)))
        {
            sKey.setRetrievalLocationNo(p.getString(LOCATION));
        }

        // 商品コード
        if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
        {
            sKey.setItemCode(p.getString(ITEM_CODE));
        }

        // 荷主コード
        sKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 状態フラグ(出庫済入庫待)
        sKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_WAITING);
        // 作業区分（移動）
        sKey.setJobType(MoveWorkInfo.JOB_TYPE_MOVEMENT);
        // ハードウェア区分
        sKey.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_RFT);
        // ソート順の指定
        // 移動元エリア
        sKey.setRetrievalAreaNoOrder(true);
        // 移動元棚
        sKey.setRetrievalLocationNoOrder(true);
        // 商品コード
        sKey.setItemCodeOrder(true);
        // ロットNo.
        sKey.setLotNoOrder(true);

        //商品マスタ情報取得
        sKey.setJoin(MoveWorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        sKey.setJoin(MoveWorkInfo.ITEM_CODE, Item.ITEM_CODE);

        // ログインユーザ情報取得
        sKey.setJoin(MoveWorkInfo.RETRIEVAL_USER_ID, "", Com_loginuser.USERID, "(+)");

        // 取得項目
        sKey.setCollect(new FieldName(MoveWorkInfo.STORE_NAME, FieldName.ALL_FIELDS));
        sKey.setCollect(Item.ITEM_NAME);
        sKey.setCollect(Item.ENTERING_QTY);
        sKey.setCollect(Item.JAN);
        sKey.setCollect(Item.ITF);
        sKey.setCollect(Com_loginuser.USERNAME);

        return sKey;
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
        MoveWorkInfo[] entities = (MoveWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (MoveWorkInfo ent : entities)
        {
            Params param = new Params();

            //作業No.
            param.set(JOB_NO, ent.getJobNo());
            // 移動元エリアNo.
            param.set(RETRIEVAL_AREA_NO, ent.getRetrievalAreaNo());
            // 移動元棚No.
            param.set(RETRIEVAL_LOCATION_NO, ent.getRetrievalLocationNo());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            param.set(LOT_NO, ent.getLotNo());
            // ケース入数
            param.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            // 予定ケース数
            param.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 予定ピース数
            param.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 出庫日時
            param.set(RETRIEVALDATE, ent.getRetrievalWorkDate());
            // 移動出庫ユーザID
            //            param.setUserId(USER_NAME,ent.getRetrievalUserId());
            // 移動出庫RFTNo.
            param.set(RFT_NO, ent.getRetrievalTerminalNo());
            // ユーザ名
            param.set(USER_NAME, String.valueOf(ent.getValue(Com_loginuser.USERNAME, "")));

            result.add(param);
        }

        return result;
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
