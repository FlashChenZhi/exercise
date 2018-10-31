// $Id: AsrsReplenishStartSCH.java 4995 2009-09-08 09:57:54Z shibamoto $
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

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.SearchKey;

import static jp.co.daifuku.wms.asrs.schedule.AsrsReplenishStartSCHParams.*;

/**
 * AS/RS 補充開始設定のスケジュール処理を行います。
 *
 * @version $Revision: 4995 $, $Date: 2009-09-08 18:57:54 +0900 (火, 08 9 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsrsReplenishStartSCH
        extends AbstractAsrsSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** カラム定義 設定単位キー */
    public static final FieldName SETTING_UNIT_KEY_COUNT = new FieldName(MoveWorkInfo.STORE_NAME, "SETTING_UNIT_KEY_COUNT");

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
    public AsrsReplenishStartSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // 日時更新チェック
        if (!canStart())
        {
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        AbstractDBHandler handler = null;
        int rowNum = 0;

        try
        {
            handler = new CarryInfoHandler(getConnection());

            // 配送先ステーションのチェックを行う
            for (rowNum = 0; rowNum < ps.length; rowNum++)
            {
                // 作業情報取得結果
                CarryInfo[] carries = (CarryInfo[])handler.find(createStationSerachKey(ps[rowNum]));

                for (CarryInfo carry : carries)
                {
                    // 出庫可能な状態かチェック
                    if (!retrievalStationCheck(carry.getDestStationNo(), ps[rowNum].getRowIndex()))
                    {
                        setErrorRowIndex(ps[rowNum].getRowIndex());
                        return false;
                    }
                }
                
            }
            
            // オペレータパラメータ生成
            AsrsInParameter[] inParams = new AsrsInParameter[ps.length];
            for (int i = 0; i < inParams.length; i++)
            {
                inParams[i] = new AsrsInParameter(getWmsUserInfo());

                inParams[i].setSettingUnitKey(ps[i].getString(LST_SETTING_UKEY));
                inParams[i].setErrorAllocCarry(ps[i].getBoolean(ERROR_ALLOC_CARRY));
            }

            // ASRS補充開始設定
            AsrsOperator asOperator = new AsrsOperator(getConnection(), this.getClass());
            for (rowNum = 0; rowNum < inParams.length; rowNum++)
            {
                asOperator.webStartReplenish(inParams[rowNum]);
            }

            setMessage(WmsMessageFormatter.format(6021021));

            return true;
        }
        catch (LockTimeOutException e)
        {
            // No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
        }
        catch (NotFoundException e)
        {
            // No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
        }
        catch (DataExistsException e)
        {
            // No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
        }
        catch (RouteException e)
        {
            // 搬送ルート異常
            setMessage(getRouteErrorMessage(e.getRouteStatus()));
            return false;
        }
        catch (OperatorException e)
        {
            //「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, ps[rowNum].getRowIndex()));
                setErrorRowIndex(ps[rowNum].getRowIndex());
                return false;
            }
            else if (OperatorException.ERR_EXIST_UNSTART_CARRYINFO.equals(e.getErrorCode()))
            {
                // 6023251=No.{0} 開始対象よりも先に引当てたもので、開始されていない搬送作業があったため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023251, ps[rowNum].getRowIndex()));
                setErrorRowIndex(ps[rowNum].getRowIndex());
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
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        WorkInfoSearchKey wKey = new WorkInfoSearchKey();
        // select
        // 設定単位キー
        wKey.setCollect(MoveWorkInfo.SETTING_UNIT_KEY);
        // エリアNo
        wKey.setCollect(WorkInfo.PLAN_AREA_NO);
        // 集約したリスト作業No.の数
        wKey.setCollect(MoveWorkInfo.SETTING_UNIT_KEY, "COUNT", SETTING_UNIT_KEY_COUNT);
        
        // where
        // 作業種別
        wKey.setJobType(new String[]{SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT, SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT}, true);
        // 状態フラグ(0:未作業)
        wKey.setKey(WorkInfo.STATUS_FLAG, WorkInfo.STATUS_FLAG_UNSTART);
        // ハードウェア区分（3:AS/RS）
        wKey.setKey(WorkInfo.HARDWARE_TYPE, WorkInfo.HARDWARE_TYPE_ASRS);
        // リスト作業No.
        if (!StringUtil.isBlank(p.getString(SETTING_UKEY)))
        {
            wKey.setKey(MoveWorkInfo.SETTING_UNIT_KEY, p.getString(SETTING_UKEY));
        }
        // エリアNo.
        if (!p.getString(AREA_NO).equals(WmsParam.ALL_AREA_NO))
        {
            wKey.setKey(WorkInfo.PLAN_AREA_NO, p.getString(AREA_NO));
        }
        // 荷主コード
        wKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 作業情報と移動作業情報を紐付け
        wKey.setJoin(WorkInfo.JOB_NO, MoveWorkInfo.WORK_CONN_KEY);

        // group by
        // 設定単位キー
        wKey.setGroup(MoveWorkInfo.SETTING_UNIT_KEY);
        wKey.setGroup(WorkInfo.PLAN_AREA_NO);

        // order by
        // 設定単位キー
        wKey.setOrder(MoveWorkInfo.SETTING_UNIT_KEY, true);

        return wKey;

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


            // リスト作業No.
            param.set(LST_SETTING_UKEY, ent.getSettingUnitKey());
            // エリアNo
            param.set(FROM_AREA, ent.getPlanAreaNo());
            // 明細数
            param.set(SUMMARY, Long.parseLong(ent.getValue(SETTING_UNIT_KEY_COUNT).toString()));

            result.add(param);
        }

        return result;
    }
    
    /**
     * リスト作業No.に紐づく搬送の搬送先ステーションが全て使用可能かのチェックを行う。
     * 
     * @param inParam 画面入力データ
     * @return 検索キー
     * @throws CommonException 例外発生する場合は通知する。
     */
    protected SearchKey createStationSerachKey(ScheduleParams p)
            throws CommonException
    {
        CarryInfoSearchKey wKey = new CarryInfoSearchKey();

        // select
        // AS/RS搬送情報.搬送先ステーションNo.
        wKey.setCollect(CarryInfo.DEST_STATION_NO);

        // join
        // 作業情報.job_no = 移動作業情報.work_conn_key
        wKey.setJoin(WorkInfo.JOB_NO, MoveWorkInfo.WORK_CONN_KEY);
        // 入出庫作業情報.システム接続キー＝AS/RS搬送情報.搬送キー
        wKey.setJoin(CarryInfo.CARRY_KEY, "", WorkInfo.SYSTEM_CONN_KEY, "");

        // where
        // 移動作業情報.設定キー
        wKey.setKey(MoveWorkInfo.SETTING_UNIT_KEY, p.get(LST_SETTING_UKEY));

        // group by
        // AS/RS搬送情報.搬送先ステーションNo.
        wKey.setGroup(CarryInfo.DEST_STATION_NO);

        // order by
        // AS/RS搬送情報.搬送先ステーションNo.
        wKey.setOrder(CarryInfo.DEST_STATION_NO, true);

        return wKey;
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
