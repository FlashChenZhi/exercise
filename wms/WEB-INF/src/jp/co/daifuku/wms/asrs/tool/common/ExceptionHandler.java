// $Id: ExceptionHandler.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.common;

import java.sql.SQLException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;

/** <jp>
 * ビジネスロジッククラスで受け取った例外を処理するクラスです。
 * <table border="1" cellpadding="3" cellspacing="0"><tr><td>
 * <pre>
 * XXXBusiness.javaの例外処理の例
 * public void btn_Add_Click(ActionEvent e) throws Exception
 * {
 *   Connection conn = null;
 *   try
 *   {
 *     // 業務処理
 *   }
 *   <font color="blue">
 *   // もしこの画面で特有な例外がスローされる場合はここでcatchする
 *   catch(UnsupportEncodingException uee)
 *   {
 *     // ハンドリング
 *   }
 *   </font>
 *   catch(Exception ex)
 *   {
 *     <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));</font>
 *   }
 *   finally
 *   {
 *     try
 *     {
 *       // コネクションクローズ
 *       if(conn != null) conn.close();
 *     }
 *     catch(SQLException se)
 *     {
 *       <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));</font>
 *     }
 *   }
 * }
 * </pre>
 * </td></tr></table>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/15</TD><TD>N.Sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp> */
/** <en>
 * This class deals with the exception that business logic class is thrown.
 * <table border="1" cellpadding="3" cellspacing="0"><tr><td>
 * <pre>
 * example Exception handling in XXXBusiness.java
 * public void btn_Add_Click(ActionEvent e) throws Exception
 * {
 *   Connection conn = null;
 *   try
 *   {
 *     // business logic 
 *   }
 *   <font color="blue">
 *   // It gets a special exception here. 
 *   catch(UnsupportEncodingException uee)
 *   {
 *     // handling
 *   }
 *   </font>
 *   catch(Exception e)
 *   {
 *     <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));</font>
 *   }
 *   finally
 *   {
 *     try
 *     {
 *       // close the database connection
 *       if(conn != null) conn.close();
 *     }
 *     catch(SQLException se)
 *     {
 *       <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));</font>
 *     }
 *   }
 * }
 * </pre>
 * </td></tr></table>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/15</TD><TD>N.Sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en> */
public class ExceptionHandler extends Object
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /** <jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp> */
    /** <en>
     * The version of this class is returned.
     * @return A version and a date
     </en> */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    /** <jp>
     * 受け取った例外の型に応じたメッセージ番号を返します。<br>
     * 以下の例外の場合メッセージを返します。<br>
     * <code>ScheduleException</code>, <code>ReadWriteException</code>, <code>SQLException</code><br>
     * それ以外は受け取った例外を再スローします。<br>
     * その際、<code>ValidateException</code>の場合はエラーメッセージが表示され、
     * <code>ValidateException</code>以外の例外はエラーページが表示されます。
     * <table border="1" cellpadding="3" cellspacing="0">
     * <tr><td>例外クラス        </td><td>メッセージ番号</td></tr>
     * <tr><td>ScheduleException </td><td>6017011</td></tr>
     * <tr><td>ReadWriteException</td><td>6017001</td></tr>
     * <tr><td>SQLException      </td><td>6017001</td></tr>
     * </table>
     * @param e  Exception
     * @param c  ビジネスロジックオブジェクト
     * @return   画面に表示するメッセージのメッセージ番号
     * @throws   ScheduleException,ReadWriteException,SQLException以外の例外  
     </jp>*/
    /** <en>
     * Return the Message number.<br>
     * The exception of the object <br>
     * <code>ScheduleException</code>, <code>ReadWriteException</code>, <code>SQLException</code><br>
     * Throw the exception received except for that.<br>
     * When <code>ValidateException</code> is caught, a message is indicated in the message area. 
     * An error page is indicated in the exception except for <code>ValidateException</code>.
     * <table border="1" cellpadding="3" cellspacing="0">
     * <tr><td>Exception class   </td><td>return the message number</td></tr>
     * <tr><td>ScheduleException </td><td>6017011</td></tr>
     * <tr><td>ReadWriteException</td><td>6017001</td></tr>
     * <tr><td>SQLException      </td><td>6017001</td></tr>
     * </table>
     * @param e  Exception
     * @param c  business logic object
     * @return   A number of message to indicate on the screen 
     * @throws Exception an exception except for ScheduleException, ReadWriteException and SQLException 
     </en>*/
    public static String getDisplayMessage(Exception e, Object c) throws Exception
    {
        String message = null;
        if (e instanceof ScheduleException)
        {
            message = "6127006";
        }
        else if (e instanceof ReadWriteException)
        {
            message = "6127005";
        }
        else if (e instanceof SQLException)
        {
            message = "6127005";
        }
        else
        {
            throw e;
        }
        return message;
    }

    // Constructors --------------------------------------------------
    /** <jp>
     * 使用されません。
     </jp>*/
    /** <en>
     * can not use.
     </en>*/
    private ExceptionHandler()
    {
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
