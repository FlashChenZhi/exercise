// $Id: LabelInfoUtil.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.util;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.EnvDefHandler;

/**
 * 各種類ラベル管理情報を格納する場所の取得に関するクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/17</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class LabelInfoUtil
{
    /**
     * サーバ側管理情報を格納する場所を取得します。
     * 
     * @return サーバ側管理情報を格納する場所
     */
    public static String getRemotePath()
    {
        return EnvDefHandler.getInstance().getDefineValue("serverAddress")
                + LabelConstants.DIRSEPACHAR
                + EnvDefHandler.getInstance().getDefineValue("rootDirectory")
                + LabelConstants.DIRSEPACHAR;
    }

    /**
     * サーバ側XML定義ファイルを格納する場所を取得します。
     * 
     * @return サーバ側XML定義ファイルを格納する場所
     */
    public static String getRemoteXMLPath()
    {
        return getRemotePath() + LabelConstants.XML_PATH + LabelConstants.DIRSEPACHAR;
    }

    /**
     * サーバ側レイアウトファイルを格納する場所を取得します。
     * 
     * @return サーバ側レイアウトファイルを格納する場所
     */
    public static String getRemoteLayoutPath()
    {
        return getRemotePath() + LabelConstants.LAYOUT_PATH + LabelConstants.DIRSEPACHAR;
    }

    /**
     * サーバ側エンティティファイルを格納する場所を取得します。
     * 
     * @return サーバ側エンティティファイルを格納する場所
     */
    public static String getRemoteEntityPath()
    {
        return getRemotePath() + LabelConstants.ENTITY_PATH + LabelConstants.DIRSEPACHAR;
    }

    /**
     * ローカル側レイアウトファイルを格納する場所を取得します。
     * 
     * @return ローカル側レイアウトファイルを格納する場所
     */
    public static String getLocalLayoutPath()
    {
        return LabelConstants.LOCAL_DATA_PATH
                + LabelConstants.DIRSEPACHAR
                + LabelConstants.LAYOUT_PATH
                + LabelConstants.DIRSEPACHAR;
    }

    /**
     * ローカル側XMLファイルを格納する場所を取得します。
     * 
     * @return ローカル側XMLファイルを格納する場所
     */
    public static String getLocalXMLPath()
    {
        return LabelConstants.LOCAL_DATA_PATH
                + LabelConstants.DIRSEPACHAR
                + LabelConstants.XML_PATH
                + LabelConstants.DIRSEPACHAR;
    }
    
    /**
     * ローカル側Entityファイルを格納する場所を取得します。
     * 
     * @return ローカル側Entityファイルを格納する場所
     */
    public static String getLocalEntityPath()
    {
        return LabelConstants.LOCAL_DATA_PATH
                + LabelConstants.DIRSEPACHAR
                + LabelConstants.ENTITY_PATH
                + LabelConstants.DIRSEPACHAR;
    }

}
