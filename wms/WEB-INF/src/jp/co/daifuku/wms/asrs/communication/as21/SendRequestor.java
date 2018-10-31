// $Id: SendRequestor.java 4725 2009-07-22 04:05:38Z shibamoto $
package jp.co.daifuku.wms.asrs.communication.as21;

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.base.common.WmsParam;


/**
 * Designer : K.Mori <BR>
 * Maker : K.Mori <BR>
 * <BR>
 * StorageSender・RetrievalSender・AutomaticModeChangeSender等の各Sendorクラスに対して
 * RMIメッセージを使用して搬送要求を行います。
 * AS/RS入庫設定、出庫設定等の各処理はスケジュール正常終了時に本クラスを使用して
 * 搬送要求を行ってください。
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4725 $, $Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $
 * @author  $Author: shibamoto $
 */
public class SendRequestor
        extends Object
{
    // Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 4725 $,$Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $");
    }

    // Constructors --------------------------------------------------
    /**
     * デフォルトのコンストラクタです。コンストラクタ内での処理はありません。
     */
    public SendRequestor()
    {
    }
    
    // Public methods ------------------------------------------------
    /**
     * 搬送指示処理に搬送要求を行います。
     * 以下のSenderクラスのwrite()メソッドをRMIを通じて呼び出します。
     * jp.co.daifuku.wms.asrs.communication.as21.StorageSenderクラス。
     * jp.co.daifuku.wms.asrs.communication.as21.AutoMaticModeChangeSenderクラス。
     */
    public void storage()
    {
        Connection conn = null;
        try
        {
            if (WmsParam.MULTI_ASRSSERVER)
            {
                conn = WmsParam.getConnection();
                String[] gcNos = GroupController.getAllGCNo(conn);

	            for (String gcNo : gcNos)
	            {
	                RmiSendClient rmiSndC = new RmiSendClient(RmiSendClient.RMI_REG_SERVER + gcNo);
		            Object[] param = new Object[2];
		            param[0] = null;
		
		            // AutomaticModeChangeSenderのwriteメソッドをコールします。
		            rmiSndC.write("AutomaticModeChangeSender" + gcNo, param);
		
		            // StorageSenderのwriteメソッドをコールします。
		            rmiSndC.write("StorageSender" + gcNo, param);
		
		            rmiSndC = null;
	            }
            }
            else
            {
	            RmiSendClient rmiSndC = new RmiSendClient();
	            Object[] param = new Object[2];
	            param[0] = null;
	
	            // AutomaticModeChangeSenderのwriteメソッドをコールします。
	            rmiSndC.write("AutomaticModeChangeSender", param);
	
	            // StorageSenderのwriteメソッドをコールします。
	            rmiSndC.write("StorageSender", param);
	
	            rmiSndC = null;
            }
        }
        catch (java.lang.Exception e)
        {
            //<jp> 6026069=搬送指示処理に送信要求できませんでした。</jp>
            //<en> 6026069=Could not submit the send request to the transfer instruction process.</en>
            RmiMsgLogClient.write(new TraceHandler(6026069, e), getClass().getName());
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                    
                }
            }
        }
    }

    /**
     * 出庫指示処理に搬送要求を行います。
     * 以下のSenderクラスのwrite()メソッドをRMIを通じて呼び出します。
     * jp.co.daifuku.wms.asrs.communication.as21.RetrievalSenderクラス。
     * jp.co.daifuku.wms.asrs.communication.as21.AutoMaticModeChangeSenderクラス。
     */
    public void retrieval()
    {
        Connection conn = null;
        try
        {
        	if (WmsParam.MULTI_ASRSSERVER)
        	{
	            conn = WmsParam.getConnection();
	            String[] gcNos = GroupController.getAllGCNo(conn);
	            
	            for (String gcNo : gcNos)
	            {
	                RmiSendClient rmiSndC = new RmiSendClient(RmiSendClient.RMI_REG_SERVER + gcNo);
		            Object[] param = new Object[2];
		            param[0] = null;
		
		            // AutomaticModeChangeSenderのwriteメソッドをコールします。
		            rmiSndC.write("AutomaticModeChangeSender" + gcNo, param);
		
		            // RetrievalSenderのwriteメソッドをコールします。
		            rmiSndC.write("RetrievalSender" + gcNo, param);
		
		            rmiSndC = null;
	            }
        	}
        	else
        	{
                RmiSendClient rmiSndC = new RmiSendClient();
                Object[] param = new Object[2];
                param[0] = null;

                // AutomaticModeChangeSenderのwriteメソッドをコールします。
                rmiSndC.write("AutomaticModeChangeSender", param);

                // RetrievalSenderのwriteメソッドをコールします。
                rmiSndC.write("RetrievalSender", param);

                rmiSndC = null;

        	}
        }
        catch (java.lang.Exception e)
        {
            //<jp> 6026069=搬送指示処理に送信要求できませんでした。</jp>
            //<en> 6026069=Could not submit the send request to the transfer instruction process.</en>
            RmiMsgLogClient.write(new TraceHandler(6026069, e), getClass().getName());
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                    
                }
            }
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

