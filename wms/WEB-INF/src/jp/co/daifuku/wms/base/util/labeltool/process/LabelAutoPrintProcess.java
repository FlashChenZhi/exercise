// $Id: LabelAutoPrintProcess.java 2583 2009-01-08 00:20:09Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.process ;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException ;
import java.sql.Connection ;
import java.sql.SQLException ;
import java.util.Enumeration ;
import java.util.HashMap ;
import java.util.Hashtable ;
import java.util.List ;
import java.util.Map ;

import jp.co.daifuku.wms.base.common.WmsParam ;
import jp.co.daifuku.wms.base.util.labeltool.LabelParam ;
import jp.co.daifuku.wms.base.util.labeltool.base.DBHandler ;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.DataMappingInfoHandler ;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.print.SBPLLabelPrintManager ;
import jp.co.daifuku.wms.base.util.labeltool.module.sato.print.SatoLanPrintAdapter ;
import jp.co.daifuku.wms.label.xmlbeans.Label ;

import org.apache.xmlbeans.XmlException ;


/**
 * ラベル自動発行を行うクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/07/16</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2583 $, $Date: 2009-01-08 09:20:09 +0900 (木, 08 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class LabelAutoPrintProcess
{
	/**
	 * 入口メソッドです。
	 * @param args パラメータ文字列
	 * @throws IOException 異常が発生した場合
	 * @throws DaiException 異常が発生した場合
	 * @throws XmlException 異常が発生した場合 
	 */
	public static void main(String[] args)
			throws XmlException,
				DaiException
	{
		//System.setProperty("user.dir", LabelConstants.USR_DIR);

		LabelAutoPrintProcess printManager = new LabelAutoPrintProcess() ;

		Connection conn = null ;

		try
		{
			conn = WmsParam.getConnection() ;

			DBHandler dbhandler = new DBHandler(conn) ;

			// ＤＢ関連情報を取得する。
			Label[] infos = DataMappingInfoHandler.getMappingInfoList() ;
			if (infos != null)
			{
				for (int i = 0; i < infos.length; i++)
				{
					// 自動発行が無効の場合、スキップする。
					if (infos[i].getDisableFlag().equals("1"))
					{
						continue ;
					}
					else
					{
						// 指定テーブルからデータを取得
						List resultData = printManager.fetchData(infos[i].getTableName(), dbhandler) ;
						if (resultData != null && resultData.size() > 0)
						{
							// ラベルの印刷を行う
							String status = printManager.printLabel(infos[i].getId(), resultData) ;
							if (SatoLanPrintAdapter.$STATUS_ACK.equals(status))
							{
								// 印刷成功した場合に、印刷済みフラグを立つ。
								printManager.modPrintFlg(infos[i].getTableName(), dbhandler) ;
							}
							if (SatoLanPrintAdapter.STATUS_OK.equals(status))
							{
								// 印刷成功した場合に、印刷済みフラグを立つ。
								printManager.modPrintFlg(infos[i].getTableName(), dbhandler) ;
							}
						}
					}
				}
			}
		}
		catch (SQLException es)
		{
			es.printStackTrace() ;
		}
		catch (IOException e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close() ;
				}
			}
			catch (SQLException ex)
			{
				ex.printStackTrace() ;
			}
		}
	}

	/**
	 * 指定テーブルから印刷待のデータを取得します。
	 * 
	 * @param tableName テーブル名
	 * @return データリスト
	 */
	private List fetchData(String tableName, DBHandler dbhandler)
	{
		String sql = "SELECT * FROM " + tableName + " WHERE PrintFlg = '0'" ;

		return dbhandler.executeQuery(sql) ;
	}

	/**
	 * 取得したDBデータを指定管理Noのラベルで印刷します。
	 * 
	 * @param id ラベル管理No
	 * @param dbData DBから取得されたデータ
	 * @return 返送ステータスコード
	 * @throws XmlException 異常が発生した場合
	 * @throws IOException 異常が発生した場合
	 * @throws DaiException 異常が発生した場合
	 */
	private String printLabel(String id, List dbData)
			throws XmlException,
				IOException,
				DaiException
	{

		// DBデータをラベル印刷用データ形式に転換する。
		Map[] dataMaps = convertDBData(id, dbData) ;

		// ラベル定義XMlファイル名を取得する。
		SatoLanPrintAdapter printer = new SatoLanPrintAdapter() ;
		printer.setAddress(LabelParam.PRINTER_IP_ADRESS) ;
		SBPLLabelPrintManager m = new SBPLLabelPrintManager() ;
		return m.printLabel(printer, id, dataMaps) ;
	}

	/**
	 * DBデータをラベル印刷用データの形式に転換します。
	 * 
	 * @param id 管理No
	 * @param dbData DBから取得されたデータ
	 * @return ラベル印刷用データ
	 * @throws XmlException 異常が発生した場合
	 * @throws IOException 異常が発生した場合
	 */
	private Map[] convertDBData(String id, List dbData)
			throws XmlException,
				IOException
	{
		Map[] printData = new Map[dbData.size()] ;

		for (int i = 0; i < dbData.size(); i++)
		{
			Map dbRecord = (Map)dbData.get(i) ;

			Hashtable mappingInfo = DataMappingInfoHandler.getMapingInfo(id) ;
			Enumeration b = mappingInfo.keys() ;
			printData[i] = new HashMap<String, String>() ;
			while (b.hasMoreElements())
			{
				String key = (String)b.nextElement() ;
				String printItem_Name = (String)mappingInfo.get(key) ;
				printData[i].put(key, (String)dbRecord.get(printItem_Name)) ;
				//printData[i].put(key.toUpperCase(), (String)dbRecord.get(printItem_Name));
			}

		}
		return printData ;
	}

	/**
	 * 印刷済みフラグを立ちます。
	 * 
	 * @param tableName テーブル名
	 */
	private void modPrintFlg(String tableName, DBHandler dbhandler)
	{
		String sql = "UPDATE " + tableName + " set PRINTFLG = '1' WHERE PRINTFLG = '0'" ;
		dbhandler.executeUpdate(sql) ;
	}
}
