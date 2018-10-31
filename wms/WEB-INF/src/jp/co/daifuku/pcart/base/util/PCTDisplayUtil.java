// $Id: PCTDisplayUtil.java 4195 2009-04-23 03:17:17Z okayama $
package jp.co.daifuku.pcart.base.util;

import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.entity.SystemDefine;


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
 * P-CART用表示項目を作成するためのUtilクラスです。<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4195 $, $Date: 2009-04-23 12:17:17 +0900 (木, 23 4 2009) $
 * @author  $Author: okayama $a
 */
public final class PCTDisplayUtil
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
        return ("$Revision: 4195 $,$Date: 2009-04-23 12:17:17 +0900 (木, 23 4 2009) $");
    }

    // Constructors --------------------------------------------------
    /**
     * コンストラクタ
     */
    private PCTDisplayUtil()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**
     * 重量誤差率から最大検品単位数を計算します。<BR>
     * （１－重量誤差率）／（２＊重量誤差率）が最大検品単位数数になります。<BR>
     * 上記計算結果が０以下の場合、１を返します。<BR>
     * 重量誤差率が0の場合は、0を返します。<BR>
     * 
     * @param  pDistinctRate 重量誤差率
     * @return ケース数
     */
    public static int getMaxInspectionUnitQty(int pDistinctRate)
    {
        if (pDistinctRate == 0)
        {
            return 0;
        }
        int maxInspection = (100 - pDistinctRate) / (2 * pDistinctRate);
        if (maxInspection <= 0)
        {
            maxInspection = 1;
        }
        else
        {
            if ((100 - pDistinctRate) % (2 * pDistinctRate) == 0 && (maxInspection > 1))
            {
                maxInspection = maxInspection - 1;
            }
        }
        return maxInspection;
    }

    //
    /**
     * 重量未登録商品フラグを取得します。<BR>
     * true：ありfalse：なし<BR>
     * @param   flag フラグ
     * @return  あり or なし
     */
    public static String getWeightFlag(boolean flag)
    {
        if (flag)
        {
            // あり
            return DisplayText.getText("LBL-P0206");
        }
        else
        {
            // なし
            return DisplayText.getText("LBL-P0207");
        }

    }

    /**
     * 商品画像登録文字列を取得します。<BR>
     * true：ありfalse：なし<BR>
     * @param   flag フラグ
     * @return  あり or なし
     */
    public static String getItemPictureString(boolean flag)
    {
        if (flag)
        {
            // あり
            return DisplayText.getText("LBL-P0206");
        }
        else
        {
            // なし
            return DisplayText.getText("LBL-P0207");
        }

    }

    /**
     * 商品画像登録フラグを取得します。<BR>
     * あり：true なし：false<BR>
     * @param   itempicture 文字列
     * @return true or false
     */
    public static boolean getItemPictureFlag(String itempicture)
    {
        if (itempicture.equals(DisplayText.getText("LBL-P0206")))
        {
            // true
            return true;
        }
        else
        {
            // false
            return false;
        }

    }

    /**
     * 商品画像登録文字列を取得します。<BR>
     * 1：あり2：なし<BR>
     * @param   flagint フラグ
     * @return  あり or なし
     */
    public static String getItemPicture(int flagint)
    {
        if (flagint == 0)
        {
            // 全て
            return DisplayText.getText("LBL-W1276");
        }
        else if (flagint == 1)
        {
            // あり
            return DisplayText.getText("LBL-P0206");
        }
        else
        {
            // なし
            return DisplayText.getText("LBL-P0207");
        }

    }


    /**
     * PCT商品マスタ取込状況を名称で取得します。<BR>
     * <CODE>SystemDefine.PCTMASTER_LOAD_FLAG_STOP</CODE> : 未処理<BR>
     * <CODE>SystemDefine.PCTMASTER_LOAD_FLAG_SAVE</CODE> : SAVE中<BR>
     * <CODE>SystemDefine.PCTMASTER_LOAD_FLAG_LOAD</CODE> : LOAD中<BR>
     * @param flag PCT商品マスタ取込フラグ
     * @return PCT商品マスタ取込状況
     */
    public static String getPCTMasterFlag(String flag)
    {

        if (SystemDefine.PCTMASTER_LOAD_FLAG_STOP.equals(flag))
        {
            // 未処理
            return DisplayText.getText("LBL-W0375");
        }
        else if (SystemDefine.PCTMASTER_LOAD_FLAG_SAVE.equals(flag))
        {
            // SAVE中
            return DisplayText.getText("LBL-P0235");
        }

        else if (SystemDefine.PCTMASTER_LOAD_FLAG_LOAD.equals(flag))
        {
            // LOAD中
            return DisplayText.getText("LBL-P0236");
        }
        else
        {
            return "";
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
