// $Id: SBPLLabelConvertManager.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.module.sato.parse ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File ;
import java.io.IOException ;

import jp.co.daifuku.wms.base.util.labeltool.base.MLOcxDispatcher ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.EnvDefHandler ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.FileManager ;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException ;
import jp.co.daifuku.wms.label.xmlbeans.DAILabelDocument ;
import jp.co.daifuku.wms.label.xmlbeans.DAILabelDocument.DAILabel ;

import org.apache.xmlbeans.XmlOptions ;

import com.jacob.com.ComThread ;


/**
 * 佐藤のラベル製作ツールで作ったレイアウトファイルを<br>
 * 標準的なXML定義情報に転換するクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/15</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SBPLLabelConvertManager
{

	/**
	 * レイアウトファイルからモデルオブジェクトに転換します。
	 * @param layoutFileName レイアウトファイル名
	 * @return モデルオブジェクト
	 * @throws DaiException 異常が発生した場合
	 */
	public DAILabelDocument convertLayoutToDoc(String layoutFileName)
			throws DaiException
	{
		// レイアウトファイルをコマンドファイルに転換する。
		if (!exportLayoutToCmd(layoutFileName))
		{
			throw new DaiException("ERR0018") ;
		}

		// 生成したコマンドファイルをXML定義ファイルを転換する。
		String tempCmdFile = EnvDefHandler.getInstance().getDefineValue("cmdPath") ;
		return parseCommandFile(tempCmdFile) ;
	}


	/**
	 * レイアウトファイルをXML定義ファイルに転換します。
	 * 
	 * @param layoutFileName レイアウトファイル名
	 * @param xmlFileName XML定義ファイル名
	 * @throws DaiException 異常が発生した場合
	 */
	public void convertLayoutToXmlFile(String layoutFileName, String xmlFileName)
			throws DaiException
	{
		// レイアウトファイルをコマンドファイルに転換する。
		if (!exportLayoutToCmd(layoutFileName))
		{
			throw new DaiException("ERR0018") ;
		}

		// 生成したコマンドファイルをXML定義ファイルを転換する。
		String tempCmdFile = EnvDefHandler.getInstance().getDefineValue("cmdPath") ;
		parseCommandFileToXmlFile(tempCmdFile, xmlFileName) ;

	}

	/**
	 * データエンティティファイルを生成します。
	 * 
	 * @param xmlFileName XML定義ファイル名
	 * @param entityName エンティティクラス名
	 * @throws DaiException 異常が発生した場合
	 */
	public void generateEntity(String xmlFileName, String entityName)
			throws DaiException
	{
		EntityWriter writer = new EntityWriter() ;
		writer.generateEntityFile(xmlFileName, entityName) ;
	}

	/**
	 * コマンドファイルをXML定義オブジェクトに転換するメソッドです。
	 * 
	 * @param commandFileName コマンドファイル名
	 * @return レイアウトモデルオブジェクト
	 * @throws DaiException 異常が発生した場合
	 */
	public DAILabelDocument parseCommandFile(String commandFileName)
			throws DaiException
	{
		DAILabelDocument doc = DAILabelDocument.Factory.newInstance() ;
		SBPLCommandFile handler = new SBPLCommandFile(commandFileName) ;
		// コマンドファイルからアイテム文字列配列を取得する。
		String[] items = handler.getItemStrings() ;
		if (items.length == 0)
		{
			return null ;
		}
		doc.getDAILabel() ;
		DAILabel labelObj = doc.addNewDAILabel() ;
		SBPLCommandParser parser = new SBPLCommandParser(labelObj) ;

		for (int i = 0; i < items.length; i++)
		{
			// アイテムの分析を行う
			parser.parseItem(items[i], i) ;
		}
		FileManager.deleteFile(commandFileName) ;
		return doc ;
	}

	/**
	 * コマンドファイルをXML定義ファイルに転換するメソッドです。
	 * 
	 * @param commandFileName コマンドファイル名
	 * @param xmlFileName XML定義ファイル名
	 * @throws DaiException 異常が発生した場合
	 */
	public void parseCommandFileToXmlFile(String commandFileName, String xmlFileName)
			throws DaiException
	{
		DAILabelDocument doc = DAILabelDocument.Factory.newInstance() ;
		doc = parseCommandFile(commandFileName) ;
		saveToXml(doc, xmlFileName) ;
	}

	/**
	 * マルチラベルリストOCXを利用し、<br>
	 * レイアウトファイルからコマンドファイルを生成します。
	 * 
	 * @param layoutFile レイアウトファイル
	 * @return true:　 正常<br>
	 *         false: 失敗<br>
	 */
	public boolean exportLayoutToCmd(String layoutFile)
	{
		MLOcxDispatcher ocx ;
		ComThread.InitSTA() ;
		try
		{
			ocx = new MLOcxDispatcher() ;
			ocx.setLayoutFile(layoutFile) ;
			ocx.setPrnPath(EnvDefHandler.getInstance().getDefineValue("prnPath")) ;
			ocx.setSetting(EnvDefHandler.getInstance().getDefineValue("printDriver")) ;
			ocx.setDarkness("S") ;
			ocx.setExOutputAckCheck(true) ;
			ocx.setMemoryCard(false) ;
			ocx.setJobName("") ;
			ocx.setLayoutNameCaption("") ;
			ocx.setMultiCut(0) ;
			ocx.setOffset("0.0000,0.0000") ;
			ocx.setProtocol(0) ;
			ocx.setSiwake(false) ;
			ocx.setSpeed("S") ;
			ocx.setStatusID(0) ;
			ocx.setTimeout(3) ;
			ocx.setTotalQtyCaption(0) ;
			ocx.setCOMMode(0) ;
			ocx.setFormoverlay(0) ;
			ocx.setOutCut(2) ;
			if (ocx.openPort(1) != 0)
			{
				return false ;
			}
			if (ocx.output() != 0)
			{
				return false ;
			}
			if (ocx.closePort() != 0)
			{
				return false ;
			}

			String tempCmdFile = EnvDefHandler.getInstance().getDefineValue("cmdPath") ;
			int retrycnt = 0 ;
			while (true)
			{
				File file = new File(tempCmdFile) ;
				if (!file.exists())
				{
					if (retrycnt == 3)
					{
						throw new Exception() ;
					}
					retrycnt++ ;
					try
					{
						Thread.sleep(100) ;
					}
					catch (InterruptedException e)
					{
					}
					continue ;
				}
				else
				{
					try
					{
						Thread.sleep(500) ;
					}
					catch (InterruptedException e)
					{
						throw new DaiException() ;
					}
					break ;
				}
			}
		}
		catch (Exception e)
		{
			return false ;
		}
		finally
		{
			ComThread.Release() ;
		}
		return true ;
	}

	/**
	 * ラベルレイアウトモデルオブジェクトをXMLファイルに保存するメソッドです。
	 * 
	 * @param doc ラベルレイアウトモデルオブジェクト
	 * @param xmlFileName XMLファイル名
	 * @throws DaiException 異常が発生した場合
	 */
	public void saveToXml(DAILabelDocument doc, String xmlFileName)
			throws DaiException
	{
		File f = new File(xmlFileName) ;
		if (!f.getParentFile().exists())
		{
			f.getParentFile().mkdir() ;
		}
		File sbplXmlFile = new File(xmlFileName) ;

		XmlOptions xmlOptions = new XmlOptions() ;
		xmlOptions.setCharacterEncoding("UTF-8") ;
		xmlOptions.setSavePrettyPrint() ;
		try
		{
			doc.save(sbplXmlFile, xmlOptions) ;
		}
		catch (IOException e)
		{
			throw new DaiException("ERR0004", e) ;
		}
	}
}
