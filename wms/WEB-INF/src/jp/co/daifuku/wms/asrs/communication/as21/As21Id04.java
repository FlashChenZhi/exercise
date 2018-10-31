// $Id: As21Id04.java 4843 2009-08-20 07:45:35Z ota $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;

/**<jp>
 * AS21通信プロトコルでの「搬送 DataCancel 要求(TransportDataCancelRequest) ID=04」電文を組み立てます。
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
 * Composes communication message "Request for transport data cancelation ID=04" according to AS21 communication protocol.
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
public class As21Id04
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * MC Keyの長さ
     </jp>*/
    /**<en>
     * Length of MC Key
     </en>*/
    protected static final int LEN_CARRYKEY = 8;

    /**<jp>
     * Station No.の長さ
     </jp>*/
    /**<en>
     * Length of Station number
     </en>*/
    protected static final int LEN_STATIONNO = 4;

    /**<jp>
     * Location No.の長さ
     </jp>*/
    /**<en>
     * Length of Location number
     </en>*/
    protected static final int LEN_LOCATIONNO = 12;

    /**<jp>
     * 電文長
     </jp>*/
    /**<en>
     * Length of the communication message
     </en>*/
    protected static final int LEN_TOTAL = OFF_CONTENT + LEN_CARRYKEY + (LEN_STATIONNO * 2) // From and To
            + LEN_LOCATIONNO;

    // Class variables -----------------------------------------------
    /**<jp>
     * 搬送情報を持つ、<code>CarryInformation</code>を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves <code>CarryInformation</code>, containing carry information.
     </en>*/
    private CarryInfo _carryInfo;

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
     * Returns the version of this class.
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
    public As21Id04()
    {
        super();
    }

    /**<jp>
     * キャンセルすべき搬送情報を持った<code>CarryInformation</code>インスタンスを指定して、この
     * クラスのインスタンスを生成します。
     * @param  ci     搬送情報を持つ<code>CarryInformation</code>
     * @param  pallet パレット情報を持つ<code>Pallet</code>
     </jp>*/
    /**<en>
     * By specifying the instance of <code>CarryInformation</code> which has the tranport information
     * to cancel, generates hte instance of this class.
     * @param  ci  <code>CarryInformation</code> preserving the transport information
     * @param  pallet  <code>Pallet</code> preserving the pallet information
     </en>*/
    public As21Id04(CarryInfo ci, Pallet pallet)
    {
        super();
        setCarryInfo(ci);
        setPallet(pallet);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 搬送 DataCancel 要求電文を作成します。
     * <p>搬送データキャンセルに必要な</p>
     * <ul>
     * <li>MC Key
     * <li>搬送元Station No.
     * <li>搬送先Station No.
     * <li>Location No.
     * </ul>
     * <p>は、コンストラクタで渡された<code>CarryInformation</code>のインスタンスから情報を入手します。
     * </p>
     * @return    搬送 DataCancel 要求電文
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     </jp>*/
    /**<en>
     * Creates the communication message requesting the cancelation of tramsport data.
     * <p></p> required for transport data to be canceled
     * <ul>
     * <li>MC Key
     * <li>Sending station number
     * <li>Receiving station number
     * <li>Location No.
     * </ul>
     * <p> acquires the information from the instance of <code>CarryInformation</code> given in constructor.
     * </p>
     * @return    communication message requesting the cancelation of Transport Data
     * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
     * @throws ReadWriteException :Notifies if exception occured when processing for database.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException,
                ReadWriteException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        try
        {
            //-------------------------------------------------
            //<jp> ここから先、順序が重要なので注意!</jp>
            //<en> Attention! Order of the contents must be observed!</en>
            //-------------------------------------------------
            // MC Key
            mbuf.append(getMCKey());
            //<jp> 搬送元Station No.(これはCarryInformationにある情報)</jp>
            //<en> Sending station number (included in CarryInformation)</en>
            mbuf.append(getFromStationNo());
            //<jp> 搬送先Station No.(これはCarryInformationにある情報)</jp>
            //<en> Receiving station number (included in CarryInformation)</en>
            mbuf.append(getDestStationNo());
            // Location No.
            mbuf.append(getDestLocationNo());

            //-------------------------------------------------
            //<jp> 送信メッセージバッファに設定</jp>
            //<en> Sets as sending message buffer.</en>
            //-------------------------------------------------
            // id
            setID("04");
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
            //<en> contents of text</en>
            setContent(String.valueOf(mbuf));

            return (getFromBuffer(0, LEN_TOTAL));
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
    /**<jp>
     * 搬送情報を持つ<code>CarryInformation</code>を取得します。
     * @return 現在保持している<code>CarryInformation</code>
     </jp>*/
    /**<en>
     * Retrieve <code>CarryInformation</code> to contain carry information.
     * @return :<code>CarryInformation</code> currently preserved
     </en>*/
    protected CarryInfo getCarryInfo()
    {
        return _carryInfo;
    }

    /**<jp>
     * 搬送情報を持つ<code>CarryInformation</code>を設定します。
     * @param carryInfo  現在保持している<code>CarryInformation</code>
     </jp>*/
    /**<en>
     * Set <code>CarryInformation</code> to contain carry information.
     * @param carryInfo :<code>CarryInformation</code> currently preserved
     </en>*/
    public void setCarryInfo(CarryInfo carryInfo)
    {
        _carryInfo = carryInfo;
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
     * @return     MC Key
     * @throws InvalidProtocolException MC Keyのデータ長が指定の長さを超えた場合に報告されます。
     </jp>*/
    /**<en>
     * Take out tje MC Key out of <code>CarryInformation</code>.
     * @return     MC Key
     * @throws InvalidProtocolException : Reports if MC Key exceeds the allowable length.
     </en>*/
    private String getMCKey()
            throws InvalidProtocolException
    {
        StringBuffer stbuff = new StringBuffer(LEN_CARRYKEY);
        //<jp> MC Key(これはCarryInformationにある情報)</jp>
        //<en> MC Key (included in CarryInformation)</en>
        String carryKey = getCarryInfo().getCarryKey();
        if (carryKey.length() > LEN_CARRYKEY)
        {
            throw new InvalidProtocolException("carryKey = " + LEN_CARRYKEY + "--->" + carryKey);
        }
        else
        {
            stbuff.replace(0, LEN_CARRYKEY, "00000000");
            stbuff.replace(LEN_CARRYKEY - carryKey.length(), LEN_CARRYKEY, carryKey);
        }
        return (String.valueOf(stbuff));
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送元ステーション番号を取り出します。
     * @return    搬送元ステーション番号
     * @throws InvalidProtocolException 不正な搬送区分が指定されたかステーションNo.が指定の長さを越えた場合に報告されます。
     </jp>*/
    /**<en>
     * Sending station number to be taken out of <code>CarryInformation</code>.
     * @return    Sending station number
     * @throws InvalidProtocolException : Reports if transport section is invalid or if station exceeds the allowable length.
     </en>*/
    private String getFromStationNo()
            throws InvalidProtocolException
    {
        String stnum = null;
        String carryFlag = getCarryInfo().getCarryFlag();
        //<jp> 搬送区分を元に搬送元ステーションをセット</jp>
        //<en> Sets sending station based on the transport section.</en>
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(carryFlag) || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carryFlag))
        {
            stnum = getCarryInfo().getSourceStationNo();
        }
        else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(carryFlag)
                || CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(carryFlag))
        {
            // UPDATE 2009/07/28 K.Mori
            // stnum = "9000";
            stnum = getPallet().getWhStationNo();
            // UPDATE 2009/07/28
        }
        else
        {
            throw new InvalidProtocolException("carryKind invalid = " + carryFlag);
        }

        if (stnum.length() != LEN_STATIONNO)
        {
            throw new InvalidProtocolException("FromStationNo = " + LEN_STATIONNO + "--->" + stnum);
        }
        return (stnum);
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送先ステーション番号を取り出します。
     * @return    搬送先ステーション番号
     * @throws InvalidProtocolException 不正な搬送区分が指定されたかステーションNo.が指定の長さを越えた場合に報告されます。
     </jp>*/
    /**<en>
     * Takes receiving station number out of <code>CarryInformation</code>.
     * @return    receicing station number
     * @throws InvalidProtocolException : Reports if transport section is invalid or if station exceeds the allowable length.
     </en>*/
    private String getDestStationNo()
            throws InvalidProtocolException
    {
        String stnum = null;
        //<jp> 搬送区分を元に搬送先ステーションをセット</jp>
        //<en> Sets receiving station according to the transport section.</en>
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(getCarryInfo().getCarryFlag())
                || CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(getCarryInfo().getCarryFlag()))
        {
            // UPDATE 2009/07/28 K.Mori
            // stnum = "9000";
            stnum = getPallet().getWhStationNo();
            // UPDATE 2009/07/28
        }
        else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(getCarryInfo().getCarryFlag())
                || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(getCarryInfo().getCarryFlag()))
        {
            stnum = getCarryInfo().getDestStationNo();
        }
        else
        {
            throw new InvalidProtocolException("carryKind invalid = " + getCarryInfo().getCarryFlag());
        }

        if (stnum.length() != LEN_STATIONNO)
        {
            throw new InvalidProtocolException("DestStationNo = " + LEN_STATIONNO + "--->" + stnum);
        }
        return (stnum);
    }

    /**<jp>
     * <code>CarryInformation</code>から搬送先棚番号を取り出します。
     * @return    搬送先棚番号
     * @throws InvalidProtocolException 不正な搬送区分が指定されたか棚番号が指定の長さを越えた場合に報告されます。
     </jp>*/
    /**<en>
     * Gain location number of receiving station from <code>CarryInformation</code>.
     * @return    location number of receiving station
     * @throws InvalidProtocolException : Reports if transport section is invalid or if location exceeds the allowable length.
     </en>*/
    private String getDestLocationNo()
            throws InvalidProtocolException
    {
        String locationNo = null;

        //<jp> 搬送区分を元に搬送先棚番号をセット</jp>
        //<en> Sets location number of receiving station according to the transport section.</en>
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(getCarryInfo().getCarryFlag()))
        {
            locationNo = getCarryInfo().getDestStationNo();
        }
        else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(getCarryInfo().getCarryFlag()))
        {
            locationNo = getPallet().getCurrentStationNo();
        }
        else if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(getCarryInfo().getCarryFlag()))
        {
            locationNo = getPallet().getCurrentStationNo();
        }
        else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(getCarryInfo().getCarryFlag()))
        {
            byte[] wk = new byte[LEN_LOCATIONNO];
            for (int i = 0; i < wk.length; i++)
            {
                wk[i] = '0';
            }
            locationNo = new String(wk);
        }
        else
        {
            throw new InvalidProtocolException("carryKind invalid = " + getCarryInfo().getCarryFlag());
        }

        if (locationNo.length() != LEN_LOCATIONNO)
        {
            throw new InvalidProtocolException("DestLocationNo = " + LEN_LOCATIONNO + "--->" + locationNo);
        }
        return (locationNo);
    }
}
//end of class
