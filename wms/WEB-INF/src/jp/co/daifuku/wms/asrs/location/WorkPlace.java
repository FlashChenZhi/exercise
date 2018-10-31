// $Id: WorkPlace.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 作業場を管理するためのクラスです。<br>
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used for workshop control.
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class WorkPlace
        extends Station
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**<jp>
     * ステーション番号を保持する配列
     </jp>*/
    /**<en>
     * Preserves station no.
     </en>*/
    private String[] _stationNos;

    /**<jp>
     * ステーション保持するMap
     </jp>*/
    /**<en>
     * Map which preserves the stations
     </en>*/
    private Map<String, Station> _stationMap = null;

    // index of next station
    private int _stationIndex = 0;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public WorkPlace()
    {
        super();
    }

    /**
     * 新しい<code>WorkPlace</code>のインスタンスを作成します。既に定義されているステーションを
     * 持つインスタンスが必要な場合は、<code>StationFactory</code>クラスを利用してください。
     * @param snum  保持するステーション番号
     * @see StationFactory
     */
    public WorkPlace(String snum)
    {
        this();
        setStationNo(snum);
    }

    /**<jp>
     * WorkPlaceのインスタンスを作成します。
     * このインスタンスはスケジュールなどの処理で作業場決定行うためです。
     * @param workplace 作業場（ステーションナンバー）
     * @param wpstn     作業場が持っている指示可能なステーション番号の配列
     </jp>*/
    /**<en>
     * Creates an instance of WorkPlace.
     * This instance will be used in workshop designation in processes such as scheduling, etc.
     * @param workplace :work place (station no.)
     * @param wpstn     :array of station no. the work place has, that are ready for instuructions
     </en>*/
    public WorkPlace(String workplace, String[] wpstn)
    {
        this(workplace);
        setWPStations(wpstn);
    }


    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**<jp>
     * 作業場内のステーションNoを取得します。
     * @param conn ステーション・インスタンスを生成するためのデータベースコネクション
     * @return 検索された作業場内のステーション
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     </jp>*/
    /**<en>
     * Gets the station no. of the work place.
     * @param conn :database connection in order ot generate the station instance
     * @return :station in the work place searched
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws InvalidDefineException :Notifies if specified station no. was invalid.
     </en>*/
    public Station getWPStation(Connection conn)
            throws ReadWriteException,
                InvalidDefineException
    {
        // if no stations on this workplace, return null.
        if (ArrayUtil.isEmpty(_stationNos))
        {
            return null;
        }

        // prepare station map
        if (null == _stationMap)
        {
            _stationMap = new LinkedHashMap();
        }

        try
        {
            // get next station number
            String stNo = nextStationNo();

            //<jp> 既に登録されていた場合はハッシュからステーションを返します。</jp>
            //<en> If it was already registered, it returns station from Hash.</en>

            Station st = _stationMap.get(stNo);
            if (null == st)
            {
                // get Station and set to Map
                st = StationFactory.makeStation(conn, stNo);
                _stationMap.put(stNo, st);
            }
            return st;
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    /**<jp>
     * ステーション番号の配列を設定します。
     * @param sts 設定するステーション番号の配列
     </jp>*/
    /**<en>
     * Sets the collection of station no. 
     * @param sts :collection of stations to set
     </en>*/
    public void setWPStations(String[] sts)
    {
        _stationNos = sts;
    }

    /**<jp>
     * ステーション番号の配列を取得します。
     * @return 保持しているステーション番号の配列
     </jp>*/
    /**<en>
     * Gets the collection of station no. 
     * @return :colldction of station no. preserved
     </en>*/
    public String[] getWPStations()
    {
        return _stationNos;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 次のステーションインデックスを返して、インデックスを進めます。
     * @return 次のステーション
     */
    protected String nextStationNo()
    {
        String nextStNo = _stationNos[_stationIndex];
        _stationIndex = (++_stationIndex % _stationNos.length);
        return nextStNo;
    }


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
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
}
//end of class
