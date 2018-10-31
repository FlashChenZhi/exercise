// $Id: MessageHandler.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil;


/**
 * このクラスはメッセージリソースファイルを読み込むクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2007/04/13</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class MessageHandler
{
    /** <code>このクラスのインスタンス</code> */
    private static MessageHandler $messageHandlerInst = null;

    /** <code>メッセージキーと値を保持するリソーステーブル</code> */
    private Hashtable<String, String> _resourceTable = null;

    /**
     * コンストラクタ
     * Hashtableを生成し、メッセージキーと値を hashtable に保持します。
     * 
     */
    private MessageHandler()
    {
        _resourceTable = new Hashtable<String, String>();
        ResourceBundle messageResource = ResourceBundle.getBundle(LabelConstants.MESSAGE_FILENAME);
        Enumeration<String> keys = messageResource.getKeys();
        while (keys.hasMoreElements())
        {
            String strKey = keys.nextElement();
            _resourceTable.put(strKey, messageResource.getString(strKey));
        }
    }

    /**
     * このメソッドはメッセージハンドラクラスのインスタンスを返します。<br>
     * 保持している自クラスのインスタンスを返します。<br>
     * 初回は、保持していないのでインスタンスを生成してそれを返します。
     *  
     * @return Hashtableのインスタンス
     */
    public static MessageHandler getInstance()
    {
        // Hashtableインスタンスを存在しない場合は新しいインスタンスを作成する。
        // インスタンスがある場合はそのまま返す。
        if ($messageHandlerInst == null)
        {
            $messageHandlerInst = new MessageHandler();
        }
        return $messageHandlerInst;
    }

    /** 
     * メッセージ番号に対応するメッセージ(文字列)を取得します。<br>
     * メッセージ番号に対応するメッセージを、保持しているhashTabから検索し、<br>
     * 文字列にして返します。
     * 
     * @param key メッセージ番号
     * @return メッセージ番号に対応するメッセージの文字列
     */
    public String getResourceValue(String key)
    {
        String value = "";
        if (StringUtil.isEmpty(key))
        {
            return value;
        }
        // hashtableからキーをつけて値を取得する。この値を返す。
        value = _resourceTable.get(key).toString();
        return value;
    }
}
