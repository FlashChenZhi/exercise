// $Id: PCTItemMasterInquirySCH.java 4259 2009-05-12 05:33:55Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.dasch.PCTItemMasterInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
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
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 商品マスタ照会のスケジュール処理を行います。
 *
 * @version $Revision: 4259 $, $Date:: 2009-05-12 14:33:55 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTItemMasterInquirySCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // private String _instanceVar ;
    /** 保存用のフィールド 荷主名称(<code>CONSIGNOR_NAME</code>) */
    private FieldName _CONSIGNOR_NAME = new FieldName("", "CONSIGNOR_NAME");

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
    public PCTItemMasterInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /** 
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT商品マスタ情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException 全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // 商品マスタチェック
        if (!itemload())
        {
            return null;
        }

        // 荷主マスタ情報
        PCTItemHandler itemHandler = new PCTItemHandler(getConnection());
        PCTItemSearchKey skey = new PCTItemSearchKey();

        // 検索条件
        skey.setConsignorCodeCollect();
        skey.setConsignorCodeGroup();
        skey.setCollect(PCTItem.CONSIGNOR_NAME, "MAX", _CONSIGNOR_NAME);
        if (itemHandler.count(skey) == 1)
        {
            PCTItem item = (PCTItem)itemHandler.findPrimary(skey);
            Params lineparam = new Params();
            // 荷主コード
            lineparam.set(CONSIGNOR_CODE, item.getConsignorCode());
            // 荷主名称
            lineparam.set(CONSIGNOR_NAME, item.getConsignorName());
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
                setMessage(WmsMessageFormatter.format(6024068));
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
