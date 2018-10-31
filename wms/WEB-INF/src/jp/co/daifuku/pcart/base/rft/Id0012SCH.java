// $Id: Id0012SCH.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.rft.AbstractIdSCH;
import jp.co.daifuku.wms.base.rft.IdSchException;
import jp.co.daifuku.wms.base.rft.RftInParameter;
import jp.co.daifuku.wms.base.rft.RftOutParameter;


/**
 * 荷主問合せスケジュール処理<br>
 *
 * @version $Revision: 4181 $, $Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $
 * @author  kojima
 * @author  Last commit: $Author: rnakai $
 */


public class Id0012SCH
        extends AbstractIdSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    // マスタ管理の有無の保管
    private boolean _masterFlag;

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
    public Id0012SCH()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /** 荷主問合せスケジュール
     * 
     * @param inquilyParams     荷主問合せをもつ<CODE>InParameter</CODE>クラスを継承したクラス(配列)
     * @return                  荷主問合せ <CODE>OutParameter</CODE>クラスを継承したクラス(配列)
     * @throws CommonException  例外が発生した場合に通知されます。
     */
    public OutParameter[] inquirySCH(InParameter[] inquilyParams)
            throws CommonException
    {
        RftInParameter inquilyParam = (RftInParameter)inquilyParams[0];

        /* 禁止文字チェック */
        if (DisplayText.isPatternMatching(inquilyParam.getConsignorCode()))
        {
            throw new IdSchException(IdSchException.PATTERN_NG);
        }

        // マスター管理導入有無取得
        hasMasterPack();

        /* 荷主情報を検索し、荷主名称を取得 */
        // 荷主情報のHANDLER定義
        ConsignorHandler consignorHandler = new ConsignorHandler(_wConn);
        // SearchKey定義
        ConsignorSearchKey sKey = new ConsignorSearchKey();

        // 検索条件
        sKey.setConsignorCode(inquilyParam.getConsignorCode());

        // 取得項目
        sKey.setConsignorNameCollect();

        // 荷主情報の検索
        Consignor consignor = (Consignor)consignorHandler.findPrimary(sKey);

        if (null == consignor)
        {
            throw (new NotFoundException());
        }

        /* 開始データ(入庫用出力パラメータ)に値をセット */
        RftOutParameter returnParam = new RftOutParameter();
        // 荷主名称
        returnParam.setConsignorName(consignor.getConsignorName());

        /* 荷主データの配列を作成 */
        RftOutParameter[] outParameter = new RftOutParameter[1];
        outParameter[0] = returnParam;

        return outParameter;
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
    /**
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     * @return masterFlag マスタ管理の有無
     */
    public boolean hasMasterPack()
            throws ReadWriteException,
                ScheduleException
    {
        /* システム定義情報コントローラ */
        WarenaviSystemController warenaviSystem = new WarenaviSystemController(_wConn, getClass());

        // 在庫管理パッケージの導入チェック
        if (warenaviSystem.hasMasterPack())
        {
            // マスター管理がありならtrue
            _masterFlag = true;
        }
        else
        {
            // マスタ管理がなしならfalse
            _masterFlag = false;
        }

        return _masterFlag;
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
        return "$Id: Id0012SCH.java 4181 2009-04-21 00:14:17Z rnakai $";
    }
}
//end of class
