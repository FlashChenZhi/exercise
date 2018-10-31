//$Id: Entity.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import jp.co.daifuku.wms.handler.field.FieldDefine;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreDefine;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.field.validator.FieldValidator;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;

/**
 * エンティティ系オブジェクトが実装するべきインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public interface Entity
        extends Cloneable
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** デフォルトのフィールド定義ファイル */
    public static File FIELD_METADATA_FILE = FieldDefine.METADATA_FILE;

    /**  デフォルトのストア定義ファイル */
    public static File STORE_METADATA_FILE = StoreDefine.METADATA_FILE;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * エンティティが保持する内容をすべてクリアします。
     */
    public void clear();

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * フィールドに値をセットします。<br>
     * 
     * @param field 対象フィールド
     * @param value セットする値<br>
     *  String, BigDecimal, java.util.Date のいずれかを使用してください。 
     */
    public void setValue(FieldName field, Object value);

    /**
     * フィールドに値をセットします。<br>
     * 
     * @param field 対象フィールド
     * @param value セットする値<br>
     *  String, BigDecimal, java.util.Date のいずれかを使用してください。 
     * @param withValidate 項目の内容チェックを行うときは true.
     */
    public void setValue(FieldName field, Object value, boolean withValidate);

    /**
     * フィールドの値を取得します。<br>
     * もし項目の値が<code>null</code>の場合は、<code>null</code>が返ります。
     * 
     * @param field 対象フィールド
     * @return フィールドの値 (<code>null</code>の可能性があります)
     */
    public Object getValue(FieldName field);

    /**
     * 値が未設定(<code>null</code>)のときのデフォルト値を指定して項目の値を取得します。
     *
     * @param field 対象フィールド
     * @param defaultValue 値が<code>null</code>の時に返すデフォルト値
     * @return フィールドの値
     */
    public Object getValue(FieldName field, Object defaultValue);

    /**
     * 値が未設定(<code>null</code>)のときのデフォルト値を指定して
     * 項目の値を<code>BigDecimal</code>として取得します。
     *
     * @param field 対象フィールド
     * @param defaultValue 値が<code>null</code>の時に返すデフォルト値
     * @return フィールドの値
     */
    public BigDecimal getBigDecimal(FieldName field, BigDecimal defaultValue);

    /**
     * 項目の値をBigDecimalとして取得します。<br>
     * 値が<code>null</code>のときは、<code>null</code>が返されます。
     *
     * @param field 対象フィールド
     * @return フィールドの値 (<code>null</code>の可能性があります)
     * @throws InvalidTypeException 値がBigDecimalではなかったとき
     */
    public BigDecimal getBigDecimal(FieldName field);

    /**
     * 値が未設定(null)のときのデフォルト値を指定して
     * 項目の値をDateとして取得します。
     *
     * @param field 対象フィールド
     * @param defaultValue 値が<code>null</code>の時に返すデフォルト値
     * @return フィールドの値
     */
    public Date getDate(FieldName field, Date defaultValue);

    /**
     * 項目の値をDateとして取得します。<br>
     * 値が<code>null</code>のときは、<code>null</code>が返されます。
     *
     * @param field 対象フィールド
     * @return フィールドの値 (<code>null</code>の可能性があります)
     * @throws InvalidTypeException 値がDateではなかったとき
     */
    public Date getDate(FieldName field);

    /**
     * FieldName:Objectのマップを返します。
     * 
     * @return キー:値のマップ
     */
    public Map getValueMap();

    /**
     * フィールド名リストを返します。
     * 
     * @return フィールド名の配列
     */
    public FieldName[] getFieldNames();

    /**
     * 現在の値が正しいかどうか、保持している値すべてをチェックします。<br>
     * NULLの値をチェックします。(必須項目のチェック)
     * 
     * @return エラーのあるフィールドの列挙,エラーなしの時は要素0.
     */
    public ValidateError[] validate();

    /**
     * 現在の値が正しいかどうか、保持している値すべてをチェックします。<br>
     * NULLの値をチェックしません。(必須項目のチェックなし)
     * 
     * @return エラーのあるフィールドの列挙,エラーなしの時は要素0.
     */
    public ValidateError[] validateValuesOnly();

    /**
     * セットされているフィールドの値が正しいかどうかチェックします。
     * 
     * @param key フィールド
     * @return FieldValidator.RETURN_XXXX
     * @see FieldValidator
     */
    public int validate(FieldName key);

    /**
     * フィールドに正しくセットできる値かどうかチェックします。
     * 
     * @param key フィールド
     * @param value チェック対象 (String,BigDecimal,Dateのいずれか)
     * @return FieldValidator.RETURN_XXXX
     * @see FieldValidator
     */
    public int validate(FieldName key, Object value);

    /**
     * このエンティティのメタ情報を取得します。
     * @return メタ情報
     */
    public StoreMetaData getStoreMetaData();

    /**
     * このエンティティのストア名(テーブル名等)を取得します。
     * @return ストア名
     */
    public String getStoreName();

    /**
     * インスタンスのクローンを返します。
     * @return インスタンスのコピー
     */
    public Object clone();

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
}
