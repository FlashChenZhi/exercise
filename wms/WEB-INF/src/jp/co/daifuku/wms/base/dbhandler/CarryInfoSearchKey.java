// $Id: CarryInfoSearchKey.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * CARRYINFO用の検索キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class CarryInfoSearchKey
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
    public CarryInfoSearchKey()
    {
        super(CarryInfo.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param value 搬送Key(<code>CARRY_KEY</code>)<br>
     * 文字列の検索値をセット搬送Key(<code>CARRY_KEY</code>)します。
     */
    public void setCarryKey(String value)
    {
        setKey(CarryInfo.CARRY_KEY, value) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarryKey(String[] values)
    {
        setKey(CarryInfo.CARRY_KEY, values, true) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarryKey(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CARRY_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCarryKey(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CARRY_KEY, values, and_or_toNext) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCarryKey(String value, String compcode)
    {
        setKey(CarryInfo.CARRY_KEY, value, compcode, "", "", true) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
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
    public void setCarryKey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CARRY_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCarryKey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CARRY_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCarryKeyOrder(boolean ascorder)
    {
        setOrder(CarryInfo.CARRY_KEY, ascorder) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCarryKeyOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.CARRY_KEY, ascorder) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)のグループ順をセットします。
     */
    public void setCarryKeyGroup()
    {
        setGroup(CarryInfo.CARRY_KEY) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCarryKeyGroup(int prio)
    {
        setGroup(CarryInfo.CARRY_KEY) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の情報取得を設定します。
     */
    public void setCarryKeyCollect()
    {
        setCollect(CarryInfo.CARRY_KEY) ;
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCarryKeyCollect(String sqlfunc)
    {
        setCollect(CarryInfo.CARRY_KEY, sqlfunc, null) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param value パレットID(<code>PALLET_ID</code>)<br>
     * 文字列の検索値をセットパレットID(<code>PALLET_ID</code>)します。
     */
    public void setPalletId(String value)
    {
        setKey(CarryInfo.PALLET_ID, value) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPalletId(String[] values)
    {
        setKey(CarryInfo.PALLET_ID, values, true) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPalletId(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.PALLET_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPalletId(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.PALLET_ID, values, and_or_toNext) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPalletId(String value, String compcode)
    {
        setKey(CarryInfo.PALLET_ID, value, compcode, "", "", true) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
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
    public void setPalletId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.PALLET_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPalletId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.PALLET_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPalletIdOrder(boolean ascorder)
    {
        setOrder(CarryInfo.PALLET_ID, ascorder) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPalletIdOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.PALLET_ID, ascorder) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)のグループ順をセットします。
     */
    public void setPalletIdGroup()
    {
        setGroup(CarryInfo.PALLET_ID) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPalletIdGroup(int prio)
    {
        setGroup(CarryInfo.PALLET_ID) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の情報取得を設定します。
     */
    public void setPalletIdCollect()
    {
        setCollect(CarryInfo.PALLET_ID) ;
    }

    /**
     * パレットID(<code>PALLET_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPalletIdCollect(String sqlfunc)
    {
        setCollect(CarryInfo.PALLET_ID, sqlfunc, null) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業種別(<code>WORK_TYPE</code>)<br>
     * 文字列の検索値をセット作業種別(<code>WORK_TYPE</code>)します。
     */
    public void setWorkType(String value)
    {
        setKey(CarryInfo.WORK_TYPE, value) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkType(String[] values)
    {
        setKey(CarryInfo.WORK_TYPE, values, true) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkType(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.WORK_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkType(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.WORK_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkType(String value, String compcode)
    {
        setKey(CarryInfo.WORK_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
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
    public void setWorkType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.WORK_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.WORK_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkTypeOrder(boolean ascorder)
    {
        setOrder(CarryInfo.WORK_TYPE, ascorder) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkTypeOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.WORK_TYPE, ascorder) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)のグループ順をセットします。
     */
    public void setWorkTypeGroup()
    {
        setGroup(CarryInfo.WORK_TYPE) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkTypeGroup(int prio)
    {
        setGroup(CarryInfo.WORK_TYPE) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の情報取得を設定します。
     */
    public void setWorkTypeCollect()
    {
        setCollect(CarryInfo.WORK_TYPE) ;
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkTypeCollect(String sqlfunc)
    {
        setCollect(CarryInfo.WORK_TYPE, sqlfunc, null) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param value 出庫グループNo.(<code>GROUP_NO</code>)<br>
     * 数値の検索値をセット出庫グループNo.(<code>GROUP_NO</code>)します。
     */
    public void setGroupNo(int value)
    {
        setKey(CarryInfo.GROUP_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setGroupNo(int[] values)
    {
        setKey(CarryInfo.GROUP_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setGroupNo(int[] values, String and_or_toNext)
    {
        setKey(CarryInfo.GROUP_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setGroupNo(int[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.GROUP_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setGroupNo(int value, String compcode)
    {
        setKey(CarryInfo.GROUP_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
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
    public void setGroupNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.GROUP_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setGroupNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.GROUP_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setGroupNoOrder(boolean ascorder)
    {
        setOrder(CarryInfo.GROUP_NO, ascorder) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setGroupNoOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.GROUP_NO, ascorder) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)のグループ順をセットします。
     */
    public void setGroupNoGroup()
    {
        setGroup(CarryInfo.GROUP_NO) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setGroupNoGroup(int prio)
    {
        setGroup(CarryInfo.GROUP_NO) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の情報取得を設定します。
     */
    public void setGroupNoCollect()
    {
        setCollect(CarryInfo.GROUP_NO) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setGroupNoCollect(String sqlfunc)
    {
        setCollect(CarryInfo.GROUP_NO, sqlfunc, null) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param value 搬送状態(<code>CMD_STATUS</code>)<br>
     * 文字列の検索値をセット搬送状態(<code>CMD_STATUS</code>)します。
     */
    public void setCmdStatus(String value)
    {
        setKey(CarryInfo.CMD_STATUS, value) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCmdStatus(String[] values)
    {
        setKey(CarryInfo.CMD_STATUS, values, true) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCmdStatus(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CMD_STATUS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCmdStatus(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CMD_STATUS, values, and_or_toNext) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCmdStatus(String value, String compcode)
    {
        setKey(CarryInfo.CMD_STATUS, value, compcode, "", "", true) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
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
    public void setCmdStatus(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CMD_STATUS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCmdStatus(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CMD_STATUS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCmdStatusOrder(boolean ascorder)
    {
        setOrder(CarryInfo.CMD_STATUS, ascorder) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCmdStatusOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.CMD_STATUS, ascorder) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)のグループ順をセットします。
     */
    public void setCmdStatusGroup()
    {
        setGroup(CarryInfo.CMD_STATUS) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCmdStatusGroup(int prio)
    {
        setGroup(CarryInfo.CMD_STATUS) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の情報取得を設定します。
     */
    public void setCmdStatusCollect()
    {
        setCollect(CarryInfo.CMD_STATUS) ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCmdStatusCollect(String sqlfunc)
    {
        setCollect(CarryInfo.CMD_STATUS, sqlfunc, null) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 優先区分(<code>PRIORITY</code>)<br>
     * 文字列の検索値をセット優先区分(<code>PRIORITY</code>)します。
     */
    public void setPriority(String value)
    {
        setKey(CarryInfo.PRIORITY, value) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(String[] values)
    {
        setKey(CarryInfo.PRIORITY, values, true) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPriority(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.PRIORITY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPriority(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.PRIORITY, values, and_or_toNext) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPriority(String value, String compcode)
    {
        setKey(CarryInfo.PRIORITY, value, compcode, "", "", true) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
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
    public void setPriority(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.PRIORITY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPriority(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.PRIORITY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPriorityOrder(boolean ascorder)
    {
        setOrder(CarryInfo.PRIORITY, ascorder) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPriorityOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.PRIORITY, ascorder) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)のグループ順をセットします。
     */
    public void setPriorityGroup()
    {
        setGroup(CarryInfo.PRIORITY) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPriorityGroup(int prio)
    {
        setGroup(CarryInfo.PRIORITY) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の情報取得を設定します。
     */
    public void setPriorityCollect()
    {
        setCollect(CarryInfo.PRIORITY) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPriorityCollect(String sqlfunc)
    {
        setCollect(CarryInfo.PRIORITY, sqlfunc, null) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 再入庫区分(<code>RESTORING_FLAG</code>)<br>
     * 文字列の検索値をセット再入庫区分(<code>RESTORING_FLAG</code>)します。
     */
    public void setRestoringFlag(String value)
    {
        setKey(CarryInfo.RESTORING_FLAG, value) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringFlag(String[] values)
    {
        setKey(CarryInfo.RESTORING_FLAG, values, true) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringFlag(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.RESTORING_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRestoringFlag(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.RESTORING_FLAG, values, and_or_toNext) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRestoringFlag(String value, String compcode)
    {
        setKey(CarryInfo.RESTORING_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
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
    public void setRestoringFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.RESTORING_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRestoringFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.RESTORING_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRestoringFlagOrder(boolean ascorder)
    {
        setOrder(CarryInfo.RESTORING_FLAG, ascorder) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringFlagOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.RESTORING_FLAG, ascorder) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)のグループ順をセットします。
     */
    public void setRestoringFlagGroup()
    {
        setGroup(CarryInfo.RESTORING_FLAG) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringFlagGroup(int prio)
    {
        setGroup(CarryInfo.RESTORING_FLAG) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の情報取得を設定します。
     */
    public void setRestoringFlagCollect()
    {
        setCollect(CarryInfo.RESTORING_FLAG) ;
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRestoringFlagCollect(String sqlfunc)
    {
        setCollect(CarryInfo.RESTORING_FLAG, sqlfunc, null) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param value 搬送区分(<code>CARRY_FLAG</code>)<br>
     * 文字列の検索値をセット搬送区分(<code>CARRY_FLAG</code>)します。
     */
    public void setCarryFlag(String value)
    {
        setKey(CarryInfo.CARRY_FLAG, value) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarryFlag(String[] values)
    {
        setKey(CarryInfo.CARRY_FLAG, values, true) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCarryFlag(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CARRY_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCarryFlag(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CARRY_FLAG, values, and_or_toNext) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCarryFlag(String value, String compcode)
    {
        setKey(CarryInfo.CARRY_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
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
    public void setCarryFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CARRY_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCarryFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CARRY_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCarryFlagOrder(boolean ascorder)
    {
        setOrder(CarryInfo.CARRY_FLAG, ascorder) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCarryFlagOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.CARRY_FLAG, ascorder) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)のグループ順をセットします。
     */
    public void setCarryFlagGroup()
    {
        setGroup(CarryInfo.CARRY_FLAG) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCarryFlagGroup(int prio)
    {
        setGroup(CarryInfo.CARRY_FLAG) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の情報取得を設定します。
     */
    public void setCarryFlagCollect()
    {
        setCollect(CarryInfo.CARRY_FLAG) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCarryFlagCollect(String sqlfunc)
    {
        setCollect(CarryInfo.CARRY_FLAG, sqlfunc, null) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)<br>
     * 文字列の検索値をセット出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)します。
     */
    public void setRetrievalStationNo(String value)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, value) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStationNo(String[] values)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, values, true) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalStationNo(String value, String compcode)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
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
    public void setRetrievalStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalStationNoOrder(boolean ascorder)
    {
        setOrder(CarryInfo.RETRIEVAL_STATION_NO, ascorder) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.RETRIEVAL_STATION_NO, ascorder) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)のグループ順をセットします。
     */
    public void setRetrievalStationNoGroup()
    {
        setGroup(CarryInfo.RETRIEVAL_STATION_NO) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalStationNoGroup(int prio)
    {
        setGroup(CarryInfo.RETRIEVAL_STATION_NO) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の情報取得を設定します。
     */
    public void setRetrievalStationNoCollect()
    {
        setCollect(CarryInfo.RETRIEVAL_STATION_NO) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalStationNoCollect(String sqlfunc)
    {
        setCollect(CarryInfo.RETRIEVAL_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param value 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)<br>
     * 文字列の検索値をセット出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)します。
     */
    public void setRetrievalDetail(String value)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, value) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDetail(String[] values)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, values, true) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDetail(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalDetail(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, values, and_or_toNext) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalDetail(String value, String compcode)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, value, compcode, "", "", true) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
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
    public void setRetrievalDetail(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalDetail(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.RETRIEVAL_DETAIL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalDetailOrder(boolean ascorder)
    {
        setOrder(CarryInfo.RETRIEVAL_DETAIL, ascorder) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDetailOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.RETRIEVAL_DETAIL, ascorder) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)のグループ順をセットします。
     */
    public void setRetrievalDetailGroup()
    {
        setGroup(CarryInfo.RETRIEVAL_DETAIL) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalDetailGroup(int prio)
    {
        setGroup(CarryInfo.RETRIEVAL_DETAIL) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の情報取得を設定します。
     */
    public void setRetrievalDetailCollect()
    {
        setCollect(CarryInfo.RETRIEVAL_DETAIL) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalDetailCollect(String sqlfunc)
    {
        setCollect(CarryInfo.RETRIEVAL_DETAIL, sqlfunc, null) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param value 作業No.(<code>WORK_NO</code>)<br>
     * 文字列の検索値をセット作業No.(<code>WORK_NO</code>)します。
     */
    public void setWorkNo(String value)
    {
        setKey(CarryInfo.WORK_NO, value) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNo(String[] values)
    {
        setKey(CarryInfo.WORK_NO, values, true) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.WORK_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.WORK_NO, values, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkNo(String value, String compcode)
    {
        setKey(CarryInfo.WORK_NO, value, compcode, "", "", true) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
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
    public void setWorkNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.WORK_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.WORK_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkNoOrder(boolean ascorder)
    {
        setOrder(CarryInfo.WORK_NO, ascorder) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNoOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.WORK_NO, ascorder) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)のグループ順をセットします。
     */
    public void setWorkNoGroup()
    {
        setGroup(CarryInfo.WORK_NO) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkNoGroup(int prio)
    {
        setGroup(CarryInfo.WORK_NO) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の情報取得を設定します。
     */
    public void setWorkNoCollect()
    {
        setCollect(CarryInfo.WORK_NO) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkNoCollect(String sqlfunc)
    {
        setCollect(CarryInfo.WORK_NO, sqlfunc, null) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)<br>
     * 文字列の検索値をセット搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)します。
     */
    public void setSourceStationNo(String value)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, value) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSourceStationNo(String[] values)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, values, true) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSourceStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSourceStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSourceStationNo(String value, String compcode)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
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
    public void setSourceStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSourceStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.SOURCE_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSourceStationNoOrder(boolean ascorder)
    {
        setOrder(CarryInfo.SOURCE_STATION_NO, ascorder) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSourceStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.SOURCE_STATION_NO, ascorder) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)のグループ順をセットします。
     */
    public void setSourceStationNoGroup()
    {
        setGroup(CarryInfo.SOURCE_STATION_NO) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSourceStationNoGroup(int prio)
    {
        setGroup(CarryInfo.SOURCE_STATION_NO) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の情報取得を設定します。
     */
    public void setSourceStationNoCollect()
    {
        setCollect(CarryInfo.SOURCE_STATION_NO) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSourceStationNoCollect(String sqlfunc)
    {
        setCollect(CarryInfo.SOURCE_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)<br>
     * 文字列の検索値をセット搬送先ステーションNo.(<code>DEST_STATION_NO</code>)します。
     */
    public void setDestStationNo(String value)
    {
        setKey(CarryInfo.DEST_STATION_NO, value) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDestStationNo(String[] values)
    {
        setKey(CarryInfo.DEST_STATION_NO, values, true) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDestStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.DEST_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDestStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.DEST_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDestStationNo(String value, String compcode)
    {
        setKey(CarryInfo.DEST_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
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
    public void setDestStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.DEST_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDestStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.DEST_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDestStationNoOrder(boolean ascorder)
    {
        setOrder(CarryInfo.DEST_STATION_NO, ascorder) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDestStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.DEST_STATION_NO, ascorder) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)のグループ順をセットします。
     */
    public void setDestStationNoGroup()
    {
        setGroup(CarryInfo.DEST_STATION_NO) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDestStationNoGroup(int prio)
    {
        setGroup(CarryInfo.DEST_STATION_NO) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の情報取得を設定します。
     */
    public void setDestStationNoCollect()
    {
        setCollect(CarryInfo.DEST_STATION_NO) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDestStationNoCollect(String sqlfunc)
    {
        setCollect(CarryInfo.DEST_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param value 到着日時(<code>ARRIVAL_DATE</code>)<br>
     * 日付の検索値をセット到着日時(<code>ARRIVAL_DATE</code>)します。
     */
    public void setArrivalDate(Date value)
    {
        setKey(CarryInfo.ARRIVAL_DATE, value) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setArrivalDate(Date[] values)
    {
        setKey(CarryInfo.ARRIVAL_DATE, values, true) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setArrivalDate(Date[] values, String and_or_toNext)
    {
        setKey(CarryInfo.ARRIVAL_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setArrivalDate(Date[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.ARRIVAL_DATE, values, and_or_toNext) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setArrivalDate(Date value, String compcode)
    {
        setKey(CarryInfo.ARRIVAL_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
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
    public void setArrivalDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.ARRIVAL_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setArrivalDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.ARRIVAL_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setArrivalDateOrder(boolean ascorder)
    {
        setOrder(CarryInfo.ARRIVAL_DATE, ascorder) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setArrivalDateOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.ARRIVAL_DATE, ascorder) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)のグループ順をセットします。
     */
    public void setArrivalDateGroup()
    {
        setGroup(CarryInfo.ARRIVAL_DATE) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setArrivalDateGroup(int prio)
    {
        setGroup(CarryInfo.ARRIVAL_DATE) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の情報取得を設定します。
     */
    public void setArrivalDateCollect()
    {
        setCollect(CarryInfo.ARRIVAL_DATE) ;
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setArrivalDateCollect(String sqlfunc)
    {
        setCollect(CarryInfo.ARRIVAL_DATE, sqlfunc, null) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param value 制御情報(<code>CONTROLINFO</code>)<br>
     * 文字列の検索値をセット制御情報(<code>CONTROLINFO</code>)します。
     */
    public void setControlinfo(String value)
    {
        setKey(CarryInfo.CONTROLINFO, value) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControlinfo(String[] values)
    {
        setKey(CarryInfo.CONTROLINFO, values, true) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControlinfo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CONTROLINFO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControlinfo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CONTROLINFO, values, and_or_toNext) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setControlinfo(String value, String compcode)
    {
        setKey(CarryInfo.CONTROLINFO, value, compcode, "", "", true) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
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
    public void setControlinfo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CONTROLINFO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControlinfo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CONTROLINFO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setControlinfoOrder(boolean ascorder)
    {
        setOrder(CarryInfo.CONTROLINFO, ascorder) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setControlinfoOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.CONTROLINFO, ascorder) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)のグループ順をセットします。
     */
    public void setControlinfoGroup()
    {
        setGroup(CarryInfo.CONTROLINFO) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setControlinfoGroup(int prio)
    {
        setGroup(CarryInfo.CONTROLINFO) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の情報取得を設定します。
     */
    public void setControlinfoCollect()
    {
        setCollect(CarryInfo.CONTROLINFO) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setControlinfoCollect(String sqlfunc)
    {
        setCollect(CarryInfo.CONTROLINFO, sqlfunc, null) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param value キャンセル要求区分(<code>CANCEL_REQUEST</code>)<br>
     * 文字列の検索値をセットキャンセル要求区分(<code>CANCEL_REQUEST</code>)します。
     */
    public void setCancelRequest(String value)
    {
        setKey(CarryInfo.CANCEL_REQUEST, value) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequest(String[] values)
    {
        setKey(CarryInfo.CANCEL_REQUEST, values, true) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequest(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCancelRequest(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST, values, and_or_toNext) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCancelRequest(String value, String compcode)
    {
        setKey(CarryInfo.CANCEL_REQUEST, value, compcode, "", "", true) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
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
    public void setCancelRequest(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCancelRequest(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCancelRequestOrder(boolean ascorder)
    {
        setOrder(CarryInfo.CANCEL_REQUEST, ascorder) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequestOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.CANCEL_REQUEST, ascorder) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)のグループ順をセットします。
     */
    public void setCancelRequestGroup()
    {
        setGroup(CarryInfo.CANCEL_REQUEST) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequestGroup(int prio)
    {
        setGroup(CarryInfo.CANCEL_REQUEST) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の情報取得を設定します。
     */
    public void setCancelRequestCollect()
    {
        setCollect(CarryInfo.CANCEL_REQUEST) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCancelRequestCollect(String sqlfunc)
    {
        setCollect(CarryInfo.CANCEL_REQUEST, sqlfunc, null) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)<br>
     * 日付の検索値をセットキャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)します。
     */
    public void setCancelRequestDate(Date value)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, value) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequestDate(Date[] values)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, values, true) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequestDate(Date[] values, String and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCancelRequestDate(Date[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, values, and_or_toNext) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCancelRequestDate(Date value, String compcode)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
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
    public void setCancelRequestDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCancelRequestDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.CANCEL_REQUEST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCancelRequestDateOrder(boolean ascorder)
    {
        setOrder(CarryInfo.CANCEL_REQUEST_DATE, ascorder) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequestDateOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.CANCEL_REQUEST_DATE, ascorder) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)のグループ順をセットします。
     */
    public void setCancelRequestDateGroup()
    {
        setGroup(CarryInfo.CANCEL_REQUEST_DATE) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCancelRequestDateGroup(int prio)
    {
        setGroup(CarryInfo.CANCEL_REQUEST_DATE) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の情報取得を設定します。
     */
    public void setCancelRequestDateCollect()
    {
        setCollect(CarryInfo.CANCEL_REQUEST_DATE) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCancelRequestDateCollect(String sqlfunc)
    {
        setCollect(CarryInfo.CANCEL_REQUEST_DATE, sqlfunc, null) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value スケジュールNo(<code>SCHEDULE_NO</code>)<br>
     * 文字列の検索値をセットスケジュールNo(<code>SCHEDULE_NO</code>)します。
     */
    public void setScheduleNo(String value)
    {
        setKey(CarryInfo.SCHEDULE_NO, value) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNo(String[] values)
    {
        setKey(CarryInfo.SCHEDULE_NO, values, true) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.SCHEDULE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setScheduleNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.SCHEDULE_NO, values, and_or_toNext) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setScheduleNo(String value, String compcode)
    {
        setKey(CarryInfo.SCHEDULE_NO, value, compcode, "", "", true) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
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
    public void setScheduleNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.SCHEDULE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setScheduleNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.SCHEDULE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setScheduleNoOrder(boolean ascorder)
    {
        setOrder(CarryInfo.SCHEDULE_NO, ascorder) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNoOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.SCHEDULE_NO, ascorder) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)のグループ順をセットします。
     */
    public void setScheduleNoGroup()
    {
        setGroup(CarryInfo.SCHEDULE_NO) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNoGroup(int prio)
    {
        setGroup(CarryInfo.SCHEDULE_NO) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の情報取得を設定します。
     */
    public void setScheduleNoCollect()
    {
        setCollect(CarryInfo.SCHEDULE_NO) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setScheduleNoCollect(String sqlfunc)
    {
        setCollect(CarryInfo.SCHEDULE_NO, sqlfunc, null) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value アイルステーションNo.(<code>AISLE_STATION_NO</code>)<br>
     * 文字列の検索値をセットアイルステーションNo.(<code>AISLE_STATION_NO</code>)します。
     */
    public void setAisleStationNo(String value)
    {
        setKey(CarryInfo.AISLE_STATION_NO, value) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNo(String[] values)
    {
        setKey(CarryInfo.AISLE_STATION_NO, values, true) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.AISLE_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.AISLE_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAisleStationNo(String value, String compcode)
    {
        setKey(CarryInfo.AISLE_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
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
    public void setAisleStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.AISLE_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.AISLE_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAisleStationNoOrder(boolean ascorder)
    {
        setOrder(CarryInfo.AISLE_STATION_NO, ascorder) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.AISLE_STATION_NO, ascorder) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のグループ順をセットします。
     */
    public void setAisleStationNoGroup()
    {
        setGroup(CarryInfo.AISLE_STATION_NO) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAisleStationNoGroup(int prio)
    {
        setGroup(CarryInfo.AISLE_STATION_NO) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の情報取得を設定します。
     */
    public void setAisleStationNoCollect()
    {
        setCollect(CarryInfo.AISLE_STATION_NO) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAisleStationNoCollect(String sqlfunc)
    {
        setCollect(CarryInfo.AISLE_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 最終ステーションNo.(<code>END_STATION_NO</code>)<br>
     * 文字列の検索値をセット最終ステーションNo.(<code>END_STATION_NO</code>)します。
     */
    public void setEndStationNo(String value)
    {
        setKey(CarryInfo.END_STATION_NO, value) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndStationNo(String[] values)
    {
        setKey(CarryInfo.END_STATION_NO, values, true) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setEndStationNo(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.END_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.END_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setEndStationNo(String value, String compcode)
    {
        setKey(CarryInfo.END_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
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
    public void setEndStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.END_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setEndStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.END_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setEndStationNoOrder(boolean ascorder)
    {
        setOrder(CarryInfo.END_STATION_NO, ascorder) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.END_STATION_NO, ascorder) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)のグループ順をセットします。
     */
    public void setEndStationNoGroup()
    {
        setGroup(CarryInfo.END_STATION_NO) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setEndStationNoGroup(int prio)
    {
        setGroup(CarryInfo.END_STATION_NO) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の情報取得を設定します。
     */
    public void setEndStationNoCollect()
    {
        setCollect(CarryInfo.END_STATION_NO) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setEndStationNoCollect(String sqlfunc)
    {
        setCollect(CarryInfo.END_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 異常コード(<code>ERROR_CODE</code>)<br>
     * 文字列の検索値をセット異常コード(<code>ERROR_CODE</code>)します。
     */
    public void setErrorCode(String value)
    {
        setKey(CarryInfo.ERROR_CODE, value) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setErrorCode(String[] values)
    {
        setKey(CarryInfo.ERROR_CODE, values, true) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setErrorCode(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.ERROR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setErrorCode(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.ERROR_CODE, values, and_or_toNext) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setErrorCode(String value, String compcode)
    {
        setKey(CarryInfo.ERROR_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
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
    public void setErrorCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.ERROR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setErrorCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.ERROR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setErrorCodeOrder(boolean ascorder)
    {
        setOrder(CarryInfo.ERROR_CODE, ascorder) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setErrorCodeOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.ERROR_CODE, ascorder) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)のグループ順をセットします。
     */
    public void setErrorCodeGroup()
    {
        setGroup(CarryInfo.ERROR_CODE) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setErrorCodeGroup(int prio)
    {
        setGroup(CarryInfo.ERROR_CODE) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の情報取得を設定します。
     */
    public void setErrorCodeCollect()
    {
        setCollect(CarryInfo.ERROR_CODE) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setErrorCodeCollect(String sqlfunc)
    {
        setCollect(CarryInfo.ERROR_CODE, sqlfunc, null) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)<br>
     * 文字列の検索値をセットメンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)します。
     */
    public void setMaintenanceTerminal(String value)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, value) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaintenanceTerminal(String[] values)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, values, true) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaintenanceTerminal(String[] values, String and_or_toNext)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaintenanceTerminal(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, values, and_or_toNext) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMaintenanceTerminal(String value, String compcode)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, value, compcode, "", "", true) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
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
    public void setMaintenanceTerminal(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaintenanceTerminal(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(CarryInfo.MAINTENANCE_TERMINAL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMaintenanceTerminalOrder(boolean ascorder)
    {
        setOrder(CarryInfo.MAINTENANCE_TERMINAL, ascorder) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaintenanceTerminalOrder(int prio, boolean ascorder)
    {
        setOrder(CarryInfo.MAINTENANCE_TERMINAL, ascorder) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)のグループ順をセットします。
     */
    public void setMaintenanceTerminalGroup()
    {
        setGroup(CarryInfo.MAINTENANCE_TERMINAL) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaintenanceTerminalGroup(int prio)
    {
        setGroup(CarryInfo.MAINTENANCE_TERMINAL) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の情報取得を設定します。
     */
    public void setMaintenanceTerminalCollect()
    {
        setCollect(CarryInfo.MAINTENANCE_TERMINAL) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMaintenanceTerminalCollect(String sqlfunc)
    {
        setCollect(CarryInfo.MAINTENANCE_TERMINAL, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
     */
    public void setRegistDate(Date value)
    {
        setKey(CarryInfo.REGIST_DATE, value) ;
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
        setKey(CarryInfo.REGIST_DATE, values, true) ;
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
        setKey(CarryInfo.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(CarryInfo.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(CarryInfo.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CarryInfo.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(CarryInfo.REGIST_DATE, ascorder) ;
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
        setOrder(CarryInfo.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(CarryInfo.REGIST_DATE) ;
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
        setGroup(CarryInfo.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(CarryInfo.REGIST_DATE) ;
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
        setCollect(CarryInfo.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
     */
    public void setRegistPname(String value)
    {
        setKey(CarryInfo.REGIST_PNAME, value) ;
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
        setKey(CarryInfo.REGIST_PNAME, values, true) ;
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
        setKey(CarryInfo.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(CarryInfo.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(CarryInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CarryInfo.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(CarryInfo.REGIST_PNAME, ascorder) ;
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
        setOrder(CarryInfo.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(CarryInfo.REGIST_PNAME) ;
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
        setGroup(CarryInfo.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(CarryInfo.REGIST_PNAME) ;
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
        setCollect(CarryInfo.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(CarryInfo.LAST_UPDATE_DATE, value) ;
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
        setKey(CarryInfo.LAST_UPDATE_DATE, values, true) ;
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
        setKey(CarryInfo.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(CarryInfo.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(CarryInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CarryInfo.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(CarryInfo.LAST_UPDATE_DATE, ascorder) ;
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
        setOrder(CarryInfo.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(CarryInfo.LAST_UPDATE_DATE) ;
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
        setGroup(CarryInfo.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(CarryInfo.LAST_UPDATE_DATE) ;
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
        setCollect(CarryInfo.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(CarryInfo.LAST_UPDATE_PNAME, value) ;
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
        setKey(CarryInfo.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(CarryInfo.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(CarryInfo.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(CarryInfo.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(CarryInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(CarryInfo.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(CarryInfo.LAST_UPDATE_PNAME, ascorder) ;
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
        setOrder(CarryInfo.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(CarryInfo.LAST_UPDATE_PNAME) ;
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
        setGroup(CarryInfo.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(CarryInfo.LAST_UPDATE_PNAME) ;
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
        setCollect(CarryInfo.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: CarryInfoSearchKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
