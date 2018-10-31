// $Id: RftId0001.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

import jp.co.daifuku.wms.base.communication.rft.RecvIdMessage;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * システム報告 ID=0001 電文
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id0001の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>            <TH>長さ</TH>    <TH>内容</TH></TR>
 * <tr><td>STX</td>               <td> 1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>           <td> 4 byte</td> <td></td></tr>
 * <tr><td>ID</td>                <td> 4 byte</td> <td>0001</td></tr>
 * <tr><td>端末送信時間</td>      <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>サーバ送信時間</td>    <td> 6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>端末号機No.</td>       <td> 3 byte</td> <td></td></tr>
 * <tr><td>報告区分</td>          <td> 1 byte</td> <td>0:起動　1:終了　3:休憩　4:再開</td></tr>
 * <tr><td>端末区分</td>          <td> 2 byte</td> <td>00:HT　10:検品端末　20:Pカート</td></tr>
 * <tr><td>端末IPアドレス</td>    <td>15 byte</td> <td>999.999.999.999</td></tr>
 * <tr><td>ETX</td>               <td> 1 byte</td> <td>0x03</td></tr>
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
public class RftId0001
        extends RecvIdMessage
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 報告区分のオフセットの定義
     */
    private static final int OFF_REPORT_FLAG = LEN_HEADER;

    /**
     * 端末区分のオフセットの定義
     */
    private static final int OFF_TERMINAL_TYPE = OFF_REPORT_FLAG + LEN_REPORT_FLAG;

    /**
     * 端末IPアドレスのオフセットの定義
     */
    private static final int OFF_IP_ADDRESS = OFF_TERMINAL_TYPE + LEN_TERMINAL_TYPE;

    /**
     * ETXのオフセットの定義
     */
    //private static final int OFF_ETX = OFF_IP_ADDRESS + LEN_IP_ADDRESS;
    /**
     * 報告区分:起動
     */
    public static final String REPORT_FLAG_START = "0";

    /**
     * 報告区分:終了
     */
    public static final String REPORT_FLAG_STOP = "1";

    /**
     * 報告区分:休憩
     */
    public static final String REPORT_FLAG_REST = "3";

    /**
     * 報告区分:再開
     */
    public static final String REPORT_FLAG_REPENDING = "4";

    /**
     * ID番号
     */
    public static final String ID = "0001";

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
     * インスタンスを生成します。
     */
    public RftId0001()
    {
        super();
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 作業種別を取得します。
     * @return   報告区分
     */
    public String getReportFlag()
    {
        String reportFlag = getFromBuffer(OFF_REPORT_FLAG, LEN_REPORT_FLAG);
        return reportFlag;
    }

    /**
     * 端末区分を取得します。
     * @return   端末区分
     */
    public String getTerminalType()
    {
        String terminalType = getFromBuffer(OFF_TERMINAL_TYPE, LEN_TERMINAL_TYPE);
        return terminalType.trim();
    }

    /**
     * 端末IPアドレスを取得します。
     * @return   端末IPアドレス
     */
    public String getIpAddress()
    {
        String ipAddress = getFromBuffer(OFF_IP_ADDRESS, LEN_IP_ADDRESS);
        return ipAddress;
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
