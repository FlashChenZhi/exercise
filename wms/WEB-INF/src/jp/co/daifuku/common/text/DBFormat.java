// $Id: DBFormat.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common.text;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import jp.co.daifuku.common.CommonParam;

/**
 * 文字列をフォーマットします。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class DBFormat
        extends Object
{
    // Class fields --------------------------------------------------
    /*
     * Comment for field
     */

    // Class variables -----------------------------------------------
    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**
     * 文字列をフォーマットします。データベースに登録するために文字の両端に
     * 'をセットします。ただし与えられた文字列がnull、0バイトの文字列だった場合、文字列''を返します。
     * エスケープ文字'が入力された場合は''に変換されます。
     * @param fmt  フォーマットの対象となる文字列を指定します。
     * @return     フォーマットされた文字列を返します。
     */
    public static String format(String fmt)
    {
        String str = null;

        if (fmt == null || fmt.trim().equals(""))
        {
            return ("''");
        }
        else
        {
            // '文字が大量にある時の対応を追加する。	author	C.Kaminishi
            String sbuff = new String(fmt);
            StringBuffer buff = new StringBuffer(fmt.length() * 2);
            while (sbuff.length() > 0)
            {
                int num = sbuff.indexOf("'");
                if (num < 0)
                {
                    buff.append(sbuff);
                    break;
                }

                buff.append(sbuff.substring(0, num));
                buff.append("''");
                sbuff = sbuff.substring(num + 1);
            }

            int len = buff.length();
            StringBuffer stbf = new StringBuffer(len + 2);
            stbf.append("'");
            stbf.append(buff);
            stbf.append("'");
            str = String.valueOf(stbf);
        }

        return str;
    }

    /**
     * 日付型データをデータベースの日付型フィールドにセットするために、フォーマットします。
     * ORACLEのTO_DATE関数を使用する形に編集します。ただし与えられた文字列がnullだった場合、
     * 文字列"null"を返します。
     * @param dat  フォーマットの対象となるDate型データを指定します。
     * @return     フォーマットされた文字列を返します。
     */
    public static String format(Date dat)
    {
        String str = null;
        SimpleDateFormat sformatter = new SimpleDateFormat("yyyyMMddHHmmss");

        if (dat == null)
        {
            return ("null");
        }
        else
        {
            StringBuffer stbf = new StringBuffer(32);
            // TO_DATE("yyyymmddhhmmdd（実際には数値が入る）", 'yyyymmddhh24miss')の形に編集
            stbf.append("TO_DATE('" + sformatter.format(dat) + "', 'yyyymmddhh24miss')");
            str = String.valueOf(stbf);
        }
        return str;
    }

    /**
     * 指定されたfmtがnullの場合、空文字列に置き換えます。データベースのレコードより取得した値がnullの場合に<BR>
     * 文字に置き換えたい場合に使用します。
     * @param fmt  置換え対象となる文字列を指定します。
     * @return     指定されたfmtがnullの場合は空文字列を返します。それ以外の場合はfmtをそのまま返します。
     */
    public static String replace(String fmt)
    {
        if (fmt == null)
        {
            return "";
        }
        //文字列の右側の空白を削除
        //		int len = fmt.length() ;
        //		try {
        //			while (fmt.lastIndexOf(" ", len) == (len - 1))
        //			{
        //				len-- ;
        //				fmt = fmt.substring(0, len) ;
        //			}
        //		}
        //		catch(IndexOutOfBoundsException e)
        //		{
        //			fmt = " " ;
        //		}
        return fmt;

    }

    /**
     * 指定された文字列内に、システムで定義されたパターン照合文字が含まれているかどうか検証します。
     * パターン照合文字の定義は、WMSシステムパラメータにて指定します。
     * @param pattern 検索対象となる文字列を指定します。
     * @return 文字列中にパターン照合文字が含まれる場合はtrue,パターン照合文字が含まれない場合はfalseを返します。
     */
    public static boolean isPatternMatching(String pattern)
    {
        String patternMachLang = CommonParam.getParam("PATTERNMATCHING_CHAR");

        if (pattern != null)
        {
            if (pattern.indexOf(patternMachLang) > -1)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定された文字列内に、システム定義されたパターン照合文字が含まれているかどうか検証しSQLを組み立てます。
     * @param key       検索対象となる文字列を指定します。
     * @param sqlstring SQL条件文を指定します。例 "STOCK.ITEMKEY {0}"
     * @param first     trueの場合条件文には"WHERE"をセットします。
     *                  falseの場合は"AND"をセットします。
     * @return SQL条件文を返します。
     */
    public static String patternMatching(String key, String sqlstring, boolean first)
    {
        String fmtSQL = "";
        Object[] fmtObj = new Object[1];

        if (key != null && !key.equals("") && sqlstring != null)
        {
            String exkey = exchangeKey(key);

            if (isPatternMatching(exkey))
            {
                fmtObj[0] = "LIKE " + format(exkey);
            }
            else
            {
                fmtObj[0] = "= " + format(exkey);
            }

            if (first)
            {
                return "WHERE " + SimpleFormat.format(sqlstring, fmtObj);
            }
            else
            {
                return "AND " + SimpleFormat.format(sqlstring, fmtObj);
            }
        }

        return fmtSQL;
    }

    /**
     * 指定された文字列内に、システムで定義されたパターン照合文字が含まれているかどうか検証しSQLを組み立てます。
     * @param key       検索対象となる文字列を指定します。
     * @param sqlstring SQL条件文を指定します。例 "STOCK.ITEMKEY {0}"
     * @param first     trueの場合最初に組み立てた条件文には"WHERE"をセットします。
     *                  falseの場合は常に"AND"をセットします。
     * @return SQL???
     */
    public static String patternMatching(String[] key, String[] sqlstring, boolean first)
    {
        String fmtSQL = "";
        Object[] fmtObj = new Object[1];
        // firstがtrueでかつ最初に条件文を組み立てるときは"WHERE"をセットするためのフラグ
        boolean addwhere = true;

        if (key != null && sqlstring != null)
        {
            for (int i = 0; i < key.length; i++)
            {
                if (key[i] != null && !key[i].equals("") && sqlstring[i] != null)
                {
                    key[i] = exchangeKey(key[i]);

                    if (isPatternMatching(key[i]))
                    {
                        fmtObj[0] = "LIKE " + format(key[i]);
                    }
                    else
                    {
                        fmtObj[0] = "= " + format(key[i]);
                    }

                    String condi = "AND ";
                    if (first && addwhere)
                    {
                        condi = "WHERE ";
                        addwhere = false;
                    }
                    fmtSQL = fmtSQL + " " + condi + SimpleFormat.format(sqlstring[i], fmtObj);
                }
            }
        }

        return fmtSQL;
    }

    /**
     * 指定された文字列内に、システムで定義されたパターン照合文字が含まれていたら
     * その文字を’%’に置き換えます
     * @param key       検索対象となる文字列を指定します。
     * @return %に置き換えた文字列を返します。
     */
    public static String exchangeKey(String key)
    {
        String matchkey = CommonParam.getParam("PATTERNMATCHING_CHAR");
        String patternMachLang = "%";

        StringBuffer buf = new StringBuffer();
        String restkey = null;
        String subkey = null;
        int len_off = 0;
        int len = 0;
        int index = 0;
        boolean endflg = true;

        if (key == null)
        {
            return key;
        }

        // 指定された文字列の長さを取得
        int totallen = key.length();

        while (endflg)
        {
            // パターン照合を検索する残りの文字列を取得
            restkey = key.substring(len_off, totallen);
            // パターン照合文字の位置を取得
            index = restkey.indexOf(matchkey);
            // 照合文字までの長さ
            len = len_off + index;

            if (index > -1)
            {
                // 照合文字までの文字列を取得
                subkey = key.substring(len_off, len);
                buf.append(subkey);
                buf.append(patternMachLang);
                len_off = len + 1;
            }
            else
            {
                // 照合文字なし
                buf.append(restkey);
                endflg = false;
            }

        }
        return String.valueOf(buf);

    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // for debug method ----------------------------------------------
    /******************** Comment-Out 2003.1.27 C.Kaminishi
     public static void main(String args[])
     {
     try
     {
     java.sql.Connection conn = jp.co.daifuku.wms.common.WmsParam.getConnection("awct6","awc");
     jp.co.daifuku.wms.dbhandler.IORetMonthlyFinder iomf = new jp.co.daifuku.wms.dbhandler.IORetMonthlyFinder(conn);
     iomf.open();
     int count = iomf.multisearch(0, "itm00000%");

     jp.co.daifuku.wms.storage.Stock stk = new jp.co.daifuku.wms.storage.Stock();
     stk.setItemKey("22");
     stk.setLotNumber("1%");
     jp.co.daifuku.wms.dbhandler.StockFinder stkfind = new jp.co.daifuku.wms.dbhandler.StockFinder(conn);
     stkfind.open();
     }
     catch (Exception e)
     {
     e.printStackTrace() ;
     }
     }
     ********************************************/
}
//end of class
