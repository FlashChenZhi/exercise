// $Id: GroupControllerSearchKey.java 5424 2009-11-06 10:33:04Z okayama $
// $LastChangedRevision: 5424 $
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
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * GROUPCONTROLLER用の検索キークラスです。
 *
 * @version $Revision: 5424 $, $Date: 2009-11-06 19:33:04 +0900 (金, 06 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */


public class GroupControllerSearchKey
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
    public GroupControllerSearchKey()
    {
        super(GroupController.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value コントローラー番号(<code>CONTROLLER_NO</code>)<br>
     * 文字列の検索値をセットコントローラー番号(<code>CONTROLLER_NO</code>)します。
     */
    public void setControllerNo(String value)
    {
        setKey(GroupController.CONTROLLER_NO, value) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNo(String[] values)
    {
        setKey(GroupController.CONTROLLER_NO, values, true) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNo(String[] values, String and_or_toNext)
    {
        setKey(GroupController.CONTROLLER_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControllerNo(String[] values, boolean and_or_toNext)
    {
        setKey(GroupController.CONTROLLER_NO, values, and_or_toNext) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setControllerNo(String value, String compcode)
    {
        setKey(GroupController.CONTROLLER_NO, value, compcode, "", "", true) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
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
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(GroupController.CONTROLLER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.CONTROLLER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setControllerNoOrder(boolean ascorder)
    {
        setOrder(GroupController.CONTROLLER_NO, ascorder) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNoOrder(int prio, boolean ascorder)
    {
        setOrder(GroupController.CONTROLLER_NO, ascorder) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)のグループ順をセットします。
     */
    public void setControllerNoGroup()
    {
        setGroup(GroupController.CONTROLLER_NO) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNoGroup(int prio)
    {
        setGroup(GroupController.CONTROLLER_NO) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の情報取得を設定します。
     */
    public void setControllerNoCollect()
    {
        setCollect(GroupController.CONTROLLER_NO) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setControllerNoCollect(String sqlfunc)
    {
        setCollect(GroupController.CONTROLLER_NO, sqlfunc, null) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット状態(<code>STATUS_FLAG</code>)します。
     */
    public void setStatusFlag(String value)
    {
        setKey(GroupController.STATUS_FLAG, value) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values)
    {
        setKey(GroupController.STATUS_FLAG, values, true) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values, String and_or_toNext)
    {
        setKey(GroupController.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(GroupController.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(GroupController.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(GroupController.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(GroupController.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlagOrder(int prio, boolean ascorder)
    {
        setOrder(GroupController.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(GroupController.STATUS_FLAG) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlagGroup(int prio)
    {
        setGroup(GroupController.STATUS_FLAG) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(GroupController.STATUS_FLAG) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStatusFlagCollect(String sqlfunc)
    {
        setCollect(GroupController.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value IPアドレス(<code>IPADDRESS</code>)<br>
     * 文字列の検索値をセットIPアドレス(<code>IPADDRESS</code>)します。
     */
    public void setIpaddress(String value)
    {
        setKey(GroupController.IPADDRESS, value) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddress(String[] values)
    {
        setKey(GroupController.IPADDRESS, values, true) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddress(String[] values, String and_or_toNext)
    {
        setKey(GroupController.IPADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIpaddress(String[] values, boolean and_or_toNext)
    {
        setKey(GroupController.IPADDRESS, values, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setIpaddress(String value, String compcode)
    {
        setKey(GroupController.IPADDRESS, value, compcode, "", "", true) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
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
    public void setIpaddress(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(GroupController.IPADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIpaddress(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.IPADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setIpaddressOrder(boolean ascorder)
    {
        setOrder(GroupController.IPADDRESS, ascorder) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddressOrder(int prio, boolean ascorder)
    {
        setOrder(GroupController.IPADDRESS, ascorder) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)のグループ順をセットします。
     */
    public void setIpaddressGroup()
    {
        setGroup(GroupController.IPADDRESS) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddressGroup(int prio)
    {
        setGroup(GroupController.IPADDRESS) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の情報取得を設定します。
     */
    public void setIpaddressCollect()
    {
        setCollect(GroupController.IPADDRESS) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setIpaddressCollect(String sqlfunc)
    {
        setCollect(GroupController.IPADDRESS, sqlfunc, null) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param value ポート番号(<code>PORT</code>)<br>
     * 数値の検索値をセットポート番号(<code>PORT</code>)します。
     */
    public void setPort(int value)
    {
        setKey(GroupController.PORT, HandlerUtil.toObject(value)) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPort(int[] values)
    {
        setKey(GroupController.PORT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPort(int[] values, String and_or_toNext)
    {
        setKey(GroupController.PORT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPort(int[] values, boolean and_or_toNext)
    {
        setKey(GroupController.PORT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPort(int value, String compcode)
    {
        setKey(GroupController.PORT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
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
    public void setPort(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(GroupController.PORT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPort(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.PORT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ポート番号(<code>PORT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPortOrder(boolean ascorder)
    {
        setOrder(GroupController.PORT, ascorder) ;
    }

    /**
     * ポート番号(<code>PORT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPortOrder(int prio, boolean ascorder)
    {
        setOrder(GroupController.PORT, ascorder) ;
    }

    /**
     * ポート番号(<code>PORT</code>)のグループ順をセットします。
     */
    public void setPortGroup()
    {
        setGroup(GroupController.PORT) ;
    }

    /**
     * ポート番号(<code>PORT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPortGroup(int prio)
    {
        setGroup(GroupController.PORT) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の情報取得を設定します。
     */
    public void setPortCollect()
    {
        setCollect(GroupController.PORT) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPortCollect(String sqlfunc)
    {
        setCollect(GroupController.PORT, sqlfunc, null) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param value 作業終了応答受信日時(<code>RECV_DATE</code>)<br>
     * 日付の検索値をセット作業終了応答受信日時(<code>RECV_DATE</code>)します。
     */
    public void setRecvDate(Date value)
    {
        setKey(GroupController.RECV_DATE, value) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRecvDate(Date[] values)
    {
        setKey(GroupController.RECV_DATE, values, true) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRecvDate(Date[] values, String and_or_toNext)
    {
        setKey(GroupController.RECV_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRecvDate(Date[] values, boolean and_or_toNext)
    {
        setKey(GroupController.RECV_DATE, values, and_or_toNext) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRecvDate(Date value, String compcode)
    {
        setKey(GroupController.RECV_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
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
    public void setRecvDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(GroupController.RECV_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRecvDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.RECV_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRecvDateOrder(boolean ascorder)
    {
        setOrder(GroupController.RECV_DATE, ascorder) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRecvDateOrder(int prio, boolean ascorder)
    {
        setOrder(GroupController.RECV_DATE, ascorder) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)のグループ順をセットします。
     */
    public void setRecvDateGroup()
    {
        setGroup(GroupController.RECV_DATE) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRecvDateGroup(int prio)
    {
        setGroup(GroupController.RECV_DATE) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の情報取得を設定します。
     */
    public void setRecvDateCollect()
    {
        setCollect(GroupController.RECV_DATE) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRecvDateCollect(String sqlfunc)
    {
        setCollect(GroupController.RECV_DATE, sqlfunc, null) ;
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
        return "$Id: GroupControllerSearchKey.java 5424 2009-11-06 10:33:04Z okayama $" ;
    }
}
