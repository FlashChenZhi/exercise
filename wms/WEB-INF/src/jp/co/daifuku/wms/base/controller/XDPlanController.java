// $Id: XDPlanController.java 5020 2009-09-17 10:25:05Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.*;

import java.sql.Connection;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;


/**
 * TC予定情報を操作するためのコントローラクラスです。
 *
 *
 * @version $Revision: 5020 $, $Date: 2009-09-17 19:25:05 +0900 (木, 17 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */
public class XDPlanController
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
    public XDPlanController(Connection conn, Class caller)
    {
        super(conn, caller);
    }


    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * TC予定情報を作業中に更新します。
     * 
     * @param planUkey 対象の予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void startPlan(String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        CrossDockPlanHandler rph = new CrossDockPlanHandler(getConnection());
        CrossDockPlanAlterKey akey = new CrossDockPlanAlterKey();

        akey.updateStatusFlag(STATUS_FLAG_NOWWORKING);
        akey.updateLastUpdatePname(getCallerName());

        akey.setPlanUkey(planUkey);

        rph.modify(akey);
    }

//    /**
//     * TC予定情報の完了処理を行います。
//     * 
//     * @param result 完了情報
//     * <ol>
//     * 参照される項目は以下の通りです
//     * <li>予定一意キー
//     * <li>実績数
//     * <li>欠品数
//     * <li>作業日
//     * </ol>
//     * 
//     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
//     * @throws NotFoundException 該当のデータがなかったときスローされます。
//     */
    //    public void completePlan(ReceivingWorkInfo result)
    //            throws ReadWriteException,
    //                NotFoundException
    //    {
    //        ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());
    //        String st = wic.getReceivingPlanStatus(result.getPlanUkey());
    //
    //        CrossDockPlanHandler rph = new CrossDockPlanHandler(getConnection());
    //        CrossDockPlanAlterKey akey = new CrossDockPlanAlterKey();
    //
    //        // set update values
    //        akey.updateStatusFlag(st);
    //        akey.updateLastUpdatePname(getCallerName());
    //
    //        setUpdateAddQty(akey, result, ReceivingWorkInfo.RESULT_QTY, CrossDockPlan.RESULT_QTY);
    //        setUpdateAddQty(akey, result, ReceivingWorkInfo.SHORTAGE_QTY, CrossDockPlan.SHORTAGE_QTY);
    //
    //        int total = result.getResultQty() + result.getShortageQty();
    //        if (0 < total)
    //        {
    //            akey.updateWorkDay(result.getWorkDay());
    //        }
    //
    //        akey.setPlanUkey(result.getPlanUkey());
    //
    //        rph.modify(akey);
    //    	
    //    	throw new Exception("This method, completePlan in XDPlanController class, is not supported.");
    //    	
    //    }
    
