// $Id: Com_loginuserAlterKey.java 8061 2013-05-24 10:24:10Z kishimoto $
// $LastChangedRevision: 8061 $
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
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * COM_LOGINUSER用の更新キークラスです。
 *
 * @version $Revision: 8061 $, $Date: 2013-05-24 19:24:10 +0900 (金, 24 5 2013) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class Com_loginuserAlterKey
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
    public static final StoreMetaData $storeMetaData = Com_loginuser.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public Com_loginuserAlterKey()
    {
        super(Com_loginuser.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * ユーザID(<code>USERID</code>)の検索値をセットします。
     * 
     * @param value ユーザID(<code>USERID</code>)<br>
     * 文字列の検索値をユーザID(<code>USERID</code>)にセットします。
     */
    public void setUserid(String value)
    {
        setKey(Com_loginuser.USERID, value) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserid(String[] values)
    {
        setKey(Com_loginuser.USERID, values, true) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserid(String[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.USERID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserid(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserid(String value, String compcode)
    {
        setKey(Com_loginuser.USERID, value, compcode, "", "", true) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の検索値をセットします。
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
    public void setUserid(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.USERID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUserid(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザID(<code>USERID</code>)の更新値をセットします。
     * @param value ユーザID(<code>USERID</code>)更新値
     */
    public void updateUserid(String value)
    {
        setAdhocUpdateValue(Com_loginuser.USERID, value) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。
     * 
     * @param value パスワード(<code>PASSWORD</code>)<br>
     * 文字列の検索値をパスワード(<code>PASSWORD</code>)にセットします。
     */
    public void setPassword(String value)
    {
        setKey(Com_loginuser.PASSWORD, value) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPassword(String[] values)
    {
        setKey(Com_loginuser.PASSWORD, values, true) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPassword(String[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.PASSWORD, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPassword(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PASSWORD, values, and_or_toNext) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPassword(String value, String compcode)
    {
        setKey(Com_loginuser.PASSWORD, value, compcode, "", "", true) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。
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
    public void setPassword(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.PASSWORD, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPassword(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PASSWORD, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * パスワード(<code>PASSWORD</code>)の更新値をセットします。
     * @param value パスワード(<code>PASSWORD</code>)更新値
     */
    public void updatePassword(String value)
    {
        setAdhocUpdateValue(Com_loginuser.PASSWORD, value) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名(<code>USERNAME</code>)<br>
     * 文字列の検索値をユーザ名(<code>USERNAME</code>)にセットします。
     */
    public void setUsername(String value)
    {
        setKey(Com_loginuser.USERNAME, value) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUsername(String[] values)
    {
        setKey(Com_loginuser.USERNAME, values, true) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUsername(String[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.USERNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUsername(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERNAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUsername(String value, String compcode)
    {
        setKey(Com_loginuser.USERNAME, value, compcode, "", "", true) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。
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
    public void setUsername(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.USERNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUsername(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザ名(<code>USERNAME</code>)の更新値をセットします。
     * @param value ユーザ名(<code>USERNAME</code>)更新値
     */
    public void updateUsername(String value)
    {
        setAdhocUpdateValue(Com_loginuser.USERNAME, value) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value ロール(<code>ROLEID</code>)<br>
     * 文字列の検索値をロール(<code>ROLEID</code>)にセットします。
     */
    public void setRoleid(String value)
    {
        setKey(Com_loginuser.ROLEID, value) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoleid(String[] values)
    {
        setKey(Com_loginuser.ROLEID, values, true) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoleid(String[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.ROLEID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRoleid(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.ROLEID, values, and_or_toNext) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRoleid(String value, String compcode)
    {
        setKey(Com_loginuser.ROLEID, value, compcode, "", "", true) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。
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
    public void setRoleid(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.ROLEID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRoleid(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.ROLEID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ロール(<code>ROLEID</code>)の更新値をセットします。
     * @param value ロール(<code>ROLEID</code>)更新値
     */
    public void updateRoleid(String value)
    {
        setAdhocUpdateValue(Com_loginuser.ROLEID, value) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)<br>
     * 数値の検索値を仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)にセットします。
     */
    public void setDummypasswordFlag(int value)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, HandlerUtil.toObject(value)) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDummypasswordFlag(int[] values)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDummypasswordFlag(int[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDummypasswordFlag(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDummypasswordFlag(int value, String compcode)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。
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
    public void setDummypasswordFlag(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDummypasswordFlag(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の更新値をセットします。
     * @param value 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)更新値
     */
    public void updateDummypasswordFlag(int value)
    {
        setAdhocUpdateValue(Com_loginuser.DUMMYPASSWORD_FLAG, HandlerUtil.toObject(value)) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。
     * 
     * @param value ユーザ状態(<code>USERSTATUS</code>)<br>
     * 数値の検索値をユーザ状態(<code>USERSTATUS</code>)にセットします。
     */
    public void setUserstatus(int value)
    {
        setKey(Com_loginuser.USERSTATUS, HandlerUtil.toObject(value)) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserstatus(int[] values)
    {
        setKey(Com_loginuser.USERSTATUS, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUserstatus(int[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.USERSTATUS, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUserstatus(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERSTATUS, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUserstatus(int value, String compcode)
    {
        setKey(Com_loginuser.USERSTATUS, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。
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
    public void setUserstatus(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.USERSTATUS, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUserstatus(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERSTATUS, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ユーザ状態(<code>USERSTATUS</code>)の更新値をセットします。
     * @param value ユーザ状態(<code>USERSTATUS</code>)更新値
     */
    public void updateUserstatus(int value)
    {
        setAdhocUpdateValue(Com_loginuser.USERSTATUS, HandlerUtil.toObject(value)) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。
     * 
     * @param value パスワード有効期限(<code>PWDEXPIRES</code>)<br>
     * 日付の検索値をパスワード有効期限(<code>PWDEXPIRES</code>)にセットします。
     */
    public void setPwdexpires(Date value)
    {
        setKey(Com_loginuser.PWDEXPIRES, value) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPwdexpires(Date[] values)
    {
        setKey(Com_loginuser.PWDEXPIRES, values, true) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPwdexpires(Date[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.PWDEXPIRES, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPwdexpires(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PWDEXPIRES, values, and_or_toNext) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPwdexpires(Date value, String compcode)
    {
        setKey(Com_loginuser.PWDEXPIRES, value, compcode, "", "", true) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。
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
    public void setPwdexpires(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.PWDEXPIRES, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPwdexpires(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PWDEXPIRES, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の更新値をセットします。
     * @param value パスワード有効期限(<code>PWDEXPIRES</code>)更新値
     */
    public void updatePwdexpires(Date value)
    {
        setAdhocUpdateValue(Com_loginuser.PWDEXPIRES, value) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。
     * 
     * @param value パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)<br>
     * 数値の検索値をパスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)にセットします。
     */
    public void setPwdchangeinterval(int value)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, HandlerUtil.toObject(value)) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPwdchangeinterval(int[] values)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPwdchangeinterval(int[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPwdchangeinterval(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPwdchangeinterval(int value, String compcode)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。
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
    public void setPwdchangeinterval(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPwdchangeinterval(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の更新値をセットします。
     * @param value パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)更新値
     */
    public void updatePwdchangeinterval(int value)
    {
        setAdhocUpdateValue(Com_loginuser.PWDCHANGEINTERVAL, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。
     * 
     * @param value 最終ログイン日時(<code>LASTACCESSDATE</code>)<br>
     * 日付の検索値を最終ログイン日時(<code>LASTACCESSDATE</code>)にセットします。
     */
    public void setLastaccessdate(Date value)
    {
        setKey(Com_loginuser.LASTACCESSDATE, value) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastaccessdate(Date[] values)
    {
        setKey(Com_loginuser.LASTACCESSDATE, values, true) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastaccessdate(Date[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.LASTACCESSDATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastaccessdate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.LASTACCESSDATE, values, and_or_toNext) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastaccessdate(Date value, String compcode)
    {
        setKey(Com_loginuser.LASTACCESSDATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。
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
    public void setLastaccessdate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.LASTACCESSDATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastaccessdate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.LASTACCESSDATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の更新値をセットします。
     * @param value 最終ログイン日時(<code>LASTACCESSDATE</code>)更新値
     */
    public void updateLastaccessdate(Date value)
    {
        setAdhocUpdateValue(Com_loginuser.LASTACCESSDATE, value) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。
     * 
     * @param value 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)<br>
     * 数値の検索値を同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)にセットします。
     */
    public void setSameuserloginmax(int value)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, HandlerUtil.toObject(value)) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSameuserloginmax(int[] values)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSameuserloginmax(int[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSameuserloginmax(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSameuserloginmax(int value, String compcode)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。
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
    public void setSameuserloginmax(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSameuserloginmax(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の更新値をセットします。
     * @param value 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)更新値
     */
    public void updateSameuserloginmax(int value)
    {
        setAdhocUpdateValue(Com_loginuser.SAMEUSERLOGINMAX, HandlerUtil.toObject(value)) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。
     * 
     * @param value 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)<br>
     * 数値の検索値を認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)にセットします。
     */
    public void setFailedloginattempts(int value)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, HandlerUtil.toObject(value)) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFailedloginattempts(int[] values)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFailedloginattempts(int[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFailedloginattempts(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFailedloginattempts(int value, String compcode)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。
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
    public void setFailedloginattempts(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFailedloginattempts(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の更新値をセットします。
     * @param value 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)更新値
     */
    public void updateFailedloginattempts(int value)
    {
        setAdhocUpdateValue(Com_loginuser.FAILEDLOGINATTEMPTS, HandlerUtil.toObject(value)) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。
     * 
     * @param value 認証ミス回数(<code>FAILEDCOUNT</code>)<br>
     * 数値の検索値を認証ミス回数(<code>FAILEDCOUNT</code>)にセットします。
     */
    public void setFailedcount(int value)
    {
        setKey(Com_loginuser.FAILEDCOUNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFailedcount(int[] values)
    {
        setKey(Com_loginuser.FAILEDCOUNT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFailedcount(int[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDCOUNT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFailedcount(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDCOUNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFailedcount(int value, String compcode)
    {
        setKey(Com_loginuser.FAILEDCOUNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。
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
    public void setFailedcount(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDCOUNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFailedcount(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDCOUNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の更新値をセットします。
     * @param value 認証ミス回数(<code>FAILEDCOUNT</code>)更新値
     */
    public void updateFailedcount(int value)
    {
        setAdhocUpdateValue(Com_loginuser.FAILEDCOUNT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。
     * 
     * @param value 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)<br>
     * 日付の検索値を最終認証ミス日時(<code>FAILEDSTARTDATE</code>)にセットします。
     */
    public void setFailedstartdate(Date value)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, value) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFailedstartdate(Date[] values)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, values, true) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFailedstartdate(Date[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setFailedstartdate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, values, and_or_toNext) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setFailedstartdate(Date value, String compcode)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。
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
    public void setFailedstartdate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setFailedstartdate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の更新値をセットします。
     * @param value 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)更新値
     */
    public void updateFailedstartdate(Date value)
    {
        setAdhocUpdateValue(Com_loginuser.FAILEDSTARTDATE, value) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。
     * 
     * @param value 削除ステータス(<code>DELETESTATUS</code>)<br>
     * 数値の検索値を削除ステータス(<code>DELETESTATUS</code>)にセットします。
     */
    public void setDeletestatus(int value)
    {
        setKey(Com_loginuser.DELETESTATUS, HandlerUtil.toObject(value)) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDeletestatus(int[] values)
    {
        setKey(Com_loginuser.DELETESTATUS, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDeletestatus(int[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.DELETESTATUS, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDeletestatus(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DELETESTATUS, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDeletestatus(int value, String compcode)
    {
        setKey(Com_loginuser.DELETESTATUS, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。
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
    public void setDeletestatus(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.DELETESTATUS, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDeletestatus(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DELETESTATUS, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の更新値をセットします。
     * @param value 削除ステータス(<code>DELETESTATUS</code>)更新値
     */
    public void updateDeletestatus(int value)
    {
        setAdhocUpdateValue(Com_loginuser.DELETESTATUS, HandlerUtil.toObject(value)) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。
     * 
     * @param value 削除日付(<code>DELETE_DATE</code>)<br>
     * 日付の検索値を削除日付(<code>DELETE_DATE</code>)にセットします。
     */
    public void setDeleteDate(Date value)
    {
        setKey(Com_loginuser.DELETE_DATE, value) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDeleteDate(Date[] values)
    {
        setKey(Com_loginuser.DELETE_DATE, values, true) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDeleteDate(Date[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.DELETE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDeleteDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DELETE_DATE, values, and_or_toNext) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDeleteDate(Date value, String compcode)
    {
        setKey(Com_loginuser.DELETE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。
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
    public void setDeleteDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.DELETE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDeleteDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DELETE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 削除日付(<code>DELETE_DATE</code>)の更新値をセットします。
     * @param value 削除日付(<code>DELETE_DATE</code>)更新値
     */
    public void updateDeleteDate(Date value)
    {
        setAdhocUpdateValue(Com_loginuser.DELETE_DATE, value) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。
     * 
     * @param value 所属(<code>DEPARTMENT</code>)<br>
     * 文字列の検索値を所属(<code>DEPARTMENT</code>)にセットします。
     */
    public void setDepartment(String value)
    {
        setKey(Com_loginuser.DEPARTMENT, value) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDepartment(String[] values)
    {
        setKey(Com_loginuser.DEPARTMENT, values, true) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDepartment(String[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.DEPARTMENT, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setDepartment(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DEPARTMENT, values, and_or_toNext) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setDepartment(String value, String compcode)
    {
        setKey(Com_loginuser.DEPARTMENT, value, compcode, "", "", true) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。
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
    public void setDepartment(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.DEPARTMENT, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setDepartment(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DEPARTMENT, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 所属(<code>DEPARTMENT</code>)の更新値をセットします。
     * @param value 所属(<code>DEPARTMENT</code>)更新値
     */
    public void updateDepartment(String value)
    {
        setAdhocUpdateValue(Com_loginuser.DEPARTMENT, value) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。
     * 
     * @param value 備考(<code>REMARK</code>)<br>
     * 文字列の検索値を備考(<code>REMARK</code>)にセットします。
     */
    public void setRemark(String value)
    {
        setKey(Com_loginuser.REMARK, value) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRemark(String[] values)
    {
        setKey(Com_loginuser.REMARK, values, true) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRemark(String[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.REMARK, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRemark(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.REMARK, values, and_or_toNext) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRemark(String value, String compcode)
    {
        setKey(Com_loginuser.REMARK, value, compcode, "", "", true) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。
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
    public void setRemark(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.REMARK, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRemark(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.REMARK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 備考(<code>REMARK</code>)の更新値をセットします。
     * @param value 備考(<code>REMARK</code>)更新値
     */
    public void updateRemark(String value)
    {
        setAdhocUpdateValue(Com_loginuser.REMARK, value) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 更新日時(<code>UPDATE_DATE</code>)<br>
     * 日付の検索値を更新日時(<code>UPDATE_DATE</code>)にセットします。
     */
    public void setUpdateDate(Date value)
    {
        setKey(Com_loginuser.UPDATE_DATE, value) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateDate(Date[] values)
    {
        setKey(Com_loginuser.UPDATE_DATE, values, true) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateDate(Date[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateDate(Date value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
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
    public void setUpdateDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新日時(<code>UPDATE_DATE</code>)の更新値をセットします。
     * @param value 更新日時(<code>UPDATE_DATE</code>)更新値
     */
    public void updateUpdateDate(Date value)
    {
        setAdhocUpdateValue(Com_loginuser.UPDATE_DATE, value) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 更新ユーザ(<code>UPDATE_USER</code>)<br>
     * 文字列の検索値を更新ユーザ(<code>UPDATE_USER</code>)にセットします。
     */
    public void setUpdateUser(String value)
    {
        setKey(Com_loginuser.UPDATE_USER, value) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUser(String[] values)
    {
        setKey(Com_loginuser.UPDATE_USER, values, true) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUser(String[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_USER, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateUser(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_USER, values, and_or_toNext) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateUser(String value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_USER, value, compcode, "", "", true) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
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
    public void setUpdateUser(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_USER, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateUser(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_USER, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の更新値をセットします。
     * @param value 更新ユーザ(<code>UPDATE_USER</code>)更新値
     */
    public void updateUpdateUser(String value)
    {
        setAdhocUpdateValue(Com_loginuser.UPDATE_USER, value) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 更新端末IP(<code>UPDATE_TERMINAL</code>)<br>
     * 文字列の検索値を更新端末IP(<code>UPDATE_TERMINAL</code>)にセットします。
     */
    public void setUpdateTerminal(String value)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, value) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTerminal(String[] values)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, values, true) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTerminal(String[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateTerminal(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, values, and_or_toNext) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateTerminal(String value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, value, compcode, "", "", true) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
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
    public void setUpdateTerminal(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateTerminal(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の更新値をセットします。
     * @param value 更新端末IP(<code>UPDATE_TERMINAL</code>)更新値
     */
    public void updateUpdateTerminal(String value)
    {
        setAdhocUpdateValue(Com_loginuser.UPDATE_TERMINAL, value) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 更新区分(<code>UPDATE_KIND</code>)<br>
     * 数値の検索値を更新区分(<code>UPDATE_KIND</code>)にセットします。
     */
    public void setUpdateKind(int value)
    {
        setKey(Com_loginuser.UPDATE_KIND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKind(int[] values)
    {
        setKey(Com_loginuser.UPDATE_KIND, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKind(int[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_KIND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateKind(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_KIND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateKind(int value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_KIND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
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
    public void setUpdateKind(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_KIND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateKind(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_KIND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新区分(<code>UPDATE_KIND</code>)の更新値をセットします。
     * @param value 更新区分(<code>UPDATE_KIND</code>)更新値
     */
    public void updateUpdateKind(int value)
    {
        setAdhocUpdateValue(Com_loginuser.UPDATE_KIND, HandlerUtil.toObject(value)) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 更新処理名(<code>UPDATE_PROCESS</code>)<br>
     * 文字列の検索値を更新処理名(<code>UPDATE_PROCESS</code>)にセットします。
     */
    public void setUpdateProcess(String value)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, value) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateProcess(String[] values)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, values, true) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateProcess(String[] values, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setUpdateProcess(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, values, and_or_toNext) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setUpdateProcess(String value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, value, compcode, "", "", true) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
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
    public void setUpdateProcess(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setUpdateProcess(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の更新値をセットします。
     * @param value 更新処理名(<code>UPDATE_PROCESS</code>)更新値
     */
    public void updateUpdateProcess(String value)
    {
        setAdhocUpdateValue(Com_loginuser.UPDATE_PROCESS, value) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateDummypasswordFlagWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_loginuser.DUMMYPASSWORD_FLAG, source, addvalue);
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateUserstatusWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_loginuser.USERSTATUS, source, addvalue);
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updatePwdchangeintervalWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_loginuser.PWDCHANGEINTERVAL, source, addvalue);
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateSameuserloginmaxWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_loginuser.SAMEUSERLOGINMAX, source, addvalue);
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateFailedloginattemptsWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_loginuser.FAILEDLOGINATTEMPTS, source, addvalue);
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateFailedcountWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_loginuser.FAILEDCOUNT, source, addvalue);
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateDeletestatusWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_loginuser.DELETESTATUS, source, addvalue);
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateUpdateKindWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Com_loginuser.UPDATE_KIND, source, addvalue);
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
        return "$Id: Com_loginuserAlterKey.java 8061 2013-05-24 10:24:10Z kishimoto $" ;
    }
}
