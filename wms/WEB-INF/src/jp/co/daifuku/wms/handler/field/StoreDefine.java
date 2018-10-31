// $Id: StoreDefine.java 6495 2009-12-16 06:03:39Z okamura $
package jp.co.daifuku.wms.handler.field;

import java.io.File;

import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ストアメタデータの定義を集約したクラスです。<br>
 * XMLオブジェクトのためのユーティリティメソッドがあります。
 *
 * @version $Revision: 6495 $, $Date: 2009-12-16 15:03:39 +0900 (水, 16 12 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */
public class StoreDefine
        extends AbstractDefine
{
    /** フィールドメタ情報のデフォルトディレクトリ名 */
    public static final String METADATA_DIR = HandlerSysDefines.DEFINE_DIR;

    /** フィールドメタ情報のデフォルトファイル名 */
    public static final String METADATA_FILENAME = "stores.xml";

    /** フィールドメタ情報のデフォルト */
    public static File METADATA_FILE = new File(METADATA_DIR, METADATA_FILENAME);

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ストアタグ */
    public static final String TAG_STORE = "store";

    /** ストア集合タグ */
    public static final String TAG_STORES = "stores";

    //----------------------------------------
    // エレメント定義
    //----------------------------------------
    /** 名称エレメント */
    public static final String ELEMENT_NAME = "name";

    /** 注釈エレメント */
    public static final String ELEMENT_DESCRIPTION = "description";

    /** リソースIDエレメント */
    public static final String ELEMENT_RESOURCE_ID = "resourceid";

    /** ストアクラスエレメント */
    public static final String ELEMENT_STORE_CLASS = "class";

    /** 使用キャラクタセットエレメント */
    public static final String ELEMENT_CHARSET = "charset";

    /** タイプエレメント */
    public static final String ELEMENT_TYPE = "type";

    /** 最大レコード数エレメント */
    public static final String ELEMENT_MAX_RECORD = "maxrec";

    /** フィールド区切りエレメント */
    public static final String ELEMENT_SPLIT_STRING = "split";

    /** 文字列囲みエレメント */
    public static final String ELEMENT_QUOTE_STRING = "quote";

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @param smeta ストアメタ情報
     * @param tgt 設定対象エレメント
     */
    public static void buildElement(StoreMetaData smeta, Element tgt)
    {
        // store defines

        // store name
        String name = smeta.getName();
        tgt.setAttribute(ELEMENT_NAME, name);

        // store descriptin
        String desc = smeta.getDescriptoin();
        if (!isEmpty(desc))
        {
            tgt.setAttribute(ELEMENT_DESCRIPTION, desc);
        }

        // resource ID
        String rid = smeta.getResourceID();
        if (!isEmpty(rid))
        {
            tgt.setAttribute(ELEMENT_RESOURCE_ID, rid);
        }

        // store type
        String type = smeta.getType();
        tgt.setAttribute(ELEMENT_TYPE, type);

        // store class
        String stcs = smeta.getStoreClass();
        if (!isEmpty(stcs))
        {
            tgt.setAttribute(ELEMENT_STORE_CLASS, stcs);
        }

        // file charset
        if (smeta.hasCharset())
        {
            String charset = smeta.getCharset();
            tgt.setAttribute(ELEMENT_CHARSET, charset);
        }

        // max records
        if (smeta.hasMaxRecordNumber())
        {
            int maxrec = smeta.getMaxRecordNumber();
            tgt.setAttribute(ELEMENT_MAX_RECORD, Integer.toString(maxrec));
        }

        // quote string
        if (smeta.hasQuoteString())
        {
            String qstr = smeta.getQuoteString();
            tgt.setAttribute(ELEMENT_QUOTE_STRING, qstr);
        }

        // split string of fields
        if (smeta.hasSplitString())
        {
            String spstr = smeta.getSplitString();
            tgt.setAttribute(ELEMENT_SPLIT_STRING, spstr);
        }

        // fields
        // 無効なフィールドも含める 2009/12/15
        FieldMetaData[] flds = smeta.getFieldMetaDatas(false);
        for (FieldMetaData aField : flds)
        {
            Element fldElement = findFieldElement(tgt, aField);

            FieldDefine.buildElementForStore(aField, fldElement);
        }
    }

    /**
     * ストアエレメントから該当フィールドに相当するフィールドエレメントを
     * 検索して返します。
     * 
     * @param tgt ストアエレメント
     * @param field 検索するフィールドのメタデータ
     * @return フィールドエレメント
     */
    private static Element findFieldElement(Element tgt, FieldMetaData field)
    {
        String fldName = field.getName();
        Document doc = tgt.getOwnerDocument();

        NodeList nList = tgt.getChildNodes();
        int siz = nList.getLength();

        for (int i = 0; i < siz; i++)
        {
            // Element efld = (Element)nList.item(i) ;
            Node eNode = nList.item(i);
            Element efld = convertNode(doc, eNode);
            if (efld == null)
            {
                continue;
            }

            String ename = efld.getAttribute(FieldDefine.ELEMENT_NAME);

            if (fldName.equals(ename))
            {
                return efld;
            }
        }

        // no element of field found.
        Element newElement = tgt.getOwnerDocument().createElement(FieldDefine.TAG_FIELD);
        tgt.appendChild(newElement);

        return newElement;
    }

    /**
     * 空のストアエレメントを作成します。
     * 
     * @param doc
     * @return ストアエレメント
     */
    public static Element createElement(Document doc)
    {
        return doc.createElement(TAG_STORE);
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
        return (src == null) || (src.length() == 0);
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: StoreDefine.java 6495 2009-12-16 06:03:39Z okamura $";
    }
}
