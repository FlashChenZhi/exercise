// $Id: As21Id08.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「搬送先変更指示(ReceivingStationChangeCommand) ID=08」電文を組み立てます。
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
 * Composes communication message "Command : changing the receiving station ID=08" according to AS21 communication protocol.
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
public class As21Id08
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 指示区分を表すフィールド（搬送先変更指示）
     </jp>*/
    /**<en>
     * Field of command (to change receiving station)
     </en>*/
    public static final String STATION_CHANGE = "1";

    /**<jp>
     * 指示区分を表すフィールド（搬送先変更不可）
     </jp>*/
    /**<en>
     * Field of command (NOT possible to change the receiving station)
     </en>*/
    public static final String STATION_NOT_CHANGE = "2";

    /**<jp>
     * MC Keyの長さ
     </jp>*/
    /**<en>
     * Length of MC Key
     </en>*/
    protected static final int LEN_CARRYKEY = 8;

    /**<jp>
     * 指示区分の長さ
     </jp>*/
    /**<en>
     * Length of command classification
     </en>*/
    protected static final int LEN_COMMANDCLASS = 1;

    /**<jp>
     * Location No.の長さ
     </jp>*/
    /**<en>
     * Length of location number
     </en>*/
    protected static final int LEN_LOCATION = 12;

    /**<jp>
     * Reject Station No.の長さ
     </jp>*/
    /**<en>
     * Length of station number
     </en>*/
    protected static final int LEN_STATION = 4;

    /**<jp>
     * AGCデータの長さ
     </jp>*/
    /**<en>
     * Length ofAGC data
     </en>*/
    protected static final int LEN_AGCDATA = 6;

    /**<jp>
     * 電文長
     </jp>*/
    /**<en>
     * Length of the message
     </en>*/
    protected static final int LEN_TOTAL =
            OFF_CONTENT + LEN_CARRYKEY + LEN_COMMANDCLASS + LEN_LOCATION + LEN_STATION + LEN_AGCDATA;

    // Class variables -----------------------------------------------
    /**<jp>
     * MC Keyを保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves MC Key.
     </en>*/
    private String _mckey;

    /**<jp>
     * 指示区分を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves the segments of instruction.
     </en>*/
    private boolean _modeChangeCommand;

    /**<jp>
     * ロケーションを保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves locations
     </en>*/
    private String _location;

    /**<jp>
     * リジェクトステーションNo.を保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves the reject station number
     </en>*/
    private String _station;

    /**<jp>
     * AGCDATAを保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves AGCDATA.
     </en>*/
    private String _agcData;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of the the class.
     * @return version and the date
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
    public As21Id08()
    {
    }

    /**<jp>
     * 搬送先変更を指示する搬送DataのMcKey、変更の指示内容、ロケーションNo.
     * リジェクトステーションNo.、AGCからの搬送先変更要求電文を指定して、
     * このクラスのインスタンスを生成します。
     * @param  mcKey       McKey
     * @param  modeCommand 指示区分
     *                          True：搬送先変更指示
     *                          False：搬送先変更不可
     * @param  locationNo      ロケーションNo.
     * @param  rejectStationNo リジェクトステーションNo.
     * @param  agcdata         AGCからの搬送先変更要求電文
     </jp>*/
    /**<en>
     * Generates the instance of this class by specifying MC Key of carry data, which indicates
     * the receiving station changes, the detail of contents changed, location number, reject
     * station number and the communication message requesting to change the receiving station from AGC.
     *
     * @param  mcKey           McKey
     * @param  modeCommand     instruction segment
     *                          True:Instruction to change receiving station
     *                          False:NOT possible to change the receiving station
     * @param  locationNo      Location number
     * @param  rejectStationNo Reject station number
     * @param  agcdata         Communication message requesting to chnage the receiving station from AGC
     </en>*/
    public As21Id08(String mcKey, boolean modeCommand, String locationNo, String rejectStationNo, String agcdata)
    {
        super();
        setMckey(mcKey);
        setModeCommand(modeCommand);
        setLocationNo(locationNo);
        setRejectStationNo(rejectStationNo);
        setAgcdata(agcdata);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * 搬送先変更指示電文を作成します。<BR>
     * @return    搬送先変更指示電文
     </jp>*/
    /**<en>
     * Creates communication message directing to change the receiving station.<BR>
     * @return    instruction message to change receiving station
     </en>*/
    public String getSendMessage()
    {
        String sCommand;

        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);
        // Mc Key
        mbuf.append(_mckey);
        //<jp> 指示区分</jp>
        //<en> instruction segment</en>
        if (_modeChangeCommand)
        {
            sCommand = STATION_CHANGE;
        }
        else
        {
            sCommand = STATION_NOT_CHANGE;
        }
        mbuf.append(sCommand);
        // Location No. 
        mbuf.append(_location);
        // Reject Station No.
        mbuf.append(_station);
        // AGC Data
        mbuf.append(_agcData);


        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Sets as sending message buffer</en>
        //-------------------------------------------------
        // id
        setID("08");
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
        //<en> content of text</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL));
    }

    /**<jp>
     * Mc keyの値をセット
     * @param mcKey Mc key
     </jp>*/
    /**<en>
     * Sets value of Mc key
     * @param mcKey Mc key
     </en>*/
    private void setMckey(String mcKey)
    {
        _mckey = mcKey;
    }

    /**<jp>
     * ModeCommandの値をセット
     * @param modeCommand 指示区分
     </jp>*/
    /**<en>
     * Sets value of ModeCommand.
     * @param modeCommand ModeCommand
     </en>*/
    private void setModeCommand(boolean modeCommand)
    {
        _modeChangeCommand = modeCommand;
    }

    /**<jp>
     * LocationNoの値をセット
     * @param locationNo ロケーションNo.
     </jp>*/
    /**<en>
     * Sets value of Location number
     * @param locationNo LocationNo
     </en>*/
    private void setLocationNo(String locationNo)
    {
        _location = locationNo;
    }

    /**<jp>
     * RejectStationNoで値をセット
     * @param rejectStationNo リジェクトステーションNo.
     </jp>*/
    /**<en>
     * Sets value of RejectStationNo.
     * @param rejectStationNo RejectStationNo
     </en>*/
    private void setRejectStationNo(String rejectStationNo)
    {
        _station = rejectStationNo;
    }

    /**<jp>
     * Agcdataで値をセット
     * @param agcdata AGCからの搬送先変更要求電文
     </jp>*/
    /**<en>
     * Sets value of Agcdata
     * @param agcdata Agcdata
     </en>*/
    private void setAgcdata(String agcdata)
    {
        _agcData = agcdata;
    }
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}

// end of class
