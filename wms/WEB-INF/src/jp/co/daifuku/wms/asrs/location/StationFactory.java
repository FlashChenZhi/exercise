// $Id: StationFactory.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.lang.reflect.Constructor;
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.asrs.dbhandler.ASLocationSearchKey;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.StationTypeHandler;
import jp.co.daifuku.wms.base.dbhandler.StationTypeSearchKey;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.StationType;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;

/**<jp>
 * Stationクラスと、そのサブクラスを生成します。
 * このクラスでは、Stationおよび、そのサブクラスを区別せずに生成します。
 * 各ステーションサブクラスを直接生成する場合は、それぞれのHandlerクラスを利用してください。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class generates the Station classes and their subclasses.
 * In this class, they are generated without distinguishing between Station and subclass.
 * If directly generating each station subclass, please use respective Handler classes.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class StationFactory
        extends Object
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection with database
     </en>*/
    private Connection _conn;

    // Accessors -----------------------------------------------------
    /**<jp>
     * データベース・コネクションを設定します。
     * @param conn データベース・コネクション
     </jp>*/
    /**<en>
     * Sets the database connection.
     * @param conn :database connection
     </en>*/
    private void setConnection(Connection conn)
    {
        _conn = conn;
    }

    /**<jp>
     * データベース・コネクションを取得します。
     * @return データベース・コネクション
     </jp>*/
    /**<en>
     * Gets the database connection.
     * @return database connection
     </en>*/
    private Connection getConnection()
    {
        return _conn;
    }

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    /**<jp>
     * ステーション番号から、Stationインスタンスを生成します。
     * 該当のステーションのタイプによって、サブクラスが返されることがあります。
     * Stationのサブクラスを直接インスタンス化することが出来ない(どのサブクラスが
     * 担当すべきか分からない) ステーションのインスタンスを取得するために使用します。
     * 該当のステーションが見つからない場合は、null ポインタが返されます。
     * トランザクション制御は内部で行っていませんので、外部でCommitする必要があります。
     * @param conn  データベース接続用の<code>Connection</code>
     * @param snum  ステーション番号
     * @return Station(またはそのサブクラス)のインスタンス
     * @throws InvalidDefineException クラス定義が、正しくなかった場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 該当のクラスが見つからなかった場合に通知されます。
     </jp>*/
    /**<en>
     * Generates the Station instance from the station no.
     * It may return the subclass dpending on the type of corresponding station.
     * This will be used to obtain the instance of the station which the direct instantiation of its subclass
     * is not possible. Or rather when it is not clear which subclass should be used to work with.
     * If corresponding station cannot be found, it returns null pointer.
     * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
     * @param conn  :<code>Connection</code> to connect with database.<BR>
     * @param snum  :station no.
     * @return :instance of Station (or its subclass)
     * @throws InvalidDefineException :Notifies if the definition of the class was incorrect.
     * @throws ReadWriteException     :Notifies if error occured in connection with database.
     * @throws NotFoundException   :Notifies if such class was not found.
     </en>*/
    public static Station makeStation(Connection conn, String snum)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        StationFactory sf = new StationFactory(conn);
        return (sf.getStation(snum));
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用のコネクションを指定して、インスタンスを作成します。
     * @param conn データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Creates an instance by specifying the connection with database.
     * @param conn :Connection to connect with database
     </en>*/
    public StationFactory(Connection conn)
    {
        setConnection(conn);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * ステーション番号から、Stationインスタンスを取得します。
     * @param  snum 作成するステーション番号
     * @return 取得したStationインスタンス
     * @throws InvalidDefineException クラス定義が、正しくなかった場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 該当のクラスが見つからなかった場合に通知されます。
     </jp>*/
    /**<en>
     * Gets Station instance from the station no.
     * @param  snum :station no. to create
     * @return :Station instance obtained
     * @throws InvalidDefineException :Notifies when definition of the class was incorrect.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if such class was not found.
     </en>*/
    protected Station getStation(String snum)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        //-------------------------------------------------
        //<jp> 変数の宣言</jp>
        //<en> variable define</en>
        //-------------------------------------------------
        StationTypeSearchKey stKey = new StationTypeSearchKey();
        StationTypeHandler stHandler = new StationTypeHandler(getConnection());
        StationType stationType = null;
        Station tgtStation = null;
        Object stobj = null;

        try
        {
            // ステーション種別管理情報取得
            stKey.setStationNo(snum);
            stationType = (StationType)stHandler.findPrimary(stKey);

            //<jp> 指定されたNumberでステーションが見つからなかった場合は例外</jp>
            //<en> Exception if teh station was not found by Number specified.</en>
            if (stationType == null)
            {
                //<jp> 対象データはありませんでした。</jp>
                //<en> There is no objective data.</en>
                //<jp> 6026044=ステーションNo.{0}がテーブル{1}に見つかりません。</jp>
                //<en> 6026044=Station No.{0} is not found in table {1}.</en>
                Object[] tObj = new Object[2];
                tObj[0] = snum;
                tObj[1] = "StationType";
                RmiMsgLogClient.write(6026044, LogMessage.F_ERROR, "StationFactory", tObj);
                throw (new NotFoundException(WmsMessageFormatter.format(6026044, tObj)));
            }

            // ハンドラインスタンスの生成
            AbstractDBHandler handler = makeHandlerInstance(DBFormat.replace(stationType.getClassName()));

            //<jp> Create find key</jp>
            //<en> 検索キー作成</en>
            ASLocationSearchKey sskey = new ASLocationSearchKey(handler.getStoreName());
            sskey.setStationNo(snum);

            stobj = handler.findPrimary(sskey);

            //<jp> 指定されたNumberでステーションが見つからなかった場合は例外</jp>
            //<en> Exception if teh station was not found by Number specified.</en>
            if (stobj == null)
            {
                //<jp> 対象データはありませんでした。</jp>
                //<en> There is no objective data.</en>
                //<jp> 6026044=ステーションNo.{0}がテーブル{1}に見つかりません。</jp>
                //<en> 6026044=Station No.{0} is not found in table {1}.</en>
                Object[] tObj = new Object[2];
                tObj[0] = snum;
                tObj[1] = " ";
                RmiMsgLogClient.write(6026044, LogMessage.F_ERROR, "StationFactory", tObj);
                throw (new NotFoundException(WmsMessageFormatter.format(6026044, tObj)));
            }

            //<jp> ステーション以外の物が帰ってきたら異常</jp>
            //<en> Error if anything other than station returned.</en>
            if (stobj instanceof Station)
            {
                tgtStation = (Station)stobj;
            }
            else
            {
                Object[] tObj = new Object[1];
                tObj[0] = "Station";
                //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
                //<en> 6006008=Object other than {0} was returned.</en>
                RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "StationFactory", tObj);
                throw (new InvalidDefineException(WmsMessageFormatter.format(6006008, tObj)));
            }
        }
        catch (NoPrimaryException e)
        {
            //<jp> 同じIDのステーションが複数存在します。StationID={0}</jp>
            //<en> 2 or more stations with identical IDs exist. StationID={0}</en>
            //<jp> 6026031=同じIDのステーションが複数存在します。StationID=({0})</jp>
            //<en> 6026031=Multiple stations with the same ID exist. StationID=({0})</en>
            Object[] tObj = new Object[1];
            tObj[0] = snum;
            RmiMsgLogClient.write(6026031, LogMessage.F_ERROR, "StationFactory", tObj);
            throw (new InvalidDefineException(WmsMessageFormatter.format(6026031, tObj)));
        }

        return (tgtStation);
    }

    /**<jp>
     * クラス名から、<code>AbstractDBHandler</code>インスタンスを生成し、返します。
     * @param classname インスタンスを生成する対象のクラス名。
     * <p>例:<br>
     * "jp.co.daifuku.awc.dbhandler.StationHandler"
     * </p>
     * @return <code>AbstractDBHandler</code>
     * @throws InvalidDefineException AbstractDBHandler以外のインスタンスが生成された場合に通知されます。。
     * @throws InvalidDefineException インスタンスの生成に失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Generate the instance of <code>AbstractDBHandler</code> according to the name of the class and return.
     * @param classname :name of the class of which the instance should generate
     * <p>Example:<br>
     * "jp.co.daifuku.awc.dbhandler.StationHandler"
     * </p>
     * @return <code>AbstractDBHandler</code>
     * @throws InvalidDefineException :Notifies if any instance other than AbstractDBHandler was generated.
     * @throws InvalidDefineException :Notifies if generation of the instance failed.
     </en>*/
    protected AbstractDBHandler makeHandlerInstance(String classname)
            throws InvalidDefineException
    {
        try
        {
            //<jp> クラスオブジェクトのロード</jp>
            //<en> load class</en>
            Class lclass = Class.forName(classname);

            //<jp> コンストラクタの取得</jp>
            //<jp> set parameter types</jp>
            //<en> パラメータータイプをセット</en>
            //<en> set parameter types</en>
            Class[] typeparams = new Class[1];
            typeparams[0] = Class.forName("java.sql.Connection");
            Constructor cconst = lclass.getConstructor(typeparams);

            //<jp> インスタンス作成のパラメータをセット</jp>
            //<en> set actual parameter</en>
            Object[] tparams = new Object[1];
            tparams[0] = _conn;

            //<jp> オブジェクト取得</jp>
            //<en> getting Object</en>
            Object tgt = cconst.newInstance(tparams);
            if (tgt instanceof AbstractDBHandler)
            {
                return ((AbstractDBHandler)tgt);
            }
            else
            {
                Object[] tObj = new Object[1];
                tObj[0] = "AbstractDBHandler";
                //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
                //<en> 6006008=Object other than {0} was returned.</en>
                RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, getClass().getName(), tObj);
                throw (new InvalidDefineException(WmsMessageFormatter.format(6006008, tObj)));
            }
        }
        catch (Exception e)
        {
            //<jp>エラーログの出力処理も行う。</jp>
            //<en>Output of error log.</en>
            Object[] tObj = new Object[1];
            tObj[0] = classname;
            //<jp> 6006003=インスタンスの生成に失敗しました。クラス名={0} {1}</jp>
            //<en> 6006003=Failed to generate the instance. Class name={0} {1}</en>
            RmiMsgLogClient.write(new TraceHandler(6006003, e), getClass().getName(), tObj);
            throw (new InvalidDefineException(WmsMessageFormatter.format(6006003, tObj)));
        }
    }

    // Private methods --------------------------------------------------------------
}
//end of class

