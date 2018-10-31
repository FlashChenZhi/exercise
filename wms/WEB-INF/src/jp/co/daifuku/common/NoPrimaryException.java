// $Id: NoPrimaryException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * 定義情報が異常な場合に使用する例外です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This exception is used when abnormal definitions are made.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class NoPrimaryException extends CommonException
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returns the version of this class.
	 * @return version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
	}

	// Constructors --------------------------------------------------
	/**<jp>
	 * 詳細メッセージを指定しないで Exception を構築します
	 </jp>*/
	/**<en>
	 * Constructs the Exception without specifying the detail message.
	 </en>*/
	public NoPrimaryException()
	{
		super() ;
	}

	/**<jp>
	 * メッセージ付きの例外を作成します。
	 * @param msg  詳細メッセージ
	 </jp>*/
	/**<en>
	 * Creates exception with message attached.
	 * @param msg  detail message
	 </en>*/
	public NoPrimaryException(String msg)
	{
		super(msg) ;
	}
}
//end of class

