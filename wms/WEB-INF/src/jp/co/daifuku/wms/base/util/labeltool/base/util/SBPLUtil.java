// $$Id: SBPLUtil.java 7996 2011-07-06 00:52:24Z kitamaki $$
package jp.co.daifuku.wms.base.util.labeltool.base.util ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException ;
import java.util.ArrayList ;
import java.util.Hashtable ;
import java.util.Map ;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommand ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommandHandler ;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException ;
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
import jp.co.daifuku.wms.label.xmlbeans.Ruler ;

import org.apache.xmlbeans.XmlObject ;


/**
 * SBPL転換用共通クラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/17</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  chenjun
 * @author  Last commit: $Author: kitamaki $
 */
public class SBPLUtil
{

	/**
	 * コマンド名がフォントかを判断するメソッドです。
	 * 
	 * @param cmdName コマンド名
	 * @return フォントの場合、trueを返します。<br>
	 *         フォントではない場合、falseを返します。
	 */
	public static boolean isFont(String cmdName)
	{
		if (!StringUtil.isEmpty(cmdName))
		{
			if (cmdName.equals(LabelConstants.CMD_FONTTYPE_K1S)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K2S)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K3S)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_K4S)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_K5S)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_K8S)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_K9S)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_KAS)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_KBS)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_KDS)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K1)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K2)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K3)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_K4)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K5)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K8)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K9)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_KA)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_KB)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_KD)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_X23)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_X24)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_X20)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_X21)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_X22)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_WB)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_WL)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_OA)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_OB)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_U)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_S)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_M)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_XU)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_XS)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_XM)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_XB)
                    || cmdName.equals(LabelConstants.CMD_FONTTYPE_XL)
					|| cmdName.equals(LabelConstants.CMD_FONTTYPE_OUTLINE))
			{
				return true ;
			}
		}
		return false ;
	}

	/**
	 * コマンド名が漢字フォントかを判断するメソッドです。
	 * 
	 * @param cmdName コマンド名
	 * @return 漢字フォントの場合、trueを返します。<br>
	 *         漢字フォントではない場合、falseを返します。
	 */
	public static boolean isKanjiFont(String cmdName)
	{
		if (cmdName.equals(LabelConstants.CMD_FONTTYPE_K1S)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K2S)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K3S)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_K4S)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_K5S)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K1)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K2)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K3)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_K4)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_K5))
		{
			return true ;
		}
		return false ;
	}

	/**
	 * コマンド名が混在文字かを判断するメソッドです。
	 * 
	 * @param cmdName コマンド名
	 * @return 混在文字の場合、trueを返します。<br>
	 *         混在文字ではない場合、falseを返します。
	 */
	public static boolean isMixedFont(String cmdName)
	{
		if (cmdName.equals(LabelConstants.CMD_FONTTYPE_K8S)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K9S)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_KAS)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_KBS)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_KDS)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K8)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_K9)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_KA)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_KB)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_KD))
		{
			return true ;
		}
		return false ;
	}

	/**
	 * コマンド名がOCRフォントかを判断するメソッドです。
	 * 
	 * @param cmdName コマンド名
	 * @return OCRフォントの場合、trueを返します。<br>
	 *         OCRフォントではない場合、falseを返します。
	 */
	public static boolean isOCRFont(String cmdName)
	{
		if (cmdName.equals(LabelConstants.CMD_FONTTYPE_OA)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_OB))
		{
			return true ;
		}
		return false ;
	}

    /**
     * 海外プリンタ用フォントかを判断するメソッドです。
     * 
     * @param cmdName コマンド名
     * @return 海外プリンタ用フォントの場合、trueを返します。<br>
     *         海外プリンタ用ではない場合、falseを返します。
     */
    public static boolean isOtherCountryFont(String cmdName)
    {
        if (cmdName.equals(LabelConstants.CMD_FONTTYPE_U)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_S)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_M)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_XU)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_XS)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_XM))
        {
            return true ;
        }
        return false ;
    }
    
    /**
     * 海外プリンタ用Smoothingフォントかを判断するメソッドです。
     * 
     * @param cmdName コマンド名
     * @return 海外プリンタ用フォントの場合、trueを返します。<br>
     *         海外プリンタ用ではない場合、falseを返します。
     */
    public static boolean isOtherXSmoothingFont(String cmdName)
    {
        if (cmdName.equals(LabelConstants.CMD_FONTTYPE_XL)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_XB)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_WB)
                || cmdName.equals(LabelConstants.CMD_FONTTYPE_WL))
        {
            return true ;
        }
        return false ;
    }
    
	/**
	 * コマンド名がSmoothingフォントかを判断するメソッドです。
	 * 
	 * @param cmdName コマンド名
	 * @return Smoothingフォントの場合、trueを返します。<br>
	 *         Smoothingフォントではない場合、falseを返します。
	 */
	public static boolean isXSmoothingFont(String cmdName)
	{
		if (cmdName.equals(LabelConstants.CMD_FONTTYPE_X23)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_X24))
		{
			return true ;
		}
		return false ;
	}

	/**
	 * コマンド名がスムージング指定がなしかを判断するメソッドです。
	 * 
	 * @param cmdName コマンド名
	 * @return スムージング指定がある場合、trueを返します。<br>
	 *         スムージング指定がない場合、falseを返します。
	 */
	public static boolean isXNoneSmoothingFont(String cmdName)
	{
		if (cmdName.equals(LabelConstants.CMD_FONTTYPE_X22)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_X21)
				|| cmdName.equals(LabelConstants.CMD_FONTTYPE_X20))
		{
			return true ;
		}
		return false ;
	}

	/**
	 * コマンド名が羅線かを判断するメソッドです。
	 * 
	 * @param cmd SBPLコマンド
	 * @return 羅線の場合、trueを返します。<br>
	 *         羅線ではない場合、falseを返します。
	 */
	public static boolean isRuler(SBPLCommand cmd)
	{
		if (!StringUtil.isEmpty(cmd.getName()))
		{
			if (cmd.getName().equals(LabelConstants.CMD_RULER_FRAME))
			{
				if (cmd.getParameters().indexOf(LabelConstants.CMD_V_POSITION) == -1
						|| cmd.getParameters().indexOf(LabelConstants.CMD_H_POSITION) == -1)
				{
					return true ;
				}
			}
		}
		return false ;
	}

	/**
	 * コマンド名が枠線かを判断するメソッドです。
	 * 
	 * @param cmd SBPLコマンド
	 * @return 枠線の場合、trueを返します。<br>
	 *         枠線ではない場合、falseを返します。
	 */
	public static boolean isFrame(SBPLCommand cmd)
	{
		if (!StringUtil.isEmpty(cmd.getName()))
		{
			if (cmd.getName().equals(LabelConstants.CMD_RULER_FRAME))
			{
				if (cmd.getParameters().indexOf(LabelConstants.CMD_V_POSITION) != -1
						&& cmd.getParameters().indexOf(LabelConstants.CMD_H_POSITION) != -1)
				{
					return true ;
				}
			}
		}
		return false ;
	}

	/**
	 * コマンド名がバーコードかを判断するメソッドです。
	 * 
	 * @param cmdName SBPLコマンド名
	 * @return バーコードの場合、trueを返します。<br>
	 *         バーコードではない場合、falseを返します。
	 */
	public static boolean isBarCode(String cmdName)
	{

		if (!StringUtil.isEmpty(cmdName))
		{
			if (cmdName.equals(LabelConstants.CMD_BARCODE_BC)
					|| cmdName.equals(LabelConstants.CMD_BARCODE_BG)
					|| cmdName.equals(LabelConstants.CMD_BARCODE_BI)
					|| cmdName.equals(LabelConstants.CMD_BARCODE_BF)
					|| cmdName.equals(LabelConstants.CMD_BARCODE_B)
					|| cmdName.equals(LabelConstants.CMD_BARCODE_D)
					|| cmdName.equals(LabelConstants.CMD_BARCODE_BD)
					|| cmdName.equals(LabelConstants.CMD_BARCODE_BW))
			{
				return true ;
			}

		}
		return false ;
	}

	/**
	 * コマンド名がQRコードかを判断するメソッドです。
	 * 
	 * @param cmdName SBPLコマンド名
     * @return QRコードの場合、trueを返します。<br>
     *         QRコードではない場合、falseを返します。
	 */
	public static boolean isQRCode(String cmdName)
	{
        if (LabelConstants.CMD_QRCODE_2D30.equals(cmdName)
                || LabelConstants.CMD_QRCODE_2D31.equals(cmdName)
                || LabelConstants.CMD_QRCODE_2D32.equals(cmdName))
        {
            return true;
        }
	    return false;
	}
	
	/**
	 * 指定コマンド文字列をコマンド名とパラメータを分解し、SBPLCommandを返します。<br>
	 * 
	 * @param str コマンド文字列
	 * @return SBPLCommandオブジェクト
	 * @throws DaiException 異常が発生した場合
	 */
	public static SBPLCommand findCommand(String str)
			throws DaiException
	{
		String nameStr = null ;
		SBPLCommand cmd = null ;
		String nameKey = null ;
		if (str != null && !str.equals(""))
		{
		    if (str.length() > 3)
		    {
		        nameKey = str.substring(0, 4);
		        nameStr = SBPLCommandHandler.getInstance().getCommandName(nameKey);
		        if (!StringUtil.isEmpty(nameStr))
		        {
		            cmd = new SBPLCommand();
		            cmd.setName(nameKey);
		            cmd.setParameters(str.substring(4, str.length()));
		            return cmd;
		        }
		    }
			if (str.length() > 2)
			{
				nameKey = str.substring(0, 3) ;
				nameStr = SBPLCommandHandler.getInstance().getCommandName(nameKey) ;
				if (!StringUtil.isEmpty(nameStr))
				{
					cmd = new SBPLCommand() ;
					cmd.setName(nameKey) ;
					cmd.setParameters(str.substring(3, str.length())) ;
					return cmd ;
				}
			}
			if (str.length() > 1)
			{
				nameKey = str.substring(0, 2) ;
				nameStr = SBPLCommandHandler.getInstance().getCommandName(nameKey) ;
				if (!StringUtil.isEmpty(nameStr))
				{
					cmd = new SBPLCommand() ;
					cmd.setName(nameKey) ;
					cmd.setParameters(str.substring(2, str.length())) ;
					return cmd ;
				}
			}
			if (str.length() > 0)
			{
				nameKey = str.substring(0, 1) ;
				nameStr = SBPLCommandHandler.getInstance().getCommandName(nameKey) ;
				if (!StringUtil.isEmpty(nameStr))
				{
					cmd = new SBPLCommand() ;
					cmd.setName(nameKey) ;
					cmd.setParameters(str.substring(1, str.length())) ;
					return cmd ;
				}
			}
		}
		return cmd ;
	}

	/**
	 * 対象データから指定ページ番号のデータを抽出するメソッドです。
	 * 
	 * @param data 対象データ 
	 * @param pageNum ページ番号
	 * @param recordCount １ページのデータカウント数
	 * @return 指定ページ番号のデータ
	 */
	public static Map[] getPageData(Map[] data, int pageNum, int recordCount)
	{
		ArrayList<Map> dataList = new ArrayList<Map>() ;
		if (pageNum * recordCount > data.length)
		{
			for (int i = (pageNum - 1) * recordCount; i < data.length; i++)
			{
				dataList.add(data[i]) ;
			}
		}
		else
		{
			for (int i = (pageNum - 1) * recordCount; i < pageNum * recordCount; i++)
			{
				dataList.add(data[i]) ;
			}
		}
		Map[] pageData = new Map[dataList.size()] ;
		for (int i = 0; i < dataList.size(); i++)
		{
			pageData[i] = dataList.get(i) ;
		}
		return pageData ;
	}

	/**
	 * JIS文字列をShift-JIS文字列に転換するメソッドです。
	 * 
	 * @param value JIS文字列
	 * @return ShiftJIS文字列
	 * @throws DaiException 異常が発生した場合
	 */
	public static String jisToShiftJIS(String value)
			throws DaiException
	{
		String prefix = new String(new byte[] {
				0x1b,
				0x24,
				0x42
		}) ;
		String suffix = new String(new byte[] {
				0x1b,
				0x28,
				0x42
		}) ;
		String tempStr = prefix + value + suffix ;
		try
		{
			tempStr = new String(tempStr.getBytes("MS932"), "MS932") ;
		}
		catch (UnsupportedEncodingException e)
		{
			return "" ;
		}
		return tempStr ;

	}

	/**
	 * アイテムの中に全てのエレメントをSeqNoをキーとしてHashtableに保持するメソッドです。<br>
	 * 
	 * @param item アイテム
	 * @return Hashtable
	 */
	public static Hashtable<Integer, XmlObject> getAllElements(Item item)
	{
		Hashtable<Integer, XmlObject> elements = new Hashtable<Integer, XmlObject>() ;
		Font[] fonts = item.getFontArray() ;
		if (fonts != null)
		{
			for (int i = 0; i < fonts.length; i++)
			{
				elements.put(fonts[i].getSeqNo(), fonts[i]) ;
			}
		}
		Ruler[] rulers = item.getRulerArray() ;
		if (fonts != null)
		{
			for (int i = 0; i < rulers.length; i++)
			{
				elements.put(rulers[i].getSeqNo(), rulers[i]) ;
			}
		}
		Frame[] frames = item.getFrameArray() ;
		if (frames != null)
		{
			for (int i = 0; i < frames.length; i++)
			{
				elements.put(frames[i].getSeqNo(), frames[i]) ;
			}
		}
		BarCode[] barCodes = item.getBarCodeArray() ;
		if (barCodes != null)
		{
			for (int i = 0; i < barCodes.length; i++)
			{
				elements.put(barCodes[i].getSeqNo(), barCodes[i]) ;
			}
		}
		BWInversion[] bWInversions = item.getBWInversionArray() ;
		if (bWInversions != null)
		{
			for (int i = 0; i < bWInversions.length; i++)
			{
				elements.put(bWInversions[i].getSeqNo(), bWInversions[i]) ;
			}
		}
		FormCaller[] formCallers = item.getFormCallerArray() ;
		if (formCallers != null)
		{
			for (int i = 0; i < formCallers.length; i++)
			{
				elements.put(formCallers[i].getSeqNo(), formCallers[i]) ;
			}
		}
		PartCopy[] partCopies = item.getPartCopyArray() ;
		if (partCopies != null)
		{
			for (int i = 0; i < partCopies.length; i++)
			{
				elements.put(partCopies[i].getSeqNo(), partCopies[i]) ;
			}
		}
		CompositeSymbol[] compositeSymbols = item.getCompositeSymbolArray() ;
		if (compositeSymbols != null)
		{
			for (int i = 0; i < compositeSymbols.length; i++)
			{
				elements.put(compositeSymbols[i].getSeqNo(), compositeSymbols[i]) ;
			}
		}
		BarCodeRatioSpec[] barCodeRatioSpecs = item.getBarCodeRatioSpecArray() ;
		if (barCodeRatioSpecs != null)
		{
			for (int i = 0; i < barCodeRatioSpecs.length; i++)
			{
				elements.put(barCodeRatioSpecs[i].getSeqNo(), barCodeRatioSpecs[i]) ;
			}
		}
		QRCode[] qrCodes = item.getQRCodeArray();
		if (qrCodes != null)
		{
		    for (int i = 0; i < qrCodes.length; i ++)
		    {
		        elements.put(qrCodes[i].getSeqNo(), qrCodes[i]);
		    }
		}
		return elements ;
	}

	/**
	 * データの中に、指定項目IDの値を集計する。(整数)
	 * @param data データ
	 * @param fieldID 項目ID
	 * @return 集計値
	 */
	public static int getGrandTotal(Map[] data, String fieldID)
	{
		int value = 0 ;
		if (data == null)
		{
			return 0 ;
		}
		for (int i = 0; i < data.length; i++)
		{
			if (data[i].get(fieldID) != null)
			{
				value += Integer.parseInt((String)data[i].get(fieldID)) ;
			}
		}
		return value ;
	}


}
