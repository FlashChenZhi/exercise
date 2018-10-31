package jp.co.daifuku.wms.ship.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.ship.schedule.ShipLoadSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.ship.operator.ShipOperator;

/**
 * 出荷積込のスケジュール処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:42:33 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class ShipLoadSCH
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
    public ShipLoadSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new ShipWorkInfoFinder(getConnection());
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

        // オペレータパラメータ生成
        ShipInParameter[] inParams = new ShipInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new ShipInParameter(getWmsUserInfo());

            inParams[i].setRowNo(ps[i].getRowIndex());
            // 出荷予定日
            inParams[i].setPlanDay(WmsFormatter.toParamDate(ps[i].getDate(PLAN_DAY)));
            // 荷主コード
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            // 出荷先コード
            inParams[i].setCustomerCode(ps[i].getString(CUSTOMER_CODE));
            // 伝票No.
            inParams[i].setTicketNo(ps[i].getString(TICKET_NO));
            // 検品済フラグ
            inParams[i].setInspectionFlag(ps[i].getBoolean(ONLY_COMPLETED_INSPECTION));
            // バースNo.
            inParams[i].setBerthNo(ps[i].getString(BERTH_NO));
            // バッチNo.
            inParams[i].setBatchNo(ps[i].getString(BATCH_NO));
        }

        int i = 0;
        try
        {
            // オペレータ生成
            ShipOperator operator = new ShipOperator(getConnection(), getClass());

            for (i = 0; i < inParams.length; i++)
            {
                // オペレータ呼び出し
                ShipOutParameter outParam = operator.webCompleteShipLoad(inParams[i]);

                ps[i].set(SETTING_UNIT_KEY, outParam.getSettingUnitKey());
            }

            // 6001014=完了しました。
            setMessage("6001014");

            return true;

        }
        catch (LockTimeOutException e)
        {
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));
            setErrorRowIndex(ps[i].getRowIndex());
            return false;
        }
        catch (NotFoundException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, inParams[i].getRowNo()));
            setErrorRowIndex(ps[i].getRowIndex());
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
                setMessage(WmsMessageFormatter.format(6023015, inParams[i].getRowNo()));
                setErrorRowIndex(ps[i].getRowIndex());
                return false;
            }
            // 在庫の引当で不足数が発生した場合
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023189=No.{0} 出庫数には引当可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023189, inParams[i].getRowNo()));
                setErrorRowIndex(ps[i].getRowIndex());
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
     * @return ShipWorkInfoSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();

        // 副問合せ
        CustomerSearchKey key = new CustomerSearchKey();
        // 荷主コード
        key.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // ルート
        if (!StringUtil.isBlank(p.getString(ROUTE)))
        {
            key.setRoute(p.getString(ROUTE));
        }

        // 出荷先コード
        String[] tmp = WmsFormatter.getFromTo(p.getString(CUSTOMER_CODE), p.getString(TO_CUSTOMER_CODE));
        String from = tmp[0];
        String to = tmp[1];

        // 出荷先コード(FROM)
        if (!StringUtil.isBlank(from))
        {
            key.setCustomerCode(from, ">=");
        }
        // 出荷先コード(TO)
        if (!StringUtil.isBlank(to))
        {
            key.setCustomerCode(to, "<=");
        }

        key.setCustomerCodeCollect();

        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 出荷予定日
        if (!StringUtil.isBlank(p.getString(PLAN_DAY)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            searchKey.setBatchNo(p.getString(BATCH_NO));
        }

        // 伝票No.
        tmp = WmsFormatter.getFromTo(p.getString(TICKET_NO), p.getString(TO_TICKET_NO));
        from = tmp[0];
        to = tmp[1];

        // 伝票No.(FROM)
        if (!StringUtil.isBlank(from))
        {
            searchKey.setShipTicketNo(from, ">=");
        }
        // 伝票No.(TO)
        if (!StringUtil.isBlank(to))
        {
            searchKey.setShipTicketNo(to, "<=");
        }
        // 検品済のみ
        if (p.getBoolean(ONLY_COMPLETED_INSPECTION))
        {
            searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_COMPLETION);
        }
        else
        {
            searchKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING, "!=");
        }
        searchKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);

        searchKey.setKey(ShipWorkInfo.CUSTOMER_CODE, key);
        // 荷主コード
        searchKey.setJoin(ShipWorkInfo.CONSIGNOR_CODE, Customer.CONSIGNOR_CODE);
        // 商品コード
        searchKey.setJoin(ShipWorkInfo.CUSTOMER_CODE, Customer.CUSTOMER_CODE);

        // 集約条件
        searchKey.setCustomerCodeGroup();
        searchKey.setShipTicketNoGroup();
        searchKey.setGroup(Customer.CUSTOMER_NAME);

        // 表示順
        searchKey.setCustomerCodeOrder(true);
        searchKey.setShipTicketNoOrder(true);

        // 取得項目
        searchKey.setCustomerCodeCollect();
        searchKey.setShipTicketNoCollect();
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
        ShipWorkInfo[] entities = (ShipWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (ShipWorkInfo ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする
            // 出荷先コード
            param.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            param.set(CUSTOMER_NAME, String.valueOf(ent.getValue(Customer.CUSTOMER_NAME, "")));
            // 伝票No.
            param.set(TICKET_NO, ent.getShipTicketNo());

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
