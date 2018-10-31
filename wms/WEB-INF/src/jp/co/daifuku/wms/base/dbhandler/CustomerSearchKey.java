// $Id: CustomerSearchKey.java 7201 2010-02-24 02:36:39Z kishimoto $
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
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * CUSTOMER用の検索キークラスです。
 *
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class CustomerSearchKey
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
    public CustomerSearchKey()
    {
        super(Customer.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値をセット荷主コード(<code>CONSIGNOR_CODE</code>)します。
     */
    public void setConsignorCode(String value)
    {
        setKey(Customer.CONSIGNOR_CODE, value) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCode(String[] values)
    {
        setKey(Customer.CONSIGNOR_CODE, values, true) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCode(String[] values, String and_or_toNext)
    {
        setKey(Customer.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(Customer.CONSIGNOR_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
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
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(Customer.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCodeOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(Customer.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setConsignorCodeGroup(int prio)
    {
        setGroup(Customer.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(Customer.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setConsignorCodeCollect(String sqlfunc)
    {
        setCollect(Customer.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 出荷先コード(<code>CUSTOMER_CODE</code>)<br>
     * 文字列の検索値をセット出荷先コード(<code>CUSTOMER_CODE</code>)します。
     */
    public void setCustomerCode(String value)
    {
        setKey(Customer.CUSTOMER_CODE, value) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCode(String[] values)
    {
        setKey(Customer.CUSTOMER_CODE, values, true) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCode(String[] values, String and_or_toNext)
    {
        setKey(Customer.CUSTOMER_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCustomerCode(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.CUSTOMER_CODE, values, and_or_toNext) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCustomerCode(String value, String compcode)
    {
        setKey(Customer.CUSTOMER_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
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
    public void setCustomerCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.CUSTOMER_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCustomerCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.CUSTOMER_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCustomerCodeOrder(boolean ascorder)
    {
        setOrder(Customer.CUSTOMER_CODE, ascorder) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCodeOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.CUSTOMER_CODE, ascorder) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)のグループ順をセットします。
     */
    public void setCustomerCodeGroup()
    {
        setGroup(Customer.CUSTOMER_CODE) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerCodeGroup(int prio)
    {
        setGroup(Customer.CUSTOMER_CODE) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の情報取得を設定します。
     */
    public void setCustomerCodeCollect()
    {
        setCollect(Customer.CUSTOMER_CODE) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCustomerCodeCollect(String sqlfunc)
    {
        setCollect(Customer.CUSTOMER_CODE, sqlfunc, null) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 出荷先名(<code>CUSTOMER_NAME</code>)<br>
     * 文字列の検索値をセット出荷先名(<code>CUSTOMER_NAME</code>)します。
     */
    public void setCustomerName(String value)
    {
        setKey(Customer.CUSTOMER_NAME, value) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerName(String[] values)
    {
        setKey(Customer.CUSTOMER_NAME, values, true) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerName(String[] values, String and_or_toNext)
    {
        setKey(Customer.CUSTOMER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCustomerName(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.CUSTOMER_NAME, values, and_or_toNext) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCustomerName(String value, String compcode)
    {
        setKey(Customer.CUSTOMER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)の検索値をセットします。
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
    public void setCustomerName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.CUSTOMER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCustomerName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.CUSTOMER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCustomerNameOrder(boolean ascorder)
    {
        setOrder(Customer.CUSTOMER_NAME, ascorder) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerNameOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.CUSTOMER_NAME, ascorder) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)のグループ順をセットします。
     */
    public void setCustomerNameGroup()
    {
        setGroup(Customer.CUSTOMER_NAME) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCustomerNameGroup(int prio)
    {
        setGroup(Customer.CUSTOMER_NAME) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)の情報取得を設定します。
     */
    public void setCustomerNameCollect()
    {
        setCollect(Customer.CUSTOMER_NAME) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCustomerNameCollect(String sqlfunc)
    {
        setCollect(Customer.CUSTOMER_NAME, sqlfunc, null) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)の検索値をセットします。
     * 
     * @param value 運送業者コード(<code>CARRIER_CODE</code>)<br>
     * 文字列の検索値をセット運送業者コード(<code>CARRIER_CODE</code>)します。
     */
    public void setCarrierCode(String value)
    {
        setKey(Customer.CARRIER_CODE, value) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarrierCode(String[] values)
    {
        setKey(Customer.CARRIER_CODE, values, true) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarrierCode(String[] values, String and_or_toNext)
    {
        setKey(Customer.CARRIER_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCarrierCode(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.CARRIER_CODE, values, and_or_toNext) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCarrierCode(String value, String compcode)
    {
        setKey(Customer.CARRIER_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)の検索値をセットします。
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
    public void setCarrierCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.CARRIER_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCarrierCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.CARRIER_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCarrierCodeOrder(boolean ascorder)
    {
        setOrder(Customer.CARRIER_CODE, ascorder) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCarrierCodeOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.CARRIER_CODE, ascorder) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)のグループ順をセットします。
     */
    public void setCarrierCodeGroup()
    {
        setGroup(Customer.CARRIER_CODE) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCarrierCodeGroup(int prio)
    {
        setGroup(Customer.CARRIER_CODE) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)の情報取得を設定します。
     */
    public void setCarrierCodeCollect()
    {
        setCollect(Customer.CARRIER_CODE) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCarrierCodeCollect(String sqlfunc)
    {
        setCollect(Customer.CARRIER_CODE, sqlfunc, null) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)の検索値をセットします。
     * 
     * @param value 運送業者名称(<code>CARRIER_NAME</code>)<br>
     * 文字列の検索値をセット運送業者名称(<code>CARRIER_NAME</code>)します。
     */
    public void setCarrierName(String value)
    {
        setKey(Customer.CARRIER_NAME, value) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarrierName(String[] values)
    {
        setKey(Customer.CARRIER_NAME, values, true) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarrierName(String[] values, String and_or_toNext)
    {
        setKey(Customer.CARRIER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCarrierName(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.CARRIER_NAME, values, and_or_toNext) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCarrierName(String value, String compcode)
    {
        setKey(Customer.CARRIER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)の検索値をセットします。
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
    public void setCarrierName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.CARRIER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCarrierName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.CARRIER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCarrierNameOrder(boolean ascorder)
    {
        setOrder(Customer.CARRIER_NAME, ascorder) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCarrierNameOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.CARRIER_NAME, ascorder) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)のグループ順をセットします。
     */
    public void setCarrierNameGroup()
    {
        setGroup(Customer.CARRIER_NAME) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCarrierNameGroup(int prio)
    {
        setGroup(Customer.CARRIER_NAME) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)の情報取得を設定します。
     */
    public void setCarrierNameCollect()
    {
        setCollect(Customer.CARRIER_NAME) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCarrierNameCollect(String sqlfunc)
    {
        setCollect(Customer.CARRIER_NAME, sqlfunc, null) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param value ルート(<code>ROUTE</code>)<br>
     * 文字列の検索値をセットルート(<code>ROUTE</code>)します。
     */
    public void setRoute(String value)
    {
        setKey(Customer.ROUTE, value) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoute(String[] values)
    {
        setKey(Customer.ROUTE, values, true) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRoute(String[] values, String and_or_toNext)
    {
        setKey(Customer.ROUTE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRoute(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.ROUTE, values, and_or_toNext) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRoute(String value, String compcode)
    {
        setKey(Customer.ROUTE, value, compcode, "", "", true) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
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
    public void setRoute(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.ROUTE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRoute(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.ROUTE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ルート(<code>ROUTE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRouteOrder(boolean ascorder)
    {
        setOrder(Customer.ROUTE, ascorder) ;
    }

    /**
     * ルート(<code>ROUTE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRouteOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.ROUTE, ascorder) ;
    }

    /**
     * ルート(<code>ROUTE</code>)のグループ順をセットします。
     */
    public void setRouteGroup()
    {
        setGroup(Customer.ROUTE) ;
    }

    /**
     * ルート(<code>ROUTE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRouteGroup(int prio)
    {
        setGroup(Customer.ROUTE) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の情報取得を設定します。
     */
    public void setRouteCollect()
    {
        setCollect(Customer.ROUTE) ;
    }

    /**
     * ルート(<code>ROUTE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRouteCollect(String sqlfunc)
    {
        setCollect(Customer.ROUTE, sqlfunc, null) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param value 郵便番号(<code>POSTAL_CODE</code>)<br>
     * 文字列の検索値をセット郵便番号(<code>POSTAL_CODE</code>)します。
     */
    public void setPostalCode(String value)
    {
        setKey(Customer.POSTAL_CODE, value) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPostalCode(String[] values)
    {
        setKey(Customer.POSTAL_CODE, values, true) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPostalCode(String[] values, String and_or_toNext)
    {
        setKey(Customer.POSTAL_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPostalCode(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.POSTAL_CODE, values, and_or_toNext) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPostalCode(String value, String compcode)
    {
        setKey(Customer.POSTAL_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
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
    public void setPostalCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.POSTAL_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPostalCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.POSTAL_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPostalCodeOrder(boolean ascorder)
    {
        setOrder(Customer.POSTAL_CODE, ascorder) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPostalCodeOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.POSTAL_CODE, ascorder) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)のグループ順をセットします。
     */
    public void setPostalCodeGroup()
    {
        setGroup(Customer.POSTAL_CODE) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPostalCodeGroup(int prio)
    {
        setGroup(Customer.POSTAL_CODE) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の情報取得を設定します。
     */
    public void setPostalCodeCollect()
    {
        setCollect(Customer.POSTAL_CODE) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPostalCodeCollect(String sqlfunc)
    {
        setCollect(Customer.POSTAL_CODE, sqlfunc, null) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param value 都道府県名(<code>PREFECTURE</code>)<br>
     * 文字列の検索値をセット都道府県名(<code>PREFECTURE</code>)します。
     */
    public void setPrefecture(String value)
    {
        setKey(Customer.PREFECTURE, value) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrefecture(String[] values)
    {
        setKey(Customer.PREFECTURE, values, true) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPrefecture(String[] values, String and_or_toNext)
    {
        setKey(Customer.PREFECTURE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPrefecture(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.PREFECTURE, values, and_or_toNext) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPrefecture(String value, String compcode)
    {
        setKey(Customer.PREFECTURE, value, compcode, "", "", true) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
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
    public void setPrefecture(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.PREFECTURE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPrefecture(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.PREFECTURE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPrefectureOrder(boolean ascorder)
    {
        setOrder(Customer.PREFECTURE, ascorder) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPrefectureOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.PREFECTURE, ascorder) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)のグループ順をセットします。
     */
    public void setPrefectureGroup()
    {
        setGroup(Customer.PREFECTURE) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPrefectureGroup(int prio)
    {
        setGroup(Customer.PREFECTURE) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の情報取得を設定します。
     */
    public void setPrefectureCollect()
    {
        setCollect(Customer.PREFECTURE) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPrefectureCollect(String sqlfunc)
    {
        setCollect(Customer.PREFECTURE, sqlfunc, null) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param value 住所1(<code>ADDRESS1</code>)<br>
     * 文字列の検索値をセット住所1(<code>ADDRESS1</code>)します。
     */
    public void setAddress1(String value)
    {
        setKey(Customer.ADDRESS1, value) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddress1(String[] values)
    {
        setKey(Customer.ADDRESS1, values, true) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddress1(String[] values, String and_or_toNext)
    {
        setKey(Customer.ADDRESS1, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAddress1(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.ADDRESS1, values, and_or_toNext) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAddress1(String value, String compcode)
    {
        setKey(Customer.ADDRESS1, value, compcode, "", "", true) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)の検索値をセットします。
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
    public void setAddress1(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.ADDRESS1, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAddress1(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.ADDRESS1, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAddress1Order(boolean ascorder)
    {
        setOrder(Customer.ADDRESS1, ascorder) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAddress1Order(int prio, boolean ascorder)
    {
        setOrder(Customer.ADDRESS1, ascorder) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)のグループ順をセットします。
     */
    public void setAddress1Group()
    {
        setGroup(Customer.ADDRESS1) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAddress1Group(int prio)
    {
        setGroup(Customer.ADDRESS1) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)の情報取得を設定します。
     */
    public void setAddress1Collect()
    {
        setCollect(Customer.ADDRESS1) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAddress1Collect(String sqlfunc)
    {
        setCollect(Customer.ADDRESS1, sqlfunc, null) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param value ﾋﾞﾙ名等(<code>ADDRESS2</code>)<br>
     * 文字列の検索値をセットﾋﾞﾙ名等(<code>ADDRESS2</code>)します。
     */
    public void setAddress2(String value)
    {
        setKey(Customer.ADDRESS2, value) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddress2(String[] values)
    {
        setKey(Customer.ADDRESS2, values, true) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAddress2(String[] values, String and_or_toNext)
    {
        setKey(Customer.ADDRESS2, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAddress2(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.ADDRESS2, values, and_or_toNext) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAddress2(String value, String compcode)
    {
        setKey(Customer.ADDRESS2, value, compcode, "", "", true) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)の検索値をセットします。
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
    public void setAddress2(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.ADDRESS2, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAddress2(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.ADDRESS2, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAddress2Order(boolean ascorder)
    {
        setOrder(Customer.ADDRESS2, ascorder) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAddress2Order(int prio, boolean ascorder)
    {
        setOrder(Customer.ADDRESS2, ascorder) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)のグループ順をセットします。
     */
    public void setAddress2Group()
    {
        setGroup(Customer.ADDRESS2) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAddress2Group(int prio)
    {
        setGroup(Customer.ADDRESS2) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)の情報取得を設定します。
     */
    public void setAddress2Collect()
    {
        setCollect(Customer.ADDRESS2) ;
    }

    /**
     * ﾋﾞﾙ名等(<code>ADDRESS2</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAddress2Collect(String sqlfunc)
    {
        setCollect(Customer.ADDRESS2, sqlfunc, null) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param value TEL(<code>TELEPHONE</code>)<br>
     * 文字列の検索値をセットTEL(<code>TELEPHONE</code>)します。
     */
    public void setTelephone(String value)
    {
        setKey(Customer.TELEPHONE, value) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTelephone(String[] values)
    {
        setKey(Customer.TELEPHONE, values, true) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTelephone(String[] values, String and_or_toNext)
    {
        setKey(Customer.TELEPHONE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTelephone(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.TELEPHONE, values, and_or_toNext) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTelephone(String value, String compcode)
    {
        setKey(Customer.TELEPHONE, value, compcode, "", "", true) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
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
    public void setTelephone(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.TELEPHONE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTelephone(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.TELEPHONE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTelephoneOrder(boolean ascorder)
    {
        setOrder(Customer.TELEPHONE, ascorder) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTelephoneOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.TELEPHONE, ascorder) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)のグループ順をセットします。
     */
    public void setTelephoneGroup()
    {
        setGroup(Customer.TELEPHONE) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTelephoneGroup(int prio)
    {
        setGroup(Customer.TELEPHONE) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の情報取得を設定します。
     */
    public void setTelephoneCollect()
    {
        setCollect(Customer.TELEPHONE) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTelephoneCollect(String sqlfunc)
    {
        setCollect(Customer.TELEPHONE, sqlfunc, null) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param value 連絡先1(<code>CONTACT1</code>)<br>
     * 文字列の検索値をセット連絡先1(<code>CONTACT1</code>)します。
     */
    public void setContact1(String value)
    {
        setKey(Customer.CONTACT1, value) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setContact1(String[] values)
    {
        setKey(Customer.CONTACT1, values, true) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setContact1(String[] values, String and_or_toNext)
    {
        setKey(Customer.CONTACT1, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setContact1(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.CONTACT1, values, and_or_toNext) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setContact1(String value, String compcode)
    {
        setKey(Customer.CONTACT1, value, compcode, "", "", true) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)の検索値をセットします。
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
    public void setContact1(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.CONTACT1, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setContact1(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.CONTACT1, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setContact1Order(boolean ascorder)
    {
        setOrder(Customer.CONTACT1, ascorder) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setContact1Order(int prio, boolean ascorder)
    {
        setOrder(Customer.CONTACT1, ascorder) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)のグループ順をセットします。
     */
    public void setContact1Group()
    {
        setGroup(Customer.CONTACT1) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setContact1Group(int prio)
    {
        setGroup(Customer.CONTACT1) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)の情報取得を設定します。
     */
    public void setContact1Collect()
    {
        setCollect(Customer.CONTACT1) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setContact1Collect(String sqlfunc)
    {
        setCollect(Customer.CONTACT1, sqlfunc, null) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param value 連絡先2(<code>CONTACT2</code>)<br>
     * 文字列の検索値をセット連絡先2(<code>CONTACT2</code>)します。
     */
    public void setContact2(String value)
    {
        setKey(Customer.CONTACT2, value) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setContact2(String[] values)
    {
        setKey(Customer.CONTACT2, values, true) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setContact2(String[] values, String and_or_toNext)
    {
        setKey(Customer.CONTACT2, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setContact2(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.CONTACT2, values, and_or_toNext) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setContact2(String value, String compcode)
    {
        setKey(Customer.CONTACT2, value, compcode, "", "", true) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)の検索値をセットします。
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
    public void setContact2(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.CONTACT2, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setContact2(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.CONTACT2, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setContact2Order(boolean ascorder)
    {
        setOrder(Customer.CONTACT2, ascorder) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setContact2Order(int prio, boolean ascorder)
    {
        setOrder(Customer.CONTACT2, ascorder) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)のグループ順をセットします。
     */
    public void setContact2Group()
    {
        setGroup(Customer.CONTACT2) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setContact2Group(int prio)
    {
        setGroup(Customer.CONTACT2) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)の情報取得を設定します。
     */
    public void setContact2Collect()
    {
        setCollect(Customer.CONTACT2) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setContact2Collect(String sqlfunc)
    {
        setCollect(Customer.CONTACT2, sqlfunc, null) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param value 仕分場所(<code>SORTING_PLACE</code>)<br>
     * 文字列の検索値をセット仕分場所(<code>SORTING_PLACE</code>)します。
     */
    public void setSortingPlace(String value)
    {
        setKey(Customer.SORTING_PLACE, value) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPlace(String[] values)
    {
        setKey(Customer.SORTING_PLACE, values, true) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPlace(String[] values, String and_or_toNext)
    {
        setKey(Customer.SORTING_PLACE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSortingPlace(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.SORTING_PLACE, values, and_or_toNext) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSortingPlace(String value, String compcode)
    {
        setKey(Customer.SORTING_PLACE, value, compcode, "", "", true) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
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
    public void setSortingPlace(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.SORTING_PLACE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSortingPlace(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.SORTING_PLACE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSortingPlaceOrder(boolean ascorder)
    {
        setOrder(Customer.SORTING_PLACE, ascorder) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPlaceOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.SORTING_PLACE, ascorder) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)のグループ順をセットします。
     */
    public void setSortingPlaceGroup()
    {
        setGroup(Customer.SORTING_PLACE) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSortingPlaceGroup(int prio)
    {
        setGroup(Customer.SORTING_PLACE) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の情報取得を設定します。
     */
    public void setSortingPlaceCollect()
    {
        setCollect(Customer.SORTING_PLACE) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSortingPlaceCollect(String sqlfunc)
    {
        setCollect(Customer.SORTING_PLACE, sqlfunc, null) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終使用日(<code>LAST_USED_DATE</code>)<br>
     * 日付の検索値をセット最終使用日(<code>LAST_USED_DATE</code>)します。
     */
    public void setLastUsedDate(Date value)
    {
        setKey(Customer.LAST_USED_DATE, value) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedDate(Date[] values)
    {
        setKey(Customer.LAST_USED_DATE, values, true) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedDate(Date[] values, String and_or_toNext)
    {
        setKey(Customer.LAST_USED_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUsedDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Customer.LAST_USED_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUsedDate(Date value, String compcode)
    {
        setKey(Customer.LAST_USED_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
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
    public void setLastUsedDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.LAST_USED_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUsedDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.LAST_USED_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUsedDateOrder(boolean ascorder)
    {
        setOrder(Customer.LAST_USED_DATE, ascorder) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedDateOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.LAST_USED_DATE, ascorder) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)のグループ順をセットします。
     */
    public void setLastUsedDateGroup()
    {
        setGroup(Customer.LAST_USED_DATE) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedDateGroup(int prio)
    {
        setGroup(Customer.LAST_USED_DATE) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の情報取得を設定します。
     */
    public void setLastUsedDateCollect()
    {
        setCollect(Customer.LAST_USED_DATE) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLastUsedDateCollect(String sqlfunc)
    {
        setCollect(Customer.LAST_USED_DATE, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
     */
    public void setRegistDate(Date value)
    {
        setKey(Customer.REGIST_DATE, value) ;
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
        setKey(Customer.REGIST_DATE, values, true) ;
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
        setKey(Customer.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Customer.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(Customer.REGIST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
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
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(Customer.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDateOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(Customer.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistDateGroup(int prio)
    {
        setGroup(Customer.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(Customer.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRegistDateCollect(String sqlfunc)
    {
        setCollect(Customer.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
     */
    public void setRegistPname(String value)
    {
        setKey(Customer.REGIST_PNAME, value) ;
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
        setKey(Customer.REGIST_PNAME, values, true) ;
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
        setKey(Customer.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(Customer.REGIST_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
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
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(Customer.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPnameOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(Customer.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistPnameGroup(int prio)
    {
        setGroup(Customer.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(Customer.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRegistPnameCollect(String sqlfunc)
    {
        setCollect(Customer.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(Customer.LAST_UPDATE_DATE, value) ;
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
        setKey(Customer.LAST_UPDATE_DATE, values, true) ;
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
        setKey(Customer.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Customer.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(Customer.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
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
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(Customer.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDateOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(Customer.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdateDateGroup(int prio)
    {
        setGroup(Customer.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(Customer.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLastUpdateDateCollect(String sqlfunc)
    {
        setCollect(Customer.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(Customer.LAST_UPDATE_PNAME, value) ;
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
        setKey(Customer.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(Customer.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(Customer.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(Customer.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
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
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Customer.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Customer.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(Customer.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePnameOrder(int prio, boolean ascorder)
    {
        setOrder(Customer.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(Customer.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUpdatePnameGroup(int prio)
    {
        setGroup(Customer.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(Customer.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLastUpdatePnameCollect(String sqlfunc)
    {
        setCollect(Customer.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: CustomerSearchKey.java 7201 2010-02-24 02:36:39Z kishimoto $" ;
    }
}
