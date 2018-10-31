// $Id: UserInfo.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.entity;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */



/**
 * ユーザ情報エンティティです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/08/06</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */


public class UserInfo
{
    /** <code>ユーザID</code> */
    private String id = null;
    
    /** <code>パスワード</code> */
    private String password = null;
    
    /** <code>権限レベル</code> */
    private String level = null;

    /**
     * @return idを返します。
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id idを設定します。
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return levelを返します。
     */
    public String getLevel()
    {
        return level;
    }

    /**
     * @param level levelを設定します。
     */
    public void setLevel(String level)
    {
        this.level = level;
    }

    /**
     * @return passwordを返します。
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password passwordを設定します。
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}

