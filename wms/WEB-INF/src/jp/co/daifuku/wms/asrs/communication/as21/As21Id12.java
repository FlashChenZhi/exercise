// $Id: As21Id12.java 4843 2009-08-20 07:45:35Z ota $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.DecimalFormat;

import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * AS21通信プロトコルでの「出庫指示(RetrievalCommand) ID=12」電文を組み立てます。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4843 $, $Date: 2009-08-20 16:45:35 +0900 (木, 20 8 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * Composes message for "Retrieval Command ID=12" according to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4843 $, $Date: 2009-08-20 16:45:35 +0900 (木, 20 8 2009) $
 * @author  $Author: ota $
 </en>*/
public class As21Id12
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 搬送区分を表すフィールド (出庫)
     </jp>*/
    /**<en>
     * Field of retrieval (carry segment)
     </en>*/
    public static final String C_RETRIEVAL = "2";

    /**<jp>
     * 搬送区分を表すフィールド (棚間移動出庫)
     </jp>*/
    /**<en>
     * Field of location to location move (carry segment)
     </en>*/
    public static final String C_MOVE = "5";

    /**<jp>
     * 出庫種別を表すフィールド (緊急出庫)
     </jp>*/
    /**<en>
     * Field of retrieval type (urgent retrieval)
     </en>*/
    public static final String RETRIEVAL_CLASS_EMG = "1";

    /**<jp>
     * 出庫種別を表すフィールド (計画出庫)
     </jp>*/
    /**<en>
     * Field of retrieval type (planned retrieval)
     </en>*/
    public static final String RETRIEVAL_CLASS_NORMAL = "2";

    /**<jp>
     * 出庫種別を表すフィールド (空棚確認)
     </jp>*/
    /**<en>
     * Field of retrieval type (confirmation for the empty location)
     </en>*/
    public static final String RETRIEVAL_CLASS_LOC_CONFIRM = "9";

    /**<jp>
     * 再入庫フラグを表すフィールド(再入庫なし)
     </jp>*/
    /**<en>
     * Field of re-storing (no restorage)
     </en>*/
    public static final String RETRIEVAL_NO_RETURN = "0";

    /**<jp>
     * 再入庫フラグを表すフィールド(同一棚へ再入庫)
     </jp>*/
    /**<en>
     * Field of re-storing (re-storing to the same location)
     </en>*/
    public static final String RETRIEVAL_RETURN = "1";

    /**<jp>
     * 出庫指示詳細を表すフィールド(在庫確認)
     </jp>*/
    /**<en>
     * Field of retrieval instruction in detail (confirmation of stocks)
     </en>*/
    public static final String RETRIEVAL_DETAIL_CONFIRM = "0";

    /**<jp>
     * 出庫指示詳細を表すフィールド(ユニット出庫)
     </jp>*/
    /**<en>
     * Field of retrieval instruction in detail (unit retrieval)
     </en>*/
    public static final String RETRIEVAL_DETAIL_UNIT = "1";

    /**<jp>
     * 出庫指示詳細を表すフィールド(ピッキング出庫)
     </jp>*/
    /**<en>
     * Field of retrieval instruction in detail (pick retrieval)
     </en>*/
    public static final String RETRIEVAL_DETAIL_PICK = "2";

    /**<jp>
     * 出庫指示詳細を表すフィールド(積増入庫)
     </jp>*/
    /**<en>
     * Field of retrieval instruction in detail (adding storing)
     </en>*/
    public static final String RETRIEVAL_DETAIL_ADDIN = "3";

    /**<jp>
     * MC Keyの長さ
     </jp>*/
    /**<en>
     * Length of MC Key
     </en>*/
    private static final int LEN_CARRYKEY = 8;

    /**<jp>
     * 搬送区分の長さ
     </jp>*/
    /**<en>
     * Length of transport classification
     </en>*/
    private static final int LEN_TRANSPORT_CLASSIFICATION = 1;

    /**<jp>
     * 種別の長さ
     </jp>*/
    /**<en>
     * Length of type
     </en>*/
    private static final int LEN_RETRIEVAL_CLASS = 1;

    /**<jp>
     * 再入庫フラグの長さ
     </jp>*/
    /**<en>
     * Length of re-storing flag
     </en>*/
    private static final int LEN_RETURN_FLAG = 1;

    /**<jp>
     * 出庫指示詳細の長さ
     </jp>*/
    /**<en>
     * Length of retrieval detailed instruction 
     </en>*/
    private static final int LEN_RETRIEVAL_DETAIL = 1;

    /**<jp>
     * グループ番号の長さ
     </jp>*/
    /**<en>
     * Length of group number
     </en>*/
    private static final int LEN_GROUP_NO = 3;

    /**<jp>
     * Station No.の長さ
     </jp>*/
    /**<en>
     * Length of Station number
     </en>*/
    private static final int LEN_STATION_NO = 4;

    /**<jp>
     * Location No.の長さ
     </jp>*/
    /**<en>
     * Length of Location number
     </en>*/
    private static final int LEN_LOCATION_NO = 12;

    /**<jp>
     * 荷姿情報の長さ
     </jp>*/
    /**<en>
     * Length of load size
     </en>*/
    private static final int LEN_DIMENSION_INFORMATION = 2;

    /**<jp>
     * BC Dataの長さ
     </jp>*/
    /**<en>
     * Length of Bar Code Data
     </en>*/
    private static final int LEN_BC_DATA = 30;

    /**<jp>
     * 作業No.の長さ
     </jp>*/
    /**<en>
     * Length of work number
     </en>*/
    private static final int LEN_WORK_NO = 8;

    /**<jp>
     * 制御情報の長さ
     </jp>*/
    /**<en>
     * Length of control information
     </en>*/
    private static final int LEN_CONTROL_INFORMATION = 30;

    /**<jp>
     * 個々の出庫指示電文長
     </jp>*/
    /**<en>
     * Length of each message for retrieval instruction 
     </en>*/
    private static final int LEN_PIECE =
            LEN_CARRYKEY + LEN_TRANSPORT_CLASSIFICATION + LEN_RETRIEVAL_CLASS + LEN_RETURN_FLAG + LEN_RETRIEVAL_DETAIL
                    + LEN_GROUP_NO + (LEN_STATION_NO * 2) // From and To
                    + (LEN_LOCATION_NO * 2) // From and To
                    + LEN_DIMENSION_INFORMATION + LEN_BC_DATA + LEN_WORK_NO + LEN_CONTROL_INFORMATION;

    /**<jp>
     * セットできる情報数
     </jp>*/
    /**<en>
     * Number of information available to set
     </en>*/
    private static final int CNT_OF_DATA = 2;

    // Class variables -----------------------------------------------
    /**<jp>
     * データベースコネクションオブジェクト、<code>Connection</code>を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves <code>Connection</code> containing database connection
     </en>*/
    private Connection _conn = null;

    /**<jp>
     * 搬送情報を持つ、<code>CarryInformation</code>を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves <code>CarryInformation</code> containing carry information
     </en>*/
    private CarryInfo[] _carryInfo;

    /**<jp>
     * パレット情報を持つ、<code>Pallet</code>を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves <code>Pallet</code>, containing pallet information.
     </en>*/
    private Pallet _pallet;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 4843 $,$Date: 2009-08-20 16:45:35 +0900 (木, 20 8 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id12()
    {
        super();
    }

    /**<jp>
     * 搬送すべき情報を持った<code>CarryInformation</code>インスタンスを指定して、この
     * クラスのインスタンスを生成します。
     * @param  ci   搬送情報を持つ<code>CarryInformation</code>
     * @param  conn データベースコネクション
     </jp>*/
    /**<en>
     * Generates instance of this class by specifying the instance <code>CarryInformation</code>,
     * which owns information to carry.
     * @param  ci   <code>CarryInformation</code> which owns carry information
     * @param  conn database connection object
     </en>*/
    public As21Id12(CarryInfo[] ci, Connection conn)
    {
        super();
        setCarryInfo(ci);
        setConnection(conn);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 出庫指示電文を作成します。
     * 出庫指示を出すのに必要な情報はCarryInformationのインスタンスから入手します。
     * <p><code>
     * 電文の内容は、以下のようになります。<br>
     * <table>
     * <tr><td>Mckey</td><td>8 byte</td></tr>
     * <tr><td>搬送区分</td><td>1 byte</td></tr>
     * <tr><td>種別</td><td>1 byte</td></tr>
     * <tr><td>再入庫フラグ</td><td>1 byte</td></tr>
     * <tr><td>出庫指示詳細</td><td>1 byte</td></tr>
     * <tr><td>グループNo.</td><td>3 byte</td></tr>
     * <tr><td>搬送元ステーション</td><td>4 byte</td></tr>
     * <tr><td>搬送先ステーション</td><td>4 byte</td></tr>
     * <tr><td>出庫元棚No.</td><td>12 byte</td></tr>
     * <tr><td>搬送先棚No.</td><td>12 byte</td></tr>
     * <tr><td>荷姿情報</td><td>2 byte</td></tr>
     * <tr><td>BC Data</td><td>30 byte</td></tr>
     * <tr><td>作業No.</td><td>8 byte</td></tr>
     * <tr><td>制御情報</td><td>30 byte</td></tr>
     * </table></code></p>
     * @return    出庫指示電文
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Creates message of retrieval instruction.
     * Necessary information to create the retrieval instruction can be acquired from the instance of 
     * CarryInformation.
     * <p><code>
     * Contents of the message should include the following;<br>
     * <table>
     * <tr><td>Mckey</td><td>8 byte</td></tr>
     * <tr><td>Carry segment</td><td>1 byte</td></tr>
     * <tr><td>Type</td><td>1 byte</td></tr>
     * <tr><td>Re-storing flag</td><td>1 byte</td></tr>
     * <tr><td>Retrieval detailed instruction</td><td>1 byte</td></tr>
     * <tr><td>Group number</td><td>3 byte</td></tr>
     * <tr><td>Sending station</td><td>4 byte</td></tr>
     * <tr><td>Receiving station</td><td>4 byte</td></tr>
     * <tr><td>origination location number</td><td>12 byte</td></tr>
     * <tr><td>destination location number</td><td>12 byte</td></tr>
     * <tr><td>Load size</td><td>2 byte</td></tr>
     * <tr><td>BC Data</td><td>30 byte</td></tr>
     * <tr><td>Work number</td><td>8 byte</td></tr>
     * <tr><td>Control information</td><td>30 byte</td></tr>
     * </table></code></p>
     * @return    Retrieval instruction message
     * @throws InvalidProtocolException  : Notifies if communication message includes improper contents in protocol aspect.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        String rstr;
        byte[] mBuffB = new byte[LEN_PIECE * CNT_OF_DATA];
        for (int i = 0; i < mBuffB.length; i++)
        {
            mBuffB[i] = '0';
        }

        int bidx = 0;

        try
        {
            for (int i = 0; i < getCarryInfo().length; i++)
            {
                if (getCarryInfo(i) != null)
                {
                    // パレット情報を検索する
                    // 搬送指示テキスト用のパレット情報検索
                    PalletHandler palletHandelr = new PalletHandler(getConnection());
                    PalletSearchKey palletKey = new PalletSearchKey();
                    Pallet pallet = null;
                    palletKey.setPalletId(getCarryInfo(i).getPalletId());
                    pallet = (Pallet)palletHandelr.findPrimary(palletKey);
                    setPallet(pallet);

                    // MC key
                    setByteArray(mBuffB, bidx, getMCKey(i), LEN_CARRYKEY);
                    bidx += LEN_CARRYKEY;
                    //<jp> 搬送区分</jp>
                    //<en> transport section</en>
                    setByteArray(mBuffB, bidx, getTransClass(i), LEN_TRANSPORT_CLASSIFICATION);
                    bidx += LEN_TRANSPORT_CLASSIFICATION;
                    //<jp> 種別</jp>
                    //<en> type</en>
                    setByteArray(mBuffB, bidx, getRetrievalClass(i), LEN_RETRIEVAL_CLASS);
                    bidx += LEN_RETRIEVAL_CLASS;
                    //<jp> 再入庫フラグ</jp>
                    //<en> re-storing flag</en>
                    setByteArray(mBuffB, bidx, getReturnFlag(i), LEN_RETURN_FLAG);
                    bidx += LEN_RETURN_FLAG;
                    //<jp> 出庫指示詳細</jp>
                    //<en> retrieval indication in detail</en>
                    setByteArray(mBuffB, bidx, getRetrievalDetail(i), LEN_RETRIEVAL_DETAIL);
                    bidx += LEN_RETRIEVAL_DETAIL;
                    //<jp> グループNo.</jp>
                    //<en> group number</en>
                    setByteArray(mBuffB, bidx, getGroupNo(i), LEN_GROUP_NO);
                    bidx += LEN_GROUP_NO;
                    //<jp> 搬送元ステーション</jp>
                    //<en> Sending station</en>
                    setByteArray(mBuffB, bidx, getFromStationNo(i), LEN_STATION_NO);
                    bidx += LEN_STATION_NO;
                    //<jp> 搬送先ステーション</jp>
                    //<en> Receiving station</en>
                    setByteArray(mBuffB, bidx, getDestStationNo(i), LEN_STATION_NO);
                    bidx += LEN_STATION_NO;
                    //<jp> 出庫元棚No.</jp>
                    //<en> originating location number</en>
                    setByteArray(mBuffB, bidx, getFromLocationNo(i), LEN_LOCATION_NO);
                    bidx += LEN_LOCATION_NO;
                    //<jp> 搬送先棚No.</jp>
                    //<en> destined location number</en>
                    setByteArray(mBuffB, bidx, getDestLocationNo(i), LEN_LOCATION_NO);
                    bidx += LEN_LOCATION_NO;
                    //<jp> 荷姿情報</jp>
                    //<en> load size</en>
                    setByteArray(mBuffB, bidx, getDimensionInfo(i), LEN_DIMENSION_INFORMATION);
                    bidx += LEN_DIMENSION_INFORMATION;
                    // BC Data
                    setByteArray(mBuffB, bidx, getBcData(i), LEN_BC_DATA);
                    bidx += LEN_BC_DATA;
                    //<jp> 作業No.</jp>
                    //<en> work number</en>
                    setByteArray(mBuffB, bidx, getWorkNo(i), LEN_WORK_NO);
                    bidx += LEN_WORK_NO;
                    //<jp> 制御情報</jp>
                    //<en> control information</en>
                    setByteArray(mBuffB, bidx, getControlInfo(i), LEN_CONTROL_INFORMATION);
                    bidx += LEN_CONTROL_INFORMATION;
                }
            }
            //-------------------------------------------------
            //<jp> 送信メッセージバッファに設定</jp>
            //<en> Setting for sending message buffer</en>
            //-------------------------------------------------
            // id
            setID("12");
            //<jp> id 区分</jp>
            //<en> id segment</en>
            setIDClass("00");
            //<jp> 送信時刻</jp>
            //<en> time sent</en>
            setSendDate();
            //<jp> AGC送信時刻</jp>
            //<en> time sent to AGC</en>
            setAGCSendDate();
            rstr = new String(mBuffB, CommunicationAgc.CODE);
            //<jp> テキスト内容</jp>
            //<en> contents of text</en>
            setContent(rstr);
            return (getFromBuffer(0, LEN_PIECE * 2 + OFF_CONTENT));
        }
        catch (Exception e)
        {
            //<jp>エラーをログファイルに落とす</jp>
            //<en>Records errors in the log file.</en>
            // 6026052=送信電文組み立て処理でエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026052, e), getClass().getName());
            throw (new InvalidProtocolException(String.valueOf(e)));
        }
    }

    // Accessor methods -----------------------------------------------
    /**
     * データベースコネクションを返します。
     * @return データベースコネクション
     */
    public Connection getConnection()
    {
        return _conn;
    }

    /**
     * データベースコネクションを設定します。
     * @param connection データベースコネクション
     */
    public void setConnection(Connection connection)
    {
        _conn = connection;
    }

    /**<jp>
     * 搬送情報を持つ<code>CarryInformation</code>配列を取得します。
     * @return 現在保持している<code>CarryInformation</code>配列
     </jp>*/
    /**<en>
     * Retrieve <code>CarryInformation</code> to contain carry information array.
     * @return :<code>CarryInformation</code> array currently preserved
     </en>*/
    protected CarryInfo[] getCarryInfo()
    {
        return _carryInfo;
    }

    /**<jp>
     * 搬送情報を持つ<code>CarryInformation</code>配列を設定します。
     * @param carryInfo  現在保持している<code>CarryInformation</code>配列
     </jp>*/
    /**<en>
     * Set <code>CarryInformation</code> to contain carry information array.
     * @param carryInfo :<code>CarryInformation</code> array currently preserved
     </en>*/
    public void setCarryInfo(CarryInfo[] carryInfo)
    {
        _carryInfo = carryInfo;
    }

    /**<jp>
     * 搬送情報を持つ<code>CarryInformation</code>を取得します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 現在保持している<code>CarryInformation</code>
     </jp>*/
    /**<en>
     * Retrieve <code>CarryInformation</code> to contain carry information..
     * @param  i :index of <code>CarryInformation</code>
     * @return :<code>CarryInformation</code> currently preserved
     </en>*/
    protected CarryInfo getCarryInfo(int i)
    {
        return _carryInfo[i];
    }

    /**<jp>
     * パレット情報を持つ<code>Pallet</code>を設定します。
     * @param pallet  現在保持している<code>Pallet</code>
     </jp>*/
    /**<en>
     * Set <code>Pallet</code> to contain pallet information.
     * @param pallet :<code>Pallet</code> currently preserved
     </en>*/
    public void setPallet(Pallet pallet)
    {
        _pallet = pallet;
    }

    /**<jp>
     * パレット情報を持つ<code>Pallet</code>を取得します。
     * @return 現在保持している<code>Pallet</code>
     </jp>*/
    /**<en>
     * Retrieve <code>Pallet</code> to contain pallet information.
     * @return :<code>Pallet</code> currently preserved
     </en>*/
    protected Pallet getPallet()
    {
        return _pallet;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * <code>CarryInformation</code>からMC Keyを取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return MC Key
     * @throws InvalidProtocolException MC Keyのデータ長が指定の長さを超えた場合に報告されます。
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes MC Key from <code>CarryInformation</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return MC Key
     * @throws InvalidProtocolException : Reports if MC Key exceeds the allowable length.
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getMCKey(int i)
            throws InvalidProtocolException,
                UnsupportedEncodingException
    {
        //<jp> MC Key(これはCarryInformationにある情報)</jp>
        //<en> MC Key (included in CarryInformation)</en>
        byte[] carryKey = getCarryInfo(i).getCarryKey().getBytes(CommunicationAgc.CODE);

        if (carryKey.length != LEN_CARRYKEY)
        {
            throw new InvalidProtocolException("Invalid carryKey:" + new String(carryKey, CommunicationAgc.CODE));
        }
        return (carryKey);
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送区分を取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 搬送区分
     * @throws InvalidProtocolException 不正な搬送区分が指定された場合に報告されます。
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes transport section from <code>CarryInformation</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return transport section
     * @throws InvalidProtocolException : Reports if entered transport section is invalid.
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getTransClass(int i)
            throws InvalidProtocolException,
                UnsupportedEncodingException
    {
        byte[] tc;
        //<jp> 搬送区分(これはCarryInformationにある情報)</jp>
        //<en> Transport section (included in CarryInformation)</en>
        String inoutKind = getCarryInfo(i).getCarryFlag();
        if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(inoutKind))
        {
            tc = C_RETRIEVAL.getBytes(CommunicationAgc.CODE);
        }
        else if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(inoutKind))
        {
            tc = C_MOVE.getBytes(CommunicationAgc.CODE);
        }
        else
        {
            throw new InvalidProtocolException("Invalid Transport Class:" + inoutKind);
        }

        return (tc);
    }

    /**<jp>
     * <code>CarryInformation</code>から種別を取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 種別 
     * @throws InvalidProtocolException 不正な種別が指定された場合に報告されます。
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes type from <code>CarryInformation</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return type 
     * @throws InvalidProtocolException : Reports if entered type is invalid.
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getRetrievalClass(int i)
            throws InvalidProtocolException,
                UnsupportedEncodingException
    {
        byte[] rc;
        //<jp> 種別(これはCarryInformationにある情報)</jp>
        //<en> Type (included in CarryInformation)</en>
        String rclass = getCarryInfo(i).getPriority();
        //<jp> 緊急</jp>
        //<en> Urgent</en>
        if (CarryInfo.PRIORITY_EMERGENCY.equals(rclass))
        {
            rc = RETRIEVAL_CLASS_EMG.getBytes(CommunicationAgc.CODE);
        }
        //<jp> 空棚確認</jp>
        //<en> Confirmation of empty location</en>
        else if (CarryInfo.PRIORITY_NORMAL.equals(rclass))
        {
            rc = RETRIEVAL_CLASS_NORMAL.getBytes(CommunicationAgc.CODE);
        }
        //<jp> 空棚確認</jp>
        //<en> Confirmation of empty location</en>
        else if (CarryInfo.PRIORITY_CHECK_EMPTY.equals(rclass))
        {
            rc = RETRIEVAL_CLASS_LOC_CONFIRM.getBytes(CommunicationAgc.CODE);
        }
        else
        {
            throw new InvalidProtocolException("Invalid Retrieval Class:" + rclass);
        }

        return (rc);
    }

    /**<jp>
     * <code>CarryInformation</code>から再入庫フラグを取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 再入庫フラグ
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes re-storing flag from <code>CarryInformation</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return Re-storing flag
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getReturnFlag(int i)
            throws UnsupportedEncodingException
    {
        if (CarryInfo.RESTORING_FLAG_SAME_LOC.equals(getCarryInfo(i).getRestoringFlag()))
        {
            return ("1".getBytes(CommunicationAgc.CODE));
        }
        else
        {
            return ("0".getBytes(CommunicationAgc.CODE));
        }
    }

    /**<jp>
     * <code>CarryInformation</code>から出庫指示詳細を取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 出庫指示詳細 
     * @throws InvalidProtocolException 不正な出庫指示詳細が指定された場合に報告されます。
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes retrieval instruction in detail from <code>CarryInformation</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return retrieval instruction in detail
     * @throws InvalidProtocolException : Reports if entered retrieval instruction in detail is invalid.
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getRetrievalDetail(int i)
            throws InvalidProtocolException,
                UnsupportedEncodingException
    {
        byte[] rd;
        //<jp> 出庫指示詳細(これはCarryInformationにある情報)</jp>
        //<en> retrieval instruction in detail (included in CarryInformation)</en>
        String rdetail = getCarryInfo(i).getRetrievalDetail();
        //<jp> 在庫確認</jp>
        //<en> Confirmation for inventory</en>
        if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(rdetail))
        {
            rd = RETRIEVAL_DETAIL_CONFIRM.getBytes(CommunicationAgc.CODE);
        }
        //<jp> ユニット出庫</jp>
        //<en> Unit retrieval</en>
        else if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(rdetail))
        {
            rd = RETRIEVAL_DETAIL_UNIT.getBytes(CommunicationAgc.CODE);
        }
        //<jp> ピッキング出庫</jp>
        //<en> Pick retrieval</en>
        else if (CarryInfo.RETRIEVAL_DETAIL_PICKING.equals(rdetail))
        {
            rd = RETRIEVAL_DETAIL_PICK.getBytes(CommunicationAgc.CODE);
        }
        //<jp> 積増入庫</jp>
        //<en> Adding storing</en>
        else if (CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(rdetail))
        {
            rd = RETRIEVAL_DETAIL_ADDIN.getBytes(CommunicationAgc.CODE);
        }
        else
        {
            throw new InvalidProtocolException("Invalid Retrieval Detail:" + rdetail);
        }

        return (rd);
    }

    /**<jp>
     * <code>CarryInformation</code>からグループ番号を取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return グループ番号 
     * @throws InvalidProtocolException グループ番号が指定の値を超えた場合に報告されます。
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes group number from <code>CarryInformation</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return Group number 
     * @throws InvalidProtocolException : Reports if group number exceeds the allowable value.
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getGroupNo(int i)
            throws InvalidProtocolException,
                UnsupportedEncodingException
    {
        //<jp> Group No.(これはCarryInformationにある情報)</jp>
        //<en> Group number (included in CarryInformation)</en>
        int groupNo = getCarryInfo(i).getGroupNo();
        if (groupNo > 999)
        {
            throw new InvalidProtocolException("group number too large");
        }
        DecimalFormat fmt = new DecimalFormat("000");
        byte[] c_groupNo = fmt.format(groupNo).getBytes(CommunicationAgc.CODE);

        return (c_groupNo);
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送元ステーション番号を取り出します。
     * パレットの現在地ステーション(棚)の親ステーション(倉庫)を返します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 搬送元ステーション番号
     * @throws InvalidProtocolException  搬送元ステーション番号が指定の長さでなかった場合に報告されます。
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes originating sending station number from <code>CarryInformation</code>.
     * Returns parent station (warehouse) of current station (location) the pallet belongs to.
     * @param  i :index of <code>CarryInformation</code>
     * @return Sending station number
     * @throws InvalidProtocolException : Reports if originating sending station number is not the allowable length.
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getFromStationNo(int i)
            throws InvalidProtocolException,
                UnsupportedEncodingException
    {
        //<jp> 出庫、棚間移動は搬送元Stationは9000固定</jp>
        //<en> Fix 9000 as sending station for retrieval and location to location moves.</en>
        // UPDATE 2009/07/28 K.Mori
        // String pst = "9000";
        String pst = getPallet().getWhStationNo();
        // UPDATE 2009/07/28 
        byte[] ssn = pst.getBytes(CommunicationAgc.CODE);
        if (ssn.length != LEN_STATION_NO)
        {
            throw new InvalidProtocolException("Invalid From Station Number:" + pst);
        }

        return (ssn);
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送先ステーション番号を取り出します。
     * 行先が棚の場合は、棚間移動と見なし、親ステーション(倉庫)を返します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 搬送先ステーション番号
     * @throws InvalidProtocolException  ステーションが取得できないか搬送先ステーション番号が指定の長さでなかった場合に報告されます。
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes receiving station number from <code>CarryInformation</code>.
     * If the load is destined to rack, it regards as location to location move and returns parent station (warehouse).
     * @param  i :index of <code>CarryInformation</code>
     * @return Receiving station number
     * @throws InvalidProtocolException : Reports if station can not be acquired or if receiving station number is not the allowable length.
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getDestStationNo(int i)
            throws InvalidProtocolException,
                UnsupportedEncodingException
    {
        Station dSt = null;
        String dStNum = null;
        byte[] ds;
        String dStNumber = getCarryInfo(i).getDestStationNo();

        if (dStNumber.length() != LEN_STATION_NO)
        {
            try
            {
                dSt = StationFactory.makeStation(getConnection(), dStNumber);
            }
            catch (Exception e)
            {
                throw new InvalidProtocolException("makeStation error StationNumber = " + dStNumber);
            }

            //<jp> 行き先が棚の場合は、棚間移動出庫なので 搬送先ステーションは、倉庫になる</jp>
            //<en> If the load is destined to rack, it should be a location to location move; therefore</en>
            //<en> the receiving station should be the warehouse.</en>
            //2004/09/02 【ダブルディープ対応】
            //ParentStationNumberをセットすると、搬送元STやID04で9000固定でセットしている処理と矛盾する。
            //ここは9000固定合わせる。
            if (dSt instanceof Shelf)
            {
                // UPDATE 2009/07/28 K.Mori
                // dStNum = "9000";
                dStNum = dSt.getWhStationNo();
                // UPDATE 2009/07/28
            }
            else
            {
                dStNum = dSt.getStationNo();
            }
        }
        else
        {
            dStNum = dStNumber;
        }

        ds = dStNum.getBytes(CommunicationAgc.CODE);
        if (ds.length != LEN_STATION_NO)
        {
            throw new InvalidProtocolException("Invalid Destination Station:" + dStNum);
        }

        return (ds);
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送元棚番号を取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 搬送元棚番号
     * @throws InvalidProtocolException  パレットが取得できないか搬送元棚番号が指定の長さでなかった場合に報告されます。
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes origination location number from <code>CarryInformation</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return origination location number
     * @throws InvalidProtocolException : Reports if pallet can not be acquired or if origination location number is not the allowable length.
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getFromLocationNo(int i)
            throws InvalidProtocolException,
                UnsupportedEncodingException
    {
        String dStNum = null;
        String stationNo = getPallet().getCurrentStationNo();

        if (stationNo.length() != LEN_LOCATION_NO)
        {
            throw new InvalidProtocolException("Invalid From Location(Station) Number:" + stationNo);
        }
        else
        {
            dStNum = stationNo;
        }

        byte[] ssn = dStNum.getBytes(CommunicationAgc.CODE);
        if (ssn.length != LEN_LOCATION_NO)
        {
            throw new InvalidProtocolException("Invalid From Location Number:" + dStNum);
        }

        return (ssn);
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送先棚番号を取り出します。
     * 棚間移動の場合にのみ実際の棚番号が入ります。それ以外の場合は、000000000000 となります。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 搬送先棚番号
     * @throws InvalidProtocolException  搬送先棚番号が指定の長さでなかった場合に報告されます。
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes destined location number from <code>CarryInformation</code>.
     * Only if it is a location to location move, the actual location number should be input. Otherwise, fill out with 
     * 000000000000.
     * @param  i :index of <code>CarryInformation</code>
     * @return destined location number
     * @throws InvalidProtocolException : Reports if destined location number is not the allowable length.
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getDestLocationNo(int i)
            throws InvalidProtocolException,
                UnsupportedEncodingException
    {
        String dStNum;
        byte[] ssn;
        String dStStno = getCarryInfo(i).getDestStationNo();

        //<jp> 棚間移動出庫</jp>
        //<en> Location to location move</en>
        if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(getCarryInfo(i).getCarryFlag()))
        {
            dStNum = dStStno;
        }
        else
        {
            dStNum = "000000000000";
        }
        ssn = dStNum.getBytes(CommunicationAgc.CODE);
        if (ssn.length != LEN_LOCATION_NO)
        {
            throw new InvalidProtocolException("Invalid Destination Location Number:" + dStNum);
        }

        return (ssn);
    }

    /**<jp>
     * <code>Pallet</code>から荷姿情報を取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 荷姿情報
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes load size information from <code>Pallet</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return load size information
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getDimensionInfo(int i)
            throws UnsupportedEncodingException
    {
        //<jp> 荷姿情報  (これはPalletにある情報)</jp>
        //<en> Load size (included in Pallet)</en>
        DecimalFormat fmt1 = new DecimalFormat("00");
        String sdim = fmt1.format(getPallet().getHeight());

        byte[] bdim = sdim.getBytes(CommunicationAgc.CODE);

        return (bdim);
    }

    /**<jp>
     * <code>CarryInformation</code>から作業番号を取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 作業番号
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     </jp>*/
    /**<en>
     * Takes work number from <code>CarryInformation</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return work number
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     </en>*/
    private byte[] getWorkNo(int i)
            throws UnsupportedEncodingException
    {
        //<jp> 作業番号(これはCarryInformationにある情報)</jp>
        //<en> work number (included in CarryInformation)</en>
        String tmpWn = getCarryInfo(i).getWorkNo();
        if (StringUtil.isBlank(tmpWn))
        {
            tmpWn = "";
        }
        byte[] wnum = operateMessage(tmpWn, LEN_WORK_NO).getBytes(CommunicationAgc.CODE);

        return (wnum);
    }

    /**<jp>
     * <code>Pallet</code>からバーコード情報を取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return バーコード情報
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     </jp>*/
    /**<en>
     * Takes bar code data from <code>Pallet</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return bar code data
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding.
     * @throws ReadWriteException :Notifies if exception occured when processing for database.
     </en>*/
    private byte[] getBcData(int i)
            throws UnsupportedEncodingException,
                ReadWriteException
    {
        //<jp> バーコード情報  (これはPalletにある情報)</jp>
        //<en> bar code data (included in Pallet)</en>
        String tmpBcd = getPallet().getBcrData();
        if (StringUtil.isBlank(tmpBcd))
        {
            tmpBcd = "";
        }
        byte[] bcd = operateMessage(tmpBcd, LEN_BC_DATA).getBytes(CommunicationAgc.CODE);

        return (bcd);
    }

    /**<jp>
     * <code>CarryInformation</code>から制御情報を取り出します。
     * @param  i <code>CarryInformation</code>のインデックス
     * @return 制御情報
     * @throws UnsupportedEncodingException サポートしていない文字のエンコーディングに遭遇したときに通知されます。 
     </jp>*/
    /**<en>
     * Takes control information from <code>CarryInformation</code>.
     * @param  i :index of <code>CarryInformation</code>
     * @return control information
     * @throws UnsupportedEncodingException : Notifies if it encontered characters for which it does not support encoding. 
     </en>*/
    private byte[] getControlInfo(int i)
            throws UnsupportedEncodingException
    {
        //<jp> 制御情報(これはCarryInformationにある情報)</jp>
        //<en> control information (included in CarryInformation)</en>
        String cinfo = getCarryInfo(i).getControlinfo();
        if (StringUtil.isBlank(cinfo))
        {
            cinfo = "";
        }
        byte[] ci = operateMessage(cinfo, LEN_CONTROL_INFORMATION).getBytes(CommunicationAgc.CODE);

        return (ci);
    }

    // debug method --------------------------------------------------
    /**<jp>
     * 個々の出庫指示電文長を出力します。
     * @param args (未使用)
     </jp>*/
    /**<en>
     * Outputs the length of individual retrieval instruction message.
     * @param args : not used
     </en>*/
    public static void main(String[] args)
    {
        System.out.println(LEN_PIECE);
    }

}
//end of class
