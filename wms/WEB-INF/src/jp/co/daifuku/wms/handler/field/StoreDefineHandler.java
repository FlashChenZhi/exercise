//$Id: StoreDefineHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.field;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

import org.xml.sax.Attributes;

/**
 * ストア定義XMLファイルをハンドリングするためのクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class StoreDefineHandler
        extends AbstractDefineHandler
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private String p_targetStoreName;

    private StoreMetaData p_storeMetaData;

    private File p_fieldDefineFile;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private List _fieldList = new ArrayList();

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 対象のストア名を指定してインスタンスを生成します。
     * @param fielddefFile フィールド定義ファイル
     * @param storeName ストア名
     */
    protected StoreDefineHandler(String storeName, File fielddefFile)
    {
        setTargetStoreName(storeName);
        setFieldDefineFile(fielddefFile);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 定義ファイルから指定されたストアのメタ情報を取得します。<br>
     * ストア定義,フィールド定義ファイルは、デフォルトを使用します。<br>
     * 
     * @param storeName ストア名
     * @return ストアメタ情報
     * @see StoreDefine
     * @see FieldDefine
     */
    public static synchronized StoreMetaData createStoreMetaData(String storeName)
    {
        return createStoreMetaData(storeName, StoreDefine.METADATA_FILE, FieldDefine.METADATA_FILE);
    }

    /**
     * 定義ファイルから指定されたストアのメタ情報を取得します。
     * 
     * @param storedefFile ストア定義ファイル
     * @param fielddefFile フィールド定義ファイル
     * @param storeName ストア名
     * @return ストアメタ情報
     */
    public static synchronized StoreMetaData createStoreMetaData(String storeName, File storedefFile, File fielddefFile)
    {
        StoreDefineHandler sHandler = new StoreDefineHandler(storeName, fielddefFile);
        try
        {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(storedefFile, sHandler);

            return sHandler.getStoreMetaData();
        }
        catch (Exception e)
        {
            handleParseExceptions(e);
        }
        return null;
    }

    /**
     * 定義ファイルから指定されたストアのメタ情報を取得します。
     * 
     * @param storeName ストア名
     * @param storedefFile ストア定義ファイル名
     * @param fielddefFile フィールド定義ファイル名
     * @return ストアメタ情報
     */
    public static synchronized StoreMetaData createStoreMetaData(String storeName, String storedefFile,
            String fielddefFile)
    {
        File storeFile = new File(HandlerSysDefines.DEFINE_DIR, storedefFile);
        File fieldFile = new File(HandlerSysDefines.DEFINE_DIR, fielddefFile);
        return createStoreMetaData(storeName, storeFile, fieldFile);
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    // throws SAXException
    {
        List fldList = _fieldList;

        String name = attributes.getValue(StoreDefine.ELEMENT_NAME);
        boolean inProcess = isInProcess();
        if (!inProcess && StoreDefine.TAG_STORE.equals(qName))
        {
            String target = getTargetStoreName();
            if (target.equals(name.toUpperCase()))
            {
                setInProcess(true);
                createStoreMetaData(attributes);
            }
        }

        if (inProcess && FieldDefine.TAG_FIELD.equals(qName))
        {
            FieldMetaData fmeta = FieldDefineHandler.createFieldMetaData(attributes);
            fldList.add(fmeta);
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String qName)
    // throws SAXException
    {
        if (isInProcess() && StoreDefine.TAG_STORE.equals(qName))
        {
            setInProcess(false);
            loadFieldMetaDatas();
        }
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return targetStoreNameを返します。
     */
    protected String getTargetStoreName()
    {
        return p_targetStoreName;
    }

    /**
     * @param targetStoreName targetStoreNameを設定します。
     */
    protected void setTargetStoreName(String targetStoreName)
    {
        p_targetStoreName = targetStoreName.toUpperCase();
    }

    /**
     * @return storeMetaDataを返します。
     */
    public StoreMetaData getStoreMetaData()
    {
        return p_storeMetaData;
    }

    /**
     * @param storeMetaData storeMetaDataを設定します。
     */
    public void setStoreMetaData(StoreMetaData storeMetaData)
    {
        p_storeMetaData = storeMetaData;
    }

    /**
     * @return fieldDefineFileを返します。
     */
    public File getFieldDefineFile()
    {
        return p_fieldDefineFile;
    }

    /**
     * @param fieldDefineFile fieldDefineFileを設定します。
     */
    public void setFieldDefineFile(File fieldDefineFile)
    {
        p_fieldDefineFile = fieldDefineFile;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ストアに関する情報をセットします。
     * @param attributes
     */
    protected void createStoreMetaData(Attributes attributes)
    {
        StoreMetaData smeta = new StoreMetaData();
        smeta.setDescriptoin(attributes.getValue(StoreDefine.ELEMENT_DESCRIPTION));
        smeta.setResourceID(attributes.getValue(StoreDefine.ELEMENT_RESOURCE_ID));
        smeta.setStoreClass(attributes.getValue(StoreDefine.ELEMENT_STORE_CLASS));
        smeta.setName(attributes.getValue(StoreDefine.ELEMENT_NAME));
        smeta.setType(attributes.getValue(StoreDefine.ELEMENT_TYPE));

        String mrStr = attributes.getValue(StoreDefine.ELEMENT_MAX_RECORD);
        if (mrStr != null && mrStr.length() > 0)
        {
            int maxrec = Integer.parseInt(mrStr);
            smeta.setMaxRecordNumber(maxrec);
        }

        String split = attributes.getValue(StoreDefine.ELEMENT_SPLIT_STRING);
        if (split != null)
        {
            smeta.setSplitString(split);
        }

        String quote = attributes.getValue(StoreDefine.ELEMENT_QUOTE_STRING);
        if (quote != null)
        {
            smeta.setQuoteString(quote);
        }

        String charset = attributes.getValue(StoreDefine.ELEMENT_CHARSET);
        if (charset != null)
        {
            smeta.setCharset(charset);
        }
        setStoreMetaData(smeta);
    }

    /**
     * 該当ストアに関するフィールドメタ情報を読み込みます。
     */
    protected void loadFieldMetaDatas()
    {
        List fldList = _fieldList;
        FieldMetaData[] flds = (FieldMetaData[])fldList.toArray(new FieldMetaData[0]);
        FieldDefineHandler fHandler = new FieldDefineHandler(flds);

        SAXParser parser;
        try
        {
            parser = SAXParserFactory.newInstance().newSAXParser();

            File flddefFile = getFieldDefineFile();
            parser.parse(flddefFile, fHandler);

            FieldMetaData[] fmetaArr = fHandler.getFieldMetaDatas();

            StoreMetaData smeta = getStoreMetaData();
            for (int i = 0; i < fmetaArr.length; i++)
            {
                smeta.addFieldMetaData(fmetaArr[i]);
            }
        }
        catch (Exception e)
        {
            handleParseExceptions(e);
        }
    }

    /**
     * XMLパース異常のハンドリングを行います。
     * 
     * @param e
     */
    protected static synchronized void handleParseExceptions(Exception e)
    {
        e.printStackTrace();
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: StoreDefineHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}
