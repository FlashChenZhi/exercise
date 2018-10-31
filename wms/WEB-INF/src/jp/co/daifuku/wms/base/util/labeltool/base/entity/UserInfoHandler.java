// $Id: UserInfoHandler.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.entity;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.base.util.LabelInfoUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * UserInfoHandler class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
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


public class UserInfoHandler
{
    /** <code>タグ名の定数</code> */
    private static final String USER = "user";

    /** <code>属性名の定数</code> */
    private static final String ID = "id";

    /** <code>属性名の定数</code> */
    private static final String PASSWORD = "password";

    /** <code>属性名の定数</code> */
    private static final String LEVEL = "level";

    /**
     * 指定したユーザIDのユーザ情報を取得します。
     * 
     * @param userId ユーザID
     * @param passWord パスワード
     * @return 情報が取得された場合に、UserInfoのインスタンスを返します。<br>
     *         情報が取得されない場合に、nullを返します。
     * @throws DaiException 異常が発生した場合
     */
    public static UserInfo getUserInfo(String userId, String passWord)
            throws DaiException
    {
        UserInfo user = null;
        String infoFileName = LabelInfoUtil.getRemotePath() + LabelConstants.USER_INFO_FILE_NAME;
        // XMLファイルを読み込みます。
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document xd = builder.parse(new File(infoFileName));
            // "data" エレメントのノードリストを取得する。
            NodeList xnl = xd.getElementsByTagName(USER);
            for (int i = 0; i < xnl.getLength(); i++)
            {
                // 子ノードの値を取得する。
                Node node = xnl.item(i);
                if (userId.equals(((Element)node).getAttribute(ID))
                        && passWord.equals(((Element)node).getAttribute(PASSWORD)))
                {
                    user = new UserInfo();
                    user.setId(((Element)node).getAttribute(ID));
                    user.setPassword(((Element)node).getAttribute(PASSWORD));
                    user.setLevel(((Element)node).getAttribute(LEVEL));
                    break;
                }
            }
        }
        catch (ParserConfigurationException e)
        {
            // ERR0028=ユーザ情報取得エラー。
            throw new DaiException("ERR0028",e);
        }
        catch (SAXException e)
        {
            // ERR0028=ユーザ情報取得エラー。
            throw new DaiException("ERR0028",e);
        }
        catch (IOException e)
        {
            // ERR0028=ユーザ情報取得エラー。
            throw new DaiException("ERR0028",e);
        }
        return user;

    }
}
