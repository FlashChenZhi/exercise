// $Id: InventSettingSearchKey.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.entity.InventSetting;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * INVENTSETTING用の検索キークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class InventSettingSearchKey
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
    public InventSettingSearchKey()
    {
        super(InventSetting.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value スケジュールNo.(<code>SCHEDULE_NO</code>)<br>
     * 文字列の検索値をセットスケジュールNo.(<code>SCHEDULE_NO</code>)します。
     */
    public void setScheduleNo(String value)
    {
        setKey(InventSetting.SCHEDULE_NO, value) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNo(String[] values)
    {
        setKey(InventSetting.SCHEDULE_NO, values, true) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
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
        setKey(InventSetting.SCHEDULE_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setScheduleNo(String[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.SCHEDULE_NO, values, and_or_toNext) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setScheduleNo(String value, String compcode)
    {
        setKey(InventSetting.SCHEDULE_NO, value, compcode, "", "", true) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
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
        setKey(InventSetting.SCHEDULE_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setScheduleNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventSetting.SCHEDULE_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setScheduleNoOrder(boolean ascorder)
    {
        setOrder(InventSetting.SCHEDULE_NO, ascorder) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)のソート順をセットします。
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
        setOrder(InventSetting.SCHEDULE_NO, ascorder) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)のグループ順をセットします。
     */
    public void setScheduleNoGroup()
    {
        setGroup(InventSetting.SCHEDULE_NO) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setScheduleNoGroup(int prio)
    {
        setGroup(InventSetting.SCHEDULE_NO) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の情報取得を設定します。
     */
    public void setScheduleNoCollect()
    {
        setCollect(InventSetting.SCHEDULE_NO) ;
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setScheduleNoCollect(String sqlfunc)
    {
        setCollect(InventSetting.SCHEDULE_NO, sqlfunc, null) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 荷主コード(<code>CONSIGNOR_CODE</code>)<br>
     * 文字列の検索値をセット荷主コード(<code>CONSIGNOR_CODE</code>)します。
     */
    public void setConsignorCode(String value)
    {
        setKey(InventSetting.CONSIGNOR_CODE, value) ;
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
        setKey(InventSetting.CONSIGNOR_CODE, values, true) ;
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
        setKey(InventSetting.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(InventSetting.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(InventSetting.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventSetting.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(InventSetting.CONSIGNOR_CODE, ascorder) ;
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
        setOrder(InventSetting.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(InventSetting.CONSIGNOR_CODE) ;
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
        setGroup(InventSetting.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(InventSetting.CONSIGNOR_CODE) ;
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
        setCollect(InventSetting.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value エリアNo.(<code>AREA_NO</code>)<br>
     * 文字列の検索値をセットエリアNo.(<code>AREA_NO</code>)します。
     */
    public void setAreaNo(String value)
    {
        setKey(InventSetting.AREA_NO, value) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaNo(String[] values)
    {
        setKey(InventSetting.AREA_NO, values, true) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setAreaNo(String[] values, String and_or_toNext)
    {
        setKey(InventSetting.AREA_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAreaNo(String[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.AREA_NO, values, and_or_toNext) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAreaNo(String value, String compcode)
    {
        setKey(InventSetting.AREA_NO, value, compcode, "", "", true) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
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
    public void setAreaNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventSetting.AREA_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAreaNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventSetting.AREA_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAreaNoOrder(boolean ascorder)
    {
        setOrder(InventSetting.AREA_NO, ascorder) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAreaNoOrder(int prio, boolean ascorder)
    {
        setOrder(InventSetting.AREA_NO, ascorder) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のグループ順をセットします。
     */
    public void setAreaNoGroup()
    {
        setGroup(InventSetting.AREA_NO) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setAreaNoGroup(int prio)
    {
        setGroup(InventSetting.AREA_NO) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の情報取得を設定します。
     */
    public void setAreaNoCollect()
    {
        setCollect(InventSetting.AREA_NO) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setAreaNoCollect(String sqlfunc)
    {
        setCollect(InventSetting.AREA_NO, sqlfunc, null) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 開始棚No.(<code>FROM_LOCATION_NO</code>)<br>
     * 文字列の検索値をセット開始棚No.(<code>FROM_LOCATION_NO</code>)します。
     */
    public void setFromLocationNo(String value)
    {
        setKey(InventSetting.FROM_LOCATION_NO, value) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFromLocationNo(String[] values)
    {
        setKey(InventSetting.FROM_LOCATION_NO, values, true) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setFromLocationNo(String[] values, String and_or_toNext)
    {
        setKey(InventSetting.FROM_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFromLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.FROM_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setFromLocationNo(String value, String compcode)
    {
        setKey(InventSetting.FROM_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)の検索値をセットします。
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
    public void setFromLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventSetting.FROM_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setFromLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventSetting.FROM_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setFromLocationNoOrder(boolean ascorder)
    {
        setOrder(InventSetting.FROM_LOCATION_NO, ascorder) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFromLocationNoOrder(int prio, boolean ascorder)
    {
        setOrder(InventSetting.FROM_LOCATION_NO, ascorder) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)のグループ順をセットします。
     */
    public void setFromLocationNoGroup()
    {
        setGroup(InventSetting.FROM_LOCATION_NO) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setFromLocationNoGroup(int prio)
    {
        setGroup(InventSetting.FROM_LOCATION_NO) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)の情報取得を設定します。
     */
    public void setFromLocationNoCollect()
    {
        setCollect(InventSetting.FROM_LOCATION_NO) ;
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setFromLocationNoCollect(String sqlfunc)
    {
        setCollect(InventSetting.FROM_LOCATION_NO, sqlfunc, null) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 終了棚No.(<code>TO_LOCATION_NO</code>)<br>
     * 文字列の検索値をセット終了棚No.(<code>TO_LOCATION_NO</code>)します。
     */
    public void setToLocationNo(String value)
    {
        setKey(InventSetting.TO_LOCATION_NO, value) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setToLocationNo(String[] values)
    {
        setKey(InventSetting.TO_LOCATION_NO, values, true) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setToLocationNo(String[] values, String and_or_toNext)
    {
        setKey(InventSetting.TO_LOCATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setToLocationNo(String[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.TO_LOCATION_NO, values, and_or_toNext) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setToLocationNo(String value, String compcode)
    {
        setKey(InventSetting.TO_LOCATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)の検索値をセットします。
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
    public void setToLocationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventSetting.TO_LOCATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setToLocationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventSetting.TO_LOCATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setToLocationNoOrder(boolean ascorder)
    {
        setOrder(InventSetting.TO_LOCATION_NO, ascorder) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setToLocationNoOrder(int prio, boolean ascorder)
    {
        setOrder(InventSetting.TO_LOCATION_NO, ascorder) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)のグループ順をセットします。
     */
    public void setToLocationNoGroup()
    {
        setGroup(InventSetting.TO_LOCATION_NO) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setToLocationNoGroup(int prio)
    {
        setGroup(InventSetting.TO_LOCATION_NO) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)の情報取得を設定します。
     */
    public void setToLocationNoCollect()
    {
        setCollect(InventSetting.TO_LOCATION_NO) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setToLocationNoCollect(String sqlfunc)
    {
        setCollect(InventSetting.TO_LOCATION_NO, sqlfunc, null) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態フラグ(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値をセット状態フラグ(<code>STATUS_FLAG</code>)します。
     */
    public void setStatusFlag(String value)
    {
        setKey(InventSetting.STATUS_FLAG, value) ;
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
        setKey(InventSetting.STATUS_FLAG, values, true) ;
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
        setKey(InventSetting.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(InventSetting.STATUS_FLAG, value, compcode, "", "", true) ;
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
        setKey(InventSetting.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventSetting.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusFlagOrder(boolean ascorder)
    {
        setOrder(InventSetting.STATUS_FLAG, ascorder) ;
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
        setOrder(InventSetting.STATUS_FLAG, ascorder) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)のグループ順をセットします。
     */
    public void setStatusFlagGroup()
    {
        setGroup(InventSetting.STATUS_FLAG) ;
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
        setGroup(InventSetting.STATUS_FLAG) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>)の情報取得を設定します。
     */
    public void setStatusFlagCollect()
    {
        setCollect(InventSetting.STATUS_FLAG) ;
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
        setCollect(InventSetting.STATUS_FLAG, sqlfunc, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 登録日時(<code>REGIST_DATE</code>)<br>
     * 日付の検索値をセット登録日時(<code>REGIST_DATE</code>)します。
     */
    public void setRegistDate(Date value)
    {
        setKey(InventSetting.REGIST_DATE, value) ;
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
        setKey(InventSetting.REGIST_DATE, values, true) ;
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
        setKey(InventSetting.REGIST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistDate(Date[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.REGIST_DATE, values, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistDate(Date value, String compcode)
    {
        setKey(InventSetting.REGIST_DATE, value, compcode, "", "", true) ;
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
        setKey(InventSetting.REGIST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventSetting.REGIST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistDateOrder(boolean ascorder)
    {
        setOrder(InventSetting.REGIST_DATE, ascorder) ;
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
        setOrder(InventSetting.REGIST_DATE, ascorder) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)のグループ順をセットします。
     */
    public void setRegistDateGroup()
    {
        setGroup(InventSetting.REGIST_DATE) ;
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
        setGroup(InventSetting.REGIST_DATE) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)の情報取得を設定します。
     */
    public void setRegistDateCollect()
    {
        setCollect(InventSetting.REGIST_DATE) ;
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
        setCollect(InventSetting.REGIST_DATE, sqlfunc, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 登録処理名(<code>REGIST_PNAME</code>)<br>
     * 文字列の検索値をセット登録処理名(<code>REGIST_PNAME</code>)します。
     */
    public void setRegistPname(String value)
    {
        setKey(InventSetting.REGIST_PNAME, value) ;
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
        setKey(InventSetting.REGIST_PNAME, values, true) ;
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
        setKey(InventSetting.REGIST_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRegistPname(String[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.REGIST_PNAME, values, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRegistPname(String value, String compcode)
    {
        setKey(InventSetting.REGIST_PNAME, value, compcode, "", "", true) ;
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
        setKey(InventSetting.REGIST_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventSetting.REGIST_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRegistPnameOrder(boolean ascorder)
    {
        setOrder(InventSetting.REGIST_PNAME, ascorder) ;
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
        setOrder(InventSetting.REGIST_PNAME, ascorder) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)のグループ順をセットします。
     */
    public void setRegistPnameGroup()
    {
        setGroup(InventSetting.REGIST_PNAME) ;
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
        setGroup(InventSetting.REGIST_PNAME) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)の情報取得を設定します。
     */
    public void setRegistPnameCollect()
    {
        setCollect(InventSetting.REGIST_PNAME) ;
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
        setCollect(InventSetting.REGIST_PNAME, sqlfunc, null) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 最終更新日時(<code>LAST_UPDATE_DATE</code>)<br>
     * 日付の検索値をセット最終更新日時(<code>LAST_UPDATE_DATE</code>)します。
     */
    public void setLastUpdateDate(Date value)
    {
        setKey(InventSetting.LAST_UPDATE_DATE, value) ;
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
        setKey(InventSetting.LAST_UPDATE_DATE, values, true) ;
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
        setKey(InventSetting.LAST_UPDATE_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdateDate(Date[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.LAST_UPDATE_DATE, values, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdateDate(Date value, String compcode)
    {
        setKey(InventSetting.LAST_UPDATE_DATE, value, compcode, "", "", true) ;
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
        setKey(InventSetting.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventSetting.LAST_UPDATE_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdateDateOrder(boolean ascorder)
    {
        setOrder(InventSetting.LAST_UPDATE_DATE, ascorder) ;
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
        setOrder(InventSetting.LAST_UPDATE_DATE, ascorder) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)のグループ順をセットします。
     */
    public void setLastUpdateDateGroup()
    {
        setGroup(InventSetting.LAST_UPDATE_DATE) ;
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
        setGroup(InventSetting.LAST_UPDATE_DATE) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)の情報取得を設定します。
     */
    public void setLastUpdateDateCollect()
    {
        setCollect(InventSetting.LAST_UPDATE_DATE) ;
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
        setCollect(InventSetting.LAST_UPDATE_DATE, sqlfunc, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)<br>
     * 文字列の検索値をセット最終更新処理名(<code>LAST_UPDATE_PNAME</code>)します。
     */
    public void setLastUpdatePname(String value)
    {
        setKey(InventSetting.LAST_UPDATE_PNAME, value) ;
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
        setKey(InventSetting.LAST_UPDATE_PNAME, values, true) ;
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
        setKey(InventSetting.LAST_UPDATE_PNAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUpdatePname(String[] values, boolean and_or_toNext)
    {
        setKey(InventSetting.LAST_UPDATE_PNAME, values, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUpdatePname(String value, String compcode)
    {
        setKey(InventSetting.LAST_UPDATE_PNAME, value, compcode, "", "", true) ;
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
        setKey(InventSetting.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventSetting.LAST_UPDATE_PNAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUpdatePnameOrder(boolean ascorder)
    {
        setOrder(InventSetting.LAST_UPDATE_PNAME, ascorder) ;
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
        setOrder(InventSetting.LAST_UPDATE_PNAME, ascorder) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)のグループ順をセットします。
     */
    public void setLastUpdatePnameGroup()
    {
        setGroup(InventSetting.LAST_UPDATE_PNAME) ;
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
        setGroup(InventSetting.LAST_UPDATE_PNAME) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)の情報取得を設定します。
     */
    public void setLastUpdatePnameCollect()
    {
        setCollect(InventSetting.LAST_UPDATE_PNAME) ;
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
        setCollect(InventSetting.LAST_UPDATE_PNAME, sqlfunc, null) ;
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
        return "$Id: InventSettingSearchKey.java 87 2008-10-04 03:07:38Z admin $" ;
    }
}
