// $Id: RmiServAbstImpl.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.rmi ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**<jp>
 * RMIを利用する、サーバオブジェクトのためのスーパークラスです。
 * 実際にオブジェクトを受け取るメソッド<code>write()</code>は、
 * 各サブクラスで実装する必要があります。
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
 * This is the superclass for server object using RMI.
 * It is necessary that method to actually accept objects <code>write()</code> 
 * be implemented by each subclass.
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
public abstract class RmiServAbstImpl extends UnicastRemoteObject implements RmiServ
{
	// Class fields --------------------------------------------------
	/*
	 * Comment for field
	 */

	// Class variables -----------------------------------------------
	protected String wBindedName ;

	// Class method --------------------------------------------------
	/**<jp>
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 </jp>*/
	/**<en>
	 * Returning the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
	}

	// Constructors --------------------------------------------------
	/**<jp>
	 * リモートオブジェクトを生成します。
	 * @throws java.rmi.RemoteException
	 </jp>*/
	/**<en>
	 * Instantiating remote object.
	 * @throws java.rmi.RemoteException
	 </en>*/
	public RmiServAbstImpl() throws java.rmi.RemoteException
	{
		super() ;
	}

	// Public methods ------------------------------------------------
	/**<jp>
	 * オブジェクト配列を受け取り、処理を行うためのメソッドです。
	 * このクラスでは抽象メソッドとなっていますので、サブクラスにて実装する必要があります。
	 * @param params 受け渡されるオブジェクト配列。
	 </jp>*/
	/**<en>
	 * This is the method to accept object arrays and to process.
	 * Implementation is necessary at subclass level since this method is 
	 * assumed abstract in this class.
	 * @param params Object array to be passed.
	 </en>*/
	public abstract void write(Object[] params) throws IOException ;

	/**<jp>
	 * 起動中の処理の終了処理を行うためのメソッドです。
	 * このクラスでは抽象メソッドとなっていますので、サブクラスにて実装する必要があります。
	 * @param params 受け渡されるオブジェクト配列。
	 </jp>*/
	/**<en>
	 * This method is used to terminate the process being activated. 
	 * Implementation is necessary at subclass level since this method is 
	 * assumed abstract in this class.
	 * @param params Object array to be passed.
	 </en>*/
	public abstract void stop() throws IOException ;

	/**<jp>
	 * サーバ・オブジェクトを、レジストリ・サーバに登録します。
	 * @param name  バインドする名前。
	 </jp>*/
	/**<en>
	 * Registering server object in the registry server.
	 * @param name  Name to bind
	 </en>*/
	public void bind(String name) throws RemoteException, MalformedURLException, AccessException
	{
		// Create and install a security manager
		if (System.getSecurityManager() == null)
		{
			System.setSecurityManager(new RMISecurityManager());
		}

		Naming.rebind(name, this) ;
		wBindedName = name ;
	}
	/**<jp>
	 * サーバ・オブジェクトを、レジストリ・サーバから抹消します。
	 </jp>*/
	/**<en>
	 * Erasing server objects from the registry server.
	 </en>*/
	public void unbind()
	{
		try
		{
			Naming.unbind(wBindedName) ;
		}
		catch (Exception e)
		{ }
	}

	/**<jp>
	 * 指定されたnameが、レジストリ・サーバに登録されているかどうかを判定します。。
	 * @param name  バインドされているかどうか判定される名前。
	 * @return true:バインドされている
	 </jp>*/
	/**<en>
	 * Determining whether/not the specified name is registered in registry server.
	 * @param name  Name to determine whether/not bound 
	 * @return true: It is bound.
	 </en>*/
	public boolean isbind(String name) throws RemoteException, MalformedURLException, AccessException
	{
		try
		{
			// Remote re = Naming.lookup(name) ;
            Naming.lookup(name) ;
		}
		catch(NotBoundException e)
		{
			//<jp> 登録されていない</jp>
			//<en> Not found in registry</en>
			return false;
		}

		return true;
	}


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

