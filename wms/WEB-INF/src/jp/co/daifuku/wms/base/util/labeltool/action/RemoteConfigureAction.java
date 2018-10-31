// $$Id: RemoteConfigureAction.java 1911 2008-12-11 02:51:48Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.action;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Component;
import java.io.File;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.EnvDefHandler;
import jp.co.daifuku.wms.base.util.labeltool.base.util.ScreenUtil;
import jp.co.daifuku.wms.base.util.labeltool.gui.RemoteConfigureScr;


/**
 * サーバ接続画面に関する動作処理を行うクラスです。<br>
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
public class RemoteConfigureAction
{

    /** <code>動作の発生元画面</code> */
    private RemoteConfigureScr scr = null;

    /**
     * このクラスのコンストラクタです。
     * 
     * @param form 動作の発生元画面
     */
    public RemoteConfigureAction(Component form)
    {
        scr = (RemoteConfigureScr)form;
    }

    /**
     * 画面の設定値を保存するメソッドです。<br>
     * 
     * @return 成功した場合、trueを返します。<br>
     *         失敗した場合、falseを返します。
     */
    public boolean save()
    {
        if (ScreenUtil.showConfirm(scr, "MSG0010") == ScreenUtil.CANCEL_OPTION)
        {
            return false;
        }
        try
        {
            File ip = new File(LabelConstants.DIRSEPACHAR
                    + LabelConstants.DIRSEPACHAR
                    + scr.getAddressField().getText().trim()
                    + LabelConstants.DIRSEPACHAR
                    + scr.getRootField().getText().trim());
            if (!ip.exists())
            {
                ScreenUtil.showMessage(scr, "ERR0032");
                return false;
            }
            EnvDefHandler.getInstance().setDefineValue(
                    "serverAddress",
                    LabelConstants.DIRSEPACHAR
                            + LabelConstants.DIRSEPACHAR
                            + scr.getAddressField().getText().trim());
            EnvDefHandler.getInstance().setDefineValue("rootDirectory",
                    scr.getRootField().getText().trim());

            ScreenUtil.showMessage(scr, "MSG0008");
            return true;
        }
        catch (Exception e)
        {
            ScreenUtil.showError(scr, "ERR0024");
            return false;
        }
    }
}
