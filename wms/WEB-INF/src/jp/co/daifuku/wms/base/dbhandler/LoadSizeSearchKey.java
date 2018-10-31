// $Id: LoadSizeSearchKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.LoadSize;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * LOADSIZE用の検索キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class LoadSizeSearchKey
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
    public LoadSizeSearchKey()
    {
        super(LoadSize.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 荷姿(<code>LOAD_SIZE</code>)<br>
     * 数値の検索値をセット荷姿(<code>LOAD_SIZE</code>)します。
     */
    public void setLoadSize(int value)
    {
        setKey(LoadSize.LOAD_SIZE, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSize(int[] values)
    {
        setKey(LoadSize.LOAD_SIZE, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSize(int[] values, String and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadSize(int[] values, boolean and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLoadSize(int value, String compcode)
    {
        setKey(LoadSize.LOAD_SIZE, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
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
    public void setLoadSize(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadSize(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLoadSizeOrder(boolean ascorder)
    {
        setOrder(LoadSize.LOAD_SIZE, ascorder) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSizeOrder(int prio, boolean ascorder)
    {
        setOrder(LoadSize.LOAD_SIZE, ascorder) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)のグループ順をセットします。
     */
    public void setLoadSizeGroup()
    {
        setGroup(LoadSize.LOAD_SIZE) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSizeGroup(int prio)
    {
        setGroup(LoadSize.LOAD_SIZE) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の情報取得を設定します。
     */
    public void setLoadSizeCollect()
    {
        setCollect(LoadSize.LOAD_SIZE) ;
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLoadSizeCollect(String sqlfunc)
    {
        setCollect(LoadSize.LOAD_SIZE, sqlfunc, null) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param value 荷姿名称(<code>LOAD_SIZE_NAME</code>)<br>
     * 文字列の検索値をセット荷姿名称(<code>LOAD_SIZE_NAME</code>)します。
     */
    public void setLoadSizeName(String value)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, value) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSizeName(String[] values)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, values, true) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSizeName(String[] values, String and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadSizeName(String[] values, boolean and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, values, and_or_toNext) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLoadSizeName(String value, String compcode)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
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
    public void setLoadSizeName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadSizeName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadSize.LOAD_SIZE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLoadSizeNameOrder(boolean ascorder)
    {
        setOrder(LoadSize.LOAD_SIZE_NAME, ascorder) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSizeNameOrder(int prio, boolean ascorder)
    {
        setOrder(LoadSize.LOAD_SIZE_NAME, ascorder) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)のグループ順をセットします。
     */
    public void setLoadSizeNameGroup()
    {
        setGroup(LoadSize.LOAD_SIZE_NAME) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSizeNameGroup(int prio)
    {
        setGroup(LoadSize.LOAD_SIZE_NAME) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の情報取得を設定します。
     */
    public void setLoadSizeNameCollect()
    {
        setCollect(LoadSize.LOAD_SIZE_NAME) ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLoadSizeNameCollect(String sqlfunc)
    {
        setCollect(LoadSize.LOAD_SIZE_NAME, sqlfunc, null) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param value 荷長(<code>LENGTH</code>)<br>
     * 数値の検索値をセット荷長(<code>LENGTH</code>)します。
     */
    public void setLength(int value)
    {
        setKey(LoadSize.LENGTH, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLength(int[] values)
    {
        setKey(LoadSize.LENGTH, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLength(int[] values, String and_or_toNext)
    {
        setKey(LoadSize.LENGTH, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLength(int[] values, boolean and_or_toNext)
    {
        setKey(LoadSize.LENGTH, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLength(int value, String compcode)
    {
        setKey(LoadSize.LENGTH, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
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
    public void setLength(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadSize.LENGTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLength(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadSize.LENGTH, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLengthOrder(boolean ascorder)
    {
        setOrder(LoadSize.LENGTH, ascorder) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLengthOrder(int prio, boolean ascorder)
    {
        setOrder(LoadSize.LENGTH, ascorder) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)のグループ順をセットします。
     */
    public void setLengthGroup()
    {
        setGroup(LoadSize.LENGTH) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLengthGroup(int prio)
    {
        setGroup(LoadSize.LENGTH) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の情報取得を設定します。
     */
    public void setLengthCollect()
    {
        setCollect(LoadSize.LENGTH) ;
    }

    /**
     * 荷長(<code>LENGTH</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLengthCollect(String sqlfunc)
    {
        setCollect(LoadSize.LENGTH, sqlfunc, null) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 荷高(<code>HEIGHT</code>)<br>
     * 数値の検索値をセット荷高(<code>HEIGHT</code>)します。
     */
    public void setHeight(int value)
    {
        setKey(LoadSize.HEIGHT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHeight(int[] values)
    {
        setKey(LoadSize.HEIGHT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setHeight(int[] values, String and_or_toNext)
    {
        setKey(LoadSize.HEIGHT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHeight(int[] values, boolean and_or_toNext)
    {
        setKey(LoadSize.HEIGHT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setHeight(int value, String compcode)
    {
        setKey(LoadSize.HEIGHT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
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
    public void setHeight(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(LoadSize.HEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setHeight(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(LoadSize.HEIGHT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setHeightOrder(boolean ascorder)
    {
        setOrder(LoadSize.HEIGHT, ascorder) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHeightOrder(int prio, boolean ascorder)
    {
        setOrder(LoadSize.HEIGHT, ascorder) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)のグループ順をセットします。
     */
    public void setHeightGroup()
    {
        setGroup(LoadSize.HEIGHT) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setHeightGroup(int prio)
    {
        setGroup(LoadSize.HEIGHT) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の情報取得を設定します。
     */
    public void setHeightCollect()
    {
        setCollect(LoadSize.HEIGHT) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setHeightCollect(String sqlfunc)
    {
        setCollect(LoadSize.HEIGHT, sqlfunc, null) ;
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
        return "$Id: LoadSizeSearchKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
