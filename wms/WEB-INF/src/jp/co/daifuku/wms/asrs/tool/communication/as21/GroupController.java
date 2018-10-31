// $Id: GroupController.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.communication.as21 ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

/**<jp>
 * グループ・コントローラの情報を管理するためのクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class controls the information of the group controller.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class GroupController extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * グループ・コントローラの状態を表すフィールド(不明)
     </jp>*/
    /**<en>
     * Field of the status of group controller (unknown)
     </en>*/
    public static final int STATUS_UNKNOWN = 0 ;
    
    /**<jp>
     * グループ・コントローラの状態を表すフィールド(オンライン)
     </jp>*/
    /**<en>
     * Field of the status of group controller (on-line)
     </en>*/
    public static final int STATUS_ONLINE = 1 ;
    
    /**<jp>
     * グループ・コントローラの状態を表すフィールド(オフライン/接続済み)
     </jp>*/
    /**<en>
     * Field of the status of group controller (off-line/connected)
     </en>*/
    public static final int STATUS_OFFLINE = 2 ;
    
    /**<jp>
     * グループ・コントローラの状態を表すフィールド(終了予約)
     </jp>*/
    /**<en>
     * Field of the status of group controller (reserved for termination)
     </en>*/
    public static final int STATUS_END_RESERVATION = 3 ;
    
    /**<jp>
     * デフォルトのグループ・コントローラを表すフィールド
     </jp>*/
    /**<en>
     * Field of default group controller
     </en>*/
    public static final int DEFAULT_AGC_NUMBER = 1 ;

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用のコネクションを保持する変数。
     * 外部から提供され、クラス内部でのトランザクション操作は行わない。
     </jp>*/
    /**<en>
     * Variables that preserve the connection with database.
     * It will be provided externally; the transaction will not be operated in this class.
     </en>*/
    private Connection wConn ;

    /**<jp>
     * グループ・コントローラ番号を保持する変数
     </jp>*/
    /**<en>
     * Variables which preserve the group controller no.
     </en>*/
    private int wControllerNo = -1 ;
    
    /**<jp>
     * グループ・コントローラのIPアドレスを保持する変数
     </jp>*/
    /**<en>
     * Variables which preserve the IP address of the group controllers
     </en>*/
    private InetAddress wIpaddress ;
    
    /**<jp>
     * グループ・コントローラのポート番号を保持する変数
     </jp>*/
    /**<en>
     * Variables which preserve the port no. of the group controller.
     </en>*/
    private int wGCPort ;
    
    /**<jp>
     * グループ・コントローラの状態を保持する変数
     </jp>*/
    /**<en>
     * Variables which preserve the status of the group controller.
     </en>*/
    private int wStatusFlag ;
    

    /**<jp>
     * テーブル検索用グループ・コントローラ番号を保持する変数
     </jp>*/
    /**<en>
     * Variables which preserve the group controller no. for the table search.
     </en>*/
    private int wSelNumber ;

    /**<jp>
     * テーブル名を保持します。
     </jp>*/
    /**<en>
     * Preserve the name of the tables.
     </en>*/
    private static String wTableName = "TEMP_DMGROUPCONTROLLER" ;
    
    /**<jp>
     * Exception発生時のLogWriteに使用します。
     </jp>*/
    /**<en>
     * This will be used in LogWrite when Exception occurs.
     </en>*/
    public StringWriter wSW = new StringWriter();

    /**<jp>
     * Exception発生時のLogWriteに使用します。
     </jp>*/
    /**<en>
     * This will be used in LogWrite when Exception occurs.
     </en>*/
    public PrintWriter  wPW = new PrintWriter(wSW);

    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public String wDelim = MessageResource.DELIM ;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }
    
    /**<jp>
     * グループ・コントローラ番号から、<code>GroupController</code>のインスタンスを作成し、返します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * @param gno 作成する<code>GroupController</code>の番号
     * @return 引数に基づいて作成される<code>GroupController</code>オブジェクト
     </jp>*/
    /**<en>
     * Create the <code>GroupController</code> instance based on the group controller no.,
     * then return.
     * @param conn  :the transaction of <code>Connection</code> for database connection is not
     * controled internally; it is necessary that it should be committed externally.
     * @param gno :number of <code>GroupController</code> creating
     * @return    :<code>GroupController</code> object which will be created based on the parameter.
     </en>*/
    public static GroupController getInstance(Connection conn, int gno)
    {
        GroupController gc = new GroupController(conn, gno) ;
        return (gc) ;
    }
    
    /**<jp>
     * 全てのグループ・コントローラの<code>GroupController</code>のインスタンスを作成し、返します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * @return 引数に基づいて作成される<code>GroupController</code>オブジェクトの配列
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Create the <code>GroupController</code> instance of all grop controllers.
     * @param conn  :the transaction of <code>Connection</code> for database connection is not
     * controled internally; it is necessary that it should be committed externally.
     * @return :the array of <code>GroupController</code> object created based on the parameter
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     </en>*/
    public static GroupController[] getInstances(Connection conn) throws ReadWriteException
    {
        //<jp> 全てのグループ・コントローラNoを取得</jp>
        //<en> Retrieve all the group controller no.</en>
        int[] gcids = getGCNo(conn) ;

        //<jp> グループ・コントローラ配列を作成</jp>
        //<en> Create the array of group controller.</en>
        GroupController[] gcs = new GroupController[gcids.length] ;

        //<jp> 全てのグループ・コントローラインスタンスを生成</jp>
        //<en> Generate the instance of all group controllers.</en>
        for (int i = 0; i < gcids.length ; i++)
        {
            gcs[i] = new GroupController(conn, gcids[i]) ;
        }
        
        return (gcs) ;
    }

    /**<jp>
     * グループ・コントローラ番号から、<code>GroupController</code>のインスタンスを作成し、ステータス状態がオンラインであればtrueを返します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * @param gno 作成する<code>GroupController</code>の番号
     * @return ステータス状態がオンライン     : true
     *         ステータス状態がオンライン以外 : false
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Create the <code>GroupController</code> instance based on the group controller no;
     * return true if the status is on-line.
     * @param conn  :the transaction of <code>Connection</code> for database connection is not
     * controled internally; it is necessary that it should be committed externally.
     * @param gno :number of <code>GroupController</code> creating
     * @return :if the status is online     : true
     *         if the status is anything othere than on-line : false
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
     </en>*/
    public static boolean isOnLine(Connection conn, int gno) throws ReadWriteException
    {
        GroupController gc = new GroupController(conn, gno) ;
        if (gc.getStatusFlag() == STATUS_ONLINE)
        {
            return true ;
        }
        
        return false ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * グループ・コントローラ番号を指定して、インスタンスを作成します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * @param gno 作成する<code>GroupController</code>の番号
     </jp>*/
    /**<en>
     * Create the instance by specifing the group controller ID.
     * @param conn  the transaction of <code>Connection</code> for database connection is not
     * controled internally; it is necessary that it should be committed externally.
     * @param gno : number of <code>GroupController</code> to create
     </en>*/
    public GroupController(Connection conn, int gno)
    {
        wConn = conn ;
        wSelNumber = gno ;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * テーブル名をセットします。変更したい場合はこのメソッドを使用します。
     * デフォルトではTEMP_GROUPCONTROLLERを参照します。
     * @param arg 設定するTableName
     </jp>*/
    /**<en>
     * Set the name of the table. This method should be used when the modification is required.
     * Refer to default TEMP_GROUPCONTROLLER.
     * @param arg TableName to set
     </en>*/
    public void setTableName(String arg)
    {
        wTableName = arg;
    }

    /**<jp>
     * グループ・コントローラ番号を取得します
     * @return    グループ・コントローラ番号
     </jp>*/
    /**<en>
     * Retrieve the group controller no.
     * @return    :the group controller no.
     </en>*/
    public int getNumber()
    {
        return (wSelNumber) ;
    }

    /**<jp>
     * グループ・コントローラのIPアドレスを設定します
     * @param ip グループ・コントローラのIPアドレス
     * @see java.net.InetAddress
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Set the IP address of the group controller.
     * @param ip :IP address of the group controller
     * @see java.net.InetAddress
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
     </en>*/
    public void setIpAddress(InetAddress ip) throws ReadWriteException
    {
        //<jp> DBのロック</jp>
        //<en> Lock DB</en>
        loadGCinfo(true) ;
        
        //<jp> DBの更新</jp>
        //<en> DB update</en>
        wIpaddress = ip ;
        updateGCinfo() ;
    }
    
    /**<jp>
     * グループ・コントローラのIPアドレスを取得します
     * @return    グループ・コントローラのIPアドレス
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Retrieve the IP address of the group controller.
     * @return    :IP address of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
     </en>*/
    public InetAddress getIpAddress() throws ReadWriteException
    {
        //<jp> DBからグループ・コントローラ情報取得</jp>
        //<en> Get the data of group controller from DB</en>
        loadGCinfo(false) ;

        return (wIpaddress) ;
    }

    /**<jp>
     * グループ・コントローラのポート番号を設定します
     * @param port グループ・コントローラのポート番号
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Set the port no. of the group controller.
     * @param port :port no. of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
     </en>*/
    public void setPort(int port) throws ReadWriteException
    {
        //<jp> DBのロック</jp>
        //<en> Lock DB</en>
        loadGCinfo(true) ;

        //<jp> DBの更新</jp>
        //<en> DB update</en>
        wGCPort = port ;
        updateGCinfo() ;
    }
    
    /**<jp>
     * グループ・コントローラのポート番号を取得します
     * @return    グループ・コントローラのポート番号
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Retrieve the port no. of the group controller.
     * @return    :port no. of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
     </en>*/
    public int getPort() throws ReadWriteException
    {
        //<jp> DBからグループ・コントローラ情報取得</jp>
        //<en> Get the data of group controller from DB</en>
        loadGCinfo(false) ;
        
        return (wGCPort) ;
    }

    //----------------------------------------------------------------
    //<jp> 動的な状態のアクセッサ・メソッド</jp>
    //<en> Accessor method in dynamic state</en>
    //----------------------------------------------------------------
    /**<jp>
     * グループ・コントローラの状態を設定します<BR>
     * 状態の一覧はこのクラスのフィールドとして定義されています。
     * @param sts グループ・コントローラの状態
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Set the status of the group controller.<BR>
     * The list of status is defined as a field of this class.
     * @param sts  :the status of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
     </en>*/
    public void setStatusFlag(int sts) throws ReadWriteException
    {
        //<jp> DBのロック</jp>
        //<en> Lock DB</en>
        loadGCinfo(true) ;
        
        //<jp> DBの更新</jp>
        //<en> DB update</en>
        wStatusFlag = sts ;
        updateGCinfo() ;
    }
    
    /**<jp>
     * グループ・コントローラの状態を取得します<BR>
     * 状態の一覧はこのクラスのフィールドとして定義されています。
     * @return    グループ・コントローラの状態
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Retrieve the the status of the group controller.<BR>
     * he list of status is defined as a field of this class.
     * @return    :the the status of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
     </en>*/
    public int getStatusFlag() throws ReadWriteException
    {
        //<jp> DBからグループ・コントローラ情報取得</jp>
        //<en> Get the data of group controller from DB</en>
        loadGCinfo(false) ;

        return (wStatusFlag) ;
    }

    /**<jp>
     * グループ・コントローラの文字列表現を返します。
     * @return    グループ・コントローラの文字列表現
     </jp>*/
    /**<en>
     * Return the string representation of the group controller.
     * @return    string representation of the group controller
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100) ;
        try
        {
            buf.append("\nGroup Controller Number:" + String.valueOf(getNumber())) ;
            buf.append("\nGroup Controller IP:" + (getIpAddress().toString())) ;
            buf.append("\nGroup Controller Port:" + String.valueOf(getPort())) ;
            buf.append("\nGroup Controller Status:" + String.valueOf(getStatusFlag())) ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
        
        return (buf.toString()) ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * グループ・コントローラの情報をデータベースから取得します。
     * キーは、Group controller numberになります。
     * @param   lockd 検索したデータのロックを行う場合はtrue、行わない場合はfalseを指定します。
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws ReadWriteException グループ・コントローラ情報に指定されたホストの IP アドレスが見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Retrieve the information of the group controller from database.
     * Group controller number will be the key.
     * @param   lockd :Specify true if locaking the searched data, or false if not locking data.
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
     </en>*/
    private void loadGCinfo(boolean lockd) throws ReadWriteException
    {
        //-------------------------------------------------
        //<jp> 変数の宣言</jp>
        //<en> variable define</en>
        //-------------------------------------------------
        String tablename = wTableName;
        
        String sqltmpl = "SELECT"
                        + " controller_no"
                        + ", status_flag"
                        + ", ipaddress"
                        + ", port "
                        + " FROM " + tablename
                        + " WHERE controller_no = {0}" ;

        String sqllocktmpl = "SELECT"
                        + " controller_no"
                        + ", status_flag"
                        + ", ipaddress"
                        + ", port "
                        + " FROM " + tablename
                        + " WHERE controller_no = {0} FOR UPDATE" ;

        Statement stmt = null ;
        ResultSet rset = null ;

        Object[] fmtObj = new Object[1] ;
        String sqlstring ;

        //-------------------------------------------------
        //<jp> 処理開始</jp>
        //<en> process start</en>
        //-------------------------------------------------
        try 
        {
            fmtObj[0] = String.valueOf(wSelNumber) ;

            //<jp> 実行するSQL文作成</jp>
            //<en> create actual SQL string</en>
            if (lockd)
            {
                //<jp> 選択行をLockするSQL</jp>
                //<en> SQL which locks the selected line.</en>
                sqlstring = SimpleFormat.format(sqllocktmpl, fmtObj) ;
            }
            else
            {
                //<jp> 選択行をLockしないSQL</jp>
                //<en> SQL which will not lock the selected line.</en>
                sqlstring = SimpleFormat.format(sqltmpl, fmtObj) ;
            }
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);

            //<jp> 最初の行をfetchする</jp>
            //<en> fetch to first row</en>
            if (rset.next() == false)
            {
                Object[] tObj = new Object[1] ;
                tObj[0] = tablename;
                //<jp> 6026033=検索対象データがありません。テーブル名:{0}</jp>
                //<en> 6026033=There is no data corresponding to the search. Table:{0}</en>
                RmiMsgLogClient.write(6124002, LogMessage.F_ERROR, "GroupController", tObj);
                throw (new ReadWriteException()) ;
            }

            //<jp> 項目を取得する</jp>
            //<en> getting column data</en>
            wControllerNo = rset.getInt("controller_no") ;
            wStatusFlag = rset.getInt("status_flag") ;
            wGCPort = rset.getInt("port") ;
            try
            {
                wIpaddress = InetAddress.getByName(DBFormat.replace(rset.getString("ipaddress"))) ;
            }
            catch (UnknownHostException e)
            {
                String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
                //<jp>エラーをログファイルに落とす</jp>
                //<en>Record the error in the log file.</en>
                e.printStackTrace(wPW) ;
                Object[] tObj = new Object[2] ;
                tObj[0] = DBFormat.replace(rset.getString("ipaddress"));
                tObj[1] = stcomment + wSW.toString() ;
                //<jp> 6026036=指定されたホストのIPアドレスがみつかりません。ホスト名:{0}</jp>
                //<en> 6026036=Cannot find the IP address of the designated host. Host:{0}</en>
                RmiMsgLogClient.write(6126002, LogMessage.F_ERROR, "GroupController", tObj);
                throw (new ReadWriteException()) ;
            }
        }
        catch (SQLException e)
        {
            String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
            //<jp>エラーをログファイルに落とす</jp>
            //<en>Record the error in the log file.</en>
            e.printStackTrace(wPW) ;
            Object[] tObj = new Object[2] ;
            tObj[0] = new Integer(e.getErrorCode()) ;
            tObj[1] = stcomment + wSW.toString() ;
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
            throw (new ReadWriteException(e)) ;
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
                //<jp>エラーをログファイルに落とす</jp>
                //<en>Recording the errors in the log file.</en>
                e.printStackTrace(wPW) ;
                Object[] tObj = new Object[2] ;
                tObj[0] = new Integer(e.getErrorCode()) ;
                tObj[1] = stcomment + wSW.toString() ;
                //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
                //<en> 6007030=Database error occured. error code={0}</en>
                RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
                throw (new ReadWriteException(e)) ;
            }
        }
    }

    /**<jp>
     * グループ・コントローラの情報をデータベースへ反映します。
     * キーは、Group controller番号になります。
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Reflect the information of group controller to database.
     * Key is the Group controller number.
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     </en>*/
    private void updateGCinfo() throws ReadWriteException
    {
        //-------------------------------------------------
        //<jp> 変数の宣言</jp>
        //<en> variable define</en>
        //-------------------------------------------------
        String tablename = wTableName;
        String sqltmpl = "UPDATE " + tablename + " set "
                        + "status_flag = {1}"
                        + ", ipaddress = {2}"
                        + ", port = {3}"
                        + " WHERE controller_no = {0}" ;

        Statement stmt = null ;

        Object[] fmtObj = new Object[4] ;
        String sqlstring ;

        //-------------------------------------------------
        //<jp> 処理開始</jp>
        //<en> process start</en>
        //-------------------------------------------------
        try
        {
            fmtObj[0] = String.valueOf(wControllerNo);
            fmtObj[1] = String.valueOf(wStatusFlag) ;
            fmtObj[2] = "'" + wIpaddress.getHostName() + "'" ;
            fmtObj[3] = String.valueOf(wGCPort) ;
            
            //<jp> 実行するSQL文作成</jp>
            //<en> create actual SQL string</en>
            sqlstring = SimpleFormat.format(sqltmpl, fmtObj) ;

            stmt = wConn.createStatement();
            stmt.executeUpdate(sqlstring);
        }
        catch (SQLException e)
        {
            String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
            //<jp>エラーをログファイルに落とす</jp>
            //<en>Record the error in the log file.</en>
            e.printStackTrace(wPW) ;
            Object[] tObj = new Object[2] ;
            tObj[0] = new Integer(e.getErrorCode()) ;
            tObj[1] = stcomment + wSW.toString() ;
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
            throw (new ReadWriteException(e)) ;
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
                //<jp>エラーをログファイルに落とす</jp>
                //<en>Record the error in the log file.</en>
                e.printStackTrace(wPW) ;
                Object[] tObj = new Object[2] ;
                tObj[0] = new Integer(e.getErrorCode()) ;
                tObj[1] = stcomment + wSW.toString() ;
                //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
                //<en> 6007030=Database error occured. error code={0}</en>
                RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
                throw (new ReadWriteException(e)) ;
            }
        }
    }

    /**<jp>
     * 全てのグループ・コントローラ番号をデータベースから取得します。
     * @param conn  データベース接続用の<code>Connection</code>トランザクション制御は内部で
     * 行っていませんので、外部でCommitする必要があります。
     * @return グループ・コントローラ番号の配列
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Get number of all group controller out of database.
     * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
     * is not done internally, it requires the external commitment.
     * @return Array of group controller number
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     </en>*/
    private static int[] getGCNo(Connection conn) throws ReadWriteException
    {
        //<jp> Exception発生時のLogWriteに使用します。</jp>
        //<en> This will be used in LogWrite when Exception occurs.</en>
        StringWriter wsw = new StringWriter();
        //<jp> Exception発生時のLogWriteに使用します。</jp>
        //<en> This will be used in LogWrite when Exception occurs.</en>
        PrintWriter  wpw = new PrintWriter(wsw);
        //<jp> デリミタ</jp>
        //<en> Delimiter</en>
        String wDelim = MessageResource.DELIM ;
        String tablename = wTableName;
        
        //-------------------------------------------------
        //<jp> 変数の宣言</jp>
        //<en> variable define</en>
        //-------------------------------------------------
        String sqlstring = "SELECT "
                        + " CONTROLLER_NO"
                        + " FROM " + tablename
                        + " ORDER BY CONTROLLER_NO";

        Statement stmt = null ;
        ResultSet rset = null ;

        Vector wgcidVect = new Vector(10) ;
        int[] regGCNo ;

        //-------------------------------------------------
        //<jp> 処理開始</jp>
        //<en> process start</en>
        //-------------------------------------------------
        try
        {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sqlstring);

            //<jp> fetchを行い、ベクトルにセットする</jp>
            //<en> fetch and set to vector</en>
            while (rset.next())
            {
                //<jp> 項目を取得する</jp>
                //<en> getting column data</en>
                wgcidVect.add(new Integer(rset.getInt("CONTROLLER_NO"))) ;
            }

            //<jp> int型配列に値をセットする（復帰値として使用）</jp>
            //<en> set to array of int (for return value)</en>
            regGCNo = new int[wgcidVect.size()] ;
            for (int i = 0; i < regGCNo.length ; i++)
            {
                Integer wi = (Integer)wgcidVect.remove(0) ;
                regGCNo[i] = wi.intValue() ;
            }
        }
        catch (SQLException e)
        {
            String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
            //<jp>エラーをログファイルに落とす</jp>
            //<en>Record the error in the log file.</en>
            e.printStackTrace(wpw) ;
            Object[] tObj = new Object[2] ;
            tObj[0] = new Integer(e.getErrorCode()) ;
            tObj[1] = stcomment + wsw.toString() ;
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
            throw (new ReadWriteException(e)) ;
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
                //<jp>エラーをログファイルに落とす</jp>
                //<en>Record the error in the log file.</en>
                e.printStackTrace(wpw) ;
                Object[] tObj = new Object[2] ;
                tObj[0] = new Integer(e.getErrorCode()) ;
                tObj[1] = stcomment + wsw.toString() ;
                //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
                //<en> 6007030=Database error occured. error code={0}</en>
                RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
                throw (new ReadWriteException(e)) ;
            }
        }
        return (regGCNo) ;
    }
    
    /**<jp>
     * コントローラーNo.をセットします。
     * @param arg  グループコントローラー番号
     </jp>*/
    /**<en>
     * Set the number of group controller.
     * @param arg : number of group controller
     </en>*/
    public void setControllerNumber(int arg)
    {
        wSelNumber = arg;
    }

    /**<jp>
     * コントローラーNo.を取得します。
     * @return グループコントローラー番号
     </jp>*/
    /**<en>
     * Retrieve the number of group controller.
     * @return number of group controller
     </en>*/
    public int getControllerNumber()
    {
        return wSelNumber;
    }
}
//end of class

