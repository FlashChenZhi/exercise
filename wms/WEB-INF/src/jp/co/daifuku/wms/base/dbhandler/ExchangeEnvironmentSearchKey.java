// $Id: ExchangeEnvironmentSearchKey.java 7201 2010-02-24 02:36:39Z kishimoto $
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
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * EXCHANGEENVIRONMENT用の検索キークラスです。
 *
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class ExchangeEnvironmentSearchKey
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
    public ExchangeEnvironmentSearchKey()
    {
        super(ExchangeEnvironment.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 取込データ区分(<code>JOB_TYPE</code>)<br>
     * 文字列の検索値をセット取込データ区分(<code>JOB_TYPE</code>)します。
     */
    public void setJobType(String value)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, value) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobType(String[] values)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, values, true) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setJobType(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, values, and_or_toNext) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setJobType(String value, String compcode)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
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
    public void setJobType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setJobType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.JOB_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setJobTypeOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.JOB_TYPE, ascorder) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJobTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.JOB_TYPE, ascorder) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)のグループ順をセットします。
     */
    public void setJobTypeGroup()
    {
        setGroup(ExchangeEnvironment.JOB_TYPE) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setJobTypeGroup(int prio)
    {
        setGroup(ExchangeEnvironment.JOB_TYPE) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の情報取得を設定します。
     */
    public void setJobTypeCollect()
    {
        setCollect(ExchangeEnvironment.JOB_TYPE) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setJobTypeCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.JOB_TYPE, sqlfunc, null) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 送受信区分(<code>EXCHANGE_TYPE</code>)<br>
     * 文字列の検索値をセット送受信区分(<code>EXCHANGE_TYPE</code>)します。
     */
    public void setExchangeType(String value)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, value) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setExchangeType(String[] values)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, values, true) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setExchangeType(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setExchangeType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setExchangeType(String value, String compcode)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
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
    public void setExchangeType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setExchangeType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXCHANGE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setExchangeTypeOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.EXCHANGE_TYPE, ascorder) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setExchangeTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.EXCHANGE_TYPE, ascorder) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)のグループ順をセットします。
     */
    public void setExchangeTypeGroup()
    {
        setGroup(ExchangeEnvironment.EXCHANGE_TYPE) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setExchangeTypeGroup(int prio)
    {
        setGroup(ExchangeEnvironment.EXCHANGE_TYPE) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の情報取得を設定します。
     */
    public void setExchangeTypeCollect()
    {
        setCollect(ExchangeEnvironment.EXCHANGE_TYPE) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setExchangeTypeCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.EXCHANGE_TYPE, sqlfunc, null) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 取込種別(<code>DATA_TYPE</code>)<br>
     * 文字列の検索値をセット取込種別(<code>DATA_TYPE</code>)します。
     */
    public void setDataType(String value)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, value) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataType(String[] values)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, values, true) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataType(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDataType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, values, and_or_toNext) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDataType(String value, String compcode)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
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
    public void setDataType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDataType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDataTypeOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.DATA_TYPE, ascorder) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDataTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.DATA_TYPE, ascorder) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)のグループ順をセットします。
     */
    public void setDataTypeGroup()
    {
        setGroup(ExchangeEnvironment.DATA_TYPE) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDataTypeGroup(int prio)
    {
        setGroup(ExchangeEnvironment.DATA_TYPE) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の情報取得を設定します。
     */
    public void setDataTypeCollect()
    {
        setCollect(ExchangeEnvironment.DATA_TYPE) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDataTypeCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.DATA_TYPE, sqlfunc, null) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param value 取込ファイル名(<code>DATA_NAME</code>)<br>
     * 文字列の検索値をセット取込ファイル名(<code>DATA_NAME</code>)します。
     */
    public void setDataName(String value)
    {
        setKey(ExchangeEnvironment.DATA_NAME, value) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataName(String[] values)
    {
        setKey(ExchangeEnvironment.DATA_NAME, values, true) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataName(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDataName(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_NAME, values, and_or_toNext) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDataName(String value, String compcode)
    {
        setKey(ExchangeEnvironment.DATA_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
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
    public void setDataName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDataName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDataNameOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.DATA_NAME, ascorder) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDataNameOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.DATA_NAME, ascorder) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)のグループ順をセットします。
     */
    public void setDataNameGroup()
    {
        setGroup(ExchangeEnvironment.DATA_NAME) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDataNameGroup(int prio)
    {
        setGroup(ExchangeEnvironment.DATA_NAME) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の情報取得を設定します。
     */
    public void setDataNameCollect()
    {
        setCollect(ExchangeEnvironment.DATA_NAME) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDataNameCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.DATA_NAME, sqlfunc, null) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 報告単位(<code>REPORT_TYPE</code>)<br>
     * 文字列の検索値をセット報告単位(<code>REPORT_TYPE</code>)します。
     */
    public void setReportType(String value)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, value) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportType(String[] values)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, values, true) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportType(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReportType(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, values, and_or_toNext) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReportType(String value, String compcode)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
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
    public void setReportType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReportType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.REPORT_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReportTypeOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.REPORT_TYPE, ascorder) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReportTypeOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.REPORT_TYPE, ascorder) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)のグループ順をセットします。
     */
    public void setReportTypeGroup()
    {
        setGroup(ExchangeEnvironment.REPORT_TYPE) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReportTypeGroup(int prio)
    {
        setGroup(ExchangeEnvironment.REPORT_TYPE) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の情報取得を設定します。
     */
    public void setReportTypeCollect()
    {
        setCollect(ExchangeEnvironment.REPORT_TYPE) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReportTypeCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.REPORT_TYPE, sqlfunc, null) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param value ファイル交換フォルダパス(<code>FOLDER_NAME</code>)<br>
     * 文字列の検索値をセットファイル交換フォルダパス(<code>FOLDER_NAME</code>)します。
     */
    public void setFolderName(String value)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, value) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFolderName(String[] values)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, values, true) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFolderName(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFolderName(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, values, and_or_toNext) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setFolderName(String value, String compcode)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
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
    public void setFolderName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFolderName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.FOLDER_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setFolderNameOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.FOLDER_NAME, ascorder) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFolderNameOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.FOLDER_NAME, ascorder) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)のグループ順をセットします。
     */
    public void setFolderNameGroup()
    {
        setGroup(ExchangeEnvironment.FOLDER_NAME) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFolderNameGroup(int prio)
    {
        setGroup(ExchangeEnvironment.FOLDER_NAME) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の情報取得を設定します。
     */
    public void setFolderNameCollect()
    {
        setCollect(ExchangeEnvironment.FOLDER_NAME) ;
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setFolderNameCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.FOLDER_NAME, sqlfunc, null) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param value ファイル名指定方法(<code>IS_PREFIX</code>)<br>
     * 文字列の検索値をセットファイル名指定方法(<code>IS_PREFIX</code>)します。
     */
    public void setIsPrefix(String value)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, value) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIsPrefix(String[] values)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, values, true) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIsPrefix(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIsPrefix(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, values, and_or_toNext) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setIsPrefix(String value, String compcode)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, value, compcode, "", "", true) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
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
    public void setIsPrefix(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setIsPrefix(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.IS_PREFIX, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setIsPrefixOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.IS_PREFIX, ascorder) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setIsPrefixOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.IS_PREFIX, ascorder) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)のグループ順をセットします。
     */
    public void setIsPrefixGroup()
    {
        setGroup(ExchangeEnvironment.IS_PREFIX) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setIsPrefixGroup(int prio)
    {
        setGroup(ExchangeEnvironment.IS_PREFIX) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の情報取得を設定します。
     */
    public void setIsPrefixCollect()
    {
        setCollect(ExchangeEnvironment.IS_PREFIX) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setIsPrefixCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.IS_PREFIX, sqlfunc, null) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param value データID(<code>DATA_ID</code>)<br>
     * 文字列の検索値をセットデータID(<code>DATA_ID</code>)します。
     */
    public void setDataId(String value)
    {
        setKey(ExchangeEnvironment.DATA_ID, value) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataId(String[] values)
    {
        setKey(ExchangeEnvironment.DATA_ID, values, true) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDataId(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDataId(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_ID, values, and_or_toNext) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDataId(String value, String compcode)
    {
        setKey(ExchangeEnvironment.DATA_ID, value, compcode, "", "", true) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
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
    public void setDataId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDataId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.DATA_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * データID(<code>DATA_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDataIdOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.DATA_ID, ascorder) ;
    }

    /**
     * データID(<code>DATA_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDataIdOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.DATA_ID, ascorder) ;
    }

    /**
     * データID(<code>DATA_ID</code>)のグループ順をセットします。
     */
    public void setDataIdGroup()
    {
        setGroup(ExchangeEnvironment.DATA_ID) ;
    }

    /**
     * データID(<code>DATA_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDataIdGroup(int prio)
    {
        setGroup(ExchangeEnvironment.DATA_ID) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の情報取得を設定します。
     */
    public void setDataIdCollect()
    {
        setCollect(ExchangeEnvironment.DATA_ID) ;
    }

    /**
     * データID(<code>DATA_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDataIdCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.DATA_ID, sqlfunc, null) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param value 拡張子(<code>EXTENTION</code>)<br>
     * 文字列の検索値をセット拡張子(<code>EXTENTION</code>)します。
     */
    public void setExtention(String value)
    {
        setKey(ExchangeEnvironment.EXTENTION, value) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setExtention(String[] values)
    {
        setKey(ExchangeEnvironment.EXTENTION, values, true) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setExtention(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXTENTION, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setExtention(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXTENTION, values, and_or_toNext) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setExtention(String value, String compcode)
    {
        setKey(ExchangeEnvironment.EXTENTION, value, compcode, "", "", true) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
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
    public void setExtention(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXTENTION, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setExtention(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.EXTENTION, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setExtentionOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.EXTENTION, ascorder) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setExtentionOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.EXTENTION, ascorder) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)のグループ順をセットします。
     */
    public void setExtentionGroup()
    {
        setGroup(ExchangeEnvironment.EXTENTION) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setExtentionGroup(int prio)
    {
        setGroup(ExchangeEnvironment.EXTENTION) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の情報取得を設定します。
     */
    public void setExtentionCollect()
    {
        setCollect(ExchangeEnvironment.EXTENTION) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setExtentionCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.EXTENTION, sqlfunc, null) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param value 取込可能行数(<code>MAX_RECORD</code>)<br>
     * 数値の検索値をセット取込可能行数(<code>MAX_RECORD</code>)します。
     */
    public void setMaxRecord(int value)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, HandlerUtil.toObject(value)) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxRecord(int[] values)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxRecord(int[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxRecord(int[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMaxRecord(int value, String compcode)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
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
    public void setMaxRecord(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxRecord(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.MAX_RECORD, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMaxRecordOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.MAX_RECORD, ascorder) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxRecordOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.MAX_RECORD, ascorder) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)のグループ順をセットします。
     */
    public void setMaxRecordGroup()
    {
        setGroup(ExchangeEnvironment.MAX_RECORD) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxRecordGroup(int prio)
    {
        setGroup(ExchangeEnvironment.MAX_RECORD) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の情報取得を設定します。
     */
    public void setMaxRecordCollect()
    {
        setCollect(ExchangeEnvironment.MAX_RECORD) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMaxRecordCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.MAX_RECORD, sqlfunc, null) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param value TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)<br>
     * 文字列の検索値をセットTimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)します。
     */
    public void setTimeKeeperPath(String value)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, value) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTimeKeeperPath(String[] values)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, values, true) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setTimeKeeperPath(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTimeKeeperPath(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, values, and_or_toNext) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setTimeKeeperPath(String value, String compcode)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, value, compcode, "", "", true) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
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
    public void setTimeKeeperPath(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setTimeKeeperPath(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.TIME_KEEPER_PATH, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setTimeKeeperPathOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.TIME_KEEPER_PATH, ascorder) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTimeKeeperPathOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.TIME_KEEPER_PATH, ascorder) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)のグループ順をセットします。
     */
    public void setTimeKeeperPathGroup()
    {
        setGroup(ExchangeEnvironment.TIME_KEEPER_PATH) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setTimeKeeperPathGroup(int prio)
    {
        setGroup(ExchangeEnvironment.TIME_KEEPER_PATH) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の情報取得を設定します。
     */
    public void setTimeKeeperPathCollect()
    {
        setCollect(ExchangeEnvironment.TIME_KEEPER_PATH) ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setTimeKeeperPathCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.TIME_KEEPER_PATH, sqlfunc, null) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value クラス名(<code>CLASS_NAME</code>)<br>
     * 文字列の検索値をセットクラス名(<code>CLASS_NAME</code>)します。
     */
    public void setClassName(String value)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, value) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setClassName(String[] values)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, values, true) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setClassName(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setClassName(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, values, and_or_toNext) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setClassName(String value, String compcode)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, value, compcode, "", "", true) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
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
    public void setClassName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setClassName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.CLASS_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setClassNameOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.CLASS_NAME, ascorder) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setClassNameOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.CLASS_NAME, ascorder) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)のグループ順をセットします。
     */
    public void setClassNameGroup()
    {
        setGroup(ExchangeEnvironment.CLASS_NAME) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setClassNameGroup(int prio)
    {
        setGroup(ExchangeEnvironment.CLASS_NAME) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の情報取得を設定します。
     */
    public void setClassNameCollect()
    {
        setCollect(ExchangeEnvironment.CLASS_NAME) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setClassNameCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.CLASS_NAME, sqlfunc, null) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param value エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)<br>
     * 文字列の検索値をセットエラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)します。
     */
    public void setAutoPrintErrorList(String value)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, value) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAutoPrintErrorList(String[] values)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, values, true) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAutoPrintErrorList(String[] values, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAutoPrintErrorList(String[] values, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, values, and_or_toNext) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAutoPrintErrorList(String value, String compcode)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, value, compcode, "", "", true) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
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
    public void setAutoPrintErrorList(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAutoPrintErrorList(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAutoPrintErrorListOrder(boolean ascorder)
    {
        setOrder(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, ascorder) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAutoPrintErrorListOrder(int prio, boolean ascorder)
    {
        setOrder(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, ascorder) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)のグループ順をセットします。
     */
    public void setAutoPrintErrorListGroup()
    {
        setGroup(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAutoPrintErrorListGroup(int prio)
    {
        setGroup(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の情報取得を設定します。
     */
    public void setAutoPrintErrorListCollect()
    {
        setCollect(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST) ;
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAutoPrintErrorListCollect(String sqlfunc)
    {
        setCollect(ExchangeEnvironment.AUTO_PRINT_ERROR_LIST, sqlfunc, null) ;
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
        return "$Id: ExchangeEnvironmentSearchKey.java 7201 2010-02-24 02:36:39Z kishimoto $" ;
    }
}
