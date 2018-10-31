// $Id: EmDate.java 3965 2009-04-06 02:55:05Z admin $
package jp.co.daifuku.emanager.util;

import java.util.Calendar;
import java.util.Date;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 日付操作に関する処理を行うクラスです。
 *
 *
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  Last commit: $Author: admin $
 */
public class EmDate
        extends Date
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar 

    /**
     * 保持する日付
     */
    private Date _date = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * 指定された日付で当クラスを初期化します。
     * 
     * @param date 日付
     */
    public EmDate(Date date)
    {
        super(date.getTime());
        _date = date;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

// 2008/12/10 K.Matsuda Start コメント修正
    /**
     * 日付データのみで比較します。
     * 
     * @param when 比較対象
     * @return この日付が比較対象の日付より後の場合はtrue、それ以外はfalse
     */
    public boolean afterDate(Date when)
    {
        return getDateOnly(_date).after(getDateOnly(when));
    }

    /**
     * 日付データのみで比較します。
     * 
     * @param when 比較対象
     * @return この日付が比較対象の日付より前の場合はtrue、それ以外はfalse
     */
    public boolean beforeDate(Date when)
    {
        return getDateOnly(_date).before(getDateOnly(when));
    }
// 2008/12/10 K.Matsuda End
    
    /**
     * 日付データのみで比較します。
     * 
     * @param when 比較対象
     * @return 等しい日付の場合はtrue
     */
    public boolean equalsDate(Date when)
    {
        return getDateOnly(_date).equals(getDateOnly(when));
    }

    /**
     * 保持している日付の日で最小の時刻(00:00:00.000)がセットされた日付を取得します。
     * 
     * @return 最小の時刻(00:00:00)がセットされた日付
     */
    public Date getFirstTimeOfDate()
    {
        return getDateOnly(_date);
    }

    /**
     * 保持している日付の日で最大の時刻(23:59:59.999)がセットされた日付を取得します。
     * 
     * @return 最小の時刻(00:00:00)がセットされた日付
     */
    public Date getLastTimeOfDate()
    {
        Calendar result = Calendar.getInstance();
        // 日付データのみ取得
        result.setTime(getDateOnly(_date));

        // 時間、分、秒、ミリ秒をセット
        result.set(Calendar.HOUR_OF_DAY, 23);
        result.set(Calendar.MINUTE, 59);
        result.set(Calendar.SECOND, 59);
        result.set(Calendar.MILLISECOND, 999);

        return result.getTime();
    }

    /**
     * 保持している日付の翌日を取得します。
     * 
     * @return 翌日
     */
    public Date nextDate()
    {
        Calendar nextDay = Calendar.getInstance();
        nextDay.setTime(_date);
        nextDay.add(Calendar.DATE, 1);
        return nextDay.getTime();
    }

    /**
     * 日付の演算を行います。
     * 
     * @param field フィールド
     * @param amount フィールドに追加される日付または時刻の量
     */
    public void add(int field, int amount)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(_date);
        cal.add(field, amount);
        _date = cal.getTime();
        super.setTime(_date.getTime());
    }

    /**
     * 月の最終日かどうかを判定します。
     * 
     * @return 最終日の場合true、それ以外はfalse
     */
    public boolean isLastDateOfMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(_date);
        // 現在の日付が最終日かどうかをチェック
        return cal.getActualMaximum(Calendar.DATE) == cal.get(Calendar.DATE);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * 指定された日付から時刻データをクリアし日付データのみを取得します。
     * 
     * @param tgt 日付
     */
    private Date getDateOnly(Date tgt)
    {
        Calendar result = Calendar.getInstance();
        // カレンダーに一度変換する
        result.setTime(tgt);

        Calendar clone = (Calendar)result.clone();

        // 一度クリアする
        result.clear();

        // 年月日をセットする
        result.set(Calendar.YEAR, clone.get(Calendar.YEAR));
        result.set(Calendar.MONTH, clone.get(Calendar.MONTH));
        result.set(Calendar.DATE, clone.get(Calendar.DATE));

        return result.getTime();
    }


    /**
     * 文字列表現を取得します。
     * 
     */
    public String toString()
    {
        return _date.toString();
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: EmDate.java 3965 2009-04-06 02:55:05Z admin $";
    }
}
