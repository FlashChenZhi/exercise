// $Id: RmiUnbinder.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.rmi ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.Naming;

/**<jp>
 * レジストリサーバーに登録されている、オブジェクトを削除（unbind)するために使用するクラスです。
 * リモートオブジェクトが異常終了などでunbindせずに終了した場合、
 * レジストリサーバーにその情報が残るため、このクラスを使用してレジストリサーバーから
 * オブジェクトを削除します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/11/12</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used ti delete (unbind) the objects that are registered in registry server.
 * In case the remote objects are terminated withoug being unbound, as in abnormal termination,
 * the data will be retained in registry server. These objects are deleted from registry server
 * using this class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/11/12</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class RmiUnbinder
{
	// Class fields --------------------------------------------------
	/**<jp>
	 * デフォルトのRMIレジストリ・サーバのホスト名。
	 </jp>*/
	/**<en>
	 * Default host name of RMI registry server
	 </en>*/
	public static final String RMI_REG_SERVER = "rmiserver" ;

	// Class variables -----------------------------------------------
	/**<jp>
	 * RMIレジストリ・サーバ名
	 </jp>*/
	/**<en>
	 * RMI registry server name
	 </en>*/
	private String wRMIServerName ;

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Return the version of this class.
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
	 * Specify the RMI registry server, then generate the instance.
	 * @param rmihost  :name of the host of RMI registry server
	 </en>*/
	public RmiUnbinder(String rmihost)
	{
		setRMIServerName(rmihost) ;
	}

	/**<jp>
	 * RMIレジストリ・サーバを指定せずに、インスタンスを生成します。
	 * RMIレジストリ・サーバ名は、デフォルトのホスト名が使われます。
	 * <pre>
	 *	デフォルトのRMIレジストリ・サーバ名 : rmiserver
	 * </pre>
	 </jp>*/
	/**<en>
	 * Generate the instance without specifing the RMI registry server.
	 * Default host name is used for the name of RMI registry server.
	 * <pre>
	 *	Default RMI registry server name: rmiserver
	 * </pre>
	 </en>*/
	public RmiUnbinder()
	{
		this(RMI_REG_SERVER) ;
	}

	// Public methods ------------------------------------------------
	/**<jp>
	 * 指定されたリモートオブジェクトを削除（unbind)します。
	 * @param robject  リモート・オブジェクト・バインド名
	 * @throws Exception  リモート呼び出しや、リモートメソッドでの異常が発生した場合に報告されます。
	 </jp>*/
	/**<en>
	 * Unbind the specified remote object.
	 * @param robject  :Remote object bind name
	 * @throws Exception  :Reports if error occurred in remote calling or in remote method.
	 </en>*/
	public void unbind(String robject) throws Exception
	{
		String nameForLookup = "//" + wRMIServerName + "/" + robject;
		Naming.unbind(nameForLookup);
	}

	/**<jp>
	 * 指定されたリモートオブジェクトがレジストリサーバーに存在するかどうか検証します。
	 * @param robject  リモート・オブジェクト・バインド名
	 * @return robjectがレジストリサーバー上に存在する場合はtrue、見つからない場合はfalseを返します。
	 * @throws Exception  リモート呼び出しや、リモートメソッドでの異常が発生した場合に報告されます。
	 </jp>*/
	/**<en>
	 * Examine whether the specified remote object exists in registry server or not.
	 * @param robject  :remote object bind name
	 * @return :returns true if robject exists in registry server. False if robject cannot be found.
	 * @throws Exception : Reports if error occurred in remote calling or in remote method.
	 </en>*/
	public boolean isbind(String robject) throws Exception
	{
		String nameForLookup = "//" + wRMIServerName ;
		String[] listobjs = Naming.list(nameForLookup);
System.out.println("DEL = " + nameForLookup + ":1099/" + robject);
		for (int i = 0 ; i < listobjs.length ; i++)
		{
System.out.println(listobjs[i]);
			if (listobjs[i].equals(nameForLookup + ":1099/" + robject))
			{
				return true;
			}
		}
		return false;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**<jp>
	 * RMIレジストリ・サーバ名を設定します。
	 * @param rmihost  RMIレジストリ・サーバのホスト名。
	 </jp>*/
	/**<en>
	 * Set the RMI registry server name.
	 * @param rmihost  :RMI registry server name
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
	 * Get the RMI registry server name.
	 * @return :name of the host of RMI registry server
	 </en>*/
	protected String getRMIServerName()
	{
		return (wRMIServerName) ;
	}

	// Private methods -----------------------------------------------

}

//end of class
