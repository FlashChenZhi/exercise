// $$Id: SBPLPartCopyConverter.java 2583 2009-01-08 00:20:09Z kumano $$
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
import jp.co.daifuku.wms.label.xmlbeans.PartCopy ;


/**
 * ラベル内コピー（部分コピー）指定オブジェクトに関する転換処理のクラスです。
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
public class SBPLPartCopyConverter
{
	/**
	 * コマンド文字列、変数保持エンティティによりオブジェクトの属性値を設定します。<br>
	 * 
	 * @param item フォントオブジェクト
	 * @param cmd フォントのSBPLコマンド
	 * @param dto 変数保持エンティティ
	 */
	public static void setProperties(PartCopy item, SBPLCommand cmd, SBPLConvertDto dto)
	{
		// 出力順番号 
		item.setSeqNo(dto.getItemSeqNo()) ;
		// 横印字位置
		item.setHPosition(dto.getHPosition()) ;
		// 縦印字位置
		item.setVPosition(dto.getVPosition()) ;
		// 回転方向
		item.setRotation(dto.getRotation()) ;
		// コピー元の縦の基点
		item.setSrcStartPointV(Integer.parseInt(cmd.getParameters().substring(
				cmd.getParameters().indexOf("V") + 1, cmd.getParameters().indexOf("H")))) ;
		// コピー元の横の基点
		item.setSrcStartPointH(Integer.parseInt(cmd.getParameters().substring(
				cmd.getParameters().indexOf("H") + 1, cmd.getParameters().indexOf("Y")))) ;
		// コピー元の縦のドット数
		item.setSrcCopySizeV(Integer.parseInt(cmd.getParameters().substring(
				cmd.getParameters().indexOf("Y") + 1, cmd.getParameters().indexOf("X")))) ;
		// コピー元の横のドット数
		item.setSrcCopySizeH(Integer.parseInt(cmd.getParameters().substring(
				cmd.getParameters().indexOf("X") + 1))) ;

		clearDtoValue(dto) ;
	}

	/**
	 * 部分コピー指定オブジェクトオブジェクトをSBPLコマンド文字列に転換します。
	 * 
	 * @param partCopyItem 部分コピー指定オブジェクト
	 * @return SBPL文字列
	 */
	public static String toSBPLString(PartCopy partCopyItem)
	{
		StringBuffer buf = new StringBuffer() ;
		buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ROTATION)
				+ partCopyItem.getRotation()) ;
		if (!StringUtil.isEmpty(partCopyItem.getVPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_V_POSITION)
					+ partCopyItem.getVPosition()) ;
		}
		if (!StringUtil.isEmpty(partCopyItem.getHPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_H_POSITION)
					+ partCopyItem.getHPosition()) ;
		}
		buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PART_COPY)
				+ "V"
				+ partCopyItem.getSrcStartPointV()
				+ "H"
				+ partCopyItem.getSrcStartPointH()
				+ "Y"
				+ partCopyItem.getSrcCopySizeV()
				+ "X"
				+ partCopyItem.getSrcCopySizeH()) ;
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
