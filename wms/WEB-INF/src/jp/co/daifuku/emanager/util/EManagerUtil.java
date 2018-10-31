// $Id: EManagerUtil.java 7765 2010-03-31 02:29:07Z shibamoto $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

package jp.co.daifuku.emanager.util;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import javax.servlet.http.HttpServletRequest;

import jp.co.daifuku.Constants;
import jp.co.daifuku.bluedog.ui.control.ListBox;
import jp.co.daifuku.bluedog.ui.control.ListBoxImpl;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;

/**
 * <jp>eManager用のユーティリティクラスです。<br></jp>
 * <en>Utility class for eManager.<br></en>
 * @author K.Fukumori
 * @since 2006/11/11
 */
public class EManagerUtil
{
    /**
     * <jp>リクエストを元にメニューへのパスを取得します。 <br></jp>
     * <en>Return the menu path by request.<br></en>
     * @param request <jp>リクエスト &nbsp;&nbsp;</jp><en>Request &nbsp;&nbsp;</en>
     * @param conn Connection
     * @return <jp>メニューへのパス &nbsp;&nbsp;</jp><en>Path of menu. &nbsp;&nbsp;</en>
     * @throws SQLException 
     */
    public static String getMenuId(HttpServletRequest request, Connection conn)
            throws SQLException
    {

        String uri = request.getRequestURI();
        String contextpath = request.getContextPath();

        uri = uri.substring(uri.indexOf(contextpath) + contextpath.length() + 1, uri.length() - 3);

        MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);
        String id = menuHandler.findMenuIdByUri(uri);

