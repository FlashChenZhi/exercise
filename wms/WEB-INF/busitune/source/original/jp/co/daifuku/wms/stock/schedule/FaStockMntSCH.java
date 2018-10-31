// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.stock.schedule.FaStockMntSCHParams;

/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaStockMntSCH
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
    public FaStockMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
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
        // TODO 処理前のチェックを行う。
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // オペレータパラメータ生成
        xxxInParameter[] inParams = new xxxInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new xxxInParameter();

            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            inParams[i].setEnteringQty(ps[i].getInt(ENTERING_QTY));
        }

        try
        {
            // オペレータ生成
            xxxOperator operator = new xxxOperator(getConnection(), getClass());
            // オペレータ呼び出し
            xxxOutParameter outParam = operator.completeRetrieval(inParams);

            // TODO 画面に合ったメッセージをセットする
            // 開始しました。
            setMessage(WmsMessageFormatter.format(6021021));
            return true;

        }
        //TODO 以下、例外処理。Operatorの処理に合わせてハンドリングを行ってください
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
        catch (RouteException e)
        {
            // TODO AS/RSの場合の例外処理
            // 搬送ルート異常
            setMessage(getRouteErrorMessage(e.getRouteStatus()));
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
                setMessage(WmsMessageFormatter.format(6023015, inParams[e.getErrorLineNo() - 1].getRowIndex()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
                return false;
            }
            // 在庫の引当で不足数が発生した場合
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023189=No.{0} 出庫数には引当可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023189, inParams[e.getErrorLineNo() - 1].getRowIndex()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
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
