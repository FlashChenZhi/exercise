// $Id: Id0022SCH.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.rft.AbstractIdSCH;
import jp.co.daifuku.wms.base.rft.RftOutParameter;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 荷主一覧問合せスケジュール処理<BR>
 *
 *
 * @version $Revision: 4181 $, $Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $
 * @author  Kojima
 * @author  Last commit: $Author: rnakai $
 */
public class Id0022SCH
        extends AbstractIdSCH
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


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     */
    public Id0022SCH()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 荷主一覧問合せを行います。<BR>
     * 
     * @param inquiryParams 問合せ条件
     * @return 取得データ
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws NotFoundException 該当データなし
     */
    public OutParameter[] inquirySCH(InParameter[] inquiryParams)
            throws ReadWriteException,
                NotFoundException
    {
        // 荷主情報ハンドラ
        ConsignorHandler conHndler = new ConsignorHandler(_wConn);
        // 荷主情報サーチキー
        ConsignorSearchKey sKey = new ConsignorSearchKey();
        
        // 取得項目をセット
        sKey.setConsignorCodeCollect();
        sKey.setConsignorNameCollect();

        // 検索結果を取得
        Consignor[] consignor = (Consignor[])conHndler.find(sKey);
        if (consignor.length == 0 || consignor == null)
        {
            throw new NotFoundException();
        }

        // 取得データをセット
        RftOutParameter[] outParam = new RftOutParameter[consignor.length];
        for (int i = 0; i < consignor.length; i++)
        {
            outParam[i] = new RftOutParameter();
            outParam[i].setConsignorCode(consignor[i].getConsignorCode());
            outParam[i].setConsignorName(consignor[i].getConsignorName());
        }

        return outParam;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


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
        return "$Id: Id0022SCH.java 4181 2009-04-21 00:14:17Z rnakai $";
    }
}
