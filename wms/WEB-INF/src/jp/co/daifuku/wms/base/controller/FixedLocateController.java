// $Id: FixedLocateController.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateHistoryHandler;
import jp.co.daifuku.wms.base.entity.FixedLocateHistory;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;


/**
 * 商品固定棚マスタ情報コントローラクラスです。
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  073019
 * @author  Last commit: $Author: admin $
 */
public class FixedLocateController
        extends AbstractController
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
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public FixedLocateController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 商品固定棚マスタ更新履歴情報登録処理を行います。
     * 
     * @param locate          対象マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 商品固定棚マスタ更新履歴情報登録済み
     */
    public void insertHistory(FixedLocateInfo locate, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == locate)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        FixedLocateHistoryHandler histch = new FixedLocateHistoryHandler(getConnection());
        FixedLocateHistory hist = new FixedLocateHistory();

        // ユーザID
        hist.setUserId(ui.getUserId());
        // ユーザ名称
        hist.setUserName(ui.getUserName());
        // 端末No
        hist.setTerminalNo(ui.getTerminalNumber());
        // 端末名称
        hist.setTerminalName(ui.getTerminalName());
        // 端末IPアドレス
        hist.setIpAddress(ui.getTerminalAddress());
        // DS番号
        hist.setDsNo(ui.getDsNumber());
        // リソース番号
        hist.setPagenameResourcekey(ui.getPageNameResourceKey());

        // 更新区分
        hist.setUpdateKind(operationKind);
        // 荷主コード
        hist.setConsignorCode(locate.getConsignorCode());
        // 商品コード
        hist.setItemCode(locate.getItemCode());
        // エリアNo
        hist.setAreaNo(locate.getAreaNo());
        // 棚フォーマット
        AreaController areacon = new AreaController(getConnection(), getClass());
        hist.setLocationStyle(areacon.getLocationStyle(locate.getAreaNo()));

        if (SystemDefine.UPDATE_KIND_REGIST.equals(operationKind))
        {
            // 修正後棚No
            hist.setUpdateLocationNo(locate.getLocationNo());
            // 修正後補充点
            hist.setUpdateReplenishmentQty(locate.getReplenishmentQty());
        }
        else
        {
            // 棚No
            hist.setLocationNo(locate.getLocationNo());
            // 補充点
            hist.setReplenishmentQty(locate.getReplenishmentQty());
        }

        histch.create(hist);
    }


    /**
     * 商品固定棚マスタ更新履歴情報登録処理を行います。
     * 
     * @param oldlocate   修正前マスタ情報
     * @param newlocate   修正後マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 商品固定棚マスタ更新履歴情報登録済み
     */
    public void insertHistory(FixedLocateInfo oldlocate, FixedLocateInfo newlocate, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == oldlocate)
        {
            throw new ReadWriteException();
        }
        if (null == newlocate)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        FixedLocateHistoryHandler histch = new FixedLocateHistoryHandler(getConnection());
        FixedLocateHistory hist = new FixedLocateHistory();

        // ユーザID
        hist.setUserId(ui.getUserId());
        // ユーザ名称
        hist.setUserName(ui.getUserName());
        // 端末No
        hist.setTerminalNo(ui.getTerminalNumber());
        // 端末名称
        hist.setTerminalName(ui.getTerminalName());
        // 端末IPアドレス
        hist.setIpAddress(ui.getTerminalAddress());
        // DS番号
        hist.setDsNo(ui.getDsNumber());
        // リソース番号
        hist.setPagenameResourcekey(ui.getPageNameResourceKey());

        // 更新区分
        hist.setUpdateKind(operationKind);
        // 荷主コード
        hist.setConsignorCode(oldlocate.getConsignorCode());
        // 商品コード
        hist.setItemCode(oldlocate.getItemCode());
        // エリアNo
        hist.setAreaNo(oldlocate.getAreaNo());
        // 棚No
        hist.setLocationNo(oldlocate.getLocationNo());
        // 補充点
        hist.setReplenishmentQty(oldlocate.getReplenishmentQty());
        // 修正後棚No
        hist.setUpdateLocationNo(newlocate.getLocationNo());
        // 修正後補充点
        hist.setUpdateReplenishmentQty(newlocate.getReplenishmentQty());
        // 棚フォーマット
        AreaController areacon = new AreaController(getConnection(), getClass());
        hist.setLocationStyle(areacon.getLocationStyle(oldlocate.getAreaNo()));

        histch.create(hist);
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
        return "$Id: FixedLocateController.java 87 2008-10-04 03:07:38Z admin $";
    }
}
