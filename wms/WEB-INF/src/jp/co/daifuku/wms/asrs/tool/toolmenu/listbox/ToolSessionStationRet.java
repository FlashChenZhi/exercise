// $Id: ToolSessionStationRet.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationFinder;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/**<jp>
 * Stationを検索し結果を保持します。
 * 検索はToolStationFinderクラスを使用します。
 * 検索条件はステーションNoをセットして検索します。
 * 検索条件など必要な情報は、画面（JSP）がToolSessionStationRetのインスタンス生成時にセットします。
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションをRemoveして下さい。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class searchs Station and retrieves results.
 * ToolStationFinder class will be used for the search.
 * Station no. will be set as a search condition.
 * Required information such as search conditions will be set at the instantiation of 
 * ToolSessionStationRet of the screen(JSP).
 * Please have the instance preserved in session when using this class.
 * Also please remove session after use of this class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolSessionStationRet extends ToolSessionRet
{
    
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * 検索キー<code>StationNo</code>
     </jp>*/
    /**<en>
     * Search key <code>StationNo</code>
     </en>*/
    private String wStationNo;

    /**<jp>
     * 検索キー<code>WHStationNo</code>
     </jp>*/
    /**<en>
     * Search key <code>WHStationNo</code>
     </en>*/
    private String wWHStationNo;

    /**<jp>
     * 検索キー<code>PRStationNo</code>
     </jp>*/
    /**<en>
     * Search key <code>PRStationNo</code>
     </en>*/
    private String wPRStationNo;

    /**<jp>
     * 検索キー<code>WorkPlaceType</code>
     </jp>*/
    /**<en>
     * Search key <code>WorkPlaceType</code>
     </en>*/
    private String wWorkPlaceType;

    /**<jp>
     * 検索キー<code>WorkKbn</code>
     </jp>*/
    /**<en>
     * Search key <code>WorkKbn</code>
     </en>*/
    private int wWorkKbn;

    /**<jp>
     * メッセージの表示判断に使います。
     * 作業メンテナンスで使用します。
     </jp>*/
    /**<en>
     * This will be used to determine the message to dispaly.
     * It will be used in work maintenance.
     </en>*/
    private String wDispMessage;

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * ステーションNoを検索条件にセットしStationを検索するためのコンストラクタです。
     * @param conn       <code>Connection</code> データベースに接続する時に使用。
     * @param stnumber   検索キー ステーションNo
     * @param whstnumber 検索キー 倉庫ステーションNo
     * @param condition  検索条件 （1画面で表示するデータの数）
     * @throws Exception 検索に失敗したときに通知されます。
     </jp>*/
    /**<en>
     * This constructor is used to set the station no. in search condition for Station search.
     * @param conn       <code>Connection</code> :used when connecting with database
     * @param stnumber   :search key station no.
     * @param whstnumber :search key warehouse station no.
     * @param condition  :search condition (number of data to show at one display on the screen)
     * @throws Exception :Notifies if the search has failed.
     </en>*/
    public ToolSessionStationRet(Connection conn, String stnumber, String whstnumber, String condition) throws Exception
    {
        ToolFindUtil fu = new ToolFindUtil(conn);
        //<jp>禁止文字チェック</jp>
        //<en>Check the invalid characters.</en>
        if (fu.isUndefinedCharForListBox(stnumber))
        {
            wLength = 0;
        }
        else
        {
            this.wConn            = conn;
            this.wStationNo   = stnumber;
            this.wWHStationNo = whstnumber;
//            try
//            {
//                this.wCondition = Integer.parseInt( condition );
//            }
//            catch (Exception e)
//            {
//                this.wCondition = 10;
//            }
            //<jp> 検索</jp>
            //<en> Search</en>
            find();
        }
    }

    /**<jp>
     * ステーションNoを検索条件にセットしStationを検索するためのコンストラクタです。
     * @param conn       <code>Connection</code> データベースに接続する時に使用。
     * @param stnumber   検索キー ステーションNo
     * @param whstnumber 検索キー 倉庫ステーションNo
     * @param prstnumber 検索キー 親ステーションNo
     * @param wptype     検索キー 作業場種別
     * @param kbn        検索キー 画面作業区分(0:作業場 1:代表ステーション)
     * @param condition  検索条件 （1画面で表示するデータの数）
     * @throws Exception 検索に失敗したときに通知されます。
     </jp>*/
    /**<en>
     * This constructor is used to set the station no. in search condition for Station search.
     * @param conn       <code>Connection</code> :used when connecting with database
     * @param stnumber   :search key station no.
     * @param whstnumber :search key warehouse station no.
     * @param prstnumber :search key parent station no.
     * @param wptype     :search key workshop type
     * @param kbn        :search key display work type (0:workshop, 1:main station)
     * @param condition  :search condition (number of data to show at one display on the screen)
     * @throws Exception :Notifies if the search has failed.
     </en>*/
    public ToolSessionStationRet(Connection conn, String stnumber, String whstnumber, String prstnumber, String wptype, int kbn, String condition) throws Exception
    {
        ToolFindUtil fu = new ToolFindUtil(conn);
        //<jp>禁止文字チェック</jp>
        //<en>Check the invalid characters.</en>
        if (fu.isUndefinedCharForListBox(stnumber))
        {
            wLength = 0;
        }
        else
        {
            this.wConn            = conn;
            this.wStationNo   = stnumber;
            this.wWHStationNo = whstnumber;
            this.wWorkPlaceType  = wptype;
            this.wWorkKbn         = kbn;
            this.wPRStationNo = prstnumber;
//            try
//            {
//                this.wCondition = Integer.parseInt( condition );
//            }
//            catch (Exception e)
//            {
//                this.wCondition = 10;
//            }
            //<jp> 検索</jp>
            //<en> Search</en>
            find_st();
        }
    }
    // Public methods ------------------------------------------------
    /**<jp>
     * ステーションNoをキーにStationを検索します。
     * @throws Exception 検索に失敗したときに通知されます。
     </jp>*/
    /**<en>
     * Search the station based on the station no. as a key.
     * @throws Exception :Notifies if the search has failed.
     </en>*/
    public void find() throws Exception
    {
        int count = 0;
        int[] temp_workplace = {Station.STAND_ALONE_STATIONS, Station.AISLE_CONMECT_STATIONS, Station.MAIN_STATIONS};

        //<jp> 検索実行</jp>
        //<en> Conduct search.</en>
        ToolStationSearchKey srhkey = new ToolStationSearchKey();

        wFinder = new ToolStationFinder(wConn);
        //<jp> カーソルオープン</jp>
        //<en> Open a cursor.</en>
        wFinder.open();

        if (!wStationNo.equals("") && wStationNo != null)
        {
            srhkey.setStationNo(wStationNo);
        }
        if (wWHStationNo != null)
        {
            srhkey.setWareHouseStationNo(wWHStationNo);

            //<jp> 作業場種別[ 1:アイル独立型作業場 or 2:アイル結合型作業場 or 3:代表ステーション]</jp>
            //<en> Workshop type [1: Stand alone workshop, 2: Connected aisle workshop or 3: Main station]</en>
            srhkey.setWorkPlaceType(temp_workplace);
            //<jp> 表示順、ステーションNo.</jp>
            //<en> Order of data dispaly-station no.</en>
            srhkey.setStationNoOrder(1, true);

            count = ((ToolStationFinder)wFinder).search(srhkey);
        }
        //<jp> 初期化</jp>
        //<en> Initialization</en>
        wLength  = count;
        wCurrent = 0;
    }

    /**<jp>
     * ステーションNoをキーにStationを検索します。
     * @throws Exception 検索に失敗したときに通知されます。
     </jp>*/
    /**<en>
     * Search the station based on the station no. as a key.
     * @throws Exception :Notifies if the search has failed.
     </en>*/
    public void find_st() throws Exception
    {
        int count = 0;
        int flg = 0;
        int[] temp_sttype = {    1,    2,    3};
        int[] main_temp_sttype = {    2,    3};

        //<jp> 検索実行</jp>
        //<en> Conduct search.</en>
        ToolStationSearchKey srhkey = new ToolStationSearchKey();

        wFinder = new ToolStationFinder(wConn);
        //<jp> カーソルオープン</jp>
        //<en> open a cursor.</en>
        wFinder.open();

        if (!wStationNo.equals("") && wStationNo != null)
        {
            srhkey.setStationNo(wStationNo);
        }
        if (wWHStationNo != null)
        {
            srhkey.setWareHouseStationNo(wWHStationNo);

            //<jp> 画面作業区分（作業場設定中 or 代表ステーション設定中かで条件が変わる）</jp>
            //<en> Display work type (conditions differs depending on the object; </en>
            //<en> either 'workshop' or 'main station' which is being set'.)</en>

            //<jp> 作業場設定中</jp>
            //<en> Setting the workshops.</en>
            if (wWorkKbn == 0)
            {
                //<jp> ステーション種別</jp>
                //<en> Station type</en>
                srhkey.setStationType(temp_sttype);
                //<jp> 表示順、アイルステーションNo.ステーションNo.</jp>
                //<en> Order of data dispaly-aisle station no., station no.</en>
    //            srhkey.setAisleStationNoOrder(1,true);
                srhkey.setStationNoOrder(1, true);

                if (wWorkPlaceType.equals("1"))
                {
                    flg = 0;
                }
                else
                {
                    flg = 1;
                }

                count = ((ToolStationFinder)wFinder).search(srhkey, wPRStationNo, flg);
            }
            //<jp> 代表ステーション設定中</jp>
            //<en> Setting the main stations.</en>
            else
            {

                //<jp> ステーション種別</jp>
                //<en> Station type</en>
                srhkey.setStationType(main_temp_sttype);
                srhkey.setSendable(Station.SENDABLE);
                srhkey.setWorkPlaceType(Station.NOT_WORKPLACE);

                //<jp> 表示順、アイルステーションNo.ステーションNo.</jp>
                //<en> Order of data dispaly-aisle station no., station no.</en>
//                srhkey.setAisleStationNoOrder(1,true);
                srhkey.setStationNoOrder(1, true);

                count = ((ToolStationFinder)wFinder).search(srhkey);
            }
        }
        //<jp> 初期化</jp>
        //<en> Initialization</en>
        wLength  = count;
        wCurrent = 0;
    }
    /**<jp>
     * Stationの検索結果を返します。
     * @return Stationの検索結果
     </jp>*/
    /**<en>
     * Return the search results for the Station.
     * @return :the search results for Station
     </en>*/
    public Station[] getStation()
    {
        //<jp> 検索結果が端数の場合最後のページはデータがあるだけ表示する。</jp>
        //<jp> よってfor文でwEndpoint分まわすと例外が発生する。</jp>
        //<en> In case the search resulted in fractional number, have the fractional number of data in last page.</en>
        //<en> Therefore if looped the process as much as wEndpoint counts with for-text, exception will occur.</en>
        if (wEndpoint >= getLength())
        {
            //<jp> 端数計算</jp>
            //<en> calculation of the fraction</en>
            wFraction  = getLength() - (wEndpoint - wCondition);
            wEndpoint  = getLength();
        }

        Station[] resultArray = null;

        if (wLength > 0)
        {
            try
            {
                resultArray = (Station[])((ToolStationFinder)wFinder).getEntitys(wStartpoint, wEndpoint);
            }
            catch (Exception e)
            {
                //<jp>エラーをログファイルに落とす</jp>
                //<en>Record the error in log.</en>
                RmiMsgLogClient.write(new TraceHandler(6126013, e), this.getClass().getName());
            }
        }

        wCurrent = wEndpoint;
        return resultArray;
    }

    /**<jp>
     * 検索結果画面で再表示ボタンが押されたかどうかを判断して、
     * 再表示ボタンが押された場合再検索します。<BR>
     * Statementクローズを行ってから、再検索します。
     * @param reload 再表示ボタンが押されたときのFlag値
     </jp>*/
    /**<en>
     * Determines whether/not the redisplay button was pressed on search result screen.
     * Conduct search once again if Redisplay button was pressed.<BR>
     * CLoas the Statement first then conduct the search once again.
     * @param reload :value of the flag when Redisplay button was pressed.
     </en>*/
    public void reload(String reload)
    {
        try
        {
            if (reload != null)
            {
                //<jp> Statementクローズ</jp>
                //<en> Close the Statement.</en>
                wFinder.close();
                //<jp> 前回の検索結果をクリア</jp>
                //<en> Clear the data of previous search.</en>
                wFinder = null;
                //<jp> 再検索</jp>
                //<en> Conduct search again.</en>
                find();
            }
        }
        catch (Exception e)
        {
        }
    }

    /**<jp>
     * メッセージの表示判断に使います。
     * @return wDispMessage
     </jp>*/
    /**<en>
     * This is used to determine the message to display.
     * @return wDispMessage
     </en>*/
    public String getDispMessage()
    {
        return wDispMessage;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
