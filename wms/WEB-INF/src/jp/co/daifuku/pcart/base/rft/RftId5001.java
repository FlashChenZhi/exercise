// $Id: RftId5001.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

import jp.co.daifuku.wms.base.communication.rft.SendIdMessage;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * システム報告応答 ID=5001 電文
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id5005の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>              <TH>長さ</TH>   <TH>内容</TH></TR>
 * <tr><td>STX</td>                 <td> 1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>             <td> 4 byte</td> <td></td></tr>
 * <tr><td>ID</td>                  <td> 4 byte</td> <td>5001</td></tr>
 * <tr><td>端末送信時間</td>        <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>サーバ送信時間</td>      <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>端末号機No.</td>         <td> 3 byte</td> <td></td></tr>
 * <tr><td>サーバ日時</td>          <td>14 byte</td> <td>YYYYMMDDHHmmSS</td></tr>
 * <tr><td>応答フラグ</td>          <td> 1 byte</td> <td>0:正常 9:エラー</td></tr>
 * <tr><td>エラー詳細</td>          <td> 2 byte</td> <td></td></tr>
 * <tr><td>ETX</td>                 <td> 1 byte</td> <td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/03/26</TD><TD>T.kojima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4181 $, $Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $
 * @author  $Author: rnakai $
 */
public class RftId5001
        extends SendIdMessage
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     *サーバ日時のオフセットの定義
     */
    private static final int OFF_SERVER_DATE = LEN_HEADER;

    /**
     * 応答フラグのオフセットの定義
     */
    private static final int OFF_ANSWER_CODE = OFF_SERVER_DATE + LEN_SERVER_DATE;

    /**
     * エラー詳細のオフセットの定義
     */
    private static final int OFF_ERROR_DETAILS = OFF_ANSWER_CODE + LEN_ANSWER_CODE;

    /**
     * ETXのオフセットの定義
     */
    private static final int OFF_ETX = OFF_ERROR_DETAILS + LEN_ERROR_DETAILS;

    /**
     * ID番号
     */
    public static final String ID = "5001";


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 親クラスのコンストラクタを呼び出した後、
     * 電文の長さをセットします。また、内部バッファを空白で初期化します。
     */
    public RftId5001()
    {
        super();
        _length = OFF_ETX + LEN_ETX;
        initializeBuffer();
    }


    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * サーバ日時を設定します。
     * @param  serverDate サーバ日時
     */
    public void setServerDate(String serverDate)
    {
        setToBufferLeft(serverDate, OFF_SERVER_DATE, LEN_SERVER_DATE);
    }

    /**
     * 応答フラグを設定します。
     * @param   ansCode     設定する応答フラグ
     */
    public void setAnsCode(String ansCode)
    {
        setToBufferLeft(ansCode, OFF_ANSWER_CODE, LEN_ANSWER_CODE);
    }

    /**
     * エラー詳細を設定します。
     * @param errDetails    設定するエラー詳細
     */
    public void setErrDetails(String errDetails)
    {
        setToBufferLeft(errDetails, OFF_ERROR_DETAILS, LEN_ERROR_DETAILS);
    }

    /**
     * ETXを設定します。
     */
    public void setETX()
    {
        setToByteBuffer(DEF_ETX, OFF_ETX);
    }

    /**
     * 応答フラグを取得します。
     * @return  応答フラグ
     */
    public String getAnsCode()
    {
        String ansCode = getFromBuffer(OFF_ANSWER_CODE, LEN_ANSWER_CODE);
        return ansCode.trim();
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 4181 $,$Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $");
    }
}
//end of class
