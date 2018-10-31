// $Id: Route.java 87 2008-10-04 03:07:38Z admin $
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
import java.util.StringTokenizer;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 指定搬送ルート情報を保持します。
 * 指定搬送ルートの定義はリソース・ファイルに納められており、既に定義されているルート情報を
 * 取得する場合は、直接インスタンスを作成せずに、<code>getInstance</code>メソッドを
 * 利用してください。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
public class ReservedRoute
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


    /**<jp>
     * ファイル内のコメント文字
     </jp>*/
    /**<en>
     * Comment characters in the file
     </en>*/
    public static final char COMMENT = '#';

    /**<jp>
     * ファイル内の区切り文字 (定義ステーションと、接続ステーション間)
     </jp>*/
    /**<en>
     * Delimiter in the file (between defined station and connected station)
     </en>*/
    public static final String DELM_DEFINEST = ":";

    /**<jp>
     * ファイル内の区切り文字 (接続ステーションの羅列)
     </jp>*/
    /**<en>
     * Delimiter in the file (row of connected stations)
     </en>*/
    public static final String DELM_CONNECTST = ",";


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
        return ("");
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
    public ReservedRoute(Connection conn)
    {
        _conn = conn;
    }

    // Public methods ------------------------------------------------

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
    public static ReservedRoute getInstance(Connection conn, String from, String to)
            throws ReadWriteException
    {
        ReservedRoute nRoute = new ReservedRoute(conn);
        nRoute.setStartStationNo(from);
        nRoute.setEndStationNo(to);

        try
        {
            nRoute.setLineNumberReader(new LineNumberReader(new FileReader(WmsParam.RESERVED_ROUTE_FILE)));
        }
        catch (IOException e)
        {
            //<jp> 6026027=指定ルート定義ファイル読み込みエラー。詳細=({0})</jp>
            //<en> 6026027=Route definition file read error. Detail=({0})</en>
            Object[] tObj = new Object[1];
            tObj[0] = e.getMessage();
            return null;
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
    public static ReservedRoute getInstance(Connection conn, Station from, Station to)
            throws ReadWriteException
    {
        ReservedRoute nRoute = getInstance(conn, from.getStationNo(), to.getStationNo());
        return (nRoute);
    }

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
            String spRoute = _startStationNo + "-" + _endStationNo;
            //<jp> ステーション一覧を取得する(最新情報が必要なので、毎回取得)</jp>
            //<en> Retrieving the list of station.(to be retrieved evey time, as the latest information is required)</en>

            int rstat = ACTIVE;

            List<String[]> list = getSpRoute(spRoute);

            if (list.size() == 0)
            {
                //<jp> 指定された搬送ルートが定義情報内に存在しない</jp>
                return NOTFOUND;
            }
            else
            {
                // 指定ルートが存在する場合、禁止ルートに指定されていないかチェック
                ProhibitedRoute pRoute = ProhibitedRoute.getInstance(_startStationNo, _endStationNo);
                if (!StringUtil.isBlank(pRoute))
                {
                    for (int j = 0; j < list.size(); j++)
                    {
                        String[] stList = list.get(j);
                        // 定義の中身がカラの場合
                        if (ArrayUtil.isEmpty(stList))
                        {
                            rstat = UNKNOWN;
                            continue;
                        }
                        // 禁止ルートのチェックを行います。
                        if (!pRoute.check(stList))
                        {
                            rstat = PROHIBITED;
                            continue;
                        }
                        else
                        {
                            // SpRouteを保持する
                            _stations = new Station[stList.length];

                            // ステーションNoからStation情報を取得する。
                            for (int i = 0; i < stList.length; i++)
                            {
                                _stations[i] = StationFactory.makeStation(_conn, stList[i]);
                            }
                            // ステーションの状態をチェック
                            for (int i = 0; i < _stations.length; i++)
                            {
                                String sts = _stations[i].getStatus();
                                if (Station.STATION_STATUS_ERROR.equals(sts))
                                {
                                    if (rstat != NOT_ACTIVE_OFFLINE)
                                    {
                                        rstat = NOT_ACTIVE_FAIL;
                                    }
                                    continue;
                                }
                                else if (Station.STATION_STATUS_DISCONNECTED.equals(sts))
                                {
                                    rstat = NOT_ACTIVE_OFFLINE;
                                    continue;
                                }
                            }
                            // 状態のチェックですべてOkの場合
                            rstat = ACTIVE;
                        }
                    }
                }
            }
            return rstat;
        }
        catch (NotFoundException e)
        {
            return NOTFOUND;
        }
    }

    /**
     * 開始Station-終了StationのSpRouteを取得する。
     * @param routeCode
     * @return
     * @throws ReadWriteException
     */
    protected List<String[]> getSpRoute(String routeCode)
            throws ReadWriteException
    {
        try
        {
            //<jp> 通常のファイルはマークサポートされている。先頭位置にマークする</jp>
            //<en> Regular files are mark-supported. Marks in start positions.</en>
            _routeFileReader.mark(10240);

            List<String[]> list = new ArrayList<String[]>();

            String line;
            do
            {
                line = _routeFileReader.readLine();
                if (line == null)
                {
                    break;
                }

                //<jp> 主定義ステーションが検索対象ステーションであれば,定義ステーションを取り込む</jp>
                //<en> If the searched station is found, get the defined station data in that station.</en>
                if (getMainStation(line) == null)
                {
                    continue;
                }
                if (getMainStation(line).equals(routeCode))
                {
                    String[] dst = getDefinedStations(line);
                    list.add(dst);
                }
                else
                {
                    continue;
                }
            } while (line != null);

            return list;
        }
        catch (IOException e)
        {
            //<jp> 6026027=特殊ルート定義ファイル読み込みエラー。詳細=({0})</jp>
            //<en> 6026027=Route definition file read error. Detail=({0})</en>
            Object[] tObj = new Object[1];
            tObj[0] = e.getMessage();
            RmiMsgLogClient.write(new TraceHandler(6026027, e), this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
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

    /**<jp>
     * ルート定義ファイルから読み込んだ情報のうち、主定義ステーションを取り出して返す。
     * @param line 定義ファイルの情報
     * @return 主定義ステーション(コメント行の場合は null を返す)
     </jp>*/
    /**<en>
     * Get main-defined stations from information loaded from the route definition file, then return.
     * @param line :information of definition file
     * @return :main-defined station (returns null if it is the comment lines.)
     </en>*/
    private String getMainStation(String line)
    {
        StringTokenizer mtoken;
        if (line.charAt(0) == COMMENT)
        {
            return (null);
        }
        mtoken = new StringTokenizer(line, DELM_DEFINEST, false);
        return (mtoken.nextToken());
    }

    /**<jp>
     * ルート定義ファイルから読み込んだ情報のうち、接続定義ステーションを取り出して返す。
     * @param line 定義ファイルの情報
     * @return 接続定義ステーション(コメント行の場合は null を返す)
     </jp>*/
    /**<en>
     * Get station defined as conenct to point from the route definition file loaded, then return.
     * @param line :information of definition file
     * @return :station defined as connect-to point (or it returns null if it is the comment lines.)
     </en>*/
    private String[] getDefinedStations(String line)
    {
        try
        {
            StringTokenizer deftoken;
            StringTokenizer mtoken;

            if (line.charAt(0) == COMMENT)
            {
                return (null);
            }
            mtoken = new StringTokenizer(line, DELM_DEFINEST, false);

            String[] lines = new String[mtoken.countTokens()];
            for (int i = 0; i < lines.length; i++)
            {
                lines[i] = new String();
                lines[i] = mtoken.nextToken();
            }

            if (!Boolean.valueOf(lines[2]))
            {
                return null;
            }
            deftoken = new StringTokenizer(lines[1], DELM_CONNECTST, false);

            String[] rStations = new String[deftoken.countTokens()];
            for (int i = 0; i < rStations.length; i++)
            {
                rStations[i] = new String();
                rStations[i] = deftoken.nextToken();
            }

            return (rStations);
        }
        catch (Exception e)
        {
            // 定義が不正の場合
            //<jp> 6026028=指定ルート定義が不正です。</jp>
            RmiMsgLogClient.write(6026028, this.getClass().getName());
            return null;
        }
    }

}
//end of class

