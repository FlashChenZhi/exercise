package jp.co.daifuku.wms.receiving.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.receiving.schedule.TcReceivingCompleteSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.receiving.operator.TcReceivingOperator;

/**
 * TC入荷一括確定のスケジュール処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:36:04 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class TcReceivingCompleteSCH
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
    public TcReceivingCompleteSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、TC入荷一括確定のスケジュールを開始します。<BR>
     * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。<BR>
     *
     * @param startParams 設定内容を持つ<CODE>ReceivingInParameter</CODE>クラスのインスタンスの配列。 <BR>
     * @return 正常に処理終了 true、なんらかの理由で処理が終了できなかった false
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    public boolean startSCH(Params startParams)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        try
        {

            TcReceivingCompleteSCHParams startParam = (TcReceivingCompleteSCHParams)startParams;

            TcReceivingInParameter sParam = new TcReceivingInParameter(getWmsUserInfo());

            // 荷主コード
            sParam.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 入荷予定日
            sParam.setPlanDay(WmsFormatter.toParamDate(startParam.getDate(PLAN_DAY)));
            // 仕入先コード
            sParam.setSupplierCode(startParam.getString(SUPPLIER_CODE));

            // ここから
            // nakai:実績送信情報をinsertするため仕方ありません。
            // 岡村さんの許可済み
            // WMSユーザ情報
            DfkUserInfo dui = (DfkUserInfo)this.getUserInfo();
            WmsUserInfo wui = new WmsUserInfo();
            wui.setDfkUserInfo(dui);
            wui.setTerminalNo(dui.getTerminalNumber());
            wui.setUserId(dui.getUserId());
            sParam.setWmsUserInfo(wui);
            // ここまで

            // オペレータ
            TcReceivingOperator operator = new TcReceivingOperator(getConnection(), getClass());

            // 一括確定処理
            OutParameter outParam = operator.webComplete((TcReceivingInParameter)sParam);

            if (outParam == null)
            {
                // 6003011 = 対象データはありませんでした。
            	setMessage(WmsMessageFormatter.format(6003011));
            	return false;
            }
            
            // 6001015 = 確定しました。
            setMessage(WmsMessageFormatter.format(6001015));
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
            setMessage(WmsMessageFormatter.format(6023115));
            return false;
        }
        catch (OperatorException e)
        {
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 6023115=他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023115));
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
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
