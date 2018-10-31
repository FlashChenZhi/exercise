package jp.co.daifuku.wms.base.common.tool.logViewer;

import java.util.ArrayList;

/**
 * ファイル名    ：CommunicationData.java
 * <PRE>
 * <B>改定履歴：</B>
 *      Ver.1.00  2006/02/09 tsukoi 名前(MTB)
 * </PRE>
 * 通信トレースログ一覧表示画面に表示する対象となる <BR>
 * トレース情報一覧を保持する。
 * <p>
 * <table border="1">
 * <CAPTION>通信トレースログファイル一覧の項目</CAPTION>
 * <TR><TH>処理日付</TH>     	<TH>長さ</TH>		<TH>内容</TH></TR>
 * <tr><td>処理時間</td>		<td>16 byte</td>	<td></td></tr>
 * <tr><td>送受信ﾌﾗｸﾞ</td>		<td>40 byte</td>	<td></td></tr>
 * <tr><td>ＩＤＮＯ</td>		<td>2 byte</td>		<td>CR + LF</td></tr>
 * <tr><td>電文</td>			<td>2 byte</td>		<td>CR + LF</td></tr>
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

public class TraceList {
    
    // Class variables -----------------------------------------------
    /**
     * 通信トレースログデータ
     */
	protected ArrayList traceData;
	
    /**
     * 端末No.
     */
	protected String rftNo;

    // Public methods ------------------------------------------------
    /**
     * コンストラクタ
     */
    public TraceList()
    {
        super();
        traceData = new ArrayList();
    }

    /**
     * コンストラクタ
     * @param size 通信トレースログデータリストのサイズ
     */
    public TraceList(int size)
    {
        traceData = new ArrayList(size);
    }

    /**
     * 通信トレースログデータのサイズを取得します。
     * @return 通信トレースログデータのサイズ
     */
    public int getSize()
    {
    	return traceData.size();
    }
    
    /**
     * 通信トレースログデータリストにデータを追加します。
     * @param data 通信トレースログデータ
     */
    public void add(TraceData data)
    {
    	traceData.add(data);
    }
    
    /**
     * 通信トレースログデータリストにデータを追加します。
     * @param index 取得する通信トレースログのインデックス
     * @return 通信トレースのログデータ
     */
    public TraceData getTraceData(int index)
    {
    	return (TraceData) traceData.get(index);
    }
    
    /**
     * 端末No.をセットします。
     * @param rftNo セットする端末No.
     */
    public void setRftNo(String rftNo)
    {
    	this.rftNo = rftNo;
    }

    /**
     * 端末No.を取得します。
     * @param 端末No.
     */
    public String getRftNo()
    {
    	return rftNo;
    }
}