// $Id: MoveResultController.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_DELETE;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_MOVE_STORAGE_CANCEL;

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveResultHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.MoveResult;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SQLSearchKey;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 移動実績情報を操作するためのコントローラクラスです。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  ss
 * @author  Last commit: $Author: kitamaki $
 */

public class MoveResultController
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
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public MoveResultController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 移動実績情報登録処理を行います。<br>
     * パラメータの作業No.から移動作業情報を検索して移動実績情報を作成します。
     *
     * @param jobNo 移動作業No.
     * @throws NotFoundException 対象作業データまたはマスタなし
     * @throws NoPrimaryException 対象作業データが複数
     * @throws ReadWriteException データベースエラー
     * @throws DataExistsException 移動実績情報登録済み
     */
    public void insert(String jobNo)
            throws NotFoundException,
                ReadWriteException,
                NoPrimaryException,
                DataExistsException
    {
        MoveWorkInfoHandler mwh = new MoveWorkInfoHandler(getConnection());
        MoveWorkInfoSearchKey key = new MoveWorkInfoSearchKey();

        // set collect
        FieldName[] collects = {
            MoveWorkInfo.STORAGE_WORK_DAY,
            MoveWorkInfo.JOB_NO,
            MoveWorkInfo.SETTING_UNIT_KEY,
            MoveWorkInfo.JOB_TYPE,
            MoveWorkInfo.STATUS_FLAG,
            MoveWorkInfo.HARDWARE_TYPE,
            MoveWorkInfo.STOCK_ID,
            MoveWorkInfo.WORK_CONN_KEY,
            MoveWorkInfo.RETRIEVAL_AREA_NO,
            MoveWorkInfo.RETRIEVAL_LOCATION_NO,

            MoveWorkInfo.CONSIGNOR_CODE,
            Consignor.CONSIGNOR_NAME,

            MoveWorkInfo.ITEM_CODE,
            Item.ITEM_NAME,
            Item.JAN,
            Item.ITF,
            Item.BUNDLE_ITF,
            Item.ENTERING_QTY,
            Item.BUNDLE_ENTERING_QTY,

            MoveWorkInfo.LOT_NO,
            MoveWorkInfo.STORAGE_DAY,
            MoveWorkInfo.STORAGE_DATE,
            MoveWorkInfo.RETRIEVAL_DAY,
            MoveWorkInfo.RETRIEVAL_USER_ID,

            MoveWorkInfo.RETRIEVAL_TERMINAL_NO,
            MoveWorkInfo.RETRIEVAL_WORK_DATE,
            //DFKLOOK:Start 3.5
            MoveWorkInfo.RETRIEVAL_WORK_SECOND,
            //DFKLOOK:End 3.5

            MoveWorkInfo.PLAN_QTY,
            MoveWorkInfo.RETRIEVAL_RESULT_QTY,

            MoveWorkInfo.STORAGE_RESULT_QTY,
            MoveWorkInfo.STORAGE_AREA_NO,
            MoveWorkInfo.STORAGE_LOCATION_NO,
            MoveWorkInfo.STORAGE_USER_ID,
            MoveWorkInfo.STORAGE_TERMINAL_NO,
            MoveWorkInfo.STORAGE_WORK_DATE,

            MoveWorkInfo.STORAGE_WORK_SECOND,
        };
        setCollectFields(key, collects);

        // collect field (not same field name with HostSend)
        key.setCollect(Com_loginuser.USERNAME, "", MoveResult.RETRIEVAL_USER_NAME);

        // set key job number
        key.setJobNo(jobNo);

        // set join ----
        key.setJoin(MoveWorkInfo.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);
        key.setJoin(MoveWorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(MoveWorkInfo.ITEM_CODE, Item.ITEM_CODE);

        // userid JOIN
        key.setJoin(MoveWorkInfo.RETRIEVAL_USER_ID, "", Com_loginuser.USERID, "(+)");

        // FIXME getting terminal info with RFT
        //  rft_no = TerminalNo.,"" = Terninal Name, ip_address = IP address


        // find ----
        Entity readEnt = mwh.findPrimary(key);
        if (null == readEnt)
        {
            // not found.
            throw new NotFoundException();
        }


        String status = (String)readEnt.getValue(MoveWorkInfo.STATUS_FLAG);

        // SystemDefine.STATUS_FLAG_MOVE_STORAGE_CANCEL
        // SystemDefine.STATUS_FLAG_DELETE
        boolean reported = (STATUS_FLAG_MOVE_STORAGE_CANCEL.equals(status) || STATUS_FLAG_DELETE.equals(status));
        String reportFlag = (reported) ? SystemDefine.REPORT_FLAG_REPORT
                                      : SystemDefine.REPORT_FLAG_NOT_REPORT;

        // build move result entity
        MoveResult resent = new MoveResult();
        // copy from read
        resent.setValueMap(readEnt.getValueMap());
        // set others
        resent.setReportFlag(reportFlag);

        resent.setRegistPname(getCallerName());
        resent.setRegistDate(new SysDate());
        resent.setLastUpdatePname(getCallerName());

        // ログインユーザ設定情報の検索を行う
        Com_loginuserSearchKey uidKey = new Com_loginuserSearchKey();
        uidKey.setUsernameCollect();
        uidKey.setUserid(resent.getStorageUserId());
        Com_loginuser user = (Com_loginuser)(new Com_loginuserHandler(getConnection())).findPrimary(uidKey);
        if (null != user)
        {
            String userName = user.getUsername();
            resent.setStorageUserName(userName);
        }

        new MoveResultHandler(getConnection()).create(resent);
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

    /**
     * 取得フィールドを検索キーにセットします。
     *
     * @param key セット先の検索キー
     * @param collects 取得フィールド一覧
     */
    private void setCollectFields(SQLSearchKey key, FieldName[] collects)
    {
        for (FieldName fld : collects)
        {
            key.setCollect(fld);
        }
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
        return "$Id: MoveResultController.java 7996 2011-07-06 00:52:24Z kitamaki $";
    }
}
