// $Id: StationSearchKey.java 5295 2009-10-28 05:23:13Z ota $
// $LastChangedRevision: 5295 $
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
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * STATION用の検索キークラスです。
 *
 * @version $Revision: 5295 $, $Date: 2009-10-28 14:23:13 +0900 (水, 28 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */


public class StationSearchKey
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
    public StationSearchKey()
    {
        super(Station.STORE_NAME) ;
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
        setKey(Station.STATION_NO, value) ;
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
        setKey(Station.STATION_NO, values, true) ;
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
        setKey(Station.STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(Station.STATION_NO, value, compcode, "", "", true) ;
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
        setKey(Station.STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Station.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStationNoOrder(boolean ascorder)
    {
        setOrder(Station.STATION_NO, ascorder) ;
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
        setOrder(Station.STATION_NO, ascorder) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)のグループ順をセットします。
     */
    public void setStationNoGroup()
    {
        setGroup(Station.STATION_NO) ;
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
        setGroup(Station.STATION_NO) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の情報取得を設定します。
     */
    public void setStationNoCollect()
    {
        setCollect(Station.STATION_NO) ;
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
        setCollect(Station.STATION_NO, sqlfunc, null) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。
     * 
     * @param value 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)<br>
     * 数値の検索値をセット最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)します。
     */
    public void setMaxPalletQty(int value)
    {
        setKey(Station.MAX_PALLET_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxPalletQty(int[] values)
    {
        setKey(Station.MAX_PALLET_QTY, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxPalletQty(int[] values, String and_or_toNext)
    {
        setKey(Station.MAX_PALLET_QTY, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxPalletQty(int[] values, boolean and_or_toNext)
    {
        setKey(Station.MAX_PALLET_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMaxPalletQty(int value, String compcode)
    {
        setKey(Station.MAX_PALLET_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。
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
    public void setMaxPalletQty(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.MAX_PALLET_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxPalletQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MAX_PALLET_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMaxPalletQtyOrder(boolean ascorder)
    {
        setOrder(Station.MAX_PALLET_QTY, ascorder) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxPalletQtyOrder(int prio, boolean ascorder)
    {
        setOrder(Station.MAX_PALLET_QTY, ascorder) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)のグループ順をセットします。
     */
    public void setMaxPalletQtyGroup()
    {
        setGroup(Station.MAX_PALLET_QTY) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxPalletQtyGroup(int prio)
    {
        setGroup(Station.MAX_PALLET_QTY) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の情報取得を設定します。
     */
    public void setMaxPalletQtyCollect()
    {
        setCollect(Station.MAX_PALLET_QTY) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMaxPalletQtyCollect(String sqlfunc)
    {
        setCollect(Station.MAX_PALLET_QTY, sqlfunc, null) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)<br>
     * 数値の検索値をセット最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)します。
     */
    public void setMaxInstruction(int value)
    {
        setKey(Station.MAX_INSTRUCTION, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxInstruction(int[] values)
    {
        setKey(Station.MAX_INSTRUCTION, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setMaxInstruction(int[] values, String and_or_toNext)
    {
        setKey(Station.MAX_INSTRUCTION, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxInstruction(int[] values, boolean and_or_toNext)
    {
        setKey(Station.MAX_INSTRUCTION, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setMaxInstruction(int value, String compcode)
    {
        setKey(Station.MAX_INSTRUCTION, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。
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
    public void setMaxInstruction(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.MAX_INSTRUCTION, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setMaxInstruction(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MAX_INSTRUCTION, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setMaxInstructionOrder(boolean ascorder)
    {
        setOrder(Station.MAX_INSTRUCTION, ascorder) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxInstructionOrder(int prio, boolean ascorder)
    {
        setOrder(Station.MAX_INSTRUCTION, ascorder) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)のグループ順をセットします。
     */
    public void setMaxInstructionGroup()
    {
        setGroup(Station.MAX_INSTRUCTION) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setMaxInstructionGroup(int prio)
    {
        setGroup(Station.MAX_INSTRUCTION) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の情報取得を設定します。
     */
    public void setMaxInstructionCollect()
    {
        setCollect(Station.MAX_INSTRUCTION) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setMaxInstructionCollect(String sqlfunc)
    {
        setCollect(Station.MAX_INSTRUCTION, sqlfunc, null) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。
     * 
     * @param value 送信可能区分(<code>SENDABLE</code>)<br>
     * 文字列の検索値をセット送信可能区分(<code>SENDABLE</code>)します。
     */
    public void setSendable(String value)
    {
        setKey(Station.SENDABLE, value) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSendable(String[] values)
    {
        setKey(Station.SENDABLE, values, true) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSendable(String[] values, String and_or_toNext)
    {
        setKey(Station.SENDABLE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSendable(String[] values, boolean and_or_toNext)
    {
        setKey(Station.SENDABLE, values, and_or_toNext) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSendable(String value, String compcode)
    {
        setKey(Station.SENDABLE, value, compcode, "", "", true) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。
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
    public void setSendable(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.SENDABLE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSendable(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.SENDABLE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSendableOrder(boolean ascorder)
    {
        setOrder(Station.SENDABLE, ascorder) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSendableOrder(int prio, boolean ascorder)
    {
        setOrder(Station.SENDABLE, ascorder) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)のグループ順をセットします。
     */
    public void setSendableGroup()
    {
        setGroup(Station.SENDABLE) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSendableGroup(int prio)
    {
        setGroup(Station.SENDABLE) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の情報取得を設定します。
     */
    public void setSendableCollect()
    {
        setCollect(Station.SENDABLE) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSendableCollect(String sqlfunc)
    {
        setCollect(Station.SENDABLE, sqlfunc, null) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS</code>)<br>
     * 文字列の検索値をセット状態(<code>STATUS</code>)します。
     */
    public void setStatus(String value)
    {
        setKey(Station.STATUS, value) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatus(String[] values)
    {
        setKey(Station.STATUS, values, true) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStatus(String[] values, String and_or_toNext)
    {
        setKey(Station.STATUS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatus(String[] values, boolean and_or_toNext)
    {
        setKey(Station.STATUS, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStatus(String value, String compcode)
    {
        setKey(Station.STATUS, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
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
    public void setStatus(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.STATUS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStatus(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.STATUS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStatusOrder(boolean ascorder)
    {
        setOrder(Station.STATUS, ascorder) ;
    }

    /**
     * 状態(<code>STATUS</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusOrder(int prio, boolean ascorder)
    {
        setOrder(Station.STATUS, ascorder) ;
    }

    /**
     * 状態(<code>STATUS</code>)のグループ順をセットします。
     */
    public void setStatusGroup()
    {
        setGroup(Station.STATUS) ;
    }

    /**
     * 状態(<code>STATUS</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStatusGroup(int prio)
    {
        setGroup(Station.STATUS) ;
    }

    /**
     * 状態(<code>STATUS</code>)の情報取得を設定します。
     */
    public void setStatusCollect()
    {
        setCollect(Station.STATUS) ;
    }

    /**
     * 状態(<code>STATUS</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStatusCollect(String sqlfunc)
    {
        setCollect(Station.STATUS, sqlfunc, null) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value コントローラーNo.(<code>CONTROLLER_NO</code>)<br>
     * 文字列の検索値をセットコントローラーNo.(<code>CONTROLLER_NO</code>)します。
     */
    public void setControllerNo(String value)
    {
        setKey(Station.CONTROLLER_NO, value) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNo(String[] values)
    {
        setKey(Station.CONTROLLER_NO, values, true) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
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
        setKey(Station.CONTROLLER_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControllerNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.CONTROLLER_NO, values, and_or_toNext) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setControllerNo(String value, String compcode)
    {
        setKey(Station.CONTROLLER_NO, value, compcode, "", "", true) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
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
        setKey(Station.CONTROLLER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.CONTROLLER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setControllerNoOrder(boolean ascorder)
    {
        setOrder(Station.CONTROLLER_NO, ascorder) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)のソート順をセットします。
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
        setOrder(Station.CONTROLLER_NO, ascorder) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)のグループ順をセットします。
     */
    public void setControllerNoGroup()
    {
        setGroup(Station.CONTROLLER_NO) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setControllerNoGroup(int prio)
    {
        setGroup(Station.CONTROLLER_NO) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の情報取得を設定します。
     */
    public void setControllerNoCollect()
    {
        setCollect(Station.CONTROLLER_NO) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setControllerNoCollect(String sqlfunc)
    {
        setCollect(Station.CONTROLLER_NO, sqlfunc, null) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value ステーション種別(<code>STATION_TYPE</code>)<br>
     * 文字列の検索値をセットステーション種別(<code>STATION_TYPE</code>)します。
     */
    public void setStationType(String value)
    {
        setKey(Station.STATION_TYPE, value) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationType(String[] values)
    {
        setKey(Station.STATION_TYPE, values, true) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationType(String[] values, String and_or_toNext)
    {
        setKey(Station.STATION_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationType(String[] values, boolean and_or_toNext)
    {
        setKey(Station.STATION_TYPE, values, and_or_toNext) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStationType(String value, String compcode)
    {
        setKey(Station.STATION_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。
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
    public void setStationType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.STATION_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.STATION_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStationTypeOrder(boolean ascorder)
    {
        setOrder(Station.STATION_TYPE, ascorder) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStationTypeOrder(int prio, boolean ascorder)
    {
        setOrder(Station.STATION_TYPE, ascorder) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)のグループ順をセットします。
     */
    public void setStationTypeGroup()
    {
        setGroup(Station.STATION_TYPE) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStationTypeGroup(int prio)
    {
        setGroup(Station.STATION_TYPE) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の情報取得を設定します。
     */
    public void setStationTypeCollect()
    {
        setCollect(Station.STATION_TYPE) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStationTypeCollect(String sqlfunc)
    {
        setCollect(Station.STATION_TYPE, sqlfunc, null) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。
     * 
     * @param value 設定種別(<code>SETTING_TYPE</code>)<br>
     * 文字列の検索値をセット設定種別(<code>SETTING_TYPE</code>)します。
     */
    public void setSettingType(String value)
    {
        setKey(Station.SETTING_TYPE, value) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSettingType(String[] values)
    {
        setKey(Station.SETTING_TYPE, values, true) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSettingType(String[] values, String and_or_toNext)
    {
        setKey(Station.SETTING_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSettingType(String[] values, boolean and_or_toNext)
    {
        setKey(Station.SETTING_TYPE, values, and_or_toNext) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSettingType(String value, String compcode)
    {
        setKey(Station.SETTING_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。
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
    public void setSettingType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.SETTING_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSettingType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.SETTING_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSettingTypeOrder(boolean ascorder)
    {
        setOrder(Station.SETTING_TYPE, ascorder) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSettingTypeOrder(int prio, boolean ascorder)
    {
        setOrder(Station.SETTING_TYPE, ascorder) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)のグループ順をセットします。
     */
    public void setSettingTypeGroup()
    {
        setGroup(Station.SETTING_TYPE) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSettingTypeGroup(int prio)
    {
        setGroup(Station.SETTING_TYPE) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の情報取得を設定します。
     */
    public void setSettingTypeCollect()
    {
        setCollect(Station.SETTING_TYPE) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSettingTypeCollect(String sqlfunc)
    {
        setCollect(Station.SETTING_TYPE, sqlfunc, null) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業場種別(<code>WORKPLACE_TYPE</code>)<br>
     * 文字列の検索値をセット作業場種別(<code>WORKPLACE_TYPE</code>)します。
     */
    public void setWorkplaceType(String value)
    {
        setKey(Station.WORKPLACE_TYPE, value) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkplaceType(String[] values)
    {
        setKey(Station.WORKPLACE_TYPE, values, true) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWorkplaceType(String[] values, String and_or_toNext)
    {
        setKey(Station.WORKPLACE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkplaceType(String[] values, boolean and_or_toNext)
    {
        setKey(Station.WORKPLACE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWorkplaceType(String value, String compcode)
    {
        setKey(Station.WORKPLACE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。
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
    public void setWorkplaceType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.WORKPLACE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWorkplaceType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.WORKPLACE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWorkplaceTypeOrder(boolean ascorder)
    {
        setOrder(Station.WORKPLACE_TYPE, ascorder) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkplaceTypeOrder(int prio, boolean ascorder)
    {
        setOrder(Station.WORKPLACE_TYPE, ascorder) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)のグループ順をセットします。
     */
    public void setWorkplaceTypeGroup()
    {
        setGroup(Station.WORKPLACE_TYPE) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWorkplaceTypeGroup(int prio)
    {
        setGroup(Station.WORKPLACE_TYPE) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の情報取得を設定します。
     */
    public void setWorkplaceTypeCollect()
    {
        setCollect(Station.WORKPLACE_TYPE) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWorkplaceTypeCollect(String sqlfunc)
    {
        setCollect(Station.WORKPLACE_TYPE, sqlfunc, null) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。
     * 
     * @param value 作業表示運用(<code>OPERATION_DISPLAY</code>)<br>
     * 文字列の検索値をセット作業表示運用(<code>OPERATION_DISPLAY</code>)します。
     */
    public void setOperationDisplay(String value)
    {
        setKey(Station.OPERATION_DISPLAY, value) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOperationDisplay(String[] values)
    {
        setKey(Station.OPERATION_DISPLAY, values, true) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setOperationDisplay(String[] values, String and_or_toNext)
    {
        setKey(Station.OPERATION_DISPLAY, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setOperationDisplay(String[] values, boolean and_or_toNext)
    {
        setKey(Station.OPERATION_DISPLAY, values, and_or_toNext) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setOperationDisplay(String value, String compcode)
    {
        setKey(Station.OPERATION_DISPLAY, value, compcode, "", "", true) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。
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
    public void setOperationDisplay(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.OPERATION_DISPLAY, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setOperationDisplay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.OPERATION_DISPLAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setOperationDisplayOrder(boolean ascorder)
    {
        setOrder(Station.OPERATION_DISPLAY, ascorder) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setOperationDisplayOrder(int prio, boolean ascorder)
    {
        setOrder(Station.OPERATION_DISPLAY, ascorder) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)のグループ順をセットします。
     */
    public void setOperationDisplayGroup()
    {
        setGroup(Station.OPERATION_DISPLAY) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setOperationDisplayGroup(int prio)
    {
        setGroup(Station.OPERATION_DISPLAY) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の情報取得を設定します。
     */
    public void setOperationDisplayCollect()
    {
        setCollect(Station.OPERATION_DISPLAY) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setOperationDisplayCollect(String sqlfunc)
    {
        setCollect(Station.OPERATION_DISPLAY, sqlfunc, null) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。
     * 
     * @param value ステーション名称(<code>STATION_NAME</code>)<br>
     * 文字列の検索値をセットステーション名称(<code>STATION_NAME</code>)します。
     */
    public void setStationName(String value)
    {
        setKey(Station.STATION_NAME, value) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationName(String[] values)
    {
        setKey(Station.STATION_NAME, values, true) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setStationName(String[] values, String and_or_toNext)
    {
        setKey(Station.STATION_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationName(String[] values, boolean and_or_toNext)
    {
        setKey(Station.STATION_NAME, values, and_or_toNext) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setStationName(String value, String compcode)
    {
        setKey(Station.STATION_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。
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
    public void setStationName(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.STATION_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setStationName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.STATION_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setStationNameOrder(boolean ascorder)
    {
        setOrder(Station.STATION_NAME, ascorder) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStationNameOrder(int prio, boolean ascorder)
    {
        setOrder(Station.STATION_NAME, ascorder) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)のグループ順をセットします。
     */
    public void setStationNameGroup()
    {
        setGroup(Station.STATION_NAME) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setStationNameGroup(int prio)
    {
        setGroup(Station.STATION_NAME) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の情報取得を設定します。
     */
    public void setStationNameCollect()
    {
        setCollect(Station.STATION_NAME) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setStationNameCollect(String sqlfunc)
    {
        setCollect(Station.STATION_NAME, sqlfunc, null) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。
     * 
     * @param value 中断中フラグ(<code>SUSPEND</code>)<br>
     * 文字列の検索値をセット中断中フラグ(<code>SUSPEND</code>)します。
     */
    public void setSuspend(String value)
    {
        setKey(Station.SUSPEND, value) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSuspend(String[] values)
    {
        setKey(Station.SUSPEND, values, true) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setSuspend(String[] values, String and_or_toNext)
    {
        setKey(Station.SUSPEND, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSuspend(String[] values, boolean and_or_toNext)
    {
        setKey(Station.SUSPEND, values, and_or_toNext) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setSuspend(String value, String compcode)
    {
        setKey(Station.SUSPEND, value, compcode, "", "", true) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。
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
    public void setSuspend(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.SUSPEND, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setSuspend(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.SUSPEND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setSuspendOrder(boolean ascorder)
    {
        setOrder(Station.SUSPEND, ascorder) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSuspendOrder(int prio, boolean ascorder)
    {
        setOrder(Station.SUSPEND, ascorder) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)のグループ順をセットします。
     */
    public void setSuspendGroup()
    {
        setGroup(Station.SUSPEND) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setSuspendGroup(int prio)
    {
        setGroup(Station.SUSPEND) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の情報取得を設定します。
     */
    public void setSuspendCollect()
    {
        setCollect(Station.SUSPEND) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setSuspendCollect(String sqlfunc)
    {
        setCollect(Station.SUSPEND, sqlfunc, null) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。
     * 
     * @param value 到着報告(<code>ARRIVAL</code>)<br>
     * 文字列の検索値をセット到着報告(<code>ARRIVAL</code>)します。
     */
    public void setArrival(String value)
    {
        setKey(Station.ARRIVAL, value) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setArrival(String[] values)
    {
        setKey(Station.ARRIVAL, values, true) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setArrival(String[] values, String and_or_toNext)
    {
        setKey(Station.ARRIVAL, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setArrival(String[] values, boolean and_or_toNext)
    {
        setKey(Station.ARRIVAL, values, and_or_toNext) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setArrival(String value, String compcode)
    {
        setKey(Station.ARRIVAL, value, compcode, "", "", true) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。
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
    public void setArrival(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.ARRIVAL, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setArrival(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.ARRIVAL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setArrivalOrder(boolean ascorder)
    {
        setOrder(Station.ARRIVAL, ascorder) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setArrivalOrder(int prio, boolean ascorder)
    {
        setOrder(Station.ARRIVAL, ascorder) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)のグループ順をセットします。
     */
    public void setArrivalGroup()
    {
        setGroup(Station.ARRIVAL) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setArrivalGroup(int prio)
    {
        setGroup(Station.ARRIVAL) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の情報取得を設定します。
     */
    public void setArrivalCollect()
    {
        setCollect(Station.ARRIVAL) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setArrivalCollect(String sqlfunc)
    {
        setCollect(Station.ARRIVAL, sqlfunc, null) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 荷姿チェック(<code>LOAD_SIZE</code>)<br>
     * 文字列の検索値をセット荷姿チェック(<code>LOAD_SIZE</code>)します。
     */
    public void setLoadSize(String value)
    {
        setKey(Station.LOAD_SIZE, value) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSize(String[] values)
    {
        setKey(Station.LOAD_SIZE, values, true) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSize(String[] values, String and_or_toNext)
    {
        setKey(Station.LOAD_SIZE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadSize(String[] values, boolean and_or_toNext)
    {
        setKey(Station.LOAD_SIZE, values, and_or_toNext) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLoadSize(String value, String compcode)
    {
        setKey(Station.LOAD_SIZE, value, compcode, "", "", true) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。
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
    public void setLoadSize(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.LOAD_SIZE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLoadSize(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.LOAD_SIZE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLoadSizeOrder(boolean ascorder)
    {
        setOrder(Station.LOAD_SIZE, ascorder) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)のソート順をセットします。
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
        setOrder(Station.LOAD_SIZE, ascorder) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)のグループ順をセットします。
     */
    public void setLoadSizeGroup()
    {
        setGroup(Station.LOAD_SIZE) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLoadSizeGroup(int prio)
    {
        setGroup(Station.LOAD_SIZE) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の情報取得を設定します。
     */
    public void setLoadSizeCollect()
    {
        setCollect(Station.LOAD_SIZE) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLoadSizeCollect(String sqlfunc)
    {
        setCollect(Station.LOAD_SIZE, sqlfunc, null) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。
     * 
     * @param value 払出し区分(<code>REMOVE</code>)<br>
     * 文字列の検索値をセット払出し区分(<code>REMOVE</code>)します。
     */
    public void setRemove(String value)
    {
        setKey(Station.REMOVE, value) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRemove(String[] values)
    {
        setKey(Station.REMOVE, values, true) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRemove(String[] values, String and_or_toNext)
    {
        setKey(Station.REMOVE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRemove(String[] values, boolean and_or_toNext)
    {
        setKey(Station.REMOVE, values, and_or_toNext) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRemove(String value, String compcode)
    {
        setKey(Station.REMOVE, value, compcode, "", "", true) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。
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
    public void setRemove(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.REMOVE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRemove(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.REMOVE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRemoveOrder(boolean ascorder)
    {
        setOrder(Station.REMOVE, ascorder) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRemoveOrder(int prio, boolean ascorder)
    {
        setOrder(Station.REMOVE, ascorder) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)のグループ順をセットします。
     */
    public void setRemoveGroup()
    {
        setGroup(Station.REMOVE) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRemoveGroup(int prio)
    {
        setGroup(Station.REMOVE) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の情報取得を設定します。
     */
    public void setRemoveCollect()
    {
        setCollect(Station.REMOVE) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRemoveCollect(String sqlfunc)
    {
        setCollect(Station.REMOVE, sqlfunc, null) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)<br>
     * 文字列の検索値をセット在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)します。
     */
    public void setInventoryCheckFlag(String value)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, value) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setInventoryCheckFlag(String[] values)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, values, true) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setInventoryCheckFlag(String[] values, String and_or_toNext)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setInventoryCheckFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, values, and_or_toNext) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setInventoryCheckFlag(String value, String compcode)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
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
    public void setInventoryCheckFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setInventoryCheckFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setInventoryCheckFlagOrder(boolean ascorder)
    {
        setOrder(Station.INVENTORY_CHECK_FLAG, ascorder) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setInventoryCheckFlagOrder(int prio, boolean ascorder)
    {
        setOrder(Station.INVENTORY_CHECK_FLAG, ascorder) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)のグループ順をセットします。
     */
    public void setInventoryCheckFlagGroup()
    {
        setGroup(Station.INVENTORY_CHECK_FLAG) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setInventoryCheckFlagGroup(int prio)
    {
        setGroup(Station.INVENTORY_CHECK_FLAG) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の情報取得を設定します。
     */
    public void setInventoryCheckFlagCollect()
    {
        setCollect(Station.INVENTORY_CHECK_FLAG) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setInventoryCheckFlagCollect(String sqlfunc)
    {
        setCollect(Station.INVENTORY_CHECK_FLAG, sqlfunc, null) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。
     * 
     * @param value 再入庫作業有無(<code>RESTORING_OPERATION</code>)<br>
     * 文字列の検索値をセット再入庫作業有無(<code>RESTORING_OPERATION</code>)します。
     */
    public void setRestoringOperation(String value)
    {
        setKey(Station.RESTORING_OPERATION, value) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringOperation(String[] values)
    {
        setKey(Station.RESTORING_OPERATION, values, true) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringOperation(String[] values, String and_or_toNext)
    {
        setKey(Station.RESTORING_OPERATION, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRestoringOperation(String[] values, boolean and_or_toNext)
    {
        setKey(Station.RESTORING_OPERATION, values, and_or_toNext) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRestoringOperation(String value, String compcode)
    {
        setKey(Station.RESTORING_OPERATION, value, compcode, "", "", true) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。
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
    public void setRestoringOperation(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.RESTORING_OPERATION, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRestoringOperation(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.RESTORING_OPERATION, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRestoringOperationOrder(boolean ascorder)
    {
        setOrder(Station.RESTORING_OPERATION, ascorder) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringOperationOrder(int prio, boolean ascorder)
    {
        setOrder(Station.RESTORING_OPERATION, ascorder) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)のグループ順をセットします。
     */
    public void setRestoringOperationGroup()
    {
        setGroup(Station.RESTORING_OPERATION) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringOperationGroup(int prio)
    {
        setGroup(Station.RESTORING_OPERATION) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の情報取得を設定します。
     */
    public void setRestoringOperationCollect()
    {
        setCollect(Station.RESTORING_OPERATION) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRestoringOperationCollect(String sqlfunc)
    {
        setCollect(Station.RESTORING_OPERATION, sqlfunc, null) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)<br>
     * 文字列の検索値をセット再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)します。
     */
    public void setRestoringInstruction(String value)
    {
        setKey(Station.RESTORING_INSTRUCTION, value) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringInstruction(String[] values)
    {
        setKey(Station.RESTORING_INSTRUCTION, values, true) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringInstruction(String[] values, String and_or_toNext)
    {
        setKey(Station.RESTORING_INSTRUCTION, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRestoringInstruction(String[] values, boolean and_or_toNext)
    {
        setKey(Station.RESTORING_INSTRUCTION, values, and_or_toNext) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRestoringInstruction(String value, String compcode)
    {
        setKey(Station.RESTORING_INSTRUCTION, value, compcode, "", "", true) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。
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
    public void setRestoringInstruction(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.RESTORING_INSTRUCTION, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRestoringInstruction(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.RESTORING_INSTRUCTION, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRestoringInstructionOrder(boolean ascorder)
    {
        setOrder(Station.RESTORING_INSTRUCTION, ascorder) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringInstructionOrder(int prio, boolean ascorder)
    {
        setOrder(Station.RESTORING_INSTRUCTION, ascorder) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)のグループ順をセットします。
     */
    public void setRestoringInstructionGroup()
    {
        setGroup(Station.RESTORING_INSTRUCTION) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRestoringInstructionGroup(int prio)
    {
        setGroup(Station.RESTORING_INSTRUCTION) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の情報取得を設定します。
     */
    public void setRestoringInstructionCollect()
    {
        setCollect(Station.RESTORING_INSTRUCTION) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRestoringInstructionCollect(String sqlfunc)
    {
        setCollect(Station.RESTORING_INSTRUCTION, sqlfunc, null) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。
     * 
     * @param value 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)<br>
     * 文字列の検索値をセット棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)します。
     */
    public void setLocationSearchFlag(String value)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, value) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchFlag(String[] values)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, values, true) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchFlag(String[] values, String and_or_toNext)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationSearchFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, values, and_or_toNext) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLocationSearchFlag(String value, String compcode)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。
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
    public void setLocationSearchFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLocationSearchFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLocationSearchFlagOrder(boolean ascorder)
    {
        setOrder(Station.LOCATION_SEARCH_FLAG, ascorder) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchFlagOrder(int prio, boolean ascorder)
    {
        setOrder(Station.LOCATION_SEARCH_FLAG, ascorder) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)のグループ順をセットします。
     */
    public void setLocationSearchFlagGroup()
    {
        setGroup(Station.LOCATION_SEARCH_FLAG) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLocationSearchFlagGroup(int prio)
    {
        setGroup(Station.LOCATION_SEARCH_FLAG) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の情報取得を設定します。
     */
    public void setLocationSearchFlagCollect()
    {
        setCollect(Station.LOCATION_SEARCH_FLAG) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLocationSearchFlagCollect(String sqlfunc)
    {
        setCollect(Station.LOCATION_SEARCH_FLAG, sqlfunc, null) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値をセット倉庫ステーションNo.(<code>WH_STATION_NO</code>)します。
     */
    public void setWhStationNo(String value)
    {
        setKey(Station.WH_STATION_NO, value) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNo(String[] values)
    {
        setKey(Station.WH_STATION_NO, values, true) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNo(String[] values, String and_or_toNext)
    {
        setKey(Station.WH_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(Station.WH_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
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
    public void setWhStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.WH_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setWhStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setWhStationNoOrder(boolean ascorder)
    {
        setOrder(Station.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(Station.WH_STATION_NO, ascorder) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のグループ順をセットします。
     */
    public void setWhStationNoGroup()
    {
        setGroup(Station.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setWhStationNoGroup(int prio)
    {
        setGroup(Station.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の情報取得を設定します。
     */
    public void setWhStationNoCollect()
    {
        setCollect(Station.WH_STATION_NO) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setWhStationNoCollect(String sqlfunc)
    {
        setCollect(Station.WH_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 親ステーションNo.(<code>PARENT_STATION_NO</code>)<br>
     * 文字列の検索値をセット親ステーションNo.(<code>PARENT_STATION_NO</code>)します。
     */
    public void setParentStationNo(String value)
    {
        setKey(Station.PARENT_STATION_NO, value) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNo(String[] values)
    {
        setKey(Station.PARENT_STATION_NO, values, true) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNo(String[] values, String and_or_toNext)
    {
        setKey(Station.PARENT_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setParentStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.PARENT_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setParentStationNo(String value, String compcode)
    {
        setKey(Station.PARENT_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。
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
    public void setParentStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.PARENT_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setParentStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.PARENT_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setParentStationNoOrder(boolean ascorder)
    {
        setOrder(Station.PARENT_STATION_NO, ascorder) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(Station.PARENT_STATION_NO, ascorder) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)のグループ順をセットします。
     */
    public void setParentStationNoGroup()
    {
        setGroup(Station.PARENT_STATION_NO) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setParentStationNoGroup(int prio)
    {
        setGroup(Station.PARENT_STATION_NO) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の情報取得を設定します。
     */
    public void setParentStationNoCollect()
    {
        setCollect(Station.PARENT_STATION_NO) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setParentStationNoCollect(String sqlfunc)
    {
        setCollect(Station.PARENT_STATION_NO, sqlfunc, null) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value アイルステーションNo.(<code>AISLE_STATION_NO</code>)<br>
     * 文字列の検索値をセットアイルステーションNo.(<code>AISLE_STATION_NO</code>)します。
     */
    public void setAisleStationNo(String value)
    {
        setKey(Station.AISLE_STATION_NO, value) ;
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
        setKey(Station.AISLE_STATION_NO, values, true) ;
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
        setKey(Station.AISLE_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setAisleStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.AISLE_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setAisleStationNo(String value, String compcode)
    {
        setKey(Station.AISLE_STATION_NO, value, compcode, "", "", true) ;
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
        setKey(Station.AISLE_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Station.AISLE_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setAisleStationNoOrder(boolean ascorder)
    {
        setOrder(Station.AISLE_STATION_NO, ascorder) ;
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
        setOrder(Station.AISLE_STATION_NO, ascorder) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)のグループ順をセットします。
     */
    public void setAisleStationNoGroup()
    {
        setGroup(Station.AISLE_STATION_NO) ;
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
        setGroup(Station.AISLE_STATION_NO) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の情報取得を設定します。
     */
    public void setAisleStationNoCollect()
    {
        setCollect(Station.AISLE_STATION_NO) ;
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
        setCollect(Station.AISLE_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)<br>
     * 文字列の検索値をセット次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)します。
     */
    public void setNextStationNo(String value)
    {
        setKey(Station.NEXT_STATION_NO, value) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setNextStationNo(String[] values)
    {
        setKey(Station.NEXT_STATION_NO, values, true) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setNextStationNo(String[] values, String and_or_toNext)
    {
        setKey(Station.NEXT_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setNextStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.NEXT_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setNextStationNo(String value, String compcode)
    {
        setKey(Station.NEXT_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。
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
    public void setNextStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.NEXT_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setNextStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.NEXT_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setNextStationNoOrder(boolean ascorder)
    {
        setOrder(Station.NEXT_STATION_NO, ascorder) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setNextStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(Station.NEXT_STATION_NO, ascorder) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)のグループ順をセットします。
     */
    public void setNextStationNoGroup()
    {
        setGroup(Station.NEXT_STATION_NO) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setNextStationNoGroup(int prio)
    {
        setGroup(Station.NEXT_STATION_NO) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の情報取得を設定します。
     */
    public void setNextStationNoCollect()
    {
        setCollect(Station.NEXT_STATION_NO) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setNextStationNoCollect(String sqlfunc)
    {
        setCollect(Station.NEXT_STATION_NO, sqlfunc, null) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)<br>
     * 文字列の検索値をセット最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)します。
     */
    public void setLastUsedStationNo(String value)
    {
        setKey(Station.LAST_USED_STATION_NO, value) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNo(String[] values)
    {
        setKey(Station.LAST_USED_STATION_NO, values, true) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNo(String[] values, String and_or_toNext)
    {
        setKey(Station.LAST_USED_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUsedStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.LAST_USED_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setLastUsedStationNo(String value, String compcode)
    {
        setKey(Station.LAST_USED_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
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
    public void setLastUsedStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.LAST_USED_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setLastUsedStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.LAST_USED_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setLastUsedStationNoOrder(boolean ascorder)
    {
        setOrder(Station.LAST_USED_STATION_NO, ascorder) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(Station.LAST_USED_STATION_NO, ascorder) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)のグループ順をセットします。
     */
    public void setLastUsedStationNoGroup()
    {
        setGroup(Station.LAST_USED_STATION_NO) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setLastUsedStationNoGroup(int prio)
    {
        setGroup(Station.LAST_USED_STATION_NO) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の情報取得を設定します。
     */
    public void setLastUsedStationNoCollect()
    {
        setCollect(Station.LAST_USED_STATION_NO) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setLastUsedStationNoCollect(String sqlfunc)
    {
        setCollect(Station.LAST_USED_STATION_NO, sqlfunc, null) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)<br>
     * 文字列の検索値をセットリジェクトステーションNo.(<code>REJECT_STATION_NO</code>)します。
     */
    public void setRejectStationNo(String value)
    {
        setKey(Station.REJECT_STATION_NO, value) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRejectStationNo(String[] values)
    {
        setKey(Station.REJECT_STATION_NO, values, true) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRejectStationNo(String[] values, String and_or_toNext)
    {
        setKey(Station.REJECT_STATION_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRejectStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.REJECT_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setRejectStationNo(String value, String compcode)
    {
        setKey(Station.REJECT_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。
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
    public void setRejectStationNo(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.REJECT_STATION_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setRejectStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.REJECT_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setRejectStationNoOrder(boolean ascorder)
    {
        setOrder(Station.REJECT_STATION_NO, ascorder) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRejectStationNoOrder(int prio, boolean ascorder)
    {
        setOrder(Station.REJECT_STATION_NO, ascorder) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)のグループ順をセットします。
     */
    public void setRejectStationNoGroup()
    {
        setGroup(Station.REJECT_STATION_NO) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setRejectStationNoGroup(int prio)
    {
        setGroup(Station.REJECT_STATION_NO) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の情報取得を設定します。
     */
    public void setRejectStationNoCollect()
    {
        setCollect(Station.REJECT_STATION_NO) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setRejectStationNoCollect(String sqlfunc)
    {
        setCollect(Station.REJECT_STATION_NO, sqlfunc, null) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。
     * 
     * @param value モード切替種別(<code>MODE_TYPE</code>)<br>
     * 文字列の検索値をセットモード切替種別(<code>MODE_TYPE</code>)します。
     */
    public void setModeType(String value)
    {
        setKey(Station.MODE_TYPE, value) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setModeType(String[] values)
    {
        setKey(Station.MODE_TYPE, values, true) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setModeType(String[] values, String and_or_toNext)
    {
        setKey(Station.MODE_TYPE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setModeType(String[] values, boolean and_or_toNext)
    {
        setKey(Station.MODE_TYPE, values, and_or_toNext) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setModeType(String value, String compcode)
    {
        setKey(Station.MODE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。
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
    public void setModeType(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.MODE_TYPE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setModeType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MODE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setModeTypeOrder(boolean ascorder)
    {
        setOrder(Station.MODE_TYPE, ascorder) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setModeTypeOrder(int prio, boolean ascorder)
    {
        setOrder(Station.MODE_TYPE, ascorder) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)のグループ順をセットします。
     */
    public void setModeTypeGroup()
    {
        setGroup(Station.MODE_TYPE) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setModeTypeGroup(int prio)
    {
        setGroup(Station.MODE_TYPE) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の情報取得を設定します。
     */
    public void setModeTypeCollect()
    {
        setCollect(Station.MODE_TYPE) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setModeTypeCollect(String sqlfunc)
    {
        setCollect(Station.MODE_TYPE, sqlfunc, null) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。
     * 
     * @param value 現在作業モード(<code>CURRENT_MODE</code>)<br>
     * 文字列の検索値をセット現在作業モード(<code>CURRENT_MODE</code>)します。
     */
    public void setCurrentMode(String value)
    {
        setKey(Station.CURRENT_MODE, value) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCurrentMode(String[] values)
    {
        setKey(Station.CURRENT_MODE, values, true) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setCurrentMode(String[] values, String and_or_toNext)
    {
        setKey(Station.CURRENT_MODE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCurrentMode(String[] values, boolean and_or_toNext)
    {
        setKey(Station.CURRENT_MODE, values, and_or_toNext) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setCurrentMode(String value, String compcode)
    {
        setKey(Station.CURRENT_MODE, value, compcode, "", "", true) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。
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
    public void setCurrentMode(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.CURRENT_MODE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setCurrentMode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.CURRENT_MODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setCurrentModeOrder(boolean ascorder)
    {
        setOrder(Station.CURRENT_MODE, ascorder) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCurrentModeOrder(int prio, boolean ascorder)
    {
        setOrder(Station.CURRENT_MODE, ascorder) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)のグループ順をセットします。
     */
    public void setCurrentModeGroup()
    {
        setGroup(Station.CURRENT_MODE) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setCurrentModeGroup(int prio)
    {
        setGroup(Station.CURRENT_MODE) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の情報取得を設定します。
     */
    public void setCurrentModeCollect()
    {
        setCollect(Station.CURRENT_MODE) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setCurrentModeCollect(String sqlfunc)
    {
        setCollect(Station.CURRENT_MODE, sqlfunc, null) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。
     * 
     * @param value モード切替要求区分(<code>MODE_REQUEST</code>)<br>
     * 文字列の検索値をセットモード切替要求区分(<code>MODE_REQUEST</code>)します。
     */
    public void setModeRequest(String value)
    {
        setKey(Station.MODE_REQUEST, value) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setModeRequest(String[] values)
    {
        setKey(Station.MODE_REQUEST, values, true) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setModeRequest(String[] values, String and_or_toNext)
    {
        setKey(Station.MODE_REQUEST, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setModeRequest(String[] values, boolean and_or_toNext)
    {
        setKey(Station.MODE_REQUEST, values, and_or_toNext) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setModeRequest(String value, String compcode)
    {
        setKey(Station.MODE_REQUEST, value, compcode, "", "", true) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。
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
    public void setModeRequest(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.MODE_REQUEST, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setModeRequest(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MODE_REQUEST, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setModeRequestOrder(boolean ascorder)
    {
        setOrder(Station.MODE_REQUEST, ascorder) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setModeRequestOrder(int prio, boolean ascorder)
    {
        setOrder(Station.MODE_REQUEST, ascorder) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)のグループ順をセットします。
     */
    public void setModeRequestGroup()
    {
        setGroup(Station.MODE_REQUEST) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setModeRequestGroup(int prio)
    {
        setGroup(Station.MODE_REQUEST) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の情報取得を設定します。
     */
    public void setModeRequestCollect()
    {
        setCollect(Station.MODE_REQUEST) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setModeRequestCollect(String sqlfunc)
    {
        setCollect(Station.MODE_REQUEST, sqlfunc, null) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value モード切替要求日時(<code>MODE_REQUEST_DATE</code>)<br>
     * 日付の検索値をセットモード切替要求日時(<code>MODE_REQUEST_DATE</code>)します。
     */
    public void setModeRequestDate(Date value)
    {
        setKey(Station.MODE_REQUEST_DATE, value) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setModeRequestDate(Date[] values)
    {
        setKey(Station.MODE_REQUEST_DATE, values, true) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setModeRequestDate(Date[] values, String and_or_toNext)
    {
        setKey(Station.MODE_REQUEST_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setModeRequestDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Station.MODE_REQUEST_DATE, values, and_or_toNext) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setModeRequestDate(Date value, String compcode)
    {
        setKey(Station.MODE_REQUEST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。
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
    public void setModeRequestDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(Station.MODE_REQUEST_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setModeRequestDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MODE_REQUEST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setModeRequestDateOrder(boolean ascorder)
    {
        setOrder(Station.MODE_REQUEST_DATE, ascorder) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     * @param prio ソート順 (このバージョンでは無視されます。)
     * 
     * @deprecated ソート順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setModeRequestDateOrder(int prio, boolean ascorder)
    {
        setOrder(Station.MODE_REQUEST_DATE, ascorder) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)のグループ順をセットします。
     */
    public void setModeRequestDateGroup()
    {
        setGroup(Station.MODE_REQUEST_DATE) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)のグループ順をセットします。
     * 
     * @param prio グループ順 (このバージョンでは無視されます。)
     * 
     * @deprecated グループ順はメソッド呼び出し順となり、
     * このメソッドのprioパラメータは無視されます。引数なしのメソッドを使用してください。
     */
    @Deprecated
    public void setModeRequestDateGroup(int prio)
    {
        setGroup(Station.MODE_REQUEST_DATE) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の情報取得を設定します。
     */
    public void setModeRequestDateCollect()
    {
        setCollect(Station.MODE_REQUEST_DATE) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の情報取得を設定します。
     * 
     * @param sqlfunc 集約取得時の条件を指定します。(MAX,MIN,MAX,AVERAGE等)<br>
     * 複数のSQL関数を使用する場合は、以下のように記述します。<br>
     * "SQRT(AVG({0}))"<br>
     * {0} は、カラム名に置き換えられます。
     */
    public void setModeRequestDateCollect(String sqlfunc)
    {
        setCollect(Station.MODE_REQUEST_DATE, sqlfunc, null) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value クラス名(<code>CLASS_NAME</code>)<br>
     * 文字列の検索値をセットクラス名(<code>CLASS_NAME</code>)します。
     */
    public void setClassName(String value)
    {
        setKey(Station.CLASS_NAME, value) ;
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
        setKey(Station.CLASS_NAME, values, true) ;
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
        setKey(Station.CLASS_NAME, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND,false = OR)
     */
    public void setClassName(String[] values, boolean and_or_toNext)
    {
        setKey(Station.CLASS_NAME, values, and_or_toNext) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「=」「!=」等　比較条件
     */
    public void setClassName(String value, String compcode)
    {
        setKey(Station.CLASS_NAME, value, compcode, "", "", true) ;
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
        setKey(Station.CLASS_NAME, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
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
        setKey(Station.CLASS_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)のソート順をセットします。
     * 
     * @param ascorder 昇順(true)又は降順(false)を指定します。
     */
    public void setClassNameOrder(boolean ascorder)
    {
        setOrder(Station.CLASS_NAME, ascorder) ;
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
        setOrder(Station.CLASS_NAME, ascorder) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)のグループ順をセットします。
     */
    public void setClassNameGroup()
    {
        setGroup(Station.CLASS_NAME) ;
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
        setGroup(Station.CLASS_NAME) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の情報取得を設定します。
     */
    public void setClassNameCollect()
    {
        setCollect(Station.CLASS_NAME) ;
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
        setCollect(Station.CLASS_NAME, sqlfunc, null) ;
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
        return "$Id: StationSearchKey.java 5295 2009-10-28 05:23:13Z ota $" ;
    }
}
