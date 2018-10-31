// $Id: NoPlanRetrievalSCH.java 7678 2010-03-18 02:30:14Z kishimoto $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;

import java.math.BigDecimal;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.stock.operator.NoPlanWorkOperator;
import static jp.co.daifuku.wms.stock.schedule.NoPlanRetrievalSCHParams.*;

/**
 * 予定外出庫設定のスケジュール処理を行います。
 *
 * @version $Revision: 7678 $, $Date: 2010-03-18 11:30:14 +0900 (木, 18 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class NoPlanRetrievalSCH
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
    public NoPlanRetrievalSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
                outParam.set(ISSUE_REPORT, webset[0].getValue());
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

            inParams[i].setRowNo(ps[i].getRowIndex());
            
            int workCase = ps[i].getInt(RETRIEVAL_CASE_QTY);
            int workPiece = ps[i].getInt(RETRIEVAL_PIECE_QTY);
            int entQty = ps[i].getInt(ENTERING_QTY);
            
            // 全数チェックがあった場合、
            if (ps[i].getBoolean(ALL))
            {
                // 作業数に入力があった場合はNGとする
                if (workCase * entQty + workPiece > 0)
                {
                    // No.{0} 全数を選択する場合は、出庫数は入力できません。
                    setMessage(WmsMessageFormatter.format(6023145, inParams[i].getRowNo()));
                    setErrorRowIndex(inParams[i].getRowNo());
                    return false;
                }
                
                // 引当可能数を作業数とする
                workCase = ps[i].getInt(ALLOC_CASE_QTY);
                workPiece = ps[i].getInt(ALLOC_PIECE_QTY);
            }
            
            int sumQty = workCase * entQty + workPiece;
            
            // 出庫数が0の場合
            if (sumQty <= 0)
            {
                // 6023046=No.{0} ケース数またはピース数には1以上の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023046, inParams[i].getRowNo()));
                setErrorRowIndex(inParams[i].getRowNo());
                return false;
            }
            else if (sumQty > WmsParam.MAX_STOCK_QTY)
            {
                // 6023218=No.{0} 出庫数には在庫上限数{1}以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023218, inParams[i].getRowNo(), WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY)));
                setErrorRowIndex(inParams[i].getRowNo());
                return false;
            }
            
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            inParams[i].setAreaNo(ps[i].getString(AREA_NO));
            inParams[i].setLocation(ps[i].getString(LOCATION_NO));
            inParams[i].setLotNo(ps[i].getString(PLAN_LOT));
            inParams[i].setResultLotNo(ps[i].getString(RESULT_LOT));
            inParams[i].setResultCaseQty(workCase);
            inParams[i].setResultPieceQty(workPiece);
            inParams[i].setEnteringQty(entQty);
            inParams[i].setResultQty(sumQty);
            inParams[i].setReasonType(ps[i].getInt(REASON));
            
        }
        
        try
        {
            // 予定外入出庫オペレータ生成
            NoPlanWorkOperator operator = new NoPlanWorkOperator(getConnection(), getClass());
            // オペレータ呼び出し
            StockOutParameter writeParam = operator.completeRetrieval(inParams);
            
            // Businessで印刷処理を行うため、オブジェクトにパラメータを上書きする
            ps[0].set(SETTING_UKEY, writeParam.getSettingUnitKey());

            // 設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
            
            
            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(ISSUE_REPORT) ? WebSetting.KEYDATA_ON : WebSetting.KEYDATA_OFF;
            updateWebSetting(getUserInfo().getTerminalNumber(), ps[0].getString(FUNCTION_ID), value); 
            
            return true;

        }
        catch (LockTimeOutException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));
            return false;
        }
        catch (OperatorException e)
        {
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParams[e.getErrorLineNo() - 1].getRowNo()));
                setErrorRowIndex(inParams[e.getErrorLineNo() - 1].getRowNo());
                return false;
            }
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023189=No.{0} 出庫数には引当可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023189, inParams[e.getErrorLineNo() - 1].getRowNo()));
                setErrorRowIndex(inParams[e.getErrorLineNo() - 1].getRowNo());
                return false;
            }
            else if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
            {
                //6023136=No.{0} {1}には{2}以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023136, e.getErrorLineNo(), DisplayText.getText("LBL-W0123"),
                        WmsFormatter.getNumFormat(Long.valueOf(e.getDetailString()))));
                setErrorRowIndex(inParams[e.getErrorLineNo() - 1].getRowNo());
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

        // set where
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        searchKey.setItemCode(p.getString(ITEM_CODE));
        // ロット
        if (!StringUtil.isBlank(p.getString(LOT_NO)))
        {
            searchKey.setLotNo(p.getString(LOT_NO));
        }
        // 出庫エリア
        if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
        {
            searchKey.setAreaNo(p.getString(AREA_NO));
        }
        else
        {
            // 全エリア指定の場合、平置、仮置、入荷エリアを対象とする
            searchKey.setJoin(Stock.AREA_NO, Area.AREA_NO);
            String[] areaType = {
                Area.AREA_TYPE_FLOOR,
                Area.AREA_TYPE_TEMPORARY,
                Area.AREA_TYPE_RECEIVING
            };
            searchKey.setKey(Area.AREA_TYPE, areaType, true);
        }
        
        String[] loc = WmsFormatter.getFromTo(p.getString(FROM_LOCATION), p.getString(TO_LOCATION));
        
        // 出庫棚開始
        if (!StringUtil.isBlank(loc[0]))
        {
            searchKey.setLocationNo(loc[0], ">=");
        }
        // 出庫棚終了
        if (!StringUtil.isBlank(loc[1]))
        {
            searchKey.setLocationNo(loc[1], "<=");
        }
        // 引当可能数が1以上であること
        searchKey.setStockQty(0, ">");
        searchKey.setAllocationQty(0, ">");

        // set join(Item Table)
        searchKey.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        searchKey.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        // set join(Consignor Table)
        searchKey.setJoin(Stock.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);

        // set order by
        searchKey.setLotNoOrder(true);
        searchKey.setStorageDateOrder(true);
        searchKey.setAreaNoOrder(true);
        searchKey.setLocationNoOrder(true);

        // set collect
        searchKey.setCollect(Stock.CONSIGNOR_CODE);
        searchKey.setCollect(Stock.ITEM_CODE);
        searchKey.setCollect(Stock.LOT_NO);
        searchKey.setCollect(Stock.STORAGE_DAY);
        searchKey.setCollect(Stock.STORAGE_DATE);
        searchKey.setCollect(Stock.AREA_NO);
        searchKey.setCollect(Stock.LOCATION_NO);
        searchKey.setCollect(Stock.ALLOCATION_QTY);
        searchKey.setCollect(Item.ITEM_NAME);
        searchKey.setCollect(Item.ENTERING_QTY);
        searchKey.setCollect(Item.JAN);
        searchKey.setCollect(Item.ITF);
        searchKey.setCollect(Consignor.CONSIGNOR_NAME);

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

            int entQty = ent.getBigDecimal(Item.ENTERING_QTY, new BigDecimal(0)).intValue();

            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo
            param.set(PLAN_LOT, ent.getLotNo());
            // 出庫エリア
            param.set(AREA_NO, ent.getAreaNo());
            // 出庫棚
            param.set(LOCATION_NO, ent.getLocationNo());
            // 入庫日
            param.set(STORAGE_DATE, ent.getStorageDate());
            // ケース入数
            param.set(ENTERING_QTY, entQty);
            // 引当可能ケース数
            param.set(ALLOC_CASE_QTY, DisplayUtil.getCaseQty(ent.getAllocationQty(), entQty));
            // 引当可能ピース数
            param.set(ALLOC_PIECE_QTY, DisplayUtil.getPieceQty(ent.getAllocationQty(), entQty));
            // JANコード
            param.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
            // ケースITF
            param.set(ITF, String.valueOf(ent.getValue(Item.ITF, "")));

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
