// $Id: LogMessage.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

/**<jp>
 * メッセージログの内容を保持するクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/05/23</TD><TD>sawa</TD><TD>メッセージ番号からファシリティ・コードを返すメソッド追加 (CVS v1.9から)</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class preserves the contents of message log.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/05/23</TD><TD>sawa</TD><TD>Add method to return facility codes from the message number(as of CVS v1.9)</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class LogMessage
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ファシリティ・コード(Debug)
     </jp>*/
    /**<en>
     * Facility code(Debug)
     </en>*/
    public static final String F_DEBUG = "0";

    /**<jp>
     * ファシリティ・コード(案内)
     </jp>*/
    /**<en>
     * Facility code (information)
     </en>*/
    public static final String F_INFO = "1";

    /**<jp>
     * ファシリティ・コード(注意)
     </jp>*/
    /**<en>
     * Facility code (notice)
     </en>*/
    public static final String F_NOTICE = "2";

    /**<jp>
     * ファシリティ・コード(警告)
     </jp>*/
    /**<en>
     * Facility code (warning)
     </en>*/
    public static final String F_WARN = "3";

    /**<jp>
     * ファシリティ・コード(異常)
     </jp>*/
    /**<en>
     * Facility code (error)
     </en>*/
    public static final String F_ERROR = "4";

    // Class variables -----------------------------------------------
    private Date wDate;

    private String wFacility;

    private String wClassInfo;

    private int wMessageNumber;

    private String wContents;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class,
     * @return version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    /**<jp>
     * メッセージ番号からファシリティ・コードを返します。<BR>
     * 範囲外のメッセージ番号のときはnullを返します。
     * @param msgnum  メッセージ番号
     * @return ファシリティ・コード
     </jp>*/
    /**<en>
     * Returns the facility code based on the message number.<BR>
     * Returns null if given message number is invalid.
     * @param msgnum  message number
     * @return facility code
     </en>*/
    public static String getFacility(int msgnum)
    {
        String no = String.valueOf(msgnum);
        String fac = no.substring(3, 4);
        if (fac.equals("0") || fac.equals("1"))
        {
            return (F_INFO);
        }
        else if (fac.equals("2") || fac.equals("3"))
        {
            return (F_NOTICE);
        }
        else if (fac.equals("4") || fac.equals("5"))
        {
            return (F_WARN);
        }
        else if (fac.equals("6") || fac.equals("7"))
        {
            return (F_ERROR);
        }
        else
        {
            return null;
        }
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * ログの詳細を指定して初期化します。
     * @param dt      ログが記録された日付
     * @param fac  ファシリティ・コード
     * @param ci  Class情報
     * @param msgnum  メッセージ番号
     * @param cont    メッセージ内容
     </jp>*/
    /**<en>
     * Specifies the log detail and initializes. 
     * @param dt      the date the log is recorded
     * @param fac  facility code
     * @param ci  class information
     * @param msgnum  message number
     * @param cont    contents of message
     </en>*/
    public LogMessage(Date dt, int msgnum, String fac, String ci, String cont)
    {
        wDate = dt;
        wMessageNumber = msgnum;
        wContents = cont;
        wFacility = fac;
        wClassInfo = ci;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * メッセージログの日付を取得します。
     * @return メッセージログの日付
     </jp>*/
    /**<en>
     * Gets the message log date.
     * @return message log date.
     </en>*/
    public Date getDate()
    {
        return (wDate);
    }

    /**<jp>
     * メッセージ番号を取得します。
     * @return メッセージ番号
     </jp>*/
    /**<en>
     * Gets the message number.
     * @return message number.
     </en>*/
    public int getMessageNumber()
    {
        return (wMessageNumber);
    }

    /**<jp>
     * ファシリティ・コードを取得します。
     * @return ファシリティ・コード
     </jp>*/
    /**<en>
     * Gets the facility code.
     * @return facility code.
     </en>*/
    public String getFacility()
    {
        return (wFacility);
    }

    /**<jp>
     * Class情報を取得します。
     * @return Class情報を取得します。
     </jp>*/
    /**<en>
     * Gets the Class information.
     * @return  Class information.
     </en>*/
    public String getClassInfo()
    {
        return (wClassInfo);
    }

    /**<jp>
     * メッセージの内容を取得します。
     * @return メッセージの内容
     </jp>*/
    /**<en>
     * Gets the contents of message.
     * @return contents of message.
     </en>*/
    public String getMessage()
    {
        return (wContents);
    }

    /**<jp>
     * 文字列表現を返します。
     * @return 文字列表現
     </jp>*/
    /**<en>
     * Returns the string representation.
     *  @return string representation.
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100);
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        buf.append("\nDate:" + frmt.format(wDate));
        buf.append("\nFacility:" + wFacility);
        buf.append("\nClassInfo:" + wClassInfo);
        buf.append("\nMessageNumber:" + String.valueOf(wMessageNumber));
        buf.append("\nContents:" + wContents);

        return String.valueOf(buf);
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

