// $Id: PCTItemRegistSCH.java 7162 2010-02-19 09:32:59Z shibamoto $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTItemRegistSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.base.util.PCTDisplayUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.SystemDefine;


/**
 * 商品マスタ登録のスケジュール処理を行います。
 *
 * @version $Revision: 7162 $, $Date:: 2010-02-19 18:32:59 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTItemRegistSCH
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
    public PCTItemRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 商品マスタ登録<BR>
     * startParamで指定されたパラメータの内容をもとに、商品マスタ登録処理を行う。<BR>
     * 正常完了した場合はtrueを、登録失敗、日次更新中など、処理条件に問題があり、<BR>
     * 処理を中断した場合はfalseを返す。処理結果のメッセージはgetMessage()メソッドで取得する。<BR>
     * @param startParams 登録内容
     * @return 正常：<CODE>true</CODE>異常：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        try
        {
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

            // 商品マスタチェック
            if (isItemLoad())
            {
                return false;
            }

            // 入力された荷主コードがマスタ登録されているかチェックします。
            // 荷主コードでDMCONSIGNOR内のデータ有無をチェックします。
            ConsignorHandler cHandler = new ConsignorHandler(getConnection());
            ConsignorSearchKey ckey = new ConsignorSearchKey();
            // 検索条件
            ckey.clear();
            // 荷主コード
            ckey.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));

            int w_count = cHandler.count(ckey);
            if (w_count <= 0)
            {
                // 6023040=荷主コードがマスタに登録されていません。
                setMessage(WmsMessageFormatter.format(6023040));
                return false;
            }

            // 重複チェック
            // 荷主コード、商品コードでDMPCTITEM内の重複チェックを行う。
            PCTItemHandler iHandler = new PCTItemHandler(getConnection());
            PCTItemSearchKey ikey = new PCTItemSearchKey();

            // 荷主コード
            ikey.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
            // 商品コード
            ikey.setItemCode(ps[0].getString(ITEM_CODE));
            // ロット入数
            ikey.setLotEnteringQty(ps[0].getInt(LOT_QTY));

            if (iHandler.count(ikey) > 0)
            {
                // 重複データあり
                // 6023007=入力された商品コードは既に登録されています
                setMessage(WmsMessageFormatter.format(6023007));
                return false;
            }

            // パラメータで取り受けた内容で登録処理を行う。
            // 商品エンティティクラス
            PCTItem itemEntity = new PCTItem();

            // システム管理区分（通常）
            itemEntity.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
            // 荷主コード
            itemEntity.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
            // 荷主名称
            itemEntity.setConsignorName(getConsignorName(ps[0].getString(CONSIGNOR_CODE)));
            // 商品コード
            itemEntity.setItemCode(ps[0].getString(ITEM_CODE));
            // 商品名称
            itemEntity.setItemName(ps[0].getString(ITEM_NAME));
            // JANコード
            itemEntity.setJan(ps[0].getString(JAN));
            // ケースITF
            itemEntity.setItf(ps[0].getString(ITF));
            // ボールITF
            itemEntity.setBundleItf(ps[0].getString(BUNDLE_ITF));
            // ロット入数
            itemEntity.setLotEnteringQty(ps[0].getInt(LOT_QTY));
            // 単重量
            // 重量誤差率
            // 最大検品可能数
            if (StringUtil.isBlank(ps[0].getString(SINGLE_WEIGHT)))
            {
                // 単重量未入力時０をセットします。
                ps[0].set(SINGLE_WEIGHT, 0.0);
                ps[0].set(MAX_INSPECTION_UNIT_QTY, 0);
            }
            else if (Double.valueOf(ps[0].getString(SINGLE_WEIGHT)) <= 0)
            {
                // 単重量未入力時０をセットします。
                ps[0].set(SINGLE_WEIGHT, 0.0);
                ps[0].set(MAX_INSPECTION_UNIT_QTY, 0);
            }

            if (ps[0].getInt(WEIGHT_DISTINCT_RATE) <= 0)
            {
                // 誤差率未入力時
                ps[0].set(WEIGHT_DISTINCT_RATE, getDefaultDistinctRate());
            }
            ps[0].set(MAX_INSPECTION_UNIT_QTY,
                    PCTDisplayUtil.getMaxInspectionUnitQty(ps[0].getInt(WEIGHT_DISTINCT_RATE)));
            itemEntity.setSingleWeight(Double.valueOf(ps[0].getString(SINGLE_WEIGHT)));
            itemEntity.setWeightDistinctRate(ps[0].getInt(WEIGHT_DISTINCT_RATE));
            itemEntity.setMaxInspectionUnitQty(ps[0].getInt(MAX_INSPECTION_UNIT_QTY));
            // メッセージ
            itemEntity.setInformation(ps[0].getString(INFORMATION));
            // ロケーションNo.(From)
            itemEntity.setLocationNo1(ps[0].getString(LOCATION_NO));
            // 登録処理名
            itemEntity.setRegistPname(getClass().getSimpleName());
            // 最終更新処理名
            itemEntity.setLastUpdatePname(getClass().getSimpleName());

            // 商品マスタへ登録         
            iHandler.create(itemEntity);

            // 6001003=登録しました。
            setMessage(WmsMessageFormatter.format(6001003));
            return true;
        }
        catch (DataExistsException e)
        {
            // 一意キーエラー
            //6023007=入力された商品コードは既に登録されています。
            setMessage(WmsMessageFormatter.format(6023007));
            return false;
        }
    }

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
        // 検索キー
        PCTItemHandler iHandler = new PCTItemHandler(getConnection());
        PCTItemSearchKey ikey = new PCTItemSearchKey();

        //検索条件のセット
        // 商品コード
        ikey.setItemCode(p.getString(ITEM_CODE));
        // 荷主コード
        ikey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // ロット入数
        ikey.setLotEnteringQty(p.getInt(LOT_QTY));

        // 検索
        PCTItem entity = (PCTItem)iHandler.findPrimary(ikey);

        List<Params> result = new ArrayList<Params>();

        Params param = new Params();

        // 返却データをセットする
        // 単重量
        param.set(SINGLE_WEIGHT, entity.getSingleWeight());
        // 重量誤差率 
        param.set(WEIGHT_DISTINCT_RATE, entity.getWeightDistinctRate());
        // 最大検品可能数
        param.set(MAX_INSPECTION_UNIT_QTY, entity.getMaxInspectionUnitQty());

        result.add(param);

        return result;
    }

    /** 
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：荷主マスタ情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * @param p 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException 全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // 荷主マスタ情報
        ConsignorHandler cHandler = new ConsignorHandler(getConnection());
        ConsignorSearchKey skey = new ConsignorSearchKey();

        // 検索条件
        skey.setConsignorCodeCollect();
        skey.setConsignorCodeGroup();

        if (cHandler.count(skey) == 1)
        {
            Params param = new Params();
            Consignor ent = (Consignor)cHandler.findPrimary(skey);
            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());

            return param;
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
     * PCTシステム情報より、PCT商品マスタ取込フラグの確認を行います。<BR>
     * SAVE中、LOAD中の場合はtrue、未処理の場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true:SAVE中、LOAD中 false:未処理
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private boolean isItemLoad()
            throws CommonException
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
                setMessage(WmsMessageFormatter.format(6024068));
                return true;
            }
        }
        return false;
    }

    /**
     * 荷主マスタ情報より、パラメータで指定された<BR>
     * 荷主コードの荷主名称を取得します。
     * @param consiignorCode 荷主コード
     * @return String 荷主名称
     * @throws CommonException 例外が発生した場合通知されます。
     */
    private String getConsignorName(String consignorCode)
            throws CommonException
    {
        // 入力された荷主コードがマスタ登録されているかチェックします。
        // 荷主コードでDMCONSIGNOR内のデータ有無をチェックします。
        ConsignorHandler iHandler = new ConsignorHandler(getConnection());
        ConsignorSearchKey ikey = new ConsignorSearchKey();
        // 検索条件
        ikey.clear();
        // 荷主コード
        ikey.setConsignorCode(consignorCode);
        // 取得項目
        ikey.setConsignorNameCollect();

        Consignor[] consEntity = (Consignor[])iHandler.find(ikey);
        if (consEntity.length > 0)
        {
            return ((String)consEntity[0].getConsignorName());
        }
        else
        {
            return "";
        }
    }

    /**
     * PCTシステム情報より、初期重量誤差率を取得します。<BR>
     * 単重量及び重量誤差率より、最大検品可能数を求める。<BR>
     * 単重量又は誤差率が０の場合、上限なし(0)を復帰します。<BR>
     * @return int 最大検品可能数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private int getDefaultDistinctRate()
            throws CommonException
    {
        PCTSystemHandler sHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey sKey = new PCTSystemSearchKey();

        // 検索条件クリア
        sKey.clear();

        PCTSystem system = (PCTSystem)sHandler.findPrimary(sKey);

        if (system == null)
        {
            return 0;
        }
        return system.getDefultDistinctRate();
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
