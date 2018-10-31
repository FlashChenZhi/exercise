// $$Id: SBPLFormCallerConverter.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.module.sato.convert ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommand ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommandHandler ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.parse.SBPLConvertDto ;
import jp.co.daifuku.wms.label.xmlbeans.FormCaller ;

/**
 * フォームオーバレイ呼出し指定オブジェクトに関する転換処理のクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/27</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SBPLFormCallerConverter
{
	/**
	 * コマンド文字列、変数保持エンティティによりオブジェクトの属性値を設定します。<br>
	 * 
	 * @param item フォントオブジェクト
	 * @param cmd フォントのSBPLコマンド
	 * @param dto 変数保持エンティティ
	 */
	public static void setProperties(FormCaller item, SBPLCommand cmd, SBPLConvertDto dto)
	{
		// 出力順番号
		item.setSeqNo(dto.getItemSeqNo()) ;
		// 登録キー
		item.setRegisterKey(cmd.getParameters()) ;
	}

	/**
	 * フォームオーバレイ呼出し指定オブジェクトオブジェクトをSBPLコマンド文字列に転換します。
	 * 
	 * @param frmCaller フォームオーバレイ呼出し指定オブジェクトオブジェクト
	 * @return SBPL文字列
	 */
	public static String toSBPLString(FormCaller frmCaller)
	{
		return SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_FORM_CALLER)
				+ frmCaller.getRegisterKey() ;
	}
}
