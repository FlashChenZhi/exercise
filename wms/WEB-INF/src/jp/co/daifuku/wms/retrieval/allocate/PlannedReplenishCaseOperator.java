// $Id: PlannedReplenishCaseOperator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.retrieval.allocate;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.Item;


/**
 * 固定棚情報に対する補充引当を行うクラスです。<br>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: admin $
 */
public class PlannedReplenishCaseOperator
        extends PlannedReplenishOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * 商品ハンドラ
     */
    private ItemHandler _ith = null;
    /**
     * 商品検索キー
     */
    private ItemSearchKey _itkey = null;
    
    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param rate 補充率
     * @param pattern 引当パタンNo.
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public PlannedReplenishCaseOperator(Connection conn, int rate, String pattern, Class caller) throws CommonException
    {
        super(conn, rate, pattern, caller);
        
        _ith = new ItemHandler(conn);
        _itkey = new ItemSearchKey();
        
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 引当要求数を求めます。<BR>
     * 親クラスで求めた数量を入数にて丸めます。
     * 
     * @param toLocInfo 補充先
     * @param rate 補充率
     * @return 引当要求数
     * @throws ReadWriteException DBエラー時に通知
     * @throws NoPrimaryException DB検索時に１件以上データがあった場合に通知
     */
    protected int calcRequireQty(FixedLocateInfo toLocInfo) throws ReadWriteException, NoPrimaryException
    {
        // 補充点 - その棚にある在庫数
        int allocateQty = super.calcRequireQty(toLocInfo);

        _itkey.clear();
        _itkey.setConsignorCode(toLocInfo.getConsignorCode());
        _itkey.setItemCode(toLocInfo.getItemCode());
        _itkey.setEnteringQtyCollect();
        int entQty = ((Item)_ith.findPrimary(_itkey)).getEnteringQty();
        if (entQty != 0)
        {
            // 入数でまるめた数量を返す
            return (allocateQty / entQty) * entQty;
        }
        else
        {
            return allocateQty;
        }
        
    }
    
    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PlannedReplenishCaseOperator.java 87 2008-10-04 03:07:38Z admin $";
    }


}
