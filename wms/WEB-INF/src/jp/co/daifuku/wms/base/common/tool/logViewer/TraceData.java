package jp.co.daifuku.wms.base.common.tool.logViewer;

/**
 * ファイル名    ：CommunicationData.java
 * <PRE>
 * <B>改定履歴：</B>
 *      Ver.1.00  2006/02/09 tsukoi 名前(MTB)
 * </PRE>
 * 通信トレースログ一覧表示画面に表示する対象となる <BR>
 * トレース情報を保持する。
 * <p>
 * <table border="1">
 * <CAPTION>通信トレースログファイルデータの項目</CAPTION>
 * <TR><TH>処理日付</TH>        <TH>長さ</TH>       <TH>内容</TH></TR>
 * <tr><td>処理時間</td>        <td>16 byte</td>    <td></td></tr>
 * <tr><td>送受信ﾌﾗｸﾞ</td>      <td>40 byte</td>    <td></td></tr>
 * <tr><td>ＩＤＮＯ</td>        <td>2 byte</td>     <td>CR + LF</td></tr>
 * <tr><td>電文</td>            <td>2 byte</td>     <td>CR + LF</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */

public class TraceData {
    
    // Class variables -----------------------------------------------
    /**
     * 処理日付
     */
    private String processDate;

    /**
     * 処理時間
     */
    private String processTime;

    /**
     * 送信／受信
     */
    private String sendRecvDivision;

    /**
     * ＩＤNo
     */
    private String idNo;

    /**
     * 電文
     */
    private byte[] message;

    // Public methods ------------------------------------------------
    
    /**
     * コンストラクタ
     */
    public TraceData()
    {
        super();
    }

    /**
     * 処理日付を設定します。
     * @param value 設定する処理日付
     */
    public void setProcessDate(String value)
    {
        processDate = value ;
    }

    /**
     * 処理日付を取得します。
     * @return 処理日付
     */
    public String getProcessDate()
    {
        return processDate ;
    }

    /**
     * 処理時刻を設定します。
     * @param value 設定する処理時刻
     */
    public void setProcessTime(String value)
    {
        processTime = value ;
    }

    /**
     * 処理時刻を取得します。
     * @return 処理時刻
     */
    public String getProcessTime()
    {
        return processTime ;
    }

    /**
     * 送信／受信を設定します。
     * @param value 設定する送信／受信
     */
    public void setSendRecvDivision(String value)
    {
        sendRecvDivision = value ;
    }

    /**
     * 送信／受信を取得します。
     * @return 送信／受信
     */
    public String getSendRecvDivision()
    {
        return sendRecvDivision ;
    }
    
    /**
     * IDNoを設定します。
     * @param value 設定するIDNo
     */
    public void setIdNo(String value)
    {
        idNo = value;
    }
    
    /**
     * IDNOを取得します
     * @return idNo
     */
    public String getIdNo()
    {
        return idNo;
    }

    /**
     * 電文内容を設定します。
     * @param value 設定する電文内容
     */
    public void setCommunicateMessage(byte[] value)
    {
        message = value ;
    }
    
    /**
     * 電文を取得します。
     * @return 電文
     */
    public byte[] getCommunicateMessage()
    {
        return message;
    }
    
    /**
     * 電文を文字列型で取得します。
     * @return 電文（文字列）
     */
    public String getStringMessage()
    {
        return new String(message);
    }
}