// $Id: SendIdMessage.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;

/**
 * RFT通信での電文共通部分を組み立て・分解するための、
 * ユーティリティ・メソッドを提供する、スーパークラスです。<BR>
 * 実際の電文組み立て・分解は、各IDごとのサブクラスを利用してください。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class SendIdMessage
        extends IdMessage
        implements AnswerCode
{
    // Class fields --------------------------------------------------

    /**
     * 電文の長さ
     */
    protected int _length;

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return "$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $";
    }

    // Constructors --------------------------------------------------
    /**
     * 親クラスのコンストラクタを呼び出します。
     */
    public SendIdMessage()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 電文内容保持バッファを空白で初期化します。
     */
    public void initializeBuffer()
    {
        Arrays.fill(_wDataBuffer, (byte)' ');
    }

    /**
     * 出来上がった電文をバッファにセットします。
     * @param   smsg    送信電文用のバイト配列
     */
    public void getSendMessage(byte[] smsg)
    {
        System.arraycopy(_wDataBuffer, 0, smsg, 0, _length);
    }

    /**
     * 出来上がった電文を文字列にして返します。
     * STXからETXまでの内容を文字列にしますので、STX ETXをセットしておく必要があります。
     * 作成した文字列に、STX ETXは含みません
     * 
     * @return 作成した文字列
     */
    public String getSendMessageString()
    {
        try
        {
            String wbuf = new String(_wDataBuffer, 1, _length - 2, IdMessage.ENCODE);
            return wbuf;
        }
        catch (UnsupportedEncodingException e)
        {
            // not occurs
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 再送用の保持データを内部バッファにセットします。
     * @param  rmsg   セットする電文情報
     * @throws ScheduleException 引数に指定されたデータが空白の場合。
     */
    public void setSendMessageString(String rmsg)
            throws ScheduleException
    {
        if (StringUtil.isBlank(rmsg))
        {
            throw new ScheduleException();
        }
        try
        {
            byte[] byteData = rmsg.getBytes(IdMessage.ENCODE);
            int byteLength = byteData.length;

            setSTX();
            for (int i = 0; i < byteLength; i++)
            {
                _wDataBuffer[LEN_STX + i] = byteData[i];
            }
            _wDataBuffer[LEN_STX + byteLength] = DEF_ETX;

            // データのサイズをセットする
            _length = LEN_STX + byteLength + LEN_ETX;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            throw new ScheduleException();
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * STXを設定します。
     */
    public void setSTX()
    {
        setToByteBuffer(DEF_STX, OFF_STX);
    }

    /**
     * SEQを設定します。
     * @param seq 設定するSEQ
     */
    public void setSEQ(int seq)
    {
        setToBufferRight(seq, OFF_SEQ, LEN_SEQ);
    }

    /**
     * IDを設定します。
     * @param id 設定する電文ID
     */
    public void setID(String id)
    {
        setToBufferLeft(id, OFF_ID, LEN_ID);
    }

    /**
     * 送信時間を設定します。
     * 送信テキストではこのパートは意味を持ちません。
     * @param  sdate 設定する時刻
     */
    public void setRftSendDate(Date sdate)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ID_DATE_FORMAT);
        String wdt = dateFormat.format(sdate);
        setToBufferLeft(wdt, OFF_RFTSENDDATE, LEN_RFTSENDDATE);
    }

    /**
     * SERV送信時間を設定します。
     * @param  adate 設定する時刻
     */
    public void setServSendDate(Date adate)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ID_DATE_FORMAT);
        String wdt = dateFormat.format(adate);
        setToBufferLeft(wdt, OFF_SERVSENDDATE, LEN_SERVSENDDATE);
    }

    /**
     * RFT号機を設定します。
     * @param rft 設定するRFT号機
     */
    public void setRftNo(String rft)
    {
        setToBufferLeft(rft, OFF_RFTNO, LEN_RFTNO);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * 内部バッファに左詰めで情報を設定します。
     * @param  src   設定する情報
     * @param  offset   セットするバッファのオフセット
     * @param  length   セットするバッファのサイズ
     */
    protected void setToBufferLeft(String src, int offset, int length)
    {
        byte[] wkb = Idutils.createByteDataLeft(src, length);
        System.arraycopy(wkb, 0, _wDataBuffer, offset, length);
    }

    /**
     * 内部バッファに右詰めで数値情報を設定します。
     * @param  src   設定する数値情報(int)
     * @param  offset   セットするバッファのオフセット
     * @param  length   セットする入力データの長さ
     */
    protected void setToBufferRight(int src, int offset, int length)
    {
        byte[] wkb = Idutils.createByteDataRight(src, length);
        System.arraycopy(wkb, 0, _wDataBuffer, offset, length);
    }

    /**
     * 内部バッファに右詰めで文字列情報を設定します。
     * @param  src   設定する文字列情報
     * @param  offset   セットするバッファのオフセット
     * @param  length   セットする入力データの長さ
     */
    protected void setToBufferRight(String src, int offset, int length)
    {
        byte[] wkb = Idutils.createByteDataRight(src, length);
        System.arraycopy(wkb, 0, _wDataBuffer, offset, length);
    }

    /**
     * 内部バッファに情報を設定します。
     * @param  src   設定する情報
     * @param  offset   セットするバッファのオフセット
     */
    protected void setToByteBuffer(byte src, int offset)
    {
        _wDataBuffer[offset] = src;
    }

    // Private methods -----------------------------------------------
}
//end of class
