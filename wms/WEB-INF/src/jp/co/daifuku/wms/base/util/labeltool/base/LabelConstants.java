// $$Id: LabelConstants.java 6565 2009-12-18 10:28:04Z kumano $$
package jp.co.daifuku.wms.base.util.labeltool.base ;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 定数クラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/07/01</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 6565 $, $Date: 2009-12-18 19:28:04 +0900 (金, 18 12 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class LabelConstants
{
	/**spiltStr IST*/
	public static final String CMD_IST = "IST" ;

	/**pageNm*/
	public static final String PAGE_ITEM = "{PAGENO}" ;

	/** <code>NEW_LINE_STR</code> */
	public static final String NEW_LINE_STR = "\n" ;

	/** <code>ENTER_STR</code> */
	public static final String ENTER_STR = "\r" ;

	/** <code>CR_STR</code> */
	public static final String CR_STR = "\r\n" ;

	/** <code>COMMA_STR</code> */
	public static final String COMMA_STR = "," ;

	/** <code>FLAG_ON</code> */
	public static final String FLAG_ON = "1" ;

	/** <code>FLAG_OFF</code> */
	public static final String FLAG_OFF = "0" ;

	/** <code>ESCAPE</code> */
	public static final String ESC = new String(new byte[] {
		27
	}) ;

	/** <code>STX</code> */
	public static final String STX = new String(new byte[] {
		0x2
	}) ; ;

	/** <code>ETX</code> */
	public static final String ETX = new String(new byte[] {
		0x3
	}) ; ;

	/** <code>カスタマイズする繰り返し体定義コマンド名</code> */
	public static final String CMD_REPEAT_DEF = "DSS" ;

	/** <code>カスタマイズするページ表示コマンド名</code> */
	public static final String CMD_PG = "PG" ;

	/** <code>印刷枚数指定コマンド名</code> */
	public static final String CMD_PRINT_NUMBER = "Q" ;

	/** <code>ジョブＩＤ番号指定コマンド名</code> */
	public static final String CMD_JOB_ID = "ID" ;

	/** <code>ジョブ名指定コマンド名</code> */
	public static final String CMD_JOB_NAME = "WK" ;

	/** <code>データ送出開始指定コマンド名</code> */
	public static final String CMD_ITEM_START = "A" ;

	/** <code>データ送出終了指定コマンド名</code> */
	public static final String CMD_ITEM_END = "Z" ;

	/** <code>用紙サイズ指定コマンド名</code> */
	public static final String CMD_SIZE = "A1" ;

	/** <code>用紙サイズ指定コマンド名SRプリンタ用</code> */
	public static final String CMD_SIZE_SR = "A1V" ;

	/** <code>フォームオーバレイ登録指定コマンド名</code> */
	public static final String CMD_FORM_REG = "&" ;

	/** <code>部分編集指定コマンド名</code> */
	public static final String CMD_PART_EDIT_FLAG = "0" ;

	/** <code>印字横位置指定コマンド名</code> */
	public static final String CMD_H_POSITION = "H" ;

	/** <code>印字縦位置指定コマンド名</code> */
	public static final String CMD_V_POSITION = "V" ;

	/** <code>回転指定コマンド名</code> */
	public static final String CMD_ROTATION = "%" ;

	/** <code>文字間ピッチ指定コマンド名</code> */
	public static final String CMD_PITCH = "P" ;

	/** <code>拡大指定コマンド名</code> */
	public static final String CMD_ENLARGE_RATIO = "L" ;

	/** <code>プロポーショナルピッチ指定コマンド名</code> */
	public static final String CMD_PROP_PITCH_MODE_START = "PS" ;

	/** <code>プロポーショナルピッチ解除コマンド名</code> */
	public static final String CMD_PROP_PITCH_MODE_END = "PR" ;

	/** <code>自動改行指定コマンド名</code> */
	public static final String CMD_LINE_PITCH = "E" ;

	/** <code>連番指定コマンド名</code> */
	public static final String CMD_SEQUENCE_PARA = "F" ;

	/** <code>漢字コード指定コマンド名</code> */
	public static final String CMD_KANJI_CODE = "KC" ;

	/** <code>バーコード比率登録指定コマンド名</code> */
	public static final String CMD_BAR_CODE_BT = "BT" ;

	/** <code>Ｘ２０フォント指定コマンド名</code> */
	public static final String CMD_FONTTYPE_X20 = "X20" ;

	/** <code>Ｘ２１フォント指定コマンド名</code> */
	public static final String CMD_FONTTYPE_X21 = "X21" ;

	/** <code>Ｘ２２フォント指定コマンド名</code> */
	public static final String CMD_FONTTYPE_X22 = "X22" ;

	/** <code>Ｘ２３フォント指定コマンド名</code> */
	public static final String CMD_FONTTYPE_X23 = "X23" ;

	/** <code>Ｘ２４フォント指定コマンド名</code> */
	public static final String CMD_FONTTYPE_X24 = "X24" ;

	/** <code>ＯＣＲ－Ａフォント指定コマンド名</code> */
	public static final String CMD_FONTTYPE_OA = "OA" ;

	/** <code>ＯＣＲ－Ｂフォント指定コマンド名</code> */
	public static final String CMD_FONTTYPE_OB = "OB" ;

	/** <code>アウトラインフォント形状指定コマンド名</code> */
	public static final String CMD_OTL_FONT_SHAPE_SPEC = "$" ;

	/** <code>アウトラインフォント印字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_OUTLINE = "$=" ;

	/** <code>１６×１６ドット横書き漢字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_K1 = "K1" ;

	/** <code>２４×２４ドット横書き漢字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_K2 = "K2" ;

	/** <code>２２×２２ドット横書き漢字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_K3 = "K3" ;
    
    /** <code>３２×３２ドット横書き漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_K4 = "K4" ;

    /** <code>４０×４０ドット横書き漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_K5 = "K5" ;
    
	/** <code>１６×１６ドット横書き半角・全角混在漢字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_K8 = "K8" ;

	/** <code>２４×２４ドット横書き半角・全角混在漢字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_K9 = "K9" ;

    /** <code>２２×２２ドット横書き半角・全角混在漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_KA = "KA" ;
    
    /** <code>２２×２２ドット横書き半角・全角混在漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_KB = "KB" ;
    
    /** <code>２２×２２ドット横書き半角・全角混在漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_KD = "KD" ;
    
	/** <code>１６×１６ドット縦書き漢字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_K1S = "k1" ;

	/** <code>２４×２４ドット縦書き漢字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_K2S = "k2" ;

	/** <code>２２×２２ドット縦書き漢字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_K3S = "k3" ;

    /** <code>２２×２２ドット縦書き漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_K4S = "k4" ;
    
    /** <code>２２×２２ドット縦書き漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_K5S = "k5" ;
    
	/** <code>１６×１６ドット縦書き半角・全角混在漢字指定コマンド名</code> */
	public static final String CMD_FONTTYPE_K8S = "k8" ;

    /** <code>２４×２４ドット縦書き半角・全角混在漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_K9S = "k9" ;
    
    /** <code>２４×２４ドット縦書き半角・全角混在漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_KAS = "kA" ;
    
    /** <code>２４×２４ドット縦書き半角・全角混在漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_KBS = "kB" ;
    
    /** <code>２４×２４ドット縦書き半角・全角混在漢字指定コマンド名</code> */
    public static final String CMD_FONTTYPE_KDS = "kD" ;
    
    /** <code>WLフォント指定コマンド名</code> */
    public static final String CMD_FONTTYPE_WB = "WB" ;
    
    /** <code>WLフォント指定コマンド名</code> */
    public static final String CMD_FONTTYPE_WL = "WL" ;
    
	/** <code>５×９ Uフォント</code> */
	public static final String CMD_FONTTYPE_U = "U" ;
    
    /** <code>８×１５ Sフォント</code> */
    public static final String CMD_FONTTYPE_S = "S" ;
    
    /** <code>５×９ Mフォント</code> */
    public static final String CMD_FONTTYPE_M = "M" ;
    
    /** <code>５×９ XUフォント</code> */
    public static final String CMD_FONTTYPE_XU = "XU" ;
    
    /** <code>１７×１７ XSフォント</code> */
    public static final String CMD_FONTTYPE_XS = "XS" ;
    
    /** <code>２４×２４ XMフォント</code> */
    public static final String CMD_FONTTYPE_XM = "XM" ;
    
    /** <code>４８×４８ XBフォント</code> */
    public static final String CMD_FONTTYPE_XB = "XB" ;
    
    /** <code>４８×４８ XLフォント</code> */
    public static final String CMD_FONTTYPE_XL = "XL" ;
    
	/** <code>罫線、枠線印字指定コマンド名</code> */
	public static final String CMD_RULER_FRAME = "FW" ;

	/** <code>バーコード指定（比率１：３）コマンド名</code> */
	public static final String CMD_BARCODE_B = "B" ;

	/** <code>バーコード指定（比率１：２）コマンド名</code> */
	public static final String CMD_BARCODE_D = "D" ;

	/** <code>比率登録によるバーコード印字指定コマンド名</code> */
	public static final String CMD_BARCODE_BD = "BD" ;

	/** <code>比率登録によるバーコード印字指定コマンド名</code> */
	public static final String CMD_BARCODE_BW = "BW" ;

	/** <code>ＣＯＤＥ９３バーコード指定コマンド名</code> */
	public static final String CMD_BARCODE_BC = "BC" ;

	/** <code>ＵＣＣ／ＥＡＮ１２８指定コマンド名</code> */
	public static final String CMD_BARCODE_BI = "BI" ;

	/** <code>ＣＯＤＥ１２８バーコード指定コマンド名</code> */
	public static final String CMD_BARCODE_BG = "BG" ;

	/** <code>ＵＰＣアドオンバーコード指定コマンド名</code> */
	public static final String CMD_BARCODE_BF = "BF" ;

	/** <code>QRコード(モデル2)指定コマンド名</code> */
	public static final String CMD_QRCODE_2D30 = "2D30";
	
    /** <code>QRコード(モデル1)指定コマンド名</code> */
    public static final String CMD_QRCODE_2D31 = "2D31";
    
    /** <code>マイクロQRコード指定コマンド名</code> */
    public static final String CMD_QRCODE_2D32 = "2D32";
    
    /** <code>QRコード(データ部)(入力モード指定)指定コマンド名</code> */
    public static final String CMD_QRCODE_DS = "DS";
    
    /** <code>QRコード(データ部)指定コマンド名</code> */
    public static final String CMD_QRCODE_DN = "DN";
	
	/** <code>白黒反転印字指定コマンド名</code> */
	public static final String CMD_BW_INVERSION = "(" ;

	/** <code>部分コピー指定コマンド名</code> */
	public static final String CMD_PART_COPY = "WD" ;

	/** <code>フォームオーバレイ呼出し指定コマンド名</code> */
	public static final String CMD_FORM_CALLER = "/" ;

	/** <code>START CODEのprefix</code> */
	public static final String START_CODE_PREFIX = ">" ;

	/** <code>START_CODE_H</code> */
	public static final String START_CODE_H = "H" ;

	/** <code>EAN、UCC合成シンボル指定コマンド名</code>のコメント */
	public static final String CMD_COMPOSITE_SYMBOL = "EU" ;
    
    /** <code>カット範囲枚数指定コマンド名</code>のコメント */
    public static final String CMD_CUT_CT = "CT" ;
    
    /** <code>搬出カット動作指定コマンド名</code>のコメント */
    public static final String CMD_CUT_NC = "NC" ;

	/** <code>回転方向_パラレル１（0°）</code> */
	public static final String ROTATION_PARRALLEL1 = "0" ;

	/** <code>文字間ピッチのディフォルト値</code> */
	public static final int PITCH_DEFAULT_VALUE = 2 ;

	/** <code>ディフォルトのアウトラインフォント形状指定パラメータ</code> */
	public static final String DEFAULT_OUTLINE_FONT_SPEC = "A,050,050,0" ;

	/** <code>ディフォルトの横方向拡大倍率</code> */
	public static final int DEFAULT_H_ENLARGE_RATIO = 1 ;

	/** <code>ディフォルトの縦方向拡大倍率</code> */
	public static final int DEFAULT_V_ENLARGE_RATIO = 1 ;

	/** <code>プロポーショナルピッチモード</code> */
	public static final String PITCH_MODE_PROP = "1" ;

	/** <code>固定ピッチモード</code> */
	public static final String PITCH_MODE_FIX = "0" ;

	/** <code>漢字コード_SJIS</code> */
	public static final String KANJI_CODE_SJIS = "1" ;

	/** <code>漢字コード_JIS</code> */
	public static final String KANJI_CODE_JIS = "0" ;

	/** <code>ページ番号のみ表示モード</code> */
	public static final String PAGE_FMT_NUMBER_ONLY = "1" ;

	/** <code>ページ番号/総ページ数表示モード</code> */
	public static final String PAGE_FMT_FULL = "0" ;

	/** <code>ディレクトリ分割文字列</code> */
	public static final String DIRSEPACHAR = "\\" ;

	/** <code>ユーザディレクトリ宣言</code> */
	public static final String USR_DIR = "C:" + DIRSEPACHAR + "LabelPrint" ;

	/** <code>ローカル側アプリのルートパス</code> */
	public static final String LOCAL_PATH = System.getProperty("user.dir") ;

	/** <code>環境定義ファイルを格納するパス</code> */
	public static final String CONF_PATH = "C:\\daifuku\\wms\\tomcat\\webapps\\wms\\WEB-INF" ;

	/** <code>ローカル側データのルートパス</code> */
	public static final String LOCAL_DATA_PATH = System.getProperty("user.dir")
			+ DIRSEPACHAR
			+ "data" ;

	/** <code>環境定義ファイル名</code> */
	public static final String ENVIRONMENT_FILENAME = "EnvironmentDef.xml" ;

	/** <code>ラベル情報管理XMLファイル名</code> */
	public static final String LABEL_MANAGE_FILE_NAME = "manage.xml" ;

	/** <code>ラベル情報ロックファイル名</code> */
	public static final String LOCK_FILE_NAME = "manage.lock" ;

	/** <code>DB定義情報XMLファイル名</code> */
	public static final String DB_DEFINATION_FILE_NAME = "dbDef.xml" ;

	/** <code>DB定義情報ロックファイル名</code> */
	public static final String LOCK_DBFILE_NAME = "dbDef.lock" ;

	/** <code>DB関連情報XMLファイル名</code> */
	public static final String DB_RELATION_FILE_NAME = "dbRelationDef.xml" ;

	/** <code>ユーザ情報XMLファイル名</code> */
	public static final String USER_INFO_FILE_NAME = "UserInfo.xml" ;

	/** <code>XML_ENCODING_STRING</code> */
	public static final String XML_ENCODING_STRING = "utf-8" ;

	/** <code>ラベルレイアウトファイルパス</code> */
	public static final String LAYOUT_PATH = "layout" ;

	/** <code>ラベルXML定義ファイルパス</code> */
	public static final String XML_PATH = "xml" ;

	/** <code>エンティティファイルパス</code> */
	public static final String ENTITY_PATH = "entity" ;


	/** <code>レイアウトファイルの拡張子名</code> */
	public static final String SUFFIX_LAYOUT = ".mllay" ;

	/** <code>XMLファイルの拡張子名</code> */
	public static final String SUFFIX_XML = ".xml" ;

	/** <code>Javaファイル拡張子名</code> */
	public static final String SUFFIX_JAVA = ".java" ;

	/** <code>メッセージ定義ファイル名</code> */
	public static final String MESSAGE_FILENAME = "MessageDef" ;

	/** <code>SBPLコマンド定義ファイル名</code> */
	public static final String SBPL_CMD_DEF_FILENAME = "SBPLCmdDef" ;

	/** <code>パッケージ宣言文字列</code> */
	public static final String ENTITY_PACKAGE_DECLARE = "jp.co.daifuku.wms.label.entity" ;

	// public static final String ENTITY_PACKAGE_DECLARE = "testSrc.Entity";

	/** <code>繰り返しフラグ ON</code> */
	public static final String REPERT_ON = "1" ;

	/** <code>繰り返しフラグ OFF</code> */
	public static final String REPERT_OFF = "0" ;

	/** <code>集合モード カウント</code> */
	public static final String AGGREGATE_MODE_COUNT = "0" ;

	/** <code>集合モード サマリ</code> */
	public static final String AGGREGATE_MODE_SUM = "1" ;

	/** <code>集合モード 集合無</code> */
	public static final String AGGREGATE_MODE_NO = "2" ;

	/** <code>ページ表示形式 総ページ表示</code> */
	public static final String PAGE_TOTAL = "0" ;

	/** <code>ページ表示形式 ページ番号のみ</code> */
	public static final String PAGE_ONLY = "1" ;
	
	/** <code>連結モード 連結モード</code> */
	public static final String CONNECTION_MODE_ON = "1";
	
    /** <code>連結モード 通常モード</code> */
	public static final String CONNECTION_MODE_OFF = "0";

}
