// $$Id: SBPLCommandParser.java 6565 2009-12-18 10:28:04Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.module.sato.parse ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import java.util.ArrayList ;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommand ;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.SBPLUtil ;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLBWInversionConverter ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLBarCodeConverter ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLBarCodeRatioSpecConverter ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLCompositeSymbolConverter ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLFontConverter ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLFormCallerConverter ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLFrameConverter ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLPartCopyConverter ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLQRCodeConverter;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.convert.SBPLRulerConverter ;
import jp.co.daifuku.wms.label.xmlbeans.BWInversion ;
import jp.co.daifuku.wms.label.xmlbeans.BarCode ;
import jp.co.daifuku.wms.label.xmlbeans.BarCodeRatioSpec ;
import jp.co.daifuku.wms.label.xmlbeans.CompositeSymbol ;
import jp.co.daifuku.wms.label.xmlbeans.Font ;
import jp.co.daifuku.wms.label.xmlbeans.FormCaller ;
import jp.co.daifuku.wms.label.xmlbeans.Frame ;
import jp.co.daifuku.wms.label.xmlbeans.Item ;
import jp.co.daifuku.wms.label.xmlbeans.PartCopy ;
import jp.co.daifuku.wms.label.xmlbeans.QRCode;
import jp.co.daifuku.wms.label.xmlbeans.RepeatDef ;
import jp.co.daifuku.wms.label.xmlbeans.Ruler ;
import jp.co.daifuku.wms.label.xmlbeans.Variable ;
import jp.co.daifuku.wms.label.xmlbeans.DAILabelDocument.DAILabel ;


