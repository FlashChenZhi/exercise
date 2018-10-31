package jp.co.daifuku.wms.base.rft;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Locale;

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * <CODE>RftOutParameter</CODE>クラスは、ベースパッケージ内のIdSch→IdProcess間のパラメータの受渡しに使用します。<BR>
 * このクラスではベースパッケージの問合せ系の各IDで使用される項目を保持します。使用する項目はIDによって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>RftOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     端末No.<BR>
 *     ユーザID<BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/02/22</TD><TD>T.Kishimoto</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 2517 $, $Date: 2009-01-06 11:23:13 +0900 (火, 06 1 2009) $
 * @author  $Author: kishimoto $
 */
public class RftOutParameter
        extends OutParameter
{
    // Class variables -----------------------------------------------   

    /** 荷主コード */
    private String _consignorCode;

    /** 荷主名称 */
    private String _consignorName;

    /** 商品コード */
    private String _itemCode;

    /** 商品名称 */
    private String _itemName;

    /** JANコード */
    private String _janCode;

    /** ケースITF */
    private String _itf;

    /** ボールITF */
    private String _bunddleItf;

    /** ケース入数 */
    private int _enteringQty;

    /** ボール入数 */
    private int _bundleEnteringQty;

    /** バッチNo */
    private String _batchNo;

    /** メニューラベル */
    private String _menuLabel;

    /** 作業区分 */
    private String _jobType;

    /** 作業区分詳細 */
    private String _jobDetails;

    /** アプリケーション名 */
    private String _appName;

    /** 端末区分 */
    private String _terminalType;

    /** パスワード入力モード */
    private boolean _passWordInputMode;

    /** 荷主スキップコード */
    private String _skipConsignorCode;

    /** ITFtoJAN */
    private boolean _itfToJan;

    /** C/P初期モード */
    private String _cpMode;

    /** 検品モード */
    private boolean _inspectionMode;
    
    /** ロケール */
    private Locale _locale;

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 2517 $,$Date: 2009-01-06 11:23:13 +0900 (火, 06 1 2009) $");
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
     * 荷主名称を返します。
     * @return 荷主名称を返します。
     */
    public String getConsignorName()
    {
        return _consignorName;
    }

    /**
     * 荷主名称を設定します。
     * @param consignorName 荷主名称
     */
    public void setConsignorName(String consignorName)
    {
        _consignorName = consignorName;
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
     * 商品名称を返します。
     * @return 商品名称を返します。
     */
    public String getItemName()
    {
        return _itemName;
    }

    /**
     * 商品名称を設定します。
     * @param itemName 商品名称
     */
    public void setItemName(String itemName)
    {
        _itemName = itemName;
    }

    /**
     * JANコードを返します。
     * @return JANコードを返します。
     */
    public String getJanCode()
    {
        return _janCode;
    }

    /**
     * JANコードを設定します。
     * @param janCode JANコード
     */
    public void setJanCode(String janCode)
    {
        _janCode = janCode;
    }

    /**
     * ケースITFを返します。
     * @return ケースITFを返します。
     */
    public String getItf()
    {
        return _itf;
    }

    /**
     * ケースITFを設定します。
     * @param itf ケースITF
     */
    public void setItf(String itf)
    {
        _itf = itf;
    }

    /**
     * ボールITFを返します。
     * @return ボールITFを返します。
     */
    public String getBunddleItf()
    {
        return _bunddleItf;
    }

    /**
     * ボールITFを設定します。
     * @param bunddleItf ボールITF
     */
    public void setBunddleItf(String bunddleItf)
    {
        _bunddleItf = bunddleItf;
    }

    /**
     * ケース入数を返します。
     * @return ケース入数を返します。
     */
    public int getEnteringQty()
    {
        return _enteringQty;
    }

    /**
     * ケース入数を設定します。
     * @param enteringQty ケース入数
     */
    public void setEnteringQty(int enteringQty)
    {
        _enteringQty = enteringQty;
    }

    /**
     * ボール入数を返します。
     * @return ボール入数を返します。
     */
    public int getBundleEnteringQty()
    {
        return _bundleEnteringQty;
    }

    /**
     * ボール入数を設定します。
     * @param bundleEnteringQty ボール入数
     */
    public void setBundleEnteringQty(int bundleEnteringQty)
    {
        _bundleEnteringQty = bundleEnteringQty;
    }

    /**
     * @return バッチNoを返します。
     */
    public String getBatchNo()
    {
        return _batchNo;
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
     * メニューラベルを取得します。
     * @return メニューラベル
     */
    public String getMenuLabel()
    {
        return _menuLabel;
    }

    /**
     * メニューラベルを設定します。
     * @param value メニューラベル
     */
    public void setMenuLabel(String value)
    {
        _menuLabel = value;
    }

    /**
     * 作業区分を取得します。
     * @return 作業区分
     */
    public String getJobType()
    {
        return _jobType;
    }

    /**
     * 作業区分を設定します。
     * @param value 作業区分
     */
    public void setJobType(String value)
    {
        _jobType = value;
    }

    /**
     * 作業区分詳細を取得します。
     * @return 作業区分詳細
     */
    public String getJobDetails()
    {
        return _jobDetails;
    }

    /**
     * 作業区分詳細を設定します。
     * @param value 作業区分詳細
     */
    public void setJobDetails(String value)
    {
        _jobDetails = value;
    }

    /**
     * アプリケーション名を取得します。
     * @return アプリケーション名
     */
    public String getAppName()
    {
        return _appName;
    }

    /**
     * アプリケーション名を設定します。
     * @param value アプリケーション名
     */
    public void setAppName(String value)
    {
        _appName = value;
    }

    /**
     * 端末区分を取得します。
     * @return 端末区分
     */
    public String getTerminalType()
    {
        return _terminalType;
    }

    /**
     * 端末区分を設定します。
     * @param value 端末区分
     */
    public void setTerminalType(String value)
    {
        _terminalType = value;
    }

    /**
     * パスワード入力モードを取得します。
     * @return パスワード入力モード
     */
    public boolean getPassWordInputMode()
    {
        return _passWordInputMode;
    }

    /**
     * パスワード入力モードを設定します。
     * @param value パスワード入力モード
     */
    public void setPassWordInputMode(boolean value)
    {
        _passWordInputMode = value;
    }

    /**
     * 荷主スキップコードを取得します。
     * @return 荷主スキップコード
     */
    public String getSkipConsignorCode()
    {
        return _skipConsignorCode;
    }

    /**
     * 荷主スキップコードを設定します。
     * @param value 荷主スキップコード
     */
    public void setSkipConsignorCode(String value)
    {
        _skipConsignorCode = value;
    }

    /**
     * ITFtoJANを取得します。
     * @return ITFtoJAN
     */
    public boolean getItfToJan()
    {
        return _itfToJan;
    }

    /**
     * ITFtoJANを設定します。
     * @param value ITFtoJAN
     */
    public void setItfToJan(boolean value)
    {
        _itfToJan = value;
    }

    /**
     * C/P初期モードを取得します。
     * @return C/P初期モード
     */
    public String getCPMode()
    {
        return _cpMode;
    }

    /**
     * C/P初期モードを設定します。
     * @param value C/P初期モード
     */
    public void setCPMode(String value)
    {
        _cpMode = value;
    }

    /**
     * 検品モードを取得します。
     * @return 検品モード
     */
    public boolean getInspectionMode()
    {
        return _inspectionMode;
    }

    /**
     * 検品モードを設定します。
     * @param value 検品モード
     */
    public void setInspectionMode(boolean value)
    {
        _inspectionMode = value;
    }
    
    /**
     * @return ロケールを取得します。
     */
    public Locale getLocale()
    {
        return _locale;
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

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
