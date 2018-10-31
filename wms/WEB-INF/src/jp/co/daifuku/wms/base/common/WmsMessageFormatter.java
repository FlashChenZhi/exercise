// $Id: WmsMessageFormatter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.text.MessageFormatter;


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
 * @see MessageFormatter
 */

public final class WmsMessageFormatter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * do not use constructor.
     */
    private WmsMessageFormatter()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ロギングメッセージ・画面メッセージを文字列で表現する形式で返します。
     * <li>呼び出し例</li>
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
    public static synchronized String format(int msgNo, Object... msgArgs)
    {
        return MessageFormatter.format(msgNo, msgArgs);
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
        return MessageFormatter.parseMsgNumber(stringMsg);
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
        return MessageFormatter.parseMsgArgs(stringMsg);
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
        return "$Id: WmsMessageFormatter.java 87 2008-10-04 03:07:38Z admin $";
    }
}