/**
 * ラベルのSBPLコマンド文字列をオブジェクトに転換するクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/23</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 6565 $, $Date: 2009-12-18 19:28:04 +0900 (金, 18 12 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SBPLCommandParser
{
	/** <code>解析の間に変数を保持するエンティティ</code> */
	private SBPLConvertDto dto = new SBPLConvertDto() ;

	/** <code>ラベルオブジェクト</code> */
	private DAILabel label ;

	/**
	 * このクラスのコンストラクタです。
	 * 
	 * @param label ラベルオブジェクト
	 */
	public SBPLCommandParser(DAILabel label)
	{
		this.label = label ;
	}

	/**
	 * アイテム単位の文字列をSBPLコマンドリストに分割します。
	 * 
	 * @param itemStr アイテム単位の文字列
	 * @return SBPLコマンドリスト
	 * @throws DaiException 異常が発生した場合
	 */
	private ArrayList<SBPLCommand> splitIntoCommands(String itemStr)
			throws DaiException
	{
		String tempStr = null ;
		ArrayList<SBPLCommand> commands = new ArrayList<SBPLCommand>() ;
		if (!itemStr.startsWith(LabelConstants.ESC + "A"))
		{
			throw new DaiException("ERR0023") ;
		}
		else
		{
			// 先頭文字ESC+Aを除く
			tempStr = itemStr.substring(2) ;
		}

		if (!tempStr.endsWith(LabelConstants.ESC + "Z"))
		{
			throw new DaiException("ERR0023") ;
		}
		else
		{
			// 末端文字ESC+Zを除く
			tempStr = tempStr.substring(0, tempStr.length() - 2) ;
		}
		String cmdStrs[] = tempStr.split(LabelConstants.ESC) ;

		if (cmdStrs.length > 0)
		{
			for (int i = 0; i < cmdStrs.length; i++)
			{
				String cmdStr = cmdStrs[i].trim() ;
				if (!cmdStr.equals(""))
				{
					SBPLCommand cmd = SBPLUtil.findCommand(cmdStr) ;
					if (cmd != null)
					{
						commands.add(cmd) ;
					}
					else
					{
						// 識別不可の印字項目が存在する場合に、エラーとする。
						throw new DaiException("ERR0040") ;
					}
				}
			}
		}
		return commands ;
	}

	/**
	 * アイテム単位のコマンド文字列をオブジェクトに転換するメソッドです。<br>
	 * 
	 * @param itemStr アイテム単位のコマンド文字列
	 * @param seqNo 印字出力順番
	 * @throws DaiException 異常が発生した場合
	 */
	public void parseItem(String itemStr, int seqNo)
			throws DaiException
	{
		SBPLCommand cmd = null ;

		// アイテム文字列をコマンドアレーにを分解する
		ArrayList<SBPLCommand> commands ;
		try
		{
			commands = splitIntoCommands(itemStr) ;
			Item item = label.addNewItem() ;
			item.setFormRegFlag(LabelConstants.FLAG_OFF) ;
			item.setPartEditFlag(LabelConstants.FLAG_OFF) ;
			for (int i = 0; i < commands.size(); i++)
			{
				cmd = commands.get(i) ;
				// アイテムの属性を設定する。
				setItemProperty(item, cmd) ;
				// 変数を保持する。
				setDtoValue(item, cmd) ;
				if (cmd.getName().equals(LabelConstants.CMD_REPEAT_DEF))
				{
					// カスタマイズする繰返し定義体のパラメータを分析する。
					parseDSSDef(cmd.getParameters()) ;
				}
				if (cmd.getName().equals(LabelConstants.CMD_IST))
				{
					// カスタマイズするISTコマンドのパラメータを分析する。
					parseISTCommand(cmd.getParameters()) ;
				}
				// フォントを分析する。
				if (SBPLUtil.isFont(cmd.getName()))
				{
					Font fontItem = item.addNewFont() ;
					// フォントの属性を解析し設定する。
					SBPLFontConverter.setProperties(fontItem, cmd, dto) ;
					dto.setItemSeqNo(dto.getItemSeqNo() + 1) ;
				}
				// 羅線を分析する。
				if (SBPLUtil.isRuler(cmd))
				{
					Ruler rulerItem = item.addNewRuler() ;
					SBPLRulerConverter.setProperties(rulerItem, cmd, dto) ;
					dto.setItemSeqNo(dto.getItemSeqNo() + 1) ;
				}
				// 枠線を分析する。
				if (SBPLUtil.isFrame(cmd))
				{
					Frame frameItem = item.addNewFrame() ;
					SBPLFrameConverter.setProperties(frameItem, cmd, dto) ;
					dto.setItemSeqNo(dto.getItemSeqNo() + 1) ;
				}
				// 黒白反転印字を分析する。
				if (cmd.getName().equals(LabelConstants.CMD_BW_INVERSION))
				{
					BWInversion bwItem = item.addNewBWInversion() ;
					SBPLBWInversionConverter.setProperties(bwItem, cmd, dto) ;
					dto.setItemSeqNo(dto.getItemSeqNo() + 1) ;
				}
				// 部分コピー指定を分析する。
				if (cmd.getName().equals(LabelConstants.CMD_PART_COPY))
				{
					PartCopy copyItem = item.addNewPartCopy() ;
					SBPLPartCopyConverter.setProperties(copyItem, cmd, dto) ;
					dto.setItemSeqNo(dto.getItemSeqNo() + 1) ;
				}
				// フォームオーバレイ呼出指定を分析する。
				if (cmd.getName().equals(LabelConstants.CMD_FORM_CALLER))
				{
					FormCaller fmItem = item.addNewFormCaller() ;
					SBPLFormCallerConverter.setProperties(fmItem, cmd, dto) ;
					dto.setItemSeqNo(dto.getItemSeqNo() + 1) ;
				}
				// バーコード比率登録指定を分析する。
				if (cmd.getName().equals(LabelConstants.CMD_BAR_CODE_BT))
				{
					BarCodeRatioSpec barRatioSpec = item.addNewBarCodeRatioSpec() ;
					SBPLBarCodeRatioSpecConverter.setProperties(barRatioSpec, cmd, dto) ;
					dto.setItemSeqNo(dto.getItemSeqNo() + 1) ;
				}
				// バーコードを分析する。
				if (SBPLUtil.isBarCode(cmd.getName()))
				{
					// 解説文字があるかを判断する。
					if (cmd.getName().equals(LabelConstants.CMD_BARCODE_D)
							&& SBPLUtil.isFont(commands.get(i + 1).getName()))
					{
						dto.setCharType(commands.get(i + 1).getName()) ;
						// 次の文字種指定コマンドをスキップする。
						i++ ;
					}
					BarCode barCodeItem = item.addNewBarCode() ;
					SBPLBarCodeConverter.setProperties(barCodeItem, cmd, dto) ;
					dto.setItemSeqNo(dto.getItemSeqNo() + 1) ;
				}
				// EAN、UCC合成シンボルを分析する。
				if (cmd.getName().equals(LabelConstants.CMD_COMPOSITE_SYMBOL))
				{
					CompositeSymbol symbol = item.addNewCompositeSymbol() ;
					SBPLCompositeSymbolConverter.setProperties(symbol, cmd, dto) ;
					dto.setItemSeqNo(dto.getItemSeqNo() + 1) ;
				}
				// QRコードを分析する。
				if (SBPLUtil.isQRCode(cmd.getName()))
				{
				    QRCode qrCodeItem = item.addNewQRCode();
				    SBPLQRCodeConverter.setProperties(qrCodeItem, cmd, commands.get(i + 1), dto);
				    dto.setItemSeqNo(dto.getItemSeqNo() + 1);
				    // 次のQRコードデータコマンドをスキップする。
				    i ++;
				}
			}
			// アイテムの出力順番号をセットする。
			item.setSeqNo(seqNo) ;
		}
		catch (DaiException e)
		{
			throw e ;
		}
		catch (NumberFormatException e)
		{
			throw new DaiException("ERR0023", e) ;
		}
	}

	/**
	 * コマンド文字列の内容により、アイテムの属性を設定します。
	 * 
	 * @param item ラベルアイテム
	 * @param cmd SBPLコマンド
	 */
	private void setItemProperty(Item item, SBPLCommand cmd)
	{
		if (cmd.getName().equals(LabelConstants.CMD_PRINT_NUMBER))
		{
			// 印刷枚数をセットする
			item.setPrintNumber(Integer.parseInt(cmd.getParameters())) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_JOB_ID))
		{
			// ジョブID番号をセットする
			item.setJobID(cmd.getParameters()) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_JOB_NAME))
		{
			// ジョブ名称をセットする
			item.setJobName(cmd.getParameters()) ;
		}
		// 部分編集フラグをセットする
		if (cmd.getName().equals(LabelConstants.CMD_PART_EDIT_FLAG))
		{
			item.setPartEditFlag(LabelConstants.FLAG_ON) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_SIZE_SR))
		{
			// 用紙サイズをセットする
			item.setVSize(Integer.parseInt(cmd.getParameters().substring(0, 5))) ;
			item.setHSize(Integer.parseInt(cmd.getParameters().substring(6))) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_SIZE))
		{
			// 用紙サイズをセットする
			item.setVSize(Integer.parseInt(cmd.getParameters().substring(0, 4))) ;
			item.setHSize(Integer.parseInt(cmd.getParameters().substring(4))) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_FORM_REG))
		{
			// フォームオーバレー登録指定フラグをセットする
			item.setFormRegFlag(LabelConstants.FLAG_ON) ;
			item.setRegisterKey(cmd.getParameters().substring(0, 2)) ;
		}
	}

	/**
	 * コマンド文字列の内容により、修飾など属性をDtoに退避します。
	 * 
	 * @param item ラベルアイテム
	 * @param cmd SBPLコマンド
	 * @throws DaiException 異常が発生した場合
	 */
	private void setDtoValue(Item item, SBPLCommand cmd)
			throws DaiException
	{
		// 縦印字位置を保持する
		if (cmd.getName().equals(LabelConstants.CMD_V_POSITION))
		{
			dto.setVPosition(cmd.getParameters()) ;
		}
		// 横印字位置を保持する
		if (cmd.getName().equals(LabelConstants.CMD_H_POSITION))
		{
			dto.setHPosition(cmd.getParameters()) ;
		}
		// 回転方向を保持する
		if (cmd.getName().equals(LabelConstants.CMD_ROTATION))
		{
			dto.setRotation(cmd.getParameters()) ;
		}
		// 文字間ピッチを保持する
		if (cmd.getName().equals(LabelConstants.CMD_PITCH))
		{
			dto.setPitch(cmd.getParameters()) ;
		}
		// 拡大倍率を保持する
		if (cmd.getName().equals(LabelConstants.CMD_ENLARGE_RATIO))
		{
			dto.setHEnlargeRatio(Integer.parseInt(cmd.getParameters().substring(0, 2))) ;
			dto.setVEnlargeRatio(Integer.parseInt(cmd.getParameters().substring(2, 4))) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_PROP_PITCH_MODE_START))
		{
			// ピッチモードをプロポーショナルに保持する
			dto.setPitchMode(LabelConstants.PITCH_MODE_PROP) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_PROP_PITCH_MODE_END))
		{
			// ピッチモードを固定に保持する
			dto.setPitchMode(LabelConstants.PITCH_MODE_FIX) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_SEQUENCE_PARA))
		{
			// 連番指定文字列をセットする
			dto.setSequencePara(cmd.getParameters()) ;
		}
		if (cmd.getName().equals(LabelConstants.CMD_KANJI_CODE))
		{
			// 漢字コードをセットする
			dto.setKanjiCode(cmd.getParameters()) ;
		}
		// 行間ピッチ幅
		if (cmd.getName().equals(LabelConstants.CMD_LINE_PITCH))
		{
			dto.setLinePitch(cmd.getParameters()) ;
		}
		// アウトラインフォント形状指定
		if (cmd.getName().equals(LabelConstants.CMD_OTL_FONT_SHAPE_SPEC))
		{
			dto.setOutlineFontSpec(cmd.getParameters()) ;
		}
		// ページ表示指定（カスタマイズ）
		if (cmd.getName().equals(LabelConstants.CMD_PG))
		{
			// カスタマイズするPGコマンドのパラメータを分析する。
			parsePGCommand(cmd.getParameters()) ;
		}
	}

	/**
	 * 繰返定義体文字列をオブジェクトに転換するメソッドです。<br>
	 * 
	 * @param value 文字列
	 * @throws DaiException 異常が発生した場合
	 */
	private void parseDSSDef(String value)
			throws DaiException
	{
		String[] params = value.split(LabelConstants.COMMA_STR) ;

		// パラメータ数が4でない場合、エラーとする。
		if (params.length != 4)
		{
			// ERR0042=DSSコマンドの引数に誤りがあります。
			throw new DaiException("ERR0042") ;
		}

		for (int i = 0; i < params.length; i++)
		{
			// パラメータの値が数字ではない場合、エラーとする。
			if (!StringUtil.isNumber(params[i]))
			{
				// ERR0042=DSSコマンドの引数に誤りがあります。
				throw new DaiException("ERR0042") ;
			}
		}

		// 繰り返し件数は1以上とする
		if (Integer.valueOf(params[1]) < 1)
		{
			// ERR0042=DSSコマンドの引数に誤りがあります。
			throw new DaiException("ERR0042") ;
		}

		RepeatDef rdf = label.addNewRepeatDef() ;
		rdf.setMaxCounts(Integer.parseInt(params[1])) ;
		rdf.setHOffset(Integer.parseInt(params[2])) ;
		rdf.setVOffset(Integer.parseInt(params[3])) ;
	}

	/**
	 * カスタマイズするISTコマンドのパラメータを分析します。
	 * 
	 * @param value カスタマイズするISTコマンドのパラメータ文字列
	 * @throws DaiException 異常が発生した場合
	 */
	private void parseISTCommand(String value)
			throws DaiException
	{
		String[] params = value.split(LabelConstants.COMMA_STR) ;

		// 引数の数が正しくない場合、エラーとする。
		if (params.length != 4)
		{
			// ERR0043=ISTコマンドの引数に誤りがあります。
			throw new DaiException("ERR0043") ;
		}

		// 第1引数チェック(繰り返しフラグ）
		if (params[1].equals(LabelConstants.REPERT_ON))
		{
			// 繰り返しON
			dto.setRepeatFlg(LabelConstants.REPERT_ON) ;
		}
		else if (params[1].equals(LabelConstants.REPERT_OFF))
		{
			// 繰り返しOFF
			dto.setRepeatFlg(LabelConstants.REPERT_OFF) ;
		}
		else
		{
			// それ以外
			// ERR0043=ISTコマンドの引数に誤りがあります。
			throw new DaiException("ERR0043") ;
		}


		// 第2引数チェック(項目名称）
		dto.setFieldID(params[2]) ;
		// 可変数の追加
		if (!StringUtil.isEmpty(params[2]))
		{
			// 禁則文字チェックを行う。
			if (!StringUtil.validateVariableName(params[2]))
			{
				throw new DaiException("ERR0036") ;
			}
			// 新規可変数の場合、追加する。
			if (!dto.getFieldList().contains(params[2]))
			{
				Variable variableItem = label.addNewVariable() ;
				variableItem.setRepeatFlg(dto.getRepeatFlg()) ;
				variableItem.setFieldId(dto.getFieldID()) ;
			}
			dto.getFieldList().add(params[2]) ;
		}

		// 第3引数チェック（集合モード）
		if (params[3].equals(LabelConstants.AGGREGATE_MODE_COUNT))
		{
			dto.setAggregateMode(LabelConstants.AGGREGATE_MODE_COUNT) ;
		}
		else if (params[3].equals(LabelConstants.AGGREGATE_MODE_SUM))
		{
			dto.setAggregateMode(LabelConstants.AGGREGATE_MODE_SUM) ;
		}
		else if (params[3].equals(LabelConstants.AGGREGATE_MODE_NO))
		{
			dto.setAggregateMode(LabelConstants.AGGREGATE_MODE_NO) ;
		}
		else
		{
			// それ以外
			// ERR0043=ISTコマンドの引数に誤りがあります。
			throw new DaiException("ERR0043") ;
		}

		// 繰り返しフラグと集合モードの関係チェック
		if (LabelConstants.REPERT_ON.equals(params[1])
				&& (LabelConstants.AGGREGATE_MODE_COUNT.equals(params[3]) || LabelConstants.AGGREGATE_MODE_SUM.equals(params[3])))
		{
			// ERR0044=ISTコマンドの引数の整合性に誤りがあります。
			throw new DaiException("ERR0044") ;
		}

	}

	/**
	 * カスタマイズするPGコマンドのパラメータを分析します。
	 * 
	 * @param value カスタマイズするISTコマンドのパラメータ文字列
	 * @throws DaiException 異常が発生した場合
	 */
	private void parsePGCommand(String value)
			throws DaiException
	{
		String[] params = value.split(LabelConstants.COMMA_STR) ;

		// 引数の数が正しくない場合、エラーとする。
		if (params.length != 2)
		{
			// ERR0045=PGコマンドの引数に誤りがあります。
			throw new DaiException("ERR0045") ;
		}

		dto.setPageFlg(LabelConstants.FLAG_ON) ;

		// 第1引数チェック
		if (LabelConstants.PAGE_FMT_FULL.equals(params[1]))
		{
			dto.setPageFormat(LabelConstants.PAGE_FMT_FULL) ;
		}
		else if (LabelConstants.PAGE_FMT_NUMBER_ONLY.equals(params[1]))
		{
			dto.setPageFormat(LabelConstants.PAGE_FMT_NUMBER_ONLY) ;
		}
		else
		{
			// ERR0045=PGコマンドの引数に誤りがあります。
			throw new DaiException("ERR0045") ;
		}
	}
}
