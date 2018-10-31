// $Id: StoragePlanController.java 5028 2009-09-18 04:31:29Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.LinkedHashSet;
import java.util.Set;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 入庫予定情報を操作するためのコントローラクラスです。
 *
 *
 * @version $Revision: 5028 $, $Date: 2009-09-18 13:31:29 +0900 (金, 18 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class StoragePlanController
        extends AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

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
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public StoragePlanController(Connection conn, Class caller)
    {
        super(conn, caller);
    }


    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 予定情報を作業中に更新します。
     * 
     * @param planUkey 対象の予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void startPlan(String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        StoragePlanHandler sph = new StoragePlanHandler(getConnection());
        StoragePlanAlterKey akey = new StoragePlanAlterKey();

        akey.updateStatusFlag(STATUS_FLAG_NOWWORKING);
        akey.updateLastUpdatePname(getCallerName());

        akey.setPlanUkey(planUkey);

        sph.modify(akey);
    }

    /**
     * 予定情報の完了処理を行います。
     * @param result 完了情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>予定一意キー
     * <li>実績数
     * <li>欠品数
     * <li>作業日
     * </ol>
     * 
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void completePlan(WorkInfo result)
            throws ReadWriteException,
                NotFoundException
    {
        WorkInfoController wic = new WorkInfoController(getConnection(), getCaller());
        String st = wic.getPlanStatus(result.getPlanUkey());

        StoragePlanHandler sph = new StoragePlanHandler(getConnection());
        StoragePlanAlterKey akey = new StoragePlanAlterKey();

        // set update values
        akey.updateStatusFlag(st);
        akey.updateLastUpdatePname(getCallerName());

        setUpdateAddQty(akey, result, WorkInfo.RESULT_QTY, StoragePlan.RESULT_QTY);
        setUpdateAddQty(akey, result, WorkInfo.SHORTAGE_QTY, StoragePlan.SHORTAGE_QTY);

        int total = result.getResultQty() + result.getShortageQty();
        if (0 < total)
        {
            akey.updateWorkDay(result.getWorkDay());
        }

        akey.setPlanUkey(result.getPlanUkey());

        sph.modify(akey);
    }


    /**
     * 予定情報のキャンセルを行います。
     * 
     * @param works 元となる入出庫作業<br>
     * 設定単位キーが参照されます
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void cancelPlan(WorkInfo[] works)
            throws ReadWriteException,
                NotFoundException
    {
        StoragePlanHandler sph = new StoragePlanHandler(getConnection());
        WorkInfoController wic = new WorkInfoController(getConnection(), getCaller());

        // create unique list
        Set<String> planukeySet = new LinkedHashSet<String>();
        for (WorkInfo work : works)
        {
            String pUkey = work.getPlanUkey();
            planukeySet.add(pUkey);
        }

        // cancel for plan unique key
        for (Object pUkeyObj : planukeySet)
        {
            String pUkey = String.valueOf(pUkeyObj);
            String pStatus = wic.getPlanStatus(pUkey);

            // update if plan status is NOT STARTED
            if (STATUS_FLAG_UNSTART.equals(pStatus))
            {
                StoragePlanAlterKey akey = new StoragePlanAlterKey();

                akey.updateStatusFlag(STATUS_FLAG_UNSTART);
                akey.updateLastUpdatePname(getCallerName());

                akey.setPlanUkey(pUkey);

                sph.modify(akey);
            }
        }
    }


    ////////////////////////////////////////////////
    // Auto Numbering from here
    ////////////////////////////////////////////////
    /**
     * 引数の入荷伝票No.と荷主コードから入庫予定情報を検索し、
     * 使用されている入荷伝票行No.を取得します。
     * 
     * @param slipNo 入荷伝票No.
     * @param consignorCode 荷主コード
     * @return 入庫予定情報(伝票行No.のみ)
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public StoragePlan[] getLineNos(String slipNo, String consignorCode)
            throws ReadWriteException
    {
        StoragePlanHandler handler = new StoragePlanHandler(getConnection());
        StoragePlanSearchKey key = new StoragePlanSearchKey();

        key.setConsignorCode(consignorCode);
        key.setReceiveTicketNo(slipNo);
        key.setReceiveLineNoCollect();
        key.setReceiveLineNoOrder(true);
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=");

        return (StoragePlan[])handler.find(key);
    }


    /**
     * 引数の入荷伝票No.と荷主コードから入庫予定情報を検索し、
     * 使用されているデータの中で最大の入荷伝票行No.を取得します。
     * 
     * @param slipNo 入荷伝票No.
     * @param consignorCode 荷主コード
     * @return 最大の入荷伝票行No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public int getMaxLineNo(String slipNo, String consignorCode)
            throws ReadWriteException
    {
        StoragePlanHandler handler = new StoragePlanHandler(getConnection());
        StoragePlanSearchKey key = new StoragePlanSearchKey();

        key.setConsignorCode(consignorCode);
        key.setReceiveTicketNo(slipNo);
        key.setReceiveLineNoCollect("Max");
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=");

        return ((StoragePlan[])handler.find(key))[0].getReceiveLineNo();
    }

    ////////////////////////////////////////////////
    // Auto Numbering to here
    ////////////////////////////////////////////////


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
     * 数量の項目に対して、更新データが0でないときに加算更新項目としてセットします。
     * 
     * @param akey 更新キー
     * @param result 元データ
     * @param qtyField 読込み対象フィールド
     * @param upField 更新対象フィールド
     */
    private void setUpdateAddQty(StoragePlanAlterKey akey, WorkInfo result, FieldName qtyField, FieldName upField)
    {
        BigDecimal rQty = result.getBigDecimal(qtyField);
        if (!BigDecimal.ZERO.equals(rQty))
        {
            akey.setUpdateWithColumn(upField, upField, rQty);
        }
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
        return "$Id: StoragePlanController.java 5028 2009-09-18 04:31:29Z kishimoto $";
    }
}
