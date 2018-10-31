// $Id: As21Id64.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「すくい完了報告(PickUpCompletionReport) ID=64」電文を処理します。
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
 * Processes the communication message "PickUpCompletionReport" ID=64 according to AS21 communication protocol.
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
public class As21Id64
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     *ID64の電文長(STX,SEQ-No,BCC,ETXを除く)
     </jp>*/
    /**<en>
     *Length of the communication message ID64 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    public static final int LEN_ID64 = 16 + 22;

    /**<jp>
     *ID64のStation No.のオフセット定義
     </jp>*/
    /**<en>
     *Defines the offset of station No. of ID64
     </en>*/
    private static final int OFF_ID64_STATION = 0;

    /**<jp>
     *ID64のStation No.の長さ(byte)
     </jp>*/
    /**<en>
     *Length of Station No. of ID64 (byte)
     </en>*/
    private static final int LEN_ID64_STATION = 4;

    /**<jp>
     *ID64の報告搬送Data件数のオフセット定義
     </jp>*/
    /**<en>
     *Defines the offset of number of carrying information data reported by ID64.
     </en>*/
    private static final int OFF_ID64_JDATAK = OFF_ID64_STATION + LEN_ID64_STATION;

    /**<jp>
     *ID64の報告搬送Data件数の長さ(byte)
     </jp>*/
    /**<en>
     *Length of the number of carrying information data reported by ID64 (byte)
     </en>*/
    private static final int LEN_ID64_JDATAK = 2;

    /**<jp>
     *ID64の報告搬送Data情報１回目のオフセット定義
     </jp>*/
    /**<en>
     *Defines the offset of 1st information from carrying data reported by ID64
     </en>*/
    private static final int OFF_ID64_1ST = OFF_ID64_JDATAK + LEN_ID64_JDATAK;

    /**<jp>
     *ID64の報告搬送Data情報２回目のオフセット定義
     </jp>*/
    /**<en>
     *IDefines the offset of 2nd information from carrying data reported by ID64
     </en>*/
    private static final int OFF_ID64_2ND = 8;

    /**<jp>
     *ID64のMC Keyのオフセット定義
     </jp>*/
    /**<en>
     *Defines the offset of MC Key of ID64
     </en>*/
    private static final int OFF_ID64_MCKEY = OFF_ID64_JDATAK + LEN_ID64_JDATAK;

    /**<jp>
     *ID64のMC Keyの長さ(byte)
     </jp>*/
    /**<en>
     *Length of MC Key of ID64 (byte)
     </en>*/
    private static final int LEN_ID64_MCKEY = 8;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variable for the preservation of communicaiton messages
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID64];

    /**<jp>
     * 報告搬送Data件数を保持するための変数。
     </jp>*/
    /**<en>
     * Variable for the preservation of the number of carrying data reported
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
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id64 <code>as21Id64</code>  すくい完了報告電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC; then initializes this class.
     * @param as21Id64 :<code>as21Id64</code>  Communication message of PickUpCompletionReport
     </en>*/
    public As21Id64(byte[] as21Id64)
    {
        super();
        setReceiveMessage(as21Id64);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * すくい完了報告電文から搬送元Station No.を取得します。
     * @return    搬送元Station No.
     </jp>*/
    /**<en>
     * Acquires the sending station No. from the communication message PickUpCompletionReport.
     * @return    sending station No.
     </en>*/
    public String getStationNo()
    {
        String stationNo = getContent().substring(OFF_ID64_STATION, OFF_ID64_STATION + LEN_ID64_STATION);
        return (stationNo);
    }

    /**<jp>
     * すくい完了報告電文から報告搬送Data件数を取得します。
     * すくい完了報告電文から報告搬送Dataは最大２件返されます。
     * @return    報告搬送Data件数
     * @throws InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知します。
     </jp>*/
    /**<en>
     * Acquires the number of carrying data reported from the communication message PickUpCompletionReport.
     * PickUpCompletionReport returns up to 2 carrying data reported.
     * @return    the number of carrying data reported
     * @throws InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
     </en>*/
    public int getJdatak()
            throws InvalidProtocolException
    {
        int rclass;

        String jdatak = getContent().substring(OFF_ID64_JDATAK, OFF_ID64_JDATAK + LEN_ID64_JDATAK);
        try
        {
            rclass = Integer.parseInt(jdatak);
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Invalid error"));
        }

        if (rclass != _countOfData)
        {
            throw (new InvalidProtocolException("resarch error"));
        }

        return (rclass);
    }

    /**<jp>
     * すくい完了報告電文から報告搬送Data情報（MC Key）を取得します。
     * @return    報告搬送Data情報（MC Key）
     * @throws InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知します。
     </jp>*/
    /**<en>
     * Acquires carrying data reported (MC Key) from PickUpCompletionReport.
     * @return    carrying data reported (MC Key)
     * @throws InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
     </en>*/
    public String[] getVmcKey()
            throws InvalidProtocolException
    {
        return (getCompInfo(OFF_ID64_MCKEY, LEN_ID64_MCKEY));
    }

    /**<jp>
     * すくい完了報告電文から報告搬送Data情報（MC Key）を取得します。
     * @param offset すくい完了報告電文のオフセット定義数
     * @param len 定義されているMC Keyの長さ(byte)
     * @return 報告搬送Data情報（MC Key）
     * @throws InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知します。
     </jp>*/
    /**<en>
     * Acquires carrying data reported (MC Key) from PickUpCompletionReport.
     * @param offset :Defined offset number for communication message:PickUpCompletionReport
     * @param len Length of MC Key in definition (byte)
     * @return carrying data reported (MC Key)
     * @throws InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
     </en>*/
    protected String[] getCompInfo(int offset, int len)
            throws InvalidProtocolException
    {
        String[] rst = new String[_countOfData];
        try
        {
            for (int i = 0; i < _countOfData; i++)
            {
                int toff = offset + (OFF_ID64_2ND * i);
                rst[i] = getContent().substring(toff, toff + len);
            }

        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Encode error"));
        }
        return (rst);
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * また、報告搬送Data件数を取得します。MC Keyが無効な場合は0をセットします。
     * @param rmsg すくい完了報告電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * Also, acquires the number of carrying data reported. Sets 0 if MC Key is invalid.
     * @param rmsg communication message:PickUpCompletionReport
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        int offset;
        String mckey;

        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; (i < rmsg.length) && (i < LEN_ID64); i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;

        //<jp> 報告搬送Dataをカウント</jp>
        //<en> counting carrying data reported</en>
        offset = OFF_ID64_MCKEY;
        mckey = getContent().substring(offset, offset + LEN_ID64_MCKEY);
        if (mckey.equals(NULL_MC_KEY))
        {
            _countOfData = 0;
        }
        else
        {
            _countOfData = 1;
            //<jp> 受信データ長とID64データ部＋BCC部の合計が同じかどうか検証</jp>
            //<en>Examine to see if the received data is the same length as the total length of ID64 data portion and BCC.</en>
            if (rmsg.length == (LEN_ID64 + Bcc.BCC_LENGTH))
            {
                offset = OFF_ID64_2ND + OFF_ID64_MCKEY;
                mckey = getContent().substring(offset, offset + LEN_ID64_MCKEY);
                if (!mckey.equals(NULL_MC_KEY))
                {
                    _countOfData++;
                }
            }
        }
    }
    // Private methods -----------------------------------------------

}
// end of class
