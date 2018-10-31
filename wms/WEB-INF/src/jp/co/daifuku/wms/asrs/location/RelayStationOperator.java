// $Id: RelayStationOperator.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 搬送の中継点となるステーションの動作を定義したステーションです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class of station defines the behaviour of the relay station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class RelayStationOperator
        extends StationOperator
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
     * 新しい<code>Station</code>のインスタンスを作成します。既に定義されているステーションを
     * 持つインスタンスが必要な場合は、<code>StationFactory</code>クラスを利用してください。
     * @param conn データベースコネクション
     * @param snum 保持する自ステーション番号
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @see StationFactory
     </jp>*/
    /**<en>
     * Create a new instance of <code>Station</code>. If the instance which already has the 
     * defined stations is needed, please use <code>StationFactory</code> class.
     * @param  conn     :Connection with database
     * @param  snum     :own station no. preserved
     * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
     * @see StationFactory
     </en>*/
    public RelayStationOperator(Connection conn, String snum) throws ReadWriteException
    {
        super(conn, snum);
    }

    /**
     * 新しい<code>RelayStationOperator</code>のインスタンスを作成します。
     * 引数で渡された、Stationインスタンスを保持します。
     * @param conn データベースコネクション
     * @param st   ステーションインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public RelayStationOperator(Connection conn, Station st) throws ReadWriteException
    {
        super(conn, st);
    }

    /**<jp>
     * 中継ステーションにおける到着処理です。
     * 到着した搬送データの情報をステーションに記録します。
     * ステーションの属性が到着報告なしの場合にこのメソッドが呼び出された場合はInvalidDefineExceptionを返します。
     * @param  ci 更新対象CarryInformation
     * @param  plt Palletインスタンス
     * @throws InvalidDefineException 本ステーションが到着報告無しの場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * This is the arrival process at the relay station.
     * Records the carry data arrived in station.
     * If this method is called for the station which the attribute is 'no arrival report is operated',
     * it returns InvalidDefineException.
     * @param ci  :CarryInformation to update
     * @param plt :Pallet instance
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if the arrival report is not operate at the station.
     * @throws ReadWriteException     :Notifies if any rtouble occured in data access.
     * @throws NotFoundException      :Notifies if there is no such data. 
     </en>*/
    public void arrival(CarryInfo ci, Pallet plt, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        //<jp> ステーションの到着チェック有無を確認する。</jp>
        //<en> Checks to see whether/not the arrival checking is done at station.</en>
        if (getStation().isArrivalCheck())
        {
            //<jp> 到着報告有りのステーションであれば、到着データを記録</jp>
            //<en> If the station operates the arrival reports, it records the arrival data.</en>
            registArrival(ci.getCarryKey(), plt);

            //<jp> 搬送指示送信要求を行なう。</jp>
            //<en> Requets to submit the carru instruction.</en>
            carryRequest();
        }
        else
        {
            //<jp> 到着報告無しのステーションの場合は例外を返す。</jp>
            //<en> If the station does not operate the arrival reports, it returns the exception.</en>
            //<jp> 6024024=到着報告無しのステーションです。到着は無効です。StationNo={0} mckey={1}</jp>
            //<en> 6024024=No arrival report for the station. Arrival is invalid. ST No={0} mckey={1}</en>
            Object[] tObj = new Object[2];
            tObj[0] = getStationNo();
            tObj[1] = ci.getCarryKey();
            RmiMsgLogClient.write(6024024, LogMessage.F_NOTICE, "RelayStationOperator", tObj);
            throw new InvalidDefineException(WmsMessageFormatter.format(6024024, tObj));
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

