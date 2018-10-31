// $Id: As21Id11.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「代替棚指示(AlternativeLocationCommand) ID=11」電文を組み立てます。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * Composes "alternative location command ID=11" according to communication protocol AS21.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class As21Id11
        extends SendIdMessage
{
    // Class fields --------------------------------------------------

    /**<jp>
     * 指示区分を表すフィールド (New Location指示 (二重格納時))
     </jp>*/
    /**<en>
     * Fieled of command, directing new location due to location duplicate
     </en>*/
    public static final String DUP_NEW_LOCATION = "01";

    /**<jp>
     * 指示区分を表すフィールド (Stationへの払出し指示 (二重格納時))
     </jp>*/
    /**<en>
     * Field of command, sending out to the next station due to location duplicate
     </en>*/
    public static final String DUP_PAID = "02";

    /**<jp>
     * 指示区分を表すフィールド (代替棚なし (二重格納時))
     </jp>*/
    /**<en>
     * Field of command, no alternative location for location duplicate
     </en>*/
    public static final String DUP_NO_SUBSHELF = "03";

    /**<jp>
     * 指示区分を表すフィールド (New Location指示 (空出荷時))
     </jp>*/
    /**<en>
     * Field of command, directing new location after empty retrieval
     </en>*/
    public static final String EMPTY_NEW_LOCATION = "11";

    /**<jp>
     * 指示区分を表すフィールド (Data Cancel (空出荷時))
     </jp>*/
    /**<en>
     * Field of command, data to be canceled (after empty retrieval)
     </en>*/
    public static final String EMPTY_DATA_CANCEL = "12";

    /**<jp>
     * 指示区分を表すフィールド (New Location指示 (荷姿不一致時))
     </jp>*/
    /**<en>
     * Field of command, directing dew location (load size mismatch).
     </en>*/
    public static final String DIM_NEW_LOCATION = "21";

    /**<jp>
     * 指示区分を表すフィールド (Stationへの払出し指示 (荷姿不一致時))
     </jp>*/
    /**<en>
     * Field of command, (transfer out to the station due to the mismatch of load size
     </en>*/
    public static final String DIM_PAID = "22";

    /**<jp>
     * 指示区分を表すフィールド (代替棚なし (荷姿不一致時))
     </jp>*/
    /**<en>
     * Field of command (no alternative location, load size mismatch)
     </en>*/
    public static final String DIM_NO_SUBSHELF = "23";

    /**<jp>
     * 指示区分の長さ
     </jp>*/
    /**<en>
     * Length of the request classification 
     </en>*/
    protected static final int REQUEST_CLASSIFICATION = 2;

    /**<jp>
     * MC Keyの長さ
     </jp>*/
    /**<en>
     * Length of MC Key
     </en>*/
    protected static final int CARRYKEY = 8;

    /**<jp>
     * Location No.の長さ
     </jp>*/
    /**<en>
     * Length of Location number
     </en>*/
    protected static final int LOCATION_NO = 12;

    /**<jp>
     * Station No.の長さ
     </jp>*/
    /**<en>
     * Length of Station number
     </en>*/
    protected static final int STATION_NO = 4;

    /**<jp>
     * 荷姿情報の長さ
     </jp>*/
    /**<en>
     * Height of load
     </en>*/
    protected static final int DIMENSION_INFORMATION = 2;

    /**<jp>
     * BC Dataの長さ
     </jp>*/
    /**<en>
     * Length of BC Data
     </en>*/
    protected static final int BC_DATA = 30;

    /**<jp>
     * 作業No.の長さ
     </jp>*/
    /**<en>
     * Length of work numbers
     </en>*/
    protected static final int WORK_NO = 8;

    /**<jp>
     * 制御情報の長さ
     </jp>*/
    /**<en>
     * Length of control information
     </en>*/
    protected static final int CONTROL_INFORMATION = 30;

    /**<jp>
     * 電文データの長さ
     </jp>*/
    /**<en>
     * Length of message data
     </en>*/
    protected static final int LEN_TOTAL =
            REQUEST_CLASSIFICATION + CARRYKEY + LOCATION_NO + STATION_NO + DIMENSION_INFORMATION + BC_DATA
                    + WORK_NO + CONTROL_INFORMATION;

    // Class variables -----------------------------------------------
    /**<jp>
     * 指示区分を保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves classification of instruction
     </en>*/
    private String _command;

    /**<jp>
     * MC Keyを保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves MC Key
     </en>*/
    private String _mcKey;

    /**<jp>
     * Location No.を保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves location No.
     </en>*/
    private String _locationNo;

    /**<jp>
     * Station No.を保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves station No.
     </en>*/
    private String _stationNo;

    /**<jp>
     * 荷姿情報を保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves load size
     </en>*/
    private String _dimInfo;

    /**<jp>
     * BC Dataを保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves BC Data
     </en>*/
    private String _bcData;

    /**<jp>
     * 作業No.を保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves work number
     </en>*/
    private String _workNo;

    /**<jp>
     * 制御情報を保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves control information
     </en>*/
    private String _controlInfo;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version adn the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id11()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 代替棚指示電文を作成します。<BR>
     * <p><code>
     * 電文の内容は、以下のようになります。<br>
     * <table>
     * <tr><td>指示区分</td><td>2 byte</td></tr>
     * <tr><td>MC Key</td><td>8 byte</td></tr>
     * <tr><td>Location No.</td><td>12 byte</td></tr>
     * <tr><td>Station No.</td><td>4 byte</td></tr>
     * <tr><td>荷姿情報</td><td>2 byte</td></tr>
     * <tr><td>BC Data</td><td>30 byte</td></tr>
     * <tr><td>作業No.</td><td>8 byte</td></tr>
     * <tr><td>制御情報</td><td>30 byte</td></tr>
     * </table></code></p>
     * @return    搬送先変更指示電文
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Creates communication message of alternative location command. <BR>
     * <p><code>
     * Contents of the message should include the following;<br>
     * <table>
     * <tr><td>Request claffification</td><td>2 byte</td></tr>
     * <tr><td>MC Key</td><td>8 byte</td></tr>
     * <tr><td>Location No.</td><td>12 byte</td></tr>
     * <tr><td>Station No.</td><td>4 byte</td></tr>
     * <tr><td>Load size</td><td>2 byte</td></tr>
     * <tr><td>BC Data</td><td>30 byte</td></tr>
     * <tr><td>Work number</td><td>8 byte</td></tr>
     * <tr><td>Control information</td><td>30 byte</td></tr>
     * </table></code></p>
     * @return    Communication message directing the changes of receiving station
     * @throws InvalidProtocolException  : Notifies if improper information from protocole aspt.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> Text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        //-------------------------------------------------
        //<jp> ここから先、順序が重要なので注意!</jp>
        //<en> Attention! Order of the contents must be observed!</en>
        //-------------------------------------------------
        //<jp> 指示区分</jp>
        //<en> Request claffification</en>
        mbuf.append(_command);
        // MC Key
        mbuf.append(_mcKey);
        // Location No.
        mbuf.append(_locationNo);
        //<jp> Station No.</jp>
        //<en> Station No.</en>
        mbuf.append(_stationNo);
        //<jp> 荷姿情報</jp>
        //<en> Load size</en>
        mbuf.append(_dimInfo);
        //<jp> BC Data</jp>
        //<en> BC Data</en>
        mbuf.append(_bcData);
        //<jp> 作業No.</jp>
        //<en> work number</en>
        mbuf.append(_workNo);
        //<jp> 制御情報</jp>
        //<en> control information</en>
        mbuf.append(_controlInfo);

        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Sets as sending message buffer.</en>
        //-------------------------------------------------
        // id
        setID("11");
        //<jp> id 区分</jp>
        //<en> id segment</en>
        setIDClass("00");
        //<jp> 送信時刻</jp>
        //<en> time sent</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> time sent to AGC</en>
        setAGCSendDate("000000");
        //<jp> テキスト内容</jp>
        //<en> text contents</en>
        setContent(String.valueOf(mbuf));
        
        return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    /**<jp>
     * 指示区分をセットします。
     * @param <code>command</code> 指示区分 01:New Location指示(二重格納時)<BR>
     *                                       02:Stationへの払出し指示(二重格納時)<BR>
     *                                       03:代替棚なし(二重格納時)<BR>
     *                                       11:New Location指示(空出荷時)<BR>
     *                                       12:Data Cancel指示(空出荷時)<BR>
     *                                       21:New Location指示(荷姿不一致時)<BR>
     *                                       22:Stationへの払出し指示(荷姿不一致時)<BR>
     *                                       23:代替棚なし(荷姿不一致時)
     * @throws InvalidProtocolException 不正な指示区分が指定された場合に報告されます。
     </jp>*/
    /**<en>
     * Sets classification of request.
     * @param command classification
     *         <code>command</code> command classification 01: directing new Location(location duplicate)<BR>
     *                                                     02:transfer out to another Station(location duplicate)<BR>
     *                                                     03:No alternative location (location duplicate)<BR>
     *                                                     11:directing New Location (empty retrieval)<BR>
     *                                                     12:directing Data Cancel (empty retrieval)<BR>
     *                                                     21:New Location directed (load size mismatch)<BR>
     *                                                     22:transfer out to Station (load size mismatch)<BR>
     *                                                     23:no alternative location (load size mismatch)
     * @throws InvalidProtocolException : Reports if entered command classification is invalid.
     </en>*/
    public void setRequestClass(String command)
            throws InvalidProtocolException
    {
        _command = command;
        if (_command == DUP_NEW_LOCATION)
        {
            //<jp> New Location指示 (二重格納時)</jp>
            //<en> directing to new location (location duplicate)</en>
        }
        else if (_command == DUP_PAID)
        {
            //<jp> Stationへの払出し指示 (二重格納時)</jp>
            //<en> transfer out to the next Station (location duplicate)</en>
        }
        else if (_command == DUP_NO_SUBSHELF)
        {
            //<jp> 代替棚なし (二重格納時)</jp>
            //<en> no alternative location (location duplicate )</en>
        }
        else if (_command == EMPTY_NEW_LOCATION)
        {
            //<jp> New Location指示 (空出荷時)</jp>
            //<en> directing New Location (after empty retrieval made)</en>
        }
        else if (_command == EMPTY_DATA_CANCEL)
        {
            //<jp> Data Cancel (空出荷時)</jp>
            //<en> Data Cancel (at empty retrival)</en>
        }
        else if (_command == DIM_NEW_LOCATION)
        {
            //<jp> New Location指示 (荷姿不一致時)</jp>
            //<en> directing New Location (load size mismatch)</en>
        }
        else if (_command == DIM_PAID)
        {
            //<jp> Stationへの払出し指示 (荷姿不一致時)</jp>
            //<en> Transfer out to another sation (load sizet mismatch)</en>
        }
        else if (_command == DIM_NO_SUBSHELF)
        {
            //<jp> 代替棚なし (荷姿不一致時)</jp>
            //<en> No alternative location (load size mismatch)</en>
        }
        else
        {
            throw new InvalidProtocolException("\n" + "was exception. request classificaiton = " + _command);
        }
    }

    /**<jp>
     * MC Keyをセットします。
     * @param mckey  MC Key
     * @throws InvalidProtocolException  MC Keyが指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Sets MC Key.
     * @param mckey 'MC Key
     * @throws InvalidProtocolException : Reports if MC Key is not the allowable length.
     </en>*/
    public void setMcKey(String mckey)
            throws InvalidProtocolException
    {
        _mcKey = mckey;
        //<jp> 文字列長チェック</jp>
        //<en> Checks the length of string</en>
        if (_mcKey.length() != CARRYKEY)
        {
            throw new InvalidProtocolException("\n" + "MC-KEY = " + CARRYKEY + "--->" + _mcKey);
        }
    }

    /**<jp>
     * ロケーションNo.をセットします。
     * @param <code>locationno</code> ロケーションNo.（新しい Locationを指示する場合に有効）
     * @throws InvalidProtocolException  ロケーションNo.が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Sets location number
     * @param locationno location number(valid when directing new location)
     * @throws InvalidProtocolException : Reports if location number is not the allowable length.
     </en>*/
    public void setLocationNo(String locationno)
            throws InvalidProtocolException
    {
        _locationNo = locationno;
        //<jp> 文字列長チェック</jp>
        //<en> Checks the length of string</en>
        if (_locationNo.length() != LOCATION_NO)
        {
            throw new InvalidProtocolException("\n" + "Location No. = " + LOCATION_NO + "--->" + _locationNo);
        }
        else if (_command == DUP_NEW_LOCATION || _command == EMPTY_NEW_LOCATION || _command == DIM_NEW_LOCATION)
        {
            //<jp> 新しいLocationを指示する場合に有効↓</jp>
            //<en> Valid when directing the new Location.</en>
        }
        else
        {
            //<jp> その他の場合は0(30H)で埋める</jp>
            //<en> Otherwise, fill out with 0(30H).</en>
            _locationNo = "000000000000";
        }
    }

    /**<jp>
     * ステーションNo.をセットします。
     * @param stationno  ステーションNo.（新しい Stationを指示する場合に有効）
     * @throws InvalidProtocolException  ステーションNo.が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Sets station number.
     * @param stationno  'station number(valid whebe directing new station)
     * @throws InvalidProtocolException : Reports if station number is not the allowable length.
     </en>*/
    public void setStationNo(String stationno)
            throws InvalidProtocolException
    {
        _stationNo = stationno;
        //<jp> 文字列長チェック</jp>
        //<en> checks the length of the string</en>
        if (_stationNo.length() != STATION_NO)
        {
            throw new InvalidProtocolException("\n" + "Station No.  = " + STATION_NO + "--->" + _stationNo);
        }
        else if (_command == DUP_PAID || _command == DIM_PAID)
        {
            //<jp> 新しいStationを指示する場合に有効↓</jp>
            //<en> valid when directing new satation</en>
        }
        else
        {
            //<jp> その他の場合は0(30H)で埋める</jp>
            //<en> otherwise, fil out with 0(30H).</en>
            _stationNo = "0000";
        }
    }

    /**<jp>
     * 荷姿情報をセットします。
     * @param diminfo 荷姿情報
     * @throws InvalidProtocolException  荷姿情報が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Sets load size.
     * @param diminfo :load size
     * @throws InvalidProtocolException : Reports if load size is not the allowable length.
     </en>*/
    public void setDimensionInfo(String diminfo)
            throws InvalidProtocolException
    {
        _dimInfo = diminfo;
        //<jp> 文字列長チェック</jp>
        //<en> Check the string length.</en>
        if (_dimInfo.length() != DIMENSION_INFORMATION)
        {
            throw new InvalidProtocolException("\n" + "load height = " + DIMENSION_INFORMATION + "--->" + _dimInfo);
        }
        else if (_command == DIM_NEW_LOCATION)
        {
            //<jp> 指示区分が21：New Location(荷姿不一致)の時のみ有効</jp>
            //<en> Valid only if the instruction classification is 21 (New Location- load size mismatch)</en>
        }
        else
        {
            //<jp> 無効のときは0で埋める</jp>
            //<en> If invalid, fill out with 0.</en>
            _dimInfo = "00";
        }
    }

    /**<jp>
     * バーコード情報をセットします。
     * @param bcdata バーコード情報
     * @throws InvalidProtocolException  バーコード情報が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Setes bar code information
     * @param bcdata :bar code information
     * @throws InvalidProtocolException : Reports if bar code information is not the allowable length.
     </en>*/
    public void setBcData(String bcdata)
            throws InvalidProtocolException
    {
        _bcData = bcdata;
        //<jp> 文字列長チェック</jp>
        //<en> checks length of the string</en>
        if (_bcData.length() != BC_DATA)
        {
            throw new InvalidProtocolException("\n" + "bar code data = " + BC_DATA + "--->" + _bcData);
        }
    }

    /**<jp>
     * 作業番号をセットします。
     * @param workno 作業番号
     * @throws InvalidProtocolException  作業番号が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Sets work number.
     * @param workno :work number
     * @throws InvalidProtocolException : Reports if work number is not the allowable length.
     </en>*/
    public void setWorkNo(String workno)
            throws InvalidProtocolException
    {
        _workNo = workno;
        //<jp> 文字列長チェック</jp>
        //<en> checks the length of string</en>
        if (_workNo.length() != WORK_NO)
        {
            throw new InvalidProtocolException("\n" + "work number = " + WORK_NO + "--->" + _workNo);
        }
    }

    /**<jp>
     * 制御情報をセットします。
     * @param controlinfo 制御情報
     * @throws InvalidProtocolException  制御情報が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Sets control information.
     * @param controlinfo :control information
     * @throws InvalidProtocolException : Reports if control information is not the allowable length.
     </en>*/
    public void setControlInfo(String controlinfo)
            throws InvalidProtocolException
    {
        _controlInfo = controlinfo;
        //<jp> 文字列長チェック</jp>
        //<en> Checks length of string </en>
        if (_controlInfo.length() != CONTROL_INFORMATION)
        {
            throw new InvalidProtocolException("\n" + "donctrol data = " + CONTROL_INFORMATION + "--->" + _controlInfo);
        }
    }
}
//end of class
