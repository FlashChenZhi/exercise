// $Id: PCTCustomerMasterModifySCH.java 6303 2009-12-02 07:09:46Z okamura $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTCustomerMasterModifySCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTCustomer;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;

/**
 * 出荷先マスタ修正削除のスケジュール処理を行います。
 *
 * @version $Revision: 6303 $, $Date: 2009-12-02 16:09:46 +0900 (水, 02 12 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class PCTCustomerMasterModifySCH
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
    public PCTCustomerMasterModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * ２画面遷移入力チェック<BR>
     * checkParamで指定された内容をもとに、出荷先マスタ存在チェックを行う。<BR>
     * 該当データが存在した場合はtrueを返す。<BR>
     * パラメータの内容に問題がある場合はfalseを返す。<BR>
     * <BR>
     * @param checkParam チェック条件
     * @return 該当データあり：<CODE>true</CODE>
     *          該当データなし：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean nextCheck(PCTCustomerMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 出荷先マスタデータベースハンドラ
        PCTCustomerHandler sHandler = new PCTCustomerHandler(getConnection());
        // 出荷先マスタ検索キー
        PCTCustomerSearchKey skey = new PCTCustomerSearchKey();

        // 検索キーのセット
        // 荷主コード
        skey.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
        // 出荷先コード
        skey.setCustomerCode(checkParam.getString(CUSTOMER_CODE));

        // 出荷先マスタの検索
        if (sHandler.count(skey) > 0)
        {
            // データが存在する場合はtrueを返却
            return true;
        }

        // (6003011)対象データはありませんでした。
        setMessage("6003011");

        // データが存在しない場合はfalseを返却
        return false;
    }

    /**
     * 出荷先マスタデータ取得処理<BR>
     * １画面目から入力された内容をパラメータとして受け取り、２画面目の出力用のデータを
     * データベースから取得して返します。<BR>
     * searchParamで指定するParameterクラスはMasterInParameter型であること。<BR>
     * @param searchParam 検索条件
     * @return 検索結果
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public List<Params> query(PCTCustomerMasterModifySCHParams searchParam)
            throws CommonException
    {

        // 返却用の配列を生成
        List<Params> result = new ArrayList<Params>();

        // 検索条件
        // 検索キー
        PCTCustomerHandler sHandler = new PCTCustomerHandler(getConnection());
        PCTCustomerSearchKey skey = new PCTCustomerSearchKey();
        // 荷主コード
        skey.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
        // 出荷先コード
        skey.setCustomerCode(searchParam.getString(CUSTOMER_CODE));

        // 取得キー
        // 荷主コード
        skey.setCollect(PCTCustomer.CONSIGNOR_CODE);
        // 出荷先コード
        skey.setCollect(PCTCustomer.CUSTOMER_CODE);
        // 作業優先度
        skey.setCollect(PCTCustomer.JOB_PRIORITY);
        // 出荷先名称
        skey.setCollect(PCTCustomer.CUSTOMER_NAME);
        // 最終更新日時
        skey.setCollect(PCTCustomer.LAST_UPDATE_DATE);
        // 最終使用日
        skey.setCollect(PCTCustomer.LAST_USED_DATE);

        // 検索処理
        PCTCustomer[] customer = (PCTCustomer[])sHandler.find(skey);

        // 取得データ件数分、繰り返す
        for (PCTCustomer ent : customer)
        {
            // パラメータクラス生成
            Params param = new Params();

            // 返却データをセット
            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 出荷先コード
            param.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            param.set(CUSTOMER_NAME, ent.getValue(PCTCustomer.CUSTOMER_NAME, ""));
            // 作業優先度
            param.set(JOB_PRIORITY, ent.getJobPriority());
            // 最終更新日
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());
            // 最終使用日
            param.set(LAST_USED_DATE, ent.getLastUsedDate());

            result.add(param);
            return result;
        }
        //6003011=対象データはありませんでした。
        setMessage("6003011");
        return result;
    }

    /**
     * 修正入力チェック<BR>
     * 修正対象の出荷先情報が使用されているかどうかをチェックします。<BR>
     * <BR>
     * @param checkParam チェック内容
     * @return 他の作業でデータが使用されている場合：<CODE>true</CODE>
     *          他の作業でデータが使用されていない場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean check(PCTCustomerMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 対象データが使用されている場合
        if (!this.existRelation(checkParam))
        {
            // 処理フラグが修正の場合
            if (PCTMasterInParameter.PROCESS_FLAG_MODIFY.equals(checkParam.getString(PROCESS_FLAG)))
            {
                //MSG-W0010=修正を行うデータが使用されています。修正を行いますか？
                setDispMessage("MSG-W0010");
            }
            // 処理フラグが削除の場合
            else
            {
                //6023003=削除を行うデータが使用されています。
                setMessage("6023003");
            }
            // 対象データが使用されている場合falseを返却
            return false;
        }
        // 対象データが使用されていない場合trueを返却
        return true;
    }

    /**
     * 出荷先マスタ修正･削除スケジュール開始処理<BR>
     * startParamで指定するParameterクラスはCustomerMasterModifySCHParams型であること。<BR>
     * <BR>
     * @param startParam 修正内容
     * @return 修正削除処理が正常に終了した場合：<CODE>true</CODE>
     *          修正削除処理が異常終了した場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(PCTCustomerMasterModifySCHParams startParam)
            throws CommonException
    {
        // 出荷先マスタデータベースハンドラ
        PCTCustomerHandler cHandler = new PCTCustomerHandler(getConnection());
        // 出荷先マスタ検索キー
        PCTCustomerSearchKey skey = new PCTCustomerSearchKey();
        // 出荷先マスタ更新キー
        PCTCustomerAlterKey akey = new PCTCustomerAlterKey();

        try
        {
            // 日次更新・取込中・排他チェック
            if (!canStart() || isLoadData() || !isNoModify(startParam))
            {
                return false;
            }

            // 検索キーのセット
            // 検索キーのクリア
            skey.clear();
            // 荷主コード
            skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 出荷先コード
            skey.setCustomerCode(startParam.getString(CUSTOMER_CODE));
            // 出荷先名称
            skey.setCustomerName(startParam.getString(CUSTOMER_NAME));
            // 作業優先度
            skey.setJobPriority(startParam.getInt(JOB_PRIORITY));

            // 修正処理
            if (PCTMasterInParameter.PROCESS_FLAG_MODIFY.equals(startParam.getString(PROCESS_FLAG)))
            {
                // 更新条件のセット
                // 荷主コード
                akey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
                // 出荷先コード
                akey.setCustomerCode(startParam.getString(CUSTOMER_CODE));

                // 更新値のセット
                // 出荷先名称
                akey.updateCustomerName(startParam.getString(CUSTOMER_NAME));
                // 最終更新処理名
                akey.updateLastUpdatePname(this.getClass().getSimpleName());
                // 最終使用日
                akey.updateLastUsedDate(new SysDate());
                // 作業優先度
                akey.updateJobPriority(startParam.getInt(JOB_PRIORITY));

                // 出荷先マスタ(更新)
                cHandler.modify(akey);


                // 更新後の出荷先マスタ取得
                // 検索キーのクリア
                skey.clear();
                // 出荷先コード
                skey.setCustomerCode(startParam.getString(CUSTOMER_CODE));
                // 荷主コード
                skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));

                // (6001004)修正しました。
                setMessage("6001004");
            }
            // 削除処理
            else
            {
                // 検索キーのクリア
                skey.clear();

                // 削除条件のセット
                // 出荷先コード
                skey.setCustomerCode(startParam.getString(CUSTOMER_CODE));
                // 荷主コード
                skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));

                // 出荷先マスタ(削除)
                cHandler.drop(skey);

                // (6001005)削除しました。
                setMessage("6001005");
            }
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
        // データが存在しなかった場合
        catch (NotFoundException e)
        {
            // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください
            setMessage("6023004");

            // 異常の場合はfalseを返却
            return false;
        }
        // データがロックされていた場合
        catch (LockTimeOutException e)
        {
            // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください
            setMessage("6023004");

            // 異常の場合はfalseを返却
            return false;
        }
    }

    /** 
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT出庫予定作業情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * @param p 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException 全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // 出荷先マスタ情報
        PCTCustomerHandler pctcustomerHandler = new PCTCustomerHandler(getConnection());
        PCTCustomerSearchKey skey = new PCTCustomerSearchKey();

        // 取得
        skey.setConsignorCodeCollect();
        skey.setConsignorCodeGroup();
        skey.setCollect(PCTCustomer.CONSIGNOR_NAME, "MAX", _CONSIGNOR_NAME);

        if (pctcustomerHandler.count(skey) == 1)
        {
            Params outParam = new Params();
            PCTCustomer pctcustomer = (PCTCustomer)pctcustomerHandler.findPrimary(skey);

            // 荷主コード
            outParam.set(PCTCustomerMasterModifySCHParams.CONSIGNOR_CODE, pctcustomer.getConsignorCode());

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

    /**
     * 出荷先マスタ関連テーブルチェック<BR>
     * checkParam内の条件に合致する出荷先情報が他のテーブルに存在しないか確認します。<BR>
     * 他のテーブルに出荷先情報が存在する場合は<CODE>true</CODE>を、<BR>
     * 見つからない場合はfalseを返します。<BR>
     * 画面側の更新・削除事前チェック処理で使用します。<BR>
     * @param checkParam 検索条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean existRelation(PCTCustomerMasterModifySCHParams checkParam)
            throws CommonException
    {

        // システム定義情報のコンストラクタ
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        if (sysController.hasPCTRetrievalPack())
        {
            // ----------出庫予定情報----------
            if (!existRetrievalPlan(checkParam))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 出荷先マスタロック処理<BR>
     * 対象の仕入先情報が他の端末で更新されていないかを確認します。<BR>
     * <BR>
     * @param checkParam 出荷先マスタチェック条件
     * @return 更新されていない：<CODE>True</CODE>
     *          更新されている：<CODE>False</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean isNoModify(PCTCustomerMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 出荷先マスタデータベースハンドラ
        PCTCustomerHandler sHandler = new PCTCustomerHandler(getConnection());
        // 出荷先マスタ検索キー
        PCTCustomerSearchKey skey = new PCTCustomerSearchKey();

        // 検索キーのセット
        // 荷主コード
        skey.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
        // 出荷先コード
        skey.setCustomerCode(checkParam.getString(CUSTOMER_CODE));
        // 最終更新日
        skey.setKey(PCTCustomer.LAST_UPDATE_DATE, checkParam.getDate(LAST_UPDATE_DATE));

        // 対象データロック処理
        if (sHandler.findPrimaryForUpdate(skey, PCTCustomerHandler.WAIT_SEC_NOWAIT) != null)
        {
            // 更新されていなかった場合はtrueを返却
            return true;
        }

        // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください。
        setMessage("6023004");

        // 更新されている場合はfalseを返却
        return false;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    /**
     * 出庫予定情報にパラメータの条件に合致するデータが存在しないかチェックします。<BR>
     * 該当データが存在する場合は<CODE>false</CODE>を、存在しない場合は<CODE>true</CODE>を
     * 返します。<BR>
     * @param inParam 検索条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRetrievalPlan(PCTCustomerMasterModifySCHParams inParam)
            throws CommonException
    {
        PCTRetPlanHandler pctRetPlanHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey pctRetPlanKey = new PCTRetPlanSearchKey();
        // 出荷先コード
        pctRetPlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 荷主コード
        pctRetPlanKey.setCustomerCode(inParam.getString(CUSTOMER_CODE));
        // 状態フラグ(削除以外)
        pctRetPlanKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 検索処理
        if (pctRetPlanHandler.count(pctRetPlanKey) > 0)
        {
            return false;
        }
        return true;
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
