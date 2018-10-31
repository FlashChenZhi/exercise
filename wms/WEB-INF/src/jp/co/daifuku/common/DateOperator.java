// $Id: DateOperator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日時を操作するクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/03/13</TD><TD>sawa</TD><TD><CODE>parse(String str, String pattern)</CODE>?a??u/TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class DateOperator
        extends Object
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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    /**
     * 特別な理由がない限り使用しないでください。<BR>
     * 現在の日時を取得し、 yyyyMMddの形式で編集
     * 
     * @return    dateString yyyyMMddの形式の現在の日時
     */
    public static String getSysdate()
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date();
        String dateString = formatter.format(curDate);

        return dateString;
    }

    /**
     * 特別な理由がない限り使用しないでください。<BR>
     * 現在の日時を取得し、 yyyyMMddHHmmssの形式で編集
     * 
     * @return    dateString yyyyMMddHHmmssの形式の現在の日時
     */
    public static String getSysdateTime()
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date();
        String dateString = formatter.format(curDate);

        return dateString;
    }

    /**
     * 特別な理由がない限りWmsFormatterを使用してください。<BR>
     * Changes the date type variable to string of the type yyyy/MM/dd
     * 
     * @param     cgDate date type variable
     * @return    dateString yyyy/MM/ddの形式
     */
    public static String changeDate(Date cgDate)
    {
        if (cgDate == null)
        {
            return "";
        }
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy/MM/dd");
        return frmt.format(cgDate);
    }

    /**
     * 特別な理由がない限りWmsFormatterを使用してください。<BR>
     * Changes the date type variable to string of the type HH:mm:ss.iii
     * 
     * @param     cgDate date type variable
     * @return    dateString HH:mm:ss.iiiの形式
     */
    public static String changeDateTimeMillis(Date cgDate)
    {
        if (cgDate == null)
        {
            return "";
        }
        SimpleDateFormat frmt = new SimpleDateFormat("HH:mm:ss.SSS");
        return frmt.format(cgDate);
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
