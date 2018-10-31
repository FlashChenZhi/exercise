// $Id: AsSimpleDirectTransferSCH.java 6779 2010-01-22 03:01:14Z shibamoto $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.schedule.AsSimpleDirectTransferSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.exception.RouteException;

/**
 * AS/RS 簡易直行設定のスケジュール処理を行います。
 *
 * @version $Revision: 6779 $, $Date:: 2010-01-22 12:01:14 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsSimpleDirectTransferSCH
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
    public AsSimpleDirectTransferSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        AsrsInParameter inParam = new AsrsInParameter(getWmsUserInfo());

        // エリアNo.
        inParam.setAreaNo(ps[0].getString(AREA));
        // 搬送元ステーションNo.
        inParam.setStorageStationNo(ps[0].getString(SOURCE_STATION_NO));
        // 搬送先ステーションNo.
        inParam.setRetrievalStationNo(ps[0].getString(DEST_STATION_NO));
        // 荷主コード
        inParam.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
        // 商品コード            
        inParam.setItemCode(ps[0].getString(ITEM_CODE));
        // 予定数
        inParam.setPlanQty(ps[0].getInt(PLAN_QTY));

        // 処理条件チェックを行う
        if (!checkparam(inParam, getConnection()))
        {
            return false;
        }
        
        try
        {
            // オペレータ生成
            AsrsOperator operator = new AsrsOperator(getConnection(), getClass());
            // オペレータ呼び出し
            operator.webStartDirectTransfer(inParam);

            // 6001006=設定しました。
            setMessage("6001006");
            return true;

        }
        catch (LockTimeOutException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
        }
        catch (NotFoundException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        catch (DataExistsException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        catch (RouteException e)
        {
            // 搬送ルート異常
            setMessage(getRouteErrorMessage(e.getRouteStatus()));
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
     * 処理条件チェックを行います。<BR>
     * @return 処理条件 OKの場合true、NGの場合falseを返します。<BR>
     * @param param AS/RS入力パラメータ
     * @param conn コネクション
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkparam(AsrsInParameter param, Connection conn)
            throws CommonException
    {
        // 入庫作業可能判定
        if (!storageStationCheck(param.getStorageStationNo()))
        {
            return false;
        }
        
        // 出庫作業可能判定
        if (!retrievalStationCheck(param.getRetrievalStationNo()))
        {
            return false;
        }

        return true;
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
