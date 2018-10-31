//$Id: Parameter.java 87 2008-10-04 03:07:38Z admin $

package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**<jp>
 * パラメータを規定するためのインターフェースです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is the interface that regulates the parameters.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Parameter 
        extends Object
{

	// Class fields --------------------------------------------------
	/**<jp>
	 * ユーザ名
	 </jp>*/
	/**<en>
	 * User's name
	 </en>*/
	private String _UserName;

	/**<jp>
	 * 端末No
	 </jp>*/
	/**<en>
	 * Terminal number
	 </en>*/
    private String _TerminalNumber;

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**<jp>
	 * ユーザ名を設定します。
	 * @param name ユーザ名
	 </jp>*/
	/**<en>
	 * Sets the user's name
	 * @param name User's name
	 </en>*/
	public void setUserName(String name)
	{
		_UserName = name ;
	}

	/**<jp>
	 * ユーザ名を取得します。
	 * @return ユーザ名
	 </jp>*/
	/**<en>
	 * Retrieves the user's name.
	 * @return User's name
	 </en>*/
	public String getUserName()
	{
		return _UserName ;
	}

	/**<jp>
	 * 端末Noを設定します。
	 * @param terminalno 端末No
	 </jp>*/
	/**<en>
	 * Sets the terminal number.
	 * @param terminalno terminal number
	 </en>*/
	public void setTerminalNumber(String terminalno)
	{
		_TerminalNumber = terminalno ;
	}

	/**<jp>
	 * 端末Noを取得します。
	 * @return 端末No
	 </jp>*/
	/**<en>
	 * Retrieves the terminal number.
	 * @return terminal number
	 </en>*/
	public String getTerminalNumber()
	{
		return _TerminalNumber ;
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

