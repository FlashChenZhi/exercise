// $Id: ReplenishWorkMntSCH.java 4995 2009-09-08 09:57:54Z shibamoto $
package jp.co.daifuku.wms.replenish.schedule;

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

import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.replenish.operator.ReplenishOperator;
import static jp.co.daifuku.wms.replenish.schedule.ReplenishWorkMntSCHParams.*;

/**
 * 補充キャンセル/補充リスト再発行のスケジュール処理を行います。
 *
 * @version $Revision: 4995 $, $Date: 2009-09-08 18:57:54 +0900 (火, 08 9 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class ReplenishWorkMntSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** カラム定義 設定単位キー集約キー */
    private final FieldName SETTING_UNIT_KEY_COUNT =
            new FieldName(MoveWorkInfo.SETTING_UNIT_KEY.getStoreName(), "SETTING_UNIT_KEY_COUNT");

    /** カラム定義 ロケーションNo.最小値集約キー */
    private final FieldName RETRIEVAL_LOCATION_NO_MIN =
            new FieldName(MoveWorkInfo.RETRIEVAL_LOCATION_NO.getStoreName(), "RETRIEVAL_LOCATION_NO_MIN");

    /** カラム定義 ロケーションNo.最大値集約キー */
    private final FieldName RETRIEVAL_LOCATION_NO_MAX =
            new FieldName(MoveWorkInfo.RETRIEVAL_LOCATION_NO.getStoreName(), "RETRIEVAL_LOCATION_NO_MAX");

    /** カラム定義 エリアNo.最大値集約キー */
    private final FieldName RETRIEVAL_AREA_NO_MAX =
            new FieldName(MoveWorkInfo.RETRIEVAL_AREA_NO.getStoreName(), "RETRIEVAL_AREA_NO_MAX");

    /** カラム定義 エリアタイプ最大値集約キー */
    private final FieldName AREA_TYPE_MAX = new FieldName(Area.AREA_TYPE.getStoreName(), "AREA_TYPE_MAX");

    /** カラム定義 出庫開始日時出庫開始単位キー毎に一意になる日付最大値集約キー */
    private final FieldName RETRIEVAL_START_DATE_MAX =
            new FieldName(MoveWorkInfo.RETRIEVAL_START_DATE.getStoreName(), "RETRIEVAL_START_DATE_MAX");

    /** カラム定義 アイテムコード最大値集約キー */
    private final FieldName ITEM_CODE_MIN = new FieldName(MoveWorkInfo.ITEM_CODE.getStoreName(), "ITEM_CODE_MIN");

    /** カラム定義 アイテムコード最小値集約キー */
    private final FieldName ITEM_CODE_MAX = new FieldName(MoveWorkInfo.ITEM_CODE.getStoreName(), "ITEM_CODE_MAX");


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
    public ReplenishWorkMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
            finder = new MoveWorkInfoFinder(getConnection());
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

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        // オペレータパラメータ生成
        ReplenishInParameter[] inParams = new ReplenishInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new ReplenishInParameter(getWmsUserInfo());

            inParams[i].setSettingUnitKey(ps[i].getString(SETTING_UNIT_KEY));
        }

        int num = 0;
        try
        {
            ReplenishOperator operator = new ReplenishOperator(getConnection(), this.getClass());

            for (; num < inParams.length; num++)
            {
                operator.cancel(inParams[num]);
                
            }

            // 6001006=設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
            
            return true;
            
        }
        catch (LockTimeOutException e)
        {
            // 6023014=No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[num].getRowIndex()));
            setErrorRowIndex(ps[num].getRowIndex());
            return false;
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode()))
            {
                // No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, ps[num].getRowIndex()));
                setErrorRowIndex(ps[num].getRowIndex());
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
        MoveWorkInfoSearchKey searchKey = new MoveWorkInfoSearchKey();

        // select
        searchKey.setCollect(MoveWorkInfo.SETTING_UNIT_KEY);
        searchKey.setCollect(MoveWorkInfo.SETTING_UNIT_KEY, "COUNT", SETTING_UNIT_KEY_COUNT);
        searchKey.setCollect(MoveWorkInfo.RETRIEVAL_LOCATION_NO, "MAX", RETRIEVAL_LOCATION_NO_MAX);
        searchKey.setCollect(MoveWorkInfo.RETRIEVAL_LOCATION_NO, "MIN", RETRIEVAL_LOCATION_NO_MIN);
        searchKey.setCollect(MoveWorkInfo.RETRIEVAL_AREA_NO, "MAX", RETRIEVAL_AREA_NO_MAX);
        searchKey.setCollect(Area.AREA_TYPE, "MAX", AREA_TYPE_MAX);
        searchKey.setCollect(MoveWorkInfo.RETRIEVAL_START_DATE, "MAX", RETRIEVAL_START_DATE_MAX);
        searchKey.setCollect(MoveWorkInfo.ITEM_CODE, "MAX", ITEM_CODE_MAX);
        searchKey.setCollect(MoveWorkInfo.ITEM_CODE, "MIN", ITEM_CODE_MIN);

        // where
        searchKey.setJobType(p.getString(SELECTED_JOB_TYPE));
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));

        // join
        searchKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_RETRIEVAL_WORKING, "=", "((", "", true);
        searchKey.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_LIST, "=", "", "", true);
        searchKey.setWorkConnKey("", "=", "", ")", false);
        searchKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_UNSTART, "=", "((((", "", true);
        searchKey.setKey(CarryInfo.CMD_STATUS, CarryInfo.CMD_STATUS_ALLOCATION, "=", "", ")", false);
        searchKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_NOWWORKING, "=", "(", "", true);
        searchKey.setKey(CarryInfo.CMD_STATUS, CarryInfo.CMD_STATUS_START, "=", "", ")))", true);
        searchKey.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_ASRS, "=", "", "))", true);
        searchKey.setJoin(MoveWorkInfo.RETRIEVAL_AREA_NO, Area.AREA_NO);
        searchKey.setJoin(MoveWorkInfo.WORK_CONN_KEY, "", WorkInfo.JOB_NO, "(+)");
        searchKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, "", CarryInfo.CARRY_KEY, "(+)");

        // order by
        searchKey.setSettingUnitKeyOrder(true);

        // group by
        searchKey.setSettingUnitKeyGroup();

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
        MoveWorkInfo[] entities = (MoveWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (MoveWorkInfo ent : entities)
        {
            Params param = new Params();

            // リスト作業No.
            param.set(SETTING_UNIT_KEY, ent.getSettingUnitKey());

            // アイテムコードを取得
            String itemCodeMin = ent.getValue(ITEM_CODE_MIN, "").toString();
            String itemCodeMax = ent.getValue(ITEM_CODE_MAX, "").toString();

            // アイテムコードが定義されている場合
            if (!StringUtil.isBlank(itemCodeMax) && !StringUtil.isBlank(itemCodeMin))
            {
                if (itemCodeMax.equals(itemCodeMin))
                {
                    // アイテムコードが一致する場合は同じアイテムなのでパラメータにセット
                    param.set(ITEM_CODE, itemCodeMin);
                }
            }

            // エリア
            param.set(RETRIEVAL_AREA_NO, ent.getValue(RETRIEVAL_AREA_NO_MAX, "").toString());
            // 開始棚
            param.set(FROM_LOCATION_NO, ent.getValue(RETRIEVAL_LOCATION_NO_MIN, "").toString());
            // 終了棚
            param.set(TO_LOCATION_NO, ent.getValue(RETRIEVAL_LOCATION_NO_MAX, "").toString());
            // 商品数
            param.set(ITEM_COUNT, ent.getValue(SETTING_UNIT_KEY_COUNT, "0").toString());
            // 出庫開始日時
            param.set(START_DATETIME, ent.getDate(RETRIEVAL_START_DATE_MAX));
            // エリアタイプ
            param.set(AREA_TYPE, ent.getValue(AREA_TYPE_MAX, "").toString());

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
