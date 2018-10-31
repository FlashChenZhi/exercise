// $Id: WeightErrorRateSettingSCH.java 4403 2009-06-08 03:49:16Z ota $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.WeightErrorRateSettingSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.base.util.PCTDisplayUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.PCTItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

/**
 * 重量誤差率一括設定のスケジュール処理を行います。
 *
 * @version $Revision: 4403 $, $Date: 2009-06-08 12:49:16 +0900 (月, 08 6 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class WeightErrorRateSettingSCH
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
    public WeightErrorRateSettingSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * パラメータの内容が正しいかチェックを行います。<BR>
     * <CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、 パラメータ入力内容チェック処理を行います。<BR>
     * 必要に応じて、各継承クラスで実装してください。
     * 
     * @param checkParam
     *            入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力内容が正常な場合 false：そうでない場合
     * @throws CommonException
     *             例外が発生した場合に通知されます。
     */
    public boolean check(WeightErrorRateSettingSCHParams checkParam)
            throws CommonException
    {

        // 商品マスタチェック
        if (!itemload())
        {
            return false;
        }

        // 単重量範囲の対象データがPCTItemマスタの存在するかチェックします。
        // 荷主コードでDMCONSIGNOR内のデータ有無をチェックします。
        PCTItemHandler iHandler = new PCTItemHandler(getConnection());
        PCTItemSearchKey skey = new PCTItemSearchKey();

        // 検索条件
        skey.clear();


        if (!StringUtil.isBlank(checkParam.getString(FROM_SINGLE_WEIGHT))
                && !StringUtil.isBlank(checkParam.getString(TO_SINGLE_WEIGHT)))
        {
            int comparison = checkParam.getString(FROM_SINGLE_WEIGHT).compareTo(checkParam.getString(TO_SINGLE_WEIGHT));

            if (0 < comparison)
            {
                // 範囲重量(From)
                skey.setSingleWeight(Double.parseDouble(checkParam.getString(TO_SINGLE_WEIGHT)), ">=");
                // 範囲重量(To)
                skey.setSingleWeight(Double.parseDouble(checkParam.getString(FROM_SINGLE_WEIGHT)), "<=");
            }
            else
            {
                // 範囲重量(From)
                skey.setSingleWeight(Double.parseDouble(checkParam.getString(FROM_SINGLE_WEIGHT)), ">=");
                // 範囲重量(To)
                skey.setSingleWeight(Double.parseDouble(checkParam.getString(TO_SINGLE_WEIGHT)), "<=");
            }
        }
        else
        {
            if (!StringUtil.isBlank(checkParam.getString(FROM_SINGLE_WEIGHT)))
            {
                // 範囲重量(From)
                skey.setSingleWeight(Double.parseDouble(checkParam.getString(FROM_SINGLE_WEIGHT)), ">=");
            }
            else if (!StringUtil.isBlank(checkParam.getString(TO_SINGLE_WEIGHT)))
            {
                // 範囲重量(To)
                skey.setSingleWeight(Double.parseDouble(checkParam.getString(TO_SINGLE_WEIGHT)), "<=");
            }
        }

        int w_count = iHandler.count(skey);

        if (w_count > 0)
        {
            return true;
        }
        else
        {
            // 6023059=更新対象データがありません。
            setMessage("6023059");
            return false;
        }
    }

    /**
     * PCT商品マスタ更新<BR>
     * startParamで指定されたパラメータの内容をもとに、商品マスタ更新処理を行う。<BR>
     * 正常完了した場合はtrueを、登録失敗、日次更新中など、処理条件に問題があり、<BR>
     * 処理を中断した場合はfalseを返す。処理結果のメッセージはgetMessage()メソッドで取得する。<BR>
     * 
     * @param startParams
     *            対象データ条件及び更新内容
     * @return 正常：<CODE>true</CODE>異常：<CODE>false</CODE>
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(WeightErrorRateSettingSCHParams startParams)
            throws CommonException
    {
        // Finder 
        PCTItemFinder iFinder = null;
        try
        {
            // チェック処理
            if (!check(startParams))
            {
                return false;
            }

            // 条件チェックを行う
            // 日次更新チェック
            if (!canStart())
            {
                return false;
            }

            // 取り込み中チェック
            if (isLoadData())
            {
                return false;
            }

            // PCT商品マスタ
            iFinder = new PCTItemFinder(getConnection());
            PCTItemHandler iHandler = new PCTItemHandler(getConnection());
            PCTItemSearchKey skey = new PCTItemSearchKey();
            PCTItemAlterKey akey = new PCTItemAlterKey();

            // 検索条件クリア
            skey.clear();
            // 更新対象条件
            if (!StringUtil.isBlank(startParams.getString(FROM_SINGLE_WEIGHT))
                    && !StringUtil.isBlank(startParams.getString(TO_SINGLE_WEIGHT)))
            {
                int comparison =
                        startParams.getString(FROM_SINGLE_WEIGHT).compareTo(startParams.getString(TO_SINGLE_WEIGHT));

                if (0 < comparison)
                {
                    // 範囲重量(From)
                    skey.setSingleWeight(Double.parseDouble(startParams.getString(TO_SINGLE_WEIGHT)), ">=");
                    // 範囲重量(To)
                    skey.setSingleWeight(Double.parseDouble(startParams.getString(FROM_SINGLE_WEIGHT)), "<=");
                }
                else
                {
                    // 範囲重量(From)
                    skey.setSingleWeight(Double.parseDouble(startParams.getString(FROM_SINGLE_WEIGHT)), ">=");
                    // 範囲重量(To)
                    skey.setSingleWeight(Double.parseDouble(startParams.getString(TO_SINGLE_WEIGHT)), "<=");
                }
            }
            else
            {
                if (!StringUtil.isBlank(startParams.getString(FROM_SINGLE_WEIGHT)))
                {
                    // 範囲重量(From)
                    skey.setSingleWeight(Double.parseDouble(startParams.getString(FROM_SINGLE_WEIGHT)), ">=");
                }
                else if (!StringUtil.isBlank(startParams.getString(TO_SINGLE_WEIGHT)))
                {
                    // 範囲重量(To)
                    skey.setSingleWeight(Double.parseDouble(startParams.getString(TO_SINGLE_WEIGHT)), "<=");
                }
            }

            // 取得情報
            // 荷主コード
            skey.setConsignorCodeCollect();
            // 商品コード
            skey.setItemCodeCollect();
            // 単重量
            skey.setSingleWeightCollect();
            // ロット入数
            skey.setLotEnteringQtyCollect();

            // 更新目的にて、対象情報一覧を取得する。
            iFinder.open(true);

            iFinder.searchForUpdate(skey, HandlerSysDefines.WAIT_SEC_NOWAIT);

            while (iFinder.hasNext())
            {

                for (PCTItem result : (PCTItem[])iFinder.getEntities(100))
                {
                    // 更新条件クリア
                    akey.clear();

                    // 更新条件
                    // 荷主コード一致
                    akey.setConsignorCode(result.getConsignorCode());
                    // 商品コード一致
                    akey.setItemCode(result.getItemCode());
                    // ロット入数
                    akey.setLotEnteringQty(result.getLotEnteringQty());
                    // 単重量
                    akey.setSingleWeight(result.getSingleWeight());
                    // 更新値編集
                    // 最大検品単位数再計算
                    akey.updateMaxInspectionUnitQty(PCTDisplayUtil.getMaxInspectionUnitQty(startParams.getInt(WEIGHT_DISTINCT_RATE)));
                    // 誤差率
                    akey.updateWeightDistinctRate(startParams.getInt(WEIGHT_DISTINCT_RATE));

                    String pname = this.getClass().getSimpleName();

                    // 最終更新処理名
                    akey.updateLastUpdatePname(pname);

                    // 商品マスタを更新
                    iHandler.modify(akey);
                }
            }

            // 6001006=設定しました。
            setMessage("6001006");
            return true;
        }
        catch (NotFoundException e)
        {
            // 6023059=更新対象データがありません。
            setMessage("6023059");
            return false;
        }
        finally
        {
            if (iFinder != null)
            {
                iFinder.close();
            }
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
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * PCTシステム情報を検索し、商品マスタ取込みフラグを確認します。
     * 
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     * @throws NoPrimaryException NoPrimaryException 一意項目が重複している場合にスローされます。
     */
    protected boolean itemload()
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {
        // PCTシステム情報ハンドラ類インスタンス生成
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey wSKey = new PCTSystemSearchKey();
        PCTSystem pctSystem = (PCTSystem)wHandler.findPrimary(wSKey);

        if (pctSystem != null)
        {
            if (PCTSystem.PCTMASTER_LOAD_FLAG_LOAD.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024068=ロード処理中のため処理できません。
                setMessage("6024068");
                return false;
            }
        }
        return true;
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
