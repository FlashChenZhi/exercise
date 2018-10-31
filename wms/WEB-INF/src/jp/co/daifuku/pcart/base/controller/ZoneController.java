// $Id: ZoneController.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.MANAGEMENT_TYPE_SYSTEM;
import static jp.co.daifuku.wms.base.entity.SystemDefine.MANAGEMENT_TYPE_USER;

import java.sql.Connection;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AbstractController;
import jp.co.daifuku.wms.base.dbhandler.ZoneAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.ZoneSearchKey;
import jp.co.daifuku.wms.base.entity.Zone;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * ゾーンマスタ情報を操作するためのコントロールクラスです。
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class ZoneController
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)<br>
     * コンストラクタ内で、データベースのエリアマスタ情報を内部に読み込みます。<br>
     * 読み込み処理は、JVM起動後一度だけ行われるため、
     * 最新の情報を取得する場合は、read()を呼び出してください。
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public ZoneController(Connection conn, Class caller)
            throws ReadWriteException
    {
        super(conn, caller);
    }

    /**
     * 登録されているゾーンマスタ情報を更新します。<br>
     * まだ未登録の場合はゾーンマスタ情報を追加します。<br>
     * 更新対象のゾーンマスタ情報は、システム管理区分が通常のものだけになります。
     * 
     * @param src 更新するゾーンマスタ情報
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 対象ゾーンマスタ情報が複数存在するときスローされます。
     * @throws NotFoundException 対象ゾーンマスタ情報が他のアプリケーションから変更されたときスローされます。
     * @throws DataExistsException 対象ゾーンマスタ情報が他のアプリケーションから登録されたときスローされます。
     */
    public void autoCreate(Zone src, WmsUserInfo ui)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException,
                DataExistsException
    {
        // read master with lock
        Zone[] zones = lock(src.getAreaNo(), src.getWorkZoneNo(), src.getZoneNo());
        if (ArrayUtil.length(zones) > 1)
        {
            throw new NoPrimaryException();
        }

        if (ArrayUtil.isEmpty(zones))
        {
            // insert if no such Zone
            src.setManagementType(MANAGEMENT_TYPE_USER);
            insert(src, ui);
            return;
        }

        Zone zone = zones[0];

        String mtype = zone.getManagementType();
        if (MANAGEMENT_TYPE_SYSTEM.equals(mtype))
        {
            // return if management type is SYSTEM
            return;
        }

        // update if exists
        update(zone, src, ui);
    }

    /**
     * ゾーンマスタレコードを検索してロックします。
     * @param areaNo エリアNo
     * @param zoneNo ゾーンNo
     * @return 該当するゾーンマスタデータ
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Zone[] lock(String areaNo, String zoneNo)
            throws LockTimeOutException,
                ReadWriteException
    {
        ZoneHandler ch = new ZoneHandler(getConnection());
        ZoneSearchKey key = new ZoneSearchKey();
        key.setAreaNo(areaNo);
        key.setZoneNo(zoneNo);

        Zone[] ents = (Zone[])super.retryLock(key, ch);
        if (ents == null)
        {
            // no such records found.
            return new Zone[0];
        }

        return ents;
    }

    /**
     * ゾーンマスタレコードを検索してロックします。
     * @param areaNo エリアNo
     * @param workZoneNo 作業ゾーンNo
     * @param zoneNo ゾーンNo
     * @return 該当するゾーンマスタデータ
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Zone[] lock(String areaNo, String workZoneNo, String zoneNo)
            throws LockTimeOutException,
                ReadWriteException
    {
        ZoneHandler ch = new ZoneHandler(getConnection());
        ZoneSearchKey key = new ZoneSearchKey();
        key.setAreaNo(areaNo);
        key.setWorkZoneNo(workZoneNo);
        key.setZoneNo(zoneNo);

        Zone[] ents = (Zone[])super.retryLock(key, ch);
        if (ents == null)
        {
            // no such records found.
            return new Zone[0];
        }

        return ents;
    }

    /**
     * ゾーンマスタ情報を作成します。
     * 
     * @param src 未指定の項目はテーブルに保存されません。
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     */
    public void insert(Zone src, WmsUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        String pname = getCallerName();
        src.setRegistPname(pname);
        src.setRegistDate(new SysDate());
        src.setLastUpdatePname(pname);

        ZoneHandler ch = new ZoneHandler(getConnection());
        ch.create(src);

        // FIXME add log info create
    }

    /**
     * エリアマスタ情報を更新します。
     * 
     * @param oldItem 更新元のエリアマスタ情報
     * @param newItem 更新データ
     * @param ui ユーザ情報
     * @throws ReadWriteException  データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のゾーンエリアマスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws NoPrimaryException 対象ゾーンマスタ情報が複数存在するときスローされます。
     */
    public void update(Zone oldZone, Zone newZone, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException
    {
        String pname = getCallerName();
        newZone.setLastUpdatePname(pname);

        ZoneAlterKey akey = new ZoneAlterKey();
        akey.setUpdateValues(newZone);

        // keys to search
        FieldName[] keyFields = {
            Zone.AREA_NO,
            Zone.WORK_ZONE_NO,
            Zone.ZONE_NO
        };

        ZoneSearchKey key = new ZoneSearchKey();
        key = (ZoneSearchKey)createKey(oldZone, key, keyFields);
        akey.setKey(key);

        ZoneHandler ch = new ZoneHandler(getConnection());
        int numrec = ch.modify(akey);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create
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
        return "$Id: ZoneController.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
