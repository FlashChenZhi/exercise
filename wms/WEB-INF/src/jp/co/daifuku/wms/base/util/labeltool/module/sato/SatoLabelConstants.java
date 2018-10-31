package jp.co.daifuku.wms.base.util.labeltool.module.sato ;

public class SatoLabelConstants
{

	/**
	 * ステータス一覧(getStatus)
	 */
	/** オフライン・エラー無 */
	public static final String STATUS_OFFLINE_NOERR = "0" ;

	/** オンライン・受信待ち・エラー無 */
	public static final String STATUS_ONLINE_MESSAGE_WAIT_NOERR = "A" ;
    
    /** オンライン・受信待ち・リボンニアエンド */
    public static final String STATUS_ONLINE_MESSAGE_WAIT_RIBONNIAEND = "B";
    
    /** オンライン・受信待ち・バッファニアフル */
    public static final String STATUS_ONLINE_MESSAGE_WAIT_BAFFANIAFULL = "C";
    
    /** オンライン・受信待ち・リボンニアエンド＆バッファニアフル */
    public static final String STATUS_ONLINE_MESSAGE_WAIT_RIBON_BAFFA = "D";

	/** オンライン・印字中・エラー無 */
	public static final String STATUS_ONLINE_IN_PRINT_NOERR = "G" ;

    /** オンライン・印字中・リボンニアエンド */
    public static final String STATUS_ONLINE_IN_PRINT_RIBONNIAEND = "H" ;
    
    /** オンライン・印字中・バッファニアフル */
    public static final String STATUS_ONLINE_IN_PRINT_BAFFANIAFULL = "I" ;
    
    /** オンライン・印字中・リボンニアエンド＆バッファニアフル */
    public static final String STATUS_ONLINE_IN_WAIT_RIBON_BAFFA = "J" ;
        
	/** オンライン・待機中・エラー無 */
	public static final String STATUS_ONLINE_IN_WAIT_NOERR = "M" ;
    
    /** オンライン・待機中・リボンニアエンド */
    public static final String STATUS_ONLINE_IN_WAIT_RIBONNIAEND = "N" ;
    
    /** オンライン・待機中・バッファニアフル */
    public static final String STATUS_ONLINE_IN_WAIT_BAFFANIAFULL = "O" ;
    
    /** オンライン・待機中・リボンニアエンド＆バッファニアフル */
    public static final String STATUS_ONLINE_IN_RIBON_BAFFA = "P" ;

	/** オンライン・解析・編集中・エラー無 */
	public static final String STATUS_ONLINE_IN_COMPILATION_NOERR = "S" ;
    
    /** オンライン・解析・編集中・リボンニアエンド */
    public static final String STATUS_ONLINE_IN_COMPILATION_RIBONNIAEND = "T" ;
    
    /** オンライン・解析・編集中・バッファニアフル */
    public static final String STATUS_ONLINE_IN_COMPILATION_BAFFANIAFULL = "U" ;
    
    /** オンライン・解析・編集中・リボンニアエンド＆バッファニアフル */
    public static final String STATUS_ONLINE_IN_COMPILATION_RIBON_BAFFA = "V" ;

    /** エラー検出 バッファオーバー */
    public static final String STATUS_ERR_BAFFA_OVER = "a" ;
    
	/** エラー検出 ヘッドオープン */
	public static final String STATUS_ERR_HEADOPEN = "b" ;

	/** エラー検出 ペーパエンド */
	public static final String STATUS_ERR_PAPPEREND = "c" ;

    /** エラー検出 リボンエンド */
    public static final String STATUS_ERR_RIBONEND = "d" ;
    
    /** エラー検出 センサーエラー */
    public static final String STATUS_ERR_SENSAERR = "f" ;
	
    /** エラー検出 ヘッドエラー */
    public static final String STATUS_ERR_HEDDOERR = "g" ;
    
    /** エラー検出 カバーオープン */
    public static final String STATUS_ERR_KABAERR = "h" ;
    
    /** エラー検出 カードエラー */
    public static final String STATUS_ERR_KADOERR = "i" ;
    
    /** エラー検出 カッタエラー */
    public static final String STATUS_ERR_CAATAERR = "j" ;
    
    /** エラー検出 その他のエラー */
    public static final String STATUS_ERR_OTHERERR = "k" ;
    
    /**
	 * エラーメッセージ表
	 */
	/** フラッシュロムエラー */
	public static final String ERR_ROMERR = "0" ;

	/** マシンエラー */
	public static final String ERR_MACHINE = "2" ;

}
