// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.schedule.FaWorkDisplaySCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.OperationDisplay;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 作業表示(FA)のスケジュール処理を行います。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaWorkDisplaySCH
        extends AbstractSCH
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
    public FaWorkDisplaySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * 出力用のデータをデータベースから取得して返します。<BR>
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
            List<Params> outParams = new ArrayList<Params>();
            WorkInfoFinder workInfoFinder = null;
            
            try
            {
                // 作業指示画面情報ハンドラ
                OperationDisplayHandler opDisHandler = new OperationDisplayHandler(getConnection());
                // 作業指示画面情報検索キー
                OperationDisplaySearchKey opDisShKy = new OperationDisplaySearchKey();

                // 検索条件をセット
                opDisShKy.setStationNo(p.getString(STATION_NO));
                // 取得順序をセット
                opDisShKy.setArrivalDateOrder(true);

                // 検索結果を取得
                OperationDisplay[] opDisplay = (OperationDisplay[])opDisHandler.find(opDisShKy);


                if (opDisplay.length == 0)
                {
                    return outParams;
                }

                Station st = StationFactory.makeStation(getConnection(), p.getString(STATION_NO));

                CarryInfo carryInfo = null;
                CarryInfoHandler cih = new CarryInfoHandler(getConnection());

                // 作業表示情報に紐付く搬送情報を検索します。
                // 紐付く搬送情報がない場合は、次の作業情報表示情報に紐付く搬送情報を検索します。
                for (int i = 0; i < opDisplay.length; i++)
                {
                    CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
                    ciKey.setCarryKey(opDisplay[i].getCarryKey());

                    // ステーションの作業モードが入庫の場合は、入庫搬送データを検索します
                    if (Station.CURRENT_MODE_STORAGE.equals(st.getCurrentMode()))
                    {
                        ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
                        ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                    }
                    // ステーションの作業モードが出庫の場合は
                    // モード切替種別が自動モード切替で、該当ステーションに出庫の搬送データがない場合は、
                    // 出庫搬送データも検索対象とします。
                    else if (Station.CURRENT_MODE_RETRIEVAL.equals(st.getCurrentMode()))
                    {
                        if (Station.MODE_TYPE_AUTO_CHANGE.equals(st.getModeType()))
                        {
                            CarryInfoSearchKey rtrvlCarryKey = new CarryInfoSearchKey();
                            rtrvlCarryKey.setDestStationNo(st.getStationNo());
                            rtrvlCarryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                            if (cih.count(rtrvlCarryKey) > 0)
                            {
                                ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL, "=", "(", "", false);
                                ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                            }
                        }
                        else
                        {
                            ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL, "=", "(", "", false);
                            ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                        }
                    }
                    // 出庫が終了した後のものは表示しない対応
                    // 搬送区分が入庫で、かつ出庫指示詳細が在庫確認・ピッキング出庫・積増入庫のものは除外
                    ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "NOT (", "", true);
                    ciKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
                    ciKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_PICKING, "=", "", "", false);
                    ciKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING, "=", "", "))", true);
                   
                    ciKey.setCollect(new FieldName(CarryInfo.STORE_NAME, FieldName.ALL_FIELDS));
                    
                    carryInfo = (CarryInfo)cih.findPrimary(ciKey);
                    // 該当搬送データが存在した場合、ここで検索処理を終了する
                    if (carryInfo != null)
                    {
                        break;
                    }
                }

                if (carryInfo == null)
                {
                    return outParams;
                }

                AreaController area = new AreaController(getConnection(), getClass());

                // 搬送区分から、返却用棚No.と搬送区分を決定します
                String locationNo = "";
                String carryFlag = "";
                // 棚No.、搬送元ステーションNo.、搬送先ステーションNo.、ステーションNo.を搬送区分から判断する
                if (CarryInfo.CARRY_FLAG_STORAGE.equals(carryInfo.getCarryFlag()))
                {
                    // 入庫時は、ステーションから棚への搬送
                    // 搬送先にはアイルをセットする
                    locationNo = carryInfo.getDestStationNo();
                    try
                    {
                        locationNo = area.getAreaNoOfWarehouse(locationNo);
                    }
                    catch (ScheduleException ex)
                    {
                        locationNo =
                                area.toDispLocation(String.valueOf(carryInfo.getValue(Stock.AREA_NO)),
                                        area.toParamLocation(locationNo));
                    }
                    carryFlag = CarryInfo.CARRY_FLAG_STORAGE;
                }
                else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(carryInfo.getCarryFlag()))
                {
                    // 出庫時は、棚からステーションへの搬送
                    // 搬送元にはアイルをセットする

                    carryFlag = CarryInfo.CARRY_FLAG_RETRIEVAL;
                    // 在庫確認の場合は出庫ロケーションNo.をセット
                    if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(carryInfo.getRetrievalDetail()))
                    {
                        locationNo = carryInfo.getRetrievalStationNo();
                    }
                    // 在庫確認以外の場合は搬送元ステーションをセット
                    else
                    {
                        locationNo = carryInfo.getSourceStationNo();
                    }

                    if (!StringUtil.isBlank(locationNo))
                    {
                        try
                        {
                            locationNo = area.getAreaNoOfWarehouse(locationNo);
                        }
                        catch (ScheduleException ex)
                        {
                            locationNo =
                                    area.toDispLocation(String.valueOf(carryInfo.getValue(Stock.AREA_NO)),
                                            area.toParamLocation(locationNo));
                        }
                    }
                    else
                    {
                        locationNo = "";
                    }
                }
                else if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(carryInfo.getCarryFlag()))
                {
                    // 棚間移動時は、棚から棚への移動
                    // 画面上、ロケーションNo.にはなにも表示しない
                    // 搬送元搬送先にはアイルをセットする
                    // ステーションはなにもセットしない
                    if (!StringUtil.isBlank(carryInfo.getRetrievalStationNo()))
                    {
                        locationNo = carryInfo.getRetrievalStationNo();
                        try
                        {
                            locationNo = area.getAreaNoOfWarehouse(locationNo);
                        }
                        catch (ScheduleException ex)
                        {
                            locationNo =
                                    area.toDispLocation(String.valueOf(carryInfo.getValue(Stock.AREA_NO)),
                                            area.toParamLocation(locationNo));
                        }
                    }
                    else
                    {
                        locationNo = "";
                    }
                }
                else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carryInfo.getCarryFlag()))
                {
                    // 直行時は、ステーションからステーションへの移動
                    // ロケーションNo.にはなにも表示しない
                    // 搬送元、搬送先には各ステーションNo.をセットする
                    // 画面上、ステーションNo.にはなにも表示しない
                    locationNo = "";
                    carryFlag = CarryInfo.CARRY_FLAG_DIRECT_TRAVEL;
                }
                else
                {
                    // それ以外
                    locationNo = "";
                }

                // 表示データを取得します。
                WorkInfoSearchKey workInfoShKy = new WorkInfoSearchKey();

                // 検索条件をセット
                workInfoShKy.setSystemConnKey(carryInfo.getCarryKey());
                // 結合条件をセット
                workInfoShKy.setJoin(WorkInfo.STOCK_ID, Stock.STOCK_ID);
                workInfoShKy.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
                workInfoShKy.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
                //取得順序をセット
                // 商品コード
                workInfoShKy.setItemCodeOrder(true);
                // ロットNo.            
                workInfoShKy.setPlanLotNoOrder(true);
                // オーダーNo.
                workInfoShKy.setOrderNoOrder(true);

                if (SystemDefine.JOB_TYPE_STORAGE.equals(carryInfo.getWorkType()))
                {
                    // 入荷伝票No.
                    workInfoShKy.setReceiveTicketNoOrder(true);
                    // 入荷行No.           
                    workInfoShKy.setReceiveLineNoOrder(true);
                    // 入荷作業枝番
                    workInfoShKy.setReceiveBranchNoOrder(true);
                }
                else if (SystemDefine.JOB_TYPE_RETRIEVAL.equals(carryInfo.getWorkType()))
                {
                    // 出荷伝票No.
                    workInfoShKy.setShipTicketNoOrder(true);
                    // 出荷行No.           
                    workInfoShKy.setShipLineNoOrder(true);
                    // 出荷作業枝番
                    workInfoShKy.setShipBranchNoOrder(true);
                }


                // 取得項目を指定
                workInfoShKy.setCollect(new FieldName(WorkInfo.STORE_NAME, FieldName.ALL_FIELDS));
                workInfoShKy.setCollect(Stock.LOT_NO);
                workInfoShKy.setCollect(Stock.STOCK_QTY);
                workInfoShKy.setCollect(Stock.STORAGE_DATE);
                workInfoShKy.setCollect(Item.ITEM_NAME);
                
                // 入出庫作業情報ファインダ
                workInfoFinder = new WorkInfoFinder(getConnection());
                workInfoFinder.open(false);
                // 検索結果を取得
                int size = workInfoFinder.search(workInfoShKy);
                
                if (size == 0)
                {
                    return outParams;
                }

                // 取得位置
                int current = 0;
                if (carryInfo.getWorkNo().equals(p.getString(WORK_NO)))
                {
                    // 作業No.が前回表示のものと同じ場合
                    current = p.getInt(CURRENT);
                }
                
                WorkInfo workInfo = (WorkInfo)workInfoFinder.getEntities(current, current + 1)[0];
                
                Params param = new Params();

                param.set(WORK_NO, carryInfo.getWorkNo());
                param.set(LOCATION_NO, area.toDispLocation(workInfo.getPlanAreaNo(),locationNo));
                param.set(CARRY_FLAG_NAME, DisplayResource.getCarryFlag(changeCarryFlag(carryFlag)));
                param.set(CARRY_FLAG, changeCarryFlag(carryFlag));
                param.set(RETRIEVAL_DETAIL, changeRetrievalDetail(carryInfo.getRetrievalDetail()));
                param.set(RETRIEVAL_DETAIL_NAME, DisplayResource.getFaRetrievalDetail(param.getString(RETRIEVAL_DETAIL)));
                param.set(ITEM_CODE, workInfo.getItemCode());
                param.set(ITEM_NAME, (String)workInfo.getValue(Item.ITEM_NAME));
                param.set(LOT_NO, (String)workInfo.getValue(Stock.LOT_NO));
                param.set(STOCK_QTY, workInfo.getValue(Stock.STOCK_QTY));
                param.set(WORK_QTY, workInfo.getPlanQty());
                param.set(STORAGE_DATE, WmsFormatter.toDispDate((Date)workInfo.getValue(Stock.STORAGE_DATE),getLocale()));
                param.set(STORAGE_TIME, WmsFormatter.toDispTime((Date)workInfo.getValue(Stock.STORAGE_DATE),getLocale()));
                param.set(BATCH_NO, workInfo.getBatchNo());
                // 入庫
                if (WorkInfo.WORK_TYPE_STORAGE.equals(workInfo.getJobType()))
                {
                    param.set(TICKET_NO, workInfo.getReceiveTicketNo());
                    param.set(LINE_NO, workInfo.getReceiveLineNo());
                }
                // 出庫
                else if (WorkInfo.JOB_TYPE_RETRIEVAL.equals(workInfo.getJobType()))
                {
                    param.set(TICKET_NO, workInfo.getShipTicketNo());
                    param.set(LINE_NO, workInfo.getShipLineNo());
                }

                param.set(STATION_NO, p.getString(STATION_NO));
                param.set(CARRY_KEY, carryInfo.getCarryKey());
                param.set(LAST_UPDATE_DATE, carryInfo.getLastUpdateDate());
                param.set(WAREHOUSE_NO, st.getWhStationNo());
                param.set(WORK_DISPLAY_OPERATE, changeOperationDisplay(st.getOperationDisplay()));
                param.set(WORK_TYPE, DisplayResource.getWorkType(changeWorkType(carryInfo.getWorkType())));
                param.set(SIZE, size);

                outParams.add(param);

                // 表示しました。
                setMessage("6001013");

                return outParams;
            }
            catch (NoPrimaryException ex)
            {
                // 6007002=データベースエラーが発生しました。メッセージログを参照してください。
                setMessage("6007002");
                throw ex;
            }
            finally
            {
            	closeFinder(workInfoFinder);
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
            if (!canStart())
            {
                return false;
            }

            // 搬送データクリアチェック
            if (isAllocationClear())
            {
                return false;
            }

            // 搬送情報ハンドラ
            CarryInfoHandler carryInfoHndl = new CarryInfoHandler(getConnection());
            // 搬送情報検索キー
            CarryInfoSearchKey carryInfoShKy = new CarryInfoSearchKey();

            // 検索条件をセット
            carryInfoShKy.setCarryKey(ps[0].getString(CARRY_KEY));

            // 検索結果を取得
            CarryInfo carryInfo = (CarryInfo)carryInfoHndl.findPrimary(carryInfoShKy);
            if (carryInfo == null)
            {
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
                return false;
            }
            else
            {

                // AS/RS作業指示画面情報ハンドラ
                OperationDisplayHandler operationDispHndl = new OperationDisplayHandler(getConnection());
                // 搬送情報検索キー
                OperationDisplaySearchKey operationDispShKy = new OperationDisplaySearchKey();

                if (operationDispHndl.count(operationDispShKy) < 1)
                {
                    // 6023115=他端末で処理されたため、処理を中断しました。
                    setMessage("6023115");
                    return false;
                }

            }

            // ステーション処理クラスを作成し、完了処理を行います。
            Station station = StationFactory.makeStation(getConnection(), ps[0].getString(STATION_NO));
            StationOperator stOperate =
                    StationOperatorFactory.makeOperator(getConnection(), station.getStationNo(), station.getClassName());
            try
            {
                stOperate.operationDisplayUpdate(carryInfo, this.getClass());
            }
            catch (NotFoundException e)
            {
                // 6023115=他端末で処理されたため、処理を中断しました。
                setMessage("6023115");
                return false;
            }

            // 6001014=完了しました。
            setMessage("6001014");
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
     * DB用搬送区分を画面用搬送区分に変換します。
     * @param carryFlag DB用搬送区分
     * @return 搬送区分
     */
    protected String changeCarryFlag(String carryFlag)
    {
        if (StringUtil.isBlank(carryFlag))
        {
            return "";
        }
        return carryFlag;
    }

    /**
     * DB作業表示運用を画面作業表示運用に変換します。
     * @param operationDisplay DB作業表示運用
     * @return 画面作業表示運用
     */
    protected String changeOperationDisplay(String operationDisplay)
    {
        // 作業指示、完了ボタンあり
        if (StringUtil.isBlank(operationDisplay))
        {
            return "";
        }
        return operationDisplay;

    }

    /**
     * DB作業種別を画面作業種別に変換します。
     * @param workType DB作業種別
     * @return 作業種別
     */
    protected String changeWorkType(String workType)
    {
        if (StringUtil.isBlank(workType))
        {
            return "";
        }
        return workType;
    }

    /**
     * DB出庫指示詳細を画面出庫指示詳細に変換します。
     * @param retrievalDetail DB出庫指示詳細
     * @return 出庫指示詳細
     */
    protected String changeRetrievalDetail(String retrievalDetail)
    {
        if (StringUtil.isBlank(retrievalDetail))
        {
            return "";
        }
        return retrievalDetail;

    }

    /**
     * DB作業区分を画面作業区分に変換します。
     * @param jobType 作業区分
     * @return 作業区分
     */
    protected String changeJobType(String jobType)
    {
        if (StringUtil.isBlank(jobType))
        {
            return "";
        }
        return jobType;

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
