// $Id: PCTOrderNoController.java 3209 2009-03-02 06:34:19Z arai $
// Handler v3.8
package jp.co.daifuku.pcart.retrieval.controller;

/*
 * Copyright 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTCustomer;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.PCTSystem;

/**
 * PCTシステム情報を読込みオーダーNoを変換するためのクラスです。<br>
 *
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  Last commit: $Author: arai $
 */
public class PCTOrderNoController
        extends PCTSystemFinder
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    /**
     * データベースコネクションを指定してインスタンスを生成します。
     * @param conn 接続済みのデータベースコネクション
     */
    public PCTOrderNoController(Connection conn)
    {
        super(conn);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * オーダーNo.とオーダーSeqNo.に分割します。
     * 戻り値の要素0にはオーダーNo.、要素1にはオーダーSeqNo.を返します。
     * @param planOrderNo 予定オーダーNo
     * @return 分割オーダーNo.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String[] dividesOrderNo(String planOrderNo)
            throws ReadWriteException
    {
        // PCTシステム情報ハンドラ生成
        PCTSystemHandler systemHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey systemshKey = new PCTSystemSearchKey();
        systemshKey.clear();
        PCTSystem[] systemEntity = (PCTSystem[])systemHandler.find(systemshKey);
        int orderNoLength = 0;
        String tmpOrderNo = null;
        String tmpOrderSeqNo = null;
        String[] returnData = new String[2];

        orderNoLength = planOrderNo.length() - systemEntity[0].getSeqDigit();
        if (systemEntity[0].getCheckDigitFlag().equals(PCTRetrievalInParameter.CHECK_DIGIT_ON) == true)
        {
            orderNoLength = orderNoLength - 1;
        }

        // オーダーNo.
        tmpOrderNo = String.valueOf(planOrderNo.substring(0, orderNoLength));
        // オーダーSeqNo.
        tmpOrderSeqNo =
                String.valueOf(planOrderNo.substring(orderNoLength, orderNoLength + systemEntity[0].getSeqDigit()));

        returnData[0] = tmpOrderNo;
        returnData[1] = tmpOrderSeqNo;

        return returnData;
    }

    /**
     * 荷主コード・出荷先コードより、予定オーダーNoの採番処理を行います。<BR>
     * @param conn      データベースとのコネクションオブジェクト
     * @param pConsignorCode    荷主コード
     * @param pCustomerCode    出荷先コード
     * @param pConsignorName    荷主名称
     * @param pCustomerName    出荷先名称
     * @param caller 呼び出し元クラス
     * @return String   オーダーNo マルチチェックにて採番が行えなかった場合、Nullを復帰します。
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    public String makePlanOrderNo(Connection conn, String pConsignorCode, String pCustomerCode, String pConsignorName,
            String pCustomerName, Class caller)
            throws CommonException
    {
        int LOOP_MAX = 99999;
        String rOrderNo = "";
        String updSeqNo = "00000";
        String SeqDegit = "00000";
        boolean getFlag = false;

        // PCT出荷先マスタ取得
        PCTCustomerHandler customerHandler = new PCTCustomerHandler(conn);
        PCTCustomerSearchKey customerSearchKey = new PCTCustomerSearchKey();
        PCTCustomerAlterKey customerAlterKey = new PCTCustomerAlterKey();

        // PCT出庫予定情報ハンドラ生成
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(conn);
        PCTRetPlanSearchKey planSearchKey = new PCTRetPlanSearchKey();

        // PCT出庫作業情報ハンドラ生成
        PCTRetWorkInfoHandler workHandler = new PCTRetWorkInfoHandler(conn);
        PCTRetWorkInfoSearchKey workSearchKey = new PCTRetWorkInfoSearchKey();

        // PCTシステム情報ハンドラ生成
        PCTSystemHandler systemHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey systemshKey = new PCTSystemSearchKey();
        systemshKey.clear();
        PCTSystem[] systemEntity = (PCTSystem[])systemHandler.find(systemshKey);

        // 検索条件クリア
        customerSearchKey.clear();
        // 荷主コード
        customerSearchKey.setConsignorCode(pConsignorCode);
        // 出荷先コード
        customerSearchKey.setCustomerCode(pCustomerCode);

        PCTCustomer[] rPCTCustomer = (PCTCustomer[])customerHandler.find(customerSearchKey);

        if (ArrayUtil.isEmpty(rPCTCustomer))
        {
            updSeqNo = "00000";

            // PCT出荷先マスタ登録
            PCTCustomer pCust = new PCTCustomer();

            // 荷主コード
            pCust.setConsignorCode(pConsignorCode);
            // 荷主名称
            pCust.setConsignorName(pConsignorName);
            // 得意先コード
            pCust.setRegularCustomerCode("");
            // 出荷先コード
            pCust.setCustomerCode(pCustomerCode);
            // 出荷先名称
            pCust.setCustomerName(pCustomerName);
            // 出荷先分類
            pCust.setCustomerCategory("");
            // SeqNo
            pCust.setSeqNo(updSeqNo);
            // 登録処理名
            pCust.setRegistPname(getClass().getSimpleName());
            // 最終更新処理名
            pCust.setLastUpdatePname(getClass().getSimpleName());

            customerHandler.create(pCust);
        }
        else
        {
            updSeqNo = rPCTCustomer[0].getSeqNo();
        }

        // PCT出庫予定情報内でのマルチチェック
        for (int lc = 0; lc < LOOP_MAX; lc++)
        {
            // オーダーNo編集
            rOrderNo = pCustomerCode + updSeqNo + SeqDegit.substring(0, systemEntity[0].getSeqDigit());
            if (systemEntity[0].getCheckDigitFlag().equals(PCTRetrievalInParameter.CHECK_DIGIT_ON) == true)
            {
                // チェックデジット計算
                rOrderNo = rOrderNo + calcCheckDegit(rOrderNo);
            }

            // PCT出庫予定情報に対して、マルチチェック
            // 条件クリア
            planSearchKey.clear();
            // 予定オーダーNo一致
            planSearchKey.setPlanOrderNo(rOrderNo);

            // 対象件数取得
            if (planHandler.count(planSearchKey) > 0)
            {
                int w_Seqno = Integer.parseInt(updSeqNo);
                w_Seqno++;
                if (w_Seqno > LOOP_MAX)
                {
                    updSeqNo = "00000";
                }
                else
                {
                    String _workStrOrder = "00000" + String.valueOf(w_Seqno);
                    updSeqNo = _workStrOrder.substring(_workStrOrder.length() - 5);
                }
                continue;
            }

            // PCT出庫作業情報に対して、マルチチェック
            // 条件クリア
            workSearchKey.clear();
            // 予定オーダーNo一致
            workSearchKey.setPlanOrderNo(rOrderNo);

            // 対象件数取得
            if (workHandler.count(workSearchKey) > 0)
            {
                int w_Seqno = Integer.parseInt(updSeqNo);
                w_Seqno++;
                if (w_Seqno > LOOP_MAX)
                {
                    updSeqNo = "00000";
                }
                else
                {
                    String _workStrOrder = "00000" + String.valueOf(w_Seqno);
                    updSeqNo = _workStrOrder.substring(_workStrOrder.length() - 5);
                }
                continue;
            }
            getFlag = true;
            break;
        }

        if (!getFlag)
        {
            // オーダーNoの採番が行えませんでした。
            return null;
        }

        // 取得したSEQNoにてPCT出荷先マスタを更新します。
        customerAlterKey.clear();
        // 更新条件
        // 荷主コード一致
        customerAlterKey.setConsignorCode(pConsignorCode);
        // 出荷先コード一致
        customerAlterKey.setCustomerCode(pCustomerCode);

        // SeqNoを更新
        int _wOrderNo = Integer.parseInt(updSeqNo) + 1;
        if (_wOrderNo > LOOP_MAX)
        {
            _wOrderNo = 0;
        }
        String _workStrOrder = "00000" + String.valueOf(_wOrderNo);
        customerAlterKey.updateSeqNo(_workStrOrder.substring(_workStrOrder.length() - 5));
        // 最終更新処理名
        customerAlterKey.updateLastUpdatePname(getClass().getSimpleName());

        customerHandler.modify(customerAlterKey);

        return rOrderNo;
    }

    /**
     * オーダーNoより、実績オーダーNo、SeqNoの採番処理を行います。<BR>
     * 戻り値の要素0にはオーダーNo.、要素1にはオーダーSeqNo.を返します。
     * @param conn      データベースとのコネクションオブジェクト
     * @param pOrderNo    オーダーNo
     * @return String   実績オーダーNo.。チェックにてデータが存在しなかった場合、Nullを復帰します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public String[] makeResultOrderNo(Connection conn, String pOrderNo)
            throws ReadWriteException
    {
        String rOrderNo = "";
        String rOrderSeq = "";
        String SeqDegit = "00000";
        String CheckDegit = "0";
        String[] returnData = new String[2];

        // PCT出庫作業情報ハンドラ生成
        PCTRetWorkInfoHandler workHandler = new PCTRetWorkInfoHandler(conn);
        PCTRetWorkInfoSearchKey workSearchKey = new PCTRetWorkInfoSearchKey();

        // PCTシステム情報ハンドラ生成
        PCTSystemHandler systemHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey systemshKey = new PCTSystemSearchKey();
        systemshKey.clear();
        PCTSystem[] systemEntity = (PCTSystem[])systemHandler.find(systemshKey);

        // PCT出庫作業情報に対して、SeqNoの最大値を取得
        // 条件クリア
        workSearchKey.clear();
        // オーダーSeqNo 最大値を取得
        workSearchKey.setOrderSeqCollect("MAX");
        // オーダーNo一致
        workSearchKey.setOrderNo(pOrderNo);

        PCTRetWorkInfo[] rPCTRetWorkInfo = (PCTRetWorkInfo[])workHandler.find(workSearchKey);

        // 対象データ取得
        if (rPCTRetWorkInfo.length <= 0)
        {
            //対象データが存在しません
            return null;
        }

        SeqDegit = SeqDegit + String.valueOf((Integer.valueOf(rPCTRetWorkInfo[0].getOrderSeq()) + 1));
        rOrderSeq =
                SeqDegit.substring((SeqDegit.length() - systemEntity[0].getSeqDigit()),
                        (SeqDegit.length() - systemEntity[0].getSeqDigit()) + systemEntity[0].getSeqDigit());

        // オーダーNo編集
        rOrderNo = pOrderNo + rOrderSeq;
        if (systemEntity[0].getCheckDigitFlag().equals(PCTRetrievalInParameter.CHECK_DIGIT_ON) == true)
        {
            // チェックデジット計算
            CheckDegit = calcCheckDegit(rOrderNo);
            rOrderNo = rOrderNo + CheckDegit;
        }

        returnData[0] = rOrderNo;
        returnData[1] = rOrderSeq;

        return returnData;
    }

    /**
     * モジュラス１０／ウェイト３形式のチェックデジット計算を行います。<BR>
     * @param pcode    計算元
     * @return String  チェックデジット値。
     */
    public String calcCheckDegit(String pcode)
    {
        int _checkDegit = 0;

        for (int lc = 0; lc < pcode.length(); lc++)
        {
            if ((lc % 2) == 0)
            {
                // ループ回数が偶数時、そのまま加算
                _checkDegit += Integer.parseInt(pcode.substring(lc, lc + 1));
            }
            else
            {
                // ループ回数が奇数時、３倍値を加算
                _checkDegit += Integer.parseInt(pcode.substring(lc, lc + 1)) * 3;
            }
        }
        if (_checkDegit > 0)
        {
            _checkDegit = 10 - (_checkDegit % 10);
            if (_checkDegit == 10)
            {
                _checkDegit = 0;
            }
        }

        return (String.valueOf(_checkDegit));
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 3209 $,$Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $");
    }
}
