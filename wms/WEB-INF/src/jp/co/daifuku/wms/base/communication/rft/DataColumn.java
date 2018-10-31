// $Id: DataColumn.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright 2004-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 電文、およびデータファイル中の各項目の長さを定義します。<BR>
 * 電文の基底クラスがこのインターフェースを継承します。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author $Author: arai $
 */
public interface DataColumn
{
    // Class fields --------------------------------------------------
    /**
     * STXの長さ
     */
    static final int LEN_STX = 1 ;

    /**
     * SEQの長さ(Byte)
     */
    static final int LEN_SEQ = 4;

    /**
     * 電文IDの長さ(Byte)
     */
    static final int LEN_ID = 4;

    /**
     * RFT送信時間の長さ(Byte)
     */
    static final int LEN_RFTSENDDATE = 6;

    /**
     * SERVSEND送信時間の長さ(Byte)
     */
    static final int LEN_SERVSENDDATE = 6;

    /**
     * RFT号機の長さ(Byte)
     */
    static final int LEN_RFTNO = 3;

    /**
     * ETXの長さ
     */
    static final int LEN_ETX = 1 ;

    /**
     * 担当者コードの長さ(byte)
     */
    static final int LEN_USER_ID = 8;

    /**
     * 担当者名の長さ
     */
    static final int LEN_WORKER_NAME = 20;

    /**
     * バスワードの長さ（byte）
     */
    static final int LEN_PASSWORD = 8;
    
    /**
     * 作業区分の長さ（byte）
     */
    static final int LEN_JOB_TYPE = 2;
    
    /**
     * 作業区分詳細の長さ(byte)
     */
    static final int LEN_JOB_DETAILS = 1;

    /**
     * 日付項目の長さ(byte)
     */
    static final int LEN_PLAN_DATE = 8;

    /**
     * 荷主コードの長さ(byte)
     */
    static final int LEN_CONSIGNOR_CODE = 16;
    
    /**
     * 荷主名の長さ(byte)
     */
    static final int LEN_CONSIGNOR_NAME = 40;

    /**
     * 仕入先コードの長さ(byte)
     */
    static final int LEN_SUPPLIER_CODE = 16;
    
    /**
     * 仕入先名称の長さ(byte)
     */
    static final int LEN_SUPPLIER_NAME = 40;

    /**
     * 出荷先コードの長さ(byte)
     */
    static final int LEN_CUSTOMER_CODE = 16;
    
    /**
     * 出荷先名称の長さ(byte)
     */
    static final int LEN_CUSTOMER_NAME = 40;

    /**
     * オーダーNo.の長さ(byte)
     */
    static final int LEN_ORDER_NO = 16;
    
    /**
     * 伝票番号の長さ(byte)
     */
    static final int LEN_TICKET_NO = 16;
    
    /**
     * 伝票行No.の長さ(byte)
     */
    static final int LEN_TICKET_LINE_NO = 3;
    
    /**
     * 商品コードの長さ(byte)
     */
    static final int LEN_ITEM_CODE = 16;
    
    /**
     * JANコードの長さ(byte)
     */
    static final int LEN_JAN_CODE = 16;
    
    /**
     * ボールITFの長さ(byte)
     */
    static final int LEN_BUNDLE_ITF = 16;
    
    /**
     * ケースITFの長さ(byte)
     */
    static final int LEN_ITF = 16;
    
    /**
     * 商品名称の長さ(byte)
     */
    static final int LEN_ITEM_NAME = 40;
    
    /**
     * ボール入数の長さ(byte)
     */
    static final int LEN_BUNDLE_ENTERING_QTY = 6;
    
    /**
     * ケース入数の長さ(byte)
     */
    static final int LEN_ENTERING_QTY = 6;
    
    /**
     * ロットNo.の長さ(byte)
     */
    static final int LEN_LOT_NO = 16;
    
    /**
     * 予定数の長さ(byte)
     */
    static final int LEN_PLAN_QTY = 9;
    
    /**
     * 実績数の長さ(byte)
     */
    static final int LEN_RESULT_QTY = 9;
    
    /**
     * CHECK MARKER 追加…
     * 残アイテム数の長さ(byte)
     */
    static final int LEN_PENDING_QTY = 9;

    /**
     * CHECK MARKER 追加…
     * 総ケース数の長さ(byte)
     */
    static final int LEN_TOTAL_CASE_QTY = 9;

    /**
     * CHECK MARKER 追加…
     * 総ピース数の長さ(byte)
     */
    static final int LEN_TOTAL_PIECE_QTY = 9;

    /**
     * ファイル名の長さ
     */
    static final int LEN_FILE_NAME = 30;

    /**
     * ファイルレコード数の長さ
     */
    static final int LEN_FILE_RECORD_NUMBER = 5;

    /**
     * バッチNo.の長さ(byte)
     */
    static final int LEN_BATCH_NO = 8;

    /**
     * CHECK MARKER 追加…
     * バースNo.の長さ(byte)
     */
    static final int LEN_BERTH_NO = 4;
    
    /**
     * 棚No（ロケーション）の長さ(byte)
     */
    static final int LEN_LOCATION = 8;

