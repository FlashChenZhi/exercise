// $Id: GroupControllerAlterKey.java 5424 2009-11-06 10:33:04Z okayama $
// $LastChangedRevision: 5424 $
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
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.handler.db.DefaultSQLAlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * GROUPCONTROLLER用の更新キークラスです。
 *
 * @version $Revision: 5424 $, $Date: 2009-11-06 19:33:04 +0900 (金, 06 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class GroupControllerAlterKey
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
    public static final StoreMetaData $storeMetaData = GroupController.$storeMetaData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public GroupControllerAlterKey()
    {
        super(GroupController.STORE_NAME) ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value コントローラー番号(<code>CONTROLLER_NO</code>)<br>
     * 文字列の検索値をコントローラー番号(<code>CONTROLLER_NO</code>)にセットします。
     */
    public void setControllerNo(String value)
    {
        setKey(GroupController.CONTROLLER_NO, value) ;
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
        setKey(GroupController.CONTROLLER_NO, values, true) ;
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
        setKey(GroupController.CONTROLLER_NO, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setControllerNo(String[] values, boolean and_or_toNext)
    {
        setKey(GroupController.CONTROLLER_NO, values, and_or_toNext) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setControllerNo(String value, String compcode)
    {
        setKey(GroupController.CONTROLLER_NO, value, compcode, "", "", true) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
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
        setKey(GroupController.CONTROLLER_NO, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setControllerNo(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.CONTROLLER_NO, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>)の更新値をセットします。
     * @param value コントローラー番号(<code>CONTROLLER_NO</code>)更新値
     */
    public void updateControllerNo(String value)
    {
        setAdhocUpdateValue(GroupController.CONTROLLER_NO, value) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 状態(<code>STATUS_FLAG</code>)<br>
     * 文字列の検索値を状態(<code>STATUS_FLAG</code>)にセットします。
     */
    public void setStatusFlag(String value)
    {
        setKey(GroupController.STATUS_FLAG, value) ;
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
        setKey(GroupController.STATUS_FLAG, values, true) ;
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
        setKey(GroupController.STATUS_FLAG, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setStatusFlag(String[] values, boolean and_or_toNext)
    {
        setKey(GroupController.STATUS_FLAG, values, and_or_toNext) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setStatusFlag(String value, String compcode)
    {
        setKey(GroupController.STATUS_FLAG, value, compcode, "", "", true) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
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
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(GroupController.STATUS_FLAG, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setStatusFlag(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.STATUS_FLAG, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 状態(<code>STATUS_FLAG</code>)の更新値をセットします。
     * @param value 状態(<code>STATUS_FLAG</code>)更新値
     */
    public void updateStatusFlag(String value)
    {
        setAdhocUpdateValue(GroupController.STATUS_FLAG, value) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value IPアドレス(<code>IPADDRESS</code>)<br>
     * 文字列の検索値をIPアドレス(<code>IPADDRESS</code>)にセットします。
     */
    public void setIpaddress(String value)
    {
        setKey(GroupController.IPADDRESS, value) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 文字列の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddress(String[] values)
    {
        setKey(GroupController.IPADDRESS, values, true) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setIpaddress(String[] values, String and_or_toNext)
    {
        setKey(GroupController.IPADDRESS, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param values 文字列の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setIpaddress(String[] values, boolean and_or_toNext)
    {
        setKey(GroupController.IPADDRESS, values, and_or_toNext) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setIpaddress(String value, String compcode)
    {
        setKey(GroupController.IPADDRESS, value, compcode, "", "", true) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
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
    public void setIpaddress(String value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(GroupController.IPADDRESS, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>)の検索値をセットします。
     * 
     * @param value 文字列の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setIpaddress(String value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.IPADDRESS, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * IPアドレス(<code>IPADDRESS</code>)の更新値をセットします。
     * @param value IPアドレス(<code>IPADDRESS</code>)更新値
     */
    public void updateIpaddress(String value)
    {
        setAdhocUpdateValue(GroupController.IPADDRESS, value) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param value ポート番号(<code>PORT</code>)<br>
     * 数値の検索値をポート番号(<code>PORT</code>)にセットします。
     */
    public void setPort(int value)
    {
        setKey(GroupController.PORT, HandlerUtil.toObject(value)) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 数値の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPort(int[] values)
    {
        setKey(GroupController.PORT, ArrayUtil.toObjectArray(values), true) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setPort(int[] values, String and_or_toNext)
    {
        setKey(GroupController.PORT, ArrayUtil.toObjectArray(values), !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param values 数値の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setPort(int[] values, boolean and_or_toNext)
    {
        setKey(GroupController.PORT, ArrayUtil.toObjectArray(values), and_or_toNext) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setPort(int value, String compcode)
    {
        setKey(GroupController.PORT, HandlerUtil.toObject(value), compcode, "", "", true) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
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
    public void setPort(int value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(GroupController.PORT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * ポート番号(<code>PORT</code>)の検索値をセットします。
     * 
     * @param value 数値の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setPort(int value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.PORT, HandlerUtil.toObject(value), compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * ポート番号(<code>PORT</code>)の更新値をセットします。
     * @param value ポート番号(<code>PORT</code>)更新値
     */
    public void updatePort(int value)
    {
        setAdhocUpdateValue(GroupController.PORT, HandlerUtil.toObject(value)) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param value 作業終了応答受信日時(<code>RECV_DATE</code>)<br>
     * 日付の検索値を作業終了応答受信日時(<code>RECV_DATE</code>)にセットします。
     */
    public void setRecvDate(Date value)
    {
        setKey(GroupController.RECV_DATE, value) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。<br>
     * 検索条件として、この次に指定されるものとは"AND"で結ばれます。
     * 
     * @param values 日付の検索配列をセットします。
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRecvDate(Date[] values)
    {
        setKey(GroupController.RECV_DATE, values, true) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。
     * (ANDまたはOR, OR以外の文字列の場合はANDとみなします)
     * 
     * @deprecated and_or_toNextにbooleanを指定するメソッドを使用してください。
     */
    @Deprecated
    public void setRecvDate(Date[] values, String and_or_toNext)
    {
        setKey(GroupController.RECV_DATE, values, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param values 日付の検索配列をセットします。
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(ANDまたはOR)
     */
    public void setRecvDate(Date[] values, boolean and_or_toNext)
    {
        setKey(GroupController.RECV_DATE, values, and_or_toNext) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     */
    public void setRecvDate(Date value, String compcode)
    {
        setKey(GroupController.RECV_DATE, value, compcode, "", "", true) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
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
    public void setRecvDate(Date value, String compcode, String left_paren, String right_paren, String and_or_toNext)
    {
        setKey(GroupController.RECV_DATE, value, compcode, left_paren, right_paren, !"OR".equals(and_or_toNext)) ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の検索値をセットします。
     * 
     * @param value 日付の検索値をセットします。
     * @param compcode 「＞」「＜」「＝」等　比較条件
     * @param left_paren 左括弧
     * @param right_paren 右括弧
     * @param and_or_toNext この条件の次条件と接続する論理を指定します。(true = AND, false = OR)
     */
    public void setRecvDate(Date value, String compcode, String left_paren, String right_paren, boolean and_or_toNext)
    {
        setKey(GroupController.RECV_DATE, value, compcode, left_paren, right_paren, and_or_toNext) ;
    }


    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>)の更新値をセットします。
     * @param value 作業終了応答受信日時(<code>RECV_DATE</code>)更新値
     */
    public void updateRecvDate(Date value)
    {
        setAdhocUpdateValue(GroupController.RECV_DATE, value) ;
    }

    /**
     * ポート番号(<code>PORT</code>)に他のカラムを基本にした加減算値セットします。
     * @param source セットするカラム
     * @param addvalue 加減算する値。加減算なしの時は nullをセットします。
     */
    public void updatePortWithColumn(FieldName source, BigDecimal addvalue)
    {
        setUpdateWithColumn(GroupController.PORT, source, addvalue);
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
        return "$Id: GroupControllerAlterKey.java 5424 2009-11-06 10:33:04Z okayama $" ;
    }
}
