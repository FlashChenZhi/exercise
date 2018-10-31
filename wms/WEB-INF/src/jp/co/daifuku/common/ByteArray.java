// $Id: ByteArray.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * バイト配列のラッパクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is the wrapper class of byte array.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ByteArray extends Object
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**<jp>
	 * 内部バッファ
	 </jp>*/
	/**<en>
	 * Internal buffer
	 </en>*/
	private byte[] wByteBuffer ;

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returns the version of this class,
     * @return the version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
	}

	// Constructors --------------------------------------------------
	/**<jp>
	 * 保持するバイト配列を指定して、インスタンスを生成ます。
	 * @param ba  設定するバイト配列
	 </jp>*/
	/**<en>
	 * Specifies the byte array to preserve then generates the instance. 
	 * @param ba Byte array to be set
	 </en>*/
	public ByteArray(byte[] ba)
	{
		setBytes(ba) ;
	}

	// Public methods ------------------------------------------------
	/**<jp>
	 * バイト配列を設定します。
	 * @param  ba   設定するバイト配列
	 </jp>*/
	/**<en>
	 * Sets the byte array.
	 * @param  ba   byte array to be set
	 </en>*/
	public void setBytes(byte[] ba)
	{
		//<jp> バイト配列をコピー</jp>
		//<en> Copy the byte array.</en>
		wByteBuffer = new byte[ba.length] ;
		for (int i = 0; i < ba.length; i++)
		{
			wByteBuffer[i] = ba[i] ;
		}
	}
	/**<jp>
	 * 保持している情報をバイト配列として取得します。
	 * @return    バイト配列
	 </jp>*/
	/**<en>
	 * Gets the preserved information; in form of byte array,
	 * @return    byte array
	 </en>*/
	public byte[] getBytes()
	{
		return (wByteBuffer) ;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

