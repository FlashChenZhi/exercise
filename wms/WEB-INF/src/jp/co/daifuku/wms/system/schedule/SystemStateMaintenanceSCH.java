// $Id: SystemStateMaintenanceSCH.java 7187 2010-02-23 06:30:54Z shibamoto $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.schedule.SystemStateMaintenanceSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * システム状態メンテナンスのスケジュール処理を行います。
 * 
 * @version $Revision: 7187 $, $Date: 2010-02-23 15:30:54 +0900 (火, 23 2 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class SystemStateMaintenanceSCH
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
    public SystemStateMaintenanceSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
    	
    	List<Params> result = new ArrayList<Params>();
    	Params param = new Params();
    	
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // 日次更新中チェック
        if (sysController.isDailyUpdating())
        {
            param.set(IN_DAILY_PROCESS, true);
        }
        else
        {
            param.set(IN_DAILY_PROCESS, false);
        }

        // 予定データ取込中チェック
        if (sysController.isDataLoading())
        {
            param.set(IN_LOAD_PLAN_DATA, true);
        }
        else
        {
            param.set(IN_LOAD_PLAN_DATA, false);
        }

        // 報告データ作成中チェック
        if (sysController.isDataReporting())
        {
            param.set(IN_CREATE_REPORT_DATA, true);
        }
        else
        {
            param.set(IN_CREATE_REPORT_DATA, false);
        }

        // 出庫引当中フラグ
        if (sysController.isRetrievalAllocating())
        {
            param.set(IN_RETRIEVAL_ALLOCATE, true);
        }
        else
        {
            param.set(IN_RETRIEVAL_ALLOCATE, false);
        }

        // 搬送データクリア中フラグ
        if (sysController.isAllocationClear())
        {
            param.set(IN_ALLOCATION_CLEAR, true);
        }
        else
        {
            param.set(IN_ALLOCATION_CLEAR, false);
        }
        
        // ホスト通信チェック
        if (sysController.isHostEnabled())
        {
            param.set(IN_HOST_COMMUNICATION, true);
        }
        else
        {
            param.set(IN_HOST_COMMUNICATION, false);
        }

        result.add(param);
        setMessage("6001013");
        
        return result;
    }

    /**
     * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータにセットされた内容に従い、<BR>
     * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * スケジュール処理が成功した場合はtrueを返します。<BR>
     * 条件エラーなどでスケジュール処理が失敗した場合はfalseを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     * @param startParams スケジュールパラメータ
     * @throws CommonException 例外が発生した場合に通知されます。
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams startParams)
            throws CommonException
    {

        WarenaviSystemController wareNaviSystem = new WarenaviSystemController(getConnection(), this.getClass());
        boolean setting = false;

        ScheduleParams param = (ScheduleParams)startParams;

        // 日次処理状態を更新
        if (param.get(IN_DAILY_PROCESS) != null)
        {
            wareNaviSystem.updateDailyUpdating(false);
            setting = true;
        }
        // 予定データ取り込み中状態を更新
        if (param.get(IN_LOAD_PLAN_DATA) != null)
        {
            wareNaviSystem.updateDataLoading(false);
            setting = true;
        }
        // 報告データ作成中状態を更新
        if (param.get(IN_CREATE_REPORT_DATA) != null)
        {
            wareNaviSystem.updateDataReporting(false);
            setting = true;
        }
        // 出庫引当中状態を更新
        if (param.get(IN_RETRIEVAL_ALLOCATE) != null)
        {
            wareNaviSystem.updateRetrievalAllocating(false);
            setting = true;
        }
        // 出庫引当中状態を更新
        if (param.get(IN_ALLOCATION_CLEAR) != null)
        {
            wareNaviSystem.updateAllocationClear(false);
            setting = true;
        }
        // ホスト通信の接続設定を更新
        if (param.get(IN_HOST_COMMUNICATION) != null)
        {

            if (SystemDefine.HOST_ENABLED.equals(param.get(IN_HOST_COMMUNICATION)) && !wareNaviSystem.isHostEnabled())
            {
                wareNaviSystem.updateHostEnabled(true);
                setting = true;
            }
            else if (SystemDefine.HOST_DISABLED.equals(param.get(IN_HOST_COMMUNICATION)) && wareNaviSystem.isHostEnabled())
            {
                wareNaviSystem.updateHostEnabled(false);
                setting = true;
            }
            else
            {
                setting = true;
            }
        }

        if (setting)
        {
            // 6001006 = 設定しました。
            setMessage("6001006");
        }

        return setting;

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
