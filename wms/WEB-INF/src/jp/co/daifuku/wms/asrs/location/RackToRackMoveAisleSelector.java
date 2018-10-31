// $Id: RackToRackMoveAisleSelector.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * アイル検索を行います。
 * このクラスは棚間移動の空棚検索可能なアイルの一覧を取得する場合に使用されます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class conducts search for aisle.
 * This class is used to obtain the list of aisle searchable for empty location.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/03/16</TD><TD>INOUE</TD><TD><code>getAisleStationNos</code>Does not throw exception.even if it does not aisle station in warehouse</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $
 * @author  $Author: admin $
 </en>*/
public class RackToRackMoveAisleSelector
        extends AisleSelector
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
     * データベース接続用のConnectionインスタンス、倉庫インスタンス、搬送元ステーションインスタンスを
     * 引数としてインスタンスを生成します。
     * @param conn データベース接続用 Connection
     * @param wh 空棚検索対象倉庫インスタンス
     * @param st 搬送元ステーションインスタンス
     * @throws ReadWriteException     データベースへのアクセスで異常が発生した場合に通知します。
     * @throws InvalidDefineException 定義情報に不整合が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Generates the instance according to the parameter, the connection instance to connect with database, 
     * instance of warehouse and instance of sending station.
     * @param conn :Connection with database
     * @param wh :instance of warehouse subject to empty location search
     * @param st  :instance of sending station
     * @throws ReadWriteException     : Notifies if error occured when accessing database.
     * @throws InvalidDefineException : Notifies if inconsistency occured in definition information.
     * @throws NotFoundException      : Notifies if there is no such data.
     </en>*/
    public RackToRackMoveAisleSelector(Connection conn, WareHouse wh, Station st)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        super(conn, wh, st);
    }


    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 指定された搬送元ステーション又は、アイル、倉庫からアイルステーションNo一覧を生成します。
     * 搬送元ステーションの定義情報内にアイルステーションが定義されている場合は、そのアイルステーションNoを使用します。
     * アイルステーションが定義されていない場合、倉庫ステーションNoを元にアイルステーションの一覧を取得します。
     * アイルステーションNoの並びはアイルステーションNoの順番となります。但し搬送元ステーションの保持する、
     * 倉庫情報内に最終使用ステーションがセットされている場合、そのステーションが最後になるように並び替えが行われます。
     * アイルステーションNoの並びは空棚検索順として使用されます。
     * 又、搬送元ステーションからのルートがNGのアイルステーションは一覧に含めません。
     * 例
     *   倉庫情報内の最終使用ステーションが未設定時のアイルステーションNoの並び
     *     9001
     *     9002
     *     9003
     *   倉庫情報内の最終使用ステーションが9001の時のアイルステーションNoの並び
     *     9002
     *     9003
     *     9001
     * @throws ReadWriteException     データベースへのアクセスで異常が発生した場合に通知されます。
     * @throws InvalidDefineException stに対するアイルステーションの定義が一つも見つからない場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Generates a list of aisle station no. based on the specified sending station.
     * If the definition of the sending station includes the aisle station information, that aisle station no. will be used.
     * If the sending station is not defined for aisle station, a list of aisle stations will b retireved based on the warehouse
     * station no.
     * Aisle station no. will be lined in order of aisle station no. sequence. Except if the station of end-use has been set
     * in the warehouse information, which is preserved by sending station, that station will be put in the end of the list. 
     * This order of aisle station no. will be used as an order if empty location search.
     * Also the list does not include the aisle stations which the route from the sending station is found unavailable.
     * Example
     *   Order of aisle station no. when station of end-use is unset in warehouse information:
     *     9001
     *     9002
     *     9003
     *   Order of aisle station no. when 9001 has been set as the station of end-use in warehouse information:
     *     9002
     *     9003
     *     9001
     * @throws ReadWriteException     : Notifies if error occured when accessing database.
     * @throws InvalidDefineException : Notifies if there is no definition of aisle station for st at all.
     * @throws NotFoundException      : Notifies if there is no such data.
     </en>*/
    protected void createAisleInformations()
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        if (getFromStation() instanceof Aisle)
        {
            //<jp> アイルの場合</jp>
            Aisle[] aisles = new Aisle[1];
            aisles[0] = (Aisle)getFromStation();
            setAisles(aisles);
        }
        else if (getFromStation() instanceof Shelf)
        {
            //<jp> 倉庫情報を元にアイルステーション一覧を取得</jp>
            //<en> If aisle station is undefined, retrieve the list of aisle station according to the warehouse information.</en>
            AisleSearchKey akey = new AisleSearchKey();
            AisleHandler ahandl = new AisleHandler(getConnection());
            akey.setWhStationNo(getFromStation().getWhStationNo());
            akey.setAisleNoOrder(true);
            Aisle[] wksts = (Aisle[])ahandl.find(akey);
            if (wksts.length == 0)
            {
                //<jp> アイルステーションの定義が一つも見つからない場合は例外</jp>
                //<en> Exception if there is no definition for aisle station at all.</en>
                //<jp> 6026049=アイルの情報が存在しません。 アイルNo.={0}</jp>
                //<en> 6026049=TAisle is not determined. AisleNo.={0}</en>
                Object[] tObj = new Object[1];
                tObj[0] = getFromStation().getWhStationNo();
                RmiMsgLogClient.write(6026049, LogMessage.F_ERROR, "AisleSelector", tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6026049, tObj));
            }

            //<jp> 倉庫情報内を元に、アイルステーションNoの並び替えを行う。</jp>
            //<en> Rearrange the order of aisle station no. according to the warehouse information.</en>
            setAisles(sortWareHouseAisles((WareHouse)StationFactory.makeStation(getConnection(), getFromStation().getWhStationNo()),
                    wksts));
        }
        else
        {
            //<jp> 対応しないインスタンスは例外</jp>
            //<jp> 6027015=指定できないインスタンスです。 インスタンス={0}</jp>
            Object[] tObj = new Object[1];
            tObj[0] = getFromStation().getClass().getSimpleName();
            RmiMsgLogClient.write(6027015, LogMessage.F_ERROR, "AisleSelector", tObj);
            throw new InvalidDefineException(WmsMessageFormatter.format(6027015, tObj));
        }
    }

    // Private methods -----------------------------------------------
}
