// $Id: PCTAutoUpdateProcess.java 4137 2009-04-13 05:17:19Z okamura $
package jp.co.daifuku.pcart.base.util;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.PCTUserResult;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.sessionmanage.SessionManage;
import jp.co.daifuku.wms.handler.db.SysDate;


/**
 * PCTプロセス終了処理<br>
 * ①DNRFTの起動中端末を停止<BR>
 * ②終了日時を取得し、終了日時、作業時間を更新
 * ③セッションのLOG OFF
 *
 *
 * @version $Revision: 4137 $, $Date: 2009-04-13 14:17:19 +0900 (月, 13 4 2009) $
 * @author  K49517
 * @author  Last commit: $Author: okamura $
 */


public class PCTAutoUpdateProcess
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /**
     * クラス名
     */
    private static final String CLASS_NAME = "PCTStopProcess";


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * TODO default constructor.
     */
    public PCTAutoUpdateProcess()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * WMS作業日更新モジュールを起動
     * @param args (未使用)
     */
    public static void main(String args[])
    {
        // コネクション
        Connection conn = null;

        try
        {
            // コネクション取得
            conn = WmsParam.getConnection();
            System.out.println("**** PCT Update Process!! ****");
            // このクラスのインスタンスを生成
            PCTAutoUpdateProcess pctStop = new PCTAutoUpdateProcess();

            // RFT端末起動停止
            String[] termNo = pctStop.updateRft(conn);
            if (termNo == null || termNo.length == 0)
            {
                conn.rollback();
            }
            // 正常に更新が終了した場合
            else
            {
                // コミット
                conn.commit();
            }

            // ユーザー実績
            if (termNo != null && termNo.length != 0)
            {
                if (pctStop.updatePctUser(conn, termNo))
                {
                    // コミット
                    conn.commit();
                }
                else
                {
                    conn.rollback();
                }
            }

            // セッション管理
            if (pctStop.sessionManage())
            {
                // コミット
                conn.commit();
            }
            else
            {
                conn.rollback();
            }
        }
        catch (Exception ex)
        {
            // 6006036=修正できませんでした。
            RmiMsgLogClient.write(new TraceHandler(6006036, ex), CLASS_NAME);
            // ロールバック
            try
            {
                conn.rollback();
            }
            catch (SQLException esql)
            {
                // エラーをログファイルに落とす
                RmiMsgLogClient.write(new TraceHandler(6006002, esql), CLASS_NAME);
            }
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                    conn = null;
                }
            }
            catch (SQLException ex)
            {
                // エラーをログファイルに落とす
                RmiMsgLogClient.write(new TraceHandler(6006002, ex), CLASS_NAME);
            }
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

    /**
     * 起動中の端末を停止する。
     * 作業中のDMRFTのUPDATEを行うか選択する。
     * 
     * @param conn データベースコネクション
     * 
     * @exception ReadWriteException
     * @exception ScheduleException
     * @exception NotFoundException
     * @return String[] 端末No.
     */
    public String[] updateRft(Connection conn)
            throws ReadWriteException,
                ScheduleException,
                NotFoundException
    {
        RftSearchKey skey = new RftSearchKey();
        RftHandler handler = new RftHandler(conn);
        skey.setStatusFlag(SystemDefine.RFT_STATUS_FLAG_START);
        skey.setSettingUnitKey("");

        if (handler.count(skey) == 0)
        {
            return null;
        }
        Rft[] rft = (Rft[])handler.find(skey);

        List<String> termList = new ArrayList<String>();

        RftAlterKey aKey = new RftAlterKey();
        for (Rft param : rft)
        {
            aKey.clearKeys();
            // 端末号機No.
            aKey.setRftNo(param.getRftNo());

            // 更新値設定
            // ユーザID
            aKey.updateUserId(null);
            // 作業区分(未作業)
            aKey.updateJobType(Rft.JOB_TYPE_UNSTART);
            // 状態フラグ(停止中)
            aKey.updateStatusFlag(Rft.RFT_STATUS_FLAG_STOP);
            // 無線状態フラグ(無線エリア内)
            aKey.updateRadioFlag(Rft.RADIO_FLAG_IN);
            // 休憩開始日時
            aKey.updateRestStartDate(null);
            // 荷主コード
            aKey.updateConsignorCode(null);
            // エリア
            aKey.updateAreaNo(null);
            // バッチ
            aKey.updateBatchSeqNo(null);
            // 設定単位キー
            aKey.updateSettingUnitKey(null);
            // 最終更新処理名
            aKey.updateLastUpdatePname(this.getClass().getSimpleName());

            // RFT管理情報更新
            handler.modify(aKey);

            termList.add(param.getRftNo());
        }

        String[] termNo = new String[termList.size()];
        termList.toArray(termNo);

        return termNo;
    }

    /**
     * PCTユーザ実績情報を更新する
     * @param conn データベースコネクション
     * @param termNo 起動中かつ作業途中の作業情報がない端末No 
     * @return true:正常完了 false:異常終了
     * 
     * @throws ScheduleException 
     * @throws ReadWriteException 
     * 
     * 
     */
    public boolean updatePctUser(Connection conn, String[] termNo)
            throws ReadWriteException,
                ScheduleException
    {
        // PCTユーザ実績情報ハンドラのインスタンス生成
        PCTUserResultHandler pctUserHndlr = new PCTUserResultHandler(conn);
        PCTUserResultSearchKey pctUserSKey = new PCTUserResultSearchKey();
        PCTUserResultAlterKey pctUserAKey = new PCTUserResultAlterKey();

        try
        {
            for (int ter = 0; ter < termNo.length; ter++)
            {
                pctUserSKey.clear();
                // 検索条件の設定
                // 作業終了日時
                pctUserSKey.setKey(PCTUserResult.WORK_ENDTIME, null);
                // 端末No.
                pctUserSKey.setKey(PCTUserResult.TERMINAL_NO, termNo[ter]);
                // 検索
                PCTUserResult[] pctUser = (PCTUserResult[])pctUserHndlr.find(pctUserSKey);

                for (int i = 0; i < pctUser.length; i++)
                {
                    // 更新条件の設定
                    pctUserAKey.clear();
                    // 端末No.
                    pctUserAKey.setTerminalNo(pctUser[i].getTerminalNo());
                    // 作業日
                    pctUserAKey.setWorkDate(pctUser[i].getWorkDate());
                    // 作業開始日時
                    pctUserAKey.setWorkStarttime(pctUser[i].getWorkStarttime());

                    // 完了区分
                    pctUserAKey.updateCompleteKind(PCTUserResult.COMPLETE_KIND_WEB);
                    // 作業終了日時
                    pctUserAKey.updateWorkEndtime(new SysDate());
                    // 作業時間
                    pctUserAKey.updateWorkTime((int)((Calendar.getInstance().getTimeInMillis() - pctUser[i].getWorkStarttime().getTime()) / 1000));
                    // 最終更新処理名
                    pctUserAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                    // PCTユーザ実績情報更新
                    pctUserHndlr.modify(pctUserAKey);
                }
            }

            return true;
        }
        catch (ReadWriteException e)
        {
            return false;
        }
        catch (NotFoundException e)
        {
            return false;
        }
    }

    /**
     * セッション管理
     * 端末で使用中のセッションをLOG OFFする
     * 
     * @return ture:正常終了 false:異常終了 
     */
    public boolean sessionManage()
    {
        // セッション取得
        SessionManage sessionManage = new SessionManage();
        
        // 使用されている端末があれば、リセットを行う。
        for (int i = 0; i < WmsParam.TERMINALSERVER_NAME.size(); i++)
        {
            String[][] sessionList = sessionManage.wtsGetSessionList(WmsParam.TERMINALSERVER_NAME.get(i));
            String sessionId = "";
            if (!ArrayUtil.isEmpty(sessionList))
            {
                for (int j = 0; j < sessionList.length; j++)
                {
                    // セッションID取得
                    sessionId = sessionList[j][0];

                    // セッション切断
                    if (!StringUtil.isBlank(sessionId))
                    {
                        sessionManage.wtsLogoff(WmsParam.TERMINALSERVER_NAME.get(i), sessionId);
                    }
                }
            }
        }

        return true;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTAutoUpdateProcess.java 4137 2009-04-13 05:17:19Z okamura $";
    }
}
