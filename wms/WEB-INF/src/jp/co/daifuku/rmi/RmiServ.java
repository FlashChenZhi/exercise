// $Id: RmiServ.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.rmi ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.Remote;

/**<jp>
 * リモート呼び出しを実現するため、サーバ・クライアントで利用されるインターフェースです。
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
 * This interface is used by server client in order to carry out the remote calling.
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
public interface RmiServ extends Remote
{
	/**<jp>
	 * オブジェクト配列を受け渡します。それぞれの要素については、
	 * サーバ・オブジェクトと、クライアントの双方で取り決めを行う必要があります。
	 * @param param 受け渡すオブジェクト配列
	 </jp>*/
	/**<en>
	 * Receives and passes the object arrays. It is required to fix rules for both
	 * server object and client concerning the respective elements.
	 * @param param Object array to pass
	 </en>*/
	void write(Object[] param) throws Exception ;

	/**<jp>
	 * サーバーとして動作している処理を停止するように要求します。
	 </jp>*/
	/**<en>
	 * Requesting server to terminate its performance.
	 </en>*/
	void stop() throws Exception ;
}

