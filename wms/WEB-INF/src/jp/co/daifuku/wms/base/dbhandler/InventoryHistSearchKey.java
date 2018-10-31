// $Id: SearchKeyProductfile.java 1749 2008-12-04 00:32:00Z dmori $
// $LastChangedRevision: 1749 $
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
import jp.co.daifuku.wms.base.entity.InventoryHist;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * INVENTORYHIST用の検索キークラスです。
 *
 * @version $Revision: 1746 $, $Date: 2008-12-04 09:21:00 +0900 (木, 04 12 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */


public class InventoryHistSearchKey
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
    public InventoryHistSearchKey()
    {
        super(InventoryHist.STORE_NAME) ;
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
        setKey(InventoryHist.CONSIGNOR_CODE, value) ;
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
        setKey(InventoryHist.CONSIGNOR_CODE, values, true) ;
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
        setKey(InventoryHist.CONSIGNOR_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setConsignorCode(String[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.CONSIGNOR_CODE, values, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setConsignorCode(String value, String compcode)
    {
        setKey(InventoryHist.CONSIGNOR_CODE, value, compcode, "", "", true) ;
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
        setKey(InventoryHist.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventoryHist.CONSIGNOR_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setConsignorCodeOrder(boolean ascorder)
    {
        setOrder(InventoryHist.CONSIGNOR_CODE, ascorder) ;
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
        setOrder(InventoryHist.CONSIGNOR_CODE, ascorder) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)のグループ順をセットします。
     */
    public void setConsignorCodeGroup()
    {
        setGroup(InventoryHist.CONSIGNOR_CODE) ;
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
        setGroup(InventoryHist.CONSIGNOR_CODE) ;
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)の情報取得を設定します。
     */
    public void setConsignorCodeCollect()
    {
        setCollect(InventoryHist.CONSIGNOR_CODE) ;
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
        setCollect(InventoryHist.CONSIGNOR_CODE, sqlfunc, null) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 商品コード(<code>ITEM_CODE</code>)<br>
     * 文字列の検索値をセット商品コード(<code>ITEM_CODE</code>)します。
     */
    public void setItemCode(String value)
    {
        setKey(InventoryHist.ITEM_CODE, value) ;
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
        setKey(InventoryHist.ITEM_CODE, values, true) ;
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
        setKey(InventoryHist.ITEM_CODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setItemCode(String[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.ITEM_CODE, values, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setItemCode(String value, String compcode)
    {
        setKey(InventoryHist.ITEM_CODE, value, compcode, "", "", true) ;
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
        setKey(InventoryHist.ITEM_CODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(InventoryHist.ITEM_CODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setItemCodeOrder(boolean ascorder)
    {
        setOrder(InventoryHist.ITEM_CODE, ascorder) ;
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
        setOrder(InventoryHist.ITEM_CODE, ascorder) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)のグループ順をセットします。
     */
    public void setItemCodeGroup()
    {
        setGroup(InventoryHist.ITEM_CODE) ;
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
        setGroup(InventoryHist.ITEM_CODE) ;
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>)の情報取得を設定します。
     */
    public void setItemCodeCollect()
    {
        setCollect(InventoryHist.ITEM_CODE) ;
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
        setCollect(InventoryHist.ITEM_CODE, sqlfunc, null) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value ロットNo(<code>LOT_NO</code>)<br>
     * 文字列の検索値をセットロットNo(<code>LOT_NO</code>)します。
     */
    public void setLotNo(String value)
    {
        setKey(InventoryHist.LOT_NO, value) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLotNo(String[] values)
    {
        setKey(InventoryHist.LOT_NO, values, true) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLotNo(String[] values, String and_or_toNext)
    {
        setKey(InventoryHist.LOT_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLotNo(String[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.LOT_NO, values, and_or_toNext) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLotNo(String value, String compcode)
    {
        setKey(InventoryHist.LOT_NO, value, compcode, "", "", true) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
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
    public void setLotNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventoryHist.LOT_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLotNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.LOT_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLotNoOrder(boolean ascorder)
    {
        setOrder(InventoryHist.LOT_NO, ascorder) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLotNoOrder(int prio, boolean ascorder)
    {
        setOrder(InventoryHist.LOT_NO, ascorder) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)のグループ順をセットします。
     */
    public void setLotNoGroup()
    {
        setGroup(InventoryHist.LOT_NO) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLotNoGroup(int prio)
    {
        setGroup(InventoryHist.LOT_NO) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の情報取得を設定します。
     */
    public void setLotNoCollect()
    {
        setCollect(InventoryHist.LOT_NO) ;
    }

    /**
     * ロットNo(<code>LOT_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLotNoCollect(String sqlfunc)
    {
        setCollect(InventoryHist.LOT_NO, sqlfunc, null) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param value 集計日(<code>SUMMARY_DATE</code>)<br>
     * 文字列の検索値をセット集計日(<code>SUMMARY_DATE</code>)します。
     */
    public void setSummaryDate(String value)
    {
        setKey(InventoryHist.SUMMARY_DATE, value) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSummaryDate(String[] values)
    {
        setKey(InventoryHist.SUMMARY_DATE, values, true) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSummaryDate(String[] values, String and_or_toNext)
    {
        setKey(InventoryHist.SUMMARY_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSummaryDate(String[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.SUMMARY_DATE, values, and_or_toNext) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSummaryDate(String value, String compcode)
    {
        setKey(InventoryHist.SUMMARY_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
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
    public void setSummaryDate(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventoryHist.SUMMARY_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSummaryDate(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.SUMMARY_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSummaryDateOrder(boolean ascorder)
    {
        setOrder(InventoryHist.SUMMARY_DATE, ascorder) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSummaryDateOrder(int prio, boolean ascorder)
    {
        setOrder(InventoryHist.SUMMARY_DATE, ascorder) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)のグループ順をセットします。
     */
    public void setSummaryDateGroup()
    {
        setGroup(InventoryHist.SUMMARY_DATE) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSummaryDateGroup(int prio)
    {
        setGroup(InventoryHist.SUMMARY_DATE) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の情報取得を設定します。
     */
    public void setSummaryDateCollect()
    {
        setCollect(InventoryHist.SUMMARY_DATE) ;
    }

    /**
     * 集計日(<code>SUMMARY_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSummaryDateCollect(String sqlfunc)
    {
        setCollect(InventoryHist.SUMMARY_DATE, sqlfunc, null) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param value 当日在庫数(<code>STOCK_QTY</code>)<br>
     * 数値の検索値をセット当日在庫数(<code>STOCK_QTY</code>)します。
     */
    public void setStockQty(int value)
    {
        setKey(InventoryHist.STOCK_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockQty(int[] values)
    {
        setKey(InventoryHist.STOCK_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStockQty(int[] values, String and_or_toNext)
    {
        setKey(InventoryHist.STOCK_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStockQty(int[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.STOCK_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStockQty(int value, String compcode)
    {
        setKey(InventoryHist.STOCK_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
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
    public void setStockQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventoryHist.STOCK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStockQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.STOCK_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStockQtyOrder(boolean ascorder)
    {
        setOrder(InventoryHist.STOCK_QTY, ascorder) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStockQtyOrder(int prio, boolean ascorder)
    {
        setOrder(InventoryHist.STOCK_QTY, ascorder) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)のグループ順をセットします。
     */
    public void setStockQtyGroup()
    {
        setGroup(InventoryHist.STOCK_QTY) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStockQtyGroup(int prio)
    {
        setGroup(InventoryHist.STOCK_QTY) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の情報取得を設定します。
     */
    public void setStockQtyCollect()
    {
        setCollect(InventoryHist.STOCK_QTY) ;
    }

    /**
     * 当日在庫数(<code>STOCK_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStockQtyCollect(String sqlfunc)
    {
        setCollect(InventoryHist.STOCK_QTY, sqlfunc, null) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 当日入庫数(<code>STORAGE_QTY</code>)<br>
     * 数値の検索値をセット当日入庫数(<code>STORAGE_QTY</code>)します。
     */
    public void setStorageQty(int value)
    {
        setKey(InventoryHist.STORAGE_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageQty(int[] values)
    {
        setKey(InventoryHist.STORAGE_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStorageQty(int[] values, String and_or_toNext)
    {
        setKey(InventoryHist.STORAGE_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageQty(int[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.STORAGE_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStorageQty(int value, String compcode)
    {
        setKey(InventoryHist.STORAGE_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
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
    public void setStorageQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventoryHist.STORAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStorageQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.STORAGE_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStorageQtyOrder(boolean ascorder)
    {
        setOrder(InventoryHist.STORAGE_QTY, ascorder) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageQtyOrder(int prio, boolean ascorder)
    {
        setOrder(InventoryHist.STORAGE_QTY, ascorder) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)のグループ順をセットします。
     */
    public void setStorageQtyGroup()
    {
        setGroup(InventoryHist.STORAGE_QTY) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStorageQtyGroup(int prio)
    {
        setGroup(InventoryHist.STORAGE_QTY) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の情報取得を設定します。
     */
    public void setStorageQtyCollect()
    {
        setCollect(InventoryHist.STORAGE_QTY) ;
    }

    /**
     * 当日入庫数(<code>STORAGE_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStorageQtyCollect(String sqlfunc)
    {
        setCollect(InventoryHist.STORAGE_QTY, sqlfunc, null) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param value 当日出庫数(<code>RETRIEVAL_QTY</code>)<br>
     * 数値の検索値をセット当日出庫数(<code>RETRIEVAL_QTY</code>)します。
     */
    public void setRetrievalQty(int value)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalQty(int[] values)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalQty(int[] values, String and_or_toNext)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalQty(int[] values, boolean and_or_toNext)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRetrievalQty(int value, String compcode)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
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
    public void setRetrievalQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRetrievalQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(InventoryHist.RETRIEVAL_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRetrievalQtyOrder(boolean ascorder)
    {
        setOrder(InventoryHist.RETRIEVAL_QTY, ascorder) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalQtyOrder(int prio, boolean ascorder)
    {
        setOrder(InventoryHist.RETRIEVAL_QTY, ascorder) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)のグループ順をセットします。
     */
    public void setRetrievalQtyGroup()
    {
        setGroup(InventoryHist.RETRIEVAL_QTY) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRetrievalQtyGroup(int prio)
    {
        setGroup(InventoryHist.RETRIEVAL_QTY) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の情報取得を設定します。
     */
    public void setRetrievalQtyCollect()
    {
        setCollect(InventoryHist.RETRIEVAL_QTY) ;
    }

    /**
     * 当日出庫数(<code>RETRIEVAL_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRetrievalQtyCollect(String sqlfunc)
    {
        setCollect(InventoryHist.RETRIEVAL_QTY, sqlfunc, null) ;
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
        return "$Id: SearchKeyBasefile.java 1746 2008-12-04 00:21:00Z dmori $" ;
    }
}
