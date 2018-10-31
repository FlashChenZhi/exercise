// $Id: As21Id33.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「作業完了報告(WorkCompletionReport) ID=33」電文を処理します。
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
 * Processes communication message "Work Completion Report", ID=33 according to the AS21 communication protocol.
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
public class As21Id33
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID33の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>以下2回繰り返し</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>搬送区分:</td><td>1 byte</td></tr>
     * <tr><td>種別:</td><td>1 byte</td></tr>
     * <tr><td>完了区分:</td><td>1 byte</td></tr>
     * <tr><td>搬送元ステーション番号:</td><td>4 byte</td></tr>
     * <tr><td>搬送先ステーション番号:</td><td>4 byte</td></tr>
     * <tr><td>棚番号:</td><td>12 byte</td></tr>
     * <tr><td>棚間移動時 棚番号:</td><td>12 byte</td></tr>
     * <tr><td>荷姿情報:</td><td>2 byte</td></tr>
     * <tr><td>BC Data:</td><td>30 byte</td></tr>
     * <tr><td>作業番号:</td><td>8 byte</td></tr>
     * <tr><td>制御情報:</td><td>30 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID33 (excluding STX, SEQ-No, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>repeating the following for 2 cycle</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>transport section:</td><td>1 byte</td></tr>
     * <tr><td>type:</td><td>1 byte</td></tr>
     * <tr><td>completion classificaiton:</td><td>1 byte</td></tr>
     * <tr><td>sending station number:</td><td>4 byte</td></tr>
     * <tr><td>receiving station number:</td><td>4 byte</td></tr>
     * <tr><td>location number:</td><td>12 byte</td></tr>
     * <tr><td>location number at the location to location move:</td><td>12 byte</td></tr>
     * <tr><td>load size:</td><td>2 byte</td></tr>
     * <tr><td>BC Data:</td><td>30 byte</td></tr>
     * <tr><td>work number:</td><td>8 byte</td></tr>
     * <tr><td>control information:</td><td>30 byte</td></tr>
     * </table>
     * </p>
     </en>*/

    /**<jp>
     * ID33の電文長(STX,SEQ-No,BCC,ETXを除く)。
     </jp>*/
    /**<en>
     * Length of communication message ID33 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    static final int LEN_ID33 = 226 + 16;

    /**<jp>
     * ID33の出庫Dataのオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of Retrieval data of with ID33.
     </en>*/
    private static final int OFF_ID33_1ST = 0;

    /**<jp>
     * ID33の出庫Dataの長さ(byte)
     </jp>*/
    /**<en>
     * Length of Retrieval data with ID33(in byte)
     </en>*/
    private static final int OFF_ID33_2ND = 113;

    /**<jp>
     * ID33のMC Keyのオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of MC Key with ID33.
     </en>*/
    private static final int OFF_ID33_MCKEY = 0;

    /**<jp>
     * ID33のMC Keyの長さ(byte)
     </jp>*/
    /**<en>
     * Length of MC Key with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_MCKEY = 8;

    /**<jp>
     * ID33の搬送区分のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of transport section with ID33
     </en>*/
    private static final int OFF_ID33_TRANS_CLASS = OFF_ID33_MCKEY + LEN_ID33_MCKEY;

    /**<jp>
     * ID33の搬送区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of transport section with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_TRANS_CLASS = 1;

    /**<jp>
     * ID33の種別のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of type with ID33
     </en>*/
    private static final int OFF_ID33_CLASS = OFF_ID33_TRANS_CLASS + LEN_ID33_TRANS_CLASS;

    /**<jp>
     * ID33の種別の長さ(byte)
     </jp>*/
    /**<en>
     * Length of type with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_CLASS = 1;

    /**<jp>
     * ID33の完了区分のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of completion (classification) with ID33
     </en>*/
    private static final int OFF_ID33_COMP_CLASS = OFF_ID33_CLASS + LEN_ID33_CLASS;

    /**<jp>
     * ID33の完了区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of completion (classification) with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_COMP_CLASS = 1;

    /**<jp>
     * ID33の搬送元StationNo.のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of sending station No. with ID33
     </en>*/
    private static final int OFF_ID33_FROM_ST = OFF_ID33_COMP_CLASS + LEN_ID33_COMP_CLASS;

    /**<jp>
     * ID33の搬送元StationNo.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of sending station No. with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_FROM_ST = 4;

    /**<jp>
     * ID33の搬送先StationNo.のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of receiving station No. with ID33.
     </en>*/
    private static final int OFF_ID33_TO_ST = OFF_ID33_FROM_ST + LEN_ID33_FROM_ST;

    /**<jp>
     * ID33の搬送先StationNo.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of receiving station No. ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_TO_ST = 4;

    /**<jp>
     * ID33のLocationNo.のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of LocationNo. with ID33
     </en>*/
    private static final int OFF_ID33_LOCATION = OFF_ID33_TO_ST + LEN_ID33_TO_ST;

    /**<jp>
     * ID33のLocationNo.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of LocationNo. with ID33(in byte)
     </en>*/
    private static final int LEN_ID33_LOCATION = 12;

    /**<jp>
     * ID33の棚間移動時のLocationNo.のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of LocationNo. when moving from location to location with ID33
     </en>*/
    private static final int OFF_ID33_MOV_LOCATION = OFF_ID33_LOCATION + LEN_ID33_LOCATION;

    /**<jp>
     * ID33の棚間移動時のLocationNo.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of LocationNo. when moving from location to location with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_MOV_LOCATION = 12;

    /**<jp>
     * ID33の荷姿情報のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of load size with ID33
     </en>*/
    private static final int OFF_ID33_DIMENSION = OFF_ID33_MOV_LOCATION + LEN_ID33_MOV_LOCATION;

    /**<jp>
     * ID33の荷姿情報の長さ(byte)
     </jp>*/
    /**<en>
     * Length of load size with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_DIMENSION = 2;

    /**<jp>
     * ID33のBC Dataのオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of BC data with ID33
     </en>*/
    private static final int OFF_ID33_BCDATA = OFF_ID33_DIMENSION + LEN_ID33_DIMENSION;

    /**<jp>
     * ID33のBC Dataの長さ(byte)
     </jp>*/
    /**<en>
     * Length of BC data with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_BCDATA = 30;

    /**<jp>
     * ID33の作業No.のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of work No. with ID33
     </en>*/
    private static final int OFF_ID33_WORKNO = OFF_ID33_BCDATA + LEN_ID33_BCDATA;

    /**<jp>
     * ID33の作業No.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of work No. with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_WORKNO = 8;

    /**<jp>
     * ID33の制御情報のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of control data with ID33
     </en>*/
    private static final int OFF_ID33_CONTROL_INFO = OFF_ID33_WORKNO + LEN_ID33_WORKNO;

    /**<jp>
     * ID33の制御情報の長さ(byte)
     </jp>*/
    /**<en>
     * Length of control data with ID33 (in byte)
     </en>*/
    private static final int LEN_ID33_CONTROL_INFO = 30;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variable which preserves the communiation message.
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID33];

    /**<jp>
     * 出庫Dataの件数を保持するための変数。
     </jp>*/
    /**<en>
     * Variable for purpose of preserving the number of retrieval data
     </en>*/
    private int _countOfData = 0;

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id33()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id33 <code>as21Id33</code>  作業完了報告電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC and initialize this class.
     * @param as21Id33 :<code>as21Id33</code>  communication message "Work Completion Report"
     </en>*/
    public As21Id33(byte[] as21Id33)
    {
        super();
        setReceiveMessage(as21Id33);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業完了報告電文からMC Keyを取得します。
     * @return    MC Key の配列。2件の完了情報があれば、要素数が2になります。
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires the MC Key from the communication message "Work Completion Report"
     * @return    array of MC Key. If there are 2 completion data, then elements should be 2 accordingly.
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public String[] getMcKey()
            throws InvalidProtocolException
    {
        return (getCompInfo(OFF_ID33_MCKEY, LEN_ID33_MCKEY));
    }

    /**<jp>
     * 作業完了報告電文から搬送区分を取得します。
     * @return     搬送区分<BR>
     *              1:入庫<BR>
     *              2:出庫<BR>
     *              4:棚間移動(出庫)<BR>
     *              5:棚間移動(入庫)
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires transport section from the communication message "Work Completion Report".
     * @return     transport section<BR>
     *              1:storage<BR>
     *              2:retrieval<BR>
     *              4:location to location move (output)<BR>
     *              5:location to location move (intput)
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public int[] getTransportationClassification()
            throws InvalidProtocolException
    {
        return (getIntCompInfo(OFF_ID33_TRANS_CLASS, LEN_ID33_TRANS_CLASS));
    }

    /**<jp>
     * 作業完了報告電文から種別を取得します。
     * 搬送区分が出庫時のみ有効です。
     * @return     種別<BR>
     *              1:緊急出庫<BR>
     *              2:計画出庫<BR>
     *              9:空棚確認
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires type from the communication message "Work Completion Report"
     * Is valid only if the transport section specifies retrieval.
     * @return     type<BR>
     *              1:Urgent retrieval<BR>
     *              2:Planned retrieval<BR>
     *              9:Confirmation of empty location
     * @throws  InvalidProtocolException  : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public int[] getCategory()
            throws InvalidProtocolException
    {
        return (getIntCompInfo(OFF_ID33_CLASS, LEN_ID33_CLASS));
    }

    /**<jp>
     * 作業完了報告電文から完了区分を取得します。
     * @return     完了区分<BR>
     *              0:正常完了<BR>
     *              1:二重格納<BR>
     *              2:空出荷<BR>
     *              3:荷姿不一致<BR>
     *              7:空棚完了(種別が空棚確認の時)<BR>
     *              8:実棚完了(種別が空棚確認の時)<BR>
     *              9:Cancel
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires the completion (classification ) from the communication message "Work Completion Report"
     * @return     cpompletion (classification)<BR>
     *              0:normal completion<BR>
     *              1:multiple set<BR>
     *              2:empty retrieval<BR>
     *              3:load size mismatch<BR>
     *              7:empty location complete (when type is confirming the empty locations)<BR>
     *              8:result location complete (when type is confirming the empty locations)<BR>
     *              9:Cancel
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public int[] getCompletionClassification()
            throws InvalidProtocolException
    {
        return (getIntCompInfo(OFF_ID33_COMP_CLASS, LEN_ID33_COMP_CLASS));
    }

    /**<jp>
     * 作業完了報告電文から搬送元StationNo.を取得します。
     * @return    搬送元StationNo.
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires sending station No. from the communication message "Work Completion Report".
     * @return    sending station No.
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public String[] getSendingStationNo()
            throws InvalidProtocolException
    {
        return (getCompInfo(OFF_ID33_FROM_ST, LEN_ID33_FROM_ST));
    }

    /**<jp>
     * 作業完了報告電文から搬送先StationNo.を取得します。
     * @return    搬送先StationNo.
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires receiving station No. from the communication message "Work Completion Report".
     * @return    receiving stationNo.
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public String[] getReceivingStationNo()
            throws InvalidProtocolException
    {
        return (getCompInfo(OFF_ID33_TO_ST, LEN_ID33_TO_ST));
    }

    /**<jp>
     * 作業完了報告電文からLocationNo.を取得します。
     * @return    LocationNo.
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquire location No. from the communication message "Work Completion Report".
     * @return    LocationNo.
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public String[] getLocationNo()
            throws InvalidProtocolException
    {
        return (getCompInfo(OFF_ID33_LOCATION, LEN_ID33_LOCATION));
    }

    /**<jp>
     * 作業完了報告電文から棚間移動時LocationNo.を取得します。
     * @return    棚間移動時LocationNo.
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires location No. at the location to location move from the communication message "Work Completion Report"
     * @return    location No. at location to location move
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public String[] getLtoLocationNo()
            throws InvalidProtocolException
    {
        return (getCompInfo(OFF_ID33_MOV_LOCATION, LEN_ID33_MOV_LOCATION));
    }

    /**<jp>
     * 作業完了報告電文から荷姿情報を取得します。
     * @return    荷姿情報
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires load size from the communication message "Work Completion Report".
     * @return    load size
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public int[] getDimension()
            throws InvalidProtocolException
    {
        return (getIntCompInfo(OFF_ID33_DIMENSION, LEN_ID33_DIMENSION));
    }

    /**<jp>
     * 作業完了報告電文からBC Dataを取得します。
     * @return    BC Data
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires BC data from communication message "Work Completion Report".
     * @return    BC Data
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public String[] getBcData()
            throws InvalidProtocolException
    {
        return (getCompInfo(OFF_ID33_BCDATA, LEN_ID33_BCDATA));
    }

    /**<jp>
     * 作業完了報告電文から作業No.を取得します。
     * @return    作業No.
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires work No. from communication message "Work Completion Report".
     * @return    work No.
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public String[] getWorkNo()
            throws InvalidProtocolException
    {
        return (getCompInfo(OFF_ID33_WORKNO, LEN_ID33_WORKNO));
    }

    /**<jp>
     * 作業完了報告電文から制御情報を取得します。
     * @return    制御情報
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires control information from the communication message "Work Completion Report".
     * @return    control data
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    public String[] getControlInformation()
            throws InvalidProtocolException
    {
        return (getCompInfo(OFF_ID33_CONTROL_INFO, LEN_ID33_CONTROL_INFO));
    }

    /**<jp>
     * 作業完了報告電文の内容を取得します。
     * @return  作業完了報告電文の内容
     </jp>*/
    /**<en>
     * Acquires contents of communication message "Work Completion Report".
     * @return  contents of communication message "Work Completion Report".
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 作業完了情報件数にあわせて、1件または2件の情報を内部バッファから取得します。
     * @param offset  1件目の情報へのオフセット。2件目のオフセットは自動的に計算されます。
     * @param len  取得する情報の長さ(Byte)
     * @return 電文より取得されたoffsetで指定された件数目の情報(String型)
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires 1 or 2 data, according to the number of work completion cases, from internal buffer.
     * @param offset  Offsetting the 1st data. OFfsetting of 2nd data will wutomatically be calculated.
     * @param len  length of data to acquire (in byte)
     * @return the data specified by offset acquired from communication message(String type).
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    protected String[] getCompInfo(int offset, int len)
            throws InvalidProtocolException
    {
        String[] rst = new String[_countOfData];
        try
        {
            for (int i = 0; i < _countOfData; i++)
            {
                int toff = offset + (OFF_ID33_2ND * i);
                rst[i] = getContent().substring(toff, toff + len);
            }
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Encode error"));
        }
        return (rst);
    }

    /**<jp>
     * 数値項目について作業完了情報件数にあわせて、1件または2件の数値情報を内部バッファから取得します。
     * @param offset  1件目の情報へのオフセット。2件目のオフセットは自動的に計算されます。
     * @param len  取得する情報の長さ(Byte)
     * @return 電文より取得されたoffsetで指定された件数目の情報(int型)
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires 1 or 2 numeric datas from the internal buffer, according to the number of work completion data.
     * @param offset  Offset the 1st data; for 2nd data the offsetting will be automatically calculated.
     * @param len  Length of acquiring data (in byte)
     * @return the data specified by offset, which was acquired from the communication message(int type).
     * @throws  InvalidProtocolException : Notifies if the value used is not following the communication message protocol.
     </en>*/
    protected int[] getIntCompInfo(int offset, int len)
            throws InvalidProtocolException
    {
        String[] wtc = getCompInfo(offset, len);
        int[] idt = new int[_countOfData];
        try
        {
            for (int i = 0; i < _countOfData; i++)
            {
                idt[i] = Integer.parseInt(wtc[i]);
            }
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException());
        }
        return (idt);
    }

    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * また、出庫Dataの件数が何件あるかをカウントしてセットします。
     * @param rmsg 作業完了情報電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * Also counts and sets the number of retrieval data.
     * @param rmsg the communication message "Work Completion Report"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        int offset;
        String mckey;

        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID33; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;

        //<jp> 出庫Dataをカウント</jp>
        //<en> counting retrieval data</en>
        offset = OFF_ID33_MCKEY;
        mckey = getContent().substring(offset, offset + LEN_ID33_MCKEY);
        if (mckey.equals(NULL_MC_KEY))
        {
            _countOfData = 0;
        }
        else
        {
            _countOfData = 1;

            offset = OFF_ID33_2ND + OFF_ID33_MCKEY;
            mckey = getContent().substring(offset, offset + LEN_ID33_MCKEY);
            if (!mckey.equals(NULL_MC_KEY))
            {
                _countOfData++;
            }
        }
    }

    // Private methods -----------------------------------------------

}
//end of class

