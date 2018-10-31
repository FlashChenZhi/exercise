// $Id: SoftZoneAlterKey.java 4122 2009-04-10 10:58:38Z ota $
// $LastChangedRevision: 4122 $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * SOFTZONE用の更新キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class SoftZoneAlterKey
        extends DefaultSQLAlterKey
{
    //------------------------------------------------------------
    // class variables (Prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------


    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;
    /** Store meta data for this alter key */
    public static final StoreMetaData $storeMetaData = SoftZone.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public SoftZoneAlterKey()
    {
        super(SoftZone.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーンID(<code>SOFT_ZONE_ID</code>)<br>
     * 文字列の検索値をソフトゾーンID(<code>SOFT_ZONE_ID</code>)にセットします。
     */
    public void setSoftZoneId(String value)
    {
        setKey(SoftZone.SOFT_ZONE_ID, value) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String[] values)
    {
        setKey(SoftZone.SOFT_ZONE_ID, values, true) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String[] values, String and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSoftZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSoftZoneId(String value, String compcode)
    {
        setKey(SoftZone.SOFT_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の更新値をセットします。
     * @param value ソフトゾーンID(<code>SOFT_ZONE_ID</code>)更新値
     */
    public void updateSoftZoneId(String value)
    {
        setAdhocUpdateValue(SoftZone.SOFT_ZONE_ID, value) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value ゾーン名称(<code>SOFT_ZONE_NAME</code>)<br>
     * 文字列の検索値をゾーン名称(<code>SOFT_ZONE_NAME</code>)にセットします。
     */
    public void setSoftZoneName(String value)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, value) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneName(String[] values)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, values, true) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneName(String[] values, String and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSoftZoneName(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, values, and_or_toNext) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSoftZoneName(String value, String compcode)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSoftZoneName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の更新値をセットします。
     * @param value ゾーン名称(<code>SOFT_ZONE_NAME</code>)更新値
     */
    public void updateSoftZoneName(String value)
    {
        setAdhocUpdateValue(SoftZone.SOFT_ZONE_NAME, value) ;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * ストアメタデータを返します。
     * @return ストアメタデータ
     */
    @Override
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: SoftZoneAlterKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
