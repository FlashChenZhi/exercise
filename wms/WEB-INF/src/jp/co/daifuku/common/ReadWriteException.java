// $Id: ReadWriteException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common;

import jp.co.daifuku.common.text.MessageFormatter;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * 外部記憶装置へのアクセスにおいて、異常が発生した場合に使用する例外です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This exception is used for errors occurred during the access to the external storage device.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ReadWriteException
        extends CommonException
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    /** <code>serialVersionUID</code>のコメント */
    private static final long serialVersionUID = 9036541957210640492L;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 詳細メッセージを指定しないで Exception を構築します
     </jp>*/
    /**<en>
     * Constructs Exception without assigning the detail message.
     </en>*/
    public ReadWriteException()
    {
        super();
    }

    /**<jp>
     * メッセージ付きの例外を作成します。
     * @param msg  詳細メッセージ
     * @deprecated ReadWriteException(int msgNo, Object[] params)を使用してください。
     </jp>*/
    /**<en>
     * Creates the exception with message attached.
     * @param msg  Detail message
     * @deprecated do not use this exception with message.
     </en>*/
    public ReadWriteException(String msg)
    {
        super(msg);
    }

    /**<jp>
     * メッセージ付きの例外を作成します。
     * @param msgNo メッセージ番号
     * @param params メッセージパラメータ
     * @deprecated do not use this exception with message.
     </jp>*/
    /**<en>
     * Creates the exception with message attached.
     * @param msgNo message number
     * @param params  message parameters
     * @deprecated do not use this exception with message.
     </en>*/
    public ReadWriteException(int msgNo, Object[] params)
    {
        super(formatMessage(msgNo, params));
    }


    /**
     * 発生元の例外を保存する例外を作成します。
     * 
     * @param cause 例外の発生元
     */
    public ReadWriteException(Throwable cause)
    {
        super(cause);
    }

    /**
     * 編集フォーマット型のメッセージを作成します。
     * @param msgNo メッセージ番号
     * @param params 編集フォーマットパラメータ
     * @return メッセージ
     */
    public static String formatMessage(int msgNo, Object[] params)
    {
        return MessageFormatter.format(msgNo, params);
    }
}
