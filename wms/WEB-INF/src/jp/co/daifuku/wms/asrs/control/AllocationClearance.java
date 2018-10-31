// $Id: AllocationClearance.java 7481 2010-03-09 02:26:20Z okayama $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.entity.BankSelect;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Shelf;

/**<jp>
 * 引当解除処理を行います。
 * 在庫確認終了設定で呼び出されるクラスです。
 * DBのコミットはこのクラスで行うべきではありません。
 * よって、このクラスを呼び出す画面またはアプリケーション内でコミットします。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/01/1</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/12/14</TD><TD>HONDO</TD><TD>棚間移動の搬送データを削除するように修正</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7481 $, $Date: 2010-03-09 11:26:20 +0900 (火, 09 3 2010) $
 * @author  $Author: okayama $
 </jp>*/
/**<en>
 * Processing the release cancel of allocations.
 * This class will be called in process of end inventory.
 * Commitmnet of DB should no be conducted in this class.
 * Commitment should be done either in the screen to call this method or within application.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/01/1</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7481 $, $Date: 2010-03-09 11:26:20 +0900 (火, 09 3 2010) $
 * @author  $Author: okayama $
 </en>*/
public class AllocationClearance
{
    // Class fields --------------------------------------------------
    /**<jp>
     * データベース接続用のConnectionインスタンス。
     * トランザクション管理は、このクラスの中で行いません。
     </jp>*/
    /**<en>
     * Connection instance to connect with database
     * Transaction control is not conducted in this class.
     </en>*/
    protected Connection _conn;

    // Class variables -----------------------------------------------

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
        return ("$Revision: 7481 $,$Date: 2010-03-09 11:26:20 +0900 (火, 09 3 2010) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * @param conn :Connection to connect with database
     </en>*/
    public AllocationClearance(Connection conn)
    {
        _conn = conn;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * スケジュールNo単位で搬送データの引当解除処理を行います。
     * 出庫データの場合、CarryInformationのみ削除を行います。
     * 入庫の場合はCarryInformationから参照されるPallet、Stockの削除も行います。
     * 戻り値には削除されたCarryInformationが保持していたアイルステーションNoを返します。
     * @param sclno  スケジュールNo
     * @return 削除されたCarryInformationが保持していたアイルステーションNoの一覧
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException 削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException 更新時にデータの不整合が発生した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Processing the release cancel of allocations in carry data by schedule no.
     * If it is a retrieval data, it only deletes the CarryInformation.
     * If it is a storage, it also deletes Pallet and Stock that CarryInformation gets reference to.
     * For the return value, it returns the aisle station no. that deleted CarryInformation preserved.
     * @param sclno  :schedule no.
     * @return :List of aisle station no. that deleted CarryInformation preserved.
     * @throws ReadWriteException :Notifies if exception occured when processing for database.
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     * @throws InvalidDefineException :Notifies if data inconsistency occured when updated.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     * @throws ScheduleException
     </en>*/
    public String[] dropwithScheduleno(String sclno)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException,
                LockTimeOutException,
                ScheduleException
    {
        CarryInfoHandler cih = new CarryInfoHandler(_conn);
        CarryInfoSearchKey cisk = new CarryInfoSearchKey();

        CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(_conn, getClass());

        cisk.setScheduleNo(sclno);
        cisk.setCarryKeyOrder(true);
        CarryInfo[] ci = (CarryInfo[])cih.find(cisk);
        if (ci.length == 0)
        {
            throw new NotFoundException();
        }

        List<CarryInfo> carryList = new ArrayList<CarryInfo>();
        for (int i = 0; i < ci.length; i++)
        {
            // 同一PeletteIDでスケジュールNoがない、または棚間移動が発生しているパレットは削除対象外
            CarryInfoHandler chandle = new CarryInfoHandler(_conn);
            CarryInfoSearchKey ckey = new CarryInfoSearchKey();
            ckey.setPalletId(ci[i].getPalletId());
            ckey.setScheduleNo("", "=", "(", "", false);
            ckey.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK, "=", "", "", false);
            ckey.setKey(CarryInfo.RETRIEVAL_STATION_NO, CarryInfo.SOURCE_STATION_NO, "!=", "", ")", true);
            if (chandle.count(ckey) > 0)
            {
                continue;
            }

            // 出庫ステーションがダブルディープの場合
            Shelf shelf = (Shelf)StationFactory.makeStation(_conn, ci[i].getRetrievalStationNo());
            if (shelf instanceof DoubleDeepShelf)
            {
                DoubleDeepShelf ddShelf = (DoubleDeepShelf)shelf;
                // 搬送先が奥棚か？
                if (BankSelect.BANK_SELECT_FAR.equals(ddShelf.getSide()))
                {
                    // 奥棚の搬送情報の場合、手前棚で棚間移動が発生しているかデータは削除対象外
                    chandle = new CarryInfoHandler(_conn);
                    ckey.clear();
                    ckey.setRetrievalStationNo(ddShelf.getPairStationNo());
                    ckey.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK, "=", "(", "", false);
                    ckey.setKey(CarryInfo.RETRIEVAL_STATION_NO, CarryInfo.SOURCE_STATION_NO, "!=", "", ")", true);
                    if (chandle.count(ckey) > 0)
                    {
                        continue;
                    }
                }
            }

            carryList.add(ci[i]);
        }

        CarryInfo[] carry = (CarryInfo[])carryList.toArray(new CarryInfo[0]);
        if (carry.length == 0)
        {
            // 引当可能なをデータが存在しない場合
            return null;
        }
        else
        {
            return carryCompOpe.releaseAllocation(carry);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
