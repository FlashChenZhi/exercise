// $Id: OutputFileNameDefine.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * Designer : M.Sakashita <BR>
 * Maker : M.Sakashita <BR>
 * <BR>
 * XLS,CSVファイル出力時のファイル名を定義するクラスです。<BR>
 * 
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  073064
 * @author  Last commit: $Author: arai $
 */


public final class OutputFileNameDefine
        extends Object
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 入荷用
     */
    public interface Receive
    {
        /** 入荷作業リスト **/
        public static final String RECEIVESTART = "ReceiveStart";

        /** 入荷作業照会リスト **/
        public static final String RECEIVEWORKINQUIRY = "ReceiveWorkInquiry";

        /** 入荷予定照会リスト **/
        public static final String RECEIVEPLANINQUIRY = "ReceivePlanInquiry";

        /** 入荷実績照会リスト **/
        public static final String RECEIVERESULTINQUIRY = "ReceiveResultInquiry";

        /** 入荷予定データ削除リスト **/
        public static final String RECEIVEPLANDELETE = "ReceivePlanDelete";

    }

    /**
     * 入庫用
     */
    public interface Storage
    {
        /** 入庫作業リスト **/
        public static final String STORAGESTART = "StorageStart";

        /** 入庫作業照会リスト **/
        public static final String STORAGEWORKINQUIRY = "StorageWorkInquiry";

        /** 入庫予定照会リスト **/
        public static final String STORAGEPLANINQUIRY = "StoragePlanInquiry";

        /** 入庫実績照会リスト **/
        public static final String STORAGERESULTINQUIRY = "StorageResultInquiry";

        /** 入庫予定データ削除リスト **/
        public static final String STORAGEPLAMDELETE = "StoragePlanDelete";

    }

    /**
     * 出庫用
     */
    public interface Retrieval
    {
        /** オーダーピッキングリスト **/
        public static final String RETRIEVALSTART = "RetrievalStart";

        /** 出庫作業照会リスト **/
        public static final String RETRIEVALWORKINQUIRY = "RetrievalWorkInquiry";

        /** 出庫予定照会リスト **/
        public static final String RETRIEVALPLANINQUIRY = "RetrievalPlanInquiry";

        /** 出庫実績照会リスト **/
        public static final String RETRIEVALRESULTINQUIRY = "RetrievalResultInquiry";

        /** 出庫予定データ削除リスト **/
        public static final String RETRIEVALPLANDELETE = "RetrievalPlanDelete";

        /** 欠品チェックリスト **/
        public static final String SHORTAGECHECK = "RetrievalShortageInquiry";

    }

    /**
     * 仕分用
     */
    public interface Sort
    {
        /** 仕分作業リスト **/
        public static final String SORTSTART = "SortStart";

        /** 仕分作業照会リスト **/
        public static final String SORTWORKINQUIRY = "SortWorkInquiry";

        /** 仕分予定照会リスト **/
        public static final String SORTPLANINQUIRY = "SortPlanInquiry";

        /** 仕分実績照会リスト **/
        public static final String SORTRESULTINQUIRY = "SortResultInquiry";

        /** 仕分予定データ削除リスト **/
        public static final String SORTPLANDELETE = "SortPlanDelete";
    }

    /**
     * 出荷用
     */
    public interface Ship
    {
        /** 出荷作業リスト **/
        public static final String SHIPSTART = "ShipStart";

        /** 出荷作業照会リスト **/
        public static final String SHIPWORKINQUIRY = "ShipWorkInquiry";

        /** 出荷予定照会リスト **/
        public static final String SHIPPLANINQUIRY = "ShipPlanInquiry";

        /** 出荷実績照会リスト **/
        public static final String SHIPRESULTINQUIRY = "ShipResultInquiry";

        /** 出荷予定データ削除リスト **/
        public static final String SHIPPLANDELETE = "ShipPlanDelete";

        /** 出荷実績(バース登録)照会リスト **/
        public static final String SHIPRESULTBERTH = "ShipResultBerth";
    }

    /**
     * 在庫用
     */
    public interface Stock
    {
        /** 商品別在庫リスト **/
        public static final String ITEMWORKINGINQUIRY = "ItemWorkingInquiry";

        /** 棚別在庫リスト **/
        public static final String LOCATIONWORKINGINQUIRY = "LocationWorkingInquiry";

        /** 長期滞留品リスト **/
        public static final String DEADSTOCKINQUIRY = "DeadStockInquiry";

        /** 在庫移動作業リスト **/
        public static final String STOCKMOVEWORK = "StockMoveWork";

        /** 在庫移動作業照会リスト **/
        public static final String STOCKMOVEWORKINGINQUIRY = "StockMoveWorkingInquiry";

        /** 在庫移動実績照会リスト **/
        public static final String STOCKMOVERESULTINQUIRY = "StockMoveResultInquiry";

        /** 棚卸作業リスト **/
        public static final String INVENTORYCHECK = "InventoryCheck";

        /** 棚卸差異リスト **/
        public static final String INVENTORYDIFF = "InventoryDiff";

        /** 予定外入庫作業リスト **/
        public static final String NOPLANSTORAGE = "NoPlanStorage";

        /** 予定外出庫作業リスト **/
        public static final String NOPLANRETRIEVAL = "NoPlanRetrieval";

        /** 予定外入出庫実績照会リスト **/
        public static final String NOPLANRESULTINQUIRY = "NoPlanResultInquiry";

    }

    /**
     * AS/RS用
     */
    public interface Asrs
    {
        /** AS/RS入庫作業リスト **/
        public static final String ASRSSTORAGE = "AsStorageList";

        /** AS/RSオーダーピッキングリスト **/
        public static final String ASRSRETRIEVAL = "AsRetrievalList";

        //
        //        /** AS/RS予定外入庫作業リスト **/
        //        public static final String ASRSNOPLANSTORAGE = "AsNoPlanStorageList";
        //
        //        /** AS/RS予定外出庫作業リスト **/
        //        public static final String ASRSNOPLANRETRIEVAL = "AsrsNoPlanRetrievalList";

        /** AS/RS作業メンテナンスリスト **/
        public static final String ASRSWORKMAINTENANCE = "AsrsWorkMaintenance";

        /** AS/RS在庫確認作業リスト **/
        public static final String ASRSINVENTORYCHECK = "AsrsInventoryCheck";

        /** AS/RS空棚リスト **/
        public static final String ASRETRIEVALLIST = "AsrsEmptyShelf";
    }

    /**
     * システム用
     */
    public interface System
    {
        /** 取込チェックリスト **/
        public static final String DATALOADCHECK = "DataLoadCheck";

        /** ユーザ別実績リスト **/
        public static final String WORKERRESULTINQUIRY = "WorkerResultInquiry";

        /** 入出庫実績リスト **/
        public static final String RESULTINQUIRY = "StockHistoryResultInquiry";

        /** メッセージログリスト **/
        public static final String MESSAGELOG = "MessageLog";

        /** 通信トレースログリスト **/
        public static final String COMMUNICATIONTRACE = "CommunicationTrace";
    }

    /**
     * マスタ用
     */
    public interface Master
    {
        /** 仕入先マスタリスト **/
        public static final String SUPPLIERMASTER = "SupplierMasterInquiry";

        /** 出荷先マスタリスト **/
        public static final String CUSTOMERMASTER = "CustomerMasterInquiry";

        /** 商品マスタリスト **/
        public static final String ITEMMASTER = "ItemMasterInquiry";

    }

    /**
     * 補充用
     */
    public interface Replenish
    {
        /** 補充作業リスト **/
        public static final String REPLENISHMENT = "Replenishment";

        /** AS/RS補充作業リスト **/
        public static final String ASRSREPLENISHMENT = "AsrsReplenishment";

        /** 補充メンテナンスリスト **/
        public static final String REPLENISHMENTMAINTENANCE = "ReplenishMaintenance";

    }

    /**
     * 分析用
     */
    public interface Analysis
    {
        /** 出荷実績ABC分析リスト */
        public static final String SHIPPINGRESULTABCANALYSIS = "ShippingResultABCAnalysis";

        /** 在庫推移リスト */
        public static final String INVENTORYMOVEMENT = "InventoryMovement";
    }

    /**
     * クロスドック用
     */
    public interface CrossDock
    {
        /** ＴＣ予定照会リスト **/
        public static final String XDPLANINQUIRY = "XdPlanInquiry";
    }

    /**
     * TC入荷用
     */
    public interface TCReceiving
    {
        /** 入荷作業照会リスト **/
        public static final String TCRECEIVINGWORKINQUIRY = "TcReceivingWorkInquiry";
        
        /** 入荷実績照会リスト **/
        public static final String TCRECEIVINGRESULTINQUIRY = "TcReceivingResultInquiry";
    }
    
    /**
     * Pカート出庫用
     */
    public interface PCartRetrieval
    {
        /** 作業者別一覧リスト */
        public static final String USERRESULTWORKER = "UserResultWorker";

        /** 作業日別一覧リスト */
        public static final String USERRESULTWORKDATE = "UserResultWorkDate";

        /** 荷主別一覧リスト */
        public static final String USERRESULTCONSIGNOR = "UserResultConsignor";

        /** エリア別一覧リスト */
        public static final String USERRESULTAREA = "UserResultArea";

        /** バッチ別一覧リスト */
        public static final String USERRESULTBATCH = "UserResultBatch";

        /** PCT出庫作業状況照会リスト **/
        public static final String PCTRETRIEVALWORKINQUIRY = "PCTRetrievalWorkInquiry";

        /** PCT出庫作業予定照会リスト **/
        public static final String PCTRETRIEVALPLANINQUIRY = "PCTRetrievalPlanInquiry";

        /** PCT出庫作業実績照会リスト **/
        public static final String PCTRETRIEVALRESULTINQUIRY = "PCTRetrievalResultInquiry";

        /** PCT出庫端末別作業照会リスト **/
        public static final String PCTRETRIEVALRFTNOINQUIRY = "PCTRetrievalRftNoInquiry";
    }
    
    /**
     * Pカートマスタ用
     */
    public interface PCartMaster
    {
        /** 作業者別一覧リスト */
        public static final String PCTWEIGHTDISTINCT = "PCTWeightDistinctionList";
        
        /** 商品マスタ一覧リスト */
        public static final String PCTITEMMASTER = "PCTItemInquiryList";
    }

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
     * 本クラスは定義情報を管理するクラスなのでインスタンスの生成は行えません。
     */
    private OutputFileNameDefine()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


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
        return "$Id: OutputFileNameDefine.java 3208 2009-03-02 05:42:52Z arai $";
    }
}
