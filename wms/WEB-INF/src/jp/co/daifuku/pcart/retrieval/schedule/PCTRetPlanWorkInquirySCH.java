// $Id: PCTRetPlanWorkInquirySCH.java 3518 2009-03-16 08:39:28Z okayama $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.retrieval.schedule.PCTRetPlanWorkInquirySCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;

/**
 * 作業予定照会のスケジュール処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 3518 $, $Date:: 2009-03-16 17:39:28 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTRetPlanWorkInquirySCH
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
    public PCTRetPlanWorkInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 荷主コードを検索し、該当件数が一件の場合のみデータを返却します。
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE><BR>
     *          荷主コードと荷主名称を保持する。<BR>
     *          一件以上が該当、もしくはデータが無かった場合はnull値を返却します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();

        // 取得項目
        // 荷主コード
        planSKey.setConsignorCodeCollect();
        // 荷主名称の最大値
        planSKey.setCollect(PCTRetPlan.CONSIGNOR_NAME, "MAX", PCTRetPlan.CONSIGNOR_NAME);

        // 検索条件
        // 状態フラグ(削除以外)
        planSKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_DELETE, "!=");

        // 集約条件
        // 荷主コード
        planSKey.setConsignorCodeGroup();

        // 検索件数が1件の場合
        if (planHandler.count(planSKey) == 1)
        {
            // 返却パラメータを生成
            Params param = new Params();
            // 検索結果の取得
            PCTRetPlan ent = (PCTRetPlan)planHandler.findPrimary(planSKey);

            // 返却パラメータの設定
            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            param.set(CONSIGNOR_NAME, String.valueOf(ent.getValue(PCTRetPlan.CONSIGNOR_NAME, "")));

            // 設定した返却パラメータ返却
            return param;
        }
        // nullを返却
        return null;
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
