package jp.co.daifuku.wms.base.rft;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Locale;

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;

/**
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * <CODE>RftInParameter</CODE>クラスは、ベースパッケージ内のIdProcess→IdSch間のパラメータの受渡しに使用します。<BR>
 * このクラスではベースパッケージの問合せ系の各IDで使用される項目を保持します。使用する項目はIDによって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>RftInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     端末No.<BR>
 *     ユーザID<BR>
 *     パスワード<BR>
 *     荷主コード<BR>
 *     商品コード<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/02/22</TD><TD>T.Kishimoto</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4150 $, $Date: 2009-04-15 13:31:40 +0900 (水, 15 4 2009) $
 * @author  $Author: kumano $
 */
public class RftInParameter
        extends InParameter
{
    // Class variables -----------------------------------------------   

    /** パスワード */
    private String _password;

    /** 荷主コード */
    private String _consignorCode;

    /** 商品コード */
    private String _itemCode;

    /** 作業区分 */
    private String _jobType;

    /** 作業区分詳細 */
    private String _jobDetails;

    /** バッチNo */
    private String _batchNo;

    /** ロケール */
    private Locale _locale;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     * @param info
     */
    public RftInParameter(WmsUserInfo info)
    {
        super();
        setWmsUserInfo(info);
    }
    
    public RftInParameter()
    {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 4150 $,$Date: 2009-04-15 13:31:40 +0900 (水, 15 4 2009) $");
    }

    /**
     * パスワードを返します。
     * @return パスワードを返します。
     */
    public String getPassword()
    {
        return _password;
    }

    /**
     * パスワードを設定します。
     * @param password パスワード
     */
    public void setPassword(String password)
    {
        _password = password;
    }

    /**
     * 荷主コードを返します。
     * @return 荷主コードを返します。
     */
    public String getConsignorCode()
    {
        return _consignorCode;
    }

    /**
     * 荷主コードを設定します。
     * @param consignorCode 荷主コード
     */
    public void setConsignorCode(String consignorCode)
    {
        _consignorCode = consignorCode;
    }

    /**
     * 商品コードを返します。
     * @return 商品コードを返します。
     */
    public String getItemCode()
    {
        return _itemCode;
    }

    /**
     * 商品コードを設定します。
     * @param itemCode 商品コード
     */
    public void setItemCode(String itemCode)
    {
        _itemCode = itemCode;
    }

    /**
     * 作業区分を設定します。
     * @param jobType 作業区分
     */
    public void setJobType(String jobType)
    {
        _jobType = jobType;
    }

    /**
     * 作業区分詳細を設定します。
     * @param jobDetails 作業区分詳細
     */
    public void setJobDetails(String jobDetails)
    {
        _jobDetails = jobDetails;
    }

    /**
     * バッチNoを設定します。
     * @param batchNo バッチNo
     */
    public void setBatchNo(String batchNo)
    {
        _batchNo = batchNo;
    }

    /**
     * ロケールを設定します。
     * 
     * @param locale ロケール<br>
     * null が指定された場合は、デフォルト・ロケールが設定されます。
     */
    public void setLocale(Locale locale)
    {
        _locale = (null == locale) ? Locale.getDefault()
                                   : locale;
    }

    /**
     * @return バッチNoを取得します。
     */
    public String getBatchNo()
    {
        return _batchNo;
    }

    /**
     * @return 作業区分を取得します。
     */
    public String getJobType()
    {
        return _jobType;
    }

    /**
     * @return 作業区分詳細を取得します。
     */
    public String getJobDetails()
    {
        return _jobDetails;
    }

    /**
     * @return ロケールを取得します。
     */
    public Locale getLocale()
    {
        return _locale;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
