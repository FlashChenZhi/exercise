package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsNoPlanStorageSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ReasonController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WebSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.WebSettingSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.base.entity.WebSetting;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 予定外入庫設定のスケジュール処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:17:02 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class AsNoPlanStorageSCH
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
    public AsNoPlanStorageSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * マスタパッケージが導入フラグを取得します。<BR>
     * @param p 検索条件をもつ<CODE>AsrsInParameter</CODE>クラスを継承したクラス
     * @return 検索結果が含まれた<CODE>AsrsOutParameter</CODE>クラスを実装したインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // システムコントローラよりマスタパッケージの有無を取得します。
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        Params outParam = new Params();
        outParam.set(MASTER_FLAG, systemController.hasMasterPack());
        outParam.set(NEED_PUL_CHANGE, WmsParam.SOFTZONE_SELECT_ITEM);
        
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
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new ItemFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
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

            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            inParams[i].setEnteringQty(ps[i].getInt(ENTERING_QTY));

            // 作業区分
            inParams[i].setJobType(ps[i].getString(JOB_TYPE));
            // 荷主コード
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            // 商品コード            
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            // 倉庫No.
            inParams[i].setWarehouseNo(ps[i].getString(WARE_HOUSE_NO));
            // エリアNo.
            inParams[i].setAreaNo(ps[i].getString(AREA_NO));
            // 作業場
            inParams[i].setWorkplace(ps[i].getString(WORK_PLACE));
            // ステーションNo.
            inParams[i].setStationNo(ps[i].getString(STATION_NO));
            // ゾーンNo.
            inParams[i].setZoneNo(ps[i].getString(ZONE_NO));
            // ロットNo.
            inParams[i].setLotNo(ps[i].getString(LOT_NO));
            // 帳票発行フラグ
            inParams[i].setPrintFlag(ps[i].getBoolean(PRINT_FLAG));
            // 商品名称
            inParams[i].setItemName(ps[i].getString(ITEM_NAME));
            // ケース入数
            inParams[i].setEnteringQty(ps[i].getInt(ENTERING_QTY));
            // 予定ケース数
            inParams[i].setStorageCaseQty(ps[i].getInt(PLAN_CASE_QTY));
            // 予定ピース数
            inParams[i].setStoragePieceQty(ps[i].getInt(PLAN_PIECE_QTY));
            // JANコード
            inParams[i].setJanCode(ps[i].getString(JAN));
            // ケースITF
            inParams[i].setItf(ps[i].getString(ITF));
            // 予定入庫数
            inParams[i].setStorageQty(inParams[i].getEnteringQty() * inParams[i].getStorageCaseQty()
                    + inParams[i].getStoragePieceQty());
            // リストセル行No.
            inParams[i].setRowNo(ps[i].getRowIndex());
            // 理由区分
            inParams[i].setReasonType(ps[i].getInt(REASON_TYPE));

            // 理由区分名称
            ReasonController reason = new ReasonController(getConnection(), this.getClass());
            inParams[i].setReasonName(reason.getReasonName(inParams[i].getReasonType()));
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

            ps[0].set(SETTING_UKEYS, outparam.getSettingUnitKey());

            // 設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
            
            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(PRINT_FLAG) ? WebSetting.KEYDATA_ON : WebSetting.KEYDATA_OFF;
            updateWebSetting(getUserInfo().getTerminalNumber(), ps[0].getString(FUNCTION_ID), value);
            
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
        AsrsInParameter param = new AsrsInParameter(getWmsUserInfo());

        // エリアNo.
        param.setAreaNo(p.getString(AREA_NO));
        // 荷主コード
        param.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        param.setItemCode(p.getString(ITEM_CODE));
        // ロットNo.
        param.setLotNo(p.getString(LOT_NO));
        // ケース入数
        param.setEnteringQty(p.getInt(ENTERING_QTY));
        // 予定ケース数
        param.setStorageCaseQty(p.getInt(PLAN_CASE_QTY));
        // 予定ピース数
        param.setStoragePieceQty(p.getInt(PLAN_PIECE_QTY));
        // 予定数
        param.setStorageQty(param.getEnteringQty() * param.getStorageCaseQty() + param.getStoragePieceQty());

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
            if (!checkCorrectEmptyPB(param.getItemCode(), param.getLotNo(), param.getEnteringQty(),
                    param.getStorageCaseQty(), param.getStoragePieceQty()))
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

        //ケース入数0の場合のチェック
        if (param.getEnteringQty() == 0 && param.getStorageCaseQty() > 0)
        {
            //6023036=ケース入数が0の場合、ケース数は入力できません。
            setMessage("6023036");
            return false;
        }

        // 入庫ケース数、入庫ピース数の入力チェックを行う
        if (param.getStorageQty() < 1)
        {
            //MSG="入庫ケース数または入庫ピース数には1以上の値を入力してください"
            setMessage("6023035");
            return false;
        }

        // 作業数のチェックを行う
        long input = (long)param.getStorageCaseQty() * (long)param.getEnteringQty() + (long)param.getStoragePieceQty();
        if (input > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023188=入庫数には作業上限数{0}以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023188, MAX_TOTAL_QTY_DISP));
            return false;
        }

        // 入庫数のチェックを行う
        if (input > WmsParam.MAX_STOCK_QTY)
        {
            // 6023217=入庫数には在庫上限数{0}以下の値を入力してしてください。
            setMessage(WmsMessageFormatter.format(6023217, MAX_STOCK_QTY_DISP));
            return false;
        }

        //リストセルにデータが存在する場合のみチェック
        if (lParams != null && lParams.length != 0)
        {
            if (!checkAddListEmptyPB(param.getItemCode(), lParams))
            {
                return false;
            }

            //最大混載数チェック
            Connection conn = getConnection();
            AreaController areacon = new AreaController(conn, this.getClass());
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
            
            if (!WmsParam.MIXED_SOFTZONE)
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
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        ItemSearchKey searchKey = new ItemSearchKey();

        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        searchKey.setItemCode(p.getString(ITEM_CODE));
        // ソフトゾーン
        searchKey.setSoftZoneIdCollect();
        searchKey.setCollect(new FieldName(Item.STORE_NAME, FieldName.ALL_FIELDS));
        searchKey.setJoin(Item.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");
        searchKey.setCollect(SoftZone.SOFT_ZONE_NAME);

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        Item[] entities = (Item[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (Item ent : entities)
        {
            Params param = new Params();

            // 該当データは一件のはずなので、要素0のみを返す。
            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, ent.getItemName());
            // ケース入数
            param.set(ENTERING_QTY, ent.getEnteringQty());
            // JANコード
            param.set(JAN, ent.getJan());
            // ケースITF
            param.set(ITF, ent.getItf());
            // ソフトゾーン名称
            param.set(SOFT_ZONE_NAME, ent.getValue(SoftZone.SOFT_ZONE_NAME, ""));

            result.add(param);
        }

        return result;
    }

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
        // 日次更新処理中のチェック
        if (isDailyUpdate())
        {
            return false;
        }

        //入庫作業可能判定
        if (!storageStationCheck(params[0].getStationNo()))
        {
            return false;
        }

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
        
        // 最大混載数チェック
        if (params != null && params.length > areacon.getNumMixedOfWarehouse(params[0].getAreaNo()))
        {
            // 6023072=No.{0} 最大混載数を超えるため、設定できません。
            setMessage(WmsMessageFormatter.format(6023072, params.length));
            return false;
        }

        return true;
    }

    /**
     * 混載される商品のソフトゾーンとソフトゾーン優先情報のチェックを行います。
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
     * 混載される商品がソフトゾーン違いでないかをチェックします。
     * 
     * @param input 入力データ
     * @param params リストセルエリアのパラメータ
     * @throws CommonException 何らかのエラーが発生した場合にthrowします。
     */
    protected boolean checkSoftZoneMixed(AsrsInParameter input, AsrsInParameter[] params)
            throws CommonException
    {
        ItemHandler ihandler = new ItemHandler(getConnection());
        ItemSearchKey ikey = new ItemSearchKey();
        
        // 入力データのソフトゾーンを取得
        ikey.setSoftZoneIdCollect();
        ikey.setConsignorCode(input.getConsignorCode());
        ikey.setItemCode(input.getItemCode());
        
        Item input_sz = (Item)ihandler.findPrimary(ikey);

        // リストセルエリアの1件目のみチェックする
        ikey.clear();
        ikey.setSoftZoneIdCollect();
        ikey.setConsignorCode(params[0].getConsignorCode());
        ikey.setItemCode(params[0].getItemCode());
        
        Item chk_sz = (Item)ihandler.findPrimary(ikey);
        
        if (input_sz.getSoftZoneId().equals(chk_sz.getSoftZoneId()))
        {
            return true;
        }
        return false;
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
