// $Id: FaAsReStoringSCH.java 7805 2010-04-09 04:04:40Z kishimoto $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.schedule.FaAsReStoringSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.ReStoringPlan;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;

/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7805 $, $Date:: 2010-04-09 13:04:40 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaAsReStoringSCH
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
    public FaAsReStoringSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
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
     * 画面から入力された内容をパラメータとして受け取り、作業No.に含まれる商品コードを取得して返します。<BR>
     * 
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *         該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     * @throws CommonException アクセスエラーなどの例外発生時にスローされます。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        ArrayList<Params> retList = new ArrayList<Params>();
        
        ReStoringPlan[] items = getItems(p.getString(WORK_NO));
        
        if (items == null || items.length == 0)
        {
            // 6023298=再入庫予定データ内に該当する作業No.が存在しません。
            setMessage("6023298");
            return retList;
        }
        
        // Setを使用し、商品コードの重複を防ぐ
        Set<String> itemList = new TreeSet<String>();
        for (ReStoringPlan item : items)
        {
            itemList.add(item.getItemCode());
        }
        
        Iterator<String> it = itemList.iterator();
        while (it.hasNext())
        {
            // パラメータクラス生成
            Params param = new Params();
            
            param.set(ITEM_CODE, it.next());
            retList.add(param);
        }

        return retList;
    }
    
    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
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

        // オペレータパラメータ生成
        AsrsInParameter[] inParams = new AsrsInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new AsrsInParameter(getWmsUserInfo());

            // エリアNo.
            inParams[i].setAreaNo(ps[i].getString(AREA_NO));
            // 作業場
            inParams[i].setWorkplace(ps[i].getString(WORK_PLACE));
            // ステーションNo.
            inParams[i].setStationNo(ps[i].getString(STATION_NO));
            // 作業No.
            inParams[i].setWorkNo(ps[i].getString(WORK_NO));
            // ソフトゾーンID
            inParams[i].setSoftZoneNo(ps[i].getString(SOFT_ZONE_ID));
            // リストセル行No.
            inParams[i].setRowNo(ps[i].getRowIndex());
        }

        // 処理条件チェックを行う
        if (!checkparam(inParams, getConnection()))
        {
            return false;
        }

        try
        {
            // オペレータ生成
            AsrsOperator operator = new AsrsOperator(getConnection(), getClass());
            // オペレータ呼び出し
            AsrsOutParameter outparam = operator.webStartReStoring(inParams);

            ps[0].set(SETTING_UNIT_KEY, outparam.getSettingUnitKey());

            // 6001006=設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
            
            // 画面定義情報を更新
            String value = ps[0].getBoolean(PRINT_FLAG) ? WebSetting.KEYDATA_ON
                                                        : WebSetting.KEYDATA_OFF;
            
            updateWebSetting(inParams[0].getTerminalNo(), ps[0].getString(FUNCTION_ID), value);
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
                // No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParams[e.getErrorLineNo() - 1].getRowNo()));
                setErrorRowIndex(inParams[e.getErrorLineNo() - 1].getRowNo());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }

    /**
     * 画面から入力された内容とリストセルエリアのデータをパラメータとして受け取り、
     * チェックを行います。<BR>
     *
     * @param p 入力パラメータ
     * @param ps リストセルエリアのパラメータ
     * @return 入力チェック、オーバーフロー、重複、商品マスタ・入庫棚エラーでない場合はtrueを返す。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean check(ScheduleParams p, ScheduleParams... ps)
            throws CommonException
    {
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
        
        AsrsInParameter param = new AsrsInParameter(getWmsUserInfo());

        // 入力エリアのデータを編集
        // エリアNo.
        param.setAreaNo(p.getString(AREA_NO));
        // 作業場
        param.setWorkplace(p.getString(WORK_PLACE));
        // ステーションNo.
        param.setStationNo(p.getString(STATION_NO));
        // 作業No.
        param.setWorkNo(p.getString(WORK_NO));
        // ソフトゾーンID
        param.setSoftZoneNo(p.getString(SOFT_ZONE_ID));

        // リストセルのデータを編集
        AsrsInParameter[] lParams = new AsrsInParameter[ps.length];
        for (int i = 0; i < ps.length; i++)
        {
            lParams[i] = new AsrsInParameter(getWmsUserInfo());

            // 商品コード
            lParams[i].setWorkNo(ps[i].getString(WORK_NO));
            // ソフトゾーンID
            lParams[i].setSoftZoneNo(ps[i].getString(SOFT_ZONE_ID));
            // リストセル行No.
            lParams[i].setRowNo(ps[i].getRowIndex());
        }

        // データ存在チェック
        ReStoringPlan[] items = getItems(param.getWorkNo());
        if (items == null || items.length == 0)
        {
            // 6023298=再入庫予定データ内に該当する作業No.が存在しません。
            setMessage("6023298");
            return false;
        }
        
        AreaController areacon = new AreaController(getConnection(), this.getClass());
        
        // 最大混載数チェック
        if (items.length > areacon.getNumMixedOfWarehouse(param.getAreaNo()))
        {
            // 6023095=混載数が最大混載数を超えるため、設定できません。
            setMessage("6023095");
            return false;
        }

        //リストセルにデータが存在する場合のみチェック
        if (lParams != null && lParams.length != 0)
        {
            // 表示件数チェックを行う
            if (lParams.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
            {
                // 6023019 = 件数が{0}件を超えるため、入力できません。
                setMessage(WmsMessageFormatter.format(6023019, MAX_NUMBER_OF_DISP_DISP));
                return false;
            }

            // 作業No.重複チェックを行う
            for (AsrsInParameter lParam : lParams)
            {
                if (param.getWorkNo().equals(lParam.getWorkNo()))
                {
                    // 6023020 = 既に同一データが存在するため、入力できません。
                    setMessage("6023020");
                    return false;
                }
            }
        }
        
        // ソフトゾーンチェック
        if (!checkSoftZonePriority(items, areacon.getWhStationNo(param.getAreaNo()), param.getSoftZoneNo()))
        {
            // 6023320=共通ソフトゾーンが存在しないため、入力できません。
            setMessage("6023320");
            return false;
        }
        else if (!WmsParam.MIXED_SOFTZONE)
        {
            // ソフトゾーン違いの混載チェック
            if (!checkSoftZoneMixed(items))
            {
                // 6023321=ソフトゾーンの違う商品が混載されているため、入力できません。
                setMessage("6023321");
                return false;
            }
        }

        // 入庫作業可能判定
        if (!storageStationCheck(param.getStationNo()))
        {
            return false;
        }
        
        // 入庫ルートチェック
        String whSt = areacon.getWhStationNo(param.getAreaNo());
        if (!chkRoute(param, whSt))
        {
            return false;
        }
        
        // 設定数（ためうち入力数 + 入力データ）
        int input = 1;
        if (lParams != null)
        {
            // 設定数（ためうちデータ数をプラスする）
            if (!SystemDefine.SOFT_ZONE_ALL.equals(param.getSoftZoneNo()))
            {
                List<String> lstPri = getSoftZonePriority(param, whSt);
                for (int i = 0; i < lParams.length; i++)
                {
                    if (lstPri.contains(lParams[i].getSoftZoneNo()))
                    {
                        // ソフトゾーン優先を考慮し、入庫可能であれば+1する
                        input = input + 1;
                    }
                }
            }
            else
            {
                // フリーソフトゾーンの場合
                input = input + lParams.length;
            }
        }
        
        // 空棚数チェック
        if (!chkEmptyShelf(param, whSt, input))
        {
            // 6023112=入庫可能な空棚がありません。
            setMessage("6023112");
            return false;
        }
        
        // 6001019 = 入力を受け付けました。
        setMessage("6001019");
        
        return true;
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
     * 処理条件チェックを行います。<BR>
     * @return 処理条件正常の場合true、NGの場合falseを返します。<BR>
     * @param params 再入庫設定パラメータの配列
     * @param conn コネクション
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkparam(AsrsInParameter[] params, Connection conn)
            throws CommonException
    {
        AreaController areacon = new AreaController(conn, this.getClass());
        
        for (AsrsInParameter param : params)
        {
            ReStoringPlan[] items = getItems(param.getWorkNo());
            
            // データが存在チェック
            if (items == null || items.length == 0)
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, param.getRowNo()));
                setErrorRowIndex(param.getRowNo());
                return false;
            }
            
            // ソフトゾーンチェック
            if (params != null && params.length > 0 && WmsParam.SOFTZONE_SELECT_ITEM && WmsParam.MIXED_SOFTZONE)
            {
                if (!checkSoftZonePriority(items, areacon.getWhStationNo(params[0].getAreaNo()), param.getSoftZoneNo()))
                {
                    // 6023318=No.{0} 共通ソフトゾーンが存在しないため、設定できません。
                    setMessage(WmsMessageFormatter.format(6023318, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
            }
            else if (!WmsParam.MIXED_SOFTZONE)
            {
                // ソフトゾーン違いの混載チェック
                if (!checkSoftZoneMixed(items))
                {
                    // 6023319=No.{0} ソフトゾーンの違う商品が混載されているため、設定できません。
                    setMessage(WmsMessageFormatter.format(6023319, param.getRowNo()));
                    setErrorRowIndex(param.getRowNo());
                    return false;
                }
            }
        }
            
        //入庫作業可能判定
        if (!storageStationCheck(params[0].getStationNo()))
        {
            return false;
        }

        return true;
    }

    /**
     * 入庫ソフトゾーンと各商品のソフトゾーン優先情報のチェックを行います。
     * 
     * @param plans 予定情報の配列
     * @param whstation 倉庫ステーションNo.
     * @param softzone 入庫ソフトゾーン
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkSoftZonePriority(ReStoringPlan[] plans, String whstation, String softzone)
            throws CommonException
    {
        if (SoftZone.SOFT_ZONE_ALL.equals(softzone))
        {
            // フリーソフトゾーンの場合、チェックなし
            return true;
        }
        
        SoftZonePriorityHandler softhandler = new SoftZonePriorityHandler(getConnection());
        SoftZonePrioritySearchKey softkey = new SoftZonePrioritySearchKey();
        ItemSearchKey itemkey = new ItemSearchKey();

        // 1件目商品の入庫可能ソフトゾーンを取得
        List<String> sz_lst = new ArrayList<String>();
        if (isFreeSoftZoneItem(plans[0]))
        {
            // 商品がフリーソフトゾーン商品の場合
            // 全ソフトゾーンを取得
            SoftZoneHandler szhandler = new SoftZoneHandler(getConnection());
            SoftZoneSearchKey szkey = new SoftZoneSearchKey();

            // フリーソフトゾーン以外
            szkey.setSoftZoneId(SoftZone.SOFT_ZONE_ALL, "!=");
            szkey.setSoftZoneIdCollect();
            szkey.setSoftZoneIdOrder(true);
            
            SoftZone[] soft = (SoftZone[])szhandler.find(szkey);
            
            for (SoftZone sz : soft)
            {
                sz_lst.add(sz.getSoftZoneId());
            }
        }
        else
        {
            // 商品がフリーソフトゾーン商品でない場合
            softkey.setPrioritySoftZoneCollect();
    
            itemkey.setItemCode(plans[0].getItemCode());
            itemkey.setSoftZoneIdCollect("DISTINCT");
            softkey.setKey(SoftZonePriority.SOFT_ZONE_ID, itemkey);
            softkey.setWhStationNo(whstation);
            softkey.setPrioritySoftZoneOrder(true);
    
            SoftZonePriority[] softzone_priority = (SoftZonePriority[])softhandler.find(softkey);
    
            for (SoftZonePriority sz : softzone_priority)
            {
                sz_lst.add(sz.getPrioritySoftZone());
            }
        }

        boolean find_flg;
        for (int i = sz_lst.size() - 1; i >= 0; i--)
        {
            // 2件目以降の商品とソフトゾーンをチェック
            for (int j = 1; j < plans.length; j++)
            {
                if (isFreeSoftZoneItem(plans[j]))
                {
                    // フリーソフトゾーン商品の場合はチェックしない
                    continue;
                }
                
                softkey.clear();
                itemkey.clear();

                softkey.setPrioritySoftZoneCollect();

                itemkey.setItemCode(plans[j].getItemCode());
                itemkey.setSoftZoneIdCollect("DISTINCT");
                softkey.setKey(SoftZonePriority.SOFT_ZONE_ID, itemkey);
                softkey.setWhStationNo(whstation);
                softkey.setPrioritySoftZoneOrder(true);

                SoftZonePriority[] chk_softzone_priority = (SoftZonePriority[])softhandler.find(softkey);

                find_flg = false;
                for (SoftZonePriority chk_sz : chk_softzone_priority)
                {
                    if (sz_lst.get(i).equals(chk_sz.getPrioritySoftZone()))
                    {
                        find_flg = true;
                        break;
                    }
                }

                if (!find_flg)
                {
                    // 見つからない場合、削除
                    sz_lst.remove(i);
                    break;
                }
            }
        }

        // 入庫ソフトゾーン優先の取得
        softkey.clear();
        softkey.setPrioritySoftZoneCollect();
        softkey.setSoftZoneId(softzone);
        softkey.setWhStationNo(whstation);
        softkey.setPrioritySoftZoneOrder(true);
        
        SoftZonePriority[] storage_sz_priority = (SoftZonePriority[])softhandler.find(softkey);
        
        for (SoftZonePriority sz : storage_sz_priority)
        {
            if (sz_lst.contains(sz.getPrioritySoftZone()))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 商品がフリーソフトゾーン商品かどうかを返します。
     * 
     * @param plan 予定情報
     * @return フリーソフトゾーン商品の場合、true
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean isFreeSoftZoneItem(ReStoringPlan plan)
            throws CommonException
    {
        ItemHandler ihandler = new ItemHandler(getConnection());
        ItemSearchKey ikey = new ItemSearchKey();
        
        ikey.setConsignorCode(plan.getConsignorCode());
        ikey.setItemCode(plan.getItemCode());
        ikey.setSoftZoneIdCollect();
        
        Item item = (Item)ihandler.findPrimary(ikey);
        
        if (item != null && Item.SOFT_ZONE_ALL.equals(item.getSoftZoneId()))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 混載される商品がソフトゾーン違いでないかをチェックします。
     * 
     * @param plans 予定情報の配列
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkSoftZoneMixed(ReStoringPlan[] plans)
            throws CommonException
    {
        // 1件目のデータを元に比較
        String chk_sz = getSoftZoneId(plans[0].getConsignorCode(), plans[0].getItemCode());
        for (int i = 1; i < plans.length; i++)
        {
            String sz = getSoftZoneId(plans[i].getConsignorCode(), plans[i].getItemCode());
            
            if (!chk_sz.equals(sz))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 指定された荷主コード、商品コードからソフトゾーンを取得し、返します。
     * 
     * @param consignor 荷主コード
     * @param item 商品コード
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected String getSoftZoneId(String consignor, String item)
            throws CommonException
    {
        ItemHandler ihandler = new ItemHandler(getConnection());
        ItemSearchKey ikey = new ItemSearchKey();
        
        // 入力データのソフトゾーンを取得
        ikey.setSoftZoneIdCollect();
        ikey.setConsignorCode(consignor);
        ikey.setItemCode(item);
        
        Item sz = (Item)ihandler.findPrimary(ikey);
        
        return sz.getSoftZoneId();
    }
    
    /**
     * 引数からソフトゾーン優先順を取得し返します。
     * 
     * @param param 入力データ
     * @param whstation 倉庫ステーション
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected List<String> getSoftZonePriority(AsrsInParameter param, String whstation)
            throws CommonException
    {
        List<String> lstPriority = new ArrayList<String>();
        SoftZonePriorityHandler softhandler = new SoftZonePriorityHandler(getConnection());
        SoftZonePrioritySearchKey softkey = new SoftZonePrioritySearchKey();
        
        softkey.setPrioritySoftZoneCollect();
        softkey.setSoftZoneId(param.getSoftZoneNo());
        softkey.setWhStationNo(whstation);
        softkey.setPrioritySoftZoneOrder(true);
        
        SoftZonePriority[] priority = (SoftZonePriority[])softhandler.find(softkey);
        
        for (SoftZonePriority sz : priority)
        {
            lstPriority.add(sz.getPrioritySoftZone());
        }
        
        return lstPriority;
    }
    
    /**
     * 引数の作業No.に含まれる商品情報を返します。<br>
     * <ol>返却する<code>ReStoringPlan</code>には以下の情報を格納します。
     * <li>荷主コード
     * <li>商品コード
     * <li>予定ロットNo.
     * </ol>
     * 
     * @param workNo 作業No.
     * @return 商品情報を格納したString配列
     * @throws ReadWriteException
     */
    protected ReStoringPlan[] getItems(String workNo)
            throws ReadWriteException
    {
        // ハンドラインスタンス生成
        ReStoringPlanHandler handler = new ReStoringPlanHandler(getConnection());
        ReStoringPlanSearchKey key = new ReStoringPlanSearchKey();
        
        // 作業No.
        key.setWorkNo(workNo);
        
        // 未作業
        key.setStatusFlag(ReStoringPlan.STATUS_FLAG_UNSTART);
        
        // 取得項目
        key.setConsignorCodeCollect();
        key.setItemCodeCollect();
        key.setPlanLotNoCollect();
        
        // ソート順
        key.setConsignorCodeOrder(true);
        key.setItemCodeOrder(true);
        key.setPlanLotNoOrder(true);
        
        // 検索処理実行
        ReStoringPlan[] plans = (ReStoringPlan[])handler.find(key);
        
        return plans;
    }
    
    /**
     * ルートチェック
     * 
     * @param param 入力データ
     * @param whst 倉庫ステーションNo.
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean chkRoute(AsrsInParameter param, String whst)
            throws CommonException
    {
        RouteController routeCon = new RouteController(getConnection());
        
        // 倉庫情報の取得
        WareHouseSearchKey whKey = new WareHouseSearchKey();
        whKey.setStationNo(whst);
        WareHouse wareHouse = (WareHouse)new WareHouseHandler(getConnection()).findPrimary(whKey);
        
        // 入庫ルートをチェックするため、ダミーのパレットを作成
        Pallet plt = new Pallet();
        
        plt.setCurrentStationNo(param.getStationNo());

        plt.setWhStationNo(whst); // 倉庫ステーション
        plt.setStatusFlag(Pallet.PALLET_STATUS_STORAGE_PLAN); // 在庫状態
        plt.setAllocationFlag(Pallet.ALLOCATION_FLAG_ALLOCATED); // 引当状態
        plt.setEmptyFlag(Pallet.EMPTY_FLAG_NORMAL); // 空パレット状態

        plt.setHeight(Pallet.HEIGHT_FREE); // 荷高
        plt.setWidth(Pallet.WIDTH_FREE); // 荷幅
        plt.setBcrData(""); // バーコード情報
        plt.setSoftZoneId(param.getSoftZoneNo()); // ソフトゾーン

        boolean ret = routeCon.storageDeterminSCH(plt, wareHouse, false);
        doRollBack(getClass()); // チェックのみのため、必ずロールバック
        
        if (!ret)
        {
            // メッセージセット
            setMessage(getRouteErrorMessage(routeCon.getRouteStatus()));
        }
        
        return ret;
    }
    
    /**
     * 空棚数チェック
     * 
     * @param param 入力データ
     * @param whstation 倉庫ステーション
     * @param input 入力データ数
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean chkEmptyShelf(AsrsInParameter param, String whstation, int input)
            throws CommonException
    {
        ShelfSearchKey searchKey = new ShelfSearchKey();
        boolean isDoubleDeep = false;
        Station st = null;
        
        try
        {
            DfkUserInfo ui = getUserInfo();
            String term = (null == ui) ? ""
                                      : ui.getTerminalNumber();

            // 副問い合わせ用TerminalAreaの検索条件のセットをする。
            TerminalAreaSearchKey tKey = new TerminalAreaSearchKey();
            tKey.setStationNoCollect();
            tKey.setTerminalNo(term);

            st = StationFactory.makeStation(getConnection(), param.getStationNo());
            
            // 作業場の場合
            if (st instanceof WorkPlace)
            {
                WorkPlace wp = (WorkPlace)st;
                String[] stations = wp.getWPStations();
                StationSearchKey stkey = new StationSearchKey();
                
                // 作業場にあるステーションをセット
                stkey.setKey(Station.STATION_NO, stations, true);
                
                // ステーション種別は、入庫可能ステーション
                stkey.setStationType(Station.STATION_TYPE_IN, "=", "(", "", false);
                stkey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);

                // 副問い合わせ用Stationの検索条件をセットする。
                stkey.setKey(Station.STATION_NO, tKey);

                if(hasAisleCombined(stations))
                {
                    // アイル結合がある場合
                    stkey.setWhStationNoCollect();
                    searchKey.setKey(Shelf.WH_STATION_NO, stkey);
                }
                else
                {
                    // アイル独立しかない場合
                    stkey.setAisleStationNoCollect();
                    searchKey.setKey(Shelf.PARENT_STATION_NO, stkey);
                }
                
                for (String station : stations)
                {
                    isDoubleDeep = chkDoubleDeep(StationFactory.makeStation(getConnection(), station));
                    if (isDoubleDeep)
                    {
                        break;
                    }
                }
            }
            // ステーションの場合
            else
            {
                searchKey.setKey(Station.STATION_NO, param.getStationNo());
                if(StringUtil.isBlank(st.getAisleStationNo()))
                {
                    // アイル結合の場合
                    searchKey.setJoin(Shelf.WH_STATION_NO, Station.WH_STATION_NO);
                }
                else
                {
                    // アイル独立の場合
                    searchKey.setJoin(Shelf.PARENT_STATION_NO, Station.AISLE_STATION_NO);
                }
                isDoubleDeep = chkDoubleDeep(st);
            }
        }
        catch (NotFoundException e)
        {
            // ステーションが見つからなかった場合
            throw new InvalidDefineException();
        }
        
        // ソフトゾーン
        if (!SystemDefine.SOFT_ZONE_ALL.equals(param.getSoftZoneNo()))
        {
            SoftZonePrioritySearchKey softkey = new SoftZonePrioritySearchKey();
            
            softkey.setPrioritySoftZoneCollect();
            softkey.setSoftZoneId(param.getSoftZoneNo());
            softkey.setWhStationNo(whstation);
            
            searchKey.setKey(Shelf.SOFT_ZONE_ID, softkey);
        }
        
        // 棚状態が空棚かつ使用不可以外、アクセス不可以外
        searchKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        searchKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_NG, "!=");
        searchKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_NG, "!=");
        
        int cnt = (new ShelfHandler(getConnection())).count(searchKey);
        
        // 再入庫が予定される搬送データ数を減算
        cnt = cnt - getCarryCount(st);

        if (isDoubleDeep)
        {
            // ダブルディープ時は2棚減算
            cnt = cnt - 2;
        }
        
        if (cnt < input)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 再入庫予定される搬送データ数を取得します。
     * 
     * @param param 入力データ
     * @param st ステーション
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected int getCarryCount(Station st)
            throws CommonException
    {
        int cnt = 0;
        
        boolean isWorkPlace = (st instanceof WorkPlace) ? true
                                                   : false;
        
        CarryInfoHandler chandler = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey ckey = new CarryInfoSearchKey();
        
        // 搬送区分
        ckey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
        // 搬送状態
        ckey.setCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL);
        // 出庫指示詳細
        ckey.setRetrievalDetail(new String[] {
            CarryInfo.RETRIEVAL_DETAIL_ADD_STORING,
            CarryInfo.RETRIEVAL_DETAIL_PICKING
        }, true);
        // 再入庫区分
        ckey.setRestoringFlag(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC);
        
        if (isWorkPlace)
        {
            ckey.setKey(Shelf.WH_STATION_NO, st.getWhStationNo());
            ckey.setJoin(CarryInfo.RETRIEVAL_STATION_NO, Shelf.STATION_NO);
        }
        else
        {
            if (StringUtil.isBlank(st.getAisleStationNo()))
            {
                // アイル結合時
                ckey.setKey(Shelf.WH_STATION_NO, st.getWhStationNo());
                ckey.setJoin(CarryInfo.RETRIEVAL_STATION_NO, Shelf.STATION_NO);
            }
            else
            {
                // アイル独立時
                StationSearchKey stkey = new StationSearchKey();
                stkey.setAisleStationNo(st.getAisleStationNo());
                stkey.setStationNoCollect();
                
                ckey.setKey(CarryInfo.DEST_STATION_NO, stkey);
            }
        }

        // 加算
        cnt = cnt + chandler.count(ckey);
        
        
        ckey.clear();
        
        // 搬送区分
        ckey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE);
        // 搬送状態
        ckey.setCmdStatus(new String[] {
            CarryInfo.CMD_STATUS_ARRIVAL,
            CarryInfo.CMD_STATUS_START
        }, true);
        // 出庫指示詳細
        ckey.setRetrievalDetail(new String[] {
            CarryInfo.RETRIEVAL_DETAIL_ADD_STORING,
            CarryInfo.RETRIEVAL_DETAIL_PICKING
        }, true);
        // 再入庫区分
        ckey.setRestoringFlag(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC);
        
        if (isWorkPlace)
        {
            ckey.setKey(Shelf.WH_STATION_NO, st.getWhStationNo());
            ckey.setJoin(CarryInfo.RETRIEVAL_STATION_NO, Shelf.STATION_NO);
        }
        else
        {
            if (StringUtil.isBlank(st.getAisleStationNo()))
            {
                // アイル結合時
                ckey.setKey(Shelf.WH_STATION_NO, st.getWhStationNo());
                ckey.setJoin(CarryInfo.RETRIEVAL_STATION_NO, Shelf.STATION_NO);
            }
            else
            {
                // アイル独立時
                StationSearchKey stkey = new StationSearchKey();
                stkey.setAisleStationNo(st.getAisleStationNo());
                stkey.setStationNoCollect();
                
                ckey.setKey(CarryInfo.SOURCE_STATION_NO, stkey);
            }
        }

        // 加算
        cnt = cnt + chandler.count(ckey);
        
        
        ckey.clear();
        // 搬送区分
        ckey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE);
        // 出庫指示詳細
        ckey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_UNKNOWN);
        
        if (isWorkPlace)
        {
            ckey.setDestStationNo(st.getWhStationNo());
        }
        else
        {
            if (StringUtil.isBlank(st.getAisleStationNo()))
            {
                // アイル結合時
                ckey.setDestStationNo(st.getWhStationNo());
            }
            else
            {
                // アイル独立時
                ckey.setAisleStationNo(st.getAisleStationNo());
            }
        }

        // 加算
        cnt = cnt + chandler.count(ckey);
        
        return cnt;
    }
    
    /**
     * 指定されたステーションに結合倉庫が存在するかを返します。
     * 
     * @param wp ステーション
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected boolean hasAisleCombined(String[] stations) 
            throws ReadWriteException 
    {
        StationHandler sthandler = new StationHandler(getConnection());
        StationSearchKey stkey = new StationSearchKey();
        
        stkey.setStationNo(stations, true);
        stkey.setAisleStationNo("");
        
        if (sthandler.count(stkey) > 0)
        {
            return true;
        }
        return false;
    }
    
    /**
     * 指定されたステーションにダブルディープが存在するかを返します。
     * 
     * @param station ステーション
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected boolean chkDoubleDeep(Station station) 
            throws ReadWriteException 
    {
        AisleHandler aislehandler = new AisleHandler(getConnection());
        AisleSearchKey aislekey = new AisleSearchKey();
        
        if (!StringUtil.isBlank(station.getAisleStationNo()))
        {
            aislekey.setStationNo(station.getAisleStationNo());
        }
        else
        {
            aislekey.setWhStationNo(station.getWhStationNo());
        }
        
        aislekey.setDoubleDeepKindCollect();
        aislekey.setDoubleDeepKindGroup();
        
        Aisle[] aisles = (Aisle[])aislehandler.find(aislekey);
        for (Aisle aisle : aisles)
        {
            if (Aisle.DOUBLE_DEEP_KIND_DOUBLE.equals(aisle.getDoubleDeepKind()))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 画面定義情報を更新します。<br>
     * 更新処理で異常が発生した場合は、Exceptionをスローせず、ロギングのみ行います。
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
