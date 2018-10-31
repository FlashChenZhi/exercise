// $Id: DaiSocketException.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.exception;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * ソケット通信でエラーが発生するとスローされるException.<br>
 * ソケット通信でエラーが発生するとスローされます。<br>
 * 主にSocketTransmitterクラスで、スローされます。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2007/04/06</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class DaiSocketException
        extends DaiException
{
    /**
     * この�クラスのコンストラクタです�。<br>
     * ベースException DaiException のコンストラクタを呼び出します。
     */
    public DaiSocketException()
    {
        super();
    }

    /**
     * パラメータのコンストラクタ。エラーメッセージをセット。<br>
     * 引数で渡されたメッセージのコンストラクタを呼び出します�。
     * 
     * @param message エラーメッセージの内容
     */
    public DaiSocketException(String message)
    {
        // ベースException DaiException のコンストラクタを呼び出す。
        super(message);
    }

    /**
     * パラメータのコンストラクタ。��エラーメッセージとベースExceptionをセット。<br>
     * ベースのDaiExceptionを呼び出します。
     * 
     * @param message エラーメッセージの内容
     * @param innerException ベースのException
     */
    public DaiSocketException(String message, Exception innerException)
    {
        super(message, innerException);
    }
}
