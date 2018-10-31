// $Id: RFTState2SCH.java 4274 2009-05-13 04:57:26Z okayama $
package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.system.schedule.RFTState2SCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftFinder;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.PCTOrderInfo;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.PCTUserResult;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.sessionmanage.SessionManage;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.SysDate;

/**
 * Pカートシステム状態メンテナンスのスケジュール処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 4274 $, $Date:: 2009-05-13 13:57:26 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RFTState2SCH
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
     * 
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public RFTState2SCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new RftFinder(getConnection());
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
     * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
     * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * このメソッドはスケジュールの結果をもとに、画面表示内容を再表示する場合に使用します。
     * 条件エラーなどでスケジュール処理が失敗した場合はnullを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
     * 
     * @param ps データベースとのコネクションオブジェクト
     * @return スケジュール処理が正常終了した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    public List<Params> startSCHgetParams(ScheduleParams... ps)
            throws CommonException
    {
        // RFT管理情報エンティティ
        Rft ent = new Rft();
        // RFT管理情報更新キー
        RftAlterKey alterKey = new RftAlterKey();
        // RFT管理情報ハンドラ
        RftHandler handler = new RftHandler(getConnection());
        // パラメータ配列の生成
        List<Params> result = new ArrayList<Params>();

        // 日次更新処理中チェック
        if (!canStart())
        {
            return null;
        }

        try
        {
            // RFT情報チェック
            ent = getRftInfo(ps[0].getString(RFT_NO));

            // 確定/キャンセル処理を行う
            if (!updateWorkStatus(ent, ps))
            {
                // (6007001)スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
                setMessage(WmsMessageFormatter.format(6007001));
                return null;
            }

            // 更新条件設定
            alterKey.clear();
            // 端末号機No.
            alterKey.setRftNo(ps[0].getString(RFT_NO));

            // 更新値設定
            // ユーザID
            alterKey.updateUserId(null);
            // 作業区分(未作業)
            alterKey.updateJobType(Rft.JOB_TYPE_UNSTART);
            // 状態フラグ(停止中)
            alterKey.updateStatusFlag(Rft.RFT_STATUS_FLAG_STOP);
            // 無線状態フラグ(無線エリア内)
            alterKey.updateRadioFlag(Rft.RADIO_FLAG_IN);
            // 休憩開始日時
            alterKey.updateRestStartDate(null);
            //alterKey荷主コード
            alterKey.updateConsignorCode(null);
            // エリア
            alterKey.updateAreaNo(null);
            // バッチ
            alterKey.updateBatchSeqNo(null);
            // 設定単位キー
            alterKey.updateSettingUnitKey(null);
            // 最終更新処理名
            alterKey.updateLastUpdatePname(this.getClass().getSimpleName());

            // RFT管理情報更新
            handler.modify(alterKey);

            // 更新結果を再表示する
            result = query(ps[0]);

            // (6001006)設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
        }
        catch (NoPrimaryException e)
        {
            // (6027006)データの不整合が発生しました。ログを参照してください。TABLE={0}
            setMessage(WmsMessageFormatter.format(6027006, Rft.STORE_NAME));
            throw e;
        }
        catch (InvalidDefineException e)
        {
            // (6027013)不正なパラメータが検出されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6027013));
            throw e;
        }
        catch (NotFoundException e)
        {
            // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください。
            setMessage(WmsMessageFormatter.format(6023004));
            return null;
        }
        return result;
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
        // RFT管理情報検索キー
        RftSearchKey searchKey = new RftSearchKey();

        // 検索条件
        // 号機No.
        if (!StringUtil.isBlank(p.getString(RFT_NO)))
        {
            searchKey.setRftNo(p.getString(RFT_NO));
        }

        // 結合条件
        // RFT管理情報.ユーザID = ログインユーザ設定.ユーザID(+)
        searchKey.setJoin(Rft.USER_ID, "", Com_loginuser.USERID, "(+)");

        // 取得項目
        // 号機No.
        searchKey.setRftNoCollect();
        // 端末区分
        searchKey.setTerminalTypeCollect();
        // 状態
        searchKey.setStatusFlagCollect();
        // ユーザ名称
        searchKey.setCollect(Com_loginuser.USERNAME);
        // 作業状態
        searchKey.setJobTypeCollect();

        // ソート順
        // 号機No.(昇順)
        searchKey.setRftNoOrder(true);

        // 生成した検索キーを返却
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
        // RFT管理情報エンティティにて検索結果を取得
        Rft[] entities = (Rft[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        // パラメータ配列の生成
        List<Params> result = new ArrayList<Params>();
        // パラメータの生成
        Params param = null;

        for (Rft ent : entities)
        {
            // パラメータの生成
            param = new Params();

            // 号機No.
            param.set(RFT_NO, ent.getRftNo());
            // 端末区分
            param.set(TERMINAL_TYPE, DisplayResource.getTerminalType(ent.getTerminalType()));
            // 状態
            param.set(STATUS, DisplayResource.getPCTJobType(ent.getJobType()));
            // ユーザ名称
            param.set(USER_NAME, ent.getValue(Com_loginuser.USERNAME, ""));

            // 作業区分が"未開始"の場合
            if (SystemDefine.JOB_TYPE_UNSTART.equals(ent.getJobType()))
            {
                // 作業フラグが"停止中"の場合
                if (SystemDefine.RFT_STATUS_FLAG_STOP.equals(ent.getStatusFlag()))
                {
                    // 状態
                    param.set(STATUS, "");
                    // ユーザ名称
                    param.set(USER_NAME, "");
                }
            }
            // 生成したパラメータを配列に格納
            result.add(param);
        }
        // 生成したパラメータを返却
        return result;
    }

    /**
     * RFT管理情報の取得
     * 
     * @param rftNo
     * @return Rft RFT管理情報エンティティ
     * @throws CommonException
     */
    protected Rft getRftInfo(String rftNo)
            throws CommonException
    {
        // RFT管理情報検索キー
        RftSearchKey searchKey = new RftSearchKey();
        // RFT管理情報ハンドラ
        RftHandler handler = new RftHandler(getConnection());

        try
        {
            // RFT号機No.
            searchKey.setRftNo(rftNo);
            // プライマリーデータの取得
            Rft ent = (Rft)handler.findPrimary(searchKey);

            // 取得したエンティティの返却
            return ent;
        }
        // 重複エラーが発生した場合
        catch (NoPrimaryException e)
        {
            // スロー
            throw e;
        }
    }

    /**
     * 作業状態を更新します。
     * 
     * @param ent RFT管理情報
     * @param param 画面に入力されたパラメータの配列
     * @return スケジュール処理が正常終了した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected boolean updateWorkStatus(Rft ent, ScheduleParams... ps)
            throws CommonException
    {
        // RFT管理情報の設定一意キーが取得出来た場合
        if (!StringUtil.isBlank(ent.getSettingUnitKey()))
        {
            // PCT出庫作業情報の更新
            cancelPCTRetWorkInfo(ps[0].getString(RFT_NO), ent.getUserId());

            // PCTオーダー情報の更新
            cancelPCTOrderInfo(ps[0].getString(RFT_NO), ent.getUserId(), ent.getSettingUnitKey());
        }

        // PCTユーザ実績情報の更新
        updatePCTUserResult(ps[0].getString(RFT_NO), ent.getUserId(), ent.getJobType());

        // セッション管理の生成
        SessionManage sessionManage = new SessionManage();
        // セッションID
        String sessionId = "";
        // セッションリスト
        String[][] sessionList = null;

        // 使用されている端末があればリセットを行う。
        for (int i = 0; i < WmsParam.TERMINALSERVER_NAME.size(); i++)
        {
            sessionList = sessionManage.wtsGetSessionList(WmsParam.TERMINALSERVER_NAME.get(i));
            sessionId = "";
            if (!ArrayUtil.isEmpty(sessionList))
            {
                for (int j = 0; j < sessionList.length; j++)
                {
                    if (sessionList[j].length == 6 && ps[0].getString(RFT_NO).equals(sessionList[j][5]))
                    {
                        // セッションID取得
                        sessionId = sessionList[j][0];
                        break;
                    }
                }
                // セッション切断
                if (!StringUtil.isBlank(sessionId))
                {
                    if (sessionManage.wtsLogoff(WmsParam.TERMINALSERVER_NAME.get(i), sessionId) != 0)
                    {
                        // 6127021=セッションをリセットできませんでした。再度、表示ボタンを押してください。
                        setMessage("6127021");
                    }
                }
            }
        }
        return true;
    }

    /**
     * ピッキングカート出庫作業情報のキャンセル処理を行います。
     * 
     * @param rftNo RFT号機No
     * @param userId ユーザID
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void cancelPCTRetWorkInfo(String rftNo, String userId)
            throws CommonException
    {
        // PCT出庫作業情報検索キー
        PCTRetWorkInfoSearchKey pctRetWorkSKey = new PCTRetWorkInfoSearchKey();
        // PCT出庫作業情報更新キー
        PCTRetWorkInfoAlterKey pctRetWorkAKey = new PCTRetWorkInfoAlterKey();
        // PCT出庫作業情報ハンドラ
        PCTRetWorkInfoHandler pctRetWorkHndlr = new PCTRetWorkInfoHandler(getConnection());

        // 予定一意キーリスト
        ArrayList<String> keyList = new ArrayList<String>();

        try
        {
            // 検索条件設定
            // RFT号機No.
            pctRetWorkSKey.setTerminalNo(rftNo);
            // ユーザID
            pctRetWorkSKey.setUserId(userId);
            // 状態フラグ(作業中、作業済)
            pctRetWorkSKey.setStatusFlag(new String[] {
                    PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKING,
                    PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED
            }, false);

            // PCT出庫作業情報取得
            PCTRetWorkInfo[] pctRetEnt = (PCTRetWorkInfo[])pctRetWorkHndlr.findForUpdate(pctRetWorkSKey);

            // 取得した件数分繰り返し
            for (int i = 0; i < pctRetEnt.length; i++)
            {
                // 更新条件設定
                pctRetWorkAKey.clear();
                // 作業No.
                pctRetWorkAKey.setJobNo(pctRetEnt[i].getJobNo());

                // 集約作業No.
                pctRetWorkAKey.updateCollectJobNo(pctRetEnt[i].getJobNo());
                // 状態フラグ(未作業)
                pctRetWorkAKey.updateStatusFlag(PCTRetWorkInfo.STATUS_FLAG_UNSTART);
                // 設定単位キー
                pctRetWorkAKey.updateSettingUnitKey(null);
                // 実績数
                pctRetWorkAKey.updateResultQty(0);
                // 欠品数
                pctRetWorkAKey.updateShortageQty(0);
                // 作業時間
                pctRetWorkAKey.updateWorkSecond(0);
                // ユーザID
                pctRetWorkAKey.updateUserId(null);
                // 端末No.
                pctRetWorkAKey.updateTerminalNo(null);
                // 最終更新処理名
                pctRetWorkAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                // PCT出庫作業情報更新
                pctRetWorkHndlr.modify(pctRetWorkAKey);

                // 予定一意キーを保持
                if (keyList.indexOf(pctRetEnt[i].getPlanUkey()) == -1)
                {
                    keyList.add(pctRetEnt[i].getPlanUkey());
                }
            }
            // 予定一意キーより、PCT出庫予定情報を更新
            cancelPCTRetPlan(keyList);
        }
        // ロックタイムアウトが発生した場合
        catch (LockTimeOutException e)
        {
            // (6026018)一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
            setMessage(WmsMessageFormatter.format(6026018, InventWorkInfo.STORE_NAME));
            return;
        }
        // DBエラーが発生した場合
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw e;
        }
        // データが存在しなかった場合
        catch (NotFoundException e)
        {
            // (6023115)他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115));
            return;
        }
    }

    /**
     * ピッキングカート出庫予定情報のキャンセル処理を行います。
     * 
     * @param planUkey 予定一意キーリスト
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void cancelPCTRetPlan(ArrayList<String> planUkey)
            throws CommonException
    {
        // PCT出庫作業情報検索キー
        PCTRetWorkInfoSearchKey pctRetWorkSKey = new PCTRetWorkInfoSearchKey();
        // PCT出庫作業情報ハンドラ
        PCTRetWorkInfoHandler pctRetWorkHndlr = new PCTRetWorkInfoHandler(getConnection());

        // PCT出庫予定情報更新キー
        PCTRetPlanAlterKey pctRetPlanAKey = new PCTRetPlanAlterKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler pctRetPlanHndlr = new PCTRetPlanHandler(getConnection());

        try
        {
            // 予定一意キーより、予定情報を更新
            for (int i = 0; i < planUkey.size(); i++)
            {
                // 検索条件設定
                pctRetWorkSKey.clear();
                // 予定一意キー
                pctRetWorkSKey.setPlanUkey(planUkey.get(i));

                // PCT出庫作業情報取得
                PCTRetWorkInfo[] pctRetWork = (PCTRetWorkInfo[])pctRetWorkHndlr.find(pctRetWorkSKey);
                boolean isUpdate = true;

                for (int j = 0; j < pctRetWork.length; j++)
                {
                    if (!PCTRetWorkInfo.STATUS_FLAG_UNSTART.equals(pctRetWork[j].getStatusFlag()))
                    {
                        isUpdate = false;
                        break;
                    }
                }

                // PCT出庫予定情報更新
                if (isUpdate)
                {
                    // 更新条件設定
                    pctRetPlanAKey.clear();
                    // 予定一意キー
                    pctRetPlanAKey.setPlanUkey(planUkey.get(i));

                    // 状態フラグ
                    pctRetPlanAKey.updateStatusFlag(PCTRetPlan.STATUS_FLAG_UNSTART);
                    // 最終更新処理名
                    pctRetPlanAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                    // PCT出庫予定情報更新
                    pctRetPlanHndlr.modify(pctRetPlanAKey);
                }
            }
        }
        // DBエラーが発生した場合
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw e;
        }
        // データが存在しなかった場合
        catch (NotFoundException e)
        {
            // (6023115)他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115));
            return;
        }
    }

    /**
     * PCTオーダー情報のキャンセル処理を行います。
     * 
     * @param rftNo RFT号機No
     * @param userId ユーザID
     * @param settingUnitKey 設定単位キー
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void cancelPCTOrderInfo(String rftNo, String userId, String settingUnitKey)
            throws CommonException
    {
        // PCTオーダー情報更新キー
        PCTOrderInfoAlterKey pctOrderAKey = new PCTOrderInfoAlterKey();
        // PCTオーダー情報ハンドラ
        PCTOrderInfoHandler pctOrderHndlr = new PCTOrderInfoHandler(getConnection());

        try
        {
            // 更新条件設定
            // 端末No.
            pctOrderAKey.setTerminalNo(rftNo);
            // ユーザID
            pctOrderAKey.setUserId(userId);
            // 状態フラグ
            pctOrderAKey.setStatusFlag(PCTOrderInfo.STATUS_FLAG_NOWWORKING);
            // 設定単位キー
            pctOrderAKey.setSettingUnitKey(settingUnitKey);

            // 作業開始日時
            pctOrderAKey.updateWorkStarttime(null);
            // 状態フラグ
            pctOrderAKey.updateStatusFlag(PCTOrderInfo.STATUS_FLAG_UNSTART);
            // ユーザID
            pctOrderAKey.updateUserId(null);
            // 端末No.
            pctOrderAKey.updateTerminalNo(null);
            // 設定単位キー
            pctOrderAKey.updateSettingUnitKey(null);
            // 最終更新処理名
            pctOrderAKey.updateLastUpdatePname(this.getClass().getSimpleName());

            // PCTオーダー情報更新
            pctOrderHndlr.modify(pctOrderAKey);

        }
        // DBエラーが発生した場合
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw e;
        }
        // データが存在しなかった場合
        catch (NotFoundException e)
        {
            // (6023115)他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115));
            return;
        }
    }

    /**
     * PCTユーザ実績情報の更新処理を行います。
     * 
     * @param rftNo RFT号機No
     * @param userId ユーザID
     * @param jobType 作業区分
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void updatePCTUserResult(String rftNo, String userId, String jobType)
            throws CommonException
    {
        // PCTユーザ実績情報検索キー
        PCTUserResultSearchKey pctUserSKey = new PCTUserResultSearchKey();
        // PCTユーザ実績情報更新キー
        PCTUserResultAlterKey pctUserAKey = new PCTUserResultAlterKey();
        // PCTユーザ実績情報ハンドラ
        PCTUserResultHandler pctUserHndlr = new PCTUserResultHandler(getConnection());

        // システム定義情報コントローラ
        WarenaviSystemController wsysCtlr = new WarenaviSystemController(getConnection(), getClass());

        try
        {
            // 検索条件の設定
            // 作業日
            pctUserSKey.setWorkDate(wsysCtlr.getWorkDay());
            // 作業終了日時
            pctUserSKey.setKey(PCTUserResult.WORK_ENDTIME, null);
            // 端末No.
            pctUserSKey.setTerminalNo(rftNo);

            // 検索
            PCTUserResult[] pctUser = (PCTUserResult[])pctUserHndlr.find(pctUserSKey);

            for (int i = 0; i < pctUser.length; i++)
            {
                // 更新条件の設定
                pctUserAKey.clear();
                // 作業日
                pctUserAKey.setWorkDate(wsysCtlr.getWorkDay());
                // 作業開始日時
                pctUserAKey.setWorkStarttime(pctUser[i].getWorkStarttime());
                // 作業終了日時
                pctUserAKey.setKey(PCTUserResult.WORK_ENDTIME, null);
                // 端末No.
                pctUserAKey.setTerminalNo(rftNo);
                // 作業区分
                pctUserAKey.setJobType(pctUser[i].getJobType());
                // ユーザID
                pctUserAKey.setUserId(pctUser[i].getUserId());

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
        // DBエラーが発生した場合
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw e;
        }
        // データが存在しなかった場合
        catch (NotFoundException e)
        {
            // (6023115)他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115));
            return;
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
