// $$Id: SBPLRulerConverter.java 2583 2009-01-08 00:20:09Z kumano $$
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
import jp.co.daifuku.wms.label.xmlbeans.Ruler ;


/**
 * 羅線オブジェクトに関する転換処理のクラスです。
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
public class SBPLRulerConverter
{
	/**
	 * コマンド文字列、変数保持エンティティによりオブジェクトの属性値を設定します。<br>
	 * 
	 * @param item フォントオブジェクト
	 * @param cmd フォントのSBPLコマンド
	 * @param dto 変数保持エンティティ
	 */
	public static void setProperties(Ruler item, SBPLCommand cmd, SBPLConvertDto dto)
	{
		// 出力順番号 
		item.setSeqNo(dto.getItemSeqNo()) ;
		// 横印字位置
		item.setHPosition(dto.getHPosition()) ;
		// 縦印字位置
		item.setVPosition(dto.getVPosition()) ;
		// 回転方向
		item.setRotation(dto.getRotation()) ;
		// 線幅 有効範囲
		item.setLineWidth(Integer.parseInt(cmd.getParameters().substring(0, 2))) ;
		// 横方向、縦方向の指定
		item.setDirection(cmd.getParameters().substring(2, 3)) ;
		// 罫線長
		item.setLineLength(Integer.parseInt(cmd.getParameters().substring(3))) ;
		item.setRepeatFlg(dto.getRepeatFlg()) ;
		clearDtoValue(dto) ;
	}

	/**
	 * 羅線オブジェクトオブジェクトをSBPLコマンド文字列に転換します。
	 * 
	 * @param rulerItem 羅線オブジェクト
	 * @return SBPL文字列
	 */
	public static String toSBPLString(Ruler rulerItem)
	{
		StringBuffer buf = new StringBuffer() ;
		buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ROTATION)
				+ rulerItem.getRotation()) ;
		if (!StringUtil.isEmpty(rulerItem.getVPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_V_POSITION)
					+ rulerItem.getVPosition()) ;
		}
		if (!StringUtil.isEmpty(rulerItem.getHPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_H_POSITION)
					+ rulerItem.getHPosition()) ;
		}
		//�����
		buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_RULER_FRAME)) ;
		buf.append(StringUtil.paddingLeft(String.valueOf(rulerItem.getLineWidth()), '0', 2)) ;
		buf.append(rulerItem.getDirection()) ;
		buf.append(rulerItem.getLineLength()) ;
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
		dto.setRepeatFlg(LabelConstants.FLAG_OFF) ;
	}
}
