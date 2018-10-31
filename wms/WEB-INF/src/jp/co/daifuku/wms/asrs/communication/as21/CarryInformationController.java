// $Id: CarryInformationController.java 4725 2009-07-22 04:05:38Z shibamoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;

/**<jp>
 * 搬送指示処理、出庫指示処理、自動モード切替搬送指示処理を起動するためのクラスです。<BR>
 * 各処理は設備レイアウトによりシングルディープ用、ダブルディープ用のいずれかが選択されて起動されます。<BR>
 * このクラス自身は、必要台数分起動後、終了します。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4725 $, $Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $
 * @author  $Author: shibamoto $
 </jp>*/
/**<en>
 * This class preserves the main method of starting the task for carrying instruction and retrieval instruction.<BR>
 * For processing these instructions, either single deep task or double deep task depending on the facility layouts.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4725 $, $Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $
 * @author  $Author: shibamoto $
 </en>*/
public class CarryInformationController
        extends Object
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returnes the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 4725 $,$Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $");
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**<jp>
     * 搬送指示処理、出庫指示処理、自動モード切替搬送指示処理を起動します。
     * 起動パラメータの指定はありません。
     * @param args (未使用)
     </jp>*/
    /**<en>
     * Starts up the process of carrying instruction and retrieval instruction as treads.<BR>
     * @param args : not used
     </en>*/
    public static void main(String[] args)
    {
       //<jp> データベースのコネクション</jp>
        //<en> Connection with thd database</en>
        Connection conn = null;

        //<jp> 時間監視処理</jp>
        //<en> Time keeping</en>
        TimeKeeper timekeeper = null;

        //<jp> 搬送指示送信処理</jp>
        //<en> Transmission of the carrying instruction</en>
        StorageSender sSend = null;

        //<jp> 出庫指示送信処理</jp>
        //<en> Transmission of the retrieval instruction</en>
        RetrievalSender rSend = null;

        //<jp> 自動モード切替搬送指示送信処理</jp>
        //<en> Transmission of the carrying instruction with automatic mode switching</en>
        AutomaticModeChangeSender srSend = null;

        try
        {
            //<jp> コネクション生成。このメソッドでのみ使用する。</jp>
            //<en> Generates the connection. Is used only in this method.</en>
            conn = WmsParam.getConnection();
            //<jp> リソースファイルから搬送指示、出庫指示データの読込み間隔時間（ms）を取り込む</jp>
            //<en> Loads from the resource file the interval time (ms) to be taken during the reading of carrying </en>
            //<en> instruction and retrieval instruction.</en>
            int wSec = WmsParam.INSTRUCT_CONTROL_SLEEP_SEC;
            //<jp> アイル定義中にダブルディープ用のアイルが含まれているか確認する。</jp>
            //<en> While the definition of aisle, it checks whether/not the aisle for double deep use is included.</en>
            AisleHandler ahandl = new AisleHandler(conn);
            AisleSearchKey akey = new AisleSearchKey();
            Aisle[] aisles = (Aisle[])ahandl.find(akey);

            boolean dExist = false;
            for (int i = 0; i < aisles.length; i++)
            {
                if (Aisle.DOUBLE_DEEP_KIND_DOUBLE.equals(aisles[i].getDoubleDeepKind()))
                {
                    dExist = true;
                    break;
                }
            }
            if(!WmsParam.MULTI_ASRSSERVER)
            {
            	if (dExist)
	            {
	                //<jp> ダブルディープレイアウトのアイルが含まれている場合</jp>
	                //<jp> ダブルディープ用の搬送送受信スレッドを起動する</jp>
	                //<en> If the aisle of double deep layout is included,</en>
	                //<en> it starts up the threads for double deep of transmission(in/out) of carrying.</en>
	                sSend = new DoubleDeepStorageSender();
	                new Thread(sSend).start();
	                rSend = new DoubleDeepRetrievalSender();
	                new Thread(rSend).start();
	                // ダブルディープ対応
	                //<jp> 自動モード切替搬送指示 起動</jp>
	                //<en> Starts the carrying instruction with automatic mode switch.</en>
	                srSend = new AutomaticModeChangeSender();
	                new Thread(srSend).start();
	            }
	            else
	            {
	                //<jp> 搬送指示 起動</jp>
	                //<en> Starts the carrying instruction.</en>
	                sSend = new StorageSender();
	                new Thread(sSend).start();
	                //<jp> 出庫指示 起動</jp>
	                //<en> Starts the retrieval instruction.</en>
	                rSend = new RetrievalSender();
	                new Thread(rSend).start();
	                //<jp> 自動モード切替搬送指示 起動</jp>
	                //<en> Starts the carrying instruction with automatic mode switch.</en>
	                srSend = new AutomaticModeChangeSender();
	                new Thread(srSend).start();
	            }
	        	//<jp> 時間監視処理 起動</jp>
	            //<en> Starts the time keeping.</en>
	            timekeeper = new TimeKeeper(wSec, sSend, rSend, srSend);
	            new Thread(timekeeper).start(); 
            }
            else
            {
            	for(String agcNumber:args)
            	{
		            if (dExist)
		            {
		                //<jp> ダブルディープレイアウトのアイルが含まれている場合</jp>
		                //<jp> ダブルディープ用の搬送送受信スレッドを起動する</jp>
		                //<en> If the aisle of double deep layout is included,</en>
		                //<en> it starts up the threads for double deep of transmission(in/out) of carrying.</en>
		            	sSend = new DoubleDeepStorageSender(agcNumber);
		                new Thread(sSend).start();
		                rSend = new DoubleDeepRetrievalSender(agcNumber);
		                new Thread(rSend).start();
		                // ダブルディープ対応
		                //<jp> 自動モード切替搬送指示 起動</jp>
		                //<en> Starts the carrying instruction with automatic mode switch.</en>
		                srSend = new AutomaticModeChangeSender(agcNumber);
		                new Thread(srSend).start();
		            }
		            else
		            {
		                //<jp> 搬送指示 起動</jp>
		                //<en> Starts the carrying instruction.</en>
		                sSend = new StorageSender(agcNumber);
		                new Thread(sSend).start();
		                //<jp> 出庫指示 起動</jp>
		                //<en> Starts the retrieval instruction.</en>
		                rSend = new RetrievalSender(agcNumber);
		                new Thread(rSend).start();
		                //<jp> 自動モード切替搬送指示 起動</jp>
		                //<en> Starts the carrying instruction with automatic mode switch.</en>
		                srSend = new AutomaticModeChangeSender(agcNumber);
		                new Thread(srSend).start();
		            }
		            //<jp> 時間監視処理 起動</jp>
		            //<en> Starts the time keeping.</en>
		            timekeeper = new TimeKeeper(wSec, sSend, rSend, srSend, agcNumber);
	            	new Thread(timekeeper).start();
            	}
            }
            //<jp> ここで生成したコネクションはもう不要なのでCLOSE</jp>
            //<en> Closes; the connection generated here is no longer necessary.</en>
            conn.close();
        }
        catch (SQLException e)
        {
            Object[] tObj = new Object[1];
            tObj[0] = String.valueOf(e.getErrorCode());
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "CarryInformationController", tObj);
        }
        catch (Exception e)
        {
            //<jp>エラーをログファイルに落とす</jp>
            //<en>Records the errors in the log file.</en>
            //<jp> 6026041=搬送指示、出庫指示の起動に失敗しました。</jp>
            //<en> 6026041=Failed to execute the transfer/picking instruction.</en>
            RmiMsgLogClient.write(new TraceHandler(6026041, e), "CarryInformationController");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
