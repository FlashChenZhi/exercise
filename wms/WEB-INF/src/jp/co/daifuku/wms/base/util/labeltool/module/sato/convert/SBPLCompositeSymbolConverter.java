// $$Id: SBPLCompositeSymbolConverter.java 2583 2009-01-08 00:20:09Z kumano $$
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
import jp.co.daifuku.wms.label.xmlbeans.CompositeSymbol ;


/**
 * ＥＡＮ.ＵＣＣ合成シンボル指定オブジェクトに関する転換処理のクラスです。
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
public class SBPLCompositeSymbolConverter
{
	/**
	 * コマンド文字列、変数保持エンティティによりオブジェクトの各属性値を設定します。
	 * 
	 * @param item バーコードオブジェクト
	 * @param cmd ＥＡＮ.ＵＣＣ合成シンボル指定のSBPLコマンド
	 * @param dto 変数保持エンティティ
	 */
	public static void setProperties(CompositeSymbol item, SBPLCommand cmd, SBPLConvertDto dto)
	{
		// 出力順番号
		item.setSeqNo(dto.getItemSeqNo()) ;
		// 横印字位置
		item.setHPosition(dto.getHPosition()) ;
		// 縦印字位置
		item.setVPosition(dto.getVPosition()) ;
		// 回転方向
		item.setRotation(dto.getRotation()) ;
		// 文字間ピッチ
		item.setPitch(dto.getPitch()) ;
		// 連番指定パラメータ�A��
		item.setSequencePara(dto.getSequencePara()) ;
		//１次元バーコード種
		item.setOneDimensionBarType(cmd.getParameters().substring(0, 2)) ;
		//最小バー幅
		item.setMinBarWidth(Integer.parseInt(cmd.getParameters().substring(2, 4))) ;
		if (cmd.getParameters().substring(0, 2).equals("11")
				|| cmd.getParameters().substring(0, 2).equals("12"))
		{
			//バーコード天地
			item.setBarTopLength(Integer.parseInt(cmd.getParameters().substring(4, 7))) ;
			item.setValue(cmd.getParameters().substring(7)) ;
		}
		else
		{
			// 印字データ
			item.setValue(cmd.getParameters().substring(4)) ;
		}
		// 繰返しフラグ
		item.setRepeatFlg(dto.getRepeatFlg()) ;
		// 可変項目のフィールドID
		item.setFieldID(dto.getFieldID()) ;
		clearDtoValue(dto) ;
	}

	/**
	 * ＥＡＮ.ＵＣＣ合成シンボルオブジェクトをSBPLコマンド文字列に転換します。
	 * 
	 * @param compositeSymbolitem ＥＡＮ.ＵＣＣ合成シンボルオブジェクト
	 * @return SBPL文字列
	 */
	public static String toSBPLString(CompositeSymbol compositeSymbolitem)
	{
		StringBuffer buf = new StringBuffer() ;

		buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ROTATION)
				+ compositeSymbolitem.getRotation()) ;
		if (!StringUtil.isEmpty(compositeSymbolitem.getVPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_V_POSITION)
					+ compositeSymbolitem.getVPosition()) ;
		}

		if (!StringUtil.isEmpty(compositeSymbolitem.getHPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_H_POSITION)
					+ compositeSymbolitem.getHPosition()) ;
		}
		if (!StringUtil.isEmpty(compositeSymbolitem.getPitch()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PITCH)
					+ compositeSymbolitem.getPitch()) ;
		}
		if (!StringUtil.isEmpty(compositeSymbolitem.getSequencePara()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_SEQUENCE_PARA)
					+ compositeSymbolitem.getSequencePara()) ;
		}

		buf.append(SBPLCommandHandler.getInstance().getCommandName(
				LabelConstants.CMD_COMPOSITE_SYMBOL)) ;
		buf.append(compositeSymbolitem.getOneDimensionBarType()) ;
		buf.append(StringUtil.paddingLeft(String.valueOf(compositeSymbolitem.getMinBarWidth()),
				'0', 2)) ;
		if (compositeSymbolitem.getBarTopLength() != 0)
		{
			buf.append(StringUtil.paddingLeft(
					String.valueOf(compositeSymbolitem.getBarTopLength()), '0', 3)) ;
		}
		buf.append(compositeSymbolitem.getValue()) ;

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
		dto.setSequencePara("") ;
		dto.setRepeatFlg(LabelConstants.FLAG_OFF) ;
		dto.setFieldID("") ;
	}
}
