// $Id: StationController.java 6954 2010-01-29 08:54:15Z shibamoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.ASWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.dbhandler.DoubleDeepShelfHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationTypeHandler;
import jp.co.daifuku.wms.base.dbhandler.StationTypeSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.StationType;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * ステーション情報を操作するためのコントローラクラスです。
 *
 *
 * @version $Revision: 6954 $, $Date: 2010-01-29 17:54:15 +0900 (金, 29 1 2010) $
 * @author  ss
 * @author  Last commit: $Author: shibamoto $
 */


public class StationController
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
    public StationController(Connection conn, Class caller)
    {
        super(conn, caller);
    }


    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * アイルステーションNoは"RM-" + アイル号機No.を、
     * 倉庫ステーションNo、棚Noは倉庫名称を、
     * ステーションNo、作業場Noはステーション名称を返します。
     * 
     * @param stationno アイルステーション、倉庫ステーション、ステーション、作業場、棚No.
     * @return 倉庫名称、又はステーション名称
     * @throws ReadWriteException データベースエラー
     */
    public String getAsStationName(String stationno)
            throws ReadWriteException
    {
        try
        {
            String stno = stationno.trim();

            // ステーションタイプ表にステーションNoが存在するかを確認します。
            StationTypeHandler typHandler = new StationTypeHandler(getConnection());
            StationTypeSearchKey typKey = new StationTypeSearchKey();
            typKey.setStationNo(stno.trim());
            StationType stype = (StationType)typHandler.findPrimary(typKey);

            if (stype != null)
            {
                // ステーションタイプ表のハンドラクラスを取得します。
                String strType = stype.getClassName();

                if (WareHouseHandler.class.getName().equals(strType))
                {
                    // 全エリアの場合
                    if (WmsParam.ALL_AREA_NO.equals(stno))
                    {
                        return DisplayText.getText("CMB-W0023");
                    }
                    WareHouseHandler whHandler = new WareHouseHandler(getConnection());
                    WareHouseSearchKey whKey = new WareHouseSearchKey();
                    whKey.setWarehouseNoCollect();
                    whKey.setWarehouseNameCollect();
                    whKey.setStationNo(stno);
                    WareHouse wh = (WareHouse)whHandler.findPrimary(whKey);

                    return wh.getWarehouseNo() + ":" + wh.getWarehouseName();
                }
                else if (AisleHandler.class.getName().equals(strType))
                {
                    AisleHandler aisleHandler = new AisleHandler(getConnection());
                    AisleSearchKey aisleKey = new AisleSearchKey();
                    aisleKey.setAisleNoCollect();
                    aisleKey.setStationNo(stno);
                    Aisle aisle = (Aisle)aisleHandler.findPrimary(aisleKey);

                    return stno + ":" + DisplayText.getText("CMB-W0026") + aisle.getAisleNo();
                }
                else if (StationHandler.class.getName().equals(strType)
                        || ASWorkPlaceHandler.class.getName().equals(strType))
                {
                    // 全ステーションの場合
                    if (WmsParam.ALL_STATION.equals(stno))
                    {
                        return DisplayText.getText("CMB-W0028");
                    }
                    StationHandler stHandler = new StationHandler(getConnection());
                    StationSearchKey stKey = new StationSearchKey();
                    stKey.setStationNameCollect();
                    stKey.setStationNo(stno);
                    Station st = (Station)stHandler.findPrimary(stKey);

                    return stno + ":" + st.getStationName();
                }
                else if (ShelfHandler.class.getName().equals(strType))
                {
                    ShelfHandler shfHandler = new ShelfHandler(getConnection());
                    ShelfSearchKey shfKey = new ShelfSearchKey();
                    shfKey.setCollect(Shelf.PARENT_STATION_NO);
                    shfKey.setCollect(Aisle.AISLE_NO);
                    shfKey.setStationNo(stno);
                    shfKey.setJoin(Shelf.PARENT_STATION_NO, Aisle.STATION_NO);
                    Shelf shlf = (Shelf)shfHandler.findPrimary(shfKey);

                    return shlf.getValue(Shelf.PARENT_STATION_NO) + ":" + DisplayText.getText("CMB-W0026")
                            + shlf.getValue(Aisle.AISLE_NO);
                }
                else if (DoubleDeepShelfHandler.class.getName().equals(strType))
                {
                    DoubleDeepShelfHandler shfHandler = new DoubleDeepShelfHandler(getConnection());
                    ShelfSearchKey shfKey = new ShelfSearchKey();
                    shfKey.setCollect(Shelf.PARENT_STATION_NO);
                    shfKey.setCollect(Aisle.AISLE_NO);
                    shfKey.setStationNo(stno);
                    shfKey.setJoin(Shelf.PARENT_STATION_NO, Aisle.STATION_NO);
                    Shelf shlf = (Shelf)shfHandler.findPrimary(shfKey);

                    return shlf.getValue(Shelf.PARENT_STATION_NO) + ":" + DisplayText.getText("CMB-W0026")
                            + shlf.getValue(Aisle.AISLE_NO);
                }
            }

            // ステーションタイプ表にステーションNoが存在しないので、空文字を返します。
            return "";
        }
        catch (NoPrimaryException e)
        {
            return "";
        }
    }

    /**
     * ステーションNoを返します。<BR>
     * 棚No.が指定された場合はアイルステーションNoを返します。
     * @param  stationno ステーション
     * @return ステーションNo
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public String getDispStationNo(String stationno)
            throws ReadWriteException
    {
        try
        {
            int waresz = WmsParam.WAREHOUSE_LENGTH;
            int banksz = WmsParam.ASRS_BANK_LENGTH;
            int baysz = WmsParam.ASRS_BAY_LENGTH;
            int levelsz = WmsParam.ASRS_LEVEL_LENGTH;
            int sublocsz = WmsParam.ASRS_SUBLOC_LENGTH;
            int stlen = waresz + banksz + baysz + levelsz + sublocsz;

            String stno = stationno.trim();
            if (stno.length() >= stlen)
            {
                // ステーションタイプ表にステーションNoが存在するかを確認します。
                ShelfHandler slfHandler = new ShelfHandler(getConnection());
                ShelfSearchKey slfKey = new ShelfSearchKey();
                slfKey.setStationNo(stno);
                Shelf shlf = (Shelf)slfHandler.findPrimary(slfKey);
                if (shlf != null)
                {
                    return shlf.getParentStationNo();
                }
            }
            return stno;
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * ステーションが属する倉庫を参照し、このステーションから再入庫する場合、空棚検索が
     * 必要なステーションかどうかをチェックします。
     * <ol>
     * <li>荷姿検知器がある。
     * <li>アイル独立のステーションで、そのアイルはダブルディープである。
     * <li>アイル結合のステーションで、その倉庫にダブルディープがある。
     * </ol>
     * @param stationNo ステーションNo
     * @return 空棚検索が必要な場合はtrue、そうでない場合はfalseを返します。
     * @throws ReadWriteException
     *          データベースに対する処理で発生した場合に通知します。
     */
    public boolean isReStoringEmptyLocationSearch(String stationNo)
            throws ReadWriteException
    {
        try
        {
            StationSearchKey stationKey = new StationSearchKey();
            StationHandler stationHandler = new StationHandler(getConnection());

            // ステーションを取得
            stationKey.setCollect(new FieldName(Station.STORE_NAME, FieldName.ALL_FIELDS));
            stationKey.setStationNo(stationNo);
            Station st = (Station)stationHandler.findPrimary(stationKey);
            if (st != null)
            {
                // 荷姿検知器の有無を確認する。
                if (Station.LOAD_SIZE_ON.equals(st.getLoadSize()))
                {
                    // 荷姿検知器が有る場合は、trueを返す。
                    return true;
                }

                // 接続するアイルのダブルディープをチェックする。
                return isDoubleDeepConnectStation(st);
            }
            else
            {
                throw new ReadWriteException();
            }
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * ステーションが接続するアイルのダブルディープ有無を参照します。<br>
     * ステーションがアイル結合タイプなら、属する倉庫内のダブルディープ有無を参照します。
     * 
     * @param stationNo ステーションNo
     * @return 接続するアイルにダブルディープが有る場合はtrue、
     *          そうでない場合はfalseを返します。
     * @throws ReadWriteException
     *          データベースに対する処理で発生した場合に通知します。
     */
    public boolean isDoubleDeepConnectStation(String stationNo)
            throws ReadWriteException
    {
        try
        {
            StationSearchKey stationKey = new StationSearchKey();
            StationHandler stationHandler = new StationHandler(getConnection());

            // ステーションを取得
            stationKey.setCollect(new FieldName(Station.STORE_NAME, FieldName.ALL_FIELDS));
            stationKey.setStationNo(stationNo);
            Station st = (Station)stationHandler.findPrimary(stationKey);
            if (st != null)
            {
                // 接続するアイルのダブルディープをチェックします。
                return isDoubleDeepConnectStation(st);
            }
            else
            {
                throw new ReadWriteException();
            }
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * ステーションが接続するアイルのダブルディープ有無を参照します。<br>
     * ステーションがアイル結合タイプなら、属する倉庫内のダブルディープ有無を参照します。
     * <ol>
     * <li>アイル独立のステーションで、そのアイルはダブルディープである。
     * <li>アイル結合のステーションで、その倉庫にダブルディープがある。
     * </ol>
     * @param st ステーション インスタンス
     * @return 接続するアイルにダブルディープが有る場合はtrue、
     *          そうでない場合はfalseを返します。
     * @throws ReadWriteException
     *          データベースに対する処理で発生した場合に通知します。
     */
    public boolean isDoubleDeepConnectStation(Station st)
            throws ReadWriteException
    {
        if (StringUtil.isBlank(st.getAisleStationNo()))
        {
            // アイル結合ならば、その倉庫にダブルディープのアイルが有るか。
            AisleSearchKey aisleKey = new AisleSearchKey();
            AisleHandler aisleHandler = new AisleHandler(getConnection());
            FieldName aisleCount = new FieldName(Aisle.STORE_NAME, "COUNT");
            aisleKey.setCollect(Aisle.AISLE_NO, "COUNT", aisleCount);
            aisleKey.setDoubleDeepKind(Aisle.DOUBLE_DEEP_KIND_DOUBLE);
            aisleKey.setWhStationNo(st.getWhStationNo());
            if (aisleHandler.count(aisleKey) > 0)
            {
                return true;
            }
        }
        else
        {
            try
            {
                // アイル独立ならば、そのアイルがダブルディープである。
                AisleSearchKey aisleKey = new AisleSearchKey();
                AisleHandler aisleHandler = new AisleHandler(getConnection());
                FieldName aisleFld = new FieldName(Aisle.STORE_NAME, Aisle.DOUBLE_DEEP_KIND.getName());
                aisleKey.setCollect(aisleFld);
                aisleKey.setStationNo(st.getAisleStationNo());
                Aisle al = (Aisle)aisleHandler.findPrimary(aisleKey);
                if (al != null)
                {
                    if (Aisle.DOUBLE_DEEP_KIND_DOUBLE.equals(al.getDoubleDeepKind()))
                    {
                        return true;
                    }
                }
                else
                {
                    throw new ReadWriteException();
                }
            }
            catch (NoPrimaryException e)
            {
                throw new ReadWriteException(e);
            }
        }

        return false;
    }

    /**
     * ステーションが属する倉庫を参照し、フリーアロケーション運用の倉庫かどうかをチェックします。
     * 
     * @param stationNo ステーションNo
     * @return フリーアロケーション運用の場合はtrue、そうでない場合はfalseを返します。
     * @throws ReadWriteException
     *          データベースに対する処理で発生した場合に通知します。
     */
    public boolean isFreeAllocationStation(String stationNo)
            throws ReadWriteException
    {
        try
        {
            WareHouseHandler whHandler = new WareHouseHandler(getConnection());
            WareHouseSearchKey whKey = new WareHouseSearchKey();

            // ステーションを取得
            whKey.setCollect(new FieldName(WareHouse.STORE_NAME, FieldName.ALL_FIELDS));
            whKey.setJoin(WareHouse.STATION_NO, Station.WH_STATION_NO);
            whKey.setKey(Station.STATION_NO, stationNo);
            WareHouse wh = (WareHouse)whHandler.findPrimary(whKey);

            if (wh != null)
            {
                // フリーアロケーション運用倉庫かどうかチェックする
                if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()))
                {
                    // フリーアロケーション運用の場合は、trueを返す。
                    return true;
                }

                // 接続するアイルのダブルディープをチェックする。
                return false;
            }
            else
            {
                throw new ReadWriteException();
            }
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
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
        return "$Id: StationController.java 6954 2010-01-29 08:54:15Z shibamoto $";
    }
}
