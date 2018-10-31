// $Id: Com_loginuserSearchKey.java 8061 2013-05-24 10:24:10Z kishimoto $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * COM_LOGINUSER用の検索キークラスです。
 *
 * @version $Revision: 8061 $, $Date: 2013-05-24 19:24:10 +0900 (金, 24 5 2013) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class Com_loginuserSearchKey
        extends DefaultSQLSearchKey
{
    //------------------------------------------------------------
    // class variables (Prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //  public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 対象のテーブル名とカラムのリストを準備して、インスタンスを
     * 生成します。
     */
    public Com_loginuserSearchKey()
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
     * 文字列の検索値をセットユーザID(<code>USERID</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserid(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERID, values, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserid(String value, String compcode)
    {
        setKey(Com_loginuser.USERID, value, compcode, "", "", true) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserid(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザID(<code>USERID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUseridOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.USERID, ascorder) ;
    }

    /**
     * ユーザID(<code>USERID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUseridOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.USERID, ascorder) ;
    }

    /**
     * ユーザID(<code>USERID</code>)のグループ順をセットします。
     */
    public void setUseridGroup()
    {
        setGroup(Com_loginuser.USERID) ;
    }

    /**
     * ユーザID(<code>USERID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUseridGroup(int prio)
    {
        setGroup(Com_loginuser.USERID) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の情報取得を設定します。
     */
    public void setUseridCollect()
    {
        setCollect(Com_loginuser.USERID) ;
    }

    /**
     * ユーザID(<code>USERID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUseridCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.USERID, sqlfunc, null) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。
     * 
     * @param value パスワード(<code>PASSWORD</code>)<br>
     * 文字列の検索値をセットパスワード(<code>PASSWORD</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPassword(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PASSWORD, values, and_or_toNext) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPassword(String value, String compcode)
    {
        setKey(Com_loginuser.PASSWORD, value, compcode, "", "", true) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPassword(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PASSWORD, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPasswordOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.PASSWORD, ascorder) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPasswordOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.PASSWORD, ascorder) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)のグループ順をセットします。
     */
    public void setPasswordGroup()
    {
        setGroup(Com_loginuser.PASSWORD) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPasswordGroup(int prio)
    {
        setGroup(Com_loginuser.PASSWORD) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の情報取得を設定します。
     */
    public void setPasswordCollect()
    {
        setCollect(Com_loginuser.PASSWORD) ;
    }

    /**
     * パスワード(<code>PASSWORD</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPasswordCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.PASSWORD, sqlfunc, null) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。
     * 
     * @param value ユーザ名(<code>USERNAME</code>)<br>
     * 文字列の検索値をセットユーザ名(<code>USERNAME</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUsername(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERNAME, values, and_or_toNext) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUsername(String value, String compcode)
    {
        setKey(Com_loginuser.USERNAME, value, compcode, "", "", true) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUsername(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUsernameOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.USERNAME, ascorder) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUsernameOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.USERNAME, ascorder) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)のグループ順をセットします。
     */
    public void setUsernameGroup()
    {
        setGroup(Com_loginuser.USERNAME) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUsernameGroup(int prio)
    {
        setGroup(Com_loginuser.USERNAME) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の情報取得を設定します。
     */
    public void setUsernameCollect()
    {
        setCollect(Com_loginuser.USERNAME) ;
    }

    /**
     * ユーザ名(<code>USERNAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUsernameCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.USERNAME, sqlfunc, null) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value ロール(<code>ROLEID</code>)<br>
     * 文字列の検索値をセットロール(<code>ROLEID</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRoleid(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.ROLEID, values, and_or_toNext) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRoleid(String value, String compcode)
    {
        setKey(Com_loginuser.ROLEID, value, compcode, "", "", true) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRoleid(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.ROLEID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ロール(<code>ROLEID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRoleidOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.ROLEID, ascorder) ;
    }

    /**
     * ロール(<code>ROLEID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRoleidOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.ROLEID, ascorder) ;
    }

    /**
     * ロール(<code>ROLEID</code>)のグループ順をセットします。
     */
    public void setRoleidGroup()
    {
        setGroup(Com_loginuser.ROLEID) ;
    }

    /**
     * ロール(<code>ROLEID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRoleidGroup(int prio)
    {
        setGroup(Com_loginuser.ROLEID) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の情報取得を設定します。
     */
    public void setRoleidCollect()
    {
        setCollect(Com_loginuser.ROLEID) ;
    }

    /**
     * ロール(<code>ROLEID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRoleidCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.ROLEID, sqlfunc, null) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)<br>
     * 数値の検索値をセット仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDummypasswordFlag(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDummypasswordFlag(int value, String compcode)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDummypasswordFlag(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DUMMYPASSWORD_FLAG, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDummypasswordFlagOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.DUMMYPASSWORD_FLAG, ascorder) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDummypasswordFlagOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.DUMMYPASSWORD_FLAG, ascorder) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)のグループ順をセットします。
     */
    public void setDummypasswordFlagGroup()
    {
        setGroup(Com_loginuser.DUMMYPASSWORD_FLAG) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDummypasswordFlagGroup(int prio)
    {
        setGroup(Com_loginuser.DUMMYPASSWORD_FLAG) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の情報取得を設定します。
     */
    public void setDummypasswordFlagCollect()
    {
        setCollect(Com_loginuser.DUMMYPASSWORD_FLAG) ;
    }

    /**
     * 仮パスワードフラグ(<code>DUMMYPASSWORD_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDummypasswordFlagCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.DUMMYPASSWORD_FLAG, sqlfunc, null) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。
     * 
     * @param value ユーザ状態(<code>USERSTATUS</code>)<br>
     * 数値の検索値をセットユーザ状態(<code>USERSTATUS</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserstatus(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERSTATUS, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUserstatus(int value, String compcode)
    {
        setKey(Com_loginuser.USERSTATUS, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUserstatus(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.USERSTATUS, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUserstatusOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.USERSTATUS, ascorder) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUserstatusOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.USERSTATUS, ascorder) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)のグループ順をセットします。
     */
    public void setUserstatusGroup()
    {
        setGroup(Com_loginuser.USERSTATUS) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUserstatusGroup(int prio)
    {
        setGroup(Com_loginuser.USERSTATUS) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の情報取得を設定します。
     */
    public void setUserstatusCollect()
    {
        setCollect(Com_loginuser.USERSTATUS) ;
    }

    /**
     * ユーザ状態(<code>USERSTATUS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUserstatusCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.USERSTATUS, sqlfunc, null) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。
     * 
     * @param value パスワード有効期限(<code>PWDEXPIRES</code>)<br>
     * 日付の検索値をセットパスワード有効期限(<code>PWDEXPIRES</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPwdexpires(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PWDEXPIRES, values, and_or_toNext) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPwdexpires(Date value, String compcode)
    {
        setKey(Com_loginuser.PWDEXPIRES, value, compcode, "", "", true) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPwdexpires(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PWDEXPIRES, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPwdexpiresOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.PWDEXPIRES, ascorder) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPwdexpiresOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.PWDEXPIRES, ascorder) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)のグループ順をセットします。
     */
    public void setPwdexpiresGroup()
    {
        setGroup(Com_loginuser.PWDEXPIRES) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPwdexpiresGroup(int prio)
    {
        setGroup(Com_loginuser.PWDEXPIRES) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の情報取得を設定します。
     */
    public void setPwdexpiresCollect()
    {
        setCollect(Com_loginuser.PWDEXPIRES) ;
    }

    /**
     * パスワード有効期限(<code>PWDEXPIRES</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPwdexpiresCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.PWDEXPIRES, sqlfunc, null) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。
     * 
     * @param value パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)<br>
     * 数値の検索値をセットパスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPwdchangeinterval(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPwdchangeinterval(int value, String compcode)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPwdchangeinterval(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.PWDCHANGEINTERVAL, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPwdchangeintervalOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.PWDCHANGEINTERVAL, ascorder) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPwdchangeintervalOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.PWDCHANGEINTERVAL, ascorder) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)のグループ順をセットします。
     */
    public void setPwdchangeintervalGroup()
    {
        setGroup(Com_loginuser.PWDCHANGEINTERVAL) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPwdchangeintervalGroup(int prio)
    {
        setGroup(Com_loginuser.PWDCHANGEINTERVAL) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の情報取得を設定します。
     */
    public void setPwdchangeintervalCollect()
    {
        setCollect(Com_loginuser.PWDCHANGEINTERVAL) ;
    }

    /**
     * パスワード有効期間日数(<code>PWDCHANGEINTERVAL</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPwdchangeintervalCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.PWDCHANGEINTERVAL, sqlfunc, null) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。
     * 
     * @param value 最終ログイン日時(<code>LASTACCESSDATE</code>)<br>
     * 日付の検索値をセット最終ログイン日時(<code>LASTACCESSDATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastaccessdate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.LASTACCESSDATE, values, and_or_toNext) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastaccessdate(Date value, String compcode)
    {
        setKey(Com_loginuser.LASTACCESSDATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastaccessdate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.LASTACCESSDATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastaccessdateOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.LASTACCESSDATE, ascorder) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastaccessdateOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.LASTACCESSDATE, ascorder) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)のグループ順をセットします。
     */
    public void setLastaccessdateGroup()
    {
        setGroup(Com_loginuser.LASTACCESSDATE) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastaccessdateGroup(int prio)
    {
        setGroup(Com_loginuser.LASTACCESSDATE) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の情報取得を設定します。
     */
    public void setLastaccessdateCollect()
    {
        setCollect(Com_loginuser.LASTACCESSDATE) ;
    }

    /**
     * 最終ログイン日時(<code>LASTACCESSDATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLastaccessdateCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.LASTACCESSDATE, sqlfunc, null) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。
     * 
     * @param value 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)<br>
     * 数値の検索値をセット同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSameuserloginmax(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSameuserloginmax(int value, String compcode)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSameuserloginmax(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.SAMEUSERLOGINMAX, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSameuserloginmaxOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.SAMEUSERLOGINMAX, ascorder) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSameuserloginmaxOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.SAMEUSERLOGINMAX, ascorder) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)のグループ順をセットします。
     */
    public void setSameuserloginmaxGroup()
    {
        setGroup(Com_loginuser.SAMEUSERLOGINMAX) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSameuserloginmaxGroup(int prio)
    {
        setGroup(Com_loginuser.SAMEUSERLOGINMAX) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の情報取得を設定します。
     */
    public void setSameuserloginmaxCollect()
    {
        setCollect(Com_loginuser.SAMEUSERLOGINMAX) ;
    }

    /**
     * 同一ユーザログイン最大数(<code>SAMEUSERLOGINMAX</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSameuserloginmaxCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.SAMEUSERLOGINMAX, sqlfunc, null) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。
     * 
     * @param value 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)<br>
     * 数値の検索値をセット認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFailedloginattempts(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setFailedloginattempts(int value, String compcode)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFailedloginattempts(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDLOGINATTEMPTS, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setFailedloginattemptsOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.FAILEDLOGINATTEMPTS, ascorder) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFailedloginattemptsOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.FAILEDLOGINATTEMPTS, ascorder) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)のグループ順をセットします。
     */
    public void setFailedloginattemptsGroup()
    {
        setGroup(Com_loginuser.FAILEDLOGINATTEMPTS) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFailedloginattemptsGroup(int prio)
    {
        setGroup(Com_loginuser.FAILEDLOGINATTEMPTS) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の情報取得を設定します。
     */
    public void setFailedloginattemptsCollect()
    {
        setCollect(Com_loginuser.FAILEDLOGINATTEMPTS) ;
    }

    /**
     * 認証ミス猶予回数(<code>FAILEDLOGINATTEMPTS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setFailedloginattemptsCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.FAILEDLOGINATTEMPTS, sqlfunc, null) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。
     * 
     * @param value 認証ミス回数(<code>FAILEDCOUNT</code>)<br>
     * 数値の検索値をセット認証ミス回数(<code>FAILEDCOUNT</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFailedcount(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDCOUNT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setFailedcount(int value, String compcode)
    {
        setKey(Com_loginuser.FAILEDCOUNT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFailedcount(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDCOUNT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setFailedcountOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.FAILEDCOUNT, ascorder) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFailedcountOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.FAILEDCOUNT, ascorder) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)のグループ順をセットします。
     */
    public void setFailedcountGroup()
    {
        setGroup(Com_loginuser.FAILEDCOUNT) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFailedcountGroup(int prio)
    {
        setGroup(Com_loginuser.FAILEDCOUNT) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の情報取得を設定します。
     */
    public void setFailedcountCollect()
    {
        setCollect(Com_loginuser.FAILEDCOUNT) ;
    }

    /**
     * 認証ミス回数(<code>FAILEDCOUNT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setFailedcountCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.FAILEDCOUNT, sqlfunc, null) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。
     * 
     * @param value 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)<br>
     * 日付の検索値をセット最終認証ミス日時(<code>FAILEDSTARTDATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFailedstartdate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, values, and_or_toNext) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setFailedstartdate(Date value, String compcode)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFailedstartdate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.FAILEDSTARTDATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setFailedstartdateOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.FAILEDSTARTDATE, ascorder) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFailedstartdateOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.FAILEDSTARTDATE, ascorder) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)のグループ順をセットします。
     */
    public void setFailedstartdateGroup()
    {
        setGroup(Com_loginuser.FAILEDSTARTDATE) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFailedstartdateGroup(int prio)
    {
        setGroup(Com_loginuser.FAILEDSTARTDATE) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の情報取得を設定します。
     */
    public void setFailedstartdateCollect()
    {
        setCollect(Com_loginuser.FAILEDSTARTDATE) ;
    }

    /**
     * 最終認証ミス日時(<code>FAILEDSTARTDATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setFailedstartdateCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.FAILEDSTARTDATE, sqlfunc, null) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。
     * 
     * @param value 削除ステータス(<code>DELETESTATUS</code>)<br>
     * 数値の検索値をセット削除ステータス(<code>DELETESTATUS</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDeletestatus(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DELETESTATUS, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDeletestatus(int value, String compcode)
    {
        setKey(Com_loginuser.DELETESTATUS, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDeletestatus(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DELETESTATUS, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDeletestatusOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.DELETESTATUS, ascorder) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDeletestatusOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.DELETESTATUS, ascorder) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)のグループ順をセットします。
     */
    public void setDeletestatusGroup()
    {
        setGroup(Com_loginuser.DELETESTATUS) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDeletestatusGroup(int prio)
    {
        setGroup(Com_loginuser.DELETESTATUS) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の情報取得を設定します。
     */
    public void setDeletestatusCollect()
    {
        setCollect(Com_loginuser.DELETESTATUS) ;
    }

    /**
     * 削除ステータス(<code>DELETESTATUS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDeletestatusCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.DELETESTATUS, sqlfunc, null) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。
     * 
     * @param value 削除日付(<code>DELETE_DATE</code>)<br>
     * 日付の検索値をセット削除日付(<code>DELETE_DATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDeleteDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DELETE_DATE, values, and_or_toNext) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDeleteDate(Date value, String compcode)
    {
        setKey(Com_loginuser.DELETE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDeleteDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DELETE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDeleteDateOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.DELETE_DATE, ascorder) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDeleteDateOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.DELETE_DATE, ascorder) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)のグループ順をセットします。
     */
    public void setDeleteDateGroup()
    {
        setGroup(Com_loginuser.DELETE_DATE) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDeleteDateGroup(int prio)
    {
        setGroup(Com_loginuser.DELETE_DATE) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の情報取得を設定します。
     */
    public void setDeleteDateCollect()
    {
        setCollect(Com_loginuser.DELETE_DATE) ;
    }

    /**
     * 削除日付(<code>DELETE_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDeleteDateCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.DELETE_DATE, sqlfunc, null) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。
     * 
     * @param value 所属(<code>DEPARTMENT</code>)<br>
     * 文字列の検索値をセット所属(<code>DEPARTMENT</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDepartment(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DEPARTMENT, values, and_or_toNext) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDepartment(String value, String compcode)
    {
        setKey(Com_loginuser.DEPARTMENT, value, compcode, "", "", true) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDepartment(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.DEPARTMENT, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDepartmentOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.DEPARTMENT, ascorder) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDepartmentOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.DEPARTMENT, ascorder) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)のグループ順をセットします。
     */
    public void setDepartmentGroup()
    {
        setGroup(Com_loginuser.DEPARTMENT) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDepartmentGroup(int prio)
    {
        setGroup(Com_loginuser.DEPARTMENT) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の情報取得を設定します。
     */
    public void setDepartmentCollect()
    {
        setCollect(Com_loginuser.DEPARTMENT) ;
    }

    /**
     * 所属(<code>DEPARTMENT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDepartmentCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.DEPARTMENT, sqlfunc, null) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。
     * 
     * @param value 備考(<code>REMARK</code>)<br>
     * 文字列の検索値をセット備考(<code>REMARK</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRemark(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.REMARK, values, and_or_toNext) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRemark(String value, String compcode)
    {
        setKey(Com_loginuser.REMARK, value, compcode, "", "", true) ;
    }

    /**
     * 備考(<code>REMARK</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRemark(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.REMARK, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 備考(<code>REMARK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRemarkOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.REMARK, ascorder) ;
    }

    /**
     * 備考(<code>REMARK</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRemarkOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.REMARK, ascorder) ;
    }

    /**
     * 備考(<code>REMARK</code>)のグループ順をセットします。
     */
    public void setRemarkGroup()
    {
        setGroup(Com_loginuser.REMARK) ;
    }

    /**
     * 備考(<code>REMARK</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRemarkGroup(int prio)
    {
        setGroup(Com_loginuser.REMARK) ;
    }

    /**
     * 備考(<code>REMARK</code>)の情報取得を設定します。
     */
    public void setRemarkCollect()
    {
        setCollect(Com_loginuser.REMARK) ;
    }

    /**
     * 備考(<code>REMARK</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRemarkCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.REMARK, sqlfunc, null) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 更新日時(<code>UPDATE_DATE</code>)<br>
     * 日付の検索値をセット更新日時(<code>UPDATE_DATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateDate(Date value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateDateOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_DATE, ascorder) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateDateOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_DATE, ascorder) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setUpdateDateGroup()
    {
        setGroup(Com_loginuser.UPDATE_DATE) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateDateGroup(int prio)
    {
        setGroup(Com_loginuser.UPDATE_DATE) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setUpdateDateCollect()
    {
        setCollect(Com_loginuser.UPDATE_DATE) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateDateCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 更新ユーザ(<code>UPDATE_USER</code>)<br>
     * 文字列の検索値をセット更新ユーザ(<code>UPDATE_USER</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateUser(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_USER, values, and_or_toNext) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateUser(String value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_USER, value, compcode, "", "", true) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateUser(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_USER, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateUserOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_USER, ascorder) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUserOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_USER, ascorder) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)のグループ順をセットします。
     */
    public void setUpdateUserGroup()
    {
        setGroup(Com_loginuser.UPDATE_USER) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateUserGroup(int prio)
    {
        setGroup(Com_loginuser.UPDATE_USER) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の情報取得を設定します。
     */
    public void setUpdateUserCollect()
    {
        setCollect(Com_loginuser.UPDATE_USER) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateUserCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.UPDATE_USER, sqlfunc, null) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 更新端末IP(<code>UPDATE_TERMINAL</code>)<br>
     * 文字列の検索値をセット更新端末IP(<code>UPDATE_TERMINAL</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateTerminal(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, values, and_or_toNext) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateTerminal(String value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, value, compcode, "", "", true) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateTerminal(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_TERMINAL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateTerminalOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_TERMINAL, ascorder) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTerminalOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_TERMINAL, ascorder) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)のグループ順をセットします。
     */
    public void setUpdateTerminalGroup()
    {
        setGroup(Com_loginuser.UPDATE_TERMINAL) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateTerminalGroup(int prio)
    {
        setGroup(Com_loginuser.UPDATE_TERMINAL) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の情報取得を設定します。
     */
    public void setUpdateTerminalCollect()
    {
        setCollect(Com_loginuser.UPDATE_TERMINAL) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateTerminalCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.UPDATE_TERMINAL, sqlfunc, null) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 更新区分(<code>UPDATE_KIND</code>)<br>
     * 数値の検索値をセット更新区分(<code>UPDATE_KIND</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateKind(int[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_KIND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateKind(int value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_KIND, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateKind(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_KIND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateKindOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_KIND, ascorder) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKindOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_KIND, ascorder) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のグループ順をセットします。
     */
    public void setUpdateKindGroup()
    {
        setGroup(Com_loginuser.UPDATE_KIND) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateKindGroup(int prio)
    {
        setGroup(Com_loginuser.UPDATE_KIND) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の情報取得を設定します。
     */
    public void setUpdateKindCollect()
    {
        setCollect(Com_loginuser.UPDATE_KIND) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateKindCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.UPDATE_KIND, sqlfunc, null) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 更新処理名(<code>UPDATE_PROCESS</code>)<br>
     * 文字列の検索値をセット更新処理名(<code>UPDATE_PROCESS</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateProcess(String[] values, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, values, and_or_toNext) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateProcess(String value, String compcode)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, value, compcode, "", "", true) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
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
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateProcess(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_loginuser.UPDATE_PROCESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateProcessOrder(boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_PROCESS, ascorder) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateProcessOrder(int prio, boolean ascorder)
    {
        setOrder(Com_loginuser.UPDATE_PROCESS, ascorder) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)のグループ順をセットします。
     */
    public void setUpdateProcessGroup()
    {
        setGroup(Com_loginuser.UPDATE_PROCESS) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setUpdateProcessGroup(int prio)
    {
        setGroup(Com_loginuser.UPDATE_PROCESS) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の情報取得を設定します。
     */
    public void setUpdateProcessCollect()
    {
        setCollect(Com_loginuser.UPDATE_PROCESS) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setUpdateProcessCollect(String sqlfunc)
    {
        setCollect(Com_loginuser.UPDATE_PROCESS, sqlfunc, null) ;
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
        return "$Id: Com_loginuserSearchKey.java 8061 2013-05-24 10:24:10Z kishimoto $" ;
    }
}
