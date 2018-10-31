package jp.co.daifuku.wms.analysis.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;

/**
 * Designer : Y.Kato <BR>
 * Maker : Y.Kato  <BR>
 * <BR>
 * 分析パッケージのスケジュール処理を行なう抽象クラスです。
 * 共通メソッドはこのクラスに実装され、スケジュール処理の個別の振る舞いについては、
 * このクラスを継承したクラスによって実装されます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/10</TD><TD>E.Takeda</TD><TD>新規作成</TD></TR>
 * <TR><TD>2008/01/15</TD><TD>Shimizu(Softecs)</TD><TD>v3.2移行</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 573 $, $Date: 2008-10-23 13:55:33 +0900 (木, 23 10 2008) $
 * @author  $Author: nakai $
 */
public class AbstractAnalysisSCH
        extends AbstractSCH
{
    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 573 $,$Date: 2008-10-23 13:55:33 +0900 (木, 23 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**
     * コンストラクタ<BR>
     * データベース接続用コネクションをセットします。<BR>
     * @param conn データベースコネクション
     * @param parent 呼び出し元クラス<br>
     *   null が指定された場合は、自身が設定されます。
     * @param locale 対象ロケール<br>
     *   null が指定された場合は、デフォルト・ロケールが設定されます。
     * @param ui ユーザ情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public AbstractAnalysisSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    // Public methods ------------------------------------------------
    /**
     * 荷主名称の取得を行います。<BR>
     * 取得できなかった場合は空文字列を返します。
     *
     * @param consignorCode 荷主コード
     * @return 荷主名称
     * @throws ReadWriteException データベースの接続でエラーを検出した場合にスローされます。
     */
    public String getConsignorName(String consignorCode)
            throws ReadWriteException
    {
        // デフォルト荷主コードが指定されていた場合は、空文字荷主名称として返します。
        if (WmsParam.DEFAULT_CONSIGNOR_CODE.equals(consignorCode))
        {
            return "";
        }

        ConsignorSearchKey skey = new ConsignorSearchKey();
        ConsignorHandler hndl = new ConsignorHandler(getConnection());

        skey.setConsignorCode(consignorCode);

        Consignor[] ent = (Consignor[])hndl.find(skey);
        if (ent.length > 0)
        {
            return ent[0].getConsignorName();
        }
        return "";
    }

    /**
     * 出荷先名称の取得を行います。<BR>
     * 取得できなかった場合は空文字列を返します。
     *
     * @param consignorCode 荷主コード
     * @param customerCode 出荷先コード
     * @return 出荷先名称
     * @throws ReadWriteException データベースの接続でエラーを検出した場合にスローされます。
     */
    public String getCustomerName(String consignorCode, String customerCode)
            throws ReadWriteException
    {
        CustomerSearchKey skey = new CustomerSearchKey();
        CustomerHandler hndl = new CustomerHandler(getConnection());

        skey.setCustomerCode(customerCode);
        skey.setConsignorCode(consignorCode);

        Customer[] ent = (Customer[])hndl.find(skey);
        if (ent.length > 0)
        {
            return ent[0].getCustomerName();
        }
        return "";
    }

    /**
     * 商品名称の取得を行います。<BR>
     * 取得できなかった場合は空文字列を返します。
     *
     * @param consignorCode 荷主コード
     * @param  itemCode 商品コード
     * @return 商品名称
     * @throws ReadWriteException データベースの接続でエラーを検出した場合にスローされます。
     */
    public String getItemName(String consignorCode, String itemCode)
            throws ReadWriteException
    {
        ItemController ic = new ItemController(getConnection(), getClass());
        return ic.getItemName(itemCode, consignorCode);
    }

    // Protected methods ---------------------------------------------
}
