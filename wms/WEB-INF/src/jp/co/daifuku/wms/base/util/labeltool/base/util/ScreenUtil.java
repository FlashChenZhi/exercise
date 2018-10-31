// $Id: ScreenUtil.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.util;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Component;

import javax.swing.JOptionPane;

import jp.co.daifuku.wms.base.util.labeltool.base.entity.MessageHandler;


/**
 * ScreenUtil class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/11</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */


public class ScreenUtil
{
    /** <code>OK_OPTION</code> */
    public static final int OK_OPTION = JOptionPane.OK_OPTION;
    
    /** <code>CANCEL_OPTION</code> */
    public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;
    
    /**
     * メッセージ表示メソッドです。<br>
     * 
     * @param parentComponent 親コンポーネント
     * @param msgCode メッセージコード
     */
    public static void showMessage(Component parentComponent, String msgCode)
    {
        JOptionPane.showMessageDialog(parentComponent,
                MessageHandler.getInstance().getResourceValue(msgCode));
    }
    
    /**
     * エラーメッセージ表示メソッドです。<br>
     * 
     * @param parentComponent 親コンポーネント
     * @param msgCode メッセージコード
     */
    public static void showError(Component parentComponent, String msgCode)
    {
        JOptionPane.showMessageDialog(parentComponent,
                MessageHandler.getInstance().getResourceValue(msgCode), null,
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 確認メッセージ表示メソッドです。<br>
     * 
     * @param parentComponent 親コンポーネント
     * @param msgCode メッセージコード
     * @return OKの場合
     */
    public static int showConfirm(Component parentComponent, String msgCode)
    {
        return JOptionPane.showConfirmDialog(parentComponent,
                MessageHandler.getInstance().getResourceValue(msgCode), null,
                JOptionPane.OK_CANCEL_OPTION);
    }
}
