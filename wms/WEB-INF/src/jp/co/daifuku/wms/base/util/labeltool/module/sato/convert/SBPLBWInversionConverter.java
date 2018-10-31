// $$Id: SBPLBWInversionConverter.java 2583 2009-01-08 00:20:09Z kumano $$
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
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.parse.SBPLConvertDto ;
import jp.co.daifuku.wms.label.xmlbeans.BWInversion ;

/**
 * 黒白反転印字指定オブジェクトに関する転換処理のクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/26</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SBPLBWInversionConverter
{
	/**
	 * コマンド文字列、変数保持エンティティによりオブジェクトの各属性値を設定します。
	 * 
	 * @param item バーコードオブジェクト
	 * @param cmd 黒白反転印字指定のSBPLコマンド
	 * @param dto 変数保持エンティティ
	 */
	public static void setProperties(BWInversion item, SBPLCommand cmd, SBPLConvertDto dto)
	{
		// 出力順番号 
		item.setSeqNo(dto.getItemSeqNo()) ;
		// 横印字位置
		item.setHPosition(dto.getHPosition()) ;
		// 縦印字位置
		item.setVPosition(dto.getVPosition()) ;
		// 回転方向
		item.setRotation(dto.getRotation()) ;
		// 横方向の反転エリア
		item.setHInversionArea(Integer.parseInt(cmd.getParameters().substring(0,
				cmd.getParameters().indexOf(LabelConstants.COMMA_STR)))) ;
		// 縦方向の反転エリア
		item.setVInversionArea(Integer.parseInt(cmd.getParameters().substring(
				cmd.getParameters().indexOf(LabelConstants.COMMA_STR) + 1))) ;
		clearDtoValue(dto) ;
	}

	/**
	 * 黒白反転印字指定オブジェクトをSBPLコマンド文字列に転換します。
	 * 
	 * @param bWInversionitem 黒白反転印字指定オブジェクト
	 * @return SBPL文字列
	 */
	public static String toSBPLString(BWInversion bWInversionitem)
	{
		StringBuffer buf = new StringBuffer() ;
		buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ROTATION)
				+ bWInversionitem.getRotation()) ;
		if (!StringUtil.isEmpty(bWInversionitem.getVPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_V_POSITION)
					+ bWInversionitem.getVPosition()) ;
		}
		if (!StringUtil.isEmpty(bWInversionitem.getHPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_H_POSITION)
					+ bWInversionitem.getHPosition()) ;
		}
		buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_BW_INVERSION)
				+ bWInversionitem.getHInversionArea()
				+ LabelConstants.COMMA_STR
				+ bWInversionitem.getVInversionArea()) ;
		return buf.toString() ;
	}

	/**
	 * 変数保持用Dtoに一部属性を初期化します。
	 * 
	 * @param dto 変数保持用Dto
	 */
	private static void clearDtoValue(SBPLConvertDto dto)
	{
		dto.setHPosition("") ;
		dto.setVPosition("") ;
	}
}
