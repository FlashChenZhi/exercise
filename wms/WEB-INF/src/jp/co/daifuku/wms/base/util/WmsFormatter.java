// $Id: WmsFormatter.java 6939 2010-01-28 08:48:54Z shibamoto $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.util;

import static java.util.Calendar.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.LocationNumber;
import jp.co.daifuku.wms.base.common.WMSConstants;

/**
 * WareNaviで画面表示用に、フォーマットを行うクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/19</TD><TD>H.Akiyama(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 6939 $, $Date: 2010-01-28 17:48:54 +0900 (木, 28 1 2010) $
 * @author  $Author: shibamoto $
 */
public class WmsFormatter
        extends Formatter
{
    // Class fields --------------------------------------------------
    /** ミリ秒の桁数 */
    public static final int MILISEC_LENGTH = 3;

    /**
     * 開始日（数値変更不可）
     */
    public static final int FROMDAY = 0;

    /**
     * 開始時間（数値変更不可）
     */
    public static final int FROMTIME = 1;

    /**
     * 終了日（数値変更不可）
     */
    public static final int TODAY = 2;

    /**
     * 終了時間（数値変更不可）
     */
    public static final int TOTIME = 3;

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 進捗率表記への変換を行います。<BR>
     * <BR>
     * 概要:double型の値をパラメータにセットし、[###0.0%]のフォーマットでString型の値を返します。<BR>
     * <BR>
     * <DIR>
     *    [パラメータ] *必須入力<BR>
     *    <DIR>
     *       進捗率(double型)<BR>
     *    </DIR>
     * <BR>
     *    [返却データ]<BR>
     *    <DIR>
     *       進捗率(String型)<BR>
     *    </DIR>
     * </DIR>
     * @param rate 進捗率（double型）
     * @return 変換後文字列
     * @throws Exception 全ての例外を報告します。 
     */
    public static synchronized String toProgressRate(double rate)
            throws Exception
    {
        String changeProgressRate;
        // 進捗率は少数1桁まで表示(DecimalFormat("##0.0%"))
        DecimalFormat dft = new DecimalFormat("##0.0" + DisplayText.getText("LBL-W0221"));
        changeProgressRate = dft.format(rate);

        return changeProgressRate;
    }

    /**
     * 進捗率表記への変換を行います。<BR>
     * <BR>
     * 概要:double型の値をパラメータにセットし、[###0.00%]のフォーマットでString型の値を返します。<BR>
     * <BR>
     * <DIR>
     *    [パラメータ] *必須入力<BR>
     *    <DIR>
     *       進捗率(double型)<BR>
     *    </DIR>
     * <BR>
     *    [返却データ]<BR>
     *    <DIR>
     *       進捗率(String型)<BR>
     *    </DIR>
     * </DIR>
     * @param rate 進捗率（double型）
     * @return 変換後文字列
     * @throws Exception 全ての例外を報告します。 
     */
    public static synchronized String toComponentRate(double rate)
            throws Exception
    {
        String changeProgressRate;
        // 進捗率は少数2桁まで表示(DecimalFormat("##0.00%"))
        DecimalFormat dft = new DecimalFormat("##0.00" + DisplayText.getText("LBL-W0221"));
        changeProgressRate = dft.format(rate);

        return changeProgressRate;
    }

    /**
     * スケジュールパラメータの日付形式からDate型への変換を行います。<BR>
     * <BR>
     * 概要:SimpleDateFormatを使用し、スケジュールパラメータへ
     * 渡すための文字列(yyyyMMdd)からDate型への変換を行います。<BR>
     * 引数が空文字の時はnullを返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(String型:yyyyMMdd)*<BR>
     *   </DIR>
     *   <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(Date型)<BR>
     *   </DIR>
     * 
     * @param paramDate スケジュールパラメータの日付形式
     * @return Dateオブジェクト
     */
    public static synchronized Date toDate(String paramDate)
    {
        // 引数が空文字の時はnullを返す
        if (StringUtil.isBlank(paramDate))
        {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(WMSConstants.PARAM_DATE_FORMAT);
        Date changeDate = null;

        try
        {
            // yyyyMMddからDateへ変換する
            changeDate = dateFormat.parse(paramDate);
        }
        catch (ParseException e)
        {
            // Exceptionの場合はnullを返す。
            return null;
        }

        return changeDate;
    }

    /**
     * スケジュールパラメータの日付形式からラベルへ表示する日付形式への変換を行います。<BR>
     * <BR>
     * 概要:Formatterクラスを使用し、スケジュールパラメータの
     * 日付形式(yyyyMMdd)から、ラベルに表示するためLocaleに従った日付形式
     * (日本ならばyyyy/MM/dd、英語圏ならmm/DD/yyyy)への変換を行います。<BR>
     * 引数が空文字の時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(String型:yyyyMMdd)*<BR>
     *   Locale*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(例:yyyy/MM/dd(ja), mm/DD/yyyy(en))<BR>
     *   </DIR>
     * 
     * @param paramDate スケジュールパラメータの日付形式
     * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
     * @return ラベルへ表示する形式の日付 
     */
     public static synchronized String toDispDate(String paramDate, Locale locale)
     {
	     // 引数が空文字の時は空文字を返す
	     if (StringUtil.isBlank(paramDate))
	     {
		     return "";
	     }
	
	     // String型(yyyyMMdd)からDate型へ変換
	     Date date = toDate(paramDate);
	
	     // FormatterクラスのgetDateFormat(Date argValue, int formatType, Locale locale)を使い、
	     // Localeに従ったString型の日付に変換
	     String changeDate = Formatter.getDateFormat(date, Constants.F_DATE, locale);
	
	     return changeDate;
     }     

    /**
     * Date型から表示用日付形式への変換を行います。<BR>
     * <BR>
     * 概要:Formatterクラスを使用し、Date型から、
     * ラベルや帳票に表示するためLocaleに従った日付形式
     * (日本ならばyyyy/MM/dd、英語圏ならmm/DD/yyyy)への変換を行います。<BR>
     * 引数が空文字の時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(Date型)*<BR>
     *   Locale*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(例:yyyy/MM/dd(ja), mm/DD/yyyy(en))<BR>
     *   </DIR>
     * 
     * @param date Date型
     * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
     * @return ラベルへ表示する形式の日付
     */
    public static synchronized String toDispDate(Date date, Locale locale)
    {
        // 引数が空文字の時は空文字を返す
        if (StringUtil.isBlank(date))
        {
            return "";
        }

        // FormatterクラスのgetDateFormat(Date argValue, int formatType, Locale locale)を使い、
        // Localeに従ったString型の日付に変換
        String changeDate = Formatter.getDateFormat(date, Constants.F_DATE, locale);

        return changeDate;
    }

    /**
     * Date型から表示用時間形式への変換を行います。<BR>
     * <BR>
     * 概要:Formatterクラスを使用し、Date型から、
     * ラベルや帳票に表示するためLocaleに従った時間形式への変換を行います。<BR>
     * 引数が空文字の時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(Date型)*<BR>
     *   Locale*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   時間 HH:MM:SS<BR>
     *   </DIR>
     * 
     * @param date Date型
     * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
     * @return ラベルへ表示する形式の時間 
     */
    public static synchronized String toDispTime(Date date, Locale locale)
    {
        // 引数が空文字の時は空文字を返す
        if (StringUtil.isBlank(date))
        {
            return "";
        }

        // FormatterクラスのgetDateFormat(Date argValue, int formatType, Locale locale)を使い、
        // Localeに従ったString型の時間に変換
        return Formatter.getDateFormat(date, Constants.F_TIME, locale);
    }


    /**
     * Date型からスケジュールパラメータの日付形式への変換を行います。<BR>
     * <BR>
     * 概要:SimpleDateFormatクラスを使用し、スケジュールパラメータの日付形式(yyyyMMdd)への変換を行います。<BR>
     * 引数がnullの時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(Date型)*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(String型:yyyyMMdd)<BR>
     *   </DIR>
     * 
     * @param date Date型
     * @return スケジュールパラメータに渡す形式の日付
     */
    public static synchronized String toParamDate(Date date)
    {
        // 引数がnullの時は空文字を返す
        if (StringUtil.isBlank(date))
        {
            return "";
        }

        // Date型からString型(yyyyMMdd)へ変換
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(WMSConstants.PARAM_DATE_FORMAT);
        String changeDate = simpleDateFormat.format(date);

        return changeDate;
    }

    /**
     * ラベルへ表示する日付形式からスケジュールパラメータの日付形式への変換を行います。<BR>
     * <BR>
     * 概要:Formatterクラスを使用し、ラベルへ表示する日付形式(ja:yyyy/MM/dd)から、
     * スケジュールパラメータの日付形式(yyyyMMdd)への変換を行います。<BR>
     * 引数が空文字の時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(ja:yyyy/MM/dd)*<BR>
     *   Locale*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(String型:yyyyMMdd)<BR>
     *   </DIR>
     * 
     * @param dispDate ラベルに表示する形式の日付
     * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
     * @return スケジュールパラメータに渡す形式の日付
     */
    public static synchronized String toParamDate(String dispDate, Locale locale)
    {
        // 引数が空文字の時は空文字を返す
        if (StringUtil.isBlank(dispDate))
        {
            return "";
        }

        // FormatterクラスのgetDate(String argValue, int formatType, Locale locale)を使い、Date型へ変換
        // yyyy/MM/dd(ロケールにより決定されたフォーマット) から Date に変換
        Date date = Formatter.getDate(dispDate, Constants.F_DATE, locale);

        // Date から yyyyMMdd に変換
        String changeDate = toParamDate(date);

        return changeDate;
    }


    /**
     * スケジュールパラメータの時刻形式からDate型への変換を行います。<BR>
     * <BR>
     * 概要:SimpleDateFormatを使用し、スケジュールパラメータへ
     * 渡すための文字列(HHmmssまたはHHmmssSSS)からDate型への変換を行います。<BR>
     * 引数が空文字の時はnullを返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   時間(String型:HHmmssまたはHHmmssSSS)*<BR>
     *   </DIR>
     *   <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   時間(Date型)<BR>
     *   </DIR>
     * 
     * @param paramTime スケジュールパラメータの時間形式
     * @return Dateオブジェクト
     */
    public static synchronized Date toTime(String paramTime)
    {
        // 引数が空文字の時はnullを返す
        if (StringUtil.isBlank(paramTime))
        {
            return null;
        }

        SimpleDateFormat dateFormat;
        if ("HHmmss".length() == paramTime.length())
        {
            dateFormat = new SimpleDateFormat("HHmmss");
        }
        else
        {
            dateFormat = new SimpleDateFormat("HHmmssSSS");
        }

        Date changeTime = null;

        try
        {
            // HHmmssからDateへ変換する
            changeTime = dateFormat.parse(paramTime);
        }
        catch (ParseException e)
        {
            // Exceptionの場合はnullを返す。
            return null;
        }

        return changeTime;
    }

    /**
     * スケジュールパラメータの時間形式からラベルへ表示する時間形式への変換を行います。<BR>
     * <BR>
     * 概要:Formatterクラスを使用し、スケジュールパラメータの
     * 時間形式(HHmmss)から、ラベルに表示するためLocaleに従った時間形式への変換を行います。<BR>
     * 引数が空文字の時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   時間(String型:HHmmss)*<BR>
     *   Locale*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   時間 HH:MM:SS<BR>
     *   </DIR>
     * 
     * @param paramTime スケジュールパラメータの時間形式
     * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
     * @return ラベルへ表示する形式の時間 
     */
     public static synchronized String toDispTime(String paramTime, Locale locale)
     {
	     // 引数が空文字の時は空文字を返す
	     if (StringUtil.isBlank(paramTime))
	     {
		     return "";
	     }
	
	     // String型(HHmmssまたはHHmmssSSS)からDate型へ変換
	     Date date = toTime(paramTime);
	
	     // FormatterクラスのgetDateFormat(Date argValue, int formatType, Locale locale)を使い、
	     // Localeに従ったString型の時間に変換
	     String changeDate;
	     if ("HHmmss".length() == paramTime.length())
	     {
		     changeDate = Formatter.getDateFormat(date, Constants.F_TIME, locale);
	     }
	     else
	     {
	    	 // TODO Formatterのミリ秒対応が行われればそちらを使用する
		     SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		     changeDate = dateFormat.format(toTime(paramTime));
	     }
	
	     return changeDate;
     }

    /**
     * Date型からスケジュールパラメータの時間形式への変換を行います。<BR>
     * <BR>
     * 概要:SimpleDateFormatクラスを使用し、スケジュールパラメータの時間形式(HHmmss)への変換を行います。<BR>
     * 引数がnullの時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   時間(Date型)*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   時間(String型:HHmmss)<BR>
     *   </DIR>
     * 
     * @param time Date型
     * @return スケジュールパラメータに渡す形式の日付
     */
    public static synchronized String toParamTime(Date time)
    {
        return toParamTime(time, 0);
    }


    /**
     * Date型からスケジュールパラメータの時間形式への変換を行います。<BR>
     * <BR>
     * 概要:SimpleDateFormatクラスを使用し、スケジュールパラメータの時間形式(HHmmss)への変換を行います。<BR>
     * 引数がnullの時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   時間(Date型)*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   時間(String型:HHmmss)<BR>
     *   </DIR>
     * 
     * @param time Date型
     * @param precision 戻り値のフォーマットに使用するミリ秒の桁数
     * @return スケジュールパラメータに渡す形式の日付
     */
    public static synchronized String toParamTime(Date time, int precision)
    {
        // 引数がnullの時は空文字を返す
        if (StringUtil.isBlank(time))
        {
            return "";
        }

        // Date型からString型(HHmmss)へ変換
        String fmt = "HHmmss";
        for (int i = 0; i < precision; i++)
        {
            fmt = fmt + "S";
        }
        SimpleDateFormat sdFmt = new SimpleDateFormat(fmt);
        String changeTime = sdFmt.format(time);

        return changeTime;
    }

    /**
     * ラベルへ表示する時間形式からスケジュールパラメータの日付形式への変換を行います。<BR>
     * <BR>
     * 概要:Formatterクラスを使用し、ラベルへ表示する日付形式(ja:HH:mm:ss)から、
     * スケジュールパラメータの日付形式(HHmmss)への変換を行います。<BR>
     * 引数が空文字の時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(ja:HH:mm:ss)*<BR>
     *   Locale*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(String型:HHmmss)<BR>
     *   </DIR>
     * 
     * @param dispTime ラベルに表示する形式の時間
     * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
     * @return スケジュールパラメータに渡す形式の日付
     * 
     */
    public static synchronized String toParamTime(String dispTime, Locale locale)
    {
        // 引数が空文字の時は空文字を返す
        if (StringUtil.isBlank(dispTime))
        {
            return "";
        }

        // FormatterクラスのgetDate(String argValue, int formatType, Locale locale)を使い、Date型へ変換
        // yyyy/MM/dd(ロケールにより決定されたフォーマット) から Date に変換
        Date time = Formatter.getDate(dispTime, Constants.F_TIME, locale);

        // Date から yyyyMMdd に変換
        return toParamTime(time);
    }

    /**
     * String型(yyyyMMddHHmmss または yyyyMMddHHmmssSSS)からDate型への変換を行います。<BR>
     * このメソッドは画面で保持した最終更新日時を文字に変換する際に、文字列型から日付型に変換するために使用します。
     * @param paramDateTime yyyyMMddHHmmss形式またはyyyyMMddHHmmssSSS形式の文字列をセットします。
     * @return 変換されたDateオブジェクト 
     */
    public static synchronized Date toDateTime(String paramDateTime)
    {
        // 引数が空文字の時はnullを返す
        if (StringUtil.isBlank(paramDateTime))
        {
            return null;
        }

        SimpleDateFormat fmt;
        if ("yyyyMMddHHmmss".length() == paramDateTime.length())
        {
            fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        }
        else
        {
            fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        }

        fmt.setLenient(true);
        try
        {
            return fmt.parse(paramDateTime, new ParsePosition(0));
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * Date型からString型(yyyyMMddHHmmss)への変換を行います。<BR>
     * このメソッドは最終更新日時を画面で保持する際に、日付型から文字列型に変換するために使用します。
     * @param dateTime 変換対象となる日付がセットされたDateオブジェクト
     * @return yyyyMMddHHmmssの形式で文字列に変換したStringオブジェクト 
     */
     public static synchronized String toParamDateTime(Date dateTime)
     {
    	 return toParamDateTime(dateTime, 0);
     }
     

    /**
     * Date型からString型(yyyyMMddHHmmssSSS...)への変換を行います。<BR>
     * このメソッドは最終更新日時を画面で保持する際に、日付型から文字列型に変換するために使用します。
     * @param dateTime 変換対象となる日付がセットされたDateオブジェクト
     * @param precision 戻り値のフォーマットに使用するミリ秒の桁数
     * @return yyyyMMddHHmmssSSSの形式で文字列に変換したStringオブジェクト 
     */
    public static synchronized String toParamDateTime(Date dateTime, int precision)
    {
        if (dateTime == null)
        {
            return "";
        }
        else
        {
            String fmt = "yyyyMMddHHmmss";
            for (int i = 0; i < precision; i++)
            {
                fmt = fmt + "S";
            }
            SimpleDateFormat sformatter = new SimpleDateFormat(fmt);
            return sformatter.format(dateTime);
        }
    }

    /**
     * Date型からスケジュールパラメータの年月形式への変換を行います。<BR>
     * <BR>
     * 概要:SimpleDateFormatクラスを使用し、スケジュールパラメータの年月形式(yyyyMM)への変換を行います。<BR>
     * 引数がnullの時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(Date型)*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(String型:yyyyMM)<BR>
     *   </DIR>
     * 
     * @param date Date型
     * @return スケジュールパラメータに渡す年月形式の日付
     */
    public static synchronized String toParamYearMonth(Date date)
    {
        // 引数がnullの時は空文字を返す
        if (StringUtil.isBlank(date))
        {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyLocalizedPattern("yyyyMM");

        // Date型からString型へ変換
        String changeDate = sdf.format(date);

        return changeDate;
    }

    /**
     * 画面入力の検索日、検索時間から日付型の検索日時を返します。<BR>
     * 検索日、検索時間(ミリ秒なし)はパラメータ型にフォーマットしたものを渡してください。<BR>
     * 
     * @param fromday 検索日(YYMMDD)
     * @param fromtime 検索時間(HHmmss)
     * @return 検索日時
     */
     public static synchronized Date getSearchFromDate(String fromday, String fromtime)
     {
	     Date fromDate;
	     fromDate = WmsFormatter.toDateTime(fromday + fromtime);
	     return fromDate;
     }


    /**
     * 画面入力の検索日、検索時間から日付型の検索日時を返します。<BR>
     * 検索日、検索時間(ミリ秒なし)はパラメータ型にフォーマットしたものを渡してください。<BR>
     * わたされた検索時間より１秒進めた日時を返します。
     * 
     * @param today 検索日(YYMMDD)
     * @param totime 検索時間(HHmmss)
     * @return 検索日時
     */
     public static synchronized Date getSearchToDate(String today, String totime)
     {
	     String time = totime;
	     if (totime != null && totime.length() > 6)
	     {
		     time = totime.substring(0, 6);
	     }
	     Date tempDate = WmsFormatter.toDateTime(today + time);
	     if (StringUtil.isBlank(tempDate))
	     {
		     return tempDate;
	     }
	
	     Calendar curDate = Calendar.getInstance();
	     curDate.setTime(tempDate);
	     curDate.add(Calendar.SECOND, 1);
	     return curDate.getTime();
     }
     

    /**
     * 画面入力の開始時刻、終了時刻、範囲検索フラグから
     * それぞれの時刻を補完して返します。<BR>
     * 引数の開始時刻、終了時刻にはパラメータ型の時刻をセットしてください。(HHmmss)
     * 
     * <BR>
     * @param inputfromday パラメータ型の開始日
     * @param inputfromtime パラメータ型の開始時刻
     * @param inputtoday パラメータ型の終了日
     * @param inputtotime パラメータ型の終了時刻
     * @param isRangeSearch 範囲検索か否か(true 範囲検索、false 範囲検索でない)
     * @return 開始時刻、終了時刻の文字列配列（パラメータ型）
     * @throws ScheduleException 検索日が空白の場合に検索時間に入力があると通知されます。
     */
    public static synchronized String[] getSearchDayTime(String inputfromday, String inputfromtime, String inputtoday,
            String inputtotime, boolean isRangeSearch)
            throws ScheduleException
    {
        if (StringUtil.isBlank(inputfromday) && !StringUtil.isBlank(inputfromtime))
        {
            throw new ScheduleException();
        }
        if (StringUtil.isBlank(inputtoday) && !StringUtil.isBlank(inputtotime))
        {
            throw new ScheduleException();
        }
        String fromday = inputfromday;
        String fromtime = inputfromtime;
        String today = inputtoday;
        String totime = inputtotime;

        // 範囲検索の場合
        if (isRangeSearch)
        {
            if (!StringUtil.isBlank(inputfromday))
            {
                if (StringUtil.isBlank(inputfromtime))
                {
                    // 開始検索日時
                    fromtime = "000000";
                }
            }
            if (!StringUtil.isBlank(inputtoday))
            {
                if (StringUtil.isBlank(inputtotime))
                {
                    // 終了検索日時
                    totime = "235959";
                }
            }
        }
        else
        {
            if (!StringUtil.isBlank(inputfromday))
            {
                if (StringUtil.isBlank(inputfromtime))
                {
                    // 開始検索日時
                    fromtime = "000000";
                    // 終了検索時間
                    totime = "235959";
                }
                else
                {
                    // 終了検索時間
                    totime = inputfromtime;
                }
                // 終了検索日
                today = inputfromday;
            }
        }

        String[] retSearch = new String[4];
        retSearch[FROMDAY] = fromday;
        retSearch[FROMTIME] = fromtime;
        retSearch[TODAY] = today;
        retSearch[TOTIME] = totime;
        return retSearch;
    }


    /**
     * 棚形式を画面表示用に変換します。<br>
     * スケジュールパラメータでの形式から画面表示用に
     * 形式をフォーマットします。<br>
     * Locationに値がセットされていない場合は、nullを返します。<br>
     * styleが不明でエリアNo.がわかる場合は、AreaController.toParamLocation() を使用してください。<br>
     * 
     * @param location スケジュールパラメータ形式の棚No.("01001001")
     * @param style フォーマットスタイル("9-99-99")
     * @return フォーマット済みの棚No. ("1-01-01")
     * @exception ScheduleException 棚変換に失敗した場合に通知されます
     */
    public static synchronized String toDispLocation(String location, String style)
            throws ScheduleException
    {
        if (StringUtil.isBlank(location))
        {
            return "";
        }

        try
        {
            LocationNumber loc = new LocationNumber(style);
            loc.parseParam(location);
            return loc.formatDisp();
        }
        catch (RuntimeException e)
        {
            return location;
        }

    }


    /**
     * 棚形式をスケジュールパラメータ用に変換します。<br>
     * 画面表示での形式からスケジュールパラメータ用に
     * 形式をフォーマットします。<br>
     * dispLocationに値がセットされていない場合、nullを返します。<br>
     * styleが不明でエリアNo.がわかる場合は、AreaController.toParamLocation() を使用してください。<br>
     * 
     * @param dispLocation 画面表示用フォーマット済み棚No.("1-01-01")
     * @param style フォーマットスタイル("9-99-99")
     * @return スケジュールパラメータ形式の棚No.("01001001")
     * @exception ScheduleException 棚変換に失敗した場合に通知されます
     * @exception LocationFormatException 棚変換に失敗した場合に通知されます
     **/
    public static synchronized String toParamLocation(String dispLocation, String style)
            throws ScheduleException,LocationFormatException
    {
        if (StringUtil.isBlank(dispLocation))
        {
            return "";
        }

        LocationNumber loc = new LocationNumber(style);
        loc.parseDisp(dispLocation);
        return loc.getformat();
    }

    /**
     * 画面で、From/Toテキストボックスに入力された値を
     * 引数に受け取り、返り値の配列0番目に小さい値を、
     * 配列1番目に大きい値を返します。<BR>
     * From/Toどちらかの値が未入力(空白またはnull)だった場合は
     * 受け取ったままの順序で返します。<BR>
     * 
     * @param from Fromテキストボックスに入力された値
     * @param to Toテキストボックスに入力された値
     * @return 配列0番目：小さい値 配列1番目：大きい値
     */
    public static synchronized String[] getFromTo(String from, String to)
    {
        if (StringUtil.isBlank(from) || StringUtil.isBlank(to))
        {
            String[] ret = {
                    from,
                    to
            };
            return ret;
        }

        if (from.compareTo(to) <= 0)
        {
            String[] ret = {
                    from,
                    to
            };
            return ret;
        }

        String[] ret = {
                to,
                from
        };
        return ret;
    }

    /**
     * 画面で、From/Toテキストボックスに入力された値を
     * 引数に受け取り、返り値の配列0番目に小さい値を、
     * 配列1番目に大きい値を返します。<BR>
     * From/Toどちらかの値が未入力(空白またはnull)だった場合は
     * 受け取ったままの順序で返します。<BR>
     * 
     * @param from Fromテキストボックスに入力された値
     * @param to Toテキストボックスに入力された値
     * @return 配列0番目：小さい値 配列1番目：大きい値
     */
    public static synchronized Date[] getFromTo(Date from, Date to)
    {
        if (StringUtil.isBlank(from) || StringUtil.isBlank(to))
        {
            Date[] ret = {
                    from,
                    to
            };
            return ret;
        }

        if (from.compareTo(to) <= 0)
        {
            Date[] ret = {
                    from,
                    to
            };
            return ret;
        }

        Date[] ret = {
                to,
                from
        };
        return ret;
    }

    /**
     * 日付・時間 テキストボックスの範囲指定時の開始日時、終了日時を返す。
     * 
     * @param inputFromDate 入力：From日付
     * @param inputFromTime 入力：From時間
     * @param inputToDate 入力：To日付
     * @param inputToTime 入力：To時間
     * @return 時間を補完した日付。
     * @throws ScheduleException 検索日が空白の場合に検索時間に入力があると通知されます。
     */
    public static synchronized Date[] getFromTo(Date inputFromDate, Date inputFromTime, Date inputToDate,
            Date inputToTime)
            throws ScheduleException
    {
        Date from = null;
        Date to = null;
        // どちらか一方の日付、または日付入力なし
        if (StringUtil.isBlank(inputFromDate) || StringUtil.isBlank(inputToDate))
        {
            from = WmsFormatter.getFromSearchDate(inputFromDate, inputFromTime);
            to = WmsFormatter.getToSearchDate(inputToDate, inputToTime);
        }
        // 両方の日付が入力されている場合
        else
        {
            // FromとToが同じ日付の場合、時間を補完してから大小チェックを行う
            if (inputFromDate.equals(inputToDate))
            {
                // どちらか一方時間の入力、または時間入力なし
                if (StringUtil.isBlank(inputFromTime) || StringUtil.isBlank(inputToTime))
                {
                    from = WmsFormatter.getFromSearchDate(inputFromDate, inputFromTime);
                    to = WmsFormatter.getToSearchDate(inputToDate, inputToTime);
                }
                //// ミリ秒の補正を行うため、ローカルで大小チェックを行う
                // Fromの方があとの日付の場合、前後を入れ替える
                else if (inputFromTime.after(inputToTime))
                {
                    from = WmsFormatter.getFromSearchDate(inputToDate, inputToTime);
                    to = WmsFormatter.getToSearchDate(inputFromDate, inputFromTime);

                }
                // Fromの方が先の日付の場合、そのまま補完する
                else
                {
                    from = WmsFormatter.getFromSearchDate(inputFromDate, inputFromTime);
                    to = WmsFormatter.getToSearchDate(inputToDate, inputToTime);

                }

            }
            // FromとToが異なる日付の場合
            else
            {
                // Fromの方があとの日付の場合、前後を入れ替える
                if (inputFromDate.after(inputToDate))
                {
                    from = WmsFormatter.getFromSearchDate(inputToDate, inputToTime);
                    to = WmsFormatter.getToSearchDate(inputFromDate, inputFromTime);

                }
                // Fromの方が先の日付の場合、そのまま補完する
                else
                {
                    from = WmsFormatter.getFromSearchDate(inputFromDate, inputFromTime);
                    to = WmsFormatter.getToSearchDate(inputToDate, inputToTime);

                }
            }
        }

        Date[] ret = {
                from,
                to
        };

        return ret;


    }

    /**
     * 画面入力の開始日、開始時刻からDateに変換し、返します。<BR>
     * 時刻が未入力だった場合、00:00:00を補完して返します。<BR>
     * 
     * <BR>
     * @param date 開始日
     * @param time 開始時刻
     * @return 日付
     * @throws ScheduleException 検索日が空白の場合に検索時間に入力があると通知されます。
     */
    public static synchronized Date getFromSearchDate(Date date, Date time)
            throws ScheduleException
    {
        // 時間だけ入力の場合は例外を通知
        if (StringUtil.isBlank(date) && !StringUtil.isBlank(time))
        {
            throw new ScheduleException();
        }
        // 両方未入力の場合はnullを返す
        if (StringUtil.isBlank(date) && StringUtil.isBlank(time))
        {
            return null;
        }

        // 時刻入力がなかった場合、補完する
        if (StringUtil.isBlank(time))
        {
            Calendar dayCal = Calendar.getInstance();
            dayCal.setTime(date);

            Calendar retCal = Calendar.getInstance();
            retCal.set(dayCal.get(YEAR), dayCal.get(MONTH), dayCal.get(DAY_OF_MONTH), 0, 0, 0);
            retCal.set(Calendar.MILLISECOND, 000);

            return retCal.getTime();

        }
        // 時刻入力があった場合
        else
        {
            Calendar dayCal = Calendar.getInstance();
            dayCal.setTime(date);
            Calendar timeCal = Calendar.getInstance();
            timeCal.setTime(time);

            Calendar retCal = Calendar.getInstance();
            retCal.set(dayCal.get(YEAR), dayCal.get(MONTH), dayCal.get(DAY_OF_MONTH), timeCal.get(HOUR_OF_DAY),
                    timeCal.get(MINUTE), timeCal.get(SECOND));
            retCal.set(Calendar.MILLISECOND, 000);

            return retCal.getTime();

        }

    }

    /**
     * 画面入力の終了日、終了時刻からDateに変換し、返します。<BR>
     * 時刻が未入力だった場合、00:00:00を補完して返します。<BR>
     * 
     * <BR>
     * @param date 終了日
     * @param time 終了時刻
     * @return 日付
     * @throws ScheduleException 検索日が空白の場合に検索時間に入力があると通知されます。
     */
    public static synchronized Date getToSearchDate(Date date, Date time)
            throws ScheduleException
    {
        // 時間だけ入力の場合は例外を通知
        if (StringUtil.isBlank(date) && !StringUtil.isBlank(time))
        {
            throw new ScheduleException();
        }
        // 両方未入力の場合はnullを返す
        if (StringUtil.isBlank(date) && StringUtil.isBlank(time))
        {
            return null;
        }

        // 時刻入力がなかった場合、補完する
        if (StringUtil.isBlank(time))
        {
            Calendar dayCal = Calendar.getInstance();
            dayCal.setTime(date);

            Calendar retCal = Calendar.getInstance();
            retCal.set(dayCal.get(YEAR), dayCal.get(MONTH), dayCal.get(DAY_OF_MONTH), 23, 59, 59);
            retCal.set(Calendar.MILLISECOND, 999);

            return retCal.getTime();

        }
        // 時刻入力があった場合
        else
        {
            Calendar dayCal = Calendar.getInstance();
            dayCal.setTime(date);
            Calendar timeCal = Calendar.getInstance();
            timeCal.setTime(time);

            Calendar retCal = Calendar.getInstance();
            retCal.set(dayCal.get(YEAR), dayCal.get(MONTH), dayCal.get(DAY_OF_MONTH), timeCal.get(HOUR_OF_DAY),
                    timeCal.get(MINUTE), timeCal.get(SECOND));
            retCal.set(Calendar.MILLISECOND, 999);

            return retCal.getTime();

        }

    }

    /**
     * 画面入力の日付と時刻を合わせたDateを返します。<BR>
     * どちらかの引数が空文字またはnullの時はnullを返します。
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(Date型)*<BR>
     *   時刻(Date型)*<BR>
     *   </DIR>
     *   <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(Date型)<BR>
     *   </DIR>
     * 
     * @param day  日付
     * @param time 時刻
     * @return Dateオブジェクト
     */
    public static synchronized Date toDate(Date day, Date time)
    {
        // どちらかの引数が空文字の時はnullを返す
        if (StringUtil.isBlank(day) || StringUtil.isBlank(time))
        {
            return null;
        }

        Calendar dayCal = Calendar.getInstance();
        dayCal.setTime(day);
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);

        Calendar retCal = Calendar.getInstance();
        retCal.set(dayCal.get(YEAR), dayCal.get(MONTH), dayCal.get(DAY_OF_MONTH), timeCal.get(HOUR_OF_DAY),
                timeCal.get(MINUTE), timeCal.get(SECOND));
        retCal.set(Calendar.MILLISECOND, 000);

        return retCal.getTime();
    }

    // PickingCart用
    /**
     * スケジュールパラメータの時間形式からラベルへ表示する時間形式への変換を行います。<BR>
     * <BR>
     * 概要:Formatterクラスを使用し、スケジュールパラメータの
     * 時間形式(HHmmss)から、ラベルに表示するためLocaleに従った時間形式への変換を行います。<BR>
     * 引数が空文字の時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日時(String型:yyyymmddHHmmss)*<BR>
     *   Locale*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   時間 YYYY/MM/DD HH:MM:SS<BR>
     *   </DIR>
     * 
     * @param paramTime スケジュールパラメータの時間形式
     * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
     * @return ラベルへ表示する形式の時間 
     */
    public static synchronized String toDispDateTime(String paramTime, Locale locale)
    {
        // 引数が空文字の時は空文字を返す
        if (StringUtil.isBlank(paramTime))
        {
            return "";
        }

        return toDispDate(paramTime.substring(0, 8), locale) + " " + toDispTime(paramTime.substring(8), locale);
    }

    /**
     * 数値を表示形式に変換します。
     * 
     * @param num 数値
     * @param decimalPoint 小数第何位まで表示するか
     * @return #,##0.0の形式
     */
    public static synchronized String toDispNumber(double num, int decimalPoint)
    {
        // 現在、deciamlPoint未使用
        DecimalFormat dft = new DecimalFormat("#,##0.0");
        return dft.format(num);
    }

    /**
     * 秒を時間と分の形式(h,hhh:mm)にフォーマットします。
     * 
     * @param second 秒
     * @param comma コンマ編集を行なう場合はtrue、それ以外はfalse
     * @return フォーマット済みの時間と分(h,hhh:mm)
     */
    public static synchronized String toDispHourMinute(int second, boolean comma)
    {
        int hour = second / 60 / 60;
        int minute = second / 60 % 60;

        DecimalFormat dfhour;
        if (comma)
        {
            dfhour = new DecimalFormat("#,##0");
        }
        else
        {
            dfhour = new DecimalFormat("###0");
        }
        DecimalFormat dfminute = new DecimalFormat("00");
        String hourMinute = dfhour.format(hour) + ":" + dfminute.format(minute);

        return hourMinute;
    }

    /**
     * 秒を時間と分の形式(h,hhh:mm)にフォーマットします。
     * 
     * @param second 秒
     * @return フォーマット済みの時間と分(h,hhh:mm)
     */
    public static synchronized String toDispHourMinute(int second)
    {
        return toDispHourMinute(second, true);
    }

    /**
     * 生産率表記への変換を行います。<BR>
     * <BR>
     * 概要:double型の値をパラメータにセットし、[###0.0%]のフォーマットでString型の値を返します。<BR>
     * <BR>
     * <DIR>
     *    [パラメータ] *必須入力<BR>
     *    <DIR>
     *       生産率(double型)<BR>
     *    </DIR>
     * <BR>
     *    [返却データ]<BR>
     *    <DIR>
     *       生産率(String型)<BR>
     *    </DIR>
     * </DIR>
     * @param rate 生産率（double型）
     * @return 変換後文字列
     * @throws Exception 全ての例外を報告します。 
     */
    public static synchronized String toProductionRate(double rate)
            throws Exception
    {
        String changeProgressRate;
        // 進捗率は少数1桁まで表示(DecimalFormat("##0.0%"))
        DecimalFormat dft = new DecimalFormat("##0.0");
        changeProgressRate = dft.format(rate) + DisplayText.getText("LBL-W0221");

        return changeProgressRate;
    }

    /**
     * ミス率表記への変換を行います。<BR>
     * <BR>
     * 概要:int型の値をパラメータにセットし、[##0%]のフォーマットでString型の値を返します。<BR>
     * <BR>
     * <DIR>
     *    [パラメータ] *必須入力<BR>
     *    <DIR>
     *       ミス率(int型)<BR>
     *    </DIR>
     * <BR>
     *    [返却データ]<BR>
     *    <DIR>
     *       ミス率(String型)<BR>
     *    </DIR>
     * </DIR>
     * @param rate ミス率（int型）
     * @return 変換後文字列
     * @throws Exception 全ての例外を報告します。 
     */
    public static synchronized String toMissRate(int rate)
            throws Exception
    {
        String changeProgressRate;
        DecimalFormat dft = new DecimalFormat("##0");
        changeProgressRate = dft.format(rate) + DisplayText.getText("LBL-W0221");

        return changeProgressRate;
    }
}
