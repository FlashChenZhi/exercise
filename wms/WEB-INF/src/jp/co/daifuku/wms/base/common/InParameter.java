//$Id: InParameter.java 6322 2009-12-03 01:27:57Z okamura $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * <CODE>InParameter</CODE>クラスは、BlueDog等の画面処理を行うクラスとスケジュール処理を行うクラス間での
 * パラメータの受渡しを行うために用意されたクラスです。<BR>
 * InParamererクラスには画面→スケジュール間で値の受渡しを行う項目をインスタンス変数に保持します。<BR>
 * 各パッケージ固有の項目を定義する場合はこのクラスを継承し、必要な項目を追加してください。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/02/20</TD><TD>T.Kishimoto</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 6322 $, $Date: 2009-12-03 10:27:57 +0900 (木, 03 12 2009) $
 * @author  $Author: okamura $
 */
public class InParameter
        extends Parameter
{
    // Class variables -----------------------------------------------
    /**
     * 検索条件(プルダウンなど) : 全て
     */
    public static final String SEARCH_ALL = "99";

    /**
     * 紐付く作業場・ステーションなし(平置エリアなど) : なし
     */
    public static final String SELECT_STATION_NONE = "STATIONNONE_";
    
    /**
     * 処理フラグ : 修正
     */
    public static final String PROCESS_FLAG_MODIFY = "0";

    /**
     * 処理フラグ : 削除
     */
    public static final String PROCESS_FLAG_DELETE = "1";

    /**
     * 処理フラグ : 全削除
     */
    public static final String PROCESS_FLAG_DELETE_ALL = "2";

    /**
     * 処理フラグ : 再発行
     */
    public static final String PROCESS_FLAG_REPRINT = "3";

    /**
     * 処理フラグ : 作業キャンセル
     */
    public static final String PROCESS_FLAG_WORK_CANCEL = "4";

    /**
     * 処理フラグ : 表示(進捗画面用)
     */
    public static final String PROCESS_FLAG_VIEW = "5";

    /**
     * 処理フラグ : 次頁(進捗画面用)
     */
    public static final String PROCESS_FLAG_NEXT_PAGE = "6";

    /**
     * 処理フラグ : 前頁(進捗画面用)
     */
    public static final String PROCESS_FLAG_PREVIOUS_PAGE = "7";

    /**
     * 処理フラグ : 入力
     */
    public static final String PROCESS_FLAG_INPUT = "8";

    /**
     * 処理フラグ：登録
     */
    public static final String PROCESS_FLAG_REGIST = "9";
    
    /**
     * 在庫有無　全て
     */ 
    public static final String STOCK_EXISTENCE_ALL = "0";

    /**
     * 在庫有無　有
     */     
    public static final String STOCK_EXISTENCE_EXISTENCE = "1";

    /**
     * 在庫有無　無
     */     
    public static final String STOCK_EXISTENCE_NOEXISTENCE = "2";
    
    /**
     * 検索対象テーブル : 予定
     */
    public static final String SEARCH_TABLE_PLAN = "1";

    /**
     * 検索対象テーブル : 作業
     */
    public static final String SEARCH_TABLE_WORK = "2";

    /**
     * 検索対象テーブル : 在庫
     */
    public static final String SEARCH_TABLE_STOCK = "3";

    /**
     * 検索対象テーブル : 実績
     */
    public static final String SEARCH_TABLE_RESULT = "4";

    /**
     * 検索対象テーブル : 実績送信
     */
    public static final String SEARCH_TABLE_HOSTSEND = "5";

    /**
     * 検索対象テーブル : マスタ
     */
    public static final String SEARCH_TABLE_MASTER = "6";

    /**
     * 検索対象テーブル：欠品
     */
    public static final String SEARCH_TABLE_SHORTAGE = "7";

    /**
     * 検索対象テーブル ： AS/RS棚情報
     */
    public static final String SEARCH_TABLE_SHELF = "8";

    /**
     * 検索対象テーブル : 作業者実績情報
     */
    public static final String SEARCH_TABLE_WORKERRESULT = "9";

    /**
     * 検索対象テーブル : 移動作業
     */
    public static final String SEARCH_TABLE_MOVE_WORK = "10";

    /**
     * 検索対象テーブル : 移動実績
     */
    public static final String SEARCH_TABLE_MOVE_RESULT = "11";

    /**
     * 検索対象テーブル : 棚卸作業
     */
    public static final String SEARCH_TABLE_INVENTORY_WORK = "12";

    /**
     * 検索対象テーブル : 棚卸実績
     */
    public static final String SEARCH_TABLE_INVENTORY_RESULT = "13";

    /**
     * 検索対象テーブル : 棚マスタ
     */
    public static final String SEARCH_TABLE_LOCATE = "14";

    /**
     * 検索対象テーブル : 在庫履歴情報
     */
    public static final String SEARCH_TABLE_STOCKHISTORY = "15";

    /**
     * 検索対象テーブル : 入荷作業情報
     */
    public static final String SEARCH_TABLE_RECEIVINGWORKINFO = "16";
    
    /**
     * 検索対象テーブル : 仕分実績送信情報
     */
    public static final String SEARCH_TABLE_SORTHOSTSEND = "16";

    /**
     * 検索対象テーブル : オーダー情報
     */
    public static final String SEARCH_TABLE_ORDERINFO = "17";
    
    // PickingCart用
    /**
     * 終了予測時間 : ロット基準
     */
    public static final String ENDPLANTIME_LOT_STANDARD = "1";

    /**
     * 終了予測時間 : オーダー基準
     */
    public static final String ENDPLANTIME_ORDER_STANDARD = "2";

    /**
     * 終了予測時間：行基準
     */
    public static final String ENDPLANTIME_LINE_STANDARD = "3";

    // InParameter
    /**
     * ボタン制御フラグ : 前次頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_ALL_OFF = "0";

    /**
     * ボタン制御フラグ : 前頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_PREVIOUS_OFF = "1";

    /**
     * ボタン制御フラグ : 次頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_NEXT_OFF = "2";

    /**
     * ボタン制御フラグ : 前次頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_ALL_ON = "3";

    /**
     * スケジュール処理フラグ : 未開始
     */
    public static final String SCH_FLAG_NOT_SCHEDULE = "0";

    /**
     * スケジュール処理フラグ : 開始済
     */
    public static final String SCH_FLAG_SCHEDULE = "1";

    /**
     * スケジュール処理フラグ : 完了
     */
    public static final String SCH_FLAG_COMPLTE = "4";

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 6322 $,$Date: 2009-12-03 10:27:57 +0900 (木, 03 12 2009) $");
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
