package jp.co.daifuku.wms.base.common.tool.logViewer;

/**
 * 動作モードクラスです。
 */
public class OperationMode
{
	public static final int RFT = 1;
	public static final int AS21 = 2;
	public static final String ARG_RFT = "-rft";
	public static final String ARG_AS21 = "-as21";
	public static final String RFT_PROPERTIES_FILE = "RftLogViewer.properties";
	public static final String AS21_PROPERTIES_FILE = "As21LogViewer.properties";
	
	protected static int mode = RFT;
	
    /**
     * 動作モードを設定します。
     * 
     * @param args 動作モード
     */
	public static void setMode(String arg)
	{
		if (arg.equals(ARG_RFT))
		{
			mode = RFT;
		}
		else if (arg.equals(ARG_AS21))
		{
			mode = AS21;
		}
	}

    /**
     * propertiesファイル名を取得します。
     * 
     * @return ファイル名
     */
	public static String getPropertyFileName()
	{
		if (mode == RFT)
		{
			return RFT_PROPERTIES_FILE;
		}
		else if (mode == AS21)
		{
			return AS21_PROPERTIES_FILE;
		}
		
		return null;
	}

    /**
     * RFT号機Noパネルを取得します。
     * 
     * @return RFT号機Noパネル
     */
	public static LvRftNo getRftNoInstance()
	{
		return getRftNoInstance(true, "");
	}
	
    /**
     * RFT号機Noコンポーネントを取得します。
     * 
     * @param enabled 入力可／入力不可
     * @param text RFT号機No
     * @return RFT号機Noコンポーネント
     */
	public static LvRftNo getRftNoInstance(boolean enabled, String text)
	{
		if (mode == AS21)
		{
			return new LvHostname(enabled, text);
		}
		else
		{
			return new LvRftNo(enabled, text);
		}
	}

    /**
     * 通信トレースログのインスタンスを取得します。
     * 
     * @return 通信トレースログのインスタンス
     */
	public static TraceLogFile getTraceLogFileInstance()
	{
		if (mode == AS21)
		{
			return new As21TraceLogFile();
		}
		else
		{
			return new TraceLogFile();
		}
	}
}
