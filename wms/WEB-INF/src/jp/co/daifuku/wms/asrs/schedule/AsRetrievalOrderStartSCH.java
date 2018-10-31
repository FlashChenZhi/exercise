// $Id: AsRetrievalOrderStartSCH.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.schedule;

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
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AsWorkInfoController;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.SearchKey;
import static jp.co.daifuku.wms.asrs.schedule.AsRetrievalOrderStartSCHParams.*;

/**
 * AS/RS 出庫開始のスケジュール処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class AsRetrievalOrderStartSCH
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
    public AsRetrievalOrderStartSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
                outParam.set(PRINT_FLAG, webset[0].getValue());
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
    public List<Params> startSCHgetParams(ScheduleParams... ps)
            throws CommonException
    {

        // 日次更新チェック
        if (!canStart())
        {
            return null;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return null;
        }

        // オペレータパラメータ生成
        AsrsInParameter[] inParams = new AsrsInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new AsrsInParameter(getWmsUserInfo());

            inParams[i].setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
            inParams[i].setAreaNo(ps[i].getString(AREA));
            inParams[i].setBatchNo(ps[i].getString(BATCH_NO));
            inParams[i].setCustomerCode(ps[i].getString(CUSTOMER_CODE));
            inParams[i].setCustomerName(ps[i].getString(CUSTOMER_NAME));
            inParams[i].setOrderNo(ps[i].getString(ORDER_NO));
            inParams[i].setRetrievalDay(ps[i].getString(RETRIEVAL_PLAN_DATE));
            inParams[i].setStationNo(ps[i].getString(STATION_NO));
            inParams[i].setStationName(ps[i].getString(STATION_NAME));
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setWorkplace(ps[i].getString(WORK_PLACE));
            inParams[i].setRowNo(ps[i].getInt(LINE_COUNT));
            inParams[i].setErrorAllocCarry(ps[i].getBoolean(ERROR_ALLOC_CARRY));

            // ステーションの出庫作業可能判定
            if (!retrievalStationCheck(ps[i].getString(STATION_NO), ps[i].getInt(LINE_COUNT)))
            {
                setErrorRowIndex(inParams[i].getRowNo());
                return null;
            }
        }

        List<Params> result = new ArrayList<Params>();
        int rowNum = 0;
        try
        {
            // オペレータ生成
            AsrsOperator operator = new AsrsOperator(getConnection(), getClass());

            for (rowNum = 0; rowNum < inParams.length; rowNum++)
            {
                Params param = new Params();
                // オペレータ呼び出し
                AsrsOutParameter outParam = operator.webStartRetrieval(inParams[rowNum]);
                param.set(SETTING_UKEYS, outParam.getSettingUnitKey());

                result.add(param);
            }

            // 設定しました。
            setMessage(WmsMessageFormatter.format(6001006));

            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(PRINT_FLAG) ? WebSetting.KEYDATA_ON : WebSetting.KEYDATA_OFF;
            updateWebSetting(getUserInfo().getTerminalNumber(), ps[0].getString(FUNCTION_ID), value);

            return result;

        }
        catch (LockTimeOutException e)
        {
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));
            return null;
        }
        catch (NotFoundException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015));
            return null;
        }
        catch (RouteException e)
        {
            // 搬送ルート異常
            setMessage(getRouteErrorMessage(e.getRouteStatus()));
            return null;
        }
        catch (OperatorException e)
        {
            //「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParams[rowNum].getRowNo()));
                setErrorRowIndex(inParams[rowNum].getRowNo());
                return null;
            }
            else if (OperatorException.ERR_EXIST_UNSTART_CARRYINFO.equals(e.getErrorCode()))
            {
                // 6023251=No.{0} 開始対象よりも先に引当てたもので、開始されていない搬送作業があったため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023251, inParams[rowNum].getRowNo()));
                setErrorRowIndex(inParams[rowNum].getRowNo());
                return null;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }

    // DFKLOOK 3.5 ADD START
    /**
     * パラメータリストを帳票出力順にソートして返します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>のリスト。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return ソート結果のパラメータリスト
     */
    public List<Params> sortPrintParams(List<Params> ps)
            throws CommonException
    {
        if (ps == null || ps.isEmpty())
        {
            return null;
        }

        WorkInfoHandler handler = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        int paramCount = ps.size();
        for (int i = 0; i < paramCount; i++)
        {
            Params pNextparam = ps.get(i);
            // 設定単位キーが1件の場合
            if (paramCount == 1)
            {
                // 設定単位キー
                key.setSettingUnitKey(pNextparam.getString(SETTING_UKEYS), "=", "", "", true);
            }
            // データの1件目
            else if (i == 0)
            {
                // 設定単位キー
                key.setSettingUnitKey(pNextparam.getString(SETTING_UKEYS), "=", "(", "", false);
            }
            // データの最後
            else if (i == paramCount - 1)
            {
                // 設定単位キー
                key.setSettingUnitKey(pNextparam.getString(SETTING_UKEYS), "=", "", ")", true);
            }
            // データの途中
            else
            {
                // 設定単位キー
                key.setSettingUnitKey(pNextparam.getString(SETTING_UKEYS), "=", "", "", false);
            }
        }

        //結合条件
        key.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);

        // ソート順
        key.setOrder(CarryInfo.DEST_STATION_NO, true);
        key.setBatchNoOrder(true);
        key.setOrderNoOrder(true);
        key.setSettingUnitKeyOrder(true);

        // グループ、取得項目
        key.setCollect(WorkInfo.CONSIGNOR_CODE);
        key.setCollect(WorkInfo.SETTING_UNIT_KEY);
        key.setCollect(WorkInfo.PLAN_AREA_NO);
        key.setCollect(CarryInfo.DEST_STATION_NO);
        key.setCollect(WorkInfo.PLAN_DAY);
        key.setCollect(WorkInfo.BATCH_NO);
        key.setCollect(WorkInfo.ORDER_NO);
        key.setCollect(WorkInfo.CUSTOMER_CODE);

        key.setGroup(WorkInfo.CONSIGNOR_CODE);
        key.setGroup(WorkInfo.SETTING_UNIT_KEY);
        key.setGroup(WorkInfo.PLAN_AREA_NO);
        key.setGroup(CarryInfo.DEST_STATION_NO);
        key.setGroup(WorkInfo.PLAN_DAY);
        key.setGroup(WorkInfo.BATCH_NO);
        key.setGroup(WorkInfo.ORDER_NO);
        key.setGroup(WorkInfo.CUSTOMER_CODE);

        WorkInfo[] works = (WorkInfo[])handler.find(key);

        // オペレータパラメータ生成
        List<Params> printParams = new ArrayList<Params>();

        for (WorkInfo work : works)
        {
            Params param = new Params();
            param.set(CONSIGNOR_CODE, work.getValue(WorkInfo.CONSIGNOR_CODE).toString());
            param.set(SETTING_UKEYS, work.getValue(WorkInfo.SETTING_UNIT_KEY).toString());
            param.set(AREA, work.getValue(WorkInfo.PLAN_AREA_NO).toString());
            param.set(STATION_NO, work.getValue(CarryInfo.DEST_STATION_NO).toString());
            param.set(RETRIEVAL_PLAN_DATE, WmsFormatter.toDate(work.getValue(WorkInfo.PLAN_DAY).toString()));
            param.set(BATCH_NO, work.getValue(WorkInfo.BATCH_NO).toString());
            param.set(ORDER_NO, work.getValue(WorkInfo.ORDER_NO).toString());
            param.set(CUSTOMER_CODE, work.getValue(WorkInfo.CUSTOMER_CODE).toString());
            printParams.add(param);
        }

        return printParams;
    }
    // DFKLOOK 3.5 ADD END

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
     * @exception CommonException 例外を返します。
     */
    protected SearchKey createSearchKey(ScheduleParams p)
            throws CommonException
    {
        WorkInfoSearchKey searchKey = new WorkInfoSearchKey();

        WorkInfo workInfo = new WorkInfo();
        String destStation = null;

        // AsWorkInfoControllerのインスタンスを生成する。
        AsWorkInfoController asWorkInfo = new AsWorkInfoController(getConnection(), this.getClass());

        //必須入力項目セット
        workInfo.setConsignorCode(p.getString(CONSIGNOR_CODE));
        workInfo.setPlanDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_PLAN_DATE)));

        // エリアNo
        workInfo.setPlanAreaNo(p.getString(AREA));

        //バッチNo.をセット
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            workInfo.setBatchNo(p.getString(BATCH_NO));
        }

        //ステーションNo.をセットする。
        if (!StringUtil.isBlank(p.getString(STATION)) && !p.getString(STATION).equals(WmsParam.ALL_STATION))
        {
            destStation = p.getString(STATION);
        }

        //オーダーNo.をセットする。
        String orderNo = null;
        String toOrderNo = null;

        if (!StringUtil.isBlank(p.getString(ORDER_NO_FROM)))
        {
            orderNo = p.getString(ORDER_NO_FROM);
            if (!StringUtil.isBlank(p.getString(ORDER_NO_TO)))
            {
                toOrderNo = p.getString(ORDER_NO_TO);
            }
        }
        else if (!StringUtil.isBlank(p.getString(ORDER_NO_TO)))
        {
            toOrderNo = p.getString(ORDER_NO_TO);
        }

        searchKey = (WorkInfoSearchKey)asWorkInfo.getRetrievalWorkKey(workInfo, destStation, orderNo, toOrderNo);
        searchKey.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS);


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
        int i = 1;
        for (WorkInfo ent : entities)
        {
            Params param = new Params();

            // 行数
            param.set(LINE_COUNT, i);
            // バッチNo.
            param.set(BATCH_NO, ent.getBatchNo());
            // オーダーNo.
            param.set(ORDER_NO, ent.getOrderNo());
            // 出荷先コード
            param.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            param.set(CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME, ""));
            // 明細数
            param.set(DETAIL_COUNT, ent.getPlanQty());
            // ｽﾃｰｼｮﾝNo.
            param.set(STATION_NO, ent.getValue(CarryInfo.DEST_STATION_NO, ""));
            // ｽﾃｰｼｮﾝ名称
            param.set(STATION_NAME, ent.getValue(Station.STATION_NAME, ""));

            //更新時使う値をセット
            param.set(AREA, ent.getPlanAreaNo());
            result.add(param);
            i++;
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
