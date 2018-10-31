// $Id: MessageFormatter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common.text;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.MessageResource;


/**
 * 文字列表現メッセージについての生成・パースを行うためのクラスです。<br>
 * 例外およびメッセージログなどで利用します。
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 * @see jp.co.daifuku.common.RmiMsgLogClient
 * @see jp.co.daifuku.common.CommonException
 */

public final class MessageFormatter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    private static final char DELIMITER = MessageResource.DELIM.charAt(0);

    /** メッセージ番号がパースできなかった場合に返されるメッセージ番号 */
    public static final int NO_MESSAGE_FOUND = -1;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ユーティリティクラスですので、publicコンストラクタはありません。
     */
    private MessageFormatter()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ロギングメッセージ・画面メッセージを文字列で表現する形式で返します。<br>
     * 
     * Java5 に対応しているシステムでは、このクラスは使用せず、このクラスを呼び出す
     * メッセージ用フォーマッタを用意してください。<br>
     * その場合のメソッドは以下のように記述してください。
     * <code><pre>
     * public static synchronized String format(int msgNo, Object... msgArgs)
     * {
     *     return MessageFormatter.format(msgNo, msgArgs);
     * }
     * </pre></code>
     * 可変引数がサポートされている JDK/JRE ではあらかじめ配列を用意することなく
     * メッセージフォーマットメソッドを呼び出すことができます。<br>
     * <li>呼び出し例 (eWareNavi3.0)</li>
     * <code><pre>
     * // メッセージ番号だけの場合
     * String msg = WmsMessageFormatter.format(6122338);
     * throw new ScheduleException(msg);
     * 
     * // メッセージ番号 + パラメータが1つの場合
     * String msg = WmsMessageFormatter.format(6288991, "A");
     * throw new ScheduleException(msg);
     * 
     * // メッセージ番号 + パラメータが3つの場合
     * String msg = WmsMessageFormatter.format(6488553, "A", "B", "C");
     * throw new ScheduleException(msg);
     * 
     * // 従来通りの呼び出しも可能です
     * Object[] msgArgs = {
     *     "X",
     * };
     * String msg = WmsMessageFormatter.format(6288991, msgArgs);
     * throw new ScheduleException(msg);
     * </pre></code>
     * 
     * @param msgNo メッセージ番号
     * @param msgArgs メッセージ引数
     * @return メッセージの文字列表現
     */
    public static synchronized String format(int msgNo, Object[] msgArgs)
    {
        String.valueOf(msgNo);
        StringBuilder buff = new StringBuilder();
        buff.append(msgNo);
        for (int i = 0; i < msgArgs.length; i++)
        {
            buff.append(DELIMITER);
            buff.append(String.valueOf(msgArgs[i]));
        }

        return new String(buff);
    }

    /**
     * 文字列表現のメッセージからメッセージ番号を取り出して返します。<br>
     * メッセージ番号がセットされていなかったり、正しいフォーマットでなかったときは
     * NO_MESSAGE_FOUND が返されます。
     * 
     * @param stringMsg  文字列表現のメッセージ
     * @return メッセージ番号
     */
    public static synchronized int parseMsgNumber(String stringMsg)
    {
        if (null == stringMsg || 0 == stringMsg.length())
        {
            return NO_MESSAGE_FOUND;
        }

        CSVTokenizer tokenizer = new CSVTokenizer(stringMsg, DELIMITER, '\u0000');
        try
        {
            String msgno = tokenizer.nextToken();
            return Integer.parseInt(msgno);
        }
        catch (Exception e)
        {
            // catch "no more element", "parse exception"
            return NO_MESSAGE_FOUND;
        }
    }


    /**
     * 文字列表現のメッセージからメッセージパラメータを取り出して返します。
     * 
     * @param stringMsg  文字列表現のメッセージ
     * @return メッセージパラメータ<br>
     * パラメータがなかったときは、String[0]が返されます。
     */
    public static synchronized String[] parseMsgArgs(String stringMsg)
    {
        if (null == stringMsg || 0 == stringMsg.length())
        {
            return new String[0];
        }

        List<String> argList = new ArrayList<String>();
        CSVTokenizer tokenizer = new CSVTokenizer(stringMsg, DELIMITER, '\u0000');
        try
        {
            // skip message number element
            tokenizer.nextToken();
            while (tokenizer.hasMoreTokens())
            {
                String arg = tokenizer.nextToken();
                argList.add(arg);
            }
        }
        catch (Exception e)
        {
            // catch "no more element"
        }
        return (String[])argList.toArray(new String[0]);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: MessageFormatter.java 87 2008-10-04 03:07:38Z admin $";
    }
}
