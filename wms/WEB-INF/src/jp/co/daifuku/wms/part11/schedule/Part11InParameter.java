package jp.co.daifuku.wms.part11.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;

/**
 * <CODE>Part11InParameter</CODE>クラスは、Part11パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスではPart11パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>Part11InParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     開始出力日時<BR>
 *     終了出力日時<BR>
 *     データベースタイプ<BR>
 *     ユーザID<BR>
 *     DS番号<BR>
 *     エリアNo<BR>
 *     棚No<BR>
 *     商品コード<BR>
 *     ロットNo<BR>
 *     画面名称<BR>
 * </DIR>
 * 
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 *
 * @version $Revision: 607 $, $Date: 2008-10-24 11:42:10 +0900 (金, 24 10 2008) $
 * @author  $Author: okamura $
 */
public class Part11InParameter
        extends InParameter
{
    // Class variables -----------------------------------------------
    /**
     * 開始出力日時
     */
    private String _startDate;

    /**
     * 終了出力日時
     */
    private String _endDate;

    /**
     * 開始出力日時
     */
    private Date _fromDate;

    /**
     * 終了出力日時
     */
    private Date _toDate;

    /**
     * データベースタイプ
     */
    private String _dbType;

    /**
     * ユーザID
     */
    private String _UserId;

    /**
     * DS番号
     */
    private String _dsNumber;

    /**
     * エリアNo
     */
    private String _areaNo;

    /**
     * 棚No
     */
    private String _locateNo;

    /**
     * 商品コード
     */
    private String _itemCode;

    /**
     * ロットNo
     */
    private String _lotNo;

    /**
     * 画面名称
     */
    private String _pageNameResouceKey;


    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public Part11InParameter(WmsUserInfo info)
    {
        super();
        setWmsUserInfo(info);
    }
    
    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 607 $,$Date: 2008-10-24 11:42:10 +0900 (金, 24 10 2008) $");
    }

    /**
     * 開始出力日時を返します。
     * @return 開始出力日時を返します。
     */
    public String getStartDate()
    {
        return _startDate;
    }

    /**
     * 開始出力日時を設定します。
     * @param rStr 開始出力日時
     */
    public void setStartDate(String rStr)
    {
        _startDate = rStr;
    }

    /**
     * 終了出力日時を返します。
     * @return 終了出力日時を返します。
     */
    public String getEndDate()
    {
        return _endDate;
    }

    /**
     * 終了出力日時を設定します。
     * @param rStr 終了出力日時
     */
    public void setEndDate(String rStr)
    {
        _endDate = rStr;
    }

    /**
     * 開始出力日時を返します。
     * @return 開始出力日時を返します。
     */
    public Date getFromDate()
    {
        return _fromDate;
    }

    /**
     * 開始出力日時を設定します。
     * @param rStr 開始出力日時
     */
    public void setFromDate(Date rStr)
    {
        _fromDate = rStr;
    }

    /**
     * 終了出力日時を返します。
     * @return 終了出力日時を返します。
     */
    public Date getToDate()
    {
        return _toDate;
    }

    /**
     * 終了出力日時を設定します。
     * @param rStr 終了出力日時
     */
    public void setToDate(Date rStr)
    {
        _toDate = rStr;
    }

    /**
     * データベースタイプを返します。
     * @return データベースタイプを返します。
     */
    public String getDbType()
    {
        return _dbType;
    }

    /**
     * データベースタイプを設定します。
     * @param rStr データベースタイプ
     */
    public void setDbType(String rStr)
    {
        _dbType = rStr;
    }

    /**
     * ユーザIDを返します。
     * @return ユーザIDを返します。
     */
    public String getUserId()
    {
        return _UserId;
    }

    /**
     * ユーザIDを設定します。
     * @param rStr ユーザID
     */
    public void setUserId(String rStr)
    {
        _UserId = rStr;
    }

    /**
     * DS番号を返します。
     * @return DS番号を返します。
     */
    public String getDsNumber()
    {
        return _dsNumber;
    }

    /**
     * DS番号を設定します。
     * @param rStr DS番号
     */
    public void setDsNumber(String rStr)
    {
        _dsNumber = rStr;
    }

    /**
     * エリアNoを返します。
     * @return エリアNoを返します。
     */
    public String getAreaNo()
    {
        return _areaNo;
    }

    /**
     * エリアNoを設定します。
     * @param rStr エリアNo
     */
    public void setAreaNo(String rStr)
    {
        _areaNo = rStr;
    }

    /**
     * 棚Noを返します。
     * @return 棚Noを返します。
     */
    public String getLocateNo()
    {
        return _locateNo;
    }

    /**
     * 棚Noを設定します。
     * @param rStr 棚No
     */
    public void setLocateNo(String rStr)
    {
        _locateNo = rStr;
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
     * @param rStr 商品コード
     */
    public void setItemCode(String rStr)
    {
        _itemCode = rStr;
    }

    /**
     * ロットNoを返します。
     * @return ロットNoを返します。
     */
    public String getLotNo()
    {
        return _lotNo;
    }

    /**
     * ロットNoを設定します。
     * @param rStr ロットNo
     */
    public void setLotNo(String rStr)
    {
        _lotNo = rStr;
    }

    /**
     * 画面名称を返します。
     * @return 画面名称を返します。
     */
    public String getPageNameResouceKey()
    {
        return _pageNameResouceKey;
    }

    /**
     * 画面名称を設定します。
     * @param rStr 画面名称
     */
    public void setPageNameResouceKey(String rStr)
    {
        _pageNameResouceKey = rStr;
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**
     * 二つのオブジェクトを比較します。<br>
     * 両方ともNULLの時は同じものと見なします。
     *
     * @param o1 比較対象1
     * @param o2 比較対象2
     * @return 同じ内容のオブジェクトまたは両方ともnullのときtrue.
     */
    protected boolean equals(Object o1, Object o2)
    {
        if (o1 == null && o2 == null)
        {
            // 両方 null なら 同じものとみなす
            return true;
        }
        if (o1 == null)
        {
            // 片方だけ null なら異なったものと見なす
            return false;
        }
        if (o2 == null)
        {
            // 片方だけ null なら異なったものと見なす
            return false;
        }
        // 両方とも値を持つ場合は、内容の比較を行う
        return o1.equals(o2);
    }


    // Private methods -----------------------------------------------
}
//end of class
