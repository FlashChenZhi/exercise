// $$Id: SBPLCommandHandler.java 1911 2008-12-11 02:51:48Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.base.entity;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;


/**
 * SBPLコマンドを読み込むクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/13</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SBPLCommandHandler
{
    /** <code>このクラスのインスタンス</code> */
    private static SBPLCommandHandler $handlerInst = null;

    /** <code>SBPLコマンド名を格納するHashtable</code> */
    private Hashtable<String, String> resourceTable = null;


    /**
     * このクラスのコンストラクタです。
     */
    private SBPLCommandHandler()
    {
        resourceTable = new Hashtable<String, String>();
        ResourceBundle sysResource = ResourceBundle.getBundle(LabelConstants.SBPL_CMD_DEF_FILENAME);
        Enumeration<String> keys = sysResource.getKeys();

        while (keys.hasMoreElements())
        {
            String strKey = keys.nextElement();
            resourceTable.put(sysResource.getString(strKey), LabelConstants.ESC
                    + sysResource.getString(strKey));
        }
    }

    /**
     * このクラスのインスタンスを取得します。
     * 
     * @return このクラスのインスタンス
     */
    public static SBPLCommandHandler getInstance()
    {
        if ($handlerInst == null)
        {
            $handlerInst = new SBPLCommandHandler();
        }
        return $handlerInst;
    }

    /**
     * コマンド名のキーに対応するコマンド名(ESC + コマンド名キー)を取得します。
     * 
     * @param key コマンド名のキー
     * @return コマンド名(ESC + コマンド名キー)
     */
    public String getCommandName(String key)
    {
        return resourceTable.get(key);
    }
}
