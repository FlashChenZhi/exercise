// $Id: PCTUserResultOrderInfoSearchKey.java 3209 2009-03-02 06:34:19Z arai $
// Handler v3.8
package jp.co.daifuku.pcart.retrieval.dbhandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.entity.PCTPickingResult;
import jp.co.daifuku.wms.handler.db.DefaultSQLSearchKey;

/**
 * 実績集計照会用の検索キークラスです。
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  matsuse
 * @author  Last commit: $Author: arai $
 */
public class PCTUserResultOrderInfoSearchKey
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
    /**
     * 検索条件
     */
    private PCTRetrievalInParameter _inParam;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 対象のテーブル名とカラムのリストを準備して、インスタンスを
     * 生成します。
     */
    public PCTUserResultOrderInfoSearchKey()
    {
        super(PCTPickingResult.STORE_NAME);
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 検索条件を設定します。
     * @param inParam 検索条件
     */
    public void setInParameter(PCTRetrievalInParameter inParam)
    {
        _inParam = inParam;
    }

    /**
     * 検索条件を返します。
     * @return 検索条件
     */
    public PCTRetrievalInParameter getInParam()
    {
        return _inParam;
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
        return "$Id: PCTUserResultOrderInfoSearchKey.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
