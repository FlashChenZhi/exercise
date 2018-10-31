// $Id: StoragePlanSearchKey.java 5127 2009-10-13 12:20:06Z ota $
// $LastChangedRevision: 5127 $
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
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * STORAGEPLAN用の検索キークラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class StoragePlanSearchKey
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
    public StoragePlanSearchKey()
    {
        super(StoragePlan.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 予定一意キー(<code>PLAN_UKEY</code>)<br>
     * 文字列の検索値をセット予定一意キー(<code>PLAN_UKEY</code>)します。
     */
    public void setPlanUkey(String value)
    {
        setKey(StoragePlan.PLAN_UKEY, value) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkey(String[] values)
    {
        setKey(StoragePlan.PLAN_UKEY, values, true) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkey(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_UKEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanUkey(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_UKEY, values, and_or_toNext) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanUkey(String value, String compcode)
    {
        setKey(StoragePlan.PLAN_UKEY, value, compcode, "", "", true) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
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
    public void setPlanUkey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_UKEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanUkey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_UKEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanUkeyOrder(boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_UKEY, ascorder) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkeyOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_UKEY, ascorder) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のグループ順をセットします。
     */
    public void setPlanUkeyGroup()
    {
        setGroup(StoragePlan.PLAN_UKEY) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanUkeyGroup(int prio)
    {
        setGroup(StoragePlan.PLAN_UKEY) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の情報取得を設定します。
     */
    public void setPlanUkeyCollect()
    {
        setCollect(StoragePlan.PLAN_UKEY) ;
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanUkeyCollect(String sqlfunc)
    {
        setCollect(StoragePlan.PLAN_UKEY, sqlfunc, null) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 取込単位キー(<code>LOAD_UNIT_KEY</code>)<br>
     * 文字列の検索値をセット取込単位キー(<code>LOAD_UNIT_KEY</code>)します。
     */
    public void setLoadUnitKey(String value)
    {
        setKey(StoragePlan.LOAD_UNIT_KEY, value) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadUnitKey(String[] values)
    {
        setKey(StoragePlan.LOAD_UNIT_KEY, values, true) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadUnitKey(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.LOAD_UNIT_KEY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadUnitKey(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.LOAD_UNIT_KEY, values, and_or_toNext) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLoadUnitKey(String value, String compcode)
    {
        setKey(StoragePlan.LOAD_UNIT_KEY, value, compcode, "", "", true) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)の検索値をセットします。
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
    public void setLoadUnitKey(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.LOAD_UNIT_KEY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadUnitKey(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.LOAD_UNIT_KEY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLoadUnitKeyOrder(boolean ascorder)
    {
        setOrder(StoragePlan.LOAD_UNIT_KEY, ascorder) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadUnitKeyOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.LOAD_UNIT_KEY, ascorder) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)のグループ順をセットします。
     */
    public void setLoadUnitKeyGroup()
    {
        setGroup(StoragePlan.LOAD_UNIT_KEY) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadUnitKeyGroup(int prio)
    {
        setGroup(StoragePlan.LOAD_UNIT_KEY) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)の情報取得を設定します。
     */
    public void setLoadUnitKeyCollect()
    {
        setCollect(StoragePlan.LOAD_UNIT_KEY) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLoadUnitKeyCollect(String sqlfunc)
    {
        setCollect(StoragePlan.LOAD_UNIT_KEY, sqlfunc, null) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value ファイル行No.(<code>FILE_LINE_NO</code>)<br>
     * 数値の検索値をセットファイル行No.(<code>FILE_LINE_NO</code>)します。
     */
    public void setFileLineNo(int value)
    {
        setKey(StoragePlan.FILE_LINE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFileLineNo(int[] values)
    {
        setKey(StoragePlan.FILE_LINE_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFileLineNo(int[] values, String and_or_toNext)
    {
        setKey(StoragePlan.FILE_LINE_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFileLineNo(int[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.FILE_LINE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setFileLineNo(int value, String compcode)
    {
        setKey(StoragePlan.FILE_LINE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
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
    public void setFileLineNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.FILE_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFileLineNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.FILE_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setFileLineNoOrder(boolean ascorder)
    {
        setOrder(StoragePlan.FILE_LINE_NO, ascorder) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFileLineNoOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.FILE_LINE_NO, ascorder) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)のグループ順をセットします。
     */
    public void setFileLineNoGroup()
    {
        setGroup(StoragePlan.FILE_LINE_NO) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFileLineNoGroup(int prio)
    {
        setGroup(StoragePlan.FILE_LINE_NO) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の情報取得を設定します。
     */
    public void setFileLineNoCollect()
    {
        setCollect(StoragePlan.FILE_LINE_NO) ;
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setFileLineNoCollect(String sqlfunc)
    {
        setCollect(StoragePlan.FILE_LINE_NO, sqlfunc, null) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット状態フラグ(<code>STATUS_FLAG</code>)します。
     */
    public void setStatusFlag(String value)
    {
        setKey(StoragePlan.STATUS_FLAG, value) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlag(String[] values)
    {
        setKey(StoragePlan.STATUS_FLAG, values, true) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(StoragePlan.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(StoragePlan.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
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
        setKey(StoragePlan.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(StoragePlan.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のソート順をセットします。
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
        setOrder(StoragePlan.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(StoragePlan.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusFlagGroup(int prio)
    {
        setGroup(StoragePlan.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(StoragePlan.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStatusFlagCollect(String sqlfunc)
    {
        setCollect(StoragePlan.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)の検索値をセットします。
     * 
     * @param value ホスト取消区分(<code>CANCEL_FLAG</code>)<br>
     * 文字列の検索値をセットホスト取消区分(<code>CANCEL_FLAG</code>)します。
     */
    public void setCancelFlag(String value)
    {
        setKey(StoragePlan.CANCEL_FLAG, value) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelFlag(String[] values)
    {
        setKey(StoragePlan.CANCEL_FLAG, values, true) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCancelFlag(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.CANCEL_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCancelFlag(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.CANCEL_FLAG, values, and_or_toNext) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCancelFlag(String value, String compcode)
    {
        setKey(StoragePlan.CANCEL_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)の検索値をセットします。
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
    public void setCancelFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.CANCEL_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCancelFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.CANCEL_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCancelFlagOrder(boolean ascorder)
    {
        setOrder(StoragePlan.CANCEL_FLAG, ascorder) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCancelFlagOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.CANCEL_FLAG, ascorder) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)のグループ順をセットします。
     */
    public void setCancelFlagGroup()
    {
        setGroup(StoragePlan.CANCEL_FLAG) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCancelFlagGroup(int prio)
    {
        setGroup(StoragePlan.CANCEL_FLAG) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)の情報取得を設定します。
     */
    public void setCancelFlagCollect()
    {
        setCollect(StoragePlan.CANCEL_FLAG) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCancelFlagCollect(String sqlfunc)
    {
        setCollect(StoragePlan.CANCEL_FLAG, sqlfunc, null) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 予定日(<code>PLAN_DAY</code>)<br>
     * 文字列の検索値をセット予定日(<code>PLAN_DAY</code>)します。
     */
    public void setPlanDay(String value)
    {
        setKey(StoragePlan.PLAN_DAY, value) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDay(String[] values)
    {
        setKey(StoragePlan.PLAN_DAY, values, true) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDay(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanDay(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_DAY, values, and_or_toNext) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanDay(String value, String compcode)
    {
        setKey(StoragePlan.PLAN_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
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
    public void setPlanDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanDayOrder(boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_DAY, ascorder) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDayOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_DAY, ascorder) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のグループ順をセットします。
     */
    public void setPlanDayGroup()
    {
        setGroup(StoragePlan.PLAN_DAY) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanDayGroup(int prio)
    {
        setGroup(StoragePlan.PLAN_DAY) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の情報取得を設定します。
     */
    public void setPlanDayCollect()
    {
        setCollect(StoragePlan.PLAN_DAY) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanDayCollect(String sqlfunc)
    {
        setCollect(StoragePlan.PLAN_DAY, sqlfunc, null) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値をセット荷主コード(<code>CONSIGNOR_CODE</code>)します。
     */
    public void setConsignorCode(String value)
    {
        setKey(StoragePlan.CONSIGNOR_CODE, value) ;
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
        setKey(StoragePlan.CONSIGNOR_CODE, values, true) ;
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
        setKey(StoragePlan.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(StoragePlan.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(StoragePlan.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(StoragePlan.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(StoragePlan.CONSIGNOR_CODE, ascorder) ;
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
        setOrder(StoragePlan.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(StoragePlan.CONSIGNOR_CODE) ;
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
        setGroup(StoragePlan.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(StoragePlan.CONSIGNOR_CODE) ;
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
        setCollect(StoragePlan.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param value 仕入先コード(<code>SUPPLIER_CODE</code>)<br>
     * 文字列の検索値をセット仕入先コード(<code>SUPPLIER_CODE</code>)します。
     */
    public void setSupplierCode(String value)
    {
        setKey(StoragePlan.SUPPLIER_CODE, value) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSupplierCode(String[] values)
    {
        setKey(StoragePlan.SUPPLIER_CODE, values, true) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSupplierCode(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.SUPPLIER_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSupplierCode(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.SUPPLIER_CODE, values, and_or_toNext) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSupplierCode(String value, String compcode)
    {
        setKey(StoragePlan.SUPPLIER_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
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
    public void setSupplierCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.SUPPLIER_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSupplierCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.SUPPLIER_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSupplierCodeOrder(boolean ascorder)
    {
        setOrder(StoragePlan.SUPPLIER_CODE, ascorder) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSupplierCodeOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.SUPPLIER_CODE, ascorder) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)のグループ順をセットします。
     */
    public void setSupplierCodeGroup()
    {
        setGroup(StoragePlan.SUPPLIER_CODE) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSupplierCodeGroup(int prio)
    {
        setGroup(StoragePlan.SUPPLIER_CODE) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の情報取得を設定します。
     */
    public void setSupplierCodeCollect()
    {
        setCollect(StoragePlan.SUPPLIER_CODE) ;
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSupplierCodeCollect(String sqlfunc)
    {
        setCollect(StoragePlan.SUPPLIER_CODE, sqlfunc, null) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)<br>
     * 文字列の検索値をセットスケジュールNo.(<code>RECEIVE_TICKET_NO</code>)します。
     */
    public void setReceiveTicketNo(String value)
    {
        setKey(StoragePlan.RECEIVE_TICKET_NO, value) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveTicketNo(String[] values)
    {
        setKey(StoragePlan.RECEIVE_TICKET_NO, values, true) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveTicketNo(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.RECEIVE_TICKET_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceiveTicketNo(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.RECEIVE_TICKET_NO, values, and_or_toNext) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReceiveTicketNo(String value, String compcode)
    {
        setKey(StoragePlan.RECEIVE_TICKET_NO, value, compcode, "", "", true) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
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
    public void setReceiveTicketNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.RECEIVE_TICKET_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceiveTicketNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.RECEIVE_TICKET_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReceiveTicketNoOrder(boolean ascorder)
    {
        setOrder(StoragePlan.RECEIVE_TICKET_NO, ascorder) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveTicketNoOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.RECEIVE_TICKET_NO, ascorder) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)のグループ順をセットします。
     */
    public void setReceiveTicketNoGroup()
    {
        setGroup(StoragePlan.RECEIVE_TICKET_NO) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveTicketNoGroup(int prio)
    {
        setGroup(StoragePlan.RECEIVE_TICKET_NO) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)の情報取得を設定します。
     */
    public void setReceiveTicketNoCollect()
    {
        setCollect(StoragePlan.RECEIVE_TICKET_NO) ;
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReceiveTicketNoCollect(String sqlfunc)
    {
        setCollect(StoragePlan.RECEIVE_TICKET_NO, sqlfunc, null) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)<br>
     * 数値の検索値をセットスケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)します。
     */
    public void setReceiveLineNo(int value)
    {
        setKey(StoragePlan.RECEIVE_LINE_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveLineNo(int[] values)
    {
        setKey(StoragePlan.RECEIVE_LINE_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveLineNo(int[] values, String and_or_toNext)
    {
        setKey(StoragePlan.RECEIVE_LINE_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceiveLineNo(int[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.RECEIVE_LINE_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReceiveLineNo(int value, String compcode)
    {
        setKey(StoragePlan.RECEIVE_LINE_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
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
    public void setReceiveLineNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.RECEIVE_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReceiveLineNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.RECEIVE_LINE_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReceiveLineNoOrder(boolean ascorder)
    {
        setOrder(StoragePlan.RECEIVE_LINE_NO, ascorder) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveLineNoOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.RECEIVE_LINE_NO, ascorder) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)のグループ順をセットします。
     */
    public void setReceiveLineNoGroup()
    {
        setGroup(StoragePlan.RECEIVE_LINE_NO) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReceiveLineNoGroup(int prio)
    {
        setGroup(StoragePlan.RECEIVE_LINE_NO) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)の情報取得を設定します。
     */
    public void setReceiveLineNoCollect()
    {
        setCollect(StoragePlan.RECEIVE_LINE_NO) ;
    }

    /**
     * スケジュールSeqNo.(<code>RECEIVE_LINE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReceiveLineNoCollect(String sqlfunc)
    {
        setCollect(StoragePlan.RECEIVE_LINE_NO, sqlfunc, null) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param value 作業枝番(<code>BRANCH_NO</code>)<br>
     * 数値の検索値をセット作業枝番(<code>BRANCH_NO</code>)します。
     */
    public void setBranchNo(int value)
    {
        setKey(StoragePlan.BRANCH_NO, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBranchNo(int[] values)
    {
        setKey(StoragePlan.BRANCH_NO, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setBranchNo(int[] values, String and_or_toNext)
    {
        setKey(StoragePlan.BRANCH_NO, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBranchNo(int[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.BRANCH_NO, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setBranchNo(int value, String compcode)
    {
        setKey(StoragePlan.BRANCH_NO, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)の検索値をセットします。
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
    public void setBranchNo(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.BRANCH_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setBranchNo(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.BRANCH_NO, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setBranchNoOrder(boolean ascorder)
    {
        setOrder(StoragePlan.BRANCH_NO, ascorder) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBranchNoOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.BRANCH_NO, ascorder) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)のグループ順をセットします。
     */
    public void setBranchNoGroup()
    {
        setGroup(StoragePlan.BRANCH_NO) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setBranchNoGroup(int prio)
    {
        setGroup(StoragePlan.BRANCH_NO) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)の情報取得を設定します。
     */
    public void setBranchNoCollect()
    {
        setCollect(StoragePlan.BRANCH_NO) ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setBranchNoCollect(String sqlfunc)
    {
        setCollect(StoragePlan.BRANCH_NO, sqlfunc, null) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 予定エリアNo.(<code>PLAN_AREA_NO</code>)<br>
     * 文字列の検索値をセット予定エリアNo.(<code>PLAN_AREA_NO</code>)します。
     */
    public void setPlanAreaNo(String value)
    {
        setKey(StoragePlan.PLAN_AREA_NO, value) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNo(String[] values)
    {
        setKey(StoragePlan.PLAN_AREA_NO, values, true) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNo(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_AREA_NO, values, and_or_toNext) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanAreaNo(String value, String compcode)
    {
        setKey(StoragePlan.PLAN_AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
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
    public void setPlanAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanAreaNoOrder(boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_AREA_NO, ascorder) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNoOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_AREA_NO, ascorder) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)のグループ順をセットします。
     */
    public void setPlanAreaNoGroup()
    {
        setGroup(StoragePlan.PLAN_AREA_NO) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanAreaNoGroup(int prio)
    {
        setGroup(StoragePlan.PLAN_AREA_NO) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の情報取得を設定します。
     */
    public void setPlanAreaNoCollect()
    {
        setCollect(StoragePlan.PLAN_AREA_NO) ;
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanAreaNoCollect(String sqlfunc)
    {
        setCollect(StoragePlan.PLAN_AREA_NO, sqlfunc, null) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 予定棚No.(<code>PLAN_LOCATION_NO</code>)<br>
     * 文字列の検索値をセット予定棚No.(<code>PLAN_LOCATION_NO</code>)します。
     */
    public void setPlanLocationNo(String value)
    {
        setKey(StoragePlan.PLAN_LOCATION_NO, value) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNo(String[] values)
    {
        setKey(StoragePlan.PLAN_LOCATION_NO, values, true) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNo(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanLocationNo(String value, String compcode)
    {
        setKey(StoragePlan.PLAN_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
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
    public void setPlanLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanLocationNoOrder(boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_LOCATION_NO, ascorder) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNoOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_LOCATION_NO, ascorder) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)のグループ順をセットします。
     */
    public void setPlanLocationNoGroup()
    {
        setGroup(StoragePlan.PLAN_LOCATION_NO) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLocationNoGroup(int prio)
    {
        setGroup(StoragePlan.PLAN_LOCATION_NO) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の情報取得を設定します。
     */
    public void setPlanLocationNoCollect()
    {
        setCollect(StoragePlan.PLAN_LOCATION_NO) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanLocationNoCollect(String sqlfunc)
    {
        setCollect(StoragePlan.PLAN_LOCATION_NO, sqlfunc, null) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値をセット商品コード(<code>ITEM_CODE</code>)します。
     */
    public void setItemCode(String value)
    {
        setKey(StoragePlan.ITEM_CODE, value) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemCode(String[] values)
    {
        setKey(StoragePlan.ITEM_CODE, values, true) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setItemCode(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(StoragePlan.ITEM_CODE, value, compcode, "", "", true) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
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
    public void setItemCode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemCodeOrder(boolean ascorder)
    {
        setOrder(StoragePlan.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemCodeOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     */
    public void setItemCodeGroup()
    {
        setGroup(StoragePlan.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setItemCodeGroup(int prio)
    {
        setGroup(StoragePlan.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     */
    public void setItemCodeCollect()
    {
        setCollect(StoragePlan.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setItemCodeCollect(String sqlfunc)
    {
        setCollect(StoragePlan.ITEM_CODE, sqlfunc, null) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 予定ロットNo.(<code>PLAN_LOT_NO</code>)<br>
     * 文字列の検索値をセット予定ロットNo.(<code>PLAN_LOT_NO</code>)します。
     */
    public void setPlanLotNo(String value)
    {
        setKey(StoragePlan.PLAN_LOT_NO, value) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNo(String[] values)
    {
        setKey(StoragePlan.PLAN_LOT_NO, values, true) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNo(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_LOT_NO, values, and_or_toNext) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanLotNo(String value, String compcode)
    {
        setKey(StoragePlan.PLAN_LOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
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
    public void setPlanLotNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanLotNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanLotNoOrder(boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_LOT_NO, ascorder) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNoOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_LOT_NO, ascorder) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のグループ順をセットします。
     */
    public void setPlanLotNoGroup()
    {
        setGroup(StoragePlan.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanLotNoGroup(int prio)
    {
        setGroup(StoragePlan.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の情報取得を設定します。
     */
    public void setPlanLotNoCollect()
    {
        setCollect(StoragePlan.PLAN_LOT_NO) ;
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanLotNoCollect(String sqlfunc)
    {
        setCollect(StoragePlan.PLAN_LOT_NO, sqlfunc, null) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 予定数(<code>PLAN_QTY</code>)<br>
     * 数値の検索値をセット予定数(<code>PLAN_QTY</code>)します。
     */
    public void setPlanQty(int value)
    {
        setKey(StoragePlan.PLAN_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQty(int[] values)
    {
        setKey(StoragePlan.PLAN_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQty(int[] values, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanQty(int[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setPlanQty(int value, String compcode)
    {
        setKey(StoragePlan.PLAN_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
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
    public void setPlanQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setPlanQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.PLAN_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setPlanQtyOrder(boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_QTY, ascorder) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQtyOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.PLAN_QTY, ascorder) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のグループ順をセットします。
     */
    public void setPlanQtyGroup()
    {
        setGroup(StoragePlan.PLAN_QTY) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setPlanQtyGroup(int prio)
    {
        setGroup(StoragePlan.PLAN_QTY) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の情報取得を設定します。
     */
    public void setPlanQtyCollect()
    {
        setCollect(StoragePlan.PLAN_QTY) ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setPlanQtyCollect(String sqlfunc)
    {
        setCollect(StoragePlan.PLAN_QTY, sqlfunc, null) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 実績数(<code>RESULT_QTY</code>)<br>
     * 数値の検索値をセット実績数(<code>RESULT_QTY</code>)します。
     */
    public void setResultQty(int value)
    {
        setKey(StoragePlan.RESULT_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultQty(int[] values)
    {
        setKey(StoragePlan.RESULT_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setResultQty(int[] values, String and_or_toNext)
    {
        setKey(StoragePlan.RESULT_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultQty(int[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.RESULT_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setResultQty(int value, String compcode)
    {
        setKey(StoragePlan.RESULT_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
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
    public void setResultQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setResultQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.RESULT_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setResultQtyOrder(boolean ascorder)
    {
        setOrder(StoragePlan.RESULT_QTY, ascorder) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultQtyOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.RESULT_QTY, ascorder) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のグループ順をセットします。
     */
    public void setResultQtyGroup()
    {
        setGroup(StoragePlan.RESULT_QTY) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setResultQtyGroup(int prio)
    {
        setGroup(StoragePlan.RESULT_QTY) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の情報取得を設定します。
     */
    public void setResultQtyCollect()
    {
        setCollect(StoragePlan.RESULT_QTY) ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setResultQtyCollect(String sqlfunc)
    {
        setCollect(StoragePlan.RESULT_QTY, sqlfunc, null) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 欠品数(<code>SHORTAGE_QTY</code>)<br>
     * 数値の検索値をセット欠品数(<code>SHORTAGE_QTY</code>)します。
     */
    public void setShortageQty(int value)
    {
        setKey(StoragePlan.SHORTAGE_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQty(int[] values)
    {
        setKey(StoragePlan.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQty(int[] values, String and_or_toNext)
    {
        setKey(StoragePlan.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShortageQty(int[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.SHORTAGE_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setShortageQty(int value, String compcode)
    {
        setKey(StoragePlan.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
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
    public void setShortageQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setShortageQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.SHORTAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setShortageQtyOrder(boolean ascorder)
    {
        setOrder(StoragePlan.SHORTAGE_QTY, ascorder) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQtyOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.SHORTAGE_QTY, ascorder) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のグループ順をセットします。
     */
    public void setShortageQtyGroup()
    {
        setGroup(StoragePlan.SHORTAGE_QTY) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setShortageQtyGroup(int prio)
    {
        setGroup(StoragePlan.SHORTAGE_QTY) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の情報取得を設定します。
     */
    public void setShortageQtyCollect()
    {
        setCollect(StoragePlan.SHORTAGE_QTY) ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setShortageQtyCollect(String sqlfunc)
    {
        setCollect(StoragePlan.SHORTAGE_QTY, sqlfunc, null) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 実績報告区分(<code>REPORT_FLAG</code>)<br>
     * 文字列の検索値をセット実績報告区分(<code>REPORT_FLAG</code>)します。
     */
    public void setReportFlag(String value)
    {
        setKey(StoragePlan.REPORT_FLAG, value) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlag(String[] values)
    {
        setKey(StoragePlan.REPORT_FLAG, values, true) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlag(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.REPORT_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReportFlag(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.REPORT_FLAG, values, and_or_toNext) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setReportFlag(String value, String compcode)
    {
        setKey(StoragePlan.REPORT_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
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
    public void setReportFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.REPORT_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setReportFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.REPORT_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setReportFlagOrder(boolean ascorder)
    {
        setOrder(StoragePlan.REPORT_FLAG, ascorder) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlagOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.REPORT_FLAG, ascorder) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のグループ順をセットします。
     */
    public void setReportFlagGroup()
    {
        setGroup(StoragePlan.REPORT_FLAG) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setReportFlagGroup(int prio)
    {
        setGroup(StoragePlan.REPORT_FLAG) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の情報取得を設定します。
     */
    public void setReportFlagCollect()
    {
        setCollect(StoragePlan.REPORT_FLAG) ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setReportFlagCollect(String sqlfunc)
    {
        setCollect(StoragePlan.REPORT_FLAG, sqlfunc, null) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 作業日(<code>WORK_DAY</code>)<br>
     * 文字列の検索値をセット作業日(<code>WORK_DAY</code>)します。
     */
    public void setWorkDay(String value)
    {
        setKey(StoragePlan.WORK_DAY, value) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDay(String[] values)
    {
        setKey(StoragePlan.WORK_DAY, values, true) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDay(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.WORK_DAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkDay(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.WORK_DAY, values, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkDay(String value, String compcode)
    {
        setKey(StoragePlan.WORK_DAY, value, compcode, "", "", true) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
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
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.WORK_DAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkDay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.WORK_DAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkDayOrder(boolean ascorder)
    {
        setOrder(StoragePlan.WORK_DAY, ascorder) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDayOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.WORK_DAY, ascorder) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のグループ順をセットします。
     */
    public void setWorkDayGroup()
    {
        setGroup(StoragePlan.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkDayGroup(int prio)
    {
        setGroup(StoragePlan.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の情報取得を設定します。
     */
    public void setWorkDayCollect()
    {
        setCollect(StoragePlan.WORK_DAY) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkDayCollect(String sqlfunc)
    {
        setCollect(StoragePlan.WORK_DAY, sqlfunc, null) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)の検索値をセットします。
     * 
     * @param value 登録区分(<code>REGIST_KIND</code>)<br>
     * 文字列の検索値をセット登録区分(<code>REGIST_KIND</code>)します。
     */
    public void setRegistKind(String value)
    {
        setKey(StoragePlan.REGIST_KIND, value) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistKind(String[] values)
    {
        setKey(StoragePlan.REGIST_KIND, values, true) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRegistKind(String[] values, String and_or_toNext)
    {
        setKey(StoragePlan.REGIST_KIND, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistKind(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.REGIST_KIND, values, and_or_toNext) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistKind(String value, String compcode)
    {
        setKey(StoragePlan.REGIST_KIND, value, compcode, "", "", true) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)の検索値をセットします。
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
    public void setRegistKind(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(StoragePlan.REGIST_KIND, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistKind(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(StoragePlan.REGIST_KIND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistKindOrder(boolean ascorder)
    {
        setOrder(StoragePlan.REGIST_KIND, ascorder) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistKindOrder(int prio, boolean ascorder)
    {
        setOrder(StoragePlan.REGIST_KIND, ascorder) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)のグループ順をセットします。
     */
    public void setRegistKindGroup()
    {
        setGroup(StoragePlan.REGIST_KIND) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRegistKindGroup(int prio)
    {
        setGroup(StoragePlan.REGIST_KIND) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)の情報取得を設定します。
     */
    public void setRegistKindCollect()
    {
        setCollect(StoragePlan.REGIST_KIND) ;
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRegistKindCollect(String sqlfunc)
    {
        setCollect(StoragePlan.REGIST_KIND, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
     */
    public void setRegistDate(Date value)
    {
        setKey(StoragePlan.REGIST_DATE, value) ;
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
        setKey(StoragePlan.REGIST_DATE, values, true) ;
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
        setKey(StoragePlan.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(StoragePlan.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(StoragePlan.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(StoragePlan.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(StoragePlan.REGIST_DATE, ascorder) ;
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
        setOrder(StoragePlan.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(StoragePlan.REGIST_DATE) ;
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
        setGroup(StoragePlan.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(StoragePlan.REGIST_DATE) ;
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
        setCollect(StoragePlan.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
     */
    public void setRegistPname(String value)
    {
        setKey(StoragePlan.REGIST_PNAME, value) ;
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
        setKey(StoragePlan.REGIST_PNAME, values, true) ;
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
        setKey(StoragePlan.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(StoragePlan.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(StoragePlan.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(StoragePlan.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(StoragePlan.REGIST_PNAME, ascorder) ;
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
        setOrder(StoragePlan.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(StoragePlan.REGIST_PNAME) ;
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
        setGroup(StoragePlan.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(StoragePlan.REGIST_PNAME) ;
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
        setCollect(StoragePlan.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(StoragePlan.LAST_UPDATE_DATE, value) ;
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
        setKey(StoragePlan.LAST_UPDATE_DATE, values, true) ;
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
        setKey(StoragePlan.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(StoragePlan.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(StoragePlan.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(StoragePlan.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(StoragePlan.LAST_UPDATE_DATE, ascorder) ;
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
        setOrder(StoragePlan.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(StoragePlan.LAST_UPDATE_DATE) ;
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
        setGroup(StoragePlan.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(StoragePlan.LAST_UPDATE_DATE) ;
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
        setCollect(StoragePlan.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(StoragePlan.LAST_UPDATE_PNAME, value) ;
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
        setKey(StoragePlan.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(StoragePlan.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(StoragePlan.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(StoragePlan.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(StoragePlan.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(StoragePlan.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(StoragePlan.LAST_UPDATE_PNAME, ascorder) ;
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
        setOrder(StoragePlan.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(StoragePlan.LAST_UPDATE_PNAME) ;
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
        setGroup(StoragePlan.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(StoragePlan.LAST_UPDATE_PNAME) ;
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
        setCollect(StoragePlan.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: StoragePlanSearchKey.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}
