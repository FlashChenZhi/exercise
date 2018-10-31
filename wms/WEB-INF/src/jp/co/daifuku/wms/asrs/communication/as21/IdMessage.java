// $Id: IdMessage.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**<jp>
 * AS21通信プロトコルでの電文共通部分を組み立て・分解するための、
 * ユーティリティ・メソッドを提供する、スーパークラスです。<BR>
 * 実際の電文組み立て・分解は、このクラスを継承して各IDごとのサブクラスを用意してください。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is the superclass provides the utility method which composes and/or decompose the common parts of 
 * communication messages according to AS21 communication protocol. 
 * Please use subclasses of each ID for the actual compositions and/or decompositions of messages.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class IdMessage
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 電文IDのオフセットの定義
     </jp>*/
    /**<en>
     * Definition of the offset of communcation message ID
     </en>*/
    static final int OFF_ID = 0;

    /**<jp>
     * 電文IDの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of the communication message (byte)
     </en>*/
    static final int LEN_ID = 2;

    /**<jp>
     * 電文ID区分のオフセットの定義
     </jp>*/
    /**<en>
     * Definition of offset of communication ID segments
     </en>*/
    static final int OFF_IDCLASS = OFF_ID + LEN_ID;

    /**<jp>
     * 電文ID区分の長さ(Byte)
     </jp>*/
    /**<en>
     * Length of communication message ID segment(byte)
     </en>*/
    static final int LEN_IDCLASS = 2;

    /**<jp>
     * MC送信時間のオフセットの定義
     </jp>*/
    /**<en>
     * Definition of offset of MC send time
     </en>*/
    static final int OFF_SENDDATE = OFF_IDCLASS + LEN_IDCLASS;

    /**<jp>
     * MC送信時間の長さ(Byte)
     </jp>*/
    /**<en>
     * Length of the MC send time (byte)
     </en>*/
    static final int LEN_SENDDATE = 6;

    /**<jp>
     * AGCSEND送信時間のオフセットの定義
     </jp>*/
    /**<en>
     * Definition of offset of AGCSEND send time
     </en>*/
    static final int OFF_AGCSENDDATE = OFF_SENDDATE + LEN_SENDDATE;

    /**<jp>
     * AGCSEND送信時間の長さ(Byte)
     </jp>*/
    /**<en>
     * Length of AGCSEND send time (byte)
     </en>*/
    static final int LEN_AGCSENDDATE = 6;

    /**<jp>
     * 内容情報のオフセットの定義
     </jp>*/
    /**<en>
     * Definition of offset of the content 
     </en>*/
    static final int OFF_CONTENT = OFF_AGCSENDDATE + LEN_AGCSENDDATE;

    /**<jp>
     * 最大データ長
     </jp>*/
    /**<en>
     * Maximum length of data
     </en>*/
    static final int LEN_MAX_CONTENT = 488;

    /**<jp>
     * 最大電文長 (STX,ETX,BCCをのぞく)
     </jp>*/
    /**<en>
     * Maximum length of the communication message (except for STX, ETX, and BCC)
     </en>*/
    static final int LEN_MAX_PACKET = 512 - 4;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message buffer
     </en>*/
    protected byte[] _dataBuffer = new byte[LEN_MAX_PACKET];

    /**<jp>
     * 日付フォーマット定義
     </jp>*/
    /**<en>
     * Definition of date format
     </en>*/
    private SimpleDateFormat _dateFormat = new SimpleDateFormat("HHmmss");

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * インスタンスを生成します。内部バッファは、 ' ' でクリアされます。
     </jp>*/
    /**<en>
     * Create instance. Internal buffer can be cleared by ' '.                                                                                                                                               
     </en>*/
    public IdMessage()
    {
        //<jp> バッファクリア</jp>
        //<en> cleanup buffer</en>
        for (int i = 0; i < _dataBuffer.length; i++)
        {
            _dataBuffer[i] = ' ';
        }
    }

    /**<jp>
     * AGCから受け取った電文を内部バッファにセットして、インスタンスを生成します。
     * @param  dt   セットする受信テキストバッファ
     </jp>*/
    /**<en>
     * Set the received message from AGC to internal buffer and generate the instance.
     * @param  dt   Received text buffer to set
     </en>*/
    public IdMessage(byte[] dt)
    {
        super();
        setReceiveMessage(dt);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * IDを取得します。
     * @return   電文ID
     </jp>*/
    /**<en>
     * Get the ID.
     * @return   communication message ID
     </en>*/
    public String getID()
    {
        return (getFromBuffer(OFF_ID, LEN_ID));
    }

    /**<jp>
     * ID区分を取得します。
     * @return 電文ID区分
     </jp>*/
    /**<en>
     * Get the ID segment
     * @return communication message ID segment
     </en>*/
    public String getIDClass()
    {
        return (getFromBuffer(OFF_IDCLASS, LEN_IDCLASS));
    }

    /**<jp>
     * 送信時刻を取得します。
     * @return 送信時刻
     </jp>*/
    /**<en>
     * Get the send time.
     * @return send time
     </en>*/
    public Date getSendDate()
    {
        String wdt = getFromBuffer(OFF_SENDDATE, LEN_SENDDATE);
        return (_dateFormat.parse(wdt, new ParsePosition(0)));
    }

    /**<jp>
     * AGC送信時刻を取得します。
     * @return AGC送信時刻
     </jp>*/
    /**<en>
     * Get AGC send time
     * @return AGC send time
     </en>*/
    public Date getAGCSendDate()
    {
        String wdt = getFromBuffer(OFF_AGCSENDDATE, LEN_AGCSENDDATE);
        return (_dateFormat.parse(wdt, new ParsePosition(0)));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * IDを設定します。
     * @param id   電文ID
     </jp>*/
    /**<en>
     * Set ID.
     * @param id   communicatin message ID
     </en>*/
    protected void setID(String id)
    {
        setToBuffer(id, OFF_ID);
    }

    /**<jp>
     * ID区分を設定します。
     * @param idclass   電文ID区分
     </jp>*/
    /**<en>
     * Set the ID segmet.
     * @param idclass   ID segment of the communication message
     </en>*/
    protected void setIDClass(String idclass)
    {
        setToBuffer(idclass, OFF_IDCLASS);
    }

    /**<jp>
     * 送信時間を設定します。時刻は現在時刻が使われます。
     </jp>*/
    /**<en>
     * Set the send time. Current time is used.
     </en>*/
    protected void setSendDate()
    {
        setSendDate(new Date());
    }

    /**<jp>
     * 送信時間を設定します。
     * @param  sdate 設定する時刻
     </jp>*/
    /**<en>
     * Set send time.
     * @param  sdate Time to set
     </en>*/
    protected void setSendDate(Date sdate)
    {
        String wdt = _dateFormat.format(sdate);
        setToBuffer(wdt, OFF_SENDDATE);
    }

    /**<jp>
     * AGC送信時間を設定します。時刻は現在時刻が使われます。
     * 送信テキストではこのパートは意味を持ちません。
     </jp>*/
    /**<en>
     * Set the AGC send time. Current time is used.
     * This part will have no meaning in send text.
     </en>*/
    protected void setAGCSendDate()
    {
        setAGCSendDate(new Date());
    }

    /**<jp>
     * AGC送信時間を設定します。
     * 送信テキストではこのパートは意味を持ちません。
     * @param  adate 設定する時刻
     </jp>*/
    /**<en>
     * Set the AGC send time. 
     * his part will have no meaning in send text.
     * @param  adate time to set
     </en>*/
    protected void setAGCSendDate(Date adate)
    {
        String wdt = _dateFormat.format(adate);
        setToBuffer(wdt, OFF_AGCSENDDATE);
    }

    /**<jp>
     * AGC送信時間を設定します。
     * 送信テキストではこのパートは意味を持ちません。
     * @param  st 設定する時刻
     </jp>*/
    /**<en>
     * Set the AGC send time. 
     * his part will have no meaning in send text.
     * @param  st time to set
     </en>*/
    protected void setAGCSendDate(String st)
    {
        setToBuffer(st, OFF_AGCSENDDATE);
    }

    /**<jp>
     * 通信電文の内容部分を設定します。
     * 中身に関してのチェックなどは行っていません。
     * @param content  設定する電文内容。
     </jp>*/
    /**<en>
     * Set the cotent of the communication message.
     * Check of the contents is not done.
     * @param content  Communication message to set
     </en>*/
    protected void setContent(String content)
    {
        setToBuffer(content, OFF_CONTENT);
    }

    /**<jp>
     * 通信電文の内容部分を取得します。
     * 中身に関してのチェックなどは行っていません。
     * @return 受信した電文内容。
     </jp>*/
    /**<en>
     * Get the content of communication message
     * Check of the content is not done.
     * @return content of received message
     </en>*/
    protected String getContent()
    {
        int wlen = _dataBuffer.length - OFF_CONTENT;
        return (getFromBuffer(OFF_CONTENT, wlen));
    }

    /**<jp>
     * 内部バッファに情報を設定します。
     * @param  src   設定する情報
     * @param  offset   セットするバッファのオフセット
     </jp>*/
    /**<en>
     * Set data to the internal buffer
     * @param  src   data to set
     * @param  offset   offset the buffer to set
     </en>*/
    protected void setToBuffer(String src, int offset)
    {
        byte[] wkb = src.getBytes();
        for (int i = 0; i < wkb.length; i++)
        {
            _dataBuffer[i + offset] = wkb[i];
        }
    }

    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param  rmsg   電文
     </jp>*/
    /**<en>
     * Set the communication message received from AGC to the internal buffer
     * @param  rmsg   communication message
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        _dataBuffer = rmsg;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * 内部バッファから情報を取得します。
     * @param  offset  バッファから取得する情報のオフセット
     * @param  len     バッファから取得する情報の長さ(bytes)
     * @return         取得した内部バッファ情報
     </jp>*/
    /**<en>
     * Get data from internal buffer
     * @param  offset  Offset of data getting from buffer
     * @param  len     Length of data getting from buffer (byte)
     * @return         data from internal buffer
     </en>*/
    protected String getFromBuffer(int offset, int len)
    {
        return (new String(_dataBuffer, offset, len));
    }

}
//end of class
