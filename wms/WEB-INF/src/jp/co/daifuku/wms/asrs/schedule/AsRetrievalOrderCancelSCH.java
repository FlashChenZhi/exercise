// $Id: AsRetrievalOrderCancelSCH.java 7533 2010-03-13 08:45:32Z ota $
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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AsWorkInfoController;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.SearchKey;
import static jp.co.daifuku.wms.asrs.schedule.AsRetrievalOrderCancelSCHParams.*;

/**
 * AS/RS 出庫キャンセルのスケジュール処理を行います。
 *
 * @version $Revision: 7533 $, $Date: 2010-03-13 17:45:32 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class AsRetrievalOrderCancelSCH
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
    public AsRetrievalOrderCancelSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        // オペレータパラメータ生成
        AsrsInParameter[] inParams = new AsrsInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new AsrsInParameter(getWmsUserInfo());
            // ステーションNo.よりインスタンス取得
            Station station = getStation(ps[i].getString(STATION_NO));
            // 中断中フラグのチェック
            if (Station.SUSPEND_OFF.equals(station.getSuspend()))
            {
                // 6023238 = No.{0} ステーション状態が中断中でなければ、キャンセルできません。
                String msg = WmsMessageFormatter.format(6023238, ps[i].getInt(LINE_COUNT));
                setErrorRowIndex(ps[i].getInt(LINE_COUNT));
                setMessage(msg);
                return false;
            }

            inParams[i].setBatchNo(ps[i].getString(BATCH_NO));
            inParams[i].setOrderNo(ps[i].getString(ORDER_NO));
            inParams[i].setToOrderNo(ps[i].getString(ORDER_NO_TO));
            inParams[i].setCustomerCode(ps[i].getString(CUSTOMER_CODE));
            inParams[i].setCustomerName(ps[i].getString(CUSTOMER_NAME));
            inParams[i].setStationNo(ps[i].getString(STATION_NO));
            inParams[i].setStationName(ps[i].getString(STATION_NAME));
            inParams[i].setAreaNo(ps[i].getString(AREA));
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setToOrderNo(ps[i].getString(ORDER_NO_TO));
            inParams[i].setRetrievalDay(WmsFormatter.toParamDate(ps[i].getDate(RETRIEVAL_PLAN_DATE)));
            inParams[i].setRowNo(ps[i].getInt(LINE_COUNT));


        }

        for (AsrsInParameter inParam : inParams)
        {
            // ステーションNo.よりインスタンス取得
            Station station = getStation(inParam.getStationNo());

            // 中断中フラグのチェック
            if (Station.SUSPEND_OFF.equals(station.getSuspend()))
            {
                // 6023238 = No.{0} ステーション状態が中断中でなければ、キャンセルできません。
                String msg = WmsMessageFormatter.format(6023238, inParam.getRowNo());
                setMessage(msg);
                setErrorRowIndex(inParam.getRowNo());
                return false;
            }
        }

        // オペレータ生成
        AsrsOperator operator = new AsrsOperator(getConnection(), getClass());

        int rowNum = 0;
        try
        {
            for (rowNum = 0; rowNum < inParams.length; rowNum++)
            {
                // オペレータ呼び出し
                operator.cancelRetrieval(inParams[rowNum]);
            }

            //6001006=設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
            return true;

        }
        catch (LockTimeOutException e)
        {
            //他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, inParams[rowNum].getRowNo()));
            setErrorRowIndex(inParams[rowNum].getRowNo());
            return false;
        }
        catch (NotFoundException e)
        {
            //他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, inParams[rowNum].getRowNo()));
            setErrorRowIndex(inParams[rowNum].getRowNo());
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
            throws CommonException
    {
        WorkInfoSearchKey searchKey = new WorkInfoSearchKey();

        WorkInfo workInfo = new WorkInfo();
        String destStation = null;
        String cmdStaus = null;

        // AsWorkInfoControllerのインスタンスを生成する。
        AsWorkInfoController asWorkInfo = new AsWorkInfoController(getConnection(), this.getClass());


        //必須入力項目セット
        // 荷主コード
        workInfo.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 出庫予定日
        workInfo.setPlanDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_PLAN_DATE)));
        // 状態フラグ(作業中)
        workInfo.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);

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

        // 搬送状態フラグをセットする。
        cmdStaus = WorkInfo.CMD_STATUS_START;

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

        searchKey = (WorkInfoSearchKey)asWorkInfo.createRetrievalStatusKey(workInfo, destStation, cmdStaus);

        // ハードウェア区分(ASRS)
        searchKey.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS);

        // condition of order number
        searchKey.setRangeKey(WorkInfo.ORDER_NO, orderNo, toOrderNo, true);

        searchKey.setCollect(WorkInfo.BATCH_NO);
        searchKey.setCollect(WorkInfo.ORDER_NO);
        searchKey.setCollect(WorkInfo.CUSTOMER_CODE);

        searchKey.setCollect(Customer.CUSTOMER_NAME);
        searchKey.setCollect(CarryInfo.DEST_STATION_NO);
        searchKey.setCollect(Station.STATION_NAME);

        // get number of record at group
        searchKey.setCollect(WorkInfo.ITEM_CODE, "COUNT", WorkInfo.PLAN_QTY);

        // Group by
        searchKey.setGroup(WorkInfo.BATCH_NO);
        searchKey.setGroup(WorkInfo.ORDER_NO);
        searchKey.setGroup(WorkInfo.CUSTOMER_CODE);
        searchKey.setGroup(Customer.CUSTOMER_NAME);
        searchKey.setGroup(CarryInfo.DEST_STATION_NO);
        searchKey.setGroup(Station.STATION_NAME);

        // Join
        searchKey.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        searchKey.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");
        searchKey.setJoin(CarryInfo.DEST_STATION_NO, "", Station.STATION_NO, "");

        // Sort order
        searchKey.setOrder(WorkInfo.BATCH_NO, true);
        searchKey.setOrder(WorkInfo.ORDER_NO, true);
        searchKey.setOrder(WorkInfo.CUSTOMER_CODE, true);
        searchKey.setOrder(CarryInfo.DEST_STATION_NO, true);


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

            i++;

            result.add(param);
        }

        return result;
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
