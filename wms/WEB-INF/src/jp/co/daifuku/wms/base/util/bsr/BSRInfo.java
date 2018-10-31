//$Id: BSRInfo.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.bsr;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * BSR情報のエンティティ・クラスです。
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

/**
 * @author kato
 *
 */
public class BSRInfo
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 日付フォーマット文字列 */
    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";

    /** 区切り文字列 */
    static final String LOG_DELIMITER = "#-----BATCH_STATUS_REPORT";

    /** 改行コード */
    static final String LOG_CRLF = "\n";

    /** ファシリティコード(正常完了) */
    public static final int NORMAL_COMPLETED = 0;

    /** ファシリティコード(処理中) */
    public static final int BEING_PROCESSED = 1;

    /** ファシリティコード(警告完了) */
    public static final int WARNINGLY_TERMINATED = 2;

    /** ファシリティコード(異常完了) */
    public static final int ABNORMAL_TERMINATED = 3;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private String _category; // カテゴリーID

    private int _facility; // 結果区分

    private int _status; // ステータスコード

    private String _description; // 詳細メッセージ

    private String _className; // 呼び出し元クラス情報

    private Date _logDate; // 発生日時

    private int _listIndex; // ログファイル内でのインデックス番号

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 呼び出し元と例外を指定してインスタンスを生成します。
     * @param category カテゴリーID
     * @param facility ファシリティコード
     * @param status ステータスコード
     * @param description 詳細メッセージ
     * @param caller 呼び出し元クラス名
     */
    public BSRInfo(String category, int facility, int status, String description, String caller)
    {
        setCategory(category);
        setFacility(facility);
        setStatus(status);
        setDescription(description);
        setClassName(caller);
        setLogDate(new Date());
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return ステータスファイル名を返します。
     */
    public String getStatusFileName()
    {
        return getCategory() + getWeekNo(getLogDate()) + BSRFile.STS_SUFFIX;
    }

    /**
     * @return ステータスファイル用レコードを返します。
     */
    public String getStatusRecord()
    {
        return String.valueOf(getFacility());
    }

    /**
     * @return ログファイル名を返します。
     */
    public String getLogFileName()
    {
        return getCategory() + getWeekNo(getLogDate()) + BSRFile.LOG_SUFFIX;
    }

    /**
     * @return ログファイル用レコードを返します。
     */
    public String getLogRecord()
    {
        String str = "";

        SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
        String dateStr = fmt.format(getLogDate());
        str += dateStr + LOG_CRLF;

        str += getClassName() + LOG_CRLF;

        str += getFacility() + LOG_CRLF;

        str += getStatus() + LOG_CRLF;

        str += getDescription() + LOG_CRLF;

        str += LOG_DELIMITER + LOG_CRLF;

        return str;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * @return categoryを返します。
     */
    public String getCategory()
    {
        return _category;
    }

    /**
     * @param category categoryを設定します。
     */
    public void setCategory(String category)
    {
        _category = category;
    }

    /**
     * @return facilityを返します。
     */
    public int getFacility()
    {
        return _facility;
    }

    /**
     * @param facility facilityを設定します。
     */
    public void setFacility(int facility)
    {
        _facility = facility;
    }

    /**
     * @return statusを返します。
     */
    public int getStatus()
    {
        return _status;
    }

    /**
     * @param status statusを設定します。
     */
    public void setStatus(int status)
    {
        _status = status;
    }

    /**
     * @return descriptionを返します。
     */
    public String getDescription()
    {
        return _description;
    }

    /**
     * @param description descriptionを設定します。
     */
    public void setDescription(String description)
    {
        _description = description;
    }

    /**
     * @return classNameを返します。
     */
    public String getClassName()
    {
        return _className;
    }

    /**
     * @param className classNameを設定します。
     */
    public void setClassName(String className)
    {
        _className = className;
    }

    /**
     * @return logDateを返します。
     */
    public Date getLogDate()
    {
        return _logDate;
    }

    /**
     * @param logDate logDateを設定します。
     */
    public void setLogDate(Date logDate)
    {
        _logDate = logDate;
    }

    /**
     * @param logDate logDateを設定します。
     */
    public void setLogDate(String logDate)
    {
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
        Date dt = null;
        try
        {
            dt = fmt.parse(logDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        _logDate = dt;
    }

    /**
     * @return インデックス番号を返します。
     */
    public int getIndex()
    {
        return _listIndex;
    }

    /**
     * @param index インデックス番号を設定します。
     */
    public void setIndex(int index)
    {
        _listIndex = index;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 与えた日時の週番号を2桁の数値文字列で返します。
     * @param dt ファイル名に使用される日付
     * @return 週番号文字列
     */
    private String getWeekNo(Date dt)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        long weekNo = cal.get(Calendar.WEEK_OF_YEAR);
        DecimalFormat fmt = new DecimalFormat("00");
        return fmt.format(weekNo);
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
        return "$Id: BSRInfo.java 87 2008-10-04 03:07:38Z admin $";
    }
}
