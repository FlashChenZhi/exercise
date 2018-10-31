// $Id: ReceivingHostSendController.java 5025 2009-09-18 01:00:06Z shibamoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingHostSend;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 入荷実績送信を操作するためのコントローラクラスです。
 *
 * @version $Revision: 5025 $, $Date: 2009-09-18 10:00:06 +0900 (金, 18 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: shibamoto $
 */
public class ReceivingHostSendController
        extends HostSendController
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
    public ReceivingHostSendController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入出庫作業情報から入出庫実績送信情報を作成します。<br>
     * <ul>
     * 以下のマスタ情報を検索します。
     * <li>荷主マスタ
     * <li>仕入先マスタ
     * <li>出荷先マスタ
     * <li>商品マスタ
     * <li>ログインユーザ
     * </ul>
     * 
     * @param jobNo 作業No. (入出庫作業情報の検索キー)
     * @param ui 設定ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    @SuppressWarnings("unused")
    public void insertByReceivingWorkInfo(String jobNo, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        ReceivingWorkInfoHandler wih = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoSearchKey key = new ReceivingWorkInfoSearchKey();

        // set collect
        FieldName[] collects = {
            ReceivingWorkInfo.WORK_DAY,
            ReceivingWorkInfo.JOB_NO,
            ReceivingWorkInfo.COLLECT_JOB_NO,
            ReceivingWorkInfo.SETTING_UNIT_KEY,
            ReceivingWorkInfo.JOB_TYPE,
            ReceivingWorkInfo.STATUS_FLAG,
            ReceivingWorkInfo.HARDWARE_TYPE,
            ReceivingWorkInfo.PLAN_UKEY,
            ReceivingWorkInfo.PLAN_DAY,

            ReceivingWorkInfo.CONSIGNOR_CODE,
            Consignor.CONSIGNOR_NAME,

            ReceivingWorkInfo.SUPPLIER_CODE,
            Supplier.SUPPLIER_NAME,

            ReceivingWorkInfo.RECEIVE_TICKET_NO,
            ReceivingWorkInfo.RECEIVE_LINE_NO,


            ReceivingWorkInfo.ITEM_CODE,
            Item.ITEM_NAME,
            Item.JAN,
            Item.ITF,
            Item.BUNDLE_ITF,
            Item.ENTERING_QTY,
            Item.BUNDLE_ENTERING_QTY,

            ReceivingWorkInfo.PLAN_LOT_NO,
            ReceivingWorkInfo.PLAN_QTY,
            ReceivingWorkInfo.RESULT_QTY,
            ReceivingWorkInfo.SHORTAGE_QTY,
            ReceivingWorkInfo.RESULT_LOT_NO,

            ReceivingWorkInfo.USER_ID,
            ReceivingWorkInfo.TERMINAL_NO,
            ReceivingWorkInfo.WORK_SECOND,

            ReceivingWorkInfo.TCDC_FLAG,
        };
        setCollectFields(key, collects);

        // collect field (not same field name with HostSend)
        key.setCollect(Com_loginuser.USERNAME, "", ReceivingHostSend.USER_NAME);

        // set key job number
        key.setJobNo(jobNo);

        // set join ----
        key.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);
        key.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(ReceivingWorkInfo.ITEM_CODE, Item.ITEM_CODE);

        key.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, Supplier.CONSIGNOR_CODE);
        key.setJoin(ReceivingWorkInfo.SUPPLIER_CODE, Supplier.SUPPLIER_CODE);

        // join with eManager tables
        key.setJoin(ReceivingWorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        // FIXME getting terminal info with RFT
        //  rft_no = TerminalNo.,"" = Terminal Name, ip_address = IP address

        // find ----
        Entity readEnt = wih.findPrimary(key);
        if (null == readEnt)
        {
            // not found.
            throw new ScheduleException();
        }
        // set values
        ReceivingHostSend hsEnt = new ReceivingHostSend();
        hsEnt.setValueMap(readEnt.getValueMap());

        // "REPORTED" if AS/RS inv. check
        hsEnt.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);

        String cname = getCallerName();
        hsEnt.setRegistPname(cname);
        hsEnt.setRegistDate(new SysDate());
        hsEnt.setLastUpdatePname(cname);

        // insert to ReceivingHostSend table
        ReceivingHostSendHandler hsh = new ReceivingHostSendHandler(getConnection());
        hsh.create(hsEnt);
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
    private void setCollectFields(ReceivingWorkInfoSearchKey key, FieldName[] collects)
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
        return "$Id: ReceivingHostSendController.java 5025 2009-09-18 01:00:06Z shibamoto $";
    }
}
