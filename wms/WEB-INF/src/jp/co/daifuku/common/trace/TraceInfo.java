//$Id: TraceInfo.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common.trace ;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.Serializable ;
import java.lang.reflect.Method ;
import java.util.Date ;


/**
 * スタックトレースをリモートに送信するためのエンティティ・クラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/07/13</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class TraceInfo
		implements Serializable
{
	//------------------------------------------------------------
	// class variables (prefix '$')
	//------------------------------------------------------------
	//	private String	$classVar ;

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	//	public static final int FIELD_VALUE = 1 ;

	/** <code>serialVersionUID</code>のコメント */
	private static final long serialVersionUID = -3115231679197187331L ;

	//------------------------------------------------------------
	// properties (prefix 'p_')
	//------------------------------------------------------------
	private Exception p_exception ;

	private String p_className ;

	private String p_revision ;

	private Date p_logDate ;

	//------------------------------------------------------------
	// instance variables (prefix '_')
	//------------------------------------------------------------
	//	private String	_instanceVar ;

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * 呼び出し元と例外を指定してインスタンスを生成します。
	 * @param caller 呼び出しもとクラス名
	 * @param e 例外
	 */
	public TraceInfo(Exception e, String caller)
	{
		setClassName(caller) ;
		setException(e) ;
		setLogDate(new Date()) ;
	}

	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------

	/**
	 * @return classNameを返します。
	 */
	public String getClassName()
	{
		return p_className ;
	}

	/**
	 * @param className classNameを設定します。
	 */
	public void setClassName(String className)
	{
		p_className = className ;
        
        /**
         * 2008.05.28
         * WebとRFTでクラス名称の渡し方が異なり
         * SimpleNameでクラス名を渡しているRFTで
         * Exceptionが発生するため、コメントアウトした
         */
		//p_revision = getClassRevision(className) ;
	}

	/**
	 * @return exceptionを返します。
	 */
	public Exception getException()
	{
		return p_exception ;
	}

	/**
	 * @param exception exceptionを設定します。
	 */
	public void setException(Exception exception)
	{
		p_exception = exception ;
	}

	/**
	 * @return logDateを返します。
	 */
	public Date getLogDate()
	{
		return p_logDate ;
	}

	/**
	 * @param logDate logDateを設定します。
	 */
	public void setLogDate(Date logDate)
	{
		p_logDate = logDate ;
	}

	/**
	 * @return revisionを返します。
	 */
	public String getRevision()
	{
		return p_revision ;
	}

	//------------------------------------------------------------
	// package methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// protected methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------

	protected static String getClassRevision(String caller)
	{
		String rev = "NONE" ;
		try
		{
			Class callClass = Class.forName(caller) ;
			Method gvMethod = callClass.getMethod("getVersion", null) ;
			Object tmprev = gvMethod.invoke(null, null) ;
			rev = tmprev.toString() ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
		return rev ;
	}


	//------------------------------------------------------------
	// private methods
	//------------------------------------------------------------


	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
	/**
	 * このクラスのリビジョンを返します。
	 * @return リビジョン文字列。
	 */
	public static String getVersion()
	{
		return "$Id: TraceInfo.java 87 2008-10-04 03:07:38Z admin $" ;
	}
}
