// $$Id: SBPLBarCodeConverter.java 2583 2009-01-08 00:20:09Z kumano $$
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
import jp.co.daifuku.wms.label.xmlbeans.BarCode ;


/**
 * バーコードに関する転換処理のクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/25</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SBPLBarCodeConverter
{
	/**
	 * コマンド文字列、変数保持エンティティによりバーコードの各属性値を設定します。
	 * 
	 * @param item バーコードオブジェクト
	 * @param cmd フォントのSBPLコマンド
	 * @param dto 変数保持エンティティ
	 */
	public static void setProperties(BarCode item, SBPLCommand cmd, SBPLConvertDto dto)
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
		// 連番指定パラメータ
		item.setSequencePara(dto.getSequencePara()) ;
		// バーコード種
		item.setBarcodeType(cmd.getName()) ;
		item.setRepeatFlg(dto.getRepeatFlg()) ;
		item.setFieldID(dto.getFieldID()) ;
		if (cmd.getName().equals(LabelConstants.CMD_BARCODE_BW))
		{
			item.setThinBarWidth(Integer.parseInt(cmd.getParameters().substring(0, 2))) ;
			item.setBarTopLength(Integer.parseInt(cmd.getParameters().substring(2, 5))) ;
			item.setValue(cmd.getParameters().substring(5)) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_BARCODE_B)
				|| cmd.getName().equals(LabelConstants.CMD_BARCODE_D)
				|| cmd.getName().equals(LabelConstants.CMD_BARCODE_BD))
		{
			item.setRatio(cmd.getParameters().substring(0, 1)) ;
			item.setThinBarWidth(Integer.parseInt(cmd.getParameters().substring(1, 3))) ;
			item.setBarTopLength(Integer.parseInt(cmd.getParameters().substring(3, 6))) ;
			item.setValue(cmd.getParameters().substring(6)) ;
			if (cmd.getName().equals(LabelConstants.CMD_BARCODE_D))
			{
				item.setCharType(dto.getCharType()) ;
			}
		}
		if (cmd.getName().equals(LabelConstants.CMD_BARCODE_BI))
		{
			item.setThinBarWidth(Integer.parseInt(cmd.getParameters().substring(0, 2))) ;
			item.setBarTopLength(Integer.parseInt(cmd.getParameters().substring(2, 5))) ;
			item.setFontDiscriptFlag(cmd.getParameters().substring(5, 6)) ;
			item.setValue(cmd.getParameters().substring(6)) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_BARCODE_BC))
		{
			item.setThinBarWidth(Integer.parseInt(cmd.getParameters().substring(0, 2))) ;
			item.setBarTopLength(Integer.parseInt(cmd.getParameters().substring(2, 5))) ;
			item.setDataDigit(Integer.parseInt(cmd.getParameters().substring(5, 7))) ;
			item.setValue(cmd.getParameters().substring(7)) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_BARCODE_BG))
		{
			item.setThinBarWidth(Integer.parseInt(cmd.getParameters().substring(0, 2))) ;
			item.setBarTopLength(Integer.parseInt(cmd.getParameters().substring(2, 5))) ;
			// START CODEを設定する。
			if (!cmd.getParameters().substring(5, 6).equals(LabelConstants.START_CODE_PREFIX))
			{
				item.setStartCode(LabelConstants.START_CODE_H) ;
				item.setValue(cmd.getParameters().substring(5)) ;
			}
			else
			{
				item.setStartCode(cmd.getParameters().substring(6, 7)) ;
				item.setValue(cmd.getParameters().substring(7)) ;
			}
		}
		if (cmd.getName().equals(LabelConstants.CMD_BARCODE_BF))
		{
			item.setThinBarWidth(Integer.parseInt(cmd.getParameters().substring(0, 2))) ;
			item.setBarTopLength(Integer.parseInt(cmd.getParameters().substring(2, 5))) ;
			item.setValue(cmd.getParameters().substring(6)) ;
		}
		clearDtoValue(dto) ;
	}

	/**
	 * バーコードオブジェクトをSBPLコマンド文字列に転換します。
	 * 
	 * @param barCodeitem バーコードオブジェクト 
	 * @return SBPLコマンド文字列
	 */
	public static String toSBPLString(BarCode barCodeitem)
	{
		StringBuffer buf = new StringBuffer() ;

		buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ROTATION)
				+ barCodeitem.getRotation()) ;
		if (!StringUtil.isEmpty(barCodeitem.getVPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_V_POSITION)
					+ barCodeitem.getVPosition()) ;
		}
		if (!StringUtil.isEmpty(barCodeitem.getHPosition()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_H_POSITION)
					+ barCodeitem.getHPosition()) ;
		}
		if (!StringUtil.isEmpty(barCodeitem.getPitch()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PITCH)
					+ barCodeitem.getPitch()) ;
		}
		if (!StringUtil.isEmpty(barCodeitem.getSequencePara()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(
					LabelConstants.CMD_SEQUENCE_PARA)
					+ barCodeitem.getSequencePara()) ;
		}
		if (LabelConstants.CMD_BARCODE_B.equals(barCodeitem.getBarcodeType())
				|| LabelConstants.CMD_BARCODE_D.equals(barCodeitem.getBarcodeType())
				|| LabelConstants.CMD_BARCODE_BD.equals(barCodeitem.getBarcodeType()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(barCodeitem.getBarcodeType())) ;
			buf.append(barCodeitem.getRatio()) ;
			buf.append(StringUtil.paddingLeft(String.valueOf(barCodeitem.getThinBarWidth()), '0', 2)) ;
			buf.append(StringUtil.paddingLeft(String.valueOf(barCodeitem.getBarTopLength()), '0', 3)) ;

			buf.append(barCodeitem.getValue()) ;
			if (!StringUtil.isEmpty(barCodeitem.getCharType()))
			{
				buf.append(SBPLCommandHandler.getInstance().getCommandName(
						barCodeitem.getCharType())
						+ LabelConstants.COMMA_STR
						+ barCodeitem.getValue()) ;
			}
		}
		if (LabelConstants.CMD_BARCODE_BW.equals(barCodeitem.getBarcodeType()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(barCodeitem.getBarcodeType())) ;
			buf.append(StringUtil.paddingLeft(String.valueOf(barCodeitem.getThinBarWidth()), '0', 2)) ;
			buf.append(StringUtil.paddingLeft(String.valueOf(barCodeitem.getBarTopLength()), '0', 3)) ;
			buf.append(barCodeitem.getValue()) ;
		}
		if (LabelConstants.CMD_BARCODE_BI.equals(barCodeitem.getBarcodeType())
				|| LabelConstants.CMD_BARCODE_BC.equals(barCodeitem.getBarcodeType())
				|| LabelConstants.CMD_BARCODE_BG.equals(barCodeitem.getBarcodeType())
				|| LabelConstants.CMD_BARCODE_BF.equals(barCodeitem.getBarcodeType()))
		{
			buf.append(SBPLCommandHandler.getInstance().getCommandName(barCodeitem.getBarcodeType())) ;
			buf.append(StringUtil.paddingLeft(String.valueOf(barCodeitem.getThinBarWidth()), '0', 2)) ;
			buf.append(StringUtil.paddingLeft(String.valueOf(barCodeitem.getBarTopLength()), '0', 3)) ;
			if (!StringUtil.isEmpty(barCodeitem.getFontDiscriptFlag()))
			{
				buf.append(barCodeitem.getFontDiscriptFlag()) ;
			}
			else if (barCodeitem.getDataDigit() != 0)
			{
				buf.append(StringUtil.paddingLeft(String.valueOf(barCodeitem.getDataDigit()), '0',
						2)) ;
			}
			else if (!StringUtil.isEmpty(barCodeitem.getStartCode()))
			{
				buf.append(LabelConstants.START_CODE_PREFIX + barCodeitem.getStartCode()) ;
			}
			buf.append(barCodeitem.getValue()) ;
		}
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
		dto.setCharType("") ;
		dto.setRepeatFlg(LabelConstants.FLAG_OFF) ;
		dto.setFieldID("") ;
	}
}
