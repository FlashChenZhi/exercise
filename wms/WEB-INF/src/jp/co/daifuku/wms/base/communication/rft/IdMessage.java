// $Id: IdMessage.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

import java.io.UnsupportedEncodingException;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

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
public abstract class IdMessage
        extends Object
        implements DataColumn
{
    /** 
     * STX
     */
    protected static final byte DEF_STX = 0x02;

    /** 
     * ETX
     */
    protected static final byte DEF_ETX = 0x03;

    /**
     *  STXのオフセットの定義
     */
    public static final int OFF_STX = 0;

    /** 
     * SEQのオフセットの定義
     */
    public static final int OFF_SEQ = LEN_STX;

    /** 
     * 電文IDのオフセットの定義
     */
    public static final int OFF_ID = OFF_SEQ + LEN_SEQ;

    /** 
     * RFT送信時間のオフセットの定義
     */
    public static final int OFF_RFTSENDDATE = OFF_ID + LEN_ID;

    /** 
     * SERVER送信時間のオフセットの定義
     */
    public static final int OFF_SERVSENDDATE = OFF_RFTSENDDATE + LEN_RFTSENDDATE;

    /** 
     * RFT号機のオフセットの定義
     */
    public static final int OFF_RFTNO = OFF_SERVSENDDATE + LEN_SERVSENDDATE;

    /** 
     * ヘッダー部全長 
     */
    public static final int LEN_HEADER = OFF_RFTNO + LEN_RFTNO;

    /** 
     * 最大電文長
     */
    public static final int LEN_MAX_PACKET = 1024;

    /** 
     * エンコード定義
     */
    public static final String ENCODE = "MS932";

    /** 
     * 時刻のフォーマット(電文ID内の"サーバ送信時間"項目) 
     */
    public static final String ID_DATE_FORMAT = "HHmmss";

    // Class variables -----------------------------------------------
    /**
     *  電文バッファ 
     */
    protected byte[] _wDataBuffer = new byte[LEN_MAX_PACKET];

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

    // Public methods ------------------------------------------------
    /**
     * IDを取得します。
     * @return   電文ID
     */
    public String getID()
    {
        return (getFromBuffer(OFF_ID, LEN_ID));
    }

    // Package methods -----------------------------------------------

    /**
     * 内部バッファから情報を取得します。<br>
     * 文字列は trim されて返されます。
     * @param  offset  バッファから取得する情報のオフセット
     * @param  len     バッファから取得する情報の長さ(bytes)
     * @return 取得データ(文字列)。
     * @since 2008-03-28 trim() を追加
     */
    protected String getFromBuffer(int offset, int len)
    {
        try
        {
            String strValue = new String(_wDataBuffer, offset, len, IdMessage.ENCODE);
            return strValue.trim();
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e); // usually not occurs
        }
    }

    /**
     * 内部バッファから情報を取得します。<br>
     * <b> 文字列は trim は行いません </b>
     * @param  offset  バッファから取得する情報のオフセット
     * @param  len     バッファから取得する情報の長さ(bytes)
     * @return 取得データ(文字列)。
     * @since 2008-03-28 trim() を追加
     */
    protected String getFromBufferNoTrim(int offset, int len)
    {
        try
        {
            return new String(_wDataBuffer, offset, len, IdMessage.ENCODE);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e); // usually not occurs
        }
    }

    /**
     * 内部バッファから整数値に変換した情報を取得します。
     * @param  offset  バッファから取得する情報のオフセット
     * @param  len     バッファから取得する情報の長さ(bytes)
     * @return 取得データ(数値)
     * @throws NumberFormatException 整数値に変換できない場合に通知されます。
     */
    protected int getIntFromBuffer(int offset, int len)
            throws NumberFormatException
    {
        return Integer.parseInt(getFromBuffer(offset, len));
    }

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
