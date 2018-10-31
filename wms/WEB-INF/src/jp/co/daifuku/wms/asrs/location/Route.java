// $Id: Route.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 搬送ルート情報を保持します。
 * 搬送ルートの定義はリソース・ファイルに納められており、既に定義されているルート情報を
 * 取得する場合は、直接インスタンスを作成せずに、<code>getInstance</code>メソッドを
 * 利用してください。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class preserves the carry route information.
 * The definition of carry route is stored in resource file. If the route information which have been
 * already defined is needed, please do not directly create an instance but use <code>getInstance</code> method.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class Route
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ルートの状態を表すフィールド。<code>ACTIVE</code>は利用可能な状態を表します。
     </jp>*/
    /**<en>
     * Field of route status - <code>ACTIVE</code> :available
     </en>*/
    public static final int ACTIVE = 1;

    /**<jp>
     * ルートの状態を表すフィールド。<code>NOT_ACTIVE_OFFLINE</code>は設備切離しによる利用不可能な状態を表します。
     </jp>*/
    /**<en>
     * Field of route status- <code>NOT_ACTIVE_OFFLINE</code>
     * :Not available due to Equipments off-line.
     </en>*/
    public static final int NOT_ACTIVE_OFFLINE = 0;

    /**<jp>
     * ルートの状態を表すフィールド。<code>NOT_ACTIVE_FAIL</code>は設備異常による利用不可能な状態を表します。
     </jp>*/
    /**<en>
     * Field of route status - <code>NOT_ACTIVE_FAIL</code>
     * : Unavailable due to Equipment error
     </en>*/
    public static final int NOT_ACTIVE_FAIL = 2;

    /**<jp>
     * ルートの状態を表すフィールド。<code>UNKNOWN</code>はルートチェック未実施の状態を表します。
     </jp>*/
    /**<en>
     * Field of route status - <code>UNKNOWN</code>
     * : Route check undone
     </en>*/
    public static final int UNKNOWN = -1;

    /**<jp>
     * ルートの状態を表すフィールド。<code>NOTFOUND</code>指定されたルート定義が見つからない状態を表します。
     </jp>*/
    /**<en>
     * Field of route status - <code>NOTFOUND</code>
     * : The specified route definition cannot be found.
     </en>*/
    public static final int NOTFOUND = -2;

    /**<jp>
     * ルートの状態を表すフィールド。<code>PROHIBITED</code>禁止ルートを表します。
     </jp>*/
    public static final int PROHIBITED = -3;

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用コネクション
     </jp>*/
    /**<en>
     * Connection with database
     </en>*/
    private Connection _conn;

    /**<jp>
     * ルートチェックの結果を保存する変数
     </jp>*/
    /**<en>
     * Variable which preserves the result of route check
     </en>*/
    private boolean _status = false;

    /**<jp>
     * ルートチェックの結果を保存する変数
     </jp>*/
    /**<en>
     * Variable which preserves the result of route check
     </en>*/
    private int _routeStatus = UNKNOWN;

    /**<jp>
     * ルートを構成するステーションを保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves stations which compose the route
     </en>*/
    private Station[] _stations;

    /**<jp>
     * ルートの開始ステーション番号
     </jp>*/
    /**<en>
     * The station no. which starts the route
     </en>*/
    private String _startStationNo;

    /**<jp>
     * ルートの終了ステーション番号
     </jp>*/
    /**<en>
     * The station no. which is the end of the route
     </en>*/
    private String _endStationNo;

    /**<jp>
     * ルートファイルを読み込むための LineNumberReader
     </jp>*/
    /**<en>
     * LineNumberReader to read the route file
     </en>*/
    private LineNumberReader _routeFileReader;

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
        return ("$Revision: 8053 $,$Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $");
    }

    /**<jp>
     * ステーション番号から、このステーションが接続されている、元ステーションを返します。
     * 複数の元ステーションが定義されている場合は、はじめに見つかった物を返します。
     * @param toStnum 検索の対象となるステーションNo
     * @return toStnumが接続されている、元ステーション
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Based on the station no., return the previous station to which this station is connected.
     * If more than 2 previous stations are defined, return one which was firstly found.
     * @param toStnum :station no. to be searched
     * @return :previous station which toStnum is connected to
     * @throws ReadWriteException  : Notifies if file I/O error occured.
     </en>*/
    public static String getConnectorStation(String toStnum)
            throws ReadWriteException
    {
        RoutePiece rp;

        try
        {
            rp = new RoutePiece(new LineNumberReader(new FileReader(WmsParam.ROUTE_FILE)), toStnum, toStnum);
        }
        catch (IOException e)
        {
            //<jp> 6026030=ルート定義ファイル読み込みエラー。詳細=({0})</jp>
            //<en> 6026030=Route definition file read error. Detail=({0})</en>
            Object[] tObj = new Object[1];
            tObj[0] = e.getMessage();
            RmiMsgLogClient.write(new TraceHandler(6026030, e), "Route", tObj);
            throw new ReadWriteException(e);
        }

        return rp.getConnectorStation();
    }

    /**<jp>
     * 指定されたステーションNoから、このステーションが接続されている、移動先ステーションを返します。
     * このメソッドはコの字フリーステーションで対となる入庫側ステーションを探すときなどに使用します。
     * @param toStnum 検索の対象となるステーションNo
     * @return toStnumの対となる移動先ステーションNo
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Based on the specified statin no., it returns the move-destinated station that this station is connected to.
     * This method wil be used when searching the storage station to be paired for free station of U-shaped conveyor.
     * @param toStnum :station no. to be searched
     * @return :station no. of move-destination, to be a pair with toStnum.
     * @throws ReadWriteException  : Notifies if file I/O error occured.
     </en>*/
    public static String getConnectorStationTo(String toStnum)
            throws ReadWriteException
    {
        RoutePiece rp;

        try
        {
            rp = new RoutePiece(new LineNumberReader(new FileReader(WmsParam.ROUTE_FILE)), toStnum, toStnum);
        }
        catch (IOException e)
        {
            //<jp> 6026030=ルート定義ファイル読み込みエラー。詳細=({0})</jp>
            //<en> 6026030=Route definition file read error. Detail=({0})</en>
            Object[] tObj = new Object[1];
            tObj[0] = e.getMessage();
            RmiMsgLogClient.write(new TraceHandler(6026030, e), "Route", tObj);
            throw new ReadWriteException(e);
        }

        return rp.getConnectorStationTo();
    }

    /**<jp>
     * ステーション番号のFrom,Toから、<code>Route</code>のインスタンスを
     * 作成し、返します。
     * @param con ステーション・インスタンスを生成するためのデータベースコネクション
     * @param from <code>Route</code>の元ステーション（String型）
     * @param to <code>Route</code>の先ステーション（String型）
     * @return 引数に基づいて作成される<code>Route</code>オブジェクト
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Create the instance of <code>Route</code> based on the From,To of station no., and return.
     * @param conn :database connection in order to generate the station instance
     * @param from :previoud station of from <code>Route</code>(String type)
     * @param to :next station of to <code>Route</code>(String type)
     * @return :object of <code>Route</code> created according to parameter
     * @throws ReadWriteException  : Notifies if file I/O error occured.
     </en>*/
    public static Route getInstance(Connection conn, String from, String to)
            throws ReadWriteException
    {
        Route nRoute = new Route(conn);
        nRoute.setStartStationNo(from);
        nRoute.setEndStationNo(to);

        try
        {
            nRoute.setLineNumberReader(new LineNumberReader(new FileReader(WmsParam.ROUTE_FILE)));
        }
        catch (IOException e)
        {
            //<jp> 6026030=ルート定義ファイル読み込みエラー。詳細=({0})</jp>
            //<en> 6026030=Route definition file read error. Detail=({0})</en>
            Object[] tObj = new Object[1];
            tObj[0] = e.getMessage();
            RmiMsgLogClient.write(new TraceHandler(6026030, e), "Route", tObj);
            throw new ReadWriteException(e);
        }

        return (nRoute);
    }

    /**<jp>
     * ステーションのFrom,Toから、<code>Route</code>のインスタンスを
     * 作成し、返します。
     * @param con ステーション・インスタンスを生成するためのデータベースコネクション
     * @param from <code>Route</code>の元ステーション（Station型）
     * @param to <code>Route</code>の先ステーション（Station型）
     * @return 引数に基づいて作成される<code>Route</code>オブジェクト
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Generate the instance of <code>Route</code> based on the From,To of the station, and return.
     * @param conn :database connection in order to generate the station instance.
     * @param from :previous station of from <code>Route</code>(Station type)
     * @param to :next station of to <code>Route</code>(Station type)
     * @return :object of <code>Route</code> created according to parameter
     * @throws ReadWriteException  : Notifies if file I/O error occured.
     </en>*/
    public static Route getInstance(Connection conn, Station from, Station to)
            throws ReadWriteException
    {
        Route nRoute = getInstance(conn, from.getStationNo(), to.getStationNo());
        return (nRoute);
    }

    /**<jp>
     * ステーションのFrom,Toから、<code>Route</code>のインスタンスを
     * 作成し、返します。
     * @param con ステーション・インスタンスを生成するためのデータベースコネクション
     * @param from <code>Route</code>の元ステーション（Station型）
     * @param to <code>Route</code>の先ステーション（String型）
     * @return 引数に基づいて作成される<code>Route</code>オブジェクト
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Creates the instance of <code>Route</code> based on the From,To of the station, and return.
     * @param conn :database connection in order to generate the station instance
     * @param from : previous station of from <code>Route</code>(Station type)
     * @param to :next station of to <code>Route</code>(String type)
     * @return :object of <code>Route</code> created according to parameter
     * @throws ReadWriteException  : Notifies if file I/O error occured.
     </en>*/
    public static Route getInstance(Connection conn, Station from, String to)
            throws ReadWriteException
    {
        Route nRoute = getInstance(conn, from.getStationNo(), to);
        return (nRoute);
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 空の搬送ルートを持つインスタンスを作成します。既に定義されている搬送ルートを
     * 持つインスタンスが必要な場合は、<code>getInstance</code>メソッドを利用してください。
     * @param conn ステーション・インスタンスを生成するためのデータベースコネクション
     </jp>*/
    /**<en>
     * Generates the instance which has an empty carry route. If the instance which has already
     * defined carry route is needed, please use <code>getInstance</code> method.
     * @param conn :database connection in order to generate the station instance
     </en>*/
    public Route(Connection conn)
    {
        _conn = conn;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ルートの状態を確認します。利用可能であればtrue、それ以外の状態であればfalseを返します。
     * また、ルートの状態（正常、切離し、異常）を記録します。
     * この内容は<code>getRouteStatus()</code>で参照することができます。
     * @return    ルートの状態
     * @throws InvalidDefineException 搬送ルート定義内のステーションが、見つからなかった場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ReadWriteException 搬送ルート定義の読込みで異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Checks the route status. It returns 'true' if available and 'false' if unavailable.
     * It also records the status of the route (normal, of-line, error).
     * One can get a reference for the detail in <code>getRouteStatus()</code>.
     * @return    :status of the route
     * @throws InvalidDefineException :Notifies if the station defined in the carry route was not found.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ReadWriteException :Notifies if error occured in reading the carry route definition.
     </en>*/
    public boolean check()
            throws InvalidDefineException,
                ReadWriteException
    {
        //<jp> ルートチェックを行い、正常であればtrueそれ以外の状態であればfalseを返す。</jp>
        //<en> Checks the route; it returns 'true' if is is correct and 'false' if not.</en>
        switch (checkStatus())
        {
            case ACTIVE:
                _routeStatus = ACTIVE;
                _status = true;
                break;
            case NOT_ACTIVE_OFFLINE:
                _routeStatus = NOT_ACTIVE_OFFLINE;
                _status = false;
                break;
            case NOT_ACTIVE_FAIL:
                _routeStatus = NOT_ACTIVE_FAIL;
                _status = false;
                break;
            case NOTFOUND:
                _routeStatus = NOTFOUND;
                _status = false;
                break;
            case PROHIBITED:
                _routeStatus = PROHIBITED;
                _status = false;
                break;
            default:
                _routeStatus = UNKNOWN;
                _status = false;
                break;
        }
        return _status;
    }

    /**<jp>
     * 二つのステーションが、直接接続されているかどうかを返します。
     * @return  直接接続されている場合は True
     * @throws ReadWriteException 搬送ルート定義の読込みで異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Returns whether/not these 2 stations are directly connected.
     * @return  :True if directly connected.
     * @throws ReadWriteException :Notifies if error occured in reading the carry route definition.
     </en>*/
    public boolean isDirect()
            throws ReadWriteException
    {
        RoutePiece rp = new RoutePiece(_routeFileReader, _startStationNo, _startStationNo);
        String[] stations = rp.getConnectedStations();
        if (stations != null)
        {
            for (int i = 0; i < stations.length; i++)
            {
                if (stations[i].equals(_endStationNo))
                {
                    return (true);
                }
            }
        }
        return (false);
    }

    /**<jp>
     * ルートチェック処理の結果セットされたルートの状態（異常、切離しなど）を返します。<BR>
     * ルートチェックを行っていない場合は<code>UNKNOWN</code>が返ります。<BR>
     * このメソッドの呼び出しで有効な値を取得するためには<code>check</code>を呼出す必要があります。<BR>
     * @return ルートチェックの結果。ルートチェックを行っていない場合は<code>UNKNOWN</code>が返ります。
     </jp>*/
    /**<en>
     * Returns the status of route (error, off-line, etc.) which have been set as a result of route check.<BR>
     * If the route check is not done, it returns <code>UNKNOWN</code>.<BR>
     * In order to retrieve the valid value by calling method, it is alos necessary to call <code>check</code>.<BR>
     * @return :result of route check. If the route check is not done, it returns <code>UNKNOWN</code>.
     </en>*/
    public int getRouteStatus()
    {
        return _routeStatus;
    }

    /**<jp>
     * このインスタンスが保持するルートチェック処理の結果を返します。<BR>
     * ルートチェックを行わずに再度チェック結果を取得したい場合に使用します。
     * このメソッドの呼び出しで有効な値を取得するためには<code>check</code>を呼出す必要があります。<BR>
     * @return ルートチェックの結果。ルートが使用可能な場合はtrue、使用不可能な場合はfalseを返します。
     * ルートチェックを行っていない場合はfalseが返ります。
     </jp>*/
    /**<en>
     * Returns the result of route check process that this instance preserves.<BR>
     * This will be used when retrieving the check results again without checking the route.
     * In order to retrieve the valid value by calling method, it is alos necessary to call <code>check</code>.<BR>
     * @return :result of route check. It retunrs 'true' if the route is available, or 'false' if unavailable.
     * It returns 'false' if route check is not done.
     </en>*/
    public boolean getStatus()
    {
        return _status;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * ルートの状態を取得します。状態の一覧は、このクラスのフィールドとして定義されています。
     * @return ルートの状態
     * @throws InvalidDefineException 搬送ルート定義内のステーションが、見つからなかった場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ReadWriteException 搬送ルート定義の読込みで異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieves the status of the route. The list of status is defined as a field of this class.
     * @return :status of the route
     * @throws InvalidDefineException :Notifies if the station defined in the carry route was not found.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ReadWriteException :Notifies if error occured in reading the carry route definition.
     </en>*/
    protected int checkStatus()
            throws InvalidDefineException,
                ReadWriteException
    {
        try
        {
            //<jp> ステーション一覧を取得する(最新情報が必要なので、毎回取得)</jp>
            //<en> Retrieving the list of station.(to be retrieved evey time, as the latest information is required)</en>
            List<String> stationList = new ArrayList<String>();

            RoutePiece rp = new RoutePiece(_routeFileReader, _startStationNo, _startStationNo);
            int[] status = new int[1];
            status[0] = 1;
            if (!rp.exists(_endStationNo, new ArrayList<String>(), stationList, _conn, status))
            {
                // ルート上の状態をreturnするように修正
                switch (status[0])
                {
                    case NOT_ACTIVE_OFFLINE:
                        return NOT_ACTIVE_OFFLINE;
                    case NOT_ACTIVE_FAIL:
                        return NOT_ACTIVE_FAIL;
                }
                //<en> The specified carry route does not exist in the definition.</en>
                return NOTFOUND;
            }

            //<jp> RoutePieceインスタンスは搬送先ステーションを返さないので、追加する。</jp>
            //<en> As RoutePiece instance does not return tje receiving station, add.</en>
            stationList.add(0, _endStationNo);

            _stations = new Station[stationList.size()];
            if (_stations.length < 1)
            {
                //<jp> ルートチェック用のステーション情報なし</jp>
                //<en> There is no station information for the route check.</en>
                return NOTFOUND;
            }

            for (int i = 0; i < _stations.length; i++)
            {
                String st = (String)(stationList.remove(0));
                _stations[i] = StationFactory.makeStation(_conn, st);
            }

            //<jp> ステーションの状態チェック</jp>
            //<jp> ステーションの状態に異常と切離しが含まれている場合、切離しを優先して記憶</jp>
            //<en> Checking the status of station</en>
            //<en> If error or off-line is included in the status of the station, record them </en>
            //<en> with a priority for off-line.</en>
            int rstat = ACTIVE;
            for (int i = 0; i < _stations.length; i++)
            {
                String sts = _stations[i].getStatus();
                if (Station.STATION_STATUS_ERROR.equals(sts))
                {
                    if (rstat != NOT_ACTIVE_OFFLINE)
                    {
                        rstat = NOT_ACTIVE_FAIL;
                    }
                }
                else if (Station.STATION_STATUS_DISCONNECTED.equals(sts))
                {
                    rstat = NOT_ACTIVE_OFFLINE;
                }
            }

            return rstat;
        }
        catch (NotFoundException e)
        {
            //<jp> From StationFactory</jp>
            //<en> From StationFactory</en>
            throw (new InvalidDefineException(e.getMessage()));
        }
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * ルートの開始ステーション番号を設定します。
     * @param sst ルートの開始ステーション番号
     </jp>*/
    /**<en>
     * Sets the station no. from which the route to start.
     * @param sst :station no. the route starts from
     </en>*/
    private void setStartStationNo(String sst)
    {
        _startStationNo = sst;
    }

    /**<jp>
     * ルートの終了ステーション番号を設定します。
     * @param est ルートの終了ステーション番号
     </jp>*/
    /**<en>
     * Sets the station no. which the route ends at
     * @param est :station no. the route ends by
     </en>*/
    private void setEndStationNo(String est)
    {
        _endStationNo = est;
    }

    /**<jp>
     * ルートファイルを読み込むための<code>LineNumberReader</code>を設定します。
     * @param lnr LineNumberReader
     </jp>*/
    /**<en>
     * Sets <code>LineNumberReader</code> in order to read the route file.
     * @param lnr LineNumberReader
     </en>*/
    private void setLineNumberReader(LineNumberReader lnr)
    {
        _routeFileReader = lnr;
    }
}
//end of class

