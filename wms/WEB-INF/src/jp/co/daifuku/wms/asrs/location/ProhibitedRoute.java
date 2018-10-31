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
import java.util.List;
import java.util.StringTokenizer;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * 禁止ルート情報を保持します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
public class ProhibitedRoute
        extends Object
{
    // Class fields --------------------------------------------------

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
    /**
     * コンストラクタ
     */
    public ProhibitedRoute()
    {
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * ステーション番号のFrom,Toから、<code>ProhibitedRoute</code>のインスタンスを
     * 作成し、返します。
     * @param from <code>Route</code>の元ステーション（String型）
     * @param to <code>Route</code>の先ステーション（String型）
     * @return 引数に基づいて作成される<code>ProhibitedRoute</code>オブジェクト
     * @throws ReadWriteException ファイルI/Oエラーが発生した場合に通知されます。
     */
    public static ProhibitedRoute getInstance(String from, String to)
            throws ReadWriteException
    {
        ProhibitedRoute nRoute = new ProhibitedRoute();
        nRoute.setStartStationNo(from);
        nRoute.setEndStationNo(to);

        try
        {
            nRoute.setLineNumberReader(new LineNumberReader(new FileReader(WmsParam.PROHIBITED_ROUTE_FILE)));
        }
        catch (IOException e)
        {
            // 禁止ルート定義ファイルの読み込み失敗、禁止ルートなし
            return null;
        }

        return (nRoute);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * ルート検索結果と禁止ルートのチェックを行います。
     * 禁止ルートの場合はfalse それ以外の場合はtrue
     * @return ルートの状態
     */
    protected boolean check(String[] routeList)
    {
        // 開始・終了ステーション
        String chkRoute = _startStationNo + "-" + _endStationNo;
        try
        {
            _routeFileReader.mark(10240);

            String line;

            boolean routeRes = true;
            do
            {
                line = _routeFileReader.readLine();
                if (line == null)
                {
                    return true;
                }
                if (getMainStation(line) == null)
                {
                    continue;
                }
                if (getMainStation(line).equals(chkRoute))
                {
                    String[] proRoute = getDefinedStations(line);

                    if (StringUtil.isBlank(proRoute))
                    {
                        // 禁止ルートなし
                        return true;
                    }
                    for (int i = 0; routeList.length > i; i++)
                    {
                        // 禁止ルートとルートリストのステーションを照合する
                        if (proRoute[i].equals(routeList[i]))
                        {
                            routeRes = false;
                        }
                        else
                        {
                            routeRes = true;
                            break;
                        }
                    }
                    if (!routeRes)
                    {
                        return routeRes;
                    }
                }
                else
                {
                    continue;
                }
            } while (line != null);
            return routeRes;
        }
        catch (IOException e)
        {
            return true;
        }
        //        return true;

    }


    /**
     * ルート検索結果と禁止ルートのチェックを行います。
     * 禁止ルートの場合はfalse それ以外の場合はtrue
     * @return ルートの状態
     */
    protected boolean check(List<String> routeList)
    {
        // 開始・終了ステーション
        String chkRoute = _startStationNo + "-" + _endStationNo;
        try
        {
            _routeFileReader.mark(10240);

            String line;
            do
            {
                line = _routeFileReader.readLine();
                if (line == null)
                {
                    return true;
                }
                if (getMainStation(line) == null)
                {
                    continue;
                }
                if (getMainStation(line).equals(chkRoute))
                {
                    String[] proRoute = getDefinedStations(line);

                    if (StringUtil.isBlank(proRoute))
                    {
                        // 禁止ルートなし
                        return true;
                    }

                    int j = 0;

                    for (int i = routeList.size() - 1; i >= 0; i--)
                    {
                        // 禁止ルートとルートリストのステーションを照合する
                        if (proRoute[j].equals(routeList.get(i)))
                        {
                            if (routeList.size() - 1 == j)
                            {
                                return false;
                            }
                            j++;
                            continue;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                else
                {
                    continue;
                }
            } while (line != null);
        }
        catch (IOException e)
        {
            return true;
        }
        return true;

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

            RmiMsgLogClient.write(6026028, this.getClass().getName());
            return null;
        }
    }

}
//end of class

