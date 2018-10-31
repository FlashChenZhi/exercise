// $Id: AnswerCode.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * RFT通信の応答電文での応答フラグの値を定義します。<BR>
 * ただし、電文によっては異なる値が使用されることがあり、
 * その場合は各電文実装クラスで定義が上書きされます。<BR>
 * そのため、特別な事情が無い限りこのInterfaceの値を直接参照せず、
 * 実装クラスのメンバとして参照してください。
 * 
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public interface AnswerCode
{
    // Class fields --------------------------------------------------
    /**
     * 応答フラグの正常を表すフィールド
     */
    public static final String ANS_CODE_NORMAL = "0";

    /**
     * 応答フラグの他端末で作業中を表すフィールド
     */
    public static final String ANS_CODE_WORKING = "1";

    /**
     * 応答フラグの作業完了済みを表すフィールド
     */
    public static final String ANS_CODE_COMPLETION = "2";

    /**
     * 応答フラグの在庫管理無しを表すフィールド
     */
    public static final String ANS_CODE_STOCK_PACKAGE_NO = "3";

    /**
     * 応答フラグの出庫可能数超過を表すフィールド
     */
    public static final String ANS_CODE_RETRIEVAL_OVER = "4";
    
    /**
     * 応答フラグの入荷数超過（MAX_TOTAL_QTY以上）を表すフィールド
     */
    public static final String ANS_CODE_RECEIVING_OVER = "4";

    /**
     * 応答フラグの日時更新処理中を表すフィールド
     */
    public static final String ANS_CODE_DAILY_UPDATING = "5";

    /**
     * 応答フラグのメンテナンス中を表すフィールド
     */
    public static final String ANS_CODE_MAINTENANCE = "6";

    /**
     * 応答フラグの複数件該当を表すフィールド
     */
    public static final String ANS_CODE_SOME_DATA = "7";

    /**
     * 応答フラグの作業中データありを表すフィールド
     */
    public static final String ANS_CODE_WORKING_DATA_EXISTS = "7";

    /**
     * 応答フラグの該当データなしを表すフィールド
     */
    public static final String ANS_CODE_NULL = "8";

    /**
     * 応答フラグのユーザ認証失敗、該当ユーザ無しを表すフィールド
     */
    public static final String ANS_CODE_NOT_LOGIN = "8";

    /**
     * 応答フラグのエラーを表すフィールド
     */
    public static final String ANS_CODE_ERROR = "9";


    /**
     * エラー詳細インターフェース
     */
    public interface ErrorDetails
    {
        /**
         * エラー詳細の正常"00"を表すフィールド
         */
        public static final String NORMAL = "00";

        /**
         * エラー詳細の予定日複数件該当"01"を表すフィールド
         */
        public static final String PLAN_DAY_DUPLICATED = "01";

        /**
         * エラー詳細の仕入先複数件該当"02"を表すフィールド
         */
        public static final String SUPPLIER_DUPLICATED = "02";

        /**
         * エラー詳細の伝票複数件該当"03"を表すフィールド
         */
        public static final String TICKET_NO_DUPLICATED = "03";

        /**
         * エラー詳細のエリア棚複数件該当"04"を表すフィールド
         */
        public static final String AREA_LOCATION_DUPLICATED = "04";

        /**
         * エラー詳細のロット複数件該当"05"を表すフィールド
         */
        public static final String LOT_NO_DUPLICATED = "05";

        /**
         * エラー詳細の移動作業複数件該当"07"を表すフィールド
         */
        public static final String JOB_NO_DUPLICATED = "07";

        /**
         * エラー詳細の商品コード複数件該当"09"を表すフィールド
         */
        public static final String ITEM_DUPLICATED = "09";

        /**
         * エラー詳細の在庫数オーバーフローを表すフィールド(OverflowException)
         */
        public static final String OVERFLOW = "10";

        /**
         * エラー詳細の棚フォーマットエラーを表すフィールド
         */
        public static final String ILLEGAL_LOCATION_FORMAT = "11";

        /**
         * エラー詳細のエリア無効を表すフィールド(AreaInvalidityException)
         */
        public static final String AREA_INVALIDITY = "12";

        /**
         * エラー詳細の対象データなしを表すフィールド(NotFoundException)
         */
        public static final String NULL = "20";

        /**
         * エラー詳細の他端末で更新済みを表すフィールド(OperatorException.ERR_ALREADY_UPDATED)
         */
        public static final String UPDATE_FINISH = "21";

        /**
         * エラー詳細の棚卸範囲外"23"を表すフィールド(OperatorException)
         */
        public static final String INVENT_LOCATION_OUTSIDE_RANGE = "23";

        /**
         * エラー詳細の棚卸確定済み"24"を表すフィールド(OperatorException)
         */
        public static final String INVENT_LOCATION_COMPLETE = "24";

        /**
         * エラー詳細の内部エラーを表すフィールド(Exception)
         */
        public static final String INTERNAL_ERROR = "30";

        /**
         * エラー詳細のパラメータエラーを表すフィールド(InvalidDefineException)
         */
        public static final String PARAMETER_ERROR = "31";

        /**
         * エラー詳細のＤＢアクセスエラーを表すフィールド(ReadWriteException,SQLException)
         */
        public static final String DB_ACCESS_ERROR = "32";

        /**
         * エラー詳細のＤＢ一意キーエラーを表すフィールド(DataExistsException,NoPrimaryException)
         */
        public static final String DB_UNIQUE_KEY_ERROR = "33";

        /**
         * エラー詳細のクラス生成エラーを表すフィールド(IllegalAccessException)
         */
        public static final String INSTACIATE_ERROR = "34";

        /**
         * エラー詳細のファイルI/Oエラーを表すフィールド(IOException)
         */
        public static final String I_O_ERROR = "35";

        /**
         * エラー詳細のDB設定値不正を表すフィールド(InvalidStatusException)
         */
        public static final String DB_INVALID_VALUE = "36";

        /**
         * エラー詳細の共通ロジックエラーを表すフィールド(ScheduleException)
         */
        public static final String SCHEDULE_ERROR = "37";

        /**
         * エラー詳細の集約処理オーバーフローを表すフィールド(OverflowException)
         */
        public static final String COLLECTION_OVERFLOW = "38";

        /**
         * エラー詳細の応答電文取得不可を表すフィールド
         */
        public static final String CANNOT_GET_RESPONSE_ID = "39";

        /**
         * エラー詳細の端末No不正を表すフィールド
         */
        public static final String ILLEGAL_TERMINAL_NUMBER = "41";

        /**
         * エラー詳細の起動時起動受信を表すフィールド
         */
        public static final String ALREADY_STARTED = "51";

        /**
         * エラー詳細の未起動時終了受信を表すフィールド
         */
        public static final String NON_START_FINISH = "52";

        /**
         * エラー詳細の休憩時休憩受信
         */
        public static final String ALREADY_RESTED = "53";

        /**
         * 作業開始エラー用インターフェース
         */
        public interface StartError
        {
            /** 作業者は他端末で作業中 */
            public static final String USER_ALREADY_WORK = "55";

            /** 端末は別ユーザログイン中 */
            public static final String TERMINAL_ALREADY_LOGIN = "56";
        }

        /**
         * 認証エラー用インターフェース
         */
        public interface Authentication
        {
            /** 認証の設定が不正 */
            //パスワード認証しない設定の時に、パスワードがセットされていた場合
            public static final String SETTING_ERROR = "57";

            /** ユーザ又はパスワード不正 */
            public static final String USER_PASSWORD_ERROR = "58";

            /** ロックされたユーザ */
            public static final String LOCKOUT_USER = "59";

            /** パスワード期限切れ */
            public static final String PASSWORD_EXPIRATION = "60";

            /** 権限不足 */
            public static final String AUTHORITY_INSUFFICIENT = "61";

            /** 仮パスワード */
            public static final String PASSWORD_TENTATIVE = "62";
        }

        /**
         * 棚未登録(エリアが棚管理エリアで、入庫時に指定された棚が、マスタに無い場合)
         */
        public static final String SHELF_UNREGISTRATION = "63";

        /**
         * 固定棚未登録(エリアが固定棚エリアで、入庫時に指定された棚が、固定棚登録されていない場合)
         */
        public static final String FIXED_SHELF_UNREGISTRATION = "64";

        //        /**
        //         * エラー詳細の「認証の設定が不正」を表すフィールド
        //         */
        //        public static final String  = "57";
        //        
        //        /**
        //         * エラー詳細の「ユーザ又はパスワード不正」を表すフィールド
        //         */
        //        public static final String  = "58";
    }
}
//end of interface
