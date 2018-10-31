// $Id: BindCleaner.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.text.DecimalFormat;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiUnbinder;
import jp.co.daifuku.wms.asrs.communication.as21.As21Sender;
import jp.co.daifuku.wms.asrs.communication.as21.As21Watcher;
import jp.co.daifuku.wms.asrs.communication.as21.AutomaticModeChangeSender;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.RetrievalSender;
import jp.co.daifuku.wms.asrs.communication.as21.StorageSender;
import jp.co.daifuku.wms.asrs.communication.as21.TimeKeeper;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * RMIレジストリサーバーに登録されているオブジェクトをレジストリサーバーから
 * 削除するために使用するクラスです。
 * 実行時は以下のように指定します。
 * java BindClener [オブジェクト名]
 * 例） java BindClener messagelog_server
 * 指定されたオブジェクトが登録されていない場合でも、Exceptionにはなりません。
 * また、AS21Watcherを指定された場合、サーバーにはグループコントローラー台数分登録されているので
 * 全グループコントローラーの情報を削除します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/11/11</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used to delete the objects registered in RMI registry server
 * from registry server.
 * Please specify as following when ecxecuting this process.
 * java BindClener [object name]
 * Ex. java BindClener messagelog_server
 * Even if the specified objects is not registered, Exception will not occur.
 * Besides, if AS21Watcher is specified, data of all group controllers will be deleted
 * since the same number of group controllers are registered in server.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/11/11</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class BindCleaner
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------
    /**<jp>
     * リモートオブジェクトをレジストリサーバーより削除します。
     * @param args 削除するオブジェクト名
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Delete the remote object from the registry server.
     * @param args : Remote object name to delete.
     * @throws  Exception  :in case any error occured
     </en>*/
    public static void main(String[] args)
            throws Exception
    {
        if (args.length > 0)
        {
            //<jp> 指定されたオブジェクト名がAS21通信処理の場合</jp>
            //<jp> レジストリサーバーには</jp>
            //<jp> グループコントローラー番号単位で登録されているため</jp>
            //<jp> 削除処理はグループコントローラー台数分実施する。</jp>
            //<en> If AS21 communication processing is specifed as an object name,</en>
            //<en> deletion will be processed as many number of times as the number of group controllers</en>
            //<en> since the group controllers are registerd according to the controller numbers, deletion</en>
            //<en> will be conducted by the same number of times as the number of group controllers.</en>
            if (args[0].equals(As21Watcher.OBJECT_NAME))
            {
                //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
                //<en> Acquire the connection with DataBase. User name and other data can be retrieved from resource file.</en>
                Connection conn = WmsParam.getConnection();
                //<jp> グループコントローラクラスからシステムに存在するすべてのグループコントローラ情報を得る。</jp>
                //<en> Get all the data of group controllers that exist in systen using group controller class.</en>
                GroupController[] gpColection = GroupController.getInstances(conn);
                //<jp> グループコントローラ台数分、unbind処理を行なう。</jp>
                //<jp> 対象となるオブジェクトは、As21WatcherとAs21Senderの2種類</jp>
                //<en> Unbind as many number of times as the number of group controllers.</en>
                //<en> There are 2 types of target objects: As21Watcher and As21Sender.</en>
                DecimalFormat fmt = new DecimalFormat("00");
                for (int i = 0; i < gpColection.length; i++)
                {
                    BindCleaner.unbind(As21Watcher.OBJECT_NAME + fmt.format(Integer.valueOf(gpColection[i].getNo())));
                    BindCleaner.unbind(As21Sender.OBJECT_NAME + fmt.format(Integer.valueOf(gpColection[i].getNo())));
                }
            }
            //<jp> 指定されたオブジェクト名がStorageSenderの場合、</jp>
            //<en> In case the StorageSender is specified as the object name,</en>
            else if (args[0].equals(StorageSender.OBJECT_NAME))
            {
                BindCleaner.unbind(TimeKeeper.OBJECT_NAME);
                BindCleaner.unbind(StorageSender.OBJECT_NAME);
                BindCleaner.unbind(RetrievalSender.OBJECT_NAME);
                BindCleaner.unbind(AutomaticModeChangeSender.OBJECT_NAME);
            }
            else
            {
                //<jp> 指定された名前のオブジェクトをunbindする。</jp>
                //<en> Unbind the object of the specified name.</en>
                BindCleaner.unbind(args[0]);
            }
        }
        else
        {
            System.out.println("Parameter Not Found [ex: RmiUnbinder object-name]");
        }
    }

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * 指定された名前のリモートオブジェクトをレジストリサーバーより削除します。
     * 指定された名前がレジストリサーバーに見つからない場合は、何も処理を行ないません。
     * @param name 削除するリモートオブジェクト名
     * @throws Exception レジストリサーバーからの削除に失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the remote object of specified bane from the registry server.
     * If the specified name cannot be found in registry server, no further process will be done.
     * @param name :remote object name to be deleted
     * @throws Exception :Notify in case the deletion of data from registry server failed.
     </en>*/
    private static void unbind(String name)
            throws Exception
    {
        try
        {
            RmiUnbinder unbinder = new RmiUnbinder();
            //<jp> 指定された名前がレジストリサーバーに登録されているか確認し</jp>
            //<jp> 登録されている場合のみレジストリサーバーから削除</jp>
            //<en> Check to see if the specified name is registered in registry server, </en>
            //<en> then delete data from registry server only if the data is found in registration.</en>
            if (unbinder.isbind(name))
            {
                unbinder.unbind(name);
            }
            else
            {
                System.out.println(name + ": Registry Server Not Found");
            }
        }
        catch (Exception e)
        {
            //<jp> 致命的なエラーが発生しました。{0}</jp>
            //<en> Fatal error occurred.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6016102, e), "BindCleaner");
            throw e;
        }
    }
}
//end of class

