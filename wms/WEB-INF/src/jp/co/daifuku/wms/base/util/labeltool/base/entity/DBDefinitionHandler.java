// $$Id: DBDefinitionHandler.java 1911 2008-12-11 02:51:48Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.base.entity;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * DB定義XMLファイルに関する処理を行うクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/30</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class DBDefinitionHandler
{

    /** <code>タグ名の定数</code> */
    private static final String TABLE = "Table";

    /** <code>タグ名の定数</code> */
    private static final String ATTRIBUTE_NAME = "name";

    /**
     * DB定義XMLファイルよりテーブル名称リストを取得します。
     * 
     * @param fileName DB定義XMLファイル名
     * @return テーブル名称リスト 
     * @throws SAXException 異常が発生した場合
     * @throws ParserConfigurationException 異常が発生した場合
     * @throws IOException 異常が発生した場合
     */
    public static ArrayList<String> getTableNames(String fileName)
            throws SAXException,
                ParserConfigurationException,
                IOException
    {
        ArrayList<String> xmlValues = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document xd = builder.parse(new org.xml.sax.InputSource(new java.io.FileInputStream(
                fileName)));
        NodeList xnl = xd.getElementsByTagName(TABLE);

        if (xnl.getLength() > 0)
        {
            xmlValues = new ArrayList<String>();
            for (int i = 0; i < xnl.getLength(); i++)
            {
                Node node = xnl.item(i);
                xmlValues.add(node.getAttributes().getNamedItem(ATTRIBUTE_NAME).getNodeValue());
            }
        }
        return xmlValues;
    }

    /**
     * DB定義XMLファイルより指定テーブル名称のDB項目リストを取得します。
     * 
     * @param fileName DB定義XMLファイル名
     * @param tableName テーブル名称
     * @return DB項目リスト
     * @throws SAXException 異常が発生した場合
     * @throws ParserConfigurationException 異常が発生した場合
     * @throws IOException 異常が発生した場合
     */
    public static ArrayList<String> getTableFields(String fileName, String tableName)
            throws SAXException,
                ParserConfigurationException,
                IOException
    {
        ArrayList<String> xmlValues = null;
        if (tableName != null && !"".equals(tableName))
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xd = builder.parse(new org.xml.sax.InputSource(new java.io.FileInputStream(
                    fileName)));
            NodeList xnl = xd.getElementsByTagName(TABLE);

            for (int i = 0; i < xnl.getLength(); i++)
            {
                Node node = xnl.item(i);
                if (node.getAttributes().getNamedItem(ATTRIBUTE_NAME).getNodeValue().equals(
                        tableName))
                {
                    xmlValues = new ArrayList<String>();
                    NodeList xnl2 = node.getChildNodes();
                    for (int j = 0; j < xnl2.getLength(); j++)
                    {
                        Node node2 = xnl2.item(j);
                        if (node2.getNodeType() == Node.TEXT_NODE)
                        {
                            continue;
                        }
                        xmlValues.add(node2.getAttributes().getNamedItem(ATTRIBUTE_NAME).getNodeValue());
                    }
                }
            }
        }
        return xmlValues;
    }
}
