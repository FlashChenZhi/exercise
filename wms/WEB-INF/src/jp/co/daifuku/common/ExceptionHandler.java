// $Id: ExceptionHandler.java 6939 2010-01-28 08:48:54Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.common;

import java.io.IOException;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.MessageFormatter;

/** 
 * Designer :  K.Mori<BR>
 * Maker :     K.Mori<BR>
 * ビジネスロジッククラスで受け取った例外を処理するクラスです。
 * 当クラスは、jp.co.daifuku.wms.base.displayパッケージにあったクラスを
 * jp.co.daifuku.commonパッケージに移動させたクラスです。
 * jp.co.daifuku.wms.base.displayパッケージのExceptionHandlerは将来的には当クラスに置き換える予定です。
 * 
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
 *   catch(UnsupportEncodingException e)
 *   {
 *     // ハンドリング
 *   }
 *   </font>
 *   catch(Exception e)
 *   {
 *     <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(e), this);</font>
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
 *       <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(e), this);</font>
 *     }
 *   }
 * }
 * </pre>
 * </td></tr></table>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/15</TD><TD>N.Sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2008/11/12</TD><TD>K.Mori</TD><TD>ORA-01555のハンドリング処理を追加</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 6939 $, $Date:: 2010-01-28 17:48:54 +0900#$
 * @author  $Author: shibamoto $
 */
public class ExceptionHandler
        extends Object
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
	/**
	 * ORA-1555（スナップショットが古すぎます）のエラー定義
	 */
	private static final int $ORACLE_SNAPSHOT_ERROR = 1555;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /** 
     * 本クラスは定義情報を管理するクラスなのでインスタンスの生成は行えません。
     */
    private ExceptionHandler()
    {
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


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


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /** 
     * 受け取った例外の型に応じたメッセージ番号を返します。<br>
     * <code>ReadWriteException</code>, <code>SQLException</code><br>はログを落とします。
     * それ以外の<code>CommonException</code>はStackTrace付きでログを落とします。
     * <code>ValidateException</code>はBlueDog Framework側で処理するのでそのままthrowします。
     * @param e  Exception
     * @param c  可変Javaオブジェクト
     * @return   画面に表示するメッセージのメッセージ番号
     * @throws   ValidateException  BlueDog入力チェック例外
     */
    public static String getDisplayMessage(Exception e, Object c)
            throws ValidateException
    {
        String message = null;

        // ダイフク共通例外であるか確認
        if (e instanceof CommonException)
        {
            // データベース、ファイルI/O例外であるか確認
            if (e instanceof ReadWriteException)
            {
                if ((Exception)e.getCause() instanceof SQLException)
                {
                    SQLException se = (SQLException)e.getCause();
                    System.out.println("TraceHandler SQLException Code = [" + se.getErrorCode() + "]");
                    System.out.println("TraceHandler SQLException State = [" + se.getSQLState() + "]");
                    System.out.println("TraceHandler SQLException Message = [" + se.getMessage() + "]");
                    // エラーコードがORA-1555の場合、エラーメッセージは出さずに注意メッセージを表示する。
                    // v3.3より親画面でページャー表示を行うため、結果セットの保持期間が長くなる事への対策。
                    if (se.getErrorCode() == $ORACLE_SNAPSHOT_ERROR)
                    {
                    	// ORA-1555 (スナップショットが古すぎます)の場合は注意メッセージの表示のみ行う。
	                    // データの内容が更新されたため、処理を中断しました。再度処理を実行してください。
	                    message = "6003109";
                    }
                    else
                    {
	                    // データベースエラーが発生しました。エラーコード = {0}
	                    // RmiMsgLogClient.write(WmsMessageFormatter.format(6006022, se.getErrorCode()), c.getClass().getName());
	                    String msg = MessageFormatter.format(6006022, new String[] {
	                        String.valueOf(se.getErrorCode())
	                    });
	                    RmiMsgLogClient.write(msg, c.getClass().getName());
	                    // データベースエラーが発生しました。ログを参照してください。
	                    message = "6007002";
                    }
                }
                else
                {
                    System.out.println("TraceHandler ReadWriteException");
                    // 6007012=外部ファイルアクセスエラーが発生しました。ログを参照してください。
                    message = "6007012";
                }
            }
            else if(e instanceof LocationFormatException)
            {
            	message = "6003103" + MessageResource.DELIM + DisplayText.getText("LBL-W0138");
            }
            else
            {
                System.out.println("TraceHandler CommonException");
                // ダイフク共通例外の場合、本来はスケジュールでハンドリングするが
                // ExceptionHandlerで受け取った場合はStackTraceをログに出力する。
                // スケジュール処理中に予期しない例外が発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006021, e), c.getClass().getName());
                // スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください
                message = "6007001";
            }
        }
        else if (e instanceof SQLException)
        {
            SQLException se = (SQLException)e;
            System.out.println("TraceHandler SQLException by business Code = [" + se.getErrorCode() + "]");
            System.out.println("TraceHandler SQLException by business State = [" + se.getSQLState() + "]");
            System.out.println("TraceHandler SQLException by business Message = [" + se.getMessage() + "]");
            // エラーコードがORA-1555の場合、エラーメッセージは出さずに注意メッセージを表示する。
            // v3.3より親画面でページャー表示を行うため、結果セットの保持期間が長くなる事への対策。
            if (se.getErrorCode() == $ORACLE_SNAPSHOT_ERROR)
            {
            	// ORA-1555 (スナップショットが古すぎます)の場合は注意メッセージの表示のみ行う。
                // データの内容が更新されたため、処理を中断しました。再度処理を実行してください。
                message = "6003109";
            }
            else
            {
	            // データベースエラーが発生しました。エラーコード = {0}
	            //RmiMsgLogClient.write(WmsMessageFormatter.format(6006022, se.getErrorCode()), c.getClass().getName());
	            String msg = MessageFormatter.format(6006022, new String[] {
	                String.valueOf(se.getErrorCode())
	            });
	            RmiMsgLogClient.write(msg, c.getClass().getName());
	            // データベースエラーが発生しました。ログを参照してください。
	            message = "6007002";
            }
        }
        else if (e instanceof ValidateException)
        {
            // 必須入力チェックがNGだった場合の処理です。
            // メッセージのセットはフレームワークで行うため、ここではセットしません。
            throw (ValidateException)e;
        }
        else if (e instanceof IOException)
        {
            // XLS、CSV出力でIO例外が発生した場合に通知されます。
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            message = "6007031";
            // 6006020=ファイルの入出力エラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006020, e), c.getClass().getName());
        }
        else
        {
            System.out.println("TraceHandler RuntimeException");
            // RuntimeException処理
            // 6006001=予期しないエラーが発生しました。{0}
            // スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください
            RmiMsgLogClient.write(new TraceHandler(6006001, e), c.getClass().getName());
            message = "6007001";
        }
        return message;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: ExceptionHandler.java 6939 2010-01-28 08:48:54Z shibamoto $";
    }
}
//end of class
