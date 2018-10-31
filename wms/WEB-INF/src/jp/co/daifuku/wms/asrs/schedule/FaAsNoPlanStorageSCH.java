// $Id: FaAsNoPlanStorageSCH.java 7469 2010-03-08 09:22:45Z kishimoto $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.schedule.FaAsNoPlanStorageSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;

/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7469 $, $Date:: 2010-03-08 18:22:45 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaAsNoPlanStorageSCH
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
    public FaAsNoPlanStorageSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
            // 作業区分
            inParams[i].setJobType(ps[i].getString(JOB_TYPE));
            // 荷主コード
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            // 商品コード            
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            // ロットNo.
            inParams[i].setLotNo(ps[i].getString(LOT_NO));
            // 入庫数
            inParams[i].setStorageQty(ps[i].getInt(WORK_QTY));
            // リストセル行No.
            inParams[i].setRowNo(ps[i].getRowIndex());
            // ソフトゾーンID
            inParams[i].setSoftZoneNo(ps[i].getString(SOFT_ZONE_ID));
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
            AsrsOutParameter outparam = operator.webStartStorage(inParams);

            ps[0].set(SETTING_UNIT_KEY, outparam.getSettingUnitKey());
            
            // 作業日取得
            WarenaviSystemController syscon = new WarenaviSystemController(getConnection(), getClass());
            ps[0].set(PLAN_DAY, syscon.getWorkDay());

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
                // No. {0} 他端末で処理されたため、処理を中断しました。
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
        // 荷主コード
        param.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        param.setItemCode(p.getString(ITEM_CODE));
        // ロットNo.
        param.setLotNo(p.getString(LOT_NO));
        // 入庫数
        param.setStorageQty(p.getInt(WORK_QTY));
        // 入力商品のソフトゾーンを取得
        String softzone = getSoftZoneId(param.getConsignorCode(), param.getItemCode());

        // リストセルのデータを編集
        AsrsInParameter[] lParams = new AsrsInParameter[ps.length];
        for (int i = 0; i < ps.length; i++)
        {
            lParams[i] = new AsrsInParameter(getWmsUserInfo());

            // 荷主コード
            lParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            // 商品コード
            lParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            // ロットNo.
            lParams[i].setLotNo(ps[i].getString(LOT_NO));
            // リストセル行No.
            lParams[i].setRowNo(ps[i].getRowIndex());
        }
        
        //空パレットチェック
        if (WmsParam.EMPTYPB_ITEMCODE.equals(param.getItemCode()))
        {
            if (!checkCorrectEmptyPB(param.getItemCode(), param.getLotNo(), 0, 0, param.getStorageQty()))
            {
                return false;
            }
        }

        //異常棚の入力チェック
        if (!checkIrregularItem(param.getItemCode()))
        {
            return false;
        }

        //直行品番の入力チェック
        if (!checkSimpleDirectTransferItem(param.getItemCode()))
        {
            return false;
        }

        // 作業数のチェックを行う
        if (param.getStorageQty() > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023188=入庫数には作業上限数{0}以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023188, MAX_TOTAL_QTY_DISP));
            return false;
        }

        // 入庫数のチェックを行う
        if (param.getStorageQty() > WmsParam.MAX_STOCK_QTY)
        {
            // 6023217=入庫数には在庫上限数{0}以下の値を入力してしてください。
            setMessage(WmsMessageFormatter.format(6023217, MAX_STOCK_QTY_DISP));
            return false;
        }
        
        AreaController areacon = new AreaController(getConnection(), this.getClass());

        //リストセルにデータが存在する場合のみチェック
        if (lParams != null && lParams.length != 0)
        {
            if (!checkAddListEmptyPB(param.getItemCode(), lParams))
            {
                return false;
            }

            //最大混載数チェック
            if (lParams.length >= areacon.getNumMixedOfWarehouse(param.getAreaNo()))
            {
                // 6023095=混載数が最大混載数を超えるため、設定できません。
                setMessage("6023095");
                return false;
            }

            // 表示件数チェックを行う
            if (lParams.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
            {
                // 6023019 = 件数が{0}件を超えるため、入力できません。
                setMessage(WmsMessageFormatter.format(6023019, MAX_NUMBER_OF_DISP_DISP));
                return false;
            }

            // 商品重複チェックを行う
            for (AsrsInParameter lParam : lParams)
            {
                if (param.getConsignorCode().equals(lParam.getConsignorCode())
                        && param.getItemCode().equals(lParam.getItemCode())
                        && param.getLotNo().equals(lParam.getLotNo()))
                {
                    // 6023020 = 既に同一データが存在するため、入力できません。
                    setMessage("6023020");
                    return false;
                }
            }
            
            // ソフトゾーンチェック
            if (lParams != null && lParams.length > 0 && WmsParam.SOFTZONE_SELECT_ITEM && WmsParam.MIXED_SOFTZONE)
            {
                // 入力商品のソフトゾーンでチェック
                if (!checkSoftZonePriority(lParams, areacon.getWhStationNo(param.getAreaNo()), softzone))
                {
                    // 6023262=共通ソフトゾーンが存在しないため、混載できません。
                    setMessage("6023262");
                    return false;
                }
            }
            else if (!WmsParam.MIXED_SOFTZONE)
            {
                // ソフトゾーン違いの混載チェック
                if (!checkSoftZoneMixed(param, lParams))
                {
                    // 6023260=ソフトゾーンの違う商品は混載できません。
                    setMessage("6023260");
                    return false;
                }
            }
        }

        //入庫作業可能判定
        if (!storageStationCheck(param.getStationNo()))
        {
            return false;
        }
        
        // 入庫ルートチェック
        if (!chkRoute(param, areacon.getWhStationNo(param.getAreaNo()), softzone))
        {
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
     * @param params 予定外入庫設定パラメータの配列
     * @param conn コネクション
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkparam(AsrsInParameter[] params, Connection conn)
            throws CommonException
    {
        AreaController areacon = new AreaController(conn, this.getClass());

        // ソフトゾーンチェック
        if (params != null && params.length > 1 && WmsParam.SOFTZONE_SELECT_ITEM && WmsParam.MIXED_SOFTZONE)
        {
            if (!checkSoftZonePriority(params, areacon.getWhStationNo(params[0].getAreaNo()), params[0].getSoftZoneNo()))
            {
                // 6023262=共通ソフトゾーンが存在しないため、混載できません。
                setMessage("6023262");
                return false;
            }
        }
        else if (!WmsParam.MIXED_SOFTZONE)
        {
            // ソフトゾーン違いの混載チェック
            if (!checkSoftZoneMixed(params))
            {
                // 6023260=ソフトゾーンの違う商品は混載できません。
                setMessage("6023260");
                return false;
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
     * @param params 予定外入庫設定パラメータの配列
     * @param whstation 倉庫ステーションNo.
     * @param softzone 入庫ソフトゾーン
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkSoftZonePriority(AsrsInParameter[] params , String whstation, String softzone)
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
        if (isFreeSoftZoneItem(params[0]))
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
    
            itemkey.setItemCode(params[0].getItemCode());
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
            for (int j = 1; j < params.length; j++)
            {
                if (isFreeSoftZoneItem(params[j]))
                {
                    // フリーソフトゾーン商品の場合はチェックしない
                    continue;
                }
                
                softkey.clear();
                itemkey.clear();

                softkey.setPrioritySoftZoneCollect();

                itemkey.setItemCode(params[j].getItemCode());
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
     * 入力商品がフリーソフトゾーン商品かどうかを返します。
     * 
     * @param item ためうち商品
     * @return フリーソフトゾーン商品の場合、true
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean isFreeSoftZoneItem(AsrsInParameter param)
            throws CommonException
    {
        ItemHandler ihandler = new ItemHandler(getConnection());
        ItemSearchKey ikey = new ItemSearchKey();
        
        ikey.setConsignorCode(param.getConsignorCode());
        ikey.setItemCode(param.getItemCode());
        ikey.setSoftZoneIdCollect();
        
        Item item = (Item)ihandler.findPrimary(ikey);
        
        if (item != null && Item.SOFT_ZONE_ALL.equals(item.getSoftZoneId()))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 混載される商品がソフトゾーン違いでないかをチェックします。(入力用)
     * 
     * @param input 入力データ
     * @param params リストセルエリアのパラメータ
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkSoftZoneMixed(AsrsInParameter input, AsrsInParameter[] params)
            throws CommonException
    {
        // 入力データ
        String input_sz = getSoftZoneId(input.getConsignorCode(), input.getItemCode());
        // リストセルデータ（1件目のみ）
        String chk_sz = getSoftZoneId(params[0].getConsignorCode(), params[0].getItemCode());
        
        if (input_sz.equals(chk_sz))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 混載される商品がソフトゾーン違いでないかをチェックします。(設定用)
     * 
     * @param params リストセルエリアのパラメータ
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkSoftZoneMixed(AsrsInParameter[] params)
            throws CommonException
    {
        // 1件目のデータを元に比較
        String chk_sz = getSoftZoneId(params[0].getConsignorCode(), params[0].getItemCode());
        for (int i = 1; i < params.length; i++)
        {
            String sz = getSoftZoneId(params[i].getConsignorCode(), params[i].getItemCode());
            
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
     * ルートチェック
     * 
     * @param param 入力データ
     * @param whst 倉庫ステーションNo.
     * @param softzone 入庫ゾーン
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean chkRoute(AsrsInParameter param, String whst, String softzone)
            throws CommonException
    {
        String storage_zone = softzone;
        if (!WmsParam.SOFTZONE_SELECT_ITEM)
        {
            // 作業者が画面選択の場合、フリーソフトゾーンでルートチェック
            storage_zone = SoftZone.SOFT_ZONE_ALL;
        }
        
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
        plt.setSoftZoneId(storage_zone); // ソフトゾーン
        plt.setBcrData(""); // バーコード情報

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
