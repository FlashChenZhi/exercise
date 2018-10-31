// $Id: PCTWeightDistinctionListSCH.java 4259 2009-05-12 05:33:55Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTWeightDistinctionListSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.WeightDistinctHandler;
import jp.co.daifuku.wms.base.dbhandler.WeightDistinctSearchKey;
import jp.co.daifuku.wms.base.entity.WeightDistinct;

/**
 * 重量差異リスト一覧のスケジュール処理を行います。
 *
 * @version $Revision: 4259 $, $Date:: 2009-05-12 14:33:55 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTWeightDistinctionListSCH
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
    public PCTWeightDistinctionListSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT出庫作業情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * 
     * @param p 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException 全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // 重量差異情報
        WeightDistinctHandler weightHandler = new WeightDistinctHandler(getConnection());
        WeightDistinctSearchKey weightSKey = new WeightDistinctSearchKey();

        // 検索条件
        weightSKey.setConsignorCodeGroup();
        // 荷主コード
        weightSKey.setConsignorCodeCollect();

        if (weightHandler.count(weightSKey) == 1)
        {
            WeightDistinct weightDis = (WeightDistinct)weightHandler.findPrimary(weightSKey);

            Params lineparam = new Params();
            lineparam.set(CONSIGNOR_CODE, weightDis.getConsignorCode());


            return lineparam;
        }
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
