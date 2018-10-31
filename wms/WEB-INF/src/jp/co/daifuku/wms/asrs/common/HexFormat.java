// $Id: HexFormat.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**<jp>
 * 数値をHex表記の文字列にフォーマットします。
 * 現在のところ、文字列から数値への変換はサポートされていません。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * Formatting the numeric value into the String of Hex notation
 * No support is provided for conversion from String to numeric value at moment.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class HexFormat
        extends Format
{
    // Class field ------------------------------------------------------------
    /**<jp>
     * フォーマットパターン(0またはHex値)
     </jp>*/
    /**<en>
     * Format pattern ( 0 or Hex value)
     </en>*/
    public static final char FMT_FILLZERO = '0';

    /**<jp>
     * フォーマットパターン(Hex値に先行するものはサプレス)
     </jp>*/
    /**<en>
     * FOrmat pattern (Suppress any value preceding Hex value) 
     </en>*/
    public static final char FMT_ZEROSUPP = '#';

    // Class variables --------------------------------------------------------
    private String _formatPattern;

    // Class methods --------------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returning version of this class
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructor ------------------------------------------------------------
    /**<jp>
     * パターンを指定してフォーマット-インスタンスを生成します。
     * @param pattern フォーマットパターン
     </jp>*/
    /**<en>
     * Generating format instance by specifying pattern
     * @param pattern Format pattern
     </en>*/
    public HexFormat(String pattern)
    {
        _formatPattern = pattern;
    }

    // Public method ----------------------------------------------------------
    /**<jp>
     * <code>int</code>の数値をHex文字列にフォーマットします。
     * 英数文字列は、小文字になりますので、大文字にするには、
     * <code>String.toUpper()</code>を利用してください。
     * @param number  フォーマットする数値
     * @return        Hex文字列
     </jp>*/
    /**<en>
     * Formatting numeric value of <code>int</code> into Hex String
     * Alphanumeric String is in lower-case letters; if requiring capital letters,
     * use <code>String.toUpper()</code>.
     * @param number  Numeric value to format
     * @return        Hex String
     </en>*/
    public String format(int number)
    {
        Integer nber = new Integer(number);
        return (String.valueOf(format(nber, new StringBuffer(), new FieldPosition(0))));
    }

    /**<jp>
     * <code>long</code>の数値をHex文字列にフォーマットします。
     * 英数文字列は、小文字になりますので、大文字にするには、
     * <code>String.toUpperCase()</code>を利用してください。
     * @param number  フォーマットする数値
     * @return        Hex文字列
     * @see java.lang.String
     </jp>*/
    /**<en>
     * Formatting numeric value of <code>long</code> into Hex String
     * Alphanumeric String is in lower-case letters; if requiring capital letters,
     * use <code>String.toUpperCase()</code>.
     * @param number  Numeric value to format
     * @return        Hex String
     * @see java.lang.String
     </en>*/
    public String format(long number)
    {
        Long nber = new Long(number);
        return (String.valueOf(format(nber, new StringBuffer(), new FieldPosition(0))));
    }

    /**<jp>
     * Formats an object to produce a string.
     * Subclasses will implement for particular object, such as:
     * <pre>
     * StringBuffer format (Number obj, StringBuffer toAppendTo)
     * Number parse (String str)
     * </pre>
     * These general routines allow polymorphic parsing and
     * formatting for objects such as the MessageFormat.
     * @param obj          The object to format
     * @param toAppendTo   where the text is to be appended
     * @param pos          On input: an alignment field, if desired.
     * On output: the offsets of the alignment field.
     * @return             the value passed in as toAppendTo (this allows chaining,
     * as with StringBuffer.append())
     * @exception IllegalArgumentException when the Format cannot format the
     * given object.
     * @see  java.text.Format
     * @see  java.text.FieldPosition
     </jp>*/
    /**<en>
     * Formats an object to produce a string.
     * Subclasses will implement for particular object, such as:
     * <pre>
     * StringBuffer format (Number obj, StringBuffer toAppendTo)
     * Number parse (String str)
     * </pre>
     * These general routines allow polymorphic parsing and
     * formatting for objects such as the MessageFormat.
     * @param obj          The object to format
     * @param toAppendTo   where the text is to be appended
     * @param pos          On input: an alignment field, if desired.
     * On output: the offsets of the alignment field.
     * @return             the value passed in as toAppendTo (this allows chaining,
     * as with StringBuffer.append())
     * @exception IllegalArgumentException when the Format cannot format the
     * given object.
     * @see  java.text.Format
     * @see  java.text.FieldPosition
     </en>*/
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
    {
        Number nmbr = null;
        StringBuffer rStr;
        int spos;

        if (obj instanceof Number)
        {
            nmbr = (Number)obj;
            String tstring = Long.toHexString(nmbr.longValue());

            if (_formatPattern.length() < tstring.length())
            {
                // If result length longer than pattern
                rStr = new StringBuffer(tstring);
                spos = tstring.length() - _formatPattern.length();

                String finalresult = rStr.substring(spos);
                toAppendTo.append(finalresult);

                return (new StringBuffer(finalresult));
            }
            else
            {
                // If result length less than pattern
                rStr = new StringBuffer(_formatPattern);
                spos = _formatPattern.length() - tstring.length();
                rStr.replace(spos, _formatPattern.length(), tstring);

                String wk = String.valueOf(rStr).replace(FMT_ZEROSUPP, ' ').trim();

                toAppendTo.append(wk);
                return (new StringBuffer(wk));
            }
        }
        return (null);
    }

    /**
     * WARNING !!!  NOT IMPLEMENTED NOW !!!
     *
     * Parses a string to produce an object.
     * Subclasses will typically implement for particular object, such as:
     * <pre>
     *       String format (Number obj);
     *       String format (long obj);
     *       String format (double obj);
     *       Number parse (String str);
     * </pre>
     * @param source : part of which should be parsed.
     * @param pos : object with index and error index information as described above.
     * <p>Before calling, set status.index to the offset you want to start
     * parsing at in the source.
     * After calling, status.index is the end of the text you parsed.
     * If error occurs, index is unchanged.
     * <p>When parsing, leading whitespace is discarded
     * (with successful parse),
     * while trailing whitespace is left as is.
     * <p>Example:
     * Parsing "_12_xy" (where _ represents a space) for a number,
     * with index == 0 will result in
     * the number 12, with status.index updated to 3
     * (just before the second space).
     * Parsing a second time will result in a ParseException
     * since "xy" is not a number, and leave index at 3.
     * <p>Subclasses will typically supply specific parse methods that
     * return different types of values. Since methods can't overload on
     * return types, these will typically be named "parse", while this
     * polymorphic method will always be called parseObject.
     * Any parse method that does not take a status should
     * throw ParseException when no text in the required format is at
     * the start position.
     * @return Object parsed from string. In case of error, returns null.
     * @see java.text.ParsePosition
     */
    public Object parseObject(String source, ParsePosition pos)
    {
        return (new String("no"));
    }

}
