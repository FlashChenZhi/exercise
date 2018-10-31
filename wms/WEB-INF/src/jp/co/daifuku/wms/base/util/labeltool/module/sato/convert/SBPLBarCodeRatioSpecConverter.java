// $$Id: SBPLBarCodeRatioSpecConverter.java 2583 2009-01-08 00:20:09Z kumano $$
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
import jp.co.daifuku.wms.label.xmlbeans.BarCodeRatioSpec ;

/**
 * バーコード比率登録指定オブジェクトに関する転換処理のクラスです。
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
public class SBPLBarCodeRatioSpecConverter
{

	/**
	 * コマンド文字列、変数保持エンティティによりオブジェクトの各属性値を設定します。
	 * 
	 * @param item バーコードオブジェクト
	 * @param cmd フォントのSBPLコマンド
	 * @param dto 変数保持エンティティ
	 */
	public static void setProperties(BarCodeRatioSpec item, SBPLCommand cmd, SBPLConvertDto dto)
	{
		// 出力順番号
		item.setSeqNo(dto.getItemSeqNo()) ;
		// バーコード種
		item.setBarCodeType(cmd.getParameters().substring(0, 1)) ;
		item.setNarrowSpace(Integer.parseInt(cmd.getParameters().substring(1, 3))) ;
		item.setWideSpace(Integer.parseInt(cmd.getParameters().substring(3, 5))) ;
		item.setNarrowBar(Integer.parseInt(cmd.getParameters().substring(5, 7))) ;
		item.setWideBar(Integer.parseInt(cmd.getParameters().substring(7))) ;
	}

	/**
	 * バーコード比率登録指定オブジェクトをSBPLコマンド文字列に転換します。
	 * 
	 * @param barCodeRatioSpecItem バーコード比率登録指定オブジェクト
	 * @return SBPL文字列
	 */
	public static String toSBPLString(BarCodeRatioSpec barCodeRatioSpecItem)
	{
		StringBuffer buf = new StringBuffer() ;
		buf.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_BAR_CODE_BT)
				+ barCodeRatioSpecItem.getBarCodeType()
				+ StringUtil.paddingLeft(String.valueOf(barCodeRatioSpecItem.getNarrowSpace()),
						'0', 2)
				+ StringUtil.paddingLeft(String.valueOf(barCodeRatioSpecItem.getWideSpace()), '0',
						2)
				+ StringUtil.paddingLeft(String.valueOf(barCodeRatioSpecItem.getNarrowBar()), '0',
						2)
				+ StringUtil.paddingLeft(String.valueOf(barCodeRatioSpecItem.getWideBar()), '0', 2)) ;
		return buf.toString() ;
	}
}
