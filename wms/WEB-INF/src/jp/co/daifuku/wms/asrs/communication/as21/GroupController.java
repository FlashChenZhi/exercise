// $Id: GroupController.java 5426 2009-11-06 10:34:16Z okayama $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.util.DbDateUtil;


/**<jp>
 * グループコントローラの情報を管理するためのクラスです。<br>
 * グループコントローラーの管理に必要な属性と定数を持ちます。<br>
 * クラスに定義されている各インスタンス変数の値は、グループコントローラー表から取得します。
 * 
 * @version $Revision: 5426 $, $Date: 2009-11-06 19:34:16 +0900 (金, 06 11 2009) $
 * @author  ssuzuki@softecs
 * @author  Last commit: $Author: okayama $
 </jp>*/
/**<en>
 * This class is used to control the informatin of group controller.
 * It preserves the attributes and constant numbers that are necessary for the control of group controller.
 * Values of each instance variables, that are defined in class, are retrieved from group controller table. 
 * 
 * @version $Revision: 5426 $, $Date: 2009-11-06 19:34:16 +0900 (金, 06 11 2009) $
 * @author  ssuzuki@softecs
 * @author  Last commit: $Author: okayama $
 </en>*/
public class GroupController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**<jp>グループ・コントローラの状態を表すフィールド(不明)</jp>*/
    /**<en>Field for the status of group controller (unknown)</en>*/
    public static final int STATUS_UNKNOWN = 0;

    /**<jp>グループ・コントローラの状態を表すフィールド(オンライン)</jp>*/
    /**<en>Field for the status of group controller (on-line)</en>*/
    public static final int STATUS_ONLINE = 1;

    /**<jp>グループ・コントローラの状態を表すフィールド(オフライン/接続済み)</jp>*/
    /**<en>Field fore the status of group controller (off-line/ connection complete)</en>*/
    public static final int STATUS_OFFLINE = 2;

    /**<jp>グループ・コントローラの状態を表すフィールド(オフライン予約)</jp>*/
    /**<en>Field for the status of group controller (reserved for off-line)</en>*/
    public static final int STATUS_END_RESERVATION = 3;

    /**<jp>デフォルトのグループ・コントローラ ("1")</jp>*/
    /**<en>Field for the default group controller ("1")</en>*/
    public static final String DEFAULT_AGC_NO = "1";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**<jp>
     * データベース接続用のコネクションを保持する変数。
     * 外部から提供され、クラス内部でのトランザクション操作は行わない。
     </jp>*/
    /**<en>
     * Variables which preserves the connection for database use.
     * It is provided from external. No transasction is performed inside the class.
     </en>*/
    private Connection _conn;

    /**<jp>グループ・コントローラのIPアドレスを保持する変数</jp>*/
    /**<en>Variables which preserves the IP address of the group controller</en>*/
    private InetAddress _inetAddress;

    /**<jp>グループ・コントローラのポート番号を保持する変数</jp>*/
    /**<en>Variables which preserves the port number of the group controller</en>*/
    private int _port;

    /**<jp>グループ・コントローラの状態を保持する変数</jp>*/
    /**<en>Variables which preserves the status of the group controller.</en>*/
    private String _status;

    /**<jp>テーブル検索用グループ・コントローラ番号を保持する変数</jp>*/
    /**<en>Variables which preserves the group controller for the table search use.</en>*/
    private String _groupControllerNo;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**<jp>
     * グループ・コントローラ番号を指定して、インスタンスを作成します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * @param gno 作成する<code>GroupController</code>の番号
     * @throws ReadWriteException  データベースアクセスエラー
     </jp>*/
    /**<en>
     * Create instance by specifying the group controller number.
     * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
     * is not done internally, it requires the external commitment.
     * @param gno : number of <code>GroupController</code> to create
     * @throws ReadWriteException  Notifies if exceptin occurs during the database connection.
     </en>
     */
    protected GroupController(Connection conn, String gno)
            throws ReadWriteException
    {
        setConnection(conn);
        _groupControllerNo = gno;

        load();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**<jp>
     * グループ・コントローラ番号から、<code>GroupController</code>のインスタンスを作成し、返します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * @param gno 作成する<code>GroupController</code>の番号
     * @return 引数に基づいて作成される<code>GroupController</code>オブジェクト
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Create the instance of <code>GroupController</code> based on the group controleer number, then returns it.
     * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
     * is not done internally, it requires the external commitment.
     * @param gno : number of <code>GroupController</code> to create
     * @return    : the object <code>GroupController</code> created on argument base.
     </en>
     * @throws ReadWriteException  Notifies if exceptin occurs during the database connection.
     */
    public static GroupController getInstance(Connection conn, String gno)
            throws ReadWriteException
    {
        GroupController gc = new GroupController(conn, gno);
        return gc;
    }

    /**<jp>
     * 全てのグループ・コントローラの<code>GroupController</code>のインスタンスを作成し、返します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * @return 引数に基づいて作成される<code>GroupController</code>オブジェクトの配列
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Create instance of all group controller <code>GroupController</code> then returns it.
     * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
     * is not done internally, it requires the external commitment.
     * @return : array of the object<code>GroupController</code> created on argument base.
     * @throws ReadWriteException : Notifies if exceptin occurs during the database connection.
     </en>*/
    public static GroupController[] getInstances(Connection conn)
            throws ReadWriteException
    {
        //<jp> 全てのグループ・コントローラNoを取得</jp>
        //<en> Get numbers of all group controller.</en>
        String[] gcids = getAllGCNo(conn);

        //<jp> グループ・コントローラ配列を作成</jp>
        //<en> Create the array of group controller</en>
        GroupController[] gcs = new GroupController[gcids.length];

        //<jp> 全てのグループ・コントローラインスタンスを生成</jp>
        //<en> Create all group controller instance</en>
        for (int i = 0; i < gcids.length; i++)
        {
            gcs[i] = getInstance(conn, gcids[i]);
        }

        return gcs;
    }

    /**<jp>
     * グループ・コントローラ番号から、<code>GroupController</code>のインスタンスを作成し、ステータス状態がオンラインであればtrueを返します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * 
     * @param gno 作成する<code>GroupController</code>の番号
     * 
     * @return ステータス状態がオンライン     : true
     *         ステータス状態がオンライン以外 : false
     *
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Create the instance of <code>GroupController</code> based on the group controller number . If the status is 
     * in on-line, it returns 'true'.
     * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
     * is not done internally, it requires the external commitment.
     * @param gno Number of <code>GroupController</code> to create
     * @return Status : on-line     : true
     *                : anything other than on-line : false
     * @throws ReadWriteException : Notifies if exception occur during the database connection.
     * @throws ReadWriteException : Notifies if the IP address of the specified host cannot be found in group controller data
     </en>*/
    public static boolean isOnLine(Connection conn, String gno)
            throws ReadWriteException
    {
        GroupController gc = new GroupController(conn, gno);
        return (STATUS_ONLINE == gc.getStatus());
    }

    /**
     * グループ・コントローラの情報をデータベースから取得します。<br>
     * 初期読み出しとして、コンストラクタから、このメソッドを呼び出しています。<br>
     * 
     * なお、ステータス以外の項目は、このメソッドが呼ばれるまでデータベースから
     * 読み出しませんので、最新の情報が必要な場合は、このメソッドを
     * 呼び出してください。<br>
     * 
     * このメソッドではレコードロックを行いません。
     * 
     * <!--
     * キーは、Group controller numberになります。
     * -->
     * @throws ReadWriteException データベース接続で例外が発生した場合または、
     * グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     */
    public void load()
            throws ReadWriteException
    {
        load(false);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**<jp>
     * グループ・コントローラ番号を取得します
     * @return    グループ・コントローラ番号
     </jp>*/
    /**<en>
     * Get the number of group controller
     * @return    group controller number
     </en>*/
    public String getNo()
    {
        return _groupControllerNo;
    }

    /**<jp>
     * ホスト名を取得します。
     * @return ホスト名
     </jp>*/
    /**<en>
     * Get the host name
     * @return host name
     </en>*/
    public String getHostName()
    {
        return getIP().getHostName();
    }

    /**<jp>
     * グループ・コントローラのIPアドレスを取得します
     * @return    グループ・コントローラのIPアドレス
     * @see java.net.InetAddress
     </jp>*/
    /**<en>
     * Get the IP address of the group controller
     * @return    IP address of the group controller
     * @see java.net.InetAddress
     </en>*/
    public InetAddress getIP()
    {
        return _inetAddress;
    }

    /**<jp>
     * グループ・コントローラのポート番号を取得します
     * @return    グループ・コントローラのポート番号
     </jp>*/
    /**<en>
     * Get the port number of the group controller
     * @return    the port number of the group controller
     </en>*/
    public int getPort()
    {
        return _port;
    }

    //----------------------------------------------------------------
    //<jp> 動的な状態のアクセッサ・メソッド</jp>
    //<en> Accessor method in dynamic state</en>
    //----------------------------------------------------------------
    /**<jp>
     * グループ・コントローラの状態を設定します。<br>
     * このメソッドでは、グループコントローラ情報のレコードをロックして更新を行います。<br>
     * トランザクションが確定するまで、該当レコードはロックされたままになります。
     * 
     * 状態の一覧はこのクラスのフィールドとして定義されています。
     * 
     * @param sts グループ・コントローラの状態
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Set the state of the group controller<br>
     * List of the state was defined as a field of this class.
     * @param sts State of the group controller
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException  : Notifies if the IP address of the specified host cannot be found in group controller data
     </en>*/
    public void setStatus(int sts)
            throws ReadWriteException
    {
        load(true);

        // 要注意 : テーブル上は 文字列
        _status = String.valueOf(sts);

        updateDB();
    }
    
    /**
     * 作業終了受信日時を更新します。
     * 
     * @param agcNo グループ・コントローラ番号
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     */
    public void setRecvDate(String agcNo)
        throws ReadWriteException
    {
        load(true);
        
        updateRecvDate(agcNo);
    }

    /**<jp>
     * グループ・コントローラの状態を取得します<br>
     * 状態の一覧はこのクラスのフィールドとして定義されています。
     * @return    グループ・コントローラの状態
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Get the state of the group controller<br>
     * List of the state was defined as a field of this class.
     * @return    State of the group controller
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException  : Notifies if the IP address of the specified host cannot be found in group controller data
     </en>*/
    public int getStatus()
            throws ReadWriteException
    {
        load(false);

        // 要注意 : テーブル上は 文字列
        return Integer.parseInt(_status);
    }

    /**<jp>
     * グループ・コントローラの文字列表現を返します。
     * @return    グループ・コントローラの文字列表現
     </jp>*/
    /**<en>
     * Retruns the string representation of the group controller
     * @return    string representation of the group controller
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        try
        {
            buf.append("\nGroup Controller Number:");
            buf.append(getNo());
            buf.append("\nGroup Controller IP:");
            buf.append(getIP());
            buf.append("\nGroup Controller Port:");
            buf.append(getPort());
            buf.append("\nGroup Controller Status:");
            buf.append(getStatus());
        }
        catch (Exception e)
        {
            // do nothing.
        }

        return String.valueOf(buf);
    }

    /**
     * データベースコネクションを返します。
     * @return データベースコネクション
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    /**
     * データベースコネクションを設定します。
     * @param conn データベースコネクション
     */
    protected void setConnection(Connection conn)
    {
        _conn = conn;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**<jp>
     * グループ・コントローラの情報をデータベースから取得します。<br>
     * ステータス以外の項目は、コンストラクタで読み込まれた後、このメソッドが
     * 呼ばれるまでデータベースから読み出しませんので、最新の情報が
     * 必要な場合は、このメソッドを呼び出してください。
     * 
     * <!--
     * キーは、Group controller numberになります。
     * -->
     * @param   needLock 検索したデータのロックを行う場合はtrue、行わない場合はfalseを指定します。
     * 
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Get the data of group controller out of database.
     * 
     * <!--
     * Key is the Group controller number.
     * -->
     * 
     * @param   needLock : set 'true' locking the searched data, or 'false' NOT locking.
     * 
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException : Notifies if the IP address of the specified host cannot be found in group controller data
     </en>*/
    protected void load(boolean needLock)
            throws ReadWriteException
    {
        GroupControllerSearchKey gcKey = new GroupControllerSearchKey();

        // collect Group Controller Number
        gcKey.setControllerNoCollect();
        gcKey.setStatusFlagCollect();
        gcKey.setIpaddressCollect();
        gcKey.setPortCollect();

        // select by Controller Number
        gcKey.setControllerNo(getNo());

        GroupControllerHandler gcH = new GroupControllerHandler(getConnection());
        jp.co.daifuku.wms.base.entity.GroupController gc = null;
        String ipaddress = "";
        try
        {
            if (needLock)
            {
                gc = (jp.co.daifuku.wms.base.entity.GroupController)gcH.findPrimaryForUpdate(gcKey, GroupControllerHandler.WAIT_SEC_UNLIMITED);
            }
            else
            {
                gc = (jp.co.daifuku.wms.base.entity.GroupController)gcH.findPrimary(gcKey);
            }

            if (null == gc)
            {
                // no group controller define found.
                throw new ReadWriteException();
            }

            // get values from Entity
            _groupControllerNo = gc.getControllerNo();
            _status = gc.getStatusFlag();
            _port = gc.getPort();

            // convert IP address (Host name) to InetAddress
            ipaddress = gc.getIpaddress();
            _inetAddress = InetAddress.getByName(ipaddress);
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
        catch (LockTimeOutException e)
        {
            throw new ReadWriteException(e);
        }
        catch (UnknownHostException e)
        {
            //<jp> 6026036=指定されたホストのIPアドレスがみつかりません。ホスト名:{0}</jp>
            //<en> 6026036=Cannot find the IP address of the designated host. Host:{0}</en>
            Object[] mps = {
                ipaddress,
            };
            RmiMsgLogClient.write(new TraceHandler(6026036, e), getClass().getName(), mps);
            throw new ReadWriteException(e);
        }
    }

    /**<jp>
     * グループ・コントローラの情報をデータベースへ反映します。<br>
     * ステータスについては、セットするたびに更新されますので、このメソッドを
     * 呼び出す必要はありません。
     * 
     * <!--
     * キーは、Group controller番号になります。
     * -->
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Reflect the information of group controller to database.
     * <!--
     * Key is the Group controller number.
     * -->
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     </en>*/
    protected void updateDB()
            throws ReadWriteException
    {
        GroupControllerAlterKey gcAKey = new GroupControllerAlterKey();

        // set update values
        gcAKey.updateStatusFlag(_status);
        gcAKey.updateIpaddress(_inetAddress.getHostName());
        gcAKey.updatePort(getPort());

        // set keys
        gcAKey.setControllerNo(getNo());

        try
        {
            GroupControllerHandler gcH = new GroupControllerHandler(getConnection());
            gcH.modify(gcAKey);
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * Group controller番号をもとにRecv_Dateを更新します。
     * 
     * @param agcNo Group controller番号
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     */
    protected void updateRecvDate(String agcNo)
            throws ReadWriteException
    {
        GroupControllerAlterKey gcAKey = new GroupControllerAlterKey();

        // set update values
        gcAKey.updateIpaddress(_inetAddress.getHostName());
        gcAKey.updatePort(getPort());
        gcAKey.updateRecvDate(DbDateUtil.getTimeStamp());

        // set keys
        gcAKey.setControllerNo(agcNo);

        try
        {
            GroupControllerHandler gcH = new GroupControllerHandler(getConnection());
            gcH.modify(gcAKey);
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException(e);
        }
    }
    /**<jp>
     * 全てのグループ・コントローラ番号をデータベースから取得します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * 
     * @return グループ・コントローラ番号の配列<br>
     * ひとつも見つからなかったときは要素数0の配列が返されます。
     * 
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Get number of all group controller out of database.
     * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
     * is not done internally, it requires the external commitment.
     * @return Array of group controller number
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     </en>*/
    protected static String[] getAllGCNo(Connection conn)
            throws ReadWriteException
    {
        GroupControllerHandler gcH = new GroupControllerHandler(conn);
        GroupControllerSearchKey gcKey = new GroupControllerSearchKey();

        // collect Group Controller Number
        gcKey.setControllerNoCollect();
        // sort by Controller Number
        gcKey.setControllerNoOrder(true);

        jp.co.daifuku.wms.base.entity.GroupController[] gcs =
                (jp.co.daifuku.wms.base.entity.GroupController[])gcH.find(gcKey);

        // check no GCs defined
        if (ArrayUtil.isEmpty(gcs))
        {
            return new String[0];
        }

        String[] gcNos = new String[gcs.length];
        for (int i = 0; i < gcs.length; i++)
        {
            gcNos[i] = gcs[i].getControllerNo();
        }

        return gcNos;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: GroupController.java 5426 2009-11-06 10:34:16Z okayama $";
    }
}
