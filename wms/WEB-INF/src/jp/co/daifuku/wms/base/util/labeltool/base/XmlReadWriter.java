// $Id: XmlReadWriter.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * XML操作クラス<br>
 * XMLファイルに対するインターフェースを提供するクラス。<br>
 * ファイルからデータを取得したり、データをセットします。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2007/04/11</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class XmlReadWriter
{
    /** <code>タグ名の定数</code> */
    private static final String CODE = "code";

    /** <code>タグ名の定数</code> */
    private static final String COMMENT = "comment";

    /** <code>タグ名の定数</code> */
    private static final String DATA = "data";

    /** <code>タグ名の定数</code> */
    private static final String ROOT = "root";

    /** <code>タグ名の定数</code> */
    private static final String VALUE = "value";

    /**
     * このメソドはXMLファイルを新規作成します。
     * 
     * @param fileName ファイルパスとファイル名称
     * @param encoding 作成するXMLファイルのエンコーディング。
     * @throws ParserConfigurationException XMLの処理に失敗したとき
     * @throws TransformerException XMLの処理に失敗したとき
     */
    public static void createXmlFile(String fileName, String encoding)
            throws ParserConfigurationException,
                TransformerException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        builder = factory.newDocumentBuilder();
        Document xmlDoc = builder.newDocument();
        xmlDoc.setXmlVersion("1.0");
        xmlDoc.setXmlStandalone(true);
        xmlDoc.appendChild(xmlDoc.createElement(ROOT));
        saveXmlFile(xmlDoc, fileName, encoding);
    }

    /**
     * このメソッドはXMLファイル読んで値をHashtableに返す。
     * 
     * @param fileName XMLファイル名とパス
     * @return XMLファイルに有る"CODE"エレメントと"VALUE"エレメントの値をHashtableに返す。
     * @throws SAXException XMLの処理に失敗したとき
     * @throws ParserConfigurationException XMLの処理に失敗したとき
     * @throws IOException XMLの処理に失敗したとき
     */
    public static Hashtable<String, String> readAllXmlContents(String fileName)
            throws SAXException,
                ParserConfigurationException,
                IOException
    {
        Hashtable<String, String> xmlValues = new Hashtable<String, String>();
        String strCode = null;
        String strValue = null;
        // XMLファイルを読み込みます。
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document xd = builder.parse(fileName);
        // "data" エレメントのノードリストを取得する。
        NodeList xnl = xd.getElementsByTagName(DATA);
        for (int i = 0; i < xnl.getLength(); i++)
        {
            // 子ノードの値を取得する。
            Node node = xnl.item(i);
            strCode = ((Element)node).getElementsByTagName(CODE).item(0).getTextContent();
            strValue = ((Element)node).getElementsByTagName(VALUE).item(0).getTextContent();
            // Hashtableに値をセットする。
            xmlValues.put(strCode, strValue);
        }
        return xmlValues;
    }

    /**
     * XML Property ファイルから読み込むメソッド<br>
     * 引数で渡されたfileNameのnodeNameで指定された項目の値を<br>
     * 取得して文字列型で返します。
     * 
     * @param fileName 読み込むファイル名とパス
     * @param nodeNameTemp 読み込む項目名
     * @return 読み込んだ項目の値を文字列で返します
     * @throws SAXException XMLの処理に失敗したとき
     * @throws ParserConfigurationException XMLの処理に失敗したとき
     * @throws IOException XMLの処理に失敗したとき
     */
    public static String readFromXmlFile(String fileName, String nodeNameTemp)
            throws SAXException,
                ParserConfigurationException,
                IOException
    {
        String nodeValue = null;
        Document xd = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String nodeName = nodeNameTemp.trim();
        // XMLファイルを読み込みます
        DocumentBuilder builder = factory.newDocumentBuilder();
        xd = builder.parse(fileName);
        // "data" エレメントのノードリストを取得
        NodeList xnl = xd.getElementsByTagName(DATA);
        for (int i = 0; i < xnl.getLength(); i++)
        {
            Element node = (Element)xnl.item(i);
            Node subNode = node.getElementsByTagName(CODE).item(0);
            // ノード名チェックする
            if (nodeName.equals(subNode.getTextContent()))
            {
                // 値を取得する。
                nodeValue = node.getElementsByTagName(VALUE).item(0).getTextContent();
            }
        }
        // 値を返す。
        return nodeValue;
    }

    /**
     * XML Property ファイルに書き込むメソッド<br>
     * fileNameに渡されたファイルのnodeNameに渡された項目に<br>
     * nodeValueの値とnodeCommentを書き込みます。
     * 
     * @param fileName 書き込むファイルの名称とパス
     * @param nodeNameTemp XMLエレメントノード名
     * @param nodeValue XMLエレメントテキスト値
     * @param nodeComment XMLエレメントコメント文字列
     * @throws SAXException XMLの処理に失敗したとき
     * @throws ParserConfigurationException XMLの処理に失敗したとき
     * @throws IOException XMLの処理に失敗したとき
     * @throws TransformerException XMLの処理に失敗したとき
     */
    public static void writeToXmlFile(String fileName, String nodeNameTemp, String nodeValue, String nodeComment)
            throws SAXException,
                ParserConfigurationException,
                IOException,
                TransformerException
    {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        boolean isNodeFound = false;

        // XMLドキュメントをロードする。
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(fileName);

        // "data" エレメントのノードリストを取得する。
        NodeList list = document.getElementsByTagName(DATA);
        for (int i = 0; i < list.getLength(); i++)
        {
            Element node = (Element)list.item(i);
            Node subNode = node.getElementsByTagName(CODE).item(0);
            if (nodeNameTemp.equals(subNode.getTextContent()))
            {
                node.getElementsByTagName(VALUE).item(0).setTextContent(nodeValue);
//                node.getElementsByTagName(COMMENT).item(0).setTextContent(nodeComment);
                // ノード見つかったらフラグを更新
                isNodeFound = true;
            }
        }
        // ノード存在しない場合はファイルに追加
        if (!isNodeFound)
        {
            Element newDataElement = document.createElement(DATA);
            Element newCodeNode = document.createElement(CODE);
            Element newValueNode = document.createElement(VALUE);
            Element newCommentNode = document.createElement(COMMENT);
            newCodeNode.setTextContent(nodeNameTemp);
            newValueNode.setTextContent(nodeValue);
            newCommentNode.setTextContent(nodeComment);
            newDataElement.appendChild(document.createTextNode("\n\t\t"));
            newDataElement.appendChild(newCodeNode);
            newDataElement.appendChild(document.createTextNode("\n\t\t"));
            newDataElement.appendChild(newValueNode);
            newDataElement.appendChild(document.createTextNode("\n\t\t"));
            newDataElement.appendChild(newCommentNode);
            newDataElement.appendChild(document.createTextNode("\n\t"));
            document.getDocumentElement().appendChild(document.createTextNode("\n\t"));
            document.getDocumentElement().appendChild(newDataElement);
        }

        saveXmlFile(document, fileName, LabelConstants.XML_ENCODING_STRING);
    }

    /**
     * xmlDocument内容をXMLファイルに保存する共通メソッド
     * 
     * @param xmlDoc XMLドキュメントオブジェクト
     * @param fileName ファイルパスとファイル名称
     * @param encoding 作成するXMLファイルのエンコーディング。
     * @throws TransformerException XMLの処理に失敗したとき
     */
    private static void saveXmlFile(Document xmlDoc, String fileName, String encoding)
            throws TransformerException
    {
        File f = new File(fileName);
        if (!f.getParentFile().exists())
        {
            f.getParentFile().mkdirs();
        }
        DOMSource doms = new DOMSource(xmlDoc);
        StreamResult sr = new StreamResult(f);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        Properties properties = t.getOutputProperties();
        properties.setProperty(OutputKeys.METHOD, "xml");
        properties.setProperty(OutputKeys.VERSION, "1.0");
        properties.setProperty(OutputKeys.STANDALONE, "yes");
        properties.setProperty(OutputKeys.ENCODING, encoding);
        properties.setProperty(OutputKeys.INDENT, "yes");
        t.transform(doms, sr);
    }
}
