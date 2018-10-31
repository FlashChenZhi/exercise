// $Id: FixedLocateInfoOperator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.master.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;


/**
 * 商品固定棚管理を行うためのオペレータクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  073019
 * @author  Last commit: $Author: admin $
 */
public class FixedLocateInfoOperator
        extends AbstractOperator
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
     * データベースコネクションと呼び出し元クラスを指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public FixedLocateInfoOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 商品固定棚情報の作成処理を行います。<BR>
     * <BR>
     * 
     * @param inParam エリア入力パラメータ
     * @param pname 処理名
     * @param conn DBコネクション
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws ScheduleException システム定義不整合
     * @throws LockTimeOutException ロックが獲得できなかった場合にスローされます。
     * @throws DataExistsException 在庫登録済みの場合にスローされます
     */
    public static void createFixedLocateInfo(MasterInParameter inParam, String pname, Connection conn)
            throws ScheduleException,
                ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                OperatorException,
                NotFoundException,
                DataExistsException
    {

        FixedLocateInfoHandler fHandler = new FixedLocateInfoHandler(conn);

        // 商品固定棚情報を登録
        // 検索キー
        FixedLocateInfo insertFixedEntity = new FixedLocateInfo();
        // 荷主コード
        insertFixedEntity.setConsignorCode(inParam.getConsignorCode());
        // 商品コード
        insertFixedEntity.setItemCode(inParam.getItemCode());
        // エリアNo
        insertFixedEntity.setAreaNo(inParam.getAreaNo());
        // 棚No
        insertFixedEntity.setLocationNo(inParam.getLocation());
        // 補充点
        insertFixedEntity.setReplenishmentQty(Integer.valueOf(inParam.getReplenishmentQty()));

        // 登録処理名
        insertFixedEntity.setRegistPname(pname);
        // 最終更新処理名
        insertFixedEntity.setLastUpdatePname(pname);

        fHandler.create(insertFixedEntity);

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
        return "$Id: FixedLocateInfoOperator.java 87 2008-10-04 03:07:38Z admin $";
    }
}
