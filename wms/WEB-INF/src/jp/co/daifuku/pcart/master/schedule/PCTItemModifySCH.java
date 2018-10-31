// $Id: PCTItemModifySCH.java 4259 2009-05-12 05:33:55Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTItemModifySCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * 商品マスタ修正・削除（基本情報設定）のスケジュール処理を行います。
 *
 * @version $Revision: 4259 $, $Date:: 2009-05-12 14:33:55 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTItemModifySCH
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
    public PCTItemModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ２画面遷移入力チェック<BR>
     * checkParamの内容をもとに、該当データ存在チェック行う。<BR>
     * 該当データが存在した場合trueを返す。該当データが存在しない、または<BR>
     * パラメータの内容に問題がある場合はfalseを返す。<BR>
     * @param p チェック条件
     * @return 正常：<CODE>true</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean nextCheck(ScheduleParams p)
            throws CommonException
    {
        // PCT商品マスタ取込フラグチェック
        if (isItemLoad())
        {
            return false;
        }

        // PCTItemModifySCHParamsにキャスト
        PCTItemModifySCHParams inParam = (PCTItemModifySCHParams)p;

        // 検索キー
        PCTItemHandler iHandler = new PCTItemHandler(getConnection());
        PCTItemSearchKey ikey = new PCTItemSearchKey();

        //検索条件のセット
        // 商品コード
        ikey.setItemCode(inParam.getString(ITEM_CODE));
        // 荷主コード
        ikey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // ロット入数
        ikey.setLotEnteringQty(inParam.getInt(LOT_QTY));

        // 検索
        PCTItem pctitem = (PCTItem)iHandler.findPrimary(ikey);
        if (pctitem == null)
        {
            // 6003011=対象データはありませんでした。
            setMessage(WmsMessageFormatter.format(6003011));
            return false;
        }

        if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(pctitem.getManagementType()))
        {
            // 6023199=システム管理商品コードのため、修正・削除できません。
            setMessage(WmsMessageFormatter.format(6023199));
            return false;
        }
        return true;
    }

    /**
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT商品マスタ情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * 
     * @param p 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException
     *             全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // 商品マスタチェック
        if (isItemLoad())
        {
            return null;
        }

        // PCT商品マスタ情報
        PCTItemHandler iHandler = new PCTItemHandler(getConnection());
        PCTItemSearchKey ikey = new PCTItemSearchKey();

        ikey.setConsignorCodeCollect();
        ikey.setConsignorCodeGroup();

        if (iHandler.count(ikey) == 1)
        {
            PCTItem ent = (PCTItem)iHandler.findPrimary(ikey);

            Params lineparam = new Params();
            lineparam.set(CONSIGNOR_CODE, ent.getConsignorCode());

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
