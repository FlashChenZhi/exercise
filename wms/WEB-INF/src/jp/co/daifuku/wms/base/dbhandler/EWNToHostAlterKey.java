// $Id: EWNToHostAlterKey.java 7201 2010-02-24 02:36:39Z kishimoto $
// $LastChangedRevision: 7201 $
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
import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * EWNTOHOST用の更新キークラスです。
 *
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class EWNToHostAlterKey
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
    public static final StoreMetaData $storeMetaData = EWNToHost.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public EWNToHostAlterKey()
    {
        super(EWNToHost.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 通信日時(<code>MESSAGE_DATE</code>)<br>
     * 日付の検索値を通信日時(<code>MESSAGE_DATE</code>)にセットします。
     */
    public void setMessageDate(Date value)
    {
        setKey(EWNToHost.MESSAGE_DATE, value) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMessageDate(Date[] values)
    {
        setKey(EWNToHost.MESSAGE_DATE, values, true) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMessageDate(Date[] values, String and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMessageDate(Date[] values, boolean and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_DATE, values, and_or_toNext) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMessageDate(Date value, String compcode)
    {
        setKey(EWNToHost.MESSAGE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMessageDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMessageDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の更新値をセットします。
     * @param value 通信日時(<code>MESSAGE_DATE</code>)更新値
     */
    public void updateMessageDate(Date value)
    {
        setAdhocUpdateValue(EWNToHost.MESSAGE_DATE, value) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
     * 
     * @param value シーケンスNo.(<code>SEQUENCE_NO</code>)<br>
     * 数値の検索値をシーケンスNo.(<code>SEQUENCE_NO</code>)にセットします。
     */
    public void setSequenceNo(int value)
    {
        setKey(EWNToHost.SEQUENCE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSequenceNo(int[] values)
    {
        setKey(EWNToHost.SEQUENCE_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSequenceNo(int[] values, String and_or_toNext)
    {
        setKey(EWNToHost.SEQUENCE_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSequenceNo(int[] values, boolean and_or_toNext)
    {
        setKey(EWNToHost.SEQUENCE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSequenceNo(int value, String compcode)
    {
        setKey(EWNToHost.SEQUENCE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSequenceNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(EWNToHost.SEQUENCE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSequenceNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(EWNToHost.SEQUENCE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の更新値をセットします。
     * @param value シーケンスNo.(<code>SEQUENCE_NO</code>)更新値
     */
    public void updateSequenceNo(int value)
    {
        setAdhocUpdateValue(EWNToHost.SEQUENCE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
     * 
     * @param value データ区分(<code>MESSAGE_ID</code>)<br>
     * 文字列の検索値をデータ区分(<code>MESSAGE_ID</code>)にセットします。
     */
    public void setMessageId(String value)
    {
        setKey(EWNToHost.MESSAGE_ID, value) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMessageId(String[] values)
    {
        setKey(EWNToHost.MESSAGE_ID, values, true) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMessageId(String[] values, String and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMessageId(String[] values, boolean and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_ID, values, and_or_toNext) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMessageId(String value, String compcode)
    {
        setKey(EWNToHost.MESSAGE_ID, value, compcode, "", "", true) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
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
    public void setMessageId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMessageId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * データ区分(<code>MESSAGE_ID</code>)の更新値をセットします。
     * @param value データ区分(<code>MESSAGE_ID</code>)更新値
     */
    public void updateMessageId(String value)
    {
        setAdhocUpdateValue(EWNToHost.MESSAGE_ID, value) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param value 通信データ(<code>DATA</code>)<br>
     * 文字列の検索値を通信データ(<code>DATA</code>)にセットします。
     */
    public void setData(String value)
    {
        setKey(EWNToHost.DATA, value) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setData(String[] values)
    {
        setKey(EWNToHost.DATA, values, true) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setData(String[] values, String and_or_toNext)
    {
        setKey(EWNToHost.DATA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setData(String[] values, boolean and_or_toNext)
    {
        setKey(EWNToHost.DATA, values, and_or_toNext) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setData(String value, String compcode)
    {
        setKey(EWNToHost.DATA, value, compcode, "", "", true) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
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
    public void setData(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(EWNToHost.DATA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setData(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(EWNToHost.DATA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 通信データ(<code>DATA</code>)の更新値をセットします。
     * @param value 通信データ(<code>DATA</code>)更新値
     */
    public void updateData(String value)
    {
        setAdhocUpdateValue(EWNToHost.DATA, value) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateSequenceNoWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(EWNToHost.SEQUENCE_NO, source, addvalue);
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
        return "$Id: EWNToHostAlterKey.java 7201 2010-02-24 02:36:39Z kishimoto $" ;
    }
}