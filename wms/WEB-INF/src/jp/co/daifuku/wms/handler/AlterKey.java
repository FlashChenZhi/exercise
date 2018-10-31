//$Id: AlterKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;

/**
 * 保管情報を変更する場合に指定するキー情報の為のインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public interface AlterKey
        extends FindKey
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 更新値をセットします。<br>
     * valuesにセットされている項目をすべて更新する値として採用します。
     * 
     * @param values 更新値
     */
    public void setUpdateValues(Entity values);

    /**
     * 更新条件をクリアします。
     */
    public void clearUpdateValues();

    /**
     * 更新値の内容を確認します。<br>
     * このメソッドは通常のアプリケーションで使用する必要はありません。
     * ハンドラクラスがデータを書き込む前に内容チェックを行うために
     * 用意されています。
     * @return エラー内容
     * @see Entity
     */
    public ValidateError[] validate();

    /**
     * 指定されたフィールドの更新値をセットします。<br>
     * このメソッドでは内容チェックを行いません。通常は、setUpdateValues()を
     * 使用してください。
     * 
     * @param field 対象フィールド
     * @param value 更新値<br>
     * String, BigDecimal, java.util.Date, FieldName のいずれかを使用してください。
     */
    public void setAdhocUpdateValue(FieldName field, Object value);

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
}
