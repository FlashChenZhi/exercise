package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.storage.schedule.StorageListStartSCHParams.*;

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
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.storage.operator.StorageOperator;

/**
 * 入庫リスト作業開始のスケジュール処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:47:37 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class StorageListStartSCH
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
    public StorageListStartSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new WorkInfoFinder(getConnection());
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
        StorageInParameter[] inParams = new StorageInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new StorageInParameter(getWmsUserInfo());

            // 荷主コード
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            // 予定日
            inParams[i].setStoragePlanDay(WmsFormatter.toParamDate(ps[i].getDate(PLAN_DAY)));
            // 商品コード
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            // 行No.
            inParams[i].setRowNo(ps[i].getRowIndex());
        }

        // 入庫オペレータ生成
        StorageOperator operator = new StorageOperator(getConnection(), this.getClass());
        List<OutParameter> settingUnitKeyList = new ArrayList<OutParameter>();

        int rowNum = 0;
        try
        {
            for (rowNum = 0; rowNum < inParams.length; rowNum++)
            {
                // 入庫作業開始
                settingUnitKeyList.add(operator.webStart(inParams[rowNum]));
            }

            Iterator<OutParameter> it = settingUnitKeyList.iterator();

            for (int i = 0; it.hasNext(); i++)
            {
                StorageOutParameter param = (StorageOutParameter)it.next();
                ps[i].set(SETTING_UKEY, param.getSettingUnitKey());
            }

            // 開始しました。
            setMessage(WmsMessageFormatter.format(6021021));
            
            
            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(ISSUE_REPORT) ? WebSetting.KEYDATA_ON : WebSetting.KEYDATA_OFF;

            updateWebSetting(getUserInfo().getTerminalNumber(), ps[0].getString(FUNCTION_ID), value);
           
            
            return true;

        }
        catch (LockTimeOutException e)
        {
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));
            return false;
        }
        catch (NotFoundException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015));
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
                setMessage(WmsMessageFormatter.format(6023015, inParams[rowNum].getRowNo()));
                setErrorRowIndex(ps[rowNum].getRowIndex());
                return false;
            }
            // 在庫の引当で不足数が発生した場合
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023189=No.{0} 出庫数には引当可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023189, inParams[rowNum].getRowNo()));
                setErrorRowIndex(ps[rowNum].getRowIndex());
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
        WorkInfoSearchKey searchKey = new WorkInfoSearchKey();

        /* 検索条件の指定 */
        // 入庫予定日
        if (!StringUtil.isBlank(p.getString(PLAN_DAY)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        }

        String[] loc = WmsFormatter.getFromTo(p.getString(ITEM_CODE), p.getString(TO_ITEM_CODE));

        // 商品コード(From)
        if (!StringUtil.isBlank(loc[0]))
        {
            searchKey.setItemCode(loc[0], ">=");
        }
        // 商品コード(To)
        if (!StringUtil.isBlank(loc[1]))
        {
            searchKey.setItemCode(loc[1], "<=");
        }

        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 状態フラグ(未作業)
        searchKey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
        // 作業区分(入庫)
        searchKey.setJobType(WorkInfo.JOB_TYPE_STORAGE);

        /* 結合条件の指定 */
        // 荷主コード
        searchKey.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        searchKey.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);

        /* 取得項目と集約条件の指定 */

        // 入庫予定日
        searchKey.setPlanDayCollect();
        searchKey.setPlanDayGroup();
        // 商品コード
        searchKey.setItemCodeCollect();
        searchKey.setItemCodeGroup();
        // 商品名称
        searchKey.setCollect(Item.ITEM_NAME);
        searchKey.setGroup(Item.ITEM_NAME);
        // 明細数(count())
        searchKey.setJobNoCollect("COUNT");

        /* ソート順の指定 */

        // 入庫予定日
        searchKey.setPlanDayOrder(true);
        // 商品コード
        searchKey.setItemCodeOrder(true);

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
        WorkInfo[] entities = (WorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (WorkInfo ent : entities)
        {
            Params param = new Params();

            // 入庫予定日
            param.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // 明細数
            param.set(DETAIL_COUNT, Formatter.getLong(ent.getJobNo()));

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
