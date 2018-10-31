// $Id: PCTSystemChecker.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.base.util;


/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * P-CART用システム全体で使用するチェック機能クラスです。<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $a
 */
public final class PCTSystemChecker
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 3209 $,$Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $");
    }

    // Constructors --------------------------------------------------
    /**
     * コンストラクタ
     */
    private PCTSystemChecker()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**
     * ロット入数の許容量チェックを行います。<BR>
     * （単重量／ロット入数）＝ロット単重量<BR>
     * （単重量＊重量誤差率）／１００＝誤差重量<BR>
     * ロット単重量が誤差重量未満の場合、falseを通知します。<BR>
     * @param  pSingleWeight 単重量
     * @param  pLotEnteringQty ロット入数
     * @param  pDistinctRate 重量誤差率
     * @return ケース数
     */
    public static boolean checkLotEntering(double pSingleWeight, int pLotEnteringQty, int pDistinctRate)
    {
        // ロット入数の許容量チェック
        // 単体数量／ロット入数＝ロット重量
        double w_lotweight = 0;
        if (pSingleWeight > 0 && pLotEnteringQty > 0)
        {
            w_lotweight = (double)pSingleWeight / (double)pLotEnteringQty;
        }
        // 単重量＊誤差率＝誤差重量
        double w_distinctweight = 0;
        if (pDistinctRate > 0)
        {
            w_distinctweight = (double)(pSingleWeight * pDistinctRate) / (double)100;
        }
        // ロット重量が誤差重量未満の場合、注意メッセージ
        if (w_lotweight < w_distinctweight)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
