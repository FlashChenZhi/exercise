// $Id: RetrievalListMntSCH.java,v 1.2 2009/02/24 02:40:25 ose Exp $
package jp.co.daifuku.wms.retrieval.schedule;

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

import static jp.co.daifuku.wms.retrieval.schedule.RetrievalListMntSCHParams.*;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.retrieval.operator.RetrievalOperator;

/**
 * 出庫リスト作業メンテナンスのスケジュール処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:40:25 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class RetrievalListMntSCH
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
    public RetrievalListMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
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
        
        int i = 0;
        try
        {
            RetrievalOperator operator = new RetrievalOperator(getConnection(), this.getClass());

            // パラメータの配列数分キャンセル処理を実行
            for (i = 0; i < ps.length; i++)
            {
                RetrievalInParameter inParam = new RetrievalInParameter(getWmsUserInfo());
                inParam.setSettingUnitKey(ps[i].getString(SETTING_UNIT_KEY));
                operator.cancel(inParam);
            }
            
            // 6121004=設定しました。
            setMessage(WmsMessageFormatter.format(6121004));
            return true;

        }
        catch (LockTimeOutException e)
        {
            // No.{0} 他端末で処理中のため、処理を中断しました。
            setErrorRowIndex(ps[i].getRowIndex());
            setMessage(WmsMessageFormatter.format(6023014, ps[i].getRowIndex()));
            return false;
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode()))
            {
                // No.{0} 他端末で処理されたため、処理を中断しました。
                setErrorRowIndex(ps[i].getRowIndex());
                setMessage(WmsMessageFormatter.format(6023015, ps[i].getRowIndex()));
                return false;
            }
            // その他の場合はそのままthrow
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
        WorkInfoSearchKey searchKey = new WorkInfoSearchKey();

        //出庫予定日
        if (!StringUtil.isBlank(p.getDate(PLAN_DAY)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        }

        // バッチNo
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            searchKey.setBatchNo(p.getString(BATCH_NO));
        }
        
        String[] tmp = WmsFormatter.getFromTo(p.getString(FROM_ORDER_NO), p.getString(TO_ORDER_NO));
        String from = tmp[0];
        String to = tmp[1];
        
        // 開始オーダーNo.
        if (!StringUtil.isBlank(from))
        {
            searchKey.setOrderNo(from, ">=");
        }
        // 終了オーダーNo.
        if (!StringUtil.isBlank(to))
        {
            searchKey.setOrderNo(to, "<=");
        }
        
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 状態フラグ（作業中）
        searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
        // 作業区分（出庫）
        searchKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);
        // ハードウェア区分（リスト）
        searchKey.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);
        // 荷主コード（出荷先マスタ）=　荷主コード（作業情報）
        searchKey.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        // 出荷先コード（出荷先マスタ）= 出荷先コード（作業情報）
        searchKey.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");
        // エリア（エリアマスタ）=　エリア（作業情報）
        searchKey.setJoin(WorkInfo.PLAN_AREA_NO, Area.AREA_NO);

        // 集約
        searchKey.setSettingUnitKeyGroup();
        searchKey.setPlanDayGroup();
        searchKey.setBatchNoGroup();
        searchKey.setOrderNoGroup();
        searchKey.setPlanAreaNoGroup();
        searchKey.setCustomerCodeGroup();
        searchKey.setGroup(Area.AREA_NAME);
        searchKey.setGroup(Customer.CUSTOMER_NAME);

        // ソート順　作業No.、出庫予定日、ﾊﾞｯﾁNo.、ｵｰﾀﾞｰNo.、出庫ｴﾘｱ、出荷先ｺｰﾄﾞ
        searchKey.setSettingUnitKeyOrder(true);
        searchKey.setPlanDayOrder(true);
        searchKey.setBatchNoOrder(true);
        searchKey.setOrderNoOrder(true);
        searchKey.setPlanAreaNoOrder(true);
        searchKey.setCustomerCodeOrder(true);
        
        // 取得項目　設定単位キー、出庫予定日、バッチNO.オーダーNO、出庫エリア、出荷先コード、出庫エリア名称、出荷先名称
        searchKey.setSettingUnitKeyCollect();
        searchKey.setPlanDayCollect();
        searchKey.setBatchNoCollect();
        searchKey.setOrderNoCollect();
        searchKey.setPlanAreaNoCollect();
        searchKey.setCustomerCodeCollect();
        searchKey.setCollect(Area.AREA_NAME);
        searchKey.setCollect(Customer.CUSTOMER_NAME);
        
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

            // リスト作業No.（設定単位キー）
            param.set(SETTING_UNIT_KEY, ent.getSettingUnitKey());
            // 出庫予定日
            param.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // バッチNo.
            param.set(BATCH_NO, ent.getBatchNo());
            // オーダーNo.
            param.set(ORDER_NO, ent.getOrderNo());
            // 出庫エリア
            param.set(PLAN_AREA_NO, ent.getPlanAreaNo());
            // 出庫エリア名称
            param.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            // 出荷先コード
            param.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名
            param.set(CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME, ""));

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
