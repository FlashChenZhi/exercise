// $Id: RoutePiece.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.common.text.StringUtil;

/**<jp>
 * ルートチェックの為の、個々のステーションのつながりを保持するためのクラスです。
 * 定義ファイルへのアクセスは、LineNumberReaderを利用します。ファイルのオープンと
 * クローズは、このクラスでは行っていません。
 * <p><pre>LineNumberReaderの例:
 * LineNumberReader nr = new LineNumberReader(new FileReader("routedef.txt")) ;</pre></p>
 * <p>定義ファイルの形式は、以下のようになります。</p>
 * <p>FROM:TOST1,TOST2,TOST3</p>
 * <p>FROMは、定義ステーションで、FROMに接続されているステーションを","で区切って羅列します。
 * 定義は方向性を意識してください。(FROMからTOへは搬送できなければならない)</p>
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
 * This class preserves the relations between individual stations for the route checks.
 * LineNumberReader is used for the access to definition file. Opening and closdure of
 * the file is not implemented in this class.
 * <p><pre>example pf LineNumberReader:
 * LineNumberReader nr = new LineNumberReader(new FileReader("routedef.txt")) ;</pre></p>
 * <p>Format of the definition file is as follows.</p>
 * <p>FROM:TOST1,TOST2,TOST3</p>
 * <p>FROM is a defining station; it rows stations which are connected to FROM, delimiting
 * respective station with ",".
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class RoutePiece
        extends Object
{
    // Class fields --------------------------------------------------
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

    /**<jp>
     * ファイル内のコメント文字
     </jp>*/
    /**<en>
     * Comment characters in the file
     </en>*/
    public static final char COMMENT = '#';

    // Class variables -----------------------------------------------
    /**<jp>
     * 定義ファイルの情報を保持する。
     </jp>*/
    /**<en>
     * Preserves information of definition file.
     </en>*/
    private LineNumberReader _defineFile;

    /**<jp>
     * 担当するステーションのステーション番号
     </jp>*/
    /**<en>
     * The station no. of the base station
     </en>*/
    private String _myStationNo;

    /**<jp>
     * 搬送元となるステーションのステーション番号
     </jp>*/
    /**<en>
     * The station no. of the base station
     </en>*/
    private String _baseStationNo;

    /**<jp>
     * 接続されているステーションの一覧
     </jp>*/
    /**<en>
     * List of connected station
     </en>*/
    private List<String> _connectedStations;

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
        return ("$Revision: 8053 $,$Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 定義ファイルを読み込む為の<code>LineNumberReader</code>インスタンスと、
     * 担当するステーション番号を指定して、インスタンスを生成します。
     * パフォーマンスの確保の為、定義ファイルのオープン/クローズは行いません。
     * @param fr     定義ファイルから読み込みを行うための<code>LineNumberReader</code>
     * @param stnum  担当するステーションのステーション番号
     </jp>*/
    /**<en>
     * Generates an instance by specifing instance of <code>LineNumberReader</code> in order
     * to read the definition file and the base station no.
     * In order to secure the performance, the opening/closure of the definition file is not implemented.
     * @param fr     :<code>LineNumberReader</code> used to read the definition file
     * @param stnum  :station no. of the base station
     </en>*/
    public RoutePiece(LineNumberReader fr, String stnum ,String baseSt)
    {
        _defineFile = fr;
        _myStationNo = stnum;
        _baseStationNo = baseSt;
        _connectedStations = new ArrayList<String>();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 接続されているステーションに、該当の物があるかチェックします。
     * 既にチェック済みのステーション番号を渡すことで、チェックの重複を防ぎます。
     * 該当のステーションが直接接続されていない場合には、再帰的に存在チェックを行います。
     * @param tgt     検索対象のステーション番号
     * @param chkd    チェック済みのステーション番号の一覧
     * @param result  該当のステーションを持つ場合は、そのステーションが追加されます。
     * @return 該当のステーションを持つ場合は、true
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合にThrowされます。
     </jp>*/
    /**<en>
     * Check to see if target station is included in the connected stations.
     * It avoids the check overlap by providing station no. that check is already done.
     * If target station is not directly connected, it recursively checks its presence.
     * @param tgt  :station no. subject to search
     * @param chkd :list of checked station no.
     * @param result  :if corrensponding station is held, this station will be added.
     * @return     :true if it holds the corrensponding station.
     * @throws ReadWriteException            : Notifies if file I/O error occured.
     </en>*/
    public boolean exists(String tgt, List<String> chkd, List<String> result, Connection conn ,int[] status)
    throws ReadWriteException, InvalidDefineException, NotFoundException
    {
        Object wkSt;
        //<jp> まだ定義ステーションが読み込まれていない場合は読み込む</jp>
        //<en> Reads the definition file if it has not done the reading.</en>
        if (_connectedStations.isEmpty())
        {
            loadConnectedStations(_connectedStations);
        }

        //<jp> 検索済みステーションに自ステーションを追加</jp>
        //<en> Add the own self station to the searched stations.</en>
        chkd.add(_myStationNo);

        //<jp> 定義ステーション中にターゲットがあるかチェック</jp>
        //<jp> あれば、自ステーションを結果に追加してリターン</jp>
        //<en> Check whether/not the target is included in defined station.</en>
        //<en> If included, add own self station to the result and return.</en>
        if (_connectedStations.indexOf(tgt) >= 0)
        {
            // 禁止ルートチェッククラスのインスタンス
            ProhibitedRoute pRoute = ProhibitedRoute.getInstance(_baseStationNo, tgt);

            if (!StringUtil.isBlank(pRoute))
            {
                chkd.add(tgt);
                String[] list = new String[chkd.size()];
                chkd.toArray(list);

                // 禁止ルートのチェックを行います。
                if (!pRoute.check(list))
                {
                    chkd.remove(chkd.size()-1);
                    return false;
                }
            }

            result.add(_myStationNo);
            return (true);
        }

        // 対象ステーションのステータスをチェック
        if (9 != status[0])
        {
            Station stations =StationFactory.makeStation(conn,_myStationNo);
            String sts = stations.getStatus();
            // ステーション状態：異常の場合
            if (Station.STATION_STATUS_ERROR.equals(sts))
            {
                status[0] = Route.NOT_ACTIVE_FAIL;
                return (false);
            }
            // ステーション状態：切り離しの場合
            else if (Station.STATION_STATUS_DISCONNECTED.equals(sts))
            {
                status[0] = Route.NOT_ACTIVE_OFFLINE;
                return (false);
            }
        }

        //<jp> 定義ステーションをそれぞれチェック</jp>
        //<en> Check the defined stations respectively.</en>
        int ccsCount = _connectedStations.size();
        for (int i = 0; i < ccsCount; i++)
        {
            wkSt = _connectedStations.remove(0);
            //<jp> 検索済みステーションと比較して、検索済みでなければ、さらにチェック</jp>
            //<en> Compare with searched stations, and if the station has not searched yet, check it further.</en>
            if (chkd.indexOf(wkSt) < 0)
            {
                //<jp> RoutePieceオブジェクトを作り、ターゲットが有るか検索</jp>
                //<en> Create the RoutePiece object, and search to see if there is the target.</en>
                RoutePiece wrp = new RoutePiece(_defineFile, (String)wkSt ,_baseStationNo);
                if (wrp.exists(tgt, chkd, result ,conn ,status))
                {
                    //<jp> 発見できたら、対象ステーションを結果に追加してリターン</jp>
                    //<en> If the target is found, add the target station to the result and return.</en>
                    result.add(_myStationNo);
                    return (true);
                }
                chkd.remove(chkd.size()-1);
            }
        }

        return (false);
    }

    /**<jp>
     * 接続されているステーションの一覧を返します。
     * @return 接続されているステーションがない場合は null が返されます。
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合にThrowされます。
     </jp>*/
    /**<en>
     * Returns a list of connected stations.
     * @return :It returns null if there is no station connected.
     * @throws ReadWriteException            : Notifies if file I/O error occured.
     </en>*/
    public String[] getConnectedStations()
            throws ReadWriteException
    {
        List<String> snv = new ArrayList<String>();
        if (loadConnectedStations(snv))
        {
            String[] rst = new String[snv.size()];
            for (int i = 0; i < rst.length; i++)
            {
                rst[i] = String.valueOf(snv.remove(0));
            }
            return (rst);
        }
        else
        {
            return (null);
        }
    }

    /**<jp>
     * 今回指定された担当ステーションに接続されているステーションを返します。
     * コの字フリーステーションで対となる入庫側ステーションを探すときなどに使用します。
     * @return 移動先ステーションNo
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合にThrowされます。
     </jp>*/
    /**<en>
     * Returns stations connected with this base station specified.
     * This will be used when searching for storage staion pairing the free station of U-shaped conveyor.
     * @return station no. (next station the carry moves to)
     * @throws ReadWriteException            : Notifies if file I/O error occured.
     </en>*/
    public String getConnectorStationTo()
            throws ReadWriteException
    {
        try
        {
            //<jp> 通常のファイルはマークサポートされている。先頭位置にマークする</jp>
            //<en> Regular files are mark-supported. Marks in start positions.</en>
            _defineFile.mark(10240);

            String line;
            do
            {
                line = _defineFile.readLine();
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
                if (getMainStation(line).equals(_myStationNo))
                {
                    String[] dst = getDefinedStations(line);
                    return dst[0];
                }
                else
                {
                    continue;
                }
            } while (line != null);
        }
        catch (IOException e)
        {
            //<jp> 6026030=ルート定義ファイル読み込みエラー。詳細=({0})</jp>
            //<en> 6026030=Route definition file read error. Detail=({0})</en>
            Object[] tObj = new Object[1];
            tObj[0] = e.getMessage();
            RmiMsgLogClient.write(new TraceHandler(6026030, e), this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }

        return null;
    }

    /**<jp>
     * このステーションが接続されている、元ステーションを返します。
     * 複数の元ステーションが定義されている場合は、はじめに見つかった物を返します。
     * @return 元ステーション
     * @throws ReadWriteException 搬送ルート定義ファイルの読込みでエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Returns the previous station that this station is connected to.
     * If more than 1 previous stations are defined, return the 1st one found.
     * @return :previous station
     * @throws ReadWriteException :Notifies if error occued when reading the carry route definition file.
     </en>*/
    public String getConnectorStation()
            throws ReadWriteException
    {
        String cStation = null;

        try
        {
            //<jp> 通常のファイルはマークサポートされている。先頭位置にマークする</jp>
            //<en> Regular files are mark-supported. It is marked in start positions.</en>
            _defineFile.mark(10240);

            String line;
            boolean found = false;

            do
            {
                line = _defineFile.readLine();
                if (line == null)
                {
                    break;
                }

                String mSta = getMainStation(line);
                if (mSta != null)
                {
                    String[] dst = getDefinedStations(line);
                    for (int i = 0; i < dst.length; i++)
                    {
                        if (_myStationNo.equals(dst[i]))
                        {
                            found = true;
                            cStation = mSta;
                            break;
                        }
                    }
                }
                else
                {
                    continue;
                }
            } while (!found);
            //<jp> マーク(先頭位置)にポインタを戻しておく</jp>
            //<en> Set the pointer back to the marked position (start position).</en>
            _defineFile.reset();
        }
        catch (IOException e)
        {
            //<jp> 6026030=ルート定義ファイル読み込みエラー。詳細=({0})</jp>
            //<en> 6026030=Route definition file read error. Detail=({0})</en>
            Object[] tObj = new Object[1];
            tObj[0] = e.getMessage();
            RmiMsgLogClient.write(new TraceHandler(6026030, e), this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }

        return (cStation);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 接続されているステーション一覧を読み込む。
     * @param tgt 接続されているステーションの一覧
     * @return 定義されていない場合 false
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合にThrowされます。
     </jp>*/
    /**<en>
     * Reads the list of connected stations.
     * @param tgt :list of connected stations
     * @return    :false if not defined.
     * @throws ReadWriteException :It throws exception if file I/O error occured.
     </en>*/
    protected boolean loadConnectedStations(List<String> tgt)
            throws ReadWriteException
    {
        //<jp> _defineFile から、このインスタンス用の定義を見つけて、Listにセットする。</jp>
        //<en> Finds the definition for this instance from wDefineFile, then set to List.</en>
        boolean found = false;
        try
        {
            //<jp> 通常のファイルはマークサポートされている。先頭位置にマークする</jp>
            //<en> Regular files are mark-supported. The start positions are marked.</en>
            _defineFile.mark(10240);

            String line;
            do
            {
                line = _defineFile.readLine();
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
                if (getMainStation(line).equals(_myStationNo))
                {
                    found = true;
                    String[] dst = getDefinedStations(line);
                    for (int i = 0; i < dst.length; i++)
                    {
                        tgt.add(dst[i]);
                    }
                }
                else
                {
                    continue;
                }
            } while (line != null);

            //<jp> マーク(先頭位置)にポインタを戻しておく</jp>
            //<en> Set the pointer back to the marked position (start position).</en>
            _defineFile.reset();
        }
        catch (IOException e)
        {
            //<jp> 6026030=ルート定義ファイル読み込みエラー。詳細=({0})</jp>
            //<en> 6026030=Route definition file read error. Detail=({0})</en>
            Object[] tObj = new Object[1];
            tObj[0] = e.getMessage();
            RmiMsgLogClient.write(new TraceHandler(6026030, e), this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
        return (found);
    }

    // Private methods -----------------------------------------------
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
        StringTokenizer deftoken;
        StringTokenizer mtoken;

        if (line.charAt(0) == COMMENT)
        {
            return (null);
        }

        //<jp> 主定義ステーションは不要なので読み飛ばす</jp>
        //<en> Skips main-defined station as is unnecessary.</en>
        mtoken = new StringTokenizer(line, DELM_DEFINEST, false);
        mtoken.nextToken();

        deftoken = new StringTokenizer(mtoken.nextToken(), DELM_CONNECTST, false);

        String[] rStations = new String[deftoken.countTokens()];
        for (int i = 0; i < rStations.length; i++)
        {
            rStations[i] = deftoken.nextToken();
        }
        return (rStations);
    }
}
//end of class
