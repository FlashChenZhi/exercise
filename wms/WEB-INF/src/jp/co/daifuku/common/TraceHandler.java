// $Id: TraceHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter ;
import java.io.StringWriter ;
import java.util.MissingResourceException ;

/**<jp>
 * 例外発生時のprintStackTraceのログ書込み編集処理を簡略化するためのクラスです。 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/05/31</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class simplifies the process of editing written log describing exception cases concerning printStackTrace. *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/05/31</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class TraceHandler
        extends Object
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**<jp>
	 * デフォルトメッセージ番号
	 </jp>*/
	/**<en>* Default message number </en>*/
	private int _DefaultMessageNum = 0 ;

	/**<jp>
	 * 通知された<CODE>Exception</CODE>
	 </jp>*/
	/**<en>* Notified<CODE>Exception</CODE> </en>*/
	private Exception p_Exception = null ;

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returns the version of this class
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
	}

	/**<jp>
	 * 画面やアプリケーションに<B>使われる側</B>は例外をキャッチした時、
	 * このメソッドを使用してログに落とすprintStackTrace文字列を取得して下さい。<BR>
	 * 例外発生時のprintStackTraceを文字列に編集します。<BR>
	 * <PRE>
	 * 使用する側の例
	 * catch(SQLException e)
	 * {
	 *     // エラーログの出力処理を行う
	 *     Object[] tObj = new Object[1] ;
	 *     tObj[1] = TraceHandler.getStackTrace(e) ;
	 *     RmiMsgLogClient.write( 6025038, this.getClass().getName(), tObj ) ;
	 *     // ReadWriteExceptionをエラーメッセージ付きでスローする
	 *     throw new ReadWriteException("6025038" + wDelim + "Item") ;
	 * }
	 * </PRE>
	 * @param  e 通知された<CODE>Exception</CODE>
	 * @return 文字列に編集したprintStackTrace
	 </jp>*/
	/**<en>
	 * Use this method to get string printStackTrace to record log.<BR>
	 * It edits string from translates printStackTrace when exceptional occurs.<BR>
	 * <PRE>
	 * Example: how to use this method
	 * catch(SQLException e)
	 * {
	 *     // Processing the output of error log
	 *     Object[] tObj = new Object[1] ;
	 *     tObj[1] = TraceHandler.getStackTrace(e) ;
	 *     RmiMsgLogClient.write( 6025038, this.getClass().getName(), tObj ) ;
	 *     // Throwing ReadWriteException with error message attached
	 *     throw new ReadWriteException("6025038" + wDelim + "Item") ;
	 * }
	 * </PRE>
	 * @param  e notified<CODE>Exception</CODE>
	 * @return string edited from StackTracetranslated
	 </en>*/
	public static String getStackTrace(Exception e)
	{
		TraceHandler tracehandler = new TraceHandler(e) ;
		return (tracehandler.getStackTrace()) ;
	}

	// Constructors --------------------------------------------------
	// public Constructors -------------------------------------------
	/**<jp>
	 * 画面が使用するコンストラクタです。<BR>
	 * <CODE>Exception</CODE>にセットされたメッセージがMessageDefに定義されたものではない場合が考えられますので、
	 * ログに落とすデフォルトメッセージ番号をセットして下さい。<BR>
	 * <PRE>
	 * 使用する側の例
	 * catch(Exception e)
	 * {
	 *     // エラーログの出力処理を行う
	 *     RmiMsgLogClient.write( new TraceHandler(msgnum, e), "PBStorage.jsp" ) ;
	 * }
	 * </PRE>
	 * @param defaultmsgnum ログに落とすデフォルトメッセージ番号
	 * @param e             通知された<CODE>Exception</CODE>
	 </jp>*/
	/**<en>
	 * This constructor is for the use by display.<BR>
	 * Set default message number to record in log, as there may be the case that message set in
	 * <CODE>Exception</CODE> is not of the definition by MessageDef.
	 * Set hte default message number to record in log.<BR>
	 * <PRE>
	 * Example:how to use this method
	 * catch(Exception e)
	 * {
	 *     // Processing the output of error log
	 *     RmiMsgLogClient.write( new TraceHandler(msgnum, e), "PBStorage.jsp" ) ;
	 * }
	 * </PRE>
	 * @param defaultmsgnum Default message number to record in log
	 * @param e             Notified<CODE>Exception</CODE>
	 </en>*/
	public TraceHandler(int defaultmsgnum, Exception e)
	{
		_DefaultMessageNum = defaultmsgnum ;
		p_Exception = e ;
	}

	// private Constructors ------------------------------------------
	/**<jp>
	 * 通知された<CODE>Exception</CODE>をセットしStackTraceを文字列に編集します。
	 * このコンストラクタは外部から呼ばれることはありません。
	 * @param e 通知された<CODE>Exception</CODE>
	 </jp>*/
	/**<en>
	 * Sets the notified <CODE>Exception</CODE> and edit string from StackTrace.
	 * There may be no case that this constructor be called to exterior.
	 * @param e Notified <CODE>Exception</CODE>
	 </en>*/
	private TraceHandler(Exception e)
	{
		p_Exception = e ;
	}

	// Public methods ------------------------------------------------
	/**<jp>
	 * 通知された<CODE>Exception</CODE>にセットされたメッセージからメッセージ番号を取得します。<BR>
	 * メッセージにメッセージ番号がセットされていない場合はデフォルトメッセージ番号を返します。
	 * @return メッセージ番号
	 </jp>*/
	/**<en>
	 * Gets message number from the message set in notified <CODE>Exception</CODE><BR>
	 * It returns default message number if there is no message number set for the message.
	 * @return Message number
	 </en>*/
	public int getMessageNumber()
	{
		//		String msg = p_Exception.getMessage() ;
		//		int msgnum = 0;
		//		try
		//		{
		//			if (msg.indexOf(MessageResource.DELIM) > 0)
		//			{
		//				StringTokenizer stk = new StringTokenizer(msg, MessageResource.DELIM, false) ;
		//				//<jp> メッセージ番号</jp>
		//				//<en> Message number</en>
		//     			msgnum = Integer.parseInt( stk.nextToken() ) ;
		//			}
		//			else
		//			{
		//				//<jp> メッセージ番号</jp>
		//				//<en> Message number</en>
		//				msgnum = Integer.parseInt( msg ) ;
		//			}
		//		}
		//		catch (Exception e)
		//		{
		//			//<jp> デフォルトメッセージ番号</jp>
		//			//<en> Default Message number</en>
		//			msgnum = wDefaultMessageNum ;
		//		}

		//		return msgnum ;
		return _DefaultMessageNum ;
	}

	/**
	 * @return exceptionを返します。
	 */
	public Exception getException()
	{
		return p_Exception ;
	}

	/**
	 * @param exception exceptionを設定します。
	 */
	public void setException(Exception exception)
	{
		p_Exception = exception ;
	}

	/**<jp>
	 * 文字列に編集したprintStackTraceを返します。
	 * @return 文字列に編集したprintStackTrace
	 </jp>*/
	/**<en>
	 * Returns the string edited from printStackTrace.
	 * @return String edited from printStackTrace
	 </en>*/
	public String getStackTrace()
	{
		String comment = "" ;
		try
		{
			comment = CommonParam.getParam("STACKTRACE_COMMENT") ;
		}
		catch (MissingResourceException e)
		{
            e.printStackTrace();
		}
		StringWriter sw = new StringWriter() ;
		p_Exception.printStackTrace(new PrintWriter(sw)) ;
		return (comment + String.valueOf(sw)) ;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

