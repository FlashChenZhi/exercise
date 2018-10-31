//$Id: FieldDefineHandler.java 7636 2010-03-17 04:11:45Z okayama $
package jp.co.daifuku.wms.handler.field;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

/**
 * フィールド定義XMLファイルをハンドリングするためのクラスです。
 *
 * @version $Revision: 7636 $, $Date: 2010-03-17 13:11:45 +0900 (水, 17 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class FieldDefineHandler
        extends AbstractDefineHandler
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private Map _fieldMetaDataMap = new HashMap();

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 定義から読み込む対象を指定してインスタンスを生成します。
     * @param fields 取得する対象のフィールド
     */
    protected FieldDefineHandler(FieldMetaData[] fields)
    {
        super();
        setFieldMap(fields);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    // throws SAXException
    {
        String name = attributes.getValue(FieldDefine.ELEMENT_NAME);
        if (!isInProcess() && FieldDefine.TAG_FIELD.equals(qName.toLowerCase()) && isTarget(name))
        {
            setInProcess(true);

            FieldMetaData fmeta = getFieldMetaData(name);
            if (fmeta != null)
            {
                updateFieldMetaData(attributes, fmeta);
            }
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String qName)
    // throws SAXException
    {
        if (isInProcess() && FieldDefine.TAG_FIELD.equals(qName.toLowerCase()))
        {
            setInProcess(false);
        }
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * フィールドメタ情報を取得します。
     * 
     * @return フィールドメタ情報
     */
    public FieldMetaData[] getFieldMetaDatas()
    {
        return (FieldMetaData[])_fieldMetaDataMap.values().toArray(new FieldMetaData[0]);
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
     * 複数のフィールドメタ情報をマップにセットします。
     * @param flds
     */
    protected void setFieldMap(FieldMetaData[] flds)
    {
        Map fldMap = _fieldMetaDataMap;
        for (int i = 0; i < flds.length; i++)
        {
            FieldMetaData fld = flds[i];
            fldMap.put(fld.getName().toUpperCase(), fld);
        }
    }

    /**
     * フィールドメタ情報をフィールド名を指定して取得します。
     * @param name
     * @return フィールドメタ情報
     */
    protected FieldMetaData getFieldMetaData(String name)
    {
        Map fldMap = _fieldMetaDataMap;
        Object tgt = fldMap.get(name.toUpperCase());
        if (tgt instanceof FieldMetaData)
        {
            return (FieldMetaData)tgt;
        }
        return null;
    }

    /**
     * 対象のフィールドかどうかチェックします。
     * @param name
     * @return 対象の時 true.
     */
    protected boolean isTarget(String name)
    {
        Map fldMap = _fieldMetaDataMap;
        String src = name.toUpperCase();

        Object tgt = fldMap.get(src);
        return (tgt != null);
    }

    /**
     * エレメント情報を作成します。
     * @param attributes
     * @return フィールドメタ情報
     */
    protected static FieldMetaData createFieldMetaData(Attributes attributes)
    {
        FieldMetaData fmeta = new FieldMetaData();
        return updateFieldMetaData(attributes, fmeta);
    }

    /**
     * エレメント情報を更新します。
     * @param attributes
     * @param fmeta 更新元フィールドメタ情報
     * @return フィールドメタ情報
     */
    protected static FieldMetaData updateFieldMetaData(Attributes attributes, FieldMetaData fmeta)
    {
        int cnt = attributes.getLength();
        for (int i = 0; i < cnt; i++)
        {
            String key = attributes.getQName(i);
            if (FieldDefine.ELEMENT_ENABLE_SPACE.equals(key))
            {
                fmeta.setEnableSpace(getBoolean(attributes, FieldDefine.ELEMENT_ENABLE_SPACE, true), false);
            }
            else if (FieldDefine.ELEMENT_REQUIRE.equals(key))
            {
                fmeta.setRequire(getBoolean(attributes, FieldDefine.ELEMENT_REQUIRE, true), false);
            }
            else if (FieldDefine.ELEMENT_LENGTH.equals(key))
            {
                fmeta.setLength(getString(attributes, FieldDefine.ELEMENT_LENGTH, true), false);
            }
            else if (FieldDefine.ELEMENT_NAME.equals(key))
            {
                fmeta.setName(getString(attributes, FieldDefine.ELEMENT_NAME, false), false);
            }
            else if (FieldDefine.ELEMENT_CLASS.equals(key))
            {
                fmeta.setTypeClass(getNumber(attributes, FieldDefine.ELEMENT_CLASS, true).intValue(), false);
            }
            else if (FieldDefine.ELEMENT_FORMAT_PATTERN.equals(key))
            {
                String ptn = getString(attributes, FieldDefine.ELEMENT_FORMAT_PATTERN, false);
                fmeta.setFormatPattern(ptn, false);
                // TODO set format length to length ... Hmm..
                // fmeta.setLength(ptn.getBytes().length, false) ;
            }
            else if (FieldDefine.ELEMENT_RESOURCE_ID.equals(key))
            {
                fmeta.setResourceID(getString(attributes, FieldDefine.ELEMENT_RESOURCE_ID, true), false);
            }
            else if (FieldDefine.ELEMENT_RANGE.equals(key))
            {
                fmeta.setRange(getString(attributes, FieldDefine.ELEMENT_RANGE, false), false);
            }
            else if (FieldDefine.ELEMENT_POSITION.equals(key))
            {
                fmeta.setPosition(getNumber(attributes, FieldDefine.ELEMENT_POSITION, false).intValue(), false);
            }
            else if (FieldDefine.ELEMENT_AUTOUPDATE.equals(key))
            {
                fmeta.setAutoUpdate(getBoolean(attributes, FieldDefine.ELEMENT_AUTOUPDATE, false), false);
            }
            else if (FieldDefine.ELEMENT_ENABLED.equals(key))
            {
                fmeta.setEnabled(getBoolean(attributes, FieldDefine.ELEMENT_ENABLED, false), false);
            }
            else if (FieldDefine.ELEMENT_TYPE.equals(key))
            {
                String value = getString(attributes, FieldDefine.ELEMENT_TYPE, false);
                int itype = FieldMetaData.TYPE_STRING;
                if (FieldDefine.DEFINE_TYPE_NUMBER.equals(value))
                {
                    itype = FieldMetaData.TYPE_NUMBER;
                }
                else if (FieldDefine.DEFINE_TYPE_STRING.equals(value))
                {
                    itype = FieldMetaData.TYPE_STRING;
                }
                else if (FieldDefine.DEFINE_TYPE_TIMESTAMP.equals(value))
                {
                    itype = FieldMetaData.TYPE_TIMESTAMP;
                }
                else
                {
                    String name = attributes.getValue(FieldDefine.ELEMENT_NAME);
                    throw new RuntimeException("Invalid field type defined on " + name + ",type = " + value);
                }
                fmeta.setType(itype, false);
            }
            else if (FieldDefine.ELEMENT_DESCRIPTION.equals(key))
            {
                fmeta.setDescription(getString(attributes, FieldDefine.ELEMENT_DESCRIPTION, false), false);
            }
        }
        return fmeta;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: FieldDefineHandler.java 7636 2010-03-17 04:11:45Z okayama $";
    }
}
