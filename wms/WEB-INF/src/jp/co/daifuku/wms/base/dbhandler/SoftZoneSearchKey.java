// $Id: SoftZoneSearchKey.java 4122 2009-04-10 10:58:38Z ota $
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
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * SOFTZONE用の検索キークラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class SoftZoneSearchKey
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
    public SoftZoneSearchKey()
    {
        super(SoftZone.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value ソフトゾーンID(<code>SOFT_ZONE_ID</code>)<br>
     * 文字列の検索値をセットソフトゾーンID(<code>SOFT_ZONE_ID</code>)します。
     */
    public void setSoftZoneId(String value)
    {
        setKey(SoftZone.SOFT_ZONE_ID, value) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String[] values)
    {
        setKey(SoftZone.SOFT_ZONE_ID, values, true) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneId(String[] values, String and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_ID, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneId(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_ID, values, and_or_toNext) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSoftZoneId(String value, String compcode)
    {
        setKey(SoftZone.SOFT_ZONE_ID, value, compcode, "", "", true) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
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
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneId(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_ID, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSoftZoneIdOrder(boolean ascorder)
    {
        setOrder(SoftZone.SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneIdOrder(int prio, boolean ascorder)
    {
        setOrder(SoftZone.SOFT_ZONE_ID, ascorder) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)のグループ順をセットします。
     */
    public void setSoftZoneIdGroup()
    {
        setGroup(SoftZone.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneIdGroup(int prio)
    {
        setGroup(SoftZone.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の情報取得を設定します。
     */
    public void setSoftZoneIdCollect()
    {
        setCollect(SoftZone.SOFT_ZONE_ID) ;
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSoftZoneIdCollect(String sqlfunc)
    {
        setCollect(SoftZone.SOFT_ZONE_ID, sqlfunc, null) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value ゾーン名称(<code>SOFT_ZONE_NAME</code>)<br>
     * 文字列の検索値をセットゾーン名称(<code>SOFT_ZONE_NAME</code>)します。
     */
    public void setSoftZoneName(String value)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, value) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneName(String[] values)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, values, true) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneName(String[] values, String and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneName(String[] values, boolean and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, values, and_or_toNext) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSoftZoneName(String value, String compcode)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
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
    public void setSoftZoneName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSoftZoneName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(SoftZone.SOFT_ZONE_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSoftZoneNameOrder(boolean ascorder)
    {
        setOrder(SoftZone.SOFT_ZONE_NAME, ascorder) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneNameOrder(int prio, boolean ascorder)
    {
        setOrder(SoftZone.SOFT_ZONE_NAME, ascorder) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)のグループ順をセットします。
     */
    public void setSoftZoneNameGroup()
    {
        setGroup(SoftZone.SOFT_ZONE_NAME) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSoftZoneNameGroup(int prio)
    {
        setGroup(SoftZone.SOFT_ZONE_NAME) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の情報取得を設定します。
     */
    public void setSoftZoneNameCollect()
    {
        setCollect(SoftZone.SOFT_ZONE_NAME) ;
    }

    /**
     * ゾーン名称(<code>SOFT_ZONE_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSoftZoneNameCollect(String sqlfunc)
    {
        setCollect(SoftZone.SOFT_ZONE_NAME, sqlfunc, null) ;
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
        return "$Id: SoftZoneSearchKey.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}
