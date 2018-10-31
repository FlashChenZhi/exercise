// $Id: IdProcess.java 6456 2009-12-14 09:29:51Z kanda $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ByteArray;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.CheckConnection;

/**<jp>
 * 下位コントローラから受信した情報を処理するためのスーパークラスです。
 * 各Idごとの処理は<code>run()</code>メソッドに記述する必要があります。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * @version $Revision: 6456 $, $Date: 2009-12-14 18:29:51 +0900 (月, 14 12 2009) $
 * @author  $Author: kanda $
 </jp>*/
/**<en>
 * This is the superclass to process data receivied from subcontroller.
 * IT is requried that the process of each ID should be described in <code>run()</code> method.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * @version $Revision: 6456 $, $Date: 2009-12-14 18:29:51 +0900 (月, 14 12 2009) $
 * @author  $Author: kanda $
 </en>*/
public abstract class IdProcess
        extends Thread
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**<jp>
     * 待ち時間のデフォルト(10秒)
     </jp>*/
    /**<en>
     * Default wait time (10 seconds)
     </en>*/
    private static final int DEFAULT_SLEEP_MSEC = 100;

    private static final int RECV_BUFFER_SIZE = 10;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**<jp>
     * データベース接続を保持します。
     </jp>*/
    /**<en>
     * Preserves the database connection.
     </en>*/
    private Connection _conn;

    /**<jp>
     * 受信電文をキャッシュするための変数。
     </jp>*/
    /**<en>
     * Variable to cache the communication message received.
     </en>*/
    private List<ByteArray> _receiveDataList;

    /**<jp>
     * 終了が指示されたかどうか保持する変数。
     </jp>*/
    /**<en>
     * Variable which preserves whether/not the termination has been instructed.
     </en>*/
    private boolean _isTerminate = false;

    /** 管理対象のAGC No. */
    private String _agcNo = GroupController.DEFAULT_AGC_NO;

    /** 同期用ミューテックス */
    private Object _mutex = new Object();
    
    // DFKLOOK ここから追加(DB再接続フラグ)
    /**<jp>
     * このフラグは、オブジェクト内で取得したDBコネクションが保持されているかの判断に使用します。
     * 使用する場合は、CheckConnectionクラスのcheckメソッドを呼出てください。
     * コネクションを失っている場合trueがセットされます。
     </jp>*/
    /**<en>
     * This flag is obtained in the DB object used to determine whether the connection isu maintained.
     * If you are using, please call the check methods of the CheckConnection class.
     * If object have lost connection is set to 'true'.
     </en>*/
    private boolean _dbConCheckFlag = false;
    // DFKLOOK ここまで

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 管理対象AGC No.にデフォルトを指定してインスタンスを生成します。
     */
    protected IdProcess()
    {
        super();
        _receiveDataList = new ArrayList<ByteArray>(RECV_BUFFER_SIZE);
        
        /* ADD Start 2009.12.08 */
        //データベースコネクションチェックフラグセット
        if ("1".equals(WmsParam.WMS_DB_CONNECT_CHECK))
        {
            _dbConCheckFlag = true;
        }
        /* ADD End 2009.12.08 */
    }

    /**<jp>
     * 引数で渡されたAGCNoをセットし、このクラスの初期化を行います。
     * @param agcNo AGCNo
     </jp>*/
    /**<en>
     * Sets the AGCNo passed through parameter, then initialize this class.
     * @param agcNo AGCNo
     </en>*/
    protected IdProcess(String agcNo)
    {
        this();
        setAgcNo(agcNo);
    }
    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**<jp>
     * 下位コントローラから受信した電文を設定し、処理を起動します。
     * @param ba  受信電文 (STX,ETX,Seq-No,BCCなどは含まれないようにしてください)
     * @return    Comment for return value
     </jp>*/
    /**<en>
     * Set the communication message received from subcontroller, then start up the process.
     * @param ba  :communication message received (please ensure to exclude STX,ETX,Seq-No and BCC.)
     </en>*/
    public void write(byte[] ba)
    {
        try
        {
            synchronized (_mutex)
            {
                _receiveDataList.add(new ByteArray(ba));
                _mutex.notifyAll();
            }
        }
        catch (Exception e)
        {
            //<jp> 6026050=コントロール系処理に電文を渡す際にエラーが発生しました。StackTrace={0}</jp>
            //<en> 6026050=Failed to deliver the text to control system process. StackTrace={0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026050, e), this.getClass().getName());
        }
    }

    /**<jp>
     * このスレッドに停止を指示します。
     </jp>*/
    /**<en>
     * Command to terminate this thread.
     </en>*/
    public void finish()
    {
        try
        {
            synchronized (_mutex)
            {
                setTerminate();
                _mutex.notifyAll();
            }
        }
        catch (Exception e)
        {
            //<jp> 6026051=コントロール系処理に停止を指示した際にエラーが発生しました。StackTrace={0}</jp>
            //<en> 6026051=Failed to instruct to stop the control system process. StackTrace={0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026051, e), this.getClass().getName());
        }
    }

    /**<jp>
     * 処理の流れを記述したメソッドです。各Idごとの詳細処理は、
     * <code>processReceivedInfo()</code>メソッドをオーバーライドする必要があります。
     </jp>*/
    /**<en>
     * This method is a description of process flow. COncerning the detailed process of each ID, 
     * it is requried to override the <code>processReceivedInfo()</code> method.
     </en>*/
    public void run()
    {
        try
        {
            while (!isTerminate())
            {
                //<jp> 情報を受け取った場合</jp>
                //<en> If the data was received,</en>
                try
                {
                    byte[] buff = read();

                    //<jp> 情報がない場合</jp>
                    //<en> If there is no data</en>
                    if (0 == buff.length)
                    {
                        // read next.
                        continue;
                    }

                    //<jp> 実際の処理を行う。異常発生時は、例外を受ける</jp>
                    //<en> Processing. If error occurs, it receives exceotions.</en>

                    // get connection or connect to DB
                    Connection conn = getConnection();

                    processReceivedInfo(buff);

                    // commit transaction.
                    conn.commit();
                }
                catch (Exception e)
                {
                    //<jp> 6024034=コントロールのID処理内で異常または警告が発生しました。StackTrace={0}</jp>
                    //<en> 6024034=Error or warning occurred in the control id process. StackTrace={0}</en>
                    RmiMsgLogClient.write(new TraceHandler(6024034, e), this.getClass().getName());

                    // rollback and disconnect from Database
                    disconnectDB();
                }
            }
        }
        finally
        {
            String name = getClass().getSimpleName();
            System.out.println(name + ":::::finally");
            // rollback and disconnect from Database
            disconnectDB();
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    /**
     * Connectionを返します。
     * @return Connectionを返します。
     * @throws ReadWriteException データベースへの接続に失敗
     */
    public Connection getConnection()
            throws ReadWriteException
    {
        try
        {
            //<jp> コネクションチェックメソッド</jp>
            // <en> Connection Check Method</en>
            CheckConnection chk = new CheckConnection();
            
            //<jp> データベース接続が行われていなければ、接続する</jp>
            //<en> If the database is not connected yet, connect;</en>
            if (null == _conn || _conn.isClosed())
            {
                _conn = WmsParam.getConnection();
            }
            //<jp> DB接続</jp>
            //<en> Reestablishes the connection with DB</en>
            if(true == chk.check(_conn, _dbConCheckFlag))
            {
                _conn = WmsParam.getConnection();
            }
        }
        catch (SQLException e)
        {
            // 6026054=コントロール系処理がデータベース操作中にエラーが発生しました。StackTrace={0}
            RmiMsgLogClient.write(new TraceHandler(6026054, e), getClass().getName());

            throw new ReadWriteException(e);
        }
        return _conn;
    }

    /**
     * Connectionを設定します。
     * @param conn Connection
     */
    public void setConnection(Connection conn)
    {
        _conn = conn;
    }

    /**
     * agcNoを返します。
     * @return agcNoを返します。
     */
    public String getAgcNo()
    {
        return _agcNo;
    }

    /**
     * agcNoを設定します。
     * @param agcNo agcNo
     */
    public void setAgcNo(String agcNo)
    {
        _agcNo = agcNo;
    }

    /**
     * 終了指示状態を返します。
     * @return 終了指示のときtrue
     */
    public boolean isTerminate()
    {
        return _isTerminate;
    }

    /**
     * 終了指示を設定します。
     */
    public void setTerminate()
    {
        _isTerminate = true;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**<jp>
     * 実際の処理を行うメソッドです。サブクラスでオーバーライドする必要があります。
     * トランザクションはコミット・ロールバックしません。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * This method actually carries out the process. Overriding in subclass is necessary.
     * The transaction will not commit or rollback.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    protected abstract void processReceivedInfo(byte[] rdt)
            throws Exception;

    /**<jp>
     * 設定された受信電文を取得します。情報がない場合は null が返されます。
     * @return    受信電文 (STX,ETX,Seq-No,BCCなどは含まれません)。
     *
     </jp>*/
    /**<en>
     * Retrieves the received communication message which has been set. Or it returns null if there is no information.
     * @return    communication message (STX,ETX,Seq-No or BCC are not included.)
     *
     </en>*/
    private byte[] read()
    {
        try
        {
            List<ByteArray> rdataList = _receiveDataList;
            synchronized (_mutex)
            {
                while (!isTerminate())
                {
                    if (0 == rdataList.size())
                    {
                        // no data received, sleep
                        _mutex.wait(getWaitMsec());
                    }
                    else
                    {
                        // data received
                        return rdataList.remove(0).getBytes();
                    }
                }
            }
        }
        catch (Exception e)
        {
            //<jp> 6026055=コントロール系処理が受信電文を取得中にエラーが発生しました。StackTrace={0}</jp>
            //<en> 6026055=Failed to acquire received text in control system process. StackTrace={0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026055, e), this.getClass().getName());
        }

        // termination
        return new byte[0];
    }

    /**<jp>
     * 異常発生時に、リトライするための待ち時間を取得します。
     * 秒数の指定は リソース・ファイルから取得されます。(CONTROL_SLEEP_SEC)
     * @return   待ち時間(秒)
     </jp>*/
    /**<en>
     * Retrieves the wait time to retry when error occured.
     * Designation of teh number of seconds will be gained from resource file.(CONTROL_SLEEP_SEC)
     * @return   wait time (second)
     </en>*/
    protected int getWaitMsec()
    {
        int wsec = DEFAULT_SLEEP_MSEC; // default sleep msec.
        try
        {
            wsec = WmsParam.CONTROL_SLEEP_SEC; // by milli seconds.
        }
        catch (Exception e)
        {
            //<jp> 6026057=コントロール系処理が異常発生用のスリープ時間を取得する際にエラーが発生しました。StackTrace={0}</jp>
            //<en> 6026057=Error occurred in control system process. StackTrace={0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026057, e), this.getClass().getName());
        }
        return wsec;
    }

    /**
     * データベースコネクションをロールバックします。<br>
     * 対象のコネクションは、このインスタンスフィールドのコネクションです。
     */
    protected void disconnectDB()
    {
        try
        {
            Connection conn = getConnection();
            boolean isClosed = (null == conn) || conn.isClosed();
            // 未接続ならば何もしない
            if (isClosed)
            {
                return;
            }
            conn.rollback();
            conn.close();
        }
        catch (Exception e)
        {
            //<jp> 6026054=コントロール系処理がデータベース操作中にエラーが発生しました。StackTrace={0}</jp>
            //<en> 6026054=Failed to operate the database in control system process. StackTrace={0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026054, e), getClass().getName());
        }
    }

    /**
     * クラスの短縮名を返します。
     * @return クラス名(短縮形)
     */
    protected String getClassName()
    {
        return getClass().getSimpleName();
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
        return "$Id: IdProcess.java 6456 2009-12-14 09:29:51Z kanda $";
    }
}
