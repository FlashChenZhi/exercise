// $Id: As21Id45.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.wms.base.entity.CarryInfo;

/**<jp>
 * AS21通信プロトコルでの「MC作業完了報告(McWorkCompletionReport) ID=45」電文を組み立てます。
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
 * Composes communication message "McWorkCompletionReport" ID=45 according to AS21 communication protocol.
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
public class As21Id45
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 払い出し区分を表すフィールド (払い出し)
     </jp>*/
    /**<en>
     * Field of classification for transfer (transfer)
     </en>*/
    public static final String PAYOUT = "0";

    /**<jp>
     * 払い出し区分を表すフィールド (戻り入庫)
     </jp>*/
    /**<en>
     * Field of classification for transfer (returns storage)
     </en>*/
    public static final String RETURN_STORAGE = "1";

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
     * Length of station No.
     </en>*/
    protected static final int LEN_STATION = 4;

    /**<jp>
     * 払い出し区分の長さ
     </jp>*/
    /**<en>
     * Length of classification for transfer
     </en>*/
    protected static final int LEN_PAYOUT_CLASS = 1;

    /**<jp>
     * MC作業完了報告電文の長さ
     </jp>*/
    /**<en>
     * Length of communication message "McWorkCompletionReport"
     </en>*/
    protected static final int LEN_TOTAL = OFF_CONTENT + LEN_CARRYKEY + LEN_STATION + LEN_PAYOUT_CLASS;

    // Class variables -----------------------------------------------
    /**<jp>
     * 搬送データを持つ、<code>CarryInformation</code>インスタンスを保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves the instance of <code>CarryInformation</code> containing
     * carrying data
     </en>*/
    private CarryInfo _carryInfo;

    /**<jp>
     * 払い出し区分（true:払い出し false:戻り入庫）
     </jp>*/
    /**<en>
     * Field of classification for transfer (true: transfer, false: return storage )
     </en>*/
    private boolean _booleanType;

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
    public As21Id45()
    {
    }

    /**<jp>
     * 搬送データを持った<code>CarryInformation</code>インスタンスを指定して、この
     * クラスのインスタンスを生成します。
     * @param  ci  搬送データを持つ<code>CarryInformation</code>インスタンス
     </jp>*/
    /**<en>
     * Generates the instance of this class by specifying the instance of <code>CarryInformation</code>
     * containing the carrying data.
     * @param  ci  instance of <code>CarryInformation</code> containing carrying data
     </en>*/
    public As21Id45(CarryInfo ci)
    {
        super();
        setCarryInforma(ci);
        setBoo(ci);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * MC作業完了報告電文を作成します。
     * @return    MC作業完了報告電文
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Creates the communication message "McWorkCompletionReport".
     * @return    communication message "McWorkCompletionReport"
     * @throws  InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        //<jp> MC Key(CarryInformationにある情報)</jp>
        //<en> MC Key(included in CarryInformation)</en>
        mbuf.append(getMCKey(_carryInfo));
        //<jp> Station No.(CarryInformationにある情報)</jp>
        //<en> Station No.(included in CarryInformation)</en>
        mbuf.append(getStationNo(_carryInfo));
        //<jp> 払い出し区分(CarryInformationにある情報)</jp>
        //<en> classification for transfer (included in CarryInformation)</en>
        mbuf.append(getPayoutclass(_booleanType));

        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Setting for sending message buffer</en>
        //-------------------------------------------------
        // ID
        setID("45");
        //<jp> ID 区分</jp>
        //<en> ID segment</en>
        setIDClass("00");
        //<jp> MC送信時刻</jp>
        //<en> MC sending time</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> AGC sending time</en>
        setAGCSendDate("000000");
        //<jp> テキスト内容</jp>
        //<en> text contents</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL));
    }

    /**<jp>
     * <code>CarryInformation</code>からMC Keyを取得します。
     * @param carryInformation 入力された搬送データ
     * @return MC Key
     * @throws InvalidProtocolException  MC Keyが指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires MC Key from <code>CarryInformation</code>.
     * @param carryInformation :carrying information which has been input 
     * @return MC Key
     * @throws InvalidProtocolException : Reports if MC Key is not the allowable length.
     </en>*/
    private String getMCKey(CarryInfo carryInformation)
            throws InvalidProtocolException
    {
        StringBuffer stbuff = new StringBuffer(LEN_CARRYKEY);
        //<jp> MC Key(CarryInformationにある情報)</jp>
        //<en> MC Key(included in CarryInformation)</en>
        String carryKey = carryInformation.getCarryKey();
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
     * @param carryInformation 入力された搬送データ
     * @return 搬送元ステーション番号
     * @throws InvalidProtocolException  搬送元ステーション番号が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Takes the sending station No. from <code>CarryInformation</code>.
     * @param carryInformation carrying information which has been input
     * @return sending station No.
     * @throws InvalidProtocolException : Reports if sending station No. is not the allowable length.
     </en>*/
    private String getStationNo(CarryInfo carryInformation)
            throws InvalidProtocolException
    {
        //<jp> 搬送元Station No.(CarryInformationにある情報)</jp>
        //<en> sending station No.(included in CarryInformation)</en>
        String sendingStationNo = "";
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(carryInformation.getCarryFlag()))
        {
            sendingStationNo = carryInformation.getSourceStationNo();
        }
        else
        {
            sendingStationNo = carryInformation.getDestStationNo();
        }
        if (sendingStationNo.length() != LEN_STATION)
        {
            throw new InvalidProtocolException("sendingStationNo = " + LEN_STATION + "--->" + sendingStationNo);
        }
        return (sendingStationNo);
    }

    /**<jp>
     * パラメータによって払い出し区分の決定処理を行う。（true:払い出し false:戻り入庫）
     * @param transferClassification trueまたはfalse
     * @return 払い出し区分
     </jp>*/
    /**<en>
     * Conducts determination process for the transfer classification by parameter.(true: transfer, false: return retrieval)
     * @param transferClassification true or false
     * @return transfer classification
     </en>*/
    public String getPayoutclass(boolean transferClassification)
    {
        String payoutclass = "";

        if (transferClassification == true)
        {
            payoutclass = PAYOUT;
        }
        else
        {
            payoutclass = RETURN_STORAGE;
        }

        return (payoutclass);

    }

    /**<jp>
     * CarryInformationインスタンスをセットする。
     * @param ci 入力された搬送データ
     </jp>*/
    /**<en>
     * Sets instance of CarryInformation
     * @param ci Carrying data which has been input
     </en>*/
    private void setCarryInforma(CarryInfo ci)
    {
        _carryInfo = ci;
    }

    /**<jp>
     * CarryInformationから搬送区分、出庫指示詳細を得て、払い出し区分を決定する。
     * @param ci 入力された搬送データ
     </jp>*/
    /**<en>
     * Acquires the carry key and the detailed instruction of retrieval, then determines transfer classification.
     * @param ci Carrying data which has been input
     </en>*/
    private void setBoo(CarryInfo ci)
    {
        //<jp> 搬送区分：直行</jp>
        //<en> Field to set transport (direct travel)</en>
        if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
        {
            //<jp> 払い出し</jp>
            //<en> transfer</en>
            _booleanType = true;
        }
        else
        {
            //<jp> 出庫指示詳細：ユニット出庫</jp>
            //<en> Field to set detail of retrieval instruction (retrieval in unit)</en>
            if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(ci.getRetrievalDetail()))
            {
                //<jp> 払い出し</jp>
                //<en> transfer</en>
                _booleanType = true;
            }
            else
            {
                //<jp> 戻り入庫</jp>
                //<en> returns storage</en>
                _booleanType = false;
            }
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

