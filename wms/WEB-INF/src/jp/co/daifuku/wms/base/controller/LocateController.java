// $Id: LocateController.java 5020 2009-09-17 10:25:05Z kishimoto $
package jp.co.daifuku.wms.base.controller;

import java.sql.Connection;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateAlterKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.LocateHistory;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 棚マスタ情報コントローラクラスです。
 *
 *
 * @version $Revision: 5020 $, $Date: 2009-09-17 19:25:05 +0900 (木, 17 9 2009) $
 * @author  073019
 * @author  Last commit: $Author: kishimoto $
 */
public class LocateController
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
    public LocateController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入庫棚チェックを行います。<br>
     * パラメータのエリア、棚、商品が入庫可能かチェックを行います。<br>
     * 
     * @param stockEntity 在庫情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>商品コード
     * <li>エリア
     * <li>棚
     * </ol>
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException エリアマスタ情報が複数存在するときスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生したときにスローされます。
     */
    public void checkStorageLocate(Stock stockEntity)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException
    {
        // エリアマスタハンドラ
        AreaHandler areaHdl = new AreaHandler(getConnection());
        AreaSearchKey areaKey = new AreaSearchKey();
        // エリアNo
        areaKey.setAreaNo(stockEntity.getAreaNo());
        Area areaEntity = (Area)areaHdl.findPrimary(areaKey);

        // エリアマスタにデータ無しの場合
        if (areaEntity == null)
        {
            throw new OperatorException(OperatorException.ERR_NO_AREA_FOUND);
        }
        // 取得したエリア種別が「AS/RS」の場合
        if (Area.AREA_TYPE_ASRS.equals(areaEntity.getAreaType()))
        {
            throw new OperatorException(OperatorException.ERR_ASRS_AREA_FOUND);
        }
        // 取得した棚管理方式が「棚マスタ管理」の場合
        if (Area.LOCATION_TYPE_MASTER.equals(areaEntity.getLocationType()))
        {
            // 棚マスタ情報ハンドラ生成
            LocateHandler locateHdl = new LocateHandler(getConnection());
            LocateSearchKey locateKey = new LocateSearchKey();
            // エリア
            locateKey.setAreaNo(stockEntity.getAreaNo());
            // 棚
            locateKey.setLocationNo(stockEntity.getLocationNo());
            // 棚マスタにデータなしの場合
            if (locateHdl.count(locateKey) == 0)
            {
                throw new OperatorException(OperatorException.ERR_NO_LOCATION_FOUND);
            }
        }
        // 取得した棚管理方式が「固定棚管理管理」の場合
        else if (Area.LOCATION_TYPE_FIXED.equals(areaEntity.getLocationType()))
        {
            // 商品固定棚情報ハンドラ
            FixedLocateInfoHandler fixedLocHdl = new FixedLocateInfoHandler(getConnection());
            FixedLocateInfoSearchKey fixedLocKey = new FixedLocateInfoSearchKey();
            // 荷主コード
            fixedLocKey.setConsignorCode(stockEntity.getConsignorCode());
            // 商品コード
            fixedLocKey.setItemCode(stockEntity.getItemCode());
            // エリア
            fixedLocKey.setAreaNo(stockEntity.getAreaNo());
            // 棚
            fixedLocKey.setLocationNo(stockEntity.getLocationNo());
            // 商品固定棚情報にデータなしの場合
            if (fixedLocHdl.count(fixedLocKey) == 0)
            {
                throw new OperatorException(OperatorException.ERR_FIXED_ITEM_LOCATION_FOUND);
            }
        }
    }

    /**
     * エリアの棚管理方法をチェックします。<br>
     * 棚マスタ管理の場合、棚マスタ情報に登録されているかチェックします。<br>
     * 商品固定棚管理の場合、商品固定棚マスタ情報に登録されているかチェックします。
     * 
     * @param stockEntity 在庫情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>商品コード
     * <li>エリアNo.
     * </ol>
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException エリアマスタ情報が複数存在するときスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生したときにスローされます。
     */
    public void checkShippingLocate(Stock stockEntity)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException
    {
        // Area master handler
        AreaHandler areaHdl = new AreaHandler(getConnection());
        AreaSearchKey areaKey = new AreaSearchKey();
        // Area Number
        areaKey.setAreaNo(stockEntity.getAreaNo());
        Area areaEntity = (Area)areaHdl.findPrimary(areaKey);

        // if there is no data in the area master
        if (areaEntity == null)
        {
            throw new OperatorException(OperatorException.ERR_NO_AREA_FOUND);
        }
        // 取得したエリア種別が「AS/RS」の場合
        if (Area.AREA_TYPE_ASRS.equals(areaEntity.getAreaType()))
        {
            throw new OperatorException(OperatorException.ERR_ASRS_AREA_FOUND);
        }
        // 取得した棚管理方式が「棚マスタ管理」の場合
        if (Area.LOCATION_TYPE_MASTER.equals(areaEntity.getLocationType()))
        {
            // 棚マスタ情報ハンドラ生成
            LocateHandler locateHdl = new LocateHandler(getConnection());
            LocateSearchKey locateKey = new LocateSearchKey();
            // エリア
            locateKey.setAreaNo(stockEntity.getAreaNo());
            // 棚
            locateKey.setLocationNo(stockEntity.getLocationNo());
            // 棚マスタにデータなしの場合
            if (locateHdl.count(locateKey) == 0)
            {
                throw new OperatorException(OperatorException.ERR_NO_LOCATION_FOUND);
            }
        }
        // 取得した棚管理方式が「固定棚管理管理」の場合
        else if (Area.LOCATION_TYPE_FIXED.equals(areaEntity.getLocationType()))
        {
            // 商品固定棚情報ハンドラ
            FixedLocateInfoHandler fixedLocHdl = new FixedLocateInfoHandler(getConnection());
            FixedLocateInfoSearchKey fixedLocKey = new FixedLocateInfoSearchKey();
            // 荷主コード
            fixedLocKey.setConsignorCode(stockEntity.getConsignorCode());
            // 商品コード
            fixedLocKey.setItemCode(stockEntity.getItemCode());
            // エリア
            fixedLocKey.setAreaNo(stockEntity.getAreaNo());
            // 棚
            fixedLocKey.setLocationNo(stockEntity.getLocationNo());
            // 商品固定棚情報にデータなしの場合
            if (fixedLocHdl.count(fixedLocKey) == 0)
            {
                throw new OperatorException(OperatorException.ERR_FIXED_ITEM_LOCATION_FOUND);
            }
        }
    }


    /**
     * 在庫加算処理を行います。<br>
     * パラメータの項目に該当する棚マスタ情報の在庫加算処理を行います。<br>
     * 状態フラグが空棚の場合は実棚に更新します。
     * 
     * @param areaNo エリアNo.
     * @param locationNo 棚No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     */
    public void increaseStock(String areaNo, String locationNo)
            throws ReadWriteException,
                NotFoundException
    {
        // 棚マスタ情報ハンドラ
        LocateHandler locateHdl = new LocateHandler(getConnection());
        LocateSearchKey locateKey = new LocateSearchKey();
        // エリア
        locateKey.setAreaNo(areaNo);
        // 棚
        locateKey.setLocationNo(locationNo);
        Locate[] locateEntitys = (Locate[])locateHdl.find(locateKey);
        if (locateEntitys != null && locateEntitys.length == 1)
        {
            // 取得した棚状態フラグが「空棚」の場合
            if (Locate.LOCATION_STATUS_FLAG_EMPTY.equals(locateEntitys[0].getStatusFlag()))
            {
                // 棚マスタの更新条件
                LocateAlterKey locateAltKey = new LocateAlterKey();
                // エリア(更新条件)
                locateAltKey.setAreaNo(areaNo);
                // 棚(更新条件)
                locateAltKey.setLocationNo(locationNo);
                // 状態フラグ(更新値)
                locateAltKey.updateStatusFlag(Locate.LOCATION_STATUS_FLAG_STORAGED);
                // 最終更新処理名(更新値)
                locateAltKey.updateLastUpdatePname(getCallerName());
                // 更新実行
                locateHdl.modify(locateAltKey);
            }
        }
    }

    /**
     * 在庫減算処理を行います。<br>
     * パラメータの項目に該当する棚マスタ情報の在庫減算処理を行います。<br>
     * 在庫が存在しなければ状態フラグを空棚に更新します。
     * 
     * @param areaNo エリアNo.
     * @param locationNo 棚No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     */
    public void decreaseStock(String areaNo, String locationNo)
            throws ReadWriteException,
                NotFoundException
    {
        // 在庫ハンドラ
        StockHandler stockHdl = new StockHandler(getConnection());
        StockSearchKey stockKey = new StockSearchKey();
        // エリア
        stockKey.setAreaNo(areaNo);
        // 棚
        stockKey.setLocationNo(locationNo);
        // 在庫情報が存在しない場合
        if (stockHdl.count(stockKey) == 0)
        {
            // 棚マスタ情報ハンドラ生成
            LocateHandler locateHdl = new LocateHandler(getConnection());
            // 棚マスタの更新条件
            LocateAlterKey locateAltKey = new LocateAlterKey();
            // エリア(更新条件)
            locateAltKey.setAreaNo(areaNo);
            // 棚(更新条件)
            locateAltKey.setLocationNo(locationNo);
            // 状態フラグ(更新値)
            locateAltKey.updateStatusFlag(Locate.LOCATION_STATUS_FLAG_EMPTY);
            // 最終更新処理名(更新値)
            locateAltKey.updateLastUpdatePname(getCallerName());
            // 更新実行
            locateHdl.modify(locateAltKey);
        }
    }

    /**
     * 空棚検索キーを取得します。<BR>
     * パラメータの棚マスタ情報のエリアに該当する空棚検索方法で棚マスタ情報の空棚を検索するための検索キーを返します。<BR>
     * パラメータの棚マスタ情報の棚がNULLの場合は検索条件とせず、それ以外の場合はパラメータの比較条件で検索を行います。<BR>
     * <PRE>
     *     使用例)
     *         棚の全一致検索の場合
     *             Locate entity = new Locate();
     *             entity.setLocationNo("11111111");
     *             getEmpLocationKey(entity, "=");
     *         棚の比較検索の場合
     *             Locate entity = new Locate();
     *             entity.setLocationNo("11111111");
     *             getEmpLocationKey(entity, ">=");
     *         棚のあいまい検索の場合
     *             Locate entity = new Locate();
     *             entity.setLocationNo("11111111*");
     *             getEmpLocationKey(entity, "=");
     * </PRE>
     * 棚マスタ情報の棚にNULLを設定する場合は、<BR>
     * <code>Entity#setValue(FieldName field, Object value, boolean withValidate)</code>を使用してください。<BR>
     * <BR>
     * 
     * @param locateEntity 棚マスタ情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>棚No.
     * <li>エリアNo.
     * </ol>
     * 
     * @param locateCompCode 棚比較条件(">=", etc.)(LIKE検索の場合は棚に「<code>*</code>」を付与し、「<code>=</code>」を使用してください)
     * @return 棚マスタ情報検索キー<BR>
     *          エリアの棚管理方式がフリー管理の場合はnullを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException オペレータ処理でエラーが発生したときにスローされます。
     * @throws NoPrimaryException エリアマスタ情報が複数存在するときスローされます。
     * @throws InvalidDefineException 指定パラメータが異常(禁止文字含むなど)の場合にスローされます。
     */
    public LocateSearchKey getEmpLocationKey(Locate locateEntity, String locateCompCode)
            throws ReadWriteException,
                OperatorException,
                NoPrimaryException,
                InvalidDefineException
    {

        // 棚マスタ検索キーの生成
        LocateSearchKey locateKey = new LocateSearchKey();

        // エリアマスタハンドラ
        AreaHandler areaHdl = new AreaHandler(getConnection());
        AreaSearchKey areaKey = new AreaSearchKey();
        // エリアNo
        areaKey.setAreaNo(locateEntity.getAreaNo());
        Area areaEntity = (Area)areaHdl.findPrimary(areaKey);
        // エリアマスタにデータが存在しない場合
        if (areaEntity == null)
        {
            throw new OperatorException(OperatorException.ERR_NO_AREA_FOUND);
        }
        // 棚管理方式がフリー管理またはエリアタイプが平置き以外の場合
        if (Area.LOCATION_TYPE_FREE.equals(areaEntity.getLocationType())
                || !(Area.AREA_TYPE_FLOOR.equals(areaEntity.getAreaType())))
        {
            return null;
        }

        /* 検索条件の指定 */

        // エリア
        locateKey.setAreaNo(locateEntity.getAreaNo());
        // 棚が指定されている場合
        if (!StringUtil.isBlank(locateEntity.getLocationNo()))
        {
            locateKey.setLocationNo(locateEntity.getLocationNo(), locateCompCode);
        }
        // 状態フラグ(空棚)
        locateKey.setStatusFlag(Locate.LOCATION_STATUS_FLAG_EMPTY);

        /* ソート順の指定 */

        // 空棚検索方法が「バンク垂直検索」の場合
        if (Locate.VACANT_SEARCH_TYPE_BANK_VERTICAL.equals(areaEntity.getVacantSearchType()))
        {
            // バンク(昇順)
            locateKey.setBankNoOrder(true);
            // ベイ(昇順)
            locateKey.setBayNoOrder(true);
            // レベル(昇順)
            locateKey.setLevelNoOrder(true);
        }
        // 空棚検索方法が「アイル垂直検索」の場合
        else if (Locate.VACANT_SEARCH_TYPE_AISLE_VERTICAL.equals(areaEntity.getVacantSearchType()))
        {
            // アイル(昇順)
            locateKey.setAisleNoOrder(true);
            // ベイ(昇順)
            locateKey.setBayNoOrder(true);
            // レベル(昇順)
            locateKey.setLevelNoOrder(true);
            // バンク(昇順)
            locateKey.setBankNoOrder(true);
        }
        // 空棚検索方法が「バンク水平検索」の場合
        else if (Locate.VACANT_SEARCH_TYPE_BANK_HORIZONTAL.equals(areaEntity.getVacantSearchType()))
        {
            // バンク(昇順)
            locateKey.setBankNoOrder(true);
            // レベル(昇順)
            locateKey.setLevelNoOrder(true);
            // ベイ(昇順)
            locateKey.setBayNoOrder(true);
        }
        // 空棚検索方法が「アイル水平検索」の場合
        else if (Locate.VACANT_SEARCH_TYPE_AISLE_HORIZONTAL.equals(areaEntity.getVacantSearchType()))
        {
            // アイル(昇順)
            locateKey.setAisleNoOrder(true);
            // レベル(昇順)
            locateKey.setLevelNoOrder(true);
            // ベイ(昇順)
            locateKey.setBayNoOrder(true);
            // バンク(昇順)
            locateKey.setBankNoOrder(true);
        }
        else
        {
            throw new InvalidDefineException();
        }

        return locateKey;
    }

    /**
     * 棚マスタ更新履歴情報登録処理を行います。
     * 
     * @param locate          対象マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 棚マスタ更新履歴情報登録済み
     */
    public void insertHistory(Locate locate, String operationKind, DfkUserInfo ui)
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
        LocateHistoryHandler histch = new LocateHistoryHandler(getConnection());
        LocateHistory hist = new LocateHistory();

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
        // システム管理区分
        hist.setManagementType(locate.getManagementType());
        // エリアNo
        hist.setAreaNo(locate.getAreaNo());
        // 棚No
        hist.setLocationNo(locate.getLocationNo());
        if (SystemDefine.UPDATE_KIND_REGIST.equals(operationKind))
        {
            // アイルNo
            hist.setUpdateAisleNo(locate.getAisleNo());
        }
        else
        {
            // アイルNo
            hist.setAisleNo(locate.getAisleNo());
        }
        // バンク
        hist.setBankNo(locate.getBankNo());
        // ベイ
        hist.setBayNo(locate.getBayNo());
        // レベル
        hist.setLevelNo(locate.getLevelNo());
        // 状態フラグ
        hist.setStatusFlag(locate.getStatusFlag());
        // 棚フォーマット
        AreaController areacon = new AreaController(getConnection(), getClass());
        hist.setLocationStyle(areacon.getLocationStyle(locate.getAreaNo()));

        histch.create(hist);
    }

    /**
     * 棚マスタ更新履歴情報登録処理を行います。
     * 
     * @param oldlocate   修正前マスタ情報
     * @param newlocate   修正後マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 棚マスタ更新履歴情報登録済み
     */
    public void insertHistory(Locate oldlocate, Locate newlocate, String operationKind, DfkUserInfo ui)
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
        LocateHistoryHandler histch = new LocateHistoryHandler(getConnection());
        LocateHistory hist = new LocateHistory();

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
        // システム管理区分
        hist.setManagementType(oldlocate.getManagementType());
        // エリアNo
        hist.setAreaNo(oldlocate.getAreaNo());
        // 棚No
        hist.setLocationNo(oldlocate.getLocationNo());
        // アイルNo
        hist.setAisleNo(oldlocate.getAisleNo());
        // バンク
        hist.setBankNo(oldlocate.getBankNo());
        // ベイ
        hist.setBayNo(oldlocate.getBayNo());
        // レベル
        hist.setLevelNo(oldlocate.getLevelNo());
        // 状態フラグ
        hist.setStatusFlag(oldlocate.getStatusFlag());
        // 修正後アイルNo
        hist.setUpdateAisleNo(newlocate.getAisleNo());
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
        return "$Id: LocateController.java 5020 2009-09-17 10:25:05Z kishimoto $";
    }
}
