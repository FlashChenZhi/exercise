// $Id: FieldDefine.java 7636 2010-03-17 04:11:45Z okayama $
package jp.co.daifuku.wms.handler.field;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * フィールドメタデータの定義を集約したクラスです。<br>
 * XMLオブジェクトのためのユーティリティメソッドがあります。
 *
 * @version $Revision: 7636 $, $Date: 2010-03-17 13:11:45 +0900 (水, 17 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class FieldDefine
{
    /** フィールドメタ情報のデフォルトディレクトリ名 */
    public static final String METADATA_DIR = HandlerSysDefines.DEFINE_DIR;

    /** フィールドメタ情報のデフォルトファイル名 */
    public static final String METADATA_FILENAME = "fields.xml";

    /** フィールドメタ情報のデフォルト */
    public static final File METADATA_FILE = new File(METADATA_DIR, METADATA_FILENAME);

    /** フィールドタグ */
    public static final String TAG_FIELD = "field";

    /** フィールド集合タグ */
    public static final String TAG_FIELDS = "fields";

    //----------------------------------------
    // エレメント定義
    //----------------------------------------
    /** 名称エレメント */
    public static final String ELEMENT_NAME = "name";

    /** リソースIDエレメント */
    public static final String ELEMENT_RESOURCE_ID = "resourceid";

    /** タイプエレメント */
    public static final String ELEMENT_TYPE = "type";

    /** 位置エレメント */
    public static final String ELEMENT_POSITION = "position";

    /** 項目長エレメント */
    public static final String ELEMENT_LENGTH = "length";

    /** タイプクラスエレメント */
    public static final String ELEMENT_CLASS = "class";

    /** フォーマットパターン エレメント */
    public static final String ELEMENT_FORMAT_PATTERN = "pattern";

    /** 空白可エレメント */
    public static final String ELEMENT_ENABLE_SPACE = "enablespace";

    /** 必須エレメント */
    public static final String ELEMENT_REQUIRE = "require";

    /** 範囲指定エレメント */
    public static final String ELEMENT_RANGE = "range";

    /** 自動更新エレメント */
    public static final String ELEMENT_AUTOUPDATE = "autoupdate";

    /** 有効/無効エレメント */
    public static final String ELEMENT_ENABLED = "enabled";

    /** 説明エレメント */
    public static final String ELEMENT_DESCRIPTION = "description";

    //----------------------------------------
    // 内容定義
    //----------------------------------------
    /** 内容定義 (タイプ:数値) */
    public static final String DEFINE_TYPE_NUMBER = "9";

    /** 内容定義 (タイプ:文字) */
    public static final String DEFINE_TYPE_STRING = "x";

    /** 内容定義 (タイプ:日時) */
    public static final String DEFINE_TYPE_TIMESTAMP = "timestamp";

    /** 真値定義 */
    private static final String DEFINE_TRUE = "true";

    /** プロパティが未指定であることを表す文字 (スペース) */
    public static final String EMPTY_PROPERTY = " ";

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * フィールド定義用のフィールド定義を生成します。
     * 
     * @param fmeta フィールドメタ情報
     * @param tgt メタ情報設定の対象エレメント
     */
    public static void buildElement(FieldMetaData fmeta, Element tgt)
    {
        buildElementForCommon(fmeta, tgt);

        // range of value (optional)
        if (fmeta.hasRange())
        {
            String range = fmeta.getRange();
            if (!isEmpty(range, false))
            {
                tgt.setAttribute(ELEMENT_RANGE, range);
            }
        }

        // resource ID
        String rid = fmeta.getResourceID();
        if (!isEmpty(rid))
        {
            tgt.setAttribute(ELEMENT_RESOURCE_ID, rid);
        }

        // length
        float leng = fmeta.getLength();
        if (leng != FieldMetaData.LENGTH_UNLIMITED)
        {
            BigDecimal bdc = new BigDecimal(String.valueOf(leng));
            if (bdc.intValue() == bdc.floatValue())
            {
                bdc = new BigDecimal(bdc.intValue());
            }

            tgt.setAttribute(ELEMENT_LENGTH, bdc.toString());
        }

        // field type (Default is String type)
        tgt.setAttribute(ELEMENT_TYPE, getFieldTypeString(fmeta));

        // type class
        if (fmeta.hasTypeClass())
        {
            int tcs = fmeta.getTypeClass();
            tgt.setAttribute(ELEMENT_CLASS, String.valueOf(tcs));
        }

        // description
        String description = fmeta.getDescription();
        if (!isEmpty(description))
        {
            tgt.setAttribute(ELEMENT_DESCRIPTION, description);
        }
    }

    /**
     * ストア定義用のフィールド定義を生成します。
     * 
     * @param fmeta フィールドメタ情報
     * @param tgt メタ情報設定の対象エレメント
     */
    public static void buildElementForStore(FieldMetaData fmeta, Element tgt)
    {
        buildElementForCommon(fmeta, tgt);

        // length (optional)
        float leng = fmeta.getLength();
        if (leng != FieldMetaData.LENGTH_UNLIMITED)
        {
            BigDecimal bdc = new BigDecimal(String.valueOf(leng));
            if (bdc.intValue() == bdc.floatValue())
            {
                bdc = new BigDecimal(bdc.intValue());
            }

            tgt.setAttribute(ELEMENT_LENGTH, bdc.toString());
        }

        // require of value (optional)
        if (fmeta.hasRequire())
        {
            boolean require = fmeta.isRequire();
            tgt.setAttribute(ELEMENT_REQUIRE, String.valueOf(require));
        }

        // range of value (optional)
        if (fmeta.hasRange())
        {
            String range = fmeta.getRange();
            if (isEmpty(range))
            {
                range = EMPTY_PROPERTY;
            }
            tgt.setAttribute(ELEMENT_RANGE, range);
        }

        // position (optional)
        if (fmeta.hasPosition())
        {
            int pos = fmeta.getPosition();
            tgt.setAttribute(ELEMENT_POSITION, String.valueOf(pos));
        }

        // field type (optional)
        if (fmeta.hasType())
        {
            String typeStr = getFieldTypeString(fmeta);
            tgt.setAttribute(ELEMENT_TYPE, typeStr);
        }

        // type class (optional)
        if (fmeta.hasTypeClass())
        {
            int tcs = fmeta.getTypeClass();
            tgt.setAttribute(ELEMENT_CLASS, String.valueOf(tcs));
        }

        // enable or disable
        if (fmeta.hasEnabled())
        {
            boolean en = fmeta.isEnabled();
            tgt.setAttribute(ELEMENT_ENABLED, String.valueOf(en));
        }
    }

    /**
     * 共通定義用のフィールド定義を生成します。
     * 
     * @param fmeta フィールドメタ情報
     * @param tgt メタ情報設定の対象エレメント
     */
    protected static void buildElementForCommon(FieldMetaData fmeta, Element tgt)
    {
        // name of field
        String name = fmeta.getName();
        tgt.setAttribute(ELEMENT_NAME, name);

        // format pattern
        if (fmeta.hasFormatPattern())
        {
            String ptn = fmeta.getFormatPattern();
            tgt.setAttribute(ELEMENT_FORMAT_PATTERN, ptn);
        }

        // enable space on the field
        if (fmeta.hasEnableSpace())
        {
            boolean es = fmeta.isEnableSpace();
            tgt.setAttribute(ELEMENT_ENABLE_SPACE, String.valueOf(es));
        }

        // auto update 
        if (fmeta.hasAutoUpdate())
        {
            boolean au = fmeta.isAutoUpdate();
            tgt.setAttribute(ELEMENT_AUTOUPDATE, String.valueOf(au));
        }
    }

    /**
     * 空のフィールドエレメントを作成します。
     * 
     * @param doc
     * @return フィールドエレメント
     */
    public static Element createElement(Document doc)
    {
        return doc.createElement(TAG_FIELD);
    }

    /**
     * 指定されたエレメントよりメタ情報を削除します。
     * @param tgt メタ情報が格納されているエレメント
     * @param exElements 削除対象外とするエレメント名
     */
    public static void stripAttribute(Element tgt, String[] exElements)
    {
        List<String> elemNameList = getElementList();
        for (String elementName : elemNameList)
        {
            if (!isInclude(exElements, elementName))
            {
                tgt.removeAttribute(elementName);
            }
            else
            {
                // TODO
                String elemValue = tgt.getAttribute(elementName);
                if (isEmpty(elemValue))
                {
                    tgt.setAttribute(elementName, EMPTY_PROPERTY);
                }
            }
        }
    }

    /**
     * 指定されたエレメントが同じ内容を持つかチェックします。
     * @param e1
     * @param e2
     * @return 両エレメントが同じ内容を持っていれば<code>true</code>
     * を返します。
     */
    public static boolean equals(Element e1, Element e2)
    {
        List<String> elementList = getElementList();
        for (String element : elementList)
        {
            String e1Attribute = e1.getAttribute(element);
            String e2Attribute = e2.getAttribute(element);
            if (!e1Attribute.equals(e2Attribute))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 2つのエレメントを比較して、異っている属性リストを取得します。<br>
     * 全ての属性が一致していた場合は、空のリストを返します。
     * @param base 基となるエレメント
     * @param tgt 比較対象エレメント
     * @return 異っている属性リスト
     */
    public static List<String> getDifferenceList(Element base, Element tgt)
    {
        List<String> diffList = new ArrayList<String>();
        for (String attr : getElementList())
        {
            String baseAttr = base.getAttribute(attr).toLowerCase();
            String tgtAttr = tgt.getAttribute(attr).toLowerCase();
            // ポジション属性は比較対象から外します
            if (!ELEMENT_POSITION.equals(attr) && !baseAttr.equals(tgtAttr))
            {
                diffList.add(attr);
            }
        }
        //return (String[])diffList.toArray(new String[0]);
        return diffList;
    }

    /**
     * 指定されたエレメントからフィールドメタ情報を生成します。
     * @param element エレメント
     * @param fieldMetaData フィールドメタ情報
     */
    public static void buildFieldMetaData(Element element, FieldMetaData fieldMetaData)
    {
        // 名称エレメント
        String name = element.getAttribute(ELEMENT_NAME);
        if (!isEmpty(name))
        {
            fieldMetaData.setName(name, true);
        }

        // リソースIDエレメント
        String resourceId = element.getAttribute(ELEMENT_RESOURCE_ID);
        if (!isEmpty(resourceId))
        {
            fieldMetaData.setResourceID(resourceId, true);
        }

        // タイプエレメント
        String type = element.getAttribute(ELEMENT_TYPE);
        if (!isEmpty(type))
        {
            fieldMetaData.setType(getFieldTypeMetaData(type), true);
        }

        // 位置エレメント
        String position = element.getAttribute(ELEMENT_POSITION);
        if (!isEmpty(position))
        {
            fieldMetaData.setPosition(Integer.parseInt(position), true);
        }

        // 項目長エレメント
        String length = element.getAttribute(ELEMENT_LENGTH);
        if (!isEmpty(length))
        {
            fieldMetaData.setLength(length, true);
        }

        // タイプクラスエレメント
        String typeClass = element.getAttribute(ELEMENT_CLASS);
        if (!isEmpty(typeClass))
        {
            fieldMetaData.setTypeClass(Integer.parseInt(typeClass), true);
        }

        // フォーマットパターン エレメント
        String format = element.getAttribute(ELEMENT_FORMAT_PATTERN);
        if (!isEmpty(format))
        {
            fieldMetaData.setFormatPattern(format, true);
        }

        // 空白可エレメント
        String space = element.getAttribute(ELEMENT_ENABLE_SPACE).toLowerCase();
        if (!isEmpty(space))
        {
            boolean setValue = DEFINE_TRUE.equals(space);
            fieldMetaData.setEnableSpace(setValue, true);
        }

        // 必須エレメント
        String require = element.getAttribute(ELEMENT_REQUIRE).toLowerCase();
        if (!isEmpty(require))
        {
            boolean setValue = DEFINE_TRUE.equals(require);
            fieldMetaData.setRequire(setValue, true);
        }

        // 範囲指定エレメント
        String range = element.getAttribute(ELEMENT_RANGE);
        if (!isEmpty(range, false))
        {
            fieldMetaData.setRange(range, true);
        }

        // 自動更新エレメント
        String auto = element.getAttribute(ELEMENT_AUTOUPDATE).toLowerCase();
        if (!isEmpty(auto))
        {
            boolean setValue = DEFINE_TRUE.equals(auto);
            fieldMetaData.setAutoUpdate(setValue, true);
        }

        // 有効/無効エレメント
        //public static final String ELEMENT_ENABLED = "enabled";
        String enable = element.getAttribute(ELEMENT_ENABLED).toLowerCase();
        if (!isEmpty(enable))
        {
            boolean setValue = DEFINE_TRUE.equals(enable);
            fieldMetaData.setEnabled(setValue, true);
        }

        // 注釈エレメント
        String description = element.getAttribute(ELEMENT_DESCRIPTION);
        if (!isEmpty(description))
        {
            fieldMetaData.setDescription(description, true);
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * 文字チェック。
     * @param src チェック文字列
     * @return 文字列が空のときtrue.
     */
    private static boolean isEmpty(String src)
    {
        return isEmpty(src, true);
    }

    /**
     * 文字チェック。
     * @param src チェック文字列
     * @param trim 空白を未設定とみなします
     * @return 文字列が空のときtrue.
     */
    private static boolean isEmpty(String src, boolean trim)
    {
        if (null == src)
        {
            return true;
        }
        else if (trim)
        {
            return (0 == src.trim().length());
        }
        else
        {
            return (0 == src.length());
        }
    }

    /**
     * フィールドタイプ文字列取得<br>
     * 指定されたフィールドメタデータよりフィールドタイプ文字列を
     * 決定し通知します。<br>
     * フィールドタイプが設定されていなければ、デフォルト値として
     * <code>DEFINE_TYPE_STRING</code>を返します。
     * @param fmeta フィールドメタ情報
     * @return フィールドタイプ文字列
     */
    private static String getFieldTypeString(FieldMetaData fmeta)
    {
        // field type (Default is String type)
        String typeStr = DEFINE_TYPE_STRING;
        if (fmeta.hasType())
        {
            int type = fmeta.getType();
            switch (type)
            {
                case FieldMetaData.TYPE_NUMBER:
                    typeStr = DEFINE_TYPE_NUMBER;
                    break;
                case FieldMetaData.TYPE_TIMESTAMP:
                    typeStr = DEFINE_TYPE_TIMESTAMP;
                    break;
                default:
                    break;
            }
        }
        return typeStr;
    }

    /**
     * フィールドタイプメタ情報取得<br>
     * 指定されたフィールドタイプ文字列より対応するメタ情報を取得します。<br>
     * いずれのフィールドタイプ文字列でもない場合は、デフォルト値として
     * <code>FieldMetaData.TYPE_STRING</code>を返します。
     * @param type フィールドタイプ文字列
     * @return フィールドタイプメタ情報
     */
    private static int getFieldTypeMetaData(String type)
    {
        if (DEFINE_TYPE_STRING.equals(type))
        {
            return FieldMetaData.TYPE_STRING;
        }
        if (DEFINE_TYPE_NUMBER.equals(type))
        {
            return FieldMetaData.TYPE_NUMBER;
        }
        if (DEFINE_TYPE_TIMESTAMP.equals(type))
        {
            return FieldMetaData.TYPE_TIMESTAMP;
        }
        return FieldMetaData.TYPE_STRING;
    }

    /**
     * フィールド定義で利用するエレメントのリストを取得します。
     * TODO エレメントの変更を行った場合は、このメソッドも変更を行う
     * 必要があります。
     * @return エレメントリスト
     */
    private static List<String> getElementList()
    {
        List<String> elements = new ArrayList<String>();
        // 名称エレメント
        elements.add(ELEMENT_NAME);
        // リソースIDエレメント
        elements.add(ELEMENT_RESOURCE_ID);
        // タイプエレメント
        elements.add(ELEMENT_TYPE);
        // 位置エレメント
        elements.add(ELEMENT_POSITION);
        // 項目長エレメント
        elements.add(ELEMENT_LENGTH);
        // タイプクラスエレメント
        elements.add(ELEMENT_CLASS);
        // フォーマットパターン エレメント
        elements.add(ELEMENT_FORMAT_PATTERN);
        // 空白可エレメント
        elements.add(ELEMENT_ENABLE_SPACE);
        // 必須エレメント
        elements.add(ELEMENT_REQUIRE);
        // 範囲指定エレメント
        elements.add(ELEMENT_RANGE);
        // 自動更新エレメント
        elements.add(ELEMENT_AUTOUPDATE);
        // 有効/無効エレメント
        elements.add(ELEMENT_ENABLED);
        // 注釈エレメント
        elements.add(ELEMENT_DESCRIPTION);
        return elements;
    }

    /**
     * 指定されたリスト内に含まれる文字列であれば<code>true</code>を
     * 返します。
     * @param list リスト
     * @param tgt 検索対象文字列
     * @return リスト内に検索対象文字列が含まれていれば<code>true</code>
     * を返します。
     */
    private static boolean isInclude(String[] list, String tgt)
    {
        for (String data : list)
        {
            if (data.equals(tgt))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: FieldDefine.java 7636 2010-03-17 04:11:45Z okayama $";
    }
}
