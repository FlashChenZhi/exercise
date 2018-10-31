// $Id: PrinterAdapter.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.base.util.labeltool.base;

import java.io.IOException;

import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiSocketException;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiSocketTimeoutException;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * プリンタの共通インターフェースです<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/19</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  chenjun
 * @author  Last commit: $Author: kitamaki $
 */
public interface PrinterAdapter
{
    /**
     * ソケット通信の接続を行います。
     * 
     * @throws DaiSocketException ソケット通信エラーが発生した場合
     */
    public void connect()
            throws DaiSocketException;

    /**
     * ソケット通信の切断を行います。
     * 
     * @throws DaiSocketException ソケット通信エラーが発生した場合
     */
    public void close()
            throws DaiSocketException;

    /**
     * データを送信するメソッドです。<br>
     * 
     * @param cmdStr 送信データ
     * @return ステータスコードを返します。<br>
     * @throws DaiSocketException ソケット通信エラーが発生した場合
     * @throws DaiSocketTimeoutException ソケット通信タイムアウトが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    public String sendCommand(byte[] cmdStr)
            throws DaiSocketException,
                DaiSocketTimeoutException,
                IOException;

    /**
     * プリンタの状態を取得するメソッドです。
     * @return 状態コード
     * @throws DaiException ソケット通信エラーが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    public String getStatus()
            throws DaiException,
                IOException;
}
