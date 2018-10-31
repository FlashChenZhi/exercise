//$Id: IdLayout.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common.tool.logViewer;

/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Designer :  <BR>
 * Maker :   <BR>
 * <BR>
 * <pre>
 * ID項目設定ファイルクラス<BR>
 * XML形式のID項目設定ファイル定義クラス<BR>
 * </pre>
 * <BR>
 * <BR>
 * XML形式のID項目設定ファイルから情報を取得(<CODE>GetIdColumns()</CODE>メソッド)<BR>
 * <DIR>
 * XML形式ファイルからID項目情報を取得し<BR>
 * 電文情報クラス(ColumnInfo)に電文情報を格納します。<BR>
 * <BR>
 * <DIR>
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class IdLayout 
{
	/**
	 * ID定義ファイルのデフォルトのファイル名
	 */
	protected final static String IdLayoutFileName = "id.xml";

	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $";
	}

	/**
	 * XMLドキュメント
	 */
	protected static Document doc = null;
	
	/**
	 * XML形式のID定義ファイルを読み込み、DOMツリーを生成します。<BR>
	 * 一度DOMツリーを生成すると、アプリケーションの終了まで保持し続けます。
	 * 
	 * @throws ParserConfigurationException		パーサの初期化エラー発生時に通知されます。
	 * @throws SAXException		SAXエラーが発生した場合に通知されます。
	 * @throws IOException		I/Oエラーが発生した場合に通知されます。
	 * @throws Exception 	全ての例外を報告します。 
	 */
	public static void load() throws ParserConfigurationException, SAXException, IOException, Exception
	{
		if (doc == null)
		{
            // XMLが読み込まれていない場合のみ読み込む
			FileInputStream oStream = null;
			
			try
			{
				// ドキュメントファクトリを生成
				DocumentBuilderFactory oFactory = DocumentBuilderFactory.newInstance();
				// ドキュメントビルダを生成
				DocumentBuilder oBuilder = oFactory.newDocumentBuilder();
				
				// DOMのオブジェクトを取得
				oStream = new FileInputStream(LogViewerParam.ConfPath + "\\" + LogViewerParam.IDFileName );
				doc = oBuilder.parse(oStream);
			}
			finally
			{
				if (oStream != null)
				{
					oStream.close();
				}
			}
		}
	}
	
	/**
	 * id.xmlファイルから名前、長さ、コメントと取得後、
	 * 引き渡された電文を分解し
	 * 電文項目情報クラスに行単位で格納します。<BR>
	 * 行単位で格納したデータを
	 * 電文情報クラスに格納し返します。
	 * 
	 * @param record 電文情報
	 * @param Id id.xmlファイルの情報
	 * @return IdData[] 電文情報クラス
	 * @throws Exception 全ての例外を報告します。
	 */
	public IdData[] getIdColumns (byte[] record, String Id) throws Exception
	{
		// 電文項目データクラス
		ColumnInfo colinfolist[] = null;

		// 電文情報クラス
		IdData idData[] = null;

        try
		{
        	// XMLファイルを読み込む
        	load();

			// ルート要素を得る
			Element root = doc.getDocumentElement();

			// 指定IDの要素のリストを取得
			String elementName = "id" + Id;
			NodeList list = root.getElementsByTagName(elementName);

			// 指定IDの要素を取得
			Element element = (Element) list.item(0);

			// item要素のリストを取得
			NodeList paramList = element.getElementsByTagName("item");

			// item要素の数、電文項目情報を配列で宣言
			colinfolist = new ColumnInfo[paramList.getLength()];
			
			// item要素の数、電文情報を配列で宣言
			idData = new IdData[paramList.getLength()];

			// 電文分解用開始位置
			int start = 0;

			// item要素の数だけループ
			for (int j = 0; j < paramList.getLength(); j ++) 
			{
			    // item要素を取得
			    Element paramElement = (Element)paramList.item(j);

			    // XML情報保持
			    ColumnInfo colinfo = new ColumnInfo();

			    // 電文情報保持
			    IdData data = new IdData();

				// name属性の値を取得
				colinfo.setName(paramElement.getAttribute("name"));

				// 子ノード（コメント）を取得
				if (paramList.item(j).getFirstChild() == null)
				{
					// nullの場合は、""をセットする。
					colinfo.setComment ("");
				}
				else
				{
					// null以外の場合は子ノード（コメント）を取得
					colinfo.setComment (paramList.item(j).getFirstChild().getNodeValue());
				}

				// 分解する電文の長さを取得
				int length = Integer.parseInt(paramElement.getAttribute("size"));

				// 電文を分解し指定項目内用を取得
				colinfo.setValue(checkChar(record, start, length));
				
				// 電文項目情報クラスに各情報を格納
				colinfolist[j] = colinfo;

				// 電文情報クラスに行単位情報を格納
				data.setTelegramData(colinfolist[j]);

				idData[j] = data;

				start += length;
			}
        }
	    catch (ParserConfigurationException e)
		{
            String msg = MessageResourceFile.getText("6006003");
            throw new Exception(msg);
        }
	    catch (SAXException e)
		{
            String msg = MessageResourceFile.getText("6006002");
            throw new Exception(msg);
        }
        catch (IOException e)
		{
            //I/Oエラーが発生
            String param[] = new String[1];
            param[0] = LogViewerParam.IDFileName;
            String msg = MessageResourceFile.getText("6006020");
            msg = MessageFormat.format(msg, param);
            throw new Exception(msg);
        }
        catch (NullPointerException e)
        {
            String param[] = new String[1];
            param[0] = "id" + Id;
            String msg = MessageResourceFile.getText("6006004");
            msg = MessageFormat.format(msg, param);
            throw new Exception(msg);
        }

        return idData;
    }

	/**
	 * 電文の1byteずつ全角・半角かを調べます。<BR>
	 * 全角だった場合はtelegramCountをカウントアップし
	 * 次の電文分解用に保持します。<BR>
	 * 分解した電文を返します。<BR>
	 * 
	 * @param record 電文
	 * @param offset 開始位置
	 * @param length 終了位置
	 * @return String 切り分け済み電文
	 */
	public String checkChar(byte[] record, int offset, int length)
	{
		byte[] buf = new byte[length];
		for (int i = 0; i < length; i ++)
		{
			if (offset + i >= record.length)
			{
				buf[i] = 0x20;
			}
			else
			{
	            buf[i] = record[offset + i];
			}
		}
		return new String(buf);
	}
}