//    /**
//     * TC予定情報のキャンセルを行います。
//     * 
//     * @param works 元となる入出庫作業<br>
//     * 設定単位キーが参照されます
//     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
//     * @throws NotFoundException 該当のデータがなかったときスローされます。
//     */
    //    public void cancelPlan(ReceivingWorkInfo[] works)
    //            throws ReadWriteException,
    //                NotFoundException
    //    {
    //    	throw new Exception("This method, cancelPlan in XDPlanController class, is not supported.");
    //    	CrossDockPlanHandler rph = new CrossDockPlanHandler(getConnection());
    //        ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());
    //
    //        // create unique list
    //        Set planukeySet = new LinkedHashSet();
    //        for (ReceivingWorkInfo work : works)
    //        {
    //            String pUkey = work.getPlanUkey();
    //            planukeySet.add(pUkey);
    //        }
    //
    //        // cancel for plan unique key
    //        for (Object pUkeyObj : planukeySet)
    //        {
    //            String pUkey = String.valueOf(pUkeyObj);
    //            String pStatus = wic.getReceivingPlanStatus(pUkey);
    //
    //            // update if plan status is NOT STARTED
    //            if (STATUS_FLAG_UNSTART.equals(pStatus))
    //            {
    //            	CrossDockPlanAlterKey akey = new CrossDockPlanAlterKey();
    //
    //                akey.updateStatusFlag(STATUS_FLAG_UNSTART);
    //                akey.updateLastUpdatePname(getCallerName());
    //
    //                akey.setPlanUkey(pUkey);
    //
    //                rph.modify(akey);
    //            }
    //        }
    //    }
    ////////////////////////////////////////////////
    // Auto Numbering from here
    ////////////////////////////////////////////////
    /**
     * 引数の入荷伝票No.と荷主コードからTC予定情報を検索し、
     * 使用されている入荷伝票行No.を取得します。
     * 
     * @param slipNo 入荷伝票No.
     * @param consignorCode 荷主コード
     * @return TC予定情報(伝票行No.のみ)
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public CrossDockPlan[] getReceivingLineNos(String slipNo, String consignorCode)
            throws ReadWriteException
    {
        CrossDockPlanHandler handler = new CrossDockPlanHandler(getConnection());
        CrossDockPlanSearchKey key = new CrossDockPlanSearchKey();

        key.setConsignorCode(consignorCode);
        key.setReceiveTicketNo(slipNo);
        key.setReceiveLineNoCollect();
        key.setReceiveLineNoOrder(true);
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=");

        return (CrossDockPlan[])handler.find(key);
    }

    /**
     * 引数の出荷伝票No.と荷主コードからTC予定情報を検索し、
     * 使用されている出荷伝票行No.を取得します。
     * 
     * @param slipNo 出荷伝票No.
     * @param consignorCode 荷主コード
     * @return TC予定情報(伝票行No.のみ)
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public CrossDockPlan[] getShippingLineNos(String slipNo, String consignorCode)
            throws ReadWriteException
    {
        CrossDockPlanHandler handler = new CrossDockPlanHandler(getConnection());
        CrossDockPlanSearchKey key = new CrossDockPlanSearchKey();

        key.setConsignorCode(consignorCode);
        key.setShipTicketNo(slipNo);
        key.setShipLineNoCollect();
        key.setShipLineNoOrder(true);
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=");

        return (CrossDockPlan[])handler.find(key);
    }


    /**
     * 引数の入荷伝票No.と荷主コードからTC予定情報を検索し、
     * 使用されているデータの中で最大の入荷伝票行No.を取得します。
     * 
     * @param slipNo 入荷伝票No.
     * @param consignorCode 荷主コード
     * @return 最大の入荷伝票行No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public int getMaxReceivingLineNo(String slipNo, String consignorCode)
            throws ReadWriteException
    {
        CrossDockPlanHandler handler = new CrossDockPlanHandler(getConnection());
        CrossDockPlanSearchKey key = new CrossDockPlanSearchKey();

        key.setConsignorCode(consignorCode);
        key.setReceiveTicketNo(slipNo);
        key.setReceiveLineNoCollect("Max");
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=");

        return ((CrossDockPlan[])handler.find(key))[0].getReceiveLineNo();
    }


    /**
     * 引数の出荷伝票No.と荷主コードからTC予定情報を検索し、
     * 使用されているデータの中で最大の出荷伝票行No.を取得します。
     * 
     * @param slipNo 出荷伝票No.
     * @param consignorCode 荷主コード
     * @return 最大の出荷伝票行No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public int getMaxShippingLineNo(String slipNo, String consignorCode)
            throws ReadWriteException
    {
        CrossDockPlanHandler handler = new CrossDockPlanHandler(getConnection());
        CrossDockPlanSearchKey key = new CrossDockPlanSearchKey();

        key.setConsignorCode(consignorCode);
        key.setShipTicketNo(slipNo);
        key.setShipLineNoCollect("Max");
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=");

        return ((CrossDockPlan[])handler.find(key))[0].getShipLineNo();
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


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: XDPlanController.java 5020 2009-09-17 10:25:05Z kishimoto $";
    }
}
