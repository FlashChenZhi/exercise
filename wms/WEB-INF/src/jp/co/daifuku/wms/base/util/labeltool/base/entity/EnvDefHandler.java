// $Id: EnvDefHandler.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.XmlReadWriter;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.base.util.StringUtil;

import org.xml.sax.SAXException;


/**
 * 環境定義ファイル(EnvironmentDef)にアクセスするクラス<br>
 * このクラスはEnvironmentDefへの読み込みを行なうクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2007/04/12</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class EnvDefHandler
{
    /** <code>このクラスのインスタンス</code> */
    private static EnvDefHandler $envDefHandlerRef = null;

    /** <code>環境ファイルのキー・値を保持するHashTable</code> */
    private Hashtable<String, String> _keyValuePair = null;

    /** <code>環境設定ファイル</code> */
    private String envFileName = null;

    /**
     * コンストラクタ<br>
     * EnvironmentDefのパスと名前を取得します。<br>
     * その後、ファイルから環境定義情報を読み込み、インスタンス内に保持します。
     * 
     * @throws DaiException 環境ファイルにアクセスエラー発生した場合
     */
    public EnvDefHandler() throws DaiException
    {
        envFileName = LabelConstants.CONF_PATH + LabelConstants.DIRSEPACHAR + LabelConstants.ENVIRONMENT_FILENAME;
        _keyValuePair = fetchEnvDefContents();
    }

    /**
     * このメソッドはEnvDefHandlerのインスタンスを返します。<br>
     * 保持していなかった場合は、新しく生成して返します。
     * 
     * @return EnvDefHandlerのインスタンス
     */
    public static EnvDefHandler getInstance()
    {
        if ($envDefHandlerRef == null)
        {
            try
            {
                $envDefHandlerRef = new EnvDefHandler();
            }
            catch (DaiException e)
            {
                e.printStackTrace();
            }
        }
        return $envDefHandlerRef;
    }

    /**
     * パラメータで渡されたキーに対応する値を返します。
     * 
     * @param key 取得するKEY項目
     * @return KEYの値
     */
    public String getDefineValue(String key)
    {
        String strValue = "";

        if (StringUtil.isEmpty(key))
        {
            return strValue;
        }

        strValue = _keyValuePair.get(key.trim());

        if (StringUtil.isEmpty(key))
        {
            return "";
        }

        return strValue;
    }

    /**
     * 環境定義ファイル(EnvironmentDef.XML)の値を更新します。<br>
     * 更新するのは１件だけです。
     * 
     * @param key 更新対象となるKEY値
     * @param value セットする値
     * @throws DaiException XMLファイル操作中に例外が発生した場合
     */
    public void setDefineValue(String key, String value)
            throws DaiException
    {
        File termFile = new File(envFileName);

        try
        {
            if (!termFile.exists())
            {
                XmlReadWriter.createXmlFile(envFileName, LabelConstants.XML_ENCODING_STRING);
            }
            XmlReadWriter.writeToXmlFile(envFileName, key, value, null);
        }
        catch (SAXException e)
        {
            throw new DaiException("XmlException", e);
        }
        catch (ParserConfigurationException e)
        {
            throw new DaiException("XmlException", e);
        }
        catch (IOException e)
        {
            throw new DaiException("IOException", e);
        }
        catch (TransformerException e)
        {
            throw new DaiException("XmlException", e);
        }
        if (_keyValuePair != null && _keyValuePair.containsKey(key))
        {
            _keyValuePair.remove(key);
            _keyValuePair.put(key, value);
        }
    }


    /**
     * このメソッドは、環境定義ファイル(EnvironmentDef)の内容を読み込み、<br>
     * ハッシュテーブルに追加し、返します。
     * 
     * @return XMLファイルからKEYと値のペアを追加したハッシュテーブル
     * @throws DaiException XMLファイル操作中に例外が発生した場合
     */
    private Hashtable<String, String> fetchEnvDefContents()
            throws DaiException
    {
        Hashtable<String, String> envHashtable = null;

        File envFile = new File(envFileName);
        if (!envFile.exists())
        {
            throw new DaiException("File not found" + envFile.getPath() + envFile.getName());
        }
        try
        {
            envHashtable = XmlReadWriter.readAllXmlContents(envFileName);
        }
        catch (SAXException e)
        {
            throw new DaiException("XML Exception", e);
        }
        catch (ParserConfigurationException e)
        {
            throw new DaiException("XML Exception", e);
        }
        catch (IOException e)
        {
            throw new DaiException("XML Exception", e);
        }
        return envHashtable;
    }
}
