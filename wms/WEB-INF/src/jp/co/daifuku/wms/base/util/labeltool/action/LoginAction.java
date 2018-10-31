// $Id: LoginAction.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.action;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Component;

import jp.co.daifuku.wms.base.util.labeltool.base.entity.Authority;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.UserInfo;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.UserInfoHandler;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.base.util.ScreenUtil;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil;
import jp.co.daifuku.wms.base.util.labeltool.gui.LoginScr;


/**
 * LoginAction class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/07/04</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */


public class LoginAction
{
    /** <code>動作の発生元画面</code> */
    private LoginScr scr = null;

    /**
     * このクラスのコンストラクタです。
     * 
     * @param form 動作の発生元画面
     */
    public LoginAction(Component form)
    {
        scr = (LoginScr)form;
    }

    /**
     * ログインを行います。
     * @return 成功した場合に、trueを返します。<br>
     *         失敗した場合に、falseを返します。
     */
    public boolean login()
    {
        if (!check())
        {
            return false;
        }
        String userId = scr.getTxtUserId().getText().trim();
        String passWord = new String(scr.getTxtPassword().getPassword());
        try
        {
            UserInfo user = UserInfoHandler.getUserInfo(userId, passWord);
            if (user == null)
            {
                ScreenUtil.showError(scr, "ERR0027");
                return false;
            }
            Authority.$id = user.getId();
            Authority.$level = user.getLevel();
            return true;
            
        }
        catch (DaiException e)
        {
            ScreenUtil.showError(scr, e.getMessage());
            return false;
        }
    }

    /**
     * チェックを行います。
     * 
     * @return エラーなし場合、trueを返します。<br>
     *         エラーがある場合、falseを返します。
     */
    private boolean check()
    {
        if (StringUtil.isEmpty(scr.getTxtUserId().getText()))
        {
            // ERR0025=ユーザIDを入力してください。
            ScreenUtil.showError(scr, "ERR0025");
            return false;
        }
        if (scr.getTxtPassword().getPassword() == null)
        {
            // ERR0026=パスワードを入力してください。
            ScreenUtil.showError(scr, "ERR0026");
            return false;
        }


        return true;
    }
}
