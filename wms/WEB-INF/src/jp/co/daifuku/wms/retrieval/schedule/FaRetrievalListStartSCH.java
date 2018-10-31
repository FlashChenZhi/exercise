// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.retrieval.schedule.FaRetrievalListStartSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.retrieval.operator.RetrievalOperator;


/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaRetrievalListStartSCH
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
    public FaRetrievalListStartSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 出庫リスト作業開始のスケジュール処理を行います。
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

            // 明細数取得用フィールド
            FieldName p_count = new FieldName(WorkInfo.STORE_NAME, "COUNT");

            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p, p_count))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder, p_count);
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
    public List<Params> startSCHgetParams(ScheduleParams... ps)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return null;
        }

        RetrievalInParameter[] inParam = new RetrievalInParameter[ps.length];

        for (int i = 0; i < ps.length; i++)
        {
            FaRetrievalListStartSCHParams startParams = (FaRetrievalListStartSCHParams)ps[i];
            inParam[i] = new RetrievalInParameter(getWmsUserInfo());

            inParam[i].setRowNo(startParams.getRowIndex());
            inParam[i].setConsignorCode(startParams.getString(CONSIGNOR_CODE));
            inParam[i].setBatchNo(startParams.getString(BATCH_NO));
            // FAのオーダーNo. = バッチNo.
            inParam[i].setOrderNo(startParams.getString(BATCH_NO));
            inParam[i].setRetrievalAreaNo(startParams.getString(AREA_NO));
        }

        // 出庫オペレータの作成
        RetrievalOperator retrievalOpe = new RetrievalOperator(getConnection(), this.getClass());

        List<OutParameter> keyList = new ArrayList<OutParameter>();
        List<Params> result = new ArrayList<Params>();

        int rowNum = 0;
        try
        {
            for (rowNum = 0; rowNum < inParam.length; rowNum++)
            {
                // 出庫作業開始
                keyList.add(retrievalOpe.webStart(inParam[rowNum]));
            }

            // 設定単位キーの取得
            Iterator<OutParameter> it = keyList.iterator();

            while (it.hasNext())
            {
                RetrievalOutParameter param = (RetrievalOutParameter)it.next();

                Params retparam = new Params();

                // 返却データをセットする
                // 画面設定キー
                retparam.set(FaRetrievalListStartSCHParams.SETTING_UNIT_KEY, param.getSettingUnitKey());

                result.add(retparam);
            }
            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(PRINT_FLAG) ? WebSetting.KEYDATA_ON
                                                       : WebSetting.KEYDATA_OFF;
            
            updateWebSetting(getTerminalNo(), ps[0].getString(FUNCTION_ID), value);
            
            return result;

        }
        catch (LockTimeOutException e)
        {
            // No. {0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, inParam[rowNum].getRowNo()));
            // エラー行の設定
            setErrorRowIndex(inParam[rowNum].getRowNo());
            return null;
        }
        catch (NotFoundException e)
        {
            // No. {0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, inParam[rowNum].getRowNo()));
            // エラー行の設定
            setErrorRowIndex(inParam[rowNum].getRowNo());
            return null;
        }
        catch (OperatorException e)
        {
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParam[rowNum].getRowNo()));
                // エラー行の設定
                setErrorRowIndex(inParam[rowNum].getRowNo());
                return null;
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
    protected SearchKey createSearchKey(ScheduleParams p, FieldName cntField)
    {

        WorkInfoSearchKey searchKey = new WorkInfoSearchKey();

        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }

        // バッチNo.
        String[] tmp = WmsFormatter.getFromTo(p.getString(FROM_BATCH_NO), p.getString(TO_BATCH_NO));
        String from = tmp[0];
        String to = tmp[1];

        if (!StringUtil.isBlank(from))
        {
            searchKey.setOrderNo(from, ">=");
        }
        if (!StringUtil.isBlank(to))
        {
            searchKey.setOrderNo(to, "<=");
        }

        // 状態フラグ(未作業)
        searchKey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);

        // ハードウェア区分(未作業)
        searchKey.setHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);

        // 作業区分(出庫)
        searchKey.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);

        // 結合条件
        searchKey.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        searchKey.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        searchKey.setJoin(WorkInfo.PLAN_AREA_NO, Area.AREA_NO);

        // 取得条件
        // バッチNo.
        searchKey.setBatchNoCollect();
        // エリアNo.
        searchKey.setPlanAreaNoCollect();
        // エリア名称
        searchKey.setCollect(Area.AREA_NAME);
        // 明細数
        searchKey.setCollect(WorkInfo.STOCK_ID, "COUNT(UNIQUE{0})", cntField);

        // 集約条件
        // バッチNo.
        searchKey.setBatchNoGroup();
        // エリアNo.
        searchKey.setPlanAreaNoGroup();
        // エリア名称
        searchKey.setGroup(Area.AREA_NAME);

        // ソート順
        // バッチNo.
        searchKey.setBatchNoOrder(true);
        // エリアNo.
        searchKey.setPlanAreaNoOrder(true);

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder, FieldName cntField)
    throws ReadWriteException
	{
    	// 最大表示件数分検索結果を取得する
		WorkInfo[] workinfo = (WorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
		List<Params> result = new ArrayList<Params>();

		for(WorkInfo ent : workinfo)
		{
			Params param = new Params();

			// 返却データをセットする
			// バッチNo.
			param.set(BATCH_NO, ent.getBatchNo());
			// エリアNo.
			param.set(AREA_NO, ent.getPlanAreaNo());
			// エリア名称
			param.set(AREA_NAME, (String)ent.getValue(Area.AREA_NAME, ""));
			// 明細数
			param.set(NO_OF_RECORDS, ent.getBigDecimal(cntField).longValue());

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

    // ------------------------------------------------------------
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
