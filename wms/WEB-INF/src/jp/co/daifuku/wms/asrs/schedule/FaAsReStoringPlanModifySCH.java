// $Id: FaAsReStoringPlanModifySCH.java 7348 2010-03-04 04:17:40Z kishimoto $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.schedule.FaAsReStoringPlanModifySCHParams.*;

import java.sql.Connection;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.ReStoringPlan;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.DatabaseHandler;

/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7348 $, $Date:: 2010-03-04 13:17:40 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaAsReStoringPlanModifySCH
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
    public FaAsReStoringPlanModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // 処理前のチェックを行う。
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
        
        // 対象データのロック
        if (!lock(ps))
        {
            return false;
        }
        
        for (ScheduleParams p : ps)
        {
            if (ProcessFlag.UPDATE.equals(p.getProcessFlag()))
            {
                // 修正
                if (!modify(p))
                {
                    return false;
                }
            }
            else if (ProcessFlag.DELETE.equals(p.getProcessFlag()))
            {
                // 削除
                if (!delete(p))
                {
                    return false;
                }
            }
        }
        
        // 6001006=設定しました。
        setMessage("6001006");
        
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
    /**
     * 修正・削除のために予定情報と作業情報のロックを行います。<br>
     * 
     * @param ps スケジュールパラメータ
     * @throws NoPrimaryException データ一意キーエラーが発生した
     * @throws ReadWriteException データベースエラーが発生した
     * @return エラーが存在する場合、falseを返します。
     */
    protected boolean lock(ScheduleParams... ps)
            throws NoPrimaryException,
                ReadWriteException
    {
        int i = 0;
        try
        {
            ReStoringPlanHandler handler = new ReStoringPlanHandler(getConnection());
            ReStoringPlanSearchKey key = new ReStoringPlanSearchKey();
            
            // 更新日時チェック
            for (i = 0; i < ps.length; i++)
            {
                key.clear();
                
                key.setPlanUkey(ps[i].getString(PLAN_UKEY));
                key.setLastUpdateDate(ps[i].getDate(LAST_UPDATE_DATE));
                
                key.setJoin(ReStoringPlan.PLAN_UKEY, WorkInfo.PLAN_UKEY);
                
                ReStoringPlan plan = (ReStoringPlan)handler.findPrimaryForUpdate(key, DatabaseHandler.WAIT_SEC_NOWAIT);
                
                if (plan == null)
                {
                    //6003021=No.{0} このデータは、他の端末で更新されたため処理できません。
                    setMessage(WmsMessageFormatter.format(6003021, ps[i].getRowIndex()));
                    setErrorRowIndex(ps[i].getRowIndex());
                    return false;
                }
            }
            
            return true;
        }
        catch (LockTimeOutException e)
        {
            // No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[i].getRowIndex()));
            setErrorRowIndex(ps[i].getRowIndex());
            return false;
        }
    }
    
    /**
     * 再入庫予定情報を修正します。
     * 
     * @param p スケジュールパラメータ
     * @throws ReadWriteException データベースエラーが発生した
     */
    protected boolean modify(ScheduleParams p)
            throws ReadWriteException
    {
        try
        {
            String pname = getClass().getSimpleName();
            
            ReStoringPlanHandler plan_handler = new ReStoringPlanHandler(getConnection());
            ReStoringPlanAlterKey plan_akey = new ReStoringPlanAlterKey();
            
            plan_akey.setPlanUkey(p.getString(PLAN_UKEY));
            
            // 予定数
            plan_akey.updatePlanQty(p.getInt(PLAN_QTY));
            if (StringUtil.isBlank(p.getDate(STORAGE_DAY)) && StringUtil.isBlank(p.getDate(STORAGE_TIME)))
            {
                // 入庫日時が空の場合は、システム日時をセットする。
                plan_akey.updateStorageDate(WmsFormatter.toDateTime(DbDateUtil.getSystemDateTime()));
            }
            else
            {
                // 入庫日時
                plan_akey.updateStorageDate(WmsFormatter.toDate(p.getDate(STORAGE_DAY), p.getDate(STORAGE_TIME)));
            }
            // 最終更新処理名
            plan_akey.updateLastUpdatePname(pname);
            
            // 予定情報の更新
            plan_handler.modify(plan_akey);
            
            WorkInfoHandler work_handler = new WorkInfoHandler(getConnection());
            WorkInfoAlterKey work_akey = new WorkInfoAlterKey();
            
            work_akey.setPlanUkey(p.getString(PLAN_UKEY));
            
            // 予定数
            work_akey.updatePlanQty(p.getInt(PLAN_QTY));
            // 最終更新処理名
            work_akey.updateLastUpdatePname(pname);
            
            // 作業情報の更新
            work_handler.modify(work_akey);
            
            return true;
        }
        catch (NotFoundException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, p.getRowIndex()));
            setErrorRowIndex(p.getRowIndex());
            return false;
        }
    }
    
    /**
     * 再入庫予定情報を削除します。
     * 
     * @param p スケジュールパラメータ
     * @throws ReadWriteException データベースエラーが発生した
     */
    protected boolean delete(ScheduleParams p)
            throws ReadWriteException
    {
        try
        {
            String pname = getClass().getSimpleName();
            
            ReStoringPlanHandler plan_handler = new ReStoringPlanHandler(getConnection());
            ReStoringPlanAlterKey plan_akey = new ReStoringPlanAlterKey();
            
            plan_akey.setPlanUkey(p.getString(PLAN_UKEY));
            // 状態フラグ:削除
            plan_akey.updateStatusFlag(ReStoringPlan.STATUS_FLAG_DELETE);
            // 最終更新処理名
            plan_akey.updateLastUpdatePname(pname);
            
            // 予定情報の更新
            plan_handler.modify(plan_akey);
            
            WorkInfoHandler work_handler = new WorkInfoHandler(getConnection());
            WorkInfoAlterKey work_akey = new WorkInfoAlterKey();
            
            work_akey.setPlanUkey(p.getString(PLAN_UKEY));
    
            // 状態フラグ:削除
            work_akey.updateStatusFlag(ReStoringPlan.STATUS_FLAG_DELETE);
            // 最終更新処理名
            work_akey.updateLastUpdatePname(pname);
            
            // 作業情報の更新
            work_handler.modify(work_akey);
            
            return true;
        }
        catch (NotFoundException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, p.getRowIndex()));
            setErrorRowIndex(p.getRowIndex());
            return false;
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
