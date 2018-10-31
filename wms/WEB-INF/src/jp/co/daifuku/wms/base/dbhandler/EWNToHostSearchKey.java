// $Id: EWNToHostSearchKey.java 7201 2010-02-24 02:36:39Z kishimoto $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * EWNTOHOST用の検索キークラスです。
 *
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class EWNToHostSearchKey
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
    public EWNToHostSearchKey()
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
     * 日付の検索値をセット通信日時(<code>MESSAGE_DATE</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMessageDate(Date[] values, boolean and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_DATE, values, and_or_toNext) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMessageDate(Date value, String compcode)
    {
        setKey(EWNToHost.MESSAGE_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。
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
    public void setMessageDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMessageDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMessageDateOrder(boolean ascorder)
    {
        setOrder(EWNToHost.MESSAGE_DATE, ascorder) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMessageDateOrder(int prio, boolean ascorder)
    {
        setOrder(EWNToHost.MESSAGE_DATE, ascorder) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)のグループ順をセットします。
     */
    public void setMessageDateGroup()
    {
        setGroup(EWNToHost.MESSAGE_DATE) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMessageDateGroup(int prio)
    {
        setGroup(EWNToHost.MESSAGE_DATE) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の情報取得を設定します。
     */
    public void setMessageDateCollect()
    {
        setCollect(EWNToHost.MESSAGE_DATE) ;
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMessageDateCollect(String sqlfunc)
    {
        setCollect(EWNToHost.MESSAGE_DATE, sqlfunc, null) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
     * 
     * @param value シーケンスNo.(<code>SEQUENCE_NO</code>)<br>
     * 数値の検索値をセットシーケンスNo.(<code>SEQUENCE_NO</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSequenceNo(int[] values, boolean and_or_toNext)
    {
        setKey(EWNToHost.SEQUENCE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSequenceNo(int value, String compcode)
    {
        setKey(EWNToHost.SEQUENCE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
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
    public void setSequenceNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(EWNToHost.SEQUENCE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSequenceNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(EWNToHost.SEQUENCE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSequenceNoOrder(boolean ascorder)
    {
        setOrder(EWNToHost.SEQUENCE_NO, ascorder) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSequenceNoOrder(int prio, boolean ascorder)
    {
        setOrder(EWNToHost.SEQUENCE_NO, ascorder) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)のグループ順をセットします。
     */
    public void setSequenceNoGroup()
    {
        setGroup(EWNToHost.SEQUENCE_NO) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSequenceNoGroup(int prio)
    {
        setGroup(EWNToHost.SEQUENCE_NO) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の情報取得を設定します。
     */
    public void setSequenceNoCollect()
    {
        setCollect(EWNToHost.SEQUENCE_NO) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSequenceNoCollect(String sqlfunc)
    {
        setCollect(EWNToHost.SEQUENCE_NO, sqlfunc, null) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
     * 
     * @param value データ区分(<code>MESSAGE_ID</code>)<br>
     * 文字列の検索値をセットデータ区分(<code>MESSAGE_ID</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMessageId(String[] values, boolean and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_ID, values, and_or_toNext) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMessageId(String value, String compcode)
    {
        setKey(EWNToHost.MESSAGE_ID, value, compcode, "", "", true) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
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
    public void setMessageId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMessageId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(EWNToHost.MESSAGE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMessageIdOrder(boolean ascorder)
    {
        setOrder(EWNToHost.MESSAGE_ID, ascorder) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMessageIdOrder(int prio, boolean ascorder)
    {
        setOrder(EWNToHost.MESSAGE_ID, ascorder) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)のグループ順をセットします。
     */
    public void setMessageIdGroup()
    {
        setGroup(EWNToHost.MESSAGE_ID) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMessageIdGroup(int prio)
    {
        setGroup(EWNToHost.MESSAGE_ID) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の情報取得を設定します。
     */
    public void setMessageIdCollect()
    {
        setCollect(EWNToHost.MESSAGE_ID) ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMessageIdCollect(String sqlfunc)
    {
        setCollect(EWNToHost.MESSAGE_ID, sqlfunc, null) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param value 通信データ(<code>DATA</code>)<br>
     * 文字列の検索値をセット通信データ(<code>DATA</code>)します。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setData(String[] values, boolean and_or_toNext)
    {
        setKey(EWNToHost.DATA, values, and_or_toNext) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setData(String value, String compcode)
    {
        setKey(EWNToHost.DATA, value, compcode, "", "", true) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
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
    public void setData(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(EWNToHost.DATA, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setData(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(EWNToHost.DATA, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 通信データ(<code>DATA</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDataOrder(boolean ascorder)
    {
        setOrder(EWNToHost.DATA, ascorder) ;
    }

    /**
     * 通信データ(<code>DATA</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDataOrder(int prio, boolean ascorder)
    {
        setOrder(EWNToHost.DATA, ascorder) ;
    }

    /**
     * 通信データ(<code>DATA</code>)のグループ順をセットします。
     */
    public void setDataGroup()
    {
        setGroup(EWNToHost.DATA) ;
    }

    /**
     * 通信データ(<code>DATA</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDataGroup(int prio)
    {
        setGroup(EWNToHost.DATA) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の情報取得を設定します。
     */
    public void setDataCollect()
    {
        setCollect(EWNToHost.DATA) ;
    }

    /**
     * 通信データ(<code>DATA</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDataCollect(String sqlfunc)
    {
        setCollect(EWNToHost.DATA, sqlfunc, null) ;
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
        return "$Id: EWNToHostSearchKey.java 7201 2010-02-24 02:36:39Z kishimoto $" ;
    }
}
