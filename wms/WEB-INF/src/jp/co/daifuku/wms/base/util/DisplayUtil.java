// $Id: DisplayUtil.java 2384 2008-12-22 06:06:56Z okayama $
package jp.co.daifuku.wms.base.util;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.ScrollListCell;
import jp.co.daifuku.bluedog.util.ControlColorSupport;
import jp.co.daifuku.common.text.StringUtil;

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
 * 表示項目を作成するためのUtilクラスです。<BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 2384 $, $Date: 2008-12-22 15:06:56 +0900 (月, 22 12 2008) $
 * @author  $Author: okayama $a
 */
public final class DisplayUtil
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
        return ("$Revision: 2384 $,$Date: 2008-12-22 15:06:56 +0900 (月, 22 12 2008) $");
    }

    // Constructors --------------------------------------------------
    /**
     * コンストラクタ
     */
    private DisplayUtil()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**
     * 総数とケース入数からケース数を計算します。<BR>
     * 総数をケースで割った商がケース数になります。<BR>
     * 入数が0の場合は、0を返します。<BR>
     * 
     * @param  pTotalQty 総数
     * @param  pEnteringQty ケース入数
     * @return ケース数
     */
    public static int getCaseQty(int pTotalQty, int pEnteringQty)
    {
        if (pEnteringQty == 0)
        {
            return 0;
        }
        return (pTotalQty / pEnteringQty);
    }

    /**
     * 総数とケース入数からケース数を計算します。<BR>
     * 総数をケースで割った商がケース数になります。<BR>
     * 入数が0の場合は、0を返します。<BR>
     * 
     * @param  pTotalQty 総数
     * @param  pEnteringQty ケース入数
     * @return ケース数
     */
    public static long getCaseQty(long pTotalQty, int pEnteringQty)
    {
        if (pEnteringQty == 0)
        {
            return 0;
        }
        return (pTotalQty / (long)pEnteringQty);
    }

    /**
     * 総数とケース入数からピース数を計算します。<BR>
     * 総数をケースで割った余りがピース数になります。<BR>
     * 入数が0の場合は、総数を返します。<BR>
     * 
     * @param  pTotalQty 総数
     * @param  pEnteringQty ケース入数
     * @return ピース数
     */
    public static int getPieceQty(int pTotalQty, int pEnteringQty)
    {
        if (pEnteringQty == 0)
        {
            return pTotalQty;
        }
        return (pTotalQty % pEnteringQty);
    }

    /**
     * 総数とケース入数からピース数を計算します。<BR>
     * 総数をケースで割った余りがピース数になります。<BR>
     * 入数が0の場合は、総数を返します。<BR>
     * 
     * @param  pTotalQty 総数
     * @param  pEnteringQty ケース入数
     * @return ピース数
     */
    public static long getPieceQty(long pTotalQty, int pEnteringQty)
    {
        if (pEnteringQty == 0)
        {
            return pTotalQty;
        }
        return (pTotalQty % (long)pEnteringQty);
    }

    /**
     * 秒数から時分秒を計算します。<BR>
     * (ユーザ別実績情報で使用)
     * 
     * @param  pTotalTime 総数
     * @return hhhmmss
     */
    public static String getTimeToDate(String pTotalTime)
    {
        if ("0".equals(pTotalTime))
        {
            return pTotalTime;
        }

        String hh = String.valueOf(Integer.parseInt(pTotalTime) / (60 * 60));
        if (hh.length() == 1)
        {
            hh = "0" + hh;
        }
        else if (hh.length() == 0)
        {
            hh = "00" + hh;
        }
        int hhMod = Integer.parseInt(pTotalTime) % (60 * 60);
        String mm = String.valueOf(hhMod / 60);
        if (mm.length() == 1)
        {
            mm = "0" + mm;
        }
        int mmMod = hhMod % 60;
        String ss = String.valueOf(mmMod);
        if (ss.length() == 1)
        {
            ss = "0" + ss;
        }

        return (hh + ":" + mm + ":" + ss);
    }

    /**
     * システムで定義されたデフォルト色でリストセルの背景色を変更します。<BR>
     * 
     * @param listCell 背景色を変更するリストセル
     * @param rowNo 行番号
     */
    public static void addHighlight(ListCell listCell, int rowNo)
    {
        if (rowNo > 0 && listCell.getMaxRows() > rowNo)
        {
            listCell.addHighlight(rowNo);
        }
    }

    /**
     * 指定された色でリストセルの背景色を変更します。<BR>
     * 
     * @param listCell 背景色を変更するリストセル
     * @param rowNo 行番号
     * @param color 背景色
     */
    public static void addHighlight(ListCell listCell, int rowNo, ControlColorSupport color)
    {
        if (rowNo > 0 && listCell.getMaxRows() > rowNo)
        {
            listCell.addHighlight(rowNo, color);
        }
    }

    /**
     * 指定された色でリストセルの背景色を変更します。<BR>
     * 
     * @param listCell 背景色を変更するリストセル
     * @param rowNo 行番号
     * @param color 背景色
     */
    public static void addHighlight(ScrollListCell listCell, int rowNo, ControlColorSupport color)
    {
        if (rowNo > 0 && listCell.getMaxRows() > rowNo)
        {
            listCell.addHighlight(rowNo, color);
        }
    }

    /**
     * システムで定義している色でリストセルの背景色を変更します。<BR>
     * 既に設定されている背景色は一度クリアされます。
     * 
     * @param listCell 背景色を変更するリストセル
     * @param rowNo 行番号
     */
    public static void setHighlight(ListCell listCell, int rowNo)
    {
        if (rowNo > 0 && listCell.getMaxRows() > rowNo)
        {
            listCell.setHighlight(rowNo);
        }
    }

    /**
     * システムで定義している色でリストセルの背景色を変更します。<BR>
     * 既に設定されている背景色は一度クリアされます。
     * 
     * @param listCell 背景色を変更するリストセル
     * @param rowNo 行番号
     */
    public static void setHighlight(ScrollListCell listCell, int rowNo)
    {
        if (rowNo > 0 && listCell.getMaxRows() > rowNo)
        {
            listCell.setHighlight(rowNo);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
