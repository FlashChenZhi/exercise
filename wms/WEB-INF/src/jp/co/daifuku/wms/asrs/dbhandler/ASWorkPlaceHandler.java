//$Id: ASWorkPlaceHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;

/**<jp>
 * <code>WorkPlace</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
 * <code>WorkPlace</code>クラスの取得に関しては、<code>StationFactory</code>を利用してください。
 * <code>WorkPlace</code>クラスには<code>getHandler</code>メソッドが用意されていますので、
 * 対応するHandlerが必要な場合は、<code>getHandler</code>メソッドを利用して取得します。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used to retrieve <code>WorkPlace</code> class from database, or to store this class in database.
 * For retrieval of <code>WorkPlace</code> class, please use <code>StationFactory</code>.
 * Since <code>WorkPlace</code>class is prepared with <code>getHandler</code> method, if the Handler is required,
 * please use <code>getHandler</code> method.
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ASWorkPlaceHandler
        extends StationHandler
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
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn :Connection with database
     </en>*/
    public ASWorkPlaceHandler(Connection conn)
    {
        super(conn);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ステーション(作業場)を検索します。検索キーは、<code>StationSearchKey</code>である必要があります。<BR>
     * このメソッド内でmakeWorkPlaceメソッドを経由しgetChildStationNumbersを使用しているからです。
     * 
     * 返されるEntity[]は、StationまたはWorkPlaceのいずれかがセットされています。<br>
     * 検索キーにヒットしたステーションが送信可能ステーションでない場合、作業場と判断して
     * WorkPlaceのエンティティに内容をセットした上で、返値に追加されます。<br>
     * 
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Searches the station. <code>StationSearchKey</code> must be used for the search key.<BR>
     * In this method, wStatement of <code>StationHandler</code>, its parent class, will not be used.<BR>
     * <B>generation of the Statement</B> is conducted in this find method.<BR>
     * It is becaouse getChildStationNumbers is used in this method, via makeWorkPlace method.<BR>
     * getChildStationNumbers method calls makeStation method, and runs SQL using wStatement.<BR>
     * Therefore, if wStatement is used by find method, it will not able to close the Statement.<BR>
     * So, be careful with the generation of Statement when customizing this method.
     * @param key :Key for the search
     * @return :object array created
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public Entity[] find(SearchKey key)
            throws ReadWriteException
    {
        // StationHandler stationHandler = new StationHandler(getConnection());
        Station[] stations = (Station[])super.find(key);

        List<WorkPlace> workPlaceList = new ArrayList<WorkPlace>();
        List<Station> stationList = new ArrayList<Station>();
        for (Station station : stations)
        {
            if (station.isSendable())
            {
                stationList.add(station);
            }
            else
            {
                WorkPlace wp = new WorkPlace();
                // copy values
                wp.setValueMap(station.getValueMap());
                // set entity to workplace list
                workPlaceList.add(wp);
            }
        }

        workPlaceList = Arrays.asList(makeWorkPlace((WorkPlace[])workPlaceList.toArray(new WorkPlace[0])));
        stationList.addAll(workPlaceList);

        return (Station[])stationList.toArray(new Station[0]);
    }

    /**<jp>
     * ステーション(作業場)を検索します。検索キーは、<code>StationSearchKey</code>である必要があります。<BR>
     * このメソッド内でmakeWorkPlaceメソッドを経由しgetChildStationNumbersを使用しているからです。<BR>
     * getChildStationNumbersメソッドはmakeStationメソッドを呼びwStatementを使用してSQLを実行しています。
     * 
     * 検索キーによって発見されたステーションが、作業場ではない場合にはステーションがそのまま
     * 返されます。
     * 
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NoPrimaryException 対象データが複数存在するときスローされます。
     </jp>*/
    /**<en>
     * Searches the station. <code>StationSearchKey</code> must be used for the search key.<BR>
     * In this method, wStatement of <code>StationHandler</code>, its parent class, will not be used.<BR>
     * <B>generation of the Statement</B> is conducted in this find method.<BR>
     * It is becaouse getChildStationNumbers is used in this method, via makeWorkPlace method.<BR>
     * getChildStationNumbers method calls makeStation method, and runs SQL using wStatement.<BR>
     * Therefore, if wStatement is used by find method, it will not able to close the Statement.<BR>
     * So, be careful with the generation of Statement when customizing this method.
     * @param key :Key for the search
     * @return :object array created
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NoPrimaryException :対象データが複数存在するときスローされます。
     </en> */
    public Entity findPrimary(SearchKey key)
            throws ReadWriteException,
                NoPrimaryException
    {
        // StationHandler stationHandler = new StationHandler(getConnection());
        Station ent = (Station)super.findPrimary(key);
        if (ent.isSendable())
        {
            // found "NOT" work place, it is station.
            return ent;
        }

        WorkPlace wp = new WorkPlace();
        // copy values
        wp.setValueMap(ent.getValueMap());

        return makeWorkPlace(wp)[0];
    }

    // Accessor methods -----------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    /**<jp>
     * 指定された作業場に所属するステーションNoの検索を行い、最終使用ステーションを元にステーションNoを並び替えて返します。
     * 所属するステーションが作業場（NOT_SENDABLE）の場合は、その作業場に所属するステーションNoの検索を行い、その中で並び替えを行います。
     * 呼出元にはステーション決定順にソートされたステーションNoの一覧が返されます。
     * @param parentStnum 作業場
     * @param lastStnum   最終使用ステーションNo
     * @return ステーション決定順にソートされたステーションNoの一覧
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search station numbers of workshops specified, then based on the final station used, rearrange the order
     * of station numbers and return.
     * If the station belongs to is the workhop itself(NOT_SENDABLE), conduct search for station numbers belong
     * to that workshop then rearrange the order of result data.
     * The caller, therefore, receives a list of station numbers sorted in order of designated stations.
     * @param parentStnum :workshop
     * @param lastStnum   :final station number used
     * @return :a list of station numbers sorted in order of designated stations
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    private String[] getChildStationNos(String parentStnum, String lastStnum)
            throws ReadWriteException
    {
        StationSearchKey key = new StationSearchKey();
        key.setParentStationNo(parentStnum);
        key.setStationNoOrder(true);
        // StationHandler wStationHandler = new StationHandler(getConnection());
        Station[] childStations = (Station[])super.find(key);

        List<String> stNumList = new ArrayList<String>(childStations.length);
        for (Station child : childStations)
        {
            String stationNo = child.getStationNo();
            if (child.isSendable())
            {
                stNumList.add(stationNo);
            }
            else
            {
                //<jp> 生成されたステーションが送信可能ではない場合、作業場と判断して所属するステーションNo一覧を生成する。</jp>
                //<en> If generated station was not sendable, it determines that the station is a workshop</en>
                //<en> and generates a list of station numbers belong to.</en>
                String lastUsedStationNo = child.getLastUsedStationNo();
                String[] grandChildStnums = getChildStationNos(stationNo, lastUsedStationNo);
                for (String wpStNo : grandChildStnums)
                {
                    stNumList.add(wpStNo);
                }
            }
        }

        //<jp> 最終使用ステーションNoを元にステーションの並びかえを行う。</jp>
        //<en> According to the final station no. used, rearrange the order of stations.</en>
        String[] sortStnumArray = sortStationNos(lastStnum, (String[])stNumList.toArray(new String[0]));

        return sortStnumArray;
    }

    /**<jp>
     * 最終使用ステーションを元にステーションNoの一覧を並び替えて返します。最終使用ステーションNoがnullかステーションNoの一覧に含まれない場合
     * 並び替えは行わず、ステーションNoの一覧をそのまま返します。
     * 例  ステーションNoの一覧が(1301, 1302, 1303)、最終使用ステーションが1301の場合の並び替え結果
     *     1302
     *     1303
     *     1301
     * @param lastStnum  最終使用ステーション
     * @param stnumArray 並び替えの対象となるステーションNoの一覧
     * @return ステーション決定順にソートされたステーションNoの一覧
     </jp>*/
    /**<en>
     * Returns a list of station numbers based on teh final station used. If the final station number used is
     * null, or not included in the list of station numbers, it will not rearrange the data, but returns it as 
     * it is.
     * EX:  list of station no. (1301, 1302, 1303), the number of final station used :1301
     *     the list will be rearranged to the following order;
     *     1302
     *     1303
     *     1301
     * @param lastStnum  :final station used
     * @param stnumArray :list of station no. subject to rearrangemet
     * @return :a list of station numbers sorted in order of designations of station
     </en>*/
    private String[] sortStationNos(String lastStnum, String[] stnumArray)
    {
        if (lastStnum == null)
        {
            return (stnumArray);
        }

        //<jp> 倉庫情報内の最終使用ステーションNoがセットされていた場合，並び替えを行う。</jp>
        //<en> If the final station number from the warehouse information was set, rearrange the order of list.</en>
        String[] newsts = new String[stnumArray.length];
        int pt = 0;
        for (int i = 0; i < stnumArray.length; i++)
        {
            //<jp> ステーションNoが一致すれば、その次のステーションを配列の先頭にする。</jp>
            //<en> If these station numbers match, place the following station in the beginning of array.</en>
            if (stnumArray[i].equals(lastStnum))
            {
                for (int j = i + 1; j < stnumArray.length; j++)
                {
                    newsts[pt] = stnumArray[j];
                    pt++;
                }
                for (int k = 0; k < i + 1; k++)
                {
                    newsts[pt] = stnumArray[k];
                    pt++;
                }
                return newsts;
            }
        }


        //<jp> 倉庫情報内の最終使用ステーションがアイルステーション一覧内にが見つからなかった場合、そのまま返す。</jp>
        //<en> If the final station from warehouse information cannot be found in aisle station no. list, return as it is.</en>
        return (stnumArray);
    }

    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>WorkPlace</code>インスタンスを生成します
     * 取得したレコードが送信可能ステーションに場合、作業場として扱わないのでインスタンス生成を行いません。
     * @param wp <CODE>ResultSet</CODE> 検索結果
     * @return 結果セットをマッピングしたEntity配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Get each item from <code>ResultSet</code> and generate <code>WorkPlace</code> instance.
     * If retrieved data was the sendable station, it will not be regarded as workshop, therefore, instance
     * will not be generated.
     * @param wp <CODE>ResultSet</CODE> search result
     * @return :Entity array mapped with result set
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    protected WorkPlace[] makeWorkPlace(WorkPlace... wp)
            throws ReadWriteException
    {
        for (int i = 0; i < wp.length; i++)
        {
            WorkPlace wkPlace = wp[i];
            String wkPlaceStNo = wkPlace.getStationNo();
            String lastUsedStationNo = wkPlace.getLastUsedStationNo();

            // collect child station numbers
            String[] childStationNos = getChildStationNos(wkPlaceStNo, lastUsedStationNo);
            wkPlace.setWPStations(childStationNos);
        }

        return (wp);
    }
}
//end of class

