// $Id: RftFormatter.java 7690 2010-03-19 03:29:33Z kishimoto $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.util;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.rft.RftConst;

/**
 * RFT画面表示用に、フォーマットを行うクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/19</TD><TD>H.Akiyama(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7690 $, $Date: 2010-03-19 12:29:33 +0900 (金, 19 3 2010) $
 * @author  $Author: kishimoto $
 */
public class RftFormatter
        extends WmsFormatter
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * ラベルへ表示する日付形式からDate型への変換を行います。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(ja:yyyy/MM/dd)*<BR>
     *   Locale*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(Date型)<BR>
     *   </DIR>
     * 
     * @param dispDate ラベルへ表示する日付形式
     * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
     * @return Dateオブジェクト
     */
     public static synchronized Date toDate(String dispDate, Locale locale)
     {
         // 引数が空文字の時は空文字を返す
         if (StringUtil.isBlank(dispDate))
         {
         return null;
         }
    
         // 表示用フォーマットから Dateに変換
         return Formatter.getDate(dispDate, Constants.F_DATE, locale);
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
      * スケジュールパラメータの日付形式からラベルへ表示する日付形式への変換を行います。<BR>
      * <BR>
      * 概要:Formatterクラスを使用し、スケジュールパラメータの
      * 日付形式(yyyyMMdd)から、ラベルに表示するためLocaleに従った日付形式
      * (日本ならばMM/dd、英語圏ならmm/DD)への変換を行います。<BR>
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
      *   日付(例:MM/dd(ja), mm/DD/(en))<BR>
      *   </DIR>
      * 
      * @param paramDate スケジュールパラメータの日付形式
      * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
      * @return ラベルへ表示する形式の日付 
      */
     public static synchronized String toDispMonthDay(String paramDate, Locale locale)
     {
         // 引数が空文字の時は空文字を返す
         if (StringUtil.isBlank(paramDate))
         {
             return "";
         }

         // String型(yyyyMMdd)からDate型へ変換

         Date date = WmsFormatter.toDate(paramDate);
         // 日本
         if (locale.equals(Locale.JAPAN))
         {
             SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
             return formatter.format(date);
         }
         // 言語圏が追加された場合はここにif文を追加
         // 英語圏
         else
         {
             SimpleDateFormat formatter = new SimpleDateFormat("mm/DD");
             return formatter.format(date);
         }
     }

     /**
      * 棚Noの表示形式を編集するメソッドです。
      * 
      * @param areaNo エリアNo
      * @param locNo 棚No
      * @param fmtStr 表現形式フォーマット文字列
      * @return 編集した棚No文字列
      */
     public static synchronized String formatAreaLocNo(String areaNo, String locNo, String fmtStr)
     {
         String tempStr = "";
         if (fmtStr == null || "".equals(fmtStr.trim()))
         {
             tempStr = locNo;
         }
         else
         {
             try
             {
                 tempStr = WmsFormatter.toDispLocation(locNo, fmtStr);
             }
             catch (ScheduleException e)
             {
                 tempStr = locNo;
             }
         }
         return RftUtil.byteMakePadRight(String.valueOf(areaNo), 4, Charset.forName(RftConst.DATA_ENCODING_STRING)) + " "
                 + tempStr;
     }
     
     /**
      * 棚形式をスケジュールパラメータ用に変換します。<br>
      * 画面表示での形式からスケジュールパラメータ用に
      * 形式をフォーマットします。<br>
      * dispLocationに値がセットされていない場合、空文字を返します。<br>
      * 変換に失敗した場合、未変換のまま返します。<br>
      * styleが不明でエリアNo.がわかる場合は、AreaController.toParamLocation() を使用してください。<br>
      * 
      * @param dispLocation 画面表示用フォーマット済み棚No.("1-01-01")
      * @param style フォーマットスタイル("9-99-99")
      * @return スケジュールパラメータ形式の棚No.("01001001")
      **/
     public static synchronized String toParamLocation(String dispLocation, String style)
     {
         try
         {
             return WmsFormatter.toParamLocation(dispLocation, style);
         }
         catch (Exception e)
         {
            return dispLocation;
         }
     }
}
