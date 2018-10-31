// $Id: RmiSendClient.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.rmi ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.Naming;

/**<jp>
 * RMIを利用して、リモート・オブジェクトへオブジェクトを送信するためのクラスです。
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
 * This class implements the sending of objects to the remote object via RMI.
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
public class RmiSendClient
{
	// Class fields --------------------------------------------------
	/**<jp>
	 * デフォルトのRMIレジストリ・サーバのホスト名。
	 </jp>*/
	/**<en>
	 * Host name of the default RMI registry server
	 </en>*/
	public static final String RMI_REG_LOG_SERVER = "rmiserver" ;

	/**<jp>
	 * as21専用のRMIレジストリ・サーバのホスト名。
	 </jp>*/
	/**<en>
	 * Host name of the RMI registry server for as21
	 </en>*/
	public static final String RMI_REG_SERVER = "as21server" ;

	// Class variables -----------------------------------------------
	/**<jp>
	 * RMIレジストリ・サーバ名
	 </jp>*/
	/**<en>
	 * Name of RMI registry server
	 </en>*/
	private String wRMIServerName ;

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returning the version of this class
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
	}

	// Constructors --------------------------------------------------
	/**<jp>
	 * RMIレジストリ・サーバを指定して、インスタンスを生成します。
	 * @param rmihost  RMIレジストリ・サーバのホスト名。
	 </jp>*/
	/**<en>
	 * Specifying RMI registry server then performing instantiation
	 * @param rmihost  Host name of RMI registry server
	 </en>*/
	public RmiSendClient(String rmihost)
	{
		setRMIServerName(rmihost) ;
	}

	/**<jp>
	 * AS/RS専用です。
	 * AS/RS用のレジストリサーバ名を保持したインスタンスを生成します。
	 * <pre>
	 *	AS/RS用のレジストリサーバ名 : as21server
	 * </pre>
	 </jp>*/
	/**<en>
	 * Instantiating without specifying RMI registry server.
	 * Default host name will be used for RMI registry server
	 * <pre>
	 *	Default RMI registry server name : as21server
	 * </pre>
	 </en>*/
	public RmiSendClient()
	{
		this(RMI_REG_SERVER) ;
	}

	// Public methods ------------------------------------------------
	/**<jp>
	 * オブジェクトの配列を、リモート・オブジェクトへ渡します。
	 * @param robject  リモート・オブジェクト・バインド名
	 * @param param  リモート・オブジェクトへ渡す、オブジェクト配列。
	 * @throws Exception  リモート呼び出しや、リモートメソッドでの異常が発生した場合に報告されます。
	 </jp>*/
	/**<en>
	 * Passing object array to the remote object.
	 * @param robject  Name of remote object bind
	 * @param param  Object array to be passed to remote object
	 * @throws Exception  Reports if abnormality occurs when calling remote or during the remote method.
	 </en>*/
	public void write(String robject, Object[] param) throws Exception
	{
		String nameForLookup = "//" + wRMIServerName + "/" + robject;
		RmiServ server = (RmiServ)Naming.lookup(nameForLookup) ;
		server.write(param);
		server = null ;
	}

	/**<jp>
	 * オブジェクトの配列を、リモート・オブジェクトへ渡します。
	 * @param robject  リモート・オブジェクト・バインド名
	 * @throws Exception  リモート呼び出しや、リモートメソッドでの異常が発生した場合に報告されます。
	 </jp>*/
	/**<en>
	 * Passing object array to the remote object.
	 * @param robject  Name of remote object bind
	 * @throws Exception  Reports if abnormality occurs when calling remote or during the remote method.
	 </en>*/
	public void stop(String robject) throws Exception
	{
		String nameForLookup = "//" + wRMIServerName + "/" + robject;
		RmiServ server = (RmiServ)Naming.lookup(nameForLookup);
		server.stop();
		server = null;
	}


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**<jp>
	 * RMIレジストリ・サーバ名を設定します。
	 * @param rmihost  RMIレジストリ・サーバのホスト名。
	 </jp>*/
	/**<en>
	 * Setting name of RMI registry server.
	 * @param rmihost  Host name of RMI registry server
	 </en>*/
	protected void setRMIServerName(String rmihost)
	{
		wRMIServerName = rmihost ;
	}

	/**<jp>
	 * RMIレジストリ・サーバ名を取得します。
	 * @return RMIレジストリ・サーバのホスト名。
	 </jp>*/
	/**<en>
	 * Getting the name of RMI registry server.
	 * @return Host name of RMI registry server
	 </en>*/
	protected String getRMIServerName()
	{
		return (wRMIServerName) ;
	}

	// Private methods -----------------------------------------------

}

//end of class
