// $$Id: ScreenController.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.controller ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.util.labeltool.gui.MainForm ;


/**
 * 画面遷移制御クラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/29</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class ScreenController
{
	/** <code>コンテナー画面</code> */
	private static MainForm $mainForm = MainForm.getInstance() ;

	/**
	 * 指定IDの画面に遷移します。
	 * 
	 * @param gamenId 遷移先の画面ID
	 * @param param 遷移パラメータ
	 */
	public static void forwardTo(ScreenId gamenId, Object param)
	{
		$mainForm.showForm(gamenId, param) ;
	}
}
