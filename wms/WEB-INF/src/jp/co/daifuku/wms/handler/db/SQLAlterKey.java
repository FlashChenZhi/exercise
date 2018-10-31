//$Id: SQLAlterKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.util.List;

import jp.co.daifuku.wms.handler.AlterKey;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 保管情報を変更する場合に指定するキー情報の為のインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public interface SQLAlterKey
        extends AlterKey
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

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
     * 数値カラムに対して、他のカラムの値を使用した内容をセットします。
     * 
     * @param target 更新対象カラム
     * @param source セットするカラム
     * @param addValue 加算する値. nullを指定するとtargetカラムにsourceカラムをそのまま
     * セットします。
     */
    public void setUpdateWithColumn(FieldName target, FieldName source, BigDecimal addValue);

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 更新値リストを返します。
     * @return 更新値リスト
     */
    public List getUpdateValueList();
}
