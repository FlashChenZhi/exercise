// $Id: PCTCustomerMasterRegistSCH.java 4259 2009-05-12 05:33:55Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTCustomerMasterRegistSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTCustomer;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 出荷先マスタ登録のスケジュール処理を行います。
 *
 * @version $Revision: 4259 $, $Date: 2009-05-12 14:33:55 +0900 (火, 12 5 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTCustomerMasterRegistSCH
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
    // private String _instanceVar ;
    /** 保存用のフィールド 荷主名称(<code>CONSIGNOR_NAME</code>) */
    private FieldName _CONSIGNOR_NAME = new FieldName("", "CONSIGNOR_NAME");

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
    public PCTCustomerMasterRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 出荷先マスタ登録処理<BR>
     * startParamsで指定された内容をもとに、出荷先マスタの登録処理を行う。<BR>
     * 正常に登録出来た場合はtrueを、処理中に問題が発生した場合はfalseを返す。<BR>
     * 処理結果のメッセージはgetMessage()メソッドで取得できる。<BR>
     * <BR>
     * @param startParam 登録内容
     * @return 正常終了：<CODE>true</CODE>
     *          異常終了：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(PCTCustomerMasterRegistSCHParams startParam)
            throws CommonException
    {
        // 出荷先マスタエンティティ
        PCTCustomer ent = new PCTCustomer();
        // 出荷先マスタデータベースハンドラ
        PCTCustomerHandler cHandler = new PCTCustomerHandler(getConnection());
        // 出荷先マスタ検索キー
        PCTCustomerSearchKey key = new PCTCustomerSearchKey();

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {
            // 日次更新・取込中チェック
            if (!canStart() || isLoadData())
            {
                return false;
            }

            // 重複チェック(DMCustomer内)
            // 荷主コード（システム定義）
            key.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 出荷先コード
            key.setCustomerCode(startParam.getString(CUSTOMER_CODE));

            // 既に存在する場合
            if (cHandler.count(key) > 0)
            {
                // (6023006)入力された出荷先コードは既に登録されています
                setMessage("6023006");

                // 異常の場合はfalseを返却
                return false;
            }

            // 登録データのセット
            // 荷主コード
            ent.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 荷主名称
            ent.setConsignorName(getConsignorName(startParam.getString(CONSIGNOR_CODE)));
            // 出荷先コード
            ent.setCustomerCode(startParam.getString(CUSTOMER_CODE));
            // 出荷先名称
            ent.setCustomerName(startParam.getString(CUSTOMER_NAME));
            // SeqNo.
            ent.setSeqNo("00000");
            // 作業優先度
            ent.setJobPriority(startParam.getInt(JOB_PRIORITY));
            // 登録処理名
            ent.setRegistPname(cName);
            // 最終更新処理名
            ent.setLastUpdatePname(cName);

            // 出荷先マスタ(新規登録)
            cHandler.create(ent);


            // (6001003)登録しました。
            setMessage("6001003");

            // 正常の場合はtrueを返却
            return true;
        }
        // DBアクセスエラーが発生した場合
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");

            // 異常の場合はfalseを返却
            return false;
        }
        // データ重複エラーが発生した場合
        catch (DataExistsException e)
        {
            // (6023006)入力された出荷先コードは既に登録されています
            setMessage("6023006");

            // 異常の場合はfalseを返却
            return false;
        }
    }

    /** 
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT出庫予定作業情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * @param searchParam 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException 全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // 予定情報
        PCTRetPlanHandler pctretplanHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey skey = new PCTRetPlanSearchKey();

        // 取得
        skey.setConsignorCodeCollect();
        skey.setConsignorCodeGroup();
        skey.setCollect(PCTRetPlan.CONSIGNOR_NAME, "MAX", _CONSIGNOR_NAME);

        // 検索条件
        skey.setStatusFlag(PCTRetPlan.STATUS_FLAG_DELETE, "!=");

        if (pctretplanHandler.count(skey) == 1)
        {
            Params outParam = new Params();
            PCTRetPlan pctretplan = (PCTRetPlan)pctretplanHandler.findPrimary(skey);
            // 荷主コード
            outParam.set(PCTCustomerMasterRegistSCHParams.CONSIGNOR_CODE, pctretplan.getConsignorCode());
            // 荷主名称
            outParam.set(PCTCustomerMasterRegistSCHParams.CONSIGNOR_NAME,
                    pctretplan.getValue(PCTRetPlan.CONSIGNOR_NAME));

            return outParam;
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
    /**
     * パラメータの内容が正しいかチェックを行います。<BR>
     * <CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、
     * パラメータ入力内容チェック処理を行います。<BR>
     * 必要に応じて、各継承クラスで実装してください。
     * @param consignorCode 荷主コード
     * @return true：入力内容が正常な場合  false：そうでない場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private String getConsignorName(String consignorCode)
            throws CommonException
    {

        PCTRetPlanHandler iHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey ikey = new PCTRetPlanSearchKey();

        // 検索条件
        ikey.clear();
        // 荷主コード
        ikey.setConsignorCode(consignorCode);
        // 取得項目
        ikey.setCollect(PCTRetPlan.CONSIGNOR_NAME, "MAX", _CONSIGNOR_NAME);

        // 検索条件
        ikey.setStatusFlag(PCTRetPlan.STATUS_FLAG_DELETE, "!=");

        PCTRetPlan[] planEntity = (PCTRetPlan[])iHandler.find(ikey);

        if (planEntity.length > 0)
        {
            return ((String)planEntity[0].getConsignorName());
        }
        else
        {
            return "";
        }
    }

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
