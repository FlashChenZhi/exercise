// $Id: DisplayResource.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.base.util;

import java.util.Locale;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInParameter;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;
import jp.co.daifuku.wms.system.schedule.SystemOutParameter;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 表示項目を作成するためのUtilクラスです。<BR>
 *
 * <BR>
 * Designer : Y.Okamura <BR>
 * Maker    : Y.Okamura <BR>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $a
 */
public final class DisplayResource
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------
    /**
     * コンストラクタ
     */
    private DisplayResource()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
    }

    /**
     * 作業テーブルの作業状態フラグから、名称を返します。<BR>
     * <CODE>SystemDefine.SEARCH_ALL</CODE> : 全て <BR>
     * <code>SystemDefine.STATUS_FLAG_UNSTART</code> : 未作業 <BR>
     * <code>SystemDefine.STATUS_FLAG_NOWWORKING</code> : 作業中 <BR>
     * <code>SystemDefine.STATUS_FLAG_COMPLETION</code> : 完了 <BR>
     * @param  status 作業状態フラグ
     * @return 作業状態フラグ名称
     */
    public static String getWorkingStatus(String status)
    {
        if (InParameter.SEARCH_ALL.equals(status))
        {
            // 全て
            return DisplayText.getText("CMB-W0004");
        }
        else if (SystemDefine.STATUS_FLAG_UNSTART.equals(status))
        {
            // 未作業
            return DisplayText.getText("CMB-W0005");
        }
        else if (SystemDefine.STATUS_FLAG_NOWWORKING.equals(status))
        {
            // 作業中
            return DisplayText.getText("CMB-W0006");
        }
        else if (SystemDefine.STATUS_FLAG_SHORTAGE_RESERVATION.equals(status))
        {
            // 欠品予約
            return DisplayText.getText("LBL-W0561");
        }
        else if (SystemDefine.STATUS_FLAG_COMPLETION.equals(status))
        {
            // 完了
            return DisplayText.getText("CMB-W0007");
        }
        else
        {
            return "";
        }
    }

    /**
     * 予定テーブルの作業状態フラグから、名称を返します。<BR>
     * <CODE>SystemDefine.SEARCH_ALL</CODE> : 全て <BR>
     * <code>SystemDefine.STATUS_FLAG_UNSTART</code> : 未作業 <BR>
     * <code>SystemDefine.STATUS_FLAG_NOWWORKING</code> : 作業中 <BR>
     * <code>SystemDefine.STATUS_FLAG_COMPLETION</code> : 完了 <BR>
     * @param  status 作業状態フラグ
     * @return 作業状態フラグ名称
     */
    public static String getPlanStatus(String status)
    {
        if (InParameter.SEARCH_ALL.equals(status))
        {
            // 全て
            return DisplayText.getText("CMB-W0004");
        }
        else if (SystemDefine.STATUS_FLAG_UNSTART.equals(status))
        {
            // 未作業
            return DisplayText.getText("CMB-W0005");
        }
        else if (SystemDefine.STATUS_FLAG_NOWWORKING.equals(status))
        {
            // 作業中
            return DisplayText.getText("CMB-W0006");
        }
        else if (SystemDefine.STATUS_FLAG_COMPLETION.equals(status))
        {
            // 完了
            return DisplayText.getText("CMB-W0007");
        }
        else
        {
            return "";
        }
    }

    /**
     * 在庫移動の作業状態フラグから、名称を返します。<BR>
     * <CODE>SystemDefine.SEARCH_ALL</CODE> : 全て <BR>
     * <code>StockSystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING</code> : 入庫待ち <BR>
     * <code>StockSystemDefine.STATUS_FLAG_MOVE_STORAGE_WORKING</code> : 入庫中 <BR>
     * @param  status 在庫移動情報の状態フラグ
     * @return 作業状態フラグ名称
     */
    public static String getMoveStatus(String status)
    {
        if (InParameter.SEARCH_ALL.equals(status))
        {
            // 全て
            return DisplayText.getText("CMB-W0004");
        }
        else if (SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING.equals(status))
        {
            // 入庫待ち
            return DisplayText.getText("RDB-W0010");
        }
        else if (SystemDefine.STATUS_FLAG_MOVE_STORAGE_WORKING.equals(status))
        {
            // 入庫中
            return DisplayText.getText("RDB-W0011");
        }
        else
        {
            return "";
        }
    }

    /**
     * 棚卸作業情報の状態フラグから、名称を返します。<BR>
     * <CODE>InventorySystemDefine.STATUS_FLAG_INVENTORY_UNSTART</CODE> : 未作業 <BR>
     * <code>InventorySystemDefine.STATUS_FLAG_INVENTORY_WORKING</code> : 作業中 <BR>
     * <code>InventorySystemDefine.STATUS_FLAG_INVENTORY_WORKING_COMPLETED</code> : 作業済 <BR>
     * <code>InventorySystemDefine.STATUS_FLAG_INVENTORY_ALREADY_COMPLETED</code> : 確定済 <BR>
     * @param  status 在庫移動情報の状態フラグ
     * @return 作業状態フラグ名称
     */
    public static String getInventWorkStatus(String status)
    {
        if (InventoryInParameter.STATUS_FLAG_INVENTORY_UNSTART.equals(status))
        {
            // 未作業
            return DisplayText.getText("CMB-W0005");
        }
        else if (InventoryInParameter.STATUS_FLAG_INVENTORY_WORKING.equals(status))
        {
            // 作業中
            return DisplayText.getText("LBL-W0424");
        }
        else if (InventoryInParameter.STATUS_FLAG_INVENTORY_WORKING_COMPLETED.equals(status))
        {
            // 作業済
            return DisplayText.getText("RDB-W0046");
        }
        else if (InventoryInParameter.STATUS_FLAG_INVENTORY_ALREADY_COMPLETED.equals(status))
        {
            // 確定済
            return DisplayText.getText("RDB-W0047");
        }
        else
        {
            return "";
        }
    }

    /**
     * 実績報告の名称を取得します。<BR>
     * <CODE>SystemDefine.REPORT_FLAG_NOT_REPORT</CODE> : 未報告<BR>
     * <CODE>SystemDefine.REPORT_FLAG_REPORT</CODE> : 報告済
     * <CODE>SystemDefine.REPORT_FLAG_RESERVE</CODE> : 搬送中
     * @param   status レポートフラグ
     * @return  未報告 or 報告済 or 搬送中
     */
    public static String getReportFlag(String status)
    {
        if (SystemDefine.REPORT_FLAG_NOT_REPORT.equals(status))
        {
            // 未報告
            return DisplayText.getText("LBL-W0329");
        }
        else if (SystemDefine.REPORT_FLAG_REPORT.equals(status))
        {
            // 報告済
            return DisplayText.getText("LBL-W0330");
        }
        else if (SystemDefine.REPORT_FLAG_CARRY.equals(status))
        {
            // 搬送中
            return DisplayText.getText("LBL-W0598");
        }
        else
        {
            return "";
        }
    }

    /**
     * スケジュール状態の名称を返します。<BR>
     * <code>SystemDefine.SEARCH_ALL</code> : 全て <BR>
     * <code>RetrievalSystemDefine.SCH_FLAG_NOT_SCHEDULE</code> : 未開始 <BR>
     * <code>RetrievalSystemDefine.SCH_FLAG_SCHEDUL</code> : 開始済 <BR>
     * @param  scheduleFlag スケジュール状態フラグ
     * @return スケジュール状態名称
     */
    public static String getScheduleStatus(String scheduleFlag)
    {
        if (InParameter.SEARCH_ALL.equals(scheduleFlag))
        {
            // 全て
            return DisplayText.getText("CMB-W0004");
        }
        else if (SystemInParameter.SCH_FLAG_NOT_SCHEDULE.equals(scheduleFlag))
        {
            // 未開始
            return DisplayText.getText("CMB-W0008");
        }
        else if (SystemInParameter.SCH_FLAG_SCHEDULE.equals(scheduleFlag))
        {
            // 開始済
            return DisplayText.getText("CMB-W0009");
        }
        else if (SystemInParameter.SCH_FLAG_COMPLTE.equals(scheduleFlag))
        {
            // 完了
            return DisplayText.getText("CMB-W0007");
        }
        else
        {
            return "";
        }
    }

    /**
     * 作業区分の名称を取得します。 <BR>
     * <CODE>SystemDefine.SEARCH_ALL</CODE> : 全て <BR>
     * <CODE>SystemDefine.JOB_TYPE_UNSTART</CODE> : 未作業 <BR>
     * <CODE>SystemDefine.JOB_TYPE_RECEIVING</CODE> : 入荷 <BR>
     * <CODE>SystemDefine.JOB_TYPE_STORAGE</CODE> : 入庫  <BR>
     * <CODE>SystemDefine.JOB_TYPE_RETRIEVAL</CODE> : 出庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_SORTING</CODE> : 仕分 <BR>
     * <CODE>SystemDefine.JOB_TYPE_SHIPPING</CODE> : 出荷 <BR>
     * <CODE>SystemDefine.JOB_TYPE_MOVEMENT_STORAGE</CODE> : 移動入庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL</CODE> : 移動出庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_NOPLAN_STORAGE</CODE> : 予定外入庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL</CODE> : 予定外出庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_MAINTENANCE_PLUS</CODE> : メンテナンス増 <BR>
     * <CODE>SystemDefine.JOB_TYPE_MAINTENANCE_MINUS</CODE> : メンテナンス減 <BR>
     * <CODE>SystemDefine.JOB_TYPE_INVENTORY</CODE> : 棚卸 <BR>
     * <CODE>SystemDefine.JOB_TYPE_INVENTORY_PLUS</CODE> : 棚卸増 <BR>
     * <CODE>SystemDefine.JOB_TYPE_INVENTORY_MINUS</CODE> : 棚卸減 <BR>
     * <CODE>SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT</CODE> : 計画補充 <BR>
     * <CODE>SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT</CODE> : 緊急補充 <BR>
     * <CODE>SystemDefine.JOB_TYPE_ASRS_EXPENDITURE</CODE> : 強制払出し <BR>
     * <CODE>SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE</CODE> : 搬送データ削除 <BR>
     * <CODE>SystemDefine.JOB_TYPE_RESTORING</CODE> : 再入庫 <BR>
     * <CODE>SystemDefine.JOB_TYPE_DIRECT_TRAVEL</CODE> : 直行 <BR>
     * <CODE>SystemDefine.JOB_TYPE_ASRS_RACK_TO_RACK</CODE> : 棚間移動 <BR>
     * @param jobType 作業区分
     * @return 作業区分名称
     */
    public static String getJobType(String jobType)
    {

        if (InParameter.SEARCH_ALL.equals(jobType))
        {
            // 全て
            return DisplayText.getText("CMB-W0004");
        }
        else if (SystemDefine.JOB_TYPE_UNSTART.equals(jobType))
        {
            return DisplayText.getText("CMB-W0005");
        }
        else if (SystemDefine.JOB_TYPE_RECEIVING.equals(jobType))
        {
            // 入荷
            return DisplayText.getText("CMB-W0012");
        }
        else if (SystemDefine.JOB_TYPE_STORAGE.equals(jobType))
        {
            // 入庫
            return DisplayText.getText("CMB-W0010");
        }
        else if (SystemDefine.JOB_TYPE_RETRIEVAL.equals(jobType))
        {
            // 出庫
            return DisplayText.getText("CMB-W0011");
        }
        else if (SystemDefine.JOB_TYPE_SORTING.equals(jobType))
        {
            // 仕分
            return DisplayText.getText("CMB-W0013");
        }
        else if (SystemDefine.JOB_TYPE_SHIPPING.equals(jobType))
        {
            // 出荷
            return DisplayText.getText("CMB-W0014");
        }
        else if (SystemDefine.JOB_TYPE_MOVEMENT.equals(jobType))
        {
            // 移動
            return DisplayText.getText("CMB-W0039");
        }
        else if (SystemDefine.JOB_TYPE_MOVEMENT_STORAGE.equals(jobType))
        {
            // 移動入庫
            return DisplayText.getText("CMB-W0016");
        }
        else if (SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL.equals(jobType))
        {
            // 移動出庫
            return DisplayText.getText("CMB-W0017");
        }
        else if (SystemDefine.JOB_TYPE_NOPLAN_STORAGE.equals(jobType))
        {
            // 予定外入庫
            return DisplayText.getText("CMB-W0018");
        }
        else if (SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL.equals(jobType))
        {
            // 予定外出庫
            return DisplayText.getText("CMB-W0019");
        }
        else if (SystemDefine.JOB_TYPE_MAINTENANCE_PLUS.equals(jobType))
        {
            // メンテナンス増
            return DisplayText.getText("LBL-W0386");
        }
        else if (SystemDefine.JOB_TYPE_MAINTENANCE_MINUS.equals(jobType))
        {
            // メンテナンス減
            return DisplayText.getText("LBL-W0387");
        }
        else if (SystemDefine.JOB_TYPE_INVENTORY.equals(jobType))
        {
            // 棚卸
            return DisplayText.getText("CMB-W0015");
        }
        else if (SystemDefine.JOB_TYPE_INVENTORY_PLUS.equals(jobType))
        {
            // 棚卸増
            return DisplayText.getText("LBL-W0388");
        }
        else if (SystemDefine.JOB_TYPE_INVENTORY_MINUS.equals(jobType))
        {
            // 棚卸減
            return DisplayText.getText("LBL-W0389");
        }
        else if (SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT.equals(jobType))
        {
            // 計画補充
            return DisplayText.getText("CMB-W0021");
        }
        else if (SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT.equals(jobType))
        {
            // 緊急補充
            return DisplayText.getText("CMB-W0022");
        }
        else if (SystemDefine.JOB_TYPE_ASRS_EXPENDITURE.equals(jobType))
        {
            // 強制払出し
            return DisplayText.getText("CMB-W0033");
        }
        else if (SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE.equals(jobType))
        {
            // 搬送データ削除
            return DisplayText.getText("LBL-W0393");
        }
        else if (SystemDefine.JOB_TYPE_DIRECT_TRAVEL.equals(jobType))
        {
            // 直行
            return DisplayText.getText("LBL-W0407");
        }
        else if (SystemDefine.JOB_TYPE_RESTORING.equals(jobType))
        {
            // 再入庫
            return DisplayText.getText("LBL-W0608");
        }
        else if (SystemDefine.JOB_TYPE_ASRS_RACK_TO_RACK.equals(jobType))
        {
            // 棚間移動
            return DisplayText.getText("LBL-W0408");
        }
        else if (SystemDefine.JOB_TYPE_ASRS_REARRANGE.equals(jobType))
        {
            // 棚替
            return DisplayText.getText("LBL-W9931");
        }
        else
        {
            return "";
        }
    }

    /**
     * 増減区分の名称を取得します。
     * <CODE>SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT</CODE> : 在庫加算(入庫) <BR>
     * <CODE>SystemDefine.INC_DEC_TYPE_STOCK_DECREMENT</CODE> : 在庫減算(出庫) <BR>
     * @param incDecType 増減区分
     * @return 増減区分名称
     */
    public static String getIncDecType(String incDecType)
    {

        if (SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT.equals(incDecType))
        {
            // 在庫加算(入庫)
            return DisplayText.getText("LBL-W0394");
        }
        else if (SystemDefine.INC_DEC_TYPE_STOCK_DECREMENT.equals(incDecType))
        {
            // 在庫減算(出庫)
            return DisplayText.getText("LBL-W0395");
        }
        else
        {
            return "";
        }
    }

    /**
     * RFT作業状態画面で表示する端末区分の表示文字列を取得します。
     *
     * @param arg   端末区分
     * @return      端末区分表示文字列(HT/検品端末/Pカート)
     */
    public static String getTerminalType(String arg)
    {
        if (SystemDefine.TERMINAL_TYPE_HT.equals(arg))
        {
            // LBL-W0318=HT
            return DisplayText.getText("LBL-W0318");
        }
        else if (SystemDefine.TERMINAL_TYPE_IT.equals(arg))
        {
            // LBL-W0319=検品端末
            return DisplayText.getText("LBL-W0319");
        }
        else if (SystemDefine.TERMINAL_TYPE_PCART.equals(arg))
        {
            // LBL-W0320=Pカート
            return DisplayText.getText("LBL-W0320");
        }
        else if (SystemDefine.TERMINAL_TYPE_CAMERA_HT.equals(arg))
        {
            // LBL-W0320=画像登録用HT
            return DisplayText.getText("LBL-W0592");
        }

        return "";
    }

    /**
     * ハードウェア区分の名称を取得します。
     *
     * @param hardwareType ハードウェア区分
     * @return ハードウェア区分名称
     */
    public static String getHardwareType(String hardwareType)
    {
        if (SystemDefine.HARDWARE_TYPE_UNSTART.equals(hardwareType))
        {
            // 未作業
            return DisplayText.getText("CMB-W0005");
        }
        else if (SystemDefine.HARDWARE_TYPE_LIST.equals(hardwareType))
        {
            // リスト
            return DisplayText.getText("LBL-W0325");
        }
        else if (SystemDefine.HARDWARE_TYPE_ASRS.equals(hardwareType))
        {
            // AS/RS
            return DisplayText.getText("LBL-W0326");
        }
        else if (SystemDefine.HARDWARE_TYPE_RFT.equals(hardwareType))
        {
            // RFT
            return DisplayText.getText("LBL-W0324");
        }

        return "";
    }

    /**
     * 取込区分の名称を取得します。
     * @param loadDataType 取込区分
     * @return 取込区分名称
     */
    public static String getLoadDataType(String loadDataType)
    {
        if (SystemInParameter.DATA_TYPE_RECEIVE.equals(loadDataType))
        {
            // 入荷予定データ
            return DisplayText.getText("LBL-W0257");
        }
        else if (SystemInParameter.DATA_TYPE_STORAGE.equals(loadDataType))
        {
            // 入庫予定データ
            return DisplayText.getText("LBL-W0258");
        }
        else if (SystemInParameter.DATA_TYPE_RETRIEVAL.equals(loadDataType))
        {
            // 出庫予定データ
            return DisplayText.getText("LBL-W0259");
        }
        else if (SystemInParameter.DATA_TYPE_SORTING.equals(loadDataType))
        {
            // 仕分予定データ
            return DisplayText.getText("LBL-W0260");
        }
        else if (SystemInParameter.DATA_TYPE_SHIPPING.equals(loadDataType))
        {
            // 出荷予定データ
            return DisplayText.getText("LBL-W0261");
        }
        else if (SystemInParameter.DATA_TYPE_ITEM_MASTER.equals(loadDataType))
        {
            // 商品マスタデータ
            return DisplayText.getText("LBL-W0505");
        }
        else if (SystemInParameter.DATA_TYPE_SUPPLIER_MASTER.equals(loadDataType))
        {
            // 仕入先マスタデータ
            return DisplayText.getText("LBL-W0506");
        }
        else if (SystemInParameter.DATA_TYPE_CUSTOMER_MASTER.equals(loadDataType))
        {
            // 出荷先マスタデータ
            return DisplayText.getText("LBL-W0507");
        }
        else if (SystemInParameter.DATA_TYPE_FIXED_LOCATION_MASTER.equals(loadDataType))
        {
            // 商品固定棚マスタデータ
            return DisplayText.getText("LBL-W0476");
        }
        else if (SystemInParameter.DATA_TYPE_CROSSDOCK.equals(loadDataType))
        {
            // TC予定データ
            return DisplayText.getText("LBL-W0553");
        }
        else if (SystemInParameter.DATA_TYPE_PCTITEM_MASTER.equals(loadDataType))
        {
            // PCT商品マスタデータ
            return DisplayText.getText("LBL-P0003");
        }
        else if (SystemInParameter.DATA_TYPE_PICKINGRECEIVE.equals(loadDataType))
        {
            // PCT入荷予定データ
            return DisplayText.getText("LBL-P0001");
        }
        else if (SystemInParameter.DATA_TYPE_PICKINGRET.equals(loadDataType))
        {
            // PCT出庫予定データ
            return DisplayText.getText("LBL-P0002");
        }
        else if (SystemInParameter.DATA_TYPE_PICKINGRET_FILE.equals(loadDataType))
        {
            // PCT出庫予定データ
            return DisplayText.getText("LBL-P0002");
        }
        else
        {
            return "";
        }
    }

    /**
     * 取込区分の名称を取得します。
     * @param loadDataType 取込区分
     * @param locale 言語
     * @return 取込区分名称
     */
    public static String getLoadDataType(String loadDataType, Locale locale)
    {
        if (SystemInParameter.DATA_TYPE_RECEIVE.equals(loadDataType))
        {
            // 入荷予定
            return DisplayText.getText(locale, "LBL-W0257");
        }
        else if (SystemInParameter.DATA_TYPE_STORAGE.equals(loadDataType))
        {
            // 入庫予定
            return DisplayText.getText(locale, "LBL-W0258");
        }
        else if (SystemInParameter.DATA_TYPE_RETRIEVAL.equals(loadDataType))
        {
            // 出庫予定
            return DisplayText.getText(locale, "LBL-W0259");
        }
        else if (SystemInParameter.DATA_TYPE_SORTING.equals(loadDataType))
        {
            // 仕分予定
            return DisplayText.getText(locale, "LBL-W0260");
        }
        else if (SystemInParameter.DATA_TYPE_SHIPPING.equals(loadDataType))
        {
            // 出荷予定
            return DisplayText.getText(locale, "LBL-W0261");
        }
        else if (SystemInParameter.DATA_TYPE_ITEM_MASTER.equals(loadDataType))
        {
            // 商品マスタ
            return DisplayText.getText(locale, "LBL-W0505");
        }
        else if (SystemInParameter.DATA_TYPE_SUPPLIER_MASTER.equals(loadDataType))
        {
            // 仕入先マスタ
            return DisplayText.getText(locale, "LBL-W0506");
        }
        else if (SystemInParameter.DATA_TYPE_CUSTOMER_MASTER.equals(loadDataType))
        {
            // 出荷先マスタ
            return DisplayText.getText(locale, "LBL-W0507");
        }
        else if (SystemInParameter.DATA_TYPE_FIXED_LOCATION_MASTER.equals(loadDataType))
        {
            // 商品固定棚マスタ
            return DisplayText.getText(locale, "LBL-W0476");
        }
        else if (SystemInParameter.DATA_TYPE_CROSSDOCK.equals(loadDataType))
        {
            // TC予定データ
            return DisplayText.getText(locale, "LBL-W0553");
        }
        else if (SystemInParameter.DATA_TYPE_PCTITEM_MASTER.equals(loadDataType))
        {
            // PCT商品マスタ
            return DisplayText.getText(locale, "LBL-P0003");
        }
        else if (SystemInParameter.DATA_TYPE_PICKINGRECEIVE.equals(loadDataType))
        {
            // PCT入荷予定データ
            return DisplayText.getText(locale, "LBL-P0001");
        }
        else if (SystemInParameter.DATA_TYPE_PICKINGRET.equals(loadDataType))
        {
            // PCT出庫予定データ
            return DisplayText.getText(locale, "LBL-P0002");
        }
        else
        {
            return "";
        }
    }

    /**
     * 取込エラー情報のエラーレベルの名称を取得します。<BR>
     * @param errLevel エラーレベル
     * @param locale 言語
     * @return エラーレベル名称
     */
    public static String getErrorLevel(String errLevel, Locale locale)
    {
        if (SystemDefine.ERROR_LEVEL_WARNING.equals(errLevel))
        {
            // 警告
            return DisplayText.getText(locale, "LBL-W0351");
        }
        else if (SystemDefine.ERROR_LEVEL_ERROR.equals(errLevel))
        {
            // エラー
            return DisplayText.getText(locale, "LBL-W0352");
        }
        else
        {
            return "";
        }
    }

    /**
     * 取込エラー情報のエラー区分の名称を取得します。<BR>
     * @param errFlag エラー区分
     * @param locale 言語
     * @return エラー区分名称
     */
    public static String getErrorFlag(String errFlag, Locale locale)
    {
        if (SystemDefine.ERROR_FLAG_OVER_LINES.equals(errFlag))
        {
            // 取込可能行数超過
            return DisplayText.getText(locale, "MSG-W0019");
        }
        else if (SystemDefine.ERROR_FLAG_ITEM_NUMBER_ERROR.equals(errFlag))
        {
            // 項目数(行サイズ)異常
            return DisplayText.getText(locale, "MSG-W0020");
        }
        else if (SystemDefine.ERROR_FLAG_INDISPENSABLE_ITEM_BLANK.equals(errFlag))
        {
            // 必須項目空白
            return DisplayText.getText(locale, "MSG-W0021");
        }
        else if (SystemDefine.ERROR_FLAG_INPUT_PROHIBITION_ITEM.equals(errFlag))
        {
            // 指定禁止項目に値あり
            return DisplayText.getText(locale, "MSG-W0022");
        }
        else if (SystemDefine.ERROR_FLAG_MASTER_UNREGIST.equals(errFlag))
        {
            // マスタ未登録
            return DisplayText.getText(locale, "MSG-W0023");
        }
        else if (SystemDefine.ERROR_FLAG_PROHIBITION_CHARACTER.equals(errFlag))
        {
            // 禁止文字あり
            return DisplayText.getText(locale, "MSG-W0024");
        }
        else if (SystemDefine.ERROR_FLAG_REPETITION_DATA.equals(errFlag))
        {
            // 重複データあり
            return DisplayText.getText(locale, "MSG-W0025");
        }
        else if (SystemDefine.ERROR_FLAG_OVER_WORKING_UNIT_QTY.equals(errFlag))
        {
            // 作業単位MAX件数超過
            return DisplayText.getText(locale, "MSG-W0026");
        }
        else if (SystemDefine.ERROR_FLAG_NO_CANCELLATION_DATA.equals(errFlag))
        {
            // 取消該当データ無し
            return DisplayText.getText(locale, "MSG-W0027");
        }
        else if (SystemDefine.ERROR_FLAG_CANCELLATION_DATA_STARTED.equals(errFlag))
        {
            // 取消データ作業開始済み
            return DisplayText.getText(locale, "MSG-W0028");
        }
        else if (SystemDefine.ERROR_FLAG_VALIDATE_ERROR.equals(errFlag))
        {
            // データの値が不正
            return DisplayText.getText(locale, "MSG-W0029");
        }
        else if (SystemDefine.ERROR_FLAG_CANCELLATION_DATA_LOCK.equals(errFlag))
        {
            // 取消データ他端末作業中
            return DisplayText.getText(locale, "MSG-W0030");
        }
        else if (SystemDefine.ERROR_FLAG_USEED_MASTER_CODE.equals(errFlag))
        {
            // データ使用中(削除不可)
            return DisplayText.getText(locale, "MSG-W0054");
        }
        else if (SystemDefine.ERROR_FLAG_NO_DELETE_DATA.equals(errFlag))
        {
            // 削除該当データ無し
            return DisplayText.getText(locale, "MSG-W0052");
        }
        else if (SystemDefine.ERROR_FLAG_DELETE_NG_DATA.equals(errFlag))
        {
            // 削除不可データ
            return DisplayText.getText(locale, "MSG-W0053");
        }
        else if (SystemDefine.WORNING_FLAG_WEIGHT_OVER.equals(errFlag))
        {
            // ロット誤差重量 > バラ重量
            return DisplayText.getText(locale, "MSG-P0004");
        }
        else if (SystemDefine.ERROR_FLAG_STARTEDBATCH.equals(errFlag))
        {
            // 作業開始済バッチ
            return DisplayText.getText(locale, "MSG-P0007");
        }
        else if (SystemDefine.ERROR_FLAG_MODIFY_DELETE_NG_DATA.equals(errFlag))
        {
            // 修正・削除不可データ
            return DisplayText.getText(locale, "MSG-P0008");
        }
        else
        {
            return "";
        }
    }

    /**
     * ユーザ別実績照会の条件集約名称を取得します。 <BR>
     * <CODE>SystemDefine.COLLECT_CONDITION_TEAM</CODE> : 期間内合計表示 <BR>
     * <CODE>SystemDefine.COLLECT_CONDITION_DAILY</CODE> : 日別合計表示 <BR>
     * <CODE>SystemDefine.COLLECT_CONDITION_DETAIL</CODE> : 詳細表示 <BR>
     * @param group 集約条件
     * @return 条件集約(文字列)
     */
    public static String getUserResultGroup(String group)
    {
        if (SystemInParameter.COLLECT_CONDITION_TEAM.equals(group))
        {
            // 期間内合計表示
            return DisplayText.getText("RDB-W0017");
        }
        else if (SystemInParameter.COLLECT_CONDITION_DAILY.equals(group))
        {
            // 日別合計表示
            return DisplayText.getText("RDB-W0018");
        }
        else if (SystemInParameter.COLLECT_CONDITION_DETAIL.equals(group))
        {
            // 詳細表示
            return DisplayText.getText("RDB-W0007");
        }
        else
        {
            return "";
        }
    }

    /**
     * 実績照会の条件集約名称を取得します。 <BR>
     * <CODE>SystemDefine.COLLECT_CONDITION_PLAN</CODE> : 予定単位 <BR>
     * <CODE>SystemDefine.COLLECT_CONDITION_DETAIL</CODE> : 詳細表示 <BR>
     * @param group 集約条件
     * @return 条件集約(文字列)
     */
    public static String getResultGroup(String group)
    {
        if (Parameter.COLLECT_CONDITION_PLAN.equals(group))
        {
            // 予定単位
            return DisplayText.getText("RDB-W0003");
        }
        else if (Parameter.COLLECT_CONDITION_DETAIL.equals(group))
        {
            // 詳細表示
            return DisplayText.getText("RDB-W0007");
        }
        else
        {
            return "";
        }
    }

    /**
     * 商品別在庫照会の条件集約名称を取得します。 <BR>
     * <CODE>StockSystemDefine.COLLECT_CONDITION_ITEM</CODE> : 商品単位 <BR>
     * <CODE>StockSystemDefine.COLLECT_CONDITION_DETAIL</CODE> : 詳細表示 <BR>
     * @param group 集約条件
     * @return 条件集約(文字列)
     */
    public static String getStockGroup(String group)
    {
        if (SystemInParameter.COLLECT_CONDITION_ITEM.equals(group))
        {
            // 商品単位
            return DisplayText.getText("RDB-W0008");
        }
        else if (SystemInParameter.COLLECT_CONDITION_DETAIL.equals(group))
        {
            // 詳細表示
            return DisplayText.getText("RDB-W0007");
        }
        else
        {
            return "";
        }
    }

    /**
     * 出庫開始の引当結果を取得します。<BR>
     * <CODE>RetrievalSystemDefine.ALLOCATION_RESULT_UNSTART</CODE> : 未引当<BR>
     * <CODE>RetrievalSystemDefine.ALLOCATE_RESULT_SHORTAGE</CODE> : 欠品<BR>
     * <CODE>RetrievalSystemDefine.ALLOCATE_RESULT_ERROR</CODE> : エラー<BR>
     * <CODE>RetrievalSystemDefine.ALLOCATE_RESULT_REPLENISHMENT</CODE> : 補充あり<BR>
     * <CODE>RetrievalSystemDefine.ALLOCATE_RESULT_COMPLETION</CODE> : 引当完了<BR>
     * @param result 引当結果
     * @return 引当結果(文字列)
     */
    public static String getAllocateResult(String result)
    {
        if (RetrievalInParameter.ALLOCATE_RESULT_UNSTART.equals(result))
        {
            // 未引当
            return DisplayText.getText("LBL-W0402");
        }
        else if (RetrievalInParameter.ALLOCATE_RESULT_SHORTAGE.equals(result))
        {
            // 欠品
            return DisplayText.getText("LBL-W0062");
        }
        else if (RetrievalInParameter.ALLOCATE_RESULT_ERROR.equals(result))
        {
            // エラー
            return DisplayText.getText("LBL-W0352");
        }
        else if (RetrievalInParameter.ALLOCATE_RESULT_REPLENISHMENT.equals(result))
        {
            // 補充あり
            return DisplayText.getText("LBL-W0401");
        }
        else if (RetrievalInParameter.ALLOCATE_RESULT_COMPLETION.equals(result))
        {
            // 引当完了
            return DisplayText.getText("LBL-W0406");
        }
        else if (RetrievalInParameter.ALLOCATE_RESULT_SHORTAGECOMP.equals(result))
        {
            // 欠品完了
            return DisplayText.getText("LBL-W0458");
        }
        else
        {
            return "";
        }
    }

    /**
     * ASRSの処理フラグの名称を取得します。<BR>
     * <CODE>AsrsSystemDefine.PROCESS_STATUS_UNINSTRUCT</CODE> : 未指示に戻す<BR>
     * <CODE>AsrsSystemDefine.PROCESS_STATUS_COMPLETE</CODE> : 強制完了<BR>
     * <CODE>AsrsSystemDefine.PROCESS_STATUS_CANCEL_ALLOCATE</CODE> : 引当を取り消す<BR>
     * <CODE>AsrsSystemDefine.PROCESS_STATUS_TRACKING_DELETE</CODE> : トラッキング削除<BR>
     * @param process 処理フラグ
     * @param locale ロケール情報
     * @return 処理フラグ
     */
    public static String getAsrsProcessStatus(String process, Locale locale)
    {
        if (AsrsInParameter.PROCESS_STATUS_UNINSTRUCT.equals(process))
        {
            // 未指示に戻す
            return DisplayText.getText(locale, "RDB-W0036");
        }
        else if (AsrsInParameter.PROCESS_STATUS_COMPLETE.equals(process))
        {
            // 強制完了
            return DisplayText.getText(locale, "RDB-W0037");
        }
        else if (AsrsInParameter.PROCESS_STATUS_CANCEL_ALLOCATE.equals(process))
        {
            // 引当を取り消す
            return DisplayText.getText(locale, "RDB-W0053");
        }
        else if (AsrsInParameter.PROCESS_STATUS_TRACKING_DELETE.equals(process))
        {
            // トラッキング削除
            return DisplayText.getText(locale, "RDB-W0038");
        }
        else if (AsrsInParameter.PROCESS_STATUS_EMPTY_RETRIEVAL.equals(process))
        {
            // 空出荷
            return DisplayText.getText(locale, "LBL-W0422");
        }
        else
        {
            return "";
        }
    }

    /**
     * 搬送区分の名称を取得します。<BR>
     * <CODE>SystemDefine.CARRY_FLAG_STORAGE</CODE> : 入庫<BR>
     * <CODE>SystemDefine.CARRY_FLAG_RETRIEVAL</CODE> : 出庫<BR>
     * <CODE>SystemDefine.CARRY_FLAG_DIRECT_TRAVEL</CODE> : 直行<BR>
     * <CODE>SystemDefine.CARRY_FLAG_RACK_TO_RACK</CODE> : 棚間移動<BR>
     * @param carryFlag 搬送区分
     * @return 搬送区分名称
     */
    public static String getCarryFlag(String carryFlag)
    {
        if (SystemDefine.CARRY_FLAG_STORAGE.equals(carryFlag))
        {
            // 入庫
            return DisplayText.getText("LBL-W0367");
        }
        else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carryFlag))
        {
            // 出庫
            return DisplayText.getText("LBL-W0368");
        }
        else if (SystemDefine.CARRY_FLAG_DIRECT_TRAVEL.equals(carryFlag))
        {
            // 直行
            return DisplayText.getText("LBL-W0407");
        }
        else if (SystemDefine.CARRY_FLAG_RACK_TO_RACK.equals(carryFlag))
        {
            // 棚間移動
            return DisplayText.getText("LBL-W0408");
        }
        else
        {
            return "";
        }
    }

    /**
     * 搬送状態の名称を取得します。<BR>
     * <CODE>SystemDefine.CMD_STATUS_ALLOCATION</CODE> : 引当<BR>
     * <CODE>SystemDefine.CMD_STATUS_START</CODE> : 開始<BR>
     * <CODE>SystemDefine.CMD_STATUS_WAIT_RESPONSE</CODE> : 応答待ち<BR>
     * <CODE>SystemDefine.CMD_STATUS_INSTRUCTION</CODE> : 指示済み<BR>
     * <CODE>SystemDefine.CMD_STATUS_PICKUP</CODE> : 掬い完了<BR>
     * <CODE>SystemDefine.CMD_STATUS_COMP_RETRIEVAL</CODE> : 出庫完了<BR>
     * <CODE>SystemDefine.CMD_STATUS_ARRIVAL</CODE> : 到着<BR>
     * <CODE>SystemDefine.CMD_STATUS_ERROR</CODE> : 異常<BR>
     * @param cmdStatus 搬送状態
     * @return 搬送状態名称
     */
    public static String getCmdStatus(String cmdStatus)
    {
        if (SystemDefine.CMD_STATUS_ALLOCATION.equals(cmdStatus))
        {
            // 引当
            return DisplayText.getText("LBL-W0409");
        }
        else if (SystemDefine.CMD_STATUS_START.equals(cmdStatus))
        {
            // 開始
            return DisplayText.getText("LBL-W0410");
        }
        else if (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(cmdStatus))
        {
            // 応答待ち
            return DisplayText.getText("LBL-W0411");
        }
        else if (SystemDefine.CMD_STATUS_INSTRUCTION.equals(cmdStatus))
        {
            // 指示済み
            return DisplayText.getText("LBL-W0412");
        }
        else if (SystemDefine.CMD_STATUS_PICKUP.equals(cmdStatus))
        {
            // 掬い完了
            return DisplayText.getText("LBL-W0413");
        }
        else if (SystemDefine.CMD_STATUS_COMP_RETRIEVAL.equals(cmdStatus))
        {
            // 出庫完了
            return DisplayText.getText("LBL-W0414");
        }
        else if (SystemDefine.CMD_STATUS_ARRIVAL.equals(cmdStatus))
        {
            // 到着
            return DisplayText.getText("LBL-W0415");
        }
        else if (SystemDefine.CMD_STATUS_ERROR.equals(cmdStatus))
        {
            // 異常
            return DisplayText.getText("LBL-W0416");
        }
        else
        {
            return "";
        }
    }

    /**
     * 搬送状態の名称を取得します。<BR>
     * <CODE>SystemDefine.CMD_STATUS_ALLOCATION</CODE> : 引当<BR>
     * <CODE>SystemDefine.CMD_STATUS_START</CODE> : 開始<BR>
     * <CODE>SystemDefine.CMD_STATUS_WAIT_RESPONSE</CODE> : 応答待ち<BR>
     * <CODE>SystemDefine.CMD_STATUS_INSTRUCTION</CODE> : 指示済み<BR>
     * <CODE>SystemDefine.CMD_STATUS_PICKUP</CODE> : 掬い完了<BR>
     * <CODE>SystemDefine.CMD_STATUS_COMP_RETRIEVAL</CODE> : 出庫完了<BR>
     * <CODE>SystemDefine.CMD_STATUS_ARRIVAL</CODE> : 到着<BR>
     * <CODE>SystemDefine.CMD_STATUS_ERROR</CODE> : 異常<BR>
     * @param cmdStatus 搬送状態
     * @param locale ロケール情報
     * @return 搬送状態名称
     */
    public static String getCmdStatus(String cmdStatus, Locale locale)
    {
        if (SystemDefine.CMD_STATUS_ALLOCATION.equals(cmdStatus))
        {
            // 引当
            return DisplayText.getText(locale, "LBL-W0409");
        }
        else if (SystemDefine.CMD_STATUS_START.equals(cmdStatus))
        {
            // 開始
            return DisplayText.getText(locale, "LBL-W0410");
        }
        else if (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(cmdStatus))
        {
            // 応答待ち
            return DisplayText.getText(locale, "LBL-W0411");
        }
        else if (SystemDefine.CMD_STATUS_INSTRUCTION.equals(cmdStatus))
        {
            // 指示済み
            return DisplayText.getText(locale, "LBL-W0412");
        }
        else if (SystemDefine.CMD_STATUS_PICKUP.equals(cmdStatus))
        {
            // 掬い完了
            return DisplayText.getText(locale, "LBL-W0413");
        }
        else if (SystemDefine.CMD_STATUS_COMP_RETRIEVAL.equals(cmdStatus))
        {
            // 出庫完了
            return DisplayText.getText(locale, "LBL-W0414");
        }
        else if (SystemDefine.CMD_STATUS_ARRIVAL.equals(cmdStatus))
        {
            // 到着
            return DisplayText.getText(locale, "LBL-W0415");
        }
        else if (SystemDefine.CMD_STATUS_ERROR.equals(cmdStatus))
        {
            // 異常
            return DisplayText.getText(locale, "LBL-W0416");
        }
        else
        {
            return "";
        }
    }

    /**
     * 作業種別の名称を取得します。<BR>
     * <CODE>SystemDefine.WORK_TYPE_STORAGE</CODE> : 入庫<BR>
     * <CODE>SystemDefine.WORK_TYPE_RETRIEVAL</CODE> : 出庫<BR>
     * <CODE>SystemDefine.WORK_TYPE_NOPLAN_STORAGE</CODE> : 予定外入庫<BR>
     * <CODE>SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL</CODE> : 予定外出庫<BR>
     * <CODE>SystemDefine.WORK_TYPE_ADD_STORAGE</CODE> : 予定外入庫(積増)<BR>
     * <CODE>SystemDefine.WORK_TYPE_DIRECT_TRAVEL</CODE> : 直行<BR>
     * <CODE>SystemDefine.WORK_TYPE_RESTORING</CODE> : 再入庫<BR>
     * <CODE>SystemDefine.WORK_TYPE_INVENTORYCHECK</CODE> : 在庫確認<BR>
     * <CODE>SystemDefine.WORK_TYPE_RACKMOVE_FROM</CODE> : 棚間移動(From)<BR>
     * <CODE>SystemDefine.WORK_TYPE_NORMAL_REPLENISHMENT</CODE> : 計画補充<BR>
     * <CODE>SystemDefine.WORK_TYPE_EMERGENCY_REPLENISHMENT</CODE> : 緊急補充<BR>
     * <CODE>SystemDefine.WORK_TYPE_EXPENDITURE</CODE> : 強制払出し<BR>
     * <CODE>SystemDefine.WORK_TYPE_CARRYINFODELETE</CODE> : 搬送データ削除<BR>
     * <CODE>SystemDefine.WORK_TYPE_EMPTYRETRIEVAL</CODE> : 空出荷<BR>
     * @param workType 作業種別
     * @return 作業種別名称
     */
    public static String getWorkType(String workType)
    {
        if (SystemDefine.WORK_TYPE_STORAGE.equals(workType))
        {
            // 入庫
            return DisplayText.getText("LBL-W0367");
        }
        else if (SystemDefine.WORK_TYPE_RETRIEVAL.equals(workType))
        {
            // 出庫
            return DisplayText.getText("LBL-W0368");
        }
        else if (SystemDefine.WORK_TYPE_NOPLAN_STORAGE.equals(workType))
        {
            // 予定外入庫
            return DisplayText.getText("LBL-W0384");
        }
        else if (SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL.equals(workType))
        {
            // 予定外出庫
            return DisplayText.getText("LBL-W0385");
        }
        else if (SystemDefine.WORK_TYPE_ADD_STORAGE.equals(workType))
        {
            // 予定外入庫(積増)
            return DisplayText.getText("LBL-W0417");
        }
        else if (SystemDefine.WORK_TYPE_DIRECT_TRAVEL.equals(workType))
        {
            // 直行
            return DisplayText.getText("LBL-W0407");
        }
        else if (SystemDefine.WORK_TYPE_RESTORING.equals(workType))
        {
            // 再入庫
            return DisplayText.getText("LBL-W0608");
        }
        else if (SystemDefine.WORK_TYPE_INVENTORYCHECK.equals(workType))
        {
            // 在庫確認
            return DisplayText.getText("LBL-W0418");
        }
        else if (SystemDefine.WORK_TYPE_RACKMOVE_FROM.equals(workType))
        {
            // 棚間移動(From)
            return DisplayText.getText("LBL-W0423");
        }
        else if (SystemDefine.WORK_TYPE_NORMAL_REPLENISHMENT.equals(workType))
        {
            // 計画補充
            return DisplayText.getText("LBL-W0419");
        }
        else if (SystemDefine.WORK_TYPE_EMERGENCY_REPLENISHMENT.equals(workType))
        {
            // 緊急補充
            return DisplayText.getText("LBL-W0420");
        }
        else if (SystemDefine.WORK_TYPE_EXPENDITURE.equals(workType))
        {
            // 強制払出し
            return DisplayText.getText("LBL-W0421");
        }
        else if (SystemDefine.WORK_TYPE_CARRYINFODELETE.equals(workType))
        {
            // 搬送データ削除
            return DisplayText.getText("LBL-W0393");
        }
        else if (SystemDefine.WORK_TYPE_EMPTYRETRIEVAL.equals(workType))
        {
            // 空出荷
            return DisplayText.getText("LBL-W0422");
        }
        else
        {
            return "";
        }
    }

    /**
     * 作業種別の名称を取得します。<BR>
     * <CODE>SystemDefine.WORK_TYPE_STORAGE</CODE> : 入庫<BR>
     * <CODE>SystemDefine.WORK_TYPE_RETRIEVAL</CODE> : 出庫<BR>
     * <CODE>SystemDefine.WORK_TYPE_NOPLAN_STORAGE</CODE> : 予定外入庫<BR>
     * <CODE>SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL</CODE> : 予定外出庫<BR>
     * <CODE>SystemDefine.WORK_TYPE_ADD_STORAGE</CODE> : 予定外入庫(積増)<BR>
     * <CODE>SystemDefine.WORK_TYPE_INVENTORYCHECK</CODE> : 在庫確認<BR>
     * <CODE>SystemDefine.WORK_TYPE_RACKMOVE_FROM</CODE> : 棚間移動(From)<BR>
     * <CODE>SystemDefine.WORK_TYPE_NORMAL_REPLENISHMENT</CODE> : 計画補充<BR>
     * <CODE>SystemDefine.WORK_TYPE_EMERGENCY_REPLENISHMENT</CODE> : 緊急補充<BR>
     * <CODE>SystemDefine.WORK_TYPE_EXPENDITURE</CODE> : 強制払出し<BR>
     * <CODE>SystemDefine.WORK_TYPE_CARRYINFODELETE</CODE> : 搬送データ削除<BR>
     * <CODE>SystemDefine.WORK_TYPE_EMPTYRETRIEVAL</CODE> : 空出荷<BR>
     * @param workType 作業種別
     * @param locale ロケール情報
     * @return 作業種別名称
     */
    public static String getWorkType(String workType, Locale locale)
    {
        if (SystemDefine.WORK_TYPE_STORAGE.equals(workType))
        {
            // 入庫
            return DisplayText.getText(locale, "LBL-W0367");
        }
        else if (SystemDefine.WORK_TYPE_RETRIEVAL.equals(workType))
        {
            // 出庫
            return DisplayText.getText(locale, "LBL-W0368");
        }
        else if (SystemDefine.WORK_TYPE_NOPLAN_STORAGE.equals(workType))
        {
            // 予定外入庫
            return DisplayText.getText(locale, "LBL-W0384");
        }
        else if (SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL.equals(workType))
        {
            // 予定外出庫
            return DisplayText.getText(locale, "LBL-W0385");
        }
        else if (SystemDefine.WORK_TYPE_ADD_STORAGE.equals(workType))
        {
            // 予定外入庫(積増)
            return DisplayText.getText(locale, "LBL-W0417");
        }
        else if (SystemDefine.WORK_TYPE_INVENTORYCHECK.equals(workType))
        {
            // 在庫確認
            return DisplayText.getText(locale, "LBL-W0418");
        }
        else if (SystemDefine.WORK_TYPE_RACKMOVE_FROM.equals(workType))
        {
            // 棚間移動(From)
            return DisplayText.getText(locale, "LBL-W0423");
        }
        else if (SystemDefine.WORK_TYPE_NORMAL_REPLENISHMENT.equals(workType))
        {
            // 計画補充
            return DisplayText.getText(locale, "LBL-W0419");
        }
        else if (SystemDefine.WORK_TYPE_EMERGENCY_REPLENISHMENT.equals(workType))
        {
            // 緊急補充
            return DisplayText.getText(locale, "LBL-W0420");
        }
        else if (SystemDefine.WORK_TYPE_EXPENDITURE.equals(workType))
        {
            // 強制払出し
            return DisplayText.getText(locale, "LBL-W0421");
        }
        else if (SystemDefine.WORK_TYPE_CARRYINFODELETE.equals(workType))
        {
            // 搬送データ削除
            return DisplayText.getText(locale, "LBL-W0393");
        }
        else if (SystemDefine.WORK_TYPE_EMPTYRETRIEVAL.equals(workType))
        {
            // 空出荷
            return DisplayText.getText(locale, "LBL-W0422");
        }
        else
        {
            return "";
        }
    }

    /**
     * モード切替種別の名称を取得します。<BR>
     * <CODE>AsrsSystemDefine.MODE_TYPE_NONE</CODE> : モード切替なし<BR>
     * <CODE>AsrsSystemDefine.MODE_TYPE_AWC_CHANGE</CODE> : 手動<BR>
     * <CODE>AsrsSystemDefine.MODE_TYPE_AGC_CHANGE</CODE> : ＡＧＣモード<BR>
     * <CODE>AsrsSystemDefine.MODE_TYPE_AUTO_CHANGE</CODE> : 自動<BR>
     * @param modeType モード切替種別
     * @return モード切替種別名称
     */
    public static String getModeType(String modeType)
    {
        if (SystemDefine.MODE_TYPE_NONE.equals(modeType))
        {
            // モード切替なし
            return DisplayText.getText("LBL-W0433");
        }
        else if (SystemDefine.MODE_TYPE_AWC_CHANGE.equals(modeType))
        {
            // 手動
            return DisplayText.getText("RDB-W0030");
        }
        else if (SystemDefine.MODE_TYPE_AGC_CHANGE.equals(modeType))
        {
            // ＡＧＣモード
            return DisplayText.getText("LBL-W0434");
        }
        else if (SystemDefine.MODE_TYPE_AUTO_CHANGE.equals(modeType))
        {
            // 自動
            return DisplayText.getText("RDB-W0031");
        }
        else
        {
            return "";
        }
    }

    /**
     * 優先区分の名称を取得します。<BR>
     * <CODE>SystemDefine.PRIORITY_EMERGENCY</CODE> : 緊急<BR>
     * <CODE>SystemDefine.PRIORITY_NORMAL</CODE> : 通常<BR>
     * <CODE>SystemDefine.PRIORITY_CHECK_EMPTY</CODE> : 空棚確認<BR>
     * @param priority 優先区分
     * @return 優先区分フラグ
     */
    public static String getPriority(String priority)
    {
        if (SystemDefine.PRIORITY_EMERGENCY.equals(priority))
        {
            // 緊急
            return DisplayText.getText("LBL-W0443");
        }
        else if (SystemDefine.PRIORITY_NORMAL.equals(priority))
        {
            // 通常
            return DisplayText.getText("RDB-W0005");
        }
        else if (SystemDefine.PRIORITY_CHECK_EMPTY.equals(priority))
        {
            // 空棚確認
            return DisplayText.getText("LBL-W0444");
        }
        else
        {
            return "";
        }
    }

    /**
     * 作業モードの名称を取得します。<BR>
     * <CODE>AsrsSystemDefine.CURRENT_MODE_NEUTRAL</CODE> : ニュートラル<BR>
     * <CODE>AsrsSystemDefine.CURRENT_MODE_STORAGE</CODE> : 入庫<BR>
     * <CODE>AsrsSystemDefine.CURRENT_MODE_RETRIEVAL</CODE> : 出庫<BR>
     * @param mode 作業モード
     * @return 作業モード名称
     */
    public static String getMode(String mode)
    {
        if (SystemDefine.CURRENT_MODE_NEUTRAL.equals(mode))
        {
            // ニュートラル
            return DisplayText.getText("LBL-W0435");
        }
        else if (SystemDefine.CURRENT_MODE_STORAGE.equals(mode))
        {
            // 入庫
            return DisplayText.getText("CMB-W0010");
        }
        else if (SystemDefine.CURRENT_MODE_RETRIEVAL.equals(mode))
        {
            // 出庫
            return DisplayText.getText("CMB-W0011");
        }
        else
        {
            return "";
        }
    }

    /**
     * 作業モード切替要求の名称を取得します。<BR>
     * <CODE>AsrsSystemDefine.MODE_REQUEST_NONE</CODE> : モード切替要求なし<BR>
     * <CODE>AsrsSystemDefine.MODE_REQUEST_STORAGE</CODE> : 入庫モード切替要求<BR>
     * <CODE>AsrsSystemDefine.MODE_REQUEST_RETRIEVAL</CODE> : 出庫モード切替要求<BR>
     * @param modeRequest モード切替要求区分
     * @return 作業モード名称
     */
    public static String getModeRequest(String modeRequest)
    {
        if (SystemDefine.MODE_REQUEST_NONE.equals(modeRequest))
        {
            // モード切替要求なし
            return DisplayText.getText("LBL-W0436");
        }
        else if (SystemDefine.MODE_REQUEST_STORAGE.equals(modeRequest))
        {
            // 入庫モード切替要求
            return DisplayText.getText("LBL-W0437");
        }
        else if (SystemDefine.MODE_REQUEST_RETRIEVAL.equals(modeRequest))
        {
            // 出庫モード切替要求
            return DisplayText.getText("LBL-W0438");
        }
        else
        {
            return "";
        }
    }

    /**
     * 機器状態の名称を取得します。<BR>
     * <CODE>SystemDefine.MACHINE_STATE_OFFLINE</CODE> : 切り離し<BR>
     * <CODE>SystemDefine.CURRENT_MODE_STORAGE</CODE> : 正常<BR>
     * <CODE>SystemDefine.CURRENT_MODE_RETRIEVAL</CODE> : 異常<BR>
     * @param machineStatus 機器状態
     * @return 機器状態名称
     */
    public static String getMachineStatus(String machineStatus)
    {
        if (SystemDefine.MACHINE_STATE_OFFLINE.equals(machineStatus))
        {
            // 切り離し
            return DisplayText.getText("LBL-W0431");
        }
        else if (SystemDefine.MACHINE_STATE_ACTIVE.equals(machineStatus))
        {
            // 正常
            return DisplayText.getText("LBL-W0432");
        }
        else if (SystemDefine.MACHINE_STATE_FAIL.equals(machineStatus))
        {
            // 異常
            return DisplayText.getText("LBL-W0416");
        }
        else
        {
            return "";
        }
    }

    /**
     * 中断中フラグの名称を取得します。<BR>
     * <CODE>AsrsSystemDefine.SUSPEND_OFF</CODE> : 使用可能<BR>
     * <CODE>AsrsSystemDefine.SUSPEND_ON</CODE> : 中断中<BR>
     * @param suspend 中断中フラグ
     * @return 中断中フラグ
     */
    public static String getSuspend(String suspend)
    {
        if (SystemDefine.SUSPEND_OFF.equals(suspend))
        {
            // 使用可能
            return DisplayText.getText("LBL-T0124");
        }
        else if (SystemDefine.SUSPEND_ON.equals(suspend))
        {
            // 中断中
            return DisplayText.getText("LBL-W0430");
        }
        else
        {
            return "";
        }
    }

    /**
     * 出庫指示詳細の名称を取得します。<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK</CODE> : 在庫確認<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_UNIT</CODE> : ユニット出庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_PICKING</CODE> : ピッキング出庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_ADD_STORING</CODE> : 積増入庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_UNKNOWN</CODE> : 指定無し(－－－－)<BR>
     * @param detail 出庫指示詳細
     * @return 出庫指示詳細
     */
    public static String getRetrievalDetail(String detail)
    {
        if (SystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(detail))
        {
            // 在庫確認
            return DisplayText.getText("LBL-W0418");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(detail))
        {
            // ユニット出庫
            return DisplayText.getText("LBL-W0439");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_PICKING.equals(detail))
        {
            // ピッキング出庫
            return DisplayText.getText("LBL-W0440");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_ADD_STORING.equals(detail))
        {
            // 積増入庫
            return DisplayText.getText("LBL-W0441");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_UNKNOWN.equals(detail))
        {
            // 指定無し(－－－－)
            return DisplayText.getText("LBL-W0442");
        }
        else
        {
            return "";
        }
    }

    /**
     * 出庫指示詳細の名称を取得します。<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK</CODE> : 在庫確認<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_UNIT</CODE> : ユニット出庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_PICKING</CODE> : ピッキング出庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_ADD_STORING</CODE> : 積増入庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_UNKNOWN</CODE> : 指定無し(－－－－)<BR>
     * @param detail 出庫指示詳細
     * @param locale ロケール情報
     * @return 出庫指示詳細
     */
    public static String getRetrievalDetail(String detail, Locale locale)
    {
        if (SystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(detail))
        {
            // 在庫確認
            return DisplayText.getText(locale, "LBL-W0418");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(detail))
        {
            // ユニット出庫
            return DisplayText.getText(locale, "LBL-W0439");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_PICKING.equals(detail))
        {
            // ピッキング出庫
            return DisplayText.getText(locale, "LBL-W0440");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_ADD_STORING.equals(detail))
        {
            // 積増入庫
            return DisplayText.getText(locale, "LBL-W0441");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_UNKNOWN.equals(detail))
        {
            // 指定無し(－－－－)
            return DisplayText.getText(locale, "LBL-W0442");
        }
        else
        {
            return "";
        }
    }

    /**
     * 出庫指示詳細の名称を取得します。(FA用)<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK</CODE> : ピッキング出庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_UNIT</CODE> : ユニット出庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_PICKING</CODE> : ピッキング出庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_ADD_STORING</CODE> : 積増入庫<BR>
     * <CODE>AsrsSystemDefine.RETRIEVAL_DETAIL_UNKNOWN</CODE> : 指定無し(－－－－)<BR>
     * @param detail 出庫指示詳細
     * @return 出庫指示詳細
     */
    public static String getFaRetrievalDetail(String detail)
    {
        if (SystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(detail))
        {
            // ピッキング
            return DisplayText.getText("LBL-W1436");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(detail))
        {
            // ユニット
            return DisplayText.getText("LBL-W1437");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_PICKING.equals(detail))
        {
            // ピッキング
            return DisplayText.getText("LBL-W1436");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_ADD_STORING.equals(detail))
        {
            // 積増入庫
            return DisplayText.getText("LBL-W0441");
        }
        else if (SystemDefine.RETRIEVAL_DETAIL_UNKNOWN.equals(detail))
        {
            // 指定無し(－－－－)
            return DisplayText.getText("LBL-W0442");
        }
        else
        {
            return "";
        }
    }

    /**
     * 欠品区分の名称を取得します。<BR>
     * <CODE>RetrievalSystemDefine.SHORTAGE_FLAG_SHORTAGE_NORMAL</CODE> : 未<BR>
     * <CODE>RetrievalSystemDefine.SHORTAGE_FLAG_SHORTAGE_COMPLETE</CODE> : 済<BR>
     * @param shortageFlag 欠品フラグ
     * @return 引当の名称
     */
    public static String getShortageFlag(String shortageFlag)
    {
        if (SystemDefine.SHORTAGE_FLAG_SHORTAGE_NORMAL.equals(shortageFlag))
        {
            // 通常
            return DisplayText.getText("LBL-W1428");
        }
        else if (SystemDefine.SHORTAGE_FLAG_SHORTAGE_COMPLETE.equals(shortageFlag))
        {
            // 欠品完了
            return DisplayText.getText("LBL-W0458");
        }
        else
        {
            return "";
        }
    }

    /**
     * 引当の名称を取得します。<BR>
     * <CODE>RetrievalSystemDefine.SHORTAGE_FLAG_SHORTAGE_NORMAL</CODE> : 未<BR>
     * <CODE>RetrievalSystemDefine.SHORTAGE_FLAG_SHORTAGE_COMPLETE</CODE> : 済<BR>
     * @param shortageFlag 欠品フラグ
     * @return 引当の名称
     */
    public static String getAllocate(String shortageFlag)
    {
        if (SystemDefine.SHORTAGE_FLAG_SHORTAGE_NORMAL.equals(shortageFlag))
        {
            // 未
            return DisplayText.getText("LBL-W0464");
        }
        else if (SystemDefine.SHORTAGE_FLAG_SHORTAGE_COMPLETE.equals(shortageFlag))
        {
            // 済
            return DisplayText.getText("LBL-W0465");
        }
        else
        {
            return "";
        }
    }

    /**
     * 引当パターン区分を取得します。
     * <CODE>RetrievalSystemDefine.ALLOCATE_TYPE_NOMAL</CODE> : 通常<BR>
     * <CODE>RetrievalSystemDefine.ALLOCATE_TYPE_REPLENISHMENT</CODE> : 補充<BR>
     * @param allocatePattarnFlag 引当パターン区分フラグ
     * @return 引当パターン区分
     */
    public static String getAllocatePattarn(String allocatePattarnFlag)
    {
        if (SystemDefine.ALLOCATE_TYPE_NORMAL.equals(allocatePattarnFlag))
        {
            // 通常
            return DisplayText.getText("RDB-W0005");
        }
        else if (SystemDefine.ALLOCATE_TYPE_REPLENISHMENT.equals(allocatePattarnFlag))
        {
            // 補充
            return DisplayText.getText("RDB-W0006");
        }
        else
        {
            return "";
        }
    }

    /**
     * エリア区分を取得します。
     * <CODE>RetrievalSystemDefine.AREA_TYPE_NORMAL</CODE> : 通常<BR>
     * <CODE>RetrievalSystemDefine.AREA_TYPE_REPLENISHMENT</CODE> : 補充<BR>
     * @param areaPattarnFlag 補充元エリア区分
     * @return 補充元エリア区分名
     */
    public static String getAreaPattarn(String areaPattarnFlag)
    {
        if (SystemDefine.REPLENISHMENT_AREA_TYPE_NORMAL_AREA.equals(areaPattarnFlag))
        {
            // 通常
            return DisplayText.getText("RDB-W0005");
        }
        else if (SystemDefine.REPLENISHMENT_AREA_TYPE_REPLENISHMENT_AREA.equals(areaPattarnFlag))
        {
            // 補充
            return DisplayText.getText("RDB-W0006");
        }
        else
        {
            return "";
        }
    }

    /**
     * Page.setConfirm() などのメソッド呼び出し用に、メッセージリソースID+パラメータの
     * 形式をフォーマットします。
     *
     * @param id リソースID
     * @param args 引数
     * @return 文字列表現
     */
    public static String format(String id, Object... args)
    {
        StringBuilder buff = new StringBuilder(id);
        for (Object arg : args)
        {
            buff.append(MessageResource.DELIM);
            buff.append(arg);
        }
        return new String(buff);
    }

    /**
     * ステーション情報の機器状態の名称を取得します。<BR>
     * <CODE>AsrsSystemDefine.STATION_STATUS_DISCONNECTED</CODE> : 切り離し<BR>
     * <CODE>AsrsSystemDefine.STATION_STATUS_NORMAL</CODE> : 正常<BR>
     * <CODE>AsrsSystemDefine.STATION_STATUS_ERROR</CODE> : 異常<BR>
     * @param stationStatus 機器状態
     * @return 機器状態名称
     */
    public static String getStationStatus(String stationStatus)
    {
        if (SystemDefine.STATION_STATUS_DISCONNECTED.equals(stationStatus))
        {
            // 切り離し
            return DisplayText.getText("LBL-W0431");
        }
        else if (SystemDefine.STATION_STATUS_NORMAL.equals(stationStatus))
        {
            // 正常
            return DisplayText.getText("LBL-W0432");
        }
        else if (SystemDefine.STATION_STATUS_ERROR.equals(stationStatus))
        {
            // 異常
            return DisplayText.getText("LBL-W0416");
        }
        else
        {
            return "";
        }
    }

    /**
     * 日次更新の状態を返します。<BR>
     *
     * @param str 日次更新の状態フラグ
     * @return 日次更新の状態名称
     */
    public static String getDailyUpdateStatus(String str)
    {
        if (SystemOutParameter.DAILYUPDATE_STATUS_NG.equals(str))
        {
            // LBL-W0425=設定不可
            return DisplayText.getText("LBL-W0425");
        }
        else if (SystemOutParameter.DAILYUPDATE_STATUS_WARNING.equals(str))
        {
            // LBL-W1444=設定可能(注意)
            return DisplayText.getText("LBL-W1444");
        }
        else
        {
            return "";
        }
    }

    /**
     * 日次更新のNG理由を返します。<BR>
     *
     * @param str NG理由フラグ
     * @return 日次更新のNG理由名称
     */
    public static String getReason(String str)
    {
        if (SystemInParameter.REASON_DAILYUPDATING.equals(str))
        {
            // LBL-W0286=日次更新
            return DisplayText.getText("LBL-W0286");
        }
        else if (SystemInParameter.REASON_LOADING.equals(str))
        {
            // LBL-W0427=取込中
            return DisplayText.getText("LBL-W0427");
        }
        else if (SystemInParameter.REASON_NOWWORKING.equals(str))
        {
            // LBL-W0424=作業中
            return DisplayText.getText("LBL-W0424");
        }
        else if (SystemInParameter.REASON_ONLINE.equals(str))
        {
            // LBL-W0426=オンライン
            return DisplayText.getText("LBL-W0426");
        }
        else if (SystemInParameter.REASON_REPORTING.equals(str))
        {
            // LBL-W0428=報告中
            return DisplayText.getText("LBL-W0428");
        }
        else if (SystemInParameter.REASON_RETRIEVALALLOCATE.equals(str))
        {
            // LBL-W0497=出庫引当中
            return DisplayText.getText("LBL-W0497");
        }
        else if (SystemInParameter.REASON_REPORTFILESTAY.equals(str))
        {
            // LBL-W0494=報告ファイル残
            return DisplayText.getText("LBL-W0494");
        }
        else if (SystemInParameter.REASON_ALLOCATED.equals(str))
        {
            // LBL-W0495=引当済
            return DisplayText.getText("LBL-W0495");
        }
        else if (SystemInParameter.REASON_NOWSTARTING.equals(str))
        {
            // LBL-W0322=起動中
            return DisplayText.getText("LBL-W0322");
        }
        else if (SystemInParameter.REASON_WAITSTORAGE.equals(str))
        {
            // LBL-W0498=出庫済入庫待
            return DisplayText.getText("LBL-W0498");
        }
        else if (SystemInParameter.REASON_REPLENISHMENTDATA.equals(str))
        {
            // LBL-W0510=補充データあり
            return DisplayText.getText("LBL-W0510");
        }
        else if (SystemInParameter.REASON_NOREPORT.equals(str))
        {
            // LBL-W0511=未報告データあり
            return DisplayText.getText("LBL-W0511");
        }
        else if (SystemInParameter.REASON_NOCOMPLETE.equals(str))
        {
            // LBL-W0519=未確定データあり
            return DisplayText.getText("LBL-W0519");
        }
        else if (SystemInParameter.REASON_ALLOCATION_CLEAR.equals(str))
        {
            // LBL-W0599=搬送データクリア中
            return DisplayText.getText("LBL-W0599");
        }
        else if (SystemInParameter.REASON_END_PROCESSING.equals(str))
        {
            // LBL-W0609=終了処理中
            return DisplayText.getText("LBL-W0609");
        }
        else
        {
            return "";
        }

    }

    /**
     * 日次更新のNG理由発生箇所を取得します。<BR>
     *
     * @param str NG理由発生箇所フラグ
     * @return 日次更新のNG理由発生箇所名称
     */
    public static String getNGHappenedPoint(String str)
    {
        if (SystemInParameter.POINT_ASRS.equals(str))
        {
            // LBL-W0373=AS/RS
            return DisplayText.getText("LBL-W0373");
        }
        else if (SystemInParameter.POINT_MOVE.equals(str))
        {
            // LBL-W0383=移動
            return DisplayText.getText("LBL-W0383");
        }
        else if (SystemInParameter.POINT_RECEIVE.equals(str))
        {
            // LBL-W0366=入荷
            return DisplayText.getText("LBL-W0366");
        }
        else if (SystemInParameter.POINT_RETRIEVAL.equals(str))
        {
            // LBL-W0368=出庫
            return DisplayText.getText("LBL-W0368");
        }
        else if (SystemInParameter.POINT_RFT.equals(str))
        {
            // LBL-W0324=RFT
            return DisplayText.getText("LBL-W0324");
        }
        else if (SystemInParameter.POINT_PCART.equals(str))
        {
            // LBL-W0320=Pカート
            return DisplayText.getText("LBL-W0320");
        }
        else if (SystemInParameter.POINT_SHIPPING.equals(str))
        {
            // LBL-W0370=出荷
            return DisplayText.getText("LBL-W0370");
        }
        else if (SystemInParameter.POINT_SORT.equals(str))
        {
            // LBL-W0369=仕分
            return DisplayText.getText("LBL-W0369");
        }
        else if (SystemInParameter.POINT_SPACE.equals(str))
        {
            // 指定なし
            return "";
        }
        else if (SystemInParameter.POINT_STORAGE.equals(str))
        {
            // LBL-W0367=入庫
            return DisplayText.getText("LBL-W0367");
        }
        else if (SystemInParameter.POINT_INVENT.equals(str))
        {
            // LBL-W0139=棚卸
            return DisplayText.getText("LBL-W0139");
        }
        else if (SystemInParameter.POINT_REPLENISHMENT.equals(str))
        {
            // LBL-W0496=補充
            return DisplayText.getText("LBL-W0496");
        }
        else if (SystemInParameter.POINT_NOPLANSTORAGE.equals(str))
        {
            // LBL-W0384=予定外入庫
            return DisplayText.getText("LBL-W0384");
        }
        else if (SystemInParameter.POINT_NOPLANRETRIEVAL.equals(str))
        {
            // LBL-W0385=予定外出庫
            return DisplayText.getText("LBL-W0385");
        }
        else if (SystemInParameter.POINT_RESTORING.equals(str))
        {
            // LBL-W0608=再入庫
            return DisplayText.getText("LBL-W0608");
        }
        else if (SystemInParameter.POINT_ASRS_STOCK.equals(str))
        {
            // LBL-W0509=AS/RS在庫確認LBL-W0608=再入庫
            return DisplayText.getText("LBL-W0509");
        }
        else if (SystemInParameter.POINT_PCTRET.equals(str))
        {
            // LBL-P0237=ピッキング
            return DisplayText.getText("LBL-P0237");
        }
        else
        {
            return "";
        }
    }

    /**
     * 報告パッケージの名称を取得します。
     * @param reportDataType 報告区分
     * @return 報告パッケージ名称
     */
    public static String getReportData(String reportDataType)
    {

        if (SystemInParameter.DATA_TYPE_RECEIVE.equals(reportDataType))
        {
            // 入荷実績報告データ
            return DisplayText.getText("LBL-W0262");
        }
        else if (SystemInParameter.DATA_TYPE_STORAGE.equals(reportDataType))
        {
            // 入庫実績報告データ
            return DisplayText.getText("LBL-W0263");
        }
        else if (SystemInParameter.DATA_TYPE_RETRIEVAL.equals(reportDataType))
        {
            // 出庫実績報告データ
            return DisplayText.getText("LBL-W0264");
        }
        else if (SystemInParameter.DATA_TYPE_SORTING.equals(reportDataType))
        {
            // 仕分実績報告データ
            return DisplayText.getText("LBL-W0265");
        }
        else if (SystemInParameter.DATA_TYPE_SHIPPING.equals(reportDataType))
        {
            // 出荷実績報告データ
            return DisplayText.getText("LBL-W0266");
        }
        else if (SystemInParameter.DATA_TYPE_MOVEMENT.equals(reportDataType))
        {
            // 在庫移動実績報告データ
            return DisplayText.getText("LBL-W0267");
        }
        else if (SystemInParameter.DATA_TYPE_STOCK.equals(reportDataType))
        {
            // 在庫情報データ
            return DisplayText.getText("LBL-W0489");
        }
        else if (SystemInParameter.DATA_TYPE_INVENTORY.equals(reportDataType))
        {
            // 棚卸実績報告データ
            return DisplayText.getText("LBL-W0268");
        }
        else if (SystemInParameter.DATA_TYPE_NOPLAN_STORAGE.equals(reportDataType))
        {
            // 予定外入庫実績報告データ
            return DisplayText.getText("LBL-W0269");
        }
        else if (SystemInParameter.DATA_TYPE_NOPLAN_RETRIEVAL.equals(reportDataType))
        {
            // 予定外出庫実績報告データ
            return DisplayText.getText("LBL-W0270");
        }
        else if (SystemInParameter.DATA_TYPE_CROSSDOCK.equals(reportDataType))
        {
            // クロスドック
            return DisplayText.getText("LBL-W0554");
        }
        else if (SystemInParameter.DATA_TYPE_PCTINSTOCK_RESULT.equals(reportDataType))
        {
            // PCT入荷実績報告データ
            return DisplayText.getText("LBL-P0005");
        }
        else if (SystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT.equals(reportDataType))
        {
            // PCT出庫実績報告データ
            return DisplayText.getText("LBL-P0006");
        }
        else if (SystemInParameter.DATA_TYPE_PCTINVENTORY_RESULT.equals(reportDataType))
        {
            // PCT棚卸実績報告データ
            return DisplayText.getText("LBL-P0007");
        }
        else if (SystemDefine.DATA_TYPE_NO_PLAN_INOUT.equals(reportDataType))
        {
            // 予定外入出庫実績データ
            return DisplayText.getText("CMB-W0067");
        }
        else if (SystemInParameter.DATA_TYPE_PCTITEM_MASTER.equals(reportDataType))
        {
            // PCT商品マスタデータ
            return DisplayText.getText("LBL-P0003");
        }
        else if (SystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT_FILE.equals(reportDataType))
        {
            // PCT出庫実績報告データ
            return DisplayText.getText("LBL-P0006");
        }
        else
        {
            return "";
        }
    }

    /**
     * データ報告環境設定の報告単位名称を取得します。
     * @param reportDataType 報告単位区分
     * @return 報告単位名称
     */
    public static String getDataReport(String reportDataType)
    {
        if (SystemInParameter.REPORT_PLANCOLLECT.equals(reportDataType))
        {
            // 予定単位（集約）
            return DisplayText.getText("LBL-W0512");
        }
        else if (SystemInParameter.REPORT_PLAN.equals(reportDataType))
        {
            // 予定単位（明細）
            return DisplayText.getText("LBL-W0513");
        }
        else if (SystemInParameter.REPORT_WORK.equals(reportDataType))
        {
            // 作業単位
            return DisplayText.getText("LBL-W0514");
        }
        else
        {
            return "";
        }
    }

    /**
     * TC/DCフラグの名称を取得します
     * @param flg TC/DCフラグ
     * @return TC/DCフラグ名称
     */
    public static String getTCDCFlag(String flg)
    {
        if (SystemDefine.TCDC_FLAG_DC.equals(flg))
        {
            // DC
            return DisplayText.getText("LBL-W0559");
        }
        else if (SystemDefine.TCDC_FLAG_TC.equals(flg))
        {
            // TC
            return DisplayText.getText("LBL-W0558");
        }
        else
        {
            return "";
        }
    }

    /**
     * PCT作業テーブルの作業状態フラグから、名称を返します。<BR>
     * <CODE>PCTRetrievalInParameter.STATUS_FLAG_UNWORK</CODE> : 未開始 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKING</code> : 作業中 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED</code> : 作業済 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_COMPLETION</code> : 完了 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION</code> : メンテ完了 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_DELETE</code> : 削除 <BR>
     * 上記以外 : 全て <BR>
     * @param  status 作業状態フラグ
     * @return 作業状態フラグ名称
     */
    public static String getPctWorkingStatus(String status)
    {
        // 作業状態
        if (PCTRetrievalInParameter.STATUS_FLAG_UNWORK.equals(status))
        {
            // 未開始
            return DisplayText.getText("CMB-W0008");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKING.equals(status))
        {
            // 作業中
            return DisplayText.getText("CMB-W0006");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED.equals(status))
        {
            // 作業済
            return DisplayText.getText("CMB-P0002");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(status))
        {
            // 完了
            return DisplayText.getText("CMB-W0007");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION.equals(status))
        {
            // メンテ完了
            return DisplayText.getText("CMB-P0001");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION.equals(status))
        {
            // 欠品完了
            return DisplayText.getText("CMB-P0006");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_DELETE.equals(status))
        {
            // 削除
            return DisplayText.getText("CMB-P0003");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_WEIGHT_UNREGIST.equals(status))
        {
            // 重量未登録
            return DisplayText.getText("CMB-P0005");
        }
        else
        {
            // 全て
            return DisplayText.getText("CMB-W0004");
        }
    }

    /**
     * PCT作業テーブルの作業状態フラグから、名称を返します。<BR>
     * <CODE>PCTRetrievalInParameter.STATUS_FLAG_UNWORK</CODE> : 未作業 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKING</code> : 作業中 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED</code> : 作業済 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_COMPLETION</code> : 完了 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION</code> : 欠品完了 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION</code> : メンテ完了 <BR>
     * <code>PCTRetrievalInParameter.STATUS_FLAG_DELETE</code> : 削除 <BR>
     * 上記以外 : 全て <BR>
     * @param  status 作業状態フラグ
     * @return 作業状態フラグ名称
     */
    public static String getPctWorkingStatus2(String status)
    {
        // 作業状態
        if (PCTRetrievalInParameter.STATUS_FLAG_UNWORK.equals(status))
        {
            // 未作業
            return DisplayText.getText("CMB-W0005");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKING.equals(status))
        {
            // 作業中
            return DisplayText.getText("CMB-W0006");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED.equals(status))
        {
            // 作業済
            return DisplayText.getText("CMB-P0002");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(status))
        {
            // 完了
            return DisplayText.getText("CMB-W0007");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION.equals(status))
        {
            // 欠品完了
            return DisplayText.getText("CMB-P0006");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION.equals(status))
        {
            // メンテ完了
            return DisplayText.getText("CMB-P0001");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_DELETE.equals(status))
        {
            // 削除
            return DisplayText.getText("CMB-P0003");
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_WEIGHT_UNREGIST.equals(status))
        {
            // 重量未登録
            return DisplayText.getText("CMB-P0005");
        }
        else
        {
            // 全て
            return DisplayText.getText("CMB-W0004");
        }
    }

    /**
     * PCTレベルの名称を取得します。
     * <CODE>SystemDefine.LEVEL_NO_A</CODE> : レベルＡ <BR>
     * <CODE>SystemDefine.LEVEL_NO_B</CODE> : レベルＢ <BR>
     * <CODE>SystemDefine.LEVEL_NO_C</CODE> : レベルＣ  <BR>
     * @param levelType 増減区分
     * @return レベル名称
     */
    public static String getLevelName(String levelType)
    {

        if (SystemDefine.LEVEL_NO_A.equals(levelType))
        {
            // レベルＡ
            return DisplayText.getText("LBL-P0211");
        }
        else if (SystemDefine.LEVEL_NO_B.equals(levelType))
        {
            // レベルＢ
            return DisplayText.getText("LBL-P0212");
        }
        else if (SystemDefine.LEVEL_NO_C.equals(levelType))
        {
            // レベルＣ
            return DisplayText.getText("LBL-P0213");
        }
        else
        {
            return "";
        }
    }

    /**
     * 商品重量の登録あり・なしを取得します。
     * @param weightUnregist 重量登録フラグ
     * @return 重量登録あり・重量登録なし
     */
    public static String getWeightFlag(boolean weightUnregist)
    {
        if (weightUnregist)
        {
            return DisplayText.getText("LBL-P0206");
        }
        else
        {
            return DisplayText.getText("LBL-P0207");
        }
    }

    /**
     * Pカートの作業種別の名称を取得します。<BR>
     * <CODE>SystemDefine.JOB_TYPE_STARTING</CODE> : 起動中<BR>
     * <CODE>SystemDefine.JOB_TYPE_UNSTART</CODE> : 未開始<BR>
     * <CODE>SystemDefine.JOB_TYPE_RECEIVING</CODE> : 入荷<BR>
     * <CODE>SystemDefine.JOB_TYPE_RETRIEVAL</CODE> : 出庫<BR>
     * <CODE>SystemDefine.JOB_TYPE_INVENTORY</CODE> : 棚卸<BR>
     * @param jobType 作業種別
     * @return 作業種別名称
     */
    public static String getPCTJobType(String jobType)
    {

        if (SystemDefine.JOB_TYPE_UNSTART.equals(jobType))
        {
            // 起動中
            return DisplayText.getText("LBL-W0322");
        }
        else if (SystemDefine.JOB_TYPE_RECEIVING.equals(jobType))
        {
            // 入荷
            return DisplayText.getText("CMB-W0012");
        }

        else if (SystemDefine.JOB_TYPE_RETRIEVAL.equals(jobType))
        {
            // 出庫
            return DisplayText.getText("CMB-W0011");
        }

        else if (SystemDefine.JOB_TYPE_INVENTORY.equals(jobType))
        {
            // 棚卸
            return DisplayText.getText("CMB-W0015");
        }
        else
        {
            return "";
        }
    }

    /**
     * PCTバッチ開始/ｷｬﾝｾﾙ状態フラグの名称を取得する<BR>
     * <code>SystemDefine.SEARCH_ALL</code> : 全て <BR>
     * <code>RetrievalSystemDefine.SCH_FLAG_NOT_SCHEDULE</code> : 未開始 <BR>
     * <code>RetrievalSystemDefine.SCH_FLAG_SCHEDUL</code> : 開始済 <BR>
     * @param schFlg スケジュール状態フラグ
     * @param status
     * @param reportFlag
     * @return スケジュール状態名称
     */
    public static String getPCTStatus(String schFlg, String status, String reportFlag)
    {
        if (!StringUtil.isBlank(schFlg))
        {
            if (SystemDefine.SCH_FLAG_NOT_SCHEDULE.equals(schFlg))
            {
                // 未開始
                return DisplayText.getText("CMB-W0008");
            }
            else if (SystemDefine.SCH_FLAG_SCHEDULE.equals(schFlg))
            {
                if (SystemDefine.STATUS_FLAG_UNSTART.equals(status))
                {
                    // 開始済
                    return DisplayText.getText("CMB-W0009");
                }
                else if (SystemDefine.STATUS_FLAG_NOWWORKING.equals(status))
                {
                    // 作業中
                    return DisplayText.getText("CMB-W0006");
                }
                else
                {
                    return "";
                }

            }
            else
            {
                return "";
            }
        }
        else if (!StringUtil.isBlank(reportFlag))
        {

            if (SystemDefine.REPORT_FLAG_NOT_REPORT.equals(reportFlag))
            {
                // 未報告
                return DisplayText.getText("LBL-W0329");
            }
            else if (SystemDefine.REPORT_FLAG_REPORT.equals(reportFlag))
            {
                // 報告済
                return DisplayText.getText("LBL-W0330");
            }
            else
            {
                return "";
            }
        }
        else
        {
            return "";
        }
    }


    /**
     * 操作区分の名称を返します。<BR>
     * <CODE>SystemDefine.OPELOG_SETTING</CODE> : 設定<BR>
     * <CODE>SystemDefine.OPELOG_REGIST</CODE> : 登録<BR>
     * <CODE>SystemDefine.OPELOG_MODIFY</CODE> : 修正<BR>
     * <CODE>SystemDefine.OPELOG_DELETE</CODE> : 削除<BR>
     * <CODE>SystemDefine.OPELOG_ALL_DELETE</CODE> : 全削除<BR>
     * <CODE>SystemDefine.OPELOG_CANCEL</CODE> : キャンセル<
     * <CODE>SystemDefine.OPELOG_PRINT</CODE> : 印刷<
     * <CODE>SystemDefine.OPELOG_XLS</CODE> : XLS<
     * <CODE>SystemDefine.OPELOG_CSV</CODE> : CSV<
     * <CODE>SystemDefine.OPELOG_PRINT_LIST</CODE> : プレビュー<
     * <CODE>SystemDefine.OPELOG_AUTO_LOADING</CODE> : 自動取込<
     * <CODE>SystemDefine.OPELOG_MANUAL_LOADING</CODE> : 手動取込<
     * <CODE>SystemDefine.OPELOG_AUTO_REPORT</CODE> : 自動報告<
     * <CODE>SystemDefine.OPELOG_MANUAL_REPORT</CODE> : 手動報告<
     * @param OperationType 操作区分
     * @return 操作区分名称
     */
    public static String getOperationType(String OperationType)
    {

        if (SystemDefine.OPELOG_SETTING.equals(OperationType))
        {
            // 設定
            return DisplayText.getText("LBL-T0196");
        }
        else if (SystemDefine.OPELOG_REGIST.equals(OperationType))
        {
            // 登録
            return DisplayText.getText("LBL-T0180");
        }

        else if (SystemDefine.OPELOG_MODIFY.equals(OperationType))
        {
            // 修正
            return DisplayText.getText("LBL-T0194");
        }

        else if (SystemDefine.OPELOG_DELETE.equals(OperationType))
        {
            // 削除
            return DisplayText.getText("LBL-T0195");
        }
        else if (SystemDefine.OPELOG_ALL_DELETE.equals(OperationType))
        {
            // 全削除
            return DisplayText.getText("LBL-T0197");
        }
        else if (SystemDefine.OPELOG_CANCEL.equals(OperationType))
        {
            // キャンセル
            return DisplayText.getText("LBL-T0198");
        }
        else if (SystemDefine.OPELOG_PRINT.equals(OperationType))
        {
            // 印刷
            return DisplayText.getText("LBL-T0199");
        }
        else if (SystemDefine.OPELOG_XLS.equals(OperationType))
        {
            // XLS
            return DisplayText.getText("LBL-T0200");
        }
        else if (SystemDefine.OPELOG_CSV.equals(OperationType))
        {
            // CSV
            return DisplayText.getText("LBL-T0271");
        }
        else if (SystemDefine.OPELOG_PRINT_LIST.equals(OperationType))
        {
            // プレビュー
            return DisplayText.getText("LBL-T0202");
        }
        else if (SystemDefine.OPELOG_AUTO_LOADING.equals(OperationType))
        {
            // 自動取込
            return DisplayText.getText("LBL-T0203");
        }
        else if (SystemDefine.OPELOG_MANUAL_LOADING.equals(OperationType))
        {
            // 手動取込
            return DisplayText.getText("LBL-T0204");
        }
        else if (SystemDefine.OPELOG_AUTO_REPORT.equals(OperationType))
        {
            // 自動報告
            return DisplayText.getText("LBL-T0205");
        }
        else if (SystemDefine.OPELOG_MANUAL_REPORT.equals(OperationType))
        {
            // 手動報告
            return DisplayText.getText("LBL-T0206");
        }
        else
        {
            return "";
        }
    }

    /**
     * 出荷先優先度の名称を返却します。
     * <CODE>SystemDefine.JOB_PRIORITY_HIGH</CODE>：高<BR>
     * <CODE>SystemDefine.JOB_PRIORITY_INSIDE</CODE>：中<BR>
     * <CODE>SystemDefine.JOB_PRIORITY_LOW</CODE>：低<BR>
     * @param jobPriority 作業優先度
     * @return String 作業優先度名称
     */
    public static String getJobPriority(int jobPriority)
    {
        if (SystemDefine.JOB_PRIORITY_HIGH == jobPriority)
        {
            // 高
            return DisplayText.getText("LBL-P0217");
        }
        else if (SystemDefine.JOB_PRIORITY_INSIDE == jobPriority)
        {
            // 中
            return DisplayText.getText("LBL-P0218");
        }
        else if (SystemDefine.JOB_PRIORITY_LOW == jobPriority)
        {
            // 低
            return DisplayText.getText("LBL-P0219");
        }
        else
        {
            return "";
        }
    }

    /**
     * 入庫作業リストの名称を返却します。
     * <CODE>SystemDefine.JOB_TYPE_NOPLAN_STORAGE</CODE>かつAS/RSエリア：AS/RS入庫作業リスト<BR>
     * <CODE>SystemDefine.JOB_TYPE_NOPLAN_STORAGE</CODE>かつ平置エリア：平置入庫作業リスト<BR>
     * <CODE>SystemDefine.JOB_TYPE_RESTORING</CODE>：AS/RS再入庫作業リスト<BR>
     * @param jobtype 作業区分
     * @param areatype エリア区分
     * @return String 入庫作業リストの名称
     */
    public static String getStorageListName(String jobtype, String areatype)
    {
        if (SystemDefine.JOB_TYPE_NOPLAN_STORAGE.equals(jobtype))
        {
            if (SystemDefine.AREA_TYPE_ASRS.equals(areatype))
            {
                // AS/RS入庫作業リスト
                return DisplayText.getText("CMB-W0058");
            }
            else
            {
                // 平置入庫作業リスト
                return DisplayText.getText("CMB-W0059");
            }
        }
        else if (SystemDefine.JOB_TYPE_RESTORING.equals(jobtype))
        {
            // AS/RS再入庫作業リスト
            return DisplayText.getText("CMB-W0073");
        }

        return "";
    }

    /**
     * 出庫作業リストの名称を返却します。
     * @param jobtype 作業区分
     * @param areatype エリア区分
     * @return String 出庫作業リストの名称
     */
    public static String getRetrievalListName(String jobtype, String areatype)
    {
        if (SystemDefine.JOB_TYPE_INVENTORY.equals(jobtype))
        {
            // AS/RS入庫作業リスト
            return DisplayText.getText("CMB-W0062");
        }
        else
        {
            if (SystemDefine.AREA_TYPE_ASRS.equals(areatype))
            {
                // AS/RS出庫作業リスト
                return DisplayText.getText("CMB-W0060");
            }
            else
            {
                // 平置出庫作業リスト
                return DisplayText.getText("CMB-W0061");
            }
        }
    }

    /**
     * 一時商品区分の名称を返却します。
     * <CODE>SystemDefine.TEMPORARY_TYPE_NORMAL</CODE>：通常<BR>
     * <CODE>SystemDefine.TEMPORARY_TYPE_TEMPORARY</CODE>：一時商品<BR>
     * @param type 一時商品区分
     * @return String 一時商品区分の名称
     */
    public static String getTemporaryType(String type)
    {
        if (SystemDefine.TEMPORARY_TYPE_NORMAL.equals(type))
        {
            // 通常
            return DisplayText.getText("LBL-W1428");
        }
        else if (SystemDefine.TEMPORARY_TYPE_TEMPORARY.equals(type))
        {
            // 一時
            return DisplayText.getText("LBL-W1427");
        }
        return "";
    }

    /**
     * ステーション種別(運用種別)の名称を返却します。
     * <CODE>SystemDefine.STATION_TYPE_OTHER</CODE>：その他<BR>
     * <CODE>SystemDefine.STATION_TYPE_IN</CODE>：入庫<BR>
     * <CODE>SystemDefine.STATION_TYPE_OUT</CODE>：出庫<BR>
     * <CODE>SystemDefine.STATION_TYPE_INOUT</CODE>：入出庫<BR>
     * @param type ステーション種別
     * @return String ステーション種別の名称
     */
    public static String getStationType(String type)
    {
        if (SystemDefine.STATION_TYPE_OTHER.equals(type))
        {
            // その他
            return DisplayText.getText("LBL-W1431");
        }
        else if (SystemDefine.STATION_TYPE_IN.equals(type))
        {
            // 入庫
            return DisplayText.getText("LBL-W0367");
        }
        else if (SystemDefine.STATION_TYPE_OUT.equals(type))
        {
            // 出庫
            return DisplayText.getText("LBL-W0368");
        }
        else if (SystemDefine.STATION_TYPE_INOUT.equals(type))
        {
            // 入出庫
            return DisplayText.getText("LBL-W1432");
        }
        return "";
    }

    /**
     * 送受信データの名称を返却します。
     * @param jobType 作業種別
     * @param  exchangeType 送受信区分
     * @return String データタイプ名称
     */
    public static String getDataType(String jobType, String exchangeType)
    {

        // 受信
        if (SystemDefine.EXCHANGE_TYPE_RECEIVE.equals(exchangeType))
        {
            if (SystemDefine.DATA_TYPE_RETRIEVAL.equals(jobType))
            {
                // 計画出庫予定データ
                return DisplayText.getText("CMB-W0065");
            }
            else if (SystemDefine.DATA_TYPE_MASTER_ITEM.equals(jobType))
            {
                // 商品マスタデータ
                return DisplayText.getText("CMB-W0064");
            }
        }
        // 送信
        else if (SystemDefine.EXCHANGE_TYPE_SEND.equals(exchangeType))
        {
            if (SystemDefine.DATA_TYPE_RETRIEVAL.equals(jobType))
            {
                // 計画出庫実績データ
                return DisplayText.getText("CMB-W0066");
            }
            else if (SystemDefine.DATA_TYPE_NO_PLAN_INOUT.equals(jobType))
            {
                // 予定外入出庫実績データ
                return DisplayText.getText("CMB-W0067");
            }
        }
        else
        {
            // 全データ
            return DisplayText.getText("CMB-W0063");
        }
        return "";
    }

    /**
     * FA用報告パッケージの名称を取得します。
     * @param reportDataType 報告区分
     * @return 報告パッケージ名称
     */
    public static String getFaReportData(String reportDataType)
    {

        if (SystemInParameter.DATA_TYPE_RETRIEVAL.equals(reportDataType))
        {
            // 出庫実績
            return DisplayText.getText("LBL-W0264");
        }
        else if (SystemInParameter.DATA_TYPE_STOCK.equals(reportDataType))
        {
            // 在庫情報
            return DisplayText.getText("LBL-W0489");
        }
        else if (SystemDefine.DATA_TYPE_NO_PLAN_INOUT.equals(reportDataType))
        {
            // 予定外入出庫実績
            return DisplayText.getText("LBL-W1442");
        }
        else
        {
            return "";
        }
    }


    /**
     * FA用取込区分の名称を取得します。
     * @param loadDataType 取込区分
     * @return 取込区分名称
     */
    public static String getFaLoadDataType(String loadDataType)
    {
        if (SystemInParameter.DATA_TYPE_RETRIEVAL.equals(loadDataType))
        {
            // 出庫予定データ
            return DisplayText.getText("LBL-W0259");
        }
        else if (SystemInParameter.DATA_TYPE_ITEM_MASTER.equals(loadDataType))
        {
            // 商品マスタ
            return DisplayText.getText("LBL-T0215");
        }
        else
        {
            return "";
        }
    }

    /**
     * 交換データ通信履歴の状態を返却します。
     * <CODE>SystemDefine.EXCHANGE_STATUS_NORMAL</CODE>：正常完了<BR>
     * <CODE>SystemDefine.EXCHANGE_STATUS_FILE_INVALID</CODE>：ファイル名が不正<BR>
     * <CODE>SystemDefine.EXCHANGE_STATUS_FILE_NOT_FOUND</CODE>：ファイル取得エラー<BR>
     * <CODE>SystemDefine.EXCHANGE_STATUS_FILE_EXISTS</CODE>：同一ファイル名二重受信<BR>
     * <CODE>SystemDefine.EXCHANGE_STATUS_RECEIVE_ERROR</CODE>：受信中データ内エラー発生<BR>
     * <CODE>SystemDefine.EXCHANGE_STATUS_FILE_DELETE_ERROR</CODE>：ファイル削除中エラー<BR>
     * <CODE>SystemDefine.EXCHANGE_STATUS_EXCEPTION</CODE>：例外発生<BR>
     * @param type ステーション種別
     * @param locale ロケール情報
     * @return String ステーション種別の名称
     */
    public static String getExchangeStatus(String type, Locale locale)
    {
        if (SystemDefine.EXCHANGE_STATUS_NORMAL.equals(type))
        {
            //LBL-W6001=正常完了
            return DisplayText.getText(locale, "LBL-W6001");
        }
        else if (SystemDefine.EXCHANGE_STATUS_SKIP.equals(type))
        {
            //LBL-W6011=スキップあり
            return DisplayText.getText(locale, "LBL-W6011");
        }
        else if (SystemDefine.EXCHANGE_STATUS_ALL_SKIP.equals(type))
        {
            //LBL-W6012=全スキップ
            return DisplayText.getText(locale, "LBL-W6012");
        }
        else if (SystemDefine.EXCHANGE_STATUS_FILE_INVALID.equals(type))
        {
            //LBL-W6008=ファイル名が不正
            return DisplayText.getText(locale, "LBL-W6008");
        }
        else if (SystemDefine.EXCHANGE_STATUS_FILE_NOT_FOUND.equals(type))
        {
            //LBL-W6009=ファイル取得エラー
            return DisplayText.getText(locale, "LBL-W6009");
        }
        else if (SystemDefine.EXCHANGE_STATUS_FILE_EXISTS.equals(type))
        {
            //LBL-W6002=同一ファイル名二重受信
            return DisplayText.getText(locale, "LBL-W6002");
        }
        else if (SystemDefine.EXCHANGE_STATUS_RECEIVE_ERROR.equals(type))
        {
            //LBL-W6003=受信中データ内エラー発生
            return DisplayText.getText(locale, "LBL-W6003");
        }
        else if (SystemDefine.EXCHANGE_STATUS_FILE_DELETE_ERROR.equals(type))
        {
            //LBL-W6005=ファイル削除中エラー
            return DisplayText.getText(locale, "LBL-W6005");
        }
        else if (SystemDefine.EXCHANGE_STATUS_EXCEPTION.equals(type))
        {
            //LBL-W6010=例外発生
            return DisplayText.getText(locale, "LBL-W6010");
        }
        return "";
    }

    /**
     * 混載の有無を取得します。<BR>
     * 
     * @param Mixed
     * @return 引当の名称
     */
    public static String getMixed(boolean Mixed)
    {
        if (Mixed)
        {
            // 混載有り
            return DisplayText.getText("LBL-W1439");
        }
        else
        {
            // 混載無し
            return "";
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