    /**
     * CHECK MARKER 追加…
     * 仕分場所の長さ(byte)
     */
    static final int LEN_SORTING_LOCATION = 16;

    /**
     * 在庫数の長さ(byte)
     */
    static final int LEN_STOCK_QTY = 9;

    /**
     * エリアNo.の長さ
     */
    static final int LEN_AREA_NO = 4;
    
    /**
     * エリア名称の長さ
     */
    static final int LEN_AREA_NAME = 40;
    
    /**
     * 移動作業Noの長さ(byte)
     */
    static final int LEN_MOVE_JOB_NO = 8;

    /**
     * 作業件数の長さ
     */
    static final int LEN_WORK_COUNT = 9;
    
    /**
     * 完了フラグの長さ(byte)
     */
    static final int LEN_COMPLETION_FLAG = 1;

    /**
     * 一覧選択フラグの長さ(byte)
     */
    static final int LEN_LIST_SELECTION_FLAG = 1;

    /**
     * 残アイテム数の長さ(byte)
     */
    static final int    LEN_REMAINING_ITEM_COUNT = 9;
    
    /**
     * 総アイテム数の長さ(byte)
     */
    static final int    LEN_TOTAL_ITEM_COUNT = 9;
    
    /**
     * 作業時間の長さ(byte)
     */
    static final int  LEN_WORK_TIME = 5;
    
    /**
     *  棚位置の長さ(byte)
     */
    static final int  LEN_LOCATION_SIDE = 1;
    
    /**
     *  箱替えオーダーの長さ(byte)
     */
    static final int LEN_BOX_INDEX = 1;
    
    /**
     * サーバ日時の長さ（byte)
     */
    static final int LEN_SERVER_DATE = 14;
    
    /**
     * 報告区分の長さ（byte)
     */
    static final int LEN_REPORT_FLAG = 1;
    
    /**
     * 端末区分の長さ（byte)
     */
    static final int LEN_TERMINAL_TYPE = 2;
    
    /**
     * 端末IPアドレスの長さ（byte)
     */
    static final int LEN_IP_ADDRESS = 15;

    /**
     * 空棚区分の長さ(byte)
     */
    static final int    LEN_EMPTY_KIND = 1;
    
    /**
     * 行Noの長さ(byte)
     */
    static final int LEN_LINE_NO = 5;
    
    /**
     * データサイズの長さ(byte)
     */
    static final int LEN_DATA_SIZE = 3;
    
    /**
     * データ内容の長さ(byte)
     */
    static final int LEN_DATA_CONTENTS = 512;
    
    /**
     * 作業中IDの長さ(byte)
     */
    static final int LEN_WORKING_ID = 4;
    
    /**
     * 集約作業Noの長さ(byte)
     */
    static final int LEN_COLLECT_JOB_NO = 8;
    
    /**
     * 誤検回数の長さ(byte)
     */
    static final int LEN_INSPECTION_ERR_COUNT = 5;

    /**
     * スキャンミス回数の長さ(byte)
     */
    static final int LEN_MISS_COUNT = 5;

    /**
     * 総仕分先数の長さ(byte)
     */
    static final int LEN_TOTAL_SORTING_COUNT = 9;
    
    /**
     * 総仕分数の長さ(byte)
     */
    static final int LEN_TOTAL_SORTING_QTY = 9;
    
    /**
     * 棚表示形式の長さ
     */
    static final int LEN_LOCATION_STYLE = 16;

    /**
     * 応答フラグの長さ
     */
    static final int LEN_ANSWER_CODE = 1;

    /**
     * エラー詳細の長さ(byte)
     */
    static final int LEN_ERROR_DETAILS = 2;

    /**
     * 作業種別の長さ(byte)
     * (注意:作業区分と混同しない事。作業種別はAS/RSで、作業区分はそれ以外で使用する。)
     */
    static final int LEN_WORK_TYPE = 2;

    /**
     * 出庫指示詳細の長さ(byte)
     */
    static final int LEN_RETRIEVAL_DETAIL = 1;

    /**
     * 強制入庫区分の長さ(byte)
     */
    static final int LEN_FORCE_STRAGE_FLAG = 1;
    
    /**
     * 強制開始区分の長さ(byte)
     */
    static final int LEN_FORCE_START_FLAG = 1;
    
    /**
     * パッケージ有無フラグの長さ(byte)
     */
    static final int LEN_PACKAGE_EXIST_FLAG = 1;
    
    /**
     * データ有無フラグの長さ(byte)
     */
    static final int LEN_DATA_EXIST_FLAG = 1;
    
    /**
     * 棚管理方式の長さ(byte)
     */
    static final int LEN_LOCATION_TYPE = 1;
    
    /**
     * 棚卸数の長さ(byte)
     */
    static final int LEN_INVENTORY_QTY = 9;
    
    /**
     * ロット入数の長さ(byte)
     */
    static final int LEN_LOT_ENTERING_QTY = 5;
    
    /**
     * メッセージの長さ(byte)
     */
    static final int LEN_MESSAGE = 50;
    
}
//end of interface
