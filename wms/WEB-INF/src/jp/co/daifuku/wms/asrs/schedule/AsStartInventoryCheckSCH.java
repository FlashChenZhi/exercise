// $Id: AsStartInventoryCheckSCH.java 6901 2010-01-25 11:22:02Z kumano $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsStartInventoryCheckSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.location.AisleOperator;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.LocationNumber;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkListHandler;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Reason;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.entity.WorkList;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 在庫確認開始設定のスケジュール処理を行います。
 *
 * @version $Revision: 6901 $, $Date: 2010-01-25 20:22:02 +0900 (月, 25 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class AsStartInventoryCheckSCH
        extends AbstractAsrsSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // ステーションNo.(最大値)
    private final FieldName _stasion_no_min = new FieldName("", "STASION_NO", "STASION_NO_MIN");

    // ステーションNo.(最小値)
    private final FieldName _stasion_no_max = new FieldName("", "STASION_NO", "STASION_NO_MAX");

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public AsStartInventoryCheckSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期データ検索を行います。
     * 
     * @param p 検索条件を指定したパラメータ
     * @return 初期表示用データ
     * @throws CommonException アクセスエラーなどの例外発生時にスローされます。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        Params outParam = new Params();

        try
        {
            WebSettingHandler webhandler = new WebSettingHandler(getConnection());
            WebSettingSearchKey key = new WebSettingSearchKey();

            // 端末No.
            key.setTerminalNo(getWmsUserInfo().getTerminalNo());
            // 画面ID
            key.setFunctionid(p.getString(FUNCTION_ID));
            // キーデータ
            key.setKeydata(WebSetting.KEY_LIST_CHECK);

            WebSetting[] webset = (WebSetting[])webhandler.find(key);

            if (webset != null && webset.length > 0)
            {
                outParam.set(PRINT_FLAG, webset[0].getValue());
            }
        }
        catch (Exception e)
        {
            // 6006042=画面定義情報の参照に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006042, e), getClass().getName());
        }

        return outParam;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、在庫確認開始設定スケジュールを行います。<BR>
     * 
     * @param startParams 設定内容を持つ<CODE>AsrsInParameter</CODE>クラスのインスタンスの配列。 <BR>
     * @return true 処理が正常終了した場合 <BR> false 正常に処理が行えなかった場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean startSCH(ScheduleParams... startParams)
            throws CommonException
    {
        ScheduleParams params = startParams[0];
        AsrsInParameter param = new AsrsInParameter(getWmsUserInfo());

        AsStartInventoryCheckSCHParams startParam = (AsStartInventoryCheckSCHParams)startParams[0];

        param.setAreaNo(startParam.getString(AREA));
        param.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
        param.setItemCode(startParam.getString(START_ITEM_CODE));
        param.setToItemCode(startParam.getString(END_ITEM_CODE));
        // 入力した棚をパラメータをセット
        param.setLocation(startParam.getString(START_LOCATION));
        param.setToLocation(startParam.getString(END_LOCATION));
        param.setStationNo(startParam.getString(STATION));

        // 正常完了チェックフラグ
        boolean isComplete = false;
        // 在庫確認中チェック更新フラグ
        boolean isUpdate = false;

        // ダブルディープチェック
        if (!checkDoubleDeep(param))
        {
            return false;
        }

        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        // ステーションの状態チェック
        if (!inventoryStationCheck(param.getStationNo()))
        {
            return false;
        }

        // 在庫確認以外の搬送がないかをチェック
        AreaController control = new AreaController(getConnection(), this.getClass());

        String[] whAisle = null;
        // 倉庫、棚範囲から紐づくアイルステーションNo.を求める
        whAisle =
                getAisleStation(control.getWhStationNo(param.getAreaNo()), param.getStationNo(), param.getLocation(),
                        param.getToLocation(), param.getAreaNo());
        // 不正棚チェック
        if (whAisle == null || whAisle.length == 0)
        {
            //<jp> 6022020=指定された棚No.の範囲に誤りがあります。</jp>
            setMessage("6022020");
            return false;
        }

        // 入力ステーションから搬送可能なアイルステーションを取得します
        AisleOperator aop = new AisleOperator();
        String[] stAisle = aop.getAisleStationNo(getConnection(), param.getStationNo());

        AsrsOperator operator = new AsrsOperator(getConnection(), getClass());
        AsrsOutParameter outParam = new AsrsOutParameter();

        // 在庫確認を開始するアイルステーションNo.配列
        String[] aisles = null;

        try
        {
            // スケジュール中に他のスケジュールによるマルチ引当を防ぐため、スケジュール引当前に
            // 在庫確認中フラグの更新を行い、トランザクションをコミットする。
            aisles = getAisleArray(whAisle, stAisle);
            // 搬送情報検索チェック
            if (!checkAisle(aisles))
            {
                return false;
            }

            String[] modifyAisle = modifyInventoryCheckFlag(getConnection(), aisles);

            if (modifyAisle == null)
            {
                // 6023163=指定された範囲で在庫確認以外の作業を行っているため設定できません。
                setMessage("6023163");
                return false;
            }
            doCommit(getClass());
            isUpdate = true;


            // ASRS在庫確認開始設定
            outParam = operator.webStartInventory(param);

            if (outParam == null)
            {
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
                return false;
            }
            else
            {
                // 正常完了の場合、フラグをON
                isComplete = true;
            }

            // 設定単位キー
            // Businessの印刷処理で使用するためパラメータに印刷KEYをセットする。
            params.set(SETTING_UKEYS, outParam.getSettingUnitKey());

            // 作業リスト情報を作成
            createWorkListByWorkInfo(outParam.getSettingUnitKey());

            // 6001006 = 設定しました。
            setMessage("6001006");

            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = startParams[0].getBoolean(COMMON_USE) ? WebSetting.KEYDATA_ON
                                                                : WebSetting.KEYDATA_OFF;

            updateWebSetting(getUserInfo().getTerminalNumber(), startParams[0].getString(FUNCTION_ID), value);

            return true;
        }
        catch (LockTimeOutException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
        }
        catch (DataExistsException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        catch (NotFoundException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        catch (RouteException e)
        {
            // 搬送ルート異常
            setMessage(getRouteErrorMessage(e.getRouteStatus()));
            return false;
        }
        catch (OperatorException e)
        {
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 他端末で処理中のため、処理を中断しました。
                setMessage("6023114");
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
        finally
        {
            if (isComplete)
            {
                // 正常だった場合はコミット
                doCommit(getClass());
            }
            else
            {
                doRollBack(getClass());
            }

            // 在庫確認中フラグを更新していた場合のチェック
            if (isUpdate)
            {
                CarryInfoHandler cryHandle = new CarryInfoHandler(getConnection());
                CarryInfoSearchKey cKey = new CarryInfoSearchKey();

                Vector<String> temp = new Vector<String>();
                // 指定アイルに対して、在庫確認作業が存在しない場合「在庫確認中」をリセットする
                for (int i = 0; i < aisles.length; i++)
                {
                    // 搬送データより、該当アイルステーションにて在庫確認作業の有無をチェックします。
                    cKey.clear();
                    cKey.setAisleStationNo(aisles[i]);
                    cKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK);

                    // 在庫確認中の搬送データがある場合は、フラグをOFFしない
                    if (cryHandle.count(cKey) > 0)
                    {
                        continue;
                    }

                    temp.add(aisles[i]);
                }

                if (temp.size() > 0)
                {
                    // 在庫確認中フラグをOFF
                    String[] recoverAisle = new String[temp.size()];
                    temp.toArray(recoverAisle);
                    updateAisle(recoverAisle);
                    doCommit(getClass());
                }
            }
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
    /**
     * 指定された倉庫と棚No範囲より、在庫確認作業が可能かどうかチェックを行ないます。<BR>
     * 指定された棚Noの範囲に存在するアイルが入出庫作業中でないかをチェックします。<BR>
     * 入出庫作業中の場合は、falseを返します<BR>
     * 
     * @param aisles 倉庫ステーションNo.
     * @return true 在庫確認作業可 <BR> false 在庫確認作業不可
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkAisle(String[] aisles)
            throws CommonException
    {
        if (aisles.length == 0)
        {
            // 6023110 = 搬送ルートが存在しないため搬送できません。
            setMessage("6023110");
            return false;
        }

        //<jp> 入出庫作業があるかどうか。</jp>
        if (!checkCarry(aisles))
        {
            // 6023133 = 現在入出庫作業中です。設定できません。
            setMessage("6023133");
            return false;
        }

        return true;
    }


    /**
     * 指定されたステーションからの搬送ルートが有るかを判断し、
     * アイルステーションの配列を返します。<BR>>
     * @param whAisle 倉庫ステーションNo.
     * @param stAisle 作業場またはステーションNo.
     * @return 指定された範囲に存在するアイルステーションNoの配列。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected String[] getAisleArray(String[] whAisle, String[] stAisle)
            throws CommonException
    {
        // ステーションから紐づくアイルと、倉庫に属するアイルで一致したものを
        // 今回搬送可能なアイルステーションとする。
        Vector<String> vec = new Vector<String>();
        for (int i = 0; i < stAisle.length; i++)
        {
            for (int j = 0; j < whAisle.length; j++)
            {
                if (stAisle[i].equals(whAisle[j]))
                {
                    vec.addElement(stAisle[i]);
                }
            }
        }
        String[] resultArray = new String[vec.size()];
        vec.copyInto(resultArray);
        return resultArray;
    }

    /**
     * 指定された倉庫と棚範囲の、アイルステーションの配列を返します。<BR>
     * frlocが空白またはnullの場合、先頭バンクを検索し扱います<BR>
     * tolocが空白またはnullの場合、最終バンクを検索し扱います。<BR>
     * 
     * @param whStno 倉庫ステーションNo.
     * @param stno 作業場またはステーションNo.
     * @param frloc 開始棚(01001001)
     * @param toloc 終了棚(01001001)
     * @param area エリア
     * @return 指定された範囲に存在するアイルステーションNoの配列。
     *          存在しない棚を指定されている場合は、nullを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected String[] getAisleStation(String whStno, String stno, String frloc, String toloc, String area)
            throws CommonException
    {
        LocationNumber fromLoc = null;
        LocationNumber toLoc = null;
        int frbank = 0;
        int tobank = 0;

        // 倉庫、棚範囲から紐づくアイルステーションNo.を求める
        ShelfHandler slfHandle = new ShelfHandler(getConnection());
        ShelfSearchKey slfKey = new ShelfSearchKey();

        AreaController areaCtlr = new AreaController(getConnection(), this.getClass());

        // 開始棚が指定されている場合、最小バンクを求めます。
        // 開始棚のステーションNo
        if (!StringUtil.isBlank(frloc))
        {
            fromLoc = new LocationNumber(areaCtlr.getLocationStyle(area));
            fromLoc.parseParam(frloc);
            String[] fromLocation = fromLoc.getLocation();

            frbank = Integer.parseInt(fromLocation[LocationNumber.IDX_BANK]);
        }
        else
        {
            slfKey.setWhStationNo(whStno);
            slfKey.setBankNoCollect("MIN");
            Shelf[] shelf = (Shelf[])slfHandle.find(slfKey);
            frbank = shelf[0].getBankNo();
            if (frbank == 0)
            {
                return null;
            }
        }

        // 終了棚が指定されている場合、最大バンクを求めます        
        // 終了棚のステーションNo
        if (!StringUtil.isBlank(toloc))
        {
            toLoc = new LocationNumber(areaCtlr.getLocationStyle(area));
            toLoc.parseParam(toloc);
            String[] toLocation = toLoc.getLocation();

            tobank = Integer.parseInt(toLocation[LocationNumber.IDX_BANK]);
        }
        else
        {
            slfKey.clear();
            slfKey.setWhStationNo(whStno);
            slfKey.setBankNoCollect("MAX");
            Shelf[] shelf2 = (Shelf[])slfHandle.find(slfKey);
            tobank = shelf2[0].getBankNo();
            if (tobank == 0)
            {
                return null;
            }
        }
        // 倉庫とバンクの範囲を指定し、紐づくアイルステーションNo.を求めます
        String[] whAisle = AisleOperator.getAisleStationNos(getConnection(), whStno, frbank, tobank);

        return whAisle;
    }

    /**
     * 搬送情報検索チェックを行います。<BR>
     * 指定されたアイルが、在庫確認以外の作業を行っていないかをチェックします。<BR>
     * 在庫確認以外の搬送情報があった場合は、NGとします。<BR>
     * 
     * @param checkAisle 搬送状態をチェックするアイルステーションNo.配列
     * @return true 在庫確認以外の搬送情報はない <BR> false 在庫確認以外の搬送情報がある
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkCarry(String[] checkAisle)
            throws CommonException
    {
        CarryInfoHandler cryHandler = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cryKey = new CarryInfoSearchKey();

        cryKey.clear();
        if (checkAisle != null && checkAisle.length > 0)
        {
            cryKey.setAisleStationNo(checkAisle, true);
        }
        cryKey.setRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK, "!=");

        // 在庫確認以外の作業が1件でもある場合は、NG
        if (cryHandler.count(cryKey) > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 指定された範囲のアイルを在庫確認中に変更します。<BR>
     * 
     * @param conn DBConnection
     * @param aislenos 在庫確認設定を行うアイルの配列
     * @return 変更されたアイルステーションNoの一覧
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected String[] modifyInventoryCheckFlag(Connection conn, String[] aislenos)
            throws CommonException
    {
        AisleHandler alHandle = new AisleHandler(conn);
        AisleSearchKey alKey = new AisleSearchKey();
        ArrayList<String> array = new ArrayList<String>();

        for (String aisleNo : aislenos)
        {
            alKey.clear();
            alKey.setStationNo(aisleNo);
            Aisle al = (Aisle)alHandle.findPrimary(alKey);
            // 該当アイルの在庫確認中フラグが未作業の場合更新する
            if (Aisle.INVENTORY_CHECK_FLAG_UNSTART.equals(al.getInventoryCheckFlag()))
            {
                AisleOperator aop = new AisleOperator(conn, aisleNo);
                aop.alterInventoryCheckFlag(Aisle.INVENTORY_CHECK_FLAG_WORKING);
            }
            // 該当アイルの在庫確認中フラグが未作業以外で
            // 作業中でない場合は、nullを返す
            else
            {
                if (!Aisle.INVENTORY_CHECK_FLAG_WORKING.equals(al.getInventoryCheckFlag()))
                {
                    return null;
                }
            }
            array.add(aisleNo);
        }

        // 在庫確認中、又は、在庫確認に更新できた場合は変更されたアイルステーションの一覧を返す
        String[] retAisles = new String[array.size()];
        array.toArray(retAisles);

        return retAisles;
    }

    /**
     * アイル情報テーブルを未作業に更新します。
     * 
     * @param aislestations アイルステーションNo.
     * @return 更新処理が正常に完了したらTrue、失敗したらFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean updateAisle(String[] aislestations)
            throws CommonException
    {
        AisleHandler aisleHandler = new AisleHandler(getConnection());
        AisleAlterKey aisleAlterKey = new AisleAlterKey();

        // ステーションNo
        aisleAlterKey.setStationNo(aislestations, true);
        // 在庫確認中フラグを更新（未作業）
        aisleAlterKey.updateInventoryCheckFlag(Aisle.INVENTORY_CHECK_FLAG_UNSTART);
        // データの更新
        aisleHandler.modify(aisleAlterKey);
        return true;
    }

    /**
     * 設定範囲内にペア棚も指定されているかチェックします。
     * 
     * @param whNo    倉庫No.
     * @param fromLoc 棚No.
     * @param toLoc   TO 棚No.
     * @param param パラメータ
     * @return 範囲内にペア棚も指定されていればTrue、それ以外はFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkPair(String whNo, LocationNumber fromLoc, LocationNumber toLoc, AsrsInParameter param)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;

        // SQL
        // 自棚、ペア棚検索共有のSQLを生成
        //        FROM DMSHELF, DMAISLE
        //        WHERE DMAISLE.DOUBLE_DEEP_KIND = '1'
        //        AND DMAISLE.STATION_NO = DMSHELF.PARENT_STATION_NO
        //        AND DMSHELF.WH_STATION_NO = '9000'
        //        AND DMSHELF.BANK_NO >= 1
        //        AND DMSHELF.BANK_NO <= 6
        //        AND DMSHELF.BAY_NO >= 1
        //        AND DMSHELF.BAY_NO <= 2
        //        AND DMSHELF.LEVEL_NO >= 1
        //        AND DMSHELF.LEVEL_NO <= 1
        //
        StringBuffer comSql = new StringBuffer();
        comSql.append("FROM DMSHELF, DMAISLE ");
        comSql.append("WHERE DMAISLE.DOUBLE_DEEP_KIND = ");
        comSql.append(DBFormat.format(Aisle.DOUBLE_DEEP_KIND_DOUBLE));
        comSql.append(" AND DMAISLE.STATION_NO = DMSHELF.PARENT_STATION_NO ");
        if (fromLoc != null)
        {
            String[] location = fromLoc.getLocation();
            comSql.append(" AND DMSHELF.BANK_NO >= ");
            comSql.append(Integer.parseInt(location[LocationNumber.IDX_BANK]));
            comSql.append(" AND DMSHELF.BAY_NO >= ");
            comSql.append(Integer.parseInt(location[LocationNumber.IDX_BAY]));
            comSql.append(" AND DMSHELF.LEVEL_NO >= ");
            comSql.append(Integer.parseInt(location[LocationNumber.IDX_LEVEL]));
        }
        if (toLoc != null)
        {
            String[] location = toLoc.getLocation();
            comSql.append(" AND DMSHELF.BANK_NO <= ");
            comSql.append(Integer.parseInt(location[LocationNumber.IDX_BANK]));
            comSql.append(" AND DMSHELF.BAY_NO <= ");
            comSql.append(Integer.parseInt(location[LocationNumber.IDX_BAY]));
            comSql.append(" AND DMSHELF.LEVEL_NO <= ");
            comSql.append(Integer.parseInt(location[LocationNumber.IDX_LEVEL]));
        }

        // ペア存在チェック用のSQLを生成
        //        SELECT COUNT(*)
        //        FROM 
        //        (SELECT DMSHELF.STATION_NO
        //        FROM DMSHELF, DMAISLE
        //        WHERE DMAISLE.DOUBLE_DEEP_KIND = '1'
        //        AND DMAISLE.STATION_NO = DMSHELF.PARENT_STATION_NO
        //        AND DMSHELF.WH_STATION_NO = '9000'
        //        AND DMSHELF.BANK_NO >= 1
        //        AND DMSHELF.BANK_NO <= 6
        //        AND DMSHELF.BAY_NO >= 1
        //        AND DMSHELF.BAY_NO <= 2
        //        AND DMSHELF.LEVEL_NO >= 1
        //        AND DMSHELF.LEVEL_NO <= 1
        //        ) ST,
        //        (SELECT DMSHELF.PAIR_STATION_NO
        //        FROM DMSHELF, DMAISLE
        //        WHERE DMAISLE.DOUBLE_DEEP_KIND = '1'
        //        AND DMAISLE.STATION_NO = DMSHELF.PARENT_STATION_NO
        //        AND DMSHELF.WH_STATION_NO = '9000'
        //        AND DMSHELF.BANK_NO >= 1
        //        AND DMSHELF.BANK_NO <= 6
        //        AND DMSHELF.BAY_NO >= 1
        //        AND DMSHELF.BAY_NO <= 2
        //        AND DMSHELF.LEVEL_NO >= 1
        //        AND DMSHELF.LEVEL_NO <= 1
        //        ) PAIR
        //        WHERE ST.STATION_NO = PAIR.PAIR_STATION_NO(+)
        //        AND PAIR.PAIR_STATION_NO IS NULL
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(*) CNT FROM (");
        sql.append("SELECT DMSHELF.STATION_NO ");
        sql.append(comSql).append(") ST, ");
        sql.append("(SELECT DMSHELF.PAIR_STATION_NO ");
        sql.append(comSql).append(") PAIR ");
        sql.append("WHERE ST.STATION_NO = PAIR.PAIR_STATION_NO(+) ");
        sql.append("AND PAIR.PAIR_STATION_NO IS NULL ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // カウントSQLの実行
            ddbHandler.execute(String.valueOf(sql));
            Entity[] countEntity = ddbHandler.getEntities(1, new Shelf());

            // ペアなしのデータが存在すればfalseを返す
            if (countEntity[0].getBigDecimal(new FieldName("", "CNT")).intValue() > 0)
            {
                return false;
            }

            return true;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
    }

    /**
     * ダブルディープ時の入力チェックを行います。
     * 
     * @param param 設定内容を持つ<CODE>AsrsInParameter</CODE>クラスのインスタンス<BR>
     * @return 入力条件が正しいならばtrue、正しくない時はfalseを返します。<BR>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkDoubleDeep(AsrsInParameter param)
            throws CommonException
    {
        // 棚No.管理クラス
        LocationNumber fromLoc = null;
        LocationNumber toLoc = null;

        // エリア情報コントローラ
        AreaController areaCtlr = new AreaController(getConnection(), getParent());

        // 倉庫ステーションNo.
        String whNo = areaCtlr.getWhStationNo(param.getAreaNo());

        // From棚No.
        if (!StringUtil.isBlank(param.getLocation()))
        {
            fromLoc = new LocationNumber(areaCtlr.getLocationStyle(param.getAreaNo()));
            fromLoc.parseParam(param.getLocation());
        }

        // To棚No.
        if (!StringUtil.isBlank(param.getToLocation()))
        {
            toLoc = new LocationNumber(areaCtlr.getLocationStyle(param.getAreaNo()));
            toLoc.parseParam(param.getToLocation());
        }

        // AS/RS棚情報ハンドラの生成
        ShelfHandler slfh = new ShelfHandler(getConnection());
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey slfKey = new ShelfSearchKey();

        // 在庫確認範囲にダブルディープ棚が存在するかチェック
        // From棚No.
        if (fromLoc != null)
        {
            String[] location = fromLoc.getLocation();
            slfKey.setBankNo(Integer.parseInt(location[LocationNumber.IDX_BANK]), ">=");
            slfKey.setBayNo(Integer.parseInt(location[LocationNumber.IDX_BAY]), ">=");
            slfKey.setLevelNo(Integer.parseInt(location[LocationNumber.IDX_LEVEL]), ">=");
        }

        // To棚No.
        if (toLoc != null)
        {
            String[] location = toLoc.getLocation();
            slfKey.setBankNo(Integer.parseInt(location[LocationNumber.IDX_BANK]), "<=");
            slfKey.setBayNo(Integer.parseInt(location[LocationNumber.IDX_BAY]), "<=");
            slfKey.setLevelNo(Integer.parseInt(location[LocationNumber.IDX_LEVEL]), "<=");
        }
        // 倉庫ステーションNo.
        slfKey.setWhStationNo(whNo);
        // AS/RS棚情報.親ステーションNo.とAS/RSアイル情報.ステーションNo.が同一
        slfKey.setJoin(Aisle.STATION_NO, Shelf.PARENT_STATION_NO);
        // AS/RSアイル情報.ダブルディープ区分(ダブルディープ)
        slfKey.setKey(Aisle.DOUBLE_DEEP_KIND, SystemDefine.DOUBLE_DEEP_KIND_DOUBLE);

        // シングルのみの場合はtrue
        if (slfh.count(slfKey) == 0)
        {
            return true;
        }

        if (fromLoc != null && toLoc != null)
        {
            slfKey.setCollect(Shelf.STATION_NO, "MIN", _stasion_no_min);
            slfKey.setCollect(Shelf.STATION_NO, "MAX", _stasion_no_max);
            Shelf[] shelf = (Shelf[])slfh.find(slfKey);
            String locfrom = String.valueOf(shelf[0].getValue(_stasion_no_min));
            String locto = String.valueOf(shelf[0].getValue(_stasion_no_max));
            fromLoc.parseParam(areaCtlr.toParamLocation(locfrom));
            toLoc.parseParam(areaCtlr.toParamLocation(locto));
        }

        // ペア棚が両方指定されているかチェック
        if (!checkPair(whNo, fromLoc, toLoc, param))
        {
            // 6023300=設定範囲にダブルディープ棚が存在する場合、ペア（奥・手前）棚の範囲で指定してください。
            setMessage("6023300");
            return false;
        }

        // 商品コードの入力チェック
        if (!StringUtil.isBlank(param.getItemCode()) || !StringUtil.isBlank(param.getToItemCode()))
        {
            // 6023301=設定範囲にダブルディープ棚が存在する場合、商品コードは指定出来ません。
            setMessage("6023301");
            return false;
        }

        // 在庫確認するアイルの中に既に在庫確認中の搬送情報がある場合、設定出来ない
        Station tStation = getStation(param.getStationNo());
        if (null == tStation)
        {
            throw new ScheduleException("No station found:" + param.getStationNo());
        }
        String aisleStationNo = tStation.getAisleStationNo();
        String[] aisleArray = null;

        // 設定したステーションがアイル結合ステーションならば、棚の範囲からアイルを取得します。
        if (StringUtil.isBlank(aisleStationNo))
        {
            slfKey.setParentStationNoCollect();
            if (fromLoc != null)
            {
                String[] location = fromLoc.getLocation();
                slfKey.setBankNo(Integer.parseInt(location[LocationNumber.IDX_BANK]), ">=");
                slfKey.setBayNo(Integer.parseInt(location[LocationNumber.IDX_BAY]), ">=");
                slfKey.setLevelNo(Integer.parseInt(location[LocationNumber.IDX_LEVEL]), ">=");
            }
            if (toLoc != null)
            {
                String[] location = toLoc.getLocation();
                slfKey.setBankNo(Integer.parseInt(location[LocationNumber.IDX_BANK]), "<=");
                slfKey.setBayNo(Integer.parseInt(location[LocationNumber.IDX_BAY]), "<=");
                slfKey.setLevelNo(Integer.parseInt(location[LocationNumber.IDX_LEVEL]), "<=");
            }
            slfKey.setWhStationNo(whNo);
            slfKey.setParentStationNoGroup();
            slfKey.setParentStationNoOrder(true);
            Shelf[] slfs = (Shelf[])slfh.find(slfKey);
            aisleArray = new String[slfs.length];
            for (int i = 0; i < slfs.length; i++)
            {
                aisleArray[i] = slfs[i].getParentStationNo();
            }
        }
        else
        {
            // 設定したステーションがアイル独立のステーション
            aisleArray = new String[1];
            aisleArray[0] = aisleStationNo;
        }

        // 搬送情報に在庫確認中データがないか検索する
        CarryInfoHandler carHnd = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cKey = new CarryInfoSearchKey();
        cKey.setRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK);
        cKey.setAisleStationNo(aisleArray, true);
        cKey.setJoin(Aisle.STATION_NO, CarryInfo.AISLE_STATION_NO);
        cKey.setKey(Aisle.DOUBLE_DEEP_KIND, SystemDefine.DOUBLE_DEEP_KIND_DOUBLE);

        if (carHnd.count(cKey) > 0)
        {
            // 6023311=設定範囲のダブルディープアイルで在庫確認中です。設定できません。
            setMessage("6023311");
            return false;
        }

        return true;
    }

    /**
     * 設定単位キーを元に、入出庫作業情報を検索を行い、作業リスト情報を作成します。<br>
     * <ul>
     * 以下のDB情報を検索します。
     * <li>AS/RS搬送情報
     * <li>在庫情報
     * <li>出荷先マスタ
     * <li>商品マスタ
     * <li>理由区分マスタ
     * <li>ログインユーザ
     * </ul>
     *
     * @param key 設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    protected void createWorkListByWorkInfo(String key)
            throws ReadWriteException,
                DataExistsException,
                ScheduleException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wkey = new WorkInfoSearchKey();

        // 取得項目一覧の作成
        FieldName[] collects = {
                WorkInfo.JOB_NO,
                CarryInfo.CARRY_KEY,
                WorkInfo.SETTING_UNIT_KEY,
                WorkInfo.COLLECT_JOB_NO,
                WorkInfo.JOB_TYPE,
                WorkInfo.PLAN_UKEY,
                WorkInfo.STOCK_ID,
                CarryInfo.PALLET_ID,
                WorkInfo.PLAN_DAY,
                WorkInfo.CONSIGNOR_CODE,
                Consignor.CONSIGNOR_NAME,
                WorkInfo.CUSTOMER_CODE,
                Customer.CUSTOMER_NAME,
                WorkInfo.SHIP_TICKET_NO,
                WorkInfo.SHIP_LINE_NO,
                WorkInfo.SHIP_BRANCH_NO,
                WorkInfo.BATCH_NO,
                WorkInfo.ORDER_NO,
                WorkInfo.PLAN_AREA_NO,
                WorkInfo.PLAN_LOCATION_NO,
                WorkInfo.ITEM_CODE,
                Item.ITEM_NAME,
                Item.JAN,
                Item.ITF,
                Item.BUNDLE_ITF,
                Item.ENTERING_QTY,
                Item.BUNDLE_ENTERING_QTY,
                WorkInfo.PLAN_LOT_NO,
                WorkInfo.PLAN_QTY,
                Stock.STOCK_QTY,
                Stock.ALLOCATION_QTY,
                WorkInfo.REASON_TYPE,
                Reason.REASON_NAME,
                CarryInfo.PRIORITY,
                CarryInfo.RETRIEVAL_STATION_NO,
                CarryInfo.RETRIEVAL_DETAIL,
                CarryInfo.WORK_NO,
                CarryInfo.SOURCE_STATION_NO,
                CarryInfo.DEST_STATION_NO,
                CarryInfo.SCHEDULE_NO,
                CarryInfo.END_STATION_NO,
                WorkInfo.USER_ID,
                WorkInfo.TERMINAL_NO,
        };

        // 取得フィールドのセット
        for (FieldName fld : collects)
        {
            wkey.setCollect(fld);
        }

        // 作業リストと列名が異なる値の取得を追加
        wkey.setCollect(Com_loginuser.USERNAME, "", WorkList.USER_NAME);

        // 検索条件のセット
        wkey.setSettingUnitKey(key);

        // 結合条件のセット
        wkey.setJoin(WorkInfo.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);
        wkey.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        wkey.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
        wkey.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        wkey.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");
        wkey.setJoin(WorkInfo.REASON_TYPE, "", Reason.REASON_TYPE, "(+)");
        wkey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);
        wkey.setJoin(WorkInfo.STOCK_ID, Stock.STOCK_ID);

        wkey.setJoin(WorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        // ソート
        wkey.setJobNoOrder(true);

        // 検索実行
        Entity[] readEnts = wih.find(wkey);
        if (null == readEnts || readEnts.length == 0)
        {
            // 見つからなかった場合は、エラー
            throw new ScheduleException();
        }

        WorkListHandler wlh = new WorkListHandler(getConnection());
        String cname = getClass().getSimpleName();

        for (Entity readEnt : readEnts)
        {
            WorkList wlEnt = new WorkList();
            wlEnt.setValueMap(readEnt.getValueMap());

            wlEnt.setRegistDate(new SysDate());
            wlEnt.setRegistPname(cname);

            // insert
            wlh.create(wlEnt);
        }
    }

    /**
     * 画面定義情報を更新します。<br>
     * 更新処理で異常が発生した場合は、Exceptionをスローせず、
     * ロギングのみ行います。
     * 
     * @param term 端末No.
     * @param funcid 画面ID
     * @param value 更新値
     */
    protected void updateWebSetting(String term, String funcid, String value)
    {
        try
        {
            WebSettingHandler webhandler = new WebSettingHandler(getConnection());
            WebSettingAlterKey akey = new WebSettingAlterKey();

            akey.setTerminalNo(term);
            akey.setFunctionid(funcid);
            akey.setKeydata(WebSetting.KEY_LIST_CHECK);

            akey.updateValue(value);
            akey.updateLastUpdatePname(getClass().getSimpleName());

            try
            {
                webhandler.modify(akey);
            }
            catch (NotFoundException e)
            {
                // 更新対象がない場合は新規作成
                WebSetting newdata = new WebSetting();

                newdata.setTerminalNo(term);
                newdata.setFunctionid(funcid);
                newdata.setKeydata(WebSetting.KEY_LIST_CHECK);
                newdata.setValue(value);
                newdata.setRegistPname(getClass().getSimpleName());
                newdata.setLastUpdatePname(getClass().getSimpleName());

                webhandler.create(newdata);
            }
        }
        catch (Exception e)
        {
            // 6006043=画面定義情報の更新に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006043, e), getClass().getName());
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }
}
//end of class
