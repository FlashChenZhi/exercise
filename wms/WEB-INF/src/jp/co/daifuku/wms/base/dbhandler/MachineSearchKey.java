// $Id: MachineSearchKey.java 7253 2010-02-26 05:58:01Z kanda $
// $LastChangedRevision: 7253 $
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
import jp.co.daifuku.wms.base.entity.Machine;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * MACHINE用の検索キークラスです。
 *
 * @version $Revision: 7253 $, $Date: 2010-02-26 14:58:01 +0900 (金, 26 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kanda $
 */


public class MachineSearchKey
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
    public MachineSearchKey()
    {
        super(Machine.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value ステーションNo.(<code>STATION_NO</code>)<br>
     * 文字列の検索値をセットステーションNo.(<code>STATION_NO</code>)します。
     */
    public void setStationNo(String value)
    {
        setKey(Machine.STATION_NO, value) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationNo(String[] values)
    {
        setKey(Machine.STATION_NO, values, true) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationNo(String[] values, String and_or_toNext)
    {
        setKey(Machine.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(Machine.STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
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
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStationNoOrder(boolean ascorder)
    {
        setOrder(Machine.STATION_NO, ascorder) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(Machine.STATION_NO, ascorder) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のグループ順をセットします。
     */
    public void setStationNoGroup()
    {
        setGroup(Machine.STATION_NO) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStationNoGroup(int prio)
    {
        setGroup(Machine.STATION_NO) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の情報取得を設定します。
     */
    public void setStationNoCollect()
    {
        setCollect(Machine.STATION_NO) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStationNoCollect(String sqlfunc)
    {
        setCollect(Machine.STATION_NO, sqlfunc, null) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 機器種別(<code>MACHINE_TYPE</code>)<br>
     * 文字列の検索値をセット機器種別(<code>MACHINE_TYPE</code>)します。
     */
    public void setMachineType(String value)
    {
        setKey(Machine.MACHINE_TYPE, value) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMachineType(String[] values)
    {
        setKey(Machine.MACHINE_TYPE, values, true) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMachineType(String[] values, String and_or_toNext)
    {
        setKey(Machine.MACHINE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMachineType(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.MACHINE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMachineType(String value, String compcode)
    {
        setKey(Machine.MACHINE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
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
    public void setMachineType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.MACHINE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMachineType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.MACHINE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMachineTypeOrder(boolean ascorder)
    {
        setOrder(Machine.MACHINE_TYPE, ascorder) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMachineTypeOrder(int prio, boolean ascorder)
    {
        setOrder(Machine.MACHINE_TYPE, ascorder) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)のグループ順をセットします。
     */
    public void setMachineTypeGroup()
    {
        setGroup(Machine.MACHINE_TYPE) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMachineTypeGroup(int prio)
    {
        setGroup(Machine.MACHINE_TYPE) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の情報取得を設定します。
     */
    public void setMachineTypeCollect()
    {
        setCollect(Machine.MACHINE_TYPE) ;
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMachineTypeCollect(String sqlfunc)
    {
        setCollect(Machine.MACHINE_TYPE, sqlfunc, null) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param value 機器No.(<code>MACHINE_NO</code>)<br>
     * 文字列の検索値をセット機器No.(<code>MACHINE_NO</code>)します。
     */
    public void setMachineNo(String value)
    {
        setKey(Machine.MACHINE_NO, value) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMachineNo(String[] values)
    {
        setKey(Machine.MACHINE_NO, values, true) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMachineNo(String[] values, String and_or_toNext)
    {
        setKey(Machine.MACHINE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMachineNo(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.MACHINE_NO, values, and_or_toNext) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMachineNo(String value, String compcode)
    {
        setKey(Machine.MACHINE_NO, value, compcode, "", "", true) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
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
    public void setMachineNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.MACHINE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMachineNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.MACHINE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMachineNoOrder(boolean ascorder)
    {
        setOrder(Machine.MACHINE_NO, ascorder) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMachineNoOrder(int prio, boolean ascorder)
    {
        setOrder(Machine.MACHINE_NO, ascorder) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)のグループ順をセットします。
     */
    public void setMachineNoGroup()
    {
        setGroup(Machine.MACHINE_NO) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMachineNoGroup(int prio)
    {
        setGroup(Machine.MACHINE_NO) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の情報取得を設定します。
     */
    public void setMachineNoCollect()
    {
        setCollect(Machine.MACHINE_NO) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMachineNoCollect(String sqlfunc)
    {
        setCollect(Machine.MACHINE_NO, sqlfunc, null) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット状態(<code>STATUS_FLAG</code>)します。
     */
    public void setStatusFlag(String value)
    {
        setKey(Machine.STATUS_FLAG, value) ;
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
        setKey(Machine.STATUS_FLAG, values, true) ;
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
        setKey(Machine.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(Machine.STATUS_FLAG, value, compcode, "", "", true) ;
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
        setKey(Machine.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Machine.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(Machine.STATUS_FLAG, ascorder) ;
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
        setOrder(Machine.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(Machine.STATUS_FLAG) ;
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
        setGroup(Machine.STATUS_FLAG) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(Machine.STATUS_FLAG) ;
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
        setCollect(Machine.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 異常コード(<code>ERROR_CODE</code>)<br>
     * 文字列の検索値をセット異常コード(<code>ERROR_CODE</code>)します。
     */
    public void setErrorCode(String value)
    {
        setKey(Machine.ERROR_CODE, value) ;
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
        setKey(Machine.ERROR_CODE, values, true) ;
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
        setKey(Machine.ERROR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setErrorCode(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.ERROR_CODE, values, and_or_toNext) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setErrorCode(String value, String compcode)
    {
        setKey(Machine.ERROR_CODE, value, compcode, "", "", true) ;
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
        setKey(Machine.ERROR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Machine.ERROR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setErrorCodeOrder(boolean ascorder)
    {
        setOrder(Machine.ERROR_CODE, ascorder) ;
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
        setOrder(Machine.ERROR_CODE, ascorder) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)のグループ順をセットします。
     */
    public void setErrorCodeGroup()
    {
        setGroup(Machine.ERROR_CODE) ;
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
        setGroup(Machine.ERROR_CODE) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>)の情報取得を設定します。
     */
    public void setErrorCodeCollect()
    {
        setCollect(Machine.ERROR_CODE) ;
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
        setCollect(Machine.ERROR_CODE, sqlfunc, null) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value コントローラー番号(<code>CONTROLLER_NO</code>)<br>
     * 文字列の検索値をセットコントローラー番号(<code>CONTROLLER_NO</code>)します。
     */
    public void setControllerNo(String value)
    {
        setKey(Machine.CONTROLLER_NO, value) ;
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
        setKey(Machine.CONTROLLER_NO, values, true) ;
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
        setKey(Machine.CONTROLLER_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControllerNo(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.CONTROLLER_NO, values, and_or_toNext) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setControllerNo(String value, String compcode)
    {
        setKey(Machine.CONTROLLER_NO, value, compcode, "", "", true) ;
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
        setKey(Machine.CONTROLLER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Machine.CONTROLLER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setControllerNoOrder(boolean ascorder)
    {
        setOrder(Machine.CONTROLLER_NO, ascorder) ;
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
        setOrder(Machine.CONTROLLER_NO, ascorder) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)のグループ順をセットします。
     */
    public void setControllerNoGroup()
    {
        setGroup(Machine.CONTROLLER_NO) ;
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
        setGroup(Machine.CONTROLLER_NO) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の情報取得を設定します。
     */
    public void setControllerNoCollect()
    {
        setCollect(Machine.CONTROLLER_NO) ;
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
        setCollect(Machine.CONTROLLER_NO, sqlfunc, null) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param value 機器名称(<code>DEVICE_NAME</code>)<br>
     * 文字列の検索値をセット機器名称(<code>DEVICE_NAME</code>)します。
     */
    public void setDeviceName(String value)
    {
        setKey(Machine.DEVICE_NAME, value) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDeviceName(String[] values)
    {
        setKey(Machine.DEVICE_NAME, values, true) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setDeviceName(String[] values, String and_or_toNext)
    {
        setKey(Machine.DEVICE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDeviceName(String[] values, boolean and_or_toNext)
    {
        setKey(Machine.DEVICE_NAME, values, and_or_toNext) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setDeviceName(String value, String compcode)
    {
        setKey(Machine.DEVICE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
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
    public void setDeviceName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Machine.DEVICE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setDeviceName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Machine.DEVICE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setDeviceNameOrder(boolean ascorder)
    {
        setOrder(Machine.DEVICE_NAME, ascorder) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDeviceNameOrder(int prio, boolean ascorder)
    {
        setOrder(Machine.DEVICE_NAME, ascorder) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)のグループ順をセットします。
     */
    public void setDeviceNameGroup()
    {
        setGroup(Machine.DEVICE_NAME) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setDeviceNameGroup(int prio)
    {
        setGroup(Machine.DEVICE_NAME) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の情報取得を設定します。
     */
    public void setDeviceNameCollect()
    {
        setCollect(Machine.DEVICE_NAME) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setDeviceNameCollect(String sqlfunc)
    {
        setCollect(Machine.DEVICE_NAME, sqlfunc, null) ;
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
        return "$Id: MachineSearchKey.java 7253 2010-02-26 05:58:01Z kanda $" ;
    }
}