        return "/menu/SubMenu.do?id=" + id;
    }

    /**
     * <jp>IPアドレス(IPv4)をホスト名に変換します。
     * ホスト名を解決できない場合は null を返却します。<br></jp>
     * <en>Return hostname by IP address (IPv4). If cannot get host name, return null.<br></en>
     * @param ip <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @return <jp>ホスト名文字列 &nbsp;&nbsp;</jp><en>host name &nbsp;&nbsp;</en>
     */
    public static String getHostnameByIpAddress(String ip)
    {
        String hostname = null;

        // IP address string to byte array
        String ipStrArray[] = ip.split("\\.");
        byte ipByteArray[] = new byte[ipStrArray.length];
        for (int i = 0; i < ipStrArray.length; i++)
        {
            try
            {
                ipByteArray[i] = Integer.valueOf(ipStrArray[i]).byteValue();
            }
            catch (NumberFormatException e)
            {
                ipByteArray = null;
            }
        }

        try
        {
            InetAddress addr = InetAddress.getByAddress(ipByteArray);
            hostname = addr.getHostName();
        }
        catch (UnknownHostException e)
        {
            hostname = null;
        }

        return hostname;
    }

    /**
     * <jp>ホスト名をIPアドレス(IPv4)に変換します。
     * ホスト名を解決できない場合は null を返却します。<br></jp>
     * <en>Return IP address (IPv4) by hostname. If cannot get host name, return null.<br></en>
     * @param hostname <jp>ホスト名 &nbsp;&nbsp;</jp><en>Host name &nbsp;&nbsp;</en>
     * @return <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     */
    public static String getIpAddressByHostname(String hostname)
    {
        StringBuffer ipaddress = new StringBuffer();
        try
        {
            InetAddress addr = InetAddress.getByName(hostname);
            byte ipArray[] = addr.getAddress();
            for (int i = 0; i < ipArray.length; i++)
            {
                if (i != 0)
                {
                    ipaddress.append(".");
                }

                // Convert byte to int.
                int value = ipArray[i] >= 0 ? ipArray[i]
                                           : (Byte.MAX_VALUE - Byte.MIN_VALUE + 1 + ipArray[i]);
                ipaddress.append(value);
            }
        }
        catch (UnknownHostException e)
        {
            return null;
        }

        return ipaddress.toString();
    }

    /**
     * <jp>IPアドレスの範囲チェックを行います。<br></jp>
     * <en><br></en>
     * @param ipAddress <jp>チェックするIPアドレス &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     * @param max <jp>IPアドレスの範囲の最大値 &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     * @param min <jp>IPアドレスの範囲の最小値 &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     * @return <jp>チェックするIPアドレスが範囲内にある場合はTRUEを返却します。 &nbsp;&nbsp;</jp>
     *          <en> &nbsp;&nbsp;</en>
     */
    public static boolean checkIpRange(String ipAddress, String max, String min)
    {
        if (compareIpAddress(ipAddress, max) > 0)
        {
            return false;
        }
        if (compareIpAddress(ipAddress, min) < 0)
        {
            return false;
        }
        return true;
    }

    /**
     * <jp>引数のIPアドレスを比較します。<br>
     * 初めのIPが大きい場合は 1, 小さい場合は -1 等しい場合は 0 を返却します。<br>
     * <br></jp>
     * <en>Compare the ipaddress.<br></en>
     * @param ip1 IP Address
     * @param ip2 IP Address
     * @return <jp>比較結果 &nbsp;&nbsp;</jp><en>Result &nbsp;&nbsp;</en>
     */
    public static int compareIpAddress(String ip1, String ip2)
    {
        String ipArray1[] = ip1.split("\\.");
        String ipArray2[] = ip2.split("\\.");


        for (int i = 0; i < ipArray1.length; i++)
        {
            int value1 = Integer.parseInt(ipArray1[i]);
            int value2 = Integer.parseInt(ipArray2[i]);

            if (value1 > value2)
            {
                return 1;
            }
            else if (value1 < value2)
            {
                return -1;
            }
        }
        return 0;
    }

    /**
     * <jp>引数の文字列に含まれるHTMLの特殊文字を変換して返却します。<br></jp>
     * <en>Convert the HTML special charactor..<br></en>
     * @param str <jp>対象文字列 &nbsp;&nbsp;</jp><en>Target string &nbsp;&nbsp;</en>
     * @return <jp>変換した文字列 &nbsp;&nbsp;</jp><en>Converted string. &nbsp;&nbsp;</en>
     */
    public static String ConvertForHtml(String str)
    {
        if (str == null)
        {
            return null;
        }
        str = str.replaceAll("\"", "&quot;");
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll(" ", "&nbsp;");
        str = str.replaceAll(" ", "&nbsp;");

        return str;
    }

    /** <jp>引数の'@'で挟まれた範囲の文字列をリソースキーと判断し、
     * ロケールに合わせたメッセージへ変換を行う為の文字。 &nbsp;&nbsp;</jp>
     * <en>The string of the range that it is put with '@' of 
     * the argument is judged a resource key, and it changes into 
     * the message checked with locale.  &nbsp;&nbsp;</en> */
    private static final String MSG_DEF_CHAR = "@";

    /** 
     * <jp>引数の'@'で挟まれた範囲の文字列をリソースキーと判断し、ロケール
     * に合わせたメッセージへ変換を行います。
     * ・引数で、'@'で両端を区切られているものをリソース番号として認識する<br>
     * ・指定されたリソースが存在しない場合は、引数としてセットされている文字をそのまま表示する<br>
     * ・複数のメッセージをセットすることも可能
     * 	'@6401015@' -> '処理中です。しばらくお待ち下さい。'<br>
     * 	'@6401015@','@LBL-0001@' -> '処理中です。しばらくお待ち下さい。', '品名コード'<br> 
     * <br></jp>
     * 
     * <en>The string of the range that it is put with '@' of the argument is judged a resource key, 
     * and it changes into the message checked with locale.  
     * It has both ends divided with '@' is recognized in the argument of the user script as a resource number. 
     * Resource number and the range that it was recognized.<br>
     * When a specified resource doesn't exist, the character being set as an argument is indicated as it is.<br>
     * It is possible that more than one message is set, too.
     * ex.<br>
     * '@6401015@' -> 'Operation accepted,please wait...'<br>
     * '@6401015@','@LBL-0001@' -> 'Operation accepted,please wait...', 'ItemCode'<br>
     * <br></en> 
     * 
     * @param str <jp>操作対象文字列 &nbsp;&nbsp;</jp><en>Terget string &nbsp;&nbsp;</en>
     * @return <jp>変換した文字列 &nbsp;&nbsp;</jp><en>Changed string. &nbsp;&nbsp;</en> 
     */
    public static String getTransString(String str)
    {
        try
        {
            int pos = 0;
            List list = new ArrayList();
            for (int i = 0; i < str.length(); i++)
            {
                int start_pos = str.indexOf(MSG_DEF_CHAR, pos);
                if (start_pos >= 0)
                {
                    int end_pos = str.indexOf(MSG_DEF_CHAR, start_pos + 1);
                    if (end_pos >= 0)
                    {
                        list.add(str.substring(start_pos, end_pos + 1));
                        pos = end_pos + 1;
                    }
                    else
                    {
                        //Error
                        break;
                    }
                }
                else
                {
                    break;
                }
            }

            String retValue = str;
            Iterator itr = list.iterator();
            while (itr.hasNext())
            {
                String orgmsg = ((String)itr.next());
                //Remove @ mark.
                String msgkey = orgmsg.replaceAll(MSG_DEF_CHAR, "");

                retValue = retValue.replaceAll(orgmsg, DispResources.getText(msgkey));
            }
            return retValue;
        }
        catch (Exception e)
        {
            return str;
        }
    }

    /**
     * <jp>リストボックスに格納されているアイテムのリストを取得します。<br>
     * BlueDOG で非推奨メソッドの為、ラップしています。<br>
     * <br></jp>
     * <en>Returns the list of listbox items.<br></en>
     * @param listbox <jp>対象のリストボックスコントロール &nbsp;&nbsp;</jp><en>Target listbox control. &nbsp;&nbsp;</en> 
     * @return <jp>リストボックスのアイテムのリスト &nbsp;&nbsp;</jp><en>List of listbox item. &nbsp;&nbsp;</en> 
     */
    public static List getListBoxItems(ListBox listbox)
    {
        // Call BlueDOG deprecated method.
        List list = ((ListBoxImpl)listbox).getListBoxItems();
        return list;
    }

    /**
     * Stringの配列をCSV形式に変換します。
     * 文字列に , (カンマ) が含まれる場合は "" で囲います。
     * また、文字列に " (ダブルコート) が含まれる場合は "" で囲った上 "" に変換します。
     * @param stringArray 変換するString配列
     * @return CSV形式の文字列
     */
    public static String getCsvFromStringArray(String[] stringArray)
    {
        StringBuffer csv = new StringBuffer();
        for (int i = 0; stringArray != null && i < stringArray.length; i++)
        {
            // 連結
            if (stringArray[i].indexOf(",") >= 0 || stringArray[i].indexOf("\"") >= 0)
            {
                csv.append("\"" + stringArray[i].replaceAll("\"", "\"\"") + "\"");
            }
            else
            {
                csv.append(stringArray[i]);
            }

            // ' を出力
            if (i != stringArray.length - 1)
            {
                csv.append(",");
            }
        }
        return csv.toString();
    }

    /**
     * CSV文字列からString配列に変換します。
     * @param csv CSV形式の文字列
     * @return String配列
     */
    public static String[] getStringArrayFromCsv(String csv)
    {
        if (csv == null)
        {
            return null;
        }

        //		if(csv.indexOf("\"") == -1)
        //		{
        //			return csv.split(",");
        //		}

        List list = new ArrayList();
        int start = 0;
        int nextComma = 0;
        int quotCount = 0;

        while (true)
        {
            // １データの切り出し
            int index = start;
            while (true)
            {
                // 次の , 位置を取得
                nextComma = csv.indexOf(",", index);
                if (nextComma == -1)
                {
                    nextComma = csv.length();
                }

                // 次の , までの " の数をカウント
                for (int i = index; i < nextComma; i++)
                {
                    if (csv.charAt(i) == '\"')
                    {
                        quotCount++;
                    }
                }

                // "" 内の , であればスキップ
                if (quotCount % 2 != 0 && nextComma != csv.length())
                {
                    index = nextComma + 1;
                    continue;
                }

                break;
            }

            // リストに追加
            if (quotCount > 0)
            {
                list.add(csv.substring(start + 1, nextComma - 1).replaceAll("\"\"", "\""));
            }
            else
            {
                list.add(csv.substring(start, nextComma));
            }

            // 終了条件
            if (nextComma == csv.length())
            {
                break;
            }
            else
            {
                start = nextComma + 1;
                quotCount = 0;
            }
        }

        return (String[])list.toArray(new String[list.size()]);
    }

    /**
     * <jp>メッセージ番号(MessageResourceKey)からメッセージを取得します。 <br></jp>
     * <en>Return the message from message No. (MessageResourceKey). <br></en>
     * @param messageNo <jp>メッセージ番号 &nbsp;&nbsp;</jp><en>Message number &nbsp;&nbsp;</en>
     * @return <jp>メッセージ文字列 &nbsp;&nbsp;</jp><en>Message string &nbsp;&nbsp;</en>
     */
    public static String convertMsg(String messageNo)
    {
        String msgParam[] = messageNo.split(Constants.MSG_DELIM);
        String key = msgParam[0];
        String params[] = new String[msgParam.length];
        for (int i = 1; i < msgParam.length; i++)
        {
            params[i - 1] = msgParam[i];
        }

        String msg = MessageResources.getText(getLogginLocale(), key, params);
        return msg;
    }

    /**
     * <jp>ログ出力用のロケールを取得します。 <br></jp>
     * <en>Return the Locale class for loggging.<br></en>
     * @return Locale
     */
    public static Locale getLogginLocale()
    {
        String localString[] = EmProperties.getProperty(EmConstants.EMPARAMKEY_LOGGING_LANG).split("_");
        String country = "";
        String language = "";
        if (localString.length >= 2)
        {
            language = localString[0];
            country = localString[1];
        }
        else
        {
            language = localString[0];
        }

        Locale locale = new Locale(language, country);
        return locale;
    }

    /**
     * <jp <br></jp>
     * <en>Returns the date format for a given locale, defalut is japanese date format<br></en>
     * @param locale
     * @parma type
     * @return String
     */
    public static String getDateFormat(Locale locale, int type)
    {

        String dateFormat = "";
        try
        {
            // choose the format from daifuku commons based on locale value 
            Field field = Constants.class.getField("DATE_FORMAT_" + locale.getLanguage());
            String[] array = (String[])(field.get(null));
            dateFormat = array[type];
        }
        catch (Exception e)
        {
            // defalut format
            dateFormat = "yyyy/MM/dd HH:mm:ss";
        }

        return dateFormat;
    }

    /**
     * <jp <br></jp>
     * <en>Returns the Oracle ate format for a given locale, defalut is japanese date format<br></en>
     * @return String
     */
    public static String getOracleDateFormat(Locale locale, int type)
    {

        String dateFormat = "";
        try
        {
            // choose the format from daifuku commons based on locale value 
            Field field = EmConstants.class.getField("ORACLE_DATE_FORMAT_" + locale.getLanguage());
            String[] array = (String[])(field.get(null));
            dateFormat = array[type];
        }
        catch (Exception e)
        {
            // defalut format
            dateFormat = "yyyy/MM/dd HH24:mi:ss.ff3";
        }

        return dateFormat;
    }

    /**
     * <jp <br></jp>
     * <en>converts given Date object to given format<br></en>
     * @return String
     */
    public static String getOracleDate(Date date, String OracleDateformat)
    {
        SimpleDateFormat dateformat = new SimpleDateFormat(OracleDateformat);
        return dateformat.format(date);
    }

    /**
     * <jp <br></jp>
     * <en>converts given Date object to given format<br></en>
     * @return String
     */
    public static String getDateFormat(Date date, String format)
    {
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(date);
    }

    /**
     * <jp <br></jp>
     * <en>Returns the date format based on Locale with out special characters like : or /, defalut is Japanese date format<br></en>
     * @return String
     */
    public static String getDateFormat_NoSlah(Locale locale)
    {
        String country = locale.getCountry();

        String dateformat = null;
        if (EmConstants.LOCALE_US.equals(country))
        {
            dateformat = "MMddyyyyHHmmss";
        }
        else
        {
            dateformat = "yyyyMMddHHmmss";
        }
        return dateformat;
    }

    /**
     * <jp <br></jp>
     * <en>Returns the date format with our time  based on Locale, defalut is Japanese date format<br></en>
     * @return String
     */
    public static String getDateFormat_NoTime(Locale locale)
    {
        String country = locale.getCountry();

        String dateformat = null;
        if (EmConstants.LOCALE_US.equals(country))
        {
            dateformat = "MM/dd/yyyy";
        }
        else
        {
            dateformat = "yyyy/MM/dd";
        }
        return dateformat;
    }

    public static String getScreenName(String pageResourceKey)
    {
        String name = "";
        try
        {
            name = DispResources.getText(EManagerUtil.getLogginLocale(), pageResourceKey);
        }
        catch (MissingResourceException ex)
        {
            ex.printStackTrace();
        }
        return name;
    }

    /**
     * <jp <br></jp>
     * <en>Method to will take search date and time and form a list of search conditions<br></en>
     * @param startDate 開始日付
     * @param startTime 開始時刻
     * @param endDate 終了日付
     * @param endTime 終了時刻
     * @return List
     */
    public static List getSearchDates(Date startDate, Date startTime, Date endDate, Date endTime)
    {
    	// 入力された開始日時が終了日時より後の場合は入れ替えを行う
    	if (startDate != null && endDate != null)
    	{
	    	if(startDate.after(endDate) 
	    			|| startDate.equals(endDate) && startTime != null
					&& endTime != null && startTime.after(endTime))
	    	{
	    		Date temp = endDate;
	    		endDate = startDate;
	    		startDate = temp;
	    		temp = endTime;
	    		endTime = startTime;
	    		startTime = temp;
	    	}
    	}
    	
        Calendar calstartDate = Calendar.getInstance();
        Calendar calStartTime = Calendar.getInstance();
        Calendar calEndDate = Calendar.getInstance();
        Calendar calEndTime = Calendar.getInstance();

        if (startDate != null)
        {
            calstartDate.setTime(startDate);
        }
        if (startTime != null)
        {
            calStartTime.setTime(startTime);
        }
        if (endDate != null)
        {
            calEndDate.setTime(endDate);
        }
        if (endTime != null)
        {
            calEndTime.setTime(endTime);
        }
        calstartDate.set(Calendar.MILLISECOND, 000);
        calEndDate.set(Calendar.MILLISECOND, 999);

        List list = new ArrayList();

        if (startDate == null && startTime == null && endDate == null && endTime == null)
        {
            list.add(null);
            list.add(null);
        }
        else if (startDate != null && startTime == null && endDate == null && endTime == null)
        {
            calstartDate.set(calstartDate.get(Calendar.YEAR), calstartDate.get(Calendar.MONTH),
                    calstartDate.get(Calendar.DATE), 0, 0, 0);

            list.add(calstartDate.getTime());
            list.add(null);
        }
        else if (startDate != null && startTime != null && endDate == null && endTime == null)
        {
            calstartDate.set(calstartDate.get(Calendar.YEAR), calstartDate.get(Calendar.MONTH),
                    calstartDate.get(Calendar.DATE), calStartTime.get(Calendar.HOUR_OF_DAY),
                    calStartTime.get(Calendar.MINUTE), calStartTime.get(Calendar.SECOND));
            list.add(calstartDate.getTime());
            list.add(null);
        }
        else if (startDate != null && startTime != null && endDate != null && endTime == null)
        {
            calstartDate.set(calstartDate.get(Calendar.YEAR), calstartDate.get(Calendar.MONTH),
                    calstartDate.get(Calendar.DATE), calStartTime.get(Calendar.HOUR_OF_DAY),
                    calStartTime.get(Calendar.MINUTE), calStartTime.get(Calendar.SECOND));
            calEndDate.set(calEndDate.get(Calendar.YEAR), calEndDate.get(Calendar.MONTH),
                    calEndDate.get(Calendar.DATE), 23, 59, 59);

            list.add(calstartDate.getTime());
            list.add(calEndDate.getTime());
        }
        else if (startDate != null && startTime != null && endDate != null && endTime != null)
        {
            calstartDate.set(calstartDate.get(Calendar.YEAR), calstartDate.get(Calendar.MONTH),
                    calstartDate.get(Calendar.DATE), calStartTime.get(Calendar.HOUR_OF_DAY),
                    calStartTime.get(Calendar.MINUTE), calStartTime.get(Calendar.SECOND));
            calEndDate.set(calEndDate.get(Calendar.YEAR), calEndDate.get(Calendar.MONTH),
                    calEndDate.get(Calendar.DATE), calEndTime.get(Calendar.HOUR_OF_DAY),
                    calEndTime.get(Calendar.MINUTE), calEndTime.get(Calendar.SECOND));
            list.add(calstartDate.getTime());
            list.add(calEndDate.getTime());
        }
        else if (startDate != null && startTime == null && endDate != null && endTime == null)
        {
            calstartDate.set(calstartDate.get(Calendar.YEAR), calstartDate.get(Calendar.MONTH),
                    calstartDate.get(Calendar.DATE), 0, 0, 0);
            calEndDate.set(calEndDate.get(Calendar.YEAR), calEndDate.get(Calendar.MONTH),
                    calEndDate.get(Calendar.DATE), 23, 59, 59);

            list.add(calstartDate.getTime());
            list.add(calEndDate.getTime());
        }
        else if (startDate != null && startTime == null && endDate != null && endTime != null)
        {
            calstartDate.set(calstartDate.get(Calendar.YEAR), calstartDate.get(Calendar.MONTH),
                    calstartDate.get(Calendar.DATE), 0, 0, 0);
            calEndDate.set(calEndDate.get(Calendar.YEAR), calEndDate.get(Calendar.MONTH),
                    calEndDate.get(Calendar.DATE), calEndTime.get(Calendar.HOUR_OF_DAY),
                    calEndTime.get(Calendar.MINUTE), calEndTime.get(Calendar.SECOND));

            list.add(calstartDate.getTime());
            list.add(calEndDate.getTime());
        }
        else if (startDate == null && startTime == null && endDate != null && endTime == null)
        {
            calEndDate.set(calEndDate.get(Calendar.YEAR), calEndDate.get(Calendar.MONTH),
                    calEndDate.get(Calendar.DATE), 23, 59, 59);

            list.add(null);
            list.add(calEndDate.getTime());
        }
        else if (startDate == null && startTime == null && endDate != null && endTime != null)
        {
            calEndDate.set(calEndDate.get(Calendar.YEAR), calEndDate.get(Calendar.MONTH),
                    calEndDate.get(Calendar.DATE), calEndTime.get(Calendar.HOUR_OF_DAY),
                    calEndTime.get(Calendar.MINUTE), calEndTime.get(Calendar.SECOND));
            list.add(null);
            list.add(calEndDate.getTime());
        }

        return list;
    }

    /**
     * 2つの日付間の日数を取得します。
     * 比較するのは日付のみで、秒以下の数値は計算で使用しません。
     * 
     * @param from 開始日付
     * @param to 終了日付
     * @return 日数 (ex. from、toが同じ日付の場合は1を、toがfromの翌日の場合は2を返す)
     */
    public static int getBetweenDays(Calendar from, Calendar to)
    {
        // cloneを取得
        Calendar fromClone = (Calendar)from.clone();
        Calendar toClone = (Calendar)to.clone();

        // クリアする
        fromClone.clear();
        toClone.clear();

        // 年、月、日のみセットする
        fromClone.set(Calendar.YEAR, from.get(Calendar.YEAR));
        fromClone.set(Calendar.MONTH, from.get(Calendar.MONTH));
        fromClone.set(Calendar.DATE, from.get(Calendar.DATE));

        toClone.set(Calendar.YEAR, to.get(Calendar.YEAR));
        toClone.set(Calendar.MONTH, to.get(Calendar.MONTH));
        toClone.set(Calendar.DATE, to.get(Calendar.DATE));

        long diff = Math.abs(toClone.getTimeInMillis() - fromClone.getTimeInMillis());

        // 一日のミリ秒(24時間 * 60分 * 60秒 * ミリ秒)
        long oneDay = 24 * 60 * 60 * 1000;

        return (int)(diff / oneDay) + 1;
    }

    /**
     * 2つの日付間の日数を取得します。
     * 比較するのは日付のみで、秒以下の数値は計算で使用しません。
     * 
     * @param from 開始日付
     * @param to 終了日付
     * @return 日数 (ex. from、toが同じ日付の場合は1を、toがfromの翌日の場合は2を返す)
     */
    public static int getBetweenDays(Date from, Date to)
    {
        Calendar calFrom = Calendar.getInstance();
        Calendar calTo = Calendar.getInstance();

        calFrom.setTime(from);
        calTo.setTime(to);

        return getBetweenDays(calFrom, calTo);
    }

    /**
     * 検索条件に入力されたコードがLike用の検索方法かどうかを判定します。
     * 
     * @param code 検索条件のコード
     * @return Like用の検索方法かどうか
     */
    public static boolean isLikeSearch(String code)
    {
        if (Validator.isEmptyCheck(code))
        {
            return false;
        }
        return code.indexOf("*") != -1;
    }

    /**
     * 入力された検索条件をLike検索用に変換します。
     * 
     * @param str 入力された検索条件
     * @return Like検索用の文字列
     */
    public static String getLikeSearchString(String str)
    {
        return str == null ? ""
                          : str.replace('*', '%');
    }
}
