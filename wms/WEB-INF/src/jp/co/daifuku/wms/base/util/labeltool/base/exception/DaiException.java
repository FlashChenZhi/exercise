// $Id: DaiException.java 7593 2010-03-16 00:11:20Z okayama $
package jp.co.daifuku.wms.base.util.labeltool.base.exception;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * アプリケーションのベースException<br>
 * ダイフクアプリケーション内で発生した例外をスローする為の基底Exceptionクラス。<br>
 * (Dai**Exceptionの基底となる)<br>
 * エラーメッセージとベースException（元となったException）を保持できる。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2007/04/05</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 7593 $, $Date: 2010-03-16 09:11:20 +0900 (火, 16 3 2010) $
 * @author  chenjun
 * @author  Last commit: $Author: okayama $
 */
@SuppressWarnings("serial")
public class DaiException
        extends Exception
{
    /** <code>基底Exceptionクラス</code> */
    private Exception _base_Exception = null;

    /** <code>エラーメッセージ</code> */
    private String exception_Message = "";

    /**
     * コンストラクタです。
     */
    public DaiException()
    {
    }

    /**
     * コンストラクタ（エラーメッセージをセット）<br>
     * 引数で渡されたメッセージをコンストラクタに渡します。
     * 
     * @param message エラーメッセージの内容
     */
    public DaiException(String message)
    {
        this.exception_Message = message == null || message.length() == 0 ? ""
                                                                            : message;
    }

    /**
     * コンストラクタ（エラーメッセージとベースExceptionをセット）<br>
     * 引数で渡されたメッセージとベースExceptionをコンストラクタに渡します。
     * 
     * @param message エラーメッセージの内容
     * @param innerException コンストラクタに渡したException
     */
    public DaiException(final String message, final Exception innerException)
    {
        this.exception_Message = message == null || message.length() == 0 ? ""
                                                                         : message;
        this._base_Exception = innerException;
    }

    /**
     * ベースExceptionを返します。<br>
     * このメソッドは保持しているベースExceptionを返します。<br>
     * 保持していない場合はNothingが返ります。<br>
     * ベースExceptionは、コンストラクタに渡したExceptionです。
     * 
     * @return baseException コンストラクタに渡したException
     */
    public final Exception getBaseException()
    {
        return this._base_Exception;
    }

    /**
     * このメソッドは保持しているエラーメッセージを返します。<br>
     * 保持していない場合はnullが返ります。<br>
     * エラーメッセージは、コンストラクタに渡したエラーメッセージです。
     * 
     * @return exceptionMessage エラーメッセージ
     */
    public final String getMessage()
    {
        return this.exception_Message;
    }
}
