// $$Id: SBPLCommand.java 1911 2008-12-11 02:51:48Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.base.entity;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * SBPLコマンドクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/05</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SBPLCommand
{
    /** <code>コマンド名</code> */
    private String name;

    /** <code>パラメータ文字列</code> */
    private String parameters;

    /**
     * @return コマンド名
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name コマンド名
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return パラメータ文字列
     */
    public String getParameters()
    {
        return parameters;
    }

    /**
     * @param parameters パラメータ文字列
     */
    public void setParameters(String parameters)
    {
        this.parameters = parameters;
    }


}
