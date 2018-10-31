// $Id: EmConstants.java 7343 2010-03-04 02:39:43Z shibamoto $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager;

import jp.co.daifuku.Constants;

/**
 * <jp>定数定義インターフェイスです。<br></jp>
 * <en>It is the constant definition interface.<br></en>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/26</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7343 $, $Date: 2010-03-04 11:39:43 +0900 (木, 04 3 2010) $
 * @author  $Author: shibamoto $
 */
public interface EmConstants
        extends Constants
{

    // Class fields --------------------------------------------------


    // SQL ErrorCode
    /** <jp>一意制約違反<br></jp><en>Unique constraint. <br></en> */
    public static final int SQLERRORCODE_ORA0001 = 1;

    // Search patturn
    /** <jp>メニューリソースキーの検索キー <br></jp><en>Search patturn for main menu resource key. <br></en> */
    public static final String MENURESOUCEKEY = "MNU.*";

    /** <jp>サブメニューリソースキーの検索キー <br></jp><en>Search patturn for sub menu resource key. <br></en> */
    public static final String FUNCTIONRESOUCEKEY = "MTLE.*";

    /** <jp>機能画面のサブメニューボタンリソースキーの検索キー <br></jp><en>Search patturn for button in submenu of function resource key. <br></en> */
    public static final String BUTTONRESOUCEKEY = "MBTN.*";

    /** <jp>機能画面ページ名リソースキーの検索キー <br></jp><en>Search patturn for function screen title resource key. <br></en> */
    public static final String PAGENAMERESOUCEKEY = "TLE.*";


    // eManagerProperties KEY
    /** <jp>eManagerPropertiesのキー：DATASOUCENAME データソース名<br></jp><en>eManagerProperties Key : DATASOUCENAME<br></en> */
    public static final String EMPARAMKEY_DATASOURCENAME = "DATASOURCENAME";

    /** <jp>eManagerPropertiesのキー：DATASOURCEXML_PATH datasource.xmlのパス<br></jp><en>eManagerProperties Key : DATASOURCEXML_PATH<br></en> */
    public static final String EMPARAMKEY_DATASOURCEXML_PATH = "DATASOURCEXML_PATH";

    /** <jp>eManagerPropertiesのキー：LOGIN_URI ログイン画面のURI<br></jp><en>eManagerProperties Key : LOGIN_URI<br></en> */
    public static final String EMPARAMKEY_LOGINURI = "LOGIN_URI";

    /** <jp>eManagerPropertiesのキー：LOGOUT_URI ログアウト画面のURI<br></jp><en>eManagerProperties Key : AUTOLOGIN_URI<br></en> */
    public static final String EMPARAMKEY_LOGOUT_URI = "LOGOUT_URI";

    /** <jp>eManagerPropertiesのキー：AUTOLOGIN_URI 自動ログイン画面のURI<br></jp><en>eManagerProperties Key : AUTOLOGIN_URI<br></en> */
    public static final String EMPARAMKEY_AUTOLOGINURI = "AUTOLOGIN_URI";

    /** <jp>eManagerPropertiesのキー：DEFAUILT_LOGIN デフォルトログイン方法<br></jp><en>eManagerProperties Key : DEFAULT_LOGIN<br></en> */
    public static final String EMPARAMKEY_DEFAULTLOGIN = "DEFAULT_LOGIN";

    /** <jp>eManagerPropertiesのキー：FRAME_A_URI フレームＡのURI<br></jp><en>eManagerProperties Key : FRAME_A_URI<br></en> */
    public static final String EMPARAMKEY_FRAME_A_URI = "FRAME_A_URI";

    /** <jp>eManagerPropertiesのキー：FRAME_B_URI フレームＢのURI<br></jp><en>eManagerProperties Key : FRAME_B_URI<br></en> */
    public static final String EMPARAMKEY_FRAME_B_URI = "FRAME_B_URI";

    /** <jp>eManagerPropertiesのキー：MAINMENU_LABELDISPTYPE メインメニューの文字列の表示タイプ<br></jp>
     * <en>eManagerProperties Key : MAINMENU_LABELDISPTYPE<br></en> */
    public static final String EMPARAMKEY_MAINMENU_LABELDISPTYPE = "MAINMENU_LABELDISPTYPE";

    /** <jp>eManagerPropertiesのキー：CUROUSEL_SPEED メインメニュー移動速度<br></jp>
     * <en>eManagerProperties Key : CUROUSEL_SPEED<br></en> */
    public static final String EMPARAMKEY_CUROUSEL_SPEED = "CUROUSEL_SPEED";

    /** <jp>eManagerPropertiesのキー：CUROUSEL_DIV_WIDTH メインメニュー幅<br></jp>
     * <en>eManagerProperties Key : CUROUSEL_DIV_WIDTH<br></en> */
    public static final String EMPARAMKEY_CUROUSEL_DIV_WIDTH = "CUROUSEL_DIV_WIDTH";

    /** <jp>eManagerPropertiesのキー：LOGVIEW_DISPLAY ログ表示ボタンを表示させる為のフラグ<br></jp>
     * <en>eManagerProperties Key : LOGVIEW_DISPLAY<br></en> */
    public static final String EMPARAMKEY_LOGVIEW_DISPLAY = "LOGVIEW_DISPLAY";

    /** <jp>eManagerPropertiesのキー：LOGGING_LANG ログ出力の言語<br></jp><en>eManagerProperties Key : LOGGING_LANG<br></en> */
    public static final String EMPARAMKEY_LOGGING_LANG = "LOGGING_LANG";

    /** <jp>eManagerPropertiesのキー：PRODUCT_NAME プロダクト名 <br></jp><en>eManagerProperties Key : PRODUCT_NAME<br></en> */
    public static final String EMPARAMKEY_PRODUCT_NAME = "PRODUCT_NAME";

    /** <jp>eManagerPropertiesのキー：PRODUCT_TITLE タイトル <br></jp><en>eManagerProperties Key : TITLE<br></en> */
    public static final String EMPARAMKEY_PRODUCT_TITLE = "TITLE";

    /** <jp>eManagerPropertiesのキー：LOGIN_YPOSITION ログインフィールドの縦位置 <br></jp><en>eManagerProperties Key : LOGIN_YPOSITION<br></en> */
    public static final String EMPARAMKEY_YLOGIN_POSITION = "LOGINFIELD_Y_POSITION";

    /** <jp>eManagerPropertiesのキー：PART11LOG_BACKUP_PATH Part11用ログのバックアップパス <br></jp> */
    public static final String PART11LOG_BACKUP_PATH = "PART11LOG_BACKUP_PATH";

    /** <jp>eManagerPropertiesのキー：PART11LOG_EXTERNAL_DISK_PATH Part11用ログの外部ディスクのパス <br></jp> */
    public static final String PART11LOG_EXTERNAL_DISK_PATH = "PART11LOG_EXTERNAL_DISK_PATH";

    /** <jp>eManagerPropertiesのキー：PART11LOG_FILE_SUFFIX Part11用ログファイルの接尾辞フォーマット <br></jp> */
    public static final String PART11LOG_FILE_SUFFIX_FORMAT = "PART11LOG_FILE_SUFFIX_FORMAT";

// 2008/12/08 K.Matsuda Start 自動ログインユーザのタイムアウト値を追加,PRODUCT_NAMEの定数を追加
    /** <jp>eManagerPropertiesのキー：AUTOLOGIN_TIMEOUT 自動ログインユーザのタイムアウト値 <br></jp> */
    public static final String EMPARAMKEY_AUTOLOGIN_TIMEOUT = "AUTOLOGIN_TIMEOUT";
    
    /** <jp>eManagerPropertiesで使用する値：PRODUCT_NAME wms eWareNaviの定数 <br></jp> */
    public static final String PRODUCT_NAME_WMS = "wms";
    
    /** <jp>eManagerPropertiesで使用する値：PRODUCT_NAME awc eAwcの定数 <br></jp> */
    public static final String PRODUCT_NAME_AWC = "awc";
// 2008/12/08 K.Matsuda End

    /** part 11 log view KEY.. to know either log view is DB or CSV*/
    public static final String PART11LOG_VIEW_KEY = "PART11LOG_VIEW_KEY";
    
    // ViewState KEY
    /** <jp>登録、修正、削除ボタンをViewStateへ保持するためのキー <br></jp><en>Key for action value in viewstate. <br></en> */
    public static final String VSKEY_PROCESS_KEY = "PROCESS_KEY";

    /** <jp>登録ボタン押下時のキー <br></jp><en>Key on click INSERT button. <br></en> */
    public static final String VSKEY_INSERT = "INSERT";

    /** <jp>修正ボタン押下時のキー <br></jp><en>Key on click MODIFY button. <br></en> */
    public static final String VSKEY_MODIFY = "MODIFY";

    /** <jp>削除ボタン押下時のキー <br></jp><en>Key on click DELETE button. <br></en> */
    public static final String VSKEY_DELETE = "DELETE";

    /** <jp>ログインの失敗数をViewStateに保持する為のキー<br></jp><en>ViewState Key : Login failed count<br></en> */
    public static final String VSKEY_FAILEDCOUNT = "VSKEY_FAILEDCOUNT";

    /** <jp>戻り先の画面のURIをViewStateに保持する為のキー<br></jp><en>ViewState Key : URI of previous screen.<br></en> */
    public static final String VSKEY_BACKURI = "VSKEY_BACKURI";

    /** <jp>次の画面のURIをViewStateに保持する為のキー<br></jp><en> ViewState Key : URI of next screen.<br></en> */
    public static final String VSKEY_NEXTURI = "VSKEY_NEXTURI";

    /** <jp><br></jp><en> Old password KEY<br></en> */
    public static final String VSKEY_OLDPWD_KEY = "VSKEY_OLDPWD_KEY";

    // Request parameter key
    /** <jp>画面ロック時間をリクエストに保持する為のキー<br></jp><en>Request parameter KEY : Wait interval on screen lock<br></en> */
    public static final String RKEY_WAITINTERVAL = "WAITINTERVAL";

    /** <jp>機能画面のURIをリクエストに保持する為のキー<br></jp><en>Request parameter KEY : URI of function screen.<br></en> */
    public static final String RKEY_FUNCTIONURI = "FUNCTIONURI";

    /** <jp>機能画面の FunctionId をリクエストに保持する為のキー<br></jp><en>Request parameter KEY : Function ID<br></en> */
    public static final String RKEY_FUNCTIONID = "FUNCTIONID";

    /** <jp>機能画面の FunctionId をリクエストに保持する為のキー<br></jp><en>Request parameter KEY : Screen Login check flag<br></en> */
    public static final String RKEY_SCREENLOGIN_FLAG = "SCREENLOGIN_FLAG";

    // Authentication status coded
    /** <jp>認証結果：認証成功<br></jp><en>Authentication result : Fine<br></en> */
    public static final int AUTHENTICATION_DEFAULT = 0;

    /** <jp>認証結果：仮パスワード状態<br></jp><en>Authentication result : Dummy passoword<br></en> */
    public static final int AUTHENTICATION_DUMMYPASSWORD = 1;

    /** <jp>認証結果：期限切れ間近<br></jp><en>Authentication result : Password expire alert<br></en> */
    public static final int AUTHENTICATION_PWDEXPIRE_ALERT = 2;

    /** <jp>認証結果：ユーザロック<br></jp><en>Authentication result : User lock<br></en> */
    public static final int AUTHENTICATION_USERLOCK = 3;
    
// 2008/12/10 K.Matsuda Start 追加
    /** <jp>認証結果：パスワード期限切れ<br></jp><en>Authentication result : Password is expired<br></en> */
    public static final int AUTHENTICATION_PWDEXPIRED = 4;
// 2008/12/10 K.Matsuda End

    // DB status
    /** <jp>更新区分：追加<br></jp><en>Update kind status : INSERT<br></en> */
    public static final int UPDATE_KIND_INSERT = 1;

    /** <jp>更新区分：修正<br></jp><en>Update kind status : UPDATE<br></en> */
    public static final int UPDATE_KIND_UPDATE = 2;

    /** <jp>メインメニュータイプ：Ａタイプ（小アイコン）<br></jp><en>Value of main menu type A<br></en> */
    public static final int MAINMENUSHOWTYPE_A = 0;

    /** <jp>メインメニュータイプ：Ｂタイプ（大アイコン）<br></jp><en>Value of main menu type B<br></en> */
    public static final int MAINMENUSHOWTYPE_B = 1;

    /** <jp>メンテナンスログ区分：ユーザ<br></jp><en>Maintenance log class : USER<br></en> */
    public static final int MAINTELOG_CLASS_USER = 1;

    /** <jp>メンテナンスログ区分：端末<br></jp><en>Maintenance log class : TERMINAL<br></en> */
    public static final int MAINTELOG_CLASS_TERMINAL = 2;

    /** <jp>メンテナンスログ区分：ロール<br></jp><en>Maintenance log class : ROLE<br></en> */
    public static final int MAINTELOG_CLASS_ROLE = 3;

    /** <jp>メンテナンスログ区分：メニュー<br></jp><en>Maintenance log class : MENU<br></en> */
    public static final int MAINTELOG_CLASS_MENU = 4;

    /** <jp>メンテナンスログ区分：システム<br></jp><en>Maintenance log class : SYSTEM<br></en> */
    public static final int MAINTELOG_CLASS_SYSTEM = 5;

    /** <jp>認証ログ区分：ログイン<br></jp><en>Authentication log class : LOGIN<br></en> */
    public static final int AUTHLOG_CLASS_LOGIN = 1;

    /** <jp>認証ログ区分：ログアウト<br></jp><en>Authentication log class : LOGOUT<br></en> */
    public static final int AUTHLOG_CLASS_LOGOUT = 2;

    /** <jp>認証ログ区分：タイムアウト<br></jp><en>Authentication log class : TIMEOUT<br></en> */
    public static final int AUTHLOG_CLASS_TIMEOUT = 3;

    /** <jp>認証ログ区分：ログイン失敗<br></jp><en>Authentication log class : LOGIN FAILED<br></en> */
    public static final int AUTHLOG_CLASS_FAILED = 4;

    /** No Login Screen access */
    public static final int AUTHLOG_CLASS_NOSCREENACESS = 5;

    /** <jp>認証ミス猶予回数：制限なし<br></jp><en>Failed attempts status : No limited<br></en> */
    public static final int FAILED_ATTEMPTS_STATUS_NOLIMITED = -1;

    /** <jp>認証ミス猶予回数：継承<br></jp><en>Failed attempts status : Extends<br></en> */
    public static final int FAILED_ATTEMPTS_STATUS_EXTEND = -2;

    /** <jp>パスワード更新間隔：制限なし<br></jp><en>Password change interval status : No limited<br></en> */
    public static final int PWDCHANGEINTERVAL_STATUS_NOLIMITED = -1;

    /** <jp>パスワード更新間隔：継承<br></jp><en>Password change interval status : Extends<br></en> */
    public static final int PWDCHANGEINTERVAL_STATUS_EXTEND = -2;

    /** <jp>同一ユーザログイン最大数：無制限<br></jp><en>Same user login max status : No limited<br></en> */
    public static final int SAMEUSERLOGINMAX_STATUS_NOLIMITED = -1;

    /** <jp>メニュー表示順：未使用<br></jp><en>Menu display order : Not display<br></en> */
    public static final int DISPORDER_NODISPLAY = -1;

    /** <jp>同一ユーザ作成禁止期間：無期限<br></jp><en>Same user Block Period status : No limited<br></en> */
    public static final int SAMEUSER_BLOCK_PERIOD_STATUS_NOLIMITED = -1;

    /** <jp>削除ステータス：有効<br></jp><en></en> */
    public static final int DELETESTATUS_EFFECTIVE = 0;

    /** <jp>削除ステータス：削除<br></jp><en></en> */
    public static final int DELETESTATUS_DELETE = 9;

    // Special value
    /** <jp>特殊ロール：admin<br></jp><en>Special role : ADMIN <br></en> */
    public static final String ADMIN_ROLE = "admin";

    /** <jp>特殊ユーザ：ANONYMOUSE_USER<br></jp><en>Special user : ANONYMOUS_USER<br></en> */
    public static final String ANONYMOUS_USER = "AUTOUSER";

    /** <jp>特殊ユーザ：DAIFUKU_SV<br></jp><en>Special user : DAIFUKU_SV<br></en> */
    public static final String DAIFUKU_SV_USER = "DAIFUKU";

// 2009/07/09 T.Kajiwara Add Serviceユーザを追加
    /** <jp>特殊ユーザ：DAIFUKU_PRIVATE<br></jp><en>Special user : DAIFUKU_PRIVATE<br></en> */
    public static final String DAIFUKU_PRIVATE_USER = "Service";
// 2009/07/09 T.Kajiwara End
    
    /** <jp>特殊ユーザ：ユーザメンテナンス<br></jp><en>Special user : USERMAINTENANCE_USER<br></en> */
    public static final String USERMAINTENANCE_USER = "USER_MNT";

    /** <jp>特殊端末：UNDEFINED_TERMINAL<br></jp><en>Special terminal : UNDEFINED_TERMINAL<br></en> */
    public static final String UNDEFINED_TERMINAL = "UNDEFINED_TERMINAL";

    /** <jp><br></jp><en>Minimum session timeout, in seconds<br></en> */
    public static final int SESSIONTIMEOUT_MIN = 10;

    /** <jp><br></jp><en>Maximum session timeout, in seconds<br></en> */
    public static final int SESSIONTIMEOUT_MAX = 86400;

    public static final int LOCKSTATUS_ALL = -1;

    public static final int LOCKSTATUS_ACTIVE = 0;

    public static final int LOCKSTATUS_LOCKED = 1;

    public static final String ROLE_ALL = "ALL";

// 2008/12/08 K.Matsuda Start
    // ユーザ状態に無効を追加 および
    // テーブル列名変更(USERLOCK_FLAG -> USERSTATUS)に伴い名称変更
    /** ユーザ状態：有効 */
    public static final int USERSTATUS_ACTIVE = 1;
    /** ユーザ状態：無効 */
    public static final int USERSTATUS_DISABLED = 2;
    /** ユーザ状態：ロック */
    public static final int USERSTATUS_LOCKED = 3;
// 2008/12/08 K.Matsuda End 

    /* Role related Targets */
    /** ロール対象：共通 */
    public static final int ROLE_TARGET_ALL = 0;
    /** ロール対象：ユーザ用 */
    public static final int ROLE_TARGET_USER = 1;
    /** ロール対象：端末用 */
    public static final int ROLE_TARGET_TERMINAL = 2;
    /** 英語ロケール */
    public static final String LOCALE_US = "US";

    //eManager 2.0

    /** <jp>オペレーションログ操作区分：設定</jp> */
    public static final int OPELOG_CLASS_SETTING = 1;

    /** <jp>オペレーションログ操作区分：登録</jp> */
    public static final int OPELOG_CLASS_REGIST = 2;

    /** <jp>オペレーションログ操作区分：修正</jp> */
    public static final int OPELOG_CLASS_MODIFY = 3;

    /** <jp>オペレーションログ操作区分：削除</jp> */
    public static final int OPELOG_CLASS_DELETE = 4;

    /** <jp>オペレーションログ操作区分：全削除</jp> */
    public static final int OPELOG_CLASS_ALL_DELETE = 5;

    /** <jp>オペレーションログ操作区分：キャンセル</jp> */
    public static final int OPELOG_CLASS_CANCEL = 6;

    /** <jp>オペレーションログ操作区分：印刷</jp> */
    public static final int OPELOG_CLASS_PRINT = 11;

    /** <jp>オペレーションログ操作区分：XLS</jp> */
    public static final int OPELOG_CLASS_XLS = 12;

    /** <jp>オペレーションログ操作区分：CSV</jp> */
    public static final int OPELOG_CLASS_CSV = 13;

    /** <jp>オペレーションログ操作区分：プレビュー</jp> */
    public static final int OPELOG_CLASS_PREVIEW = 14;
    
    /** <jp>オペレーションログ操作区分：印刷(リストボックス)</jp> */
    public static final int OPELOG_CLASS_PRINT_LIST = 15;

    /** <jp>オペレーションログ操作区分：XLS(リストボックス)</jp> */
    public static final int OPELOG_CLASS_XLS_LIST = 16;

    /** <jp>オペレーションログ操作区分：CSV(リストボックス)</jp> */
    public static final int OPELOG_CLASS_CSV_LIST = 17;
    
    /** <jp>オペレーションログ操作区分：切断</jp> */
    public static final int OPELOG_CLASS_DISCONNECT = 18;
    
    /** <jp>オペレーションログ操作区分：リセット</jp> */
    public static final int OPELOG_CLASS_RESET = 19;
    
    /** <jp>オペレーションログ操作区分：自動取込</jp> */
    public static final int OPELOG_CLASS_AUTO_LOADING = 21;

    /** <jp>オペレーションログ操作区分：手動取込</jp> */
    public static final int OPELOG_CLASS_MANUAL_LOADING = 22;

    /** <jp>オペレーションログ操作区分：自動報告</jp> */
    public static final int OPELOG_CLASS_AUTO_REPORT = 23;

    /** <jp>オペレーションログ操作区分：手動報告</jp> */
    public static final int OPELOG_CLASS_MANUAL_REPORT = 24;

    /** <jp>オペレーションログ操作区分：プレビュー(リストボックス)</jp> */
    public static final int OPELOG_CLASS_PREVIEW_LIST = 25;
    
    /** <jp>オペレーションログ操作区分：ログイン</jp> */
    public static final int OPELOG_CLASS_LOGIN = 31;

    /** <jp>オペレーションログ操作区分：ログアウト</jp> */
    public static final int OPELOG_CLASS_LOGOUT = 32;

    /** <jp>オペレーションログ操作区分：ロック解除</jp> */
    public static final int OPELOG_CLASS_LOCK_CANCELLATION = 33;

    /** <jp>オペレーションログ操作区分：再パスワード</jp> */
    public static final int OPELOG_CLASS_PASSWORD_REISSUE = 34;

    /** <jp>オペレーションログ操作区分：端末切替設定</jp> */
    public static final int OPELOG_CLASS_TERMINAL_SWITCH = 35;

    /** <jp>オペレーションログ操作区分：端末ユーザ設定</jp> */
    public static final int OPELOG_CLASS_TERMINAL_USER = 36;

    /** <jp>オペレーションログ操作区分：入力</jp> */
    public static final int OPELOG_CLASS_INPUT = 37;

    /** <jp>オペレーションログ操作区分：制御モジュール</jp> */
    public static final int OPELOG_CLASS_CONTROL_MODULE = 41;

    /** <jp>ユーザマスタ更新区分：登録</jp> */
    public static final int USERMASTER_CLASS_REGIST = 1;

    /** <jp>ユーザマスタ更新区分：修正</jp> */
    public static final int USERMASTER_CLASS_MODIFY = 2;

    /** <jp>ユーザマスタ更新区分：削除</jp> */
    public static final int USERMASTER_CLASS_DELETE = 3;

    /** <jp>ログエクスポートインポート設定マスタフラグ：ユーザ管理用マスタ</jp> */
    public static final int LOG_SETTING_TOOLS_MASTER = 1;

    /** <jp>ログエクスポートインポート設定マスタフラグ：プロダクト用マスタ</jp> */
    public static final int LOG_SETTING_PRODUCT_MASTER = 2;

    /** DS番号 */
    public static final String RKEY_DSNUMBER = "DSNUMBER";

    /** TRUE */
    public static final int DB_FLAG_TRUE = 1;

    /** FALSE */
    public static final int DB_FLAG_FALSE = 0;

    /**<jp>オペレーションログパスワード</jp>*/
    public static final String OPELOG_PASSWORD = "XXXXX";

    /**<jp>DS番号：ログイン画面</jp>*/
    public static final String DS_NO_LOGIN = "000101";

    /**<jp>DS番号：自動グイン画面</jp>*/
    public static final String DS_NO_AUTOLOGIN = "000102";

    /**<jp>DS番号：パスワード変更画面</jp>*/
    public static final String DS_NO_CHANGEPASSWORD = "000103";

    /**<jp>DS番号：メインメニュー画面</jp>*/
    public static final String DS_NO_MAINMENU = "000104";

    /**<jp>DS番号：端末切替画面</jp>*/
    public static final String DS_NO_LOGINSUCCESS = "000105";


    /** Part11ログ検索テーブル(取込用) */
    public static final String PART11LOG_VIEW_IMP = "IMP";

    /** Part11ログ検索テーブル(通常用) */
    public static final String PART11LOG_VIEW_DB = "DB";


    /** <en>Default date formats for Oralce<br></en>*/
    public static final String[] ORACLE_DATE_FORMAT = {
            "MM/dd/yyyy HH24:mi:ss.ff3",
            "MM/dd/yyyy",
            "MM/dd/yy",
            "HH24:mi:ss"
    };

    /**<en>Japanese Date formats for Oracle<br></en>*/
    public static final String[] ORACLE_DATE_FORMAT_ja = {
            "yyyy/MM/dd HH24:mi:ss.ff3",
            "yyyy/MM/dd",
            "yy/MM/dd",
            "HH24:mi:ss"
    };

    /**<en>English Date formats for Oracle<br></en>*/
    public static final String[] ORACLE_DATE_FORMAT_en = {
            "MM/dd/yyyy HH24:mi:ss.ff3",
            "MM/dd/yyyy",
            "MM/dd/yy",
            "HH24:mi:ss"
    };

    /**<en>Chinese Date formats for Oracle<br></en>*/
    public static final String[] ORACLE_DATE_FORMAT_zh = {
            "yyyy-MM-dd HH24:mi:ss.ff3",
            "yyyy-MM-dd",
            "yy-MM-dd",
            "HH24:mi:ss"
    };

    /**
     * LOG_DATEの時間用フォーマット
     */
    public static final String MILLISECOND_FORMAT = "HH:mm:ss.SSS";
    

}
//end of class