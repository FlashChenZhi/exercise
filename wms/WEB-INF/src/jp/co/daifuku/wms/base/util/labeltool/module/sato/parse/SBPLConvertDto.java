// $$Id: SBPLConvertDto.java 2583 2009-01-08 00:20:09Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.module.sato.parse ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.ArrayList ;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants ;

/**
 * SBPL分析の間に、変数保持用データエンティティです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/20</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SBPLConvertDto
{
	/** <code>可変印字項目のフィールドIDを格納する変数</code> */
	private ArrayList<String> fieldList = new ArrayList<String>() ;

	/** <code>出力順番号</code> */
	private int itemSeqNo = 0 ;

	/** <code>印字横位置</code> */
	private String hPosition = "" ;

	/** <code>印字縦位置</code> */
	private String vPosition = "" ;

	/** <code>回転方向</code> */
	private String rotation = LabelConstants.ROTATION_PARRALLEL1 ;

	/** <code>文字間ピッチ</code> */
	private String pitch = "" ;

	/** <code>行間ピッチ幅</code> */
	private String linePitch = "" ;

	/** <code>アウトラインフォント形状指定パラメータ</code> */
	private String outlineFontSpec = LabelConstants.DEFAULT_OUTLINE_FONT_SPEC ;

	/** <code>横方向拡大倍率</code> */
	private int hEnlargeRatio = LabelConstants.DEFAULT_H_ENLARGE_RATIO ;

	/** <code>縦方向拡大倍率</code> */
	private int vEnlargeRatio = LabelConstants.DEFAULT_V_ENLARGE_RATIO ;

	/** <code>ピッチモード</code>のコメント */
	private String pitchMode = LabelConstants.PITCH_MODE_FIX ;

	/** <code>連番指定パラメータ</code> */
	private String sequencePara = "" ;

	/** <code>漢字コード</code> */
	private String kanjiCode = LabelConstants.KANJI_CODE_JIS ;

	/** <code>繰返しフラグ</code> */
	private String repeatFlg = LabelConstants.FLAG_OFF ;

	/** <code>可変項目のフィールドID</code> */
	private String fieldID = "" ;

	/** <code>集計モード</code> */
	private String aggregateMode = "" ;

	/** <code>解説文字種</code> */
	private String charType = "" ;

	/** <code>ページ表示フラグ</code> */
	private String pageFlg = LabelConstants.FLAG_OFF ;

	/** <code>ページ表示形式</code> */
	private String pageFormat = LabelConstants.PAGE_FMT_NUMBER_ONLY ;

	/**
	 * @return aggregateModeを返します。
	 */
	public String getAggregateMode()
	{
		return aggregateMode ;
	}

	/**
	 * @param aggregateMode aggregateModeを設定します。
	 */
	public void setAggregateMode(String aggregateMode)
	{
		this.aggregateMode = aggregateMode ;
	}

	/**
	 * @return charTypeを返します。
	 */
	public String getCharType()
	{
		return charType ;
	}

	/**
	 * @param charType charTypeを設定します。
	 */
	public void setCharType(String charType)
	{
		this.charType = charType ;
	}

	/**
	 * @return fieldIDを返します。
	 */
	public String getFieldID()
	{
		return fieldID ;
	}

	/**
	 * @param fieldID fieldIDを設定します。
	 */
	public void setFieldID(String fieldID)
	{
		this.fieldID = fieldID ;
	}

	/**
	 * @return fieldListを返します。
	 */
	public ArrayList<String> getFieldList()
	{
		return fieldList ;
	}

	/**
	 * @param fieldList fieldListを設定します。
	 */
	public void setFieldList(ArrayList<String> fieldList)
	{
		this.fieldList = fieldList ;
	}

	/**
	 * @return hEnlargeRatioを返します。
	 */
	public int getHEnlargeRatio()
	{
		return hEnlargeRatio ;
	}

	/**
	 * @param enlargeRatio hEnlargeRatioを設定します。
	 */
	public void setHEnlargeRatio(int enlargeRatio)
	{
		hEnlargeRatio = enlargeRatio ;
	}

	/**
	 * @return hPositionを返します。
	 */
	public String getHPosition()
	{
		return hPosition ;
	}

	/**
	 * @param position hPositionを設定します。
	 */
	public void setHPosition(String position)
	{
		hPosition = position ;
	}

	/**
	 * @return kanjiCodeを返します。
	 */
	public String getKanjiCode()
	{
		return kanjiCode ;
	}

	/**
	 * @param kanjiCode kanjiCodeを設定します。
	 */
	public void setKanjiCode(String kanjiCode)
	{
		this.kanjiCode = kanjiCode ;
	}

	/**
	 * @return linePitchを返します。
	 */
	public String getLinePitch()
	{
		return linePitch ;
	}

	/**
	 * @param linePitch linePitchを設定します。
	 */
	public void setLinePitch(String linePitch)
	{
		this.linePitch = linePitch ;
	}

	/**
	 * @return outlineFontSpecを返します。
	 */
	public String getOutlineFontSpec()
	{
		return outlineFontSpec ;
	}

	/**
	 * @param outlineFontSpec outlineFontSpecを設定します。
	 */
	public void setOutlineFontSpec(String outlineFontSpec)
	{
		this.outlineFontSpec = outlineFontSpec ;
	}

	/**
	 * @return pageFlgを返します。
	 */
	public String getPageFlg()
	{
		return pageFlg ;
	}

	/**
	 * @param pageFlg pageFlgを設定します。
	 */
	public void setPageFlg(String pageFlg)
	{
		this.pageFlg = pageFlg ;
	}

	/**
	 * @return pitchを返します。
	 */
	public String getPitch()
	{
		return pitch ;
	}

	/**
	 * @param pitch pitchを設定します。
	 */
	public void setPitch(String pitch)
	{
		this.pitch = pitch ;
	}

	/**
	 * @return pitchModeを返します。
	 */
	public String getPitchMode()
	{
		return pitchMode ;
	}

	/**
	 * @param pitchMode pitchModeを設定します。
	 */
	public void setPitchMode(String pitchMode)
	{
		this.pitchMode = pitchMode ;
	}

	/**
	 * @return repeatFlgを返します。
	 */
	public String getRepeatFlg()
	{
		return repeatFlg ;
	}

	/**
	 * @param repeatFlg repeatFlgを設定します。
	 */
	public void setRepeatFlg(String repeatFlg)
	{
		this.repeatFlg = repeatFlg ;
	}

	/**
	 * @return rotationを返します。
	 */
	public String getRotation()
	{
		return rotation ;
	}

	/**
	 * @param rotation rotationを設定します。
	 */
	public void setRotation(String rotation)
	{
		this.rotation = rotation ;
	}

	/**
	 * @return sequenceParaを返します。
	 */
	public String getSequencePara()
	{
		return sequencePara ;
	}

	/**
	 * @param sequencePara sequenceParaを設定します。
	 */
	public void setSequencePara(String sequencePara)
	{
		this.sequencePara = sequencePara ;
	}

	/**
	 * @return vEnlargeRatioを返します。
	 */
	public int getVEnlargeRatio()
	{
		return vEnlargeRatio ;
	}

	/**
	 * @param enlargeRatio vEnlargeRatioを設定します。
	 */
	public void setVEnlargeRatio(int enlargeRatio)
	{
		vEnlargeRatio = enlargeRatio ;
	}

	/**
	 * @return vPositionを返します。
	 */
	public String getVPosition()
	{
		return vPosition ;
	}

	/**
	 * @param position vPositionを設定します。
	 */
	public void setVPosition(String position)
	{
		vPosition = position ;
	}

	/**
	 * @return itemSeqNoを返します。
	 */
	public int getItemSeqNo()
	{
		return itemSeqNo ;
	}

	/**
	 * @param itemSeqNo itemSeqNoを設定します。
	 */
	public void setItemSeqNo(int itemSeqNo)
	{
		this.itemSeqNo = itemSeqNo ;
	}

	/**
	 * @return pageFormatを返します。
	 */
	public String getPageFormat()
	{
		return pageFormat ;
	}

	/**
	 * @param pageFormat pageFormatを設定します。
	 */
	public void setPageFormat(String pageFormat)
	{
		this.pageFormat = pageFormat ;
	}

}
