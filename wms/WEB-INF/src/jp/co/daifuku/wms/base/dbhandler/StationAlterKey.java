// $Id: StationAlterKey.java 5295 2009-10-28 05:23:13Z ota $
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
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * STATION用の更新キークラスです。
 *
 * @version $Revision: 5295 $, $Date: 2009-10-28 14:23:13 +0900 (水, 28 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class StationAlterKey
        extends DefaultSQLAlterKey
{
    //------------------------------------------------------------
    // class variables (Prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------


    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;
    /** Store meta data for this alter key */
    public static final StoreMetaData $storeMetaData = Station.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public StationAlterKey()
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
     * 文字列の検索値をステーションNo.(<code>STATION_NO</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.STATION_NO, values, and_or_toNext) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStationNo(String value, String compcode)
    {
        setKey(Station.STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ステーションNo.(<code>STATION_NO</code>)の更新値をセットします。
     * @param value ステーションNo.(<code>STATION_NO</code>)更新値
     */
    public void updateStationNo(String value)
    {
        setAdhocUpdateValue(Station.STATION_NO, value) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。
     * 
     * @param value 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)<br>
     * 数値の検索値を最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMaxPalletQty(int[] values, boolean and_or_toNext)
    {
        setKey(Station.MAX_PALLET_QTY, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMaxPalletQty(int value, String compcode)
    {
        setKey(Station.MAX_PALLET_QTY, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMaxPalletQty(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MAX_PALLET_QTY, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)の更新値をセットします。
     * @param value 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)更新値
     */
    public void updateMaxPalletQty(int value)
    {
        setAdhocUpdateValue(Station.MAX_PALLET_QTY, HandlerUtil.toObject(value)) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)<br>
     * 数値の検索値を最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setMaxInstruction(int[] values, boolean and_or_toNext)
    {
        setKey(Station.MAX_INSTRUCTION, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setMaxInstruction(int value, String compcode)
    {
        setKey(Station.MAX_INSTRUCTION, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setMaxInstruction(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MAX_INSTRUCTION, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)の更新値をセットします。
     * @param value 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)更新値
     */
    public void updateMaxInstruction(int value)
    {
        setAdhocUpdateValue(Station.MAX_INSTRUCTION, HandlerUtil.toObject(value)) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。
     * 
     * @param value 送信可能区分(<code>SENDABLE</code>)<br>
     * 文字列の検索値を送信可能区分(<code>SENDABLE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSendable(String[] values, boolean and_or_toNext)
    {
        setKey(Station.SENDABLE, values, and_or_toNext) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSendable(String value, String compcode)
    {
        setKey(Station.SENDABLE, value, compcode, "", "", true) ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSendable(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.SENDABLE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 送信可能区分(<code>SENDABLE</code>)の更新値をセットします。
     * @param value 送信可能区分(<code>SENDABLE</code>)更新値
     */
    public void updateSendable(String value)
    {
        setAdhocUpdateValue(Station.SENDABLE, value) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS</code>)<br>
     * 文字列の検索値を状態(<code>STATUS</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStatus(String[] values, boolean and_or_toNext)
    {
        setKey(Station.STATUS, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStatus(String value, String compcode)
    {
        setKey(Station.STATUS, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStatus(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.STATUS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 状態(<code>STATUS</code>)の更新値をセットします。
     * @param value 状態(<code>STATUS</code>)更新値
     */
    public void updateStatus(String value)
    {
        setAdhocUpdateValue(Station.STATUS, value) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value コントローラーNo.(<code>CONTROLLER_NO</code>)<br>
     * 文字列の検索値をコントローラーNo.(<code>CONTROLLER_NO</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setControllerNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.CONTROLLER_NO, values, and_or_toNext) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setControllerNo(String value, String compcode)
    {
        setKey(Station.CONTROLLER_NO, value, compcode, "", "", true) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.CONTROLLER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>)の更新値をセットします。
     * @param value コントローラーNo.(<code>CONTROLLER_NO</code>)更新値
     */
    public void updateControllerNo(String value)
    {
        setAdhocUpdateValue(Station.CONTROLLER_NO, value) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value ステーション種別(<code>STATION_TYPE</code>)<br>
     * 文字列の検索値をステーション種別(<code>STATION_TYPE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStationType(String[] values, boolean and_or_toNext)
    {
        setKey(Station.STATION_TYPE, values, and_or_toNext) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStationType(String value, String compcode)
    {
        setKey(Station.STATION_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStationType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.STATION_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ステーション種別(<code>STATION_TYPE</code>)の更新値をセットします。
     * @param value ステーション種別(<code>STATION_TYPE</code>)更新値
     */
    public void updateStationType(String value)
    {
        setAdhocUpdateValue(Station.STATION_TYPE, value) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。
     * 
     * @param value 設定種別(<code>SETTING_TYPE</code>)<br>
     * 文字列の検索値を設定種別(<code>SETTING_TYPE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSettingType(String[] values, boolean and_or_toNext)
    {
        setKey(Station.SETTING_TYPE, values, and_or_toNext) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSettingType(String value, String compcode)
    {
        setKey(Station.SETTING_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSettingType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.SETTING_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 設定種別(<code>SETTING_TYPE</code>)の更新値をセットします。
     * @param value 設定種別(<code>SETTING_TYPE</code>)更新値
     */
    public void updateSettingType(String value)
    {
        setAdhocUpdateValue(Station.SETTING_TYPE, value) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 作業場種別(<code>WORKPLACE_TYPE</code>)<br>
     * 文字列の検索値を作業場種別(<code>WORKPLACE_TYPE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWorkplaceType(String[] values, boolean and_or_toNext)
    {
        setKey(Station.WORKPLACE_TYPE, values, and_or_toNext) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWorkplaceType(String value, String compcode)
    {
        setKey(Station.WORKPLACE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWorkplaceType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.WORKPLACE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>)の更新値をセットします。
     * @param value 作業場種別(<code>WORKPLACE_TYPE</code>)更新値
     */
    public void updateWorkplaceType(String value)
    {
        setAdhocUpdateValue(Station.WORKPLACE_TYPE, value) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。
     * 
     * @param value 作業表示運用(<code>OPERATION_DISPLAY</code>)<br>
     * 文字列の検索値を作業表示運用(<code>OPERATION_DISPLAY</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setOperationDisplay(String[] values, boolean and_or_toNext)
    {
        setKey(Station.OPERATION_DISPLAY, values, and_or_toNext) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setOperationDisplay(String value, String compcode)
    {
        setKey(Station.OPERATION_DISPLAY, value, compcode, "", "", true) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setOperationDisplay(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.OPERATION_DISPLAY, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>)の更新値をセットします。
     * @param value 作業表示運用(<code>OPERATION_DISPLAY</code>)更新値
     */
    public void updateOperationDisplay(String value)
    {
        setAdhocUpdateValue(Station.OPERATION_DISPLAY, value) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。
     * 
     * @param value ステーション名称(<code>STATION_NAME</code>)<br>
     * 文字列の検索値をステーション名称(<code>STATION_NAME</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStationName(String[] values, boolean and_or_toNext)
    {
        setKey(Station.STATION_NAME, values, and_or_toNext) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStationName(String value, String compcode)
    {
        setKey(Station.STATION_NAME, value, compcode, "", "", true) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStationName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.STATION_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ステーション名称(<code>STATION_NAME</code>)の更新値をセットします。
     * @param value ステーション名称(<code>STATION_NAME</code>)更新値
     */
    public void updateStationName(String value)
    {
        setAdhocUpdateValue(Station.STATION_NAME, value) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。
     * 
     * @param value 中断中フラグ(<code>SUSPEND</code>)<br>
     * 文字列の検索値を中断中フラグ(<code>SUSPEND</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setSuspend(String[] values, boolean and_or_toNext)
    {
        setKey(Station.SUSPEND, values, and_or_toNext) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setSuspend(String value, String compcode)
    {
        setKey(Station.SUSPEND, value, compcode, "", "", true) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setSuspend(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.SUSPEND, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 中断中フラグ(<code>SUSPEND</code>)の更新値をセットします。
     * @param value 中断中フラグ(<code>SUSPEND</code>)更新値
     */
    public void updateSuspend(String value)
    {
        setAdhocUpdateValue(Station.SUSPEND, value) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。
     * 
     * @param value 到着報告(<code>ARRIVAL</code>)<br>
     * 文字列の検索値を到着報告(<code>ARRIVAL</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setArrival(String[] values, boolean and_or_toNext)
    {
        setKey(Station.ARRIVAL, values, and_or_toNext) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setArrival(String value, String compcode)
    {
        setKey(Station.ARRIVAL, value, compcode, "", "", true) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setArrival(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.ARRIVAL, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 到着報告(<code>ARRIVAL</code>)の更新値をセットします。
     * @param value 到着報告(<code>ARRIVAL</code>)更新値
     */
    public void updateArrival(String value)
    {
        setAdhocUpdateValue(Station.ARRIVAL, value) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 荷姿チェック(<code>LOAD_SIZE</code>)<br>
     * 文字列の検索値を荷姿チェック(<code>LOAD_SIZE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLoadSize(String[] values, boolean and_or_toNext)
    {
        setKey(Station.LOAD_SIZE, values, and_or_toNext) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLoadSize(String value, String compcode)
    {
        setKey(Station.LOAD_SIZE, value, compcode, "", "", true) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLoadSize(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.LOAD_SIZE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>)の更新値をセットします。
     * @param value 荷姿チェック(<code>LOAD_SIZE</code>)更新値
     */
    public void updateLoadSize(String value)
    {
        setAdhocUpdateValue(Station.LOAD_SIZE, value) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。
     * 
     * @param value 払出し区分(<code>REMOVE</code>)<br>
     * 文字列の検索値を払出し区分(<code>REMOVE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRemove(String[] values, boolean and_or_toNext)
    {
        setKey(Station.REMOVE, values, and_or_toNext) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRemove(String value, String compcode)
    {
        setKey(Station.REMOVE, value, compcode, "", "", true) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRemove(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.REMOVE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 払出し区分(<code>REMOVE</code>)の更新値をセットします。
     * @param value 払出し区分(<code>REMOVE</code>)更新値
     */
    public void updateRemove(String value)
    {
        setAdhocUpdateValue(Station.REMOVE, value) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)<br>
     * 文字列の検索値を在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setInventoryCheckFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, values, and_or_toNext) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setInventoryCheckFlag(String value, String compcode)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setInventoryCheckFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.INVENTORY_CHECK_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)の更新値をセットします。
     * @param value 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)更新値
     */
    public void updateInventoryCheckFlag(String value)
    {
        setAdhocUpdateValue(Station.INVENTORY_CHECK_FLAG, value) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。
     * 
     * @param value 再入庫作業有無(<code>RESTORING_OPERATION</code>)<br>
     * 文字列の検索値を再入庫作業有無(<code>RESTORING_OPERATION</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRestoringOperation(String[] values, boolean and_or_toNext)
    {
        setKey(Station.RESTORING_OPERATION, values, and_or_toNext) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRestoringOperation(String value, String compcode)
    {
        setKey(Station.RESTORING_OPERATION, value, compcode, "", "", true) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRestoringOperation(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.RESTORING_OPERATION, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>)の更新値をセットします。
     * @param value 再入庫作業有無(<code>RESTORING_OPERATION</code>)更新値
     */
    public void updateRestoringOperation(String value)
    {
        setAdhocUpdateValue(Station.RESTORING_OPERATION, value) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)<br>
     * 文字列の検索値を再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRestoringInstruction(String[] values, boolean and_or_toNext)
    {
        setKey(Station.RESTORING_INSTRUCTION, values, and_or_toNext) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRestoringInstruction(String value, String compcode)
    {
        setKey(Station.RESTORING_INSTRUCTION, value, compcode, "", "", true) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRestoringInstruction(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.RESTORING_INSTRUCTION, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)の更新値をセットします。
     * @param value 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)更新値
     */
    public void updateRestoringInstruction(String value)
    {
        setAdhocUpdateValue(Station.RESTORING_INSTRUCTION, value) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。
     * 
     * @param value 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)<br>
     * 文字列の検索値を棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLocationSearchFlag(String[] values, boolean and_or_toNext)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, values, and_or_toNext) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLocationSearchFlag(String value, String compcode)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLocationSearchFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.LOCATION_SEARCH_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)の更新値をセットします。
     * @param value 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)更新値
     */
    public void updateLocationSearchFlag(String value)
    {
        setAdhocUpdateValue(Station.LOCATION_SEARCH_FLAG, value) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)<br>
     * 文字列の検索値を倉庫ステーションNo.(<code>WH_STATION_NO</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setWhStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.WH_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setWhStationNo(String value, String compcode)
    {
        setKey(Station.WH_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setWhStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.WH_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>)の更新値をセットします。
     * @param value 倉庫ステーションNo.(<code>WH_STATION_NO</code>)更新値
     */
    public void updateWhStationNo(String value)
    {
        setAdhocUpdateValue(Station.WH_STATION_NO, value) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 親ステーションNo.(<code>PARENT_STATION_NO</code>)<br>
     * 文字列の検索値を親ステーションNo.(<code>PARENT_STATION_NO</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setParentStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.PARENT_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setParentStationNo(String value, String compcode)
    {
        setKey(Station.PARENT_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setParentStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.PARENT_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>)の更新値をセットします。
     * @param value 親ステーションNo.(<code>PARENT_STATION_NO</code>)更新値
     */
    public void updateParentStationNo(String value)
    {
        setAdhocUpdateValue(Station.PARENT_STATION_NO, value) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value アイルステーションNo.(<code>AISLE_STATION_NO</code>)<br>
     * 文字列の検索値をアイルステーションNo.(<code>AISLE_STATION_NO</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setAisleStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.AISLE_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setAisleStationNo(String value, String compcode)
    {
        setKey(Station.AISLE_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setAisleStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.AISLE_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>)の更新値をセットします。
     * @param value アイルステーションNo.(<code>AISLE_STATION_NO</code>)更新値
     */
    public void updateAisleStationNo(String value)
    {
        setAdhocUpdateValue(Station.AISLE_STATION_NO, value) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)<br>
     * 文字列の検索値を次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setNextStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.NEXT_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setNextStationNo(String value, String compcode)
    {
        setKey(Station.NEXT_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setNextStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.NEXT_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)の更新値をセットします。
     * @param value 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)更新値
     */
    public void updateNextStationNo(String value)
    {
        setAdhocUpdateValue(Station.NEXT_STATION_NO, value) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)<br>
     * 文字列の検索値を最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setLastUsedStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.LAST_USED_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setLastUsedStationNo(String value, String compcode)
    {
        setKey(Station.LAST_USED_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setLastUsedStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.LAST_USED_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)の更新値をセットします。
     * @param value 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)更新値
     */
    public void updateLastUsedStationNo(String value)
    {
        setAdhocUpdateValue(Station.LAST_USED_STATION_NO, value) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)<br>
     * 文字列の検索値をリジェクトステーションNo.(<code>REJECT_STATION_NO</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRejectStationNo(String[] values, boolean and_or_toNext)
    {
        setKey(Station.REJECT_STATION_NO, values, and_or_toNext) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRejectStationNo(String value, String compcode)
    {
        setKey(Station.REJECT_STATION_NO, value, compcode, "", "", true) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRejectStationNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.REJECT_STATION_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)の更新値をセットします。
     * @param value リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)更新値
     */
    public void updateRejectStationNo(String value)
    {
        setAdhocUpdateValue(Station.REJECT_STATION_NO, value) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。
     * 
     * @param value モード切替種別(<code>MODE_TYPE</code>)<br>
     * 文字列の検索値をモード切替種別(<code>MODE_TYPE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setModeType(String[] values, boolean and_or_toNext)
    {
        setKey(Station.MODE_TYPE, values, and_or_toNext) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setModeType(String value, String compcode)
    {
        setKey(Station.MODE_TYPE, value, compcode, "", "", true) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setModeType(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MODE_TYPE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * モード切替種別(<code>MODE_TYPE</code>)の更新値をセットします。
     * @param value モード切替種別(<code>MODE_TYPE</code>)更新値
     */
    public void updateModeType(String value)
    {
        setAdhocUpdateValue(Station.MODE_TYPE, value) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。
     * 
     * @param value 現在作業モード(<code>CURRENT_MODE</code>)<br>
     * 文字列の検索値を現在作業モード(<code>CURRENT_MODE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setCurrentMode(String[] values, boolean and_or_toNext)
    {
        setKey(Station.CURRENT_MODE, values, and_or_toNext) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setCurrentMode(String value, String compcode)
    {
        setKey(Station.CURRENT_MODE, value, compcode, "", "", true) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setCurrentMode(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.CURRENT_MODE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 現在作業モード(<code>CURRENT_MODE</code>)の更新値をセットします。
     * @param value 現在作業モード(<code>CURRENT_MODE</code>)更新値
     */
    public void updateCurrentMode(String value)
    {
        setAdhocUpdateValue(Station.CURRENT_MODE, value) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。
     * 
     * @param value モード切替要求区分(<code>MODE_REQUEST</code>)<br>
     * 文字列の検索値をモード切替要求区分(<code>MODE_REQUEST</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setModeRequest(String[] values, boolean and_or_toNext)
    {
        setKey(Station.MODE_REQUEST, values, and_or_toNext) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setModeRequest(String value, String compcode)
    {
        setKey(Station.MODE_REQUEST, value, compcode, "", "", true) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setModeRequest(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MODE_REQUEST, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>)の更新値をセットします。
     * @param value モード切替要求区分(<code>MODE_REQUEST</code>)更新値
     */
    public void updateModeRequest(String value)
    {
        setAdhocUpdateValue(Station.MODE_REQUEST, value) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value モード切替要求日時(<code>MODE_REQUEST_DATE</code>)<br>
     * 日付の検索値をモード切替要求日時(<code>MODE_REQUEST_DATE</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setModeRequestDate(Date[] values, boolean and_or_toNext)
    {
        setKey(Station.MODE_REQUEST_DATE, values, and_or_toNext) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setModeRequestDate(Date value, String compcode)
    {
        setKey(Station.MODE_REQUEST_DATE, value, compcode, "", "", true) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setModeRequestDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.MODE_REQUEST_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>)の更新値をセットします。
     * @param value モード切替要求日時(<code>MODE_REQUEST_DATE</code>)更新値
     */
    public void updateModeRequestDate(Date value)
    {
        setAdhocUpdateValue(Station.MODE_REQUEST_DATE, value) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value クラス名(<code>CLASS_NAME</code>)<br>
     * 文字列の検索値をクラス名(<code>CLASS_NAME</code>)にセットします。
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
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setClassName(String[] values, boolean and_or_toNext)
    {
        setKey(Station.CLASS_NAME, values, and_or_toNext) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setClassName(String value, String compcode)
    {
        setKey(Station.CLASS_NAME, value, compcode, "", "", true) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
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
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setClassName(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(Station.CLASS_NAME, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * クラス名(<code>CLASS_NAME</code>)の更新値をセットします。
     * @param value クラス名(<code>CLASS_NAME</code>)更新値
     */
    public void updateClassName(String value)
    {
        setAdhocUpdateValue(Station.CLASS_NAME, value) ;
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateMaxPalletQtyWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Station.MAX_PALLET_QTY, source, addvalue);
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updateMaxInstructionWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(Station.MAX_INSTRUCTION, source, addvalue);
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * ストアメタデータを返します。
     * @return ストアメタデータ
     */
    @Override
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData;
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: StationAlterKey.java 5295 2009-10-28 05:23:13Z ota $" ;
    }
}
