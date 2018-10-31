// $Id: WebSettingAlterKey.java 5165 2009-10-19 07:11:09Z okamura $
// $LastChangedRevision: 5165 $
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
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WEBSETTING用の更新キークラスです。
 *
 * @version $Revision: 5165 $, $Date: 2009-10-19 16:11:09 +0900 (月, 19 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class WebSettingAlterKey
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
    public static final StoreMetaData $storeMetaData = WebSetting.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public WebSettingAlterKey()
    {
        super(WebSetting.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 端末No.(<code>TERMINAL_NO</code>)<br>
     * 文字列の検索値を端末No.(<code>TERMINAL_NO</code>)にセットします。
     */
    public void setTerminalNo(String value)
    {
        setKey(WebSetting.TERMINAL_NO, value) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNo(String[] values)
    {
        setKey(WebSetting.TERMINAL_NO, values, true) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalNo(String[] values, String and_or_toNext)
    {
        setKey(WebSetting.TERMINAL_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setTerminalNo(String[] values, boolean and_or_toNext)
    {
        setKey(WebSetting.TERMINAL_NO, values, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setTerminalNo(String value, String compcode)
    {
        setKey(WebSetting.TERMINAL_NO, value, compcode, "", "", true) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
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
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WebSetting.TERMINAL_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setTerminalNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WebSetting.TERMINAL_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 端末No.(<code>TERMINAL_NO</code>)の更新値をセットします。
     * @param value 端末No.(<code>TERMINAL_NO</code>)更新値
     */
    public void updateTerminalNo(String value)
    {
        setAdhocUpdateValue(WebSetting.TERMINAL_NO, value) ;
    }

    /**
     * 画面ID(<code>FUNCTIONID</code>)の検索値をセットします。
     * 
     * @param value 画面ID(<code>FUNCTIONID</code>)<br>
     * 文字列の検索値を画面ID(<code>FUNCTIONID</code>)にセットします。
     */
    public void setFunctionid(String value)
    {
        setKey(WebSetting.FUNCTIONID, value) ;
    }

    /**
     * 画面ID(<code>FUNCTIONID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFunctionid(String[] values)
    {
        setKey(WebSetting.FUNCTIONID, values, true) ;
    }

    /**
     * 画面ID(<code>FUNCTIONID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFunctionid(String[] values, String and_or_toNext)
    {
        setKey(WebSetting.FUNCTIONID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 画面ID(<code>FUNCTIONID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFunctionid(String[] values, boolean and_or_toNext)
    {
        setKey(WebSetting.FUNCTIONID, values, and_or_toNext) ;
    }

    /**
     * 画面ID(<code>FUNCTIONID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFunctionid(String value, String compcode)
    {
        setKey(WebSetting.FUNCTIONID, value, compcode, "", "", true) ;
    }

    /**
     * 画面ID(<code>FUNCTIONID</code>)の検索値をセットします。
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
    public void setFunctionid(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WebSetting.FUNCTIONID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 画面ID(<code>FUNCTIONID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFunctionid(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WebSetting.FUNCTIONID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 画面ID(<code>FUNCTIONID</code>)の更新値をセットします。
     * @param value 画面ID(<code>FUNCTIONID</code>)更新値
     */
    public void updateFunctionid(String value)
    {
        setAdhocUpdateValue(WebSetting.FUNCTIONID, value) ;
    }

    /**
     * パラメータ名(<code>KEYDATA</code>)の検索値をセットします。
     * 
     * @param value パラメータ名(<code>KEYDATA</code>)<br>
     * 文字列の検索値をパラメータ名(<code>KEYDATA</code>)にセットします。
     */
    public void setKeydata(String value)
    {
        setKey(WebSetting.KEYDATA, value) ;
    }

    /**
     * パラメータ名(<code>KEYDATA</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setKeydata(String[] values)
    {
        setKey(WebSetting.KEYDATA, values, true) ;
    }

    /**
     * パラメータ名(<code>KEYDATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setKeydata(String[] values, String and_or_toNext)
    {
        setKey(WebSetting.KEYDATA, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パラメータ名(<code>KEYDATA</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setKeydata(String[] values, boolean and_or_toNext)
    {
        setKey(WebSetting.KEYDATA, values, and_or_toNext) ;
    }

    /**
     * パラメータ名(<code>KEYDATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setKeydata(String value, String compcode)
    {
        setKey(WebSetting.KEYDATA, value, compcode, "", "", true) ;
    }

    /**
     * パラメータ名(<code>KEYDATA</code>)の検索値をセットします。
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
    public void setKeydata(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WebSetting.KEYDATA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パラメータ名(<code>KEYDATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setKeydata(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WebSetting.KEYDATA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * パラメータ名(<code>KEYDATA</code>)の更新値をセットします。
     * @param value パラメータ名(<code>KEYDATA</code>)更新値
     */
    public void updateKeydata(String value)
    {
        setAdhocUpdateValue(WebSetting.KEYDATA, value) ;
    }

    /**
     * 設定値(<code>VALUE</code>)の検索値をセットします。
     * 
     * @param value 設定値(<code>VALUE</code>)<br>
     * 文字列の検索値を設定値(<code>VALUE</code>)にセットします。
     */
    public void setValue(String value)
    {
        setKey(WebSetting.VALUE, value) ;
    }

    /**
     * 設定値(<code>VALUE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setValue(String[] values)
    {
        setKey(WebSetting.VALUE, values, true) ;
    }

    /**
     * 設定値(<code>VALUE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setValue(String[] values, String and_or_toNext)
    {
        setKey(WebSetting.VALUE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定値(<code>VALUE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setValue(String[] values, boolean and_or_toNext)
    {
        setKey(WebSetting.VALUE, values, and_or_toNext) ;
    }

    /**
     * 設定値(<code>VALUE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setValue(String value, String compcode)
    {
        setKey(WebSetting.VALUE, value, compcode, "", "", true) ;
    }

    /**
     * 設定値(<code>VALUE</code>)の検索値をセットします。
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
    public void setValue(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WebSetting.VALUE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定値(<code>VALUE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setValue(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WebSetting.VALUE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 設定値(<code>VALUE</code>)の更新値をセットします。
     * @param value 設定値(<code>VALUE</code>)更新値
     */
    public void updateValue(String value)
    {
        setAdhocUpdateValue(WebSetting.VALUE, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値を登録日時(<code>REGIST_DATE</code>)にセットします。
     */
    public void setRegistDate(Date value)
    {
        setKey(WebSetting.REGIST_DATE, value) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDate(Date[] values)
    {
        setKey(WebSetting.REGIST_DATE, values, true) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDate(Date[] values, String and_or_toNext)
    {
        setKey(WebSetting.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(WebSetting.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(WebSetting.REGIST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
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
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WebSetting.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WebSetting.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録日時(<code>REGIST_DATE</code>)の更新値をセットします。
     * @param value 登録日時(<code>REGIST_DATE</code>)更新値
     */
    public void updateRegistDate(Date value)
    {
        setAdhocUpdateValue(WebSetting.REGIST_DATE, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値を登録処理名(<code>REGIST_PNAME</code>)にセットします。
     */
    public void setRegistPname(String value)
    {
        setKey(WebSetting.REGIST_PNAME, value) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPname(String[] values)
    {
        setKey(WebSetting.REGIST_PNAME, values, true) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPname(String[] values, String and_or_toNext)
    {
        setKey(WebSetting.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(WebSetting.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(WebSetting.REGIST_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
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
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WebSetting.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WebSetting.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の更新値をセットします。
     * @param value 登録処理名(<code>REGIST_PNAME</code>)更新値
     */
    public void updateRegistPname(String value)
    {
        setAdhocUpdateValue(WebSetting.REGIST_PNAME, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値を最終更新日時(<code>LAST_UPDATE_DATE</code>)にセットします。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(WebSetting.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDate(Date[] values)
    {
        setKey(WebSetting.LAST_UPDATE_DATE, values, true) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDate(Date[] values, String and_or_toNext)
    {
        setKey(WebSetting.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(WebSetting.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(WebSetting.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
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
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WebSetting.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WebSetting.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の更新値をセットします。
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)更新値
     */
    public void updateLastUpdateDate(Date value)
    {
        setAdhocUpdateValue(WebSetting.LAST_UPDATE_DATE, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値を最終更新処理名(<code>LAST_UPDATE_PNAME</code>)にセットします。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(WebSetting.LAST_UPDATE_PNAME, value) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePname(String[] values)
    {
        setKey(WebSetting.LAST_UPDATE_PNAME, values, true) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePname(String[] values, String and_or_toNext)
    {
        setKey(WebSetting.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(WebSetting.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(WebSetting.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
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
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(WebSetting.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(WebSetting.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の更新値をセットします。
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)更新値
     */
    public void updateLastUpdatePname(String value)
    {
        setAdhocUpdateValue(WebSetting.LAST_UPDATE_PNAME, value) ;
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
        return "$Id: WebSettingAlterKey.java 5165 2009-10-19 07:11:09Z okamura $" ;
    }
}
