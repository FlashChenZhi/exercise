// $Id: Com_terminalSearchKey.java 87 2008-10-04 03:07:38Z admin $
// Handler v3.8
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
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * COM_TERMINAL用の検索キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class Com_terminalSearchKey
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
    public Com_terminalSearchKey()
    {
        super(Com_terminal.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param value 端末No.(<code>TERMINALNUMBER</code>)<br>
     * 文字列の検索値をセット端末No.(<code>TERMINALNUMBER</code>)します。
     */
    public void setTerminalnumber(String value)
    {
        setKey(Com_terminal.TERMINALNUMBER, value) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalnumber(String[] values)
    {
        setKey(Com_terminal.TERMINALNUMBER, values, true) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalnumber(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNUMBER, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalnumber(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNUMBER, values, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalnumber(String value, String compcode)
    {
        setKey(Com_terminal.TERMINALNUMBER, value, compcode, "", "", true) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
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
    public void setTerminalnumber(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNUMBER, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalnumber(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNUMBER, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalnumberOrder(boolean ascorder)
    {
        setOrder(Com_terminal.TERMINALNUMBER, ascorder) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalnumberOrder(int prio, boolean ascorder)
    {
        setOrder(Com_terminal.TERMINALNUMBER, ascorder) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)のグループ順をセットします。
     */
    public void setTerminalnumberGroup()
    {
        setGroup(Com_terminal.TERMINALNUMBER) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalnumberGroup(int prio)
    {
        setGroup(Com_terminal.TERMINALNUMBER) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の情報取得を設定します。
     */
    public void setTerminalnumberCollect()
    {
        setCollect(Com_terminal.TERMINALNUMBER) ;
    }

    /**
     * 端末No.(<code>TERMINALNUMBER</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminalnumberCollect(String sqlfunc)
    {
        setCollect(Com_terminal.TERMINALNUMBER, sqlfunc, null) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param value 端末名(<code>TERMINALNAME</code>)<br>
     * 文字列の検索値をセット端末名(<code>TERMINALNAME</code>)します。
     */
    public void setTerminalname(String value)
    {
        setKey(Com_terminal.TERMINALNAME, value) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalname(String[] values)
    {
        setKey(Com_terminal.TERMINALNAME, values, true) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalname(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalname(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNAME, values, and_or_toNext) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminalname(String value, String compcode)
    {
        setKey(Com_terminal.TERMINALNAME, value, compcode, "", "", true) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
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
    public void setTerminalname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminalname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminalnameOrder(boolean ascorder)
    {
        setOrder(Com_terminal.TERMINALNAME, ascorder) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalnameOrder(int prio, boolean ascorder)
    {
        setOrder(Com_terminal.TERMINALNAME, ascorder) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)のグループ順をセットします。
     */
    public void setTerminalnameGroup()
    {
        setGroup(Com_terminal.TERMINALNAME) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminalnameGroup(int prio)
    {
        setGroup(Com_terminal.TERMINALNAME) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の情報取得を設定します。
     */
    public void setTerminalnameCollect()
    {
        setCollect(Com_terminal.TERMINALNAME) ;
    }

    /**
     * 端末名(<code>TERMINALNAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminalnameCollect(String sqlfunc)
    {
        setCollect(Com_terminal.TERMINALNAME, sqlfunc, null) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param value 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)<br>
     * 文字列の検索値をセット端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)します。
     */
    public void setTerminaladdress(String value)
    {
        setKey(Com_terminal.TERMINALADDRESS, value) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminaladdress(String[] values)
    {
        setKey(Com_terminal.TERMINALADDRESS, values, true) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTerminaladdress(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminaladdress(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALADDRESS, values, and_or_toNext) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTerminaladdress(String value, String compcode)
    {
        setKey(Com_terminal.TERMINALADDRESS, value, compcode, "", "", true) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
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
    public void setTerminaladdress(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.TERMINALADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTerminaladdress(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.TERMINALADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTerminaladdressOrder(boolean ascorder)
    {
        setOrder(Com_terminal.TERMINALADDRESS, ascorder) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminaladdressOrder(int prio, boolean ascorder)
    {
        setOrder(Com_terminal.TERMINALADDRESS, ascorder) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)のグループ順をセットします。
     */
    public void setTerminaladdressGroup()
    {
        setGroup(Com_terminal.TERMINALADDRESS) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTerminaladdressGroup(int prio)
    {
        setGroup(Com_terminal.TERMINALADDRESS) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の情報取得を設定します。
     */
    public void setTerminaladdressCollect()
    {
        setCollect(Com_terminal.TERMINALADDRESS) ;
    }

    /**
     * 端末IPアドレスorマシン名(<code>TERMINALADDRESS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTerminaladdressCollect(String sqlfunc)
    {
        setCollect(Com_terminal.TERMINALADDRESS, sqlfunc, null) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value ロールID(<code>ROLEID</code>)<br>
     * 文字列の検索値をセットロールID(<code>ROLEID</code>)します。
     */
    public void setRoleid(String value)
    {
        setKey(Com_terminal.ROLEID, value) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoleid(String[] values)
    {
        setKey(Com_terminal.ROLEID, values, true) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
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
        setKey(Com_terminal.ROLEID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRoleid(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.ROLEID, values, and_or_toNext) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRoleid(String value, String compcode)
    {
        setKey(Com_terminal.ROLEID, value, compcode, "", "", true) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
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
        setKey(Com_terminal.ROLEID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRoleid(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.ROLEID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRoleidOrder(boolean ascorder)
    {
        setOrder(Com_terminal.ROLEID, ascorder) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)のソート順をセットします。
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
        setOrder(Com_terminal.ROLEID, ascorder) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)のグループ順をセットします。
     */
    public void setRoleidGroup()
    {
        setGroup(Com_terminal.ROLEID) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRoleidGroup(int prio)
    {
        setGroup(Com_terminal.ROLEID) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の情報取得を設定します。
     */
    public void setRoleidCollect()
    {
        setCollect(Com_terminal.ROLEID) ;
    }

    /**
     * ロールID(<code>ROLEID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRoleidCollect(String sqlfunc)
    {
        setCollect(Com_terminal.ROLEID, sqlfunc, null) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param value プリンタ名(<code>PRINTERNAME</code>)<br>
     * 文字列の検索値をセットプリンタ名(<code>PRINTERNAME</code>)します。
     */
    public void setPrintername(String value)
    {
        setKey(Com_terminal.PRINTERNAME, value) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrintername(String[] values)
    {
        setKey(Com_terminal.PRINTERNAME, values, true) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrintername(String[] values, String and_or_toNext)
    {
        setKey(Com_terminal.PRINTERNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPrintername(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.PRINTERNAME, values, and_or_toNext) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPrintername(String value, String compcode)
    {
        setKey(Com_terminal.PRINTERNAME, value, compcode, "", "", true) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
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
    public void setPrintername(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.PRINTERNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPrintername(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.PRINTERNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPrinternameOrder(boolean ascorder)
    {
        setOrder(Com_terminal.PRINTERNAME, ascorder) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPrinternameOrder(int prio, boolean ascorder)
    {
        setOrder(Com_terminal.PRINTERNAME, ascorder) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)のグループ順をセットします。
     */
    public void setPrinternameGroup()
    {
        setGroup(Com_terminal.PRINTERNAME) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPrinternameGroup(int prio)
    {
        setGroup(Com_terminal.PRINTERNAME) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の情報取得を設定します。
     */
    public void setPrinternameCollect()
    {
        setCollect(Com_terminal.PRINTERNAME) ;
    }

    /**
     * プリンタ名(<code>PRINTERNAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPrinternameCollect(String sqlfunc)
    {
        setCollect(Com_terminal.PRINTERNAME, sqlfunc, null) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param value 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)<br>
     * 数値の検索値をセット自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)します。
     */
    public void setAutologinFlag(int value)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, HandlerUtil.toObject(value)) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAutologinFlag(int[] values)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAutologinFlag(int[] values, String and_or_toNext)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAutologinFlag(int[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAutologinFlag(int value, String compcode)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
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
    public void setAutologinFlag(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAutologinFlag(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Com_terminal.AUTOLOGIN_FLAG, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAutologinFlagOrder(boolean ascorder)
    {
        setOrder(Com_terminal.AUTOLOGIN_FLAG, ascorder) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAutologinFlagOrder(int prio, boolean ascorder)
    {
        setOrder(Com_terminal.AUTOLOGIN_FLAG, ascorder) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)のグループ順をセットします。
     */
    public void setAutologinFlagGroup()
    {
        setGroup(Com_terminal.AUTOLOGIN_FLAG) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAutologinFlagGroup(int prio)
    {
        setGroup(Com_terminal.AUTOLOGIN_FLAG) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の情報取得を設定します。
     */
    public void setAutologinFlagCollect()
    {
        setCollect(Com_terminal.AUTOLOGIN_FLAG) ;
    }

    /**
     * 自動ログインフラグ(<code>AUTOLOGIN_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAutologinFlagCollect(String sqlfunc)
    {
        setCollect(Com_terminal.AUTOLOGIN_FLAG, sqlfunc, null) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 更新日時(<code>UPDATE_DATE</code>)<br>
     * 日付の検索値をセット更新日時(<code>UPDATE_DATE</code>)します。
     */
    public void setUpdateDate(Date value)
    {
        setKey(Com_terminal.UPDATE_DATE, value) ;
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
        setKey(Com_terminal.UPDATE_DATE, values, true) ;
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
        setKey(Com_terminal.UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateDate(Date value, String compcode)
    {
        setKey(Com_terminal.UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(Com_terminal.UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Com_terminal.UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateDateOrder(boolean ascorder)
    {
        setOrder(Com_terminal.UPDATE_DATE, ascorder) ;
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
        setOrder(Com_terminal.UPDATE_DATE, ascorder) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setUpdateDateGroup()
    {
        setGroup(Com_terminal.UPDATE_DATE) ;
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
        setGroup(Com_terminal.UPDATE_DATE) ;
    }

    /**
     * 更新日時(<code>UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setUpdateDateCollect()
    {
        setCollect(Com_terminal.UPDATE_DATE) ;
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
        setCollect(Com_terminal.UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 更新ユーザ(<code>UPDATE_USER</code>)<br>
     * 文字列の検索値をセット更新ユーザ(<code>UPDATE_USER</code>)します。
     */
    public void setUpdateUser(String value)
    {
        setKey(Com_terminal.UPDATE_USER, value) ;
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
        setKey(Com_terminal.UPDATE_USER, values, true) ;
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
        setKey(Com_terminal.UPDATE_USER, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateUser(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_USER, values, and_or_toNext) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateUser(String value, String compcode)
    {
        setKey(Com_terminal.UPDATE_USER, value, compcode, "", "", true) ;
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
        setKey(Com_terminal.UPDATE_USER, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Com_terminal.UPDATE_USER, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateUserOrder(boolean ascorder)
    {
        setOrder(Com_terminal.UPDATE_USER, ascorder) ;
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
        setOrder(Com_terminal.UPDATE_USER, ascorder) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)のグループ順をセットします。
     */
    public void setUpdateUserGroup()
    {
        setGroup(Com_terminal.UPDATE_USER) ;
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
        setGroup(Com_terminal.UPDATE_USER) ;
    }

    /**
     * 更新ユーザ(<code>UPDATE_USER</code>)の情報取得を設定します。
     */
    public void setUpdateUserCollect()
    {
        setCollect(Com_terminal.UPDATE_USER) ;
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
        setCollect(Com_terminal.UPDATE_USER, sqlfunc, null) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 更新端末IP(<code>UPDATE_TERMINAL</code>)<br>
     * 文字列の検索値をセット更新端末IP(<code>UPDATE_TERMINAL</code>)します。
     */
    public void setUpdateTerminal(String value)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, value) ;
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
        setKey(Com_terminal.UPDATE_TERMINAL, values, true) ;
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
        setKey(Com_terminal.UPDATE_TERMINAL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateTerminal(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, values, and_or_toNext) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateTerminal(String value, String compcode)
    {
        setKey(Com_terminal.UPDATE_TERMINAL, value, compcode, "", "", true) ;
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
        setKey(Com_terminal.UPDATE_TERMINAL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Com_terminal.UPDATE_TERMINAL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateTerminalOrder(boolean ascorder)
    {
        setOrder(Com_terminal.UPDATE_TERMINAL, ascorder) ;
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
        setOrder(Com_terminal.UPDATE_TERMINAL, ascorder) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)のグループ順をセットします。
     */
    public void setUpdateTerminalGroup()
    {
        setGroup(Com_terminal.UPDATE_TERMINAL) ;
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
        setGroup(Com_terminal.UPDATE_TERMINAL) ;
    }

    /**
     * 更新端末IP(<code>UPDATE_TERMINAL</code>)の情報取得を設定します。
     */
    public void setUpdateTerminalCollect()
    {
        setCollect(Com_terminal.UPDATE_TERMINAL) ;
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
        setCollect(Com_terminal.UPDATE_TERMINAL, sqlfunc, null) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 更新区分(<code>UPDATE_KIND</code>)<br>
     * 数値の検索値をセット更新区分(<code>UPDATE_KIND</code>)します。
     */
    public void setUpdateKind(int value)
    {
        setKey(Com_terminal.UPDATE_KIND, HandlerUtil.toObject(value)) ;
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
        setKey(Com_terminal.UPDATE_KIND, ArrayUtil.toObjectArray(values), true) ;
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
        setKey(Com_terminal.UPDATE_KIND, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateKind(int[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_KIND, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateKind(int value, String compcode)
    {
        setKey(Com_terminal.UPDATE_KIND, HandlerUtil.toObject(value), compcode, "", "", true) ;
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
        setKey(Com_terminal.UPDATE_KIND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Com_terminal.UPDATE_KIND, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateKindOrder(boolean ascorder)
    {
        setOrder(Com_terminal.UPDATE_KIND, ascorder) ;
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
        setOrder(Com_terminal.UPDATE_KIND, ascorder) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)のグループ順をセットします。
     */
    public void setUpdateKindGroup()
    {
        setGroup(Com_terminal.UPDATE_KIND) ;
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
        setGroup(Com_terminal.UPDATE_KIND) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>)の情報取得を設定します。
     */
    public void setUpdateKindCollect()
    {
        setCollect(Com_terminal.UPDATE_KIND) ;
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
        setCollect(Com_terminal.UPDATE_KIND, sqlfunc, null) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 更新処理名(<code>UPDATE_PROCESS</code>)<br>
     * 文字列の検索値をセット更新処理名(<code>UPDATE_PROCESS</code>)します。
     */
    public void setUpdateProcess(String value)
    {
        setKey(Com_terminal.UPDATE_PROCESS, value) ;
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
        setKey(Com_terminal.UPDATE_PROCESS, values, true) ;
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
        setKey(Com_terminal.UPDATE_PROCESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setUpdateProcess(String[] values, boolean and_or_toNext)
    {
        setKey(Com_terminal.UPDATE_PROCESS, values, and_or_toNext) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setUpdateProcess(String value, String compcode)
    {
        setKey(Com_terminal.UPDATE_PROCESS, value, compcode, "", "", true) ;
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
        setKey(Com_terminal.UPDATE_PROCESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Com_terminal.UPDATE_PROCESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setUpdateProcessOrder(boolean ascorder)
    {
        setOrder(Com_terminal.UPDATE_PROCESS, ascorder) ;
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
        setOrder(Com_terminal.UPDATE_PROCESS, ascorder) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)のグループ順をセットします。
     */
    public void setUpdateProcessGroup()
    {
        setGroup(Com_terminal.UPDATE_PROCESS) ;
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
        setGroup(Com_terminal.UPDATE_PROCESS) ;
    }

    /**
     * 更新処理名(<code>UPDATE_PROCESS</code>)の情報取得を設定します。
     */
    public void setUpdateProcessCollect()
    {
        setCollect(Com_terminal.UPDATE_PROCESS) ;
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
        setCollect(Com_terminal.UPDATE_PROCESS, sqlfunc, null) ;
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
        return "$Id: Com_terminalSearchKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
